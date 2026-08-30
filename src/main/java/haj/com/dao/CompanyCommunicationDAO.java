package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyCommunication;

public class CompanyCommunicationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyCommunication
 	 * @author TechFinium 
 	 * @see    CompanyCommunication
 	 * @return a list of {@link haj.com.entity.CompanyCommunication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyCommunication> allCompanyCommunication() throws Exception {
		return (List<CompanyCommunication>)super.getList("select o from CompanyCommunication o");
	}

	/**
	 * Get all CompanyCommunication between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyCommunication
 	 * @return a list of {@link haj.com.entity.CompanyCommunication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyCommunication> allCompanyCommunication(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyCommunication o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyCommunication>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyCommunication
 	 * @return a {@link haj.com.entity.CompanyCommunication}
 	 * @throws Exception global exception
 	 */
	public CompanyCommunication findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyCommunication o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyCommunication)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyCommunication by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyCommunication
  	 * @return a list of {@link haj.com.entity.CompanyCommunication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyCommunication> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyCommunication o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyCommunication>)super.getList(hql, parameters);
	}
}

