package com.ccys.qyuilib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ccys.qyuilib.base.QyBaseApplication;


/**
 * SharedPreferences的一个工具类，
 * 调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 *
 */
public class SharedUtils {
	/**
	 * 全局上下文
	 * */
	private final static Context context= QyBaseApplication.getContext();

	/**
	 * 保存在手机里面的文件名
	 */
	private final static String APPINFO= QyAppUtil.getAppPackageName(context)+"_shared";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * @param key 键
	 * @param object 值
	 */
	public static void setParam(String key, Object object){
		String type="";
		if(null!=object){
			type = object.getClass().getSimpleName();
		}
		//通过反射拿到对象类型
		Editor editor = getEditor();
		//根据不同数据类型存放不同数据类型
		if("String".equals(type)){
			editor.putString(key, (String)object);
		}
		else if("Integer".equals(type)){
			editor.putInt(key, (Integer)object);
		}
		else if("Boolean".equals(type)){
			editor.putBoolean(key, (Boolean)object);
		}
		else if("Float".equals(type)){
			editor.putFloat(key, (Float)object);
		}
		else if("Long".equals(type)){
			editor.putLong(key, (Long)object);
		}
		
		editor.commit();
	}
	
	
	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * @param key  键
	 * @param defaultObject 默认值
	 * @return 获取的对象
	 */
	public static Object getParam(String key, Object defaultObject){
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
		if("String".equals(type)){
			return sp.getString(key, (String)defaultObject);
		}else if("Integer".equals(type)){
			return sp.getInt(key, (Integer)defaultObject);
		}else if("Boolean".equals(type)){
			return sp.getBoolean(key, (Boolean)defaultObject);
		}else if("Float".equals(type)){
			return sp.getFloat(key, (Float)defaultObject);
		}else if("Long".equals(type)){
			return sp.getLong(key, (Long)defaultObject);
		}
		return "";
	}
	 
	/**
	 * 获取
	 * */
	public static Editor getEditor(){
		return context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE).edit();
	}
	public static SharedPreferences getSharePreference(){
		return context.getSharedPreferences(APPINFO, Context.MODE_PRIVATE);
	}
	/**
	 * 清空 保存的数据
	 * */
	public static void ClearData(){
		Editor editor = getEditor();
		editor.clear();
		editor.commit();
	}
	
}

