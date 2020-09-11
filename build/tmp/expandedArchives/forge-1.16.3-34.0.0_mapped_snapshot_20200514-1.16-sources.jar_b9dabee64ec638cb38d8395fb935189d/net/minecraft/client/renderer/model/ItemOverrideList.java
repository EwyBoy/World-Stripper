package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemOverrideList {
   public static final ItemOverrideList EMPTY = new ItemOverrideList();
   private final List<ItemOverride> overrides = Lists.newArrayList();
   private final List<IBakedModel> overrideBakedModels;

   protected ItemOverrideList() {
      this.overrideBakedModels = Collections.emptyList();
   }

   @Deprecated //Forge: Use IUnbakedModel, add texture getter
   public ItemOverrideList(ModelBakery modelBakeryIn, BlockModel blockModelIn, Function<ResourceLocation, IUnbakedModel> modelGetter, List<ItemOverride> itemOverridesIn) {
      this(modelBakeryIn, (IUnbakedModel)blockModelIn, modelGetter, modelBakeryIn.getSpriteMap()::getSprite, itemOverridesIn);
   }
   public ItemOverrideList(ModelBakery modelBakeryIn, IUnbakedModel blockModelIn, Function<ResourceLocation, IUnbakedModel> modelGetter, Function<RenderMaterial, net.minecraft.client.renderer.texture.TextureAtlasSprite> textureGetter, List<ItemOverride> itemOverridesIn) {
      this.overrideBakedModels = itemOverridesIn.stream().map((p_217649_3_) -> {
         IUnbakedModel iunbakedmodel = modelGetter.apply(p_217649_3_.getLocation());
         return Objects.equals(iunbakedmodel, blockModelIn) ? null : modelBakeryIn.getBakedModel(p_217649_3_.getLocation(), ModelRotation.X0_Y0, textureGetter);
      }).collect(Collectors.toList());
      Collections.reverse(this.overrideBakedModels);

      for(int i = itemOverridesIn.size() - 1; i >= 0; --i) {
         this.overrides.add(itemOverridesIn.get(i));
      }

   }

   @Nullable
   public IBakedModel func_239290_a_(IBakedModel p_239290_1_, ItemStack p_239290_2_, @Nullable ClientWorld p_239290_3_, @Nullable LivingEntity p_239290_4_) {
      if (!this.overrides.isEmpty()) {
         for(int i = 0; i < this.overrides.size(); ++i) {
            ItemOverride itemoverride = this.overrides.get(i);
            if (itemoverride.func_239289_a_(p_239290_2_, p_239290_3_, p_239290_4_)) {
               IBakedModel ibakedmodel = this.overrideBakedModels.get(i);
               if (ibakedmodel == null) {
                  return p_239290_1_;
               }

               return ibakedmodel;
            }
         }
      }

      return p_239290_1_;
   }

   public com.google.common.collect.ImmutableList<ItemOverride> getOverrides() {
      return com.google.common.collect.ImmutableList.copyOf(overrides);
   }
}