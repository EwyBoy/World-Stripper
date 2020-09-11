package net.minecraft.tags;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagCollectionReader<T> {
   private static final Logger field_242217_a = LogManager.getLogger();
   private static final Gson field_242218_b = new Gson();
   private static final int field_242219_c = ".json".length();
   private final Function<ResourceLocation, Optional<T>> field_242220_d;
   private final String field_242221_e;
   private final String field_242222_f;

   public TagCollectionReader(Function<ResourceLocation, Optional<T>> p_i241899_1_, String p_i241899_2_, String p_i241899_3_) {
      this.field_242220_d = p_i241899_1_;
      this.field_242221_e = p_i241899_2_;
      this.field_242222_f = p_i241899_3_;
   }

   public CompletableFuture<Map<ResourceLocation, ITag.Builder>> func_242224_a(IResourceManager p_242224_1_, Executor p_242224_2_) {
      return CompletableFuture.supplyAsync(() -> {
         Map<ResourceLocation, ITag.Builder> map = Maps.newHashMap();

         for(ResourceLocation resourcelocation : p_242224_1_.getAllResourceLocations(this.field_242221_e, (p_242225_0_) -> {
            return p_242225_0_.endsWith(".json");
         })) {
            String s = resourcelocation.getPath();
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s.substring(this.field_242221_e.length() + 1, s.length() - field_242219_c));

            try {
               for(IResource iresource : p_242224_1_.getAllResources(resourcelocation)) {
                  try (
                     InputStream inputstream = iresource.getInputStream();
                     Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                  ) {
                     JsonObject jsonobject = JSONUtils.fromJson(field_242218_b, reader, JsonObject.class);
                     if (jsonobject == null) {
                        field_242217_a.error("Couldn't load {} tag list {} from {} in data pack {} as it is empty or null", this.field_242222_f, resourcelocation1, resourcelocation, iresource.getPackName());
                     } else {
                        map.computeIfAbsent(resourcelocation1, (p_242229_0_) -> {
                           return ITag.Builder.create();
                        }).func_232956_a_(jsonobject, iresource.getPackName());
                     }
                  } catch (RuntimeException | IOException ioexception) {
                     field_242217_a.error("Couldn't read {} tag list {} from {} in data pack {}", this.field_242222_f, resourcelocation1, resourcelocation, iresource.getPackName(), ioexception);
                  } finally {
                     IOUtils.closeQuietly((Closeable)iresource);
                  }
               }
            } catch (IOException ioexception1) {
               field_242217_a.error("Couldn't read {} tag list {} from {}", this.field_242222_f, resourcelocation1, resourcelocation, ioexception1);
            }
         }

         return map;
      }, p_242224_2_);
   }

   public ITagCollection<T> func_242226_a(Map<ResourceLocation, ITag.Builder> p_242226_1_) {
      Map<ResourceLocation, ITag<T>> map = Maps.newHashMap();
      Function<ResourceLocation, ITag<T>> function = map::get;
      Function<ResourceLocation, T> function1 = (p_242228_1_) -> {
         return this.field_242220_d.apply(p_242228_1_).orElse((T)null);
      };

      while(!p_242226_1_.isEmpty()) {
         boolean flag = false;
         Iterator<Entry<ResourceLocation, ITag.Builder>> iterator = p_242226_1_.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry<ResourceLocation, ITag.Builder> entry = iterator.next();
            Optional<ITag<T>> optional = entry.getValue().func_232959_a_(function, function1);
            if (optional.isPresent()) {
               map.put(entry.getKey(), optional.get());
               iterator.remove();
               flag = true;
            }
         }

         if (!flag) {
            break;
         }
      }

      p_242226_1_.forEach((p_242227_3_, p_242227_4_) -> {
         field_242217_a.error("Couldn't load {} tag {} as it is missing following references: {}", this.field_242222_f, p_242227_3_, p_242227_4_.func_232963_b_(function, function1).map(Objects::toString).collect(Collectors.joining(",")));
      });
      return ITagCollection.func_242202_a(map);
   }
}