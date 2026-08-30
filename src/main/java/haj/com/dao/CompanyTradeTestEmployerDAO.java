package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyTradeTestEmployerDAO.
 */
public class CompanyTradeTestEmployerDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyTradeTestEmployer.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception global exception
	 * @see    CompanyTradeTestEmployer
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyTradeTestEmployer> allCompanyTradeTestEmployer() throws Exception {
		return (List<CompanyTradeTestEmployer>)super.getList("select o from CompanyTradeTestEmployer o");
	}

	/**
	 * Get all CompanyTradeTestEmployer between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception global exception
	 * @see    CompanyTradeTestEmployer
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyTradeTestEmployer> allCompanyTradeTestEmployer(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyTradeTestEmployer o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyTradeTestEmployer>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception global exception
	 * @see    CompanyTradeTestEmployer
	 */
	public CompanyTradeTestEmployer findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyTradeTestEmployer o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyTradeTestEmployer)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyTradeTestEmployer by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception global exception
	 * @see    CompanyTradeTestEmployer
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyTradeTestEmployer> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyTradeTestEmployer o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyTradeTestEmployer>)super.getList(hql, parameters);
	}
}

