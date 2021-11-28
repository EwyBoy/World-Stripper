package com.ewyboy.worldstripper.client.gui.config.widget.entries;

import com.ewyboy.worldstripper.client.gui.config.widget.OptionsListWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OptionsEntryButton extends OptionsListWidget.Entry {

    private final Button button;
    private final TextComponent title;

    public OptionsEntryButton(String title, Button button) {
        this.title = new TranslationTextComponent(title);
        this.button = button;
        button.setMessage(this.title);
    }

    @Override
    public void render(MatrixStack matrixStack, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
        this.button.x = rowLeft;
        this.button.y = rowTop + height / 6;
        this.button.render(matrixStack, mouseX, mouseY, deltaTime);
    }

    @Override
    public boolean mouseClicked(double mouseY, double mouseX, int button) {
        if (button == 0 && this.button.isHovered()) {
            this.button.playDownSound(Minecraft.getInstance().getSoundManager());
            this.button.onPress();
            return true;
        }
        return false;
    }
}
