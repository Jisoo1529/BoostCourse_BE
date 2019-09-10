package kr.or.connect.guestbook.argumentresolver;

import java.util.HashMap;
import java.util.Map;

public class HeaderInfo {
	private Map<String, String>map;//Map의 직접 사용이 불가능하므로 필드로 Map을 가지고 있음. 
	
	public HeaderInfo() {
		map = new HashMap<>();//map생성자 
	}
	
	public void put(String name, String value) {
		map.put(name,value);
	}
	
	public String get(String name) {
		return map.get(name);
	}
}
