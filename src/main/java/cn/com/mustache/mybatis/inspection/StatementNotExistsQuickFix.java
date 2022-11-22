package cn.com.mustache.mybatis.inspection;

import cn.com.mustache.mybatis.generate.StatementGenerators;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class StatementNotExistsQuickFix extends GenericQuickFix {

    @SafeFieldForPreview
    private final SmartPsiElementPointer<PsiMethod> method;

    public StatementNotExistsQuickFix(@NotNull PsiMethod method) {
        PsiFile containingFile = method.getContainingFile();
        Project project = containingFile == null ? method.getProject() : containingFile.getProject();
        this.method = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(method, containingFile);
    }

    @NotNull
    @Override
    public String getName() {
        return "MybatisGenerator statement";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        StatementGenerators.applyGenerate(method.getElement());
    }

}
