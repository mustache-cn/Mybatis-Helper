package cn.com.mustache.mybatis.generate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.mustache.mybatis.generate.plugin.CommonDAOInterfacePlugin;
import cn.com.mustache.mybatis.generate.plugin.DbRemarksCommentGenerator;
import cn.com.mustache.mybatis.generate.plugin.GeneratorSwagger2DocPlugin;
import cn.com.mustache.mybatis.generate.plugin.JavaTypeResolverJsr310Impl;
import cn.com.mustache.mybatis.generate.plugin.LombokPlugin;
import cn.com.mustache.mybatis.generate.plugin.MySQLForUpdatePlugin;
import cn.com.mustache.mybatis.generate.plugin.MySQLLimitPlugin;
import cn.com.mustache.mybatis.generate.plugin.RepositoryPlugin;
import cn.com.mustache.mybatis.model.Config;
import cn.com.mustache.mybatis.model.DbType;
import cn.com.mustache.mybatis.setting.PersistentConfig;
import cn.com.mustache.mybatis.util.DbToolsUtil;
import cn.com.mustache.mybatis.util.GeneratorCallback;
import cn.com.mustache.mybatis.util.StringUtil;
import com.google.common.base.Strings;
import com.intellij.database.model.RawConnectionConfig;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import org.mybatis.generator.api.IntellijMyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.intellij.IntellijTableInfo;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ??????mybatis????????????
 */
public class MybatisGenerator {
    private static final Logger logger = LoggerFactory.getLogger(MybatisGenerator.class);
    private String currentDbName;
    private Project project;
    private PersistentConfig persistentConfig;//??????????????????
    private final Config config;//??????????????????
    private DbType dbType;//???????????????
    private IntellijTableInfo intellijTableInfo;

    public MybatisGenerator(Config config) {
        this.config = config;
    }

    /**
     * ????????????????????????
     */
    public List<String> execute(final AnActionEvent anActionEvent, boolean saveConfig) throws Exception {
        List<String> result = new ArrayList<>();
        this.project = anActionEvent.getProject();
        this.persistentConfig = PersistentConfig.getInstance(project);

        if (saveConfig) {
            saveConfig();//????????? ???????????????????????????
        }
        final PsiElement[] psiElements = anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY);

        if (psiElements == null || psiElements.length == 0) {
            result.add("can not generate! \nplease select table");
            return result;
        }
        PsiElement psiElement = psiElements[0];
        if (!(psiElement instanceof DbTable)) {
            result.add("can not generate! \nplease select table");
            return result;
        }

        intellijTableInfo = DbToolsUtil.buildIntellijTableInfo((DbTable) psiElement);

        RawConnectionConfig connectionConfig = ((DbTable) psiElements[0]).getDataSource().getConnectionConfig();

        String driverClass = connectionConfig.getDriverClass();
        if (driverClass.contains("mysql")) {
            currentDbName = ((DbTable) psiElements[0]).getParent().getName();
            dbType = DbType.MySQL;
        } else if (driverClass.contains("oracle")) {
            currentDbName = ((DbTable) psiElements[0]).getParent().getName();
            dbType = DbType.Oracle;
        } else if (driverClass.contains("postgresql")) {
            currentDbName = ((DbTable) psiElements[0]).getParent().getParent().getName();
            dbType = DbType.PostgreSQL;
        } else if (driverClass.contains("sqlserver")) {
            currentDbName = ((DbTable) psiElements[0]).getParent().getName();
            dbType = DbType.SqlServer;
        } else if (driverClass.contains("mariadb")) {
            currentDbName = ((DbTable) psiElements[0]).getParent().getName();
            dbType = DbType.MariaDB;
        } else {
            String failMessage = String.format("db type not support!" +
                            "\n your driver class:%s" +
                            "\n current support db type:mysql???mariadb???oracle,postgresql",
                    driverClass);
            Messages.showMessageDialog(project, failMessage,
                    "Test Connection Error", Messages.getInformationIcon());
            return result;
        }

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {

                Configuration configuration = new Configuration();
                Context context = new Context(ModelType.CONDITIONAL);
                configuration.addContext(context);

                context.setId("myid");
                context.addProperty("autoDelimitKeywords", "true");
                context.setIntellij(true);

                if (DbType.MySQL.equals(dbType) || DbType.MariaDB.equals(dbType)) {
                    // ??????beginningDelimiter???endingDelimiter????????????????????????(")??????Mysql????????????????????????????????????????????????????????????`
                    context.addProperty("beginningDelimiter", "`");
                    context.addProperty("endingDelimiter", "`");
                }

                context.addProperty("javaFileEncoding", "UTF-8");
                context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, "UTF-8");
                context.setTargetRuntime("MyBatis3");

                TableConfiguration tableConfig = buildTableConfig(context);
                JavaModelGeneratorConfiguration modelConfig = buildModelConfig();
                SqlMapGeneratorConfiguration mapperConfig = buildMapperXmlConfig();
                JavaClientGeneratorConfiguration daoConfig = buildDaoConfig();
                CommentGeneratorConfiguration commentConfig = buildCommentConfig();

                context.addTableConfiguration(tableConfig);
                context.setJavaModelGeneratorConfiguration(modelConfig);
                context.setSqlMapGeneratorConfiguration(mapperConfig);
                context.setJavaClientGeneratorConfiguration(daoConfig);
                context.setCommentGeneratorConfiguration(commentConfig);
                addPluginConfiguration(context);

                createFolderForNeed(config);
                List<String> warnings = new ArrayList<>();
                ShellCallback shellCallback = new DefaultShellCallback(config.isOverrideJava());
                Set<String> fullyqualifiedTables = new HashSet<>();
                Set<String> contexts = new HashSet<>();
                try {
                    IntellijMyBatisGenerator intellijMyBatisGenerator = new IntellijMyBatisGenerator(configuration, shellCallback, warnings);
                    intellijMyBatisGenerator.generate(new GeneratorCallback(), contexts, fullyqualifiedTables, intellijTableInfo);
                    if (!warnings.isEmpty()) {
                        result.addAll(warnings);
                    }
                } catch (Exception e) {
                    Messages.showMessageDialog(e.getMessage(), "MybatisGenerator Failure", Messages.getErrorIcon());
                    result.add(e.getMessage());
                }
                // project.getBaseDir().refresh(true, true);
                // project.getBaseDir().refresh(false, true);
                VirtualFile baseDir = ProjectUtil.guessProjectDir(project);
                if (baseDir != null) {
                    baseDir.refresh(true, true);
                    baseDir.refresh(false, true);
                }
            }
        });
        return result;
    }

    /**
     * ??????????????????
     */
    private void createFolderForNeed(Config config) {

        String modelMvnPath = config.getModelMvnPath();
        String daoMvnPath = config.getDaoMvnPath();
        String xmlMvnPath = config.getXmlMvnPath();

        String modelPath = config.getProjectFolder() + "/" + modelMvnPath + "/";
        String daoPath = config.getProjectFolder() + "/" + daoMvnPath + "/";
        String xmlPath = config.getProjectFolder() + "/" + xmlMvnPath + "/";

        File modelFile = new File(modelPath);
        if (!modelFile.exists() && !modelFile.isDirectory()) {
            modelFile.mkdirs();
        }

        File daoFile = new File(daoPath);
        if (!daoFile.exists() && !daoFile.isDirectory()) {
            daoFile.mkdirs();
        }

        File xmlFile = new File(xmlPath);
        if (!xmlFile.exists() && !xmlFile.isDirectory()) {
            xmlFile.mkdirs();
        }

    }


    /**
     * ?????????????????????????????????
     */
    private void saveConfig() {
        Map<String, Config> historyConfigList = persistentConfig.getHistoryConfigList();
        if (historyConfigList == null) {
            historyConfigList = new HashMap<>();
        }

        String daoName = config.getDaoName();
        String modelName = config.getModelName();
        String daoPostfix = daoName.replace(modelName, "");
        config.setDaoPostfix(daoPostfix);

        historyConfigList.put(config.getName(), config);
        persistentConfig.setHistoryConfigList(historyConfigList);

    }


    /**
     * ??????table??????
     */
    private TableConfiguration buildTableConfig(Context context) {
        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(config.getTableName());
        tableConfig.setDomainObjectName(config.getModelName());
        String schema;
        if (!Strings.isNullOrEmpty(currentDbName)) {
            schema = currentDbName;
        } else {
            throw new RuntimeException("can not find schema");

        }
        if (dbType.equals(DbType.MySQL)
                || dbType.equals(DbType.MariaDB)
                || dbType.equals(DbType.PostgreSQL)) {
            tableConfig.setSchema(schema);
        } else {
            tableConfig.setCatalog(schema);
        }

        if (!config.isUseExample()) {
            tableConfig.setUpdateByExampleStatementEnabled(false);
            tableConfig.setCountByExampleStatementEnabled(false);
            tableConfig.setDeleteByExampleStatementEnabled(false);
            tableConfig.setSelectByExampleStatementEnabled(false);
        }
        if (config.isUseSchemaPrefix()) {
            if (DbType.MySQL.equals(dbType)) {
                tableConfig.setSchema(schema);
            } else if (DbType.Oracle.equals(dbType)) {
                tableConfig.setSchema(schema);
            } else {
                tableConfig.setCatalog(schema);
            }
        }

        if (DbType.PostgreSQL.equals(dbType)) {
            tableConfig.setDelimitIdentifiers(true);
        }

        if (!StringUtil.isEmpty(config.getPrimaryKey())) {
            if (DbType.MySQL.equals(dbType) || DbType.MariaDB.equals(dbType)) {
                //dbType???JDBC?????????????????????useGeneratedKeys??????Mybatis?????????Jdbc3KeyGenerator,
                //?????????KeyGenerator??????????????????????????????INSERT ??????????????????resultSet???????????? ?????????????????????
                //?????????????????????????????????????????????????????????
                //???????????????RDS + ?????????????????? ??????????????????
                //?????????SelectKey??????Mybatis?????????SelectKeyGenerator???INSERT??????????????????????????????????????????????????????
                //????????????????????????????????????????????????????????????????????????
            }
            tableConfig.setGeneratedKey(new GeneratedKey(config.getPrimaryKey(), "JDBC", true, null));
        }

        if (config.isUseActualColumnNames()) {
            tableConfig.addProperty("useActualColumnNames", "true");
        }

        if (config.isUseTableNameAlias()) {
            tableConfig.setAlias(config.getTableName());
        }

//        if (ignoredColumns != null) {
//            ignoredColumns.stream().forEach(new Consumer<IgnoredColumn>() {
//                @Override
//                public void accept(IgnoredColumn ignoredColumn) {
//                    tableConfig.addIgnoredColumn(ignoredColumn);
//                }
//            });
//        }
//        if (columnOverrides != null) {
//            for (ColumnOverride columnOverride : columnOverrides) {
//                tableConfig.addColumnOverride(columnOverride);
//            }
//        }

        tableConfig.setMapperName(config.getDaoName());
        return tableConfig;
    }


    /**
     * ?????????????????????
     *
     * @return
     */
    private JavaModelGeneratorConfiguration buildModelConfig() {
        String projectFolder = config.getProjectFolder();
        String modelPackage = config.getModelPackage();
        String modelPackageTargetFolder = config.getModelTargetFolder();
        String modelMvnPath = config.getModelMvnPath();

        JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();

        if (!StringUtil.isEmpty(modelPackage)) {
            modelConfig.setTargetPackage(modelPackage);
        } else {
            modelConfig.setTargetPackage("generator");
        }

        if (!StringUtil.isEmpty(modelPackageTargetFolder)) {
            modelConfig.setTargetProject(modelPackageTargetFolder + "/" + modelMvnPath + "/");
        } else {
            modelConfig.setTargetProject(projectFolder + "/" + modelMvnPath + "/");
        }
        return modelConfig;
    }

    /**
     * ??????mapper.xml????????????
     *
     * @return
     */
    private SqlMapGeneratorConfiguration buildMapperXmlConfig() {

        String projectFolder = config.getProjectFolder();
        String mappingXMLPackage = config.getXmlPackage();
        String mappingXMLTargetFolder = config.getProjectFolder();
        String xmlMvnPath = config.getXmlMvnPath();

        SqlMapGeneratorConfiguration mapperConfig = new SqlMapGeneratorConfiguration();

        if (!StringUtil.isEmpty(mappingXMLPackage)) {
            mapperConfig.setTargetPackage(mappingXMLPackage);
        } else {
            mapperConfig.setTargetPackage("generator");
        }

        if (!StringUtil.isEmpty(mappingXMLTargetFolder)) {
            mapperConfig.setTargetProject(mappingXMLTargetFolder + "/" + xmlMvnPath + "/");
        } else {
            mapperConfig.setTargetProject(projectFolder + "/" + xmlMvnPath + "/");
        }

        if (config.isOverrideXML()) {//14
            String mappingXMLFilePath = getMappingXMLFilePath(config);
            File mappingXMLFile = new File(mappingXMLFilePath);
            if (mappingXMLFile.exists()) {
                mappingXMLFile.delete();
            }
        }

        return mapperConfig;
    }

    /**
     * ??????dao??????????????????
     *
     * @return
     */
    private JavaClientGeneratorConfiguration buildDaoConfig() {

        String projectFolder = config.getProjectFolder();
        String daoPackage = config.getDaoPackage();
        String daoTargetFolder = config.getDaoTargetFolder();
        String daoMvnPath = config.getDaoMvnPath();

        JavaClientGeneratorConfiguration daoConfig = new JavaClientGeneratorConfiguration();
        daoConfig.setConfigurationType("XMLMAPPER");
        daoConfig.setTargetPackage(daoPackage);

        if (!StringUtil.isEmpty(daoPackage)) {
            daoConfig.setTargetPackage(daoPackage);
        } else {
            daoConfig.setTargetPackage("generator");
        }

        if (!StringUtil.isEmpty(daoTargetFolder)) {
            daoConfig.setTargetProject(daoTargetFolder + "/" + daoMvnPath + "/");
        } else {
            daoConfig.setTargetProject(projectFolder + "/" + daoMvnPath + "/");
        }

        return daoConfig;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    private CommentGeneratorConfiguration buildCommentConfig() {
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.setConfigurationType(DbRemarksCommentGenerator.class.getName());

        if (config.isComment()) {
            commentConfig.addProperty("columnRemarks", "true");
        }
        if (config.isAnnotation()) {
            commentConfig.addProperty("annotations", "true");
        }

        return commentConfig;
    }

    /**
     * ???????????????????????????????????????????????????jar?????????
     */
    private void addPluginConfiguration(Context context) {
        //?????????????????????
        PluginConfiguration serializablePlugin = new PluginConfiguration();
        serializablePlugin.addProperty("type", "org.mybatis.generator.plugins.SerializablePlugin");
        serializablePlugin.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(serializablePlugin);


        if (config.isNeedToStringHashcodeEquals()) {
            PluginConfiguration equalsHashCodePlugin = new PluginConfiguration();
            equalsHashCodePlugin.addProperty("type", "org.mybatis.generator.plugins.EqualsHashCodePlugin");
            equalsHashCodePlugin.setConfigurationType("org.mybatis.generator.plugins.EqualsHashCodePlugin");
            context.addPluginConfiguration(equalsHashCodePlugin);
            PluginConfiguration toStringPluginPlugin = new PluginConfiguration();
            toStringPluginPlugin.addProperty("type", "org.mybatis.generator.plugins.ToStringPlugin");
            toStringPluginPlugin.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
            context.addPluginConfiguration(toStringPluginPlugin);
        }

        // limit/offset??????
        if (config.isOffsetLimit()) {
            if (DbType.MySQL.equals(dbType) || DbType.PostgreSQL.equals(dbType)) {
                PluginConfiguration mySQLLimitPlugin = new PluginConfiguration();
                String pluginClassName = MySQLLimitPlugin.class.getName();
                mySQLLimitPlugin.addProperty("type", pluginClassName);
                mySQLLimitPlugin.setConfigurationType(pluginClassName);
                context.addPluginConfiguration(mySQLLimitPlugin);
            }
        }

        //for JSR310
        if (config.isJsr310Support()) {
            JavaTypeResolverConfiguration javaTypeResolverPlugin = new JavaTypeResolverConfiguration();
            javaTypeResolverPlugin.setConfigurationType(JavaTypeResolverJsr310Impl.class.getName());
            context.setJavaTypeResolverConfiguration(javaTypeResolverPlugin);
        }

        //forUpdate ??????
        if (config.isNeedForUpdate()) {
            if (DbType.MySQL.equals(dbType) || DbType.PostgreSQL.equals(dbType)) {
                PluginConfiguration mySQLForUpdatePlugin = new PluginConfiguration();
                String pluginClassName = MySQLForUpdatePlugin.class.getName();
                mySQLForUpdatePlugin.addProperty("type", pluginClassName);
                mySQLForUpdatePlugin.setConfigurationType(pluginClassName);
                context.addPluginConfiguration(mySQLForUpdatePlugin);
            }
        }

        //repository ??????
        if (config.isAnnotationDAO()) {
            if (DbType.MySQL.equals(dbType) || DbType.PostgreSQL.equals(dbType)) {
                PluginConfiguration repositoryPlugin = new PluginConfiguration();
                String pluginClassName = RepositoryPlugin.class.getName();
                repositoryPlugin.addProperty("type", pluginClassName);
                repositoryPlugin.setConfigurationType(pluginClassName);
                context.addPluginConfiguration(repositoryPlugin);
            }
        }

        if (config.isUseDAOExtendStyle()) {//13
            if (DbType.MySQL.equals(dbType) || DbType.PostgreSQL.equals(dbType)) {
                PluginConfiguration commonDAOInterfacePlugin = new PluginConfiguration();
                String pluginClassName = CommonDAOInterfacePlugin.class.getName();
                commonDAOInterfacePlugin.addProperty("type", pluginClassName);
                commonDAOInterfacePlugin.setConfigurationType(pluginClassName);
                context.addPluginConfiguration(commonDAOInterfacePlugin);
            }
        }
        // Lombok ??????
        if (config.isUseLombokPlugin()) {
            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            String pluginClassName = LombokPlugin.class.getName();
            pluginConfiguration.addProperty("type", pluginClassName);
            pluginConfiguration.setConfigurationType(pluginClassName);
            context.addPluginConfiguration(pluginConfiguration);
        }
        // Swagger ??????
        if (config.isUseSwaggerPlugin()) {
            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            String pluginClassName = GeneratorSwagger2DocPlugin.class.getName();
            pluginConfiguration.addProperty("type", pluginClassName);
            pluginConfiguration.setConfigurationType(pluginClassName);
            context.addPluginConfiguration(pluginConfiguration);
        }

    }

    /**
     * ??????xml???????????? ?????????????????????xml
     */
    private String getMappingXMLFilePath(Config config) {
        StringBuilder sb = new StringBuilder();
        String mappingXMLPackage = config.getXmlPackage();
        String mappingXMLTargetFolder = config.getProjectFolder();
        String xmlMvnPath = config.getXmlMvnPath();
        sb.append(mappingXMLTargetFolder + "/" + xmlMvnPath + "/");

        if (!StringUtil.isEmpty(mappingXMLPackage)) {
            sb.append(mappingXMLPackage.replace(".", "/")).append("/");
        }
        if (!StringUtil.isEmpty(config.getDaoName())) {
            sb.append(config.getDaoName()).append(".xml");
        } else {
            sb.append(config.getModelName()).append("Dao.xml");
        }

        return sb.toString();
    }
}
