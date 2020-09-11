package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.WorldTemplate;
import javax.annotation.Nullable;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class NotifableRealmsScreen extends RealmsScreen {
   protected abstract void func_223627_a_(@Nullable WorldTemplate p_223627_1_);
}