package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCT_PRODUCTID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_PRODUCTID_GENERATOR")
	@Column(name="product_id")
	private int productId;

	@Lob
	private String description;

	private String name;

	@Column(name="url_name")
	private String urlName;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

	//bi-directional many-to-one association to ProductPicture
	@OneToMany(mappedBy="product")
	private List<ProductPicture> productPictures;

	public Product() {
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	/**
	 * @return the urlName
	 */
	public String getUrlName() {
		return urlName;
	}

	/**
	 * @param urlName the urlName to set
	 */
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<ProductPicture> getProductPictures() {
		return this.productPictures;
	}

	public void setProductPictures(List<ProductPicture> productPictures) {
		this.productPictures = productPictures;
	}

	public ProductPicture addProductPicture(ProductPicture productPicture) {
		getProductPictures().add(productPicture);
		productPicture.setProduct(this);

		return productPicture;
	}

	public ProductPicture removeProductPicture(ProductPicture productPicture) {
		getProductPictures().remove(productPicture);
		productPicture.setProduct(null);

		return productPicture;
	}

}