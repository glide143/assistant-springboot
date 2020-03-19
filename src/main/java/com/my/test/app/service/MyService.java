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

@Service
public class MyService extends DialogflowApp {

    @Autowired
    private FunFactService funFactService;

    private String[] funFactSuggestions = {"Fun Fact"};

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Hi welcome to my app");
        responseBuilder.add("What would you like to hear ?");
        responseBuilder.addSuggestions(funFactSuggestions);
        return responseBuilder.build();
    }

    private BasicCard createFunFactBasicCard(FunFact funFact) {

        Image image = new Image();
        image.setUrl(funFact.getUrlImg());
        image.setAccessibilityText(funFact.getSubTitle());

        return new BasicCard().setTitle(funFact.getTitle())
                              .setSubtitle(funFact.getSubTitle())
                              .setImage(image)
                              .setFormattedText(funFact.getMsg());
    }

    @ForIntent("fun_fact")
    public ActionResponse funFact(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);

        try {
            FunFact funFact = funFactService.getRandomFunFact();

            BasicCard basicCard = createFunFactBasicCard(funFact);

            responseBuilder.add("Here's a fun fact for you");
            responseBuilder.add(basicCard)
                           .add("Do you want to hear more fact")
                           .addSuggestions(funFactSuggestions);
        } catch (RuntimeException e){
            responseBuilder.add(e.getMessage())
                           .endConversation();
        }
        return responseBuilder.build();
    }

}
