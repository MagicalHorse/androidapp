package com.shenma.yueba.util;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*定义一个倒计时类*/
public class MyCountDown extends CountDownTimer {
	private View view;
	private String historyText;

	public MyCountDown(long millisInFuture, long countDownInterval, View view,
			String historyText) {
		super(millisInFuture, countDownInterval);
		this.view = view;
		this.historyText = historyText;
	}

	@Override
	public void onFinish() {
		if (view instanceof Button) {
			((Button) view).setText(historyText);
		} else if (view instanceof TextView) {
			((TextView) view).setText(historyText);
		}

	}

	@Override
	public void onTick(long millisUntilFinished) {
		if (view instanceof Button) {
			((Button) view).setText(millisUntilFinished / 1000 + "\"");
		} else if (view instanceof TextView) {
			((TextView) view).setText(millisUntilFinished / 1000 + "\"");
		}
	}

}
