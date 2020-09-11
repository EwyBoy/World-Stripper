package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InventoryScreen extends DisplayEffectsScreen<PlayerContainer> implements IRecipeShownListener {
   private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
   /** The old x position of the mouse pointer */
   private float oldMouseX;
   /** The old y position of the mouse pointer */
   private float oldMouseY;
   private final RecipeBookGui recipeBookGui = new RecipeBookGui();
   private boolean removeRecipeBookGui;
   private boolean widthTooNarrow;
   private boolean buttonClicked;

   public InventoryScreen(PlayerEntity player) {
      super(player.container, player.inventory, new TranslationTextComponent("container.crafting"));
      this.field_230711_n_ = true;
      this.field_238742_p_ = 97;
   }

   public void func_231023_e_() {
      if (this.field_230706_i_.playerController.isInCreativeMode()) {
         this.field_230706_i_.displayGuiScreen(new CreativeScreen(this.field_230706_i_.player));
      } else {
         this.recipeBookGui.tick();
      }
   }

   protected void func_231160_c_() {
      if (this.field_230706_i_.playerController.isInCreativeMode()) {
         this.field_230706_i_.displayGuiScreen(new CreativeScreen(this.field_230706_i_.player));
      } else {
         super.func_231160_c_();
         this.widthTooNarrow = this.field_230708_k_ < 379;
         this.recipeBookGui.init(this.field_230708_k_, this.field_230709_l_, this.field_230706_i_, this.widthTooNarrow, this.container);
         this.removeRecipeBookGui = true;
         this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.field_230708_k_, this.xSize);
         this.field_230705_e_.add(this.recipeBookGui);
         this.setFocusedDefault(this.recipeBookGui);
         this.func_230480_a_(new ImageButton(this.guiLeft + 104, this.field_230709_l_ / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (p_214086_1_) -> {
            this.recipeBookGui.initSearchBar(this.widthTooNarrow);
            this.recipeBookGui.toggleVisibility();
            this.guiLeft = this.recipeBookGui.updateScreenPosition(this.widthTooNarrow, this.field_230708_k_, this.xSize);
            ((ImageButton)p_214086_1_).setPosition(this.guiLeft + 104, this.field_230709_l_ / 2 - 22);
            this.buttonClicked = true;
         }));
      }
   }

   protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
      this.field_230712_o_.func_243248_b(p_230451_1_, this.field_230704_d_, (float)this.field_238742_p_, (float)this.field_238743_q_, 4210752);
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.func_230446_a_(p_230430_1_);
      this.hasActivePotionEffects = !this.recipeBookGui.isVisible();
      if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
         this.func_230450_a_(p_230430_1_, p_230430_4_, p_230430_2_, p_230430_3_);
         this.recipeBookGui.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      } else {
         this.recipeBookGui.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
         this.recipeBookGui.func_230477_a_(p_230430_1_, this.guiLeft, this.guiTop, false, p_230430_4_);
      }

      this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
      this.recipeBookGui.func_238924_c_(p_230430_1_, this.guiLeft, this.guiTop, p_230430_2_, p_230430_3_);
      this.oldMouseX = (float)p_230430_2_;
      this.oldMouseY = (float)p_230430_3_;
   }

   protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.func_238474_b_(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
      drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.field_230706_i_.player);
   }

   public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity p_228187_5_) {
      float f = (float)Math.atan((double)(mouseX / 40.0F));
      float f1 = (float)Math.atan((double)(mouseY / 40.0F));
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)posX, (float)posY, 1050.0F);
      RenderSystem.scalef(1.0F, 1.0F, -1.0F);
      MatrixStack matrixstack = new MatrixStack();
      matrixstack.translate(0.0D, 0.0D, 1000.0D);
      matrixstack.scale((float)scale, (float)scale, (float)scale);
      Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
      Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
      quaternion.multiply(quaternion1);
      matrixstack.rotate(quaternion);
      float f2 = p_228187_5_.renderYawOffset;
      float f3 = p_228187_5_.rotationYaw;
      float f4 = p_228187_5_.rotationPitch;
      float f5 = p_228187_5_.prevRotationYawHead;
      float f6 = p_228187_5_.rotationYawHead;
      p_228187_5_.renderYawOffset = 180.0F + f * 20.0F;
      p_228187_5_.rotationYaw = 180.0F + f * 40.0F;
      p_228187_5_.rotationPitch = -f1 * 20.0F;
      p_228187_5_.rotationYawHead = p_228187_5_.rotationYaw;
      p_228187_5_.prevRotationYawHead = p_228187_5_.rotationYaw;
      EntityRendererManager entityrenderermanager = Minecraft.getInstance().getRenderManager();
      quaternion1.conjugate();
      entityrenderermanager.setCameraOrientation(quaternion1);
      entityrenderermanager.setRenderShadow(false);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
      RenderSystem.runAsFancy(() -> {
         entityrenderermanager.renderEntityStatic(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
      });
      irendertypebuffer$impl.finish();
      entityrenderermanager.setRenderShadow(true);
      p_228187_5_.renderYawOffset = f2;
      p_228187_5_.rotationYaw = f3;
      p_228187_5_.rotationPitch = f4;
      p_228187_5_.prevRotationYawHead = f5;
      p_228187_5_.rotationYawHead = f6;
      RenderSystem.popMatrix();
   }

   protected boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
      return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isPointInRegion(x, y, width, height, mouseX, mouseY);
   }

   public boolean func_231044_a_(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
      if (this.recipeBookGui.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_)) {
         this.func_231035_a_(this.recipeBookGui);
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookGui.isVisible() ? false : super.func_231044_a_(p_231044_1_, p_231044_3_, p_231044_5_);
      }
   }

   public boolean func_231048_c_(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
      if (this.buttonClicked) {
         this.buttonClicked = false;
         return true;
      } else {
         return super.func_231048_c_(p_231048_1_, p_231048_3_, p_231048_5_);
      }
   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
      return this.recipeBookGui.func_195604_a(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize, mouseButton) && flag;
   }

   /**
    * Called when the mouse is clicked over a slot or outside the gui.
    */
   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.handleMouseClick(slotIn, slotId, mouseButton, type);
      this.recipeBookGui.slotClicked(slotIn);
   }

   public void recipesUpdated() {
      this.recipeBookGui.recipesUpdated();
   }

   public void func_231164_f_() {
      if (this.removeRecipeBookGui) {
         this.recipeBookGui.removed();
      }

      super.func_231164_f_();
   }

   public RecipeBookGui getRecipeGui() {
      return this.recipeBookGui;
   }
}