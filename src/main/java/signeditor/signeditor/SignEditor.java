package signeditor.signeditor;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import signeditor.signeditor.Commands.SignEditorCommand;

import java.io.File;
import java.io.IOException;

public final class SignEditor extends JavaPlugin {
    public File file = new File(getDataFolder() + File.separator + "sign-config.yml");
    public FileConfiguration fileConfiguration = new YamlConfiguration();
    private static SignEditor pl;


    public SignEditor() {
        pl = this;
    }

    public static SignEditor getPl() {
        return pl;
    }

    @Override
    public void onEnable() {
        createConfig();
        getCommand("SignEditor").setExecutor(new SignEditorCommand());
    }

    private void createConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource("sign-config.yml", false);
        }

        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
