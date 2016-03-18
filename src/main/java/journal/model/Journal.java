package journal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@Column(name="JOURNAL_ID")
	@JsonIgnore
	private long id;
	
	private String issn;
	private String name;
	private String logo;
	private String tags;
	private String language;
	
	@Transient
	private String url;
	
	public Journal() {}
	
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
	public void setIssn(String issn) {
		this.issn = issn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String[] getTags() {
		return tags != null && tags.length() > 0 ? tags.split("\\s*,\\s*") : new String[0];
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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