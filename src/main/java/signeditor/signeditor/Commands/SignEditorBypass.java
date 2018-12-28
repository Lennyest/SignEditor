package signeditor.signeditor.Commands;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import signeditor.signeditor.SignEditor;
import signeditor.signeditor.Utils.LennyLib;

public class SignEditorBypass implements CommandExecutor {

    private String prefix = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString());
    String bypassProtectionEnable = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("bypassProtectionEnable").toString());
    String bypassProtectionDisable = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("bypassProtectionDisable").toString());
    boolean useBPprefixEnable = (boolean) SignEditor.getPl().fileConfiguration.get("useBPprefixEnable");
    boolean useBPprefixDisable = (boolean) SignEditor.getPl().fileConfiguration.get("useBPprefixDisable");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;


        if (!player.hasPermission("SignEditor.BYPASS")) {
            return false;
        }


        if (!(args.length == 1)) {
            player.sendMessage(ChatColor.RED + "Wrong usage!");
        }

        if (args[0].equalsIgnoreCase("enable")) {
            if (useBPprefixEnable) {
                player.sendMessage(bypassProtectionEnable);
            } else {
                player.sendMessage(prefix + " " + bypassProtectionEnable);
            }
                LennyLib.isBypassEnabled = true;
                return true;
        }
        if (args[0].equalsIgnoreCase("disable")) {
            if (LennyLib.isBypassEnabled()) {
                LennyLib.isBypassEnabled = false;
                if (useBPprefixDisable) {
                    player.sendMessage(bypassProtectionDisable);
                } else {
                    player.sendMessage(prefix + " " + bypassProtectionDisable);
                }
                return true;
            }
        }

        return true;
    }
}
