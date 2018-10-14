package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the benefit_of_job database table.
 * 
 */
@Entity
@Table(name="benefit_of_job")
@NamedQuery(name="BenefitOfJob.findAll", query="SELECT b FROM BenefitOfJob b")
public class BenefitOfJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BENEFIT_OF_JOB_BOJID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BENEFIT_OF_JOB_BOJID_GENERATOR")
	@Column(name="boj_id")
	private int bojId;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;

	//bi-directional many-to-one association to Benefit
	@ManyToOne
	@JoinColumn(name="benefit_id")
	private Benefit benefit;

	public BenefitOfJob() {
	}

	public int getBojId() {
		return this.bojId;
	}

	public void setBojId(int bojId) {
		this.bojId = bojId;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Benefit getBenefit() {
		return this.benefit;
	}

	public void setBenefit(Benefit benefit) {
		this.benefit = benefit;
	}

}