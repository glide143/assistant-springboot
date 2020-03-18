package com.my.test.app.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class History {
    private int id;
    private String msg;
    private String url;
    private String imgUrl;
}
