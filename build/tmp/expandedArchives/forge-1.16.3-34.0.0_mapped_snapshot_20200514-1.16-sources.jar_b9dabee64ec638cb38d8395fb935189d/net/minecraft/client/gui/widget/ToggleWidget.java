package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ToggleWidget extends Widget {
   protected ResourceLocation resourceLocation;
   protected boolean stateTriggered;
   protected int xTexStart;
   protected int yTexStart;
   protected int xDiffTex;
   protected int yDiffTex;

   public ToggleWidget(int xIn, int yIn, int widthIn, int heightIn, boolean triggered) {
      super(xIn, yIn, widthIn, heightIn, StringTextComponent.field_240750_d_);
      this.stateTriggered = triggered;
   }

   public void initTextureValues(int xTexStartIn, int yTexStartIn, int xDiffTexIn, int yDiffTexIn, ResourceLocation resourceLocationIn) {
      this.xTexStart = xTexStartIn;
      this.yTexStart = yTexStartIn;
      this.xDiffTex = xDiffTexIn;
      this.yDiffTex = yDiffTexIn;
      this.resourceLocation = resourceLocationIn;
   }

   public void setStateTriggered(boolean triggered) {
      this.stateTriggered = triggered;
   }

   public boolean isStateTriggered() {
      return this.stateTriggered;
   }

   public void setPosition(int xIn, int yIn) {
      this.field_230690_l_ = xIn;
      this.field_230691_m_ = yIn;
   }

   public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(this.resourceLocation);
      RenderSystem.disableDepthTest();
      int i = this.xTexStart;
      int j = this.yTexStart;
      if (this.stateTriggered) {
         i += this.xDiffTex;
      }

      if (this.func_230449_g_()) {
         j += this.yDiffTex;
      }

      this.func_238474_b_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, i, j, this.field_230688_j_, this.field_230689_k_);
      RenderSystem.enableDepthTest();
   }
}