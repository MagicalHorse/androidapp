package com.shenma.yueba.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-9 下午5:56:14  
 * 程序的简单说明  
 */

public class NoSrcollListView extends ListView{

        public NoSrcollListView(Context context, AttributeSet attrs) {
                super(context, attrs);
        }
        
        /**
         * 设置不滚动
         */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
                int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                                MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, expandSpec);

        }

}
