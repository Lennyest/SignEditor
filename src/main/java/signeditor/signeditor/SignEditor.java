package signeditor.signeditor;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import signeditor.signeditor.Commands.SignEditorBypass;
import signeditor.signeditor.Commands.SignEditorCommand;
import signeditor.signeditor.Commands.SignEditorGUI;
import signeditor.signeditor.Commands.SignEditorReload;
import signeditor.signeditor.Events.SIgnEditorChangeEvent;
import signeditor.signeditor.Events.SignEditorBlockInteraction;

import java.io.File;
import java.io.IOException;

public final class SignEditor extends JavaPlugin {
    public File file = new File(getDataFolder() + File.separator + "sign-config.yml");
    public FileConfiguration fileConfiguration = new YamlConfiguration();

    public File blockFile = new File(getDataFolder() + File.separator + "sign-data.yml");
    public FileConfiguration blockConfiguration = new YamlConfiguration();
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
        createBlockData();
        getCommand("SignEditor").setExecutor(new SignEditorCommand());
        getCommand("SignEditorReload").setExecutor(new SignEditorReload());
        getCommand("SignEditorGUI").setExecutor(new SignEditorGUI());
        getCommand("SignEditorBypass").setExecutor(new SignEditorBypass());

        getServer().getPluginManager().registerEvents(new SignEditorGUI(), this);
        getServer().getPluginManager().registerEvents(new SIgnEditorChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEditorBlockInteraction(), this);
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

    private void createBlockData() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (!blockFile.exists()) {
            blockFile.getParentFile().mkdirs();
            saveResource("sign-data.yml", false);
        }

        try {
            blockConfiguration.load(blockFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
