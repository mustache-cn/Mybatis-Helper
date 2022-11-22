package cn.com.mustache.mybatis.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;

/**
 * @author Steven Han
 */
public interface IdDomElement extends DomElement {

    @Required
    @NameValue
    @Attribute("id")
    GenericAttributeValue<String> getId();

    void setValue(String content);
}
