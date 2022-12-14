package cn.com.mustache.mybatis.inspection;

import java.util.Objects;

import cn.com.mustache.mybatis.dom.model.Select;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class ResultTypeQuickFix extends GenericQuickFix {

    private final Select select;
    private final SmartPsiElementPointer<PsiClass> target;

    public ResultTypeQuickFix(@NotNull Select select, @NotNull PsiClass target) {
        this.select = select;
        PsiFile containingFile = target.getContainingFile();
        Project project = containingFile == null ? target.getProject() : containingFile.getProject();
        this.target = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(target);
    }

    @NotNull
    @Override
    public String getName() {
        return "Correct resultType";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        GenericAttributeValue<PsiClass> resultType = select.getResultType();
        resultType.setValue(target.getElement());
    }

    @NotNull
    public PsiClass getTarget() {
        return Objects.requireNonNull(target.getElement());
    }

    @NotNull
    public Select getSelect() {
        return select;
    }
}
