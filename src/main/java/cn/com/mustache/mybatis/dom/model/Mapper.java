package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;
import com.intellij.util.xml.SubTagsList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface Mapper extends DomElement {

    @NotNull
    @SubTagsList({"insert", "update", "delete", "select"})
    List<IdDomElement> getDaoElements();

    @Required
    @NameValue
    @NotNull
    @Attribute("namespace")
    GenericAttributeValue<String> getNamespace();

    @NotNull
    @SubTagList("resultMap")
    List<ResultMap> getResultMaps();

    @NotNull
    @SubTagList("parameterMap")
    List<ParameterMap> getParameterMaps();

    @NotNull
    @SubTagList("sql")
    List<Sql> getSqls();

    @NotNull
    @SubTagList("insert")
    List<Insert> getInserts();

    @NotNull
    @SubTagList("update")
    List<Update> getUpdates();

    @NotNull
    @SubTagList("delete")
    List<Delete> getDeletes();

    @NotNull
    @SubTagList("select")
    List<Select> getSelects();

    @SubTagList("select")
    Select addSelect();

    @SubTagList("update")
    Update addUpdate();

    @SubTagList("insert")
    Insert addInsert();

    @SubTagList("delete")
    Delete addDelete();
}
