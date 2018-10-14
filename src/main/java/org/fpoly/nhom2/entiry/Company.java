package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMPANY_COMPANYID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANY_COMPANYID_GENERATOR")
	@Column(name="company_id")
	private int companyId;

	@Column(name="contact_email")
	private String contactEmail;

	@Column(name="contact_name")
	private String contactName;

	@Column(name="contact_phone")
	private String contactPhone;

	@Column(name="date_added")
	private Timestamp dateAdded;

	@Column(name="date_updated")
	private Timestamp dateUpdated;

	@Lob
	private String description;

	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="established_year")
	private Date establishedYear;

	private String logo;

	private String name;

	private String phone;

	private byte status;

	@Column(name="url_name")
	private String urlName;

	private String website;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;

	//bi-directional many-to-one association to CompanyAdmin
	@OneToMany(mappedBy="company")
	private List<CompanyAdmin> companyAdmins;

	//bi-directional many-to-one association to CompanyCategory
	@OneToMany(mappedBy="company")
	private List<CompanyCategory> companyCategories;

	//bi-directional many-to-one association to FollowedCompany
	@OneToMany(mappedBy="company")
	private List<FollowedCompany> followedCompanies;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="company")
	private List<Job> jobs;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="company")
	private List<Post> posts;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="company")
	private List<Product> products;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="company")
	private List<Review> reviews;

	public Company() {
	}

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Timestamp getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Timestamp getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEstablishedYear() {
		return this.establishedYear;
	}

	public void setEstablishedYear(Date establishedYear) {
		this.establishedYear = establishedYear;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getUrlName() {
		return this.urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<CompanyAdmin> getCompanyAdmins() {
		return this.companyAdmins;
	}

	public void setCompanyAdmins(List<CompanyAdmin> companyAdmins) {
		this.companyAdmins = companyAdmins;
	}

	public CompanyAdmin addCompanyAdmin(CompanyAdmin companyAdmin) {
		getCompanyAdmins().add(companyAdmin);
		companyAdmin.setCompany(this);

		return companyAdmin;
	}

	public CompanyAdmin removeCompanyAdmin(CompanyAdmin companyAdmin) {
		getCompanyAdmins().remove(companyAdmin);
		companyAdmin.setCompany(null);

		return companyAdmin;
	}

	public List<CompanyCategory> getCompanyCategories() {
		return this.companyCategories;
	}

	public void setCompanyCategories(List<CompanyCategory> companyCategories) {
		this.companyCategories = companyCategories;
	}

	public CompanyCategory addCompanyCategory(CompanyCategory companyCategory) {
		getCompanyCategories().add(companyCategory);
		companyCategory.setCompany(this);

		return companyCategory;
	}

	public CompanyCategory removeCompanyCategory(CompanyCategory companyCategory) {
		getCompanyCategories().remove(companyCategory);
		companyCategory.setCompany(null);

		return companyCategory;
	}

	public List<FollowedCompany> getFollowedCompanies() {
		return this.followedCompanies;
	}

	public void setFollowedCompanies(List<FollowedCompany> followedCompanies) {
		this.followedCompanies = followedCompanies;
	}

	public FollowedCompany addFollowedCompany(FollowedCompany followedCompany) {
		getFollowedCompanies().add(followedCompany);
		followedCompany.setCompany(this);

		return followedCompany;
	}

	public FollowedCompany removeFollowedCompany(FollowedCompany followedCompany) {
		getFollowedCompanies().remove(followedCompany);
		followedCompany.setCompany(null);

		return followedCompany;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Job addJob(Job job) {
		getJobs().add(job);
		job.setCompany(this);

		return job;
	}

	public Job removeJob(Job job) {
		getJobs().remove(job);
		job.setCompany(null);

		return job;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setCompany(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setCompany(null);

		return post;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCompany(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCompany(null);

		return product;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setCompany(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setCompany(null);

		return review;
	}

}