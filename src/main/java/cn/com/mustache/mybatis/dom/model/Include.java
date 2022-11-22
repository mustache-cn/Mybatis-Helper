package cn.com.mustache.mybatis.dom.model;

import cn.com.mustache.mybatis.dom.converter.SqlConverter;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * @author Steven Han
 */
public interface Include extends DomElement {

    @Attribute("refid")
    @Convert(SqlConverter.class)
    GenericAttributeValue<XmlTag> getRefId();

}
