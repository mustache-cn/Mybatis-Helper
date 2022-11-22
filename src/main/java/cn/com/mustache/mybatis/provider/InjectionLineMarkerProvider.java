package cn.com.mustache.mybatis.provider;

import java.util.Collection;
import java.util.Optional;

import cn.com.mustache.mybatis.annotation.Annotation;
import cn.com.mustache.mybatis.constant.Icons;
import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.util.JavaUtil;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UastUtils;

/**
 * @author Steven Han
 */
public class InjectionLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        UElement uElement = UastUtils.getUParentForIdentifier(element);
        if (uElement == null) {
            return;
        }

        PsiElement identifier = uElement.getJavaPsi();
        if (identifier == null) {
            return;
        }
        if (!(identifier instanceof PsiField)) return;
        PsiField field = (PsiField) identifier;
        if (!isTargetField(field)) return;

        PsiType type = field.getType();
        if (!(type instanceof PsiClassReferenceType)) return;

        Optional<PsiClass> clazz = JavaUtil.findClazz(identifier.getProject(), type.getCanonicalText());
        if (!clazz.isPresent()) return;

        PsiClass psiClass = clazz.get();
        Optional<Mapper> mapper = MapperUtil.findFirstMapper(identifier.getProject(), psiClass);
        if (!mapper.isPresent()) return;

        NavigationGutterIconBuilder<PsiElement> builder =
                NavigationGutterIconBuilder.create(Icons.SPRING_INJECTION_ICON)
                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                        .setTarget(psiClass)
                        .setTooltipTitle("Data access object found - " + psiClass.getQualifiedName());
        result.add(builder.createLineMarkerInfo(field.getNameIdentifier()));
    }

    private boolean isTargetField(PsiField field) {
        if (JavaUtil.isAnnotationPresent(field, Annotation.AUTOWIRED)) {
            return true;
        }
        Optional<PsiAnnotation> resourceAnno = JavaUtil.getPsiAnnotation(field, Annotation.RESOURCE);
        if (resourceAnno.isPresent()) {
            PsiAnnotationMemberValue nameValue = resourceAnno.get().findAttributeValue("name");
            String name = nameValue.getText().replaceAll("\"", "");
            return StringUtils.isBlank(name) || name.equals(field.getName());
        }
        return false;
    }

}
