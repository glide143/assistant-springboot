package com.my.test.app.service;

import com.my.test.app.domain.FunFact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FunFactOnb implements FunFactService {
    private  List<FunFact> funFacts;
    private static final String IMG_URL = "https://upload.wikimedia.org/wikipedia/en/2/2c/Google_Actions_Logo.png";

    public FunFactOnb (){
        initFacts();
    }


    @Override
    public FunFact getRandomFunFact() {
        if(!funFacts.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(funFacts.size());
            FunFact funFact = funFacts.get(randomIndex);
            funFacts.remove(funFact);
            return funFact;
        }

        throw new RuntimeException("That's all the fun fact for now come back again next time");
    }




    private void initFacts() {
        funFacts = new ArrayList<>();
        funFacts.add(FunFact.builder().id(1).msg(IMG_URL).urlImg("Fun Fact 1").build());
        funFacts.add(FunFact.builder().id(2).msg(IMG_URL).urlImg("Fun Fact 2").build());
        funFacts.add(FunFact.builder().id(3).msg(IMG_URL).urlImg("Fun Fact 3").build());
        funFacts.add(FunFact.builder().id(4).msg(IMG_URL).urlImg("Fun Fact 4").build());
    }

}
