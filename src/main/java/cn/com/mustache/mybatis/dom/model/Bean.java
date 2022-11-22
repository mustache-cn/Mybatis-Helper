package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface Bean extends DomElement {

    @NotNull
    @SubTagList("property")
    List<BeanProperty> getBeanProperties();

}
