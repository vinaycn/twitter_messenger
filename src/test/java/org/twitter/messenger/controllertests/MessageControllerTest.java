package org.twitter.messenger.controllertests;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.twitter.messenger.controller.MessageController;
import org.twitter.messenger.modelwrapper.MessageWrapper;
import org.twitter.messenger.service.MessageService;
import org.twitter.messenger.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageController.class)
public class MessageControllerTest {


	private MockMvc mockMvc;
	
	@MockBean
	private MessageService messageService;
	
	
	@MockBean
	private PersonService personService;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	@Test
	public void getMessages() throws Exception{
		List<MessageWrapper> messageList = new ArrayList<>();
		MessageWrapper message = new MessageWrapper();
		message.setContent("hello");
		message.setPersonId(1);
		messageList.add(message);
		MessageWrapper message1 = new MessageWrapper();
		message1.setContent("hello wats up");
		message1.setPersonId(1);
		messageList.add(message1);
		when(this.messageService.getMessage(1)).thenReturn(messageList);
		when(this.personService.validatePerson(1)).thenReturn(true);
		HttpHeaders httpHeaders = new HttpHeaders();
		String plainClientCredentials="user:user";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		httpHeaders.set("Authorization","Basic " +base64ClientCredentials);
		mockMvc.perform(get("/people/1/messages").accept(MediaType.parseMediaType("application/json")))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$",hasSize(2)));
	}
	
	
	
	
	

}
