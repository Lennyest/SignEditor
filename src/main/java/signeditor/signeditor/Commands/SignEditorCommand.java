package signeditor.signeditor.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import signeditor.signeditor.SignEditor;

import java.util.HashSet;
import java.util.Set;

public class SignEditorCommand implements CommandExecutor {
    public String prefix = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("prefix").toString());
    public String isNotPlayer = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("isNotPlayer").toString());
    public boolean useINPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useINPprefix");
    public String noPermissions = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("noPermissions").toString());
    public boolean useNPprefix = SignEditor.getPl().fileConfiguration.getBoolean("useNPprefix");
    public String updatedSign = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("updatedSign").toString());
    public boolean useUSprefix = SignEditor.getPl().fileConfiguration.getBoolean("useUSprefix");
    public String incorrectUsage = ChatColor.translateAlternateColorCodes('&', SignEditor.getPl().fileConfiguration.get("incorrectUsage").toString());
    public boolean useIUprefix = SignEditor.getPl().fileConfiguration.getBoolean("useIUprefix");
    public int SignPostMaxDistance = (int) SignEditor.getPl().fileConfiguration.get("SignPostMaxDistance");
    public int SignWallMaxDistance = (int) SignEditor.getPl().fileConfiguration.get("SignWallMaxDistance");

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(args.length >= 1)) {
            if (useIUprefix) {
                sender.sendMessage(prefix + " " + incorrectUsage);
            } else {
                sender.sendMessage(incorrectUsage);
            }
        }

        if (!(sender instanceof Player)) {
            if (useINPprefix) {
                sender.sendMessage(prefix + " " + isNotPlayer);
            } else {
                sender.sendMessage(isNotPlayer);
            }
        }
        if (!sender.hasPermission("SignEditor.USE")) {
            if (useNPprefix) {
                sender.sendMessage(prefix + " " + noPermissions);
            } else {
                sender.sendMessage(noPermissions);
            }
        }

        if (args.length >= 1) {

            if (!isInt(args[0])) {
                if (useIUprefix) {
                    sender.sendMessage(prefix + " " + incorrectUsage);
                } else {
                    sender.sendMessage(incorrectUsage);
                }
            }


            Set<Material> matSet = new HashSet<>();

            Player player = (Player) sender;


            StringBuilder message = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                message.append(" ").append(args[i]);

            }
            matSet.add(Material.AIR);
            if (player.getTargetBlock(matSet, SignWallMaxDistance).getType() == Material.WALL_SIGN) {
                Block b = player.getTargetBlock(matSet, SignWallMaxDistance);
                Sign sign = (Sign) b.getLocation().getBlock().getState();
                if (isInt(args[0])) {
                    if ((Integer.parseInt(args[0])) <= 3) {
                        sign.setLine(Integer.parseInt(args[0]), ChatColor.translateAlternateColorCodes('&', message.toString()));
                        sign.update();
                        if (useUSprefix) {
                            player.sendMessage(prefix + " " + updatedSign);
                        } else {
                            player.sendMessage(updatedSign);
                        }
                        return true;
                    }
                }
            }
            if (player.getTargetBlock(matSet, SignPostMaxDistance).getType() == Material.SIGN_POST) {
                Block b = player.getTargetBlock(matSet, SignPostMaxDistance);
                Sign sign = (Sign) b.getLocation().getBlock().getState();
                if (isInt(args[0])) {
                    if ((Integer.parseInt(args[0])) <= 3) {
                        sign.setLine(Integer.parseInt(args[0]), ChatColor.translateAlternateColorCodes('&', message.toString()));
                        sign.update();
                        if (useUSprefix) {
                            player.sendMessage(prefix + " " + updatedSign);
                        } else {
                            player.sendMessage(updatedSign);
                        }
                        return true;
                    }
                }
            }
        }
        return true;
    }
}

