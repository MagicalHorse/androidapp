package com.shenma.yueba.util;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 异步任务管理类
 * 
 * @author zhou
 * 
 */
public class TaskManager extends ThreadGroup {

	protected int mMin;
	protected int mMax;
	protected boolean mIsClosed = false;
	protected int mWorkCount;// 线程总数
	protected int mAvailable;// 当前可用线程数
	protected List<ITask> mTaskList = Collections
			.synchronizedList(new LinkedList<ITask>());

	/**
	 * 默认线程池最小0个线程, 最大1个线程
	 */
	public TaskManager() {
		this(0, 1);
	}

	/**
	 * 初始化线程池以及线程池管理线程
	 * 
	 * @param min
	 *            线程池最小线程数
	 * @param max
	 *            线程池最大线程数
	 */
	public TaskManager(int min, int max) {
		super("1");
		mMin = min;
		mMax = max;
		for (int i = 0; i < min; i++) {
			mAvailable++;
			WorkThread worker = new WorkThread(this, i);
			worker.start();
			mWorkCount++;
		}
	}

	/**
	 * 添加任务
	 * 
	 * @param task
	 *            任务
	 * @return 任务当前位置 0表示前边没有任务
	 */
	public int addTask(ITask task) {
		synchronized (this) {
			if (mIsClosed) {
				throw new IllegalStateException("TaskManager pool is closed.");
			}
			if (task == null) {
				throw new NullPointerException("Task is null.");
			}
			mTaskList.add(task);
			if (mAvailable < mTaskList.size()) {
				if (mWorkCount < mMax) {
					mAvailable++;
					WorkThread worker = new WorkThread(this, mWorkCount);
					worker.start();
					mWorkCount++;
				}
			}
			notify();
			return (mTaskList.size() - 1);
		}
	}

	/**
	 * 获得任务
	 * 
	 * @return 任务
	 */
	protected ITask getTask() {
		synchronized (this) {
			if (mIsClosed) {
				throw new IllegalStateException("TaskManager pool is closed.");
			}
			if (mTaskList.size() == 0) {
				throw new NullPointerException("Task is null.");
			}
			ITask task = mTaskList.remove(0);
			notifyTaskNumChange();
			return task;
		}
	}

	/**
	 * 移除任务
	 * 
	 * @param task
	 *            任务
	 * @return 如果移除成功返回true 任务已经执行或者任务为空返回false
	 */
	public boolean removeTask(ITask task) {
		synchronized (this) {
			if (mIsClosed) {
				throw new IllegalStateException("TaskManager pool is closed.");
			}
			if (task == null) {
				return false;
			}
			return mTaskList.remove(task);
		}
	}

	/**
	 * 通知所有任务所在位置
	 */
	private synchronized void notifyTaskNumChange() {
		synchronized (this) {
			for (int i = 0; i < mTaskList.size(); i++) {
				mTaskList.get(i).onTaskNumChanged(i);
			}
		}
	}

	/**
	 * 关闭线程池
	 * 
	 * @param immediately
	 *            是否立即关闭
	 */
	public void closePool(boolean immediately) {
		synchronized (this) {
			if (mIsClosed) {
				throw new IllegalStateException();
			}
			mIsClosed = true;
			if (immediately) {
				stop();
			} else {
				notifyAllWorker();
			}
			mTaskList.clear();
			mWorkCount = 0;
			mAvailable = 0;
		}
	}

	/**
	 * 唤醒所有线程
	 */
	public void notifyAllWorker() {
		synchronized (this) {
			notifyAll();
		}
	}

	/**
	 * 让线程等待
	 */
	public void waitThread() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 耗时操作前可用线程数减1
	 */
	public void taskStart() {
		mAvailable--;
	}

	/**
	 * 耗时操作后可用线程加1
	 */
	public void taskFinish() {
		mAvailable++;
	}

	/**
	 * 判断是否废弃该线程
	 * 
	 * @return 是否废弃线程 true：该线程应该废弃, false：该线程是执行线程
	 */
	public boolean isThreadFinish() {
		if (mIsClosed) {
			return true;
		}
		if (mAvailable > mMin && mTaskList.size() == 0) {
			mAvailable--;
			mWorkCount--;
			return true;
		} else {
			return false;
		}
	}

}
