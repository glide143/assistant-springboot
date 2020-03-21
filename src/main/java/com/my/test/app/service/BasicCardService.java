package com.my.test.app.service;

import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.my.test.app.domain.FunFact;

public interface BasicCardService {
    BasicCard createBasicCardFunFact(FunFact funFact);
}
