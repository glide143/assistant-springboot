package com.mel.assistant.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	private Constants() {

	}

	// Suggestion chip constants
	public static final String[] CONFIRMATION_SUGGESTIONS = new String[] { "Sure", "No thanks" };
	public static final HashMap<String, String[]> SINGLE_CATEGORY_SUGGESTIONS;
	// Fact constants
	public static final List<String> INITIAL_HISTORY_FACTS = Arrays.asList("google_history_fact_1",
			"google_history_fact_2", "google_history_fact_3", "google_history_fact_4");
	public static final List<String> INITIAL_HEADQUARTERS_FACTS = Arrays.asList("google_headquarters_fact_1",
			"google_headquarters_fact_2", "google_headquarters_fact_3");
	// Card constants
	public static final HashMap<String, String> GOOGLE_CARD;

	static {
		GOOGLE_CARD = new HashMap<>();
		GOOGLE_CARD.put("url", "google_app_logo_url");
		GOOGLE_CARD.put("a11y", "google_app_logo_a11y");
	}

	public static final HashMap<String, String> STAN_CARD;

	static {
		STAN_CARD = new HashMap<>();
		STAN_CARD.put("url", "stan_url");
		STAN_CARD.put("a11y", "stan_a11y");
	}

	public static final HashMap<String, String> GOOGLEPLEX_CARD;

	static {
		GOOGLEPLEX_CARD = new HashMap<>();
		GOOGLEPLEX_CARD.put("url", "googleplex_url");
		GOOGLEPLEX_CARD.put("a11y", "googleplex_a11y");
	}

	public static final HashMap<String, String> GOOGLEPLEX_BIKE_CARD;

	static {
		GOOGLEPLEX_BIKE_CARD = new HashMap<>();
		GOOGLEPLEX_BIKE_CARD.put("url", "googleplex_biking_url");
		GOOGLEPLEX_BIKE_CARD.put("a11y", "googleplex_biking_a11y");
	}

	public static final List<Map<String, String>> CARDS = Arrays.asList(GOOGLE_CARD, STAN_CARD, GOOGLEPLEX_CARD,
			GOOGLEPLEX_BIKE_CARD);

	static {
		SINGLE_CATEGORY_SUGGESTIONS = new HashMap<>();
		SINGLE_CATEGORY_SUGGESTIONS.put("headquarters", new String[] { "Headquarters" });
		SINGLE_CATEGORY_SUGGESTIONS.put("history", new String[] { "History" });
	}
}
