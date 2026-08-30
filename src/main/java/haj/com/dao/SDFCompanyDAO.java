package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.CompanySdfReportBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFCompanyDAO.
 */
public class SDFCompanyDAO extends AbstractDAO {

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
	 * Get all SDFCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompany
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allSDFCompany() throws Exception {
		return (List<SDFCompany>) super.getList("select o from SDFCompany o");
	}

	/**
	 * Get all SDFCompany between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompany
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allSDFCompany(Long from, int noRows) throws Exception {
		String hql = "select o from SDFCompany o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SDFCompany>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * All company from SDF.
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
	public List<SDFCompany> allCompanyFromSDF(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from SDFCompany o " + "left join fetch o.company comp " + "left join fetch o.sdf s " + "left join fetch s.gender g " + "left join fetch s.equity e " + "left join fetch s.nationality n " + "left join fetch comp.sicCode sicCode " + "left join fetch comp.residentialAddress resiA " + "left join fetch resiA.municipality resiAM " + "left join fetch resiA.town resiTown " + "left join fetch comp.postalAddress postalA " + "left join fetch postalA.municipality postalAAM "
				+ "left join fetch postalA.town postalTown " + "left join fetch comp.registeredAddress regA " + "left join fetch regA.municipality regAM " + "left join fetch regA.town regTown " + "left join fetch comp.chamber chamber " + "left join fetch o.sdfType sdfType " + "left join fetch o.relationshipToCompany rc " + "left join fetch o.scopeOfResponsibility sor " + "where o.sdf.id = :formUserId " + "and comp.companyStatus <> :companyStatus " + "and o.company.linkedCompany is null";

		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allCompanyFromSDFWherePrimary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA "
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where o.sdf.id = :formUserId " 
					+ "and comp.companyStatus not in (:companyStatus)" 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id = :primarySdfID";

		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allCompanyFromSDFWherePrimarySearch(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA " 
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where o.sdf.id = :formUserId " 
					+ "and comp.companyStatus <> :companyStatus " 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id = :primarySdfID";

		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		
		return (List<SDFCompany>) sortAndFilterWhereSDFCompayInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}
	


	@SuppressWarnings("unchecked")
	public List<SDFCompany> allCompanyFromSDFWherePrimary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA " 
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where comp.companyStatus <> :companyStatus " 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id = :primarySdfID";

		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allWorkplaceApprovalSDFCompany(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA " 
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where comp.companyStatus <> :companyStatus " 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id = :primarySdfID";

		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int allCompanyFromSDFCountWherePrimary(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.company.companyStatus <> :companyStatus and o.company.linkedCompany is null and o.sdfType.id = :primarySdfID ";
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (int) super.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allCompanyFromSDFWherePrimaryAndSecondary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA " 
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where o.sdf.id = :formUserId " 
					+ "and comp.companyStatus <> :companyStatus " 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id not in (:LAB_ID,:ALT_LAB_ID,:EMP_ID,:ALT_EMP_ID)";

		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("LAB_ID", HAJConstants.LAB_ID);
		filters.put("ALT_LAB_ID", HAJConstants.ALT_LAB_ID);
		filters.put("EMP_ID", HAJConstants.EMP_ID);
		filters.put("ALT_EMP_ID", HAJConstants.ALT_EMP_ID);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allCompanyFromSDFWherePrimaryAndSecondaryAndLabAndEmp(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA " 
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where o.sdf.id = :formUserId " 
					+ "and comp.companyStatus <> :companyStatus " 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id not in (:ALT_LAB_ID)";

		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
//		filters.put("LAB_ID", HAJConstants.LAB_ID);
		filters.put("ALT_LAB_ID", HAJConstants.ALT_LAB_ID);
//		filters.put("EMP_ID", HAJConstants.EMP_ID);
//		filters.put("ALT_EMP_ID", HAJConstants.ALT_EMP_ID);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	
	public long allCompanyFromSDFCountWherePrimary(Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdf.id = :formUserId and o.company.companyStatus not in (:companyStatus) and o.company.linkedCompany is null and o.sdfType.id = :primarySdfID";
		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (long) super.getUniqueResult(hql, filters);
	}
	
	public long allCompanyFromSDFCountWherePrimarySearch(Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdf.id = :formUserId and o.company.companyStatus <> :companyStatus and o.company.linkedCompany is null and o.sdfType.id = :primarySdfID";
		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return countWhereSDFCompanynfo(filters, hql);
	}
	
	
	
	
	public long allWorkplaceCompanySDFCount(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.company.companyStatus <> :companyStatus and o.company.linkedCompany is null and o.sdfType.id = :primarySdfID";
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (long) super.getUniqueResult(hql, filters);
	}


	public int allCompanyFromSDFCountWherePrimaryAndSecondary(Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdf.id = :formUserId and o.company.companyStatus <> :companyStatus and o.company.linkedCompany is null and o.sdfType.id not in (:LAB_ID,:ALT_LAB_ID,:EMP_ID,:ALT_EMP_ID)";
		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		filters.put("LAB_ID", HAJConstants.LAB_ID);
		filters.put("ALT_LAB_ID", HAJConstants.ALT_LAB_ID);
		filters.put("EMP_ID", HAJConstants.EMP_ID);
		filters.put("ALT_EMP_ID", HAJConstants.ALT_EMP_ID);
		return (int) super.countWhere(filters, hql);
	}
	
	public int allCompanyFromSDFCountWherePrimaryAndSecondaryLabAndEmpAndAltEmp(Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdf.id = :formUserId and o.company.companyStatus <> :companyStatus and o.company.linkedCompany is null and o.sdfType.id not in (:ALT_LAB_ID)";
		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
//		filters.put("LAB_ID", HAJConstants.LAB_ID);
		filters.put("ALT_LAB_ID", HAJConstants.ALT_LAB_ID);
//		filters.put("EMP_ID", HAJConstants.EMP_ID);
//		filters.put("ALT_EMP_ID", HAJConstants.ALT_EMP_ID);
		return (int) super.countWhere(filters, hql);
	}
	
	/**
	 * All company from SDF count.
	 *
	 * @param filters
	 *            the filters
	 * @param formUser
	 *            the form user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allCompanyFromSDFCount(Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdf.id = :formUserId and o.company.companyStatus <> :companyStatus";
		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		return (long) super.getUniqueResult(hql, filters);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompany
	 */
	public SDFCompany findByKey(Long id) throws Exception {
		String hql = "select o from SDFCompany o " 
				+ "left join fetch o.company comp " 
				+ "left join fetch o.sdf s " 
				+ "left join fetch s.gender g " 
				+ "left join fetch s.equity e " 
				+ "left join fetch s.nationality n " 
				+ "left join fetch comp.sicCode sicCode " 
				+ "left join fetch comp.residentialAddress resiA " 
				+ "left join fetch comp.previousCompany pre "
				+ "left join fetch resiA.municipality resiAM " 
				+ "left join fetch resiA.town resiTown " 
				+ "left join fetch comp.postalAddress postalA " 
				+ "left join fetch postalA.municipality postalAAM "
				+ "left join fetch postalA.town postalTown " 
				+ "left join fetch comp.registeredAddress regA " 
				+ "left join fetch regA.municipality regAM " 
				+ "left join fetch regA.town regTown " 
				+ "left join fetch comp.chamber chamber " 
				+ "left join fetch o.sdfType sdfType " 
				+ "left join fetch o.relationshipToCompany rc " 
				+ "left join fetch o.scopeOfResponsibility sor " 
				+ "where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SDFCompany) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SDFCompany by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompany
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findByName(String description) throws Exception {
		String hql = "select o from SDFCompany o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	/**
	 * All SDF for company.
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
	 * @param company
	 *            the company
	 * @param formUser
	 *            the form user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allSDFForCompany(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company, Users formUser) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.sdfType left join fetch o.sdf where o.company.id = :companyId";
		filters.put("companyId", company.getId());
		// filters.put("currentUserID", formUser.getId());
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public SDFCompany findPrimarySDF(Company company) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.sdfType left join fetch o.sdf where o.company.id = :companyID and o.sdfType.id = :prmID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("prmID", HAJConstants.PRIMARY_ID);
		return (SDFCompany) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findAllPrimarySDF(Company company) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.sdfType left join fetch o.sdf where o.company.id = :companyID and o.sdfType.id = :prmID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("prmID", HAJConstants.PRIMARY_ID);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findPrimaryAndLabourSDF(Company company) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.sdfType left join fetch o.sdf where o.company.id = :companyID and o.sdfType.id in (:prmID, :labID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("prmID", HAJConstants.PRIMARY_ID);
		parameters.put("labID", HAJConstants.LAB_ID);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Users> findPrimaryAndLabourSDFUsers(Company company) throws Exception {
		String hql = "select o.sdf from SDFCompany o where o.company.id = :companyID and o.sdfType.id in (:prmID, :labID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("prmID", HAJConstants.PRIMARY_ID);
		parameters.put("labID", HAJConstants.LAB_ID);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findSDFUsers(Company company) throws Exception {
		String hql = "select o.sdf from SDFCompany o where o.company.id = :companyID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<Users>) super.getList(hql, parameters);
	}

	/**
	 * All SDF for company count.
	 *
	 * @param filters
	 *            the filters
	 * @param company
	 *            the company
	 * @param formUser
	 *            the form user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allSDFForCompanyCount(Map<String, Object> filters, Company company, Users formUser) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.company.id = :companyId";
		filters.put("companyId", company.getId());
		// filters.put("currentUserID", formUser.getId());
		return (long) super.getUniqueResult(hql, filters);
	}

	/**
	 * By SDF.
	 *
	 * @param sdfId
	 *            the sdf id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> bySDF(Long sdfId) throws Exception {
		String hql = "select o from SDFCompany o " + "left join fetch o.company comp " + "left join fetch o.sdf s " + "left join fetch s.gender g " + "left join fetch s.equity e " + "left join fetch s.nationality n " + "left join fetch comp.sicCode sicCode " + "left join fetch comp.residentialAddress resiA " + "left join fetch resiA.municipality resiAM " + "left join fetch resiA.town resiTown " + "left join fetch comp.postalAddress postalA " + "left join fetch postalA.municipality postalAAM "
				+ "left join fetch postalA.town postalTown " + "left join fetch comp.registeredAddress regA " + "left join fetch regA.municipality regAM " + "left join fetch regA.town regTown " + "left join fetch comp.chamber chamber " + "left join fetch o.sdfType sdfType " + "left join fetch o.relationshipToCompany rc " + "left join fetch o.scopeOfResponsibility sor " + "where o.sdf.id = :sdfId " + "and o.company.approvalDate is null " + "and comp.linkedCompany is null "
				+ "order by comp.companyStatus";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", sdfId);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SDFCompany> bySDFPrimary(Long sdfId) throws Exception {
		String hql = "select o from SDFCompany o " + "left join fetch o.company comp " + "left join fetch o.sdf s " + "left join fetch s.gender g " + "left join fetch s.equity e " + "left join fetch s.nationality n " + "left join fetch comp.sicCode sicCode " + "left join fetch comp.residentialAddress resiA " + "left join fetch resiA.municipality resiAM " + "left join fetch resiA.town resiTown " + "left join fetch comp.postalAddress postalA " + "left join fetch postalA.municipality postalAAM "
				+ "left join fetch postalA.town postalTown " + "left join fetch comp.registeredAddress regA " + "left join fetch regA.municipality regAM " + "left join fetch regA.town regTown " + "left join fetch comp.chamber chamber " + "left join fetch o.sdfType sdfType " + "left join fetch o.relationshipToCompany rc " + "left join fetch o.scopeOfResponsibility sor " + "where o.sdf.id = :sdfId " + "and o.company.approvalDate is null " + "and comp.linkedCompany is null "
				+ "and sdfType.setmisCode = :primaryCode " + "order by comp.companyStatus";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", sdfId);
		parameters.put("primaryCode", "Primary");
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	/**
	 * By SDF child.
	 *
	 * @param sdfId
	 *            the sdf id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> bySDFChild(Long sdfId) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.company comp " + " left join fetch o.sdf s  " + "left join fetch comp.sicCode sicCode " + "left join fetch comp.residentialAddress resiA " + "left join fetch resiA.municipality resiAM " + "left join fetch comp.postalAddress postalA " + "left join fetch postalA.municipality postalAAM " + "left join fetch comp.registeredAddress regA " + "left join fetch regA.municipality regAM " + "left join fetch comp.chamber chamber "
				+ "left join fetch o.sdfType sdfType " + "where o.sdf.id = :sdfId " + "and o.company.linkedCompany is not null " + "order by comp.companyStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", sdfId);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	/**
	 * Linked company.
	 *
	 * @param sdfCompany
	 *            the sdf company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> linkedCompany(SDFCompany sdfCompany) throws Exception {
		String hql = "select o from SDFCompany o " + "left join fetch o.company comp " + "left join fetch comp.linkedCompany lc " + "left join fetch o.sdf s  " + "left join fetch comp.sicCode sicCode " + "left join fetch comp.residentialAddress resiA " + "left join fetch resiA.municipality resiAM " + "left join fetch comp.postalAddress postalA " + "left join fetch postalA.municipality postalAAM " + "left join fetch comp.registeredAddress regA " + "left join fetch regA.municipality regAM "
				+ "left join fetch comp.chamber chamber " + "left join fetch o.sdfType sdfType " + "where o.company.linkedCompany.id = :companyID " + "and o.sdf.id = :sdfID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", sdfCompany.getCompany().getId());
		parameters.put("sdfID", sdfCompany.getSdf().getId());
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	/**
	 * Sites by company.
	 *
	 * @param sdfCompany
	 *            the sdf company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> sitesByCompany(SDFCompany sdfCompany) throws Exception {
		String hql = "select o from Sites o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", sdfCompany.getCompany().getId());
		return (List<Sites>) super.getList(hql, parameters);
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
		String hql = "select o from SDFCompany o " + "left join fetch o.sdfType sdfType " + "where o.company.id = :companyId and o.sdf.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		return (SDFCompany) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by company user.
	 *
	 * @param companyId
	 *            the company id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findByCompanyUser(Long companyId) throws Exception {
		String hql = "select count(o) from CompanyUsers o where o.company.id = :companyId and o.companyUserType is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by company sign off.
	 *
	 * @param companyId
	 *            the company id
	 * @param signOff
	 *            the sign off
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findByCompanySignOff(Long companyId, boolean signOff) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.company c left join fetch o.sdf s left join fetch o.sdfType st where c.id = :companyId and st.signOffWsp = :signOff";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("signOff", signOff);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SDFCompany> findByCompany(Long companyId) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.company c left join fetch o.sdf s left join fetch o.sdfType st where c.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findByCompanyAndSdfType(Long companyId, Long sdfTypeId) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.company c left join fetch o.sdf s left join fetch o.sdfType st where c.id = :companyId and o.sdfType.id = :sdfTypeId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("sdfTypeId", sdfTypeId);
		return (List<SDFCompany>) super.getList(hql, parameters);
	}

	public long findByCompanyCount(Long companyId) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByCompanyCountPrimary(Long companyId) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.company.id = :companyId and o.sdfType.id = :primarySdfID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Company> findByUser(Users users) throws Exception {
		String hql = "select distinct(o.company) from SDFCompany o where o.sdf.id = :sdfId and o.company.linkedCompany is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", users.getId());
		return (List<Company>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SDFCompany> findNotCompletedWsp(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId))";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findNotCompletedWsp(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u) throws Exception {
		String hql = "select o from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId)) and o.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID)";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("userID", u.getId());
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public long countNotCompletedWsp(Map<String, Object> filters, Users u) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId)) and o.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID)";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("userID", u.getId());
		return (long) super.getUniqueResult(hql, filters);
	}
	
	public long countNotCompletedWsp(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId))";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		return (long) super.getUniqueResult(hql, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findNotCompletedWspByFinYear(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId) and x.finYear = :finYear)";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("finYear", finYear);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public long countNotCompletedWspByFinYear(Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId) and x.finYear = :finYear)";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("finYear", finYear);
		return (long) super.getUniqueResult(hql, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> findNotCompletedWspByUserRegionAndByFinYear(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u, Integer finYear) throws Exception {
		String hql = "select o from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId) and x.finYear = :finYear) and o.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID)";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("userID", u.getId());
		filters.put("finYear", finYear);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public long countNotCompletedWspByUserRegionAndByFinYear(Map<String, Object> filters, Users u, Integer finYear) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.sdfType.id = :primarySDF and o.company.nonLevyPaying is not null and o.company.numberOfEmployees is not null and o.company.id in (select x.company.id from Wsp x where x.wspStatusEnum in (:pendingSignOffId, :pendingId) and x.finYear = :finYear) and o.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID)";
		filters.put("primarySDF", HAJConstants.PRIMARY_ID);
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("userID", u.getId());
		filters.put("finYear", finYear);
		return (long) super.getUniqueResult(hql, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findByUserAttributes(Users users, long compId) throws Exception {
		String hql = "select distinct(o.sdf) from SDFCompany o where (o.sdf.email = :email or o.sdf.rsaIDNumber = :rsaIDNumber or o.sdf.passportNumber = :passportNumber) and o.company.id =:compId";
	
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("compId", compId);
		parameters.put("email", users.getEmail());
		parameters.put("rsaIDNumber", users.getRsaIDNumber());
		parameters.put("passportNumber", users.getPassportNumber());
		if (users.getId() != null) {
			hql += " and o.sdf.id <> :userId";
			parameters.put("userId", users.getId());
		}
		return (List<Users>) super.getList(hql, parameters);
	}
	
	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o ";
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
					
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("email")) {
						hql += " where o.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}
					else {
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
	
	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o ";
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
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("email")) {
						hql += " where o.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}
					else {
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
	
	public List<?> sortAndFilterWhereSDFCompayInfo(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
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
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("email")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
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
	
	/**
	 * Count.
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhereSDFCompanynfo(Map<String, Object> filters, String hql) {
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
					if (entry.getKey().equals("companyName") || entry.getKey().equals("companyName") || entry.getKey().equals("email}")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
					}else{
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allActiveCompanyFromSDFWherePrimary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SDFCompany o " 
					+ "left join fetch o.company comp " 
					+ "left join fetch o.sdf s " 
					+ "left join fetch s.gender g " 
					+ "left join fetch s.equity e " 
					+ "left join fetch s.nationality n " 
					+ "left join fetch comp.sicCode sicCode " 
					+ "left join fetch comp.residentialAddress resiA " 
					+ "left join fetch comp.previousCompany pre "
					+ "left join fetch resiA.municipality resiAM " 
					+ "left join fetch resiA.town resiTown " 
					+ "left join fetch comp.postalAddress postalA " 
					+ "left join fetch postalA.municipality postalAAM "
					+ "left join fetch postalA.town postalTown " 
					+ "left join fetch comp.registeredAddress regA " 
					+ "left join fetch regA.municipality regAM " 
					+ "left join fetch regA.town regTown " 
					+ "left join fetch comp.chamber chamber " 
					+ "left join fetch o.sdfType sdfType " 
					+ "left join fetch o.relationshipToCompany rc " 
					+ "left join fetch o.scopeOfResponsibility sor " 
					+ "where comp.companyStatus = :companyStatus " 
					+ "and o.company.linkedCompany is null "
					+ "and o.sdfType.id = :primarySdfID";

		filters.put("companyStatus", CompanyStatusEnum.Active);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (List<SDFCompany>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int allActiveCompanyFromSDFCountWherePrimary(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SDFCompany o where o.company.companyStatus = :companyStatus and o.company.linkedCompany is null and o.sdfType.id = :primarySdfID ";
		filters.put("companyStatus", CompanyStatusEnum.Active);
		filters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (int) super.countWhere(filters, hql);
	}
	
	public List<CompanySdfReportBean> populateCompanySdfReport() throws Exception {
		String sql = "select    " + 
				"	c.levy_number as entityId   " + 
				"	, c.company_name as companyName   " + 
				"	, c.trading_name as tradingName   " + 
				"	, case   " + 
				"		when r.id is not null then  r.description   " + 
				"		else 'N/A'   " + 
				"	end as region   " + 
				"	, case   " + 
				"		when uClo.id is not null then CONCAT(uClo.first_name, ' ', uClo.last_name)    " + 
				"		else 'N/A'   " + 
				"	end as cloUserFullName   " + 
				"	, u.first_name as sdfFirstName   " + 
				"	, u.last_name as sdfLastName   " + 
				"	, u.email as sdfEmail   " + 
				"	, u.rsa_id_number as sdfRsaIdNumber   " + 
				"	, u.passport_number as sdfPassportNumber   " + 
				"	, u.cell_number as sdfContactNumber   " + 
				"	, sdft.description as sdfType   " + 
				"	, case   " + 
				"		when c.company_status = '0' then 'Awaiting merSETA Approval'    " + 
				"		when c.company_status = '1' then 'Active'    " + 
				"		when c.company_status = '2' then 'In-Active'   " + 
				"		when c.company_status = '3' then 'Rejected'   " + 
				"		when c.company_status = '4' then 'Approved'   " + 
				"		when c.company_status = '5' then 'Pending Change Approval'   " + 
				"		when c.company_status = '6' then 'Non-merSETA Company'    " + 
				"		Else 'Not Assigned'   " + 
				"	end as companyStatus   " + 
				"	, case   " + 
				"		when c.company_status in (3, 0)   " + 
				"			then (   " + 
				"				select    " + 
				"					GROUP_CONCAT(description)   " + 
				"				from    " + 
				"					reject_reasons   " + 
				"				where    " + 
				"					id in (   " + 
				"						select   " + 
				"							reject_reasons_id   " + 
				"						from    " + 
				"							reject_reasons_child   " + 
				"						where   " + 
				"							reject_reasons_id is not null   " + 
				"							and company_id = c.id   " + 
				"							   " + 
				"					)   " + 
				"			)			   " + 
				"		else 'N/A'   " + 
				"	end as rejectionReasons   " + 
				"		   " + 
				"from    " + 
				"	sdf_company sdf   " + 
				"	   " + 
				"left join company c on c.id = sdf.company_id   " + 
				"left join address resAdd on resAdd.id = c.residential_address_id   " + 
				"left join region_town rt on rt.town_id = resAdd.town_id   " + 
				"left join region r on r.id = rt.region_id   " + 
				"   " + 
				"left join users u on u.id = sdf.sdf_id   " + 
				"   " + 
				"left join sdf_type sdft on sdft.id = sdf.sdf_type_id   " + 
				"   " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id   " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id   " + 
				"left join users uClo on uClo.id = hceClo.user_id" ;
		return (List<CompanySdfReportBean>) super.nativeSelectSqlList(sql, CompanySdfReportBean.class);
	}
	
}