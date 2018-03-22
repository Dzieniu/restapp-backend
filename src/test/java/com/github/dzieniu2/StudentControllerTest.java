package com.github.dzieniu2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.security.Role;
import com.github.dzieniu2.service.UserService;
import net.minidev.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	@Test
	@Ignore
	public void getAllUsersTest() throws Exception {

		this.mockMvc
				.perform(get("/user").with(user("admin@gmail.com").password("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("testowy")));
	}

	@Test
	@Ignore
	public void getUserTest() throws Exception {

		JSONObject json = new JSONObject();
		json.put("name", "student");

		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("email", "admin@gmail.com");
		objectNode.put("password", "$2a$10$twGpgXasUuf2QzVT0ZuA.OXM.LTpS7a8GPBqFvnpITY.bcdURmcbO");
		objectNode.put("role", "ROLE_ADMIN");

		this.mockMvc
				.perform(get("/user/1").with(user("admin@gmail.com").password("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("jbikbb")));
	}

	@Test
	public void postUserTest() throws Exception {

		JSONObject json = new JSONObject();
		json.put("email", "test@gmail.com");
		json.put("password", encoder.encode("test"));
		json.put("role", "ROLE_STUDENT");

		this.mockMvc
				.perform(post("/user/",json)
						.with(user("admin@gmail.com").password("admin").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
}
