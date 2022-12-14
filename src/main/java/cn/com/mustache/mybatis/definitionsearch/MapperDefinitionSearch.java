package cn.com.mustache.mybatis.definitionsearch;

import cn.com.mustache.mybatis.service.JavaService;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.searches.DefinitionsScopedSearch;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.Processor;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Han
 */
public class MapperDefinitionSearch extends QueryExecutorBase<XmlElement, DefinitionsScopedSearch.SearchParameters> {

    public MapperDefinitionSearch() {
        super(true);
    }

    @Override
    public void processQuery(@NotNull DefinitionsScopedSearch.SearchParameters queryParameters,
                             @NotNull Processor<? super XmlElement> consumer) {
        final PsiElement element = queryParameters.getElement();

        Processor<DomElement> processor = domElement -> consumer.process(domElement.getXmlElement());

        JavaService.getInstance(queryParameters.getProject()).process(element, processor);
    }
}
