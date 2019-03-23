package com.mel.assistant.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.actions.api.App;

@RestController
@RequestMapping(value = "assistant")
public class AssistantRestController {

	@Autowired
	private App app;

	@RequestMapping(value = "webhook", method = RequestMethod.POST)
	public String printAnimal(@RequestBody String assistantRequest, @RequestHeader Map<String, String> headersMap) {
		String jsonResponse = "";
		try {
			jsonResponse = app.handleRequest(assistantRequest, headersMap).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}

}
