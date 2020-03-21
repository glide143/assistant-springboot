package com.my.test.app.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CustomizeFallBack implements FallBackService {

    @Override
    public String getGeneralFallBack() {
        List<String> generalFallBack = Arrays.asList("Sorry, what was that?",
                                                     "I didn't quite get that. I can tell you some fun facts",
                                                     "Sorry, could you say that again?",
                                                     "I missed what you said. What was that?",
                                                     "Sorry, I didn't get that. Can you rephrase?",
                                                     "One more time?",
                                                     "Sorry, could you say that again?",
                                                     "I missed what you said. What was that?");
        int randomIndex = getRandomIndex(generalFallBack);

        return generalFallBack.get(randomIndex);
    }

    @Override
    public String getFinalFallBack() {
        return "I'm sorry I'm having trouble here. Maybe we should try this again later.";
    }

    private int getRandomIndex(List<String> fallbackList) {
        return new Random().nextInt(fallbackList.size());
    }
}
