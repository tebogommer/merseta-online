package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;

import haj.com.entity.Company;
import haj.com.entity.lookup.SICCode;
import haj.com.sars.SarsEmployerDetail;

public class SarsLevyStandardDeviationDataBean implements Serializable {
	
	
	private String description;
	private String additionalInformation;
	
	private Double monthOne;
	private Double monthTwo;
	private Double monthThree;
	private Double monthFour;
	private Double monthFive;
	private Double monthSix;
	private Double monthSeven;
	private Double monthEight;
	private Double monthNine;
	private Double monthTen;
	private Double monthEleven;
	private Double monthTweleve;
	
	private Double total;
	private Double average;
	private Double standardDeviation;
	private Long standardPercentage;
	private String status;
	
	/* used as the count for lazy load */
	private BigInteger counter;
	
	public SarsLevyStandardDeviationDataBean() {
		super();
	}
	
	public SarsLevyStandardDeviationDataBean(String description, Double monthOne, Double monthTwo, Double monthThree,
			Double monthFour, Double monthFive, Double monthSix, Double monthSeven, Double monthEight, Double monthNine,
			Double monthTen, Double monthEleven, Double monthTweleve) {
		super();
		this.description = description;
		this.monthOne = monthOne;
		this.monthTwo = monthTwo;
		this.monthThree = monthThree;
		this.monthFour = monthFour;
		this.monthFive = monthFive;
		this.monthSix = monthSix;
		this.monthSeven = monthSeven;
		this.monthEight = monthEight;
		this.monthNine = monthNine;
		this.monthTen = monthTen;
		this.monthEleven = monthEleven;
		this.monthTweleve = monthTweleve;
	}
	
	public SarsLevyStandardDeviationDataBean(BigInteger counter) {
		super();
		this.counter = counter;
	}

	public Double getGrantTotal(){
		return monthOne + monthTwo + monthThree + monthFour + monthFive + monthSix + monthSeven + monthEight + monthNine + monthTen + monthEleven + monthTweleve;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Double getMonthOne() {
		return monthOne;
	}

	public void setMonthOne(Double monthOne) {
		this.monthOne = monthOne;
	}

	public Double getMonthTwo() {
		return monthTwo;
	}

	public void setMonthTwo(Double monthTwo) {
		this.monthTwo = monthTwo;
	}

	public Double getMonthThree() {
		return monthThree;
	}

	public void setMonthThree(Double monthThree) {
		this.monthThree = monthThree;
	}

	public Double getMonthFour() {
		return monthFour;
	}

	public void setMonthFour(Double monthFour) {
		this.monthFour = monthFour;
	}

	public Double getMonthFive() {
		return monthFive;
	}

	public void setMonthFive(Double monthFive) {
		this.monthFive = monthFive;
	}

	public Double getMonthSix() {
		return monthSix;
	}

	public void setMonthSix(Double monthSix) {
		this.monthSix = monthSix;
	}

	public Double getMonthSeven() {
		return monthSeven;
	}

	public void setMonthSeven(Double monthSeven) {
		this.monthSeven = monthSeven;
	}

	public Double getMonthEight() {
		return monthEight;
	}

	public void setMonthEight(Double monthEight) {
		this.monthEight = monthEight;
	}

	public Double getMonthNine() {
		return monthNine;
	}

	public void setMonthNine(Double monthNine) {
		this.monthNine = monthNine;
	}

	public Double getMonthTen() {
		return monthTen;
	}

	public void setMonthTen(Double monthTen) {
		this.monthTen = monthTen;
	}

	public Double getMonthEleven() {
		return monthEleven;
	}

	public void setMonthEleven(Double monthEleven) {
		this.monthEleven = monthEleven;
	}

	public Double getMonthTweleve() {
		return monthTweleve;
	}

	public void setMonthTweleve(Double monthTweleve) {
		this.monthTweleve = monthTweleve;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public Long getStandardPercentage() {
		return standardPercentage;
	}

	public void setStandardPercentage(Long standardPercentage) {
		this.standardPercentage = standardPercentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigInteger getCounter() {
		return counter;
	}
	
	public void setCounter(BigInteger counter) {
		this.counter = counter;
	}
}