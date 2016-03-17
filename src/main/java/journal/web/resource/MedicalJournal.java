package journal.web.resource;

import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.COLLECTION_RESOURCE;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_ITEM;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static journal.web.resource.MimeType.APPLICATION_JSON_UTF8;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Represents medical journal resource items and collection resource.
 * 
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
@RestController
@EnableAutoConfiguration
public class MedicalJournal {
	@RequestMapping(path=COLLECTION_RESOURCE, method=GET, produces=APPLICATION_JSON_UTF8)
	String allJournals() {
		return "{}";
	}
	
	@RequestMapping(path=RESOURCE_ITEM, method=GET, produces=APPLICATION_JSON_UTF8)
	String aJournal(String journalId) {
		return "{}";
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