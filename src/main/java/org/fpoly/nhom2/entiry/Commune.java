package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the commune database table.
 * 
 */
@Entity
@NamedQuery(name="Commune.findAll", query="SELECT c FROM Commune c")
public class Commune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMMUNE_COMMUNEID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMMUNE_COMMUNEID_GENERATOR")
	@Column(name="commune_id")
	private int communeId;

	private String name;

	//bi-directional many-to-one association to Address
	@JsonIgnore
	@OneToMany(mappedBy="commune")
	private List<Address> addresses;

	//bi-directional many-to-one association to CityOrDist
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="city_or_dist_id")
	private CityOrDist cityOrDist;

	public Commune() {
	}

	public int getCommuneId() {
		return this.communeId;
	}

	public void setCommuneId(int communeId) {
		this.communeId = communeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setCommune(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setCommune(null);

		return address;
	}

	public CityOrDist getCityOrDist() {
		return this.cityOrDist;
	}

	public void setCityOrDist(CityOrDist cityOrDist) {
		this.cityOrDist = cityOrDist;
	}

}