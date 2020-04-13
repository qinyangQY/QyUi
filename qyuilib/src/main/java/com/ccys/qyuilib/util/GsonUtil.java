package com.ccys.qyuilib.util;

import com.google.gson.Gson;

/**
 * 包名：com.qinyang.qyuilib.util
 * 创建人：秦洋
 * 创建时间：2019/3/3
 * 解析json字符串的工具类
 */
public class GsonUtil {
    public static <T> T  BeanFormToJson(String json,Class<T> t){
        Gson gson = new Gson();
        return gson.fromJson(json,t);
    }
    public static String JsonFormToBean(Object src){
        Gson gson = new Gson();
        return gson.toJson(src);
    }

    /**
     * 格式化json
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr){
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\'){
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append("<br/>");
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append("<br/>");
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append("<br/>");
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }
    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("&nbsp&nbsp&nbsp");
        }
    }
}
