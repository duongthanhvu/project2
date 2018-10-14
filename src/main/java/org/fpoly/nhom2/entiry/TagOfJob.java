package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tag_of_job database table.
 * 
 */
@Entity
@Table(name="tag_of_job")
@NamedQuery(name="TagOfJob.findAll", query="SELECT t FROM TagOfJob t")
public class TagOfJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAG_OF_JOB_TOJID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAG_OF_JOB_TOJID_GENERATOR")
	@Column(name="toj_id")
	private int tojId;

	//bi-directional many-to-one association to Tag
	@ManyToOne
	@JoinColumn(name="tag_id")
	private Tag tag;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;

	public TagOfJob() {
	}

	public int getTojId() {
		return this.tojId;
	}

	public void setTojId(int tojId) {
		this.tojId = tojId;
	}

	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}