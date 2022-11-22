package cn.com.mustache.mybatis.dom.converter;

import java.util.Collection;

import cn.com.mustache.mybatis.dom.model.IdDomElement;
import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.dom.model.ResultMap;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class ResultMapConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper, ConvertContext context) {
        DomElement invocationElement = context.getInvocationElement();
        if (isContextElementOfResultMap(mapper, invocationElement)) {
            return doFilterResultMapItself(mapper, (ResultMap) invocationElement.getParent());
        } else {
            return mapper.getResultMaps();
        }
    }

    private boolean isContextElementOfResultMap(Mapper mapper, DomElement invocationElement) {
        return MapperUtil.isMapperWithSameNamespace(MapperUtil.getMapper(invocationElement), mapper)
                && invocationElement.getParent() instanceof ResultMap;
    }

    private Collection<? extends IdDomElement> doFilterResultMapItself(Mapper mapper, final ResultMap resultMap) {
        return Collections2.filter(mapper.getResultMaps(), new Predicate<ResultMap>() {
            @Override
            public boolean apply(ResultMap input) {
                return !MapperUtil.getId(input).equals(MapperUtil.getId(resultMap));
            }
        });
    }

}
