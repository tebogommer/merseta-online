package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.DgVerificationReportBean;
import haj.com.bean.MgVerificationReportBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.MgVerification;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MgVerificationDAO extends AbstractDAO  {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + " left join fetch o.wsp mgwsp " + " ";
	
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MgVerification
 	 * @author TechFinium 
 	 * @see    MgVerification
 	 * @return a list of {@link haj.com.entity.MgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerification() throws Exception {
		return (List<MgVerification>)super.getList("select o from MgVerification o "+leftJoins);
	}

	/**
	 * Get all MgVerification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MgVerification
 	 * @return a list of {@link haj.com.entity.MgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerification(Long from, int noRows) throws Exception {
	 	String hql = "select o from MgVerification o "+leftJoins ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MgVerification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MgVerification
 	 * @return a {@link haj.com.entity.MgVerification}
 	 * @throws Exception global exception
 	 */
	public MgVerification findByKey(Long id) throws Exception {
	 	String hql = "select o from MgVerification o "+leftJoins+" where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MgVerification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MgVerification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MgVerification
  	 * @return a list of {@link haj.com.entity.MgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MgVerification> findByName(String description) throws Exception {
	 	String hql = "select o from MgVerification o "+leftJoins+" where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MgVerification>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by wsp Id
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MgVerification
 	 * @return a {@link haj.com.entity.MgVerification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public MgVerification findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from MgVerification o "+leftJoins+" where o.wsp.id = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		List<MgVerification> l = (List<MgVerification>)super.getList(hql, parameters);
		if (l != null && l.size() >0) return l.get(0);
		else return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> rejectedMgVerification(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.wsp is not null and o.status = :status";
		filters.put("status", ApprovalEnum.PendingCommitteeApproval);
		return (List<MgVerification>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countRejectedSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from MgVerification o where o.wsp is not null and o.status = :status";
		filters.put("status", ApprovalEnum.PendingCommitteeApproval);
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> mgVerificationByStatus(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		String hql = "select o from MgVerification o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
		return (List<MgVerification>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countByStatus(Class<?> entity, Map<String, Object> filters, ApprovalEnum status) {
		String hql = "select count(o) from MgVerification o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
		return ((Long) getUniqueResult(hql, filters)).intValue();
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
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
						
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o.wsp." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o.wsp." + sortField + " desc ";
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
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterSearchStatus(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) {
		String hql = "select o from " + entity.getName() + " o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
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
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
						
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o.wsp." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o.wsp." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSearchStatus(Class<?> entity, Map<String, Object> filters, ApprovalEnum status) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp is not null and o.status = :status";
		filters.put("status", status);
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
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterMgVerification(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getKey().contains("finYear")){
					int finYear = Integer.parseInt(String.valueOf(entry.getValue()));
					parms.put(entry.getKey(), finYear);
				}
				
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					
					if (entry.getKey().contains("companyName") || entry.getKey().contains("levyNumber")) {
						hql += " where o." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}else if(entry.getKey().contains("finYear")){
						hql += " where o." + entry.getKey() + " = " + " :" + hvar;
						ft = false;
					}else {
						hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					}
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				if (sortField.contains("companyName") || sortField.contains("levyNumber")) {
					hql += " order by o." + sortField + " asc ";
				}
				else{
					hql += " order by o." + sortField + " asc ";
				}
				
				break;
			case DESCENDING:
				if (sortField.contains("companyName") || sortField.contains("levyNumber")) {
					hql += " order by o." + sortField + " asdescc ";
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
	
	public int countSearchMgVerification(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getKey().contains("finYear")){
					int finYear = Integer.parseInt(String.valueOf(entry.getValue()));
					parms.put(entry.getKey(), finYear);
				}
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("levyNumber")) {
						hql += " where o.wsp.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}else if(entry.getKey().contains("finYear")){
						hql += " where o." + entry.getKey() + " = " + " :" + hvar;
						ft = false;
					}else {
						hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterWhereMgVerification(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		System.out.println(hql);
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
				System.out.println("This is the entry : " + entry.getKey() + " Input field is : " + hvar);
				if (entry.getKey().equals("wspcompanycompanyName")) {
					hql += " and o." + "wsp.company.companyName" + " like " + " :" + hvar;
				}else if(entry.getKey().equals("wspcompanylevyNumber")){
					hql += " and o." + "wsp.company.levyNumber" + " = " + " :" + hvar;
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
	
	public int countWhereMgVerification(Map<String, Object> filters, String hql) {
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
				if (entry.getKey().equals("wspcompanycompanyName")) {
					hql += " and o." + "wsp.company.companyName" + " like " + " :" + hvar;
				}else if(entry.getKey().equals("wspcompanylevyNumber")){
					hql += " and o." + "wsp.company.levyNumber" + " = " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	/*********************************************/
	
	public List<?> sortAndFilterMgVerificationS(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getKey().contains("finYear")){
					int finYear = Integer.parseInt(String.valueOf(entry.getValue()));
					parms.put(entry.getKey(), finYear);
				}
				
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {					
					if (entry.getKey().contains("companyName") || entry.getKey().contains("levyNumber")) {
						hql += " and o." + entry.getKey() + " like " + ":" + hvar;
						ft = false;
					}
				} 
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				if (sortField.contains("companyName") || sortField.contains("levyNumber")) {
					hql += " order by o." + sortField + " asc ";
				}
				else{
					hql += " order by o." + sortField + " asc ";
				}
				
				break;
			case DESCENDING:
				if (sortField.contains("companyName") || sortField.contains("levyNumber")) {
					hql += " order by o." + sortField + " asdescc ";
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
	
	public int countSearchMgVerificationS(Class<?> entity, Map<String, Object> filters, String hql) {
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getKey().contains("finYear")){
					int finYear = Integer.parseInt(String.valueOf(entry.getValue()));
					parms.put(entry.getKey(), finYear);
				}
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("levyNumber")) {
						hql += " and o." + entry.getKey() + " like " + ":" + hvar;
						ft = false;
					}
				} 
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterMgVerificationByRegion(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from "+ entity.getName() +" o where o.wsp.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID)";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getKey().contains("finYear")){
					int finYear = Integer.parseInt(String.valueOf(entry.getValue()));
					parms.put(entry.getKey(), finYear);
				}
				
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					
					if (entry.getKey().contains("companyName") || entry.getKey().contains("levyNumber")) {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}else if(entry.getKey().contains("finYear")){
						hql += " and o." + entry.getKey() + " = " + " :" + hvar;
						ft = false;
					}
				} 
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				if (sortField.contains("companyName") || sortField.contains("levyNumber")) {
					hql += " order by o." + sortField + " asc ";
				}
				else{
					hql += " order by o." + sortField + " asc ";
				}
				
				break;
			case DESCENDING:
				if (sortField.contains("companyName") || sortField.contains("levyNumber")) {
					hql += " order by o." + sortField + " asdescc ";
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
	
	public int countSearchMgVerificationByRegion(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID)";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getKey().contains("finYear")){
					int finYear = Integer.parseInt(String.valueOf(entry.getValue()));
					parms.put(entry.getKey(), finYear);
				}
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("levyNumber")) {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}else if(entry.getKey().contains("finYear")){
						hql += " and o." + entry.getKey() + " = " + " :" + hvar;
						ft = false;
					}					
				} 
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	/* Reporting Start */
	
	/**
	 * MG Verification Report Summary
	 * Uses Native SQL to generate report and map to MgVerificationReportBean
	 * @return MgVerificationReportBean
	 * @param finYear
	 * @throws Exception
	 */
	public List<MgVerificationReportBean> allMandatoryVerificationsByFinYearReport(Integer finYear) throws Exception {
		String sql ="select  " + 
				"	c.levy_number as levyNumber " + 
				"	, c.company_name as organisationName " + 
				"	, c.trading_name as tradingName " + 
				"	, c.numberOfEmployees as numberOfEmployees " + 
				"	, ot.description as organisationType " + 
				"	, r.description as region " + 
				"	, uClo.first_name as cloFirstName " + 
				"	, uClo.last_name as cloLastName " + 
				"	, uCloOnMG.first_name as cloFirstNameOnMg " + 
				"	, uCloOnMG.last_name as cloLastNameOnMg " + 
				"	, (SELECT signOffClo.sign_off_date " + 
				"		from signoff signOffClo " + 
				"		where signOffClo.mg_verification_id = mv.id and signOffClo.accept = true and signOffClo.user_id = IF(uCloOnMG.id is null , uClo.id , uCloOnMG.id)   " + 
				"		) as cloSignOffDate " + 
				"	, uCrm.first_name as crmFirstName " + 
				"	, uCrm.last_name as crmLastName " + 
				"	, uCrmOnMG.first_name as crmFirstNameOnMg " + 
				"	, uCrmOnMG.last_name as crmLastNameOnMg " + 
				"	, (SELECT signOffCrm.sign_off_date " + 
				"		from signoff signOffCrm " + 
				"		where signOffCrm.mg_verification_id = mv.id and signOffCrm.accept = true and signOffCrm.user_id = IF(uCrmOnMG.id is null , uCrm.id , uCrmOnMG.id)   " + 
				"		) as crmSignOffDate " + 
				"	, case " + 
				"		when mv.primary_user_sign_off_id is null then null " + 
				"		when mv.primary_user_sign_off_id is not null then  " + 
				"			 (SELECT signOffsdf.sign_off_date " + 
				"				from signoff signOffsdf " + 
				"				where signOffsdf.mg_verification_id = mv.id and signOffsdf.accept = true and signOffsdf.user_id = mv.primary_user_sign_off_id " + 
				"				) end as sdfSignOffDate " + 
				"	, case " + 
				"		when mv.status is null then 'Not Started - In Progress'  " + 
				"		when mv.status = 0 then 'Approved'  " + 
				"		when mv.status = 1 then 'Rejected'  " + 
				"		when mv.status = 2 then 'Pending Manager Approval'  " + 
				"		when mv.status = 3 then 'Pending Approval'  " + 
				"		when mv.status = 4 then 'Pending Sign Off'  " + 
				"		when mv.status = 5 then 'Completed'  " + 
				"		when mv.status = 6 then 'Pending accept code of conduct'  " + 
				"		when mv.status = 7 then 'Awaiting DHET'  " + 
				"		when mv.status = 8 then 'Pending Final Approval'  " + 
				"		when mv.status = 9 then 'Withdrawn'  " + 
				"		when mv.status = 10 then 'N/A'  " + 
				"		when mv.status = 11 then 'Recommended'  " + 
				"		when mv.status = 12 then 'Appealed'  " + 
				"		end as mgVerificationStatus " + 
				"	, case " + 
				"		when mv.status <> 1 then null " + 
				"		when mv.status = 1 then  " + 
				"		(SELECT GROUP_CONCAT(rr.description, '. ') " + 
				"			from reject_reasons rr " + 
				"			where rr.id in  " + 
				"				(select reject_reason_id from reject_reason_multiple_selection where target_class = :passedTargetClass and target_key = mv.id ) " + 
				"		) end as rejectionReasons " + 
				"from mg_verification mv " + 
				"left join wsp w on w.id = mv.wsp_id " + 
				"left join company c on c.id = w.company_id " + 
				"left join organisation_type ot on ot.id = c.organisation_type_id " + 
				"left join address resAdd on resAdd.id = c.residential_address_id " + 
				"left join region_town rt on rt.town_id = resAdd.town_id " + 
				"left join region r on r.id = rt.region_id " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id " + 
				"left join hosting_company_employees hceCrm on hceCrm.id = rt.crm_id " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id " + 
				"left join job_title jtCrm on jtCrm.id = hceCrm.job_title_id " + 
				"left join users uClo on uClo.id = hceClo.user_id " + 
				"left join users uCrm on uCrm.id = hceCrm.user_id " + 
				"left join users uCloOnMG on uCloOnMG.id = mv.clo_generated_for_id " + 
				"left join users uCrmOnMG on uCrmOnMG.id = mv.crm_user_assigned_id " + 
				"left join towns tw on tw.id = resAdd.town_id " + 
				"left join municipality mc on mc.id = resAdd.municipality_id " + 
				"where w.fin_year = :finYearPassed ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("finYearPassed", finYear);
		parameters.put("passedTargetClass", MgVerification.class.getName());
		return (List<MgVerificationReportBean>)super.nativeSelectSqlList(sql, MgVerificationReportBean.class, parameters);
	}
	
	/* Reporting End */
}

