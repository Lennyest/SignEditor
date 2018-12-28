package signeditor.signeditor.Events;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import signeditor.signeditor.SignEditor;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

public class SignEditorBlockInteraction implements Listener {

    @Getter
    private static File file = SignEditor.getPl().blockFile;
    @Getter
    private static FileConfiguration fileConfiguration = SignEditor.getPl().blockConfiguration;
    @Getter
    private static String generatedCode;
    Calendar cal = Calendar.getInstance();

    @EventHandler
    public void placeSign(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.SIGN_POST || event.getBlockPlaced().getType() == Material.WALL_SIGN) {
            Player player = event.getPlayer();
            int randomCode = new Random().nextInt(800000)+200000;

            String actualCode = String.valueOf(randomCode);
            fileConfiguration.set(actualCode + " " + player.getUniqueId(), event.getBlockPlaced().getLocation().toString());
            fileConfiguration.set(actualCode, cal.getTime());
            player.sendMessage(actualCode);
            generatedCode = actualCode;

            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @EventHandler
    public void breakSign(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SIGN_POST || event.getBlock().getType() == Material.WALL_SIGN) {
            Player player = event.getPlayer();

            if (fileConfiguration.contains(generatedCode + " " + player.getUniqueId())) {
                fileConfiguration.set(generatedCode + " " + player.getUniqueId(), null);
                fileConfiguration.set(generatedCode,  null);

            } else {
                event.setCancelled(true);
            }

            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
