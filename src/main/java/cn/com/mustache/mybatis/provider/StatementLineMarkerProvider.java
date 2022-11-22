package cn.com.mustache.mybatis.provider;

import java.util.Optional;
import javax.swing.*;

import cn.com.mustache.mybatis.constant.Icons;
import cn.com.mustache.mybatis.dom.model.Delete;
import cn.com.mustache.mybatis.dom.model.IdDomElement;
import cn.com.mustache.mybatis.dom.model.Insert;
import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.dom.model.Select;
import cn.com.mustache.mybatis.dom.model.Update;
import cn.com.mustache.mybatis.util.JavaUtil;
import cn.com.mustache.mybatis.util.MapperUtil;
import cn.com.mustache.mybatis.util.StringUtil;
import com.google.common.collect.ImmutableSet;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class StatementLineMarkerProvider extends SimpleLineMarkerProvider<XmlToken, PsiElement> {
    private static final String MAPPER_CLASS = Mapper.class.getSimpleName().toLowerCase();

    private static final ImmutableSet<String> TARGET_TYPES = ImmutableSet.of(
            Select.class.getSimpleName().toLowerCase(),
            Update.class.getSimpleName().toLowerCase(),
            Insert.class.getSimpleName().toLowerCase(),
            Delete.class.getSimpleName().toLowerCase()
    );

    @Override
    public boolean isTheElement(@NotNull PsiElement element) {
        return element instanceof XmlToken
                && MapperUtil.isElementWithinMybatisFile(element)
                && isTargetType((XmlToken) element);
    }

    @NotNull
    @Override
    public Optional<? extends PsiElement> apply(@NotNull XmlToken from) {
        DomElement domElement = DomUtil.getDomElement(from);
        if (domElement == null) {
            return Optional.empty();
        }
        if (domElement instanceof IdDomElement) {
            return JavaUtil.findMethod(from.getProject(), (IdDomElement) domElement);
        }
        XmlTag xmlTag = domElement.getXmlTag();
        if (xmlTag == null) {
            return Optional.empty();
        }
        String clazzName = xmlTag.getAttributeValue("namespace");
        if (StringUtil.isEmpty(clazzName)) {
            return Optional.empty();
        }
        return JavaUtil.findClazz(from.getProject(), clazzName);
    }

    private boolean isTargetType(XmlToken token) {
        String tokenText = token.getText();
        if (MAPPER_CLASS.equals(tokenText) && token.getNextSibling() instanceof PsiWhiteSpace) {
            return true;
        }
        if (TARGET_TYPES.contains(tokenText) && token.getParent() instanceof XmlTag) {
            PsiElement nextSibling = token.getNextSibling();
            return nextSibling instanceof PsiWhiteSpace;
        }
        return false;
    }

    @NotNull
    @Override
    public Navigatable getNavigable(@NotNull XmlToken from, @NotNull PsiElement target) {
        return (Navigatable) target.getNavigationElement();
    }

    @NotNull
    @Override
    public String getTooltip(@NotNull XmlToken from, @NotNull PsiElement target) {
        String text = null;
        if (target instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) target;
            PsiClass psiClass = psiMethod.getContainingClass();
            if (psiClass != null) {
                text = psiClass.getName() + "#" + psiMethod.getName();
            }

        }
        if (text == null && target instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) target;
            text = psiClass.getQualifiedName();
        }
        if (text == null) {
            text = target.getContainingFile().getText();
        }
        return "Data access object found - " + text;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return Icons.STATEMENT_LINE_MARKER_ICON;
    }

}
