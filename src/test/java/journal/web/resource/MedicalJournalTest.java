package journal.web.resource;

import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_COLLECTION;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_ITEM;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static journal.web.resource.MedicalJournal.loadMockResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MockServletContext.class)
@WebAppConfiguration
public class MedicalJournalTest {
	private MockMvc mvc;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new MedicalJournal()).build();
	}
	
	@Test
	public void getResourceCollection() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(RESOURCE_COLLECTION).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().json(loadMockResponse("allJournals.json")));
	}
	
	@Test
	public void getResourceItem() throws Exception {
		String journalId = "1111-1111";
		mvc.perform(MockMvcRequestBuilders.get(RESOURCE_ITEM, journalId).accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().json(loadMockResponse("aJournal_" + journalId + ".json")));
	}
}