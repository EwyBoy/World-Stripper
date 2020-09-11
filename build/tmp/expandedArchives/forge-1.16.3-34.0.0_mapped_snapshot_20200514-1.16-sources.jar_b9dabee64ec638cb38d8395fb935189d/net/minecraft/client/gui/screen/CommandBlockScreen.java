package net.minecraft.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.play.client.CUpdateCommandBlockPacket;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommandBlockScreen extends AbstractCommandBlockScreen {
   private final CommandBlockTileEntity commandBlock;
   private Button modeBtn;
   private Button conditionalBtn;
   private Button autoExecBtn;
   private CommandBlockTileEntity.Mode commandBlockMode = CommandBlockTileEntity.Mode.REDSTONE;
   private boolean conditional;
   private boolean automatic;

   public CommandBlockScreen(CommandBlockTileEntity commandBlockIn) {
      this.commandBlock = commandBlockIn;
   }

   CommandBlockLogic getLogic() {
      return this.commandBlock.getCommandBlockLogic();
   }

   int func_195236_i() {
      return 135;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.modeBtn = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50 - 100 - 4, 165, 100, 20, new TranslationTextComponent("advMode.mode.sequence"), (p_214191_1_) -> {
         this.nextMode();
         this.updateMode();
      }));
      this.conditionalBtn = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50, 165, 100, 20, new TranslationTextComponent("advMode.mode.unconditional"), (p_214190_1_) -> {
         this.conditional = !this.conditional;
         this.updateConditional();
      }));
      this.autoExecBtn = this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 50 + 4, 165, 100, 20, new TranslationTextComponent("advMode.mode.redstoneTriggered"), (p_214189_1_) -> {
         this.automatic = !this.automatic;
         this.updateAutoExec();
      }));
      this.doneButton.field_230693_o_ = false;
      this.trackOutputButton.field_230693_o_ = false;
      this.modeBtn.field_230693_o_ = false;
      this.conditionalBtn.field_230693_o_ = false;
      this.autoExecBtn.field_230693_o_ = false;
   }

   public void updateGui() {
      CommandBlockLogic commandblocklogic = this.commandBlock.getCommandBlockLogic();
      this.commandTextField.setText(commandblocklogic.getCommand());
      this.field_195238_s = commandblocklogic.shouldTrackOutput();
      this.commandBlockMode = this.commandBlock.getMode();
      this.conditional = this.commandBlock.isConditional();
      this.automatic = this.commandBlock.isAuto();
      this.updateTrackOutput();
      this.updateMode();
      this.updateConditional();
      this.updateAutoExec();
      this.doneButton.field_230693_o_ = true;
      this.trackOutputButton.field_230693_o_ = true;
      this.modeBtn.field_230693_o_ = true;
      this.conditionalBtn.field_230693_o_ = true;
      this.autoExecBtn.field_230693_o_ = true;
   }

   public void func_231152_a_(Minecraft p_231152_1_, int p_231152_2_, int p_231152_3_) {
      super.func_231152_a_(p_231152_1_, p_231152_2_, p_231152_3_);
      this.updateTrackOutput();
      this.updateMode();
      this.updateConditional();
      this.updateAutoExec();
      this.doneButton.field_230693_o_ = true;
      this.trackOutputButton.field_230693_o_ = true;
      this.modeBtn.field_230693_o_ = true;
      this.conditionalBtn.field_230693_o_ = true;
      this.autoExecBtn.field_230693_o_ = true;
   }

   protected void func_195235_a(CommandBlockLogic commandBlockLogicIn) {
      this.field_230706_i_.getConnection().sendPacket(new CUpdateCommandBlockPacket(new BlockPos(commandBlockLogicIn.getPositionVector()), this.commandTextField.getText(), this.commandBlockMode, commandBlockLogicIn.shouldTrackOutput(), this.conditional, this.automatic));
   }

   private void updateMode() {
      switch(this.commandBlockMode) {
      case SEQUENCE:
         this.modeBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.sequence"));
         break;
      case AUTO:
         this.modeBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.auto"));
         break;
      case REDSTONE:
         this.modeBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.redstone"));
      }

   }

   private void nextMode() {
      switch(this.commandBlockMode) {
      case SEQUENCE:
         this.commandBlockMode = CommandBlockTileEntity.Mode.AUTO;
         break;
      case AUTO:
         this.commandBlockMode = CommandBlockTileEntity.Mode.REDSTONE;
         break;
      case REDSTONE:
         this.commandBlockMode = CommandBlockTileEntity.Mode.SEQUENCE;
      }

   }

   private void updateConditional() {
      if (this.conditional) {
         this.conditionalBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.conditional"));
      } else {
         this.conditionalBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.unconditional"));
      }

   }

   private void updateAutoExec() {
      if (this.automatic) {
         this.autoExecBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.autoexec.bat"));
      } else {
         this.autoExecBtn.func_238482_a_(new TranslationTextComponent("advMode.mode.redstoneTriggered"));
      }

   }
}