package net.minecraft.client.gui.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CSeenAdvancementsPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancementsScreen extends Screen implements ClientAdvancementManager.IListener {
   private static final ResourceLocation WINDOW = new ResourceLocation("textures/gui/advancements/window.png");
   private static final ResourceLocation TABS = new ResourceLocation("textures/gui/advancements/tabs.png");
   private static final ITextComponent field_243327_c = new TranslationTextComponent("advancements.sad_label");
   private static final ITextComponent field_243328_p = new TranslationTextComponent("advancements.empty");
   private static final ITextComponent field_243329_q = new TranslationTextComponent("gui.advancements");
   private final ClientAdvancementManager clientAdvancementManager;
   private final Map<Advancement, AdvancementTabGui> tabs = Maps.newLinkedHashMap();
   private AdvancementTabGui selectedTab;
   private boolean isScrolling;
   private static int tabPage, maxPages;

   public AdvancementsScreen(ClientAdvancementManager p_i47383_1_) {
      super(NarratorChatListener.EMPTY);
      this.clientAdvancementManager = p_i47383_1_;
   }

   protected void func_231160_c_() {
      this.tabs.clear();
      this.selectedTab = null;
      this.clientAdvancementManager.setListener(this);
      if (this.selectedTab == null && !this.tabs.isEmpty()) {
         this.clientAdvancementManager.setSelectedTab(this.tabs.values().iterator().next().getAdvancement(), true);
      } else {
         this.clientAdvancementManager.setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getAdvancement(), true);
      }
      if (this.tabs.size() > AdvancementTabType.MAX_TABS) {
          int guiLeft = (this.field_230708_k_ - 252) / 2;
          int guiTop = (this.field_230709_l_ - 140) / 2;
          func_230480_a_(new net.minecraft.client.gui.widget.button.Button(guiLeft,            guiTop - 50, 20, 20, new net.minecraft.util.text.StringTextComponent("<"), b -> tabPage = Math.max(tabPage - 1, 0       )));
          func_230480_a_(new net.minecraft.client.gui.widget.button.Button(guiLeft + 252 - 20, guiTop - 50, 20, 20, new net.minecraft.util.text.StringTextComponent(">"), b -> tabPage = Math.min(tabPage + 1, maxPages)));
          maxPages = this.tabs.size() / AdvancementTabType.MAX_TABS;
      }
   }

   public void func_231164_f_() {
      this.clientAdvancementManager.setListener((ClientAdvancementManager.IListener)null);
      ClientPlayNetHandler clientplaynethandler = this.field_230706_i_.getConnection();
      if (clientplaynethandler != null) {
         clientplaynethandler.sendPacket(CSeenAdvancementsPacket.closedScreen());
      }

   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (p_231044_5_ == 0) {
         int i = (this.field_230708_k_ - 252) / 2;
         int j = (this.field_230709_l_ - 140) / 2;

         for(AdvancementTabGui advancementtabgui : this.tabs.values()) {
            if (advancementtabgui.getPage() == tabPage && advancementtabgui.func_195627_a(i, j, p_231044_1_, p_231044_3_)) {
               this.clientAdvancementManager.setSelectedTab(advancementtabgui.getAdvancement(), true);
               break;
            }
         }
      }

      return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (this.field_230706_i_.gameSettings.keyBindAdvancements.matchesKey(p_231046_1_, p_231046_2_)) {
         this.field_230706_i_.displayGuiScreen((Screen)null);
         this.field_230706_i_.mouseHelper.grabMouse();
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      int i = (this.field_230708_k_ - 252) / 2;
      int j = (this.field_230709_l_ - 140) / 2;
      this.func_230446_a_(p_230430_1_);
      if (maxPages != 0) {
          net.minecraft.util.text.ITextComponent page = new net.minecraft.util.text.StringTextComponent(String.format("%d / %d", tabPage + 1, maxPages + 1));
         int width = this.field_230712_o_.func_238414_a_(page);
         RenderSystem.disableLighting();
         this.field_230712_o_.func_238407_a_(p_230430_1_, page.func_241878_f(), i + (252 / 2) - (width / 2), j - 44, -1);
      }
      this.func_238696_c_(p_230430_1_, p_230430_2_, p_230430_3_, i, j);
      this.func_238695_a_(p_230430_1_, i, j);
      this.func_238697_d_(p_230430_1_, p_230430_2_, p_230430_3_, i, j);
   }

   public boolean func_231045_a_(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      if (p_231045_5_ != 0) {
         this.isScrolling = false;
         return false;
      } else {
         if (!this.isScrolling) {
            this.isScrolling = true;
         } else if (this.selectedTab != null) {
            this.selectedTab.func_195626_a(p_231045_6_, p_231045_8_);
         }

         return true;
      }
   }

   private void func_238696_c_(MatrixStack p_238696_1_, int p_238696_2_, int p_238696_3_, int p_238696_4_, int p_238696_5_) {
      AdvancementTabGui advancementtabgui = this.selectedTab;
      if (advancementtabgui == null) {
         func_238467_a_(p_238696_1_, p_238696_4_ + 9, p_238696_5_ + 18, p_238696_4_ + 9 + 234, p_238696_5_ + 18 + 113, -16777216);
         int i = p_238696_4_ + 9 + 117;
         func_238472_a_(p_238696_1_, this.field_230712_o_, field_243328_p, i, p_238696_5_ + 18 + 56 - 9 / 2, -1);
         func_238472_a_(p_238696_1_, this.field_230712_o_, field_243327_c, i, p_238696_5_ + 18 + 113 - 9, -1);
      } else {
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(p_238696_4_ + 9), (float)(p_238696_5_ + 18), 0.0F);
         advancementtabgui.func_238682_a_(p_238696_1_);
         RenderSystem.popMatrix();
         RenderSystem.depthFunc(515);
         RenderSystem.disableDepthTest();
      }
   }

   public void func_238695_a_(MatrixStack p_238695_1_, int p_238695_2_, int p_238695_3_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      this.field_230706_i_.getTextureManager().bindTexture(WINDOW);
      this.func_238474_b_(p_238695_1_, p_238695_2_, p_238695_3_, 0, 0, 252, 140);
      if (this.tabs.size() > 1) {
         this.field_230706_i_.getTextureManager().bindTexture(TABS);

         for(AdvancementTabGui advancementtabgui : this.tabs.values()) {
            if (advancementtabgui.getPage() == tabPage)
            advancementtabgui.func_238683_a_(p_238695_1_, p_238695_2_, p_238695_3_, advancementtabgui == this.selectedTab);
         }

         RenderSystem.enableRescaleNormal();
         RenderSystem.defaultBlendFunc();

         for(AdvancementTabGui advancementtabgui1 : this.tabs.values()) {
            if (advancementtabgui1.getPage() == tabPage)
            advancementtabgui1.drawIcon(p_238695_2_, p_238695_3_, this.field_230707_j_);
         }

         RenderSystem.disableBlend();
      }

      this.field_230712_o_.func_243248_b(p_238695_1_, field_243329_q, (float)(p_238695_2_ + 8), (float)(p_238695_3_ + 6), 4210752);
   }

   private void func_238697_d_(MatrixStack p_238697_1_, int p_238697_2_, int p_238697_3_, int p_238697_4_, int p_238697_5_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.selectedTab != null) {
         RenderSystem.pushMatrix();
         RenderSystem.enableDepthTest();
         RenderSystem.translatef((float)(p_238697_4_ + 9), (float)(p_238697_5_ + 18), 400.0F);
         this.selectedTab.func_238684_c_(p_238697_1_, p_238697_2_ - p_238697_4_ - 9, p_238697_3_ - p_238697_5_ - 18, p_238697_4_, p_238697_5_);
         RenderSystem.disableDepthTest();
         RenderSystem.popMatrix();
      }

      if (this.tabs.size() > 1) {
         for(AdvancementTabGui advancementtabgui : this.tabs.values()) {
            if (advancementtabgui.getPage() == tabPage && advancementtabgui.func_195627_a(p_238697_4_, p_238697_5_, (double)p_238697_2_, (double)p_238697_3_)) {
               this.func_238652_a_(p_238697_1_, advancementtabgui.func_238685_d_(), p_238697_2_, p_238697_3_);
            }
         }
      }

   }

   public void rootAdvancementAdded(Advancement advancementIn) {
      AdvancementTabGui advancementtabgui = AdvancementTabGui.create(this.field_230706_i_, this, this.tabs.size(), advancementIn);
      if (advancementtabgui != null) {
         this.tabs.put(advancementIn, advancementtabgui);
      }
   }

   public void rootAdvancementRemoved(Advancement advancementIn) {
   }

   public void nonRootAdvancementAdded(Advancement advancementIn) {
      AdvancementTabGui advancementtabgui = this.getTab(advancementIn);
      if (advancementtabgui != null) {
         advancementtabgui.addAdvancement(advancementIn);
      }

   }

   public void nonRootAdvancementRemoved(Advancement advancementIn) {
   }

   public void onUpdateAdvancementProgress(Advancement advancementIn, AdvancementProgress progress) {
      AdvancementEntryGui advancemententrygui = this.getAdvancementGui(advancementIn);
      if (advancemententrygui != null) {
         advancemententrygui.setAdvancementProgress(progress);
      }

   }

   public void setSelectedTab(@Nullable Advancement advancementIn) {
      this.selectedTab = this.tabs.get(advancementIn);
   }

   public void advancementsCleared() {
      this.tabs.clear();
      this.selectedTab = null;
   }

   @Nullable
   public AdvancementEntryGui getAdvancementGui(Advancement p_191938_1_) {
      AdvancementTabGui advancementtabgui = this.getTab(p_191938_1_);
      return advancementtabgui == null ? null : advancementtabgui.getAdvancementGui(p_191938_1_);
   }

   @Nullable
   private AdvancementTabGui getTab(Advancement p_191935_1_) {
      while(p_191935_1_.getParent() != null) {
         p_191935_1_ = p_191935_1_.getParent();
      }

      return this.tabs.get(p_191935_1_);
   }
}