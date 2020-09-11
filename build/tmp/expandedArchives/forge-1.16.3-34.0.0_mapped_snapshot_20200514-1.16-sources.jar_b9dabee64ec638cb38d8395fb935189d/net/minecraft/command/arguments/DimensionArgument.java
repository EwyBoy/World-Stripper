package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DimensionArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> field_237479_a_ = Stream.of(World.field_234918_g_, World.field_234919_h_).map((p_212593_0_) -> {
      return p_212593_0_.func_240901_a_().toString();
   }).collect(Collectors.toList());
   private static final DynamicCommandExceptionType field_237480_b_ = new DynamicCommandExceptionType((p_212594_0_) -> {
      return new TranslationTextComponent("argument.dimension.invalid", p_212594_0_);
   });

   public ResourceLocation parse(StringReader p_parse_1_) throws CommandSyntaxException {
      return ResourceLocation.read(p_parse_1_);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_listSuggestions_1_, SuggestionsBuilder p_listSuggestions_2_) {
      return p_listSuggestions_1_.getSource() instanceof ISuggestionProvider ? ISuggestionProvider.func_212476_a(((ISuggestionProvider)p_listSuggestions_1_.getSource()).func_230390_p_().stream().map(RegistryKey::func_240901_a_), p_listSuggestions_2_) : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return field_237479_a_;
   }

   public static DimensionArgument getDimension() {
      return new DimensionArgument();
   }

   public static ServerWorld getDimensionArgument(CommandContext<CommandSource> p_212592_0_, String p_212592_1_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_212592_0_.getArgument(p_212592_1_, ResourceLocation.class);
      RegistryKey<World> registrykey = RegistryKey.func_240903_a_(Registry.field_239699_ae_, resourcelocation);
      ServerWorld serverworld = p_212592_0_.getSource().getServer().getWorld(registrykey);
      if (serverworld == null) {
         throw field_237480_b_.create(resourcelocation);
      } else {
         return serverworld;
      }
   }
}