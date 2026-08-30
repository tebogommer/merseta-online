package haj.com.report.bean;

import java.beans.Transient;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import haj.com.framework.IDataEntity;

public class ByChamberReportBean implements IDataEntity {

	private String description;
	
	private String yearIndicator;
	
	private String tradingStatusType;
	
	private Integer iAuto;

	private BigInteger auto;
	private BigDecimal autoChamber;
	private BigInteger metal;
	private BigInteger motor;
	private BigInteger newTyre;
	private BigInteger plastic;
	private BigInteger unknown;
	private BigInteger NA;
	private BigInteger acm;
	private BigInteger total;
	
	private Integer theyear;
	private Integer theMonth;
	
	private Double doubAuto;
	private Double doubMetal;
	private Double doubMotor;
	private Double doubNewTyre;
	private Double doubPlastic;
	private Double doubUnknown;
	private Double doubNA;
	private Double doubAcm;
	private Double doubTotal;
	
	private BigDecimal bdAuto;
	private BigDecimal bdMetal;
	private BigDecimal bdMotor;
	private BigDecimal bdNewTyre;
	private BigDecimal bdPlastic;
	private BigDecimal bdUnknown;
	private BigDecimal bdNA;
	private BigDecimal bdAcm;
	private BigDecimal bdTotal;
	
	
	private Double man;
	private Double dis;
	
	private int id;
	private BigInteger smallCount;
	private BigInteger midCount;
	private BigInteger largeCount;
	
	private BigInteger idPassed;
	
	private BigInteger wspID;
	
	private Date dateGenerated;
	
	
	public ByChamberReportBean() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getAuto() {
		return auto;
	}

	public void setAuto(BigInteger auto) {
		this.auto = auto;
	}

	public BigInteger getMetal() {
		return metal;
	}

	public void setMetal(BigInteger metal) {
		this.metal = metal;
	}

	public BigInteger getMotor() {
		return motor;
	}

	public void setMotor(BigInteger motor) {
		this.motor = motor;
	}

	public BigInteger getNewTyre() {
		return newTyre;
	}

	public void setNewTyre(BigInteger newTyre) {
		this.newTyre = newTyre;
	}

	public BigInteger getPlastic() {
		return plastic;
	}

	public void setPlastic(BigInteger plastic) {
		this.plastic = plastic;
	}

	public BigInteger getUnknown() {
		return unknown;
	}

	public void setUnknown(BigInteger unknown) {
		this.unknown = unknown;
	}

	public BigInteger gettotal() {
		return total;
	}

	public void settotal(BigInteger total) {
		this.total = total;
	}

	public BigInteger getNA() {
		return NA;
	}

	public void setNA(BigInteger nA) {
		NA = nA;
	}

	public BigInteger getAcm() {
		return acm;
	}

	public void setAcm(BigInteger acm) {
		this.acm = acm;
	}
	
	public BigInteger getTotal() {
		return total;
	}
	public Double getDoubAuto() {
		return doubAuto;
	}

	public void setDoubAuto(Double doubAuto) {
		this.doubAuto = doubAuto;
	}

	public Double getDoubMetal() {
		return doubMetal;
	}

	public void setDoubMetal(Double doubMetal) {
		this.doubMetal = doubMetal;
	}

	public Double getDoubMotor() {
		return doubMotor;
	}

	public void setDoubMotor(Double doubMotor) {
		this.doubMotor = doubMotor;
	}

	public Double getDoubNewTyre() {
		return doubNewTyre;
	}

	public void setDoubNewTyre(Double doubNewTyre) {
		this.doubNewTyre = doubNewTyre;
	}

	public Double getDoubPlastic() {
		return doubPlastic;
	}

	public void setDoubPlastic(Double doubPlastic) {
		this.doubPlastic = doubPlastic;
	}

	public Double getDoubUnknown() {
		return doubUnknown;
	}

	public void setDoubUnknown(Double doubUnknown) {
		this.doubUnknown = doubUnknown;
	}

	public Double getDoubNA() {
		return doubNA;
	}

	public void setDoubNA(Double doubNA) {
		this.doubNA = doubNA;
	}

	public Double getDoubAcm() {
		return doubAcm;
	}

	public void setDoubAcm(Double doubAcm) {
		this.doubAcm = doubAcm;
	}

	public Double getDoubTotal() {
		return doubTotal;
	}

	public void setDoubTotal(Double doubTotal) {
		this.doubTotal = doubTotal;
	}

	public Integer getTheyear() {
		return theyear;
	}

	public void setTheyear(Integer theyear) {
		this.theyear = theyear;
	}

	public Double getMan() {
		return man;
	}

	public void setMan(Double man) {
		this.man = man;
	}

	public Double getDis() {
		return dis;
	}

	public void setDis(Double dis) {
		this.dis = dis;
	}

	public BigInteger getSmallCount() {
		return smallCount;
	}

	public void setSmallCount(BigInteger smallCount) {
		this.smallCount = smallCount;
	}

	public BigInteger getMidCount() {
		return midCount;
	}

	public void setMidCount(BigInteger midCount) {
		this.midCount = midCount;
	}

	public BigInteger getLargeCount() {
		return largeCount;
	}

	public void setLargeCount(BigInteger largeCount) {
		this.largeCount = largeCount;
	}


	public BigDecimal getBdAuto() {
		return bdAuto;
	}

	public void setBdAuto(BigDecimal bdAuto) {
		this.bdAuto = bdAuto;
	}

	public BigDecimal getBdMetal() {
		return bdMetal;
	}

	public void setBdMetal(BigDecimal bdMetal) {
		this.bdMetal = bdMetal;
	}

	public BigDecimal getBdMotor() {
		return bdMotor;
	}

	public void setBdMotor(BigDecimal bdMotor) {
		this.bdMotor = bdMotor;
	}

	public BigDecimal getBdNewTyre() {
		return bdNewTyre;
	}

	public void setBdNewTyre(BigDecimal bdNewTyre) {
		this.bdNewTyre = bdNewTyre;
	}

	public BigDecimal getBdPlastic() {
		return bdPlastic;
	}

	public void setBdPlastic(BigDecimal bdPlastic) {
		this.bdPlastic = bdPlastic;
	}

	public BigDecimal getBdUnknown() {
		return bdUnknown;
	}

	public void setBdUnknown(BigDecimal bdUnknown) {
		this.bdUnknown = bdUnknown;
	}

	public BigDecimal getBdNA() {
		return bdNA;
	}

	public void setBdNA(BigDecimal bdNA) {
		this.bdNA = bdNA;
	}

	public BigDecimal getBdAcm() {
		return bdAcm;
	}

	public void setBdAcm(BigDecimal bdAcm) {
		this.bdAcm = bdAcm;
	}

	public BigDecimal getBdTotal() {
		return bdTotal;
	}

	public void setBdTotal(BigDecimal bdTotal) {
		this.bdTotal = bdTotal;
	}
	
	@Transient
	public String getTotalValid() {
		
		return total != null ? total.toString() : doubTotal != null ? doubTotal.toString() : bdTotal.toString();
	}
	
	@Transient
	public String getAutoValid() {
		return auto != null ? auto.toString() : doubAuto != null ? Long.toString(Math.round(doubAuto)) : (bdAuto.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString();
	}
	
	@Transient
	public String getMetalValid() {
		return metal != null ? metal.toString() : doubMetal != null ? Long.toString(Math.round(doubMetal)) : (bdMetal.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString(); 
	}
	
	@Transient
	public String getMotorValid() {
		return motor != null ? motor.toString() : doubMotor != null ? Long.toString(Math.round(doubMotor)) : (bdMotor.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString();
	}
	
	@Transient
	public String getNewTyreValid() {
		return newTyre != null ? newTyre.toString() : doubNewTyre != null ? Long.toString(Math.round(doubNewTyre)) : (bdNewTyre.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString();
	}
	
	
	@Transient
	public String getPlasticValid() {
		return plastic != null ? plastic.toString() : doubPlastic != null ? Long.toString(Math.round(doubPlastic)) : (bdPlastic.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString();
	}
	
	@Transient
	public String getUnknownValid() {
		return unknown != null ? unknown.toString() : doubUnknown != null ? Long.toString(Math.round(doubUnknown)) : (bdUnknown.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString();
	}

	@Transient
	public String getAcmValid() {
		return acm != null ? acm.toString() : doubAcm != null ? Long.toString(Math.round(doubAcm)) : (bdAcm.setScale(2, BigDecimal.ROUND_HALF_EVEN)).toString();
	}

	public String getYearIndicator() {
		return yearIndicator;
	}

	public void setYearIndicator(String yearIndicator) {
		this.yearIndicator = yearIndicator;
	}

	public BigDecimal getAutoChamber() {
		return autoChamber;
	}

	public void setAutoChamber(BigDecimal autoChamber) {
		this.autoChamber = autoChamber;
	}

	public BigInteger getIdPassed() {
		return idPassed;
	}

	public void setIdPassed(BigInteger idPassed) {
		this.idPassed = idPassed;
	}

	public BigInteger getWspID() {
		return wspID;
	}

	public void setWspID(BigInteger wspID) {
		this.wspID = wspID;
	}

	public String getTradingStatusType() {
		return tradingStatusType;
	}

	public void setTradingStatusType(String tradingStatusType) {
		this.tradingStatusType = tradingStatusType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public Integer getTheMonth() {
		return theMonth;
	}

	public void setTheMonth(Integer theMonth) {
		this.theMonth = theMonth;
	}

	public Integer getiAuto() {
		return iAuto;
	}

	public void setiAuto(Integer iAuto) {
		this.iAuto = iAuto;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}


	
	
	
}
