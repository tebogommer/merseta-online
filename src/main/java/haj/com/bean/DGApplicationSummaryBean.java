package haj.com.bean;

import haj.com.entity.OfoCodes;

public class DGApplicationSummaryBean {
	private String occupationCat;
	private Long totalFemaleApplied;
	private Long totalMaleApplied;
	private Long totalPWDFemaleApplied;
	private Long totalPWDMaleApplied;
	private Long totalSAApplied;
	private Long totalNonSAApplied;
	private Long total;
	private double totalEstimatedCost;

	public DGApplicationSummaryBean() {
	}

	public DGApplicationSummaryBean(OfoCodes ofo, Long totalFemaleApplied, Long totalMaleApplied, Long totalPWDFemaleApplied, Long totalPWDMaleApplied, Long totalSAApplied, Long totalNonSAApplied, Double totalEstimatedCost) {
		super();
		if (ofo != null) {
			this.occupationCat = ofo.getOfoDescription();
		}

		this.totalFemaleApplied = totalFemaleApplied;
		this.totalMaleApplied = totalMaleApplied;
		this.totalPWDFemaleApplied = totalPWDFemaleApplied;
		this.totalPWDMaleApplied = totalPWDMaleApplied;
		this.totalSAApplied = totalSAApplied;
		this.totalNonSAApplied = totalNonSAApplied;
		if (totalEstimatedCost != null) this.totalEstimatedCost = Math.round(totalEstimatedCost);
		else this.totalEstimatedCost = 0.0;
		this.total = calculateTotal();
	}

	public String getOccupationCat() {
		return occupationCat;
	}

	public void setOccupationCat(String occupationCat) {
		this.occupationCat = occupationCat;
	}

	public Long getTotalFemaleApplied() {
		return totalFemaleApplied;
	}

	public void setTotalFemaleApplied(Long totalFemaleApplied) {
		this.totalFemaleApplied = totalFemaleApplied;
	}

	public Long getTotalMaleApplied() {
		return totalMaleApplied;
	}

	public void setTotalMaleApplied(Long totalMaleApplied) {
		this.totalMaleApplied = totalMaleApplied;
	}

	public Long getTotalPWDFemaleApplied() {
		return totalPWDFemaleApplied;
	}

	public void setTotalPWDFemaleApplied(Long totalPWDFemaleApplied) {
		this.totalPWDFemaleApplied = totalPWDFemaleApplied;
	}

	public Long getTotalPWDMaleApplied() {
		return totalPWDMaleApplied;
	}

	public void setTotalPWDMaleApplied(Long totalPWDMaleApplied) {
		this.totalPWDMaleApplied = totalPWDMaleApplied;
	}

	public Long getTotalSAApplied() {
		return totalSAApplied;
	}

	public void setTotalSAApplied(Long totalSAApplied) {
		this.totalSAApplied = totalSAApplied;
	}

	public Long getTotalNonSAApplied() {
		return totalNonSAApplied;
	}

	public void setTotalNonSAApplied(Long totalNonSAApplied) {
		this.totalNonSAApplied = totalNonSAApplied;
	}

	public Long calculateTotal() {
		total = (long) (totalFemaleApplied + totalMaleApplied);
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
