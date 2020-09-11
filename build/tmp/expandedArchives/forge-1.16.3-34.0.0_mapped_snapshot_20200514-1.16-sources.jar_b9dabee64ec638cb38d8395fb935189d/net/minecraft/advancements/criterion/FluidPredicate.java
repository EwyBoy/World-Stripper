package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class FluidPredicate {
   public static final FluidPredicate field_226643_a_ = new FluidPredicate((ITag<Fluid>)null, (Fluid)null, StatePropertiesPredicate.EMPTY);
   @Nullable
   private final ITag<Fluid> field_226644_b_;
   @Nullable
   private final Fluid field_226645_c_;
   private final StatePropertiesPredicate field_226646_d_;

   public FluidPredicate(@Nullable ITag<Fluid> p_i225738_1_, @Nullable Fluid p_i225738_2_, StatePropertiesPredicate p_i225738_3_) {
      this.field_226644_b_ = p_i225738_1_;
      this.field_226645_c_ = p_i225738_2_;
      this.field_226646_d_ = p_i225738_3_;
   }

   public boolean func_226649_a_(ServerWorld p_226649_1_, BlockPos p_226649_2_) {
      if (this == field_226643_a_) {
         return true;
      } else if (!p_226649_1_.isBlockPresent(p_226649_2_)) {
         return false;
      } else {
         FluidState fluidstate = p_226649_1_.getFluidState(p_226649_2_);
         Fluid fluid = fluidstate.getFluid();
         if (this.field_226644_b_ != null && !this.field_226644_b_.func_230235_a_(fluid)) {
            return false;
         } else if (this.field_226645_c_ != null && fluid != this.field_226645_c_) {
            return false;
         } else {
            return this.field_226646_d_.matches(fluidstate);
         }
      }
   }

   public static FluidPredicate func_226648_a_(@Nullable JsonElement p_226648_0_) {
      if (p_226648_0_ != null && !p_226648_0_.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(p_226648_0_, "fluid");
         Fluid fluid = null;
         if (jsonobject.has("fluid")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(jsonobject, "fluid"));
            fluid = Registry.FLUID.getOrDefault(resourcelocation);
         }

         ITag<Fluid> itag = null;
         if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(JSONUtils.getString(jsonobject, "tag"));
            itag = TagCollectionManager.func_242178_a().func_241837_c().get(resourcelocation1);
            if (itag == null) {
               throw new JsonSyntaxException("Unknown fluid tag '" + resourcelocation1 + "'");
            }
         }

         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.deserializeProperties(jsonobject.get("state"));
         return new FluidPredicate(itag, fluid, statepropertiespredicate);
      } else {
         return field_226643_a_;
      }
   }

   public JsonElement func_226647_a_() {
      if (this == field_226643_a_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.field_226645_c_ != null) {
            jsonobject.addProperty("fluid", Registry.FLUID.getKey(this.field_226645_c_).toString());
         }

         if (this.field_226644_b_ != null) {
            jsonobject.addProperty("tag", TagCollectionManager.func_242178_a().func_241837_c().func_232975_b_(this.field_226644_b_).toString());
         }

         jsonobject.add("state", this.field_226646_d_.toJsonElement());
         return jsonobject;
      }
   }
}