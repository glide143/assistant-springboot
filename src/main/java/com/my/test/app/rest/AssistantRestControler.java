package com.my.test.app.rest;

import com.google.actions.api.ActionResponse;
import com.google.actions.api.App;
import com.google.protobuf.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "assistant")
public class AssistantRestControler {
    @Autowired
    private App app;

    @PostMapping(value = "webhook")
    public String actionResponse(@RequestBody
            String assistantRequest, @RequestHeader
            Map<String, String> headersMap) throws ExecutionException, InterruptedException {
        return app.handleRequest(assistantRequest, headersMap).get();
    }



}
