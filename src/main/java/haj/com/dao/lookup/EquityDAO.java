package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Equity;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EquityDAO.
 */
public class EquityDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Equity.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Equity}
	 * @throws Exception global exception
	 * @see    Equity
	 */
	@SuppressWarnings("unchecked")
	public List<Equity> allEquity() throws Exception {
		return (List<Equity>)super.getList("select o from Equity o");
	}

	/**
	 * Get all Equity between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Equity}
	 * @throws Exception global exception
	 * @see    Equity
	 */
	@SuppressWarnings("unchecked")
	public List<Equity> allEquity(Long from, int noRows) throws Exception {
	 	String hql = "select o from Equity o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Equity>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Equity}
	 * @throws Exception global exception
	 * @see    Equity
	 */
	public Equity findByKey(Long id) throws Exception {
	 	String hql = "select o from Equity o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Equity)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the equity
	 * @throws Exception the exception
	 */
	public Equity findByCode(String code) throws Exception {
	 	String hql = "select o from Equity o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (Equity)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Equity by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Equity}
	 * @throws Exception global exception
	 * @see    Equity
	 */
	@SuppressWarnings("unchecked")
	public List<Equity> findByName(String description) throws Exception {
	 	String hql = "select o from Equity o where o.description like  :description";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Equity>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Equity> findByNameEcludeOtherAndUnkown(String description) throws Exception {
	 	String hql = "select o from Equity o where o.description like  :description and o.id <> 4 and o.id <> 5";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Equity>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param equity the equity
	 * @return the equity
	 * @throws Exception the exception
	 */
	public Equity findUniqueCode(Equity equity) throws Exception {
	 	String hql = "select o from Equity o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (equity.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", equity.getId());
	 	}
	   
	    parameters.put("code", equity.getCode());
		return (Equity)super.getUniqueResult(hql, parameters);
	}
}

