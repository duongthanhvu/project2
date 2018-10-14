package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the followed_company database table.
 * 
 */
@Entity
@Table(name="followed_company")
@NamedQuery(name="FollowedCompany.findAll", query="SELECT f FROM FollowedCompany f")
public class FollowedCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FOLLOWED_COMPANY_FCID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FOLLOWED_COMPANY_FCID_GENERATOR")
	@Column(name="fc_id")
	private int fcId;

	private byte news;

	private byte product;

	private byte recruitment;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public FollowedCompany() {
	}

	public int getFcId() {
		return this.fcId;
	}

	public void setFcId(int fcId) {
		this.fcId = fcId;
	}

	public byte getNews() {
		return this.news;
	}

	public void setNews(byte news) {
		this.news = news;
	}

	public byte getProduct() {
		return this.product;
	}

	public void setProduct(byte product) {
		this.product = product;
	}

	public byte getRecruitment() {
		return this.recruitment;
	}

	public void setRecruitment(byte recruitment) {
		this.recruitment = recruitment;
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