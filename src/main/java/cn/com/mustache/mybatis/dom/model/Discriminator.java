package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;

/**
 * @author Steven Han
 */
public interface Discriminator extends DomElement {

    @Required
    @SubTagList("case")
    List<Case> getCases();

}
