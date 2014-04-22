package com.joker.dict.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.joker.dict.model.Verb;
import com.joker.dict.util.Constants;

public class VerbFormService {
	private static final String VERBFORMEN_URL = "http://www.verbformen.de/konjugation/%s.htm";
	private static final String VERB_PATTERN = "^(.+)\\s+(ich\\s+.*)$";
	private static final List<String> PRONOUNS = Arrays.asList("ich", "du", "er", "wir", "ihr", "sie");
	
	public Verb getVerbFormenDescription(String text) throws IOException {
		Document doc = Jsoup.connect(String.format(VERBFORMEN_URL, URLEncoder.encode(text, "UTF-8")))
					.timeout(5000)
					.userAgent(Constants.USER_AGENT)
					.get();
		
		// indikativ
		Element indikativEl = doc.getElementById("indikativ");
		if (indikativEl == null) {
			return null;
		}
		
		Verb verb = new Verb();
		verb.setText(text.trim());
		
		for (ListIterator<Element> iter = indikativEl.children().listIterator(); iter.hasNext(); ) {
			String line = iter.next().text()
								.replaceAll("[\\(\\)*]", "")
								.toLowerCase();
			
			if (!line.matches(VERB_PATTERN)) {
				continue;
			}
			
			String tense = sanitize(line.replaceAll(VERB_PATTERN, "$1"));
			String [] parts = sanitize(line.replaceAll(VERB_PATTERN, "$2")).split("\\s");
			String[] verbs = new String[6];
			int index = -1;
			for (String part : parts) {
				if (part.trim().isEmpty()) {
					continue;
				}
				
				if (PRONOUNS.contains(part)) {
					index = PRONOUNS.indexOf(part);
				} else {
					verbs[index] = (verbs[index] == null ? part : verbs[index] + " " + part).trim();
				}
			}
			
			if ("präsens".equals(tense)) {
				verb.setPresent(verbs);
			} else if ("präteritum".equals(tense)) {
				verb.setPast(verbs);
			} else if ("perfekt".equals(tense)) {
				verb.setPresentPerfect(verbs[0].substring(verbs[0].lastIndexOf(" ")).trim());
			}
		}
		
		return verb;
	}
	
	private static String sanitize(String text) {
		return text.replaceAll("[^\\s\\.\\-\\p{IsAlphabetic}]", "");
	}
}
