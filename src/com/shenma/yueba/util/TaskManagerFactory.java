package com.shenma.yueba.util;





/**
 * 异步任务工具类
 * @author zhou
 *
 */
public class TaskManagerFactory {
	
	private static TaskManager mDataTaskManager, mImageTaskManager;

	/**
	 * 创建数据请求线程池
	 * @return
	 */
	public static TaskManager createDataTaskManager() {
		if (mDataTaskManager == null) {
		    int CPU_COUNT = Runtime.getRuntime().availableProcessors();
			mDataTaskManager = new TaskManager(0, Runtime.getRuntime().availableProcessors()*3+1);
		}
		return mDataTaskManager;
	}
	
	/**
	 * 创建图片处理线程池
	 * @return
	 */
	public static TaskManager createImageTaskManager() {
		if (mImageTaskManager == null) {
			
			mImageTaskManager = new TaskManager(0, Runtime.getRuntime().availableProcessors()*3+1);
		}
		return mImageTaskManager;
	}
	
}
