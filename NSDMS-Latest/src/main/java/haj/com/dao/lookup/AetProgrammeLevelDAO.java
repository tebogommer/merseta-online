package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.AetProgrammeLevel;

public class AetProgrammeLevelDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AetProgrammeLevel
 	 * @author TechFinium 
 	 * @see    AetProgrammeLevel
 	 * @return a list of {@link haj.com.entity.AetProgrammeLevel}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AetProgrammeLevel> allAetProgrammeLevel() throws Exception {
		return (List<AetProgrammeLevel>)super.getList("select o from AetProgrammeLevel o");
	}

	/**
	 * Get all AetProgrammeLevel between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AetProgrammeLevel
 	 * @return a list of {@link haj.com.entity.AetProgrammeLevel}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AetProgrammeLevel> allAetProgrammeLevel(Long from, int noRows) throws Exception {
	 	String hql = "select o from AetProgrammeLevel o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AetProgrammeLevel>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AetProgrammeLevel
 	 * @return a {@link haj.com.entity.AetProgrammeLevel}
 	 * @throws Exception global exception
 	 */
	public AetProgrammeLevel findByKey(Long id) throws Exception {
	 	String hql = "select o from AetProgrammeLevel o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AetProgrammeLevel)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AetProgrammeLevel by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AetProgrammeLevel
  	 * @return a list of {@link haj.com.entity.AetProgrammeLevel}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AetProgrammeLevel> findByName(String description) throws Exception {
	 	String hql = "select o from AetProgrammeLevel o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AetProgrammeLevel>)super.getList(hql, parameters);
	}
}

