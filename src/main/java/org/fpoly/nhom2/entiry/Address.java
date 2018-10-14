package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ADDRESS_ADDRESSID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADDRESS_ADDRESSID_GENERATOR")
	@Column(name="address_id")
	private int addressId;

	private String name;

	@Column(name="other_details")
	private String otherDetails;

	private String street;

	//bi-directional many-to-one association to Commune
	@ManyToOne
	@JoinColumn(name="commune_id")
	private Commune commune;

	//bi-directional many-to-one association to Company
	@OneToMany(mappedBy="address")
	private List<Company> companies;

	//bi-directional many-to-one association to Profile
	@OneToMany(mappedBy="address")
	private List<Profile> profiles;

	public Address() {
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherDetails() {
		return this.otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Commune getCommune() {
		return this.commune;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}

	public List<Company> getCompanies() {
		return this.companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company addCompany(Company company) {
		getCompanies().add(company);
		company.setAddress(this);

		return company;
	}

	public Company removeCompany(Company company) {
		getCompanies().remove(company);
		company.setAddress(null);

		return company;
	}

	public List<Profile> getProfiles() {
		return this.profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public Profile addProfile(Profile profile) {
		getProfiles().add(profile);
		profile.setAddress(this);

		return profile;
	}

	public Profile removeProfile(Profile profile) {
		getProfiles().remove(profile);
		profile.setAddress(null);

		return profile;
	}

}