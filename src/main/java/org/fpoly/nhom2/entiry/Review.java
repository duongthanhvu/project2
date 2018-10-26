package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


/**
 * The persistent class for the review database table.
 * 
 */
@Entity
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REVIEW_REVIEWID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REVIEW_REVIEWID_GENERATOR")
	@Column(name="review_id")
	private int reviewId;

	@CreationTimestamp
	@Column(name="date_reviewed", updatable=false)
	private Timestamp dateReviewed;

	@Lob
	private String description;

	private int rating;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Review() {
	}

	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Timestamp getDateReviewed() {
		return this.dateReviewed;
	}

	public void setDateReviewed(Timestamp dateReviewed) {
		this.dateReviewed = dateReviewed;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}