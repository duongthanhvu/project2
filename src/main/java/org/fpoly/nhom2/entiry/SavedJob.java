package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the saved_job database table.
 * 
 */
@Entity
@Table(name="saved_job")
@NamedQuery(name="SavedJob.findAll", query="SELECT s FROM SavedJob s")
public class SavedJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SAVED_JOB_SAVEDJOBID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SAVED_JOB_SAVEDJOBID_GENERATOR")
	@Column(name="saved_job_id")
	private int savedJobId;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public SavedJob() {
	}

	public int getSavedJobId() {
		return this.savedJobId;
	}

	public void setSavedJobId(int savedJobId) {
		this.savedJobId = savedJobId;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}