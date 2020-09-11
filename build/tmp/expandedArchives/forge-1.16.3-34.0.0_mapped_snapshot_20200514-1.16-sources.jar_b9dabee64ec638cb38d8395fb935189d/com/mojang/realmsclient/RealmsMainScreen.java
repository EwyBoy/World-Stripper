package com.mojang.realmsclient;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.Ping;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RegionPingResult;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.screens.RealmsClientOutdatedScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsParentalConsentScreen;
import com.mojang.realmsclient.gui.screens.RealmsPendingInvitesScreen;
import com.mojang.realmsclient.util.RealmsPersistence;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.KeyCombo;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.ConnectingToRealmsAction;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsMainScreen extends RealmsScreen {
   private static final Logger field_224012_a = LogManager.getLogger();
   private static final ResourceLocation field_237540_b_ = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
   private static final ResourceLocation field_237541_c_ = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
   private static final ResourceLocation field_237542_p_ = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
   private static final ResourceLocation field_237543_q_ = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final ResourceLocation field_237544_r_ = new ResourceLocation("realms", "textures/gui/realms/leave_icon.png");
   private static final ResourceLocation field_237545_s_ = new ResourceLocation("realms", "textures/gui/realms/invitation_icons.png");
   private static final ResourceLocation field_237546_t_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
   private static final ResourceLocation field_237547_u_ = new ResourceLocation("realms", "textures/gui/realms/world_icon.png");
   private static final ResourceLocation field_237548_v_ = new ResourceLocation("realms", "textures/gui/title/realms.png");
   private static final ResourceLocation field_237549_w_ = new ResourceLocation("realms", "textures/gui/realms/configure_icon.png");
   private static final ResourceLocation field_237550_x_ = new ResourceLocation("realms", "textures/gui/realms/questionmark.png");
   private static final ResourceLocation field_237551_y_ = new ResourceLocation("realms", "textures/gui/realms/news_icon.png");
   private static final ResourceLocation field_237552_z_ = new ResourceLocation("realms", "textures/gui/realms/popup.png");
   private static final ResourceLocation field_237534_A_ = new ResourceLocation("realms", "textures/gui/realms/darken.png");
   private static final ResourceLocation field_237535_B_ = new ResourceLocation("realms", "textures/gui/realms/cross_icon.png");
   private static final ResourceLocation field_237536_C_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
   private static final ResourceLocation field_237537_D_ = new ResourceLocation("minecraft", "textures/gui/widgets.png");
   private static final ITextComponent field_243000_E = new TranslationTextComponent("mco.invites.nopending");
   private static final ITextComponent field_243001_F = new TranslationTextComponent("mco.invites.pending");
   private static final List<ITextComponent> field_243002_G = ImmutableList.of(new TranslationTextComponent("mco.trial.message.line1"), new TranslationTextComponent("mco.trial.message.line2"));
   private static final ITextComponent field_243003_H = new TranslationTextComponent("mco.selectServer.uninitialized");
   private static final ITextComponent field_243004_I = new TranslationTextComponent("mco.selectServer.expiredList");
   private static final ITextComponent field_243005_J = new TranslationTextComponent("mco.selectServer.expiredRenew");
   private static final ITextComponent field_243006_K = new TranslationTextComponent("mco.selectServer.expiredTrial");
   private static final ITextComponent field_243007_L = new TranslationTextComponent("mco.selectServer.expiredSubscribe");
   private static final ITextComponent field_243008_M = (new TranslationTextComponent("mco.selectServer.minigame")).func_240702_b_(" ");
   private static final ITextComponent field_243009_N = new TranslationTextComponent("mco.selectServer.popup");
   private static final ITextComponent field_243010_O = new TranslationTextComponent("mco.selectServer.expired");
   private static final ITextComponent field_243011_P = new TranslationTextComponent("mco.selectServer.expires.soon");
   private static final ITextComponent field_243012_Q = new TranslationTextComponent("mco.selectServer.expires.day");
   private static final ITextComponent field_243013_R = new TranslationTextComponent("mco.selectServer.open");
   private static final ITextComponent field_243014_S = new TranslationTextComponent("mco.selectServer.closed");
   private static final ITextComponent field_243015_T = new TranslationTextComponent("mco.selectServer.leave");
   private static final ITextComponent field_243016_U = new TranslationTextComponent("mco.selectServer.configure");
   private static final ITextComponent field_243017_V = new TranslationTextComponent("mco.selectServer.info");
   private static final ITextComponent field_243018_W = new TranslationTextComponent("mco.news");
   private static List<ResourceLocation> field_227918_e_ = ImmutableList.of();
   private static final RealmsDataFetcher field_224017_f = new RealmsDataFetcher();
   private static boolean field_224013_b;
   private static int field_224018_g = -1;
   private static volatile boolean field_224031_t;
   private static volatile boolean field_224032_u;
   private static volatile boolean field_224033_v;
   private static Screen field_224000_H;
   private static boolean field_224001_I;
   private final RateLimiter field_224014_c;
   private boolean field_224015_d;
   private final Screen field_224019_h;
   private volatile RealmsMainScreen.ServerList field_224020_i;
   private long field_224021_j = -1L;
   private Button field_224022_k;
   private Button field_224023_l;
   private Button field_224024_m;
   private Button field_224025_n;
   private Button field_224026_o;
   private List<ITextComponent> field_224027_p;
   private List<RealmsServer> field_224028_q = Lists.newArrayList();
   private volatile int field_224029_r;
   private int field_224030_s;
   private boolean field_224034_w;
   private boolean field_224035_x;
   private boolean field_224036_y;
   private volatile boolean field_224037_z;
   private volatile boolean field_223993_A;
   private volatile boolean field_223994_B;
   private volatile boolean field_223995_C;
   private volatile String field_223996_D;
   private int field_223997_E;
   private int field_223998_F;
   private boolean field_223999_G;
   private List<KeyCombo> field_224002_J;
   private int field_224003_K;
   private ReentrantLock field_224004_L = new ReentrantLock();
   private IBidiRenderer field_243019_aI = IBidiRenderer.field_243257_a;
   private RealmsMainScreen.ServerState field_237539_ap_;
   private Button field_224006_N;
   private Button field_224007_O;
   private Button field_224008_P;
   private Button field_224009_Q;
   private Button field_224010_R;
   private Button field_224011_S;

   public RealmsMainScreen(Screen p_i232181_1_) {
      this.field_224019_h = p_i232181_1_;
      this.field_224014_c = RateLimiter.create((double)0.016666668F);
   }

   private boolean func_223928_a() {
      if (func_223968_l() && this.field_224034_w) {
         if (this.field_224037_z && !this.field_223993_A) {
            return true;
         } else {
            for(RealmsServer realmsserver : this.field_224028_q) {
               if (realmsserver.field_230588_g_.equals(this.field_230706_i_.getSession().getPlayerID())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean func_223990_b() {
      if (func_223968_l() && this.field_224034_w) {
         if (this.field_224035_x) {
            return true;
         } else {
            return this.field_224037_z && !this.field_223993_A && this.field_224028_q.isEmpty() ? true : this.field_224028_q.isEmpty();
         }
      } else {
         return false;
      }
   }

   public void func_231160_c_() {
      this.field_224002_J = Lists.newArrayList(new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, () -> {
         field_224013_b = !field_224013_b;
      }), new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
         if (RealmsClient.field_224944_a == RealmsClient.Environment.STAGE) {
            this.func_223973_x();
         } else {
            this.func_223884_v();
         }

      }), new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
         if (RealmsClient.field_224944_a == RealmsClient.Environment.LOCAL) {
            this.func_223973_x();
         } else {
            this.func_223962_w();
         }

      }));
      if (field_224000_H != null) {
         this.field_230706_i_.displayGuiScreen(field_224000_H);
      } else {
         this.field_224004_L = new ReentrantLock();
         if (field_224033_v && !func_223968_l()) {
            this.func_223975_u();
         }

         this.func_223895_s();
         this.func_223965_t();
         if (!this.field_224015_d) {
            this.field_230706_i_.setConnectedToRealms(false);
         }

         this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
         if (func_223968_l()) {
            field_224017_f.func_225087_d();
         }

         this.field_223994_B = false;
         if (func_223968_l() && this.field_224034_w) {
            this.func_223901_c();
         }

         this.field_224020_i = new RealmsMainScreen.ServerList();
         if (field_224018_g != -1) {
            this.field_224020_i.func_230932_a_((double)field_224018_g);
         }

         this.func_230481_d_(this.field_224020_i);
         this.func_212932_b(this.field_224020_i);
         this.field_243019_aI = IBidiRenderer.func_243258_a(this.field_230712_o_, field_243009_N, 100);
      }
   }

   private static boolean func_223968_l() {
      return field_224032_u && field_224031_t;
   }

   public void func_223901_c() {
      this.field_224025_n = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 190, this.field_230709_l_ - 32, 90, 20, new TranslationTextComponent("mco.selectServer.configure"), (p_237637_1_) -> {
         this.func_223966_f(this.func_223967_a(this.field_224021_j));
      }));
      this.field_224022_k = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 93, this.field_230709_l_ - 32, 90, 20, new TranslationTextComponent("mco.selectServer.play"), (p_237635_1_) -> {
         RealmsServer realmsserver1 = this.func_223967_a(this.field_224021_j);
         if (realmsserver1 != null) {
            this.func_223911_a(realmsserver1, this);
         }
      }));
      this.field_224023_l = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ - 32, 90, 20, DialogTexts.field_240637_h_, (p_237632_1_) -> {
         if (!this.field_224036_y) {
            this.field_230706_i_.displayGuiScreen(this.field_224019_h);
         }

      }));
      this.field_224024_m = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 100, this.field_230709_l_ - 32, 90, 20, new TranslationTextComponent("mco.selectServer.expiredRenew"), (p_237629_1_) -> {
         this.func_223930_q();
      }));
      this.field_224026_o = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 202, this.field_230709_l_ - 32, 90, 20, new TranslationTextComponent("mco.selectServer.leave"), (p_237624_1_) -> {
         this.func_223906_g(this.func_223967_a(this.field_224021_j));
      }));
      this.field_224007_O = this.func_230480_a_(new RealmsMainScreen.PendingInvitesButton());
      this.field_224008_P = this.func_230480_a_(new RealmsMainScreen.NewsButton());
      this.field_224006_N = this.func_230480_a_(new RealmsMainScreen.InfoButton());
      this.field_224011_S = this.func_230480_a_(new RealmsMainScreen.CloseButton());
      this.field_224009_Q = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 52, this.func_223932_C() + 137 - 20, 98, 20, new TranslationTextComponent("mco.selectServer.trial"), (p_237618_1_) -> {
         if (this.field_224037_z && !this.field_223993_A) {
            Util.getOSType().openURI("https://aka.ms/startjavarealmstrial");
            this.field_230706_i_.displayGuiScreen(this.field_224019_h);
         }
      }));
      this.field_224010_R = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 52, this.func_223932_C() + 160 - 20, 98, 20, new TranslationTextComponent("mco.selectServer.buy"), (p_237612_0_) -> {
         Util.getOSType().openURI("https://aka.ms/BuyJavaRealms");
      }));
      RealmsServer realmsserver = this.func_223967_a(this.field_224021_j);
      this.func_223915_a(realmsserver);
   }

   private void func_223915_a(@Nullable RealmsServer p_223915_1_) {
      this.field_224022_k.field_230693_o_ = this.func_223897_b(p_223915_1_) && !this.func_223990_b();
      this.field_224024_m.field_230694_p_ = this.func_223920_c(p_223915_1_);
      this.field_224025_n.field_230694_p_ = this.func_223941_d(p_223915_1_);
      this.field_224026_o.field_230694_p_ = this.func_223959_e(p_223915_1_);
      boolean flag = this.func_223990_b() && this.field_224037_z && !this.field_223993_A;
      this.field_224009_Q.field_230694_p_ = flag;
      this.field_224009_Q.field_230693_o_ = flag;
      this.field_224010_R.field_230694_p_ = this.func_223990_b();
      this.field_224011_S.field_230694_p_ = this.func_223990_b() && this.field_224035_x;
      this.field_224024_m.field_230693_o_ = !this.func_223990_b();
      this.field_224025_n.field_230693_o_ = !this.func_223990_b();
      this.field_224026_o.field_230693_o_ = !this.func_223990_b();
      this.field_224008_P.field_230693_o_ = true;
      this.field_224007_O.field_230693_o_ = true;
      this.field_224023_l.field_230693_o_ = true;
      this.field_224006_N.field_230693_o_ = !this.func_223990_b();
   }

   private boolean func_223977_m() {
      return (!this.func_223990_b() || this.field_224035_x) && func_223968_l() && this.field_224034_w;
   }

   private boolean func_223897_b(@Nullable RealmsServer p_223897_1_) {
      return p_223897_1_ != null && !p_223897_1_.field_230591_j_ && p_223897_1_.field_230586_e_ == RealmsServer.Status.OPEN;
   }

   private boolean func_223920_c(@Nullable RealmsServer p_223920_1_) {
      return p_223920_1_ != null && p_223920_1_.field_230591_j_ && this.func_223885_h(p_223920_1_);
   }

   private boolean func_223941_d(@Nullable RealmsServer p_223941_1_) {
      return p_223941_1_ != null && this.func_223885_h(p_223941_1_);
   }

   private boolean func_223959_e(@Nullable RealmsServer p_223959_1_) {
      return p_223959_1_ != null && !this.func_223885_h(p_223959_1_);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.field_224036_y = false;
      ++this.field_224030_s;
      --this.field_224003_K;
      if (this.field_224003_K < 0) {
         this.field_224003_K = 0;
      }

      if (func_223968_l()) {
         field_224017_f.func_225086_b();
         if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.SERVER_LIST)) {
            List<RealmsServer> list = field_224017_f.func_225078_e();
            this.field_224020_i.func_231409_q_();
            boolean flag = !this.field_224034_w;
            if (flag) {
               this.field_224034_w = true;
            }

            if (list != null) {
               boolean flag1 = false;

               for(RealmsServer realmsserver : list) {
                  if (this.func_223991_i(realmsserver)) {
                     flag1 = true;
                  }
               }

               this.field_224028_q = list;
               if (this.func_223928_a()) {
                  this.field_224020_i.func_241825_a_(new RealmsMainScreen.TrialServerEntry());
               }

               for(RealmsServer realmsserver1 : this.field_224028_q) {
                  this.field_224020_i.func_230513_b_(new RealmsMainScreen.ServerEntry(realmsserver1));
               }

               if (!field_224001_I && flag1) {
                  field_224001_I = true;
                  this.func_223944_n();
               }
            }

            if (flag) {
               this.func_223901_c();
            }
         }

         if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.field_224029_r = field_224017_f.func_225081_f();
            if (this.field_224029_r > 0 && this.field_224014_c.tryAcquire(1)) {
               RealmsNarratorHelper.func_239550_a_(I18n.format("mco.configure.world.invite.narration", this.field_224029_r));
            }
         }

         if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.TRIAL_AVAILABLE) && !this.field_223993_A) {
            boolean flag2 = field_224017_f.func_225071_g();
            if (flag2 != this.field_224037_z && this.func_223990_b()) {
               this.field_224037_z = flag2;
               this.field_223994_B = false;
            } else {
               this.field_224037_z = flag2;
            }
         }

         if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.LIVE_STATS)) {
            RealmsServerPlayerLists realmsserverplayerlists = field_224017_f.func_225079_h();

            for(RealmsServerPlayerList realmsserverplayerlist : realmsserverplayerlists.field_230612_a_) {
               for(RealmsServer realmsserver2 : this.field_224028_q) {
                  if (realmsserver2.field_230582_a_ == realmsserverplayerlist.field_230609_a_) {
                     realmsserver2.func_230772_a_(realmsserverplayerlist);
                     break;
                  }
               }
            }
         }

         if (field_224017_f.func_225083_a(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            this.field_223995_C = field_224017_f.func_225059_i();
            this.field_223996_D = field_224017_f.func_225063_j();
         }

         field_224017_f.func_225072_c();
         if (this.func_223990_b()) {
            ++this.field_223998_F;
         }

         if (this.field_224006_N != null) {
            this.field_224006_N.field_230694_p_ = this.func_223977_m();
         }

      }
   }

   private void func_223944_n() {
      (new Thread(() -> {
         List<RegionPingResult> list = Ping.func_224864_a();
         RealmsClient realmsclient = RealmsClient.func_224911_a();
         PingResult pingresult = new PingResult();
         pingresult.field_230571_a_ = list;
         pingresult.field_230572_b_ = this.func_223952_o();

         try {
            realmsclient.func_224903_a(pingresult);
         } catch (Throwable throwable) {
            field_224012_a.warn("Could not send ping result to Realms: ", throwable);
         }

      })).start();
   }

   private List<Long> func_223952_o() {
      List<Long> list = Lists.newArrayList();

      for(RealmsServer realmsserver : this.field_224028_q) {
         if (this.func_223991_i(realmsserver)) {
            list.add(realmsserver.field_230582_a_);
         }
      }

      return list;
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
      this.func_223939_y();
   }

   private void func_223930_q() {
      RealmsServer realmsserver = this.func_223967_a(this.field_224021_j);
      if (realmsserver != null) {
         String s = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + realmsserver.field_230583_b_ + "&profileId=" + this.field_230706_i_.getSession().getPlayerID() + "&ref=" + (realmsserver.field_230592_k_ ? "expiredTrial" : "expiredRealm");
         this.field_230706_i_.keyboardListener.setClipboardString(s);
         Util.getOSType().openURI(s);
      }
   }

   private void func_223895_s() {
      if (!field_224033_v) {
         field_224033_v = true;
         (new Thread("MCO Compatability Checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.func_224911_a();

               try {
                  RealmsClient.CompatibleVersionResponse realmsclient$compatibleversionresponse = realmsclient.func_224939_i();
                  if (realmsclient$compatibleversionresponse == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                     RealmsMainScreen.field_224000_H = new RealmsClientOutdatedScreen(RealmsMainScreen.this.field_224019_h, true);
                     RealmsMainScreen.this.field_230706_i_.execute(() -> {
                        RealmsMainScreen.this.field_230706_i_.displayGuiScreen(RealmsMainScreen.field_224000_H);
                     });
                     return;
                  }

                  if (realmsclient$compatibleversionresponse == RealmsClient.CompatibleVersionResponse.OTHER) {
                     RealmsMainScreen.field_224000_H = new RealmsClientOutdatedScreen(RealmsMainScreen.this.field_224019_h, false);
                     RealmsMainScreen.this.field_230706_i_.execute(() -> {
                        RealmsMainScreen.this.field_230706_i_.displayGuiScreen(RealmsMainScreen.field_224000_H);
                     });
                     return;
                  }

                  RealmsMainScreen.this.func_223975_u();
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.field_224033_v = false;
                  RealmsMainScreen.field_224012_a.error("Couldn't connect to realms", (Throwable)realmsserviceexception);
                  if (realmsserviceexception.field_224981_a == 401) {
                     RealmsMainScreen.field_224000_H = new RealmsGenericErrorScreen(new TranslationTextComponent("mco.error.invalid.session.title"), new TranslationTextComponent("mco.error.invalid.session.message"), RealmsMainScreen.this.field_224019_h);
                     RealmsMainScreen.this.field_230706_i_.execute(() -> {
                        RealmsMainScreen.this.field_230706_i_.displayGuiScreen(RealmsMainScreen.field_224000_H);
                     });
                  } else {
                     RealmsMainScreen.this.field_230706_i_.execute(() -> {
                        RealmsMainScreen.this.field_230706_i_.displayGuiScreen(new RealmsGenericErrorScreen(realmsserviceexception, RealmsMainScreen.this.field_224019_h));
                     });
                  }
               }

            }
         }).start();
      }

   }

   private void func_223965_t() {
   }

   private void func_223975_u() {
      (new Thread("MCO Compatability Checker #1") {
         public void run() {
            RealmsClient realmsclient = RealmsClient.func_224911_a();

            try {
               Boolean obool = realmsclient.func_224918_g();
               if (obool) {
                  RealmsMainScreen.field_224012_a.info("Realms is available for this user");
                  RealmsMainScreen.field_224031_t = true;
               } else {
                  RealmsMainScreen.field_224012_a.info("Realms is not available for this user");
                  RealmsMainScreen.field_224031_t = false;
                  RealmsMainScreen.this.field_230706_i_.execute(() -> {
                     RealmsMainScreen.this.field_230706_i_.displayGuiScreen(new RealmsParentalConsentScreen(RealmsMainScreen.this.field_224019_h));
                  });
               }

               RealmsMainScreen.field_224032_u = true;
            } catch (RealmsServiceException realmsserviceexception) {
               RealmsMainScreen.field_224012_a.error("Couldn't connect to realms", (Throwable)realmsserviceexception);
               RealmsMainScreen.this.field_230706_i_.execute(() -> {
                  RealmsMainScreen.this.field_230706_i_.displayGuiScreen(new RealmsGenericErrorScreen(realmsserviceexception, RealmsMainScreen.this.field_224019_h));
               });
            }

         }
      }).start();
   }

   private void func_223884_v() {
      if (RealmsClient.field_224944_a != RealmsClient.Environment.STAGE) {
         (new Thread("MCO Stage Availability Checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.func_224911_a();

               try {
                  Boolean obool = realmsclient.func_224931_h();
                  if (obool) {
                     RealmsClient.func_224940_b();
                     RealmsMainScreen.field_224012_a.info("Switched to stage");
                     RealmsMainScreen.field_224017_f.func_225087_d();
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.field_224012_a.error("Couldn't connect to Realms: " + realmsserviceexception);
               }

            }
         }).start();
      }

   }

   private void func_223962_w() {
      if (RealmsClient.field_224944_a != RealmsClient.Environment.LOCAL) {
         (new Thread("MCO Local Availability Checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.func_224911_a();

               try {
                  Boolean obool = realmsclient.func_224931_h();
                  if (obool) {
                     RealmsClient.func_224941_d();
                     RealmsMainScreen.field_224012_a.info("Switched to local");
                     RealmsMainScreen.field_224017_f.func_225087_d();
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.field_224012_a.error("Couldn't connect to Realms: " + realmsserviceexception);
               }

            }
         }).start();
      }

   }

   private void func_223973_x() {
      RealmsClient.func_224921_c();
      field_224017_f.func_225087_d();
   }

   private void func_223939_y() {
      field_224017_f.func_225070_k();
   }

   private void func_223966_f(RealmsServer p_223966_1_) {
      if (this.field_230706_i_.getSession().getPlayerID().equals(p_223966_1_.field_230588_g_) || field_224013_b) {
         this.func_223949_z();
         this.field_230706_i_.displayGuiScreen(new RealmsConfigureWorldScreen(this, p_223966_1_.field_230582_a_));
      }

   }

   private void func_223906_g(@Nullable RealmsServer p_223906_1_) {
      if (p_223906_1_ != null && !this.field_230706_i_.getSession().getPlayerID().equals(p_223906_1_.field_230588_g_)) {
         this.func_223949_z();
         ITextComponent itextcomponent = new TranslationTextComponent("mco.configure.world.leave.question.line1");
         ITextComponent itextcomponent1 = new TranslationTextComponent("mco.configure.world.leave.question.line2");
         this.field_230706_i_.displayGuiScreen(new RealmsLongConfirmationScreen(this::func_237625_d_, RealmsLongConfirmationScreen.Type.Info, itextcomponent, itextcomponent1, true));
      }

   }

   private void func_223949_z() {
      field_224018_g = (int)this.field_224020_i.func_230966_l_();
   }

   @Nullable
   private RealmsServer func_223967_a(long p_223967_1_) {
      for(RealmsServer realmsserver : this.field_224028_q) {
         if (realmsserver.field_230582_a_ == p_223967_1_) {
            return realmsserver;
         }
      }

      return null;
   }

   private void func_237625_d_(boolean p_237625_1_) {
      if (p_237625_1_) {
         (new Thread("Realms-leave-server") {
            public void run() {
               try {
                  RealmsServer realmsserver = RealmsMainScreen.this.func_223967_a(RealmsMainScreen.this.field_224021_j);
                  if (realmsserver != null) {
                     RealmsClient realmsclient = RealmsClient.func_224911_a();
                     realmsclient.func_224912_c(realmsserver.field_230582_a_);
                     RealmsMainScreen.this.field_230706_i_.execute(() -> {
                        RealmsMainScreen.this.func_243059_h(realmsserver);
                     });
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  RealmsMainScreen.field_224012_a.error("Couldn't configure world");
                  RealmsMainScreen.this.field_230706_i_.execute(() -> {
                     RealmsMainScreen.this.field_230706_i_.displayGuiScreen(new RealmsGenericErrorScreen(realmsserviceexception, RealmsMainScreen.this));
                  });
               }

            }
         }).start();
      }

      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_243059_h(RealmsServer p_243059_1_) {
      field_224017_f.func_225085_a(p_243059_1_);
      this.field_224028_q.remove(p_243059_1_);
      this.field_224020_i.func_231039_at__().removeIf((p_243041_1_) -> {
         return p_243041_1_ instanceof RealmsMainScreen.ServerEntry && ((RealmsMainScreen.ServerEntry)p_243041_1_).field_223734_a.field_230582_a_ == this.field_224021_j;
      });
      this.field_224020_i.func_241215_a_((RealmsMainScreen.ListEntry)null);
      this.func_223915_a((RealmsServer)null);
      this.field_224021_j = -1L;
      this.field_224022_k.field_230693_o_ = false;
   }

   public void func_223978_e() {
      this.field_224021_j = -1L;
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_224002_J.forEach(KeyCombo::func_224800_a);
         this.func_223955_A();
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   private void func_223955_A() {
      if (this.func_223990_b() && this.field_224035_x) {
         this.field_224035_x = false;
      } else {
         this.field_230706_i_.displayGuiScreen(this.field_224019_h);
      }

   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      this.field_224002_J.forEach((p_237578_1_) -> {
         p_237578_1_.func_224799_a(p_231042_1_);
      });
      return true;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.field_237539_ap_ = RealmsMainScreen.ServerState.NONE;
      this.field_224027_p = null;
      this.func_230446_a_(p_230430_1_);
      this.field_224020_i.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.func_237579_a_(p_230430_1_, this.field_230708_k_ / 2 - 50, 7);
      if (RealmsClient.field_224944_a == RealmsClient.Environment.STAGE) {
         this.func_237613_c_(p_230430_1_);
      }

      if (RealmsClient.field_224944_a == RealmsClient.Environment.LOCAL) {
         this.func_237604_b_(p_230430_1_);
      }

      if (this.func_223990_b()) {
         this.func_237605_b_(p_230430_1_, p_230430_2_, p_230430_3_);
      } else {
         if (this.field_223994_B) {
            this.func_223915_a((RealmsServer)null);
            if (!this.field_230705_e_.contains(this.field_224020_i)) {
               this.field_230705_e_.add(this.field_224020_i);
            }

            RealmsServer realmsserver = this.func_223967_a(this.field_224021_j);
            this.field_224022_k.field_230693_o_ = this.func_223897_b(realmsserver);
         }

         this.field_223994_B = false;
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.field_224027_p != null) {
         this.func_237583_a_(p_230430_1_, this.field_224027_p, p_230430_2_, p_230430_3_);
      }

      if (this.field_224037_z && !this.field_223993_A && this.func_223990_b()) {
         this.field_230706_i_.getTextureManager().bindTexture(field_237536_C_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int k = 8;
         int i = 8;
         int j = 0;
         if ((Util.milliTime() / 800L & 1L) == 1L) {
            j = 8;
         }

         AbstractGui.func_238463_a_(p_230430_1_, this.field_224009_Q.field_230690_l_ + this.field_224009_Q.func_230998_h_() - 8 - 4, this.field_224009_Q.field_230691_m_ + this.field_224009_Q.func_238483_d_() / 2 - 4, 0.0F, (float)j, 8, 8, 8, 16);
      }

   }

   private void func_237579_a_(MatrixStack p_237579_1_, int p_237579_2_, int p_237579_3_) {
      this.field_230706_i_.getTextureManager().bindTexture(field_237548_v_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.scalef(0.5F, 0.5F, 0.5F);
      AbstractGui.func_238463_a_(p_237579_1_, p_237579_2_ * 2, p_237579_3_ * 2 - 5, 0.0F, 0.0F, 200, 50, 200, 50);
      RenderSystem.popMatrix();
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.func_223979_a(p_231044_1_, p_231044_3_) && this.field_224035_x) {
         this.field_224035_x = false;
         this.field_224036_y = true;
         return true;
      } else {
         return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
      }
   }

   private boolean func_223979_a(double p_223979_1_, double p_223979_3_) {
      int i = this.func_223989_B();
      int j = this.func_223932_C();
      return p_223979_1_ < (double)(i - 5) || p_223979_1_ > (double)(i + 315) || p_223979_3_ < (double)(j - 5) || p_223979_3_ > (double)(j + 171);
   }

   private void func_237605_b_(MatrixStack p_237605_1_, int p_237605_2_, int p_237605_3_) {
      int i = this.func_223989_B();
      int j = this.func_223932_C();
      if (!this.field_223994_B) {
         this.field_223997_E = 0;
         this.field_223998_F = 0;
         this.field_223999_G = true;
         this.func_223915_a((RealmsServer)null);
         if (this.field_230705_e_.contains(this.field_224020_i)) {
            IGuiEventListener iguieventlistener = this.field_224020_i;
            if (!this.field_230705_e_.remove(iguieventlistener)) {
               field_224012_a.error("Unable to remove widget: " + iguieventlistener);
            }
         }

         RealmsNarratorHelper.func_239550_a_(field_243009_N.getString());
      }

      if (this.field_224034_w) {
         this.field_223994_B = true;
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.7F);
      RenderSystem.enableBlend();
      this.field_230706_i_.getTextureManager().bindTexture(field_237534_A_);
      int l = 0;
      int k = 32;
      AbstractGui.func_238463_a_(p_237605_1_, 0, 32, 0.0F, 0.0F, this.field_230708_k_, this.field_230709_l_ - 40 - 32, 310, 166);
      RenderSystem.disableBlend();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(field_237552_z_);
      AbstractGui.func_238463_a_(p_237605_1_, i, j, 0.0F, 0.0F, 310, 166, 310, 166);
      if (!field_227918_e_.isEmpty()) {
         this.field_230706_i_.getTextureManager().bindTexture(field_227918_e_.get(this.field_223997_E));
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         AbstractGui.func_238463_a_(p_237605_1_, i + 7, j + 7, 0.0F, 0.0F, 195, 152, 195, 152);
         if (this.field_223998_F % 95 < 5) {
            if (!this.field_223999_G) {
               this.field_223997_E = (this.field_223997_E + 1) % field_227918_e_.size();
               this.field_223999_G = true;
            }
         } else {
            this.field_223999_G = false;
         }
      }

      this.field_243019_aI.func_241866_c(p_237605_1_, this.field_230708_k_ / 2 + 52, j + 7, 10, 5000268);
   }

   private int func_223989_B() {
      return (this.field_230708_k_ - 310) / 2;
   }

   private int func_223932_C() {
      return this.field_230709_l_ / 2 - 80;
   }

   private void func_237581_a_(MatrixStack p_237581_1_, int p_237581_2_, int p_237581_3_, int p_237581_4_, int p_237581_5_, boolean p_237581_6_, boolean p_237581_7_) {
      int i = this.field_224029_r;
      boolean flag = this.func_223931_b((double)p_237581_2_, (double)p_237581_3_);
      boolean flag1 = p_237581_7_ && p_237581_6_;
      if (flag1) {
         float f = 0.25F + (1.0F + MathHelper.sin((float)this.field_224030_s * 0.5F)) * 0.25F;
         int j = -16777216 | (int)(f * 64.0F) << 16 | (int)(f * 64.0F) << 8 | (int)(f * 64.0F) << 0;
         this.func_238468_a_(p_237581_1_, p_237581_4_ - 2, p_237581_5_ - 2, p_237581_4_ + 18, p_237581_5_ + 18, j, j);
         j = -16777216 | (int)(f * 255.0F) << 16 | (int)(f * 255.0F) << 8 | (int)(f * 255.0F) << 0;
         this.func_238468_a_(p_237581_1_, p_237581_4_ - 2, p_237581_5_ - 2, p_237581_4_ + 18, p_237581_5_ - 1, j, j);
         this.func_238468_a_(p_237581_1_, p_237581_4_ - 2, p_237581_5_ - 2, p_237581_4_ - 1, p_237581_5_ + 18, j, j);
         this.func_238468_a_(p_237581_1_, p_237581_4_ + 17, p_237581_5_ - 2, p_237581_4_ + 18, p_237581_5_ + 18, j, j);
         this.func_238468_a_(p_237581_1_, p_237581_4_ - 2, p_237581_5_ + 17, p_237581_4_ + 18, p_237581_5_ + 18, j, j);
      }

      this.field_230706_i_.getTextureManager().bindTexture(field_237546_t_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      boolean flag3 = p_237581_7_ && p_237581_6_;
      float f2 = flag3 ? 16.0F : 0.0F;
      AbstractGui.func_238463_a_(p_237581_1_, p_237581_4_, p_237581_5_ - 6, f2, 0.0F, 15, 25, 31, 25);
      boolean flag2 = p_237581_7_ && i != 0;
      if (flag2) {
         int k = (Math.min(i, 6) - 1) * 8;
         int l = (int)(Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.field_224030_s) * 0.57F), MathHelper.cos((float)this.field_224030_s * 0.35F))) * -6.0F);
         this.field_230706_i_.getTextureManager().bindTexture(field_237545_s_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float f1 = flag ? 8.0F : 0.0F;
         AbstractGui.func_238463_a_(p_237581_1_, p_237581_4_ + 4, p_237581_5_ + 4 + l, (float)k, f1, 8, 8, 48, 16);
      }

      int j1 = p_237581_2_ + 12;
      boolean flag4 = p_237581_7_ && flag;
      if (flag4) {
         ITextComponent itextcomponent = i == 0 ? field_243000_E : field_243001_F;
         int i1 = this.field_230712_o_.func_238414_a_(itextcomponent);
         this.func_238468_a_(p_237581_1_, j1 - 3, p_237581_3_ - 3, j1 + i1 + 3, p_237581_3_ + 8 + 3, -1073741824, -1073741824);
         this.field_230712_o_.func_243246_a(p_237581_1_, itextcomponent, (float)j1, (float)p_237581_3_, -1);
      }

   }

   private boolean func_223931_b(double p_223931_1_, double p_223931_3_) {
      int i = this.field_230708_k_ / 2 + 50;
      int j = this.field_230708_k_ / 2 + 66;
      int k = 11;
      int l = 23;
      if (this.field_224029_r != 0) {
         i -= 3;
         j += 3;
         k -= 5;
         l += 5;
      }

      return (double)i <= p_223931_1_ && p_223931_1_ <= (double)j && (double)k <= p_223931_3_ && p_223931_3_ <= (double)l;
   }

   public void func_223911_a(RealmsServer p_223911_1_, Screen p_223911_2_) {
      if (p_223911_1_ != null) {
         try {
            if (!this.field_224004_L.tryLock(1L, TimeUnit.SECONDS)) {
               return;
            }

            if (this.field_224004_L.getHoldCount() > 1) {
               return;
            }
         } catch (InterruptedException interruptedexception) {
            return;
         }

         this.field_224015_d = true;
         this.field_230706_i_.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(p_223911_2_, new ConnectingToRealmsAction(this, p_223911_2_, p_223911_1_, this.field_224004_L)));
      }

   }

   private boolean func_223885_h(RealmsServer p_223885_1_) {
      return p_223885_1_.field_230588_g_ != null && p_223885_1_.field_230588_g_.equals(this.field_230706_i_.getSession().getPlayerID());
   }

   private boolean func_223991_i(RealmsServer p_223991_1_) {
      return this.func_223885_h(p_223991_1_) && !p_223991_1_.field_230591_j_;
   }

   private void func_237614_c_(MatrixStack p_237614_1_, int p_237614_2_, int p_237614_3_, int p_237614_4_, int p_237614_5_) {
      this.field_230706_i_.getTextureManager().bindTexture(field_237542_p_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      AbstractGui.func_238463_a_(p_237614_1_, p_237614_2_, p_237614_3_, 0.0F, 0.0F, 10, 28, 10, 28);
      if (p_237614_4_ >= p_237614_2_ && p_237614_4_ <= p_237614_2_ + 9 && p_237614_5_ >= p_237614_3_ && p_237614_5_ <= p_237614_3_ + 27 && p_237614_5_ < this.field_230709_l_ - 40 && p_237614_5_ > 32 && !this.func_223990_b()) {
         this.func_237603_a_(field_243010_O);
      }

   }

   private void func_237606_b_(MatrixStack p_237606_1_, int p_237606_2_, int p_237606_3_, int p_237606_4_, int p_237606_5_, int p_237606_6_) {
      this.field_230706_i_.getTextureManager().bindTexture(field_237543_q_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.field_224030_s % 20 < 10) {
         AbstractGui.func_238463_a_(p_237606_1_, p_237606_2_, p_237606_3_, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         AbstractGui.func_238463_a_(p_237606_1_, p_237606_2_, p_237606_3_, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (p_237606_4_ >= p_237606_2_ && p_237606_4_ <= p_237606_2_ + 9 && p_237606_5_ >= p_237606_3_ && p_237606_5_ <= p_237606_3_ + 27 && p_237606_5_ < this.field_230709_l_ - 40 && p_237606_5_ > 32 && !this.func_223990_b()) {
         if (p_237606_6_ <= 0) {
            this.func_237603_a_(field_243011_P);
         } else if (p_237606_6_ == 1) {
            this.func_237603_a_(field_243012_Q);
         } else {
            this.func_237603_a_(new TranslationTextComponent("mco.selectServer.expires.days", p_237606_6_));
         }
      }

   }

   private void func_237620_d_(MatrixStack p_237620_1_, int p_237620_2_, int p_237620_3_, int p_237620_4_, int p_237620_5_) {
      this.field_230706_i_.getTextureManager().bindTexture(field_237540_b_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      AbstractGui.func_238463_a_(p_237620_1_, p_237620_2_, p_237620_3_, 0.0F, 0.0F, 10, 28, 10, 28);
      if (p_237620_4_ >= p_237620_2_ && p_237620_4_ <= p_237620_2_ + 9 && p_237620_5_ >= p_237620_3_ && p_237620_5_ <= p_237620_3_ + 27 && p_237620_5_ < this.field_230709_l_ - 40 && p_237620_5_ > 32 && !this.func_223990_b()) {
         this.func_237603_a_(field_243013_R);
      }

   }

   private void func_237626_e_(MatrixStack p_237626_1_, int p_237626_2_, int p_237626_3_, int p_237626_4_, int p_237626_5_) {
      this.field_230706_i_.getTextureManager().bindTexture(field_237541_c_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      AbstractGui.func_238463_a_(p_237626_1_, p_237626_2_, p_237626_3_, 0.0F, 0.0F, 10, 28, 10, 28);
      if (p_237626_4_ >= p_237626_2_ && p_237626_4_ <= p_237626_2_ + 9 && p_237626_5_ >= p_237626_3_ && p_237626_5_ <= p_237626_3_ + 27 && p_237626_5_ < this.field_230709_l_ - 40 && p_237626_5_ > 32 && !this.func_223990_b()) {
         this.func_237603_a_(field_243014_S);
      }

   }

   private void func_237630_f_(MatrixStack p_237630_1_, int p_237630_2_, int p_237630_3_, int p_237630_4_, int p_237630_5_) {
      boolean flag = false;
      if (p_237630_4_ >= p_237630_2_ && p_237630_4_ <= p_237630_2_ + 28 && p_237630_5_ >= p_237630_3_ && p_237630_5_ <= p_237630_3_ + 28 && p_237630_5_ < this.field_230709_l_ - 40 && p_237630_5_ > 32 && !this.func_223990_b()) {
         flag = true;
      }

      this.field_230706_i_.getTextureManager().bindTexture(field_237544_r_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 28.0F : 0.0F;
      AbstractGui.func_238463_a_(p_237630_1_, p_237630_2_, p_237630_3_, f, 0.0F, 28, 28, 56, 28);
      if (flag) {
         this.func_237603_a_(field_243015_T);
         this.field_237539_ap_ = RealmsMainScreen.ServerState.LEAVE;
      }

   }

   private void func_237633_g_(MatrixStack p_237633_1_, int p_237633_2_, int p_237633_3_, int p_237633_4_, int p_237633_5_) {
      boolean flag = false;
      if (p_237633_4_ >= p_237633_2_ && p_237633_4_ <= p_237633_2_ + 28 && p_237633_5_ >= p_237633_3_ && p_237633_5_ <= p_237633_3_ + 28 && p_237633_5_ < this.field_230709_l_ - 40 && p_237633_5_ > 32 && !this.func_223990_b()) {
         flag = true;
      }

      this.field_230706_i_.getTextureManager().bindTexture(field_237549_w_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = flag ? 28.0F : 0.0F;
      AbstractGui.func_238463_a_(p_237633_1_, p_237633_2_, p_237633_3_, f, 0.0F, 28, 28, 56, 28);
      if (flag) {
         this.func_237603_a_(field_243016_U);
         this.field_237539_ap_ = RealmsMainScreen.ServerState.CONFIGURE;
      }

   }

   protected void func_237583_a_(MatrixStack p_237583_1_, List<ITextComponent> p_237583_2_, int p_237583_3_, int p_237583_4_) {
      if (!p_237583_2_.isEmpty()) {
         int i = 0;
         int j = 0;

         for(ITextComponent itextcomponent : p_237583_2_) {
            int k = this.field_230712_o_.func_238414_a_(itextcomponent);
            if (k > j) {
               j = k;
            }
         }

         int i1 = p_237583_3_ - j - 5;
         int j1 = p_237583_4_;
         if (i1 < 0) {
            i1 = p_237583_3_ + 12;
         }

         for(ITextComponent itextcomponent1 : p_237583_2_) {
            int l = j1 - (i == 0 ? 3 : 0) + i;
            this.func_238468_a_(p_237583_1_, i1 - 3, l, i1 + j + 3, j1 + 8 + 3 + i, -1073741824, -1073741824);
            this.field_230712_o_.func_243246_a(p_237583_1_, itextcomponent1, (float)i1, (float)(j1 + i), 16777215);
            i += 10;
         }

      }
   }

   private void func_237580_a_(MatrixStack p_237580_1_, int p_237580_2_, int p_237580_3_, int p_237580_4_, int p_237580_5_, boolean p_237580_6_) {
      boolean flag = false;
      if (p_237580_2_ >= p_237580_4_ && p_237580_2_ <= p_237580_4_ + 20 && p_237580_3_ >= p_237580_5_ && p_237580_3_ <= p_237580_5_ + 20) {
         flag = true;
      }

      this.field_230706_i_.getTextureManager().bindTexture(field_237550_x_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = p_237580_6_ ? 20.0F : 0.0F;
      AbstractGui.func_238463_a_(p_237580_1_, p_237580_4_, p_237580_5_, f, 0.0F, 20, 20, 40, 20);
      if (flag) {
         this.func_237603_a_(field_243017_V);
      }

   }

   private void func_237582_a_(MatrixStack p_237582_1_, int p_237582_2_, int p_237582_3_, boolean p_237582_4_, int p_237582_5_, int p_237582_6_, boolean p_237582_7_, boolean p_237582_8_) {
      boolean flag = false;
      if (p_237582_2_ >= p_237582_5_ && p_237582_2_ <= p_237582_5_ + 20 && p_237582_3_ >= p_237582_6_ && p_237582_3_ <= p_237582_6_ + 20) {
         flag = true;
      }

      this.field_230706_i_.getTextureManager().bindTexture(field_237551_y_);
      if (p_237582_8_) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.color4f(0.5F, 0.5F, 0.5F, 1.0F);
      }

      boolean flag1 = p_237582_8_ && p_237582_7_;
      float f = flag1 ? 20.0F : 0.0F;
      AbstractGui.func_238463_a_(p_237582_1_, p_237582_5_, p_237582_6_, f, 0.0F, 20, 20, 40, 20);
      if (flag && p_237582_8_) {
         this.func_237603_a_(field_243018_W);
      }

      if (p_237582_4_ && p_237582_8_) {
         int i = flag ? 0 : (int)(Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.field_224030_s) * 0.57F), MathHelper.cos((float)this.field_224030_s * 0.35F))) * -6.0F);
         this.field_230706_i_.getTextureManager().bindTexture(field_237545_s_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         AbstractGui.func_238463_a_(p_237582_1_, p_237582_5_ + 10, p_237582_6_ + 2 + i, 40.0F, 0.0F, 8, 8, 48, 16);
      }

   }

   private void func_237604_b_(MatrixStack p_237604_1_) {
      String s = "LOCAL!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.field_230708_k_ / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.field_230712_o_.func_238421_b_(p_237604_1_, "LOCAL!", 0.0F, 0.0F, 8388479);
      RenderSystem.popMatrix();
   }

   private void func_237613_c_(MatrixStack p_237613_1_) {
      String s = "STAGE!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.field_230708_k_ / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.field_230712_o_.func_238421_b_(p_237613_1_, "STAGE!", 0.0F, 0.0F, -256);
      RenderSystem.popMatrix();
   }

   public RealmsMainScreen func_223942_f() {
      RealmsMainScreen realmsmainscreen = new RealmsMainScreen(this.field_224019_h);
      realmsmainscreen.func_231158_b_(this.field_230706_i_, this.field_230708_k_, this.field_230709_l_);
      return realmsmainscreen;
   }

   public static void func_227932_a_(IResourceManager p_227932_0_) {
      Collection<ResourceLocation> collection = p_227932_0_.getAllResourceLocations("textures/gui/images", (p_227934_0_) -> {
         return p_227934_0_.endsWith(".png");
      });
      field_227918_e_ = collection.stream().filter((p_227931_0_) -> {
         return p_227931_0_.getNamespace().equals("realms");
      }).collect(ImmutableList.toImmutableList());
   }

   private void func_237603_a_(ITextComponent... p_237603_1_) {
      this.field_224027_p = Arrays.asList(p_237603_1_);
   }

   private void func_237598_a_(Button p_237598_1_) {
      this.field_230706_i_.displayGuiScreen(new RealmsPendingInvitesScreen(this.field_224019_h));
   }

   @OnlyIn(Dist.CLIENT)
   class CloseButton extends Button {
      public CloseButton() {
         super(RealmsMainScreen.this.func_223989_B() + 4, RealmsMainScreen.this.func_223932_C() + 4, 12, 12, new TranslationTextComponent("mco.selectServer.close"), null);
      }

      @Override
      public void func_230930_b_() {
            RealmsMainScreen.this.func_223955_A();
      }

      public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
         RealmsMainScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsMainScreen.field_237535_B_);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float f = this.func_230449_g_() ? 12.0F : 0.0F;
         func_238463_a_(p_230431_1_, this.field_230690_l_, this.field_230691_m_, 0.0F, f, 12, 12, 12, 24);
         if (this.func_231047_b_((double)p_230431_2_, (double)p_230431_3_)) {
            RealmsMainScreen.this.func_237603_a_(this.func_230458_i_());
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   class InfoButton extends Button {
      public InfoButton() {
         super(RealmsMainScreen.this.field_230708_k_ - 37, 6, 20, 20, new TranslationTextComponent("mco.selectServer.info"), null);
      }
      @Override
      public void func_230930_b_() {
            RealmsMainScreen.this.field_224035_x = !RealmsMainScreen.this.field_224035_x;
      }

      public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
         RealmsMainScreen.this.func_237580_a_(p_230431_1_, p_230431_2_, p_230431_3_, this.field_230690_l_, this.field_230691_m_, this.func_230449_g_());
      }
   }

   @OnlyIn(Dist.CLIENT)
   abstract class ListEntry extends ExtendedList.AbstractListEntry<RealmsMainScreen.ListEntry> {
      private ListEntry() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   class NewsButton extends Button {
      public NewsButton() {
         super(RealmsMainScreen.this.field_230708_k_ - 62, 6, 20, 20, StringTextComponent.field_240750_d_, null);
         this.func_238482_a_(new TranslationTextComponent("mco.news"));
      }
      @Override
      public void func_230930_b_() {
            if (RealmsMainScreen.this.field_223996_D != null) {
               Util.getOSType().openURI(RealmsMainScreen.this.field_223996_D);
               if (RealmsMainScreen.this.field_223995_C) {
                  RealmsPersistence.RealmsPersistenceData realmspersistence$realmspersistencedata = RealmsPersistence.func_225188_a();
                  realmspersistence$realmspersistencedata.field_225186_b = false;
                  RealmsMainScreen.this.field_223995_C = false;
                  RealmsPersistence.func_225187_a(realmspersistence$realmspersistencedata);
               }

            }
      }

      public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
         RealmsMainScreen.this.func_237582_a_(p_230431_1_, p_230431_2_, p_230431_3_, RealmsMainScreen.this.field_223995_C, this.field_230690_l_, this.field_230691_m_, this.func_230449_g_(), this.field_230693_o_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class PendingInvitesButton extends Button implements IScreen {
      public PendingInvitesButton() {
         super(RealmsMainScreen.this.field_230708_k_ / 2 + 47, 6, 22, 22, StringTextComponent.field_240750_d_, null);
      }

      @Override
      public void func_230930_b_() {
            RealmsMainScreen.this.func_237598_a_(this);
      }

      public void func_231023_e_() {
         this.func_238482_a_(new TranslationTextComponent(RealmsMainScreen.this.field_224029_r == 0 ? "mco.invites.nopending" : "mco.invites.pending"));
      }

      public void func_230431_b_(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
         RealmsMainScreen.this.func_237581_a_(p_230431_1_, p_230431_2_, p_230431_3_, this.field_230690_l_, this.field_230691_m_, this.func_230449_g_(), this.field_230693_o_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class ServerEntry extends RealmsMainScreen.ListEntry {
      private final RealmsServer field_223734_a;

      public ServerEntry(RealmsServer resourceManagerIn) {
         this.field_223734_a = resourceManagerIn;
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         this.func_237678_a_(this.field_223734_a, p_230432_1_, p_230432_4_, p_230432_3_, p_230432_7_, p_230432_8_);
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         if (this.field_223734_a.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
            RealmsMainScreen.this.field_224021_j = -1L;
            RealmsMainScreen.this.field_230706_i_.displayGuiScreen(new RealmsCreateRealmScreen(this.field_223734_a, RealmsMainScreen.this));
         } else {
            RealmsMainScreen.this.field_224021_j = this.field_223734_a.field_230582_a_;
         }

         return true;
      }

      private void func_237678_a_(RealmsServer p_237678_1_, MatrixStack p_237678_2_, int p_237678_3_, int p_237678_4_, int p_237678_5_, int p_237678_6_) {
         this.func_237679_b_(p_237678_1_, p_237678_2_, p_237678_3_ + 36, p_237678_4_, p_237678_5_, p_237678_6_);
      }

      private void func_237679_b_(RealmsServer p_237679_1_, MatrixStack p_237679_2_, int p_237679_3_, int p_237679_4_, int p_237679_5_, int p_237679_6_) {
         if (p_237679_1_.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
            RealmsMainScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsMainScreen.field_237547_u_);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableAlphaTest();
            AbstractGui.func_238463_a_(p_237679_2_, p_237679_3_ + 10, p_237679_4_ + 6, 0.0F, 0.0F, 40, 20, 40, 20);
            float f = 0.5F + (1.0F + MathHelper.sin((float)RealmsMainScreen.this.field_224030_s * 0.25F)) * 0.25F;
            int k2 = -16777216 | (int)(127.0F * f) << 16 | (int)(255.0F * f) << 8 | (int)(127.0F * f);
            AbstractGui.func_238472_a_(p_237679_2_, RealmsMainScreen.this.field_230712_o_, RealmsMainScreen.field_243003_H, p_237679_3_ + 10 + 40 + 75, p_237679_4_ + 12, k2);
         } else {
            int i = 225;
            int j = 2;
            if (p_237679_1_.field_230591_j_) {
               RealmsMainScreen.this.func_237614_c_(p_237679_2_, p_237679_3_ + 225 - 14, p_237679_4_ + 2, p_237679_5_, p_237679_6_);
            } else if (p_237679_1_.field_230586_e_ == RealmsServer.Status.CLOSED) {
               RealmsMainScreen.this.func_237626_e_(p_237679_2_, p_237679_3_ + 225 - 14, p_237679_4_ + 2, p_237679_5_, p_237679_6_);
            } else if (RealmsMainScreen.this.func_223885_h(p_237679_1_) && p_237679_1_.field_230593_l_ < 7) {
               RealmsMainScreen.this.func_237606_b_(p_237679_2_, p_237679_3_ + 225 - 14, p_237679_4_ + 2, p_237679_5_, p_237679_6_, p_237679_1_.field_230593_l_);
            } else if (p_237679_1_.field_230586_e_ == RealmsServer.Status.OPEN) {
               RealmsMainScreen.this.func_237620_d_(p_237679_2_, p_237679_3_ + 225 - 14, p_237679_4_ + 2, p_237679_5_, p_237679_6_);
            }

            if (!RealmsMainScreen.this.func_223885_h(p_237679_1_) && !RealmsMainScreen.field_224013_b) {
               RealmsMainScreen.this.func_237630_f_(p_237679_2_, p_237679_3_ + 225, p_237679_4_ + 2, p_237679_5_, p_237679_6_);
            } else {
               RealmsMainScreen.this.func_237633_g_(p_237679_2_, p_237679_3_ + 225, p_237679_4_ + 2, p_237679_5_, p_237679_6_);
            }

            if (!"0".equals(p_237679_1_.field_230599_r_.field_230607_a_)) {
               String s = TextFormatting.GRAY + "" + p_237679_1_.field_230599_r_.field_230607_a_;
               RealmsMainScreen.this.field_230712_o_.func_238421_b_(p_237679_2_, s, (float)(p_237679_3_ + 207 - RealmsMainScreen.this.field_230712_o_.getStringWidth(s)), (float)(p_237679_4_ + 3), 8421504);
               if (p_237679_5_ >= p_237679_3_ + 207 - RealmsMainScreen.this.field_230712_o_.getStringWidth(s) && p_237679_5_ <= p_237679_3_ + 207 && p_237679_6_ >= p_237679_4_ + 1 && p_237679_6_ <= p_237679_4_ + 10 && p_237679_6_ < RealmsMainScreen.this.field_230709_l_ - 40 && p_237679_6_ > 32 && !RealmsMainScreen.this.func_223990_b()) {
                  RealmsMainScreen.this.func_237603_a_(new StringTextComponent(p_237679_1_.field_230599_r_.field_230608_b_));
               }
            }

            if (RealmsMainScreen.this.func_223885_h(p_237679_1_) && p_237679_1_.field_230591_j_) {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.enableBlend();
               RealmsMainScreen.this.field_230706_i_.getTextureManager().bindTexture(RealmsMainScreen.field_237537_D_);
               RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
               ITextComponent itextcomponent;
               ITextComponent itextcomponent1;
               if (p_237679_1_.field_230592_k_) {
                  itextcomponent = RealmsMainScreen.field_243006_K;
                  itextcomponent1 = RealmsMainScreen.field_243007_L;
               } else {
                  itextcomponent = RealmsMainScreen.field_243004_I;
                  itextcomponent1 = RealmsMainScreen.field_243005_J;
               }

               int l = RealmsMainScreen.this.field_230712_o_.func_238414_a_(itextcomponent1) + 17;
               int i1 = 16;
               int j1 = p_237679_3_ + RealmsMainScreen.this.field_230712_o_.func_238414_a_(itextcomponent) + 8;
               int k1 = p_237679_4_ + 13;
               boolean flag = false;
               if (p_237679_5_ >= j1 && p_237679_5_ < j1 + l && p_237679_6_ > k1 && p_237679_6_ <= k1 + 16 & p_237679_6_ < RealmsMainScreen.this.field_230709_l_ - 40 && p_237679_6_ > 32 && !RealmsMainScreen.this.func_223990_b()) {
                  flag = true;
                  RealmsMainScreen.this.field_237539_ap_ = RealmsMainScreen.ServerState.EXPIRED;
               }

               int l1 = flag ? 2 : 1;
               AbstractGui.func_238463_a_(p_237679_2_, j1, k1, 0.0F, (float)(46 + l1 * 20), l / 2, 8, 256, 256);
               AbstractGui.func_238463_a_(p_237679_2_, j1 + l / 2, k1, (float)(200 - l / 2), (float)(46 + l1 * 20), l / 2, 8, 256, 256);
               AbstractGui.func_238463_a_(p_237679_2_, j1, k1 + 8, 0.0F, (float)(46 + l1 * 20 + 12), l / 2, 8, 256, 256);
               AbstractGui.func_238463_a_(p_237679_2_, j1 + l / 2, k1 + 8, (float)(200 - l / 2), (float)(46 + l1 * 20 + 12), l / 2, 8, 256, 256);
               RenderSystem.disableBlend();
               int i2 = p_237679_4_ + 11 + 5;
               int j2 = flag ? 16777120 : 16777215;
               RealmsMainScreen.this.field_230712_o_.func_243248_b(p_237679_2_, itextcomponent, (float)(p_237679_3_ + 2), (float)(i2 + 1), 15553363);
               AbstractGui.func_238472_a_(p_237679_2_, RealmsMainScreen.this.field_230712_o_, itextcomponent1, j1 + l / 2, i2 + 1, j2);
            } else {
               if (p_237679_1_.field_230594_m_ == RealmsServer.ServerType.MINIGAME) {
                  int l2 = 13413468;
                  int k = RealmsMainScreen.this.field_230712_o_.func_238414_a_(RealmsMainScreen.field_243008_M);
                  RealmsMainScreen.this.field_230712_o_.func_243248_b(p_237679_2_, RealmsMainScreen.field_243008_M, (float)(p_237679_3_ + 2), (float)(p_237679_4_ + 12), 13413468);
                  RealmsMainScreen.this.field_230712_o_.func_238421_b_(p_237679_2_, p_237679_1_.func_230778_c_(), (float)(p_237679_3_ + 2 + k), (float)(p_237679_4_ + 12), 7105644);
               } else {
                  RealmsMainScreen.this.field_230712_o_.func_238421_b_(p_237679_2_, p_237679_1_.func_230768_a_(), (float)(p_237679_3_ + 2), (float)(p_237679_4_ + 12), 7105644);
               }

               if (!RealmsMainScreen.this.func_223885_h(p_237679_1_)) {
                  RealmsMainScreen.this.field_230712_o_.func_238421_b_(p_237679_2_, p_237679_1_.field_230587_f_, (float)(p_237679_3_ + 2), (float)(p_237679_4_ + 12 + 11), 5000268);
               }
            }

            RealmsMainScreen.this.field_230712_o_.func_238421_b_(p_237679_2_, p_237679_1_.func_230775_b_(), (float)(p_237679_3_ + 2), (float)(p_237679_4_ + 1), 16777215);
            RealmsTextureManager.func_225205_a(p_237679_1_.field_230588_g_, () -> {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               AbstractGui.func_238466_a_(p_237679_2_, p_237679_3_ - 36, p_237679_4_, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
               AbstractGui.func_238466_a_(p_237679_2_, p_237679_3_ - 36, p_237679_4_, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
            });
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class ServerList extends RealmsObjectSelectionList<RealmsMainScreen.ListEntry> {
      private boolean field_241824_o_;

      public ServerList() {
         super(RealmsMainScreen.this.field_230708_k_, RealmsMainScreen.this.field_230709_l_, 32, RealmsMainScreen.this.field_230709_l_ - 40, 36);
      }

      public void func_231409_q_() {
         super.func_231409_q_();
         this.field_241824_o_ = false;
      }

      public int func_241825_a_(RealmsMainScreen.ListEntry p_241825_1_) {
         this.field_241824_o_ = true;
         return this.func_230513_b_(p_241825_1_);
      }

      public boolean func_230971_aw__() {
         return RealmsMainScreen.this.func_241217_q_() == this;
      }

      public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
         if (p_231046_1_ != 257 && p_231046_1_ != 32 && p_231046_1_ != 335) {
            return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
         } else {
            ExtendedList.AbstractListEntry extendedlist$abstractlistentry = this.func_230958_g_();
            return extendedlist$abstractlistentry == null ? super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_) : extendedlist$abstractlistentry.func_231044_a_(0.0D, 0.0D, 0);
         }
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         if (p_231044_5_ == 0 && p_231044_1_ < (double)this.func_230952_d_() && p_231044_3_ >= (double)this.field_230672_i_ && p_231044_3_ <= (double)this.field_230673_j_) {
            int i = RealmsMainScreen.this.field_224020_i.func_230968_n_();
            int j = this.func_230952_d_();
            int k = (int)Math.floor(p_231044_3_ - (double)this.field_230672_i_) - this.field_230677_n_ + (int)this.func_230966_l_() - 4;
            int l = k / this.field_230669_c_;
            if (p_231044_1_ >= (double)i && p_231044_1_ <= (double)j && l >= 0 && k >= 0 && l < this.func_230965_k_()) {
               this.func_231401_a_(k, l, p_231044_1_, p_231044_3_, this.field_230670_d_);
               RealmsMainScreen.this.field_224003_K = RealmsMainScreen.this.field_224003_K + 7;
               this.func_231400_a_(l);
            }

            return true;
         } else {
            return super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
         }
      }

      public void func_231400_a_(int p_231400_1_) {
         this.func_239561_k_(p_231400_1_);
         if (p_231400_1_ != -1) {
            RealmsServer realmsserver;
            if (this.field_241824_o_) {
               if (p_231400_1_ == 0) {
                  RealmsNarratorHelper.func_239551_a_(I18n.format("mco.trial.message.line1"), I18n.format("mco.trial.message.line2"));
                  realmsserver = null;
               } else {
                  if (p_231400_1_ - 1 >= RealmsMainScreen.this.field_224028_q.size()) {
                     RealmsMainScreen.this.field_224021_j = -1L;
                     return;
                  }

                  realmsserver = RealmsMainScreen.this.field_224028_q.get(p_231400_1_ - 1);
               }
            } else {
               if (p_231400_1_ >= RealmsMainScreen.this.field_224028_q.size()) {
                  RealmsMainScreen.this.field_224021_j = -1L;
                  return;
               }

               realmsserver = RealmsMainScreen.this.field_224028_q.get(p_231400_1_);
            }

            RealmsMainScreen.this.func_223915_a(realmsserver);
            if (realmsserver == null) {
               RealmsMainScreen.this.field_224021_j = -1L;
            } else if (realmsserver.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
               RealmsNarratorHelper.func_239550_a_(I18n.format("mco.selectServer.uninitialized") + I18n.format("mco.gui.button"));
               RealmsMainScreen.this.field_224021_j = -1L;
            } else {
               RealmsMainScreen.this.field_224021_j = realmsserver.field_230582_a_;
               if (RealmsMainScreen.this.field_224003_K >= 10 && RealmsMainScreen.this.field_224022_k.field_230693_o_) {
                  RealmsMainScreen.this.func_223911_a(RealmsMainScreen.this.func_223967_a(RealmsMainScreen.this.field_224021_j), RealmsMainScreen.this);
               }

               RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", realmsserver.field_230584_c_));
            }
         }
      }

      public void func_241215_a_(@Nullable RealmsMainScreen.ListEntry p_241215_1_) {
         super.func_241215_a_(p_241215_1_);
         int i = this.func_231039_at__().indexOf(p_241215_1_);
         if (!this.field_241824_o_ || i > 0) {
            RealmsServer realmsserver = RealmsMainScreen.this.field_224028_q.get(i - (this.field_241824_o_ ? 1 : 0));
            RealmsMainScreen.this.field_224021_j = realmsserver.field_230582_a_;
            RealmsMainScreen.this.func_223915_a(realmsserver);
         }

      }

      public void func_231401_a_(int p_231401_1_, int p_231401_2_, double p_231401_3_, double p_231401_5_, int p_231401_7_) {
         if (this.field_241824_o_) {
            if (p_231401_2_ == 0) {
               RealmsMainScreen.this.field_224035_x = true;
               return;
            }

            --p_231401_2_;
         }

         if (p_231401_2_ < RealmsMainScreen.this.field_224028_q.size()) {
            RealmsServer realmsserver = RealmsMainScreen.this.field_224028_q.get(p_231401_2_);
            if (realmsserver != null) {
               if (realmsserver.field_230586_e_ == RealmsServer.Status.UNINITIALIZED) {
                  RealmsMainScreen.this.field_224021_j = -1L;
                  Minecraft.getInstance().displayGuiScreen(new RealmsCreateRealmScreen(realmsserver, RealmsMainScreen.this));
               } else {
                  RealmsMainScreen.this.field_224021_j = realmsserver.field_230582_a_;
               }

               if (RealmsMainScreen.this.field_237539_ap_ == RealmsMainScreen.ServerState.CONFIGURE) {
                  RealmsMainScreen.this.field_224021_j = realmsserver.field_230582_a_;
                  RealmsMainScreen.this.func_223966_f(realmsserver);
               } else if (RealmsMainScreen.this.field_237539_ap_ == RealmsMainScreen.ServerState.LEAVE) {
                  RealmsMainScreen.this.field_224021_j = realmsserver.field_230582_a_;
                  RealmsMainScreen.this.func_223906_g(realmsserver);
               } else if (RealmsMainScreen.this.field_237539_ap_ == RealmsMainScreen.ServerState.EXPIRED) {
                  RealmsMainScreen.this.func_223930_q();
               }

            }
         }
      }

      public int func_230945_b_() {
         return this.func_230965_k_() * 36;
      }

      public int func_230949_c_() {
         return 300;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum ServerState {
      NONE,
      EXPIRED,
      LEAVE,
      CONFIGURE;
   }

   @OnlyIn(Dist.CLIENT)
   class TrialServerEntry extends RealmsMainScreen.ListEntry {
      private TrialServerEntry() {
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         this.func_237681_a_(p_230432_1_, p_230432_2_, p_230432_4_, p_230432_3_, p_230432_7_, p_230432_8_);
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         RealmsMainScreen.this.field_224035_x = true;
         return true;
      }

      private void func_237681_a_(MatrixStack p_237681_1_, int p_237681_2_, int p_237681_3_, int p_237681_4_, int p_237681_5_, int p_237681_6_) {
         int i = p_237681_4_ + 8;
         int j = 0;
         boolean flag = false;
         if (p_237681_3_ <= p_237681_5_ && p_237681_5_ <= (int)RealmsMainScreen.this.field_224020_i.func_230966_l_() && p_237681_4_ <= p_237681_6_ && p_237681_6_ <= p_237681_4_ + 32) {
            flag = true;
         }

         int k = 8388479;
         if (flag && !RealmsMainScreen.this.func_223990_b()) {
            k = 6077788;
         }

         for(ITextComponent itextcomponent : RealmsMainScreen.field_243002_G) {
            AbstractGui.func_238472_a_(p_237681_1_, RealmsMainScreen.this.field_230712_o_, itextcomponent, RealmsMainScreen.this.field_230708_k_ / 2, i + j, k);
            j += 10;
         }

      }
   }
}