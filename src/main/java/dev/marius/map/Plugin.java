package dev.marius.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.marius.map.commands.*;
import dev.marius.map.events.PlayerClickListener;
import dev.marius.map.events.VanishListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class Plugin extends JavaPlugin {
    static Plugin instance;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Config config;

    private final File configFile = new File("plugins\\MAP\\config.json");

    @Override
    public void onEnable() {
        instance = this;

        try {
            this.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);
        getServer().getPluginManager().registerEvents(new VanishListener(this), this);

        for (Command command : new Command[] {
                new GamemodeCommand(),
                new PlayerguiCommand(),
                new StaffChatCommand(),
                new TpHereCommand(),
                new TpToCommand(),
                new VanishCommand()
        }) {
            command.register(this);
        }

        getServer().getConsoleSender().sendMessage("[MAP] Enabled plugin");
    }

    @Override
    public void onDisable() {
        try(FileWriter writer = new FileWriter(getConfigFile())) {
            writer.write(getGson().toJson(getConfiguration(), Config.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getConsoleSender().sendMessage("[MAP] Disabled plugin");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadConfig() throws IOException {
        String content;
        File file = getConfigFile();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            content = "{}";
        } else {
            try(FileReader reader = new FileReader(file)) {
                StringBuilder builder = new StringBuilder(); int current = 0;
                while ((current = reader.read()) != -1) builder.append((char) current);
                content = builder.toString();
            }
        }
        config = getGson().fromJson(content, Config.class);
    }

    public static Gson getGson() {
        return gson;
    }

    @NotNull
    public static Config getConfiguration() {
        return config;
    }

    public File getConfigFile() {
        return configFile;
    }
}
