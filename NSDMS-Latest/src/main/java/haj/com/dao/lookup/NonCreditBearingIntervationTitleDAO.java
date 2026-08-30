package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;

public class NonCreditBearingIntervationTitleDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NonCreditBearingIntervationTitle
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
 	 * @return a list of {@link haj.com.entity.NonCreditBearingIntervationTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonCreditBearingIntervationTitle> allNonCreditBearingIntervationTitle() throws Exception {
		return (List<NonCreditBearingIntervationTitle>)super.getList("select o from NonCreditBearingIntervationTitle o");
	}

	/**
	 * Get all NonCreditBearingIntervationTitle between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NonCreditBearingIntervationTitle
 	 * @return a list of {@link haj.com.entity.NonCreditBearingIntervationTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonCreditBearingIntervationTitle> allNonCreditBearingIntervationTitle(Long from, int noRows) throws Exception {
	 	String hql = "select o from NonCreditBearingIntervationTitle o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NonCreditBearingIntervationTitle>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NonCreditBearingIntervationTitle
 	 * @return a {@link haj.com.entity.NonCreditBearingIntervationTitle}
 	 * @throws Exception global exception
 	 */
	public NonCreditBearingIntervationTitle findByKey(Long id) throws Exception {
	 	String hql = "select o from NonCreditBearingIntervationTitle o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NonCreditBearingIntervationTitle)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NonCreditBearingIntervationTitle by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NonCreditBearingIntervationTitle
  	 * @return a list of {@link haj.com.entity.NonCreditBearingIntervationTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonCreditBearingIntervationTitle> findByName(String description) throws Exception {
	 	String hql = "select o from NonCreditBearingIntervationTitle o where (o.description like :description or o.code like :description) order by o.id asc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NonCreditBearingIntervationTitle>)super.getList(hql, parameters);
	}
}

