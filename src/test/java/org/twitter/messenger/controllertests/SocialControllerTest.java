package org.twitter.messenger.controllertests;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.twitter.messenger.controller.SocialController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SocialController.class)
public class SocialControllerTest {

}
