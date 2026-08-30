package haj.com.dao;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Wsp;
import haj.com.entity.enums.AllocationStatusEnum;
import haj.com.entity.lookup.DGYear;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DgAllocationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgAllocation
	 * 
	 * @author TechFinium
	 * @see DgAllocation
	 * @return a list of {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocation() throws Exception {
		return (List<DgAllocation>) super.getList("select o from DgAllocation o order by o.balanceRemaining desc");
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocationNotStatus() throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.status is null and o.mandatoryGrant.wsp.finYear is not null order by o.id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocationNotStatusAdditional() throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.status is null and o.mandatoryGrant.wsp.finYear is null order by o.id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocationNotStatusAdditional(DGYear dgYear) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.status is null and o.mandatoryGrant.wsp.dgYear.id = :dgYearID order by o.id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgYearID", dgYear.getId());
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public DGYear findAllocationAmount(int year) throws Exception {
		String hql = "select o from DGYear o where o.description = :year";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("year", "DG" + ("" + year).substring(2, 4));
		DGYear dgYear = null;
		List<DGYear> dgYears = (List<DGYear>) super.getList(hql, parameters, 1);
		if (dgYears.size() > 0) dgYear = dgYears.get(0);
		return dgYear;
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParentNotStatus(DgAllocationParent allocationParent, AllocationStatusEnum allocationStatusEnum) throws Exception {
		String hql = "select o from DgAllocationParent o where o.dontAllocate = :dontAllocate and (o.allocationStatusEnum is null or o.allocationStatusEnum <> :status) and o.id > :dgAllocationParentID and o.status is null and o.wsp is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", allocationStatusEnum);
		parameters.put("dontAllocate", false);
		parameters.put("dgAllocationParentID", allocationParent.getId());
		return (List<DgAllocationParent>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParentStatus(AllocationStatusEnum allocationStatusEnum) throws Exception {
		String hql = "select o from DgAllocationParent o where o.allocationStatusEnum = :status";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", allocationStatusEnum);
		return (List<DgAllocationParent>) super.getList(hql, parameters);
	}

	/**
	 * Get all DgAllocation between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see DgAllocation
	 * @return a list of {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocation(Long from, int noRows) throws Exception {
		String hql = "select o from DgAllocation o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<DgAllocation>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see DgAllocation
	 * @return a {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 *             global exception
	 */
	public DgAllocation findByKey(Long id) throws Exception {
		String hql = "select o from DgAllocation o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DgAllocation) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgAllocation by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DgAllocation
	 * @return a list of {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocation> findByName(String description) throws Exception {
		String hql = "select o from DgAllocation o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> findByParent(DgAllocationParent wsp) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.id = :id  order by o.id asc";
//		String hql = "select o from DgAllocation o where o.dgAllocationParent.id = :id  order by o.mandatoryGrant.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", wsp.getId());
		return (List<DgAllocation>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgAllocation> findByParentWhereAmountAwarded(DgAllocationParent wsp) throws Exception {
		/*BigDecimal maxAwaded=new BigDecimal(0.00);
		AllocationStatusEnum allocationStatusEnum=AllocationStatusEnum.Approved;
		String hql = "select o from DgAllocation o where o.dgAllocationParent.id = :id and o.totalAwardAmount > :maxAwaded and o.allocationStatusEnum = :allocationStatusEnum order by o.mandatoryGrant.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", wsp.getId());
		parameters.put("maxAwaded",maxAwaded);
		parameters.put("allocationStatusEnum",allocationStatusEnum);
		return (List<DgAllocation>) super.getList(hql, parameters);*/
		
		BigDecimal maxAwaded=new BigDecimal(0.00);
		String hql = "select o from DgAllocation o where o.dgAllocationParent.id = :id and o.totalAwardAmount > :maxAwaded order by o.mandatoryGrant.interventionType.prioritisationScale asc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", wsp.getId());
		parameters.put("maxAwaded",maxAwaded);
		return (List<DgAllocation>) super.getList(hql, parameters);
	}
	
	public BigDecimal findTotalAllocatedWhereAmountAwarded(DgAllocationParent da) throws Exception {
		BigDecimal maxAwaded=new BigDecimal(0.00);
		String hql = "select sum(o.totalAwardAmount) from DgAllocation o where o.dgAllocationParent.id = :id and o.totalAwardAmount > :maxAwaded order by o.mandatoryGrant.interventionType.prioritisationScale asc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", da.getId());
		parameters.put("maxAwaded",maxAwaded);
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> findByParentNotStatus(DgAllocationParent wsp, AllocationStatusEnum allocationStatusEnum) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.id = :id and (o.allocationStatusEnum is null or o.allocationStatusEnum <> :allocationStatusEnum) order by o.id ,o.mandatoryGrant.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", wsp.getId());
		parameters.put("allocationStatusEnum", allocationStatusEnum);
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> findBySuperParent(DgAllocationParent wsp) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.allocationParent.id = :id  order by o.mandatoryGrant.wsp.id,o.id asc";
		// String hql = "select o from DgAllocation o where o.dgAllocationParent.allocationParent.id = :id  order by o.mandatoryGrant.wsp.id,o.mandatoryGrant.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", wsp.getId());
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	public BigDecimal findTotalAllocated(int year, DgAllocationParent da) throws Exception {
		String hql = "select sum(o.totalAwardAmount) from DgAllocation o where o.mandatoryGrant.wsp.finYear = :year ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (da != null && da.getId() != null) {
			hql += "and o.dgAllocationParent.id < :id";
			parameters.put("id", da.getId());
		}
		parameters.put("year", year);
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}


	public BigDecimal findTotalAllocated(DGYear year, DgAllocationParent da) throws Exception {
 		String hql = "select sum(COALESCE(o.totalAwardAmount)) from DgAllocation o where o.mandatoryGrant.wsp.dgYear.id = :year ";
//		String hql = "select sum(o.totalAwardAmount) from DgAllocation o where o.mandatoryGrant.wsp.dgYear.id = :year ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (da != null && da.getId() != null) {
			hql += "and dgAllocationParent.id < :id ";
			parameters.put("id", da.getId());
		}
		parameters.put("year", year.getId());
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}

	public BigDecimal findTotalRequested(int year) throws Exception {
		String hql = "select sum(o.requestedAmount) from DgAllocation o where o.mandatoryGrant.wsp.finYear = :year ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("year", year);
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgAllocation> findByParentWsp(Long wspId) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.wsp.id = :wspId order by o.id ,o.mandatoryGrant.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<DgAllocation>) super.getList(hql, parameters);
	}
	
	public int countByParentWsp(Long wspId) throws Exception {
		String hql = "select count(o) from DgAllocation o where o.dgAllocationParent.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<DgAllocation> findByParentWspWithStatus(Long wspId) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.wsp.id = :wspId and o.dgAllocationParent.status is not null order by o.id ,o.mandatoryGrant.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<DgAllocation>) super.getList(hql, parameters);
	}
	
	public int countByParentWspWithStatus(Long wspId) throws Exception {
		String hql = "select count(o) from DgAllocation o where o.dgAllocationParent.wsp.id = :wspId and o.dgAllocationParent.status is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	

	
	public BigDecimal findTotalRequested(DGYear year) throws Exception {
		String hql = "select sum(o.requestedAmount) from DgAllocation o where o.mandatoryGrant.wsp.dgYear.id = :year ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("year", year.getId());
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}
}
