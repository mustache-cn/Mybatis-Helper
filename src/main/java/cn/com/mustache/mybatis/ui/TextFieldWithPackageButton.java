package cn.com.mustache.mybatis.ui;

import java.awt.event.ActionListener;
import javax.swing.*;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.TextAccessor;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextFieldWithPackageButton extends ComponentWithBrowseButton<JTextField> implements TextAccessor {
    public TextFieldWithPackageButton() {
        this((ActionListener) null);
    }

    public TextFieldWithPackageButton(JTextField field) {
        this(field, null);
    }

    public TextFieldWithPackageButton(JTextField field, @Nullable ActionListener actionListener) {
        this(field, actionListener, null);
    }

    public TextFieldWithPackageButton(JTextField field, @Nullable ActionListener actionListener, @Nullable Disposable parent) {
        super(field, actionListener);
        if (!(field instanceof JBTextField)) {
            UIUtil.addUndoRedoActions(field);
        }
        addActionListener(actionListener);
    }

    public TextFieldWithPackageButton(ActionListener actionListener) {
        this(actionListener, null);
    }

    public TextFieldWithPackageButton(ActionListener actionListener, Disposable parent) {
        this(new ExtendableTextField(10), // to prevent field to be infinitely resized in grid-box layouts
                actionListener, parent);
    }

    @NotNull
    public JTextField getTextField() {
        return getChildComponent();
    }

    @NotNull
    @Override
    public String getText() {
        return StringUtil.notNullize(getTextField().getText());
    }

    @Override
    public void setText(@NlsSafe @Nullable String text) {
        getTextField().setText(text);
    }

    public boolean isEditable() {
        return getTextField().isEditable();
    }

    public void setEditable(boolean b) {
        getTextField().setEditable(b);
    }
}
