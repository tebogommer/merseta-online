package haj.com.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import haj.com.annotations.CSVAnnotation;

// TODO: Auto-generated Javadoc
/**
 * The Class AttachmentBean.
 */
public class HeaderBean implements Serializable {

	@CSVAnnotation(length = 6)
	private String header = "HEADER";
	@CSVAnnotation(length = 3)
	private String supplierIdentifier = "599";
	@CSVAnnotation(length = 19)
	private String fileDescription;
	@CSVAnnotation(length = 9)
	private Integer numberOfRecords;
	@CSVAnnotation
	private String filler;

	public HeaderBean() {
		super();
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSupplierIdentifier() {
		return supplierIdentifier;
	}

	public void setSupplierIdentifier(String supplierIdentifier) {
		this.supplierIdentifier = supplierIdentifier;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

}
