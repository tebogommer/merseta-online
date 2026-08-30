package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacyPerson;

public class LegacyOrganisationSitesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyOrganisationSites
 	 * @author TechFinium 
 	 * @see    LegacyOrganisationSites
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSites() throws Exception {
		return (List<LegacyOrganisationSites>)super.getList("select o from LegacyOrganisationSites o");
	}
	
	public Integer countAllLegacyOrganisationSitesNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationSites o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSitesNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyOrganisationSites o where o.processed = false";
		return (List<LegacyOrganisationSites>) super.getList(hql,  numberOfEntries);
	}

	/**
	 * Get all LegacyOrganisationSites between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyOrganisationSites
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> allLegacyOrganisationSites(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyOrganisationSites o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyOrganisationSites>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyOrganisationSites
 	 * @return a {@link haj.com.entity.LegacyOrganisationSites}
 	 * @throws Exception global exception
 	 */
	public LegacyOrganisationSites findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyOrganisationSites o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyOrganisationSites)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyOrganisationSites by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyOrganisationSites
  	 * @return a list of {@link haj.com.entity.LegacyOrganisationSites}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyOrganisationSites o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyOrganisationSites>)super.getList(hql, parameters);
	}
	
	public Integer countBySdlNumber(String sdlNumber) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationSites o where o.sdlNumber = :sdlNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdlNumber",sdlNumber);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationSites> findBySdlNumberList(String sdlNumber) throws Exception {
	 	String hql = "select o from LegacyOrganisationSites o where o.sdlNumber = :sdlNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdlNumber",sdlNumber);
	    return (List<LegacyOrganisationSites>)super.getList(hql, parameters);
	}
	
	public LegacyOrganisationSites findBySdlNumber(String sdlNumber) throws Exception {
	 	String hql = "select o from LegacyOrganisationSites o where o.sdlNumber = :sdlNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdlNumber",sdlNumber);
		return (LegacyOrganisationSites)super.getUniqueResult(hql, parameters);
	}	
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationSites o")).intValue();
	}
	
	/* Reporting Start */
	
	// count all entries by processed value
	public Integer countAllLegacyOrganisationSitesByProcessedValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationSites o where o.processed = :value" ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
	}
	
	// Counts all Entries by duplicate Site Number Processed
	public Integer countAllLegacyOrganisationSitesDuplicateSiteNumbers(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationSites o where o.duplicateSiteNumber = :value and o.processed = :processed" ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
	    parameters.put("processed", true);
		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
	}
	
	// Counts all Entries by MainSdlNumberOnSarsEmployerFile Processed
	public Integer countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationSites o where o.mainSdlNumberOnSarsEmployerFile = :value and o.processed = :processed" ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
	    parameters.put("processed", true);
		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
	}
	
	// Counts all Entries by LinkedSdlNumberOnSarsEmployerFile Processed
	public Integer countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyOrganisationSites o where o.linkedSdlNumberOnSarsEmployerFile = :value and o.processed = :processed" ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
	    parameters.put("processed", true);
		return ((Long)super.getUniqueResult(hql,parameters)).intValue();
	}
	
	// counts all entries NotLinkedToCompany Processed
	public Integer countAllLegacyOrganisationSitesNotLinkedToCompany() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyOrganisationSites o where o.company is null and o.processed = true")).intValue();
	}
	/* Reporting End */
}

