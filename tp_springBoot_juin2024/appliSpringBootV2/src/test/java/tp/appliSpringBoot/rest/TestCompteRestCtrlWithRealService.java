package tp.appliSpringBoot.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // with all layers
@AutoConfigureMockMvc // to test controller with reals spring services implementations
@ActiveProfiles({ "h2", "reinit" }) // init profile for ...init.ReinitDefaultDataSet
public class TestCompteRestCtrlWithRealService {
	@Autowired
	private MockMvc mvc;

	@Test // à lancer sans le profile withSecurity
	public void testComptesWithSoldeMiniWithRealService() {
		try {
			MvcResult mvcResult = mvc
					.perform(get("/rest/api-bank/compte?soldeMini=200").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
					.andExpect(jsonPath("$[0].label", is("compteC"))).andReturn();
			//à adapter selon jeux de données de init.ReInitDefaultDataset
			System.out.println(">>>>>>>>> jsonResult=" + mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
		}
	}
}
