package com.mel.assistant.service;

import static com.mel.assistant.domain.Constants.CARDS;
import static com.mel.assistant.domain.Constants.CONFIRMATION_SUGGESTIONS;
import static com.mel.assistant.domain.Constants.INITIAL_HEADQUARTERS_FACTS;
import static com.mel.assistant.domain.Constants.INITIAL_HISTORY_FACTS;
import static com.mel.assistant.domain.Constants.SINGLE_CATEGORY_SUGGESTIONS;
import static com.mel.assistant.util.MyUtils.genRanNumByListSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.google.actions.api.ActionContext;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import com.mel.assistant.domain.Conversation;

@Service
public class AssistantService extends DialogflowApp {
	private ResourceBundle rb = ResourceBundle.getBundle("resources");

	@ForIntent("Unrecognized Deep Link")
	public ActionResponse deepLinkWelcome(ActionRequest request) {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		responseBuilder.add(String.format(rb.getString("deep_link_fallback"), ((String) request.getParameter("any"))))
				.addSuggestions(getCategorySuggestionsList());

		return responseBuilder.build();
	}

	@ForIntent("tell_fact")
	public ActionResponse tellFact(ActionRequest request) {
		return fact(request);
	}

	// Fulfill "choose_fact" and "tell_fact" intent fact fulfillment function
	@ForIntent("choose_fact")
	public ActionResponse chooseFact(ActionRequest request) {
		return fact(request);
	}

	@ForIntent("quit_facts")
	public ActionResponse quitFact(ActionRequest resquest) {
		ResponseBuilder responseBuilder = getResponseBuilder(resquest);
		List<String> quitMsg = Arrays.asList("Okay, thanks for listening!",
				"I hope you learned something interesting! Have a great day!", "Okay! Bye!");

		int quitMsgIndex = genRanNumByListSize(quitMsg.size());
		responseBuilder.add(quitMsg.get(quitMsgIndex)).endConversation();

		return responseBuilder.build();

	}

	private ActionResponse fact(ActionRequest request) {
		Conversation conversation = Conversation.of(request);

		String selectedCategory = conversation.getCategory();

		Map<String, Object> conversationData = request.getConversationData();

		// Set the initial facts
		if (conversationData.get("history") == null && conversationData.get("headquarters") == null) {
			conversationData.put("history", INITIAL_HISTORY_FACTS);
			conversationData.put("headquarters", INITIAL_HEADQUARTERS_FACTS);
		}

		ResponseBuilder responseBuilder = getResponseBuilder(request);
		List<String> selectedCategories = new ArrayList<>();
		if (selectedCategory == "history") {
			selectedCategories = conversation.getHistory();
		} else {
			selectedCategories = conversation.getHeadquarters();
		}
		List<String> historyFacts = conversation.getHistory();
		List<String> headquartersFacts = conversation.getHeadquarters();

		if (historyFacts.isEmpty() && headquartersFacts.isEmpty()) {
			// no facts are left
			responseBuilder.add(rb.getString("heardItAll")).endConversation();
		} else if (selectedCategories.isEmpty()) {
			// Suggest other category if no more facts in current category
			String otherCategory = ((selectedCategory == "history") ? "history" : "headquarters");
			String response = String.format(rb.getString("factTransition"), selectedCategory, otherCategory);

			List<String> suggestions = new ArrayList<>();
			Collections.addAll(suggestions, SINGLE_CATEGORY_SUGGESTIONS.get(otherCategory));

			// Add context to redirect to other fact category
			Map<String, String> contextParameter = new HashMap<>();
			contextParameter.put("category", otherCategory);

			ActionContext context = new ActionContext("choose_fact-followup", 5);
			context.setParameters(contextParameter);

			responseBuilder.add(response).addSuggestions(suggestions.toArray(new String[0])).add(context);
		} else {
			// There are facts remaining in the currently selected category
			int factIndex = genRanNumByListSize(selectedCategories.size());
			String fact = selectedCategories.get(factIndex);

			// Update user storage to remove fact that will be said to user
			List<String> updatedFacts = new ArrayList<>(selectedCategories);
			updatedFacts.remove(factIndex);
			conversationData.put(selectedCategory, updatedFacts);

			// Get random card image and accessibility text
			int cardIndex = genRanNumByListSize(CARDS.size());
			String imageUrl = CARDS.get(cardIndex).get("url");
			String imageA11y = CARDS.get(cardIndex).get("a11y");

			// Setup button for card
			Button learnMoreButton = new Button().setTitle(rb.getString("card_link_out_text"))
					.setOpenUrlAction(new OpenUrlAction().setUrl(rb.getString("card_link_out_url")));

			responseBuilder.add(String.format(rb.getString(selectedCategory), rb.getString(fact)))
					.add(rb.getString("nextFact") + " POGI SI MEL!")
					.add(new BasicCard().setTitle(rb.getString(fact))
							.setImage(new Image().setUrl(rb.getString(imageUrl))
									.setAccessibilityText(rb.getString(imageA11y)))
							.setButtons(Collections.singletonList(learnMoreButton)))
					.addSuggestions(CONFIRMATION_SUGGESTIONS);
		}
		return responseBuilder.build();
	}

	private String[] getCategorySuggestionsList() {
		List<String> categorySuggestionsList = new ArrayList<String>();
		for (String[] categorySuggestions : SINGLE_CATEGORY_SUGGESTIONS.values()) {
			for (String suggestion : categorySuggestions) {
				categorySuggestionsList.add(suggestion);
			}
		}
		return categorySuggestionsList.toArray(new String[categorySuggestionsList.size()]);
	}
}
