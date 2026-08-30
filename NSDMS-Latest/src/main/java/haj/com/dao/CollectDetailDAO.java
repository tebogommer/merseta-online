package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CollectDetail;

public class CollectDetailDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CollectDetail
 	 * @author TechFinium 
 	 * @see    CollectDetail
 	 * @return a list of {@link haj.com.entity.CollectDetail}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CollectDetail> allCollectDetail() throws Exception {
		return (List<CollectDetail>)super.getList("select o from CollectDetail o");
	}

	/**
	 * Get all CollectDetail between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CollectDetail
 	 * @return a list of {@link haj.com.entity.CollectDetail}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CollectDetail> allCollectDetail(Long from, int noRows) throws Exception {
	 	String hql = "select o from CollectDetail o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CollectDetail>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CollectDetail
 	 * @return a {@link haj.com.entity.CollectDetail}
 	 * @throws Exception global exception
 	 */
	public CollectDetail findByKey(Long id) throws Exception {
	 	String hql = "select o from CollectDetail o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CollectDetail)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CollectDetail by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CollectDetail
  	 * @return a list of {@link haj.com.entity.CollectDetail}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CollectDetail> findByName(String description) throws Exception {
	 	String hql = "select o from CollectDetail o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CollectDetail>)super.getList(hql, parameters);
	}
}

