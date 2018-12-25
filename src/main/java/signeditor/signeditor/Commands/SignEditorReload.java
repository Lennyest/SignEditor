package signeditor.signeditor.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import signeditor.signeditor.SignEditor;


public class SignEditorReload implements CommandExecutor {
    private String prefix = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString());
    private String reloadMessage = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("reloadMessage").toString());
    private boolean useRMprefix = SignEditor.getPl().fileConfiguration.getBoolean("useRMprefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (useRMprefix) {
                SignEditor.getPl().saveResource("sign-config.yml", true);
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                sender.sendMessage(prefix + " " + reloadMessage);
            } else {
                SignEditor.getPl().saveResource("sign-config.yml", true);
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                sender.sendMessage(reloadMessage);
            }
            return true;
    }
}