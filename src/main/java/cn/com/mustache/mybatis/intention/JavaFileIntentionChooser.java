package cn.com.mustache.mybatis.intention;

import cn.com.mustache.mybatis.service.JavaService;
import cn.com.mustache.mybatis.util.JavaUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public abstract class JavaFileIntentionChooser implements IntentionChooser {

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        if (!(file instanceof PsiJavaFile))
            return false;
        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
        return null != element && JavaUtil.isElementWithinInterface(element) && isAvailable(element);
    }

    public abstract boolean isAvailable(@NotNull PsiElement element);

    public boolean isPositionOfParameterDeclaration(@NotNull PsiElement element) {
        return element.getParent() instanceof PsiParameter;
    }

    public boolean isPositionOfMethodDeclaration(@NotNull PsiElement element) {
        return element.getParent() instanceof PsiMethod;
    }

    public boolean isPositionOfInterfaceDeclaration(@NotNull PsiElement element) {
        return element.getParent() instanceof PsiClass;
    }

    public boolean isTargetPresentInXml(@NotNull PsiElement element) {
        return JavaService.getInstance(element.getProject()).findWithFindFirstProcessor(element).isPresent();
    }

}
