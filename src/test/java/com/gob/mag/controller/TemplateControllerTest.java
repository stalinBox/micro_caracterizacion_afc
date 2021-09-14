//package com.gob.mag.controller;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import ec.gob.mag.controller.TemplateController;
//import ec.gob.mag.services.TemplateService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = TemplateController.class)
//public class TemplateControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private TemplateService templateService;
//
//	@Test
//	public void retrieveDetailsForCourse() throws Exception {
////		"http://127.0.0.1:8080/micro_template/template/findAll"
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//				.get("http://127.0.0.1:8080/micro_template/template/findAll").accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());
//		String expected = "{id:Course1,name:Spring,description:10 Steps}";
//
//		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//	}
//}
