package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORY_CATEGORYID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_CATEGORYID_GENERATOR")
	@Column(name="category_id")
	private int categoryId;

	private String description;

	private String name;

	//bi-directional many-to-one association to CompanyCategory
	@OneToMany(mappedBy="category")
	private List<CompanyCategory> companyCategories;

	public Category() {
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompanyCategory> getCompanyCategories() {
		return this.companyCategories;
	}

	public void setCompanyCategories(List<CompanyCategory> companyCategories) {
		this.companyCategories = companyCategories;
	}

	public CompanyCategory addCompanyCategory(CompanyCategory companyCategory) {
		getCompanyCategories().add(companyCategory);
		companyCategory.setCategory(this);

		return companyCategory;
	}

	public CompanyCategory removeCompanyCategory(CompanyCategory companyCategory) {
		getCompanyCategories().remove(companyCategory);
		companyCategory.setCategory(null);

		return companyCategory;
	}

}