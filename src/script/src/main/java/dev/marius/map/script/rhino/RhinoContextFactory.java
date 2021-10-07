package dev.marius.map.script.rhino;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

public class RhinoContextFactory extends ContextFactory {
    protected int timeLimit;

    public RhinoContextFactory(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    protected Context makeContext() {
        RhinoContext cx = new RhinoContext(this);
        try {
            Context.class.getDeclaredField("VERSION_ES6");
            cx.setLanguageVersion(RhinoContext.VERSION_ES6);
        } catch (NoSuchFieldException e) {
            cx.setLanguageVersion(Context.VERSION_1_7);
        }
        cx.setInstructionObserverThreshold(10000);
        return cx;
    }

    @Override
    protected void observeInstructionCount(Context cx, int instructionCount) {
        RhinoContext mcx = (RhinoContext) cx;
        long currentTime = System.currentTimeMillis();

        if (currentTime - mcx.startTime > timeLimit) {
            throw new Error("Script timed out (" + timeLimit + "ms)");
        }
    }

    @Override
    protected Object doTopCall(
            Callable callable, Context cx, Scriptable scope,
            Scriptable thisObj, Object[] args
    ) {
        RhinoContext mcx = (RhinoContext) cx;
        mcx.startTime = System.currentTimeMillis();

        return super.doTopCall(callable, cx, scope, thisObj, args);
    }

    private static class RhinoContext extends Context {
        long startTime;

        private RhinoContext(ContextFactory factory) {
            super(factory);
        }
    }
}
