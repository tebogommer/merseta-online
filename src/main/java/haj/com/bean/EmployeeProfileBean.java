package haj.com.bean;

import haj.com.entity.OfoCodes;

public class EmployeeProfileBean {
	private String occupationCat;
	private Long totalFemale;
	private Long totalMale;
	private Long totalPWDFemale;
	private Long totalPWDMale;
	private Long totalSA;
	private Long totalNonSA;
	private Long ofoCode;

	public EmployeeProfileBean() {

	}

	public EmployeeProfileBean(OfoCodes ofo, long totalFemale, long totalMale, long totalPWDFemale, long totalPWDMale, long totalSA, long totalNonSA) {
		super();
		if (ofo != null) {
			this.occupationCat = ofo.getOfoDescription();
		}

		this.totalFemale = totalFemale;
		this.totalMale = totalMale;
		this.totalPWDFemale = totalPWDFemale;
		this.totalPWDMale = totalPWDMale;
		this.totalSA = totalSA;
		this.totalNonSA = totalNonSA;
	}

	public String getOccupationCat() {
		return occupationCat;
	}

	public void setOccupationCat(String occupationCat) {
		this.occupationCat = occupationCat;
	}

	public Long getTotalFemale() {
		return totalFemale;
	}

	public void setTotalFemale(Long totalFemale) {
		this.totalFemale = totalFemale;
	}

	public Long getTotalMale() {
		return totalMale;
	}

	public void setTotalMale(Long totalMale) {
		this.totalMale = totalMale;
	}

	public Long getTotalPWDFemale() {
		return totalPWDFemale;
	}

	public void setTotalPWDFemale(Long totalPWDFemale) {
		this.totalPWDFemale = totalPWDFemale;
	}

	public Long getTotalPWDMale() {
		return totalPWDMale;
	}

	public void setTotalPWDMale(Long totalPWDMale) {
		this.totalPWDMale = totalPWDMale;
	}

	public Long getTotalSA() {
		return totalSA;
	}

	public void setTotalSA(Long totalSA) {
		this.totalSA = totalSA;
	}

	public Long getTotalNonSA() {
		return totalNonSA;
	}

	public void setTotalNonSA(Long totalNonSA) {
		this.totalNonSA = totalNonSA;
	}

	public Long getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(Long ofoCode) {
		this.ofoCode = ofoCode;
	}

}
