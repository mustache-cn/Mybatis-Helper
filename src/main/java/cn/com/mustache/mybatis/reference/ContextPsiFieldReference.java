package cn.com.mustache.mybatis.reference;

import java.util.Optional;

import cn.com.mustache.mybatis.constant.MybatisConstant;
import cn.com.mustache.mybatis.dom.MapperBacktrackingUtils;
import cn.com.mustache.mybatis.service.JavaService;
import cn.com.mustache.mybatis.util.JavaUtil;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class ContextPsiFieldReference extends PsiReferenceBase<XmlAttributeValue> {

    protected ContextReferenceSetResolver resolver;

    protected int index;

    public ContextPsiFieldReference(XmlAttributeValue element, TextRange range, int index) {
        super(element, range, false);
        this.index = index;
        resolver = ReferenceSetResolverFactory.createPsiFieldResolver(element);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public PsiElement resolve() {
        Optional<PsiElement> resolved = resolver.resolve(index);
        return resolved.orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Optional<PsiClass> clazz = getTargetClazz();
        return clazz.isPresent() ? JavaUtil.findSettablePsiFields(clazz.get()) : PsiReference.EMPTY_ARRAY;
    }

    @SuppressWarnings("unchecked")
    private Optional<PsiClass> getTargetClazz() {
        if (getElement().getValue().contains(MybatisConstant.DOT_SEPARATOR)) {
            int ind = 0 == index ? 0 : index - 1;
            Optional<PsiElement> resolved = resolver.resolve(ind);
            if (resolved.isPresent()) {
                return JavaService.getInstance(myElement.getProject()).getReferenceClazzOfPsiField(resolved.get());
            }
        } else {
            return MapperBacktrackingUtils.getPropertyClazz(myElement);
        }
        return Optional.empty();
    }

    public ContextReferenceSetResolver getResolver() {
        return resolver;
    }

    public void setResolver(ContextReferenceSetResolver resolver) {
        this.resolver = resolver;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
