package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DocumentTracker;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentTrackerDAO.
 */
public class DocumentTrackerDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * All document tracker.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentTracker> allDocumentTracker() throws Exception {
		return (List<DocumentTracker>)super.getList("select o from DocumentTracker o");
	}


	/**
	 * By doc.
	 *
	 * @param docId the doc id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentTracker> byDoc(Long docId) throws Exception  {
		String hql = "select o from DocumentTracker o join fetch o.user where o.doc.id = :docId order by o.date desc";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("docId", docId);
	    return (List<DocumentTracker>)super.getList(hql, parameters);
	}


	/**
	 * Find by key.
	 *
	 * @param id the id
	 * @return the document tracker
	 * @throws Exception the exception
	 */
	public DocumentTracker findByKey(Long id) throws Exception {
	 	String hql = "select o from DocumentTracker o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DocumentTracker)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentTracker> findByName(String desc) throws Exception {
	 	String hql = "select o from DocumentTracker where o.desc like  :desc order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("desc", ""+desc.trim()+"%");
		return (List<DocumentTracker>)super.getList(hql, parameters);
	}
}

