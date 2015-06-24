package com.shenma.yueba.wxapi.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-23 上午9:54:06  
 * 程序的简单说明  
 */

public class WenXinErrorBean {

private static Map<String ,String> error_map=new HashMap<String, String>();
private static WenXinErrorBean bean;

private WenXinErrorBean()
{
	StaticWenXinErrorBean swb=new StaticWenXinErrorBean();
	Field[] Field_array=swb.getClass().getDeclaredFields();
	if(Field_array!=null)
	{
		for(int i=0;i<Field_array.length;i++)
		{
			addMap(Field_array[i]);
		}
	}
	
}


void addMap(Field field)
{
	String name=field.getName();
	String  type=field.getGenericType().toString();
	if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
        Method m;
		try {
			m =field.getClass().getMethod("get"+name);
        String value = (String) m.invoke(field);    //调用getter方法获取属性值
        if(value != null){
        	error_map.put(name, value);
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}


public WenXinErrorBean the()
{
	if(bean==null)
	{
		bean=new WenXinErrorBean();
	}
	return bean;
}

/*****
 * 对照错误key 返回 value 
 * @param str String key(不存在 相应的key 则返回 未知)
 * @return String  
 * ***/
public static String getValue(String str)
{
	if(error_map.containsKey(str))
	{
		return error_map.get(str);
	}else
	{
		return "未知";
	}
}

}
