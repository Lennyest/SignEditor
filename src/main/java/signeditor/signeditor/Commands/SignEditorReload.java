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
    private String noPermissions = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("noPermissions").toString());
    private boolean useNPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useNPprefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("SignEditor.RELOAD")) {
            if (useNPprefix) {
                sender.sendMessage(prefix + " " + noPermissions);
            } else {
                sender.sendMessage(noPermissions);
            }
        }
            if (useRMprefix) {
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                sender.sendMessage(prefix + " " + reloadMessage.replaceAll("\\{player}", sender.getName()));
            } else {
                SignEditor.getPl().fileConfiguration = YamlConfiguration.loadConfiguration(SignEditor.getPl().file);
                sender.sendMessage(reloadMessage);
            }
            return true;
    }
}