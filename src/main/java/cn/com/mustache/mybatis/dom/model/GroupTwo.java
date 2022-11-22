package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import cn.com.mustache.mybatis.dom.converter.AliasConverter;
import cn.com.mustache.mybatis.dom.converter.DaoMethodConverter;
import cn.com.mustache.mybatis.dom.converter.ParameterMapConverter;
import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface GroupTwo extends GroupOne, IdDomElement {

    @SubTagList("bind")
    List<Bind> getBinds();

    @NotNull
    @Attribute("parameterMap")
    @Convert(ParameterMapConverter.class)
    GenericAttributeValue<XmlTag> getParameterMap();

    @Attribute("id")
    @Convert(DaoMethodConverter.class)
    GenericAttributeValue<String> getId();

    @NotNull
    @Attribute("parameterType")
    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getParameterType();
}
