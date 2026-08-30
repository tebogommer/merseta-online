package haj.com.report.bean;

import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

public class TradesAllocation implements IDataEntity {

	private static final long serialVersionUID = 1L;
	private OfoCodes ofoCodes;
	private InterventionType interventionType;
	private long number;
	private Qualification qualification;

	public TradesAllocation(OfoCodes ofoCodes, InterventionType interventionType, long number, Qualification qualification) {
		super();
		this.ofoCodes = ofoCodes;
		this.interventionType = interventionType;
		this.number = number;
		this.qualification = qualification;
	}

	public TradesAllocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

}
