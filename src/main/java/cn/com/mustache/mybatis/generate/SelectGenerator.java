package cn.com.mustache.mybatis.generate;

import java.util.Optional;

import cn.com.mustache.mybatis.dom.model.GroupTwo;
import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.dom.model.Select;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class SelectGenerator extends StatementGenerator {

    public SelectGenerator(@NotNull String... patterns) {
        super(patterns);
    }

    @NotNull
    @Override
    protected GroupTwo getTarget(@NotNull Mapper mapper, @NotNull PsiMethod method) {
        Select select = mapper.addSelect();
        setupResultType(method, select);
        return select;
    }

    private void setupResultType(PsiMethod method, Select select) {
        Optional<PsiClass> clazz = StatementGenerator.getSelectResultType(method);
        if (clazz.isPresent()) {
            select.getResultType().setValue(clazz.get());
        }
    }

    @NotNull
    @Override
    public String getId() {
        return "SelectGenerator";
    }

    @NotNull
    @Override
    public String getDisplayText() {
        return "Select Statement";
    }
}
