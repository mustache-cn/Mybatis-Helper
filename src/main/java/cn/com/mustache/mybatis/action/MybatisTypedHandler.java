package cn.com.mustache.mybatis.action;

import cn.com.mustache.mybatis.util.DomUtil;
import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class MybatisTypedHandler extends TypedHandlerDelegate {

    @Override
    public Result checkAutoPopup(char charTyped, final Project project, final Editor editor, PsiFile file) {
        if (charTyped == '.' && DomUtil.isMybatisFile(file)) {
            autoPopupParameter(project, editor);
            return Result.STOP;
        }
        return super.checkAutoPopup(charTyped, project, editor, file);
    }

    @Override
    public Result charTyped(char c, final Project project, @NotNull final Editor editor, @NotNull PsiFile file) {
        int index = editor.getCaretModel().getOffset() - 2;
        PsiFile topLevelFile = InjectedLanguageManager.getInstance(project).getTopLevelFile(file);
        boolean parameterCase = c == '{' &&
                index >= 0 &&
                editor.getDocument().getText().charAt(index) == '#' &&
//                file instanceof SqlFile &&
                DomUtil.isMybatisFile(topLevelFile);
        if (parameterCase) {
            autoPopupParameter(project, editor);
            return Result.STOP;
        }
        return super.charTyped(c, project, editor, file);
    }

    private static void autoPopupParameter(final Project project, final Editor editor) {
        AutoPopupController.getInstance(project).autoPopupMemberLookup(editor, psiFile -> true);
    }


}