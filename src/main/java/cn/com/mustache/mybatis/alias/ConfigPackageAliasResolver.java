package cn.com.mustache.mybatis.alias;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.mustache.mybatis.util.MapperUtil;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class ConfigPackageAliasResolver extends PackageAliasResolver {

    public ConfigPackageAliasResolver(Project project) {
        super(project);
    }

    @NotNull
    @Override
    public Collection<String> getPackages(@Nullable PsiElement element) {
        final ArrayList<String> result = Lists.newArrayList();
        MapperUtil.processConfiguredPackage(project, pkg -> {
            result.add(pkg.getName().getStringValue());
            return true;
        });
        return result;
    }

}
