package com.shenma.yueba.baijia.modle;

public class CardBean {

	private String key;// 文件key   名字+后缀名
    private String Bucket;//上传的Bucket
    private String Endpoint;//上传的服务器节点
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getBucket() {
		return Bucket;
	}
	public void setBucket(String bucket) {
		Bucket = bucket;
	}
	public String getEndpoint() {
		return Endpoint;
	}
	public void setEndpoint(String endpoint) {
		Endpoint = endpoint;
	}
    
    
    
}
