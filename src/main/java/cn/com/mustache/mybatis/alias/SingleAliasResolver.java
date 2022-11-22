package cn.com.mustache.mybatis.alias;

import java.util.Set;

import cn.com.mustache.mybatis.dom.model.TypeAlias;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.google.common.collect.Sets;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class SingleAliasResolver extends AliasResolver {

    public SingleAliasResolver(Project project) {
        super(project);
    }

    @NotNull
    @Override
    public Set<AliasDesc> getClassAliasDescriptions(@Nullable PsiElement element) {
        final Set<AliasDesc> result = Sets.newHashSet();
        MapperUtil.processConfiguredTypeAliases(project, new Processor<TypeAlias>() {
            @Override
            public boolean process(TypeAlias typeAlias) {
                addAliasDesc(result, typeAlias.getType().getValue(), typeAlias.getAlias().getStringValue());
                return true;
            }
        });
        return result;
    }

}
