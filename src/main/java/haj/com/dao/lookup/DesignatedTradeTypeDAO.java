package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DesignatedTradeType;

public class DesignatedTradeTypeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DesignatedTradeType
 	 * @author TechFinium 
 	 * @see    DesignatedTradeType
 	 * @return a list of {@link haj.com.entity.DesignatedTradeType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeType> allDesignatedTradeType() throws Exception {
		return (List<DesignatedTradeType>)super.getList("select o from DesignatedTradeType o");
	}

	/**
	 * Get all DesignatedTradeType between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DesignatedTradeType
 	 * @return a list of {@link haj.com.entity.DesignatedTradeType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeType> allDesignatedTradeType(Long from, int noRows) throws Exception {
	 	String hql = "select o from DesignatedTradeType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DesignatedTradeType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DesignatedTradeType
 	 * @return a {@link haj.com.entity.DesignatedTradeType}
 	 * @throws Exception global exception
 	 */
	public DesignatedTradeType findByKey(Long id) throws Exception {
	 	String hql = "select o from DesignatedTradeType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DesignatedTradeType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DesignatedTradeType by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DesignatedTradeType
  	 * @return a list of {@link haj.com.entity.DesignatedTradeType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeType> findByName(String description) throws Exception {
	 	String hql = "select o from DesignatedTradeType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DesignatedTradeType>)super.getList(hql, parameters);
	}
}

