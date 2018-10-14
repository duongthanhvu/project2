package org.fpoly.nhom2.entiry;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the product_picture database table.
 * 
 */
@Entity
@Table(name="product_picture")
@NamedQuery(name="ProductPicture.findAll", query="SELECT p FROM ProductPicture p")
public class ProductPicture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCT_PICTURE_PICTUREID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_PICTURE_PICTUREID_GENERATOR")
	@Column(name="picture_id")
	private int pictureId;

	private String description;

	private String filename;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

	public ProductPicture() {
	}

	public int getPictureId() {
		return this.pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}