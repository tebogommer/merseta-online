package haj.com.service;

import java.util.List;

import haj.com.dao.DocumentTrackerDAO;
import haj.com.entity.Doc;
import haj.com.entity.DocumentTracker;
import haj.com.entity.Users;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentTrackerService.
 */
public class DocumentTrackerService extends AbstractService {

	/** The dao. */
	private static DocumentTrackerDAO dao = new DocumentTrackerDAO();
	
	/** The doc service. */
	private DocService docService = new DocService();

	/**
	 * All document tracker.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<DocumentTracker> allDocumentTracker() throws Exception {
	  	return dao.allDocumentTracker();
	}


	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void create(DocumentTracker entity) throws Exception  {
		this.dao.create(entity);
	}

	
	/**
	 * Creates the.
	 *
	 * @param user the user
	 * @param doc the doc
	 * @param documentTrackerEnum the document tracker enum
	 * @throws Exception the exception
	 */
	public static void create(Users user, Doc doc, DocumentTrackerEnum documentTrackerEnum) throws Exception  {
		dao.create(new DocumentTracker(doc, user, new java.util.Date(), documentTrackerEnum));
	}
	
	/**
	 * Update.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void update(DocumentTracker entity) throws Exception  {
		dao.update(entity);
	}

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void delete(DocumentTracker entity) throws Exception  {
		dao.delete(entity);
	}

	/**
	 * Find by key.
	 *
	 * @param id the id
	 * @return the document tracker
	 * @throws Exception the exception
	 */
	public DocumentTracker findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find by name.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<DocumentTracker> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * By doc.
	 *
	 * @param doc the doc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<DocumentTracker> byDoc( Doc doc) throws Exception  { 
		return dao.byDoc(doc.getId());
	}
	
	/**
	 * By root.
	 *
	 * @param originalDoc the original doc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<DocumentTracker> byRoot( Doc originalDoc) throws Exception  {
		Doc d = docService.getRootDoc(originalDoc.getDocVerions(), originalDoc);
		return byDoc(d);
	}
	
	/**
	 * Delete DocumentTracker Info.
	 *
	 * @param doc the doc
	 * @throws Exception the exception
	 */
	public void deleteByDoc(Doc doc) throws Exception
	{
		List<DocumentTracker> dtList=byDoc(doc);
		for(DocumentTracker dt:dtList)
		{
			delete(dt);
		}
	}
}
