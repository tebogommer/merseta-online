package haj.com.bean;

import haj.com.entity.OfoCodes;

public class WSPReportSummayBean {
	private String occupationCat;
	private Long totalFemalePlanForTraining;
	private Long totalMalePlanForTraining;
	private Long totalPWDFemalePlanForTraining;
	private Long totalPWDMalePlanForTraining;
	private Long totalSAPlanForTraining;
	private Long totalNonSAPlanForTraining;
	private Long total;
	private double totalEstimatedCost;

	public WSPReportSummayBean() {
	}

	public WSPReportSummayBean(OfoCodes ofo, long totalFemalePlanForTraining, long totalMalePlanForTraining, long totalPWDFemalePlanForTraining, long totalPWDMalePlanForTraining, long totalSAPlanForTraining, long totalNonSAPlanForTraining, Double totalEstimatedCost) {
		super();
		if (ofo != null) {
			this.occupationCat = ofo.getOfoDescription();
		}

		this.totalFemalePlanForTraining = totalFemalePlanForTraining;
		this.totalMalePlanForTraining = totalMalePlanForTraining;
		this.totalPWDFemalePlanForTraining = totalPWDFemalePlanForTraining;
		this.totalPWDMalePlanForTraining = totalPWDMalePlanForTraining;
		this.totalSAPlanForTraining = totalSAPlanForTraining;
		this.totalNonSAPlanForTraining = totalNonSAPlanForTraining;
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

	public Long getTotalFemalePlanForTraining() {
		return totalFemalePlanForTraining;
	}

	public void setTotalFemalePlanForTraining(Long totalFemalePlanForTraining) {
		this.totalFemalePlanForTraining = totalFemalePlanForTraining;
	}

	public Long getTotalMalePlanForTraining() {
		return totalMalePlanForTraining;
	}

	public void setTotalMalePlanForTraining(Long totalMalePlanForTraining) {
		this.totalMalePlanForTraining = totalMalePlanForTraining;
	}

	public Long getTotalPWDFemalePlanForTraining() {
		return totalPWDFemalePlanForTraining;
	}

	public void setTotalPWDFemalePlanForTraining(Long totalPWDFemalePlanForTraining) {
		this.totalPWDFemalePlanForTraining = totalPWDFemalePlanForTraining;
	}

	public Long getTotalPWDMalePlanForTraining() {
		return totalPWDMalePlanForTraining;
	}

	public void setTotalPWDMalePlanForTraining(Long totalPWDMalePlanForTraining) {
		this.totalPWDMalePlanForTraining = totalPWDMalePlanForTraining;
	}

	public Long getTotalSAPlanForTraining() {
		return totalSAPlanForTraining;
	}

	public void setTotalSAPlanForTraining(Long totalSAPlanForTraining) {
		this.totalSAPlanForTraining = totalSAPlanForTraining;
	}

	public Long getTotalNonSAPlanForTraining() {
		return totalNonSAPlanForTraining;
	}

	public void setTotalNonSAPlanForTraining(Long totalNonSAPlanForTraining) {
		this.totalNonSAPlanForTraining = totalNonSAPlanForTraining;
	}

	public Long calculateTotal() {
		total = (long) (totalFemalePlanForTraining + totalMalePlanForTraining);
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
