package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyLearnershipAssessment3;
import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;

public class LegacyModeratorLearnershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyModeratorLearnership
 	 * @author TechFinium 
 	 * @see    LegacyModeratorLearnership
 	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorLearnership> allLegacyModeratorLearnership() throws Exception {
		return (List<LegacyModeratorLearnership>)super.getList("select o from LegacyModeratorLearnership o");
	}

	/**
	 * Get all LegacyModeratorLearnership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyModeratorLearnership
 	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorLearnership> allLegacyModeratorLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyModeratorLearnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyModeratorLearnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorLearnership> findByModeratorRegNo(String moderatorRegNo) throws Exception {
	 	String hql = "select o from LegacyModeratorLearnership o where o.moderatorRegNo = :moderatorRegNo and o.learnership is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("moderatorRegNo", moderatorRegNo.trim());
		return (List<LegacyModeratorLearnership>)super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyModeratorLearnership
 	 * @return a {@link haj.com.entity.LegacyModeratorLearnership}
 	 * @throws Exception global exception
 	 */
	public LegacyModeratorLearnership findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyModeratorLearnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyModeratorLearnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyModeratorLearnership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyModeratorLearnership
  	 * @return a list of {@link haj.com.entity.LegacyModeratorLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorLearnership> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyModeratorLearnership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyModeratorLearnership>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyModeratorLearnership o")).intValue();
	}
	
	public Integer countAllLegacyModeratorLearnershipNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyModeratorLearnership o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyModeratorLearnership> allLegacyModeratorLearnershipNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyModeratorLearnership o where o.processed = false";
		return (List<LegacyModeratorLearnership>) super.getList(hql, numberOfEntries);
	}
}

