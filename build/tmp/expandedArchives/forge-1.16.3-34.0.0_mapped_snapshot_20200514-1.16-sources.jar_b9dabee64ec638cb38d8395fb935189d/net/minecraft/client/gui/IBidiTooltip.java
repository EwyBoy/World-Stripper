package net.minecraft.client.gui;

import java.util.List;
import java.util.Optional;
import net.minecraft.util.IReorderingProcessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IBidiTooltip {
   Optional<List<IReorderingProcessor>> func_241867_d();
}