package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class ItemStackTileEntityRenderer {
   private static final ShulkerBoxTileEntity[] SHULKER_BOXES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(ShulkerBoxTileEntity::new).toArray((p_199929_0_) -> {
      return new ShulkerBoxTileEntity[p_199929_0_];
   });
   private static final ShulkerBoxTileEntity SHULKER_BOX = new ShulkerBoxTileEntity((DyeColor)null);
   public static final ItemStackTileEntityRenderer instance = new ItemStackTileEntityRenderer();
   private final ChestTileEntity chestBasic = new ChestTileEntity();
   private final ChestTileEntity chestTrap = new TrappedChestTileEntity();
   private final EnderChestTileEntity enderChest = new EnderChestTileEntity();
   private final BannerTileEntity banner = new BannerTileEntity();
   private final BedTileEntity bed = new BedTileEntity();
   private final ConduitTileEntity conduit = new ConduitTileEntity();
   private final ShieldModel modelShield = new ShieldModel();
   private final TridentModel trident = new TridentModel();

   public void func_239207_a_(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
      Item item = p_239207_1_.getItem();
      if (item instanceof BlockItem) {
         Block block = ((BlockItem)item).getBlock();
         if (block instanceof AbstractSkullBlock) {
            GameProfile gameprofile = null;
            if (p_239207_1_.hasTag()) {
               CompoundNBT compoundnbt = p_239207_1_.getTag();
               if (compoundnbt.contains("SkullOwner", 10)) {
                  gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
               } else if (compoundnbt.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundnbt.getString("SkullOwner"))) {
                  GameProfile gameprofile1 = new GameProfile((UUID)null, compoundnbt.getString("SkullOwner"));
                  gameprofile = SkullTileEntity.updateGameProfile(gameprofile1);
                  compoundnbt.remove("SkullOwner");
                  compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
               }
            }

            SkullTileEntityRenderer.render((Direction)null, 180.0F, ((AbstractSkullBlock)block).getSkullType(), gameprofile, 0.0F, p_239207_3_, p_239207_4_, p_239207_5_);
         } else {
            TileEntity tileentity;
            if (block instanceof AbstractBannerBlock) {
               this.banner.loadFromItemStack(p_239207_1_, ((AbstractBannerBlock)block).getColor());
               tileentity = this.banner;
            } else if (block instanceof BedBlock) {
               this.bed.setColor(((BedBlock)block).getColor());
               tileentity = this.bed;
            } else if (block == Blocks.CONDUIT) {
               tileentity = this.conduit;
            } else if (block == Blocks.CHEST) {
               tileentity = this.chestBasic;
            } else if (block == Blocks.ENDER_CHEST) {
               tileentity = this.enderChest;
            } else if (block == Blocks.TRAPPED_CHEST) {
               tileentity = this.chestTrap;
            } else {
               if (!(block instanceof ShulkerBoxBlock)) {
                  return;
               }

               DyeColor dyecolor = ShulkerBoxBlock.getColorFromItem(item);
               if (dyecolor == null) {
                  tileentity = SHULKER_BOX;
               } else {
                  tileentity = SHULKER_BOXES[dyecolor.getId()];
               }
            }

            TileEntityRendererDispatcher.instance.renderItem(tileentity, p_239207_3_, p_239207_4_, p_239207_5_, p_239207_6_);
         }
      } else {
         if (item == Items.SHIELD) {
            boolean flag = p_239207_1_.getChildTag("BlockEntityTag") != null;
            p_239207_3_.push();
            p_239207_3_.scale(1.0F, -1.0F, -1.0F);
            RenderMaterial rendermaterial = flag ? ModelBakery.LOCATION_SHIELD_BASE : ModelBakery.LOCATION_SHIELD_NO_PATTERN;
            IVertexBuilder ivertexbuilder = rendermaterial.getSprite().wrapBuffer(ItemRenderer.func_239391_c_(p_239207_4_, this.modelShield.getRenderType(rendermaterial.getAtlasLocation()), true, p_239207_1_.hasEffect()));
            this.modelShield.func_228294_b_().render(p_239207_3_, ivertexbuilder, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
            if (flag) {
               List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.func_230138_a_(ShieldItem.getColor(p_239207_1_), BannerTileEntity.func_230139_a_(p_239207_1_));
               BannerTileEntityRenderer.func_241717_a_(p_239207_3_, p_239207_4_, p_239207_5_, p_239207_6_, this.modelShield.func_228293_a_(), rendermaterial, false, list, p_239207_1_.hasEffect());
            } else {
               this.modelShield.func_228293_a_().render(p_239207_3_, ivertexbuilder, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            p_239207_3_.pop();
         } else if (item == Items.TRIDENT) {
            p_239207_3_.push();
            p_239207_3_.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.func_239391_c_(p_239207_4_, this.trident.getRenderType(TridentModel.TEXTURE_LOCATION), false, p_239207_1_.hasEffect());
            this.trident.render(p_239207_3_, ivertexbuilder1, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
            p_239207_3_.pop();
         }

      }
   }
}