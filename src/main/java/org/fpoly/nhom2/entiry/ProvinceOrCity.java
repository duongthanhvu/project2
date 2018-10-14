package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the province_or_city database table.
 * 
 */
@Entity
@Table(name="province_or_city")
@NamedQuery(name="ProvinceOrCity.findAll", query="SELECT p FROM ProvinceOrCity p")
public class ProvinceOrCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROVINCE_OR_CITY_POCID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROVINCE_OR_CITY_POCID_GENERATOR")
	@Column(name="poc_id")
	private int pocId;

	private String country;

	private String name;

	//bi-directional many-to-one association to CityOrDist
	@OneToMany(mappedBy="provinceOrCity")
	private List<CityOrDist> cityOrDists;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="provinceOrCity")
	private List<Job> jobs;

	public ProvinceOrCity() {
	}

	public int getPocId() {
		return this.pocId;
	}

	public void setPocId(int pocId) {
		this.pocId = pocId;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityOrDist> getCityOrDists() {
		return this.cityOrDists;
	}

	public void setCityOrDists(List<CityOrDist> cityOrDists) {
		this.cityOrDists = cityOrDists;
	}

	public CityOrDist addCityOrDist(CityOrDist cityOrDist) {
		getCityOrDists().add(cityOrDist);
		cityOrDist.setProvinceOrCity(this);

		return cityOrDist;
	}

	public CityOrDist removeCityOrDist(CityOrDist cityOrDist) {
		getCityOrDists().remove(cityOrDist);
		cityOrDist.setProvinceOrCity(null);

		return cityOrDist;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Job addJob(Job job) {
		getJobs().add(job);
		job.setProvinceOrCity(this);

		return job;
	}

	public Job removeJob(Job job) {
		getJobs().remove(job);
		job.setProvinceOrCity(null);

		return job;
	}

}