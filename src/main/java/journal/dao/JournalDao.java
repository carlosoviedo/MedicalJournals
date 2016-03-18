package journal.dao;

import journal.model.Journal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
public interface JournalDao extends PagingAndSortingRepository<Journal, Long> {
	Journal findByIssn(String issn);
}