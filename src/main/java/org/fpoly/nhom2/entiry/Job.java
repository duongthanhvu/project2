package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the job database table.
 * 
 */
@Entity
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="JOB_JOBID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JOB_JOBID_GENERATOR")
	@Column(name="job_id")
	private int jobId;

	@CreationTimestamp
	@Column(name="date_created",updatable=false)
	private Timestamp dateCreated;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="date_expired")
	private Date dateExpired;

	@UpdateTimestamp
	@Column(name="date_updated")
	private Timestamp dateUpdated;

	@Lob
	private String description;

	@Lob
	private String requirement;

	private String salary;

	private String title;

	@Column(name="url_title")
	private String urlTitle;

	//bi-directional many-to-one association to BenefitOfJob
	@OneToMany(mappedBy="job")
	private List<BenefitOfJob> benefitOfJobs;

	//bi-directional many-to-one association to ProvinceOrCity
	@ManyToOne
	@JoinColumn(name="location_id")
	private ProvinceOrCity provinceOrCity;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	//bi-directional many-to-one association to SavedJob
	@OneToMany(mappedBy="job")
	private List<SavedJob> savedJobs;

	//bi-directional many-to-one association to TagOfJob
	@OneToMany(mappedBy="job")
	private List<TagOfJob> tagOfJobs;

	public Job() {
	}

	public int getJobId() {
		return this.jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public Timestamp getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateExpired() {
		return this.dateExpired;
	}

	public void setDateExpired(Date dateExpired) {
		this.dateExpired = dateExpired;
	}

	public Timestamp getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequirement() {
		return this.requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getSalary() {
		return this.salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlTitle() {
		return this.urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}

	public List<BenefitOfJob> getBenefitOfJobs() {
		return this.benefitOfJobs;
	}

	public void setBenefitOfJobs(List<BenefitOfJob> benefitOfJobs) {
		this.benefitOfJobs = benefitOfJobs;
	}

	public BenefitOfJob addBenefitOfJob(BenefitOfJob benefitOfJob) {
		getBenefitOfJobs().add(benefitOfJob);
		benefitOfJob.setJob(this);

		return benefitOfJob;
	}

	public BenefitOfJob removeBenefitOfJob(BenefitOfJob benefitOfJob) {
		getBenefitOfJobs().remove(benefitOfJob);
		benefitOfJob.setJob(null);

		return benefitOfJob;
	}

	public ProvinceOrCity getProvinceOrCity() {
		return this.provinceOrCity;
	}

	public void setProvinceOrCity(ProvinceOrCity provinceOrCity) {
		this.provinceOrCity = provinceOrCity;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<SavedJob> getSavedJobs() {
		return this.savedJobs;
	}

	public void setSavedJobs(List<SavedJob> savedJobs) {
		this.savedJobs = savedJobs;
	}

	public SavedJob addSavedJob(SavedJob savedJob) {
		getSavedJobs().add(savedJob);
		savedJob.setJob(this);

		return savedJob;
	}

	public SavedJob removeSavedJob(SavedJob savedJob) {
		getSavedJobs().remove(savedJob);
		savedJob.setJob(null);

		return savedJob;
	}

	public List<TagOfJob> getTagOfJobs() {
		return this.tagOfJobs;
	}

	public void setTagOfJobs(List<TagOfJob> tagOfJobs) {
		this.tagOfJobs = tagOfJobs;
	}

	public TagOfJob addTagOfJob(TagOfJob tagOfJob) {
		getTagOfJobs().add(tagOfJob);
		tagOfJob.setJob(this);

		return tagOfJob;
	}

	public TagOfJob removeTagOfJob(TagOfJob tagOfJob) {
		getTagOfJobs().remove(tagOfJob);
		tagOfJob.setJob(null);

		return tagOfJob;
	}

}