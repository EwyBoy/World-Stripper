package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class MultiplayerScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ServerPinger oldServerPinger = new ServerPinger();
   private final Screen parentScreen;
   protected ServerSelectionList serverListSelector;
   private ServerList savedServerList;
   private Button btnEditServer;
   private Button btnSelectServer;
   private Button btnDeleteServer;
   /** The text to be displayed when the player's cursor hovers over a server listing. */
   private List<ITextComponent> hoveringText;
   private ServerData selectedServer;
   private LanServerDetector.LanServerList lanServerList;
   private LanServerDetector.LanServerFindThread lanServerDetector;
   private boolean initialized;

   public MultiplayerScreen(Screen parentScreen) {
      super(new TranslationTextComponent("multiplayer.title"));
      this.parentScreen = parentScreen;
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.field_230706_i_.keyboardListener.enableRepeatEvents(true);
      if (this.initialized) {
         this.serverListSelector.func_230940_a_(this.field_230708_k_, this.field_230709_l_, 32, this.field_230709_l_ - 64);
      } else {
         this.initialized = true;
         this.savedServerList = new ServerList(this.field_230706_i_);
         this.savedServerList.loadServerList();
         this.lanServerList = new LanServerDetector.LanServerList();

         try {
            this.lanServerDetector = new LanServerDetector.LanServerFindThread(this.lanServerList);
            this.lanServerDetector.start();
         } catch (Exception exception) {
            LOGGER.warn("Unable to start LAN server detection: {}", (Object)exception.getMessage());
         }

         this.serverListSelector = new ServerSelectionList(this, this.field_230706_i_, this.field_230708_k_, this.field_230709_l_, 32, this.field_230709_l_ - 64, 36);
         this.serverListSelector.updateOnlineServers(this.savedServerList);
      }

      this.field_230705_e_.add(this.serverListSelector);
      this.btnSelectServer = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 154, this.field_230709_l_ - 52, 100, 20, new TranslationTextComponent("selectServer.select"), (p_214293_1_) -> {
         this.connectToSelected();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 50, this.field_230709_l_ - 52, 100, 20, new TranslationTextComponent("selectServer.direct"), (p_214286_1_) -> {
         this.selectedServer = new ServerData(I18n.format("selectServer.defaultName"), "", false);
         this.field_230706_i_.displayGuiScreen(new ServerListScreen(this, this::func_214290_d, this.selectedServer));
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4 + 50, this.field_230709_l_ - 52, 100, 20, new TranslationTextComponent("selectServer.add"), (p_214288_1_) -> {
         this.selectedServer = new ServerData(I18n.format("selectServer.defaultName"), "", false);
         this.field_230706_i_.displayGuiScreen(new AddServerScreen(this, this::func_214284_c, this.selectedServer));
      }));
      this.btnEditServer = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 154, this.field_230709_l_ - 28, 70, 20, new TranslationTextComponent("selectServer.edit"), (p_214283_1_) -> {
         ServerSelectionList.Entry serverselectionlist$entry = this.serverListSelector.func_230958_g_();
         if (serverselectionlist$entry instanceof ServerSelectionList.NormalEntry) {
            ServerData serverdata = ((ServerSelectionList.NormalEntry)serverselectionlist$entry).getServerData();
            this.selectedServer = new ServerData(serverdata.serverName, serverdata.serverIP, false);
            this.selectedServer.copyFrom(serverdata);
            this.field_230706_i_.displayGuiScreen(new AddServerScreen(this, this::func_214292_b, this.selectedServer));
         }

      }));
      this.btnDeleteServer = this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 74, this.field_230709_l_ - 28, 70, 20, new TranslationTextComponent("selectServer.delete"), (p_214294_1_) -> {
         ServerSelectionList.Entry serverselectionlist$entry = this.serverListSelector.func_230958_g_();
         if (serverselectionlist$entry instanceof ServerSelectionList.NormalEntry) {
            String s = ((ServerSelectionList.NormalEntry)serverselectionlist$entry).getServerData().serverName;
            if (s != null) {
               ITextComponent itextcomponent = new TranslationTextComponent("selectServer.deleteQuestion");
               ITextComponent itextcomponent1 = new TranslationTextComponent("selectServer.deleteWarning", s);
               ITextComponent itextcomponent2 = new TranslationTextComponent("selectServer.deleteButton");
               ITextComponent itextcomponent3 = DialogTexts.field_240633_d_;
               this.field_230706_i_.displayGuiScreen(new ConfirmScreen(this::func_214285_a, itextcomponent, itextcomponent1, itextcomponent2, itextcomponent3));
            }
         }

      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4, this.field_230709_l_ - 28, 70, 20, new TranslationTextComponent("selectServer.refresh"), (p_214291_1_) -> {
         this.refreshServerList();
      }));
      this.func_230480_a_(new Button(this.field_230708_k_ / 2 + 4 + 76, this.field_230709_l_ - 28, 75, 20, DialogTexts.field_240633_d_, (p_214289_1_) -> {
         this.field_230706_i_.displayGuiScreen(this.parentScreen);
      }));
      this.func_214295_b();
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (this.lanServerList.getWasUpdated()) {
         List<LanServerInfo> list = this.lanServerList.getLanServers();
         this.lanServerList.setWasNotUpdated();
         this.serverListSelector.updateNetworkServers(list);
      }

      this.oldServerPinger.pingPendingNetworks();
   }

   public void func_231164_f_() {
      this.field_230706_i_.keyboardListener.enableRepeatEvents(false);
      if (this.lanServerDetector != null) {
         this.lanServerDetector.interrupt();
         this.lanServerDetector = null;
      }

      this.oldServerPinger.clearPendingNetworks();
   }

   private void refreshServerList() {
      this.field_230706_i_.displayGuiScreen(new MultiplayerScreen(this.parentScreen));
   }

   private void func_214285_a(boolean p_214285_1_) {
      ServerSelectionList.Entry serverselectionlist$entry = this.serverListSelector.func_230958_g_();
      if (p_214285_1_ && serverselectionlist$entry instanceof ServerSelectionList.NormalEntry) {
         this.savedServerList.func_217506_a(((ServerSelectionList.NormalEntry)serverselectionlist$entry).getServerData());
         this.savedServerList.saveServerList();
         this.serverListSelector.func_241215_a_((ServerSelectionList.Entry)null);
         this.serverListSelector.updateOnlineServers(this.savedServerList);
      }

      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_214292_b(boolean p_214292_1_) {
      ServerSelectionList.Entry serverselectionlist$entry = this.serverListSelector.func_230958_g_();
      if (p_214292_1_ && serverselectionlist$entry instanceof ServerSelectionList.NormalEntry) {
         ServerData serverdata = ((ServerSelectionList.NormalEntry)serverselectionlist$entry).getServerData();
         serverdata.serverName = this.selectedServer.serverName;
         serverdata.serverIP = this.selectedServer.serverIP;
         serverdata.copyFrom(this.selectedServer);
         this.savedServerList.saveServerList();
         this.serverListSelector.updateOnlineServers(this.savedServerList);
      }

      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_214284_c(boolean p_214284_1_) {
      if (p_214284_1_) {
         this.savedServerList.addServerData(this.selectedServer);
         this.savedServerList.saveServerList();
         this.serverListSelector.func_241215_a_((ServerSelectionList.Entry)null);
         this.serverListSelector.updateOnlineServers(this.savedServerList);
      }

      this.field_230706_i_.displayGuiScreen(this);
   }

   private void func_214290_d(boolean p_214290_1_) {
      if (p_214290_1_) {
         this.connectToServer(this.selectedServer);
      } else {
         this.field_230706_i_.displayGuiScreen(this);
      }

   }

   public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (super.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_)) {
         return true;
      } else if (p_231046_1_ == 294) {
         this.refreshServerList();
         return true;
      } else if (this.serverListSelector.func_230958_g_() != null) {
         if (p_231046_1_ != 257 && p_231046_1_ != 335) {
            return this.serverListSelector.func_231046_a_(p_231046_1_, p_231046_2_, p_231046_3_);
         } else {
            this.connectToSelected();
            return true;
         }
      } else {
         return false;
      }
   }

   public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.hoveringText = null;
      this.func_230446_a_(p_230430_1_);
      this.serverListSelector.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      func_238472_a_(p_230430_1_, this.field_230712_o_, this.field_230704_d_, this.field_230708_k_ / 2, 20, 16777215);
      super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
      if (this.hoveringText != null) {
         this.func_243308_b(p_230430_1_, this.hoveringText, p_230430_2_, p_230430_3_);
      }

   }

   public void connectToSelected() {
      ServerSelectionList.Entry serverselectionlist$entry = this.serverListSelector.func_230958_g_();
      if (serverselectionlist$entry instanceof ServerSelectionList.NormalEntry) {
         this.connectToServer(((ServerSelectionList.NormalEntry)serverselectionlist$entry).getServerData());
      } else if (serverselectionlist$entry instanceof ServerSelectionList.LanDetectedEntry) {
         LanServerInfo lanserverinfo = ((ServerSelectionList.LanDetectedEntry)serverselectionlist$entry).getServerData();
         this.connectToServer(new ServerData(lanserverinfo.getServerMotd(), lanserverinfo.getServerIpPort(), true));
      }

   }

   private void connectToServer(ServerData server) {
      this.field_230706_i_.displayGuiScreen(new ConnectingScreen(this, this.field_230706_i_, server));
   }

   public void func_214287_a(ServerSelectionList.Entry p_214287_1_) {
      this.serverListSelector.func_241215_a_(p_214287_1_);
      this.func_214295_b();
   }

   protected void func_214295_b() {
      this.btnSelectServer.field_230693_o_ = false;
      this.btnEditServer.field_230693_o_ = false;
      this.btnDeleteServer.field_230693_o_ = false;
      ServerSelectionList.Entry serverselectionlist$entry = this.serverListSelector.func_230958_g_();
      if (serverselectionlist$entry != null && !(serverselectionlist$entry instanceof ServerSelectionList.LanScanEntry)) {
         this.btnSelectServer.field_230693_o_ = true;
         if (serverselectionlist$entry instanceof ServerSelectionList.NormalEntry) {
            this.btnEditServer.field_230693_o_ = true;
            this.btnDeleteServer.field_230693_o_ = true;
         }
      }

   }

   @Override
   public void func_231175_as__() {
      this.field_230706_i_.displayGuiScreen(this.parentScreen);
   }

   public ServerPinger getOldServerPinger() {
      return this.oldServerPinger;
   }

   public void func_238854_b_(List<ITextComponent> p_238854_1_) {
      this.hoveringText = p_238854_1_;
   }

   public ServerList getServerList() {
      return this.savedServerList;
   }
}