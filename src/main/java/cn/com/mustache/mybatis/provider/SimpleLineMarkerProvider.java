package cn.com.mustache.mybatis.provider;

import java.util.Optional;
import java.util.function.Supplier;
import javax.swing.*;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public abstract class SimpleLineMarkerProvider<F extends PsiElement, T extends PsiElement> extends MarkerProviderAdaptor {
    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public LineMarkerInfo<F> getLineMarkerInfo(@NotNull PsiElement element) {
        if (!isTheElement(element)) return null;

        Optional<? extends T> processResult = apply((F) element);
        return processResult.map(t -> new LineMarkerInfo<F>(
                (F) element,
                element.getTextRange(),
                getIcon(),
                getTooltipProvider(t),
                getNavigationHandler(t),
                GutterIconRenderer.Alignment.CENTER,
                getAccessibleNameProvider()
        )).orElse(null);
    }

    private Function<F, String> getTooltipProvider(final T target) {
        return from -> getTooltip(from, target);
    }

    private GutterIconNavigationHandler<F> getNavigationHandler(final T target) {
        return (e, from) -> getNavigable(from, target).navigate(true);
    }

    private Supplier<String> getAccessibleNameProvider() {
        return () -> "marker";
    }

    public abstract boolean isTheElement(@NotNull PsiElement element);

    @NotNull
    public abstract Optional<? extends T> apply(@NotNull F from);

    @NotNull
    public abstract Navigatable getNavigable(@NotNull F from, @NotNull T target);

    @NotNull
    public abstract String getTooltip(@NotNull F from, @NotNull T target);

    @NotNull
    public abstract Icon getIcon();
}
