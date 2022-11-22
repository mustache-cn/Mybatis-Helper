package cn.com.mustache.mybatis.service;

import java.util.Optional;

import cn.com.mustache.mybatis.dom.model.IdDomElement;
import cn.com.mustache.mybatis.dom.model.Mapper;
import cn.com.mustache.mybatis.util.JavaUtil;
import cn.com.mustache.mybatis.util.MapperUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Han
 */
public class JavaService {

    private final Project project;

    private final JavaPsiFacade javaPsiFacade;

    private final EditorService editorService;

    public JavaService(Project project) {
        this.project = project;
        this.javaPsiFacade = JavaPsiFacade.getInstance(project);
        this.editorService = EditorService.getInstance(project);
    }

    public static JavaService getInstance(@NotNull Project project) {
        return project.getService(JavaService.class);
    }

    public Optional<PsiClass> getReferenceClazzOfPsiField(@NotNull PsiElement field) {
        if (!(field instanceof PsiField)) {
            return Optional.empty();
        }
        PsiType type = ((PsiField) field).getType();
        return type instanceof PsiClassReferenceType ? Optional.ofNullable(((PsiClassReferenceType) type).resolve()) : Optional.empty();
    }

    public Optional<DomElement> findStatement(@Nullable PsiMethod method) {
        if (method == null) {
            return Optional.empty();
        }
        CommonProcessors.FindFirstProcessor<DomElement> processor = new CommonProcessors.FindFirstProcessor<>();
        process(method, processor);
        return processor.isFound() ? Optional.ofNullable(processor.getFoundValue()) : Optional.empty();
    }

    public void processMethod(@NotNull PsiMethod psiMethod, @NotNull Processor<IdDomElement> processor) {
        PsiClass psiClass = psiMethod.getContainingClass();
        if (null == psiClass) return;
        String id = psiClass.getQualifiedName() + "." + psiMethod.getName();
        for (Mapper mapper : MapperUtil.findMappers(psiMethod.getProject())) {
            for (IdDomElement idDomElement : mapper.getDaoElements()) {
                if (MapperUtil.getIdSignature(idDomElement).equals(id)) {
                    processor.process(idDomElement);
                }
            }
        }
    }

    public void processClass(@NotNull PsiClass clazz, @NotNull Processor<Mapper> processor) {
        String ns = clazz.getQualifiedName();
        for (Mapper mapper : MapperUtil.findMappers(clazz.getProject())) {
            if (MapperUtil.getNamespace(mapper).equals(ns)) {
                processor.process(mapper);
            }
        }
    }

    public void process(@NotNull PsiElement target, @NotNull Processor processor) {
        if (target instanceof PsiMethod) {
            processMethod((PsiMethod) target, processor);
        } else if (target instanceof PsiClass) {
            processClass((PsiClass) target, processor);
        }
    }

    public <T> Optional<T> findWithFindFirstProcessor(@NotNull PsiElement target) {
        CommonProcessors.FindFirstProcessor<T> processor = new CommonProcessors.FindFirstProcessor<T>();
        process(target, processor);
        return Optional.ofNullable(processor.getFoundValue());
    }

    public void importClazz(PsiJavaFile file, String clazzName) {
        if (!JavaUtil.hasImportClazz(file, clazzName)) {
            Optional<PsiClass> clazz = JavaUtil.findClazz(project, clazzName);
            PsiImportList importList = file.getImportList();
            if (clazz.isPresent() && null != importList) {
                PsiElementFactory elementFactory = javaPsiFacade.getElementFactory();
                PsiImportStatement statement = elementFactory.createImportStatement(clazz.get());
                importList.add(statement);
                editorService.format(file, statement);
            }
        }
    }
}

