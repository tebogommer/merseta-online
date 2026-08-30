package haj.com.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import haj.com.entity.datatakeon.TS2;
import haj.com.sars.SarsLevyDetails;

public class ReconSchemeYears implements Serializable {

	private Integer year;
	private Date beforeDate;

	private SarsLevyDetails sarsLevyDetails;
	private TS2 ts2;
	private Double difference;
	private List<ReconSchemeYears> reconSchemeYears;
	private byte[] data;
	private Double transferIn;
	private Double transferOut;

	public ReconSchemeYears() {
	}



	public ReconSchemeYears(Integer year) {
		super();
		this.year = year;
	}



	public ReconSchemeYears(Integer year, Date beforeDate) {
		super();
		this.year = year;
		this.beforeDate = beforeDate;
	}



	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getBeforeDate() {
		return beforeDate;
	}

	public void setBeforeDate(Date beforeDate) {
		this.beforeDate = beforeDate;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReconSchemeYears other = (ReconSchemeYears) obj;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}



	public SarsLevyDetails getSarsLevyDetails() {
		return sarsLevyDetails;
	}



	public void setSarsLevyDetails(SarsLevyDetails sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}



	public TS2 getTs2() {
		return ts2;
	}



	public void setTs2(TS2 ts2) {
		this.ts2 = ts2;
	}



	public Double getDifference() {
		return difference;
	}



	public void setDifference(Double difference) {
		this.difference = difference;
	}



	public List<ReconSchemeYears> getReconSchemeYears() {
		return reconSchemeYears;
	}



	public void setReconSchemeYears(List<ReconSchemeYears> reconSchemeYears) {
		this.reconSchemeYears = reconSchemeYears;
	}



	public byte[] getData() {
		return data;
	}



	public void setData(byte[] data) {
		this.data = data;
	}



	public Double getTransferIn() {
		return transferIn;
	}



	public void setTransferIn(Double transferIn) {
		this.transferIn = transferIn;
	}



	public Double getTransferOut() {
		return transferOut;
	}



	public void setTransferOut(Double transferOut) {
		this.transferOut = transferOut;
	}


}
