package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorldSelectionScreen extends Screen {
   protected final Screen prevScreen;
   private List<IReorderingProcessor> worldVersTooltip;
   private Button deleteButton;
   private Button selectButton;
   private Button renameButton;
   private Button copyButton;
   protected TextFieldWidget field_212352_g;
   private WorldSelectionList selectionList;

   public WorldSelectionScreen(Screen screenIn) {
      super(new TranslationTextComponent("selectWorld.title"));
      this.prevScreen = screenIn;
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      return super.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
   }

   public void func_231023_e_() {
      this.field_212352_g.tick();
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.field_212352_g = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 100, 22, 200, 20, this.field_212352_g, new TranslationTextComponent("selectWorld.search"));
      this.field_212352_g.setResponder((p_214329_1_) -> {
         this.selectionList.func_212330_a(() -> {
            return p_214329_1_;
         }, false);
      });
      this.selectionList = new WorldSelectionList(this, this.field_230706_i_, this.field_230708_k_, this.field_230709_l_, 48, this.field_230709_l_ - 64, 36, () -> {
         return this.field_212352_g.getText();
      }, this.selectionList);
      this.field_230705_e_.add(this.field_212352_g);
      this.field_230705_e_.add(this.selectionList);
      this.selectButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 154, this.field_230709_l_ - 52, 150, 20, new TranslationTextComponent("selectWorld.select"), (p_214325_1_) -> {
         this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214438_a);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ - 52, 150, 20, new TranslationTextComponent("selectWorld.create"), (p_214326_1_) -> {
         this.field_230706_i_.displayGuiScreen(CreateWorldScreen.func_243425_a(this));
      }));
      this.renameButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 154, this.field_230709_l_ - 28, 72, 20, new TranslationTextComponent("selectWorld.edit"), (p_214323_1_) -> {
         this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214444_c);
      }));
      this.deleteButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 76, this.field_230709_l_ - 28, 72, 20, new TranslationTextComponent("selectWorld.delete"), (p_214330_1_) -> {
         this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214442_b);
      }));
      this.copyButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ - 28, 72, 20, new TranslationTextComponent("selectWorld.recreate"), (p_214328_1_) -> {
         this.selectionList.func_214376_a().ifPresent(WorldSelectionList.Entry::func_214445_d);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 82, this.field_230709_l_ - 28, 72, 20, DialogTexts.field_240633_d_, (p_214327_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.prevScreen);
      }));
      this.func_214324_a(false);
      this.setFocusedDefault(this.field_212352_g);
   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      return super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_) ? true : this.field_212352_g.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
   }

   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen(this.prevScreen);
   }

   public boolean func_231042_a_(char p_231042_1_, int p_231042_2_) {
      return this.field_212352_g.func_231042_a_(p_231042_1_, p_231042_2_);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.worldVersTooltip = null;
      this.selectionList.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      this.field_212352_g.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 8, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.worldVersTooltip != null) {
         this.func_238654_b_(p_230430_1_, this.worldVersTooltip, p_230430_2_, p_230430_3_);
      }

   }

   public void func_239026_b_(List<IReorderingProcessor> p_239026_1_) {
      this.worldVersTooltip = p_239026_1_;
   }

   public void func_214324_a(boolean p_214324_1_) {
      this.selectButton.field_230693_o_ = p_214324_1_;
      this.deleteButton.field_230693_o_ = p_214324_1_;
      this.renameButton.field_230693_o_ = p_214324_1_;
      this.copyButton.field_230693_o_ = p_214324_1_;
   }

   public void func_231164_f_() {
      if (this.selectionList != null) {
         this.selectionList.func_231039_at__().forEach(WorldSelectionList.Entry::close);
      }

   }
}