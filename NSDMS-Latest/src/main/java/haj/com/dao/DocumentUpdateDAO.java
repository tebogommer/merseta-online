package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DocumentUpdate;

public class DocumentUpdateDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DocumentUpdate
 	 * @author TechFinium 
 	 * @see    DocumentUpdate
 	 * @return a list of {@link haj.com.entity.DocumentUpdate}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DocumentUpdate> allDocumentUpdate() throws Exception {
		return (List<DocumentUpdate>)super.getList("select o from DocumentUpdate o");
	}

	/**
	 * Get all DocumentUpdate between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DocumentUpdate
 	 * @return a list of {@link haj.com.entity.DocumentUpdate}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DocumentUpdate> allDocumentUpdate(Long from, int noRows) throws Exception {
	 	String hql = "select o from DocumentUpdate o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DocumentUpdate>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DocumentUpdate
 	 * @return a {@link haj.com.entity.DocumentUpdate}
 	 * @throws Exception global exception
 	 */
	public DocumentUpdate findByKey(Long id) throws Exception {
	 	String hql = "select o from DocumentUpdate o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DocumentUpdate)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DocumentUpdate by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DocumentUpdate
  	 * @return a list of {@link haj.com.entity.DocumentUpdate}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DocumentUpdate> findByName(String description) throws Exception {
	 	String hql = "select o from DocumentUpdate o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DocumentUpdate>)super.getList(hql, parameters);
	}
}

