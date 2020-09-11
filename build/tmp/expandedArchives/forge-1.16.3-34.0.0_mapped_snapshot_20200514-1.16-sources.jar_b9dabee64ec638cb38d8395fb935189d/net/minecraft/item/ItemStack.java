package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemStack extends net.minecraftforge.common.capabilities.CapabilityProvider<ItemStack> implements net.minecraftforge.common.extensions.IForgeItemStack {
   public static final Codec<ItemStack> field_234691_a_ = RecordCodecBuilder.create((p_234698_0_) -> {
      return p_234698_0_.group(Registry.ITEM.fieldOf("id").forGetter((p_234706_0_) -> {
         return p_234706_0_.item;
      }), Codec.INT.fieldOf("Count").forGetter((p_234705_0_) -> {
         return p_234705_0_.count;
      }), CompoundNBT.field_240597_a_.optionalFieldOf("tag").forGetter((p_234704_0_) -> {
         return Optional.ofNullable(p_234704_0_.tag);
      })).apply(p_234698_0_, ItemStack::new);
   });
   private net.minecraftforge.registries.IRegistryDelegate<Item> delegate;
   private CompoundNBT capNBT;

   private static final Logger LOGGER = LogManager.getLogger();
   public static final ItemStack EMPTY = new ItemStack((Item)null);
   public static final DecimalFormat DECIMALFORMAT = Util.make(new DecimalFormat("#.##"), (p_234699_0_) -> {
      p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   });
   private static final Style field_234692_e_ = Style.field_240709_b_.func_240712_a_(TextFormatting.DARK_PURPLE).func_240722_b_(true);
   private int count;
   private int animationsToGo;
   @Deprecated
   private final Item item;
   private CompoundNBT tag;
   private boolean isEmpty;
   private Entity field_234693_k_;
   private CachedBlockInfo canDestroyCacheBlock;
   private boolean canDestroyCacheResult;
   private CachedBlockInfo canPlaceOnCacheBlock;
   private boolean canPlaceOnCacheResult;

   public ItemStack(IItemProvider itemIn) {
      this(itemIn, 1);
   }

   private ItemStack(IItemProvider p_i231596_1_, int p_i231596_2_, Optional<CompoundNBT> p_i231596_3_) {
      this(p_i231596_1_, p_i231596_2_);
      p_i231596_3_.ifPresent(this::setTag);
   }

   public ItemStack(IItemProvider itemIn, int count) { this(itemIn, count, (CompoundNBT) null); }
   public ItemStack(IItemProvider itemIn, int count, @Nullable CompoundNBT capNBT) {
      super(ItemStack.class);
      this.capNBT = capNBT;
      this.item = itemIn == null ? null : itemIn.asItem();
      this.count = count;
      if (this.item != null && this.item.isDamageable(this)) {
         this.setDamage(this.getDamage());
      }

      this.updateEmptyState();
      this.forgeInit();
   }

   private void updateEmptyState() {
      this.isEmpty = false;
      this.isEmpty = this.isEmpty();
   }

   private ItemStack(CompoundNBT compound) {
      super(ItemStack.class);
      this.capNBT = compound.contains("ForgeCaps") ? compound.getCompound("ForgeCaps") : null;
      this.item = Registry.ITEM.getOrDefault(new ResourceLocation(compound.getString("id")));
      this.count = compound.getByte("Count");
      if (compound.contains("tag", 10)) {
         this.tag = compound.getCompound("tag");
         this.getItem().updateItemStackNBT(compound);
      }

      if (this.getItem().isDamageable(this)) {
         this.setDamage(this.getDamage());
      }

      this.updateEmptyState();
      this.forgeInit();
   }

   public static ItemStack read(CompoundNBT compound) {
      try {
         return new ItemStack(compound);
      } catch (RuntimeException runtimeexception) {
         LOGGER.debug("Tried to load invalid item: {}", compound, runtimeexception);
         return EMPTY;
      }
   }

   public boolean isEmpty() {
      if (this == EMPTY) {
         return true;
      } else if (this.getItemRaw() != null && this.getItemRaw() != Items.AIR) {
         return this.count <= 0;
      } else {
         return true;
      }
   }

   /**
    * Splits off a stack of the given amount of this stack and reduces this stack by the amount.
    */
   public ItemStack split(int amount) {
      int i = Math.min(amount, this.count);
      ItemStack itemstack = this.copy();
      itemstack.setCount(i);
      this.shrink(i);
      return itemstack;
   }

   /**
    * Returns the object corresponding to the stack.
    */
   public Item getItem() {
      return this.isEmpty || this.delegate == null ? Items.AIR : this.delegate.get();
   }

   public ActionResultType onItemUse(ItemUseContext context) {
      if (!context.getWorld().isRemote) return net.minecraftforge.common.ForgeHooks.onPlaceItemIntoWorld(context);
      return onItemUse(context, (c) -> getItem().onItemUse(context));
   }

   public ActionResultType onItemUseFirst(ItemUseContext context) {
      return onItemUse(context, (c) -> getItem().onItemUseFirst(this, context));
   }

   private ActionResultType onItemUse(ItemUseContext context, java.util.function.Function<ItemUseContext, ActionResultType> callback) {
      PlayerEntity playerentity = context.getPlayer();
      BlockPos blockpos = context.getPos();
      CachedBlockInfo cachedblockinfo = new CachedBlockInfo(context.getWorld(), blockpos, false);
      if (playerentity != null && !playerentity.abilities.allowEdit && !this.canPlaceOn(context.getWorld().getTags(), cachedblockinfo)) {
         return ActionResultType.PASS;
      } else {
         Item item = this.getItem();
         ActionResultType actionresulttype = callback.apply(context);
         if (playerentity != null && actionresulttype.isSuccessOrConsume()) {
            playerentity.addStat(Stats.ITEM_USED.get(item));
         }

         return actionresulttype;
      }
   }

   public float getDestroySpeed(BlockState blockIn) {
      return this.getItem().getDestroySpeed(this, blockIn);
   }

   /**
    * Called whenr the item stack is equipped and right clicked. Replaces the item stack with the return value.
    */
   public ActionResult<ItemStack> useItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
      return this.getItem().onItemRightClick(worldIn, playerIn, hand);
   }

   /**
    * Called when the item in use count reach 0, e.g. item food eaten. Return the new ItemStack. Args : world, entity
    */
   public ItemStack onItemUseFinish(World worldIn, LivingEntity entityLiving) {
      return this.getItem().onItemUseFinish(this, worldIn, entityLiving);
   }

   /**
    * Write the stack fields to a NBT object. Return the new NBT object.
    */
   public CompoundNBT write(CompoundNBT nbt) {
      ResourceLocation resourcelocation = Registry.ITEM.getKey(this.getItem());
      nbt.putString("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());
      nbt.putByte("Count", (byte)this.count);
      if (this.tag != null) {
         nbt.put("tag", this.tag.copy());
      }
      CompoundNBT cnbt = this.serializeCaps();
      if (cnbt != null && !cnbt.isEmpty()) {
         nbt.put("ForgeCaps", cnbt);
      }
      return nbt;
   }

   /**
    * Returns maximum size of the stack.
    */
   public int getMaxStackSize() {
      return this.getItem().getItemStackLimit(this);
   }

   /**
    * Returns true if the ItemStack can hold 2 or more units of the item.
    */
   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.isDamageable() || !this.isDamaged());
   }

   /**
    * true if this itemStack is damageable
    */
   public boolean isDamageable() {
      if (!this.isEmpty && this.getItem().isDamageable(this)) {
         CompoundNBT compoundnbt = this.getTag();
         return compoundnbt == null || !compoundnbt.getBoolean("Unbreakable");
      } else {
         return false;
      }
   }

   /**
    * returns true when a damageable item is damaged
    */
   public boolean isDamaged() {
      return this.isDamageable() && getItem().isDamaged(this);
   }

   public int getDamage() {
      return this.getItem().getDamage(this);
   }

   public void setDamage(int damage) {
      this.getItem().setDamage(this, damage);
   }

   /**
    * Returns the max damage an item in the stack can take.
    */
   public int getMaxDamage() {
      return this.getItem().getMaxDamage(this);
   }

   /**
    * Attempts to damage the ItemStack with par1 amount of damage, If the ItemStack has the Unbreaking enchantment there
    * is a chance for each point of damage to be negated. Returns true if it takes more damage than getMaxDamage().
    * Returns false otherwise or if the ItemStack can't be damaged or if all points of damage are negated.
    */
   public boolean attemptDamageItem(int amount, Random rand, @Nullable ServerPlayerEntity damager) {
      if (!this.isDamageable()) {
         return false;
      } else {
         if (amount > 0) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, this);
            int j = 0;

            for(int k = 0; i > 0 && k < amount; ++k) {
               if (UnbreakingEnchantment.negateDamage(this, i, rand)) {
                  ++j;
               }
            }

            amount -= j;
            if (amount <= 0) {
               return false;
            }
         }

         if (damager != null && amount != 0) {
            CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(damager, this, this.getDamage() + amount);
         }

         int l = this.getDamage() + amount;
         this.setDamage(l);
         return l >= this.getMaxDamage();
      }
   }

   public <T extends LivingEntity> void damageItem(int amount, T entityIn, Consumer<T> onBroken) {
      if (!entityIn.world.isRemote && (!(entityIn instanceof PlayerEntity) || !((PlayerEntity)entityIn).abilities.isCreativeMode)) {
         if (this.isDamageable()) {
            amount = this.getItem().damageItem(this, amount, entityIn, onBroken);
            if (this.attemptDamageItem(amount, entityIn.getRNG(), entityIn instanceof ServerPlayerEntity ? (ServerPlayerEntity)entityIn : null)) {
               onBroken.accept(entityIn);
               Item item = this.getItem();
               this.shrink(1);
               if (entityIn instanceof PlayerEntity) {
                  ((PlayerEntity)entityIn).addStat(Stats.ITEM_BROKEN.get(item));
               }

               this.setDamage(0);
            }

         }
      }
   }

   /**
    * Calls the delegated method to the Item to damage the incoming Entity, and if necessary, triggers a stats increase.
    */
   public void hitEntity(LivingEntity entityIn, PlayerEntity playerIn) {
      Item item = this.getItem();
      if (item.hitEntity(this, entityIn, playerIn)) {
         playerIn.addStat(Stats.ITEM_USED.get(item));
      }

   }

   /**
    * Called when a Block is destroyed using this ItemStack
    */
   public void onBlockDestroyed(World worldIn, BlockState blockIn, BlockPos pos, PlayerEntity playerIn) {
      Item item = this.getItem();
      if (item.onBlockDestroyed(this, worldIn, blockIn, pos, playerIn)) {
         playerIn.addStat(Stats.ITEM_USED.get(item));
      }

   }

   /**
    * Check whether the given Block can be harvested using this ItemStack.
    */
   public boolean canHarvestBlock(BlockState blockIn) {
      return this.getItem().canHarvestBlock(this, blockIn);
   }

   public ActionResultType func_111282_a_(PlayerEntity playerIn, LivingEntity entityIn, Hand hand) {
      return this.getItem().itemInteractionForEntity(this, playerIn, entityIn, hand);
   }

   /**
    * Returns a new stack with the same properties.
    */
   public ItemStack copy() {
      if (this.isEmpty()) {
         return EMPTY;
      } else {
         ItemStack itemstack = new ItemStack(this.getItem(), this.count, this.serializeCaps());
         itemstack.setAnimationsToGo(this.getAnimationsToGo());
         if (this.tag != null) {
            itemstack.tag = this.tag.copy();
         }

         return itemstack;
      }
   }

   public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
      if (stackA.isEmpty() && stackB.isEmpty()) {
         return true;
      } else if (!stackA.isEmpty() && !stackB.isEmpty()) {
         if (stackA.tag == null && stackB.tag != null) {
            return false;
         } else {
            return (stackA.tag == null || stackA.tag.equals(stackB.tag)) && stackA.areCapsCompatible(stackB);
         }
      } else {
         return false;
      }
   }

   /**
    * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
    */
   public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
      if (stackA.isEmpty() && stackB.isEmpty()) {
         return true;
      } else {
         return !stackA.isEmpty() && !stackB.isEmpty() ? stackA.isItemStackEqual(stackB) : false;
      }
   }

   /**
    * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
    */
   private boolean isItemStackEqual(ItemStack other) {
      if (this.count != other.count) {
         return false;
      } else if (this.getItem() != other.getItem()) {
         return false;
      } else if (this.tag == null && other.tag != null) {
         return false;
      } else {
         return (this.tag == null || this.tag.equals(other.tag)) && this.areCapsCompatible(other);
      }
   }

   /**
    * Compares Item and damage value of the two stacks
    */
   public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
      if (stackA == stackB) {
         return true;
      } else {
         return !stackA.isEmpty() && !stackB.isEmpty() ? stackA.isItemEqual(stackB) : false;
      }
   }

   public static boolean areItemsEqualIgnoreDurability(ItemStack stackA, ItemStack stackB) {
      if (stackA == stackB) {
         return true;
      } else {
         return !stackA.isEmpty() && !stackB.isEmpty() ? stackA.isItemEqualIgnoreDurability(stackB) : false;
      }
   }

   /**
    * compares ItemStack argument to the instance ItemStack; returns true if the Items contained in both ItemStacks are
    * equal
    */
   public boolean isItemEqual(ItemStack other) {
      return !other.isEmpty() && this.getItem() == other.getItem();
   }

   public boolean isItemEqualIgnoreDurability(ItemStack stack) {
      if (!this.isDamageable()) {
         return this.isItemEqual(stack);
      } else {
         return !stack.isEmpty() && this.getItem() == stack.getItem();
      }
   }

   public String getTranslationKey() {
      return this.getItem().getTranslationKey(this);
   }

   public String toString() {
      return this.count + " " + this.getItem();
   }

   /**
    * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update
    * maps.
    */
   public void inventoryTick(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
      if (this.animationsToGo > 0) {
         --this.animationsToGo;
      }

      if (this.getItem() != null) {
         this.getItem().inventoryTick(this, worldIn, entityIn, inventorySlot, isCurrentItem);
      }

   }

   public void onCrafting(World worldIn, PlayerEntity playerIn, int amount) {
      playerIn.addStat(Stats.ITEM_CRAFTED.get(this.getItem()), amount);
      this.getItem().onCreated(this, worldIn, playerIn);
   }

   public int getUseDuration() {
      return this.getItem().getUseDuration(this);
   }

   public UseAction getUseAction() {
      return this.getItem().getUseAction(this);
   }

   /**
    * Called when the player releases the use item button.
    */
   public void onPlayerStoppedUsing(World worldIn, LivingEntity entityLiving, int timeLeft) {
      this.getItem().onPlayerStoppedUsing(this, worldIn, entityLiving, timeLeft);
   }

   public boolean isCrossbowStack() {
      return this.getItem().isCrossbow(this);
   }

   /**
    * Returns true if the ItemStack has an NBTTagCompound. Currently used to store enchantments.
    */
   public boolean hasTag() {
      return !this.isEmpty && this.tag != null && !this.tag.isEmpty();
   }

   @Nullable
   public CompoundNBT getTag() {
      return this.tag;
   }

   public CompoundNBT getOrCreateTag() {
      if (this.tag == null) {
         this.setTag(new CompoundNBT());
      }

      return this.tag;
   }

   public CompoundNBT getOrCreateChildTag(String key) {
      if (this.tag != null && this.tag.contains(key, 10)) {
         return this.tag.getCompound(key);
      } else {
         CompoundNBT compoundnbt = new CompoundNBT();
         this.setTagInfo(key, compoundnbt);
         return compoundnbt;
      }
   }

   /**
    * Get an NBTTagCompound from this stack's NBT data.
    */
   @Nullable
   public CompoundNBT getChildTag(String key) {
      return this.tag != null && this.tag.contains(key, 10) ? this.tag.getCompound(key) : null;
   }

   public void removeChildTag(String p_196083_1_) {
      if (this.tag != null && this.tag.contains(p_196083_1_)) {
         this.tag.remove(p_196083_1_);
         if (this.tag.isEmpty()) {
            this.tag = null;
         }
      }

   }

   public ListNBT getEnchantmentTagList() {
      return this.tag != null ? this.tag.getList("Enchantments", 10) : new ListNBT();
   }

   /**
    * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
    */
   public void setTag(@Nullable CompoundNBT nbt) {
      this.tag = nbt;
      if (this.getItem().isDamageable(this)) {
         this.setDamage(this.getDamage());
      }

   }

   public ITextComponent getDisplayName() {
      CompoundNBT compoundnbt = this.getChildTag("display");
      if (compoundnbt != null && compoundnbt.contains("Name", 8)) {
         try {
            ITextComponent itextcomponent = ITextComponent.Serializer.func_240643_a_(compoundnbt.getString("Name"));
            if (itextcomponent != null) {
               return itextcomponent;
            }

            compoundnbt.remove("Name");
         } catch (JsonParseException jsonparseexception) {
            compoundnbt.remove("Name");
         }
      }

      return this.getItem().getDisplayName(this);
   }

   public ItemStack setDisplayName(@Nullable ITextComponent name) {
      CompoundNBT compoundnbt = this.getOrCreateChildTag("display");
      if (name != null) {
         compoundnbt.putString("Name", ITextComponent.Serializer.toJson(name));
      } else {
         compoundnbt.remove("Name");
      }

      return this;
   }

   /**
    * Clear any custom name set for this ItemStack
    */
   public void clearCustomName() {
      CompoundNBT compoundnbt = this.getChildTag("display");
      if (compoundnbt != null) {
         compoundnbt.remove("Name");
         if (compoundnbt.isEmpty()) {
            this.removeChildTag("display");
         }
      }

      if (this.tag != null && this.tag.isEmpty()) {
         this.tag = null;
      }

   }

   /**
    * Returns true if the itemstack has a display name
    */
   public boolean hasDisplayName() {
      CompoundNBT compoundnbt = this.getChildTag("display");
      return compoundnbt != null && compoundnbt.contains("Name", 8);
   }

   /**
    * Return a list of strings containing information about the item
    */
   @OnlyIn(Dist.CLIENT)
   public List<ITextComponent> getTooltip(@Nullable PlayerEntity playerIn, ITooltipFlag advanced) {
      List<ITextComponent> list = Lists.newArrayList();
      IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("")).func_230529_a_(this.getDisplayName()).func_240699_a_(this.getRarity().color);
      if (this.hasDisplayName()) {
         iformattabletextcomponent.func_240699_a_(TextFormatting.ITALIC);
      }

      list.add(iformattabletextcomponent);
      if (!advanced.isAdvanced() && !this.hasDisplayName() && this.getItem() == Items.FILLED_MAP) {
         list.add((new StringTextComponent("#" + FilledMapItem.getMapId(this))).func_240699_a_(TextFormatting.GRAY));
      }

      int i = this.func_242393_J();
      if (func_242394_a(i, ItemStack.TooltipDisplayFlags.ADDITIONAL)) {
         this.getItem().addInformation(this, playerIn == null ? null : playerIn.world, list, advanced);
      }

      if (this.hasTag()) {
         if (func_242394_a(i, ItemStack.TooltipDisplayFlags.ENCHANTMENTS)) {
            addEnchantmentTooltips(list, this.getEnchantmentTagList());
         }

         if (this.tag.contains("display", 10)) {
            CompoundNBT compoundnbt = this.tag.getCompound("display");
            if (func_242394_a(i, ItemStack.TooltipDisplayFlags.DYE) && compoundnbt.contains("color", 99)) {
               if (advanced.isAdvanced()) {
                  list.add((new TranslationTextComponent("item.color", String.format("#%06X", compoundnbt.getInt("color")))).func_240699_a_(TextFormatting.GRAY));
               } else {
                  list.add((new TranslationTextComponent("item.dyed")).func_240701_a_(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}));
               }
            }

            if (compoundnbt.getTagId("Lore") == 9) {
               ListNBT listnbt = compoundnbt.getList("Lore", 8);

               for(int j = 0; j < listnbt.size(); ++j) {
                  String s = listnbt.getString(j);

                  try {
                     IFormattableTextComponent iformattabletextcomponent1 = ITextComponent.Serializer.func_240643_a_(s);
                     if (iformattabletextcomponent1 != null) {
                        list.add(TextComponentUtils.func_240648_a_(iformattabletextcomponent1, field_234692_e_));
                     }
                  } catch (JsonParseException jsonparseexception) {
                     compoundnbt.remove("Lore");
                  }
               }
            }
         }
      }

      if (func_242394_a(i, ItemStack.TooltipDisplayFlags.MODIFIERS)) {
         for(EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
            Multimap<Attribute, AttributeModifier> multimap = this.getAttributeModifiers(equipmentslottype);
            if (!multimap.isEmpty()) {
               list.add(StringTextComponent.field_240750_d_);
               list.add((new TranslationTextComponent("item.modifiers." + equipmentslottype.getName())).func_240699_a_(TextFormatting.GRAY));

               for(Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  double d0 = attributemodifier.getAmount();
                  boolean flag = false;
                  if (playerIn != null) {
                     if (attributemodifier.getID() == Item.ATTACK_DAMAGE_MODIFIER) {
                        d0 = d0 + playerIn.func_233638_c_(Attributes.field_233823_f_);
                        d0 = d0 + (double)EnchantmentHelper.getModifierForCreature(this, CreatureAttribute.UNDEFINED);
                        flag = true;
                     } else if (attributemodifier.getID() == Item.ATTACK_SPEED_MODIFIER) {
                        d0 += playerIn.func_233638_c_(Attributes.field_233825_h_);
                        flag = true;
                     }
                  }

                  double d1;
                  if (attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                     if (entry.getKey().equals(Attributes.field_233820_c_)) {
                        d1 = d0 * 10.0D;
                     } else {
                        d1 = d0;
                     }
                  } else {
                     d1 = d0 * 100.0D;
                  }

                  if (flag) {
                     list.add((new StringTextComponent(" ")).func_230529_a_(new TranslationTextComponent("attribute.modifier.equals." + attributemodifier.getOperation().getId(), DECIMALFORMAT.format(d1), new TranslationTextComponent(entry.getKey().func_233754_c_()))).func_240699_a_(TextFormatting.DARK_GREEN));
                  } else if (d0 > 0.0D) {
                     list.add((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier.getOperation().getId(), DECIMALFORMAT.format(d1), new TranslationTextComponent(entry.getKey().func_233754_c_()))).func_240699_a_(TextFormatting.BLUE));
                  } else if (d0 < 0.0D) {
                     d1 = d1 * -1.0D;
                     list.add((new TranslationTextComponent("attribute.modifier.take." + attributemodifier.getOperation().getId(), DECIMALFORMAT.format(d1), new TranslationTextComponent(entry.getKey().func_233754_c_()))).func_240699_a_(TextFormatting.RED));
                  }
               }
            }
         }
      }

      if (this.hasTag()) {
         if (func_242394_a(i, ItemStack.TooltipDisplayFlags.UNBREAKABLE) && this.tag.getBoolean("Unbreakable")) {
            list.add((new TranslationTextComponent("item.unbreakable")).func_240699_a_(TextFormatting.BLUE));
         }

         if (func_242394_a(i, ItemStack.TooltipDisplayFlags.CAN_DESTROY) && this.tag.contains("CanDestroy", 9)) {
            ListNBT listnbt1 = this.tag.getList("CanDestroy", 8);
            if (!listnbt1.isEmpty()) {
               list.add(StringTextComponent.field_240750_d_);
               list.add((new TranslationTextComponent("item.canBreak")).func_240699_a_(TextFormatting.GRAY));

               for(int k = 0; k < listnbt1.size(); ++k) {
                  list.addAll(getPlacementTooltip(listnbt1.getString(k)));
               }
            }
         }

         if (func_242394_a(i, ItemStack.TooltipDisplayFlags.CAN_PLACE) && this.tag.contains("CanPlaceOn", 9)) {
            ListNBT listnbt2 = this.tag.getList("CanPlaceOn", 8);
            if (!listnbt2.isEmpty()) {
               list.add(StringTextComponent.field_240750_d_);
               list.add((new TranslationTextComponent("item.canPlace")).func_240699_a_(TextFormatting.GRAY));

               for(int l = 0; l < listnbt2.size(); ++l) {
                  list.addAll(getPlacementTooltip(listnbt2.getString(l)));
               }
            }
         }
      }

      if (advanced.isAdvanced()) {
         if (this.isDamaged()) {
            list.add(new TranslationTextComponent("item.durability", this.getMaxDamage() - this.getDamage(), this.getMaxDamage()));
         }

         list.add((new StringTextComponent(Registry.ITEM.getKey(this.getItem()).toString())).func_240699_a_(TextFormatting.DARK_GRAY));
         if (this.hasTag()) {
            list.add((new TranslationTextComponent("item.nbt_tags", this.tag.keySet().size())).func_240699_a_(TextFormatting.DARK_GRAY));
         }
      }

      net.minecraftforge.event.ForgeEventFactory.onItemTooltip(this, playerIn, list, advanced);
      return list;
   }

   @OnlyIn(Dist.CLIENT)
   private static boolean func_242394_a(int p_242394_0_, ItemStack.TooltipDisplayFlags p_242394_1_) {
      return (p_242394_0_ & p_242394_1_.func_242397_a()) == 0;
   }

   @OnlyIn(Dist.CLIENT)
   private int func_242393_J() {
      return this.hasTag() && this.tag.contains("HideFlags", 99) ? this.tag.getInt("HideFlags") : 0;
   }

   public void func_242395_a(ItemStack.TooltipDisplayFlags p_242395_1_) {
      CompoundNBT compoundnbt = this.getOrCreateTag();
      compoundnbt.putInt("HideFlags", compoundnbt.getInt("HideFlags") | p_242395_1_.func_242397_a());
   }

   @OnlyIn(Dist.CLIENT)
   public static void addEnchantmentTooltips(List<ITextComponent> p_222120_0_, ListNBT p_222120_1_) {
      for(int i = 0; i < p_222120_1_.size(); ++i) {
         CompoundNBT compoundnbt = p_222120_1_.getCompound(i);
         Registry.ENCHANTMENT.func_241873_b(ResourceLocation.tryCreate(compoundnbt.getString("id"))).ifPresent((p_222123_2_) -> {
            p_222120_0_.add(p_222123_2_.getDisplayName(compoundnbt.getInt("lvl")));
         });
      }

   }

   @OnlyIn(Dist.CLIENT)
   private static Collection<ITextComponent> getPlacementTooltip(String stateString) {
      try {
         BlockStateParser blockstateparser = (new BlockStateParser(new StringReader(stateString), true)).parse(true);
         BlockState blockstate = blockstateparser.getState();
         ResourceLocation resourcelocation = blockstateparser.getTag();
         boolean flag = blockstate != null;
         boolean flag1 = resourcelocation != null;
         if (flag || flag1) {
            if (flag) {
               return Lists.newArrayList(blockstate.getBlock().func_235333_g_().func_240699_a_(TextFormatting.DARK_GRAY));
            }

            ITag<Block> itag = BlockTags.getCollection().get(resourcelocation);
            if (itag != null) {
               Collection<Block> collection = itag.func_230236_b_();
               if (!collection.isEmpty()) {
                  return collection.stream().map(Block::func_235333_g_).map((p_222119_0_) -> {
                     return p_222119_0_.func_240699_a_(TextFormatting.DARK_GRAY);
                  }).collect(Collectors.toList());
               }
            }
         }
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return Lists.newArrayList((new StringTextComponent("missingno")).func_240699_a_(TextFormatting.DARK_GRAY));
   }

   public boolean hasEffect() {
      return this.getItem().hasEffect(this);
   }

   public Rarity getRarity() {
      return this.getItem().getRarity(this);
   }

   /**
    * True if it is a tool and has no enchantments to begin with
    */
   public boolean isEnchantable() {
      if (!this.getItem().isEnchantable(this)) {
         return false;
      } else {
         return !this.isEnchanted();
      }
   }

   /**
    * Adds an enchantment with a desired level on the ItemStack.
    */
   public void addEnchantment(Enchantment ench, int level) {
      this.getOrCreateTag();
      if (!this.tag.contains("Enchantments", 9)) {
         this.tag.put("Enchantments", new ListNBT());
      }

      ListNBT listnbt = this.tag.getList("Enchantments", 10);
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("id", String.valueOf((Object)Registry.ENCHANTMENT.getKey(ench)));
      compoundnbt.putShort("lvl", (short)((byte)level));
      listnbt.add(compoundnbt);
   }

   /**
    * True if the item has enchantment data
    */
   public boolean isEnchanted() {
      if (this.tag != null && this.tag.contains("Enchantments", 9)) {
         return !this.tag.getList("Enchantments", 10).isEmpty();
      } else {
         return false;
      }
   }

   public void setTagInfo(String key, INBT value) {
      this.getOrCreateTag().put(key, value);
   }

   /**
    * Return whether this stack is on an item frame.
    */
   public boolean isOnItemFrame() {
      return this.field_234693_k_ instanceof ItemFrameEntity;
   }

   public void func_234695_a_(@Nullable Entity p_234695_1_) {
      this.field_234693_k_ = p_234695_1_;
   }

   /**
    * Return the item frame this stack is on. Returns null if not on an item frame.
    */
   @Nullable
   public ItemFrameEntity getItemFrame() {
      return this.field_234693_k_ instanceof ItemFrameEntity ? (ItemFrameEntity)this.func_234694_A_() : null;
   }

   @Nullable
   public Entity func_234694_A_() {
      return !this.isEmpty ? this.field_234693_k_ : null;
   }

   /**
    * Get this stack's repair cost, or 0 if no repair cost is defined.
    */
   public int getRepairCost() {
      return this.hasTag() && this.tag.contains("RepairCost", 3) ? this.tag.getInt("RepairCost") : 0;
   }

   /**
    * Set this stack's repair cost.
    */
   public void setRepairCost(int cost) {
      this.getOrCreateTag().putInt("RepairCost", cost);
   }

   /**
    * Gets the attribute modifiers for this ItemStack.
    * Will check for an NBT tag list containing modifiers for the stack.
    */
   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
      Multimap<Attribute, AttributeModifier> multimap;
      if (this.hasTag() && this.tag.contains("AttributeModifiers", 9)) {
         multimap = HashMultimap.create();
         ListNBT listnbt = this.tag.getList("AttributeModifiers", 10);

         for(int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            if (!compoundnbt.contains("Slot", 8) || compoundnbt.getString("Slot").equals(equipmentSlot.getName())) {
               Optional<Attribute> optional = Registry.field_239692_aP_.func_241873_b(ResourceLocation.tryCreate(compoundnbt.getString("AttributeName")));
               if (optional.isPresent()) {
                  AttributeModifier attributemodifier = AttributeModifier.func_233800_a_(compoundnbt);
                  if (attributemodifier != null && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L) {
                     multimap.put(optional.get(), attributemodifier);
                  }
               }
            }
         }
      } else {
         multimap = this.getItem().getAttributeModifiers(equipmentSlot, this);
      }

      return multimap;
   }

   public void addAttributeModifier(Attribute attributeName, AttributeModifier modifier, @Nullable EquipmentSlotType equipmentSlot) {
      this.getOrCreateTag();
      if (!this.tag.contains("AttributeModifiers", 9)) {
         this.tag.put("AttributeModifiers", new ListNBT());
      }

      ListNBT listnbt = this.tag.getList("AttributeModifiers", 10);
      CompoundNBT compoundnbt = modifier.func_233801_e_();
      compoundnbt.putString("AttributeName", Registry.field_239692_aP_.getKey(attributeName).toString());
      if (equipmentSlot != null) {
         compoundnbt.putString("Slot", equipmentSlot.getName());
      }

      listnbt.add(compoundnbt);
   }

   /**
    * Get a ChatComponent for this Item's display name that shows this Item on hover
    */
   public ITextComponent getTextComponent() {
      IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("")).func_230529_a_(this.getDisplayName());
      if (this.hasDisplayName()) {
         iformattabletextcomponent.func_240699_a_(TextFormatting.ITALIC);
      }

      IFormattableTextComponent iformattabletextcomponent1 = TextComponentUtils.func_240647_a_(iformattabletextcomponent);
      if (!this.isEmpty) {
         iformattabletextcomponent1.func_240699_a_(this.getRarity().color).func_240700_a_((p_234702_1_) -> {
            return p_234702_1_.func_240716_a_(new HoverEvent(HoverEvent.Action.field_230551_b_, new HoverEvent.ItemHover(this)));
         });
      }

      return iformattabletextcomponent1;
   }

   private static boolean isStateAndTileEntityEqual(CachedBlockInfo p_206846_0_, @Nullable CachedBlockInfo p_206846_1_) {
      if (p_206846_1_ != null && p_206846_0_.getBlockState() == p_206846_1_.getBlockState()) {
         if (p_206846_0_.getTileEntity() == null && p_206846_1_.getTileEntity() == null) {
            return true;
         } else {
            return p_206846_0_.getTileEntity() != null && p_206846_1_.getTileEntity() != null ? Objects.equals(p_206846_0_.getTileEntity().write(new CompoundNBT()), p_206846_1_.getTileEntity().write(new CompoundNBT())) : false;
         }
      } else {
         return false;
      }
   }

   public boolean canDestroy(ITagCollectionSupplier p_206848_1_, CachedBlockInfo p_206848_2_) {
      if (isStateAndTileEntityEqual(p_206848_2_, this.canDestroyCacheBlock)) {
         return this.canDestroyCacheResult;
      } else {
         this.canDestroyCacheBlock = p_206848_2_;
         if (this.hasTag() && this.tag.contains("CanDestroy", 9)) {
            ListNBT listnbt = this.tag.getList("CanDestroy", 8);

            for(int i = 0; i < listnbt.size(); ++i) {
               String s = listnbt.getString(i);

               try {
                  Predicate<CachedBlockInfo> predicate = BlockPredicateArgument.blockPredicate().parse(new StringReader(s)).create(p_206848_1_);
                  if (predicate.test(p_206848_2_)) {
                     this.canDestroyCacheResult = true;
                     return true;
                  }
               } catch (CommandSyntaxException commandsyntaxexception) {
               }
            }
         }

         this.canDestroyCacheResult = false;
         return false;
      }
   }

   public boolean canPlaceOn(ITagCollectionSupplier p_206847_1_, CachedBlockInfo p_206847_2_) {
      if (isStateAndTileEntityEqual(p_206847_2_, this.canPlaceOnCacheBlock)) {
         return this.canPlaceOnCacheResult;
      } else {
         this.canPlaceOnCacheBlock = p_206847_2_;
         if (this.hasTag() && this.tag.contains("CanPlaceOn", 9)) {
            ListNBT listnbt = this.tag.getList("CanPlaceOn", 8);

            for(int i = 0; i < listnbt.size(); ++i) {
               String s = listnbt.getString(i);

               try {
                  Predicate<CachedBlockInfo> predicate = BlockPredicateArgument.blockPredicate().parse(new StringReader(s)).create(p_206847_1_);
                  if (predicate.test(p_206847_2_)) {
                     this.canPlaceOnCacheResult = true;
                     return true;
                  }
               } catch (CommandSyntaxException commandsyntaxexception) {
               }
            }
         }

         this.canPlaceOnCacheResult = false;
         return false;
      }
   }

   public int getAnimationsToGo() {
      return this.animationsToGo;
   }

   public void setAnimationsToGo(int animations) {
      this.animationsToGo = animations;
   }

   public int getCount() {
      return this.isEmpty ? 0 : this.count;
   }

   public void setCount(int count) {
      this.count = count;
      this.updateEmptyState();
   }

   public void grow(int count) {
      this.setCount(this.count + count);
   }

   public void shrink(int count) {
      this.grow(-count);
   }

   /**
    * Called as the stack is being used by an entity.
    */
   public void onItemUsed(World worldIn, LivingEntity livingEntityIn, int countIn) {
      this.getItem().onUse(worldIn, livingEntityIn, this, countIn);
   }

   public boolean isFood() {
      return this.getItem().isFood();
   }

   // FORGE START
   public void deserializeNBT(CompoundNBT nbt) {
      final ItemStack itemStack = ItemStack.read(nbt);
      getStack().setTag(itemStack.getTag());
      if (itemStack.capNBT != null) deserializeCaps(itemStack.capNBT);
   }

   /**
    * Set up forge's ItemStack additions.
    */
   private void forgeInit() {
      Item item = getItemRaw();
      if (item != null) {
         this.delegate = item.delegate;
         net.minecraftforge.common.capabilities.ICapabilityProvider provider = item.initCapabilities(this, this.capNBT);
         this.gatherCapabilities(provider);
         if (this.capNBT != null) deserializeCaps(this.capNBT);
      }
   }

   /**
    * Internal call to get the actual item, not the delegate.
    * In all other methods, FML replaces calls to this.item with the item delegate.
    */
   @Nullable
   private Item getItemRaw() {
       return this.item;
   }

   public SoundEvent getDrinkSound() {
      return this.getItem().getDrinkSound();
   }

   public SoundEvent getEatSound() {
      return this.getItem().getEatSound();
   }

   public static enum TooltipDisplayFlags {
      ENCHANTMENTS,
      MODIFIERS,
      UNBREAKABLE,
      CAN_DESTROY,
      CAN_PLACE,
      ADDITIONAL,
      DYE;

      private int field_242396_h = 1 << this.ordinal();

      public int func_242397_a() {
         return this.field_242396_h;
      }
   }
}