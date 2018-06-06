package com.demoapplication.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	//private static final String RESOURCE_LOCATION_PATTERN = "http://localhost:8080/station/[a-zA-Z]";
    private static final String StationId = "happ" ;


    @InjectMocks
	StationController controller;

	@Autowired
	WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void initTests() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	//@Test
	public void shouldHaveEmptyDB() throws Exception {
		mvc.perform(get("/Station")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void shouldCreateRetrieveDelete() throws Exception {
		Station r1 = mockStation("shouldCreateRetrieveDelete");
		byte[] r1Json = toJson(r1);

		//CREATE
		MvcResult result = mvc.perform(post("/Station")
				.content(r1Json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())

				.andReturn();
		//String StationId = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

		//RETRIEVE
		mvc.perform(get("/Station/" + StationId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.stationId", is((String)StationId)))
				.andExpect(jsonPath("$.name", is(r1.getName())))
				.andExpect(jsonPath("$.callSign", is(r1.getCallSign())))
				.andExpect(jsonPath("$.hdEnabled", is(r1.isHdEnabled())));

		mvc.perform(get("/Station/HdEnabled/" + true)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].hdEnabled", is(true)));
				
		//DELETE
		mvc.perform(delete("/Station/" +StationId))
				.andExpect(status().isNoContent());

		//RETRIEVE should fail
		mvc.perform(get("/Station/" + StationId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());


	}

	@Test
	public void shouldCreateAndUpdateAndDelete() throws Exception {
        Station r1 = mockStation("shouldCreateAndUpdate");
		byte[] r1Json = toJson(r1);
		//CREATE
		MvcResult result = mvc.perform(post("/Station/")
				.content(r1Json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				//.andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
				.andReturn();
		//String StationId = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        Station r2 = mockStation("shouldCreateAndUpdate2");
		r2.setStationId(StationId);
		byte[] r2Json = toJson(r2);

		//UPDATE
		result = mvc.perform(put("/Station/" + StationId)
				.content(r2Json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();

		//RETRIEVE updated using id
		ResultActions resultActions = mvc.perform(get("/Station/" + StationId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(r2.getName())))
				.andExpect(jsonPath("$.callSign", is(r2.getCallSign())))
				.andExpect(jsonPath("$.stationId", is((String) StationId)))
				.andExpect(jsonPath("$.hdEnabled", is(r2.isHdEnabled())));

//RETRIEVE updated using id
		ResultActions resultActions1 = mvc.perform(get("/Station/HdEnabled/" + true)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].hdEnabled", is(true)));

		//DELETE
		mvc.perform(delete("/Station/" + StationId))
				.andExpect(status().isNoContent());
	}

    /*
	 ******************************
	 */




	private Station mockStation(String prefix) {
		Station r = new Station();
		r.setStationId(StationId);
		r.setCallSign(prefix + "_CallSign");
		r.setName(prefix + "_name");
		r.setHdEnabled(true);
		return r;
	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}

}
