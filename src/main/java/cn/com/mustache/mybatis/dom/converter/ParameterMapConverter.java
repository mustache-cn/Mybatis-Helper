package cn.com.mustache.mybatis.dom.converter;

import java.util.Collection;

import cn.com.mustache.mybatis.dom.model.IdDomElement;
import cn.com.mustache.mybatis.dom.model.Mapper;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class ParameterMapConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper,
                                                             ConvertContext context) {
        return mapper.getParameterMaps();
    }

}
