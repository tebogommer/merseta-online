package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Language;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class LanguageDAO.
 */
public class LanguageDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Language.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Language}
	 * @throws Exception global exception
	 * @see    Language
	 */
	@SuppressWarnings("unchecked")
	public List<Language> allLanguage() throws Exception {
		return (List<Language>)super.getList("select o from Language o");
	}

	/**
	 * Get all Language between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Language}
	 * @throws Exception global exception
	 * @see    Language
	 */
	@SuppressWarnings("unchecked")
	public List<Language> allLanguage(Long from, int noRows) throws Exception {
	 	String hql = "select o from Language o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Language>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Language}
	 * @throws Exception global exception
	 * @see    Language
	 */
	public Language findByKey(Long id) throws Exception {
	 	String hql = "select o from Language o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Language)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Language by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Language}
	 * @throws Exception global exception
	 * @see    Language
	 */
	@SuppressWarnings("unchecked")
	public List<Language> findByName(String description) throws Exception {
	 	String hql = "select o from Language o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Language>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param language the language
	 * @return the language
	 * @throws Exception the exception
	 */
	public Language findUniqueCode(Language language) throws Exception {
	 	String hql = "select o from Language o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (language.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", language.getId());
	 	}
	   
	    parameters.put("code", language.getCode());
		return (Language)super.getUniqueResult(hql, parameters);
	}
}

