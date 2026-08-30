package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.AppraisalCategories;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AppraisalCategoriesDAO extends AbstractDAO  {
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AppraisalCategories
 	 * @author TechFinium 
 	 * @see    AppraisalCategories
 	 * @return a list of {@link haj.com.entity.AppraisalCategories}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCategories> allAppraisalCategories() throws Exception {
		return (List<AppraisalCategories>)super.getList("select o from AppraisalCategories o");
	}

	/**
	 * Get all AppraisalCategories between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AppraisalCategories
 	 * @return a list of {@link haj.com.entity.AppraisalCategories}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCategories> allAppraisalCategories(Long from, int noRows) throws Exception {
	 	String hql = "select o from AppraisalCategories o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AppraisalCategories>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AppraisalCategories
 	 * @return a {@link haj.com.entity.AppraisalCategories}
 	 * @throws Exception global exception
 	 */
	public AppraisalCategories findByKey(Long id) throws Exception {
	 	String hql = "select o from AppraisalCategories o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AppraisalCategories)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AppraisalCategories by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AppraisalCategories
  	 * @return a list of {@link haj.com.entity.AppraisalCategories}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCategories> findByName(String description) throws Exception {
	 	String hql = "select o from AppraisalCategories o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AppraisalCategories>)super.getList(hql, parameters);
	}
	
}

