package cn.com.mustache.mybatis.dom.description;

import cn.com.mustache.mybatis.dom.model.Configuration;
import cn.com.mustache.mybatis.util.DomUtil;
import com.intellij.openapi.module.Module;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class ConfigurationDescription extends DomFileDescription<Configuration> {

    public ConfigurationDescription() {
        super(Configuration.class, "configuration");
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        return DomUtil.isMybatisConfigurationFile(file);
    }

}
