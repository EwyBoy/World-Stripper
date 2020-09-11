package net.minecraft.resources;

import java.util.function.Consumer;

public class ServerPackFinder implements IPackFinder {
   private final VanillaPack field_195738_a = new VanillaPack("minecraft");

   public void func_230230_a_(Consumer<ResourcePackInfo> p_230230_1_, ResourcePackInfo.IFactory p_230230_2_) {
      ResourcePackInfo resourcepackinfo = ResourcePackInfo.createResourcePack("vanilla", false, () -> {
         return this.field_195738_a;
      }, p_230230_2_, ResourcePackInfo.Priority.BOTTOM, IPackNameDecorator.field_232626_b_);
      if (resourcepackinfo != null) {
         p_230230_1_.accept(resourcepackinfo);
      }

   }
}