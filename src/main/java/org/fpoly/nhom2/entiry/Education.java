package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the education database table.
 * 
 */
@Entity
@NamedQuery(name="Education.findAll", query="SELECT e FROM Education e")
public class Education implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EDUCATION_EDUID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EDUCATION_EDUID_GENERATOR")
	@Column(name="edu_id")
	private int eduId;

	@Temporal(TemporalType.DATE)
	@Column(name="from_month")
	private Date fromMonth;

	private String qualification;

	private String school;

	private String subject;

	@Temporal(TemporalType.DATE)
	@Column(name="to_month")
	private Date toMonth;

	//bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name="profile_id")
	private Profile profile;

	public Education() {
	}

	public int getEduId() {
		return this.eduId;
	}

	public void setEduId(int eduId) {
		this.eduId = eduId;
	}

	public Date getFromMonth() {
		return this.fromMonth;
	}

	public void setFromMonth(Date fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getToMonth() {
		return this.toMonth;
	}

	public void setToMonth(Date toMonth) {
		this.toMonth = toMonth;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}