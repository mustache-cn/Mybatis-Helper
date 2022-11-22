package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import cn.com.mustache.mybatis.dom.converter.AliasConverter;
import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface ParameterMap extends IdDomElement {

    @NotNull
    @Attribute("type")
    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getType();

    @SubTagList("parameter")
    List<Parameter> getParameters();

}
