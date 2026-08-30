package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LearnerMentorRatio;

public class LearnerMentorRatioDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnerMentorRatio
 	 * @author TechFinium 
 	 * @see    LearnerMentorRatio
 	 * @return a list of {@link haj.com.entity.LearnerMentorRatio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnerMentorRatio> allLearnerMentorRatio() throws Exception {
		return (List<LearnerMentorRatio>)super.getList("select o from LearnerMentorRatio o");
	}

	/**
	 * Get all LearnerMentorRatio between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LearnerMentorRatio
 	 * @return a list of {@link haj.com.entity.LearnerMentorRatio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnerMentorRatio> allLearnerMentorRatio(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnerMentorRatio o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LearnerMentorRatio>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LearnerMentorRatio
 	 * @return a {@link haj.com.entity.LearnerMentorRatio}
 	 * @throws Exception global exception
 	 */
	public LearnerMentorRatio findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnerMentorRatio o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LearnerMentorRatio)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnerMentorRatio by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LearnerMentorRatio
  	 * @return a list of {@link haj.com.entity.LearnerMentorRatio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnerMentorRatio> findByName(String description) throws Exception {
	 	String hql = "select o from LearnerMentorRatio o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnerMentorRatio>)super.getList(hql, parameters);
	}
}

