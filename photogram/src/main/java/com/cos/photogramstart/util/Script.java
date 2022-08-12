package com.cos.photogramstart.util;

public class Script {

	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString(); // back(String msg)은 script코드를 하나 만들어서 리턴하는 함수임
	}
}
