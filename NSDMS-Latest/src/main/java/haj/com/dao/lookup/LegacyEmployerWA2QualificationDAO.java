package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyEmployerWA2Qualification;
import haj.com.entity.lookup.LegacyEmployerWA2Qualification;

public class LegacyEmployerWA2QualificationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyEmployerWA2Qualification
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2Qualification
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2Qualification() throws Exception {
		return (List<LegacyEmployerWA2Qualification>)super.getList("select o from LegacyEmployerWA2Qualification o");
	}

	/**
	 * Get all LegacyEmployerWA2Qualification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyEmployerWA2Qualification
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2Qualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Qualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyEmployerWA2Qualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyEmployerWA2Qualification
 	 * @return a {@link haj.com.entity.LegacyEmployerWA2Qualification}
 	 * @throws Exception global exception
 	 */
	public LegacyEmployerWA2Qualification findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Qualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyEmployerWA2Qualification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyEmployerWA2Qualification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyEmployerWA2Qualification
  	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Qualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Qualification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyEmployerWA2Qualification>)super.getList(hql, parameters);
	}
	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyEmployerWA2Qualification o")).intValue();
	}
	
	public Integer countAllLegacyEmployerWA2QualificationNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyEmployerWA2Qualification o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> allLegacyEmployerWA2QualificationNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyEmployerWA2Qualification o where o.processed = false";
		return (List<LegacyEmployerWA2Qualification>) super.getList(hql, numberOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> findByLevy(String linkedSld) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Qualification o where o.linkedSld = :linkedSld " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSld", linkedSld);
		return (List<LegacyEmployerWA2Qualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> findDistinctByLevy(String linkedSld) throws Exception {
	 	String hql = "select distinct(o) from LegacyEmployerWA2Qualification o where o.linkedSld = :linkedSld " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSld", linkedSld);
		return (List<LegacyEmployerWA2Qualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Qualification> findDistinctByLevy(String linkedSld, String sdlNo) throws Exception {
	 	String hql = "select distinct(o) from LegacyEmployerWA2Qualification o where o.linkedSld = :linkedSld and o.sdlNo = :sdlNo" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSld", linkedSld);
	    parameters.put("sdlNo", sdlNo);
		return (List<LegacyEmployerWA2Qualification>)super.getList(hql, parameters);
	}
}

