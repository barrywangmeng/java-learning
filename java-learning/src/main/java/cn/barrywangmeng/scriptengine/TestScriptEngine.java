package cn.barrywangmeng.scriptengine;//package cn.barrywangmeng.javaLearning.engine;
//
//
//
//import sun.misc.IOUtils;
//
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.util.Map;
//
//import javax.script.Bindings;
//
///**
// * 测试ScriptEngine
// * Created by WangMeng on 2017/10/18.
// */
//public class TestScriptEngine {
//    public static void main(String[] args) throws Exception {
//        String url = "http://jiage.autohome.com.cn/price/carlist/p-30160-1-0-0-0-0-1";
//        String regex = "\\s*<span\\s*class='%s'></span>";
//        InputStream stream = TestScriptEngine.class.getClassLoader().getResourceAsStream("1.txt");
//        String content = IOUtils.toString(stream, Charset.forName("utf-8"));
//
//        String result = StringUtils.substringAfter(content, "result.specList =  {");
//        result = StringUtils.substringBefore(result, "result.uri = ");
//        result = "{" + result;
//        result = StringUtils.trimToEmpty(result);
//        result = StringUtils.removeEnd(result, ";");
//        JSONObject jsonObject = JSON.parseObject(result);
//        JSONArray list = jsonObject.getJSONArray("list");
//        for (int i = 0; i < list.size(); i++) {
//            JSONObject object = list.getJSONObject(i);
//            //裸车价
//            String nakedPrice = object.getString("nakedPrice");
//            Map<String, String> nakedPricePairs = convertValue(url, nakedPrice);
//            for(Map.Entry<String, String> entry: nakedPricePairs.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                nakedPrice = StringUtils.replacePattern(nakedPrice, String.format(regex, key), value);
//            }
//            Document document = Jsoup.parse(nakedPrice);
//            System.out.println("裸车价:" + document.text());
//
//            //购车总价
//            String fullPrice = object.getString("fullPrice");
//            Map<String, String> fullPricePairs = convertValue(url, fullPrice);
//            for(Map.Entry<String, String> entry: fullPricePairs.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                fullPrice = StringUtils.replacePattern(fullPrice, String.format(regex, key), value);
//            }
//            Document fullPriceDocument = Jsoup.parse(fullPrice);
//            System.out.println("购车总价:" + fullPriceDocument.text());
//
//            //促销套餐
//            String salesPack = object.getString("salesPack");
//            Map<String, String> salesPackPairs = convertValue(url, salesPack);
//            for(Map.Entry<String, String> entry: salesPackPairs.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                salesPack = StringUtils.replacePattern(salesPack, String.format(regex, key), value);
//            }
//            Document salesPackDocument = Jsoup.parse(salesPack);
//            System.out.println("促销套餐:" + salesPackDocument.text());
//
//            //购车感受
//            String feelContent = object.getString("feelContent");
//            Map<String, String> feelContentPairs = convertValue(url, feelContent);
//            for(Map.Entry<String, String> entry: feelContentPairs.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                feelContent = StringUtils.replacePattern(feelContent, String.format(regex, key), value);
//            }
//            Document feelContentDocument = Jsoup.parse(feelContent);
//            System.out.println("购车感受:" + feelContentDocument.text());
//        }
//    }
//
//    private static Map<String,String> convertValue(String url, String value) throws Exception{
//        Document document = Jsoup.parse(value);
//        Elements scripts = document.getElementsByTag("basic");
//        for (Element script : scripts) {
//            String html = script.html();
//            if (StringUtils.containsIgnoreCase(html, ")(document);")) {
//
//                Bindings binding = ScriptEngineUtils.getScriptEngine().createBindings();
//                binding.put("$content", html);
//                binding.put("$href", url);
//                binding.put("$js", html);
//
//                String hookScript = "(function () {\n" +
//                        "    var map = {};\n" +
//                        "    var js = $js;\n" +
//                        "    var hook = \"var doc = {};\" +\n" +
//                        "        \"doc.createElement = function() {\" +\n" +
//                        "        \"var o = {};\" +\n" +
//                        "        \"o.sheet = {};\" +\n" +
//                        "        \"o.sheet.insertRule = function(s) {\" +\n" +
//                        "        \"var key=s.substring(1,s.indexOf('::'));\"+\n" +
//                        "        \"var value=s.substring(s.indexOf(':\\\"')+2,s.indexOf('\\\" }'));\"+\n" +
//                        "        \"map[key]=value;\"+\n" +
//                        "        \"};\" +\n" +
//                        "        \"o.sheet.insertBefore = function(s) {\" +\n" +
//                        "        \"var key=s.substring(1,s.indexOf('::'));\"+\n" +
//                        "        \"var value=s.substring(s.indexOf(':\\\"')+2,s.indexOf('\\\" }'));\"+\n" +
//                        "        \"map[key]=value;\"+\n" +
//                        "        \"};\" +\n" +
//                        "        \"o.sheet.addRule = function(s,rule) {\" +\n" +
//                        "        \"};\" +\n" +
//                        "        \"return o;\" +\n" +
//                        "        \"};\" +\n" +
//                        "        \"doc.head = [];\" +\n" +
//                        "        \"doc.defaultView = {};\" +\n" +
//                        "        \"doc.head.appendChild= function(){};\" +\n" +
//                        "        \"doc.querySelectorAll = function(){\" +\n" +
//                        "        \"return[];\" +\n" +
//                        "        \"};\" +\n" +
//                        "        \"var window = {};\"+\n" +
//                        "        \"window.decodeURIComponent=decodeURIComponent;\" +\n" +
//                        "        \"window.location={};\" +\n" +
//                        "        \"window.location.href=$href;\" +\n" +
//                        "        \"window.getComputedStyple={};\" +\n" +
//                        "        \"this.window=window;\"+\n" +
//                        "        \"var document = doc;\";\n" +
//                        "    js = js.replace('(document);', '(doc);');\n" +
//                        "    eval(hook + js);\n" +
//                        "    var content = $content;\n" +
//                        "    if (content == null || content == '' || content == undefined) {\n" +
//                        "        return null;\n" +
//                        "    }\n" +
//                        "    return map;\n" +
//                        "})();";
//                Object data = ScriptEngineUtils.getScriptEngine().eval(hookScript, binding);
//                return (Map<String, String>)data;
//            }
//        }
//
//        return null;
//    }
//}
