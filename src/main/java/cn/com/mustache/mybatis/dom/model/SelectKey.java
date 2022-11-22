package cn.com.mustache.mybatis.dom.model;

import cn.com.mustache.mybatis.dom.converter.AliasConverter;
import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface SelectKey extends GroupOne {
    @NotNull
    @Attribute("resultType")
    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getResultType();
}
