package com.example;

import io.rsocket.exceptions.ApplicationErrorException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeTypeUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.rsocket.server.port=0")
class DemoRsocketSecurityApplicationTests {
	@Autowired
	RSocketRequester.Builder requester;

	@LocalRSocketServerPort
	int port;

	@Test
	public void messageWhenAuthenticatedThenSuccess() {
		UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("user", "password");
		RSocketRequester requester = this.requester
				.rsocketStrategies((builder) -> builder.encoder(new SimpleAuthenticationEncoder()))
				.setupMetadata(credentials, MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.v0"))
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
				.connectTcp("localhost", this.port)
				.block();

		String message = requester.route("hello")
				.data(Mono.just("World"))
				.retrieveMono(String.class)
				.block();

		assertThat(message).isEqualTo("Hello World, user!");
	}

	@Test
	public void messageWhenNotAuthenticatedThenError() {
		RSocketRequester requester = this.requester
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
				.connectTcp("localhost", this.port)
				.block();

		assertThatThrownBy(() -> requester.route("hello")
				.data(Mono.empty())
				.retrieveMono(String.class)
				.block())
				.isInstanceOf(ApplicationErrorException.class)
				.hasMessage("Access Denied");
	}

}
