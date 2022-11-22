package cn.com.mustache.mybatis.locator;

import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public abstract class LocateStrategy {

    public abstract boolean apply(@NotNull PsiClass clazz);

}
