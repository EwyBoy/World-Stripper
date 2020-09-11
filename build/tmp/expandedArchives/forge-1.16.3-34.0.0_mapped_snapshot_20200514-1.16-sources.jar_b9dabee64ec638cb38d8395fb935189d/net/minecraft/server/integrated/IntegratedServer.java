package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.LanServerPingThread;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.Snooper;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.CryptManager;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.GameType;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class IntegratedServer extends MinecraftServer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft mc;
   private boolean isGamePaused;
   private int serverPort = -1;
   private LanServerPingThread lanServerPing;
   private UUID playerUuid;

   public IntegratedServer(Thread p_i232494_1_, Minecraft p_i232494_2_, DynamicRegistries.Impl p_i232494_3_, SaveFormat.LevelSave p_i232494_4_, ResourcePackList p_i232494_5_, DataPackRegistries p_i232494_6_, IServerConfiguration p_i232494_7_, MinecraftSessionService p_i232494_8_, GameProfileRepository p_i232494_9_, PlayerProfileCache p_i232494_10_, IChunkStatusListenerFactory p_i232494_11_) {
      super(p_i232494_1_, p_i232494_3_, p_i232494_4_, p_i232494_7_, p_i232494_5_, p_i232494_2_.getProxy(), p_i232494_2_.getDataFixer(), p_i232494_6_, p_i232494_8_, p_i232494_9_, p_i232494_10_, p_i232494_11_);
      this.setServerOwner(p_i232494_2_.getSession().getUsername());
      this.setDemo(p_i232494_2_.isDemo());
      this.setBuildLimit(256);
      this.setPlayerList(new IntegratedPlayerList(this, this.field_240767_f_, this.field_240766_e_));
      this.mc = p_i232494_2_;
   }

   /**
    * Initialises the server and starts it.
    */
   public boolean init() {
      LOGGER.info("Starting integrated minecraft server version " + SharedConstants.getVersion().getName());
      this.setOnlineMode(true);
      this.setAllowPvp(true);
      this.setAllowFlight(true);
      LOGGER.info("Generating keypair");
      this.setKeyPair(CryptManager.generateKeyPair());
      if (!net.minecraftforge.fml.server.ServerLifecycleHooks.handleServerAboutToStart(this)) return false;
      this.func_240800_l__();
      this.setMOTD(this.getServerOwner() + " - " + this.func_240793_aU_().getWorldName());
      return net.minecraftforge.fml.server.ServerLifecycleHooks.handleServerStarting(this);
   }

   /**
    * Main function called by run() every loop.
    */
   public void tick(BooleanSupplier hasTimeLeft) {
      boolean flag = this.isGamePaused;
      this.isGamePaused = Minecraft.getInstance().getConnection() != null && Minecraft.getInstance().isGamePaused();
      IProfiler iprofiler = this.getProfiler();
      if (!flag && this.isGamePaused) {
         iprofiler.startSection("autoSave");
         LOGGER.info("Saving and pausing game...");
         this.getPlayerList().saveAllPlayerData();
         this.save(false, false, false);
         iprofiler.endSection();
      }

      if (!this.isGamePaused) {
         super.tick(hasTimeLeft);
         int i = Math.max(2, this.mc.gameSettings.renderDistanceChunks + -1);
         if (i != this.getPlayerList().getViewDistance()) {
            LOGGER.info("Changing view distance to {}, from {}", i, this.getPlayerList().getViewDistance());
            this.getPlayerList().setViewDistance(i);
         }

      }
   }

   public boolean allowLoggingRcon() {
      return true;
   }

   public boolean allowLogging() {
      return true;
   }

   public File getDataDirectory() {
      return this.mc.gameDir;
   }

   public boolean isDedicatedServer() {
      return false;
   }

   public int func_241871_k() {
      return 0;
   }

   /**
    * Get if native transport should be used. Native transport means linux server performance improvements and optimized
    * packet sending/receiving on linux
    */
   public boolean shouldUseNativeTransport() {
      return false;
   }

   /**
    * Called on exit from the main run() loop.
    */
   public void finalTick(CrashReport report) {
      this.mc.crashed(report);
   }

   /**
    * Adds the server info, including from theWorldServer, to the crash report.
    */
   public CrashReport addServerInfoToCrashReport(CrashReport report) {
      report = super.addServerInfoToCrashReport(report);
      report.getCategory().addDetail("Type", "Integrated Server (map_client.txt)");
      report.getCategory().addDetail("Is Modded", () -> {
         return this.func_230045_q_().orElse("Probably not. Jar signature remains and both client + server brands are untouched.");
      });
      return report;
   }

   public Optional<String> func_230045_q_() {
      String s = ClientBrandRetriever.getClientModName();
      if (!s.equals("vanilla")) {
         return Optional.of("Definitely; Client brand changed to '" + s + "'");
      } else {
         s = this.getServerModName();
         if (!"vanilla".equals(s)) {
            return Optional.of("Definitely; Server brand changed to '" + s + "'");
         } else {
            return Minecraft.class.getSigners() == null ? Optional.of("Very likely; Jar signature invalidated") : Optional.empty();
         }
      }
   }

   public void fillSnooper(Snooper snooper) {
      super.fillSnooper(snooper);
      snooper.addClientStat("snooper_partner", this.mc.getSnooper().getUniqueID());
   }

   public boolean shareToLAN(GameType gameMode, boolean cheats, int port) {
      try {
         this.getNetworkSystem().addEndpoint((InetAddress)null, port);
         LOGGER.info("Started serving on {}", (int)port);
         this.serverPort = port;
         this.lanServerPing = new LanServerPingThread(this.getMOTD(), port + "");
         this.lanServerPing.start();
         this.getPlayerList().setGameType(gameMode);
         this.getPlayerList().setCommandsAllowedForAll(cheats);
         int i = this.getPermissionLevel(this.mc.player.getGameProfile());
         this.mc.player.setPermissionLevel(i);

         for(ServerPlayerEntity serverplayerentity : this.getPlayerList().getPlayers()) {
            this.getCommandManager().send(serverplayerentity);
         }

         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   /**
    * Saves all necessary data as preparation for stopping the server.
    */
   public void stopServer() {
      super.stopServer();
      if (this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }

   }

   /**
    * Sets the serverRunning variable to false, in order to get the server to shut down.
    */
   public void initiateShutdown(boolean waitForServer) {
      if (isServerRunning())
      this.runImmediately(() -> {
         for(ServerPlayerEntity serverplayerentity : Lists.newArrayList(this.getPlayerList().getPlayers())) {
            if (!serverplayerentity.getUniqueID().equals(this.playerUuid)) {
               this.getPlayerList().playerLoggedOut(serverplayerentity);
            }
         }

      });
      super.initiateShutdown(waitForServer);
      if (this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }

   }

   /**
    * Returns true if this integrated server is open to LAN
    */
   public boolean getPublic() {
      return this.serverPort > -1;
   }

   /**
    * Gets serverPort.
    */
   public int getServerPort() {
      return this.serverPort;
   }

   /**
    * Sets the game type for all worlds.
    */
   public void setGameType(GameType gameMode) {
      super.setGameType(gameMode);
      this.getPlayerList().setGameType(gameMode);
   }

   /**
    * Return whether command blocks are enabled.
    */
   public boolean isCommandBlockEnabled() {
      return true;
   }

   public int getOpPermissionLevel() {
      return 2;
   }

   public int getFunctionLevel() {
      return 2;
   }

   public void setPlayerUuid(UUID uuid) {
      this.playerUuid = uuid;
   }

   public boolean isServerOwner(GameProfile profileIn) {
      return profileIn.getName().equalsIgnoreCase(this.getServerOwner());
   }

   public int func_230512_b_(int p_230512_1_) {
      return (int)(this.mc.gameSettings.field_238329_c_ * (float)p_230512_1_);
   }

   public boolean func_230540_aS_() {
      return this.mc.gameSettings.field_241568_aS_;
   }
}