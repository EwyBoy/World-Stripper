package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.command.CommandSource;
import net.minecraft.command.FunctionObject;
import net.minecraft.resources.FunctionReloader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;

public class FunctionManager {
   private static final ResourceLocation TICK_TAG_ID = new ResourceLocation("tick");
   private static final ResourceLocation LOAD_TAG_ID = new ResourceLocation("load");
   private final MinecraftServer server;
   private boolean isExecuting;
   private final ArrayDeque<FunctionManager.QueuedCommand> commandQueue = new ArrayDeque<>();
   private final List<FunctionManager.QueuedCommand> commandChain = Lists.newArrayList();
   private final List<FunctionObject> tickFunctions = Lists.newArrayList();
   private boolean loadFunctionsRun;
   private FunctionReloader field_240944_i_;

   public FunctionManager(MinecraftServer p_i232597_1_, FunctionReloader p_i232597_2_) {
      this.server = p_i232597_1_;
      this.field_240944_i_ = p_i232597_2_;
      this.func_240948_b_(p_i232597_2_);
   }

   public int getMaxCommandChainLength() {
      return this.server.getGameRules().getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH);
   }

   public CommandDispatcher<CommandSource> getCommandDispatcher() {
      return this.server.getCommandManager().getDispatcher();
   }

   public void tick() {
      this.func_240945_a_(this.tickFunctions, TICK_TAG_ID);
      if (this.loadFunctionsRun) {
         this.loadFunctionsRun = false;
         Collection<FunctionObject> collection = this.field_240944_i_.func_240942_b_().func_241834_b(LOAD_TAG_ID).func_230236_b_();
         this.func_240945_a_(collection, LOAD_TAG_ID);
      }

   }

   private void func_240945_a_(Collection<FunctionObject> p_240945_1_, ResourceLocation p_240945_2_) {
      this.server.getProfiler().startSection(p_240945_2_::toString);

      for(FunctionObject functionobject : p_240945_1_) {
         this.execute(functionobject, this.getCommandSource());
      }

      this.server.getProfiler().endSection();
   }

   public int execute(FunctionObject p_195447_1_, CommandSource p_195447_2_) {
      int i = this.getMaxCommandChainLength();
      if (this.isExecuting) {
         if (this.commandQueue.size() + this.commandChain.size() < i) {
            this.commandChain.add(new FunctionManager.QueuedCommand(this, p_195447_2_, new FunctionObject.FunctionEntry(p_195447_1_)));
         }

         return 0;
      } else {
         try {
            this.isExecuting = true;
            int j = 0;
            FunctionObject.IEntry[] afunctionobject$ientry = p_195447_1_.getEntries();

            for(int k = afunctionobject$ientry.length - 1; k >= 0; --k) {
               this.commandQueue.push(new FunctionManager.QueuedCommand(this, p_195447_2_, afunctionobject$ientry[k]));
            }

            while(!this.commandQueue.isEmpty()) {
               try {
                  FunctionManager.QueuedCommand functionmanager$queuedcommand = this.commandQueue.removeFirst();
                  this.server.getProfiler().startSection(functionmanager$queuedcommand::toString);
                  functionmanager$queuedcommand.execute(this.commandQueue, i);
                  if (!this.commandChain.isEmpty()) {
                     Lists.reverse(this.commandChain).forEach(this.commandQueue::addFirst);
                     this.commandChain.clear();
                  }
               } finally {
                  this.server.getProfiler().endSection();
               }

               ++j;
               if (j >= i) {
                  return j;
               }
            }

            return j;
         } finally {
            this.commandQueue.clear();
            this.commandChain.clear();
            this.isExecuting = false;
         }
      }
   }

   public void func_240946_a_(FunctionReloader p_240946_1_) {
      this.field_240944_i_ = p_240946_1_;
      this.func_240948_b_(p_240946_1_);
   }

   private void func_240948_b_(FunctionReloader p_240948_1_) {
      this.tickFunctions.clear();
      this.tickFunctions.addAll(p_240948_1_.func_240942_b_().func_241834_b(TICK_TAG_ID).func_230236_b_());
      this.loadFunctionsRun = true;
   }

   public CommandSource getCommandSource() {
      return this.server.getCommandSource().withPermissionLevel(2).withFeedbackDisabled();
   }

   public Optional<FunctionObject> get(ResourceLocation p_215361_1_) {
      return this.field_240944_i_.func_240940_a_(p_215361_1_);
   }

   public ITag<FunctionObject> func_240947_b_(ResourceLocation p_240947_1_) {
      return this.field_240944_i_.func_240943_b_(p_240947_1_);
   }

   public Iterable<ResourceLocation> func_240949_f_() {
      return this.field_240944_i_.func_240931_a_().keySet();
   }

   public Iterable<ResourceLocation> func_240950_g_() {
      return this.field_240944_i_.func_240942_b_().getRegisteredTags();
   }

   public static class QueuedCommand {
      private final FunctionManager functionManager;
      private final CommandSource sender;
      private final FunctionObject.IEntry entry;

      public QueuedCommand(FunctionManager p_i48018_1_, CommandSource p_i48018_2_, FunctionObject.IEntry p_i48018_3_) {
         this.functionManager = p_i48018_1_;
         this.sender = p_i48018_2_;
         this.entry = p_i48018_3_;
      }

      public void execute(ArrayDeque<FunctionManager.QueuedCommand> commandQueue, int maxCommandChainLength) {
         try {
            this.entry.execute(this.functionManager, this.sender, commandQueue, maxCommandChainLength);
         } catch (Throwable throwable) {
         }

      }

      public String toString() {
         return this.entry.toString();
      }
   }
}