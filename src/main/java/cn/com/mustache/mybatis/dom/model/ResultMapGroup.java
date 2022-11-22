package cn.com.mustache.mybatis.dom.model;

import cn.com.mustache.mybatis.dom.converter.ResultMapConverter;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface ResultMapGroup extends DomElement {

    @NotNull
    @Attribute("resultMap")
    @Convert(ResultMapConverter.class)
    GenericAttributeValue<XmlTag> getResultMap();
}
