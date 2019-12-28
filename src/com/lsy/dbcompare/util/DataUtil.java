package com.lsy.dbcompare.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * <h4>鍔熻兘鎻忚堪锛氭暟鎹В鏋愬伐鍏�
 * <h5><pre>
 * @author 绾冲窛
 * 2018骞�5鏈�16鏃�
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class DataUtil {

	public static HashMap<String, List<Object[]>> compareData(
			Vector<Object[]> dataLeft, Vector<Object[]> dataRight) {
		HashMap<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
		
		Iterator<Object[]> it = dataLeft.iterator();
		int k=0;
		while(it.hasNext()){
			Object[] leftRow = it.next();
			
			Iterator<Object[]> dr = dataRight.iterator();
			while(dr.hasNext()){
				Object[] rightRow = dr.next();
				int i;
				for (i = 0; i < rightRow.length; i++) {
					if (leftRow[i]==null&&rightRow[i]!=null) {
						break;
					}
					if (leftRow[i]==null&&rightRow[i]==null) {
						continue;
					}
					if(!leftRow[i].equals(rightRow[i])){
						break;
					}
				}
				if(i == rightRow.length){//濡傛灉姣斿埌鏈�鍚庝竴涓瓧娈甸兘娌℃湁涓嶅悓鐨勶紝鏁存潯鏁版嵁鐩稿悓锛岀Щ闄�
					dr.remove();
					it.remove();
					break;
				}
			}
		}		
		map.put("left", dataLeft);
		map.put("right", dataRight);
		
		return map;
	}
}
