package com.ewyboy.worldstripper.client.mixin;

import com.ewyboy.worldstripper.workers.StripWorker;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(InGameHud.class)
public class ProgressBar {

    private static final Identifier BAR_TEXTURE_1 = new Identifier("textures/block/red_concrete.png");
    private static final Identifier BAR_TEXTURE_2 = new Identifier("textures/block/yellow_concrete.png");
    private static final Identifier BAR_TEXTURE_3 = new Identifier("textures/block/lime_concrete.png");
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/block/gray_concrete.png");
    private static final Identifier BAR_BACKGROUND_TEXTURE = new Identifier("textures/block/white_concrete.png");

    @Inject(at = @At("TAIL"), method = "render")
    public void init(MatrixStack stack, float deltaTime, CallbackInfo info) {
        float percent = StripWorker.getProgress();

        if (percent == 0) {
            return;
        }

        MinecraftClient mc = MinecraftClient.getInstance();
        percent = Math.round(percent);

        // TODO: Linear Scaling - This does not work at SCALE : 1
        int width = (int) (mc.getWindow().getScaledWidth() * 0.6 / mc.getWindow().getScaleFactor());
        int height = (int) (mc.getWindow().getScaledHeight() * 0.2 / mc.getWindow().getScaleFactor());
        int centerX = mc.getWindow().getScaledWidth() / 2;
        int centerY = mc.getWindow().getScaledHeight() / 2;

        String text = (int) percent + " / 100%";

        int textX = centerX - (mc.textRenderer.getWidth(text) / 2);
        int textY = centerY - (mc.textRenderer.fontHeight / 2);

        int startX = centerX - width;
        int stopX = centerX + width;
        int startY = centerY - height;
        int stopY = centerY + height;

        draw(startX, startY, stopX, stopY, BACKGROUND_TEXTURE);
        draw(startX + 2, startY + 2, stopX - 2, stopY - 2, BAR_BACKGROUND_TEXTURE);
        draw(startX + 2, startY + 2, getProgressBarWidth(startX, percent, width) - 2, stopY - 2, getBarTexture(percent));

        mc.textRenderer.draw(stack, percent + " / 100%", textX, textY, 0xff212121);

        if (percent >= 100) {
            StripWorker.setProgress(0);
        }

    }

    private Identifier getBarTexture(float percent) {
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

    private static void draw(int startX, int startY, int endX, int endY, Identifier texture) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        Objects.requireNonNull(MinecraftClient.getInstance()).getTextureManager().bindTexture(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
            buffer.vertex(startX, endY, 0.0D).color(255, 255, 255, 255).texture(startX / 32.0F, endY / 32.0F).next();
            buffer.vertex(endX, endY, 0.0D).color(255, 255, 255, 255).texture(endX / 32.0F, endY / 32.0F).next();
            buffer.vertex(endX, startY, 0.0D).color(255, 255, 255, 255).texture(endX / 32.0F, startY / 32.0F).next();
            buffer.vertex(startX, startY, 0.0D).color(255, 255, 255, 255).texture(startX / 32.0F, startY / 32.0F).next();
        tessellator.draw();
    }


}
