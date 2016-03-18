package journal.web.resource;

import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.JOURNAL_URL_PARAM;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_COLLECTION;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.RESOURCE_ITEM;
import static journal.web.resource.MedicalJournal.MedicalJournalEndpoints.NEW_RESOURCE_ITEM;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import journal.dao.JournalDao;
import journal.model.Journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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
	private JournalDao journalDao;
	
	@Autowired
	public MedicalJournal(JournalDao journalDao) {
		this.journalDao = journalDao;
	}

	@RequestMapping(path=RESOURCE_COLLECTION, method=GET, produces=APPLICATION_JSON_UTF8_VALUE)
	Iterable<Journal> getAllJournals() {
		Iterable<Journal> results = journalDao.findAll();
		results.forEach(journal -> journal.setUrl(generateUrlFrom(journal)));
		return results;
	}

	@RequestMapping(path=RESOURCE_ITEM, method=GET, produces=APPLICATION_JSON_UTF8_VALUE)
	Journal getJournal(@PathVariable String journalId) {
		Journal journal = journalDao.findByIssn(journalId); 
		journal.setUrl(generateUrlFrom(journal));
		return journal;
	}
	
	@RequestMapping(path=NEW_RESOURCE_ITEM, method=POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	Journal addJournal(Journal journal) {
		return journalDao.save(journal);
	}
	
	private static String generateUrlFrom(Journal journal) {
		return RESOURCE_ITEM.replace(JOURNAL_URL_PARAM, journal.getIssn());
	}
	
	static class MedicalJournalEndpoints {
		static final String JSON_EXTENSION = ".json";
		static final String JOURNAL_URL_PARAM = "{journalId}";
		static final String RESOURCE_BASE = "/journals";
		static final String RESOURCE_COLLECTION = RESOURCE_BASE + JSON_EXTENSION;
		static final String NEW_RESOURCE_ITEM = RESOURCE_BASE;
		static final String RESOURCE_ITEM = RESOURCE_BASE + "/" + JOURNAL_URL_PARAM + JSON_EXTENSION;
	}
}