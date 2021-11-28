package com.ewyboy.worldstripper.client.gui.config.widget.entries;

import com.ewyboy.worldstripper.client.gui.config.widget.OptionsListWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class OptionsEntryDescription extends OptionsListWidget.Entry {

    private final TextComponent title;

    public OptionsEntryDescription(String title) {
        this.title = new TranslationTextComponent(title);
    }

    @Override
    public void render(MatrixStack matrixStack, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
        drawCenteredString(matrixStack, client.font, title, rowLeft + (width / 2), rowTop + (height / 4) + (client.font.lineHeight / 2) + 10, 0xffffff);
    }

    public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int text, int x, int y) {
        IReorderingProcessor ireorderingprocessor = font.getVisualOrderText();
        fontRenderer.drawShadow(matrixStack, ireorderingprocessor, (float) (text - fontRenderer.width(ireorderingprocessor) / 2), (float) x, y);
    }

}
