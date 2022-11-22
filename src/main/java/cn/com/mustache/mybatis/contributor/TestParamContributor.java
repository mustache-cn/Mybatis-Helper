package cn.com.mustache.mybatis.contributor;

import java.util.Optional;

import cn.com.mustache.mybatis.annotation.Annotation;
import cn.com.mustache.mybatis.constant.Icons;
import cn.com.mustache.mybatis.constant.MybatisConstant;
import cn.com.mustache.mybatis.dom.model.IdDomElement;
import cn.com.mustache.mybatis.util.JavaUtil;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Steven Han
 */
public class TestParamContributor extends CompletionContributor {
    private static final Logger logger = LoggerFactory.getLogger(TestParamContributor.class);

    public TestParamContributor() {
        extend(CompletionType.BASIC,
                XmlPatterns.psiElement()
                        .inside(XmlPatterns.xmlAttributeValue()
                                .inside(XmlPatterns.xmlAttribute().withName("test"))),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(
                            @NotNull final CompletionParameters parameters,
                            final ProcessingContext context,
                            @NotNull final CompletionResultSet result) {
                        final PsiElement position = parameters.getPosition();
                        addElementForPsiParameter(
                                position.getProject(),
                                result,
                                MapperUtil.findParentIdDomElement(position).orElse(null));
                    }
                });
    }

    static void addElementForPsiParameter(
            @NotNull final Project project,
            @NotNull final CompletionResultSet result,
            @Nullable final IdDomElement element) {
        if (element == null) {
            return;
        }

        final PsiMethod method = JavaUtil.findMethod(project, element).orElse(null);

        if (method == null) {
            logger.info("psiMethod null");
            return;
        }

        final PsiParameter[] parameters = method.getParameterList().getParameters();

        // For a single parameter MyBatis uses its name, while for a multitude they're
        // named as param1, param2, etc. I'll check if the @Param annotation [value] is present
        // and eventually I'll use its text.
        if (parameters.length == 1) {
            final PsiParameter parameter = parameters[0];
            result.addElement(buildLookupElementWithIcon(
                    parameter.getName(),
                    parameter.getType().getPresentableText()));
        } else {
            for (int i = 0; i < parameters.length; i++) {
                final PsiParameter parameter = parameters[i];
                final Optional<String> value = JavaUtil.getAnnotationValueText(parameter, Annotation.PARAM);
                result.addElement(buildLookupElementWithIcon(
                        value.isPresent() ? value.get() : "param" + (i + 1),
                        parameter.getType().getPresentableText()));
            }
        }
    }

    private static LookupElement buildLookupElementWithIcon(
            final String parameterName,
            final String parameterType) {
        return PrioritizedLookupElement.withPriority(
                LookupElementBuilder.create(parameterName)
                        .withTypeText(parameterType)
                        .withIcon(Icons.PARAM_COMPLETION_ICON),
                MybatisConstant.PRIORITY);
    }
}
