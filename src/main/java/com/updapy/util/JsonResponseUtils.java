package com.updapy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import com.updapy.form.ajax.JsonResponse;

@Component
public class JsonResponseUtils {

	private static String STATUS_FAIL = "FAIL";
	private static String STATUS_SUCCESS = "SUCCESS";

	@Inject
	private MessageUtils messageUtils;

	public JsonResponse buildFailedJsonResponse(String messageKey) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(STATUS_FAIL);
		buildMessages(Arrays.asList(messageKey), jsonResponse);
		return jsonResponse;
	}

	public JsonResponse buildFailedJsonResponseFromErrorObject(List<ObjectError> errors) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(STATUS_FAIL);
		buildMessagesFromErrorObject(errors, jsonResponse);
		return jsonResponse;
	}

	public JsonResponse buildSuccessfulJsonResponse(String messageKey) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(STATUS_SUCCESS);
		buildMessages(Arrays.asList(messageKey), jsonResponse);
		return jsonResponse;
	}

	private void buildMessages(List<String> messageKeys, JsonResponse jsonResponse) {
		List<String> messages = new ArrayList<String>();
		for (String messageKey : messageKeys) {
			messages.add(messageUtils.getSimpleMessage(messageKey));
		}
		jsonResponse.setResult(messages);
	}

	private void buildMessagesFromErrorObject(List<ObjectError> objectErrors, JsonResponse jsonResponse) {
		List<String> messages = new ArrayList<String>();
		for (ObjectError objectError : objectErrors) {
			messages.add(messageUtils.getSimpleMessage(objectError));
		}
		jsonResponse.setResult(messages);
	}

}
