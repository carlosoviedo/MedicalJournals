package journal.web.resource;

import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_COLLECTION;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_ITEM;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents medical journal resource items and collection resource.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
@RestController
public class MedicalJournal {
	private static final String _allJournalsMockResponse;
	private static final Map<String, String> _aJournalMockResponses;
	
	static {
		_allJournalsMockResponse = loadMockResponse("allJournals.json");
		_aJournalMockResponses = loadMockResponses("1111-1111", "1111-2222", "1111-3333", "1111-4444");
	}
	
	static String loadMockResponse(String path) {
		try (InputStream is = new ClassPathResource(path).getInputStream()) {
			return new BufferedReader(new InputStreamReader(is), 1024)
						.lines()
						.reduce("", String::concat);
		}
		catch (IOException e) {
			throw new RuntimeException(String.format("File could not be loaded: %s", path), e);
		}
	}
	
	static Map<String, String> loadMockResponses(String... issns) {
		Map<String, String> mockResponses = new HashMap<>(issns.length+1);
		MessageFormat fileNameFormatter = new MessageFormat("aJournal_{0}.json");
		for (String issn : issns) {
			mockResponses.put(issn, loadMockResponse(fileNameFormatter.format(new Object[]{issn})));
		}
		return mockResponses;
	}

	@RequestMapping(path=RESOURCE_COLLECTION, method=GET, produces=APPLICATION_JSON_UTF8_VALUE)
	String allJournals() {
		return _allJournalsMockResponse;
	}

	@RequestMapping(path=RESOURCE_ITEM, method=GET, produces=APPLICATION_JSON_UTF8_VALUE)
	String aJournal(@PathVariable String journalId) {
		return _aJournalMockResponses.get(journalId);
	}
	
	static class MedicalJournalEndpoints {
		static final String JSON_EXTENSION = ".json";
		static final String JOURNAL_URL_PARAM = "{journalId}";
		static final String RESOURCE_BASE = "/journals";
		static final String RESOURCE_COLLECTION = RESOURCE_BASE + JSON_EXTENSION;
		static final String RESOURCE_ITEM = RESOURCE_BASE + "/" + JOURNAL_URL_PARAM + JSON_EXTENSION;
	}
}