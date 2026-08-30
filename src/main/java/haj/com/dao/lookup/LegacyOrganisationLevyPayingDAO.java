package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyOrganisationLevyPaying;

public class LegacyOrganisationLevyPayingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyOrganisationLevyPaying
 	 * @author TechFinium 
 	 * @see    LegacyOrganisationLevyPaying
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPaying() throws Exception {
		return (List<LegacyOrganisationLevyPaying>)super.getList("select o from LegacyOrganisationLevyPaying o");
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPayingWithLimit(int numberOfEntries) throws Exception {
		return (List<LegacyOrganisationLevyPaying>)super.getList("select o from LegacyOrganisationLevyPaying o", numberOfEntries);
	}

	/**
	 * Get all LegacyOrganisationLevyPaying between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyOrganisationLevyPaying
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPaying(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyOrganisationLevyPaying o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyOrganisationLevyPaying>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyOrganisationLevyPaying
 	 * @return a {@link haj.com.entity.LegacyOrganisationLevyPaying}
 	 * @throws Exception global exception
 	 */
	public LegacyOrganisationLevyPaying findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyOrganisationLevyPaying o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyOrganisationLevyPaying)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyOrganisationLevyPaying by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyOrganisationLevyPaying
  	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyOrganisationLevyPaying o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyOrganisationLevyPaying>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationLevyPaying o")).intValue();
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public Integer countAllSdlNumberOnSarsEmployerFileEmpty() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationLevyPaying o where o.sdlNumberOnSarsEmployerFile is null")).intValue();
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public Integer countAllSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationLevyPaying o where o.sdlNumberOnSarsEmployerFile = :value " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public Integer countAllMainSdlNumberOnSarsEmployerFileEmpty() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationLevyPaying o where o.mainSdlNumberOnSarsEmployerFile is null")).intValue();
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public Integer countAllMainSdlNumberOnSarsEmployerFileValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationLevyPaying o where o.mainSdlNumberOnSarsEmployerFile = :value " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

