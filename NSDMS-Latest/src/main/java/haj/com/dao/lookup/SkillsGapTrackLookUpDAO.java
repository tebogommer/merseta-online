package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SkillsGapTrackLookUp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsGapTrackLookUpDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsGapTrackLookUp
 	 * @author TechFinium 
 	 * @see    SkillsGapTrackLookUp
 	 * @return a list of {@link haj.com.entity.SkillsGapTrackLookUp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsGapTrackLookUp> allSkillsGapTrackLookUp() throws Exception {
		return (List<SkillsGapTrackLookUp>)super.getList("select o from SkillsGapTrackLookUp o");
	}

	/**
	 * Get all SkillsGapTrackLookUp between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SkillsGapTrackLookUp
 	 * @return a list of {@link haj.com.entity.SkillsGapTrackLookUp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsGapTrackLookUp> allSkillsGapTrackLookUp(Long from, int noRows) throws Exception {
	 	String hql = "select o from SkillsGapTrackLookUp o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SkillsGapTrackLookUp>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SkillsGapTrackLookUp
 	 * @return a {@link haj.com.entity.SkillsGapTrackLookUp}
 	 * @throws Exception global exception
 	 */
	public SkillsGapTrackLookUp findByKey(Long id) throws Exception {
	 	String hql = "select o from SkillsGapTrackLookUp o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SkillsGapTrackLookUp)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsGapTrackLookUp by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SkillsGapTrackLookUp
  	 * @return a list of {@link haj.com.entity.SkillsGapTrackLookUp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsGapTrackLookUp> findByName(String description) throws Exception {
	 	String hql = "select o from SkillsGapTrackLookUp o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SkillsGapTrackLookUp>)super.getList(hql, parameters);
	}
}

