package cn.com.mustache.mybatis.generate;

import cn.com.mustache.mybatis.dom.model.GroupTwo;
import cn.com.mustache.mybatis.dom.model.Mapper;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class DeleteGenerator extends StatementGenerator {

    public DeleteGenerator(@NotNull String... patterns) {
        super(patterns);
    }

    @NotNull
    @Override
    protected GroupTwo getTarget(@NotNull Mapper mapper, @NotNull PsiMethod method) {
        return mapper.addDelete();
    }

    @NotNull
    @Override
    public String getId() {
        return "DeleteGenerator";
    }

    @NotNull
    @Override
    public String getDisplayText() {
        return "Delete Statement";
    }

}
