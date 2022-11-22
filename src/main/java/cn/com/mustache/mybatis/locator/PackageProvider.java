package cn.com.mustache.mybatis.locator;

import java.util.Set;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public abstract class PackageProvider {

    @NotNull
    public abstract Set<PsiPackage> getPackages(@NotNull Project project);

}