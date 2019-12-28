package com.lsy.dbcompare.login;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <h4>功能描述:登录用户数据解析</h4>
 * <h5><pre>
 * author 纳川
 * 2018年5月14日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class UsersUtil {
	@SuppressWarnings("unchecked")
	public static Map<String, String> getUsers() throws DocumentException{
		Map<String, String> map = new HashMap<String,String>();
		SAXReader saxReader = new SAXReader();
		
		Document dom = saxReader.read("c:/dbtool/user-cfg/users.xml");
		Element users = dom.getRootElement();
		
		for (Iterator<Element> it = users.elementIterator();it.hasNext();) {
			Element user = it.next();
			String yhm = user.elementText("username");
			String pwd = user.elementText("pwd");
			map.put(yhm, pwd);
		}
		
		return map;
	}
}
