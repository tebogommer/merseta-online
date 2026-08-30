package haj.com.bean;

import haj.com.entity.OfoCodes;

public class ATRReportSummayBean {
	private String occupationCat;
	private Long totalFemaleTrained;
	private Long totalMaleTrained;
	private Long totalPWDFemaleTrained;
	private Long totalPWDMaleTrained;
	private Long totalSATrained;
	private Long totalNonSATrained;
	private Long total;
	private double totalEstimatedCost;

	public ATRReportSummayBean() {
	}

	public ATRReportSummayBean(OfoCodes ofo, long totalFemaleTrained, long totalMaleTrained, long totalPWDFemaleTrained, long totalPWDMaleTrained, long totalSATrained, long totalNonSATrained, Double totalEstimatedCost) {
		super();

		if (ofo != null) {
			this.occupationCat = ofo.getOfoDescription();
		}

		this.totalFemaleTrained = totalFemaleTrained;
		this.totalMaleTrained = totalMaleTrained;
		this.totalPWDFemaleTrained = totalPWDFemaleTrained;
		this.totalPWDMaleTrained = totalPWDMaleTrained;
		this.totalSATrained = totalSATrained;
		this.totalNonSATrained = totalNonSATrained;
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

	public Long getTotalFemaleTrained() {
		return totalFemaleTrained;
	}

	public void setTotalFemaleTrained(Long totalFemaleTrained) {
		this.totalFemaleTrained = totalFemaleTrained;
	}

	public Long getTotalMaleTrained() {
		return totalMaleTrained;
	}

	public void setTotalMaleTrained(Long totalMaleTrained) {
		this.totalMaleTrained = totalMaleTrained;
	}

	public Long getTotalPWDFemaleTrained() {
		return totalPWDFemaleTrained;
	}

	public void setTotalPWDFemaleTrained(Long totalPWDFemaleTrained) {
		this.totalPWDFemaleTrained = totalPWDFemaleTrained;
	}

	public Long getTotalPWDMaleTrained() {
		return totalPWDMaleTrained;
	}

	public void setTotalPWDMaleTrained(Long totalPWDMaleTrained) {
		this.totalPWDMaleTrained = totalPWDMaleTrained;
	}

	public Long getTotalSATrained() {
		return totalSATrained;
	}

	public void setTotalSATrained(Long totalSATrained) {
		this.totalSATrained = totalSATrained;
	}

	public Long getTotalNonSATrained() {
		return totalNonSATrained;
	}

	public void setTotalNonSATrained(Long totalNonSATrained) {
		this.totalNonSATrained = totalNonSATrained;
	}

	public Long calculateTotal() {
		total = (long) (totalFemaleTrained + totalMaleTrained);
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
