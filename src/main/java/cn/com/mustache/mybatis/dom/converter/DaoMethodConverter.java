package cn.com.mustache.mybatis.dom.converter;

import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.util.JavaUtil;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.intellij.psi.PsiMethod;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class DaoMethodConverter extends ConverterAdaptor<PsiMethod> {

    @Nullable
    @Override
    public PsiMethod fromString(@Nullable @NonNls String id, ConvertContext context) {
        Mapper mapper = MapperUtil.getMapper(context.getInvocationElement());
        return JavaUtil.findMethod(context.getProject(), MapperUtil.getNamespace(mapper), id).orElse(null);
    }

}