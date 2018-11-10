package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

/**
 * The persistent class for the followed_company database table.
 * 
 */
@Entity
@Table(name = "followed_company")
@NamedQuery(name = "FollowedCompany.findAll", query = "SELECT f FROM FollowedCompany f")
public class FollowedCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FOLLOWED_COMPANY_FCID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOLLOWED_COMPANY_FCID_GENERATOR")
	@Column(name = "fc_id")
	private int fcId;

	private boolean news;

	private boolean product;

	private boolean recruitment;

	@CreationTimestamp
	@Column(name="date_follow", updatable=false)
	private Timestamp dateFollow;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public FollowedCompany() {
	}

	public int getFcId() {
		return this.fcId;
	}

	public void setFcId(int fcId) {
		this.fcId = fcId;
	}

	/**
	 * @return the news
	 */
	public boolean isNews() {
		return news;
	}

	public void setNews(boolean news) {
		this.news = news;
	}

	/**
	 * @return the product
	 */
	public boolean isProduct() {
		return product;
	}

	public void setProduct(boolean product) {
		this.product = product;
	}

	/**
	 * @return the recruitment
	 */
	public boolean isRecruitment() {
		return recruitment;
	}

	public void setRecruitment(boolean recruitment) {
		this.recruitment = recruitment;
	}

	/**
	 * @return the dateFollow
	 */
	public Timestamp getDateFollow() {
		return dateFollow;
	}

	/**
	 * @param dateFollow the dateFollow to set
	 */
	public void setDateFollow(Timestamp dateFollow) {
		this.dateFollow = dateFollow;
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