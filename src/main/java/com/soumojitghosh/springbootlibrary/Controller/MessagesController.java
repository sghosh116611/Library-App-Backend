package com.soumojitghosh.springbootlibrary.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumojitghosh.springbootlibrary.entity.Message;
import com.soumojitghosh.springbootlibrary.requestmodels.AdminQuestionRequest;
import com.soumojitghosh.springbootlibrary.service.MessagesService;
import com.soumojitghosh.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	@Autowired
	private MessagesService messageService;

	@PostMapping("/secure/add/message")
	public void postMessage(@RequestHeader("Authorization") String token, @RequestBody Message messageRequest)
			throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		messageService.postMessage(messageRequest, userEmail);
	}

	@PutMapping("/secure/admin/message")
	public void putMessage(@RequestHeader("Authorization") String token,
			@RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		System.out.println(userEmail);
		System.out.println(admin);
		if (admin == null || !admin.equals("Admin")) {
			throw new Exception("Administration page only.");
		}
		messageService.putMessage(adminQuestionRequest, userEmail);
	}

}
