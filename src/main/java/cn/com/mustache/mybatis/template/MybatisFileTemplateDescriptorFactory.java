package cn.com.mustache.mybatis.template;

import cn.com.mustache.mybatis.constant.Icons;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

/**
 * @author Steven Han
 */
public class MybatisFileTemplateDescriptorFactory implements FileTemplateGroupDescriptorFactory {

    public static final String MYBATIS_MAPPER_XML_TEMPLATE = "Mybatis Mapper.xml";

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("Mybatis", Icons.MYBATIS_LOGO);
        group.addTemplate(new FileTemplateDescriptor(MYBATIS_MAPPER_XML_TEMPLATE, Icons.MYBATIS_LOGO));
        return group;
    }

}
