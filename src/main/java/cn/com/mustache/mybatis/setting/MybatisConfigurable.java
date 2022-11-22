package cn.com.mustache.mybatis.setting;

import static cn.com.mustache.mybatis.generate.StatementGenerators.DELETE_GENERATOR;
import static cn.com.mustache.mybatis.generate.StatementGenerators.INSERT_GENERATOR;
import static cn.com.mustache.mybatis.generate.StatementGenerators.SELECT_GENERATOR;
import static cn.com.mustache.mybatis.generate.StatementGenerators.UPDATE_GENERATOR;

import javax.swing.*;

import cn.com.mustache.mybatis.generate.GenerateModel;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class MybatisConfigurable implements SearchableConfigurable {

    private final MybatisSetting mybatisSetting;

    private MybatisSettingForm mybatisSettingForm;

    private final String separator = ";";

    private final Splitter splitter = Splitter.on(separator).omitEmptyStrings().trimResults();

    private final Joiner joiner = Joiner.on(separator);

    public MybatisConfigurable() {
        mybatisSetting = MybatisSetting.getInstance();
    }

    @Override
    public @NotNull String getId() {
        return "Mybatis";
    }

    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Mybatis Helper";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return getId();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == mybatisSettingForm) {
            this.mybatisSettingForm = new MybatisSettingForm();
        }
        return mybatisSettingForm.mainPanel;
    }

    @Override
    public boolean isModified() {
        return mybatisSetting.getStatementGenerateModel().getIdentifier() != mybatisSettingForm.modelComboBox.getSelectedIndex()
                || !joiner.join(INSERT_GENERATOR.getPatterns()).equals(mybatisSettingForm.insertPatternTextField.getText())
                || !joiner.join(DELETE_GENERATOR.getPatterns()).equals(mybatisSettingForm.deletePatternTextField.getText())
                || !joiner.join(UPDATE_GENERATOR.getPatterns()).equals(mybatisSettingForm.updatePatternTextField.getText())
                || !joiner.join(SELECT_GENERATOR.getPatterns()).equals(mybatisSettingForm.selectPatternTextField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        mybatisSetting.setStatementGenerateModel(GenerateModel.getInstance(mybatisSettingForm.modelComboBox.getSelectedIndex()));
        INSERT_GENERATOR.setPatterns(Sets.newHashSet(splitter.split(mybatisSettingForm.insertPatternTextField.getText())));
        DELETE_GENERATOR.setPatterns(Sets.newHashSet(splitter.split(mybatisSettingForm.deletePatternTextField.getText())));
        UPDATE_GENERATOR.setPatterns(Sets.newHashSet(splitter.split(mybatisSettingForm.updatePatternTextField.getText())));
        SELECT_GENERATOR.setPatterns(Sets.newHashSet(splitter.split(mybatisSettingForm.selectPatternTextField.getText())));
    }

    @Override
    public void reset() {
        mybatisSettingForm.modelComboBox.setSelectedIndex(mybatisSetting.getStatementGenerateModel().getIdentifier());
        mybatisSettingForm.insertPatternTextField.setText(joiner.join(INSERT_GENERATOR.getPatterns()));
        mybatisSettingForm.deletePatternTextField.setText(joiner.join(DELETE_GENERATOR.getPatterns()));
        mybatisSettingForm.updatePatternTextField.setText(joiner.join(UPDATE_GENERATOR.getPatterns()));
        mybatisSettingForm.selectPatternTextField.setText(joiner.join(SELECT_GENERATOR.getPatterns()));
    }

    @Override
    public void disposeUIResources() {
        mybatisSettingForm.mainPanel = null;
    }

}
