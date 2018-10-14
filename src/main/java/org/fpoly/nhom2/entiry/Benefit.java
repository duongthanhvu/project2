package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the benefit database table.
 * 
 */
@Entity
@NamedQuery(name="Benefit.findAll", query="SELECT b FROM Benefit b")
public class Benefit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BENEFIT_BENEFITID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BENEFIT_BENEFITID_GENERATOR")
	@Column(name="benefit_id")
	private int benefitId;

	@Lob
	private String description;

	private String title;

	//bi-directional many-to-one association to BenefitOfJob
	@OneToMany(mappedBy="benefit")
	private List<BenefitOfJob> benefitOfJobs;

	public Benefit() {
	}

	public int getBenefitId() {
		return this.benefitId;
	}

	public void setBenefitId(int benefitId) {
		this.benefitId = benefitId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<BenefitOfJob> getBenefitOfJobs() {
		return this.benefitOfJobs;
	}

	public void setBenefitOfJobs(List<BenefitOfJob> benefitOfJobs) {
		this.benefitOfJobs = benefitOfJobs;
	}

	public BenefitOfJob addBenefitOfJob(BenefitOfJob benefitOfJob) {
		getBenefitOfJobs().add(benefitOfJob);
		benefitOfJob.setBenefit(this);

		return benefitOfJob;
	}

	public BenefitOfJob removeBenefitOfJob(BenefitOfJob benefitOfJob) {
		getBenefitOfJobs().remove(benefitOfJob);
		benefitOfJob.setBenefit(null);

		return benefitOfJob;
	}

}