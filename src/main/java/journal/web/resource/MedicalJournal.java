package journal.web.resource;

import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.COLLECTION_RESOURCE;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_ITEM;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static journal.web.resource.MimeType.APPLICATION_JSON_UTF8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ClassPathResource;

/**
 * Represents medical journal resource items and collection resource.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
@RestController
@EnableAutoConfiguration
public class MedicalJournal {
	private static final String _allJournalsMockResponse;
	private static final Map<String, String> _aJournalMockResponses;
	
	static {
		_allJournalsMockResponse = loadMockResponse("allJournals.json");
		_aJournalMockResponses = loadMockResponses("1111-1111", "1111-2222", "1111-3333", "1111-4444");
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
	
	private static Map<String, String> loadMockResponses(String... issns) {
		Map<String, String> mockResponses = new HashMap<>(issns.length+1);
		MessageFormat fileNameFormatter = new MessageFormat("aJournal_{0}.json");
		for (String issn : issns) {
			mockResponses.put(issn, loadMockResponse(fileNameFormatter.format(new Object[]{issn})));
		}
		return mockResponses;
	}

	@RequestMapping(path=COLLECTION_RESOURCE, method=GET, produces=APPLICATION_JSON_UTF8)
	String allJournals() {
		return _allJournalsMockResponse;
	}

	@RequestMapping(path=RESOURCE_ITEM, method=GET, produces=APPLICATION_JSON_UTF8)
	String aJournal(@PathVariable String journalId) {
		return _aJournalMockResponses.get(journalId);
	}
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(MedicalJournal.class, args);
    }
	
	static class MedicalJournalEndpoints {
		static final String JOURNAL_URL_PARAM = "{journalId}";
		static final String COLLECTION_RESOURCE = "/journals";
		static final String RESOURCE_ITEM = COLLECTION_RESOURCE + "/" + JOURNAL_URL_PARAM;
	}
}