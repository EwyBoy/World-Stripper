package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public abstract class Screen extends FocusableGui implements IScreen, IRenderable {
   private static final Logger field_230701_a_ = LogManager.getLogger();
   private static final Set<String> field_230702_b_ = Sets.newHashSet("http", "https");
   protected final ITextComponent field_230704_d_;
   protected final List<IGuiEventListener> field_230705_e_ = Lists.newArrayList();
   @Nullable
   protected Minecraft field_230706_i_;
   protected ItemRenderer field_230707_j_;
   public int field_230708_k_;
   public int field_230709_l_;
   protected final List<Widget> field_230710_m_ = Lists.newArrayList();
   public boolean field_230711_n_;
   protected FontRenderer field_230712_o_;
   private URI field_230703_c_;

   protected Screen(ITextComponent titleIn) {
      this.field_230704_d_ = titleIn;
   }

   public ITextComponent func_231171_q_() {
      return this.field_230704_d_;
   }

   public String func_231167_h_() {
      return this.func_231171_q_().getString();
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      for(int i = 0; i < this.field_230710_m_.size(); ++i) {
         this.field_230710_m_.get(i).func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      }

   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256 && this.func_231178_ax__()) {
         this.func_231175_as__();
         return true;
      } else if (p_231046_1_ == 258) {
         boolean flag = !func_231173_s_();
         if (!this.func_231049_c__(flag)) {
            this.func_231049_c__(flag);
         }

         return false;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public boolean func_231178_ax__() {
      return true;
   }

   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   protected <T extends Widget> T func_230480_a_(T p_230480_1_) {
      this.field_230710_m_.add(p_230480_1_);
      return this.func_230481_d_(p_230480_1_);
   }

   protected <T extends IGuiEventListener> T func_230481_d_(T p_230481_1_) {
      this.field_230705_e_.add(p_230481_1_);
      return p_230481_1_;
   }

   protected void func_230457_a_(MatrixStack p_230457_1_, ItemStack p_230457_2_, int p_230457_3_, int p_230457_4_) {
      FontRenderer font = p_230457_2_.getItem().getFontRenderer(p_230457_2_);
      net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(p_230457_2_);
      this.renderToolTip(p_230457_1_, Lists.transform(this.func_231151_a_(p_230457_2_), ITextComponent::func_241878_f), p_230457_3_, p_230457_4_, (font == null ? this.field_230712_o_ : font));
      net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
   }

   public List<ITextComponent> func_231151_a_(ItemStack p_231151_1_) {
      return p_231151_1_.getTooltip(this.field_230706_i_.player, this.field_230706_i_.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
   }

   public void func_238652_a_(MatrixStack p_238652_1_, ITextComponent p_238652_2_, int p_238652_3_, int p_238652_4_) {
      this.func_238654_b_(p_238652_1_, Arrays.asList(p_238652_2_.func_241878_f()), p_238652_3_, p_238652_4_);
   }

   public void func_243308_b(MatrixStack p_243308_1_, List<ITextComponent> p_243308_2_, int p_243308_3_, int p_243308_4_) {
      this.func_238654_b_(p_243308_1_, Lists.transform(p_243308_2_, ITextComponent::func_241878_f), p_243308_3_, p_243308_4_);
   }

   public void func_238654_b_(MatrixStack p_238654_1_, List<? extends IReorderingProcessor> p_238654_2_, int p_238654_3_, int p_238654_4_) {
      this.renderToolTip(p_238654_1_, p_238654_2_, p_238654_3_, p_238654_4_, field_230712_o_);
   }
   public void renderToolTip(MatrixStack p_238654_1_, List<? extends IReorderingProcessor> p_238654_2_, int p_238654_3_, int p_238654_4_, FontRenderer font) {
      //net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(p_238654_1_, p_238654_2_, p_238654_3_, p_238654_4_, field_230708_k_, field_230709_l_, -1, font);
      if (!p_238654_2_.isEmpty()) {
         int i = 0;

         for(IReorderingProcessor ireorderingprocessor : p_238654_2_) {
            int j = this.field_230712_o_.func_243245_a(ireorderingprocessor);
            if (j > i) {
               i = j;
            }
         }

         int i2 = p_238654_3_ + 12;
         int j2 = p_238654_4_ - 12;
         int k = 8;
         if (p_238654_2_.size() > 1) {
            k += 2 + (p_238654_2_.size() - 1) * 10;
         }

         if (i2 + i > this.field_230708_k_) {
            i2 -= 28 + i;
         }

         if (j2 + k + 6 > this.field_230709_l_) {
            j2 = this.field_230709_l_ - k - 6;
         }

         p_238654_1_.push();
         int l = -267386864;
         int i1 = 1347420415;
         int j1 = 1344798847;
         int k1 = 400;
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         Matrix4f matrix4f = p_238654_1_.getLast().getMatrix();
         func_238462_a_(matrix4f, bufferbuilder, i2 - 3, j2 - 4, i2 + i + 3, j2 - 3, 400, -267386864, -267386864);
         func_238462_a_(matrix4f, bufferbuilder, i2 - 3, j2 + k + 3, i2 + i + 3, j2 + k + 4, 400, -267386864, -267386864);
         func_238462_a_(matrix4f, bufferbuilder, i2 - 3, j2 - 3, i2 + i + 3, j2 + k + 3, 400, -267386864, -267386864);
         func_238462_a_(matrix4f, bufferbuilder, i2 - 4, j2 - 3, i2 - 3, j2 + k + 3, 400, -267386864, -267386864);
         func_238462_a_(matrix4f, bufferbuilder, i2 + i + 3, j2 - 3, i2 + i + 4, j2 + k + 3, 400, -267386864, -267386864);
         func_238462_a_(matrix4f, bufferbuilder, i2 - 3, j2 - 3 + 1, i2 - 3 + 1, j2 + k + 3 - 1, 400, 1347420415, 1344798847);
         func_238462_a_(matrix4f, bufferbuilder, i2 + i + 2, j2 - 3 + 1, i2 + i + 3, j2 + k + 3 - 1, 400, 1347420415, 1344798847);
         func_238462_a_(matrix4f, bufferbuilder, i2 - 3, j2 - 3, i2 + i + 3, j2 - 3 + 1, 400, 1347420415, 1347420415);
         func_238462_a_(matrix4f, bufferbuilder, i2 - 3, j2 + k + 2, i2 + i + 3, j2 + k + 3, 400, 1344798847, 1344798847);
         RenderSystem.enableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.shadeModel(7425);
         bufferbuilder.finishDrawing();
         WorldVertexBufferUploader.draw(bufferbuilder);
         RenderSystem.shadeModel(7424);
         RenderSystem.disableBlend();
         RenderSystem.enableTexture();
         IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
         p_238654_1_.translate(0.0D, 0.0D, 400.0D);

         for(int l1 = 0; l1 < p_238654_2_.size(); ++l1) {
            IReorderingProcessor ireorderingprocessor1 = p_238654_2_.get(l1);
            if (ireorderingprocessor1 != null) {
               this.field_230712_o_.func_238416_a_(ireorderingprocessor1, (float)i2, (float)j2, -1, true, matrix4f, irendertypebuffer$impl, false, 0, 15728880);
            }

            if (l1 == 0) {
               j2 += 2;
            }

            j2 += 10;
         }

         irendertypebuffer$impl.finish();
         p_238654_1_.pop();
      }
   }

   protected void func_238653_a_(MatrixStack p_238653_1_, @Nullable Style p_238653_2_, int p_238653_3_, int p_238653_4_) {
      if (p_238653_2_ != null && p_238653_2_.getHoverEvent() != null) {
         HoverEvent hoverevent = p_238653_2_.getHoverEvent();
         HoverEvent.ItemHover hoverevent$itemhover = hoverevent.func_240662_a_(HoverEvent.Action.field_230551_b_);
         if (hoverevent$itemhover != null) {
            this.func_230457_a_(p_238653_1_, hoverevent$itemhover.func_240689_a_(), p_238653_3_, p_238653_4_);
         } else {
            HoverEvent.EntityHover hoverevent$entityhover = hoverevent.func_240662_a_(HoverEvent.Action.field_230552_c_);
            if (hoverevent$entityhover != null) {
               if (this.field_230706_i_.gameSettings.advancedItemTooltips) {
                  this.func_243308_b(p_238653_1_, hoverevent$entityhover.func_240684_b_(), p_238653_3_, p_238653_4_);
               }
            } else {
               ITextComponent itextcomponent = hoverevent.func_240662_a_(HoverEvent.Action.field_230550_a_);
               if (itextcomponent != null) {
                  this.func_238654_b_(p_238653_1_, this.field_230706_i_.fontRenderer.func_238425_b_(itextcomponent, Math.max(this.field_230708_k_ / 2, 200)), p_238653_3_, p_238653_4_);
               }
            }
         }

      }
   }

   protected void func_231155_a_(String p_231155_1_, boolean p_231155_2_) {
   }

   public boolean func_230455_a_(@Nullable Style p_230455_1_) {
      if (p_230455_1_ == null) {
         return false;
      } else {
         ClickEvent clickevent = p_230455_1_.getClickEvent();
         if (func_231173_s_()) {
            if (p_230455_1_.getInsertion() != null) {
               this.func_231155_a_(p_230455_1_.getInsertion(), false);
            }
         } else if (clickevent != null) {
            if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.field_230706_i_.gameSettings.chatLinks) {
                  return false;
               }

               try {
                  URI uri = new URI(clickevent.getValue());
                  String s = uri.getScheme();
                  if (s == null) {
                     throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
                  }

                  if (!field_230702_b_.contains(s.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
                  }

                  if (this.field_230706_i_.gameSettings.chatLinksPrompt) {
                     this.field_230703_c_ = uri;
                     this.field_230706_i_.displayGuiScreen(new ConfirmOpenLinkScreen(this::func_231162_c_, clickevent.getValue(), false));
                  } else {
                     this.func_231156_a_(uri);
                  }
               } catch (URISyntaxException urisyntaxexception) {
                  field_230701_a_.error("Can't open url for {}", clickevent, urisyntaxexception);
               }
            } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI uri1 = (new File(clickevent.getValue())).toURI();
               this.func_231156_a_(uri1);
            } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.func_231155_a_(clickevent.getValue(), true);
            } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.func_231159_b_(clickevent.getValue(), false);
            } else if (clickevent.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
               this.field_230706_i_.keyboardListener.setClipboardString(clickevent.getValue());
            } else {
               field_230701_a_.error("Don't know how to handle {}", (Object)clickevent);
            }

            return true;
         }

         return false;
      }
   }

   public void func_231161_c_(String p_231161_1_) {
      this.func_231159_b_(p_231161_1_, true);
   }

   public void func_231159_b_(String p_231159_1_, boolean p_231159_2_) {
      p_231159_1_ = net.minecraftforge.event.ForgeEventFactory.onClientSendMessage(p_231159_1_);
      if (p_231159_1_.isEmpty()) return;
      if (p_231159_2_) {
         this.field_230706_i_.ingameGUI.getChatGUI().addToSentMessages(p_231159_1_);
      }
      //if (net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(mc.player, msg) != 0) return; //Forge: TODO Client command re-write

      this.field_230706_i_.player.sendChatMessage(p_231159_1_);
   }

   public void func_231158_b_(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
      this.field_230706_i_ = p_231158_1_;
      this.field_230707_j_ = p_231158_1_.getItemRenderer();
      this.field_230712_o_ = p_231158_1_.fontRenderer;
      this.field_230708_k_ = p_231158_2_;
      this.field_230709_l_ = p_231158_3_;
      java.util.function.Consumer<Widget> remove = (b) -> {
         field_230710_m_.remove(b);
         field_230705_e_.remove(b);
      };
      if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Pre(this, this.field_230710_m_, this::func_230480_a_, remove))) {
      this.field_230710_m_.clear();
      this.field_230705_e_.clear();
      this.func_231035_a_((IGuiEventListener)null);
      this.func_231160_c_();
      }
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post(this, this.field_230710_m_, this::func_230480_a_, remove));
   }

   public List<? extends IGuiEventListener> func_231039_at__() {
      return this.field_230705_e_;
   }

   protected void func_231160_c_() {
   }

   public void func_231023_e_() {
   }

   public void func_231164_f_() {
   }

   public void func_230446_a_(MatrixStack p_230446_1_) {
      this.func_238651_a_(p_230446_1_, 0);
   }

   public void func_238651_a_(MatrixStack p_238651_1_, int p_238651_2_) {
      if (this.field_230706_i_.world != null) {
         this.func_238468_a_(p_238651_1_, 0, 0, this.field_230708_k_, this.field_230709_l_, -1072689136, -804253680);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, p_238651_1_));
      } else {
         this.func_231165_f_(p_238651_2_);
      }

   }

   public void func_231165_f_(int p_231165_1_) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      this.field_230706_i_.getTextureManager().bindTexture(field_230663_f_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(0.0D, (double)this.field_230709_l_, 0.0D).tex(0.0F, (float)this.field_230709_l_ / 32.0F + (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)this.field_230708_k_, (double)this.field_230709_l_, 0.0D).tex((float)this.field_230708_k_ / 32.0F, (float)this.field_230709_l_ / 32.0F + (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos((double)this.field_230708_k_, 0.0D, 0.0D).tex((float)this.field_230708_k_ / 32.0F, (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
      bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0F, (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
      tessellator.draw();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, new MatrixStack()));
   }

   public boolean func_231177_au__() {
      return true;
   }

   private void func_231162_c_(boolean p_231162_1_) {
      if (p_231162_1_) {
         this.func_231156_a_(this.field_230703_c_);
      }

      this.field_230703_c_ = null;
      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_231156_a_(URI p_231156_1_) {
      Util.getOSType().openURI(p_231156_1_);
   }

   public static boolean func_231172_r_() {
      if (Minecraft.IS_RUNNING_ON_MAC) {
         return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 343) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 347);
      } else {
         return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 341) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 345);
      }
   }

   public static boolean func_231173_s_() {
      return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344);
   }

   public static boolean func_231174_t_() {
      return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 342) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 346);
   }

   public static boolean func_231166_g_(int p_231166_0_) {
      return p_231166_0_ == 88 && func_231172_r_() && !func_231173_s_() && !func_231174_t_();
   }

   public static boolean func_231168_h_(int p_231168_0_) {
      return p_231168_0_ == 86 && func_231172_r_() && !func_231173_s_() && !func_231174_t_();
   }

   public static boolean func_231169_i_(int p_231169_0_) {
      return p_231169_0_ == 67 && func_231172_r_() && !func_231173_s_() && !func_231174_t_();
   }

   public static boolean func_231170_j_(int p_231170_0_) {
      return p_231170_0_ == 65 && func_231172_r_() && !func_231173_s_() && !func_231174_t_();
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
   }

   public static void func_231153_a_(Runnable p_231153_0_, String p_231153_1_, String p_231153_2_) {
      try {
         p_231153_0_.run();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, p_231153_1_);
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected screen");
         crashreportcategory.addDetail("Screen name", () -> {
            return p_231153_2_;
         });
         throw new ReportedException(crashreport);
      }
   }

   protected boolean func_231154_a_(String p_231154_1_, char p_231154_2_, int p_231154_3_) {
      int i = p_231154_1_.indexOf(58);
      int j = p_231154_1_.indexOf(47);
      if (p_231154_2_ == ':') {
         return (j == -1 || p_231154_3_ <= j) && i == -1;
      } else if (p_231154_2_ == '/') {
         return p_231154_3_ > i;
      } else {
         return p_231154_2_ == '_' || p_231154_2_ == '-' || p_231154_2_ >= 'a' && p_231154_2_ <= 'z' || p_231154_2_ >= '0' && p_231154_2_ <= '9' || p_231154_2_ == '.';
      }
   }

   public boolean func_231047_b_(double p_231047_1_, double p_231047_3_) {
      return true;
   }

   public void func_230476_a_(List<Path> p_230476_1_) {
   }

   public Minecraft getMinecraft() {
      return this.field_230706_i_;
   }
}