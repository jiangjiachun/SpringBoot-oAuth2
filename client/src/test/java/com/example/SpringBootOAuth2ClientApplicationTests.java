package com.example;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

//@SpringBootTest
class SpringBootOAuth2ClientApplicationTests {

	@Test
	void contextLoads() throws URISyntaxException {
	    URI validRedirectUri = new URI("http://127.0.0.1/#/login/oauth2/code/messaging-client-oidc");
        System.out.println(validRedirectUri.getFragment());
	    
	}

}
