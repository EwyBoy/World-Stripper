package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StatsScreen extends Screen implements IProgressMeter {
   private static final ITextComponent field_243320_c = new TranslationTextComponent("multiplayer.downloadingStats");
   protected final Screen parentScreen;
   private StatsScreen.CustomStatsList generalStats;
   private StatsScreen.StatsList itemStats;
   private StatsScreen.MobStatsList mobStats;
   private final StatisticsManager stats;
   @Nullable
   private ExtendedList<?> displaySlot;
   /** When true, the game will be paused when the gui is shown */
   private boolean doesGuiPauseGame = true;

   public StatsScreen(Screen parent, StatisticsManager manager) {
      super(new TranslationTextComponent("gui.stats"));
      this.parentScreen = parent;
      this.stats = manager;
   }

   protected void func_231160_c_() {
      this.doesGuiPauseGame = true;
      this.field_230706_i_.getConnection().sendPacket(new CClientStatusPacket(CClientStatusPacket.State.REQUEST_STATS));
   }

   public void initLists() {
      this.generalStats = new StatsScreen.CustomStatsList(this.field_230706_i_);
      this.itemStats = new StatsScreen.StatsList(this.field_230706_i_);
      this.mobStats = new StatsScreen.MobStatsList(this.field_230706_i_);
   }

   public void initButtons() {
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 120, this.field_230709_l_ - 52, 80, 20, new TranslationTextComponent("stat.generalButton"), (p_213109_1_) -> {
         this.func_213110_a(this.generalStats);
      }));
      Button button = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 40, this.field_230709_l_ - 52, 80, 20, new TranslationTextComponent("stat.itemsButton"), (p_213115_1_) -> {
         this.func_213110_a(this.itemStats);
      }));
      Button button1 = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 40, this.field_230709_l_ - 52, 80, 20, new TranslationTextComponent("stat.mobsButton"), (p_213114_1_) -> {
         this.func_213110_a(this.mobStats);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ - 28, 200, 20, DialogTexts.field_240632_c_, (p_213113_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      if (this.itemStats.func_231039_at__().isEmpty()) {
         button.field_230693_o_ = false;
      }

      if (this.mobStats.func_231039_at__().isEmpty()) {
         button1.field_230693_o_ = false;
      }

   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.doesGuiPauseGame) {
         this.func_230446_a_(p_230430_1_);
         func_238472_a_(p_230430_1_, this.field_230712_o_, field_243320_c, this.field_230708_k_ / 2, this.field_230709_l_ / 2, 16777215);
         func_238471_a_(p_230430_1_, this.field_230712_o_, LOADING_STRINGS[(int)(Util.milliTime() / 150L % (long)LOADING_STRINGS.length)], this.field_230708_k_ / 2, this.field_230709_l_ / 2 + 9 * 2, 16777215);
      } else {
         this.func_213116_d().func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
         super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      }

   }

   public void onStatsUpdated() {
      if (this.doesGuiPauseGame) {
         this.initLists();
         this.initButtons();
         this.func_213110_a(this.generalStats);
         this.doesGuiPauseGame = false;
      }

   }

   public boolean func_231177_au__() {
      return !this.doesGuiPauseGame;
   }

   @Nullable
   public ExtendedList<?> func_213116_d() {
      return this.displaySlot;
   }

   public void func_213110_a(@Nullable ExtendedList<?> p_213110_1_) {
      this.field_230705_e_.remove(this.generalStats);
      this.field_230705_e_.remove(this.itemStats);
      this.field_230705_e_.remove(this.mobStats);
      if (p_213110_1_ != null) {
         this.field_230705_e_.add(0, p_213110_1_);
         this.displaySlot = p_213110_1_;
      }

   }

   private static String func_238672_b_(Stat<ResourceLocation> p_238672_0_) {
      return "stat." + p_238672_0_.getValue().toString().replace(':', '.');
   }

   private int func_195224_b(int p_195224_1_) {
      return 115 + 40 * p_195224_1_;
   }

   private void func_238667_a_(MatrixStack p_238667_1_, int p_238667_2_, int p_238667_3_, Item p_238667_4_) {
      this.func_238674_c_(p_238667_1_, p_238667_2_ + 1, p_238667_3_ + 1, 0, 0);
      RenderSystem.enableRescaleNormal();
      this.field_230707_j_.renderItemIntoGUI(p_238667_4_.getDefaultInstance(), p_238667_2_ + 2, p_238667_3_ + 2);
      RenderSystem.disableRescaleNormal();
   }

   private void func_238674_c_(MatrixStack p_238674_1_, int p_238674_2_, int p_238674_3_, int p_238674_4_, int p_238674_5_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(field_230664_g_);
      func_238464_a_(p_238674_1_, p_238674_2_, p_238674_3_, this.func_230927_p_(), (float)p_238674_4_, (float)p_238674_5_, 18, 18, 128, 128);
   }

   @OnlyIn(Dist.CLIENT)
   class CustomStatsList extends ExtendedList<StatsScreen.CustomStatsList.Entry> {
      public CustomStatsList(Minecraft mcIn) {
         super(mcIn, StatsScreen.this.field_230708_k_, StatsScreen.this.field_230709_l_, 32, StatsScreen.this.field_230709_l_ - 64, 10);
         ObjectArrayList<Stat<ResourceLocation>> objectarraylist = new ObjectArrayList<>(Stats.CUSTOM.iterator());
         objectarraylist.sort(java.util.Comparator.comparing((p_238679_0_) -> {
            return I18n.format(StatsScreen.func_238672_b_(p_238679_0_));
         }));

         for(Stat<ResourceLocation> stat : objectarraylist) {
            this.func_230513_b_(new StatsScreen.CustomStatsList.Entry(stat));
         }

      }

      protected void func_230433_a_(MatrixStack p_230433_1_) {
         StatsScreen.this.func_230446_a_(p_230433_1_);
      }

      @OnlyIn(Dist.CLIENT)
      class Entry extends ExtendedList.AbstractListEntry<StatsScreen.CustomStatsList.Entry> {
         private final Stat<ResourceLocation> field_214405_b;
         private final ITextComponent field_243321_c;

         private Entry(Stat<ResourceLocation> p_i50466_2_) {
            this.field_214405_b = p_i50466_2_;
            this.field_243321_c = new TranslationTextComponent(StatsScreen.func_238672_b_(p_i50466_2_));
         }

         public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            AbstractGui.func_238475_b_(p_230432_1_, StatsScreen.this.field_230712_o_, this.field_243321_c, p_230432_4_ + 2, p_230432_3_ + 1, p_230432_2_ % 2 == 0 ? 16777215 : 9474192);
            String s = this.field_214405_b.format(StatsScreen.this.stats.getValue(this.field_214405_b));
            AbstractGui.func_238476_c_(p_230432_1_, StatsScreen.this.field_230712_o_, s, p_230432_4_ + 2 + 213 - StatsScreen.this.field_230712_o_.getStringWidth(s), p_230432_3_ + 1, p_230432_2_ % 2 == 0 ? 16777215 : 9474192);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class MobStatsList extends ExtendedList<StatsScreen.MobStatsList.Entry> {
      public MobStatsList(Minecraft mcIn) {
         super(mcIn, StatsScreen.this.field_230708_k_, StatsScreen.this.field_230709_l_, 32, StatsScreen.this.field_230709_l_ - 64, 9 * 4);

         for(EntityType<?> entitytype : Registry.ENTITY_TYPE) {
            if (StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get(entitytype)) > 0 || StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get(entitytype)) > 0) {
               this.func_230513_b_(new StatsScreen.MobStatsList.Entry(entitytype));
            }
         }

      }

      protected void func_230433_a_(MatrixStack p_230433_1_) {
         StatsScreen.this.func_230446_a_(p_230433_1_);
      }

      @OnlyIn(Dist.CLIENT)
      class Entry extends ExtendedList.AbstractListEntry<StatsScreen.MobStatsList.Entry> {
         private final EntityType<?> field_214411_b;
         private final ITextComponent field_243322_c;
         private final ITextComponent field_243323_d;
         private final boolean field_243324_e;
         private final ITextComponent field_243325_f;
         private final boolean field_243326_g;

         public Entry(EntityType<?> p_i50018_2_) {
            this.field_214411_b = p_i50018_2_;
            this.field_243322_c = p_i50018_2_.getName();
            int i = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get(p_i50018_2_));
            if (i == 0) {
               this.field_243323_d = new TranslationTextComponent("stat_type.minecraft.killed.none", this.field_243322_c);
               this.field_243324_e = false;
            } else {
               this.field_243323_d = new TranslationTextComponent("stat_type.minecraft.killed", i, this.field_243322_c);
               this.field_243324_e = true;
            }

            int j = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get(p_i50018_2_));
            if (j == 0) {
               this.field_243325_f = new TranslationTextComponent("stat_type.minecraft.killed_by.none", this.field_243322_c);
               this.field_243326_g = false;
            } else {
               this.field_243325_f = new TranslationTextComponent("stat_type.minecraft.killed_by", this.field_243322_c, j);
               this.field_243326_g = true;
            }

         }

         public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            AbstractGui.func_238475_b_(p_230432_1_, StatsScreen.this.field_230712_o_, this.field_243322_c, p_230432_4_ + 2, p_230432_3_ + 1, 16777215);
            AbstractGui.func_238475_b_(p_230432_1_, StatsScreen.this.field_230712_o_, this.field_243323_d, p_230432_4_ + 2 + 10, p_230432_3_ + 1 + 9, this.field_243324_e ? 9474192 : 6316128);
            AbstractGui.func_238475_b_(p_230432_1_, StatsScreen.this.field_230712_o_, this.field_243325_f, p_230432_4_ + 2 + 10, p_230432_3_ + 1 + 9 * 2, this.field_243326_g ? 9474192 : 6316128);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class StatsList extends ExtendedList<StatsScreen.StatsList.Entry> {
      protected final List<StatType<Block>> field_195113_v;
      protected final List<StatType<Item>> field_195114_w;
      private final int[] field_195112_D = new int[]{3, 4, 1, 2, 5, 6};
      protected int field_195115_x = -1;
      protected final List<Item> field_195116_y;
      protected final java.util.Comparator<Item> field_195117_z = new StatsScreen.StatsList.Comparator();
      @Nullable
      protected StatType<?> field_195110_A;
      protected int field_195111_B;

      public StatsList(Minecraft mcIn) {
         super(mcIn, StatsScreen.this.field_230708_k_, StatsScreen.this.field_230709_l_, 32, StatsScreen.this.field_230709_l_ - 64, 20);
         this.field_195113_v = Lists.newArrayList();
         this.field_195113_v.add(Stats.BLOCK_MINED);
         this.field_195114_w = Lists.newArrayList(Stats.ITEM_BROKEN, Stats.ITEM_CRAFTED, Stats.ITEM_USED, Stats.ITEM_PICKED_UP, Stats.ITEM_DROPPED);
         this.func_230944_a_(true, 20);
         Set<Item> set = Sets.newIdentityHashSet();

         for(Item item : Registry.ITEM) {
            boolean flag = false;

            for(StatType<Item> stattype : this.field_195114_w) {
               if (stattype.contains(item) && StatsScreen.this.stats.getValue(stattype.get(item)) > 0) {
                  flag = true;
               }
            }

            if (flag) {
               set.add(item);
            }
         }

         for(Block block : Registry.BLOCK) {
            boolean flag1 = false;

            for(StatType<Block> stattype1 : this.field_195113_v) {
               if (stattype1.contains(block) && StatsScreen.this.stats.getValue(stattype1.get(block)) > 0) {
                  flag1 = true;
               }
            }

            if (flag1) {
               set.add(block.asItem());
            }
         }

         set.remove(Items.AIR);
         this.field_195116_y = Lists.newArrayList(set);

         for(int i = 0; i < this.field_195116_y.size(); ++i) {
            this.func_230513_b_(new StatsScreen.StatsList.Entry());
         }

      }

      protected void func_230448_a_(MatrixStack p_230448_1_, int p_230448_2_, int p_230448_3_, Tessellator p_230448_4_) {
         if (!this.field_230668_b_.mouseHelper.isLeftDown()) {
            this.field_195115_x = -1;
         }

         for(int i = 0; i < this.field_195112_D.length; ++i) {
            StatsScreen.this.func_238674_c_(p_230448_1_, p_230448_2_ + StatsScreen.this.func_195224_b(i) - 18, p_230448_3_ + 1, 0, this.field_195115_x == i ? 0 : 18);
         }

         if (this.field_195110_A != null) {
            int k = StatsScreen.this.func_195224_b(this.func_195105_b(this.field_195110_A)) - 36;
            int j = this.field_195111_B == 1 ? 2 : 1;
            StatsScreen.this.func_238674_c_(p_230448_1_, p_230448_2_ + k, p_230448_3_ + 1, 18 * j, 0);
         }

         for(int l = 0; l < this.field_195112_D.length; ++l) {
            int i1 = this.field_195115_x == l ? 1 : 0;
            StatsScreen.this.func_238674_c_(p_230448_1_, p_230448_2_ + StatsScreen.this.func_195224_b(l) - 18 + i1, p_230448_3_ + 1 + i1, 18 * this.field_195112_D[l], 18);
         }

      }

      public int func_230949_c_() {
         return 375;
      }

      protected int func_230952_d_() {
         return this.field_230670_d_ / 2 + 140;
      }

      protected void func_230433_a_(MatrixStack p_230433_1_) {
         StatsScreen.this.func_230446_a_(p_230433_1_);
      }

      protected void func_230938_a_(int p_230938_1_, int p_230938_2_) {
         this.field_195115_x = -1;

         for(int i = 0; i < this.field_195112_D.length; ++i) {
            int j = p_230938_1_ - StatsScreen.this.func_195224_b(i);
            if (j >= -36 && j <= 0) {
               this.field_195115_x = i;
               break;
            }
         }

         if (this.field_195115_x >= 0) {
            this.func_195107_a(this.func_195108_d(this.field_195115_x));
            this.field_230668_b_.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }

      }

      private StatType<?> func_195108_d(int p_195108_1_) {
         return p_195108_1_ < this.field_195113_v.size() ? this.field_195113_v.get(p_195108_1_) : this.field_195114_w.get(p_195108_1_ - this.field_195113_v.size());
      }

      private int func_195105_b(StatType<?> p_195105_1_) {
         int i = this.field_195113_v.indexOf(p_195105_1_);
         if (i >= 0) {
            return i;
         } else {
            int j = this.field_195114_w.indexOf(p_195105_1_);
            return j >= 0 ? j + this.field_195113_v.size() : -1;
         }
      }

      protected void func_230447_a_(MatrixStack p_230447_1_, int p_230447_2_, int p_230447_3_) {
         if (p_230447_3_ >= this.field_230672_i_ && p_230447_3_ <= this.field_230673_j_) {
            StatsScreen.StatsList.Entry statsscreen$statslist$entry = this.func_230933_a_((double)p_230447_2_, (double)p_230447_3_);
            int i = (this.field_230670_d_ - this.func_230949_c_()) / 2;
            if (statsscreen$statslist$entry != null) {
               if (p_230447_2_ < i + 40 || p_230447_2_ > i + 40 + 20) {
                  return;
               }

               Item item = this.field_195116_y.get(this.func_231039_at__().indexOf(statsscreen$statslist$entry));
               this.func_238680_a_(p_230447_1_, this.func_200208_a(item), p_230447_2_, p_230447_3_);
            } else {
               ITextComponent itextcomponent = null;
               int j = p_230447_2_ - i;

               for(int k = 0; k < this.field_195112_D.length; ++k) {
                  int l = StatsScreen.this.func_195224_b(k);
                  if (j >= l - 18 && j <= l) {
                     itextcomponent = this.func_195108_d(k).func_242170_d();
                     break;
                  }
               }

               this.func_238680_a_(p_230447_1_, itextcomponent, p_230447_2_, p_230447_3_);
            }

         }
      }

      protected void func_238680_a_(MatrixStack p_238680_1_, @Nullable ITextComponent p_238680_2_, int p_238680_3_, int p_238680_4_) {
         if (p_238680_2_ != null) {
            int i = p_238680_3_ + 12;
            int j = p_238680_4_ - 12;
            int k = StatsScreen.this.field_230712_o_.func_238414_a_(p_238680_2_);
            this.func_238468_a_(p_238680_1_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 400.0F);
            StatsScreen.this.field_230712_o_.func_243246_a(p_238680_1_, p_238680_2_, (float)i, (float)j, -1);
            RenderSystem.popMatrix();
         }
      }

      protected ITextComponent func_200208_a(Item p_200208_1_) {
         return p_200208_1_.getName();
      }

      protected void func_195107_a(StatType<?> p_195107_1_) {
         if (p_195107_1_ != this.field_195110_A) {
            this.field_195110_A = p_195107_1_;
            this.field_195111_B = -1;
         } else if (this.field_195111_B == -1) {
            this.field_195111_B = 1;
         } else {
            this.field_195110_A = null;
            this.field_195111_B = 0;
         }

         this.field_195116_y.sort(this.field_195117_z);
      }

      @OnlyIn(Dist.CLIENT)
      class Comparator implements java.util.Comparator<Item> {
         private Comparator() {
         }

         public int compare(Item p_compare_1_, Item p_compare_2_) {
            int i;
            int j;
            if (StatsList.this.field_195110_A == null) {
               i = 0;
               j = 0;
            } else if (StatsList.this.field_195113_v.contains(StatsList.this.field_195110_A)) {
               StatType<Block> stattype = (StatType<Block>)StatsList.this.field_195110_A;
               i = p_compare_1_ instanceof BlockItem ? StatsScreen.this.stats.getValue(stattype, ((BlockItem)p_compare_1_).getBlock()) : -1;
               j = p_compare_2_ instanceof BlockItem ? StatsScreen.this.stats.getValue(stattype, ((BlockItem)p_compare_2_).getBlock()) : -1;
            } else {
               StatType<Item> stattype1 = (StatType<Item>)StatsList.this.field_195110_A;
               i = StatsScreen.this.stats.getValue(stattype1, p_compare_1_);
               j = StatsScreen.this.stats.getValue(stattype1, p_compare_2_);
            }

            return i == j ? StatsList.this.field_195111_B * Integer.compare(Item.getIdFromItem(p_compare_1_), Item.getIdFromItem(p_compare_2_)) : StatsList.this.field_195111_B * Integer.compare(i, j);
         }
      }

      @OnlyIn(Dist.CLIENT)
      class Entry extends ExtendedList.AbstractListEntry<StatsScreen.StatsList.Entry> {
         private Entry() {
         }

         public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            Item item = StatsScreen.this.itemStats.field_195116_y.get(p_230432_2_);
            StatsScreen.this.func_238667_a_(p_230432_1_, p_230432_4_ + 40, p_230432_3_, item);

            for(int i = 0; i < StatsScreen.this.itemStats.field_195113_v.size(); ++i) {
               Stat<Block> stat;
               if (item instanceof BlockItem) {
                  stat = StatsScreen.this.itemStats.field_195113_v.get(i).get(((BlockItem)item).getBlock());
               } else {
                  stat = null;
               }

               this.func_238681_a_(p_230432_1_, stat, p_230432_4_ + StatsScreen.this.func_195224_b(i), p_230432_3_, p_230432_2_ % 2 == 0);
            }

            for(int j = 0; j < StatsScreen.this.itemStats.field_195114_w.size(); ++j) {
               this.func_238681_a_(p_230432_1_, StatsScreen.this.itemStats.field_195114_w.get(j).get(item), p_230432_4_ + StatsScreen.this.func_195224_b(j + StatsScreen.this.itemStats.field_195113_v.size()), p_230432_3_, p_230432_2_ % 2 == 0);
            }

         }

         protected void func_238681_a_(MatrixStack p_238681_1_, @Nullable Stat<?> p_238681_2_, int p_238681_3_, int p_238681_4_, boolean p_238681_5_) {
            String s = p_238681_2_ == null ? "-" : p_238681_2_.format(StatsScreen.this.stats.getValue(p_238681_2_));
            AbstractGui.func_238476_c_(p_238681_1_, StatsScreen.this.field_230712_o_, s, p_238681_3_ - StatsScreen.this.field_230712_o_.getStringWidth(s), p_238681_4_ + 5, p_238681_5_ ? 16777215 : 9474192);
         }
      }
   }
}