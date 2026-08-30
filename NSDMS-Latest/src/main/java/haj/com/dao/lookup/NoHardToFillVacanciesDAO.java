package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class NoHardToFillVacanciesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NoHardToFillVacancies
 	 * @author TechFinium 
 	 * @see    NoHardToFillVacancies
 	 * @return a list of {@link haj.com.entity.NoHardToFillVacancies}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NoHardToFillVacancies> allNoHardToFillVacancies() throws Exception {
		return (List<NoHardToFillVacancies>)super.getList("select o from NoHardToFillVacancies o");
	}

	/**
	 * Get all NoHardToFillVacancies between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NoHardToFillVacancies
 	 * @return a list of {@link haj.com.entity.NoHardToFillVacancies}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NoHardToFillVacancies> allNoHardToFillVacancies(Long from, int noRows) throws Exception {
	 	String hql = "select o from NoHardToFillVacancies o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NoHardToFillVacancies>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NoHardToFillVacancies
 	 * @return a {@link haj.com.entity.NoHardToFillVacancies}
 	 * @throws Exception global exception
 	 */
	public NoHardToFillVacancies findByKey(Long id) throws Exception {
	 	String hql = "select o from NoHardToFillVacancies o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NoHardToFillVacancies)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NoHardToFillVacancies by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NoHardToFillVacancies
  	 * @return a list of {@link haj.com.entity.NoHardToFillVacancies}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NoHardToFillVacancies> findByName(String description) throws Exception {
	 	String hql = "select o from NoHardToFillVacancies o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NoHardToFillVacancies>)super.getList(hql, parameters);
	}
}

