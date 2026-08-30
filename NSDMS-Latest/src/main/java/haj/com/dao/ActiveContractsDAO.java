package haj.com.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.SortOrder;

import haj.com.bean.MoaStatusReportBean;
import haj.com.bean.PipTaskTrackerReportBean;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ActiveContractsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ActiveContracts
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 * @return a list of {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContracts() throws Exception {
		return (List<ActiveContracts>) super.getList("select o from ActiveContracts o");
	}

	/**
	 * Get all ActiveContracts between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see ActiveContracts
	 * @return a list of {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContracts(Long from, int noRows) throws Exception {
		String hql = "select o from ActiveContracts o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<ActiveContracts>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see ActiveContracts
	 * @return a {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 *             global exception
	 */
	public ActiveContracts findByKey(Long id) throws Exception {
		String hql = "select o from ActiveContracts o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (ActiveContracts) super.getUniqueResult(hql, parameters);
	}
	
	public ActiveContracts findByDgAllocationParent(Long id) throws Exception {
		String hql = "select o from ActiveContracts o where o.dgAllocationParent.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (ActiveContracts) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findActiveContractsByDgAllocationParent(Long id) throws Exception {
		String hql = "select o from ActiveContracts o where o.dgAllocationParent.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<ActiveContracts>) super.getList(hql, parameters);
	}
	
	
	public ActiveContracts findByWSP(Long id) throws Exception {
		String hql = "select o from ActiveContracts o where o.dgAllocationParent.wsp.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (ActiveContracts) super.getUniqueResult(hql, parameters);
	}

	
	
	public ActiveContracts findByWSP(Wsp wsp) throws Exception {
		String hql = "select o from ActiveContracts o where o.wsp.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", wsp.getId());
		return (ActiveContracts) super.getUniqueResult(hql, parameters);
	}
	/**
	 * Find ActiveContracts by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see ActiveContracts
	 * @return a list of {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findByName(String description) throws Exception {
		String hql = "select o from ActiveContracts o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<ActiveContracts>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findByCompany(Long companyID) throws Exception {
		String hql = "select o from ActiveContracts o where o.company.id = :companyID or o.dgAllocationParent.wsp.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (List<ActiveContracts>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findAllEletronicSignoff(Boolean eletronicSignoff) throws Exception {
		String hql = "select o from ActiveContracts o where o.eletronicSignoff = :eletronicSignoff";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("eletronicSignoff", eletronicSignoff);
		return (List<ActiveContracts>) super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> findActiveContractDetail(Long activeContractsID) throws Exception {
		String hql = "select o from ActiveContractDetail o where o.activeContracts.id = :activeContractsID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("activeContractsID", activeContractsID);
		return (List<ActiveContractDetail>) super.getList(hql, parameters);
	}

	public BigDecimal findTotalContractValue(DgAllocationParent dgAllocationParent) throws Exception {
		String hql = "select sum(o.totalAwardAmount) from DgAllocation o where o.dgAllocationParent.id = :dgAllocationParentID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgAllocationParentID", dgAllocationParent.getId());
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}

	public java.lang.Long findTotalLearners(DgAllocationParent dgAllocationParent) throws Exception {
		String hql = "select (sum(o.maxPossibleLearners) + sum(o.coFundingLearnersAwarded)) from DgAllocation o where o.dgAllocationParent.id = :dgAllocationParentID and (o.totalAwardAmount > 0)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgAllocationParentID", dgAllocationParent.getId());
		return (java.lang.Long) super.getUniqueResult(hql, parameters);
	}
	
	public int findTotalLearnersIntValue(DgAllocationParent dgAllocationParent) throws Exception {
		String hql = "select (sum(o.maxPossibleLearners) + sum(o.coFundingLearnersAwarded)) from DgAllocation o where o.dgAllocationParent.id = :dgAllocationParentID and (o.totalAwardAmount > 0)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgAllocationParentID", dgAllocationParent.getId());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countDgContractsAwaitingProcessing() throws Exception {
		String hql = "select count(o) from ActiveContracts o where o.signOffState = :signOffStateValue and o.awaitingBatchSignOff = :awaitingBatchSignOff and o.id not in (select distinct(x.activeContracts.id) from DgContractingBulkItems x where x.statusAssigned is null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("signOffStateValue", true);
		parameters.put("awaitingBatchSignOff", true);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findByEletronicSignoffNotSubmittedAndNotOpen(Boolean eletronicSignOff, Boolean submitted) throws Exception {
		String hql = "select o from ActiveContracts o where o.eletronicSignoff = :eletronicSignOff and o.submitted = :submitted and o.extensionTerminationWorkflowActive = :extensionterminationUnderway and o.status not in (:approvalEnumWithdrawn, :approvalEnumApproved, :approvalEnumProjectTerminated)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("eletronicSignOff", eletronicSignOff); // true
		parameters.put("submitted", submitted); // false
		parameters.put("approvalEnumWithdrawn", ApprovalEnum.Withdrawn); 
		parameters.put("approvalEnumApproved", ApprovalEnum.Approved); 
		parameters.put("approvalEnumProjectTerminated", ApprovalEnum.ProjectTerminated); 
		parameters.put("extensionterminationUnderway", false);
		return (List<ActiveContracts>) super.getList(hql, parameters);
	}
	
	/**
	 * Sort and filter.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return the list
	 */
	protected final Log logger = LogFactory.getLog(this.getClass());
	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,String hql) {
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName") || entry.getKey().contains("levyNumber")) {
						hql += " and o.dgAllocationParent.wsp." + entry.getKey() + " like " + " :" + hvar;
					}
					else if(entry.getKey().equals("description"))//For region
					{
						hql += " and o.dgAllocationParent.wsp.company.regionTown.region.description." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}

		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
					if (sortField.contains("companyName") || sortField.contains("tradingName") ||sortField.contains("levyNumber")) {
						hql += " order by o.dgAllocationParent.wsp." + sortField + " asc ";
					}
					else if(sortField.equals("description"))//For region
					{
						hql += " order by o.dgAllocationParent.wsp.company.regionTown.region.description." +  sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.contains("companyName") || sortField.contains("tradingName") ||sortField.contains("levyNumber")) {
						hql += " order by o.dgAllocationParent.wsp." + sortField + " desc ";
					}
					else if(sortField.contains("description"))//For region
					{
						hql += " order by o.dgAllocationParent.wsp.company.regionTown.region.description." +  sortField + " desc ";
					}
					else{
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}
		
		//logger.fatal("HQL: "+hql);
		return getList(hql, filters, startingAt, pageSize);
	
	}
	
	public List<?> sortAndFilterDGStatusSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,String hql) {
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName") || entry.getKey().contains("levyNumber")) {
						hql += " and o.dgAllocationParent.wsp." + entry.getKey() + " like " + " :" + hvar;
					}
					else if(entry.getKey().equals("description"))//For region
					{
						hql += " and o.dgAllocationParent.wsp.company.regionTown.region.description." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}

		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
					if (sortField.contains("companyName") || sortField.contains("tradingName") ||sortField.contains("levyNumber")) {
						hql += " order by o.odgAllocationParent.wsp." + sortField + " asc ";
					}
					else if(sortField.equals("description"))//For region
					{
						hql += " order by o.dgAllocationParent.wsp.company.regionTown.region.description." +  sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.contains("companyName") || sortField.contains("tradingName") ||sortField.contains("levyNumber")) {
						hql += " order by o.dgAllocationParent.wsp." + sortField + " desc ";
					}
					else if(sortField.contains("description"))//For region
					{
						hql += " order by o.dgAllocationParent.wsp.company.regionTown.region.description." +  sortField + " desc ";
					}
					else{
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}
		
		//logger.fatal("HQL: "+hql);
		return getList(hql, filters, startingAt, pageSize);
	
	}
	
	/**
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @param filters
	 *            the filters
	 * @return the int
	 */
	public int countSearch(Map<String, Object> filters,String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName") || entry.getKey().contains("levyNumber")) {
						hql += " and o.dgAllocationParent.wsp." + entry.getKey() + " like " + " :" + hvar;
					}
					else if(entry.getKey().contains("description"))//For region
					{
						hql += " and o.dgAllocationParent.wsp.company.regionTown.region.description." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		//logger.fatal("HQL: "+hql);
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public int countDGStatusSearch(Map<String, Object> filters,String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName") || entry.getKey().contains("levyNumber")) {
						hql += " and o.dgAllocationParent.wsp." + entry.getKey() + " like " + " :" + hvar;
					}
					else if(entry.getKey().contains("description"))//For region
					{
						hql += " and o.dgAllocationParent.wsp.company.regionTown.region.description." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		//logger.fatal("HQL: "+hql);
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterSearchActiveContracts(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName") || entry.getKey().contains("levyNumber")) {
						
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					else if(entry.getKey().equals("description"))//For region
					{
						hql += " and o.dgAllocationParent.wsp.company.regionTown.region.description." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
					if (sortField.contains("companyName") || sortField.contains("tradingName") ||sortField.contains("levyNumber")) {
						
						hql += " order by o." + sortField + " asc ";
					}
					else if(sortField.equals("description"))//For region
					{
						hql += " order by o.dgAllocationParent.wsp.company.regionTown.region.description." +  sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.contains("companyName") || sortField.contains("tradingName") ||sortField.contains("levyNumber")) {
						// dgAllocationParent.wsp.company.levyNumber
						hql += " order by o. " + sortField + " desc ";
					}
					else if(sortField.contains("description"))//For region
					{
						hql += " order by o.dgAllocationParent.wsp.company.regionTown.region.description." +  sortField + " desc ";
					}
					else{
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @param filters
	 *            the filters
	 * @return the int
	 */
	public int countSearchActiveContracts(Map<String, Object> filters,String hql) {
		if (filters != null) {
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName") || entry.getKey().contains("levyNumber")) {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					} else if(entry.getKey().contains("description")){
						hql += " and o.dgAllocationParent.wsp.company.regionTown.region.description." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public int countByCloCrmActiveContractsByMoaTypeAndGrantYear(Long userID, MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select count(o) from ActiveContracts o where "
				+ " o.moaType = :moaType and "
				+ " o.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.dgAllocationParent.wsp.company.nonSetaCompany = false and "
				+ " o.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userID);
		parameters.put("moaType", moaType);
		parameters.put("finYear", finYear);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countActiveContractsReportByMoaTypeAndGrantYear(MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select count(o) from ActiveContracts o where "
				+ " o.moaType = :moaType and "
				+ " o.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.dgAllocationParent.wsp.company.nonSetaCompany = false";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("moaType", moaType);
		parameters.put("finYear", finYear);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<MoaStatusReportBean> populateMoaStatusReportByGrantYear(Integer grantYear) throws Exception {
		String sql = "select  " + 
				"	c.levy_number as entityId  " + 
				"	, c.company_name as companyName  " + 
				"	, cham.description as chamber  " + 
				"	, r.description as region  " + 
				"	, CONCAT(c.levy_number, 'DGYR',w.fin_year) as referanceNumber  " + 
				"	, ac.contract_value as contractValue  " + 
				"	, case  " + 
				"		when ac.moa_type = 'DGMOA' then 'DG MOA'  " + 
				"		when ac.moa_type = 'SpecialProject' then 'Special Project'  " + 
				"		else ''  " + 
				"	end as moaType  " + 
				"	, dap.acceptance_date as acceptanceDate  " + 
				"	, case  " + 
				"		when dap.acceptance_date is not null then ADDDATE(dap.acceptance_date, 30)  " + 
				"		else null  " + 
				"	end as dueDate  " + 
				"	, dap.approval_date as allocationApprovalDate  " + 
				"	, case  " + 
				"		when ac.status = '0' then 'Approved'  " + 
				"		when ac.status = '1' then 'Rejected'  " + 
				"		when ac.status = '2' then 'Pending Manager Approval'  " + 
				"		when ac.status = '3' then 'Pending Approval'		  " + 
				"		when ac.status = '9' then 'Withdrawn'		  " + 
				"		when ac.status = '23' then 'Project Terminated Manager Approval'  " + 
				"		when ac.status = '24' then 'Suspend Project'		  " + 
				"		when ac.status = '25' then 'Project Terminated'  " + 
				"		else 'Unable to locate status'  " + 
				"	end as moaStatus  " + 
				"from   " + 
				"	active_contracts ac  " + 
				"left join dg_allocation_parent dap on dap.id = ac.dg_allocation_parent_id  " + 
				"left join wsp w on w.id = dap.wsp_id  " + 
				"left join company c on c.id = w.company_id  " + 
				"left join sic_code sc on sc.id = c.sic_code_id  " + 
				"left join chamber cham on cham.id = sc.chamber_id  " + 
				"left join address resAdd on resAdd.id = c.residential_address_id  " + 
				"left join region_town rt on rt.town_id = resAdd.town_id  " + 
				"left join region r on r.id = rt.region_id  " + 
				"where   " + 
				"	ac.moa_type = 'DGMOA'  " + 
				"	and w.fin_year = " + grantYear.toString();
		return (List<MoaStatusReportBean>) super.nativeSelectSqlList(sql, MoaStatusReportBean.class);
	}
	
	public List<PipTaskTrackerReportBean> populatePipTaskTrackerReportByGrantYear(Integer grantYear) throws Exception {
		String sql = "select   " + 
				"	c.levy_number as entityId   " + 
				"	, c.company_name as companyName   " + 
				"	, cham.description as chamber   " + 
				"	, r.description as region   " + 
				"	, CONCAT(c.levy_number, 'DGYR',w.fin_year) as referanceNumber   " + 
				"	, case   " + 
				"		when ac.moa_type = 'DGMOA' then 'DG MOA'   " + 
				"		when ac.moa_type = 'SpecialProject' then 'Special Project'   " + 
				"		else ''   " + 
				"	end as moaType   " + 
				"	, ac.contract_value as contractValue   " + 
				"	, dap.approval_date as approvalDate   " + 
				"	, dap.acceptance_date as acceptanceDate   " + 
				"	, case   " + 
				"		when sdf.id is not null then CONCAT(sdf.first_name, ' ', sdf.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as sdfFullName   " + 
				"	, case   " + 
				"		when sdf.id is not null then ac.sdf_sign_date   " + 
				"		else null   " + 
				"	end as sdfSignDate	   " + 
				"	, case   " + 
				"		when clo.id is not null then CONCAT(clo.first_name, ' ', clo.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as cloFullName   " + 
				"	, case   " + 
				"		when clo.id is not null then ac.clo_sign_date   " + 
				"		else null   " + 
				"	end as cloSignDate   " + 
				"	, case   " + 
				"		when crm.id is not null then CONCAT(crm.first_name, ' ', crm.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as crmFullName   " + 
				"	, case   " + 
				"		when crm.id is not null then ac.crm_sign_date   " + 
				"		else null   " + 
				"	end as crmSignDate   " + 
				"from    " + 
				"	active_contracts ac   " + 
				"left join dg_allocation_parent dap on dap.id = ac.dg_allocation_parent_id   " + 
				"left join wsp w on w.id = dap.wsp_id   " + 
				"left join company c on c.id = w.company_id   " + 
				"left join sic_code sc on sc.id = c.sic_code_id   " + 
				"left join chamber cham on cham.id = sc.chamber_id   " + 
				"left join address resAdd on resAdd.id = c.residential_address_id   " + 
				"left join region_town rt on rt.town_id = resAdd.town_id   " + 
				"left join region r on r.id = rt.region_id   " + 
				"   " + 
				"left join users sdf on sdf.id = ac.sdf_id   " + 
				"left join users clo on clo.id = ac.clo_id   " + 
				"left join users crm on crm.id = ac.crm_id   " + 
				"   " + 
				"where    " + 
				"	ac.moa_type = 'DGMOA'   " + 
				"	and w.fin_year = " + grantYear.toString() ;
		return (List<PipTaskTrackerReportBean>) super.nativeSelectSqlList(sql, PipTaskTrackerReportBean.class);
	}

	
}
