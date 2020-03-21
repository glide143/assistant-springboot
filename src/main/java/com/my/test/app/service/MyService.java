package com.my.test.app.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.my.test.app.domain.FunFact;
import com.my.test.app.exception.NoMoreFunFactsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService extends DialogflowApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyService.class);
    @Autowired
    private FunFactService funFactService;

    @Autowired
    private FallBackService fallBackService;

    @Autowired
    private BasicCardService basicCardService;

    private String[] funFactSuggestions = { "Yes, Tell more fun facts","No, Thanks" };

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Hi welcome to my app");
        responseBuilder.add("What would you like to hear ?");
        responseBuilder.addSuggestions(new String[]{"Tell me some fun fact"});
        return responseBuilder.build();
    }

    @ForIntent("fun_fact")
    public ActionResponse funFact(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);

        try {
            FunFact funFact = funFactService.getRandomFunFact();

            BasicCard basicCard = basicCardService.createBasicCardFunFact(funFact);

            responseBuilder.add("Here's a fun fact for you");
            responseBuilder.add(basicCard)
                           .add("Do you want to hear more fact")
                           .addSuggestions(funFactSuggestions);
        } catch (NoMoreFunFactsException e) {
            responseBuilder.add(e.getMessage())
                           .endConversation();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            String finalCallBackMsg = fallBackService.getFinalFallBack();

            responseBuilder.add(finalCallBackMsg)
                           .endConversation();
        }
        return responseBuilder.build();
    }

    @ForIntent("Default Fallback Intent")
    public ActionResponse defaultFallBackIntent(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);

        String defaultMsg = fallBackService.getGeneralFallBack();

        responseBuilder.add(defaultMsg)
                       .addSuggestions(new String[] { "Tell me a fact" });
        return responseBuilder.build();
    }

    @ForIntent("End Conversation")
    public ActionResponse endConversation(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        String endConvoMsg = fallBackService.getEndConversationFallBack();
        responseBuilder.add(endConvoMsg)
                       .endConversation();
        return responseBuilder.build();
    }

}
