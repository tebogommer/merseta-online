package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AppraisalCategoryCodeDAO extends AbstractDAO  {
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AppraisalCategoryCode
 	 * @author TechFinium 
 	 * @see    AppraisalCategoryCode
 	 * @return a list of {@link haj.com.entity.AppraisalCategoryCode}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCategoryCode> allAppraisalCategoryCode() throws Exception {
		return (List<AppraisalCategoryCode>)super.getList("select o from AppraisalCategoryCode o");
	}

	/**
	 * Get all AppraisalCategoryCode between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AppraisalCategoryCode
 	 * @return a list of {@link haj.com.entity.AppraisalCategoryCode}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCategoryCode> allAppraisalCategoryCode(Long from, int noRows) throws Exception {
	 	String hql = "select o from AppraisalCategoryCode o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AppraisalCategoryCode>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AppraisalCategoryCode
 	 * @return a {@link haj.com.entity.AppraisalCategoryCode}
 	 * @throws Exception global exception
 	 */
	public AppraisalCategoryCode findByKey(Long id) throws Exception {
	 	String hql = "select o from AppraisalCategoryCode o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AppraisalCategoryCode)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AppraisalCategoryCode by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AppraisalCategoryCode
  	 * @return a list of {@link haj.com.entity.AppraisalCategoryCode}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalCategoryCode> findByName(String description) throws Exception {
	 	String hql = "select o from AppraisalCategoryCode o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AppraisalCategoryCode>)super.getList(hql, parameters);
	}
	
}

