package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.chat.NormalChatListener;
import net.minecraft.client.gui.chat.OverlayChatListener;
import net.minecraft.client.gui.overlay.BossOverlayGui;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.client.gui.overlay.PlayerTabOverlayGui;
import net.minecraft.client.gui.overlay.SubtitleOverlayGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.FoodStats;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IngameGui extends AbstractGui {
   protected static final ResourceLocation VIGNETTE_TEX_PATH = new ResourceLocation("textures/misc/vignette.png");
   protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
   protected static final ResourceLocation PUMPKIN_BLUR_TEX_PATH = new ResourceLocation("textures/misc/pumpkinblur.png");
   private static final ITextComponent field_243249_e = new TranslationTextComponent("demo.demoExpired");
   protected final Random rand = new Random();
   protected final Minecraft mc;
   protected final ItemRenderer itemRenderer;
   protected final NewChatGui persistantChatGUI;
   protected int ticks;
   @Nullable
   protected ITextComponent overlayMessage;
   protected int overlayMessageTime;
   protected boolean animateOverlayMessageColor;
   public float prevVignetteBrightness = 1.0F;
   protected int remainingHighlightTicks;
   protected ItemStack highlightingItemStack = ItemStack.EMPTY;
   protected final DebugOverlayGui overlayDebug;
   protected final SubtitleOverlayGui overlaySubtitle;
   /** The spectator GUI for this in-game GUI instance */
   protected final SpectatorGui spectatorGui;
   protected final PlayerTabOverlayGui overlayPlayerList;
   protected final BossOverlayGui overlayBoss;
   /** A timer for the current title and subtitle displayed */
   protected int titlesTimer;
   /** The current title displayed */
   @Nullable
   protected ITextComponent displayedTitle;
   /** The current sub-title displayed */
   @Nullable
   protected ITextComponent displayedSubTitle;
   /** The time that the title take to fade in */
   protected int titleFadeIn;
   /** The time that the title is display */
   protected int titleDisplayTime;
   /** The time that the title take to fade out */
   protected int titleFadeOut;
   protected int playerHealth;
   protected int lastPlayerHealth;
   /** The last recorded system time */
   protected long lastSystemTime;
   /** Used with updateCounter to make the heart bar flash */
   protected long healthUpdateCounter;
   protected int scaledWidth;
   protected int scaledHeight;
   protected final Map<ChatType, List<IChatListener>> chatListeners = Maps.newHashMap();

   public IngameGui(Minecraft mcIn) {
      this.mc = mcIn;
      this.itemRenderer = mcIn.getItemRenderer();
      this.overlayDebug = new DebugOverlayGui(mcIn);
      this.spectatorGui = new SpectatorGui(mcIn);
      this.persistantChatGUI = new NewChatGui(mcIn);
      this.overlayPlayerList = new PlayerTabOverlayGui(mcIn, this);
      this.overlayBoss = new BossOverlayGui(mcIn);
      this.overlaySubtitle = new SubtitleOverlayGui(mcIn);

      for(ChatType chattype : ChatType.values()) {
         this.chatListeners.put(chattype, Lists.newArrayList());
      }

      IChatListener ichatlistener = NarratorChatListener.INSTANCE;
      this.chatListeners.get(ChatType.CHAT).add(new NormalChatListener(mcIn));
      this.chatListeners.get(ChatType.CHAT).add(ichatlistener);
      this.chatListeners.get(ChatType.SYSTEM).add(new NormalChatListener(mcIn));
      this.chatListeners.get(ChatType.SYSTEM).add(ichatlistener);
      this.chatListeners.get(ChatType.GAME_INFO).add(new OverlayChatListener(mcIn));
      this.setDefaultTitlesTimes();
   }

   /**
    * Set the differents times for the titles to their default values
    */
   public void setDefaultTitlesTimes() {
      this.titleFadeIn = 10;
      this.titleDisplayTime = 70;
      this.titleFadeOut = 20;
   }

   public void func_238445_a_(MatrixStack p_238445_1_, float p_238445_2_) {
      this.scaledWidth = this.mc.getMainWindow().getScaledWidth();
      this.scaledHeight = this.mc.getMainWindow().getScaledHeight();
      FontRenderer fontrenderer = this.getFontRenderer();
      RenderSystem.enableBlend();
      if (Minecraft.isFancyGraphicsEnabled()) {
         this.renderVignette(this.mc.getRenderViewEntity());
      } else {
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
      }

      ItemStack itemstack = this.mc.player.inventory.armorItemInSlot(3);
      if (this.mc.gameSettings.func_243230_g().func_243192_a() && itemstack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
         this.renderPumpkinOverlay();
      }

      float f = MathHelper.lerp(p_238445_2_, this.mc.player.prevTimeInPortal, this.mc.player.timeInPortal);
      if (f > 0.0F && !this.mc.player.isPotionActive(Effects.NAUSEA)) {
         this.renderPortal(f);
      }

      if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
         this.spectatorGui.func_238528_a_(p_238445_1_, p_238445_2_);
      } else if (!this.mc.gameSettings.hideGUI) {
         this.func_238443_a_(p_238445_2_, p_238445_1_);
      }

      if (!this.mc.gameSettings.hideGUI) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(field_230665_h_);
         RenderSystem.enableBlend();
         RenderSystem.enableAlphaTest();
         this.func_238456_d_(p_238445_1_);
         RenderSystem.defaultBlendFunc();
         this.mc.getProfiler().startSection("bossHealth");
         this.overlayBoss.func_238484_a_(p_238445_1_);
         this.mc.getProfiler().endSection();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(field_230665_h_);
         if (this.mc.playerController.shouldDrawHUD()) {
            this.func_238457_e_(p_238445_1_);
         }

         this.func_238458_f_(p_238445_1_);
         RenderSystem.disableBlend();
         int i = this.scaledWidth / 2 - 91;
         if (this.mc.player.isRidingHorse()) {
            this.func_238446_a_(p_238445_1_, i);
         } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.func_238454_b_(p_238445_1_, i);
         }

         if (this.mc.gameSettings.heldItemTooltips && this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
            this.func_238453_b_(p_238445_1_);
         } else if (this.mc.player.isSpectator()) {
            this.spectatorGui.func_238527_a_(p_238445_1_);
         }
      }

      if (this.mc.player.getSleepTimer() > 0) {
         this.mc.getProfiler().startSection("sleep");
         RenderSystem.disableDepthTest();
         RenderSystem.disableAlphaTest();
         float f2 = (float)this.mc.player.getSleepTimer();
         float f1 = f2 / 100.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F - (f2 - 100.0F) / 10.0F;
         }

         int j = (int)(220.0F * f1) << 24 | 1052704;
         func_238467_a_(p_238445_1_, 0, 0, this.scaledWidth, this.scaledHeight, j);
         RenderSystem.enableAlphaTest();
         RenderSystem.enableDepthTest();
         this.mc.getProfiler().endSection();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.mc.isDemo()) {
         this.func_238455_c_(p_238445_1_);
      }

      this.func_238444_a_(p_238445_1_);
      if (this.mc.gameSettings.showDebugInfo) {
         this.overlayDebug.render(p_238445_1_);
      }

      if (!this.mc.gameSettings.hideGUI) {
         if (this.overlayMessage != null && this.overlayMessageTime > 0) {
            this.mc.getProfiler().startSection("overlayMessage");
            float f3 = (float)this.overlayMessageTime - p_238445_2_;
            int i1 = (int)(f3 * 255.0F / 20.0F);
            if (i1 > 255) {
               i1 = 255;
            }

            if (i1 > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight - 68), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               int k1 = 16777215;
               if (this.animateOverlayMessageColor) {
                  k1 = MathHelper.hsvToRGB(f3 / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               int k = i1 << 24 & -16777216;
               int l = fontrenderer.func_238414_a_(this.overlayMessage);
               this.func_238448_a_(p_238445_1_, fontrenderer, -4, l, 16777215 | k);
               fontrenderer.func_243248_b(p_238445_1_, this.overlayMessage, (float)(-l / 2), -4.0F, k1 | k);
               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.mc.getProfiler().endSection();
         }

         if (this.displayedTitle != null && this.titlesTimer > 0) {
            this.mc.getProfiler().startSection("titleAndSubtitle");
            float f4 = (float)this.titlesTimer - p_238445_2_;
            int j1 = 255;
            if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
               float f5 = (float)(this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f4;
               j1 = (int)(f5 * 255.0F / (float)this.titleFadeIn);
            }

            if (this.titlesTimer <= this.titleFadeOut) {
               j1 = (int)(f4 * 255.0F / (float)this.titleFadeOut);
            }

            j1 = MathHelper.clamp(j1, 0, 255);
            if (j1 > 8) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight / 2), 0.0F);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.pushMatrix();
               RenderSystem.scalef(4.0F, 4.0F, 4.0F);
               int l1 = j1 << 24 & -16777216;
               int i2 = fontrenderer.func_238414_a_(this.displayedTitle);
               this.func_238448_a_(p_238445_1_, fontrenderer, -10, i2, 16777215 | l1);
               fontrenderer.func_243246_a(p_238445_1_, this.displayedTitle, (float)(-i2 / 2), -10.0F, 16777215 | l1);
               RenderSystem.popMatrix();
               if (this.displayedSubTitle != null) {
                  RenderSystem.pushMatrix();
                  RenderSystem.scalef(2.0F, 2.0F, 2.0F);
                  int k2 = fontrenderer.func_238414_a_(this.displayedSubTitle);
                  this.func_238448_a_(p_238445_1_, fontrenderer, 5, k2, 16777215 | l1);
                  fontrenderer.func_243246_a(p_238445_1_, this.displayedSubTitle, (float)(-k2 / 2), 5.0F, 16777215 | l1);
                  RenderSystem.popMatrix();
               }

               RenderSystem.disableBlend();
               RenderSystem.popMatrix();
            }

            this.mc.getProfiler().endSection();
         }

         this.overlaySubtitle.render(p_238445_1_);
         Scoreboard scoreboard = this.mc.world.getScoreboard();
         ScoreObjective scoreobjective = null;
         ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.player.getScoreboardName());
         if (scoreplayerteam != null) {
            int j2 = scoreplayerteam.getColor().getColorIndex();
            if (j2 >= 0) {
               scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j2);
            }
         }

         ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
         if (scoreobjective1 != null) {
            this.func_238447_a_(p_238445_1_, scoreobjective1);
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableAlphaTest();
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, (float)(this.scaledHeight - 48), 0.0F);
         this.mc.getProfiler().startSection("chat");
         this.persistantChatGUI.func_238492_a_(p_238445_1_, this.ticks);
         this.mc.getProfiler().endSection();
         RenderSystem.popMatrix();
         scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
         if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || this.mc.isIntegratedServerRunning() && this.mc.player.connection.getPlayerInfoMap().size() <= 1 && scoreobjective1 == null) {
            this.overlayPlayerList.setVisible(false);
         } else {
            this.overlayPlayerList.setVisible(true);
            this.overlayPlayerList.func_238523_a_(p_238445_1_, this.scaledWidth, scoreboard, scoreobjective1);
         }
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
   }

   protected void func_238448_a_(MatrixStack p_238448_1_, FontRenderer p_238448_2_, int p_238448_3_, int p_238448_4_, int p_238448_5_) {
      int i = this.mc.gameSettings.getTextBackgroundColor(0.0F);
      if (i != 0) {
         int j = -p_238448_4_ / 2;
         func_238467_a_(p_238448_1_, j - 2, p_238448_3_ - 2, j + p_238448_4_ + 2, p_238448_3_ + 9 + 2, ColorHelper.PackedColor.func_233005_a_(i, p_238448_5_));
      }

   }

   protected void func_238456_d_(MatrixStack p_238456_1_) {
      GameSettings gamesettings = this.mc.gameSettings;
      if (gamesettings.func_243230_g().func_243192_a()) {
         if (this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR || this.isTargetNamedMenuProvider(this.mc.objectMouseOver)) {
            if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !this.mc.player.hasReducedDebug() && !gamesettings.reducedDebugInfo) {
               RenderSystem.pushMatrix();
               RenderSystem.translatef((float)(this.scaledWidth / 2), (float)(this.scaledHeight / 2), (float)this.func_230927_p_());
               ActiveRenderInfo activerenderinfo = this.mc.gameRenderer.getActiveRenderInfo();
               RenderSystem.rotatef(activerenderinfo.getPitch(), -1.0F, 0.0F, 0.0F);
               RenderSystem.rotatef(activerenderinfo.getYaw(), 0.0F, 1.0F, 0.0F);
               RenderSystem.scalef(-1.0F, -1.0F, -1.0F);
               RenderSystem.renderCrosshair(10);
               RenderSystem.popMatrix();
            } else {
               RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
               int i = 15;
               this.func_238474_b_(p_238456_1_, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
               if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.CROSSHAIR) {
                  float f = this.mc.player.getCooledAttackStrength(0.0F);
                  boolean flag = false;
                  if (this.mc.pointedEntity != null && this.mc.pointedEntity instanceof LivingEntity && f >= 1.0F) {
                     flag = this.mc.player.getCooldownPeriod() > 5.0F;
                     flag = flag & this.mc.pointedEntity.isAlive();
                  }

                  int j = this.scaledHeight / 2 - 7 + 16;
                  int k = this.scaledWidth / 2 - 8;
                  if (flag) {
                     this.func_238474_b_(p_238456_1_, k, j, 68, 94, 16, 16);
                  } else if (f < 1.0F) {
                     int l = (int)(f * 17.0F);
                     this.func_238474_b_(p_238456_1_, k, j, 36, 94, 16, 4);
                     this.func_238474_b_(p_238456_1_, k, j, 52, 94, l, 4);
                  }
               }
            }

         }
      }
   }

   private boolean isTargetNamedMenuProvider(RayTraceResult rayTraceIn) {
      if (rayTraceIn == null) {
         return false;
      } else if (rayTraceIn.getType() == RayTraceResult.Type.ENTITY) {
         return ((EntityRayTraceResult)rayTraceIn).getEntity() instanceof INamedContainerProvider;
      } else if (rayTraceIn.getType() == RayTraceResult.Type.BLOCK) {
         BlockPos blockpos = ((BlockRayTraceResult)rayTraceIn).getPos();
         World world = this.mc.world;
         return world.getBlockState(blockpos).getContainer(world, blockpos) != null;
      } else {
         return false;
      }
   }

   protected void func_238444_a_(MatrixStack p_238444_1_) {
      Collection<EffectInstance> collection = this.mc.player.getActivePotionEffects();
      if (!collection.isEmpty()) {
         RenderSystem.enableBlend();
         int i = 0;
         int j = 0;
         PotionSpriteUploader potionspriteuploader = this.mc.getPotionSpriteUploader();
         List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());
         this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);

         for(EffectInstance effectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
            Effect effect = effectinstance.getPotion();
            if (!effectinstance.shouldRenderHUD()) continue;
            // Rebind in case previous renderHUDEffect changed texture
            this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
            if (effectinstance.isShowIcon()) {
               int k = this.scaledWidth;
               int l = 1;
               if (this.mc.isDemo()) {
                  l += 15;
               }

               if (effect.isBeneficial()) {
                  ++i;
                  k = k - 25 * i;
               } else {
                  ++j;
                  k = k - 25 * j;
                  l += 26;
               }

               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               float f = 1.0F;
               if (effectinstance.isAmbient()) {
                  this.func_238474_b_(p_238444_1_, k, l, 165, 166, 24, 24);
               } else {
                  this.func_238474_b_(p_238444_1_, k, l, 141, 166, 24, 24);
                  if (effectinstance.getDuration() <= 200) {
                     int i1 = 10 - effectinstance.getDuration() / 20;
                     f = MathHelper.clamp((float)effectinstance.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F) + MathHelper.cos((float)effectinstance.getDuration() * (float)Math.PI / 5.0F) * MathHelper.clamp((float)i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               TextureAtlasSprite textureatlassprite = potionspriteuploader.getSprite(effect);
               int j1 = k;
               int k1 = l;
               float f1 = f;
               list.add(() -> {
                  this.mc.getTextureManager().bindTexture(textureatlassprite.getAtlasTexture().getTextureLocation());
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, f1);
                  func_238470_a_(p_238444_1_, j1 + 3, k1 + 3, this.func_230927_p_(), 18, 18, textureatlassprite);
               });
               effectinstance.renderHUDEffect(this, p_238444_1_, k, l, this.func_230927_p_(), f);
            }
         }

         list.forEach(Runnable::run);
      }
   }

   protected void func_238443_a_(float p_238443_1_, MatrixStack p_238443_2_) {
      PlayerEntity playerentity = this.getRenderViewPlayer();
      if (playerentity != null) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
         ItemStack itemstack = playerentity.getHeldItemOffhand();
         HandSide handside = playerentity.getPrimaryHand().opposite();
         int i = this.scaledWidth / 2;
         int j = this.func_230927_p_();
         int k = 182;
         int l = 91;
         this.func_230926_e_(-90);
         this.func_238474_b_(p_238443_2_, i - 91, this.scaledHeight - 22, 0, 0, 182, 22);
         this.func_238474_b_(p_238443_2_, i - 91 - 1 + playerentity.inventory.currentItem * 20, this.scaledHeight - 22 - 1, 0, 22, 24, 22);
         if (!itemstack.isEmpty()) {
            if (handside == HandSide.LEFT) {
               this.func_238474_b_(p_238443_2_, i - 91 - 29, this.scaledHeight - 23, 24, 22, 29, 24);
            } else {
               this.func_238474_b_(p_238443_2_, i + 91, this.scaledHeight - 23, 53, 22, 29, 24);
            }
         }

         this.func_230926_e_(j);
         RenderSystem.enableRescaleNormal();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();

         for(int i1 = 0; i1 < 9; ++i1) {
            int j1 = i - 90 + i1 * 20 + 2;
            int k1 = this.scaledHeight - 16 - 3;
            this.renderHotbarItem(j1, k1, p_238443_1_, playerentity, playerentity.inventory.mainInventory.get(i1));
         }

         if (!itemstack.isEmpty()) {
            int i2 = this.scaledHeight - 16 - 3;
            if (handside == HandSide.LEFT) {
               this.renderHotbarItem(i - 91 - 26, i2, p_238443_1_, playerentity, itemstack);
            } else {
               this.renderHotbarItem(i + 91 + 10, i2, p_238443_1_, playerentity, itemstack);
            }
         }

         if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.HOTBAR) {
            float f = this.mc.player.getCooledAttackStrength(0.0F);
            if (f < 1.0F) {
               int j2 = this.scaledHeight - 20;
               int k2 = i + 91 + 6;
               if (handside == HandSide.RIGHT) {
                  k2 = i - 91 - 22;
               }

               this.mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
               int l1 = (int)(f * 19.0F);
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               this.func_238474_b_(p_238443_2_, k2, j2, 0, 94, 18, 18);
               this.func_238474_b_(p_238443_2_, k2, j2 + 18 - l1, 18, 112 - l1, 18, l1);
            }
         }

         RenderSystem.disableRescaleNormal();
         RenderSystem.disableBlend();
      }
   }

   public void func_238446_a_(MatrixStack p_238446_1_, int p_238446_2_) {
      this.mc.getProfiler().startSection("jumpBar");
      this.mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
      float f = this.mc.player.getHorseJumpPower();
      int i = 182;
      int j = (int)(f * 183.0F);
      int k = this.scaledHeight - 32 + 3;
      this.func_238474_b_(p_238446_1_, p_238446_2_, k, 0, 84, 182, 5);
      if (j > 0) {
         this.func_238474_b_(p_238446_1_, p_238446_2_, k, 0, 89, j, 5);
      }

      this.mc.getProfiler().endSection();
   }

   public void func_238454_b_(MatrixStack p_238454_1_, int p_238454_2_) {
      this.mc.getProfiler().startSection("expBar");
      this.mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
      int i = this.mc.player.xpBarCap();
      if (i > 0) {
         int j = 182;
         int k = (int)(this.mc.player.experience * 183.0F);
         int l = this.scaledHeight - 32 + 3;
         this.func_238474_b_(p_238454_1_, p_238454_2_, l, 0, 64, 182, 5);
         if (k > 0) {
            this.func_238474_b_(p_238454_1_, p_238454_2_, l, 0, 69, k, 5);
         }
      }

      this.mc.getProfiler().endSection();
      if (this.mc.player.experienceLevel > 0) {
         this.mc.getProfiler().startSection("expLevel");
         String s = "" + this.mc.player.experienceLevel;
         int i1 = (this.scaledWidth - this.getFontRenderer().getStringWidth(s)) / 2;
         int j1 = this.scaledHeight - 31 - 4;
         this.getFontRenderer().func_238421_b_(p_238454_1_, s, (float)(i1 + 1), (float)j1, 0);
         this.getFontRenderer().func_238421_b_(p_238454_1_, s, (float)(i1 - 1), (float)j1, 0);
         this.getFontRenderer().func_238421_b_(p_238454_1_, s, (float)i1, (float)(j1 + 1), 0);
         this.getFontRenderer().func_238421_b_(p_238454_1_, s, (float)i1, (float)(j1 - 1), 0);
         this.getFontRenderer().func_238421_b_(p_238454_1_, s, (float)i1, (float)j1, 8453920);
         this.mc.getProfiler().endSection();
      }

   }

   public void func_238453_b_(MatrixStack p_238453_1_) {
      this.mc.getProfiler().startSection("selectedItemName");
      if (this.remainingHighlightTicks > 0 && !this.highlightingItemStack.isEmpty()) {
         IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("")).func_230529_a_(this.highlightingItemStack.getDisplayName()).func_240699_a_(this.highlightingItemStack.getRarity().color);
         if (this.highlightingItemStack.hasDisplayName()) {
            iformattabletextcomponent.func_240699_a_(TextFormatting.ITALIC);
         }

         ITextComponent highlightTip = this.highlightingItemStack.getHighlightTip(iformattabletextcomponent);
         int i = this.getFontRenderer().func_238414_a_(highlightTip);
         int j = (this.scaledWidth - i) / 2;
         int k = this.scaledHeight - 59;
         if (!this.mc.playerController.shouldDrawHUD()) {
            k += 14;
         }

         int l = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);
         if (l > 255) {
            l = 255;
         }

         if (l > 0) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            func_238467_a_(p_238453_1_, j - 2, k - 2, j + i + 2, k + 9 + 2, this.mc.gameSettings.getChatBackgroundColor(0));
            FontRenderer font = highlightingItemStack.getItem().getFontRenderer(highlightingItemStack);
            if (font == null) {
               this.getFontRenderer().func_243246_a(p_238453_1_, highlightTip, (float)j, (float)k, 16777215 + (l << 24));
            } else {
               j = (this.scaledWidth - font.func_238414_a_(highlightTip)) / 2;
               font.func_243246_a(p_238453_1_, highlightTip, (float)j, (float)k, 16777215 + (l << 24));
            }
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
         }
      }

      this.mc.getProfiler().endSection();
   }

   public void func_238455_c_(MatrixStack p_238455_1_) {
      this.mc.getProfiler().startSection("demo");
      ITextComponent itextcomponent;
      if (this.mc.world.getGameTime() >= 120500L) {
         itextcomponent = field_243249_e;
      } else {
         itextcomponent = new TranslationTextComponent("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.world.getGameTime())));
      }

      int i = this.getFontRenderer().func_238414_a_(itextcomponent);
      this.getFontRenderer().func_243246_a(p_238455_1_, itextcomponent, (float)(this.scaledWidth - i - 10), 5.0F, 16777215);
      this.mc.getProfiler().endSection();
   }

   protected void func_238447_a_(MatrixStack p_238447_1_, ScoreObjective p_238447_2_) {
      Scoreboard scoreboard = p_238447_2_.getScoreboard();
      Collection<Score> collection = scoreboard.getSortedScores(p_238447_2_);
      List<Score> list = collection.stream().filter((p_212911_0_) -> {
         return p_212911_0_.getPlayerName() != null && !p_212911_0_.getPlayerName().startsWith("#");
      }).collect(Collectors.toList());
      if (list.size() > 15) {
         collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
      } else {
         collection = list;
      }

      List<Pair<Score, ITextComponent>> list1 = Lists.newArrayListWithCapacity(collection.size());
      ITextComponent itextcomponent = p_238447_2_.getDisplayName();
      int i = this.getFontRenderer().func_238414_a_(itextcomponent);
      int j = i;
      int k = this.getFontRenderer().getStringWidth(": ");

      for(Score score : collection) {
         ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
         ITextComponent itextcomponent1 = ScorePlayerTeam.func_237500_a_(scoreplayerteam, new StringTextComponent(score.getPlayerName()));
         list1.add(Pair.of(score, itextcomponent1));
         j = Math.max(j, this.getFontRenderer().func_238414_a_(itextcomponent1) + k + this.getFontRenderer().getStringWidth(Integer.toString(score.getScorePoints())));
      }

      int i2 = collection.size() * 9;
      int j2 = this.scaledHeight / 2 + i2 / 3;
      int k2 = 3;
      int l2 = this.scaledWidth - j - 3;
      int l = 0;
      int i1 = this.mc.gameSettings.getTextBackgroundColor(0.3F);
      int j1 = this.mc.gameSettings.getTextBackgroundColor(0.4F);

      for(Pair<Score, ITextComponent> pair : list1) {
         ++l;
         Score score1 = pair.getFirst();
         ITextComponent itextcomponent2 = pair.getSecond();
         String s = TextFormatting.RED + "" + score1.getScorePoints();
         int k1 = j2 - l * 9;
         int l1 = this.scaledWidth - 3 + 2;
         func_238467_a_(p_238447_1_, l2 - 2, k1, l1, k1 + 9, i1);
         this.getFontRenderer().func_243248_b(p_238447_1_, itextcomponent2, (float)l2, (float)k1, -1);
         this.getFontRenderer().func_238421_b_(p_238447_1_, s, (float)(l1 - this.getFontRenderer().getStringWidth(s)), (float)k1, -1);
         if (l == collection.size()) {
            func_238467_a_(p_238447_1_, l2 - 2, k1 - 9 - 1, l1, k1 - 1, j1);
            func_238467_a_(p_238447_1_, l2 - 2, k1 - 1, l1, k1, i1);
            this.getFontRenderer().func_243248_b(p_238447_1_, itextcomponent, (float)(l2 + j / 2 - i / 2), (float)(k1 - 9), -1);
         }
      }

   }

   private PlayerEntity getRenderViewPlayer() {
      return !(this.mc.getRenderViewEntity() instanceof PlayerEntity) ? null : (PlayerEntity)this.mc.getRenderViewEntity();
   }

   private LivingEntity getMountEntity() {
      PlayerEntity playerentity = this.getRenderViewPlayer();
      if (playerentity != null) {
         Entity entity = playerentity.getRidingEntity();
         if (entity == null) {
            return null;
         }

         if (entity instanceof LivingEntity) {
            return (LivingEntity)entity;
         }
      }

      return null;
   }

   private int getRenderMountHealth(LivingEntity p_212306_1_) {
      if (p_212306_1_ != null && p_212306_1_.isLiving()) {
         float f = p_212306_1_.getMaxHealth();
         int i = (int)(f + 0.5F) / 2;
         if (i > 30) {
            i = 30;
         }

         return i;
      } else {
         return 0;
      }
   }

   private int getVisibleMountHealthRows(int p_212302_1_) {
      return (int)Math.ceil((double)p_212302_1_ / 10.0D);
   }

   private void func_238457_e_(MatrixStack p_238457_1_) {
      PlayerEntity playerentity = this.getRenderViewPlayer();
      if (playerentity != null) {
         int i = MathHelper.ceil(playerentity.getHealth());
         boolean flag = this.healthUpdateCounter > (long)this.ticks && (this.healthUpdateCounter - (long)this.ticks) / 3L % 2L == 1L;
         long j = Util.milliTime();
         if (i < this.playerHealth && playerentity.hurtResistantTime > 0) {
            this.lastSystemTime = j;
            this.healthUpdateCounter = (long)(this.ticks + 20);
         } else if (i > this.playerHealth && playerentity.hurtResistantTime > 0) {
            this.lastSystemTime = j;
            this.healthUpdateCounter = (long)(this.ticks + 10);
         }

         if (j - this.lastSystemTime > 1000L) {
            this.playerHealth = i;
            this.lastPlayerHealth = i;
            this.lastSystemTime = j;
         }

         this.playerHealth = i;
         int k = this.lastPlayerHealth;
         this.rand.setSeed((long)(this.ticks * 312871));
         FoodStats foodstats = playerentity.getFoodStats();
         int l = foodstats.getFoodLevel();
         int i1 = this.scaledWidth / 2 - 91;
         int j1 = this.scaledWidth / 2 + 91;
         int k1 = this.scaledHeight - 39;
         float f = (float)playerentity.func_233637_b_(Attributes.field_233818_a_);
         int l1 = MathHelper.ceil(playerentity.getAbsorptionAmount());
         int i2 = MathHelper.ceil((f + (float)l1) / 2.0F / 10.0F);
         int j2 = Math.max(10 - (i2 - 2), 3);
         int k2 = k1 - (i2 - 1) * j2 - 10;
         int l2 = k1 - 10;
         int i3 = l1;
         int j3 = playerentity.getTotalArmorValue();
         int k3 = -1;
         if (playerentity.isPotionActive(Effects.REGENERATION)) {
            k3 = this.ticks % MathHelper.ceil(f + 5.0F);
         }

         this.mc.getProfiler().startSection("armor");

         for(int l3 = 0; l3 < 10; ++l3) {
            if (j3 > 0) {
               int i4 = i1 + l3 * 8;
               if (l3 * 2 + 1 < j3) {
                  this.func_238474_b_(p_238457_1_, i4, k2, 34, 9, 9, 9);
               }

               if (l3 * 2 + 1 == j3) {
                  this.func_238474_b_(p_238457_1_, i4, k2, 25, 9, 9, 9);
               }

               if (l3 * 2 + 1 > j3) {
                  this.func_238474_b_(p_238457_1_, i4, k2, 16, 9, 9, 9);
               }
            }
         }

         this.mc.getProfiler().endStartSection("health");

         for(int l5 = MathHelper.ceil((f + (float)l1) / 2.0F) - 1; l5 >= 0; --l5) {
            int i6 = 16;
            if (playerentity.isPotionActive(Effects.POISON)) {
               i6 += 36;
            } else if (playerentity.isPotionActive(Effects.WITHER)) {
               i6 += 72;
            }

            int j4 = 0;
            if (flag) {
               j4 = 1;
            }

            int k4 = MathHelper.ceil((float)(l5 + 1) / 10.0F) - 1;
            int l4 = i1 + l5 % 10 * 8;
            int i5 = k1 - k4 * j2;
            if (i <= 4) {
               i5 += this.rand.nextInt(2);
            }

            if (i3 <= 0 && l5 == k3) {
               i5 -= 2;
            }

            int j5 = 0;
            if (playerentity.world.getWorldInfo().isHardcore()) {
               j5 = 5;
            }

            this.func_238474_b_(p_238457_1_, l4, i5, 16 + j4 * 9, 9 * j5, 9, 9);
            if (flag) {
               if (l5 * 2 + 1 < k) {
                  this.func_238474_b_(p_238457_1_, l4, i5, i6 + 54, 9 * j5, 9, 9);
               }

               if (l5 * 2 + 1 == k) {
                  this.func_238474_b_(p_238457_1_, l4, i5, i6 + 63, 9 * j5, 9, 9);
               }
            }

            if (i3 > 0) {
               if (i3 == l1 && l1 % 2 == 1) {
                  this.func_238474_b_(p_238457_1_, l4, i5, i6 + 153, 9 * j5, 9, 9);
                  --i3;
               } else {
                  this.func_238474_b_(p_238457_1_, l4, i5, i6 + 144, 9 * j5, 9, 9);
                  i3 -= 2;
               }
            } else {
               if (l5 * 2 + 1 < i) {
                  this.func_238474_b_(p_238457_1_, l4, i5, i6 + 36, 9 * j5, 9, 9);
               }

               if (l5 * 2 + 1 == i) {
                  this.func_238474_b_(p_238457_1_, l4, i5, i6 + 45, 9 * j5, 9, 9);
               }
            }
         }

         LivingEntity livingentity = this.getMountEntity();
         int j6 = this.getRenderMountHealth(livingentity);
         if (j6 == 0) {
            this.mc.getProfiler().endStartSection("food");

            for(int k6 = 0; k6 < 10; ++k6) {
               int i7 = k1;
               int k7 = 16;
               int i8 = 0;
               if (playerentity.isPotionActive(Effects.HUNGER)) {
                  k7 += 36;
                  i8 = 13;
               }

               if (playerentity.getFoodStats().getSaturationLevel() <= 0.0F && this.ticks % (l * 3 + 1) == 0) {
                  i7 = k1 + (this.rand.nextInt(3) - 1);
               }

               int k8 = j1 - k6 * 8 - 9;
               this.func_238474_b_(p_238457_1_, k8, i7, 16 + i8 * 9, 27, 9, 9);
               if (k6 * 2 + 1 < l) {
                  this.func_238474_b_(p_238457_1_, k8, i7, k7 + 36, 27, 9, 9);
               }

               if (k6 * 2 + 1 == l) {
                  this.func_238474_b_(p_238457_1_, k8, i7, k7 + 45, 27, 9, 9);
               }
            }

            l2 -= 10;
         }

         this.mc.getProfiler().endStartSection("air");
         int l6 = playerentity.getAir();
         int j7 = playerentity.getMaxAir();
         if (playerentity.areEyesInFluid(FluidTags.WATER) || l6 < j7) {
            int l7 = this.getVisibleMountHealthRows(j6) - 1;
            l2 = l2 - l7 * 10;
            int j8 = MathHelper.ceil((double)(l6 - 2) * 10.0D / (double)j7);
            int l8 = MathHelper.ceil((double)l6 * 10.0D / (double)j7) - j8;

            for(int k5 = 0; k5 < j8 + l8; ++k5) {
               if (k5 < j8) {
                  this.func_238474_b_(p_238457_1_, j1 - k5 * 8 - 9, l2, 16, 18, 9, 9);
               } else {
                  this.func_238474_b_(p_238457_1_, j1 - k5 * 8 - 9, l2, 25, 18, 9, 9);
               }
            }
         }

         this.mc.getProfiler().endSection();
      }
   }

   private void func_238458_f_(MatrixStack p_238458_1_) {
      LivingEntity livingentity = this.getMountEntity();
      if (livingentity != null) {
         int i = this.getRenderMountHealth(livingentity);
         if (i != 0) {
            int j = (int)Math.ceil((double)livingentity.getHealth());
            this.mc.getProfiler().endStartSection("mountHealth");
            int k = this.scaledHeight - 39;
            int l = this.scaledWidth / 2 + 91;
            int i1 = k;
            int j1 = 0;

            for(boolean flag = false; i > 0; j1 += 20) {
               int k1 = Math.min(i, 10);
               i -= k1;

               for(int l1 = 0; l1 < k1; ++l1) {
                  int i2 = 52;
                  int j2 = 0;
                  int k2 = l - l1 * 8 - 9;
                  this.func_238474_b_(p_238458_1_, k2, i1, 52 + j2 * 9, 9, 9, 9);
                  if (l1 * 2 + 1 + j1 < j) {
                     this.func_238474_b_(p_238458_1_, k2, i1, 88, 9, 9, 9);
                  }

                  if (l1 * 2 + 1 + j1 == j) {
                     this.func_238474_b_(p_238458_1_, k2, i1, 97, 9, 9, 9);
                  }
               }

               i1 -= 10;
            }

         }
      }
   }

   protected void renderPumpkinOverlay() {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableAlphaTest();
      this.mc.getTextureManager().bindTexture(PUMPKIN_BLUR_TEX_PATH);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0.0D, (double)this.scaledHeight, -90.0D).tex(0.0F, 1.0F).endVertex();
      bufferbuilder.pos((double)this.scaledWidth, (double)this.scaledHeight, -90.0D).tex(1.0F, 1.0F).endVertex();
      bufferbuilder.pos((double)this.scaledWidth, 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
      bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
      tessellator.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void updateVignetteBrightness(Entity entityIn) {
      if (entityIn != null) {
         float f = MathHelper.clamp(1.0F - entityIn.getBrightness(), 0.0F, 1.0F);
         this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(f - this.prevVignetteBrightness) * 0.01D);
      }
   }

   protected void renderVignette(Entity entityIn) {
      WorldBorder worldborder = this.mc.world.getWorldBorder();
      float f = (float)worldborder.getClosestDistance(entityIn);
      double d0 = Math.min(worldborder.getResizeSpeed() * (double)worldborder.getWarningTime() * 1000.0D, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
      double d1 = Math.max((double)worldborder.getWarningDistance(), d0);
      if ((double)f < d1) {
         f = 1.0F - (float)((double)f / d1);
      } else {
         f = 0.0F;
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      if (f > 0.0F) {
         RenderSystem.color4f(0.0F, f, f, 1.0F);
      } else {
         RenderSystem.color4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
      }

      this.mc.getTextureManager().bindTexture(VIGNETTE_TEX_PATH);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0.0D, (double)this.scaledHeight, -90.0D).tex(0.0F, 1.0F).endVertex();
      bufferbuilder.pos((double)this.scaledWidth, (double)this.scaledHeight, -90.0D).tex(1.0F, 1.0F).endVertex();
      bufferbuilder.pos((double)this.scaledWidth, 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
      bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
      tessellator.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
   }

   protected void renderPortal(float timeInPortal) {
      if (timeInPortal < 1.0F) {
         timeInPortal = timeInPortal * timeInPortal;
         timeInPortal = timeInPortal * timeInPortal;
         timeInPortal = timeInPortal * 0.8F + 0.2F;
      }

      RenderSystem.disableAlphaTest();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, timeInPortal);
      this.mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.NETHER_PORTAL.getDefaultState());
      float f = textureatlassprite.getMinU();
      float f1 = textureatlassprite.getMinV();
      float f2 = textureatlassprite.getMaxU();
      float f3 = textureatlassprite.getMaxV();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(0.0D, (double)this.scaledHeight, -90.0D).tex(f, f3).endVertex();
      bufferbuilder.pos((double)this.scaledWidth, (double)this.scaledHeight, -90.0D).tex(f2, f3).endVertex();
      bufferbuilder.pos((double)this.scaledWidth, 0.0D, -90.0D).tex(f2, f1).endVertex();
      bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
      tessellator.draw();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderHotbarItem(int x, int y, float partialTicks, PlayerEntity player, ItemStack stack) {
      if (!stack.isEmpty()) {
         float f = (float)stack.getAnimationsToGo() - partialTicks;
         if (f > 0.0F) {
            RenderSystem.pushMatrix();
            float f1 = 1.0F + f / 5.0F;
            RenderSystem.translatef((float)(x + 8), (float)(y + 12), 0.0F);
            RenderSystem.scalef(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            RenderSystem.translatef((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
         }

         this.itemRenderer.renderItemAndEffectIntoGUI(player, stack, x, y);
         if (f > 0.0F) {
            RenderSystem.popMatrix();
         }

         this.itemRenderer.renderItemOverlays(this.mc.fontRenderer, stack, x, y);
      }
   }

   /**
    * The update tick for the ingame UI
    */
   public void tick() {
      if (this.overlayMessageTime > 0) {
         --this.overlayMessageTime;
      }

      if (this.titlesTimer > 0) {
         --this.titlesTimer;
         if (this.titlesTimer <= 0) {
            this.displayedTitle = null;
            this.displayedSubTitle = null;
         }
      }

      ++this.ticks;
      Entity entity = this.mc.getRenderViewEntity();
      if (entity != null) {
         this.updateVignetteBrightness(entity);
      }

      if (this.mc.player != null) {
         ItemStack itemstack = this.mc.player.inventory.getCurrentItem();
         if (itemstack.isEmpty()) {
            this.remainingHighlightTicks = 0;
         } else if (!this.highlightingItemStack.isEmpty() && itemstack.getItem() == this.highlightingItemStack.getItem() && (itemstack.getDisplayName().equals(this.highlightingItemStack.getDisplayName()) && itemstack.getHighlightTip(itemstack.getDisplayName()).equals(highlightingItemStack.getHighlightTip(highlightingItemStack.getDisplayName())))) {
            if (this.remainingHighlightTicks > 0) {
               --this.remainingHighlightTicks;
            }
         } else {
            this.remainingHighlightTicks = 40;
         }

         this.highlightingItemStack = itemstack;
      }

   }

   public void func_238451_a_(ITextComponent p_238451_1_) {
      this.setOverlayMessage(new TranslationTextComponent("record.nowPlaying", p_238451_1_), true);
   }

   public void setOverlayMessage(ITextComponent component, boolean animateColor) {
      this.overlayMessage = component;
      this.overlayMessageTime = 60;
      this.animateOverlayMessageColor = animateColor;
   }

   public void func_238452_a_(@Nullable ITextComponent p_238452_1_, @Nullable ITextComponent p_238452_2_, int p_238452_3_, int p_238452_4_, int p_238452_5_) {
      if (p_238452_1_ == null && p_238452_2_ == null && p_238452_3_ < 0 && p_238452_4_ < 0 && p_238452_5_ < 0) {
         this.displayedTitle = null;
         this.displayedSubTitle = null;
         this.titlesTimer = 0;
      } else if (p_238452_1_ != null) {
         this.displayedTitle = p_238452_1_;
         this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
      } else if (p_238452_2_ != null) {
         this.displayedSubTitle = p_238452_2_;
      } else {
         if (p_238452_3_ >= 0) {
            this.titleFadeIn = p_238452_3_;
         }

         if (p_238452_4_ >= 0) {
            this.titleDisplayTime = p_238452_4_;
         }

         if (p_238452_5_ >= 0) {
            this.titleFadeOut = p_238452_5_;
         }

         if (this.titlesTimer > 0) {
            this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
         }

      }
   }

   public void func_238450_a_(ChatType p_238450_1_, ITextComponent p_238450_2_, UUID p_238450_3_) {
      for(IChatListener ichatlistener : this.chatListeners.get(p_238450_1_)) {
         ichatlistener.say(p_238450_1_, p_238450_2_, p_238450_3_);
      }

   }

   /**
    * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
    */
   public NewChatGui getChatGUI() {
      return this.persistantChatGUI;
   }

   public int getTicks() {
      return this.ticks;
   }

   public FontRenderer getFontRenderer() {
      return this.mc.fontRenderer;
   }

   public SpectatorGui getSpectatorGui() {
      return this.spectatorGui;
   }

   public PlayerTabOverlayGui getTabList() {
      return this.overlayPlayerList;
   }

   /**
    * Reset the GuiPlayerTabOverlay's message header and footer
    */
   public void resetPlayersOverlayFooterHeader() {
      this.overlayPlayerList.resetFooterHeader();
      this.overlayBoss.clearBossInfos();
      this.mc.getToastGui().clear();
   }

   /**
    * Accessor for the GuiBossOverlay
    */
   public BossOverlayGui getBossOverlay() {
      return this.overlayBoss;
   }

   public void reset() {
      this.overlayDebug.resetChunk();
   }
}