package haj.com.bean;

import java.io.Serializable;

public class IngterSetaTransferBean implements Serializable {

	private Integer schemeYear;
	private String description;
	private String description2;
	private Double amount;




	public IngterSetaTransferBean(String description, Double amount) {
		super();
		this.description = description;
		this.amount = amount;
	}
	public IngterSetaTransferBean(Integer schemeYear, String description, String description2, Double amount) {
		super();
		this.schemeYear = schemeYear;
		this.description = description;
		this.description2 = description2;
		this.amount = amount;
	}
	public IngterSetaTransferBean(Integer schemeYear, String description, Double amount) {
		super();
		this.schemeYear = schemeYear;
		this.description = description;
		this.amount = amount;
	}
	public IngterSetaTransferBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getSchemeYear() {
		return schemeYear;
	}
	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription2() {
		return description2;
	}
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
}
