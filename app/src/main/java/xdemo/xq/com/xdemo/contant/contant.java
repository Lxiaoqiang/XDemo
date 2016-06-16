package xdemo.xq.com.xdemo.contant;

import android.os.Environment;

import xdemo.xq.com.xdemo.R;

/**
 * Created by lhq on 2016/3/29.
 */
public class contant {
    public static final String VIDEO_PATH = Environment.getExternalStorageDirectory() + "/rms/myVideo/";

    public static String[] st = {"stock", "susan", "lili", "feca",
            "jianli", "sfgg", "yanf", "ffad", "ggg", "aaa", "aaaa", "ggg",
            "jianli", "sfgg", "yanf", "ffad", "ggg", "aaa", "aaaa", "ggg",
            "jianli", "sfgg", "yanf", "ffad", "ggg", "aaa", "aaaa", "ggg",
            "jianli", "sfgg", "yanf", "ffad", "ggg", "aaa", "aaaa", "ggg",
            "jianli", "sfgg", "yanf", "ffad", "ggg", "aaa", "aaaa", "ggg",
            "sdfd", "aad", "sdf"};
    public static String getWhetherById(String id){
        String str = "";
        if (id.equals("00"))
        {
                str = "晴";
        }else if(id.equals("01"))
        {
            str = "多云";
        }else if(id.equals("02"))
        {
            str = "阴";
        }else if(id.equals("03"))
        {
            str = "阵雨";
        }else if(id.equals("04"))
        {
            str = "雷阵雨";
        }else if(id.equals("05"))
        {
            str = "雷阵雨伴有冰雹";
        }else if(id.equals("06"))
        {
            str = "雨夹雪";
        }else if(id.equals("07"))
        {
            str = "小雨";
        }else if(id.equals("08"))
        {
            str = "中雨";
        }else if(id.equals("09"))
        {
            str = "大雨";
        }else if(id.equals("10"))
        {
            str = "暴雨";
        }else if(id.equals("11"))
        {
            str = "大暴雨";
        }else if(id.equals("12"))
        {
            str = "特大暴雨";
        }else if(id.equals("13"))
        {
            str = "阵雪";
        }else if(id.equals("14"))
        {
            str = "小雪";
        }else if(id.equals("15"))
        {
            str = "中雪";
        }else if(id.equals("16"))
        {
            str = "大雪";
        }else if(id.equals("17"))
        {
            str = "暴雪";
        }else if(id.equals("18"))
        {
            str = "雾";
        }else if(id.equals("19"))
        {
            str = "冻雨";
        }else if(id.equals("20"))
        {
            str = "沙尘暴";
        }else if(id.equals("21"))
        {
            str = "小雨-中雨";
        }else if(id.equals("22"))
        {
            str = "中雨-大雨";
        }else if(id.equals("23"))
        {
            str = "大雨-暴雨";
        }else if(id.equals("24"))
        {
            str = "暴雨-大暴雨";
        }else if(id.equals("25"))
        {
            str = "大暴雨-特大暴雨";
        }else if(id.equals("26"))
        {
            str = "小雪-中雪";
        }else if(id.equals("27"))
        {
            str = "中雪-大雪";
        }else if(id.equals("28"))
        {
                str = "大雪-暴雪";
        }else if(id.equals("29"))
        {
            str = "浮尘";
        }else if(id.equals("30"))
        {
            str = "扬沙";
        }else if(id.equals("31"))
        {
            str = "强沙尘暴";
        }else if(id.equals("53"))
        {
            str = "霾";
        }

        return str;
    }
    public static int getWhetherIcon(String s){
        int icon = R.mipmap.sun;
        if (s.equals("01")){
            icon = R.mipmap.duoyun;
        }else if (s.equals("02")) {
            icon = R.mipmap.ying;
        }else if(s.equals("03")){
            icon = R.mipmap.dayu;
        }else if(s.equals("04")){
            icon = R.mipmap.thunderbolt;
        }else if(s.equals("05")){
            icon = R.mipmap.rain;
        }
        return icon;
    }
}
