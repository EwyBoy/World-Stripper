package net.minecraft.util.text;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.event.HoverEvent;

public class TextComponentUtils {
   public static IFormattableTextComponent func_240648_a_(IFormattableTextComponent p_240648_0_, Style p_240648_1_) {
      if (p_240648_1_.isEmpty()) {
         return p_240648_0_;
      } else {
         Style style = p_240648_0_.getStyle();
         if (style.isEmpty()) {
            return p_240648_0_.func_230530_a_(p_240648_1_);
         } else {
            return style.equals(p_240648_1_) ? p_240648_0_ : p_240648_0_.func_230530_a_(style.func_240717_a_(p_240648_1_));
         }
      }
   }

   public static IFormattableTextComponent func_240645_a_(@Nullable CommandSource p_240645_0_, ITextComponent p_240645_1_, @Nullable Entity p_240645_2_, int p_240645_3_) throws CommandSyntaxException {
      if (p_240645_3_ > 100) {
         return p_240645_1_.func_230532_e_();
      } else {
         IFormattableTextComponent iformattabletextcomponent = p_240645_1_ instanceof ITargetedTextComponent ? ((ITargetedTextComponent)p_240645_1_).func_230535_a_(p_240645_0_, p_240645_2_, p_240645_3_ + 1) : p_240645_1_.func_230531_f_();

         for(ITextComponent itextcomponent : p_240645_1_.getSiblings()) {
            iformattabletextcomponent.func_230529_a_(func_240645_a_(p_240645_0_, itextcomponent, p_240645_2_, p_240645_3_ + 1));
         }

         return iformattabletextcomponent.func_240703_c_(func_240646_a_(p_240645_0_, p_240645_1_.getStyle(), p_240645_2_, p_240645_3_));
      }
   }

   private static Style func_240646_a_(@Nullable CommandSource p_240646_0_, Style p_240646_1_, @Nullable Entity p_240646_2_, int p_240646_3_) throws CommandSyntaxException {
      HoverEvent hoverevent = p_240646_1_.getHoverEvent();
      if (hoverevent != null) {
         ITextComponent itextcomponent = hoverevent.func_240662_a_(HoverEvent.Action.field_230550_a_);
         if (itextcomponent != null) {
            HoverEvent hoverevent1 = new HoverEvent(HoverEvent.Action.field_230550_a_, func_240645_a_(p_240646_0_, itextcomponent, p_240646_2_, p_240646_3_ + 1));
            return p_240646_1_.func_240716_a_(hoverevent1);
         }
      }

      return p_240646_1_;
   }

   public static ITextComponent getDisplayName(GameProfile profile) {
      if (profile.getName() != null) {
         return new StringTextComponent(profile.getName());
      } else {
         return profile.getId() != null ? new StringTextComponent(profile.getId().toString()) : new StringTextComponent("(unknown)");
      }
   }

   public static ITextComponent makeGreenSortedList(Collection<String> collection) {
      return makeSortedList(collection, (p_197681_0_) -> {
         return (new StringTextComponent(p_197681_0_)).func_240699_a_(TextFormatting.GREEN);
      });
   }

   public static <T extends Comparable<T>> ITextComponent makeSortedList(Collection<T> collection, Function<T, ITextComponent> toTextComponent) {
      if (collection.isEmpty()) {
         return StringTextComponent.field_240750_d_;
      } else if (collection.size() == 1) {
         return toTextComponent.apply(collection.iterator().next());
      } else {
         List<T> list = Lists.newArrayList(collection);
         list.sort(Comparable::compareTo);
         return func_240649_b_(list, toTextComponent);
      }
   }

   public static <T> IFormattableTextComponent func_240649_b_(Collection<T> p_240649_0_, Function<T, ITextComponent> p_240649_1_) {
      if (p_240649_0_.isEmpty()) {
         return new StringTextComponent("");
      } else if (p_240649_0_.size() == 1) {
         return p_240649_1_.apply(p_240649_0_.iterator().next()).func_230532_e_();
      } else {
         IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("");
         boolean flag = true;

         for(T t : p_240649_0_) {
            if (!flag) {
               iformattabletextcomponent.func_230529_a_((new StringTextComponent(", ")).func_240699_a_(TextFormatting.GRAY));
            }

            iformattabletextcomponent.func_230529_a_(p_240649_1_.apply(t));
            flag = false;
         }

         return iformattabletextcomponent;
      }
   }

   public static IFormattableTextComponent func_240647_a_(ITextComponent p_240647_0_) {
      return new TranslationTextComponent("chat.square_brackets", p_240647_0_);
   }

   public static ITextComponent toTextComponent(Message message) {
      return (ITextComponent)(message instanceof ITextComponent ? (ITextComponent)message : new StringTextComponent(message.getString()));
   }
}