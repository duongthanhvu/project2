package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the profile database table.
 * 
 */
@Entity
@NamedQuery(name="Profile.findAll", query="SELECT p FROM Profile p")
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="profile_id")
	private int profileId;

	@Column(name="avatar_picture")
	private String avatarPicture;

	@CreationTimestamp
	@Column(name="date_created",updatable=false)
	private Timestamp dateCreated;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date_of_birth")
	private Date dateOfBirth;

	@UpdateTimestamp
	@Column(name="date_updated")
	private Timestamp dateUpdated;

	@Lob
	@Column(name="employment_history")
	private String employmentHistory;

	private String fullname;

	private String gender;

	@Column(name="job_level")
	private String jobLevel;

	@Column(name="martial_status")
	private Byte martialStatus;

	private String phone;

	@Column(name="professional_title")
	private String professionalTitle;

	@Lob
	private String summary;

	@Column(name="url_name")
	private String urlName;

	//bi-directional many-to-one association to Cv
	@OneToMany(mappedBy="profile")
	private List<Cv> cvs;

	//bi-directional many-to-one association to Education
	@OneToMany(mappedBy="profile")
	private List<Education> educations;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="profile_id")
	private User user;

	//bi-directional many-to-one association to SkillList
	@OneToMany(mappedBy="profile")
	private List<SkillList> skillLists;

	public Profile() {
	}

	public int getProfileId() {
		return this.profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public String getAvatarPicture() {
		return this.avatarPicture;
	}

	public void setAvatarPicture(String avatarPicture) {
		this.avatarPicture = avatarPicture;
	}

	public Timestamp getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Timestamp getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getEmploymentHistory() {
		return this.employmentHistory;
	}

	public void setEmploymentHistory(String employmentHistory) {
		this.employmentHistory = employmentHistory;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getJobLevel() {
		return this.jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public Byte getMartialStatus() {
		return this.martialStatus;
	}

	public void setMartialStatus(Byte martialStatus) {
		this.martialStatus = martialStatus;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfessionalTitle() {
		return this.professionalTitle;
	}

	public void setProfessionalTitle(String professionalTitle) {
		this.professionalTitle = professionalTitle;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrlName() {
		return this.urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public List<Cv> getCvs() {
		return this.cvs;
	}

	public void setCvs(List<Cv> cvs) {
		this.cvs = cvs;
	}

	public Cv addCv(Cv cv) {
		getCvs().add(cv);
		cv.setProfile(this);

		return cv;
	}

	public Cv removeCv(Cv cv) {
		getCvs().remove(cv);
		cv.setProfile(null);

		return cv;
	}

	public List<Education> getEducations() {
		return this.educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public Education addEducation(Education education) {
		getEducations().add(education);
		education.setProfile(this);

		return education;
	}

	public Education removeEducation(Education education) {
		getEducations().remove(education);
		education.setProfile(null);

		return education;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SkillList> getSkillLists() {
		return this.skillLists;
	}

	public void setSkillLists(List<SkillList> skillLists) {
		this.skillLists = skillLists;
	}

	public SkillList addSkillList(SkillList skillList) {
		getSkillLists().add(skillList);
		skillList.setProfile(this);

		return skillList;
	}

	public SkillList removeSkillList(SkillList skillList) {
		getSkillLists().remove(skillList);
		skillList.setProfile(null);

		return skillList;
	}

}