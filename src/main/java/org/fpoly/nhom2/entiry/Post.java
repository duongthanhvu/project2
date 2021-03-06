package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "POST_POSTID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_POSTID_GENERATOR")
	@Column(name = "post_id")
	private int postId;

	@Lob
	private String content;

	@CreationTimestamp
	@Column(name = "date_created", updatable = false)
	private Timestamp dateCreated;

	@UpdateTimestamp
	@Column(name = "date_updated")
	private Timestamp dateUpdated;

	private String title;

	@Column(name = "url_title")
	private String urlTitle;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	// bi-directional many-to-one association to View Count
	@ManyToOne
	@JoinColumn(name = "view_count_id",updatable=false)
	private ViewCount viewCount;

	public Post() {
	}

	public int getPostId() {
		return this.postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the urlTitle
	 */
	public String getUrlTitle() {
		return urlTitle;
	}

	/**
	 * @param urlTitle the urlTitle to set
	 */
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the viewCount
	 */
	public ViewCount getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(ViewCount viewCount) {
		this.viewCount = viewCount;
	}
}