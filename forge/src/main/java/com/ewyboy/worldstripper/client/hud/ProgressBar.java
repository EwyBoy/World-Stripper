package com.ewyboy.worldstripper.client.hud;

import com.ewyboy.worldstripper.workers.StripWorker;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class ProgressBar {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            init(event.getMatrixStack());
        }
    }

    private static final ResourceLocation BAR_TEXTURE_1 = new ResourceLocation("textures/block/red_concrete.png");
    private static final ResourceLocation BAR_TEXTURE_2 = new ResourceLocation("textures/block/yellow_concrete.png");
    private static final ResourceLocation BAR_TEXTURE_3 = new ResourceLocation("textures/block/lime_concrete.png");
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/block/gray_concrete.png");
    private static final ResourceLocation BAR_BACKGROUND_TEXTURE = new ResourceLocation("textures/block/white_concrete.png");

    public void init(MatrixStack stack) {
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

        mc.font.draw(stack, percent + " / 100%", textX, textY, 0xff212121);

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
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        Objects.requireNonNull(Minecraft.getInstance()).getTextureManager().bind(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.vertex(startX, endY, 0.0D).color(255, 255, 255, 255).uv(startX / 32.0F, endY / 32.0F).endVertex();
        buffer.vertex(endX, endY, 0.0D).color(255, 255, 255, 255).uv(endX / 32.0F, endY / 32.0F).endVertex();
        buffer.vertex(endX, startY, 0.0D).color(255, 255, 255, 255).uv(endX / 32.0F, startY / 32.0F).endVertex();
        buffer.vertex(startX, startY, 0.0D).color(255, 255, 255, 255).uv(startX / 32.0F, startY / 32.0F).endVertex();
        tessellator.end();
    }


}
