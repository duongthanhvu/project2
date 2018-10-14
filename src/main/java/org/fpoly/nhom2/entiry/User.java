package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_USERID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_USERID_GENERATOR")
	@Column(name="user_id")
	private int userId;

	@Column(name="date_registered")
	private Timestamp dateRegistered;

	@Column(name="date_updated")
	private Timestamp dateUpdated;

	private String email;

	private String password;

	private String username;

	//bi-directional many-to-one association to CompanyAdmin
	@OneToMany(mappedBy="user")
	private List<CompanyAdmin> companyAdmins;

	//bi-directional many-to-one association to FollowedCompany
	@OneToMany(mappedBy="user")
	private List<FollowedCompany> followedCompanies;

	//bi-directional one-to-one association to Profile
	@OneToOne(mappedBy="user")
	private Profile profile;

	//bi-directional many-to-one association to Report
	@OneToMany(mappedBy="user")
	private List<Report> reports;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="user")
	private List<Review> reviews;

	//bi-directional many-to-one association to SavedJob
	@OneToMany(mappedBy="user")
	private List<SavedJob> savedJobs;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getDateRegistered() {
		return this.dateRegistered;
	}

	public void setDateRegistered(Timestamp dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public Timestamp getDateUpdated() {
		return this.dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CompanyAdmin> getCompanyAdmins() {
		return this.companyAdmins;
	}

	public void setCompanyAdmins(List<CompanyAdmin> companyAdmins) {
		this.companyAdmins = companyAdmins;
	}

	public CompanyAdmin addCompanyAdmin(CompanyAdmin companyAdmin) {
		getCompanyAdmins().add(companyAdmin);
		companyAdmin.setUser(this);

		return companyAdmin;
	}

	public CompanyAdmin removeCompanyAdmin(CompanyAdmin companyAdmin) {
		getCompanyAdmins().remove(companyAdmin);
		companyAdmin.setUser(null);

		return companyAdmin;
	}

	public List<FollowedCompany> getFollowedCompanies() {
		return this.followedCompanies;
	}

	public void setFollowedCompanies(List<FollowedCompany> followedCompanies) {
		this.followedCompanies = followedCompanies;
	}

	public FollowedCompany addFollowedCompany(FollowedCompany followedCompany) {
		getFollowedCompanies().add(followedCompany);
		followedCompany.setUser(this);

		return followedCompany;
	}

	public FollowedCompany removeFollowedCompany(FollowedCompany followedCompany) {
		getFollowedCompanies().remove(followedCompany);
		followedCompany.setUser(null);

		return followedCompany;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Report> getReports() {
		return this.reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public Report addReport(Report report) {
		getReports().add(report);
		report.setUser(this);

		return report;
	}

	public Report removeReport(Report report) {
		getReports().remove(report);
		report.setUser(null);

		return report;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setUser(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setUser(null);

		return review;
	}

	public List<SavedJob> getSavedJobs() {
		return this.savedJobs;
	}

	public void setSavedJobs(List<SavedJob> savedJobs) {
		this.savedJobs = savedJobs;
	}

	public SavedJob addSavedJob(SavedJob savedJob) {
		getSavedJobs().add(savedJob);
		savedJob.setUser(this);

		return savedJob;
	}

	public SavedJob removeSavedJob(SavedJob savedJob) {
		getSavedJobs().remove(savedJob);
		savedJob.setUser(null);

		return savedJob;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}