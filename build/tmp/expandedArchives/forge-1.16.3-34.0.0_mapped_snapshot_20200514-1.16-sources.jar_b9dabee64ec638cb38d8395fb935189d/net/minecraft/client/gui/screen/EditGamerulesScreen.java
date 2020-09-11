package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EditGamerulesScreen extends Screen {
   private final Consumer<Optional<GameRules>> field_238965_a_;
   private EditGamerulesScreen.GamerulesList field_238966_b_;
   private final Set<EditGamerulesScreen.Gamerule> field_238967_c_ = Sets.newHashSet();
   private Button field_238968_p_;
   @Nullable
   private List<IReorderingProcessor> field_238969_q_;
   private final GameRules field_238970_r_;

   public EditGamerulesScreen(GameRules p_i232310_1_, Consumer<Optional<GameRules>> p_i232310_2_) {
      super(new TranslationTextComponent("editGamerule.title"));
      this.field_238970_r_ = p_i232310_1_;
      this.field_238965_a_ = p_i232310_2_;
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      super.func_231160_c_();
      this.field_238966_b_ = new EditGamerulesScreen.GamerulesList(this.field_238970_r_);
      this.field_230705_e_.add(this.field_238966_b_);
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155 + 160, this.field_230709_l_ - 29, 150, 20, DialogTexts.field_240633_d_, (p_238976_1_) -> {
         this.field_238965_a_.accept(Optional.empty());
      }));
      this.field_238968_p_ = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ - 29, 150, 20, DialogTexts.field_240632_c_, (p_238971_1_) -> {
         this.field_238965_a_.accept(Optional.of(this.field_238970_r_));
      }));
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public void func_231175_as__() {
      this.field_238965_a_.accept(Optional.empty());
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.field_238969_q_ = null;
      this.field_238966_b_.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.field_238969_q_ != null) {
         this.func_238654_b_(p_230430_1_, this.field_238969_q_, p_230430_2_, p_230430_3_);
      }

   }

   private void func_238980_b_(@Nullable List<IReorderingProcessor> p_238980_1_) {
      this.field_238969_q_ = p_238980_1_;
   }

   private void func_238984_g_() {
      this.field_238968_p_.field_230693_o_ = this.field_238967_c_.isEmpty();
   }

   private void func_238972_a_(EditGamerulesScreen.Gamerule p_238972_1_) {
      this.field_238967_c_.add(p_238972_1_);
      this.func_238984_g_();
   }

   private void func_238977_b_(EditGamerulesScreen.Gamerule p_238977_1_) {
      this.field_238967_c_.remove(p_238977_1_);
      this.func_238984_g_();
   }

   @OnlyIn(Dist.CLIENT)
   public class BooleanEntry extends EditGamerulesScreen.ValueEntry {
      private final Button field_238986_c_;

      public BooleanEntry(final ITextComponent p_i232311_2_, List<IReorderingProcessor> p_i232311_3_, final String p_i232311_4_, final GameRules.BooleanValue p_i232311_5_) {
         super(p_i232311_3_, p_i232311_2_);
         this.field_238986_c_ = new Button(10, 5, 44, 20, DialogTexts.func_240638_a_(p_i232311_5_.get()), (p_238988_1_) -> {
            boolean flag = !p_i232311_5_.get();
            p_i232311_5_.set(flag, (MinecraftServer)null);
            p_238988_1_.func_238482_a_(DialogTexts.func_240638_a_(p_i232311_5_.get()));
         }) {
            protected IFormattableTextComponent func_230442_c_() {
               return DialogTexts.func_244281_a(p_i232311_2_, p_i232311_5_.get()).func_240702_b_("\n").func_240702_b_(p_i232311_4_);
            }
         };
         this.field_241647_b_.add(this.field_238986_c_);
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         this.func_241649_a_(p_230432_1_, p_230432_3_, p_230432_4_);
         this.field_238986_c_.field_230690_l_ = p_230432_4_ + p_230432_5_ - 45;
         this.field_238986_c_.field_230691_m_ = p_230432_3_;
         this.field_238986_c_.func_230430_a_(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public abstract class Gamerule extends AbstractOptionList.Entry<EditGamerulesScreen.Gamerule> {
      @Nullable
      private final List<IReorderingProcessor> field_239000_a_;

      public Gamerule(@Nullable List<IReorderingProcessor> p_i232315_2_) {
         this.field_239000_a_ = p_i232315_2_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public class GamerulesList extends AbstractOptionList<EditGamerulesScreen.Gamerule> {
      public GamerulesList(final GameRules p_i232316_2_) {
         super(EditGamerulesScreen.this.field_230706_i_, EditGamerulesScreen.this.field_230708_k_, EditGamerulesScreen.this.field_230709_l_, 43, EditGamerulesScreen.this.field_230709_l_ - 32, 24);
         final Map<GameRules.Category, Map<GameRules.RuleKey<?>, EditGamerulesScreen.Gamerule>> map = Maps.newHashMap();
         GameRules.visitAll(new GameRules.IRuleEntryVisitor() {
            public void func_230482_b_(GameRules.RuleKey<GameRules.BooleanValue> p_230482_1_, GameRules.RuleType<GameRules.BooleanValue> p_230482_2_) {
               this.func_239011_a_(p_230482_1_, (p_239012_1_, p_239012_2_, p_239012_3_, p_239012_4_) -> {
                  return EditGamerulesScreen.this.new BooleanEntry(p_239012_1_, p_239012_2_, p_239012_3_, p_239012_4_);
               });
            }

            public void func_230483_c_(GameRules.RuleKey<GameRules.IntegerValue> p_230483_1_, GameRules.RuleType<GameRules.IntegerValue> p_230483_2_) {
               this.func_239011_a_(p_230483_1_, (p_239013_1_, p_239013_2_, p_239013_3_, p_239013_4_) -> {
                  return EditGamerulesScreen.this.new IntegerEntry(p_239013_1_, p_239013_2_, p_239013_3_, p_239013_4_);
               });
            }

            private <T extends GameRules.RuleValue<T>> void func_239011_a_(GameRules.RuleKey<T> p_239011_1_, EditGamerulesScreen.IRuleEntry<T> p_239011_2_) {
               ITextComponent itextcomponent = new TranslationTextComponent(p_239011_1_.func_234911_b_());
               ITextComponent itextcomponent1 = (new StringTextComponent(p_239011_1_.getName())).func_240699_a_(TextFormatting.YELLOW);
               T t = p_i232316_2_.get(p_239011_1_);
               String s = t.stringValue();
               ITextComponent itextcomponent2 = (new TranslationTextComponent("editGamerule.default", new StringTextComponent(s))).func_240699_a_(TextFormatting.GRAY);
               String s1 = p_239011_1_.func_234911_b_() + ".description";
               List<IReorderingProcessor> list;
               String s2;
               if (I18n.hasKey(s1)) {
                  Builder<IReorderingProcessor> builder = ImmutableList.<IReorderingProcessor>builder().add(itextcomponent1.func_241878_f());
                  ITextComponent itextcomponent3 = new TranslationTextComponent(s1);
                  EditGamerulesScreen.this.field_230712_o_.func_238425_b_(itextcomponent3, 150).forEach(builder::add);
                  list = builder.add(itextcomponent2.func_241878_f()).build();
                  s2 = itextcomponent3.getString() + "\n" + itextcomponent2.getString();
               } else {
                  list = ImmutableList.of(itextcomponent1.func_241878_f(), itextcomponent2.func_241878_f());
                  s2 = itextcomponent2.getString();
               }

               map.computeIfAbsent(p_239011_1_.func_234912_c_(), (p_239010_0_) -> {
                  return Maps.newHashMap();
               }).put(p_239011_1_, p_239011_2_.create(itextcomponent, list, s2, t));
            }
         });
         map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((p_239004_1_) -> {
            this.func_230513_b_(EditGamerulesScreen.this.new NameEntry((new TranslationTextComponent(p_239004_1_.getKey().func_234908_a_())).func_240701_a_(new TextFormatting[]{TextFormatting.BOLD, TextFormatting.YELLOW})));
            p_239004_1_.getValue().entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(GameRules.RuleKey::getName))).forEach((p_239005_1_) -> {
               this.func_230513_b_(p_239005_1_.getValue());
            });
         });
      }

      public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
         super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         if (this.func_231047_b_((double)p_230430_2_, (double)p_230430_3_)) {
            EditGamerulesScreen.Gamerule editgamerulesscreen$gamerule = this.func_230933_a_((double)p_230430_2_, (double)p_230430_3_);
            if (editgamerulesscreen$gamerule != null) {
               EditGamerulesScreen.this.func_238980_b_(editgamerulesscreen$gamerule.field_239000_a_);
            }
         }

      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   interface IRuleEntry<T extends GameRules.RuleValue<T>> {
      EditGamerulesScreen.Gamerule create(ITextComponent p_create_1_, List<IReorderingProcessor> p_create_2_, String p_create_3_, T p_create_4_);
   }

   @OnlyIn(Dist.CLIENT)
   public class IntegerEntry extends EditGamerulesScreen.ValueEntry {
      private final TextFieldWidget field_238997_d_;

      public IntegerEntry(ITextComponent p_i232314_2_, List<IReorderingProcessor> p_i232314_3_, String p_i232314_4_, GameRules.IntegerValue p_i232314_5_) {
         super(p_i232314_3_, p_i232314_2_);
         this.field_238997_d_ = new TextFieldWidget(EditGamerulesScreen.this.field_230706_i_.fontRenderer, 10, 5, 42, 20, p_i232314_2_.func_230532_e_().func_240702_b_("\n").func_240702_b_(p_i232314_4_).func_240702_b_("\n"));
         this.field_238997_d_.setText(Integer.toString(p_i232314_5_.get()));
         this.field_238997_d_.setResponder((p_238999_2_) -> {
            if (p_i232314_5_.func_234909_b_(p_238999_2_)) {
               this.field_238997_d_.setTextColor(14737632);
               EditGamerulesScreen.this.func_238977_b_(this);
            } else {
               this.field_238997_d_.setTextColor(16711680);
               EditGamerulesScreen.this.func_238972_a_(this);
            }

         });
         this.field_241647_b_.add(this.field_238997_d_);
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         this.func_241649_a_(p_230432_1_, p_230432_3_, p_230432_4_);
         this.field_238997_d_.field_230690_l_ = p_230432_4_ + p_230432_5_ - 44;
         this.field_238997_d_.field_230691_m_ = p_230432_3_;
         this.field_238997_d_.func_230430_a_(p_230432_1_, p_230432_7_, p_230432_8_, p_230432_10_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public class NameEntry extends EditGamerulesScreen.Gamerule {
      private final ITextComponent field_238994_c_;

      public NameEntry(ITextComponent p_i232313_2_) {
         super((List<IReorderingProcessor>)null);
         this.field_238994_c_ = p_i232313_2_;
      }

      public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         AbstractGui.func_238472_a_(p_230432_1_, EditGamerulesScreen.this.field_230706_i_.fontRenderer, this.field_238994_c_, p_230432_4_ + p_230432_5_ / 2, p_230432_3_ + 5, 16777215);
      }

      public List<? extends IGuiEventListener> func_231039_at__() {
         return ImmutableList.of();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public abstract class ValueEntry extends EditGamerulesScreen.Gamerule {
      private final List<IReorderingProcessor> field_241646_a_;
      protected final List<IGuiEventListener> field_241647_b_ = Lists.newArrayList();

      public ValueEntry(@Nullable List<IReorderingProcessor> p_i241256_2_, ITextComponent p_i241256_3_) {
         super(p_i241256_2_);
         this.field_241646_a_ = EditGamerulesScreen.this.field_230706_i_.fontRenderer.func_238425_b_(p_i241256_3_, 175);
      }

      public List<? extends IGuiEventListener> func_231039_at__() {
         return this.field_241647_b_;
      }

      protected void func_241649_a_(MatrixStack p_241649_1_, int p_241649_2_, int p_241649_3_) {
         if (this.field_241646_a_.size() == 1) {
            EditGamerulesScreen.this.field_230706_i_.fontRenderer.func_238422_b_(p_241649_1_, this.field_241646_a_.get(0), (float)p_241649_3_, (float)(p_241649_2_ + 5), 16777215);
         } else if (this.field_241646_a_.size() >= 2) {
            EditGamerulesScreen.this.field_230706_i_.fontRenderer.func_238422_b_(p_241649_1_, this.field_241646_a_.get(0), (float)p_241649_3_, (float)p_241649_2_, 16777215);
            EditGamerulesScreen.this.field_230706_i_.fontRenderer.func_238422_b_(p_241649_1_, this.field_241646_a_.get(1), (float)p_241649_3_, (float)(p_241649_2_ + 10), 16777215);
         }

      }
   }
}