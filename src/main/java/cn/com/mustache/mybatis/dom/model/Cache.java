package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

/**
 * @author Steven Han
 */
public interface Cache extends DomElement {

    @SubTagList("property")
    List<Property> getProperties();

}
