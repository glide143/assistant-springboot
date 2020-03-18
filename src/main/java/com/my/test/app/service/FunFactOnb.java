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
            int randomIndex = funFacts.size() == 0 ? 0 : rand.nextInt(funFacts.size());
            FunFact funFact = funFacts.get(randomIndex);
            funFacts.remove(funFact);
            return funFact;
        }
        initFacts();
        throw new RuntimeException("That's all the fun fact for now come back again next time");
    }




    private void initFacts() {
        funFacts = new ArrayList<>();
        funFacts.add(FunFact.builder().id(1).title("Fun Fact 1").subTitle("Fun Fact 1").msg("Fun Fact 1").urlImg(IMG_URL).build());
        funFacts.add(FunFact.builder().id(2).title("Fun Fact 2").subTitle("Fun Fact 2").msg("Fun Fact 2").urlImg(IMG_URL).build());
        funFacts.add(FunFact.builder().id(3).title("Fun Fact 3").subTitle("Fun Fact 3").msg("Fun Fact 3").urlImg(IMG_URL).build());
        funFacts.add(FunFact.builder().id(4).title("Fun Fact 4").subTitle("Fun Fact 4").msg("Fun Fact 4").urlImg(IMG_URL).build());
    }

}
