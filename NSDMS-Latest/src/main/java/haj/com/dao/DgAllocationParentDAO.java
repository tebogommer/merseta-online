package haj.com.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.DgAllocationStatusReportBean;
import haj.com.entity.DgAllocationParent;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DgAllocationParentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgAllocationParent
 	 * @author TechFinium 
 	 * @see    DgAllocationParent
 	 * @return a list of {@link haj.com.entity.DgAllocationParent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParent() throws Exception {
		return (List<DgAllocationParent>)super.getList("select o from DgAllocationParent o");
	}

	/**
	 * Get all DgAllocationParent between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DgAllocationParent
 	 * @return a list of {@link haj.com.entity.DgAllocationParent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParent(Long from, int noRows) throws Exception {
	 	String hql = "select o from DgAllocationParent o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DgAllocationParent>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgAllocationParent
 	 * @return a {@link haj.com.entity.DgAllocationParent}
 	 * @throws Exception global exception
 	 */
	public DgAllocationParent findByKey(Long id) throws Exception {
	 	String hql = "select o from DgAllocationParent o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DgAllocationParent)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgAllocationParent by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgAllocationParent
  	 * @return a list of {@link haj.com.entity.DgAllocationParent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> findByName(String description) throws Exception {
	 	String hql = "select o from DgAllocationParent o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DgAllocationParent>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> findByChildrenByParent(Long dgAllocationParentId) throws Exception {
	 	String hql = "select o from DgAllocationParent o where o.allocationParent.id = :dgAllocationParentId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgAllocationParentId", dgAllocationParentId);
		return (List<DgAllocationParent>)super.getList(hql, parameters);
	}
	
	  @SuppressWarnings("unchecked")
    public List<DgAllocationParent> findByWSPReturnList(Long wspID) throws Exception {
         String hql = "select o from DgAllocationParent o where o.wsp.id = :wspID " ;
         Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("wspID", wspID);
        return (List<DgAllocationParent>)super.getList(hql, parameters);
    }
	
	/**
     * Find DgAllocationParent by WSP
      * @author TechFinium 
      * @param wspID the wspID 
      * @see    DgAllocationParent
       * @return a list of {@link haj.com.entity.DgAllocationParent}
      * @throws Exception global exception
      */
    @SuppressWarnings("unchecked")
    public DgAllocationParent findByWSP(Long wspID) throws Exception {
         String hql = "select o from DgAllocationParent o where o.wsp.id = :wspID " ;
         Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("wspID", wspID);
        return (DgAllocationParent)super.getUniqueResult(hql, parameters);
    }
    
	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o where o.wsp is not null";
		if (filters != null) {
			boolean ft = true;
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
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
						
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp is not null";
		if (filters != null) {
			boolean ft = true;
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
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public BigDecimal findTotalContractValue(DgAllocationParent dgAllocationParent) throws Exception {
		String hql = "select sum(o.totalAwardAmount) from DgAllocation o where o.dgAllocationParent.id = :dgAllocationParentID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgAllocationParentID", dgAllocationParent.getId());
		return (BigDecimal) super.getUniqueResult(hql, parameters);
	}
	
	public Integer countByWspId(Long wspID) throws Exception {
		String hql = "select count(o) from DgAllocationParent o where o.wsp.id = :wspID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wspID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/* Reporting Native Start */
	public List<DgAllocationStatusReportBean> populateNativeAllocationStatusList(Integer grantYear) throws Exception {
		String sql = " select   " + 
				"	c.levy_number as entityId   " + 
				"	, c.company_name as companyName  " + 
				"	, c.trading_name as tradingName  " + 
				"	, w.fin_year as grantYear  " + 
				"	, c.numberOfEmployees as numberOfEmployees  " + 
				"	, case  " + 
				"		when dap.company_categorization = 0 then 'Silver'  " + 
				"		when dap.company_categorization = 1 then 'Gold'  " + 
				"		when dap.company_categorization = 2 then 'Platinum'  " + 
				"		ELSE 'Unable to locate Categorization'  " + 
				"	end as categorization  " + 
				"	, ot.description as organisationType  " + 
				"	, t.description as town   " + 
				"	, m.municipality_desc as municipality  " + 
				"	, p.province_desc as province  " + 
				"	, cham.description as chamber  " + 
				"	, r.description as region  " + 
				"	, case  " + 
				"		when uClo.id is not null then CONCAT(uClo.first_name, ' ', uClo.last_name)   " + 
				"		else 'Unable to locate CLO user'  " + 
				"	end as cloUser  " + 
				"	, case  " + 
				"		when ac.id is not null and ac.contract_value is not null then ac.contract_value  " + 
				"		when amount.total is not null then amount.total  " + 
				"		else null  " + 
				"	end as contractValue  " + 
				"   , case     " + 
				"		when dap.status = 17 then 'Accepted MOA'      " + 
				"		when dap.status = 9 then 'Withdrawn'    " + 
				"		when dap.status = 16 then 'Requested Higher Allocation'    " + 
				"		when dap.status = 18 then 'Requested Change'    " + 
				"		when dap.status = 12 then 'Appealed'    " + 
				"		when dap.status = 1 then 'Rejected'		    " + 
				"		else 'N/A'    " + 
				"	end as statusAssigned" + 
				"	, case  " + 
				"		when dap.status in (9) then  " + 
				"			case  " + 
				"				when dap.discretional_withdrawal_appeal_enum = 0 then 'Economic Decline'  " + 
				"				when dap.discretional_withdrawal_appeal_enum = 1 then 'Company Closing Down'  " + 
				"				when dap.discretional_withdrawal_appeal_enum = 2 then 'Change in Skills Planning Requirements'  " + 
				"				when dap.discretional_withdrawal_appeal_enum = 3 then 'Request for numbers applied for'  " + 
				"				when dap.discretional_withdrawal_appeal_enum = 4 then 'Reduce the allocated number'												  " + 
				"				when dap.discretional_withdrawal_appeal_enum = 4 then 'MoA not returned within 30 business days after allocation'				  " + 
				"				Else 'N/A'  " + 
				"			end  " + 
				"		when dap.status in (16, 18) and allc.id is not null then allc.description  " + 
				"		when dap.status in (12) and dap.appeal_status is not null then 'Appeal Status'  " + 
				"		when dap.status in (1) then 'Rejected'  " + 
				"		else 'N/A'  " + 
				"	end as reason  " + 
				"From   " + 
				"	dg_allocation_parent dap  " + 
				"inner join wsp w on w.id = dap.wsp_id  " + 
				"inner join company c on c.id = w.company_id  " + 
				"left join sic_code sc on sc.id = c.sic_code_id  " + 
				"left join chamber cham on cham.id = sc.chamber_id  " + 
				"left join organisation_type ot on ot.id = c.organisation_type_id  " + 
				"left join address resAdd on resAdd.id = c.residential_address_id  " + 
				"left join region_town rt on rt.town_id = resAdd.town_id  " + 
				"left join region r on r.id = rt.region_id  " + 
				"left join towns t on t.id = rt.town_id  " + 
				"left join municipality m on m.id = resAdd.municipality_id  " + 
				"left join province p on p.id = m.provinces_idprovinces  " + 
				"  " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id  " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id  " + 
				"left join users uClo on uClo.id = hceClo.user_id  " + 
				"  " + 
				"left join active_contracts ac on ac.dg_allocation_parent_id = dap.id  " + 
				"left join (  " + 
				"	select   " + 
				"		dg_allocation_parent_id as id  " + 
				"		, sum(total_award_amount) as total  " + 
				"	from  " + 
				"		dg_allocation  " + 
				"	group by   " + 
				"		dg_allocation_parent_id  " + 
				") amount on amount.id = dap.id  " + 
				"  " + 
				"left join (  " + 
				"	select   " + 
				"		dg_allocation_parent_id as allocation_parent_id  " + 
				"		, Max(id) as maxId   " + 
				"	from   " + 
				"		allocation_changes_reasons  " + 
				"	group by   " + 
				"		dg_allocation_parent_id  " + 
				") lastestAllChangeReason on lastestAllChangeReason.allocation_parent_id = dap.id  " + 
				"left join allocation_changes_reasons acr on acr.id = lastestAllChangeReason.maxId  " + 
				"left join allocation_change allc on allc.id = acr.allocation_change_id "+ 
				"where w.fin_year = " + grantYear.toString(); 
		return (List<DgAllocationStatusReportBean>) super.nativeSelectSqlList(sql, DgAllocationStatusReportBean.class);
	}
	
	
	/* Reporting Native End */
}

