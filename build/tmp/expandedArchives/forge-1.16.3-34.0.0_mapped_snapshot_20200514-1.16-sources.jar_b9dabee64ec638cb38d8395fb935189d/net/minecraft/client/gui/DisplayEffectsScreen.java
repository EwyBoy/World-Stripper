package net.minecraft.client.gui;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DisplayEffectsScreen<T extends Container> extends ContainerScreen<T> {
   /** True if there is some potion effect to display */
   protected boolean hasActivePotionEffects;

   public DisplayEffectsScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
      super(screenContainer, inv, titleIn);
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.updateActivePotionEffects();
   }

   protected void updateActivePotionEffects() {
      if (this.field_230706_i_.player.getActivePotionEffects().isEmpty()) {
         this.guiLeft = (this.field_230708_k_ - this.xSize) / 2;
         this.hasActivePotionEffects = false;
      } else {
         if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent(this)))
            this.guiLeft = (this.field_230708_k_ - this.xSize) / 2;
         else
         this.guiLeft = 160 + (this.field_230708_k_ - this.xSize - 200) / 2;
         this.hasActivePotionEffects = true;
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.hasActivePotionEffects) {
         this.func_238811_b_(p_230430_1_);
      }

   }

   private void func_238811_b_(MatrixStack p_238811_1_) {
      int i = this.guiLeft - 124;
      Collection<EffectInstance> collection = this.field_230706_i_.player.getActivePotionEffects();
      if (!collection.isEmpty()) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int j = 33;
         if (collection.size() > 5) {
            j = 132 / (collection.size() - 1);
         }

         Iterable<EffectInstance> iterable = collection.stream().filter(effectInstance -> effectInstance.shouldRender()).sorted().collect(java.util.stream.Collectors.toList());
         this.func_238810_a_(p_238811_1_, i, j, iterable);
         this.func_238812_b_(p_238811_1_, i, j, iterable);
         this.func_238813_c_(p_238811_1_, i, j, iterable);
      }
   }

   private void func_238810_a_(MatrixStack p_238810_1_, int p_238810_2_, int p_238810_3_, Iterable<EffectInstance> p_238810_4_) {
      this.field_230706_i_.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
      int i = this.guiTop;

      for(EffectInstance effectinstance : p_238810_4_) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_238474_b_(p_238810_1_, p_238810_2_, i, 0, 166, 140, 32);
         i += p_238810_3_;
      }

   }

   private void func_238812_b_(MatrixStack p_238812_1_, int p_238812_2_, int p_238812_3_, Iterable<EffectInstance> p_238812_4_) {
      PotionSpriteUploader potionspriteuploader = this.field_230706_i_.getPotionSpriteUploader();
      int i = this.guiTop;

      for(EffectInstance effectinstance : p_238812_4_) {
         Effect effect = effectinstance.getPotion();
         TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
         this.field_230706_i_.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
         func_238470_a_(p_238812_1_, p_238812_2_ + 6, i + 7, this.func_230927_p_(), 18, 18, textureatlassprite);
         i += p_238812_3_;
      }

   }

   private void func_238813_c_(MatrixStack p_238813_1_, int p_238813_2_, int p_238813_3_, Iterable<EffectInstance> p_238813_4_) {
      int i = this.guiTop;

      for(EffectInstance effectinstance : p_238813_4_) {
         effectinstance.renderInventoryEffect(this, p_238813_1_, p_238813_2_, i, this.func_230927_p_());
         if (!effectinstance.shouldRenderInvText()) {
            i += p_238813_3_;
            continue;
         }
         String s = I18n.format(effectinstance.getPotion().getName());
         if (effectinstance.getAmplifier() >= 1 && effectinstance.getAmplifier() <= 9) {
            s = s + ' ' + I18n.format("enchantment.level." + (effectinstance.getAmplifier() + 1));
         }

         this.field_230712_o_.func_238405_a_(p_238813_1_, s, (float)(p_238813_2_ + 10 + 18), (float)(i + 6), 16777215);
         String s1 = EffectUtils.getPotionDurationString(effectinstance, 1.0F);
         this.field_230712_o_.func_238405_a_(p_238813_1_, s1, (float)(p_238813_2_ + 10 + 18), (float)(i + 6 + 10), 8355711);
         i += p_238813_3_;
      }

   }
}