package com.joker.dict.service;

import java.io.IOException;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.joker.dict.model.Noun;

public class DictService {
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 "
												+ "(KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36";
	private static final String DICT_CC_URL = "http://www.dict.cc/?s=";
	
	public Noun getDictDescription(String text) throws IOException {
		Document doc = Jsoup.connect(DICT_CC_URL + text)
					.timeout(5000)
					.userAgent(USER_AGENT)
					.get();
		
		Elements nouns = doc.select("tr[title^=article]");
		for (ListIterator<Element> iter = nouns.listIterator(); iter.hasNext(); ) {
			Element el = iter.next();
			
			if (el.attr("title").contains("article sg")) {
				String [] parts = el.text().toLowerCase().replaceAll("\\?\\w*\\?", "").split("|");
				String gender = parts[0].trim().replace("(die|der|das)\\s*(.*)", "$1");
				String noun = parts[0].trim().replace("(die|der|das)\\s*(.*)", "$2");
				String plural = parts[0].trim().replace("(die|der|das)\\s*(.*)", "$2");
				return new Noun(noun, gender, plural);
			}
		}
		
		return null;
	}

	public String getVerbFormenDescription(String text) {
		return null;
	}
}
