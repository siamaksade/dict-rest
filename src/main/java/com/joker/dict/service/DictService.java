package com.joker.dict.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.joker.dict.model.Noun;
import com.joker.dict.util.Constants;

public class DictService {
	private static final String DICT_CC_URL = "http://www.dict.cc/?s=%s";

	public Noun getDictDescription(String text) throws IOException {
		Document doc = Jsoup
				.connect(
						String.format(DICT_CC_URL,
								URLEncoder.encode(text, "UTF-8")))
				.timeout(5000).userAgent(Constants.USER_AGENT).get();

		Elements nouns = doc.select("tr[title^=article]");
		for (ListIterator<Element> iter = nouns.listIterator(); iter.hasNext();) {
			Element el = iter.next();
			if (el.attr("title").contains("article sg")) {
				String[] parts = el.text()
								   .trim()
								   .toLowerCase()
								   .replaceAll("[^|\\.\\-\\s\\p{IsAlphabetic}]", "")
								   .replaceAll("^noun\\d?(.+)edit$", "$1")
								   .split("\\|");
				String gender = parts[0].trim().replaceAll("(die|der|das)\\s*(.*)", "$1");
				String noun = parts[0].trim().replaceAll("(die|der|das)\\s*(.*)", "$2");
				String plural = parts[1].trim()
						.replaceAll("[^\\s\\p{IsAlphabetic}]", "")
						.replaceAll("(die|der|das)\\s*(.*)", "$2");
				return new Noun(noun, gender, plural);
			}
		}

		return null;
	}
}
