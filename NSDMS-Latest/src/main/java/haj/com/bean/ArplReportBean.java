package haj.com.bean;

import java.util.Date;

import haj.com.entity.enums.AprlProgressReportingEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.TradeTestProgressReportingEnum;
import haj.com.framework.IDataEntity;

public class ArplReportBean implements IDataEntity {

	private AprlProgressReportingEnum aprlProgressReportingEnum;
	private TradeTestProgressReportingEnum tradeTestProgressReportingEnum;
	private Date dateGenerated;
	private CollectionEnum collectionEnum;
	private Integer count;

	public ArplReportBean() {
		super();
	}

	public AprlProgressReportingEnum getAprlProgressReportingEnum() {
		return aprlProgressReportingEnum;
	}

	public void setAprlProgressReportingEnum(AprlProgressReportingEnum aprlProgressReportingEnum) {
		this.aprlProgressReportingEnum = aprlProgressReportingEnum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public CollectionEnum getCollectionEnum() {
		return collectionEnum;
	}

	public void setCollectionEnum(CollectionEnum collectionEnum) {
		this.collectionEnum = collectionEnum;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public TradeTestProgressReportingEnum getTradeTestProgressReportingEnum() {
		return tradeTestProgressReportingEnum;
	}

	public void setTradeTestProgressReportingEnum(TradeTestProgressReportingEnum tradeTestProgressReportingEnum) {
		this.tradeTestProgressReportingEnum = tradeTestProgressReportingEnum;
	}
	
}
