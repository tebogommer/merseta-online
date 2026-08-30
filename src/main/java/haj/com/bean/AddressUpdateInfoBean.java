package haj.com.bean;

/**
 * The Class DiscretionaryGrantBean.
 */
public class AddressUpdateInfoBean {

	private Boolean lineOne;
	private Boolean lineTwo;
	private Boolean lineThree;
	private Boolean lineFour;
	private Boolean town;
	private Boolean postCode;
	private Boolean muncipality;
	private Boolean statsSaAreaCode;
	private Boolean longitudeDegrees;
	private Boolean latitudeDegrees;

	public AddressUpdateInfoBean() {
		super();
		lineOne = false;
		lineTwo = false;
		lineThree = false;
		lineFour = false;
		town = false;
		postCode = false;
		muncipality = false;
		longitudeDegrees = false;
		latitudeDegrees = false;
		statsSaAreaCode = false;
	}

	public Boolean getLineOne() {
		return lineOne;
	}

	public void setLineOne(Boolean lineOne) {
		this.lineOne = lineOne;
	}
	public Boolean getLineTwo() {
		return lineTwo;
	}
	public void setLineTwo(Boolean lineTwo) {
		this.lineTwo = lineTwo;
	}
	public Boolean getLineThree() {
		return lineThree;
	}
	public void setLineThree(Boolean lineThree) {
		this.lineThree = lineThree;
	}
	public Boolean getLineFour() {
		return lineFour;
	}
	public void setLineFour(Boolean lineFour) {
		this.lineFour = lineFour;
	}
	public Boolean getPostCode() {
		return postCode;
	}
	public void setPostCode(Boolean postCode) {
		this.postCode = postCode;
	}
	public Boolean getMuncipality() {
		return muncipality;
	}
	public void setMuncipality(Boolean muncipality) {
		this.muncipality = muncipality;
	}
	public Boolean getLongitudeDegrees() {
		return longitudeDegrees;
	}
	public void setLongitudeDegrees(Boolean longitudeDegrees) {
		this.longitudeDegrees = longitudeDegrees;
	}
	public Boolean getLatitudeDegrees() {
		return latitudeDegrees;
	}
	public void setLatitudeDegrees(Boolean latitudeDegrees) {
		this.latitudeDegrees = latitudeDegrees;
	}

	public Boolean getTown() {
		return town;
	}

	public void setTown(Boolean town) {
		this.town = town;
	}

	public Boolean getStatsSaAreaCode() {
		return statsSaAreaCode;
	}

	public void setStatsSaAreaCode(Boolean statsSaAreaCode) {
		this.statsSaAreaCode = statsSaAreaCode;
	}

}