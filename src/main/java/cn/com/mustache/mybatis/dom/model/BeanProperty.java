package cn.com.mustache.mybatis.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface BeanProperty extends DomElement {

    @NotNull
    @Attribute("name")
    GenericAttributeValue<String> getName();

    @NotNull
    @Attribute("value")
    GenericAttributeValue<String> getValue();
}
