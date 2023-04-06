package com.codeup.codeupspringblog;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeupSpringBlogApplication.class)
@AutoConfigureMockMvc
public class CodeupSpringBlogApplicationTest {

	private User testUser;
	private HttpSession httpSession;

	@Autowired
	private MockMvc mvc;

	@Autowired
	UserRepository userDao;

	@Autowired
	PostRepository postDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void setup() throws Exception {

		testUser = userDao.findByUsername("testUser");

		// Creates the test user if not exists
		if(testUser == null){
			User newUser = new User();
			newUser.setUsername("testUser");
			newUser.setPassword(passwordEncoder.encode("pass"));
			newUser.setEmail("testUser@codeup.com");
			testUser = userDao.save(newUser);
		}

		// Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
		httpSession = this.mvc.perform(MockMvcRequestBuilders.post("/login").with(csrf())
						.param("username", "testUser")
						.param("password", "pass"))
				.andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(redirectedUrl("/posts"))
				.andReturn()
				.getRequest()
				.getSession();
	}

//	@Test
//	public void testAdsIndex() throws Exception {
//		Post existingPost = postDao.findAll().get(0);
//
//		// Makes a Get request to /ads and verifies that we get some of the static text of the ads/index.html template and at least the title from the first Ad is present in the template.
//		JInternalFrame existingPost;
//		this.mvc.perform(MockMvcRequestBuilders.get("/posts"))
//				.andExpect(status().isOk())
//				// Test the static content of the page
//				.andExpect((ResultMatcher) content().string(containsString("All ads")))
//				// Test the dynamic content of the page
//				.andExpect((ResultMatcher) content().string(containsString(existingPost.getTitle())));
//	}

}
