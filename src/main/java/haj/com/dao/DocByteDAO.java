package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DocByte;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DocByteDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DocByte
 	 * @author TechFinium 
 	 * @see    DocByte
 	 * @return a list of {@link haj.com.entity.DocByte}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DocByte> allDocByte() throws Exception {
		return (List<DocByte>)super.getList("select o from DocByte o");
	}

	/**
	 * Get all DocByte between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DocByte
 	 * @return a list of {@link haj.com.entity.DocByte}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DocByte> allDocByte(Long from, int noRows) throws Exception {
	 	String hql = "select o from DocByte o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DocByte>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DocByte
 	 * @return a {@link haj.com.entity.DocByte}
 	 * @throws Exception global exception
 	 */
	public DocByte findByKey(Long id) throws Exception {
	 	String hql = "select o from DocByte o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DocByte)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DocByte by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DocByte
  	 * @return a list of {@link haj.com.entity.DocByte}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DocByte> findByName(String description) throws Exception {
	 	String hql = "select o from DocByte o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DocByte>)super.getList(hql, parameters);
	}
}

