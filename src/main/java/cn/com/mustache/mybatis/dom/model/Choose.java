package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTag;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface Choose extends DomElement {

    @NotNull
    @Required
    @SubTagList("when")
    List<When> getWhens();

    @SubTag("otherwise")
    Otherwise getOtherwise();

}
