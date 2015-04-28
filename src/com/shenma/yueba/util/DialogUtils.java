package com.shenma.yueba.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {

	/**
	 * 带是否可以点击外部消失和点击回退键消失
	 * @param context
	 * @param content
	 * @param mydialogInterface
	 */
	public static void alertDialog(Context context,String title,String content,
			final DialogUtilInter mydialogInterface,boolean towButton,String buttonText1,String buttonText2,boolean canOutDismisss,boolean canBackDismiss) {
		if(((Activity)context).isFinishing()){
			return ;
		}
		if(towButton){
			new AlertDialog.Builder(context).setTitle(title).setMessage(content)
			.setPositiveButton(buttonText1, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (mydialogInterface != null) {
						dialog.dismiss();
						mydialogInterface.dialogCallBack();
					}
				}
			}).setNegativeButton(buttonText2, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					dialog.dismiss();
				}
			}).setCancelable(canBackDismiss).show().setCanceledOnTouchOutside(canOutDismisss);
			
		}else{
			new AlertDialog.Builder(context).setTitle(title).setMessage(content)
			.setPositiveButton(buttonText1, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (mydialogInterface != null) {
						dialog.dismiss();
						mydialogInterface.dialogCallBack();
					}
				}
			}).setCancelable(canBackDismiss).show().setCanceledOnTouchOutside(canOutDismisss);
		}
		
		
	}

	
	/***
	 * 弹出对话框，带回调方法，多选,列表形式的
	 * @param context
	 * @param content
	 * @param mydialogInterface
	 */
	public static void alertDialogMultipleSelect(Context context, String title,
			String[] content, final DialogUtilInter mydialogInterface2,boolean backAbleDismiss,boolean outClickDismiss) {
		if(((Activity)context).isFinishing()){
			return ;
		}
		new AlertDialog.Builder(context).setTitle(title)
				.setItems(content, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, final int which) {
						if (mydialogInterface2 != null) {
							mydialogInterface2.dialogCallBack(which);
						}
					}
				}).setCancelable(backAbleDismiss).show().setCanceledOnTouchOutside(outClickDismiss);

	}
}
	
	
	
