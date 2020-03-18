package com.my.test.app.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class FunFact {
    private int id;
    private String title;
    private String subTitle;
    private String msg;
    private String urlImg;
}
