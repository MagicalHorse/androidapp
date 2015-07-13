package com.shenma.yueba;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.ToolsUtil;

public class UpdateManager implements View.OnClickListener {
	private Context mContext;
	// 提示语
	private String updateMsg = "有最新的软件包哦，亲快下载吧~";
	// 返回的安装包url
	private String apkUrl = "www.joybar.com/aaa.apk"; // petandroid1.apk
	private Dialog downloadDialog;
	/* 下载包安装路径 */
	private static final String savePath = Constants.SD;
	private static final String saveFileName = savePath + Constants.apkName;
	/* 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	private int progress;
	private Thread downLoadThread;
	private boolean interceptFlag = false;
	private String NewVersion;// 传过来的新版本号
	private AlertDialog dialog;
	private ProgressDialog pd;

	public UpdateManager(Context context, String NewVersion) {
		this.mContext = context;
		this.NewVersion = NewVersion;
		pd = new ProgressDialog(context);
		pd.setMessage("更新中...");
		pd.setCanceledOnTouchOutside(false);

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				// mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				// 安装
				downloadDialog.dismiss();
				pd.dismiss();
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public void startUpdate() {
		if (haveUpdate(NewVersion)) {
			dialog = new AlertDialog.Builder(mContext).create();
			dialog.getWindow().setGravity(Gravity.CENTER);
			dialog.show();
			dialog.getWindow().setContentView(showNoticeDialog());
		}
	}

	private View showNoticeDialog() {
		LayoutInflater factory = LayoutInflater.from(mContext);
		View view = factory.inflate(R.layout.exitdialog, null);
		TextView title = (TextView) view.findViewById(R.id.zfb_text_title);
		TextView content = (TextView) view.findViewById(R.id.dialog_content);
		title.setText("软件版本更新");
		content.setText("有最新的软件包哦，亲快下载吧!");
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setText("确定");
		Button cancel = (Button) view.findViewById(R.id.canel);
		cancel.setText("取消");
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		return view;
	}

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		builder.setView(v);
		builder.setNeutralButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		// downloadDialog.show();

		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				String down_url = apkUrl + NewVersion.replaceAll("\\.", "_")
						+ "/pet.apk";
				URL url = new URL(down_url);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestProperty("Accept-Encoding", "identity");
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(6 * 1000);
				conn.connect();
				int length = conn.getContentLength();
				int reCode = conn.getResponseCode();
				InputStream is = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];
				do {
					int numread = is.read(buf);
					// count += numread;
					// progress =(int)(((float)count / length) * 100);
					// 更新进度
					// mHandler.sendEmptyMessage(DOWN_UPDATE);
					// if(numread <= 0){
					// 下载完成通知安装
					// mHandler.sendEmptyMessage(DOWN_OVER);
					// break;
					// }
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.
				mHandler.sendEmptyMessage(DOWN_OVER);
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 下载apk
	 * 
	 * @param url
	 */
	private void downloadApk() {
		pd.show();
		pd.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				interceptFlag = true;
			}
		});
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			dialog.cancel();
			showDownloadDialog();
			break;
		case R.id.canel:
			dialog.cancel();
			break;

		default:
			break;
		}
	}

	/**
	 * 是否跟本地版本对应
	 * 
	 * @param serviceVersion
	 *            服务器版本
	 * @return
	 */
	public boolean haveUpdate(String serviceVersion) {
		String localhostVersion = ToolsUtil.getVersionName(mContext);
		String[] sVersion = serviceVersion.split("\\.");
		if (sVersion.length != 3) {
			return false;
		}
		String[] lVersion = localhostVersion.split("\\.");
		if (lVersion.length != 3) {
			return true;
		}

		int sMajorVersion;
		int sMinorVersion;
		int sMiniVersion;
		try {
			sMajorVersion = Integer.parseInt(sVersion[0]);
			sMinorVersion = Integer.parseInt(sVersion[1]);
			sMiniVersion = Integer.parseInt(sVersion[2]);
		} catch (NumberFormatException e) {
			return false;
		}

		int lMajorVersion;
		int lMinorVersion;
		int lMiniVersion;
		try {
			lMajorVersion = Integer.parseInt(lVersion[0]);
			lMinorVersion = Integer.parseInt(lVersion[1]);
			lMiniVersion = Integer.parseInt(lVersion[2]);
		} catch (NumberFormatException e) {
			return true;
		}

		if (sMajorVersion > lMajorVersion) {
			return true;
		} else if (sMajorVersion < lMajorVersion) {
			return false;
		}
		if (sMinorVersion < lMinorVersion) {
			return true;
		} else if (sMinorVersion < lMinorVersion) {
			return false;
		}
		if (sMiniVersion > lMiniVersion) {
			return true;
		} else if (sMiniVersion < lMiniVersion) {
			return false;
		}

		return false;
	}
}