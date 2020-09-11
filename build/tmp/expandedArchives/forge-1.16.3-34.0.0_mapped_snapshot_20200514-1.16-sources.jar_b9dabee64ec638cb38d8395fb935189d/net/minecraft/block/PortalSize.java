package net.minecraft.block;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.EntitySize;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

public class PortalSize {
   private static final AbstractBlock.IPositionPredicate field_242962_a = (p_242966_0_, p_242966_1_, p_242966_2_) -> {
      return p_242966_0_.isPortalFrame(p_242966_1_, p_242966_2_);
   };
   private final IWorld world;
   private final Direction.Axis axis;
   private final Direction rightDir;
   private int portalBlockCount;
   @Nullable
   private BlockPos bottomLeft;
   private int height;
   private int width;

   public static Optional<PortalSize> func_242964_a(IWorld p_242964_0_, BlockPos p_242964_1_, Direction.Axis p_242964_2_) {
      return func_242965_a(p_242964_0_, p_242964_1_, (p_242968_0_) -> {
         return p_242968_0_.isValid() && p_242968_0_.portalBlockCount == 0;
      }, p_242964_2_);
   }

   public static Optional<PortalSize> func_242965_a(IWorld p_242965_0_, BlockPos p_242965_1_, Predicate<PortalSize> p_242965_2_, Direction.Axis p_242965_3_) {
      Optional<PortalSize> optional = Optional.of(new PortalSize(p_242965_0_, p_242965_1_, p_242965_3_)).filter(p_242965_2_);
      if (optional.isPresent()) {
         return optional;
      } else {
         Direction.Axis direction$axis = p_242965_3_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
         return Optional.of(new PortalSize(p_242965_0_, p_242965_1_, direction$axis)).filter(p_242965_2_);
      }
   }

   public PortalSize(IWorld worldIn, BlockPos pos, Direction.Axis axisIn) {
      this.world = worldIn;
      this.axis = axisIn;
      this.rightDir = axisIn == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
      this.bottomLeft = this.func_242971_a(pos);
      if (this.bottomLeft == null) {
         this.bottomLeft = pos;
         this.width = 1;
         this.height = 1;
      } else {
         this.width = this.func_242974_d();
         if (this.width > 0) {
            this.height = this.func_242975_e();
         }
      }

   }

   @Nullable
   private BlockPos func_242971_a(BlockPos p_242971_1_) {
      for(int i = Math.max(0, p_242971_1_.getY() - 21); p_242971_1_.getY() > i && func_196900_a(this.world.getBlockState(p_242971_1_.down())); p_242971_1_ = p_242971_1_.down()) {
      }

      Direction direction = this.rightDir.getOpposite();
      int j = this.func_242972_a(p_242971_1_, direction) - 1;
      return j < 0 ? null : p_242971_1_.offset(direction, j);
   }

   private int func_242974_d() {
      int i = this.func_242972_a(this.bottomLeft, this.rightDir);
      return i >= 2 && i <= 21 ? i : 0;
   }

   private int func_242972_a(BlockPos p_242972_1_, Direction p_242972_2_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int i = 0; i <= 21; ++i) {
         blockpos$mutable.setPos(p_242972_1_).move(p_242972_2_, i);
         BlockState blockstate = this.world.getBlockState(blockpos$mutable);
         if (!func_196900_a(blockstate)) {
            if (field_242962_a.test(blockstate, this.world, blockpos$mutable)) {
               return i;
            }
            break;
         }

         BlockState blockstate1 = this.world.getBlockState(blockpos$mutable.move(Direction.DOWN));
         if (!field_242962_a.test(blockstate1, this.world, blockpos$mutable)) {
            break;
         }
      }

      return 0;
   }

   private int func_242975_e() {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      int i = this.func_242969_a(blockpos$mutable);
      return i >= 3 && i <= 21 && this.func_242970_a(blockpos$mutable, i) ? i : 0;
   }

   private boolean func_242970_a(BlockPos.Mutable p_242970_1_, int p_242970_2_) {
      for(int i = 0; i < this.width; ++i) {
         BlockPos.Mutable blockpos$mutable = p_242970_1_.setPos(this.bottomLeft).move(Direction.UP, p_242970_2_).move(this.rightDir, i);
         if (!field_242962_a.test(this.world.getBlockState(blockpos$mutable), this.world, blockpos$mutable)) {
            return false;
         }
      }

      return true;
   }

   private int func_242969_a(BlockPos.Mutable p_242969_1_) {
      for(int i = 0; i < 21; ++i) {
         p_242969_1_.setPos(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
         if (!field_242962_a.test(this.world.getBlockState(p_242969_1_), this.world, p_242969_1_)) {
            return i;
         }

         p_242969_1_.setPos(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
         if (!field_242962_a.test(this.world.getBlockState(p_242969_1_), this.world, p_242969_1_)) {
            return i;
         }

         for(int j = 0; j < this.width; ++j) {
            p_242969_1_.setPos(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
            BlockState blockstate = this.world.getBlockState(p_242969_1_);
            if (!func_196900_a(blockstate)) {
               return i;
            }

            if (blockstate.isIn(Blocks.NETHER_PORTAL)) {
               ++this.portalBlockCount;
            }
         }
      }

      return 21;
   }

   private static boolean func_196900_a(BlockState p_196900_0_) {
      return p_196900_0_.isAir() || p_196900_0_.func_235714_a_(BlockTags.field_232872_am_) || p_196900_0_.isIn(Blocks.NETHER_PORTAL);
   }

   public boolean isValid() {
      return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
   }

   public void placePortalBlocks() {
      BlockState blockstate = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
      BlockPos.getAllInBoxMutable(this.bottomLeft, this.bottomLeft.offset(Direction.UP, this.height - 1).offset(this.rightDir, this.width - 1)).forEach((p_242967_2_) -> {
         this.world.setBlockState(p_242967_2_, blockstate, 18);
      });
   }

   public boolean func_208508_f() {
      return this.isValid() && this.portalBlockCount == this.width * this.height;
   }

   public static Vector3d func_242973_a(TeleportationRepositioner.Result p_242973_0_, Direction.Axis p_242973_1_, Vector3d p_242973_2_, EntitySize p_242973_3_) {
      double d0 = (double)p_242973_0_.field_243680_b - (double)p_242973_3_.width;
      double d1 = (double)p_242973_0_.field_243681_c - (double)p_242973_3_.height;
      BlockPos blockpos = p_242973_0_.field_243679_a;
      double d2;
      if (d0 > 0.0D) {
         float f = (float)blockpos.func_243648_a(p_242973_1_) + p_242973_3_.width / 2.0F;
         d2 = MathHelper.clamp(MathHelper.func_233020_c_(p_242973_2_.getCoordinate(p_242973_1_) - (double)f, 0.0D, d0), 0.0D, 1.0D);
      } else {
         d2 = 0.5D;
      }

      double d4;
      if (d1 > 0.0D) {
         Direction.Axis direction$axis = Direction.Axis.Y;
         d4 = MathHelper.clamp(MathHelper.func_233020_c_(p_242973_2_.getCoordinate(direction$axis) - (double)blockpos.func_243648_a(direction$axis), 0.0D, d1), 0.0D, 1.0D);
      } else {
         d4 = 0.0D;
      }

      Direction.Axis direction$axis1 = p_242973_1_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
      double d3 = p_242973_2_.getCoordinate(direction$axis1) - ((double)blockpos.func_243648_a(direction$axis1) + 0.5D);
      return new Vector3d(d2, d4, d3);
   }

   public static PortalInfo func_242963_a(ServerWorld p_242963_0_, TeleportationRepositioner.Result p_242963_1_, Direction.Axis p_242963_2_, Vector3d p_242963_3_, EntitySize p_242963_4_, Vector3d p_242963_5_, float p_242963_6_, float p_242963_7_) {
      BlockPos blockpos = p_242963_1_.field_243679_a;
      BlockState blockstate = p_242963_0_.getBlockState(blockpos);
      Direction.Axis direction$axis = blockstate.get(BlockStateProperties.HORIZONTAL_AXIS);
      double d0 = (double)p_242963_1_.field_243680_b;
      double d1 = (double)p_242963_1_.field_243681_c;
      int i = p_242963_2_ == direction$axis ? 0 : 90;
      Vector3d vector3d = p_242963_2_ == direction$axis ? p_242963_5_ : new Vector3d(p_242963_5_.z, p_242963_5_.y, -p_242963_5_.x);
      double d2 = (double)p_242963_4_.width / 2.0D + (d0 - (double)p_242963_4_.width) * p_242963_3_.getX();
      double d3 = (d1 - (double)p_242963_4_.height) * p_242963_3_.getY();
      double d4 = 0.5D + p_242963_3_.getZ();
      boolean flag = direction$axis == Direction.Axis.X;
      Vector3d vector3d1 = new Vector3d((double)blockpos.getX() + (flag ? d2 : d4), (double)blockpos.getY() + d3, (double)blockpos.getZ() + (flag ? d4 : d2));
      return new PortalInfo(vector3d1, vector3d, p_242963_6_ + (float)i, p_242963_7_);
   }
}