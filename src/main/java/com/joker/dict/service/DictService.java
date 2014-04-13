package com.joker.dict.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class DictService {
	private static final String DICT_CC_URL = "http://www.dict.cc/?s=";

	public String getDictDescription(String text) {
		Client client = ClientBuilder.newClient();
		String page = client.target(DICT_CC_URL + text)
						.request(MediaType.TEXT_PLAIN)
						.get(String.class);
		
		for (String line : page.split("\n")) {
			if (line.indexOf("article") > -1) {
				String[] parts = line.split("</[^>]+>");
				for (String s : parts) {
					System.out.println(s);
				}
			}
		}
		return null;
	}

	public String getVerbFormenDescription(String text) {
		return null;
	}
	
	public static void main(String[] args) {
		  String text = "<option value=\"ENRU\">EN &lt;&gt; RU<option value=\"ENSK\">EN &lt;&gt;"
				+ "SK</option><option value=\"ENSQ\">EN &lt;&gt; SQ</option><option value=\"ENSR\">EN &lt;&gt; "
				+ "SR</option><option value=\"ENSV\">EN &lt;&gt; SV</option><option value=\"ENTR\">EN &lt;&gt; "
				+ "TR</option></select> <a href=\"http://www.dict.cc/?home_tab=comments\">"
				+ "<img src=\"http://www9.dict.cc/img/fbplus1.png\" width=\"35\" height=\"14\" border=\"0\" "
				+ "style=\"margin:3px 2px;vertical-align:middle\" alt=\"\"></a>";
		  
		  String[] arr = text.split("</[^>]+>");
		System.out.println("Size: " + arr.length);
		for (String s : arr) {
			System.out.println(s);
		}
	}
}
