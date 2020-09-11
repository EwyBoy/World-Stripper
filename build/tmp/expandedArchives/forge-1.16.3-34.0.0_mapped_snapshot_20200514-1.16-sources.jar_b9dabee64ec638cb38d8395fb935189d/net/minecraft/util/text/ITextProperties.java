package net.minecraft.util.text;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITextProperties {
   Optional<Unit> field_240650_b_ = Optional.of(Unit.INSTANCE);
   ITextProperties field_240651_c_ = new ITextProperties() {
      public <T> Optional<T> func_230438_a_(ITextProperties.ITextAcceptor<T> p_230438_1_) {
         return Optional.empty();
      }

      @OnlyIn(Dist.CLIENT)
      public <T> Optional<T> func_230439_a_(ITextProperties.IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_) {
         return Optional.empty();
      }
   };

   <T> Optional<T> func_230438_a_(ITextProperties.ITextAcceptor<T> p_230438_1_);

   @OnlyIn(Dist.CLIENT)
   <T> Optional<T> func_230439_a_(ITextProperties.IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_);

   static ITextProperties func_240652_a_(final String p_240652_0_) {
      return new ITextProperties() {
         public <T> Optional<T> func_230438_a_(ITextProperties.ITextAcceptor<T> p_230438_1_) {
            return p_230438_1_.accept(p_240652_0_);
         }

         @OnlyIn(Dist.CLIENT)
         public <T> Optional<T> func_230439_a_(ITextProperties.IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_) {
            return p_230439_1_.accept(p_230439_2_, p_240652_0_);
         }
      };
   }

   @OnlyIn(Dist.CLIENT)
   static ITextProperties func_240653_a_(final String p_240653_0_, final Style p_240653_1_) {
      return new ITextProperties() {
         public <T> Optional<T> func_230438_a_(ITextProperties.ITextAcceptor<T> p_230438_1_) {
            return p_230438_1_.accept(p_240653_0_);
         }

         public <T> Optional<T> func_230439_a_(ITextProperties.IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_) {
            return p_230439_1_.accept(p_240653_1_.func_240717_a_(p_230439_2_), p_240653_0_);
         }
      };
   }

   @OnlyIn(Dist.CLIENT)
   static ITextProperties func_240655_a_(ITextProperties... p_240655_0_) {
      return func_240654_a_(ImmutableList.copyOf(p_240655_0_));
   }

   @OnlyIn(Dist.CLIENT)
   static ITextProperties func_240654_a_(final List<ITextProperties> p_240654_0_) {
      return new ITextProperties() {
         public <T> Optional<T> func_230438_a_(ITextProperties.ITextAcceptor<T> p_230438_1_) {
            for(ITextProperties itextproperties : p_240654_0_) {
               Optional<T> optional = itextproperties.func_230438_a_(p_230438_1_);
               if (optional.isPresent()) {
                  return optional;
               }
            }

            return Optional.empty();
         }

         public <T> Optional<T> func_230439_a_(ITextProperties.IStyledTextAcceptor<T> p_230439_1_, Style p_230439_2_) {
            for(ITextProperties itextproperties : p_240654_0_) {
               Optional<T> optional = itextproperties.func_230439_a_(p_230439_1_, p_230439_2_);
               if (optional.isPresent()) {
                  return optional;
               }
            }

            return Optional.empty();
         }
      };
   }

   default String getString() {
      StringBuilder stringbuilder = new StringBuilder();
      this.func_230438_a_((p_241754_1_) -> {
         stringbuilder.append(p_241754_1_);
         return Optional.empty();
      });
      return stringbuilder.toString();
   }

   @OnlyIn(Dist.CLIENT)
   public interface IStyledTextAcceptor<T> {
      Optional<T> accept(Style p_accept_1_, String p_accept_2_);
   }

   public interface ITextAcceptor<T> {
      Optional<T> accept(String p_accept_1_);
   }
}