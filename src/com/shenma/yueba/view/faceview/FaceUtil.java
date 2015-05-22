package com.shenma.yueba.view.faceview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FaceUtil {
	public static String toHtmls(int resId){
		return "<img src=\"" + resId + "\"/>";
	}
	/**
	 * 得到所有表情的资源文件列表
	 * 
	 * @return
	 */
	public static List<Integer> getAllFace(int[] mImageIds) {
		 List<Integer> integers = new ArrayList<Integer>();
		 for(int i=0;i<mImageIds.length;i++){
			 integers.add(mImageIds[i]);
		 }
		return integers;
	}
	/**
	 * 计算需要分几页显示
	 * 
	 * @return
	 */
	public static int pageSize(int totalCount, int pageCount) {
		return totalCount % pageCount > 0 ? totalCount / pageCount + 1: totalCount / pageCount;
	}
	/**
	 * 分页计算
	 * 
	 * @param num
	 *            每页的个数
	 * @param cur
	 *            页码
	 * @param list
	 *            数据
	 * @return
	 */
	public static List<Integer> getFace(int num, int cur, List<Integer> list) {
		int len = list.size();
		int pageSize = len / num;
		int pageExcess = len % num;
		if (cur < list.size() / num || cur == pageSize) {
			return list.subList(cur * num - num, cur * num);
		} else {
			if (pageExcess > 0) {
				return list.subList(pageSize * num, len);
			}
		}
		return list;
	}
	/**
	 * 初始化表情
	 */
	public static Map<String, String> getFaceMap() {
		Map<String, String> mFaceMap = new LinkedHashMap<String, String>();
		mFaceMap.put("[微笑]", "file:///android_asset/biaoqing001.png");
		mFaceMap.put("[色色]", "file:///android_asset/biaoqing002.png");
		mFaceMap.put("[嘻嘻]", "file:///android_asset/biaoqing003.png");
		mFaceMap.put("[偷笑]", "file:///android_asset/biaoqing004.png");
		mFaceMap.put("[害羞]", "file:///android_asset/biaoqing005.png");
		mFaceMap.put("[大哭]", "file:///android_asset/biaoqing006.png");
		mFaceMap.put("[流泪]", "file:///android_asset/biaoqing007.png");
		mFaceMap.put("[耍酷]", "file:///android_asset/biaoqing008.png");
		mFaceMap.put("[发怒]", "file:///android_asset/biaoqing009.png");
		mFaceMap.put("[吃惊]", "file:///android_asset/biaoqing010.png");
		mFaceMap.put("[疑问]", "file:///android_asset/biaoqing011.png");
		mFaceMap.put("[好衰]", "file:///android_asset/biaoqing012.png");
		mFaceMap.put("[吐舌]", "file:///android_asset/biaoqing013.png");
		mFaceMap.put("[调皮]", "file:///android_asset/biaoqing014.png");
		mFaceMap.put("[惊恐]", "file:///android_asset/biaoqing015.png");
		mFaceMap.put("[睡觉]", "file:///android_asset/biaoqing016.png");
		mFaceMap.put("[困乏]", "file:///android_asset/biaoqing017.png");
		mFaceMap.put("[不屑]", "file:///android_asset/biaoqing018.png");
		mFaceMap.put("[晕晕]", "file:///android_asset/biaoqing019.png");
		mFaceMap.put("[悠闲]", "file:///android_asset/biaoqing020.png");
		mFaceMap.put("[尴尬]", "file:///android_asset/biaoqing021.png");
		mFaceMap.put("[脸红]", "file:///android_asset/biaoqing022.png");
		mFaceMap.put("[安慰]", "file:///android_asset/biaoqing023.png");
		mFaceMap.put("[闭嘴]", "file:///android_asset/biaoqing024.png");
		mFaceMap.put("[狂吐]", "file:///android_asset/biaoqing025.png");
		mFaceMap.put("[饥饿]", "file:///android_asset/biaoqing026.png");
		mFaceMap.put("[鄙视]", "file:///android_asset/biaoqing027.png");
		mFaceMap.put("[不爽]", "file:///android_asset/biaoqing028.png");
		mFaceMap.put("[强大]", "file:///android_asset/biaoqing029.png");
		mFaceMap.put("[胜利]", "file:///android_asset/biaoqing030.png");
		mFaceMap.put("[弱爆]", "file:///android_asset/biaoqing031.png");
		mFaceMap.put("[握手]", "file:///android_asset/biaoqing032.png");
		mFaceMap.put("[勾引]", "file:///android_asset/biaoqing033.png");
		mFaceMap.put("[爱你]", "file:///android_asset/biaoqing034.png");
		mFaceMap.put("[抱拳]", "file:///android_asset/biaoqing035.png");
		mFaceMap.put("[OK]", "file:///android_asset/biaoqing036.png");
		mFaceMap.put("[便便]", "file:///android_asset/biaoqing037.png");
		mFaceMap.put("[吃饭]", "file:///android_asset/biaoqing038.png");
		mFaceMap.put("[爱心]", "file:///android_asset/biaoqing039.png");
		mFaceMap.put("[心碎]", "file:///android_asset/biaoqing040.png");
		mFaceMap.put("[咖啡]", "file:///android_asset/biaoqing042.png");
		mFaceMap.put("[钱币]", "file:///android_asset/biaoqing042.png");
		mFaceMap.put("[西瓜]", "file:///android_asset/biaoqing043.png");
		mFaceMap.put("[吃药]", "file:///android_asset/biaoqing044.png");
		mFaceMap.put("[内裤]", "file:///android_asset/biaoqing045.png");
		mFaceMap.put("[内衣]", "file:///android_asset/biaoqing046.png");
		mFaceMap.put("[强壮]", "file:///android_asset/biaoqing047.png");
		mFaceMap.put("[猪头]", "file:///android_asset/biaoqing048.png");
		mFaceMap.put("[玫瑰]", "file:///android_asset/biaoqing049.png");
		mFaceMap.put("[凋谢]", "file:///android_asset/biaoqing050.png");
		mFaceMap.put("[蛋糕]", "file:///android_asset/biaoqing051.png");
		mFaceMap.put("[流汗]", "file:///android_asset/biaoqing052.png");
		mFaceMap.put("[围观]", "file:///android_asset/biaoqing053.png");
		mFaceMap.put("[夜晚]", "file:///android_asset/biaoqing054.png");
		mFaceMap.put("[亲亲]", "file:///android_asset/biaoqing055.png");
		mFaceMap.put("[礼物]", "file:///android_asset/biaoqing056.png");
		mFaceMap.put("[憨笑]", "file:///android_asset/biaoqing057.png");
		mFaceMap.put("[咧嘴]", "file:///android_asset/biaoqing058.png");
		mFaceMap.put("[可爱]", "file:///android_asset/biaoqing059.png");
		mFaceMap.put("[阴笑]", "file:///android_asset/biaoqing060.png");
		mFaceMap.put("[捂嘴]", "file:///android_asset/biaoqing061.png");
		mFaceMap.put("[气炸]", "file:///android_asset/biaoqing062.png");
		mFaceMap.put("[呜呜]", "file:///android_asset/biaoqing063.png");
		mFaceMap.put("[狂暴]", "file:///android_asset/biaoqing064.png");
		mFaceMap.put("[好囧]", "file:///android_asset/biaoqing065.png");
		mFaceMap.put("[惊吓]", "file:///android_asset/biaoqing066.png");
		mFaceMap.put("[好色]", "file:///android_asset/biaoqing067.png");
		mFaceMap.put("[飞吻]", "file:///android_asset/biaoqing068.png");
		mFaceMap.put("[坏坏]", "file:///android_asset/biaoqing069.png");
		mFaceMap.put("[捂眼]", "file:///android_asset/biaoqing070.png");
		mFaceMap.put("[可怜]", "file:///android_asset/biaoqing071.png");
		mFaceMap.put("[发呆]", "file:///android_asset/biaoqing072.png");
		mFaceMap.put("[封嘴]", "file:///android_asset/biaoqing073.png");
		mFaceMap.put("[叹气]", "file:///android_asset/biaoqing074.png");
		mFaceMap.put("[鬼脸]", "file:///android_asset/biaoqing075.png");
		mFaceMap.put("[委屈]", "file:///android_asset/biaoqing076.png");
		mFaceMap.put("[抠鼻]", "file:///android_asset/biaoqing077.png");
		mFaceMap.put("[吓尿]", "file:///android_asset/biaoqing078.png");
		mFaceMap.put("[斜视]", "file:///android_asset/biaoqing079.png");
		mFaceMap.put("[鄙视]", "file:///android_asset/biaoqing080.png");
		mFaceMap.put("[敲打]", "file:///android_asset/biaoqing081.png");
		mFaceMap.put("[晕乎]", "file:///android_asset/biaoqing082.png");
		mFaceMap.put("[恶心]", "file:///android_asset/biaoqing083.png");
		mFaceMap.put("[鼓掌]", "file:///android_asset/biaoqing084.png");
		return mFaceMap;
	}
}
