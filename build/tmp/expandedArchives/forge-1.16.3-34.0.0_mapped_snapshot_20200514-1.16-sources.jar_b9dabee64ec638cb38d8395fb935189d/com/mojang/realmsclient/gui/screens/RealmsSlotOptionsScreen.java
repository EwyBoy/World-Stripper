package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsSlotOptionsScreen extends RealmsScreen {
   public static final ITextComponent[] field_238035_a_ = new ITextComponent[]{new TranslationTextComponent("options.difficulty.peaceful"), new TranslationTextComponent("options.difficulty.easy"), new TranslationTextComponent("options.difficulty.normal"), new TranslationTextComponent("options.difficulty.hard")};
   public static final ITextComponent[] field_238036_b_ = new ITextComponent[]{new TranslationTextComponent("selectWorld.gameMode.survival"), new TranslationTextComponent("selectWorld.gameMode.creative"), new TranslationTextComponent("selectWorld.gameMode.adventure")};
   private static final ITextComponent field_238037_p_ = new TranslationTextComponent("mco.configure.world.on");
   private static final ITextComponent field_238038_q_ = new TranslationTextComponent("mco.configure.world.off");
   private static final ITextComponent field_243171_r = new TranslationTextComponent("selectWorld.gameMode");
   private static final ITextComponent field_243172_s = new TranslationTextComponent("mco.configure.world.edit.slot.name");
   private TextFieldWidget field_224642_e;
   protected final RealmsConfigureWorldScreen field_224638_a;
   private int field_224643_f;
   private int field_224644_g;
   private int field_224645_h;
   private final RealmsWorldOptions field_224646_i;
   private final RealmsServer.ServerType field_224647_j;
   private final int field_224648_k;
   private int field_224649_l;
   private int field_224650_m;
   private Boolean field_224651_n;
   private Boolean field_224652_o;
   private Boolean field_224653_p;
   private Boolean field_224654_q;
   private Integer field_224655_r;
   private Boolean field_224656_s;
   private Boolean field_224657_t;
   private Button field_224658_u;
   private Button field_224659_v;
   private Button field_224660_w;
   private Button field_224661_x;
   private RealmsSlotOptionsScreen.SettingsSlider field_224662_y;
   private Button field_224663_z;
   private Button field_224635_A;
   private RealmsLabel field_224636_B;
   private RealmsLabel field_224637_C;

   public RealmsSlotOptionsScreen(RealmsConfigureWorldScreen p_i51750_1_, RealmsWorldOptions p_i51750_2_, RealmsServer.ServerType p_i51750_3_, int p_i51750_4_) {
      this.field_224638_a = p_i51750_1_;
      this.field_224646_i = p_i51750_2_;
      this.field_224647_j = p_i51750_3_;
      this.field_224648_k = p_i51750_4_;
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public void func_231023_e_() {
      this.field_224642_e.tick();
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.field_230706_i_.displayGuiScreen(this.field_224638_a);
         return true;
      } else {
         return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void func_231160_c_() {
      this.field_224644_g = 170;
      this.field_224643_f = this.field_230708_k_ / 2 - this.field_224644_g;
      this.field_224645_h = this.field_230708_k_ / 2 + 10;
      this.field_224649_l = this.field_224646_i.field_230621_h_;
      this.field_224650_m = this.field_224646_i.field_230622_i_;
      if (this.field_224647_j == RealmsServer.ServerType.NORMAL) {
         this.field_224651_n = this.field_224646_i.field_230614_a_;
         this.field_224655_r = this.field_224646_i.field_230618_e_;
         this.field_224657_t = this.field_224646_i.field_230620_g_;
         this.field_224653_p = this.field_224646_i.field_230615_b_;
         this.field_224654_q = this.field_224646_i.field_230616_c_;
         this.field_224652_o = this.field_224646_i.field_230617_d_;
         this.field_224656_s = this.field_224646_i.field_230619_f_;
      } else {
         ITextComponent itextcomponent;
         if (this.field_224647_j == RealmsServer.ServerType.ADVENTUREMAP) {
            itextcomponent = new TranslationTextComponent("mco.configure.world.edit.subscreen.adventuremap");
         } else if (this.field_224647_j == RealmsServer.ServerType.INSPIRATION) {
            itextcomponent = new TranslationTextComponent("mco.configure.world.edit.subscreen.inspiration");
         } else {
            itextcomponent = new TranslationTextComponent("mco.configure.world.edit.subscreen.experience");
         }

         this.field_224637_C = new RealmsLabel(itextcomponent, this.field_230708_k_ / 2, 26, 16711680);
         this.field_224651_n = true;
         this.field_224655_r = 0;
         this.field_224657_t = false;
         this.field_224653_p = true;
         this.field_224654_q = true;
         this.field_224652_o = true;
         this.field_224656_s = true;
      }

      this.field_224642_e = new TextFieldWidget(this.field_230706_i_.fontRenderer, this.field_224643_f + 2, func_239562_k_(1), this.field_224644_g - 4, 20, (TextFieldWidget)null, new TranslationTextComponent("mco.configure.world.edit.slot.name"));
      this.field_224642_e.setMaxStringLength(10);
      this.field_224642_e.setText(this.field_224646_i.func_230787_a_(this.field_224648_k));
      this.func_212932_b(this.field_224642_e);
      this.field_224658_u = this.func_230480_a_(new Button(this.field_224645_h, func_239562_k_(1), this.field_224644_g, 20, this.func_224618_d(), (p_238059_1_) -> {
         this.field_224651_n = !this.field_224651_n;
         p_238059_1_.func_238482_a_(this.func_224618_d());
      }));
      this.func_230480_a_(new Button(this.field_224643_f, func_239562_k_(3), this.field_224644_g, 20, this.func_224610_c(), (p_238057_1_) -> {
         this.field_224650_m = (this.field_224650_m + 1) % field_238036_b_.length;
         p_238057_1_.func_238482_a_(this.func_224610_c());
      }));
      this.field_224659_v = this.func_230480_a_(new Button(this.field_224645_h, func_239562_k_(3), this.field_224644_g, 20, this.func_224606_e(), (p_238056_1_) -> {
         this.field_224653_p = !this.field_224653_p;
         p_238056_1_.func_238482_a_(this.func_224606_e());
      }));
      this.func_230480_a_(new Button(this.field_224643_f, func_239562_k_(5), this.field_224644_g, 20, this.func_224625_b(), (p_238055_1_) -> {
         this.field_224649_l = (this.field_224649_l + 1) % field_238035_a_.length;
         p_238055_1_.func_238482_a_(this.func_224625_b());
         if (this.field_224647_j == RealmsServer.ServerType.NORMAL) {
            this.field_224660_w.field_230693_o_ = this.field_224649_l != 0;
            this.field_224660_w.func_238482_a_(this.func_224626_f());
         }

      }));
      this.field_224660_w = this.func_230480_a_(new Button(this.field_224645_h, func_239562_k_(5), this.field_224644_g, 20, this.func_224626_f(), (p_238053_1_) -> {
         this.field_224654_q = !this.field_224654_q;
         p_238053_1_.func_238482_a_(this.func_224626_f());
      }));
      this.field_224662_y = this.func_230480_a_(new RealmsSlotOptionsScreen.SettingsSlider(this.field_224643_f, func_239562_k_(7), this.field_224644_g, this.field_224655_r, 0.0F, 16.0F));
      this.field_224661_x = this.func_230480_a_(new Button(this.field_224645_h, func_239562_k_(7), this.field_224644_g, 20, this.func_224621_g(), (p_238052_1_) -> {
         this.field_224652_o = !this.field_224652_o;
         p_238052_1_.func_238482_a_(this.func_224621_g());
      }));
      this.field_224635_A = this.func_230480_a_(new Button(this.field_224643_f, func_239562_k_(9), this.field_224644_g, 20, this.func_224634_i(), (p_238051_1_) -> {
         this.field_224657_t = !this.field_224657_t;
         p_238051_1_.func_238482_a_(this.func_224634_i());
      }));
      this.field_224663_z = this.func_230480_a_(new Button(this.field_224645_h, func_239562_k_(9), this.field_224644_g, 20, this.func_224594_h(), (p_238049_1_) -> {
         this.field_224656_s = !this.field_224656_s;
         p_238049_1_.func_238482_a_(this.func_224594_h());
      }));
      if (this.field_224647_j != RealmsServer.ServerType.NORMAL) {
         this.field_224658_u.field_230693_o_ = false;
         this.field_224659_v.field_230693_o_ = false;
         this.field_224661_x.field_230693_o_ = false;
         this.field_224660_w.field_230693_o_ = false;
         this.field_224662_y.field_230693_o_ = false;
         this.field_224663_z.field_230693_o_ = false;
         this.field_224635_A.field_230693_o_ = false;
      }

      if (this.field_224649_l == 0) {
         this.field_224660_w.field_230693_o_ = false;
      }

      this.func_230480_a_(new Button(this.field_224643_f, func_239562_k_(13), this.field_224644_g, 20, new TranslationTextComponent("mco.configure.world.buttons.done"), (p_238048_1_) -> {
         this.func_224613_k();
      }));
      this.func_230480_a_(new Button(this.field_224645_h, func_239562_k_(13), this.field_224644_g, 20, DialogTexts.field_240633_d_, (p_238046_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.field_224638_a);
      }));
      this.func_230481_d_(this.field_224642_e);
      this.field_224636_B = this.func_230481_d_(new RealmsLabel(new TranslationTextComponent("mco.configure.world.buttons.options"), this.field_230708_k_ / 2, 17, 16777215));
      if (this.field_224637_C != null) {
         this.func_230481_d_(this.field_224637_C);
      }

      this.func_231411_u_();
   }

   private ITextComponent func_224625_b() {
      return (new TranslationTextComponent("options.difficulty")).func_240702_b_(": ").func_230529_a_(field_238035_a_[this.field_224649_l]);
   }

   private ITextComponent func_224610_c() {
      return new TranslationTextComponent("options.generic_value", field_243171_r, field_238036_b_[this.field_224650_m]);
   }

   private ITextComponent func_224618_d() {
      return (new TranslationTextComponent("mco.configure.world.pvp")).func_240702_b_(": ").func_230529_a_(func_238050_c_(this.field_224651_n));
   }

   private ITextComponent func_224606_e() {
      return (new TranslationTextComponent("mco.configure.world.spawnAnimals")).func_240702_b_(": ").func_230529_a_(func_238050_c_(this.field_224653_p));
   }

   private ITextComponent func_224626_f() {
      return this.field_224649_l == 0 ? (new TranslationTextComponent("mco.configure.world.spawnMonsters")).func_240702_b_(": ").func_230529_a_(new TranslationTextComponent("mco.configure.world.off")) : (new TranslationTextComponent("mco.configure.world.spawnMonsters")).func_240702_b_(": ").func_230529_a_(func_238050_c_(this.field_224654_q));
   }

   private ITextComponent func_224621_g() {
      return (new TranslationTextComponent("mco.configure.world.spawnNPCs")).func_240702_b_(": ").func_230529_a_(func_238050_c_(this.field_224652_o));
   }

   private ITextComponent func_224594_h() {
      return (new TranslationTextComponent("mco.configure.world.commandBlocks")).func_240702_b_(": ").func_230529_a_(func_238050_c_(this.field_224656_s));
   }

   private ITextComponent func_224634_i() {
      return (new TranslationTextComponent("mco.configure.world.forceGameMode")).func_240702_b_(": ").func_230529_a_(func_238050_c_(this.field_224657_t));
   }

   private static ITextComponent func_238050_c_(boolean p_238050_0_) {
      return p_238050_0_ ? field_238037_p_ : field_238038_q_;
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.field_230712_o_.func_243248_b(p_230430_1_, field_243172_s, (float)(this.field_224643_f + this.field_224644_g / 2 - this.field_230712_o_.func_238414_a_(field_243172_s) / 2), (float)(func_239562_k_(0) - 5), 16777215);
      this.field_224636_B.func_239560_a_(this, p_230430_1_);
      if (this.field_224637_C != null) {
         this.field_224637_C.func_239560_a_(this, p_230430_1_);
      }

      this.field_224642_e.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   private String func_224604_j() {
      return this.field_224642_e.getText().equals(this.field_224646_i.func_230790_b_(this.field_224648_k)) ? "" : this.field_224642_e.getText();
   }

   private void func_224613_k() {
      if (this.field_224647_j != RealmsServer.ServerType.ADVENTUREMAP && this.field_224647_j != RealmsServer.ServerType.EXPERIENCE && this.field_224647_j != RealmsServer.ServerType.INSPIRATION) {
         this.field_224638_a.func_224386_a(new RealmsWorldOptions(this.field_224651_n, this.field_224653_p, this.field_224654_q, this.field_224652_o, this.field_224655_r, this.field_224656_s, this.field_224649_l, this.field_224650_m, this.field_224657_t, this.func_224604_j()));
      } else {
         this.field_224638_a.func_224386_a(new RealmsWorldOptions(this.field_224646_i.field_230614_a_, this.field_224646_i.field_230615_b_, this.field_224646_i.field_230616_c_, this.field_224646_i.field_230617_d_, this.field_224646_i.field_230618_e_, this.field_224646_i.field_230619_f_, this.field_224649_l, this.field_224650_m, this.field_224646_i.field_230620_g_, this.func_224604_j()));
      }

   }

   @OnlyIn(Dist.CLIENT)
   class SettingsSlider extends AbstractSlider {
      private final double field_238066_c_;
      private final double field_238067_d_;

      public SettingsSlider(int p_i232222_2_, int p_i232222_3_, int p_i232222_4_, int p_i232222_5_, float p_i232222_6_, float p_i232222_7_) {
         super(p_i232222_2_, p_i232222_3_, p_i232222_4_, 20, StringTextComponent.field_240750_d_, 0.0D);
         this.field_238066_c_ = (double)p_i232222_6_;
         this.field_238067_d_ = (double)p_i232222_7_;
         this.field_230683_b_ = (double)((MathHelper.clamp((float)p_i232222_5_, p_i232222_6_, p_i232222_7_) - p_i232222_6_) / (p_i232222_7_ - p_i232222_6_));
         this.func_230979_b_();
      }

      public void func_230972_a_() {
         if (RealmsSlotOptionsScreen.this.field_224662_y.field_230693_o_) {
            RealmsSlotOptionsScreen.this.field_224655_r = (int)MathHelper.lerp(MathHelper.clamp(this.field_230683_b_, 0.0D, 1.0D), this.field_238066_c_, this.field_238067_d_);
         }
      }

      protected void func_230979_b_() {
         this.func_238482_a_((new TranslationTextComponent("mco.configure.world.spawnProtection")).func_240702_b_(": ").func_230529_a_((ITextComponent)(RealmsSlotOptionsScreen.this.field_224655_r == 0 ? new TranslationTextComponent("mco.configure.world.off") : new StringTextComponent(String.valueOf((Object)RealmsSlotOptionsScreen.this.field_224655_r)))));
      }

      public void func_230982_a_(double p_230982_1_, double p_230982_3_) {
      }

      public void func_231000_a__(double p_231000_1_, double p_231000_3_) {
      }
   }
}