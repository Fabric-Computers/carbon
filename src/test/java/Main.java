import org.carbon.vm2.Globals;
import org.carbon.vm2.LuaValue;
import org.carbon.vm2.lib.jse.JsePlatform;
import org.carbon.vm2.script.LuaScriptEngineFactory;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

public class Main {

    public static void main(String[] args) {
        String script = """
                print("Test starting")

                local coro = coroutine.create(function() while true do print("Test") end end)
                
                coroutine.resume(coro)
                """;

        Globals globals = JsePlatform.debugGlobals();

        LuaValue chunk = globals.load(script, "script");

        Thread executor = new Thread(chunk::call);

        executor.start();

        globals.interrupt();
    }

    @Test
    public void testScriptEngine() {

        String script = """
                print("Test starting")
                coro = coroutine.create(function() while true do print("Test") end end)
                print(coro)
                coroutine.resume(coro)
                """;

        ScriptEngineFactory factory = new LuaScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();

        Thread executor = new Thread(() -> {

            try {
                engine.eval(script);
            } catch (ScriptException e) {
                e.printStackTrace();
            }

        });

        executor.start();

        /*
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        executor.interrupt();
                    }
                },
                5000
        );

         */
    }

    @Test
    public void testGlobals() {
        String script = """
                print("Test starting")
                coro = coroutine.create(function() while 1 == 1 do print("Test") end end)
                print(coro)
                coroutine.resume(coro)
                print(coro)
                """;

        Globals globals = JsePlatform.debugGlobals();

        LuaValue chunk = globals.load(script, "script");

        Thread executor = new Thread(chunk::call);

        executor.start();

    }
}
