package journal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
@Entity
@Table(name="MEDICAL_JOURNAL")
public class Journal {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	private String issn;
	private String name;
	private String logo;
	private String tags;
	private String language;
	private String url;
	
	protected Journal() {}	// Required by JPA provider when creating instances of this class
	
	public Journal(String issn, String name, String language) {
		this(issn, name, language, null, null);
	}
	
	public Journal(String issn, String name, String language, String logo, String tags) {
		this.issn = issn;
		this.name = name;
		this.language = language;
		this.logo = logo;
		this.tags = tags;
	}
	
	public long getId() {
		return id;
	}
	public String getIssn() {
		return issn;
	}
	public String getName() {
		return name;
	}
	public String getLogo() {
		return logo;
	}
	public String[] getTags() {
		return tags.split("\\s*,\\s*");
	}
	public String getLanguage() {
		return language;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return String.format("Journal[id=%s, issn=%s, name=%s, logo=%s, tags=%s, language=%s]", id, issn, name, logo, tags, language);
	}
}