package cn.com.mustache.mybatis.alias;

import java.util.Set;

import cn.com.mustache.mybatis.util.JavaUtil;
import com.google.common.collect.ImmutableSet;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class InnerAliasResolver extends AliasResolver {

    private final Set<AliasDesc> innerAliasDescs = ImmutableSet.of(
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.String").get(), "string"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Byte").get(), "byte"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Long").get(), "long"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Short").get(), "short"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Integer").get(), "int"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Integer").get(), "integer"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Double").get(), "double"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Float").get(), "float"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Boolean").get(), "boolean"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.Date").get(), "date"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.math.BigDecimal").get(), "decimal"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.lang.Object").get(), "object"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.Map").get(), "map"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.HashMap").get(), "hashmap"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.List").get(), "list"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.ArrayList").get(), "arraylist"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.Collection").get(), "collection"),
            AliasDesc.create(JavaUtil.findClazz(project, "java.util.Iterator").get(), "iterator")
    );

    public InnerAliasResolver(Project project) {
        super(project);
    }

    @NotNull
    @Override
    public Set<AliasDesc> getClassAliasDescriptions(@Nullable PsiElement element) {
        return innerAliasDescs;
    }

}
