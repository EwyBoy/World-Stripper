package net.minecraft.network.play.server;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SJoinGamePacket implements IPacket<IClientPlayNetHandler> {
   private int playerId;
   /** First 8 bytes of the SHA-256 hash of the world's seed */
   private long hashedSeed;
   private boolean hardcoreMode;
   private GameType gameType;
   private GameType field_241785_e_;
   private Set<RegistryKey<World>> field_240811_e_;
   private DynamicRegistries.Impl field_240812_f_;
   private DimensionType field_240813_g_;
   private RegistryKey<World> dimension;
   private int maxPlayers;
   private int viewDistance;
   private boolean reducedDebugInfo;
   /** Set to false when the doImmediateRespawn gamerule is true */
   private boolean enableRespawnScreen;
   private boolean field_240814_m_;
   private boolean field_240815_n_;

   public SJoinGamePacket() {
   }

   public SJoinGamePacket(int p_i242082_1_, GameType p_i242082_2_, GameType p_i242082_3_, long p_i242082_4_, boolean p_i242082_6_, Set<RegistryKey<World>> p_i242082_7_, DynamicRegistries.Impl p_i242082_8_, DimensionType p_i242082_9_, RegistryKey<World> p_i242082_10_, int p_i242082_11_, int p_i242082_12_, boolean p_i242082_13_, boolean p_i242082_14_, boolean p_i242082_15_, boolean p_i242082_16_) {
      this.playerId = p_i242082_1_;
      this.field_240811_e_ = p_i242082_7_;
      this.field_240812_f_ = p_i242082_8_;
      this.field_240813_g_ = p_i242082_9_;
      this.dimension = p_i242082_10_;
      this.hashedSeed = p_i242082_4_;
      this.gameType = p_i242082_2_;
      this.field_241785_e_ = p_i242082_3_;
      this.maxPlayers = p_i242082_11_;
      this.hardcoreMode = p_i242082_6_;
      this.viewDistance = p_i242082_12_;
      this.reducedDebugInfo = p_i242082_13_;
      this.enableRespawnScreen = p_i242082_14_;
      this.field_240814_m_ = p_i242082_15_;
      this.field_240815_n_ = p_i242082_16_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.playerId = buf.readInt();
      this.hardcoreMode = buf.readBoolean();
      this.gameType = GameType.getByID(buf.readByte());
      this.field_241785_e_ = GameType.getByID(buf.readByte());
      int i = buf.readVarInt();
      this.field_240811_e_ = Sets.newHashSet();

      for(int j = 0; j < i; ++j) {
         this.field_240811_e_.add(RegistryKey.func_240903_a_(Registry.field_239699_ae_, buf.readResourceLocation()));
      }

      this.field_240812_f_ = buf.func_240628_a_(DynamicRegistries.Impl.field_243626_a);
      this.field_240813_g_ = buf.func_240628_a_(DimensionType.field_236002_f_).get();
      this.dimension = RegistryKey.func_240903_a_(Registry.field_239699_ae_, buf.readResourceLocation());
      this.hashedSeed = buf.readLong();
      this.maxPlayers = buf.readVarInt();
      this.viewDistance = buf.readVarInt();
      this.reducedDebugInfo = buf.readBoolean();
      this.enableRespawnScreen = buf.readBoolean();
      this.field_240814_m_ = buf.readBoolean();
      this.field_240815_n_ = buf.readBoolean();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeInt(this.playerId);
      buf.writeBoolean(this.hardcoreMode);
      buf.writeByte(this.gameType.getID());
      buf.writeByte(this.field_241785_e_.getID());
      buf.writeVarInt(this.field_240811_e_.size());

      for(RegistryKey<World> registrykey : this.field_240811_e_) {
         buf.writeResourceLocation(registrykey.func_240901_a_());
      }

      buf.func_240629_a_(DynamicRegistries.Impl.field_243626_a, this.field_240812_f_);
      buf.func_240629_a_(DimensionType.field_236002_f_, () -> {
         return this.field_240813_g_;
      });
      buf.writeResourceLocation(this.dimension.func_240901_a_());
      buf.writeLong(this.hashedSeed);
      buf.writeVarInt(this.maxPlayers);
      buf.writeVarInt(this.viewDistance);
      buf.writeBoolean(this.reducedDebugInfo);
      buf.writeBoolean(this.enableRespawnScreen);
      buf.writeBoolean(this.field_240814_m_);
      buf.writeBoolean(this.field_240815_n_);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleJoinGame(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int getPlayerId() {
      return this.playerId;
   }

   /**
    * get value
    */
   @OnlyIn(Dist.CLIENT)
   public long getHashedSeed() {
      return this.hashedSeed;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isHardcoreMode() {
      return this.hardcoreMode;
   }

   @OnlyIn(Dist.CLIENT)
   public GameType getGameType() {
      return this.gameType;
   }

   @OnlyIn(Dist.CLIENT)
   public GameType func_241786_f_() {
      return this.field_241785_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public Set<RegistryKey<World>> func_240816_f_() {
      return this.field_240811_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public DynamicRegistries func_240817_g_() {
      return this.field_240812_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionType func_244297_i() {
      return this.field_240813_g_;
   }

   @OnlyIn(Dist.CLIENT)
   public RegistryKey<World> func_240819_i_() {
      return this.dimension;
   }

   @OnlyIn(Dist.CLIENT)
   public int getViewDistance() {
      return this.viewDistance;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isReducedDebugInfo() {
      return this.reducedDebugInfo;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_229743_k_() {
      return this.enableRespawnScreen;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_240820_n_() {
      return this.field_240814_m_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_240821_o_() {
      return this.field_240815_n_;
   }
}