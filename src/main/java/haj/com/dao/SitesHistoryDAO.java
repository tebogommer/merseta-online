package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.entity.SitesHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesHistory.
 */
public class SitesHistoryDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SitesHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> allSitesHistory() throws Exception {
		return (List<SitesHistory>)super.getList("select o from SitesHistory o");
	}

	/**
	 * Get all SitesHistory between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> allSitesHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from SitesHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SitesHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	public SitesHistory findByKey(Long id) throws Exception {
	 	//String hql = "select o from SitesHistory o where o.id = :id " ;
		String hql = "select o from SitesHistory o left join fetch o.registeredAddress ra left join fetch ra.municipality  where o.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SitesHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SitesHistory by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception global exception
	 * @see    Sites
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> findByName(String description) throws Exception {
	 	String hql = "select o from SitesHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SitesHistory>)super.getList(hql, parameters);
	}
	
	/**
	 * Find SitesHistory by company.
	 *
	 * @author TechFinium
	 * @param company the company
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> findByCompany(Company company) throws Exception {
	 	String hql = "select o from SitesHistory o left join fetch o.registeredAddress ra left join fetch ra.municipality where o.company.id = :companyID order by o.createDate desc" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
		return (List<SitesHistory>)super.getList(hql, parameters);
	}
	
	public Integer countByCompany(Company company) throws Exception {
	 	String hql = "select count(o) from SitesHistory o where o.company.id = :companyID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Find SitesHistory by Site.
	 *
	 * @author TechFinium
	 * @param Site the site
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> findBySite(Sites site) throws Exception {
	 	String hql = "select o from SitesHistory o where o.forSites.id = :siteID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("siteID", site.getId());
		return (List<SitesHistory>)super.getList(hql, parameters);
	}
	
	public Long findSumByCompany(Company company) throws Exception {
	 	String hql = "select sum(COALESCE(o.numberOfEmployees,0)) from SitesHistory o where o.company.id = :companyID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyID", company.getId());
		return (Long)super.getUniqueResult(hql, parameters);
	}
	
	
	/**
	 * Find SitesHistory for company by company name .
	 *
	 * @author TechFinium
	 * @param companyName the companyName, company the company
	 * @return a list of {@link haj.com.entity.SitesHistory}
	 * @throws Exception global exception
	 * @see    SitesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<SitesHistory> findByNameAndCompany(String companyName, Company company) throws Exception
	{
		String hql = "select o from SitesHistory o"
				+ " left join fetch o.registeredAddress re"
				+ " left join fetch re.municipality"
				+ " where o.companyName like  :companyName"
				+ " and o.company.id =:companyId"
				+ " order by o.companyName ";
		
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyName","%"+companyName.trim()+"%");
	    parameters.put("companyId", company.getId());
	    return (List<SitesHistory>) super.getList(hql, parameters);
	}
	
	
}

