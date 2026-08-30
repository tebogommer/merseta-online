package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DesignatedTrade;

public class DesignatedTradeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DesignatedTrade
 	 * @author TechFinium 
 	 * @see    DesignatedTrade
 	 * @return a list of {@link haj.com.entity.DesignatedTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTrade> allDesignatedTrade() throws Exception {
		return (List<DesignatedTrade>)super.getList("select o from DesignatedTrade o");
	}

	/**
	 * Get all DesignatedTrade between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DesignatedTrade
 	 * @return a list of {@link haj.com.entity.DesignatedTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTrade> allDesignatedTrade(Long from, int noRows) throws Exception {
	 	String hql = "select o from DesignatedTrade o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DesignatedTrade>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DesignatedTrade
 	 * @return a {@link haj.com.entity.DesignatedTrade}
 	 * @throws Exception global exception
 	 */
	public DesignatedTrade findByKey(Long id) throws Exception {
	 	String hql = "select o from DesignatedTrade o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DesignatedTrade)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DesignatedTrade by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DesignatedTrade
  	 * @return a list of {@link haj.com.entity.DesignatedTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTrade> findByName(String description) throws Exception {
	 	String hql = "select o from DesignatedTrade o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DesignatedTrade>)super.getList(hql, parameters);
	}
}

