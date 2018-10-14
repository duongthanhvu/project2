package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the company_category database table.
 * 
 */
@Entity
@Table(name="company_category")
@NamedQuery(name="CompanyCategory.findAll", query="SELECT c FROM CompanyCategory c")
public class CompanyCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMPANY_CATEGORY_COMCATID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANY_CATEGORY_COMCATID_GENERATOR")
	@Column(name="com_cat_id")
	private int comCatId;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	public CompanyCategory() {
	}

	public int getComCatId() {
		return this.comCatId;
	}

	public void setComCatId(int comCatId) {
		this.comCatId = comCatId;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}