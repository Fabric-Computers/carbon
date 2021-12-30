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

        try {
            engine.eval("print(\"Hi\")");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
