package cn.com.mustache.mybatis.dom.model;

import cn.com.mustache.mybatis.dom.converter.PropertyConverter;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * @author Steven Han
 */
public interface PropertyGroup extends DomElement {

    @Attribute("property")
    @Convert(PropertyConverter.class)
    GenericAttributeValue<XmlAttributeValue> getProperty();
}
