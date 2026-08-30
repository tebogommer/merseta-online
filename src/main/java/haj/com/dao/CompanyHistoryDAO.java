package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.CompanyHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CompanyHistoryDAO extends AbstractDAO {

	private static final String leftJoins = " " + "left join fetch o.residentialAddress cra " + "left join fetch cra.municipality craM " + "left join fetch o.postalAddress cpa " + "left join fetch cpa.municipality cpaM " + "left join fetch o.registeredAddress crega "
			+ "left join fetch crega.municipality cregaM " + "left join fetch o.chamber cch " + "left join fetch o.sicCode csc " + "left join fetch o.institutionType cit " + "left join fetch o.organisationType cot " + "left join fetch o.linkedCompany lc "
			+ "left join fetch lc.residentialAddress cral " + "left join fetch cral.municipality craMl " + "left join fetch lc.postalAddress cpal " + "left join fetch cpal.municipality cpaMl";

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyHistory
	 * 
	 * @author TechFinium
	 * @see CompanyHistory
	 * @return a list of {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHistory> allCompanyHistory() throws Exception {
		return (List<CompanyHistory>) super.getList("select o from CompanyHistory o");
	}

	/**
	 * Get all CompanyHistory between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see CompanyHistory
	 * @return a list of {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHistory> allCompanyHistory(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<CompanyHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CompanyHistory
	 * @return a {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             global exception
	 */
	public CompanyHistory findByKey(Long id) throws Exception {
		String hql = "select o from CompanyHistory o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyHistory) super.getUniqueResult(hql, parameters);
	}

	public Company findCompanyByKey(Long id) throws Exception {
		String hql = "select o from Company o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Company) super.getUniqueResult(hql, parameters);
	}
	
	public Company findByKeyWithJoins(Long id) throws Exception {
		String hql = "select o from Company o "+leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Company) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyHistory by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see CompanyHistory
	 * @return a list of {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHistory> findByName(String description) throws Exception {
		String hql = "select o from CompanyHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CompanyHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyHistory> findByCompany(Company company) throws Exception {
		String hql = "select o from CompanyHistory o where o.forCompany.id = :companyID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<CompanyHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyHistory> findByCompanyLatest(Company company) throws Exception {
		String hql = "select o from CompanyHistory o " + leftJoins + " where o.forCompany.id = :companyID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<CompanyHistory>) super.getList(hql, parameters, 1);
	}
	

	@SuppressWarnings("unchecked")
	public List<CompanyHistory> findByCompanyOldest(Company company) throws Exception {
		String hql = "select o from CompanyHistory o " + leftJoins + " where o.forCompany.id = :companyID order by o.createDate ASC";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<CompanyHistory>) super.getList(hql, parameters, 1);
	}

}
