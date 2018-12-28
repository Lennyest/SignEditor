package signeditor.signeditor.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

import java.util.ArrayList;


public class SignEditorGUI implements CommandExecutor, Listener {

    private static Inventory SignEditorGUI = Bukkit.createInventory(null, 36, "SignEditor GUI");
    public static Inventory messagesGUI = Bukkit.createInventory(null, 36, "Messages GUI");


    private String isNotPlayer = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("isNotPlayer").toString());
    private static boolean useINPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useINPprefix");
    private String noPermissions = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("noPermissions").toString());
    private static boolean useNPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useNPprefix");
    private String updatedSign = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("updatedSign").toString());
    private static boolean useUSprefix = SignEditor.getPl().fileConfiguration.getBoolean("useUSprefix");
    private String incorrectUsage = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("incorrectUsage").toString());
    private static boolean useIUprefix = SignEditor.getPl().fileConfiguration.getBoolean("useIUprefix");
    private String prefix = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString());

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

    private double version = (double) SignEditor.getPl().fileConfiguration.get("Version");
    private boolean useVersionItem = (boolean) SignEditor.getPl().fileConfiguration.get("UseVersionItem");

    /*
    Add a null check for basically everything, need to find a good way to do it without having to repeat myself too much.
    Add a {sender} variable for the config which replaces with sender.
    Move all itemstacks to another class to avoid clutter like now.
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
                versionItemMeta.setDisplayName(ChatColor.GREEN + "Current Version: " + version);
                versionItem.setItemMeta(versionItemMeta);
                SignEditorGUI.setItem(0, versionItem);
            }

            Player player = (Player) sender;
            player.openInventory(SignEditorGUI);
        }
        return true;
    }

    @EventHandler
    public void eventClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getTitle().equalsIgnoreCase("SignEditor GUI")) {
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() == Material.AIR) return;

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', closeButtonDisplayName))) {
                event.setCancelled(true);
                player.closeInventory();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', messagesButtonDisplayName))) {
                player.closeInventory();
                player.openInventory(messagesGUI);
            }

        }

        ItemStack isNotPlayer = new ItemStack(Material.PAPER, 1);
        ItemMeta isNotPlayerItemMeta = isNotPlayer.getItemMeta();
        isNotPlayerItemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "isNotPlayer");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("isNotPlayer").toString()));
        isNotPlayerItemMeta.setLore(lore);
        isNotPlayer.setItemMeta(isNotPlayerItemMeta);

        if (useINPprefix) {
            ItemStack INPGreen = new ItemStack(Material.STAINED_CLAY, 1, (byte) 13);
            ItemMeta INPGreenMeta = INPGreen.getItemMeta();
            INPGreenMeta.setDisplayName(ChatColor.GREEN + "Use isNotPlayer prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useINPprefix"));
            INPGreen.setItemMeta(INPGreenMeta);
            messagesGUI.setItem(10, INPGreen);
        } else {
            ItemStack INPRed = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
            ItemMeta INPRedMeta = INPRed.getItemMeta();
            INPRedMeta.setDisplayName(ChatColor.GREEN + "Use isNotPlayer prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useINPprefix"));
            INPRed.setItemMeta(INPRedMeta);
            messagesGUI.setItem(10, INPRed);
        }

        ItemStack noPermissions = new ItemStack(Material.PAPER, 1);
        ItemMeta noPermissionsItemMeta = noPermissions.getItemMeta();
        noPermissionsItemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "noPermissions");
        ArrayList<String> noPermissionsLore = new ArrayList<>();
        noPermissionsLore.add(ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("noPermissions").toString()));
        noPermissionsItemMeta.setLore(noPermissionsLore);
        noPermissions.setItemMeta(noPermissionsItemMeta);

        if (useNPprefix) {
            ItemStack NPGreen = new ItemStack(Material.STAINED_CLAY, 1, (byte) 13);
            ItemMeta INPGreenMeta = NPGreen.getItemMeta();
            INPGreenMeta.setDisplayName(ChatColor.GREEN + "Use noPermissions prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useNPprefix"));
            NPGreen.setItemMeta(INPGreenMeta);
            messagesGUI.setItem(12, NPGreen);
        } else {
            ItemStack NPRed = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
            ItemMeta NPRedMeta = NPRed.getItemMeta();
            NPRedMeta.setDisplayName(ChatColor.GREEN + "Use noPermissions prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useNPprefix"));
            NPRed.setItemMeta(NPRedMeta);
            messagesGUI.setItem(12, NPRed);
        }

        ItemStack incorrectUsage = new ItemStack(Material.PAPER, 1);
        ItemMeta incorrectUsageMeta = noPermissions.getItemMeta();
        incorrectUsageMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "incorrectUsage");
        ArrayList<String> incorrectUsageLore = new ArrayList<>();
        incorrectUsageLore.add(ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("incorrectUsage").toString()));
        incorrectUsageMeta.setLore(incorrectUsageLore);
        incorrectUsage.setItemMeta(incorrectUsageMeta);



        if (useIUprefix) {
            ItemStack IUGreen = new ItemStack(Material.STAINED_CLAY, 1, (byte) 13);
            ItemMeta INPGreenMeta = IUGreen.getItemMeta();
            INPGreenMeta.setDisplayName(ChatColor.GREEN + "Use incorrectUsage prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useIUprefix"));
            IUGreen.setItemMeta(INPGreenMeta);
            messagesGUI.setItem(14, IUGreen);
        } else {
            ItemStack IURed = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
            ItemMeta IURedMeta = IURed.getItemMeta();
            IURedMeta.setDisplayName(ChatColor.GREEN + "Use incorrectUsage prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useIUprefix"));
            IURed.setItemMeta(IURedMeta);
            messagesGUI.setItem(14, IURed);
        }


        ItemStack updatedSign = new ItemStack(Material.PAPER, 1);
        ItemMeta updatedSignItemMeta = updatedSign.getItemMeta();
        updatedSignItemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "updatedSign");
        ArrayList<String> updatedSignlore = new ArrayList<>();
        updatedSignlore.add(ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("updatedSign").toString()));
        updatedSignItemMeta.setLore(updatedSignlore);
        updatedSign.setItemMeta(updatedSignItemMeta);

        if (useUSprefix) {
            ItemStack IUGreen = new ItemStack(Material.STAINED_CLAY, 1, (byte) 13);
            ItemMeta INPGreenMeta = IUGreen.getItemMeta();
            INPGreenMeta.setDisplayName(ChatColor.GREEN + "Use updatedSign prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useUSprefix"));
            IUGreen.setItemMeta(INPGreenMeta);
            messagesGUI.setItem(16, IUGreen);
        } else {
            ItemStack IURed = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
            ItemMeta IURedMeta = IURed.getItemMeta();
            IURedMeta.setDisplayName(ChatColor.GREEN + "Use updatedSign prefix: " + ChatColor.WHITE + ChatColor.BOLD + SignEditor.getPl().fileConfiguration.get("useUSprefix"));
            IURed.setItemMeta(IURedMeta);
            messagesGUI.setItem(16, IURed);
        }

        ItemStack prefix = new ItemStack(Material.PAPER, 1);
        ItemMeta prefixM = prefix.getItemMeta();
        prefixM.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "prefix");
        ArrayList<String> prefixLore = new ArrayList<>();
        prefixLore.add(ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString()));
        prefixM.setLore(prefixLore);
        prefix.setItemMeta(prefixM);

        ItemStack signpostMax = new ItemStack(Material.PAPER, 1);
        ItemMeta signpostMeta = signpostMax.getItemMeta();
        signpostMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SignPostMaxDistance");
        ArrayList<String> signpostMaxLore = new ArrayList<>();
        signpostMaxLore.add(ChatColor.GREEN + "Max distance: " + ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("SignPostMaxDistance").toString()));
        signpostMeta.setLore(signpostMaxLore);
        signpostMax.setItemMeta(signpostMeta);

        ItemStack wallpostMax = new ItemStack(Material.PAPER, 1);
        ItemMeta wallpostMeta = signpostMax.getItemMeta();
        wallpostMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SignWallMaxDistance");
        ArrayList<String> wallpostMaxLore = new ArrayList<>();
        wallpostMaxLore.add(ChatColor.GREEN + "Max distance: " + ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("SignWallMaxDistance").toString()));
        wallpostMeta.setLore(wallpostMaxLore);
        wallpostMax.setItemMeta(wallpostMeta);

        ItemStack closeButton = new ItemStack(closeButtonMaterial, 1, (byte) closeButtonDataType);
        ItemMeta closeButtonM = closeButton.getItemMeta();
        closeButtonM.setDisplayName(ChatColor.translateAlternateColorCodes('&', closeButtonDisplayName));
        closeButton.setItemMeta(closeButtonM);

        if (event.getInventory().getTitle().equalsIgnoreCase("Messages GUI")) {
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() == Material.AIR) return;

            event.setCancelled(true);

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', closeButtonDisplayName))) {
                player.closeInventory();
            }
        }


        messagesGUI.setItem(31, closeButton);
        messagesGUI.setItem(1, isNotPlayer);
        messagesGUI.setItem(3, noPermissions);
        messagesGUI.setItem(5, incorrectUsage);
        messagesGUI.setItem(7, updatedSign);
        messagesGUI.setItem(20, signpostMax);
        messagesGUI.setItem(22, prefix);
        messagesGUI.setItem(24, wallpostMax);
    }
}
