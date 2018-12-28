package signeditor.signeditor.Utils;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import signeditor.signeditor.Events.SignEditorBlockInteraction;

import java.io.File;
import java.io.IOException;

public class LennyLib {

    @Getter
    public static boolean isBypassEnabled;

    private static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void lennyGetClicked(Player player, String fileName, String value, FileConfiguration configuration, File f, Inventory inventory, String updatedMessage, AsyncPlayerChatEvent event) {
            configuration.set(fileName, value);
            try {
                configuration.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }

            player.openInventory(inventory);
            player.updateInventory();
            player.sendMessage(colorize(updatedMessage));
            event.setCancelled(true);

    }

    public static boolean isSignProtected(Player player, Block block) {
        String code = SignEditorBlockInteraction.getGeneratedCode();
        FileConfiguration fileCFG = SignEditorBlockInteraction.getFileConfiguration();
        if (fileCFG.contains(code + " " + player.getUniqueId())) {
            if (fileCFG.get(code + " " + player.getUniqueId(), block.getLocation().toString()) != null) {
                return true;
            }
            if (isBypassEnabled) {
                return true;
            }
        }
        return false;
    }
}
