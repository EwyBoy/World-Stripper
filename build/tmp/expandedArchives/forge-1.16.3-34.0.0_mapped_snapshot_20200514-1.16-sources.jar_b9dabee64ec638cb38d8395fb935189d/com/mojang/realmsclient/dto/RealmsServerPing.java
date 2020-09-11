package com.mojang.realmsclient.dto;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsServerPing extends ValueObject {
   public volatile String field_230607_a_ = "0";
   public volatile String field_230608_b_ = "";
}