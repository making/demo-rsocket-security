package com.example;

import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@Profile("!bearer")
public class HelloController {

	@MessageMapping("hello")
	public String hello(@AuthenticationPrincipal UserDetails user, @Payload String payload) {
		return "Hello " + payload + ", " + user.getUsername() + "!";
	}
}
