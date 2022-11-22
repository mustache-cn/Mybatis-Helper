package cn.com.mustache.mybatis.intention;

import cn.com.mustache.mybatis.annotation.Annotation;
import cn.com.mustache.mybatis.util.JavaUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class GenerateParamChooser extends JavaFileIntentionChooser {

    public static final JavaFileIntentionChooser INSTANCE = new GenerateParamChooser();

    @Override
    public boolean isAvailable(@NotNull PsiElement element) {
        PsiParameter parameter = PsiTreeUtil.getParentOfType(element, PsiParameter.class);
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        return (null != parameter && !JavaUtil.isAnnotationPresent(parameter, Annotation.PARAM)) ||
                (null != method && !JavaUtil.isAllParameterWithAnnotation(method, Annotation.PARAM));
    }
}
