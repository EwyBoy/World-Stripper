package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommandSuggestionHelper {
   private static final Pattern field_228092_a_ = Pattern.compile("(\\s+)");
   private static final Style field_243252_b = Style.field_240709_b_.func_240712_a_(TextFormatting.RED);
   private static final Style field_243253_c = Style.field_240709_b_.func_240712_a_(TextFormatting.GRAY);
   private static final List<Style> field_243254_d = Stream.of(TextFormatting.AQUA, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.LIGHT_PURPLE, TextFormatting.GOLD).map(Style.field_240709_b_::func_240712_a_).collect(ImmutableList.toImmutableList());
   private final Minecraft field_228093_b_;
   private final Screen field_228094_c_;
   private final TextFieldWidget field_228095_d_;
   private final FontRenderer field_228096_e_;
   private final boolean field_228097_f_;
   private final boolean field_228098_g_;
   private final int field_228099_h_;
   private final int field_228100_i_;
   private final boolean field_228101_j_;
   private final int field_228102_k_;
   private final List<IReorderingProcessor> field_228103_l_ = Lists.newArrayList();
   private int field_228104_m_;
   private int field_228105_n_;
   private ParseResults<ISuggestionProvider> field_228106_o_;
   private CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> field_228107_p_;
   private CommandSuggestionHelper.Suggestions field_228108_q_;
   private boolean field_228109_r_;
   private boolean field_228110_s_;

   public CommandSuggestionHelper(Minecraft p_i225919_1_, Screen p_i225919_2_, TextFieldWidget p_i225919_3_, FontRenderer p_i225919_4_, boolean p_i225919_5_, boolean p_i225919_6_, int p_i225919_7_, int p_i225919_8_, boolean p_i225919_9_, int p_i225919_10_) {
      this.field_228093_b_ = p_i225919_1_;
      this.field_228094_c_ = p_i225919_2_;
      this.field_228095_d_ = p_i225919_3_;
      this.field_228096_e_ = p_i225919_4_;
      this.field_228097_f_ = p_i225919_5_;
      this.field_228098_g_ = p_i225919_6_;
      this.field_228099_h_ = p_i225919_7_;
      this.field_228100_i_ = p_i225919_8_;
      this.field_228101_j_ = p_i225919_9_;
      this.field_228102_k_ = p_i225919_10_;
      p_i225919_3_.setTextFormatter(this::func_228122_a_);
   }

   public void func_228124_a_(boolean p_228124_1_) {
      this.field_228109_r_ = p_228124_1_;
      if (!p_228124_1_) {
         this.field_228108_q_ = null;
      }

   }

   public boolean onKeyPressed(int p_228115_1_, int p_228115_2_, int p_228115_3_) {
      if (this.field_228108_q_ != null && this.field_228108_q_.func_228154_b_(p_228115_1_, p_228115_2_, p_228115_3_)) {
         return true;
      } else if (this.field_228094_c_.func_241217_q_() == this.field_228095_d_ && p_228115_1_ == 258) {
         this.func_228128_b_(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean onScroll(double p_228112_1_) {
      return this.field_228108_q_ != null && this.field_228108_q_.func_228147_a_(MathHelper.clamp(p_228112_1_, -1.0D, 1.0D));
   }

   public boolean onClick(double p_228113_1_, double p_228113_3_, int p_228113_5_) {
      return this.field_228108_q_ != null && this.field_228108_q_.func_228150_a_((int)p_228113_1_, (int)p_228113_3_, p_228113_5_);
   }

   public void func_228128_b_(boolean p_228128_1_) {
      if (this.field_228107_p_ != null && this.field_228107_p_.isDone()) {
         com.mojang.brigadier.suggestion.Suggestions suggestions = this.field_228107_p_.join();
         if (!suggestions.isEmpty()) {
            int i = 0;

            for(Suggestion suggestion : suggestions.getList()) {
               i = Math.max(i, this.field_228096_e_.getStringWidth(suggestion.getText()));
            }

            int j = MathHelper.clamp(this.field_228095_d_.func_195611_j(suggestions.getRange().getStart()), 0, this.field_228095_d_.func_195611_j(0) + this.field_228095_d_.getAdjustedWidth() - i);
            int k = this.field_228101_j_ ? this.field_228094_c_.field_230709_l_ - 12 : 72;
            this.field_228108_q_ = new CommandSuggestionHelper.Suggestions(j, k, i, this.func_241575_a_(suggestions), p_228128_1_);
         }
      }

   }

   private List<Suggestion> func_241575_a_(com.mojang.brigadier.suggestion.Suggestions p_241575_1_) {
      String s = this.field_228095_d_.getText().substring(0, this.field_228095_d_.getCursorPosition());
      int i = func_228121_a_(s);
      String s1 = s.substring(i).toLowerCase(Locale.ROOT);
      List<Suggestion> list = Lists.newArrayList();
      List<Suggestion> list1 = Lists.newArrayList();

      for(Suggestion suggestion : p_241575_1_.getList()) {
         if (!suggestion.getText().startsWith(s1) && !suggestion.getText().startsWith("minecraft:" + s1)) {
            list1.add(suggestion);
         } else {
            list.add(suggestion);
         }
      }

      list.addAll(list1);
      return list;
   }

   public void init() {
      String s = this.field_228095_d_.getText();
      if (this.field_228106_o_ != null && !this.field_228106_o_.getReader().getString().equals(s)) {
         this.field_228106_o_ = null;
      }

      if (!this.field_228110_s_) {
         this.field_228095_d_.setSuggestion((String)null);
         this.field_228108_q_ = null;
      }

      this.field_228103_l_.clear();
      StringReader stringreader = new StringReader(s);
      boolean flag = stringreader.canRead() && stringreader.peek() == '/';
      if (flag) {
         stringreader.skip();
      }

      boolean flag1 = this.field_228097_f_ || flag;
      int i = this.field_228095_d_.getCursorPosition();
      if (flag1) {
         CommandDispatcher<ISuggestionProvider> commanddispatcher = this.field_228093_b_.player.connection.getCommandDispatcher();
         if (this.field_228106_o_ == null) {
            this.field_228106_o_ = commanddispatcher.parse(stringreader, this.field_228093_b_.player.connection.getSuggestionProvider());
         }

         int j = this.field_228098_g_ ? stringreader.getCursor() : 1;
         if (i >= j && (this.field_228108_q_ == null || !this.field_228110_s_)) {
            this.field_228107_p_ = commanddispatcher.getCompletionSuggestions(this.field_228106_o_, i);
            this.field_228107_p_.thenRun(() -> {
               if (this.field_228107_p_.isDone()) {
                  this.func_228125_b_();
               }
            });
         }
      } else {
         String s1 = s.substring(0, i);
         int k = func_228121_a_(s1);
         Collection<String> collection = this.field_228093_b_.player.connection.getSuggestionProvider().getPlayerNames();
         this.field_228107_p_ = ISuggestionProvider.suggest(collection, new SuggestionsBuilder(s1, k));
      }

   }

   private static int func_228121_a_(String p_228121_0_) {
      if (Strings.isNullOrEmpty(p_228121_0_)) {
         return 0;
      } else {
         int i = 0;

         for(Matcher matcher = field_228092_a_.matcher(p_228121_0_); matcher.find(); i = matcher.end()) {
         }

         return i;
      }
   }

   private static IReorderingProcessor func_243255_a(CommandSyntaxException p_243255_0_) {
      ITextComponent itextcomponent = TextComponentUtils.toTextComponent(p_243255_0_.getRawMessage());
      String s = p_243255_0_.getContext();
      return s == null ? itextcomponent.func_241878_f() : (new TranslationTextComponent("command.context.parse_error", itextcomponent, p_243255_0_.getCursor(), s)).func_241878_f();
   }

   private void func_228125_b_() {
      if (this.field_228095_d_.getCursorPosition() == this.field_228095_d_.getText().length()) {
         if (this.field_228107_p_.join().isEmpty() && !this.field_228106_o_.getExceptions().isEmpty()) {
            int i = 0;

            for(Entry<CommandNode<ISuggestionProvider>, CommandSyntaxException> entry : this.field_228106_o_.getExceptions().entrySet()) {
               CommandSyntaxException commandsyntaxexception = entry.getValue();
               if (commandsyntaxexception.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                  ++i;
               } else {
                  this.field_228103_l_.add(func_243255_a(commandsyntaxexception));
               }
            }

            if (i > 0) {
               this.field_228103_l_.add(func_243255_a(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
            }
         } else if (this.field_228106_o_.getReader().canRead()) {
            this.field_228103_l_.add(func_243255_a(Commands.func_227481_a_(this.field_228106_o_)));
         }
      }

      this.field_228104_m_ = 0;
      this.field_228105_n_ = this.field_228094_c_.field_230708_k_;
      if (this.field_228103_l_.isEmpty()) {
         this.func_228120_a_(TextFormatting.GRAY);
      }

      this.field_228108_q_ = null;
      if (this.field_228109_r_ && this.field_228093_b_.gameSettings.autoSuggestCommands) {
         this.func_228128_b_(false);
      }

   }

   private void func_228120_a_(TextFormatting p_228120_1_) {
      CommandContextBuilder<ISuggestionProvider> commandcontextbuilder = this.field_228106_o_.getContext();
      SuggestionContext<ISuggestionProvider> suggestioncontext = commandcontextbuilder.findSuggestionContext(this.field_228095_d_.getCursorPosition());
      Map<CommandNode<ISuggestionProvider>, String> map = this.field_228093_b_.player.connection.getCommandDispatcher().getSmartUsage(suggestioncontext.parent, this.field_228093_b_.player.connection.getSuggestionProvider());
      List<IReorderingProcessor> list = Lists.newArrayList();
      int i = 0;
      Style style = Style.field_240709_b_.func_240712_a_(p_228120_1_);

      for(Entry<CommandNode<ISuggestionProvider>, String> entry : map.entrySet()) {
         if (!(entry.getKey() instanceof LiteralCommandNode)) {
            list.add(IReorderingProcessor.func_242239_a(entry.getValue(), style));
            i = Math.max(i, this.field_228096_e_.getStringWidth(entry.getValue()));
         }
      }

      if (!list.isEmpty()) {
         this.field_228103_l_.addAll(list);
         this.field_228104_m_ = MathHelper.clamp(this.field_228095_d_.func_195611_j(suggestioncontext.startPos), 0, this.field_228095_d_.func_195611_j(0) + this.field_228095_d_.getAdjustedWidth() - i);
         this.field_228105_n_ = i;
      }

   }

   private IReorderingProcessor func_228122_a_(String p_228122_1_, int p_228122_2_) {
      return this.field_228106_o_ != null ? func_228116_a_(this.field_228106_o_, p_228122_1_, p_228122_2_) : IReorderingProcessor.func_242239_a(p_228122_1_, Style.field_240709_b_);
   }

   @Nullable
   private static String func_228127_b_(String p_228127_0_, String p_228127_1_) {
      return p_228127_1_.startsWith(p_228127_0_) ? p_228127_1_.substring(p_228127_0_.length()) : null;
   }

   private static IReorderingProcessor func_228116_a_(ParseResults<ISuggestionProvider> p_228116_0_, String p_228116_1_, int p_228116_2_) {
      List<IReorderingProcessor> list = Lists.newArrayList();
      int i = 0;
      int j = -1;
      CommandContextBuilder<ISuggestionProvider> commandcontextbuilder = p_228116_0_.getContext().getLastChild();

      for(ParsedArgument<ISuggestionProvider, ?> parsedargument : commandcontextbuilder.getArguments().values()) {
         ++j;
         if (j >= field_243254_d.size()) {
            j = 0;
         }

         int k = Math.max(parsedargument.getRange().getStart() - p_228116_2_, 0);
         if (k >= p_228116_1_.length()) {
            break;
         }

         int l = Math.min(parsedargument.getRange().getEnd() - p_228116_2_, p_228116_1_.length());
         if (l > 0) {
            list.add(IReorderingProcessor.func_242239_a(p_228116_1_.substring(i, k), field_243253_c));
            list.add(IReorderingProcessor.func_242239_a(p_228116_1_.substring(k, l), field_243254_d.get(j)));
            i = l;
         }
      }

      if (p_228116_0_.getReader().canRead()) {
         int i1 = Math.max(p_228116_0_.getReader().getCursor() - p_228116_2_, 0);
         if (i1 < p_228116_1_.length()) {
            int j1 = Math.min(i1 + p_228116_0_.getReader().getRemainingLength(), p_228116_1_.length());
            list.add(IReorderingProcessor.func_242239_a(p_228116_1_.substring(i, i1), field_243253_c));
            list.add(IReorderingProcessor.func_242239_a(p_228116_1_.substring(i1, j1), field_243252_b));
            i = j1;
         }
      }

      list.add(IReorderingProcessor.func_242239_a(p_228116_1_.substring(i), field_243253_c));
      return IReorderingProcessor.func_242241_a(list);
   }

   public void func_238500_a_(MatrixStack p_238500_1_, int p_238500_2_, int p_238500_3_) {
      if (this.field_228108_q_ != null) {
         this.field_228108_q_.func_238501_a_(p_238500_1_, p_238500_2_, p_238500_3_);
      } else {
         int i = 0;

         for(IReorderingProcessor ireorderingprocessor : this.field_228103_l_) {
            int j = this.field_228101_j_ ? this.field_228094_c_.field_230709_l_ - 14 - 13 - 12 * i : 72 + 12 * i;
            AbstractGui.func_238467_a_(p_238500_1_, this.field_228104_m_ - 1, j, this.field_228104_m_ + this.field_228105_n_ + 1, j + 12, this.field_228102_k_);
            this.field_228096_e_.func_238407_a_(p_238500_1_, ireorderingprocessor, (float)this.field_228104_m_, (float)(j + 2), -1);
            ++i;
         }
      }

   }

   public String func_228129_c_() {
      return this.field_228108_q_ != null ? "\n" + this.field_228108_q_.func_228155_c_() : "";
   }

   @OnlyIn(Dist.CLIENT)
   public class Suggestions {
      private final Rectangle2d field_228138_b_;
      private final String field_228140_d_;
      private final List<Suggestion> field_241576_d_;
      private int field_228141_e_;
      private int field_228142_f_;
      private Vector2f field_228143_g_ = Vector2f.ZERO;
      private boolean field_228144_h_;
      private int field_228145_i_;

      private Suggestions(int p_i241247_2_, int p_i241247_3_, int p_i241247_4_, List<Suggestion> p_i241247_5_, boolean p_i241247_6_) {
         int i = p_i241247_2_ - 1;
         int j = CommandSuggestionHelper.this.field_228101_j_ ? p_i241247_3_ - 3 - Math.min(p_i241247_5_.size(), CommandSuggestionHelper.this.field_228100_i_) * 12 : p_i241247_3_;
         this.field_228138_b_ = new Rectangle2d(i, j, p_i241247_4_ + 1, Math.min(p_i241247_5_.size(), CommandSuggestionHelper.this.field_228100_i_) * 12);
         this.field_228140_d_ = CommandSuggestionHelper.this.field_228095_d_.getText();
         this.field_228145_i_ = p_i241247_6_ ? -1 : 0;
         this.field_241576_d_ = p_i241247_5_;
         this.func_228153_b_(0);
      }

      public void func_238501_a_(MatrixStack p_238501_1_, int p_238501_2_, int p_238501_3_) {
         int i = Math.min(this.field_241576_d_.size(), CommandSuggestionHelper.this.field_228100_i_);
         int j = -5592406;
         boolean flag = this.field_228141_e_ > 0;
         boolean flag1 = this.field_241576_d_.size() > this.field_228141_e_ + i;
         boolean flag2 = flag || flag1;
         boolean flag3 = this.field_228143_g_.x != (float)p_238501_2_ || this.field_228143_g_.y != (float)p_238501_3_;
         if (flag3) {
            this.field_228143_g_ = new Vector2f((float)p_238501_2_, (float)p_238501_3_);
         }

         if (flag2) {
            AbstractGui.func_238467_a_(p_238501_1_, this.field_228138_b_.getX(), this.field_228138_b_.getY() - 1, this.field_228138_b_.getX() + this.field_228138_b_.getWidth(), this.field_228138_b_.getY(), CommandSuggestionHelper.this.field_228102_k_);
            AbstractGui.func_238467_a_(p_238501_1_, this.field_228138_b_.getX(), this.field_228138_b_.getY() + this.field_228138_b_.getHeight(), this.field_228138_b_.getX() + this.field_228138_b_.getWidth(), this.field_228138_b_.getY() + this.field_228138_b_.getHeight() + 1, CommandSuggestionHelper.this.field_228102_k_);
            if (flag) {
               for(int k = 0; k < this.field_228138_b_.getWidth(); ++k) {
                  if (k % 2 == 0) {
                     AbstractGui.func_238467_a_(p_238501_1_, this.field_228138_b_.getX() + k, this.field_228138_b_.getY() - 1, this.field_228138_b_.getX() + k + 1, this.field_228138_b_.getY(), -1);
                  }
               }
            }

            if (flag1) {
               for(int i1 = 0; i1 < this.field_228138_b_.getWidth(); ++i1) {
                  if (i1 % 2 == 0) {
                     AbstractGui.func_238467_a_(p_238501_1_, this.field_228138_b_.getX() + i1, this.field_228138_b_.getY() + this.field_228138_b_.getHeight(), this.field_228138_b_.getX() + i1 + 1, this.field_228138_b_.getY() + this.field_228138_b_.getHeight() + 1, -1);
                  }
               }
            }
         }

         boolean flag4 = false;

         for(int l = 0; l < i; ++l) {
            Suggestion suggestion = this.field_241576_d_.get(l + this.field_228141_e_);
            AbstractGui.func_238467_a_(p_238501_1_, this.field_228138_b_.getX(), this.field_228138_b_.getY() + 12 * l, this.field_228138_b_.getX() + this.field_228138_b_.getWidth(), this.field_228138_b_.getY() + 12 * l + 12, CommandSuggestionHelper.this.field_228102_k_);
            if (p_238501_2_ > this.field_228138_b_.getX() && p_238501_2_ < this.field_228138_b_.getX() + this.field_228138_b_.getWidth() && p_238501_3_ > this.field_228138_b_.getY() + 12 * l && p_238501_3_ < this.field_228138_b_.getY() + 12 * l + 12) {
               if (flag3) {
                  this.func_228153_b_(l + this.field_228141_e_);
               }

               flag4 = true;
            }

            CommandSuggestionHelper.this.field_228096_e_.func_238405_a_(p_238501_1_, suggestion.getText(), (float)(this.field_228138_b_.getX() + 1), (float)(this.field_228138_b_.getY() + 2 + 12 * l), l + this.field_228141_e_ == this.field_228142_f_ ? -256 : -5592406);
         }

         if (flag4) {
            Message message = this.field_241576_d_.get(this.field_228142_f_).getTooltip();
            if (message != null) {
               CommandSuggestionHelper.this.field_228094_c_.func_238652_a_(p_238501_1_, TextComponentUtils.toTextComponent(message), p_238501_2_, p_238501_3_);
            }
         }

      }

      public boolean func_228150_a_(int p_228150_1_, int p_228150_2_, int p_228150_3_) {
         if (!this.field_228138_b_.contains(p_228150_1_, p_228150_2_)) {
            return false;
         } else {
            int i = (p_228150_2_ - this.field_228138_b_.getY()) / 12 + this.field_228141_e_;
            if (i >= 0 && i < this.field_241576_d_.size()) {
               this.func_228153_b_(i);
               this.func_228146_a_();
            }

            return true;
         }
      }

      public boolean func_228147_a_(double p_228147_1_) {
         int i = (int)(CommandSuggestionHelper.this.field_228093_b_.mouseHelper.getMouseX() * (double)CommandSuggestionHelper.this.field_228093_b_.getMainWindow().getScaledWidth() / (double)CommandSuggestionHelper.this.field_228093_b_.getMainWindow().getWidth());
         int j = (int)(CommandSuggestionHelper.this.field_228093_b_.mouseHelper.getMouseY() * (double)CommandSuggestionHelper.this.field_228093_b_.getMainWindow().getScaledHeight() / (double)CommandSuggestionHelper.this.field_228093_b_.getMainWindow().getHeight());
         if (this.field_228138_b_.contains(i, j)) {
            this.field_228141_e_ = MathHelper.clamp((int)((double)this.field_228141_e_ - p_228147_1_), 0, Math.max(this.field_241576_d_.size() - CommandSuggestionHelper.this.field_228100_i_, 0));
            return true;
         } else {
            return false;
         }
      }

      public boolean func_228154_b_(int p_228154_1_, int p_228154_2_, int p_228154_3_) {
         if (p_228154_1_ == 265) {
            this.func_228148_a_(-1);
            this.field_228144_h_ = false;
            return true;
         } else if (p_228154_1_ == 264) {
            this.func_228148_a_(1);
            this.field_228144_h_ = false;
            return true;
         } else if (p_228154_1_ == 258) {
            if (this.field_228144_h_) {
               this.func_228148_a_(Screen.func_231173_s_() ? -1 : 1);
            }

            this.func_228146_a_();
            return true;
         } else if (p_228154_1_ == 256) {
            this.func_228152_b_();
            return true;
         } else {
            return false;
         }
      }

      public void func_228148_a_(int p_228148_1_) {
         this.func_228153_b_(this.field_228142_f_ + p_228148_1_);
         int i = this.field_228141_e_;
         int j = this.field_228141_e_ + CommandSuggestionHelper.this.field_228100_i_ - 1;
         if (this.field_228142_f_ < i) {
            this.field_228141_e_ = MathHelper.clamp(this.field_228142_f_, 0, Math.max(this.field_241576_d_.size() - CommandSuggestionHelper.this.field_228100_i_, 0));
         } else if (this.field_228142_f_ > j) {
            this.field_228141_e_ = MathHelper.clamp(this.field_228142_f_ + CommandSuggestionHelper.this.field_228099_h_ - CommandSuggestionHelper.this.field_228100_i_, 0, Math.max(this.field_241576_d_.size() - CommandSuggestionHelper.this.field_228100_i_, 0));
         }

      }

      public void func_228153_b_(int p_228153_1_) {
         this.field_228142_f_ = p_228153_1_;
         if (this.field_228142_f_ < 0) {
            this.field_228142_f_ += this.field_241576_d_.size();
         }

         if (this.field_228142_f_ >= this.field_241576_d_.size()) {
            this.field_228142_f_ -= this.field_241576_d_.size();
         }

         Suggestion suggestion = this.field_241576_d_.get(this.field_228142_f_);
         CommandSuggestionHelper.this.field_228095_d_.setSuggestion(CommandSuggestionHelper.func_228127_b_(CommandSuggestionHelper.this.field_228095_d_.getText(), suggestion.apply(this.field_228140_d_)));
         if (NarratorChatListener.INSTANCE.isActive() && this.field_228145_i_ != this.field_228142_f_) {
            NarratorChatListener.INSTANCE.say(this.func_228155_c_());
         }

      }

      public void func_228146_a_() {
         Suggestion suggestion = this.field_241576_d_.get(this.field_228142_f_);
         CommandSuggestionHelper.this.field_228110_s_ = true;
         CommandSuggestionHelper.this.field_228095_d_.setText(suggestion.apply(this.field_228140_d_));
         int i = suggestion.getRange().getStart() + suggestion.getText().length();
         CommandSuggestionHelper.this.field_228095_d_.clampCursorPosition(i);
         CommandSuggestionHelper.this.field_228095_d_.setSelectionPos(i);
         this.func_228153_b_(this.field_228142_f_);
         CommandSuggestionHelper.this.field_228110_s_ = false;
         this.field_228144_h_ = true;
      }

      private String func_228155_c_() {
         this.field_228145_i_ = this.field_228142_f_;
         Suggestion suggestion = this.field_241576_d_.get(this.field_228142_f_);
         Message message = suggestion.getTooltip();
         return message != null ? I18n.format("narration.suggestion.tooltip", this.field_228142_f_ + 1, this.field_241576_d_.size(), suggestion.getText(), message.getString()) : I18n.format("narration.suggestion", this.field_228142_f_ + 1, this.field_241576_d_.size(), suggestion.getText());
      }

      public void func_228152_b_() {
         CommandSuggestionHelper.this.field_228108_q_ = null;
      }
   }
}