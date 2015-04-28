package com.shenma.yueba.util;



/**
 * 异步任务接口
 * @author zhou
 *
 */
public interface ITask {

	//执行任务
	public void execute();

	//前面还有几个任务
	public void onTaskNumChanged(int taskNum);

}
