package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyOrganisationNonLevyPaying;

public class LegacyOrganisationNonLevyPayingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyOrganisationNonLevyPaying
 	 * @author TechFinium 
 	 * @see    LegacyOrganisationNonLevyPaying
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allLegacyOrganisationNonLevyPaying() throws Exception {
		return (List<LegacyOrganisationNonLevyPaying>)super.getList("select o from LegacyOrganisationNonLevyPaying o");
	}

	/**
	 * Get all LegacyOrganisationNonLevyPaying between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyOrganisationNonLevyPaying
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allLegacyOrganisationNonLevyPaying(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyOrganisationNonLevyPaying o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyOrganisationNonLevyPaying>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyOrganisationNonLevyPaying
 	 * @return a {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
 	 * @throws Exception global exception
 	 */
	public LegacyOrganisationNonLevyPaying findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyOrganisationNonLevyPaying o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyOrganisationNonLevyPaying)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyOrganisationNonLevyPaying by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyOrganisationNonLevyPaying
  	 * @return a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyOrganisationNonLevyPaying o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyOrganisationNonLevyPaying>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationNonLevyPaying o")).intValue();
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public Integer countAllSdlNumberOnSarsEmployerFileEmpty() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationNonLevyPaying o where o.sdlNumberOnSarsEmployerFile is null")).intValue();
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public Integer countAllSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationNonLevyPaying o where o.sdlNumberOnSarsEmployerFile = :value " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public Integer countAllMainSdlNumberOnSarsEmployerFileEmpty() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationNonLevyPaying o where o.mainSdlNumberOnSarsEmployerFile is null")).intValue();
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public Integer countAllMainSdlNumberOnSarsEmployerFileValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationNonLevyPaying o where o.mainSdlNumberOnSarsEmployerFile = :value " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

