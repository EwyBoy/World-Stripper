package net.minecraft.world.storage;

public class FolderName {
   public static final FolderName field_237245_a_ = new FolderName("advancements");
   public static final FolderName field_237246_b_ = new FolderName("stats");
   public static final FolderName field_237247_c_ = new FolderName("playerdata");
   public static final FolderName field_237248_d_ = new FolderName("players");
   public static final FolderName field_237249_e_ = new FolderName("level.dat");
   public static final FolderName field_237250_f_ = new FolderName("generated");
   public static final FolderName field_237251_g_ = new FolderName("datapacks");
   public static final FolderName field_237252_h_ = new FolderName("resources.zip");
   public static final FolderName field_237253_i_ = new FolderName(".");
   private final String field_237254_j_;

   public FolderName(String p_i232151_1_) {
      this.field_237254_j_ = p_i232151_1_;
   }

   public String func_237255_a_() {
      return this.field_237254_j_;
   }

   public String toString() {
      return "/" + this.field_237254_j_;
   }
}