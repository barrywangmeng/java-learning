package cn.barrywangmeng.scriptengine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by WangMeng on 2017/10/18.
 */
public class ScriptEngineUtils {
    private static ScriptEngine engine;
    public static ScriptEngine getScriptEngine() {
        if (engine == null) {
            synchronized (ScriptEngineUtils.class) {
                if (engine != null) {
                    return engine;
                }
                ScriptEngineManager factory = new ScriptEngineManager();
                engine = factory.getEngineByName("JavaScript");
            }
        }
        return engine;
    }
}
