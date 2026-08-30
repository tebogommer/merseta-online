package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LearnershipDataUpload;

public class LearnershipDataUploadDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnershipDataUpload
 	 * @author TechFinium 
 	 * @see    LearnershipDataUpload
 	 * @return a list of {@link haj.com.entity.LearnershipDataUpload}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDataUpload> allLearnershipDataUpload() throws Exception {
		return (List<LearnershipDataUpload>)super.getList("select o from LearnershipDataUpload o");
	}

	/**
	 * Get all LearnershipDataUpload between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LearnershipDataUpload
 	 * @return a list of {@link haj.com.entity.LearnershipDataUpload}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDataUpload> allLearnershipDataUpload(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnershipDataUpload o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LearnershipDataUpload>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LearnershipDataUpload
 	 * @return a {@link haj.com.entity.LearnershipDataUpload}
 	 * @throws Exception global exception
 	 */
	public LearnershipDataUpload findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnershipDataUpload o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LearnershipDataUpload)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnershipDataUpload by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LearnershipDataUpload
  	 * @return a list of {@link haj.com.entity.LearnershipDataUpload}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDataUpload> findByName(String description) throws Exception {
	 	String hql = "select o from LearnershipDataUpload o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnershipDataUpload>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LearnershipDataUpload o")).intValue();
	}
}

