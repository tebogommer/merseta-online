package haj.com.bean;

import haj.com.entity.OfoCodes;

public class PivotalTrainingReportBean {
	private String occupationCat;
	private Long totalFemale;
	private Long totalMale;
	private Long totalPWDFemale;
	private Long totalPWDMale;
	private Long totalSA;
	private Long totalNonSA;
	private Long total;
	private double totalEstimatedCost;

	public PivotalTrainingReportBean() {
	}

	public PivotalTrainingReportBean(OfoCodes ofo, Long totalFemale, Long totalMale, Long totalPWDFemale, Long totalPWDMale, Long totalSA, Long totalNonSA, Double totalEstimatedCost) {
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
		if (totalEstimatedCost != null) this.totalEstimatedCost = totalEstimatedCost;
		else this.totalEstimatedCost = 0;
		this.total = calculateTotal();
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

	public Long calculateTotal() {
		total = (long) (totalFemale + totalMale);
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public double getTotalEstimatedCost() {
		return totalEstimatedCost;
	}

	public void setTotalEstimatedCost(double totalEstimatedCost) {
		this.totalEstimatedCost = totalEstimatedCost;
	}

	public Long getTotal() {
		return total;
	}

}
