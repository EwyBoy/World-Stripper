package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheckboxButton extends AbstractButton {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
   private boolean checked;
   private final boolean field_238499_c_;

   public CheckboxButton(int p_i232257_1_, int p_i232257_2_, int p_i232257_3_, int p_i232257_4_, ITextComponent p_i232257_5_, boolean p_i232257_6_) {
      this(p_i232257_1_, p_i232257_2_, p_i232257_3_, p_i232257_4_, p_i232257_5_, p_i232257_6_, true);
   }

   public CheckboxButton(int p_i232258_1_, int p_i232258_2_, int p_i232258_3_, int p_i232258_4_, ITextComponent p_i232258_5_, boolean p_i232258_6_, boolean p_i232258_7_) {
      super(p_i232258_1_, p_i232258_2_, p_i232258_3_, p_i232258_4_, p_i232258_5_);
      this.checked = p_i232258_6_;
      this.field_238499_c_ = p_i232258_7_;
   }

   public void func_230930_b_() {
      this.checked = !this.checked;
   }

   public boolean isChecked() {
      return this.checked;
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(TEXTURE);
      RenderSystem.enableDepthTest();
      FontRenderer fontrenderer = minecraft.fontRenderer;
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      func_238463_a_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, this.func_230999_j_() ? 20.0F : 0.0F, this.checked ? 20.0F : 0.0F, 20, this.field_230689_k_, 64, 64);
      this.func_230441_a_(p_230431_1_, minecraft, p_230431_2_, p_230431_3_);
      if (this.field_238499_c_) {
         func_238475_b_(p_230431_1_, fontrenderer, this.func_230458_i_(), this.field_230690_l_ + 24, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, 14737632 | MathHelper.ceil(this.field_230695_q_ * 255.0F) << 24);
      }

   }
}