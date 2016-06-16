package xdemo.xq.com.xdemo.dataanalysis;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import xdemo.xq.com.xdemo.entity.Whether;

/**
 * Created by lhq on 2016/4/1.
 */
public class Data {

    /**
     * json解析工具包
     */
    private static Gson gson;
    /**
     * Gson解析类
     */
    private static JsonParser parser;
    private static void init(){
        if (gson == null && parser == null) {
            gson = new Gson();
            parser = new JsonParser();
        }
    }
    public static Whether getWhether(String s){
        init();
        Whether whether = null;
        JsonObject jsonObject = parser.parse(s).getAsJsonObject();
        if (jsonObject != null){
            JsonElement jsonElement = jsonObject.get("result");
            if (jsonElement!= null){
                whether = gson.fromJson(jsonElement,Whether.class);
            }
        }
        return  whether;
    }
}
