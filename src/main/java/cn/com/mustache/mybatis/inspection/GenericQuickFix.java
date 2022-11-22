package cn.com.mustache.mybatis.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public abstract class GenericQuickFix implements LocalQuickFix {

    @NotNull
    @Override
    public String getFamilyName() {
        return getName();
    }

}
