package com.my.test.app.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.my.test.app.domain.FunFact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MyService extends DialogflowApp {

    @Autowired
    private FunFactService funFactService;

    private static final String IMG_URL = "https://upload.wikimedia.org/wikipedia/en/2/2c/Google_Actions_Logo.png";

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Here's a fun fact for you");

        List<Button> buttons = Collections.singletonList(new Button().setTitle("Click HERE")
                                                                     .setOpenUrlAction(new OpenUrlAction().setUrl(
                                                                             "https://www.google.com/")));
        List<String> suggestions = Arrays.asList("Hey you", "Hi");
        try {
            FunFact funFact = funFactService.getRandomFunFact();

            BasicCard basicCard = createFunFactBasicCard(buttons, funFact);

            responseBuilder.add(basicCard)
                           .addSuggestions(suggestions.toArray(new String[] {}));
        } catch (RuntimeException e){
            responseBuilder.add(e.getMessage())
                           .endConversation();
        }
        return responseBuilder.build();
    }

    private BasicCard createFunFactBasicCard(List<Button> buttons, FunFact funFact) {
        Image image = new Image();
        image.setUrl(funFact.getUrlImg());
        image.setAccessibilityText("Image alt text");

        return new BasicCard().setTitle(funFact.getTitle())
                              .setSubtitle(funFact.getSubTitle())
                              .setImage(image)
                              .setFormattedText(funFact.getMsg())
                              .setButtons(buttons);
    }
}
