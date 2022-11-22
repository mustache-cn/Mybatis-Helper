package cn.com.mustache.mybatis.dom.model;

import cn.com.mustache.mybatis.dom.converter.AliasConverter;
import cn.com.mustache.mybatis.dom.converter.ResultMapConverter;
import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface ResultMap extends GroupFour, IdDomElement {

    @NotNull
    @Attribute("extends")
    @Convert(ResultMapConverter.class)
    GenericAttributeValue<XmlAttributeValue> getExtends();

    @NotNull
    @Attribute("type")
    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getType();

}