package com.mel.assistant.rest;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.actions.api.App;
import com.mel.assistant.service.FactsAboutGoogle;

@RestController
@RequestMapping(value = "assistant")
public class AssistantRestController {

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String displayCode() throws Exception {
		String project_id = System.getenv().get("GOOGLE_CLOUD_PROJECT");
		System.out.println(project_id);

		return project_id;
	}

	@RequestMapping(value = "code", method = RequestMethod.POST)
	public String printAnimal(@RequestBody String assistantRequest, @RequestHeader Map<String, String> headersMap) {
		App app = new FactsAboutGoogle();
		String jsonResponse = "";
		try {
			jsonResponse = app.handleRequest(assistantRequest, headersMap).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonResponse;
	}

}
