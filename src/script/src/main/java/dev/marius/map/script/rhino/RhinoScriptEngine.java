package dev.marius.map.script.rhino;

import org.jetbrains.annotations.*;
import org.mozilla.javascript.*;

import javax.script.ScriptException;
import java.util.Map;

public class RhinoScriptEngine {
    private int timeLimit;

    public void setTimeLimit(int milliseconds) {
        timeLimit = milliseconds;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public Object evaluate(String script, String filename, @NotNull Map<String, Object> args)
            throws Throwable {
        RhinoContextFactory factory = new RhinoContextFactory(timeLimit);
        Context cx = factory.enterContext();
        cx.setClassShutter(new MinecraftHidingClassShutter());
        ScriptableObject scriptable = new ImporterTopLevel(cx);
        Scriptable scope = cx.initStandardObjects(scriptable);

        for (Map.Entry<String, Object> entry : args.entrySet()) {
            ScriptableObject.putProperty(scope, entry.getKey(),
                    Context.javaToJS(entry.getValue(), scope)
            );
        }
        try {
            return cx.evaluateString(scope, script, filename, 1, null);
        } catch (Error e) {
            throw new ScriptException(e.getMessage());
        } catch (RhinoException e) {
            String msg;
            int line = (line = e.lineNumber()) == 0 ? -1 : line;

            if (e instanceof JavaScriptException javaScriptException) {
                msg = String.valueOf(javaScriptException.getValue());
            } else {
                msg = e.getMessage();
            }

            ScriptException scriptException = new ScriptException(msg, e.sourceName(), line);
            scriptException.initCause(e);

            throw scriptException;
        } finally {
            Context.exit();
        }
    }
}
