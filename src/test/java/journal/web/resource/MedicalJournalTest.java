package journal.web.resource;

import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_COLLECTION;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_ITEM;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import journal.dao.JournalDao;
import journal.model.Journal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
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
	
	@Mock
	JournalDao journalDao;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(new MedicalJournal(journalDao)).build();
	}
	
	@Test
	public void getResourceCollection() throws Exception {
		setupDaoForResourceCollection(journalDao);
		
		mvc.perform(MockMvcRequestBuilders.get(RESOURCE_COLLECTION).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().json(loadMockResponse("allJournals.json")));
	}

	@Test
	public void getResourceItem() throws Exception {
		String journalId = "1111-1111";
		setupDaoForResourceItem(journalId);
		
		mvc.perform(MockMvcRequestBuilders.get(RESOURCE_ITEM, journalId).accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().json(loadMockResponse("aJournal_" + journalId + ".json")));
	}

	private void setupDaoForResourceCollection(JournalDao journalDao) {
		List<Journal> result = new ArrayList<>(4);
		result.add(new Journal("1111-1111", "Medical Journal 1", "en-GB", "https://www.google.com", "biology, cardiology"));
		result.add(new Journal("1111-2222", "Medical Journal 2", "es-AR", "https://www.google.com", "endocrinology, infectious diseases"));
		result.add(new Journal("1111-3333", "Medical Journal 3", "es-MX", "https://www.google.com", "genetics, microbiology"));
		result.add(new Journal("1111-4444", "Medical Journal 4", "en-US", "https://www.google.com", "neurology, oncology"));
		when(journalDao.findAll()).thenReturn(result);
	}
	
	private void setupDaoForResourceItem(String journalId) {
		when(journalDao.findByIssn(eq(journalId))).thenReturn(new Journal("1111-1111", "Medical Journal 1", "en-GB", "https://www.google.com", "biology, cardiology"));
	}
	
	private static String loadMockResponse(String path) {
		try (InputStream is = new ClassPathResource(path).getInputStream()) {
			return new BufferedReader(new InputStreamReader(is), 1024)
						.lines()
						.reduce("", String::concat);
		}
		catch (IOException e) {
			throw new RuntimeException(String.format("File could not be loaded: %s", path), e);
		}
	}
}