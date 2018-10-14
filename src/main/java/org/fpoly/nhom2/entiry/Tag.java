package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tag database table.
 * 
 */
@Entity
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAG_TAGID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAG_TAGID_GENERATOR")
	@Column(name="tag_id")
	private int tagId;

	private String title;

	//bi-directional many-to-one association to TagOfJob
	@OneToMany(mappedBy="tag")
	private List<TagOfJob> tagOfJobs;

	public Tag() {
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TagOfJob> getTagOfJobs() {
		return this.tagOfJobs;
	}

	public void setTagOfJobs(List<TagOfJob> tagOfJobs) {
		this.tagOfJobs = tagOfJobs;
	}

	public TagOfJob addTagOfJob(TagOfJob tagOfJob) {
		getTagOfJobs().add(tagOfJob);
		tagOfJob.setTag(this);

		return tagOfJob;
	}

	public TagOfJob removeTagOfJob(TagOfJob tagOfJob) {
		getTagOfJobs().remove(tagOfJob);
		tagOfJob.setTag(null);

		return tagOfJob;
	}

}