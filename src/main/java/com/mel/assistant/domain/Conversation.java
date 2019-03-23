package com.mel.assistant.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.actions.api.ActionRequest;

public class Conversation {

	public static final List<String> INITIAL_HISTORY_FACTS = Arrays.asList("google_history_fact_1",
			"google_history_fact_2", "google_history_fact_3", "google_history_fact_4");
	public static final List<String> INITIAL_HEADQUARTERS_FACTS = Arrays.asList("google_headquarters_fact_1",
			"google_headquarters_fact_2", "google_headquarters_fact_3");

	private List<String> history = new ArrayList<>();

	private List<String> headquarters = new ArrayList<>();

	private Map<String, Object> conversationData;

	private ActionRequest request;

	private String category;

	private Conversation(ActionRequest actionResponse) {
		this.request = actionResponse;
		this.category = ((String) actionResponse.getParameter("category"));
		this.conversationData = actionResponse.getConversationData();
		initDataForList();
	}

	public static Conversation of(ActionRequest actionResponse) {
		if (Objects.isNull(actionResponse)) {
			throw new IllegalArgumentException("Something went wrong.");
		}
		return new Conversation(actionResponse);
	}

	public List<String> getHistory() {
		return history;
	}

	private void setHistory(List<String> history) {
		this.history = history;
	}

	public List<String> getHeadquarters() {
		return headquarters;
	}

	public Map<String, Object> getConversationData() {
		return conversationData;
	}

	public String getCategory() {
		return category;
	}

	private void setHeadquarters(List<String> headquarters) {
		this.headquarters = headquarters;
	}

	private void initDataForList() {
		System.out.print(conversationData.get("history") == null && conversationData.get("headquarters") == null);
		if (conversationData.get("history") == null && conversationData.get("headquarters") == null) {
			history.addAll(INITIAL_HISTORY_FACTS);
			headquarters.addAll(INITIAL_HEADQUARTERS_FACTS);
		} else {
			List<String> history = (List<String>) conversationData.get("history");
			List<String> headquarters = (List<String>) conversationData.get("headquarters");
			setHeadquarters(history);
			setHistory(headquarters);

		}

	}

}
