package org.fpoly.nhom2.entiry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the photo_of_company database table.
 * 
 */
@Entity
@Table(name="photo_of_company")
@NamedQuery(name="PoCompany.findAll", query="SELECT p FROM PoCompany p")
public class PoCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PHOTO_OF_COMPANY_POCID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PHOTO_OF_COMPANY_POCID_GENERATOR")
	@Column(name="photo_of_company_id")
	private int poCompanyId;

    private String photo;
    
    private String type;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	public PoCompany() {
	}

	/**
     * @return the poCompanyId
     */
    public int getPoCompanyId() {
        return poCompanyId;
    }

    /**
     * @param poCompanyId the poCompanyId to set
     */
    public void setPoCompanyId(int poCompanyId) {
        this.poCompanyId = poCompanyId;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }
}