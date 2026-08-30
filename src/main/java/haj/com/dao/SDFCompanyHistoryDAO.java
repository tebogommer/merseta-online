package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFCompanyHistoryDAO.
 */
public class SDFCompanyHistoryDAO extends AbstractDAO {

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
	 * Get all SDFCompanyHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompanyHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> allSDFCompanyHistory() throws Exception {
		return (List<SDFCompanyHistory>) super.getList("select o from SDFCompanyHistory o");
	}

	/**
	 * Get all SDFCompanyHistory between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompanyHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> allSDFCompanyHistory(Long from, int noRows) throws Exception {
		String hql = "select o from SDFCompanyHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SDFCompanyHistory>) super.getList(hql, parameters, from.intValue(), noRows);
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
	public List<SDFCompanyHistory> allCompanyFromSDF(Class<SDFCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		String hql = "select o from SDFCompanyHistory o " 
				+ "left join fetch o.company comp " 
				+ "left join fetch o.sdf s " 
				+ "left join fetch s.gender g " 
				+ "left join fetch s.equity e " 
				+ "left join fetch s.nationality n " 
				+ "left join fetch comp.sicCode sicCode "
				+ "left join fetch comp.residentialAddress resiA " 
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
				+ "and o.company.linkedCompany is null";

		filters.put("formUserId", formUser.getId());
		filters.put("companyStatus", CompanyStatusEnum.InActive);
		return (List<SDFCompanyHistory>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
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
		String hql = "select count(o) from SDFCompanyHistory o where o.sdf.id = :formUserId and o.company.companyStatus <> :companyStatus";
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
	 * @return a {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompanyHistory
	 */
	public SDFCompanyHistory findByKey(Long id) throws Exception {
		String hql = "select o from SDFCompanyHistory o " 
				+ "left join fetch o.company comp " 
				+ "left join fetch o.sdf s " 
				+ "left join fetch s.gender g " 
				+ "left join fetch s.equity e " 
				+ "left join fetch s.nationality n " 
				+ "left join fetch comp.sicCode sicCode "
				+ "left join fetch comp.residentialAddress resiA " 
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
		return (SDFCompanyHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SDFCompanyHistory by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             global exception
	 * @see SDFCompanyHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> findByName(String description) throws Exception {
		String hql = "select o from SDFCompanyHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
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
	public List<SDFCompanyHistory> allSDFForCompany(Class<SDFCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company, Users formUser) throws Exception {
		String hql = "select o from SDFCompanyHistory o left join fetch o.sdfType left join fetch o.sdf where o.company.id = :companyId";
		filters.put("companyId", company.getId());
//		filters.put("currentUserID", formUser.getId());
		return (List<SDFCompanyHistory>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
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
		String hql = "select count(o) from SDFCompanyHistory o where o.company.id = :companyId";
		filters.put("companyId", company.getId());
//		filters.put("currentUserID", formUser.getId());
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
	public List<SDFCompanyHistory> bySDF(Long sdfId) throws Exception {
		String hql = "select o from SDFCompanyHistory o " 
				+ "left join fetch o.company comp " 
				+ "left join fetch o.sdf s " 
				+ "left join fetch s.gender g " 
				+ "left join fetch s.equity e " 
				+ "left join fetch s.nationality n " 
				+ "left join fetch comp.sicCode sicCode "
				+ "left join fetch comp.residentialAddress resiA " 
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
				+ "where o.sdf.id = :sdfId " 
				+ "order by o.createDate desc"; 

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", sdfId);
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
	}
	
	

	/**
	 * By For SDFCompany.
	 *
	 * @param sdfId
	 *            the sdf id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> findByForSDFCompanyID(Long id) throws Exception {
		String hql = "select o from SDFCompanyHistory o " 
				    + "left join fetch o.forSdfCompany f " 
				    + "where o.forSdfCompany.id = :id " ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> bySDFPrimary(Long sdfId) throws Exception {
		String hql = "select o from SDFCompanyHistory o " 
				+ "left join fetch o.company comp " 
				+ "left join fetch o.sdf s " 
				+ "left join fetch s.gender g " 
				+ "left join fetch s.equity e " 
				+ "left join fetch s.nationality n " 
				+ "left join fetch comp.sicCode sicCode "
				+ "left join fetch comp.residentialAddress resiA " 
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
				+ "where o.sdf.id = :sdfId " 
				+ "and o.company.approvalDate is null "
				+ "and comp.linkedCompany is null "
				+ "and sdfType.setmisCode = :primaryCode " 
				+ "order by comp.companyStatus";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", sdfId);
		parameters.put("primaryCode", "Primary");
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
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
	public List<SDFCompanyHistory> bySDFChild(Long sdfId) throws Exception {
		String hql = "select o from SDFCompanyHistory o left join fetch o.company comp " 
				+ " left join fetch o.sdf s  " 
				+ "left join fetch comp.sicCode sicCode " 
				+ "left join fetch comp.residentialAddress resiA "
				+ "left join fetch resiA.municipality resiAM "
				+ "left join fetch comp.postalAddress postalA "
				+ "left join fetch postalA.municipality postalAAM " 
				+ "left join fetch comp.registeredAddress regA "
				+ "left join fetch regA.municipality regAM " 
				+ "left join fetch comp.chamber chamber " 
				+ "left join fetch o.sdfType sdfType "
				+ "where o.sdf.id = :sdfId " 
				+ "and o.company.linkedCompany is not null " 
				+ "order by comp.companyStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", sdfId);
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
	}

	/**
	 * Linked company.
	 *
	 * @param SDFCompanyHistory
	 *            the sdf company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> linkedCompany(SDFCompanyHistory SDFCompanyHistory) throws Exception {
		String hql = "select o from SDFCompanyHistory o " 
				+ "left join fetch o.company comp " 
				+ "left join fetch comp.linkedCompany lc " 
				+ "left join fetch o.sdf s  " 
				+ "left join fetch comp.sicCode sicCode " 
				+ "left join fetch comp.residentialAddress resiA "
				+ "left join fetch resiA.municipality resiAM "
				+ "left join fetch comp.postalAddress postalA "
				+ "left join fetch postalA.municipality postalAAM " 
				+ "left join fetch comp.registeredAddress regA " 
				+ "left join fetch regA.municipality regAM " 
				+ "left join fetch comp.chamber chamber " 
				+ "left join fetch o.sdfType sdfType "
				+ "where o.company.linkedCompany.id = :companyID " 
				+ "and o.sdf.id = :sdfID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", SDFCompanyHistory.getCompany().getId());
		parameters.put("sdfID", SDFCompanyHistory.getSdf().getId());
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
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
	public SDFCompanyHistory findByCompanyAndUser(Long companyId, Long userId) throws Exception {
		String hql = "select o from SDFCompanyHistory o where o.company.id = :companyId and o.sdf.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		return (SDFCompanyHistory) super.getUniqueResult(hql, parameters);
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
	public List<SDFCompanyHistory> findByCompanySignOff(Long companyId, boolean signOff) throws Exception {
		String hql = "select o from SDFCompanyHistory o left join fetch o.company c left join fetch o.sdf s left join fetch o.sdfType st where c.id = :companyId and st.signOffWsp = :signOff";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("signOff", signOff);
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> findByCompany(Long companyId) throws Exception {
		String hql = "select o from SDFCompanyHistory o left join fetch o.company c left join fetch o.sdf s left join fetch o.sdfType st where c.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
	}

	public long findByCompanyCount(Long companyId) throws Exception {
		String hql = "select count(o) from SDFCompanyHistory o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findByUser(Users users) throws Exception {
		String hql = "select distinct(o.company) from SDFCompanyHistory o where o.sdf.id = :sdfId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdfId", users.getId());
		return (List<Company>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> allSDFCompanyHistoryByCompany(Company company) throws Exception {
		String hql = "select o from SDFCompanyHistory o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		return (List<SDFCompanyHistory>) super.getList(hql, parameters);
	}

}
