package com.my.test.app.service;

import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.my.test.app.domain.FunFact;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomizeBasicCardService implements BasicCardService {

    @Override
    public BasicCard createBasicCardFunFact(FunFact funFact) {
        Objects.requireNonNull(funFact);

        Image image = new Image();
        image.setUrl(funFact.getUrlImg());
        image.setAccessibilityText(funFact.getSubTitle());

        return new BasicCard().setTitle(funFact.getTitle())
                              .setSubtitle(funFact.getSubTitle())
                              .setImage(image)
                              .setFormattedText(funFact.getMsg());
    }



}
