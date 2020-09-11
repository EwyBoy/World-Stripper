package net.minecraft.client.gui.screen;

import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.network.play.client.CUpdateMinecartCommandBlockPacket;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EditMinecartCommandBlockScreen extends AbstractCommandBlockScreen {
   private final CommandBlockLogic commandBlockLogic;

   public EditMinecartCommandBlockScreen(CommandBlockLogic p_i46595_1_) {
      this.commandBlockLogic = p_i46595_1_;
   }

   public CommandBlockLogic getLogic() {
      return this.commandBlockLogic;
   }

   int func_195236_i() {
      return 150;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_195238_s = this.getLogic().shouldTrackOutput();
      this.updateTrackOutput();
      this.commandTextField.setText(this.getLogic().getCommand());
   }

   protected void func_195235_a(CommandBlockLogic commandBlockLogicIn) {
      if (commandBlockLogicIn instanceof CommandBlockMinecartEntity.MinecartCommandLogic) {
         CommandBlockMinecartEntity.MinecartCommandLogic commandblockminecartentity$minecartcommandlogic = (CommandBlockMinecartEntity.MinecartCommandLogic)commandBlockLogicIn;
         this.field_230706_i_.getConnection().sendPacket(new CUpdateMinecartCommandBlockPacket(commandblockminecartentity$minecartcommandlogic.getMinecart().getEntityId(), this.commandTextField.getText(), commandBlockLogicIn.shouldTrackOutput()));
      }

   }
}