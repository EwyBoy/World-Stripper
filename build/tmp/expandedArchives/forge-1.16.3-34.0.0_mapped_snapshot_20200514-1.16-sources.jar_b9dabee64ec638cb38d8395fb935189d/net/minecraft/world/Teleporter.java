package net.minecraft.world;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class Teleporter implements net.minecraftforge.common.util.ITeleporter {
   protected final ServerWorld world;

   public Teleporter(ServerWorld worldIn) {
      this.world = worldIn;
   }

   public Optional<TeleportationRepositioner.Result> func_242957_a(BlockPos p_242957_1_, boolean p_242957_2_) {
      PointOfInterestManager pointofinterestmanager = this.world.getPointOfInterestManager();
      int i = p_242957_2_ ? 16 : 128;
      pointofinterestmanager.ensureLoadedAndValid(this.world, p_242957_1_, i);
      Optional<PointOfInterest> optional = pointofinterestmanager.getInSquare((p_242952_0_) -> {
         return p_242952_0_ == PointOfInterestType.NETHER_PORTAL;
      }, p_242957_1_, i, PointOfInterestManager.Status.ANY).sorted(Comparator.<PointOfInterest>comparingDouble((p_242954_1_) -> {
         return p_242954_1_.getPos().distanceSq(p_242957_1_);
      }).thenComparingInt((p_242959_0_) -> {
         return p_242959_0_.getPos().getY();
      })).filter((p_242958_1_) -> {
         return this.world.getBlockState(p_242958_1_.getPos()).func_235901_b_(BlockStateProperties.HORIZONTAL_AXIS);
      }).findFirst();
      return optional.map((p_242951_1_) -> {
         BlockPos blockpos = p_242951_1_.getPos();
         this.world.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
         BlockState blockstate = this.world.getBlockState(blockpos);
         return TeleportationRepositioner.func_243676_a(blockpos, blockstate.get(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, (p_242953_2_) -> {
            return this.world.getBlockState(p_242953_2_) == blockstate;
         });
      });
   }

   public Optional<TeleportationRepositioner.Result> func_242956_a(BlockPos p_242956_1_, Direction.Axis p_242956_2_) {
      Direction direction = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, p_242956_2_);
      double d0 = -1.0D;
      BlockPos blockpos = null;
      double d1 = -1.0D;
      BlockPos blockpos1 = null;
      WorldBorder worldborder = this.world.getWorldBorder();
      int i = this.world.func_234938_ad_() - 1;
      BlockPos.Mutable blockpos$mutable = p_242956_1_.func_239590_i_();

      for(BlockPos.Mutable blockpos$mutable1 : BlockPos.func_243514_a(p_242956_1_, 16, Direction.EAST, Direction.SOUTH)) {
         int j = Math.min(i, this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos$mutable1.getX(), blockpos$mutable1.getZ()));
         int k = 1;
         if (worldborder.contains(blockpos$mutable1) && worldborder.contains(blockpos$mutable1.move(direction, 1))) {
            blockpos$mutable1.move(direction.getOpposite(), 1);

            for(int l = j; l >= 0; --l) {
               blockpos$mutable1.setY(l);
               if (this.world.isAirBlock(blockpos$mutable1)) {
                  int i1;
                  for(i1 = l; l > 0 && this.world.isAirBlock(blockpos$mutable1.move(Direction.DOWN)); --l) {
                  }

                  if (l + 4 <= i) {
                     int j1 = i1 - l;
                     if (j1 <= 0 || j1 >= 3) {
                        blockpos$mutable1.setY(l);
                        if (this.func_242955_a(blockpos$mutable1, blockpos$mutable, direction, 0)) {
                           double d2 = p_242956_1_.distanceSq(blockpos$mutable1);
                           if (this.func_242955_a(blockpos$mutable1, blockpos$mutable, direction, -1) && this.func_242955_a(blockpos$mutable1, blockpos$mutable, direction, 1) && (d0 == -1.0D || d0 > d2)) {
                              d0 = d2;
                              blockpos = blockpos$mutable1.toImmutable();
                           }

                           if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
                              d1 = d2;
                              blockpos1 = blockpos$mutable1.toImmutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (d0 == -1.0D && d1 != -1.0D) {
         blockpos = blockpos1;
         d0 = d1;
      }

      if (d0 == -1.0D) {
         blockpos = (new BlockPos(p_242956_1_.getX(), MathHelper.clamp(p_242956_1_.getY(), 70, this.world.func_234938_ad_() - 10), p_242956_1_.getZ())).toImmutable();
         Direction direction1 = direction.rotateY();
         if (!worldborder.contains(blockpos)) {
            return Optional.empty();
         }

         for(int l1 = -1; l1 < 2; ++l1) {
            for(int k2 = 0; k2 < 2; ++k2) {
               for(int i3 = -1; i3 < 3; ++i3) {
                  BlockState blockstate1 = i3 < 0 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
                  blockpos$mutable.func_239621_a_(blockpos, k2 * direction.getXOffset() + l1 * direction1.getXOffset(), i3, k2 * direction.getZOffset() + l1 * direction1.getZOffset());
                  this.world.setBlockState(blockpos$mutable, blockstate1);
               }
            }
         }
      }

      for(int k1 = -1; k1 < 3; ++k1) {
         for(int i2 = -1; i2 < 4; ++i2) {
            if (k1 == -1 || k1 == 2 || i2 == -1 || i2 == 3) {
               blockpos$mutable.func_239621_a_(blockpos, k1 * direction.getXOffset(), i2, k1 * direction.getZOffset());
               this.world.setBlockState(blockpos$mutable, Blocks.OBSIDIAN.getDefaultState(), 3);
            }
         }
      }

      BlockState blockstate = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, p_242956_2_);

      for(int j2 = 0; j2 < 2; ++j2) {
         for(int l2 = 0; l2 < 3; ++l2) {
            blockpos$mutable.func_239621_a_(blockpos, j2 * direction.getXOffset(), l2, j2 * direction.getZOffset());
            this.world.setBlockState(blockpos$mutable, blockstate, 18);
         }
      }

      return Optional.of(new TeleportationRepositioner.Result(blockpos.toImmutable(), 2, 3));
   }

   private boolean func_242955_a(BlockPos p_242955_1_, BlockPos.Mutable p_242955_2_, Direction p_242955_3_, int p_242955_4_) {
      Direction direction = p_242955_3_.rotateY();

      for(int i = -1; i < 3; ++i) {
         for(int j = -1; j < 4; ++j) {
            p_242955_2_.func_239621_a_(p_242955_1_, p_242955_3_.getXOffset() * i + direction.getXOffset() * p_242955_4_, j, p_242955_3_.getZOffset() * i + direction.getZOffset() * p_242955_4_);
            if (j < 0 && !this.world.getBlockState(p_242955_2_).getMaterial().isSolid()) {
               return false;
            }

            if (j >= 0 && !this.world.isAirBlock(p_242955_2_)) {
               return false;
            }
         }
      }

      return true;
   }
}