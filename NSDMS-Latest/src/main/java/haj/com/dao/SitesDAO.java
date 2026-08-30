package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesDAO.
 */
public class SitesDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Sites.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> allSites() throws Exception {
		return (List<Sites>)super.getList("select o from Sites o");
	}

	/**
	 * Get all Sites between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> allSites(Long from, int noRows) throws Exception {
	 	String hql = "select o from Sites o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Sites>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	public Sites findByKey(Long id) throws Exception {
	 	//String hql = "select o from Sites o where o.id = :id " ;
		String hql = "select o from Sites o left join fetch o.registeredAddress ra left join fetch ra.municipality  where o.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Sites)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Sites by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> findByName(String description) throws Exception {
	 	String hql = "select o from Sites o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Sites>)super.getList(hql, parameters);
	}
	
	/**
	 * Find Sites by description.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> findByCompany(Company company) throws Exception {
	 	String hql = "select o from Sites o left join fetch o.registeredAddress ra left join fetch ra.municipality where o.company.id = :companyID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
		return (List<Sites>)super.getList(hql, parameters);
	}
	
	public Long findSumByCompany(Company company) throws Exception {
	 	String hql = "select sum(COALESCE(o.numberOfEmployees,0)) from Sites o where o.company.id = :companyID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
	
	/**
	 * Find Sites for company by company name .
	 *
	 * @author TechFinium
	 * @param companyName the companyName, company the company
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> findByNameAndCompany(String companyName, Company company) throws Exception {
		String hql = "select o from Sites o"
				+ " left join fetch o.registeredAddress re"
				+ " left join fetch re.municipality"
				+ " where o.companyName like  :companyName"
				+ " and o.company.id =:companyId"
				+ " order by o.companyName ";
		
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName","%"+companyName.trim()+"%");
	    parameters.put("companyId", company.getId());
	    return (List<Sites>) super.getList(hql, parameters);
	}
	
	public Integer countAllSitesBySiteNumberAssigned() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from Sites o where o.companySiteNumber <> null")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Sites> allSitesWhereSiteNumberNotAssigned() throws Exception {
		return (List<Sites>)super.getList("select o from Sites o where o.companySiteNumber is null");
	}
	
	public Integer countAllSitesWhereSiteNumberNotAssigned() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from Sites o where o.companySiteNumber is null")).intValue();
	}	
	
	@SuppressWarnings("unchecked")
	public List<Sites> findByCompanyTraining(Company company) throws Exception {
	 	String hql = "select o from Sites o left join fetch o.registeredAddress ra left join fetch ra.municipality where o.company.id = :companyID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
		return (List<Sites>)super.getList(hql, parameters);
	}
	
}