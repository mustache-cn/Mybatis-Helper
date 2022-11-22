package cn.com.mustache.mybatis.setting;


import java.util.Map;

import cn.com.mustache.mybatis.model.Config;
import cn.com.mustache.mybatis.model.User;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * 配置持久化
 */
@State(name = "PersistentConfig", storages = {@Storage("mybatis-helper-generator-config.xml")})
public class PersistentConfig implements PersistentStateComponent<PersistentConfig> {

    private Map<String, Config> initConfig;
    private Map<String, User> users;
    private Map<String, Config> historyConfigList;

    @Nullable
    public static PersistentConfig getInstance(Project project) {
        return project.getService(PersistentConfig.class);
    }

    @Nullable
    public PersistentConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PersistentConfig persistentConfig) {
        XmlSerializerUtil.copyBean(persistentConfig, this);
    }


    public Map<String, Config> getInitConfig() {
        return initConfig;
    }

    public void setInitConfig(Map<String, Config> initConfig) {
        this.initConfig = initConfig;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<String, Config> getHistoryConfigList() {
        return historyConfigList;
    }

    public void setHistoryConfigList(Map<String, Config> historyConfigList) {
        this.historyConfigList = historyConfigList;
    }
}
