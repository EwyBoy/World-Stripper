package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class BlockPredicate {
   public static final BlockPredicate field_226231_a_ = new BlockPredicate((ITag<Block>)null, (Block)null, StatePropertiesPredicate.EMPTY, NBTPredicate.ANY);
   @Nullable
   private final ITag<Block> field_226232_b_;
   @Nullable
   private final Block field_226233_c_;
   private final StatePropertiesPredicate field_226234_d_;
   private final NBTPredicate field_226235_e_;

   public BlockPredicate(@Nullable ITag<Block> p_i225708_1_, @Nullable Block p_i225708_2_, StatePropertiesPredicate p_i225708_3_, NBTPredicate p_i225708_4_) {
      this.field_226232_b_ = p_i225708_1_;
      this.field_226233_c_ = p_i225708_2_;
      this.field_226234_d_ = p_i225708_3_;
      this.field_226235_e_ = p_i225708_4_;
   }

   public boolean func_226238_a_(ServerWorld p_226238_1_, BlockPos p_226238_2_) {
      if (this == field_226231_a_) {
         return true;
      } else if (!p_226238_1_.isBlockPresent(p_226238_2_)) {
         return false;
      } else {
         BlockState blockstate = p_226238_1_.getBlockState(p_226238_2_);
         Block block = blockstate.getBlock();
         if (this.field_226232_b_ != null && !this.field_226232_b_.func_230235_a_(block)) {
            return false;
         } else if (this.field_226233_c_ != null && block != this.field_226233_c_) {
            return false;
         } else if (!this.field_226234_d_.matches(blockstate)) {
            return false;
         } else {
            if (this.field_226235_e_ != NBTPredicate.ANY) {
               TileEntity tileentity = p_226238_1_.getTileEntity(p_226238_2_);
               if (tileentity == null || !this.field_226235_e_.test(tileentity.write(new CompoundNBT()))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static BlockPredicate func_226237_a_(@Nullable JsonElement p_226237_0_) {
      if (p_226237_0_ != null && !p_226237_0_.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(p_226237_0_, "block");
         NBTPredicate nbtpredicate = NBTPredicate.deserialize(jsonobject.get("nbt"));
         Block block = null;
         if (jsonobject.has("block")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(jsonobject, "block"));
            block = Registry.BLOCK.getOrDefault(resourcelocation);
         }

         ITag<Block> itag = null;
         if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(JSONUtils.getString(jsonobject, "tag"));
            itag = TagCollectionManager.func_242178_a().func_241835_a().get(resourcelocation1);
            if (itag == null) {
               throw new JsonSyntaxException("Unknown block tag '" + resourcelocation1 + "'");
            }
         }

         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.deserializeProperties(jsonobject.get("state"));
         return new BlockPredicate(itag, block, statepropertiespredicate, nbtpredicate);
      } else {
         return field_226231_a_;
      }
   }

   public JsonElement func_226236_a_() {
      if (this == field_226231_a_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.field_226233_c_ != null) {
            jsonobject.addProperty("block", Registry.BLOCK.getKey(this.field_226233_c_).toString());
         }

         if (this.field_226232_b_ != null) {
            jsonobject.addProperty("tag", TagCollectionManager.func_242178_a().func_241835_a().func_232975_b_(this.field_226232_b_).toString());
         }

         jsonobject.add("nbt", this.field_226235_e_.serialize());
         jsonobject.add("state", this.field_226234_d_.toJsonElement());
         return jsonobject;
      }
   }

   public static class Builder {
      @Nullable
      private Block field_226239_a_;
      @Nullable
      private ITag<Block> field_226240_b_;
      private StatePropertiesPredicate field_226241_c_ = StatePropertiesPredicate.EMPTY;
      private NBTPredicate field_226242_d_ = NBTPredicate.ANY;

      private Builder() {
      }

      public static BlockPredicate.Builder func_226243_a_() {
         return new BlockPredicate.Builder();
      }

      public BlockPredicate.Builder func_233458_a_(Block p_233458_1_) {
         this.field_226239_a_ = p_233458_1_;
         return this;
      }

      public BlockPredicate.Builder func_226244_a_(ITag<Block> p_226244_1_) {
         this.field_226240_b_ = p_226244_1_;
         return this;
      }

      public BlockPredicate.Builder func_233459_a_(StatePropertiesPredicate p_233459_1_) {
         this.field_226241_c_ = p_233459_1_;
         return this;
      }

      public BlockPredicate func_226245_b_() {
         return new BlockPredicate(this.field_226240_b_, this.field_226239_a_, this.field_226241_c_, this.field_226242_d_);
      }
   }
}