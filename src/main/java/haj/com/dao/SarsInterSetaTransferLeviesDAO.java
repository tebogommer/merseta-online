package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.sars.SarsInterSetaTransferLevies;

public class SarsInterSetaTransferLeviesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsInterSetaTransferLevies
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 * @return a list of {@link haj.com.sars.SarsInterSetaTransferLevies}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsInterSetaTransferLevies> allSarsInterSetaTransferLevies() throws Exception {
		return (List<SarsInterSetaTransferLevies>)super.getList("select o from SarsInterSetaTransferLevies o");
	}

	/**
	 * Get all SarsInterSetaTransferLevies between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SarsInterSetaTransferLevies
 	 * @return a list of {@link haj.com.sars.SarsInterSetaTransferLevies}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsInterSetaTransferLevies> allSarsInterSetaTransferLevies(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsInterSetaTransferLevies o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsInterSetaTransferLevies>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SarsInterSetaTransferLevies
 	 * @return a {@link haj.com.sars.SarsInterSetaTransferLevies}
 	 * @throws Exception global exception
 	 */
	public SarsInterSetaTransferLevies findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsInterSetaTransferLevies o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsInterSetaTransferLevies)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SarsInterSetaTransferLevies by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SarsInterSetaTransferLevies
  	 * @return a list of {@link haj.com.sars.SarsInterSetaTransferLevies}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsInterSetaTransferLevies> findByName(String description) throws Exception {
	 	String hql = "select o from SarsInterSetaTransferLevies o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsInterSetaTransferLevies>)super.getList(hql, parameters);
	}
}

