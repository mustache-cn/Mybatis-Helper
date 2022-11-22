package cn.com.mustache.mybatis.locator;

import cn.com.mustache.mybatis.util.JavaUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class MapperLocator {

    public static LocateStrategy dfltLocateStrategy = new PackageLocateStrategy();

    public static MapperLocator getInstance(@NotNull Project project) {
        return project.getService(MapperLocator.class);
    }

    public boolean process(@Nullable PsiMethod method) {
        return null != method && process(method.getContainingClass());
    }

    public boolean process(@Nullable PsiClass clazz) {
        return null != clazz && JavaUtil.isElementWithinInterface(clazz) && dfltLocateStrategy.apply(clazz);
    }

}
