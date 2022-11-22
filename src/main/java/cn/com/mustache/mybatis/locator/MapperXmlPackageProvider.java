package cn.com.mustache.mybatis.locator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.google.common.collect.Sets;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class MapperXmlPackageProvider extends PackageProvider {

    @NotNull
    @Override
    public Set<PsiPackage> getPackages(@NotNull Project project) {
        HashSet<PsiPackage> res = Sets.newHashSet();
        Collection<Mapper> mappers = MapperUtil.findMappers(project);
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        for (Mapper mapper : mappers) {
            String namespace = MapperUtil.getNamespace(mapper);
            PsiClass clazz = javaPsiFacade.findClass(namespace, GlobalSearchScope.allScope(project));
            if (null != clazz) {
                PsiFile file = clazz.getContainingFile();
                if (file instanceof PsiJavaFile) {
                    String packageName = ((PsiJavaFile) file).getPackageName();
                    PsiPackage pkg = javaPsiFacade.findPackage(packageName);
                    if (null != pkg) {
                        res.add(pkg);
                    }
                }
            }
        }
        return res;
    }

}
