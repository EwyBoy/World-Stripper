package net.minecraft.client.gui.screen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class FlatPresetsScreen extends Screen {
   private static final Logger field_238631_a_ = LogManager.getLogger();
   private static final List<FlatPresetsScreen.LayerItem> FLAT_WORLD_PRESETS = Lists.newArrayList();
   /** The parent GUI */
   private final CreateFlatWorldScreen parentScreen;
   private ITextComponent presetsShare;
   private ITextComponent listText;
   private FlatPresetsScreen.SlotList list;
   private Button btnSelect;
   private TextFieldWidget export;
   private FlatGenerationSettings field_241594_u_;

   public FlatPresetsScreen(CreateFlatWorldScreen parent) {
      super(new TranslationTextComponent("createWorld.customize.presets.title"));
      this.parentScreen = parent;
   }

   @Nullable
   private static FlatLayerInfo func_238638_a_(String p_238638_0_, int p_238638_1_) {
      String[] astring = p_238638_0_.split("\\*", 2);
      int i;
      if (astring.length == 2) {
         try {
            i = Math.max(Integer.parseInt(astring[0]), 0);
         } catch (NumberFormatException numberformatexception) {
            field_238631_a_.error("Error while parsing flat world string => {}", (Object)numberformatexception.getMessage());
            return null;
         }
      } else {
         i = 1;
      }

      int j = Math.min(p_238638_1_ + i, 256);
      int k = j - p_238638_1_;
      String s = astring[astring.length - 1];

      Block block;
      try {
         block = Registry.BLOCK.func_241873_b(new ResourceLocation(s)).orElse((Block)null);
      } catch (Exception exception) {
         field_238631_a_.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
         return null;
      }

      if (block == null) {
         field_238631_a_.error("Error while parsing flat world string => Unknown block, {}", (Object)s);
         return null;
      } else {
         FlatLayerInfo flatlayerinfo = new FlatLayerInfo(k, block);
         flatlayerinfo.setMinY(p_238638_1_);
         return flatlayerinfo;
      }
   }

   private static List<FlatLayerInfo> func_238637_a_(String p_238637_0_) {
      List<FlatLayerInfo> list = Lists.newArrayList();
      String[] astring = p_238637_0_.split(",");
      int i = 0;

      for(String s : astring) {
         FlatLayerInfo flatlayerinfo = func_238638_a_(s, i);
         if (flatlayerinfo == null) {
            return Collections.emptyList();
         }

         list.add(flatlayerinfo);
         i += flatlayerinfo.getLayerCount();
      }

      return list;
   }

   public static FlatGenerationSettings func_243299_a(Registry<Biome> p_243299_0_, String p_243299_1_, FlatGenerationSettings p_243299_2_) {
      Iterator<String> iterator = Splitter.on(';').split(p_243299_1_).iterator();
      if (!iterator.hasNext()) {
         return FlatGenerationSettings.func_242869_a(p_243299_0_);
      } else {
         List<FlatLayerInfo> list = func_238637_a_(iterator.next());
         if (list.isEmpty()) {
            return FlatGenerationSettings.func_242869_a(p_243299_0_);
         } else {
            FlatGenerationSettings flatgenerationsettings = p_243299_2_.func_241527_a_(list, p_243299_2_.func_236943_d_());
            RegistryKey<Biome> registrykey = Biomes.PLAINS;
            if (iterator.hasNext()) {
               try {
                  ResourceLocation resourcelocation = new ResourceLocation(iterator.next());
                  registrykey = RegistryKey.func_240903_a_(Registry.field_239720_u_, resourcelocation);
                  p_243299_0_.func_243575_c(registrykey).orElseThrow(() -> {
                     return new IllegalArgumentException("Invalid Biome: " + resourcelocation);
                  });
               } catch (Exception exception) {
                  field_238631_a_.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
               }
            }

            RegistryKey<Biome> registrykey1 = registrykey;
            flatgenerationsettings.func_242870_a(() -> {
               return p_243299_0_.func_243576_d(registrykey1);
            });
            return flatgenerationsettings;
         }
      }
   }

   private static String func_243303_b(Registry<Biome> p_243303_0_, FlatGenerationSettings p_243303_1_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(int i = 0; i < p_243303_1_.getFlatLayers().size(); ++i) {
         if (i > 0) {
            stringbuilder.append(",");
         }

         stringbuilder.append(p_243303_1_.getFlatLayers().get(i));
      }

      stringbuilder.append(";");
      stringbuilder.append((Object)p_243303_0_.getKey(p_243303_1_.getBiome()));
      return stringbuilder.toString();
   }

   protected void func_231160_c_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      this.presetsShare = new TranslationTextComponent("createWorld.customize.presets.share");
      this.listText = new TranslationTextComponent("createWorld.customize.presets.list");
      this.export = new TextFieldWidget(this.field_230712_o_, 50, 40, this.field_230708_k_ - 100, 20, this.presetsShare);
      this.export.setMaxStringLength(1230);
      Registry<Biome> registry = this.parentScreen.createWorldGui.field_238934_c_.func_239055_b_().func_243612_b(Registry.field_239720_u_);
      this.export.setText(func_243303_b(registry, this.parentScreen.func_238603_g_()));
      this.field_241594_u_ = this.parentScreen.func_238603_g_();
      this.field_230705_e_.add(this.export);
      this.list = new FlatPresetsScreen.SlotList();
      this.field_230705_e_.add(this.list);
      this.btnSelect = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ - 28, 150, 20, new TranslationTextComponent("createWorld.customize.presets.select"), (p_243298_2_) -> {
         FlatGenerationSettings flatgenerationsettings = func_243299_a(registry, this.export.getText(), this.field_241594_u_);
         this.parentScreen.func_238602_a_(flatgenerationsettings);
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ - 28, 150, 20, DialogTexts.field_240633_d_, (p_243294_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      this.func_213074_a(this.list.func_230958_g_() != null);
   }

   public boolean func_231043_a_(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
      return this.list.func_231043_a_(p_231043_1_, p_231043_3_, p_231043_5_);
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      String s = this.export.getText();
      this.func_231158_b_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.export.setText(s);
   }

   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen(this.parentScreen);
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.list.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, 0.0F, 400.0F);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 8, 16777215);
      func_238475_b_(p_230430_1_, this.field_230712_o_, this.presetsShare, 50, 30, 10526880);
      func_238475_b_(p_230430_1_, this.field_230712_o_, this.listText, 50, 70, 10526880);
      RenderSystem.popMatrix();
      this.export.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void func_231023_e_() {
      this.export.tick();
      super.func_231023_e_();
   }

   public void func_213074_a(boolean p_213074_1_) {
      this.btnSelect.field_230693_o_ = p_213074_1_ || this.export.getText().length() > 1;
   }

   private static void func_238640_a_(ITextComponent p_238640_0_, IItemProvider p_238640_1_, RegistryKey<Biome> p_238640_2_, List<Structure<?>> p_238640_3_, boolean p_238640_4_, boolean p_238640_5_, boolean p_238640_6_, FlatLayerInfo... p_238640_7_) {
      FLAT_WORLD_PRESETS.add(new FlatPresetsScreen.LayerItem(p_238640_1_.asItem(), p_238640_0_, (p_243301_6_) -> {
         Map<Structure<?>, StructureSeparationSettings> map = Maps.newHashMap();

         for(Structure<?> structure : p_238640_3_) {
            map.put(structure, DimensionStructuresSettings.field_236191_b_.get(structure));
         }

         DimensionStructuresSettings dimensionstructuressettings = new DimensionStructuresSettings(p_238640_4_ ? Optional.of(DimensionStructuresSettings.field_236192_c_) : Optional.empty(), map);
         FlatGenerationSettings flatgenerationsettings = new FlatGenerationSettings(dimensionstructuressettings, p_243301_6_);
         if (p_238640_5_) {
            flatgenerationsettings.func_236936_a_();
         }

         if (p_238640_6_) {
            flatgenerationsettings.func_236941_b_();
         }

         for(int i = p_238640_7_.length - 1; i >= 0; --i) {
            flatgenerationsettings.getFlatLayers().add(p_238640_7_[i]);
         }

         flatgenerationsettings.func_242870_a(() -> {
            return p_243301_6_.func_243576_d(p_238640_2_);
         });
         flatgenerationsettings.updateLayers();
         return flatgenerationsettings.func_236937_a_(dimensionstructuressettings);
      }));
   }

   static {
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.classic_flat"), Blocks.GRASS_BLOCK, Biomes.PLAINS, Arrays.asList(Structure.field_236381_q_), false, false, false, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(2, Blocks.DIRT), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.tunnelers_dream"), Blocks.STONE, Biomes.MOUNTAINS, Arrays.asList(Structure.field_236367_c_), true, true, false, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(230, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.water_world"), Items.WATER_BUCKET, Biomes.DEEP_OCEAN, Arrays.asList(Structure.field_236377_m_, Structure.field_236373_i_, Structure.field_236376_l_), false, false, false, new FlatLayerInfo(90, Blocks.WATER), new FlatLayerInfo(5, Blocks.SAND), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(5, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.overworld"), Blocks.GRASS, Biomes.PLAINS, Arrays.asList(Structure.field_236381_q_, Structure.field_236367_c_, Structure.field_236366_b_, Structure.field_236372_h_), true, true, true, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.snowy_kingdom"), Blocks.SNOW, Biomes.SNOWY_TUNDRA, Arrays.asList(Structure.field_236381_q_, Structure.field_236371_g_), false, false, false, new FlatLayerInfo(1, Blocks.SNOW), new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.bottomless_pit"), Items.FEATHER, Biomes.PLAINS, Arrays.asList(Structure.field_236381_q_), false, false, false, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(2, Blocks.COBBLESTONE));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.desert"), Blocks.SAND, Biomes.DESERT, Arrays.asList(Structure.field_236381_q_, Structure.field_236370_f_, Structure.field_236367_c_), true, true, false, new FlatLayerInfo(8, Blocks.SAND), new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.redstone_ready"), Items.REDSTONE, Biomes.DESERT, Collections.emptyList(), false, false, false, new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
      func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.the_void"), Blocks.BARRIER, Biomes.THE_VOID, Collections.emptyList(), false, true, false, new FlatLayerInfo(1, Blocks.AIR));
   }

   @OnlyIn(Dist.CLIENT)
   static class LayerItem {
      public final Item icon;
      public final ITextComponent name;
      public final Function<Registry<Biome>, FlatGenerationSettings> field_238643_c_;

      public LayerItem(Item p_i242057_1_, ITextComponent p_i242057_2_, Function<Registry<Biome>, FlatGenerationSettings> p_i242057_3_) {
         this.icon = p_i242057_1_;
         this.name = p_i242057_2_;
         this.field_238643_c_ = p_i242057_3_;
      }

      public ITextComponent func_238644_a_() {
         return this.name;
      }
   }

   @OnlyIn(Dist.CLIENT)
   class SlotList extends ExtendedList<FlatPresetsScreen.SlotList.PresetEntry> {
      public SlotList() {
         super(FlatPresetsScreen.this.field_230706_i_, FlatPresetsScreen.this.field_230708_k_, FlatPresetsScreen.this.field_230709_l_, 80, FlatPresetsScreen.this.field_230709_l_ - 37, 24);

         for(int i = 0; i < FlatPresetsScreen.FLAT_WORLD_PRESETS.size(); ++i) {
            this.func_230513_b_(new FlatPresetsScreen.SlotList.PresetEntry());
         }

      }

      public void func_241215_a_(@Nullable FlatPresetsScreen.SlotList.PresetEntry p_241215_1_) {
         super.func_241215_a_(p_241215_1_);
         if (p_241215_1_ != null) {
            NarratorChatListener.INSTANCE.say((new TranslationTextComponent("narrator.select", FlatPresetsScreen.FLAT_WORLD_PRESETS.get(this.func_231039_at__().indexOf(p_241215_1_)).func_238644_a_())).getString());
         }

         FlatPresetsScreen.this.func_213074_a(p_241215_1_ != null);
      }

      protected boolean func_230971_aw__() {
         return FlatPresetsScreen.this.func_241217_q_() == this;
      }

      public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
         if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
            return true;
         } else {
            if ((p_231046_1_ == 257 || p_231046_1_ == 335) && this.func_230958_g_() != null) {
               this.func_230958_g_().func_214399_a();
            }

            return false;
         }
      }

      @OnlyIn(Dist.CLIENT)
      public class PresetEntry extends ExtendedList.AbstractListEntry<FlatPresetsScreen.SlotList.PresetEntry> {
         public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            FlatPresetsScreen.LayerItem flatpresetsscreen$layeritem = FlatPresetsScreen.FLAT_WORLD_PRESETS.get(p_230432_2_);
            this.func_238647_a_(p_230432_1_, p_230432_4_, p_230432_3_, flatpresetsscreen$layeritem.icon);
            FlatPresetsScreen.this.field_230712_o_.func_243248_b(p_230432_1_, flatpresetsscreen$layeritem.name, (float)(p_230432_4_ + 18 + 5), (float)(p_230432_3_ + 6), 16777215);
         }

         public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
            if (p_231044_5_ == 0) {
               this.func_214399_a();
            }

            return false;
         }

         private void func_214399_a() {
            SlotList.this.func_241215_a_(this);
            FlatPresetsScreen.LayerItem flatpresetsscreen$layeritem = FlatPresetsScreen.FLAT_WORLD_PRESETS.get(SlotList.this.func_231039_at__().indexOf(this));
            Registry<Biome> registry = FlatPresetsScreen.this.parentScreen.createWorldGui.field_238934_c_.func_239055_b_().func_243612_b(Registry.field_239720_u_);
            FlatPresetsScreen.this.field_241594_u_ = flatpresetsscreen$layeritem.field_238643_c_.apply(registry);
            FlatPresetsScreen.this.export.setText(FlatPresetsScreen.func_243303_b(registry, FlatPresetsScreen.this.field_241594_u_));
            FlatPresetsScreen.this.export.setCursorPositionZero();
         }

         private void func_238647_a_(MatrixStack p_238647_1_, int p_238647_2_, int p_238647_3_, Item p_238647_4_) {
            this.func_238646_a_(p_238647_1_, p_238647_2_ + 1, p_238647_3_ + 1);
            RenderSystem.enableRescaleNormal();
            FlatPresetsScreen.this.field_230707_j_.renderItemIntoGUI(new ItemStack(p_238647_4_), p_238647_2_ + 2, p_238647_3_ + 2);
            RenderSystem.disableRescaleNormal();
         }

         private void func_238646_a_(MatrixStack p_238646_1_, int p_238646_2_, int p_238646_3_) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            SlotList.this.field_230668_b_.getTextureManager().bindTexture(AbstractGui.field_230664_g_);
            AbstractGui.func_238464_a_(p_238646_1_, p_238646_2_, p_238646_3_, FlatPresetsScreen.this.func_230927_p_(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}