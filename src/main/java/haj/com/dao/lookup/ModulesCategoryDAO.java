package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ModulesCategory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ModulesCategoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ModulesCategory
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 * @return a list of {@link haj.com.entity.ModulesCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesCategory> allModulesCategory() throws Exception {
		return (List<ModulesCategory>)super.getList("select o from ModulesCategory o");
	}

	/**
	 * Get all ModulesCategory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ModulesCategory
 	 * @return a list of {@link haj.com.entity.ModulesCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesCategory> allModulesCategory(Long from, int noRows) throws Exception {
	 	String hql = "select o from ModulesCategory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ModulesCategory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ModulesCategory
 	 * @return a {@link haj.com.entity.ModulesCategory}
 	 * @throws Exception global exception
 	 */
	public ModulesCategory findByKey(Long id) throws Exception {
	 	String hql = "select o from ModulesCategory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ModulesCategory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ModulesCategory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ModulesCategory
  	 * @return a list of {@link haj.com.entity.ModulesCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesCategory> findByName(String description) throws Exception {
	 	String hql = "select o from ModulesCategory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ModulesCategory>)super.getList(hql, parameters);
	}
}

