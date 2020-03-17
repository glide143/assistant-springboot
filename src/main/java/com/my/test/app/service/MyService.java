package com.my.test.app.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MyService extends DialogflowApp {

    private static final String IMG_URL = "";

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Welcome to my app");

        List<Button> buttons = Collections.singletonList(new Button().setTitle("Click HERE"));
        List<String> suggestions = Arrays.asList("Hey you", "Whats up");

        BasicCard basicCard = new BasicCard().setTitle("Title:This is a title")
                                             .setSubtitle("This is a subtitle")
                                             .setImage(new Image().setUrl(IMG_URL)
                                                                  .setAccessibilityText("Image alt text"))
                                             .setButtons(buttons);
        responseBuilder.add("This is the first simple response for a basic card.")
                       .add(basicCard)
                       .addSuggestions(suggestions.toArray(new String[]{}));
        return responseBuilder.build();
    }
}
