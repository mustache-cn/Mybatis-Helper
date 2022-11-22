package cn.com.mustache.mybatis.intention;

import cn.com.mustache.mybatis.generate.StatementGenerators;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class GenerateStatementIntention extends GenericIntention {

    public GenerateStatementIntention() {
        super(GenerateStatementChooser.INSTANCE);
    }

    @NotNull
    @Override
    public String getText() {
        return "[Mybatis] MybatisGenerator new statement";
    }

    @Override
    public void invoke(@NotNull final Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
        StatementGenerators.applyGenerate(PsiTreeUtil.getParentOfType(element, PsiMethod.class));
    }

}
