package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ModulesUnitStandardsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ModulesUnitStandards
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 * @return a list of {@link haj.com.entity.ModulesUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesUnitStandards> allModulesUnitStandards() throws Exception {
		return (List<ModulesUnitStandards>)super.getList("select o from ModulesUnitStandards o");
	}

	/**
	 * Get all ModulesUnitStandards between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ModulesUnitStandards
 	 * @return a list of {@link haj.com.entity.ModulesUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesUnitStandards> allModulesUnitStandards(Long from, int noRows) throws Exception {
	 	String hql = "select o from ModulesUnitStandards o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ModulesUnitStandards>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ModulesUnitStandards
 	 * @return a {@link haj.com.entity.ModulesUnitStandards}
 	 * @throws Exception global exception
 	 */
	public ModulesUnitStandards findByKey(Long id) throws Exception {
	 	String hql = "select o from ModulesUnitStandards o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ModulesUnitStandards)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ModulesUnitStandards by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ModulesUnitStandards
  	 * @return a list of {@link haj.com.entity.ModulesUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesUnitStandards> findByName(String description) throws Exception {
	 	String hql = "select o from ModulesUnitStandards o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ModulesUnitStandards>)super.getList(hql, parameters);
	}
}

