package dev.marius.map.script;

import dev.marius.map.script.rhino.RhinoScriptEngine;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Script {
    private Script() {}

    /**
     * Executes a script via the <a href="https://github.com/mozilla/rhino/">rhino</a> javascript engine
     *
     * @param location Location of the script file
     * @param executor The executor of the script
     * @param arguments Arguments for the script
     * @param parameters Custom parameters for the scripts
     * @param failureHandler {@link ScriptResult Result} handler that is called at every exception and other things
     *
     * @return The return value of the executed script. Returns null if there was a exception or the script
     *         isn't returning anything
     */
    public static @Nullable Object launchScript(@NotNull File location, Player executor, String[] arguments,
                                                Map<String, Object> parameters, Consumer<ScriptResult> failureHandler) {
        String filename = location.getPath();
        int index = filename.lastIndexOf('.');
        String ext = filename.substring(index + 1);

        if (!ext.equalsIgnoreCase("js")) {
            failureHandler.accept(ScriptResult.NOT_VALID_SCRIPT_FILE);
            return null;
        }

        String script;

        try {
            InputStream stream = new FileInputStream(location);
            DataInputStream in = new DataInputStream(stream);
            byte[] data = new byte[in.available()];
            in.readFully(data);
            in.close();
            script = new String(data, 0, data.length, StandardCharsets.UTF_8);
        } catch (IOException e) {
            failureHandler.accept(ScriptResult.COULD_NOT_READ_SCRIPT);
            return null;
        }

        RhinoScriptEngine engine;

        try {
            engine = new RhinoScriptEngine();
        } catch (NoClassDefFoundError ignored) {
            failureHandler.accept(ScriptResult.NO_SCRIPT_ENGINE);
            return null;
        }

        ScriptContext context = new ScriptContext();

        engine.setTimeLimit(3000);

        Map<String, Object> vars = new HashMap<>();
        vars.put("args", arguments);
        vars.put("context", context);
        vars.put("player", executor);
        vars.putAll(parameters);

        Object result;
        try {
            result = engine.evaluate(script, filename, vars);
        } catch (NumberFormatException e) {
            throw e;
        } catch (Throwable e) {
            failureHandler.accept(ScriptResult.ERROR_IN_SCRIPT);
            return null;
        }

        failureHandler.accept(ScriptResult.SUCCESS);
        return result;
    }
}
