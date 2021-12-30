import org.carbon.vm2.script.LuaScriptEngineFactory;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

public class Main {
    @Test
    public void testScriptEngine() {
        ScriptEngineFactory factory = new LuaScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();

        Thread executor = new Thread(() -> {
            try {
                engine.eval("while true do print(\"hi\") end");
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        });

        executor.start();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        executor.interrupt();
                    }
                },
                5000
        );
    }
}
