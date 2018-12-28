package signeditor.signeditor.Events;

import lombok.Getter;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import signeditor.signeditor.Commands.SignEditorGUI;
import signeditor.signeditor.SignEditor;
import signeditor.signeditor.Utils.LennyLib;

import java.io.File;

public class SIgnEditorChangeEvent implements Listener {

    private boolean clickedIsNotPlayer;
    private boolean clickedNoPermissions;
    private boolean clickedIncorrectUsage;
    private boolean clickedUpdatedSign;
    private boolean clickedPrefix;


    private File f = SignEditor.getPl().file;
    private FileConfiguration configuration = SignEditor.getPl().fileConfiguration;

    @EventHandler
    public void inventoryClickPrefix(InventoryClickEvent event) {

        if (event.getInventory().getTitle().equalsIgnoreCase("Messages GUI")) {
            if (event.getCurrentItem().getItemMeta() == null) return;
            if (event.getCurrentItem().getType() == Material.AIR) return;
            Player player = (Player) event.getWhoClicked();
            if (player.hasPermission("SignEditor.CHANGE")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "isNotPlayer")) {
                    player.closeInventory();
                    player.sendTitle(ChatColor.GREEN + "Enter a new message", "", 10, 25, 10);
                    clickedIsNotPlayer = true;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "noPermissions")) {
                    player.closeInventory();
                    player.sendTitle(ChatColor.GREEN + "Enter a new message", "", 10, 25, 10);
                    clickedNoPermissions = true;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "incorrectUsage")) {
                    player.closeInventory();
                    player.sendTitle(ChatColor.GREEN + "Enter a new message", "", 10, 25, 10);
                    clickedIncorrectUsage = true;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "updatedSign")) {
                    player.closeInventory();
                    player.sendTitle(ChatColor.GREEN + "Enter a new message", "", 10, 25, 10);
                    clickedUpdatedSign = true;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "prefix")) {
                    player.closeInventory();
                    player.sendTitle(ChatColor.GREEN + "Enter a new message", "", 10, 25, 10);
                    clickedPrefix = true;
                }
            }
        }
    }

    @EventHandler
    public void changeConfigValueForIsNotPlayer(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("SignEditor.CHANGE")) {

                if (clickedIsNotPlayer) {
                    LennyLib.lennyGetClicked(player, "isNotPlayer", event.getMessage(), configuration, f, SignEditorGUI.messagesGUI, ChatColor.GREEN + "Updated Message!", event);
                } else if (clickedNoPermissions) {
                    LennyLib.lennyGetClicked(player, "noPermissions", event.getMessage(), configuration, f, SignEditorGUI.messagesGUI, ChatColor.GREEN + "Updated Message!", event);
                } else if (clickedPrefix) {
                    LennyLib.lennyGetClicked(player, "prefix", event.getMessage(), configuration, f, SignEditorGUI.messagesGUI, ChatColor.GREEN + "Updated Message!", event);
                } else  if (clickedUpdatedSign) {
                    LennyLib.lennyGetClicked(player, "updatedSign", event.getMessage(), configuration, f, SignEditorGUI.messagesGUI, ChatColor.GREEN + "Updated Message!", event);
                } else if (clickedIncorrectUsage) {
                    LennyLib.lennyGetClicked(player, "incorrectUsage", event.getMessage(), configuration, f, SignEditorGUI.messagesGUI, ChatColor.GREEN + "Updated Message!", event);
                }
        }
    }
}
