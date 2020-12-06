package com.ewyboy.worldstripper.client.gui.config.widget.entries;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class OptionsEntryValueResource<T> extends OptionsEntryValue<T> {

    public static final Predicate<String> ANY = s -> true;

    private final Button button;
    private final TextFieldWidget textField;

    public OptionsEntryValueResource(String optionName, T value, Consumer<T> save, Button button, Predicate<String> validator) {
        super(optionName, save);

        this.value = value;
        this.textField = new WatchedTextfield(this, client.fontRenderer, 0, 0, 200, 20);
        textField.setText(String.valueOf(value));
        textField.setValidator(validator);
        this.button = button;
        button.setMessage(new StringTextComponent("X"));
    }

    public OptionsEntryValueResource(String optionName, T value, Consumer<T> save, Button button) {
        this(optionName, value, save, button, s -> true);
    }

    @Override
    protected void drawValue(MatrixStack matrixStack, int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        textField.x = x;
        textField.y = y + entryHeight / 6;
        this.button.x = textField.x + textField.getWidth() + 10;
        this.button.y = y + entryHeight / 6;
        textField.render(matrixStack, mouseX, mouseY, partialTicks);
        button.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseY, double mouseX, int button) {
        if (button == 0 && this.button.isHovered()) {
            this.button.playDownSound(Minecraft.getInstance().getSoundHandler());
            this.button.onPress();
            return true;
        }
        return false;
    }

    @Override
    public IGuiEventListener getListener() {
        return textField;
    }

    @SuppressWarnings("unchecked")
    private void setValue(String text) {
        if (value instanceof String)
            value = (T) text;

        try {
            if (value instanceof Integer)
                value = (T) Integer.valueOf(text);
            else if (value instanceof Short)
                value = (T) Short.valueOf(text);
            else if (value instanceof Byte)
                value = (T) Byte.valueOf(text);
            else if (value instanceof Long)
                value = (T) Long.valueOf(text);
            else if (value instanceof Double)
                value = (T) Double.valueOf(text);
            else if (value instanceof Float)
                value = (T) Float.valueOf(text);
        } catch (NumberFormatException e) {
            // no-op
        }
    }

    private static class WatchedTextfield extends TextFieldWidget {
        private final OptionsEntryValueResource<?> watcher;

        public WatchedTextfield(OptionsEntryValueResource<?> watcher, FontRenderer fontRenderer, int x, int y, int width, int height) {
            super(fontRenderer, x, y, width, height, new StringTextComponent(""));
            this.watcher = watcher;
        }

        @Override
        public void writeText(String string) {
            super.writeText(string);
            watcher.setValue(getText());
        }

        @Override
        public void setText(String value) {
            super.setText(value);
            watcher.setValue(getText());
        }

        @Override
        public void deleteWords(int count) {
            super.deleteWords(count);
            watcher.setValue(getText());
        }
    }
}
