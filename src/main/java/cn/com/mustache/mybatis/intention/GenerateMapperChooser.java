package cn.com.mustache.mybatis.intention;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class GenerateMapperChooser extends JavaFileIntentionChooser {

    public static final JavaFileIntentionChooser INSTANCE = new GenerateMapperChooser();

    @Override
    public boolean isAvailable(@NotNull PsiElement element) {
        if (isPositionOfInterfaceDeclaration(element)) {
            PsiClass clazz = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if (null != clazz) {
                return !isTargetPresentInXml(clazz);
            }
        }
        return false;
    }

}
