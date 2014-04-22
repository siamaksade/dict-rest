package com.joker.dict.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.joker.dict.model.Verb;
import com.joker.dict.util.Constants;

public class VerbFormService {
	private static final String VERBFORMEN_URL = "http://www.verbformen.de/konjugation/%s.htm";
	
	private static final String VERB_PATTERN = "^([^\\s]+)\\s+ich(.+)du(.+)er(.+)wir(.+)ihr(.+)sie(.+)";
	
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
			
			String[] verbs = new String[6];
			for (int i = 0; i < 6; i++) {
				String compVerb = line.replaceAll(VERB_PATTERN, "$" + (i + 2)).trim();
				if (compVerb.contains(" ")) {
					verbs[i] = sanitize(compVerb.substring(compVerb.lastIndexOf(" ")));
				} else {
					verbs[i] = sanitize(compVerb);
				}
			}
			
			if ("präsens".equals(tense)) {
				verb.setPresent(verbs);
			} else if ("präteritum".equals(tense)) {
				verb.setPast(verbs);
			} else if ("perfekt".equals(tense)) {
				verb.setPresentPerfect(verbs[0]);
			}
		}
		
		return verb;
	}
	
	private String sanitize(String text) {
		return text.replaceAll("[^\\p{IsAlphabetic}]", "");
	}
}
