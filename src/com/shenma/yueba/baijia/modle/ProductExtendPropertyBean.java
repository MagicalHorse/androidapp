package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-22 下午5:49:06  
 * 程序的简单说明   上传商品 扩展属性
 */

public class ProductExtendPropertyBean implements Serializable{
	String Name="";//属性1",
    String Value="";//value1",
    String Type="";// : 1
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}

}
