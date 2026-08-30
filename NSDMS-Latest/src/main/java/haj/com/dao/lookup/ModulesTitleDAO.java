package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ModulesTitle;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ModulesTitleDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ModulesTitle
 	 * @author TechFinium 
 	 * @see    ModulesTitle
 	 * @return a list of {@link haj.com.entity.ModulesTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesTitle> allModulesTitle() throws Exception {
		return (List<ModulesTitle>)super.getList("select o from ModulesTitle o");
	}

	/**
	 * Get all ModulesTitle between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ModulesTitle
 	 * @return a list of {@link haj.com.entity.ModulesTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesTitle> allModulesTitle(Long from, int noRows) throws Exception {
	 	String hql = "select o from ModulesTitle o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ModulesTitle>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ModulesTitle
 	 * @return a {@link haj.com.entity.ModulesTitle}
 	 * @throws Exception global exception
 	 */
	public ModulesTitle findByKey(Long id) throws Exception {
	 	String hql = "select o from ModulesTitle o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ModulesTitle)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ModulesTitle by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ModulesTitle
  	 * @return a list of {@link haj.com.entity.ModulesTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ModulesTitle> findByName(String description) throws Exception {
	 	String hql = "select o from ModulesTitle o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ModulesTitle>)super.getList(hql, parameters);
	}
}

