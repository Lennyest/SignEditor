package signeditor.signeditor.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import signeditor.signeditor.SignEditor;

public class SignEditorGUI implements CommandExecutor, Listener {

    private Inventory SignEditorGUI = Bukkit.createInventory(null, 36, "SignEditor GUI");

    private String prefix = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString());
    private double version = (double) SignEditor.getPl().fileConfiguration.get("Version");
    private String isNotPlayer = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("isNotPlayer").toString());
    private boolean useINPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useINPprefix");
    private String noPermissions = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("noPermissions").toString());
    private boolean useNPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useNPprefix");
    private String incorrectUsage = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("incorrectUsage").toString());
    private boolean useIUprefix = SignEditor.getPl().fileConfiguration.getBoolean("useIUprefix");

    private Material closeButtonMaterial = Material.valueOf(SignEditor.getPl().fileConfiguration.get("CloseButtonMaterial").toString());
    private String closeButtonDisplayName = SignEditor.getPl().fileConfiguration.get("CloseButtonDisplayName").toString();
    private int closeButtonDataType = (int) SignEditor.getPl().fileConfiguration.get("CloseButtonDataValue");
    private boolean closeButtonMaterialUse = (boolean) SignEditor.getPl().fileConfiguration.get("UseCloseButton");
    private int closeButtonMaterialPosition = (int) SignEditor.getPl().fileConfiguration.get("CloseButtonPosition");


    private Material messagesButtonMaterial = Material.valueOf(SignEditor.getPl().fileConfiguration.get("MessagesButtonMaterial").toString());
    private String messagesButtonDisplayName = SignEditor.getPl().fileConfiguration.get("MessagesButtonDisplayName").toString();
    private int messagesButtonDataType = (int) SignEditor.getPl().fileConfiguration.get("MessagesButtonDataValue");
    private boolean messagesButtonMaterialUse = (boolean) SignEditor.getPl().fileConfiguration.get("UseMessagesButton");
    private int messagesButtonMaterialPosition = (int) SignEditor.getPl().fileConfiguration.get("MessagesButtonPosition");

    private boolean useVersionItem = (boolean) SignEditor.getPl().fileConfiguration.get("UseVersionItem");

    /*
    Add a null check for basically everything, need to find a good way to do it without having to repeat myself too much.
    Finish reload command to make the plugin more user-friendly.
    Add a check if any items are overlapping, especially final ones like the version itemstack. Will probably have to do it manually.
    Anything else?
    */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (useINPprefix) {
                sender.sendMessage(prefix + " " + isNotPlayer);
            } else {
                sender.sendMessage(isNotPlayer);
            }
        } else { // To avoid ClassCastException

            if (!(args.length == 0)) {
                if (useIUprefix) {
                    sender.sendMessage(prefix + " " + incorrectUsage);
                } else {
                    sender.sendMessage(incorrectUsage);
                }
            }

            if (!sender.hasPermission("SignEditor.GUI")) {
                if (useNPprefix) {
                    sender.sendMessage(prefix + " " + noPermissions);
                } else {
                    sender.sendMessage(noPermissions);
                }
            }

            if (closeButtonMaterialUse) {
                if (closeButtonMaterial == null) {
                    ItemStack closeButtonR = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
                    ItemMeta closeButton2 = closeButtonR.getItemMeta();
                    closeButton2.setDisplayName(ChatColor.translateAlternateColorCodes('&', closeButtonDisplayName));
                    closeButtonR.setItemMeta(closeButton2);
                    SignEditorGUI.setItem(closeButtonMaterialPosition, closeButtonR);
                } else {
                    ItemStack closeButton = new ItemStack(closeButtonMaterial, 1, (byte) closeButtonDataType);
                    ItemMeta closeButtonM = closeButton.getItemMeta();
                    closeButtonM.setDisplayName(ChatColor.translateAlternateColorCodes('&', closeButtonDisplayName));
                    closeButton.setItemMeta(closeButtonM);
                    SignEditorGUI.setItem(closeButtonMaterialPosition, closeButton);
                }
            }

            if (messagesButtonMaterialUse) {
                if (messagesButtonMaterial == null) {
                    ItemStack messagesButton1 = new ItemStack(Material.PAPER, 1, (byte) 0);
                    ItemMeta messageMeta = messagesButton1.getItemMeta();
                    messageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesButtonDisplayName));
                    messagesButton1.setItemMeta(messageMeta);
                    SignEditorGUI.setItem(closeButtonMaterialPosition, messagesButton1);
                } else {
                    ItemStack wMessageButton = new ItemStack(messagesButtonMaterial, 1, (byte) messagesButtonDataType);
                    ItemMeta itemMeta = wMessageButton.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', messagesButtonDisplayName));
                    wMessageButton.setItemMeta(itemMeta);
                    SignEditorGUI.setItem(messagesButtonMaterialPosition, wMessageButton);
                }
            }

            if (useVersionItem) {
                ItemStack versionItem = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
                ItemMeta versionItemMeta = versionItem.getItemMeta();
                versionItemMeta.setDisplayName(ChatColor.GREEN + "Current Version: " + version );
                versionItem.setItemMeta(versionItemMeta);
                SignEditorGUI.setItem(0, versionItem);
            }
            Player player = (Player) sender;
            player.openInventory(SignEditorGUI);
        }
        return true;
    }

    @EventHandler
    public void closeButtonClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase( "SignEditorGUI")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(closeButtonDisplayName)) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("&6Messages")) { //Added at 06.03 not tested.
                    Player player = (Player) event.getWhoClicked();
                    event.setCancelled(true);
                    player.closeInventory();
                }
            }
        }
    }
}
