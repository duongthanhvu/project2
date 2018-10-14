package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cv database table.
 * 
 */
@Entity
@NamedQuery(name="Cv.findAll", query="SELECT c FROM Cv c")
public class Cv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CV_CVID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CV_CVID_GENERATOR")
	@Column(name="cv_id")
	private int cvId;

	@Column(name="date_uploaded")
	private Timestamp dateUploaded;

	@Column(name="original_filename")
	private String originalFilename;

	@Column(name="unique_filename")
	private String uniqueFilename;

	//bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name="profile_id")
	private Profile profile;

	public Cv() {
	}

	public int getCvId() {
		return this.cvId;
	}

	public void setCvId(int cvId) {
		this.cvId = cvId;
	}

	public Timestamp getDateUploaded() {
		return this.dateUploaded;
	}

	public void setDateUploaded(Timestamp dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public String getOriginalFilename() {
		return this.originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getUniqueFilename() {
		return this.uniqueFilename;
	}

	public void setUniqueFilename(String uniqueFilename) {
		this.uniqueFilename = uniqueFilename;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}