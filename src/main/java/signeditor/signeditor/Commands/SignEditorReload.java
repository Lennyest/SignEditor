package signeditor.signeditor.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import signeditor.signeditor.SignEditor;

import java.io.IOException;

public class SignEditorReload implements CommandExecutor {
    private String prefix = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString());
    private String reloadMessage = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("reloadMessage").toString());
    private boolean useRMprefix = SignEditor.getPl().fileConfiguration.getBoolean("useRMprefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (useRMprefix) {
                SignEditor.getPl().fileConfiguration.load(SignEditor.getPl().file);
                sender.sendMessage(prefix + " " + reloadMessage);
                return true;
            } else {
                SignEditor.getPl().fileConfiguration.load(SignEditor.getPl().file);
                sender.sendMessage(reloadMessage);
                return true;
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return false;
    }
}
