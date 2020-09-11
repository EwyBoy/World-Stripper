package net.minecraft.client.audio;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OggAudioStreamWrapper implements IAudioStream {
   private final OggAudioStreamWrapper.IFactory field_239536_a_;
   private IAudioStream field_239537_b_;
   private final BufferedInputStream field_239538_c_;

   public OggAudioStreamWrapper(OggAudioStreamWrapper.IFactory p_i232496_1_, InputStream p_i232496_2_) throws IOException {
      this.field_239536_a_ = p_i232496_1_;
      this.field_239538_c_ = new BufferedInputStream(p_i232496_2_);
      this.field_239538_c_.mark(Integer.MAX_VALUE);
      this.field_239537_b_ = p_i232496_1_.create(new OggAudioStreamWrapper.Stream(this.field_239538_c_));
   }

   public AudioFormat getAudioFormat() {
      return this.field_239537_b_.getAudioFormat();
   }

   public ByteBuffer func_216455_a(int p_216455_1_) throws IOException {
      ByteBuffer bytebuffer = this.field_239537_b_.func_216455_a(p_216455_1_);
      if (!bytebuffer.hasRemaining()) {
         this.field_239537_b_.close();
         this.field_239538_c_.reset();
         this.field_239537_b_ = this.field_239536_a_.create(new OggAudioStreamWrapper.Stream(this.field_239538_c_));
         bytebuffer = this.field_239537_b_.func_216455_a(p_216455_1_);
      }

      return bytebuffer;
   }

   public void close() throws IOException {
      this.field_239537_b_.close();
      this.field_239538_c_.close();
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface IFactory {
      IAudioStream create(InputStream p_create_1_) throws IOException;
   }

   @OnlyIn(Dist.CLIENT)
   static class Stream extends FilterInputStream {
      private Stream(InputStream p_i232497_1_) {
         super(p_i232497_1_);
      }

      public void close() {
      }
   }
}