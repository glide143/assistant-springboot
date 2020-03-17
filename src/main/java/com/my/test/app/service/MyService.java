package com.my.test.app.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MyService extends DialogflowApp {

    private static final String IMG_URL = "https://upload.wikimedia.org/wikipedia/en/2/2c/Google_Actions_Logo.png";

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Welcome to my app");

        List<Button> buttons = Collections.singletonList(new Button().setTitle("Click HERE")
                                                                     .setOpenUrlAction(new OpenUrlAction().setUrl(
                                                                             "https://www.google.com/")));
        List<String> suggestions = Arrays.asList("Hey you", "Whats up");

        Image image = new Image();
        image.setUrl(IMG_URL);
        image.setAccessibilityText("Image alt text");

        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

        BasicCard basicCard = new BasicCard();
        basicCard.setTitle("TThis is a title");
        basicCard.setSubtitle("This is a subtitle");
        basicCard.setImage(image);
        basicCard.setFormattedText(text);
        basicCard.setButtons(buttons);

        responseBuilder.add("This is the first simple response for a basic card.")
                       .add(basicCard)
                       .addSuggestions(suggestions.toArray(new String[] {}));

        return responseBuilder.build();
    }
}
