package com.updapy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.SendMessage;
import com.updapy.model.User;
import com.updapy.service.EmailSenderService;
import com.updapy.service.UserService;
import com.updapy.util.JsonResponseUtils;

@RequestMapping("/contact")
@Controller
public class ContactController {

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailSenderService emailSenderService;

	@RequestMapping({ "/", "" })
	public ModelAndView contactPage() {
		ModelAndView modelAndView = new ModelAndView("contact");
		SendMessage sendMessage = new SendMessage();

		User user = userService.getCurrentUserLight();
		if (user != null) {
			// fill in information if user is connected
			sendMessage.setEmail(user.getEmail());
			sendMessage.setAnonymous(false);
		} else {
			// anonymous
			sendMessage.setAnonymous(true);
		}

		modelAndView.addObject("sendMessage", sendMessage);
		return addNotifications(user, modelAndView);
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse sendMessage(@Valid @RequestBody SendMessage sendMessage, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			// Send the message via email
			boolean isSent = emailSenderService.sendAdminMessage(sendMessage.getEmail(), sendMessage.getMessage(), sendMessage.isAnonymous());
			if (!isSent) {
				return jsonResponseUtils.buildFailedJsonResponse("contact.send.error");
			}
			return jsonResponseUtils.buildSuccessfulJsonResponse("contact.send.confirm");
		}
	}

	private ModelAndView addNotifications(User user, ModelAndView modelAndView) {
		if (user != null) {
			modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
			modelAndView.addObject("rssKey", user.getRssKey());
		}
		return modelAndView;
	}

}
