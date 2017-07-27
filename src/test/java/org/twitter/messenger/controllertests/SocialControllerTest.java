package org.twitter.messenger.controllertests;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.twitter.messenger.ChallengeApplication;
import org.twitter.messenger.model.Person;
import org.twitter.messenger.modelwrapper.PersonWrapper;
import org.twitter.messenger.service.PersonService;
import org.twitter.messenger.service.SocialService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@WebAppConfiguration
public class SocialControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@MockBean
	private SocialService socialService;

	@MockBean
	private PersonService personService;

	@Autowired
	private WebApplicationContext wac;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	/*
	 * @Test public void follow() throws Exception {
	 * when(this.socialService.follow(2,1));
	 * mockMvc.perform(post("/people/1/followers/2"))
	 * .andExpect(status().isOk())
	 * .andExpect(content().contentType(contentType)) .andExpect(jsonPath("$",
	 * hasSize(1))); }
	 * 
	 * @Test public void unfollow() throws Exception {
	 * mockMvc.perform(get("/people/1/followers")) .andExpect(status().isOk())
	 * .andExpect(content().contentType(contentType)) .andExpect(jsonPath("$",
	 * hasSize(1))); }
	 */

	@Test
	public void getFollowers() throws Exception {
		List<PersonWrapper> followersList = new ArrayList<>();
		PersonWrapper personWrapper = new PersonWrapper();
		personWrapper.setName("Vinay");
		personWrapper.setHandle("ranbir");
		followersList.add(personWrapper);
		when(this.socialService.getFollowers(1)).thenReturn(followersList);
		when(this.personService.validatePerson(1)).thenReturn(true);
		mockMvc.perform(get("/people/1/followers")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void getFollowings() throws Exception {
		List<Person> followingList = new ArrayList<>();
		Person personWrapper = new Person();
		personWrapper.setName("Vinay");
		personWrapper.setHandle("ranbir");
		followingList.add(personWrapper);
		when(this.socialService.getFollowings(1)).thenReturn(followingList);
		when(this.personService.validatePerson(1)).thenReturn(true);
		mockMvc.perform(get("/people/1/following")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)));
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
