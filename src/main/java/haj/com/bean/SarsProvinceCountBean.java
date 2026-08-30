
package haj.com.bean;

import java.math.BigInteger;

/**
 * The Class DiscretionaryGrantBean.
 */
public class SarsProvinceCountBean {

	private String description;
	
	private BigInteger gauteng;
	private BigInteger easternCape;
	private BigInteger freeState;
	private BigInteger kwazulunatal;
	private BigInteger limpopo;
	private BigInteger mpumalanga;
	private BigInteger northernCape;
	private BigInteger northWest;
	private BigInteger westernCape;
	private BigInteger saNational;
	private BigInteger outsideSA;
	
	private BigInteger total;
	
	private Boolean totalEntry;
	

	public SarsProvinceCountBean() {
		super();
	}
	
	public SarsProvinceCountBean(String description) {
		super();
		this.description = description;
		this.outsideSA = this.saNational = this.westernCape = this.northWest = this.northernCape = BigInteger.ZERO;
		this.mpumalanga = this.limpopo = this.kwazulunatal = this.freeState = this.easternCape = this.gauteng = this.total = BigInteger.ZERO;
		totalEntry = false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getGauteng() {
		return gauteng;
	}

	public void setGauteng(BigInteger gauteng) {
		this.gauteng = gauteng;
	}

	public BigInteger getEasternCape() {
		return easternCape;
	}

	public void setEasternCape(BigInteger easternCape) {
		this.easternCape = easternCape;
	}

	public BigInteger getFreeState() {
		return freeState;
	}

	public void setFreeState(BigInteger freeState) {
		this.freeState = freeState;
	}

	public BigInteger getKwazulunatal() {
		return kwazulunatal;
	}

	public void setKwazulunatal(BigInteger kwazulunatal) {
		this.kwazulunatal = kwazulunatal;
	}

	public BigInteger getLimpopo() {
		return limpopo;
	}

	public void setLimpopo(BigInteger limpopo) {
		this.limpopo = limpopo;
	}

	public BigInteger getMpumalanga() {
		return mpumalanga;
	}

	public void setMpumalanga(BigInteger mpumalanga) {
		this.mpumalanga = mpumalanga;
	}

	public BigInteger getNorthernCape() {
		return northernCape;
	}

	public void setNorthernCape(BigInteger northernCape) {
		this.northernCape = northernCape;
	}

	public BigInteger getNorthWest() {
		return northWest;
	}

	public void setNorthWest(BigInteger northWest) {
		this.northWest = northWest;
	}

	public BigInteger getWesternCape() {
		return westernCape;
	}

	public void setWesternCape(BigInteger westernCape) {
		this.westernCape = westernCape;
	}

	public BigInteger getSaNational() {
		return saNational;
	}

	public void setSaNational(BigInteger saNational) {
		this.saNational = saNational;
	}

	public BigInteger getOutsideSA() {
		return outsideSA;
	}

	public void setOutsideSA(BigInteger outsideSA) {
		this.outsideSA = outsideSA;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public Boolean getTotalEntry() {
		return totalEntry;
	}

	public void setTotalEntry(Boolean totalEntry) {
		this.totalEntry = totalEntry;
	}

}