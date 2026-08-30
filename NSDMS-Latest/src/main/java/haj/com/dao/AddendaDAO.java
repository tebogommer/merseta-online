package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.Addenda;

public class AddendaDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Addenda
 	 * @author TechFinium 
 	 * @see    Addenda
 	 * @return a list of {@link haj.com.entity.Addenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Addenda> allAddenda() throws Exception {
		return (List<Addenda>)super.getList("select o from Addenda o");
	}

	/**
	 * Get all Addenda between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Addenda
 	 * @return a list of {@link haj.com.entity.Addenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Addenda> allAddenda(Long from, int noRows) throws Exception {
	 	String hql = "select o from Addenda o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Addenda>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Addenda
 	 * @return a {@link haj.com.entity.Addenda}
 	 * @throws Exception global exception
 	 */
	public Addenda findByKey(Long id) throws Exception {
	 	String hql = "select o from Addenda o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Addenda)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Addenda by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Addenda
  	 * @return a list of {@link haj.com.entity.Addenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Addenda> findByName(String description) throws Exception {
	 	String hql = "select o from Addenda o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Addenda>)super.getList(hql, parameters);
	}
}

