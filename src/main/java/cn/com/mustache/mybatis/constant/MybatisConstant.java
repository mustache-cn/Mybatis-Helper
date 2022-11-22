package cn.com.mustache.mybatis.constant;

import com.intellij.psi.util.ReferenceSetBase;

/**
 * @author Steven Han
 */
public final class MybatisConstant {

    private MybatisConstant() {
        throw new UnsupportedOperationException();
    }

    public static final String DOT_SEPARATOR = String.valueOf(ReferenceSetBase.DOT_SEPARATOR);

    public static final double PRIORITY = 400.0;

}
