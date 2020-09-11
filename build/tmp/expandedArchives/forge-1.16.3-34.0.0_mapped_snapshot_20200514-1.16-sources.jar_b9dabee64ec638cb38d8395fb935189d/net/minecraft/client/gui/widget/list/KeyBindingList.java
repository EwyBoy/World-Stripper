package net.minecraft.client.gui.widget.list;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;

@OnlyIn(Dist.CLIENT)
public class KeyBindingList extends AbstractOptionList<KeyBindingList.Entry> {
   private final ControlsScreen controlsScreen;
   private int maxListLabelWidth;

   public KeyBindingList(ControlsScreen controls, Minecraft mcIn) {
      super(mcIn, controls.field_230708_k_ + 45, controls.field_230709_l_, 43, controls.field_230709_l_ - 32, 20);
      this.controlsScreen = controls;
      KeyBinding[] akeybinding = ArrayUtils.clone(mcIn.gameSettings.keyBindings);
      Arrays.sort((Object[])akeybinding);
      String s = null;

      for(KeyBinding keybinding : akeybinding) {
         String s1 = keybinding.getKeyCategory();
         if (!s1.equals(s)) {
            s = s1;
            this.func_230513_b_(new KeyBindingList.CategoryEntry(new TranslationTextComponent(s1)));
         }

         ITextComponent itextcomponent = new TranslationTextComponent(keybinding.getKeyDescription());
         int i = mcIn.fontRenderer.func_238414_a_(itextcomponent);
         if (i > this.maxListLabelWidth) {
            this.maxListLabelWidth = i;
         }

         this.func_230513_b_(new KeyBindingList.KeyEntry(keybinding, itextcomponent));
      }

   }

   protected int func_230952_d_() {
      return super.func_230952_d_() + 15 + 20;
   }

   public int func_230949_c_() {
      return super.func_230949_c_() + 32;
   }

   @OnlyIn(Dist.CLIENT)
   public class CategoryEntry extends KeyBindingList.Entry {
      private final ITextComponent labelText;
      private final int labelWidth;

      public CategoryEntry(ITextComponent p_i232280_2_) {
         this.labelText = p_i232280_2_;
         this.labelWidth = KeyBindingList.this.field_230668_b_.fontRenderer.func_238414_a_(this.labelText);
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         KeyBindingList.this.field_230668_b_.fontRenderer.func_243248_b(p_230432_1_, this.labelText, (float)(KeyBindingList.this.field_230668_b_.currentScreen.field_230708_k_ / 2 - this.labelWidth / 2), (float)(p_230432_3_ + p_230432_6_ - 9 - 1), 16777215);
      }

      public boolean func_231049_c__(boolean p_231049_1_) {
         return false;
      }

      public List<? extends IGuiEventListener> func_231039_at__() {
         return Collections.emptyList();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry extends AbstractOptionList.Entry<KeyBindingList.Entry> {
   }

   @OnlyIn(Dist.CLIENT)
   public class KeyEntry extends KeyBindingList.Entry {
      /** The keybinding specified for this KeyEntry */
      private final KeyBinding keybinding;
      /** The localized key description for this KeyEntry */
      private final ITextComponent keyDesc;
      private final Button btnChangeKeyBinding;
      private final Button btnReset;

      private KeyEntry(final KeyBinding p_i232281_2_, final ITextComponent p_i232281_3_) {
         this.keybinding = p_i232281_2_;
         this.keyDesc = p_i232281_3_;
         this.btnChangeKeyBinding = new Button(0, 0, 75 + 20 /*Forge: add space*/, 20, p_i232281_3_, (p_214386_2_) -> {
            KeyBindingList.this.controlsScreen.buttonId = p_i232281_2_;
         }) {
            protected IFormattableTextComponent func_230442_c_() {
               return p_i232281_2_.isInvalid() ? new TranslationTextComponent("narrator.controls.unbound", p_i232281_3_) : new TranslationTextComponent("narrator.controls.bound", p_i232281_3_, super.func_230442_c_());
            }
         };
         this.btnReset = new Button(0, 0, 50, 20, new TranslationTextComponent("controls.reset"), (p_214387_2_) -> {
            keybinding.setToDefault();
            KeyBindingList.this.field_230668_b_.gameSettings.setKeyBindingCode(p_i232281_2_, p_i232281_2_.getDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
         }) {
            protected IFormattableTextComponent func_230442_c_() {
               return new TranslationTextComponent("narrator.controls.reset", p_i232281_3_);
            }
         };
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         boolean flag = KeyBindingList.this.controlsScreen.buttonId == this.keybinding;
         KeyBindingList.this.field_230668_b_.fontRenderer.func_243248_b(p_230432_1_, this.keyDesc, (float)(p_230432_4_ + 90 - KeyBindingList.this.maxListLabelWidth), (float)(p_230432_3_ + p_230432_6_ / 2 - 9 / 2), 16777215);
         this.btnReset.field_230690_l_ = p_230432_4_ + 190 + 20;
         this.btnReset.field_230691_m_ = p_230432_3_;
         this.btnReset.field_230693_o_ = !this.keybinding.isDefault();
         this.btnReset.func_230430_a_(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
         this.btnChangeKeyBinding.field_230690_l_ = p_230432_4_ + 105;
         this.btnChangeKeyBinding.field_230691_m_ = p_230432_3_;
         this.btnChangeKeyBinding.func_238482_a_(this.keybinding.func_238171_j_());
         boolean flag1 = false;
         boolean keyCodeModifierConflict = true; // less severe form of conflict, like SHIFT conflicting with SHIFT+G
         if (!this.keybinding.isInvalid()) {
            for(KeyBinding keybinding : KeyBindingList.this.field_230668_b_.gameSettings.keyBindings) {
               if (keybinding != this.keybinding && this.keybinding.conflicts(keybinding)) {
                  flag1 = true;
                  keyCodeModifierConflict &= keybinding.hasKeyCodeModifierConflict(keybinding);
               }
            }
         }

         if (flag) {
            this.btnChangeKeyBinding.func_238482_a_((new StringTextComponent("> ")).func_230529_a_(this.btnChangeKeyBinding.func_230458_i_().func_230532_e_().func_240699_a_(TextFormatting.YELLOW)).func_240702_b_(" <").func_240699_a_(TextFormatting.YELLOW));
         } else if (flag1) {
            this.btnChangeKeyBinding.func_238482_a_(this.btnChangeKeyBinding.func_230458_i_().func_230532_e_().func_240699_a_(keyCodeModifierConflict ? TextFormatting.GOLD : TextFormatting.RED));
         }

         this.btnChangeKeyBinding.func_230430_a_(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
      }

      public List<? extends IGuiEventListener> func_231039_at__() {
         return ImmutableList.of(this.btnChangeKeyBinding, this.btnReset);
      }

      public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
         if (this.btnChangeKeyBinding.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
            return true;
         } else {
            return this.btnReset.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
         }
      }

      public boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
         return this.btnChangeKeyBinding.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_) || this.btnReset.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_);
      }
   }
}