package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the city_or_dist database table.
 * 
 */
@Entity
@Table(name="city_or_dist")
@NamedQuery(name="CityOrDist.findAll", query="SELECT c FROM CityOrDist c")
public class CityOrDist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CITY_OR_DIST_CITYORDISTID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_OR_DIST_CITYORDISTID_GENERATOR")
	@Column(name="city_or_dist_id")
	private int cityOrDistId;

	private String name;

	//bi-directional many-to-one association to ProvinceOrCity
	@ManyToOne
	@JoinColumn(name="poc_id")
	private ProvinceOrCity provinceOrCity;

	//bi-directional many-to-one association to Commune
	@OneToMany(mappedBy="cityOrDist")
	private List<Commune> communes;

	public CityOrDist() {
	}

	public int getCityOrDistId() {
		return this.cityOrDistId;
	}

	public void setCityOrDistId(int cityOrDistId) {
		this.cityOrDistId = cityOrDistId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProvinceOrCity getProvinceOrCity() {
		return this.provinceOrCity;
	}

	public void setProvinceOrCity(ProvinceOrCity provinceOrCity) {
		this.provinceOrCity = provinceOrCity;
	}

	public List<Commune> getCommunes() {
		return this.communes;
	}

	public void setCommunes(List<Commune> communes) {
		this.communes = communes;
	}

	public Commune addCommune(Commune commune) {
		getCommunes().add(commune);
		commune.setCityOrDist(this);

		return commune;
	}

	public Commune removeCommune(Commune commune) {
		getCommunes().remove(commune);
		commune.setCityOrDist(null);

		return commune;
	}

}