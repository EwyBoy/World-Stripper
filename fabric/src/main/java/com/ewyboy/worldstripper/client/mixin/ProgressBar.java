package com.ewyboy.worldstripper.client.mixin;

import com.ewyboy.worldstripper.workers.StripWorker;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

@Mixin(Gui.class)
public class ProgressBar {

    private static final ResourceLocation BAR_TEXTURE_1 = new ResourceLocation("textures/block/red_concrete.png");
    private static final ResourceLocation BAR_TEXTURE_2 = new ResourceLocation("textures/block/yellow_concrete.png");
    private static final ResourceLocation BAR_TEXTURE_3 = new ResourceLocation("textures/block/lime_concrete.png");
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/block/gray_concrete.png");
    private static final ResourceLocation BAR_BACKGROUND_TEXTURE = new ResourceLocation("textures/block/white_concrete.png");

    @Inject(at = @At("TAIL"), method = "render")
    public void init(GuiGraphics stack, float deltaTime, CallbackInfo info) {
        float percent = StripWorker.getProgress();

        if (percent == 0) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        percent = Math.round(percent);

        double gui = mc.getWindow().getGuiScale();
        int guiWidth = mc.getWindow().getGuiScaledWidth();
        int guiHeight = mc.getWindow().getGuiScaledHeight();

        int width = (int) ((guiWidth * gui + 1) / (10 + gui + 1));
        int height = (int) ((int) ((guiHeight * gui) / (10 + gui)) / 4.5);

        int centerX = mc.getWindow().getGuiScaledWidth() / 2;
        int centerY = mc.getWindow().getGuiScaledHeight() / 2;

        String text = (int) percent + " / 100%";

        int textX = centerX - (mc.font.width(text) / 2);
        int textY = centerY - (mc.font.lineHeight / 2);

        int startX = centerX - width;
        int stopX = centerX + width;
        int startY = centerY - height;
        int stopY = centerY + height;

        draw(startX, startY, stopX, stopY, BACKGROUND_TEXTURE);
        draw(startX + 2, startY + 2, stopX - 2, stopY - 2, BAR_BACKGROUND_TEXTURE);
        draw(startX + 2, startY + 2, getProgressBarWidth(startX, percent, width) - 2, stopY - 2, getBarTexture(percent));

        stack.drawString(mc.font, percent + " / 100%", textX, textY, 0xff212121, false);

        if (percent >= 100) {
            StripWorker.setProgress(0);
        }

    }

    private ResourceLocation getBarTexture(float percent) {
        if (percent < 33.33f) {
            return BAR_TEXTURE_1;
        } else if (percent < 66.66f) {
            return BAR_TEXTURE_2;
        } else {
            return BAR_TEXTURE_3;
        }
    }

    private int getProgressBarWidth(int start, float percent, int maxWidth) {
        return (int) (start + (double) (((percent / 100) * maxWidth) * 2));
    }

    private static void draw(int startX, int startY, int endX, int endY, ResourceLocation texture) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        RenderSystem.setShader(GameRenderer:: getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Objects.requireNonNull(Minecraft.getInstance()).getTextureManager().getTexture(texture);

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buffer.vertex(startX, endY, 0.0D).uv(startX / 32.0F, endY / 32.0F).color(255, 255, 255, 255).endVertex();
        buffer.vertex(endX, endY, 0.0D).uv(endX / 32.0F, endY / 32.0F).color(255, 255, 255, 255).endVertex();
        buffer.vertex(endX, startY, 0.0D).uv(endX / 32.0F, startY / 32.0F).color(255, 255, 255, 255).endVertex();
        buffer.vertex(startX, startY, 0.0D).uv(startX / 32.0F, startY / 32.0F).color(255, 255, 255, 255).endVertex();
        tesselator.end();
    }


}
