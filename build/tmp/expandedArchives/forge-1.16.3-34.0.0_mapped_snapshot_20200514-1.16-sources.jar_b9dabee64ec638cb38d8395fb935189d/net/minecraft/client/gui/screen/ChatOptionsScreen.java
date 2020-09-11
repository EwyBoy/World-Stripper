package net.minecraft.client.gui.screen;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatOptionsScreen extends WithNarratorSettingsScreen {
   private static final AbstractOption[] CHAT_OPTIONS = new AbstractOption[]{AbstractOption.CHAT_VISIBILITY, AbstractOption.CHAT_COLOR, AbstractOption.CHAT_LINKS, AbstractOption.CHAT_LINKS_PROMPT, AbstractOption.CHAT_OPACITY, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND_OPACITY, AbstractOption.CHAT_SCALE, AbstractOption.field_238235_g_, AbstractOption.field_238236_h_, AbstractOption.CHAT_WIDTH, AbstractOption.CHAT_HEIGHT_FOCUSED, AbstractOption.CHAT_HEIGHT_UNFOCUSED, AbstractOption.NARRATOR, AbstractOption.AUTO_SUGGEST_COMMANDS, AbstractOption.REDUCED_DEBUG_INFO};

   public ChatOptionsScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
      super(parentScreenIn, gameSettingsIn, new TranslationTextComponent("options.chat.title"), CHAT_OPTIONS);
   }
}