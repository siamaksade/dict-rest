package com.joker.dict.service;

import java.io.IOException;
import java.util.ListIterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DictService {
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 "
												+ "(KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36";
	private static final String DICT_CC_URL = "http://www.dict.cc/?s=";

	public String getDictDescription(String text) throws IOException {
		// Client client = ClientBuilder.newClient();
		// String page = client.target(DICT_CC_URL + text)
		// .request(MediaType.TEXT_PLAIN)
		// .get(String.class);
		//
		// for (String line : page.split("\n")) {
		// if (line.indexOf("article") > -1) {
		// String[] parts = line.split("</[^>]+>");
		// for (String s : parts) {
		// System.out.println(s);
		// }
		// }
		// }

		Document doc = Jsoup.connect(DICT_CC_URL + text)
					.timeout(5000)
					.userAgent(USER_AGENT)
					.get();
		
		Elements nouns = doc.select("tr[title^=article]");
		for (ListIterator<Element> iter = nouns.listIterator(); iter.hasNext(); ) {
			Element el = iter.next();
			
			if (el.attr("title").contains("article sg")) {
				System.out.println("### text: " + el.text());
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
