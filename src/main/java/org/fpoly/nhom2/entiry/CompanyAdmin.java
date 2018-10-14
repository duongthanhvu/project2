package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the company_admin database table.
 * 
 */
@Entity
@Table(name="company_admin")
@NamedQuery(name="CompanyAdmin.findAll", query="SELECT c FROM CompanyAdmin c")
public class CompanyAdmin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMPANY_ADMIN_CAID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANY_ADMIN_CAID_GENERATOR")
	@Column(name="ca_id")
	private int caId;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public CompanyAdmin() {
	}

	public int getCaId() {
		return this.caId;
	}

	public void setCaId(int caId) {
		this.caId = caId;
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