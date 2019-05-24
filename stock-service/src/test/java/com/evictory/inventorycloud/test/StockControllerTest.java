package com.evictory.inventorycloud.test;

import java.util.ArrayList;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.evictory.inventorycloud.controller.StockController;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockController stockController;

	@Test
	public void testStockControllerIsOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/stock/openstock/draft").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testStockControllerIsNotFound() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/stock/openstock/drafts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	public void stockControllerPostStatusIsOk() throws Exception {
		Stock stockobj = new Stock();
		List<StockDetails> stockDetails = new ArrayList<StockDetails>();

		stockobj.setId(4);
		stockobj.setReason("isuued");
		stockobj.setUserId(6);
		stockobj.setDate(null);
		stockobj.setStockDetails(stockDetails);

		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(stockobj);

		mockMvc.perform(MockMvcRequestBuilders.post("/stock/openstock/draft").contentType(MediaType.APPLICATION_JSON)
				.content(s)).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void stockControllerDeleteStatusIsOk() throws Exception {
		Stock stockobj = new Stock();
		List<StockDetails> stockDetails = new ArrayList<StockDetails>();

		stockobj.setId(1);
		stockobj.setReason("Deleted");
		stockobj.setUserId(8);
		stockobj.setDate(null);
		stockobj.setStockDetails(stockDetails);

		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(stockobj);

		mockMvc.perform(MockMvcRequestBuilders.post("/stock/openstock/draft").contentType(MediaType.APPLICATION_JSON)
				.content(s));

		mockMvc.perform(MockMvcRequestBuilders.delete("/stock/openstock/draft/entry/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void stockControllerUpdateStatusIsOk() throws Exception {

		Stock stockobj = new Stock();
		List<StockDetails> stockDetails = new ArrayList<StockDetails>();

		stockobj.setId(6);
		stockobj.setReason("Updated");
		stockobj.setUserId(7);
		stockobj.setDate(null);
		stockobj.setStockDetails(stockDetails);

		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(stockobj);

		mockMvc.perform(MockMvcRequestBuilders.post("/stock/openstock/draft").contentType(MediaType.APPLICATION_JSON)
				.content(s));

		mockMvc.perform(MockMvcRequestBuilders.put("/stock/openstock/draft/entry/6")
				.contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(MockMvcResultMatchers.status().isOk());
		// .andExpect(MockMvcResultMatchers.status().isOk());

	}
}
