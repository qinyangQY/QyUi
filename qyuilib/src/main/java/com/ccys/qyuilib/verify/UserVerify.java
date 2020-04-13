package com.ccys.qyuilib.verify;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserVerify {

    //正则表达式
    public static final String VERIFY_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";//邮箱
    public static final String VERIFY_PHONE = "^(([1][3-9][0-9]{9})|([0]{1}[0-9]{2,3}-[0-9]{7,8}))$";//手机号
    public static final String VERIFY_FIXEDTELEPHONE = "^(0[0-9]{2})\\d{8}$|^(0[0-9]{3}(\\d{7,8}))$";//座机
    public static final String VERIFY_ACCOUNT = "^[a-z0-9_-]{3,16}$"; //用户名验证，3-16个字符
    public static final String VERIFY_PASSWORD = "^[a-z0-9_-]{6,18}$"; //匹配密码的格式，6-18个字符

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";

    //使用正则表达式进行验证
    public static boolean VerifyText(String regEx, String str){
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /**
     * 规则3：必须同时包含字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && str.matches(regex);
        return isRight;
    }
    public static String delHTMLTag(String htmlStr) {
        if(TextUtils.isEmpty(htmlStr)){
            return "";
        }
        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim(); // 返回文本字符串
    }
}
