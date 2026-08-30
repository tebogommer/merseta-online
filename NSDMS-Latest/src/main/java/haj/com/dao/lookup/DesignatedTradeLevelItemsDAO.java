package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DesignatedTradeLevelItems;

public class DesignatedTradeLevelItemsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DesignatedTradeLevelItems
 	 * @author TechFinium 
 	 * @see    DesignatedTradeLevelItems
 	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevelItems> allDesignatedTradeLevelItems() throws Exception {
		return (List<DesignatedTradeLevelItems>)super.getList("select o from DesignatedTradeLevelItems o");
	}

	/**
	 * Get all DesignatedTradeLevelItems between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DesignatedTradeLevelItems
 	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevelItems> allDesignatedTradeLevelItems(Long from, int noRows) throws Exception {
	 	String hql = "select o from DesignatedTradeLevelItems o " ;
	    Map<String, Object> parameters = new HashMap<>();
		return (List<DesignatedTradeLevelItems>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DesignatedTradeLevelItems
 	 * @return a {@link haj.com.entity.DesignatedTradeLevelItems}
 	 * @throws Exception global exception
 	 */
	public DesignatedTradeLevelItems findByKey(Long id) throws Exception {
	 	String hql = "select o from DesignatedTradeLevelItems o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (DesignatedTradeLevelItems)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DesignatedTradeLevelItems by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DesignatedTradeLevelItems
  	 * @return a list of {@link haj.com.entity.DesignatedTradeLevelItems}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevelItems> findByName(String description) throws Exception {
	 	String hql = "select o from DesignatedTradeLevelItems o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DesignatedTradeLevelItems>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevelItems> findByDesignatedTradeLevel(Long designatedTradeLevelId) throws Exception {
	 	String hql = "select o from DesignatedTradeLevelItems o where o.designatedTradeLevel.id = :designatedTradeLevelId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("designatedTradeLevelId", designatedTradeLevelId);
		return (List<DesignatedTradeLevelItems>)super.getList(hql, parameters);
	}
	
}