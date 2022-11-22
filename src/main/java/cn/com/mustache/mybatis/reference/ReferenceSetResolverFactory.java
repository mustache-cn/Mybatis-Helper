package cn.com.mustache.mybatis.reference;

import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public final class ReferenceSetResolverFactory {

    private ReferenceSetResolverFactory() {
        throw new UnsupportedOperationException();
    }

    public static <F extends XmlAttributeValue> ContextReferenceSetResolver createPsiFieldResolver(@NotNull F target) {
        return new PsiFieldReferenceSetResolver(target);
    }

}
