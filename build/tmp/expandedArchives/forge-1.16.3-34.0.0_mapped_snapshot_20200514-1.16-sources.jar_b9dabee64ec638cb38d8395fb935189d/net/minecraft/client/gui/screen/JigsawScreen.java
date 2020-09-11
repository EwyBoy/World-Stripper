package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.JigsawBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.play.client.CJigsawBlockGeneratePacket;
import net.minecraft.network.play.client.CUpdateJigsawBlockPacket;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JigsawScreen extends Screen {
   private static final ITextComponent field_243346_a = new TranslationTextComponent("jigsaw_block.joint_label");
   private static final ITextComponent field_243347_b = new TranslationTextComponent("jigsaw_block.pool");
   private static final ITextComponent field_243348_c = new TranslationTextComponent("jigsaw_block.name");
   private static final ITextComponent field_243349_p = new TranslationTextComponent("jigsaw_block.target");
   private static final ITextComponent field_243350_q = new TranslationTextComponent("jigsaw_block.final_state");
   private final JigsawTileEntity field_214259_a;
   private TextFieldWidget field_238818_b_;
   private TextFieldWidget field_238819_c_;
   private TextFieldWidget field_238820_p_;
   private TextFieldWidget field_214262_d;
   private int field_238821_r_;
   private boolean field_238822_s_ = true;
   private Button field_238823_t_;
   private Button field_214263_e;
   private JigsawTileEntity.OrientationType field_238824_v_;

   public JigsawScreen(JigsawTileEntity p_i51083_1_) {
      super(NarratorChatListener.EMPTY);
      this.field_214259_a = p_i51083_1_;
   }

   public void func_231023_e_() {
      this.field_238818_b_.tick();
      this.field_238819_c_.tick();
      this.field_238820_p_.tick();
      this.field_214262_d.tick();
   }

   private void func_214256_b() {
      this.func_214258_d();
      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   private void func_214257_c() {
      this.field_230706_i_.displayGuiScreen((Screen)null);
   }

   private void func_214258_d() {
      this.field_230706_i_.getConnection().sendPacket(new CUpdateJigsawBlockPacket(this.field_214259_a.getPos(), new ResourceLocation(this.field_238818_b_.getText()), new ResourceLocation(this.field_238819_c_.getText()), new ResourceLocation(this.field_238820_p_.getText()), this.field_214262_d.getText(), this.field_238824_v_));
   }

   private void func_238835_m_() {
      this.field_230706_i_.getConnection().sendPacket(new CJigsawBlockGeneratePacket(this.field_214259_a.getPos(), this.field_238821_r_, this.field_238822_s_));
   }

   public void func_231175_as__() {
      this.func_214257_c();
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_238820_p_ = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 152, 20, 300, 20, new TranslationTextComponent("jigsaw_block.pool"));
      this.field_238820_p_.setMaxStringLength(128);
      this.field_238820_p_.setText(this.field_214259_a.func_235670_g_().toString());
      this.field_238820_p_.setResponder((p_238833_1_) -> {
         this.func_214253_a();
      });
      this.field_230705_e_.add(this.field_238820_p_);
      this.field_238818_b_ = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 152, 55, 300, 20, new TranslationTextComponent("jigsaw_block.name"));
      this.field_238818_b_.setMaxStringLength(128);
      this.field_238818_b_.setText(this.field_214259_a.func_235668_d_().toString());
      this.field_238818_b_.setResponder((p_238830_1_) -> {
         this.func_214253_a();
      });
      this.field_230705_e_.add(this.field_238818_b_);
      this.field_238819_c_ = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 152, 90, 300, 20, new TranslationTextComponent("jigsaw_block.target"));
      this.field_238819_c_.setMaxStringLength(128);
      this.field_238819_c_.setText(this.field_214259_a.func_235669_f_().toString());
      this.field_238819_c_.setResponder((p_214254_1_) -> {
         this.func_214253_a();
      });
      this.field_230705_e_.add(this.field_238819_c_);
      this.field_214262_d = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 152, 125, 300, 20, new TranslationTextComponent("jigsaw_block.final_state"));
      this.field_214262_d.setMaxStringLength(256);
      this.field_214262_d.setText(this.field_214259_a.getFinalState());
      this.field_230705_e_.add(this.field_214262_d);
      this.field_238824_v_ = this.field_214259_a.func_235671_j_();
      int i = this.field_230712_o_.func_238414_a_(field_243346_a) + 10;
      this.field_238823_t_ = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 152 + i, 150, 300 - i, 20, this.func_238836_u_(), (p_238834_1_) -> {
         JigsawTileEntity.OrientationType[] ajigsawtileentity$orientationtype = JigsawTileEntity.OrientationType.values();
         int j = (this.field_238824_v_.ordinal() + 1) % ajigsawtileentity$orientationtype.length;
         this.field_238824_v_ = ajigsawtileentity$orientationtype[j];
         p_238834_1_.func_238482_a_(this.func_238836_u_());
      }));
      boolean flag = JigsawBlock.func_235508_h_(this.field_214259_a.getBlockState()).getAxis().isVertical();
      this.field_238823_t_.field_230693_o_ = flag;
      this.field_238823_t_.field_230694_p_ = flag;
      this.func_230480_a_(new AbstractSlider(this.field_230708_k_ / 2 - 154, 180, 100, 20, StringTextComponent.field_240750_d_, 0.0D) {
         {
            this.func_230979_b_();
         }

         protected void func_230979_b_() {
            this.func_238482_a_(new TranslationTextComponent("jigsaw_block.levels", JigsawScreen.this.field_238821_r_));
         }

         protected void func_230972_a_() {
            JigsawScreen.this.field_238821_r_ = MathHelper.floor(MathHelper.clampedLerp(0.0D, 7.0D, this.field_230683_b_));
         }
      });
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50, 180, 100, 20, new TranslationTextComponent("jigsaw_block.keep_jigsaws"), (p_238832_1_) -> {
         this.field_238822_s_ = !this.field_238822_s_;
         p_238832_1_.func_230994_c_(250);
      }) {
         public ITextComponent func_230458_i_() {
            return DialogTexts.func_244281_a(super.func_230458_i_(), JigsawScreen.this.field_238822_s_);
         }
      });
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 54, 180, 100, 20, new TranslationTextComponent("jigsaw_block.generate"), (p_238831_1_) -> {
         this.func_214256_b();
         this.func_238835_m_();
      }));
      this.field_214263_e = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 4 - 150, 210, 150, 20, DialogTexts.field_240632_c_, (p_238828_1_) -> {
         this.func_214256_b();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, 210, 150, 20, DialogTexts.field_240633_d_, (p_238825_1_) -> {
         this.func_214257_c();
      }));
      this.setFocusedDefault(this.field_238820_p_);
      this.func_214253_a();
   }

   private void func_214253_a() {
      this.field_214263_e.field_230693_o_ = ResourceLocation.isResouceNameValid(this.field_238818_b_.getText()) && ResourceLocation.isResouceNameValid(this.field_238819_c_.getText()) && ResourceLocation.isResouceNameValid(this.field_238820_p_.getText());
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      String s = this.field_238818_b_.getText();
      String s1 = this.field_238819_c_.getText();
      String s2 = this.field_238820_p_.getText();
      String s3 = this.field_214262_d.getText();
      int i = this.field_238821_r_;
      JigsawTileEntity.OrientationType jigsawtileentity$orientationtype = this.field_238824_v_;
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.field_238818_b_.setText(s);
      this.field_238819_c_.setText(s1);
      this.field_238820_p_.setText(s2);
      this.field_214262_d.setText(s3);
      this.field_238821_r_ = i;
      this.field_238824_v_ = jigsawtileentity$orientationtype;
      this.field_238823_t_.func_238482_a_(this.func_238836_u_());
   }

   private ITextComponent func_238836_u_() {
      return new TranslationTextComponent("jigsaw_block.joint." + this.field_238824_v_.func_176610_l());
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (!this.field_214263_e.field_230693_o_ || p_231046_1_ != 257 && p_231046_1_ != 335) {
         return false;
      } else {
         this.func_214256_b();
         return true;
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243347_b, this.field_230708_k_ / 2 - 153, 10, 10526880);
      this.field_238820_p_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243348_c, this.field_230708_k_ / 2 - 153, 45, 10526880);
      this.field_238818_b_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243349_p, this.field_230708_k_ / 2 - 153, 80, 10526880);
      this.field_238819_c_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238475_b_(p_230430_1_, this.field_230712_o_, field_243350_q, this.field_230708_k_ / 2 - 153, 115, 10526880);
      this.field_214262_d.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (JigsawBlock.func_235508_h_(this.field_214259_a.getBlockState()).getAxis().isVertical()) {
         func_238475_b_(p_230430_1_, this.field_230712_o_, field_243346_a, this.field_230708_k_ / 2 - 153, 156, 16777215);
      }

      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }
}