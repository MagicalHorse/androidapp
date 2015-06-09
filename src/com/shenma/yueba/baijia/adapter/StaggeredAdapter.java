package com.shenma.yueba.baijia.adapter;

import java.util.LinkedList;
import java.util.List;

import me.maxwin.view.XListView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListInfo;
import com.shenma.yueba.baijia.modle.MyFavoriteProductListPic;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-20 上午11:18:48  
 * 程序的简单说明  
 */

public class StaggeredAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<MyFavoriteProductListInfo> mInfos;
    private XListView mListView;
    ImageFetcher mImageFetcher;
    public StaggeredAdapter(Context context, XListView xListView,ImageFetcher mImageFetcher) {
        mContext = context;
        mInfos = new LinkedList<MyFavoriteProductListInfo>();
        mListView = xListView;
        this.mImageFetcher=mImageFetcher;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        MyFavoriteProductListInfo duitangInfo = mInfos.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
            convertView = layoutInflator.inflate(R.layout.infos_list, null);
            holder = new ViewHolder();
            holder.imageView = (ScaleImageView) convertView.findViewById(R.id.news_pic);
            holder.contentView = (TextView) convertView.findViewById(R.id.news_title);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        int width=320;
        MyFavoriteProductListPic pic=duitangInfo.getPic();
        int heitht=0;
        if(pic!=null)
        {
        	heitht=(int)pic.getRatio()*width;
        }
        
        holder.imageView.setImageWidth(width);
        holder.imageView.setImageHeight(heitht);
        holder.contentView.setText(duitangInfo.getName());
        mImageFetcher.loadImage(ToolsUtil.getImage(ToolsUtil.nullToString(pic.getPic()), width, 0), holder.imageView);
        return convertView;
    }

    class ViewHolder {
        ScaleImageView imageView;
        TextView contentView;
        TextView timeView;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mInfos.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public void addItemLast(List<MyFavoriteProductListInfo> datas) {
       if(datas!=null)
       {
    	   mInfos.addAll(datas);
       }
    	
    }

    public void addItemTop(List<MyFavoriteProductListInfo> datas) {
       if(datas!=null)
       {
    	for (MyFavoriteProductListInfo info : datas) {
            mInfos.addFirst(info);
        }
       }
    }
}