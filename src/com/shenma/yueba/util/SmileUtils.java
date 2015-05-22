package com.shenma.yueba.util;

/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

import com.shenma.yueba.R;

public class SmileUtils {
	public static final String biaoqing001 = "[微笑]";
	public static final String biaoqing002 = "[色色]";
	public static final String biaoqing003 = "[嘻嘻]";
	public static final String biaoqing004 = "[偷笑]";
	public static final String biaoqing005 = "[害羞]";
	public static final String biaoqing006 = "[大哭]";
	public static final String biaoqing007 = "[流泪]";
	public static final String biaoqing008 = "[耍酷]";
	public static final String biaoqing009 = "[发怒]";
	public static final String biaoqing010 = "[吃惊]";
	public static final String biaoqing011 = "[疑问]";
	public static final String biaoqing012 = "[好衰]";
	public static final String biaoqing013 = "[吐舌]";
	public static final String biaoqing014 = "[调皮]";
	public static final String biaoqing015 = "[惊恐]";
	public static final String biaoqing016 = "[睡觉]";
	public static final String biaoqing017 = "[困乏]";
	public static final String biaoqing018 = "[不屑]";
	public static final String biaoqing019 = "[晕晕]";
	public static final String biaoqing020 = "[微笑]";
	public static final String biaoqing021 = "[尴尬]";
	public static final String biaoqing022 = "[脸红]";
	public static final String biaoqing023 = "[安慰]";
	public static final String biaoqing024 = "[闭嘴]";
	public static final String biaoqing025 = "[狂吐]";
	public static final String biaoqing026 = "[饥饿]";
	public static final String biaoqing027 = "[鄙视]";
	public static final String biaoqing028 = "[不爽]";
	public static final String biaoqing029 = "[强大]";
	public static final String biaoqing030 = "[胜利]";
	public static final String biaoqing031 = "[弱爆]";
	public static final String biaoqing032 = "[握手]";
	public static final String biaoqing033 = "[勾引]";
	public static final String biaoqing034 = "[爱你]";
	public static final String biaoqing035 = "[抱拳]";
	public static final String biaoqing036 = "[OK]";
	public static final String biaoqing037 = "[便便]";
	public static final String biaoqing038 = "[吃饭]";
	public static final String biaoqing039 = "[爱心]";
	public static final String biaoqing040 = "[心碎]";
	public static final String biaoqing041 = "[咖啡]";
	public static final String biaoqing042 = "[钱币]";
	public static final String biaoqing043 = "[西瓜]";
	public static final String biaoqing044 = "[吃药]";
	public static final String biaoqing045 = "[内裤]";
	public static final String biaoqing046 = "[内衣]";
	public static final String biaoqing047 = "[强壮]";
	public static final String biaoqing048 = "[猪头]";
	public static final String biaoqing049 = "[玫瑰]";
	public static final String biaoqing050 = "[凋谢]";
	public static final String biaoqing051 = "[蛋糕]";
	public static final String biaoqing052 = "[流汗]";
	public static final String biaoqing053 = "[围观]";
	public static final String biaoqing054 = "[夜晚]";
	public static final String biaoqing055 = "[亲亲]";
	public static final String biaoqing056 = "[礼物]";
	public static final String biaoqing057 = "[憨笑]";
	public static final String biaoqing058 = "[咧嘴]";
	public static final String biaoqing059 = "[可爱]";
	public static final String biaoqing060 = "[阴笑]";
	public static final String biaoqing061 = "[捂嘴]";
	public static final String biaoqing062 = "[气炸]";
	public static final String biaoqing063 = "[呜呜]";
	public static final String biaoqing064 = "[狂暴]";
	public static final String biaoqing065 = "[好囧]";
	public static final String biaoqing066 = "[惊吓]";
	public static final String biaoqing067 = "[好色]";
	public static final String biaoqing068 = "[飞吻]";
	public static final String biaoqing069 = "[坏坏]";
	public static final String biaoqing070 = "[捂眼]";
	public static final String biaoqing071 = "[可怜]";
	public static final String biaoqing072 = "[发呆]";
	public static final String biaoqing073 = "[封嘴]";
	public static final String biaoqing074 = "[叹气]";
	public static final String biaoqing075 = "[鬼脸]";
	public static final String biaoqing076 = "[委屈]";
	public static final String biaoqing077 = "[抠鼻]";
	public static final String biaoqing078 = "[吓尿]";
	public static final String biaoqing079 = "[斜视]";
	public static final String biaoqing080 = "[鄙视]";
	public static final String biaoqing081 = "[敲打]";
	public static final String biaoqing082 = "[晕乎]";
	public static final String biaoqing083 = "[恶心]";
	public static final String biaoqing084 = "[鼓掌]";

	private static final Factory spannableFactory = Spannable.Factory
			.getInstance();

	private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

	static {

		addPattern(emoticons, biaoqing001, R.drawable.biaoqing001);
		addPattern(emoticons, biaoqing002, R.drawable.biaoqing002);
		addPattern(emoticons, biaoqing003, R.drawable.biaoqing003);
		addPattern(emoticons, biaoqing004, R.drawable.biaoqing004);
		addPattern(emoticons, biaoqing005, R.drawable.biaoqing005);
		addPattern(emoticons, biaoqing006, R.drawable.biaoqing006);
		addPattern(emoticons, biaoqing007, R.drawable.biaoqing007);
		addPattern(emoticons, biaoqing008, R.drawable.biaoqing008);
		addPattern(emoticons, biaoqing009, R.drawable.biaoqing009);
		addPattern(emoticons, biaoqing010, R.drawable.biaoqing010);
		
		addPattern(emoticons, biaoqing011, R.drawable.biaoqing011);
		addPattern(emoticons, biaoqing012, R.drawable.biaoqing012);
		addPattern(emoticons, biaoqing013, R.drawable.biaoqing013);
		addPattern(emoticons, biaoqing014, R.drawable.biaoqing014);
		addPattern(emoticons, biaoqing015, R.drawable.biaoqing015);
		addPattern(emoticons, biaoqing016, R.drawable.biaoqing016);
		addPattern(emoticons, biaoqing017, R.drawable.biaoqing017);
		addPattern(emoticons, biaoqing018, R.drawable.biaoqing018);
		addPattern(emoticons, biaoqing019, R.drawable.biaoqing019);
		addPattern(emoticons, biaoqing020, R.drawable.biaoqing020);
		
		
		
		addPattern(emoticons, biaoqing021, R.drawable.biaoqing021);
		addPattern(emoticons, biaoqing022, R.drawable.biaoqing022);
		addPattern(emoticons, biaoqing023, R.drawable.biaoqing023);
		addPattern(emoticons, biaoqing024, R.drawable.biaoqing024);
		addPattern(emoticons, biaoqing025, R.drawable.biaoqing025);
		addPattern(emoticons, biaoqing026, R.drawable.biaoqing026);
		addPattern(emoticons, biaoqing027, R.drawable.biaoqing027);
		addPattern(emoticons, biaoqing028, R.drawable.biaoqing028);
		addPattern(emoticons, biaoqing029, R.drawable.biaoqing029);
		addPattern(emoticons, biaoqing030, R.drawable.biaoqing030);
		
		
		
		
		addPattern(emoticons, biaoqing031, R.drawable.biaoqing031);
		addPattern(emoticons, biaoqing032, R.drawable.biaoqing032);
		addPattern(emoticons, biaoqing033, R.drawable.biaoqing033);
		addPattern(emoticons, biaoqing034, R.drawable.biaoqing034);
		addPattern(emoticons, biaoqing035, R.drawable.biaoqing035);
		addPattern(emoticons, biaoqing036, R.drawable.biaoqing036);
		addPattern(emoticons, biaoqing037, R.drawable.biaoqing037);
		addPattern(emoticons, biaoqing038, R.drawable.biaoqing038);
		addPattern(emoticons, biaoqing039, R.drawable.biaoqing039);
		addPattern(emoticons, biaoqing040, R.drawable.biaoqing040);
		
		
		
		addPattern(emoticons, biaoqing041, R.drawable.biaoqing041);
		addPattern(emoticons, biaoqing042, R.drawable.biaoqing042);
		addPattern(emoticons, biaoqing043, R.drawable.biaoqing043);
		addPattern(emoticons, biaoqing044, R.drawable.biaoqing044);
		addPattern(emoticons, biaoqing045, R.drawable.biaoqing045);
		addPattern(emoticons, biaoqing046, R.drawable.biaoqing046);
		addPattern(emoticons, biaoqing047, R.drawable.biaoqing047);
		addPattern(emoticons, biaoqing048, R.drawable.biaoqing048);
		addPattern(emoticons, biaoqing049, R.drawable.biaoqing049);
		addPattern(emoticons, biaoqing050, R.drawable.biaoqing050);
		
		
		addPattern(emoticons, biaoqing051, R.drawable.biaoqing051);
		addPattern(emoticons, biaoqing052, R.drawable.biaoqing052);
		addPattern(emoticons, biaoqing053, R.drawable.biaoqing053);
		addPattern(emoticons, biaoqing054, R.drawable.biaoqing054);
		addPattern(emoticons, biaoqing055, R.drawable.biaoqing055);
		addPattern(emoticons, biaoqing056, R.drawable.biaoqing056);
		addPattern(emoticons, biaoqing057, R.drawable.biaoqing057);
		addPattern(emoticons, biaoqing058, R.drawable.biaoqing058);
		addPattern(emoticons, biaoqing059, R.drawable.biaoqing059);
		addPattern(emoticons, biaoqing060, R.drawable.biaoqing060);
		
		addPattern(emoticons, biaoqing061, R.drawable.biaoqing061);
		addPattern(emoticons, biaoqing062, R.drawable.biaoqing062);
		addPattern(emoticons, biaoqing063, R.drawable.biaoqing063);
		addPattern(emoticons, biaoqing064, R.drawable.biaoqing064);
		addPattern(emoticons, biaoqing065, R.drawable.biaoqing065);
		addPattern(emoticons, biaoqing066, R.drawable.biaoqing066);
		addPattern(emoticons, biaoqing067, R.drawable.biaoqing067);
		addPattern(emoticons, biaoqing068, R.drawable.biaoqing068);
		addPattern(emoticons, biaoqing069, R.drawable.biaoqing069);
		addPattern(emoticons, biaoqing070, R.drawable.biaoqing070);
		
		addPattern(emoticons, biaoqing071, R.drawable.biaoqing071);
		addPattern(emoticons, biaoqing072, R.drawable.biaoqing072);
		addPattern(emoticons, biaoqing073, R.drawable.biaoqing073);
		addPattern(emoticons, biaoqing074, R.drawable.biaoqing074);
		addPattern(emoticons, biaoqing075, R.drawable.biaoqing075);
		addPattern(emoticons, biaoqing076, R.drawable.biaoqing076);
		addPattern(emoticons, biaoqing077, R.drawable.biaoqing077);
		addPattern(emoticons, biaoqing078, R.drawable.biaoqing078);
		addPattern(emoticons, biaoqing079, R.drawable.biaoqing079);
		addPattern(emoticons, biaoqing080, R.drawable.biaoqing080);
		
		addPattern(emoticons, biaoqing081, R.drawable.biaoqing081);
		addPattern(emoticons, biaoqing082, R.drawable.biaoqing082);
		addPattern(emoticons, biaoqing083, R.drawable.biaoqing083);
		addPattern(emoticons, biaoqing084, R.drawable.biaoqing084);
		
	}

	private static void addPattern(Map<Pattern, Integer> map, String smile,
			int resource) {
		map.put(Pattern.compile(Pattern.quote(smile)), resource);
	}

	/**
	 * replace existing spannable with smiles
	 * 
	 * @param context
	 * @param spannable
	 * @return
	 */
	public static boolean addSmiles(Context context, Spannable spannable) {
		boolean hasChanges = false;
		for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
			Matcher matcher = entry.getKey().matcher(spannable);
			while (matcher.find()) {
				boolean set = true;
				for (ImageSpan span : spannable.getSpans(matcher.start(),
						matcher.end(), ImageSpan.class))
					if (spannable.getSpanStart(span) >= matcher.start()
							&& spannable.getSpanEnd(span) <= matcher.end())
						spannable.removeSpan(span);
					else {
						set = false;
						break;
					}
				if (set) {
					hasChanges = true;
					spannable.setSpan(new ImageSpan(context, entry.getValue()),
							matcher.start(), matcher.end(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}
		return hasChanges;
	}

	public static Spannable getSmiledText(Context context, CharSequence text) {
		Spannable spannable = spannableFactory.newSpannable(text);
		addSmiles(context, spannable);
		return spannable;
	}

	public static boolean containsKey(String key) {
		boolean b = false;
		for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
			Matcher matcher = entry.getKey().matcher(key);
			if (matcher.find()) {
				b = true;
				break;
			}
		}

		return b;
	}

}
