package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.VacancyReasons;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class VacancyReasonsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all VacancyReasons
 	 * @author TechFinium 
 	 * @see    VacancyReasons
 	 * @return a list of {@link haj.com.entity.VacancyReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<VacancyReasons> allVacancyReasons() throws Exception {
		return (List<VacancyReasons>)super.getList("select o from VacancyReasons o");
	}

	/**
	 * Get all VacancyReasons between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    VacancyReasons
 	 * @return a list of {@link haj.com.entity.VacancyReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<VacancyReasons> allVacancyReasons(Long from, int noRows) throws Exception {
	 	String hql = "select o from VacancyReasons o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<VacancyReasons>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    VacancyReasons
 	 * @return a {@link haj.com.entity.VacancyReasons}
 	 * @throws Exception global exception
 	 */
	public VacancyReasons findByKey(Long id) throws Exception {
	 	String hql = "select o from VacancyReasons o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (VacancyReasons)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find VacancyReasons by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    VacancyReasons
  	 * @return a list of {@link haj.com.entity.VacancyReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<VacancyReasons> findByName(String description) throws Exception {
	 	String hql = "select o from VacancyReasons o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<VacancyReasons>)super.getList(hql, parameters);
	}
}

