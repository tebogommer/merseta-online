package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.GpGrantBatchList;

public class GpGrantBatchListDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all GpGrantBatchList
 	 * @author TechFinium 
 	 * @see    GpGrantBatchList
 	 * @return a list of {@link haj.com.entity.GpGrantBatchList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchList> allGpGrantBatchList() throws Exception {
		return (List<GpGrantBatchList>)super.getList("select o from GpGrantBatchList o");
	}
	
	public int countAllGpGrantBatchList() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from GpGrantBatchList o")).intValue();
	}

	/**
	 * Get all GpGrantBatchList between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    GpGrantBatchList
 	 * @return a list of {@link haj.com.entity.GpGrantBatchList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchList> allGpGrantBatchList(Long from, int noRows) throws Exception {
	 	String hql = "select o from GpGrantBatchList o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<GpGrantBatchList>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    GpGrantBatchList
 	 * @return a {@link haj.com.entity.GpGrantBatchList}
 	 * @throws Exception global exception
 	 */
	public GpGrantBatchList findByKey(Long id) throws Exception {
	 	String hql = "select o from GpGrantBatchList o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (GpGrantBatchList)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find GpGrantBatchList by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    GpGrantBatchList
  	 * @return a list of {@link haj.com.entity.GpGrantBatchList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchList> findByName(String description) throws Exception {
	 	String hql = "select o from GpGrantBatchList o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<GpGrantBatchList>)super.getList(hql, parameters);
	}
}

