package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.CompanyRegionReportBean;
import haj.com.bean.CompanyStatusVsSarsBean;
import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.report.bean.ByChamberReportBean;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyDAO.
 */
public class CompanyDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + "left join fetch o.residentialAddress cra " + "left join fetch cra.municipality craM " + "left join fetch o.postalAddress cpa " + "left join fetch cpa.municipality cpaM " + "left join fetch o.registeredAddress crega "
			+ "left join fetch crega.municipality cregaM " + "left join fetch o.chamber cch " + "left join fetch o.sicCode csc " + "left join fetch o.institutionType cit " + "left join fetch o.organisationType cot " + "left join fetch o.linkedCompany lc "
			+ "left join fetch lc.residentialAddress cral " + "left join fetch cral.municipality craMl " + "left join fetch lc.postalAddress cpal " + "left join fetch cpal.municipality cpaMl";

	private static final String nonSetaCompanyFalse = " o.nonSetaCompany = false ";
	private static final String nonSetaCompanyTrue = " o.nonSetaCompany = true ";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Company.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             global exception
	 * @see Company
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompany() throws Exception {
		return (List<Company>) super.getList("select o from Company o" + leftJoins + " and " + nonSetaCompanyFalse);
	}
	
	/**
	 * Get all Company between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             global exception
	 * @see Company
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompany(Long from, int noRows) throws Exception {
		String hql = "select o from Company o" + leftJoins + " and " + nonSetaCompanyFalse;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<Company>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Company}
	 * @throws Exception
	 *             global exception
	 * @see Company
	 */
	public Company findByKey(Long id) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Company) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by GUID.
	 *
	 * @param guid
	 *            the guid
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	public Company findByGUID(String guid) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.companyGuid LIKE :guid ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("guid", guid);
		return (Company) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Company by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             global exception
	 * @see Company
	 */
	@SuppressWarnings("unchecked")
	public List<Company> findByName(String companyName) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.companyName like :companyName and "+nonSetaCompanyFalse+" order by o.companyName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyName", "" + companyName.trim() + "%");
		return (List<Company>) super.getList(hql, parameters);
	}

	/**
	 * Find Company by company name or levy number.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             global exception
	 * @see Company
	 */
	@SuppressWarnings("unchecked")
	public List<Company> findByNameOrLevyNum(String companyName) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.companyName like :companyName OR o.tradingName like  :companyName OR o.levyNumber like  :companyName and "+nonSetaCompanyFalse+" order by o.companyName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyName", "" + companyName.trim() + "%");
		return (List<Company>) super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findByNameOrLevyNum(String companyName, Qualification qualification,  ApprovalEnum approvalEnum) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.id in(select x.company.id from WorkPlaceApproval x where x.qualification.id = :qualificationID and x.approvalEnum = :approvalEnum) and (o.companyName like :companyName OR o.tradingName like  :companyName OR o.levyNumber like  :companyName) and "+nonSetaCompanyFalse+" order by o.companyName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyName", "" + companyName.trim() + "%");
		parameters.put("qualificationID", qualification.getId());
		parameters.put("approvalEnum", approvalEnum);
		return (List<Company>) super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findByActiveNameOrLevyNum(String companyName) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.companyStatus = :comapnyStatus and (o.companyName like :companyName OR o.tradingName like  :companyName OR o.levyNumber like  :companyName) and "+nonSetaCompanyFalse+" order by o.companyName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyName" , "" + companyName.trim() + "%");
		parameters.put("comapnyStatus" , CompanyStatusEnum.Active);
		return (List<Company>) super.getList(hql, parameters, 10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findByLevyNum(String companyName) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.levyNumber like  :companyName and "+nonSetaCompanyFalse+" order by o.companyName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyName", "" + companyName.trim() + "%");
		return (List<Company>) super.getList(hql, parameters);
	}
	
	public Company findByLevyNumber(String levyNumber) throws Exception {
		String hql = "select o from Company o where o.levyNumber = :levyNumber and " + nonSetaCompanyFalse;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumber", levyNumber);
		return (Company) super.getUniqueResult(hql, parameters);
	}
	
	public Company findByLevyNumberIncludingNonSeta(String levyNumber) throws Exception {
		String hql = "select o from Company o where o.levyNumber = :levyNumber";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumber", levyNumber);
		return (Company) super.getUniqueResult(hql, parameters);
	}
	
	public int countCompaniesLinkedByCloCrmUser(Long userID) throws Exception {
		String hql = "select count(o) from Company o where o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userID", userID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Company findByLevyNumberWhereStatusNotInList(String levyNumber, List<CompanyStatusEnum> companyStatusList) throws Exception {
		String hql = "select o from Company o where o.levyNumber = :levyNumber";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumber", levyNumber);
		if (!companyStatusList.isEmpty()) {
	    	hql += " and o.companyStatus not in ( ";
	    	Integer counter = 1;
	    	for (CompanyStatusEnum status : companyStatusList) {
	    		if (counter == companyStatusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		parameters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return (Company) super.getUniqueResult(hql, parameters);
	}
	
	
	/**
	 * Find Company by company name or registration number.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             global exception
	 * @see Company
	 */
	@SuppressWarnings("unchecked")
	public List<Company> findByNameOrRegNumber(String companyName) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.companyName like  :companyName " + "OR o.tradingName like  :companyName " + "OR o.companyRegistrationNumber like  :companyName and "+nonSetaCompanyFalse+" order by o.companyName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyName", "" + companyName.trim() + "%");
		return (List<Company>) super.getList(hql, parameters);
	}
	
	public Integer countByRegNumber(String companyRegNumber) throws Exception {
		String hql = "select count(o) from Company o where UPPER(o.companyRegistrationNumber) = :companyRegNumber and "+nonSetaCompanyFalse;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyRegNumber", companyRegNumber.trim().toUpperCase());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	/**
	 * All company.
	 *
	 * @param class1
	 *            the class 1
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param formUser
	 *            the form user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompany(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from Company o" + leftJoins + "where o.formUser.id = :formUserId";
		filters.put("formUserId", formUser.getId());
		return (List<Company>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	/**
	 * All company count.
	 *
	 * @param formUser
	 *            the form user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allCompanyCount(Users formUser) throws Exception {
		String hql = "select count(o) from Company o" + leftJoins + "where o.formUser.id = :formUserId";
		// filters.put("formUserId", formUser.getId());
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("formUserId", formUser.getId());
		return (long) super.getUniqueResult(hql, parameters);

	}

	/**
	 * Search by reg.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public Company searchByReg(String criteria) throws Exception {
		String hql = "select o from Company o " + leftJoins + " where o.companyRegistrationNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Company findNonMersetaCompany(String criteria) throws Exception {
		String hql = "select o from Company o " + leftJoins + " where o.companyRegistrationNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	

	@SuppressWarnings("unchecked")
	public Company searchNonSetaByReg(String criteria) throws Exception {
		String hql = "select o from Company o " + leftJoins + " where o.companyRegistrationNumber = :criteria and "+nonSetaCompanyTrue+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	
	/**
	 * Search by SDL.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public Company searchBySDL(String criteria) throws Exception {
		System.out.println("Inside CompanyDAO:: searchBySDL :: criteria ---" + criteria);
		String hql = "select o from Company o " + leftJoins + " where o.levyNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		System.out.println("Inside CompanyDAO:: searchBySDL :: Company:: ---" + l);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	
	/**
	 * Search by accreditationNumber.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public Company searchByAccreditationNumber(String criteria) throws Exception {
		String hql = "select o from Company o " + leftJoins + " where o.accreditationNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	
	/**
	 * Search by SDL.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public Company searchBySDLNoJoins(String criteria) throws Exception {
		String hql = "select o from Company o where o.levyNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	
	/**
	 * Search by Accreditation Number No Joins.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public Company searchByAccreditationNumberNoJoins(String criteria) throws Exception {
		String hql = "select o from Company o where o.accreditationNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}
	
	/**
	 * Search by reg and institution type.
	 *
	 * @param criteria
	 *            the criteria
	 * @param institutionTypeId
	 *            the institution type id
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	public Company searchByRegAndInstitutionType(String criteria, Long institutionTypeId) throws Exception {
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress left join fetch o.chamber left join fetch o.sicCode left join fetch o.institutionType where o.companyRegistrationNumber = :criteria and "+nonSetaCompanyFalse+" order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("criteria", criteria);
		// parameters.put("institutionTypeId", institutionTypeId);
		List<Company> l = (List<Company>) super.getList(hql, parameters, 1);
		if (l == null || l.size() == 0)
			return null;
		else
			return l.get(0);
	}

	/**
	 * Find by company and user.
	 *
	 * @param companyId
	 *            the company id
	 * @param userId
	 *            the user id
	 * @return the SDF company
	 * @throws Exception
	 *             the exception
	 */
	public SDFCompany findByCompanyAndUser(Long companyId, Long userId) throws Exception {
		String hql = "select o from SDFCompany o where o.company.id = :companyId and o.sdf.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		return (SDFCompany) super.getUniqueResult(hql, parameters);
	}

	public SDFCompany findPrimarySDF(Long companyId) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.sdf s where o.company.id = :companyId and o.sdfType.code = :primary";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("primary", "PRM");
		parameters.put("companyId", companyId);
		return (SDFCompany) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findLastNNumber() throws Exception {
		String hql = "select o.levyNumber from Company o where o.levyNumber like 'N%' and "+nonSetaCompanyFalse+" order by o.levyNumber desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<String>) super.getList(hql, parameters,1);
	}

	/**
	 * Find linked company.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> findLinkedCompany(Long companyId) throws Exception {
		String hql = "select o from Company o" + leftJoins + " where o.linkedCompany.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Company>) super.getList(hql, parameters);
	}
	
	

	@Override
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o left join fetch o.residentialAddress left join fetch o.postalAddress ";
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
					hql += " where o." + entry.getKey() + " like " + " :" + hvar;
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
	
	
	public List<?> sortAndFilterWhereDG(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		String insertCustomHql = "wsp.company.";
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
//					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					hql += " and o." + insertCustomHql + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
//					hql += " order by o." + sortField + " asc ";
					hql += " order by o." + insertCustomHql + sortField + " asc ";
					break;
				case DESCENDING:
//					hql += " order by o." + sortField + " desc ";
					hql += " order by o." + insertCustomHql + sortField + " desc ";
					break;
				default:
					break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countWhereDG(Class<?> entity, Map<String, Object> filters, String hql) {
		if (filters != null) {
			String insertCustomHql = "wsp.company.";
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
//					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					hql += " and o." + insertCustomHql + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public List<?> sortAndFilterSearch1(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {

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
					
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("tradingName")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
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
				if (sortField.equals("companyName") || sortField.equals("levyNumber") ||sortField.equals("email")) {
					hql += " order by o.company." + sortField + " asc ";
				}
				else{
					hql += " order by o." + sortField + " asc ";
				}
				
				break;
			case DESCENDING:
				if (sortField.equals("companyName") || sortField.equals("levyNumber") ||sortField.equals("email")) {
					hql += " order by o.company." + sortField + " asdescc ";
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
	
	public int countSearch1(Map<String, Object> filters, String hql) {
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
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("tradingName")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}	
	
	@SuppressWarnings("unchecked")
	public List<Company> findByLevyNumberForStatusCheck(String levyNumber) throws Exception {
		String hql = "select o from Company o " + leftJoins + " where o.levyNumber = :levyNumber";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumber", levyNumber);
		return (List<Company>) super.getList(hql, parameters, 1);
	}
	
	public int countAllWpaWhereWpaNumberAplliedForCompany() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from Company o where o.workplaceApprovalNumber <> null")).intValue();
	}

	public Integer countAllCompaniesByCompanySiteNumberAssigned(String levyNumberStart) throws Exception {
		String hql = "select count(o) from Company o where o.levyNumber like :levyNumberStart and o.companySiteNumber <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumberStart", levyNumberStart + "%");
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompaniesByCompanyWhereSiteNumberNotAssigned(String levyNumberStart) throws Exception {
		String hql = "select o from Company o where o.levyNumber like :levyNumberStart and o.companySiteNumber is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumberStart", levyNumberStart + "%");
		return (List<Company>) super.getList(hql, parameters);
	}
	
	public Integer countAllCompaniesByCompanyWhereSiteNumberNotAssigned(String levyNumberStart) throws Exception {
		String hql = "select count(o) from Company o where o.levyNumber like :levyNumberStart and o.companySiteNumber is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("levyNumberStart", levyNumberStart + "%");
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countCompaniesByRegistartionNumber(String regNumber) throws Exception {
		String hql = "select count(o) from Company o where UPPER(o.companyRegistrationNumber) = :regNumber";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regNumber" ,regNumber.trim().toUpperCase());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countCompaniesByRegistartionNumberAndNotCompanyId(String regNumber, Long companyId) throws Exception {
		String hql = "select count(o) from Company o where UPPER(o.companyRegistrationNumber) = :regNumber and o.id <> :companyId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("regNumber", regNumber.trim().toUpperCase());
		parameters.put("companyId", companyId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countCompaniesByLevyNumber(String levyNumber) throws Exception {
		String hql = "select count(o) from Company o where o.levyNumber = :levyNumber and o.companyStatus <> :companyStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("levyNumber" ,levyNumber.trim());
		parameters.put("companyStatus", CompanyStatusEnum.InActive);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public Integer countCompaniesByLevyNumberAndNotCompanyId(String levyNumber, Long companyId) throws Exception {
		String hql = "select count(o) from Company o where o.levyNumber = :levyNumber and o.id <> :companyId and o.companyStatus <> :companyStatus";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("levyNumber", levyNumber.trim());
		parameters.put("companyId", companyId);
		parameters.put("companyStatus", CompanyStatusEnum.InActive);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<CompanyStatusVsSarsBean> populateDistinctHostingCompanyProcessByUserId(Long sarsFileId) throws Exception {		
		String sql = "select   " + 
				"	c.levy_number as entityId  " + 
				"	, c.company_name as companyName  " + 
				"	, c.trading_name as tradingName  " + 
				"	, case  " + 
				"		when c.company_status = '0' then 'Pending Approval'  " + 
				"		when c.company_status = '1' then 'Active'  " + 
				"		when c.company_status = '2' then 'In-Active'  " + 
				"		when c.company_status = '3' then 'Rejected'  " + 
				"		when c.company_status = '4' then 'Approved'  " + 
				"		when c.company_status = '5' then 'Pending Change Approva'  " + 
				"		when c.company_status = '6' then 'Non-merSETA Company'  " + 
				"		when c.company_status = '7' then 'Deregistered'  " + 
				"		when c.company_status = '8' then 'Pending Replacement'  " + 
				"		End as nsdmsStatus  " + 
				"	, case  " + 
				"		when sed.trading_status = 'A' then '(A) Active'  " + 
				"		when sed.trading_status = 'B' then '(B) Estate'  " + 
				"		when sed.trading_status = 'S' then '(S) Stopped Trading'  " + 
				"		when sed.trading_status = 'X' then '(X) Cannot be traced'  " + 
				"		when sed.trading_status = 'Y' then '(Y) Deregistered'  " + 
				"		when sed.trading_status = 'D' then '(D) Unknown'  " + 
				"		when sed.trading_status = 'R' then '(R) Unknown'  " + 
				"		end as sarsTradingStatus  " + 
				"from   " + 
				"	company c  " + 
				"left join sars_employer_detail sed on sed.ref_no = c.levy_number and sed.sars_filel_id = :sarsFileId  " + 
				"where   " + 
				"	c.non_seta_company = false  " + 
				"	and c.levy_number in (select distinct ref_no from sars_employer_detail)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sarsFileId", sarsFileId);
		return (List<CompanyStatusVsSarsBean>) super.nativeSelectSqlList(sql, CompanyStatusVsSarsBean.class, parameters);
	}
	
	public List<ByChamberReportBean> reportActiveMersetaCompaniesReport() throws Exception {
		String hql ="SELECT   " + 
				"    description   " + 
				"    , MAX(if(chamberInd = 'auto', num, 0)) as auto   " + 
				"    , MAX(if(chamberInd = 'metal', num, 0)) as metal   " + 
				"    , MAX(if(chamberInd = 'motor', num, 0)) as motor   " + 
				"    , MAX(if(chamberInd = 'newTyre', num, 0)) as newTyre   " + 
				"    , MAX(if(chamberInd = 'plastic', num, 0)) as plastic   " + 
				"    , MAX(if(chamberInd = 'unknown', num, 0)) as unknown   " + 
				"    , MAX(if(chamberInd = 'NA', num, 0)) as NA    " + 
				"    , Now() as dateGenerated   " + 
				"FROM (   " + 
				"    select   " + 
				"        companySize as description   " + 
				"        , count(id) as num   " + 
				"        , chamberInd   " + 
				"    FROM (   " + 
				"   " + 
				"        select    " + 
				"            c.id as id   " + 
				"            ,case   " + 
				"                when ch.id = 1 then 'auto'   " + 
				"                when ch.id = 3 then 'metal'   " + 
				"                when ch.id = 4 then 'motor'   " + 
				"                when ch.id = 5 then 'newTyre'   " + 
				"                when ch.id = 6 then 'plastic'    " + 
				"                when ch.id = 7 then 'unknown'   " + 
				"                when ch.id = 8 then 'NA'   " + 
				"                else 'NA'    " + 
				"                end as chamberInd    " + 
				"            , 'Small' as companySize     " + 
				"        FROM    " + 
				"            company c     " + 
				"        inner join sic_code sc on sc.id = c.sic_code_id   " + 
				"        inner join chamber ch on ch.id = sc.chamber_id   " + 
				"        WHERE   " + 
				"            c.non_seta_company = false   " + 
				"            and c.company_status = '1'   " + 
				"            and c.numberOfEmployees < 50   " + 
				"   " + 
				"        UNION   " + 
				"           " + 
				"        select    " + 
				"            c.id as id   " + 
				"            ,case   " + 
				"                when ch.id = 1 then 'auto'   " + 
				"                when ch.id = 3 then 'metal'   " + 
				"                when ch.id = 4 then 'motor'   " + 
				"                when ch.id = 5 then 'newTyre'   " + 
				"                when ch.id = 6 then 'plastic'    " + 
				"                when ch.id = 7 then 'unknown'   " + 
				"                when ch.id = 8 then 'NA'   " + 
				"                else 'NA'    " + 
				"                end as chamberInd    " + 
				"            , 'Medium' as companySize     " + 
				"        FROM    " + 
				"            company c     " + 
				"        inner join sic_code sc on sc.id = c.sic_code_id   " + 
				"        inner join chamber ch on ch.id = sc.chamber_id   " + 
				"        WHERE   " + 
				"            c.non_seta_company = false   " + 
				"            and c.company_status = '1'   " + 
				"            and c.numberOfEmployees between 50 AND 150   " + 
				"   " + 
				"        UNION   " + 
				"           " + 
				"        select    " + 
				"            c.id as id   " + 
				"            ,case   " + 
				"                when ch.id = 1 then 'auto'   " + 
				"                when ch.id = 3 then 'metal'   " + 
				"                when ch.id = 4 then 'motor'   " + 
				"                when ch.id = 5 then 'newTyre'   " + 
				"                when ch.id = 6 then 'plastic'    " + 
				"                when ch.id = 7 then 'unknown'   " + 
				"                when ch.id = 8 then 'NA'   " + 
				"                else 'NA'    " + 
				"                end as chamberInd    " + 
				"            , 'Large' as companySize     " + 
				"        FROM    " + 
				"            company c     " + 
				"        inner join sic_code sc on sc.id = c.sic_code_id   " + 
				"        inner join chamber ch on ch.id = sc.chamber_id   " + 
				"        WHERE   " + 
				"            c.non_seta_company = false   " + 
				"            and c.company_status = '1'   " + 
				"            and c.numberOfEmployees > 150   " + 
				"   " + 
				"    ) as nums group by description, chamberInd   " + 
				"   " + 
				") counts    " + 
				"group by description";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	public List<CompanyRegionReportBean> generateCompanyRegionReport() throws Exception {
		String hql ="select    " + 
				"	c.company_name as companyName   " + 
				"	, c.trading_name as tradingName   " + 
				"	, c.levy_number as levyNumber   " + 
				"	, case   " + 
				"		when r.id is not null then  r.description   " + 
				"		else 'N/A'   " + 
				"	end as region   " + 
				"	, case   " + 
				"		when t.id is not null then  t.description   " + 
				"		else 'N/A'   " + 
				"	end as town	   " + 
				"	, case   " + 
				"		when uClo.id is not null then CONCAT(uClo.first_name, ' ', uClo.last_name)    " + 
				"		else 'N/A'   " + 
				"	end as cloUserFullName   " + 
				"	, case   " + 
				"		when uClo.id is not null then uClo.email   " + 
				"		else 'N/A'   " + 
				"	end as cloUserEmail   " + 
				"	, case   " + 
				"		when uCrm.id is not null then CONCAT(uCrm.first_name, ' ', uCrm.last_name)    " + 
				"		else 'N/A'   " + 
				"	end as crmUserFullName   " + 
				"	, case   " + 
				"		when uCrm.id is not null then uCrm.email   " + 
				"		else 'N/A'   " + 
				"	end as crmUserEmail   " + 
				"	   " + 
				"from    " + 
				"	company c   " + 
				"	   " + 
				"left join address resAdd on resAdd.id = c.residential_address_id   " + 
				"left join region_town rt on rt.town_id = resAdd.town_id   " + 
				"left join region r on r.id = rt.region_id   " + 
				"left join towns t on t.id = rt.town_id   " + 
				"   " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id   " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id   " + 
				"left join users uClo on uClo.id = hceClo.user_id   " + 
				"   " + 
				"left join hosting_company_employees hceCrm on hceCrm.id = rt.crm_id   " + 
				"left join job_title jtCrm on jtCrm.id = hceCrm.job_title_id   " + 
				"left join users uCrm on uCrm.id = hceCrm.user_id   " + 
				"   " + 
				"where    " + 
				"	EXISTS(   " + 
				"		select    " + 
				"			1   " + 
				"		from   " + 
				"			company_users   " + 
				"		where    " + 
				"			company_id = c.id   " + 
				"		)";
		return (List<CompanyRegionReportBean>)super.nativeSelectSqlList(hql, CompanyRegionReportBean.class);
	}
				
}
