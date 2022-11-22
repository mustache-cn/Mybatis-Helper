package cn.com.mustache.mybatis.intention;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public interface IntentionChooser {

    boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file);

}
