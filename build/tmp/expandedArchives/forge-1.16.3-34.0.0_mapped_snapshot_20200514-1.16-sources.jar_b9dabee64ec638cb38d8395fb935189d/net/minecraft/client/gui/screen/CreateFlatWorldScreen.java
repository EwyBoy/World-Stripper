package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreateFlatWorldScreen extends Screen {
   protected final CreateWorldScreen createWorldGui;
   private final Consumer<FlatGenerationSettings> field_238601_b_;
   private FlatGenerationSettings generatorInfo;
   /** The text used to identify the material for a layer */
   private ITextComponent materialText;
   /** The text used to identify the height of a layer */
   private ITextComponent heightText;
   private CreateFlatWorldScreen.DetailsList createFlatWorldListSlotGui;
   /** The remove layer button */
   private Button removeLayerButton;

   public CreateFlatWorldScreen(CreateWorldScreen p_i242055_1_, Consumer<FlatGenerationSettings> p_i242055_2_, FlatGenerationSettings p_i242055_3_) {
      super(new TranslationTextComponent("createWorld.customize.flat.title"));
      this.createWorldGui = p_i242055_1_;
      this.field_238601_b_ = p_i242055_2_;
      this.generatorInfo = p_i242055_3_;
   }

   public FlatGenerationSettings func_238603_g_() {
      return this.generatorInfo;
   }

   public void func_238602_a_(FlatGenerationSettings p_238602_1_) {
      this.generatorInfo = p_238602_1_;
   }

   protected void func_231160_c_() {
      this.materialText = new TranslationTextComponent("createWorld.customize.flat.tile");
      this.heightText = new TranslationTextComponent("createWorld.customize.flat.height");
      this.createFlatWorldListSlotGui = new CreateFlatWorldScreen.DetailsList();
      this.field_230705_e_.add(this.createFlatWorldListSlotGui);
      this.removeLayerButton = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ - 52, 150, 20, new TranslationTextComponent("createWorld.customize.flat.removeLayer"), (p_213007_1_) -> {
         if (this.hasSelectedLayer()) {
            List<FlatLayerInfo> list = this.generatorInfo.getFlatLayers();
            int i = this.createFlatWorldListSlotGui.func_231039_at__().indexOf(this.createFlatWorldListSlotGui.func_230958_g_());
            int j = list.size() - i - 1;
            list.remove(j);
            this.createFlatWorldListSlotGui.func_241215_a_(list.isEmpty() ? null : this.createFlatWorldListSlotGui.func_231039_at__().get(Math.min(i, list.size() - 1)));
            this.generatorInfo.updateLayers();
            this.createFlatWorldListSlotGui.func_214345_a();
            this.onLayersChanged();
         }
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ - 52, 150, 20, new TranslationTextComponent("createWorld.customize.presets"), (p_213011_1_) -> {
         this.field_230706_i_.displayGuiScreen(new FlatPresetsScreen(this));
         this.generatorInfo.updateLayers();
         this.onLayersChanged();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ - 28, 150, 20, DialogTexts.field_240632_c_, (p_213010_1_) -> {
         this.field_238601_b_.accept(this.generatorInfo);
         this.field_230706_i_.displayGuiScreen(this.createWorldGui);
         this.generatorInfo.updateLayers();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 5, this.field_230709_l_ - 28, 150, 20, DialogTexts.field_240633_d_, (p_213009_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.createWorldGui);
         this.generatorInfo.updateLayers();
      }));
      this.generatorInfo.updateLayers();
      this.onLayersChanged();
   }

   /**
    * Would update whether or not the edit and remove buttons are enabled, but is currently disabled and always disables
    * the buttons (which are invisible anyways)
    */
   private void onLayersChanged() {
      this.removeLayerButton.field_230693_o_ = this.hasSelectedLayer();
   }

   /**
    * Returns whether there is a valid layer selection
    */
   private boolean hasSelectedLayer() {
      return this.createFlatWorldListSlotGui.func_230958_g_() != null;
   }

   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen(this.createWorldGui);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.createFlatWorldListSlotGui.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 8, 16777215);
      int i = this.field_230708_k_ / 2 - 92 - 16;
      func_238475_b_(p_230430_1_, this.field_230712_o_, this.materialText, i, 32, 16777215);
      func_238475_b_(p_230430_1_, this.field_230712_o_, this.heightText, i + 2 + 213 - this.field_230712_o_.func_238414_a_(this.heightText), 32, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   @OnlyIn(Dist.CLIENT)
   class DetailsList extends ExtendedList<CreateFlatWorldScreen.DetailsList.LayerEntry> {
      public DetailsList() {
         super(CreateFlatWorldScreen.this.field_230706_i_, CreateFlatWorldScreen.this.field_230708_k_, CreateFlatWorldScreen.this.field_230709_l_, 43, CreateFlatWorldScreen.this.field_230709_l_ - 60, 24);

         for(int i = 0; i < CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().size(); ++i) {
            this.func_230513_b_(new CreateFlatWorldScreen.DetailsList.LayerEntry());
         }

      }

      public void func_241215_a_(@Nullable CreateFlatWorldScreen.DetailsList.LayerEntry p_241215_1_) {
         super.func_241215_a_(p_241215_1_);
         if (p_241215_1_ != null) {
            FlatLayerInfo flatlayerinfo = CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().get(CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().size() - this.func_231039_at__().indexOf(p_241215_1_) - 1);
            Item item = flatlayerinfo.getLayerMaterial().getBlock().asItem();
            if (item != Items.AIR) {
               NarratorChatListener.INSTANCE.say((new TranslationTextComponent("narrator.select", item.getDisplayName(new ItemStack(item)))).getString());
            }
         }

         CreateFlatWorldScreen.this.onLayersChanged();
      }

      protected boolean func_230971_aw__() {
         return CreateFlatWorldScreen.this.func_241217_q_() == this;
      }

      protected int func_230952_d_() {
         return this.field_230670_d_ - 70;
      }

      public void func_214345_a() {
         int i = this.func_231039_at__().indexOf(this.func_230958_g_());
         this.func_230963_j_();

         for(int j = 0; j < CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().size(); ++j) {
            this.func_230513_b_(new CreateFlatWorldScreen.DetailsList.LayerEntry());
         }

         List<CreateFlatWorldScreen.DetailsList.LayerEntry> list = this.func_231039_at__();
         if (i >= 0 && i < list.size()) {
            this.func_241215_a_(list.get(i));
         }

      }

      @OnlyIn(Dist.CLIENT)
      class LayerEntry extends ExtendedList.AbstractListEntry<CreateFlatWorldScreen.DetailsList.LayerEntry> {
         private LayerEntry() {
         }

         public void func_230432_a_(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            FlatLayerInfo flatlayerinfo = CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().get(CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().size() - p_230432_2_ - 1);
            BlockState blockstate = flatlayerinfo.getLayerMaterial();
            Item item = blockstate.getBlock().asItem();
            if (item == Items.AIR) {
               if (blockstate.isIn(Blocks.WATER)) {
                  item = Items.WATER_BUCKET;
               } else if (blockstate.isIn(Blocks.LAVA)) {
                  item = Items.LAVA_BUCKET;
               }
            }

            ItemStack itemstack = new ItemStack(item);
            this.func_238605_a_(p_230432_1_, p_230432_4_, p_230432_3_, itemstack);
            CreateFlatWorldScreen.this.field_230712_o_.func_243248_b(p_230432_1_, item.getDisplayName(itemstack), (float)(p_230432_4_ + 18 + 5), (float)(p_230432_3_ + 3), 16777215);
            String s;
            if (p_230432_2_ == 0) {
               s = I18n.format("createWorld.customize.flat.layer.top", flatlayerinfo.getLayerCount());
            } else if (p_230432_2_ == CreateFlatWorldScreen.this.generatorInfo.getFlatLayers().size() - 1) {
               s = I18n.format("createWorld.customize.flat.layer.bottom", flatlayerinfo.getLayerCount());
            } else {
               s = I18n.format("createWorld.customize.flat.layer", flatlayerinfo.getLayerCount());
            }

            CreateFlatWorldScreen.this.field_230712_o_.func_238421_b_(p_230432_1_, s, (float)(p_230432_4_ + 2 + 213 - CreateFlatWorldScreen.this.field_230712_o_.getStringWidth(s)), (float)(p_230432_3_ + 3), 16777215);
         }

         public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
            if (p_231044_5_ == 0) {
               DetailsList.this.func_241215_a_(this);
               return true;
            } else {
               return false;
            }
         }

         private void func_238605_a_(MatrixStack p_238605_1_, int p_238605_2_, int p_238605_3_, ItemStack p_238605_4_) {
            this.func_238604_a_(p_238605_1_, p_238605_2_ + 1, p_238605_3_ + 1);
            RenderSystem.enableRescaleNormal();
            if (!p_238605_4_.isEmpty()) {
               CreateFlatWorldScreen.this.field_230707_j_.renderItemIntoGUI(p_238605_4_, p_238605_2_ + 2, p_238605_3_ + 2);
            }

            RenderSystem.disableRescaleNormal();
         }

         private void func_238604_a_(MatrixStack p_238604_1_, int p_238604_2_, int p_238604_3_) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            DetailsList.this.field_230668_b_.getTextureManager().bindTexture(AbstractGui.field_230664_g_);
            AbstractGui.func_238464_a_(p_238604_1_, p_238604_2_, p_238604_3_, CreateFlatWorldScreen.this.func_230927_p_(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}