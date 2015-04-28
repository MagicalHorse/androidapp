package com.shenma.yueba.util;




/**
 * 异步任务工作线程
 * @author zhou
 *
 */
public class WorkThread extends Thread {

	private TaskManager mManager;
	private ITask mTask;

	public WorkThread(TaskManager manager, int id) {
		super(manager, id + "");
		mManager = manager;
	}

	/**
	 * 执行方法
	 */
	public void run() {
		while (true) {
			try {
				mTask = mManager.getTask();
			} catch (IllegalStateException ex) {
				break;
			} catch (NullPointerException ex) {
				mManager.waitThread();
				continue;
			}
			mManager.taskStart();
			mTask.execute();
			mManager.taskFinish();
			if(mManager.isThreadFinish()) {
				break;
			}
		}
	}
}
