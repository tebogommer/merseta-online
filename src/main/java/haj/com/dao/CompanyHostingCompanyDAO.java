package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.CompanyHostingCompany;
import haj.com.entity.HostingCompany;
import haj.com.entity.UserHostingCompany;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyHostingCompanyDAO.
 */
public class CompanyHostingCompanyDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception global exception
	 * @see    CompanyHostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHostingCompany> allCompanyHostingCompany() throws Exception {
		return (List<CompanyHostingCompany>)super.getList("select o from CompanyHostingCompany o");
	}

	/**
	 * Get all CompanyHostingCompany between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception global exception
	 * @see    CompanyHostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHostingCompany> allCompanyHostingCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyHostingCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyHostingCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception global exception
	 * @see    CompanyHostingCompany
	 */
	public CompanyHostingCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyHostingCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyHostingCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyHostingCompany by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception global exception
	 * @see    CompanyHostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHostingCompany> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyHostingCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyHostingCompany>)super.getList(hql, parameters);
	}
	
	/**
	 * Find count of UserHostingCompany by Company and HostingCompany.
	 *
	 * @author TechFinium
	 * @param hostingCompany the hosting company
	 * @param company the company
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception global exception
	 * @see    UserHostingCompany
	 */
	public Long findCountByCompanyAndHoustingCompany(HostingCompany hostingCompany, Company company) throws Exception {
	 	String hql = "select count(o) from CompanyHostingCompany o where o.hostingCompany.id = :hostingCompanyID and o.company.id = :companyID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyID", hostingCompany.getId());
	    parameters.put("companyID", company.getId());
		return (Long)super.getUniqueResult(hql, parameters);
	}
}

