package com.quantity_measurement_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.dto.QuantityInputDTO;
import com.quantity_measurement_app.security.CustomUserDetailsService;
import com.quantity_measurement_app.security.JwtFilter;
import com.quantity_measurement_app.security.JwtUtil;
import com.quantity_measurement_app.security.OAuth2SuccessHandler;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuantityMeasurementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private IQuantityMeasurementService service;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private OAuth2SuccessHandler oAuth2SuccessHandler;

	@TestConfiguration
	static class TestConfig {
		@Bean
		@Primary
		public IQuantityMeasurementService mockService() {
			return mock(IQuantityMeasurementService.class);
		}
	}

	@Test
	void testHealthEndpoint_Returns200() throws Exception {
		mockMvc.perform(get("/api/v1/quantities/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", equalTo("UP")))
				.andExpect(jsonPath("$.message", containsString("running")));
	}

	@Test
	void testCompareQuantities_Success() throws Exception {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);

		when(service.compare(any(QuantityDTO.class), any(QuantityDTO.class))).thenReturn(true);

		mockMvc.perform(post("/api/v1/quantities/compare")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", equalTo(true)))
				.andExpect(jsonPath("$.operation", equalTo("COMPARE")))
				.andExpect(jsonPath("$.isEqual", equalTo(true)));
	}

	@Test
	void testConvertQuantity_Success() throws Exception {
		QuantityDTO input = new QuantityDTO(1.0, "FEET", "LENGTHUNIT");
		QuantityDTO expected = new QuantityDTO(12.0, "INCHES", "LENGTHUNIT");

		when(service.convert(any(QuantityDTO.class), anyString())).thenReturn(expected);

		mockMvc.perform(post("/api/v1/quantities/convert?targetUnit=INCHES")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", equalTo(true)))
				.andExpect(jsonPath("$.operation", equalTo("CONVERT")))
				.andExpect(jsonPath("$.result.value", equalTo(12.0)));
	}

	@Test
	void testAddQuantities_Success() throws Exception {
		QuantityDTO q1 = new QuantityDTO(5.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(3.0, "FEET", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);
		QuantityDTO result = new QuantityDTO(8.0, "FEET", "LENGTHUNIT");

		when(service.add(any(QuantityDTO.class), any(QuantityDTO.class))).thenReturn(result);

		mockMvc.perform(post("/api/v1/quantities/add")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", equalTo(true)))
				.andExpect(jsonPath("$.operation", equalTo("ADD")))
				.andExpect(jsonPath("$.result.value", equalTo(8.0)));
	}

	@Test
	void testSubtractQuantities_Success() throws Exception {
		QuantityDTO q1 = new QuantityDTO(5.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);
		QuantityDTO result = new QuantityDTO(3.0, "FEET", "LENGTHUNIT");

		when(service.subtract(any(QuantityDTO.class), any(QuantityDTO.class))).thenReturn(result);

		mockMvc.perform(post("/api/v1/quantities/subtract")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", equalTo(true)))
				.andExpect(jsonPath("$.operation", equalTo("SUBTRACT")))
				.andExpect(jsonPath("$.result.value", equalTo(3.0)));
	}

	@Test
	void testDivideQuantities_Success() throws Exception {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);

		when(service.divide(any(QuantityDTO.class), any(QuantityDTO.class))).thenReturn(5.0);

		mockMvc.perform(post("/api/v1/quantities/divide")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", equalTo(true)))
				.andExpect(jsonPath("$.operation", equalTo("DIVIDE")))
				.andExpect(jsonPath("$.result", equalTo(5.0)));
	}

	@Test
	void testCompareQuantities_InvalidInput_MissingQuantity() throws Exception {
		QuantityInputDTO input = new QuantityInputDTO(null, null);

		mockMvc.perform(post("/api/v1/quantities/compare")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testConvertQuantity_MissingTargetUnit() throws Exception {
		QuantityDTO input = new QuantityDTO(1.0, "FEET", "LENGTHUNIT");

		mockMvc.perform(post("/api/v1/quantities/convert")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testEndpoint_ValidationError_InvalidQuantityValue() throws Exception {
		QuantityDTO q1 = new QuantityDTO(0.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);

		mockMvc.perform(post("/api/v1/quantities/compare")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testEndpoint_ValidationError_InvalidMeasurementType() throws Exception {
		QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "INVALID");
		QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);

		mockMvc.perform(post("/api/v1/quantities/compare")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testDivideQuantities_DivisionByZero() throws Exception {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LENGTHUNIT");
		QuantityInputDTO input = new QuantityInputDTO(q1, q2);

		when(service.divide(any(QuantityDTO.class), any(QuantityDTO.class)))
				.thenThrow(new IllegalArgumentException("Division by zero"));

		mockMvc.perform(post("/api/v1/quantities/divide")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.success", equalTo(false)));
	}
}
