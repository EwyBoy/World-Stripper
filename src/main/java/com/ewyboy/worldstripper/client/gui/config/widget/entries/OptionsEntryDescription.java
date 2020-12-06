package com.ewyboy.worldstripper.client.gui.config.value;

import com.ewyboy.worldstripper.client.gui.config.OptionsListWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OptionsEntryDescription extends OptionsListWidget.Entry {

    private final TextComponent title;

    public OptionsEntryDescription(String title) {
        this.title = new TranslationTextComponent(title);
    }

    @Override
    public void render(MatrixStack matrixStack, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
        client.fontRenderer.drawStringWithShadow(matrixStack, title.getString(), rowLeft + 10, (rowTop + (height / 4) + (client.fontRenderer.FONT_HEIGHT / 2) + 10), 0xffffff);
    }

}
