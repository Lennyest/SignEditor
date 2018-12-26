package signeditor.signeditor.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import signeditor.signeditor.Commands.SignEditorGUI;
import signeditor.signeditor.SignEditor;

import java.io.IOException;

public class SIgnEditorChangeEvent implements Listener {

    private boolean clickedIsNotPlayer;
    private boolean clickedNoPermissions;
    private boolean clickedIncorrectUsage;
    private boolean clickedUpdatedSign;
    private boolean clickedPrefix;


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
                event.setCancelled(true);
                SignEditor.getPl().fileConfiguration.set("isNotPlayer", event.getMessage());
                try {
                    SignEditor.getPl().fileConfiguration.save(SignEditor.getPl().file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                player.sendMessage(ChatColor.GREEN + "Updated message!");
                clickedIsNotPlayer = false;
                player.openInventory(SignEditorGUI.messagesGUI);
            } else if (clickedNoPermissions) {
                event.setCancelled(true);
                SignEditor.getPl().fileConfiguration.set("noPermissions", event.getMessage());
                try {
                    SignEditor.getPl().fileConfiguration.save(SignEditor.getPl().file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clickedNoPermissions = false;
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                player.sendMessage(ChatColor.GREEN + "Updated message!");
            } else if (clickedIncorrectUsage) {
                event.setCancelled(true);
                SignEditor.getPl().fileConfiguration.set("incorrectUsage", event.getMessage());
                try {
                    SignEditor.getPl().fileConfiguration.save(SignEditor.getPl().file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clickedIncorrectUsage = false;
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                player.sendMessage(ChatColor.GREEN + "Updated message!");
            } else if (clickedUpdatedSign) {
                event.setCancelled(true);
                SignEditor.getPl().fileConfiguration.set("updatedSign", event.getMessage());
                try {
                    SignEditor.getPl().fileConfiguration.save(SignEditor.getPl().file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clickedUpdatedSign = false;
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                player.sendMessage(ChatColor.GREEN + "Updated message!");
            } else if (clickedPrefix) {
                event.setCancelled(true);
                SignEditor.getPl().fileConfiguration.set("prefix", event.getMessage());
                try {
                    SignEditor.getPl().fileConfiguration.save(SignEditor.getPl().file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clickedUpdatedSign = false;
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                player.sendMessage(ChatColor.GREEN + "Updated message!");
            }
        }
    }
}
