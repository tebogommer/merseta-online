package haj.com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.DocDAO;
import haj.com.entity.BankingDetails;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.HostingCompanyTemplates;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.LearnerDocRequirements;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.sars.SarsLeviesPaid;
import haj.com.ui.CompanyInfoUI;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DocService.
 */
public class DocService extends AbstractService {

	/** The dao. */
	private DocDAO dao = new DocDAO();

	/** The doc service. */
	private static DocService docService = null;

	/**
	 * Instance.
	 *
	 * @return the doc service
	 */
	public static synchronized DocService instance() {
		if (docService == null) {
			docService = new DocService();
		}
		return docService;
	}

	/**
	 * Get all Doc.
	 *
	 * @author TechFinium
	 * @return List<Doc>
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public List<Doc> allDoc() throws Exception {
		return dao.allDoc();
	}

	/**
	 * Create or update Doc.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public void create(Doc entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
//		
	}

	/**
	 * Update Doc.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public void update(Doc entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Doc.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public void delete(Doc entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a Doc object
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public Doc findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return Doc
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public Doc findByKeyWithJoins(Long id) throws Exception {
		return dao.findByKeyWithJoins(id);
	}

	/**
	 * Find Doc by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return List<Doc>
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public List<Doc> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Doc.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return List<Doc>
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> allDoc(int first, int pageSize) throws Exception {
		return dao.allDoc(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Doc
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Doc.class);
	}

	/**
	 * All doc.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Doc class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return List<Doc> containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> allDoc(Class<Doc> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Doc>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> allDocAndResolve(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.configDoc cd where o.targetClass = :targetClass and o.targetKey = :targetKey and o.configDoc is null";
		return resolveVersions((List<Doc>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql),false);

	}
	
	public int countWhere(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Doc o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.configDoc is null";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            Doc class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Doc entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Doc> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by users.
	 *
	 * @param user
	 *            the user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> findByUsers(Users user) throws Exception {
		return dao.findByUsers(user.getId());
	}

	/**
	 * Find by users and company null.
	 *
	 * @param userId
	 *            the user id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> findByUsersAndCompanyNull(Long userId) throws Exception {
		return dao.findByUsersAndCompanyNull(userId);
	}

	/**
	 * Gets the versions.
	 *
	 * @param d
	 *            the d
	 * @return the versions
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> getVersions(Doc d) throws Exception {
		return dao.getVersions(d);
	}

	/**
	 * Gets the versions by version number.
	 *
	 * @param d
	 *            the d
	 * @return the versions
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> findByVersionNo(Doc d) throws Exception {
		return dao.findByVersionNo(d);
	}

	/**
	 * Save.
	 *
	 * @param doc
	 *            the doc
	 * @param contents
	 *            the contents
	 * @param fileName
	 *            the file name
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	public void save(Doc doc, byte[] contents, String fileName, Users user) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		doc.setData(contents);
		doc.setOriginalFname(fileName);
		doc.setExtension(FilenameUtils.getExtension(fileName));

		doc.setUsers(user);
		doc.setVersionNo(1);
		// if (doc.getTemplate() != null) {
		// doc.setKeyword(doc.getTemplate().getTitle());
		// }

		createList.add(doc);
		createList.add(new DocByte(doc.getData(), doc));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
//		dao.create(doc);
//		dao.create(new DocByte(doc.getData(), doc));
		DocumentTrackerService.create(user, doc, DocumentTrackerEnum.Upload);
	}

	public void save(Doc doc) throws Exception {
		doc.setVersionNo(1);
		// if (doc.getTemplate() != null) {
		// doc.setKeyword(doc.getTemplate().getTitle());
		// }

		dao.create(doc);
		dao.create(new DocByte(doc.getData(), doc));
		DocumentTrackerService.create(doc.getUsers(), doc, DocumentTrackerEnum.Upload);
	}

	/**
	 * Gets the root doc.
	 *
	 * @param docs
	 *            the docs
	 * @param originalDoc
	 *            the original doc
	 * @return the root doc
	 */
	public Doc getRootDoc(List<Doc> docs, Doc originalDoc) {
		Doc t = null;
		if (docs == null || docs.size() == 0) {
			t = originalDoc;
		} else {
			for (Doc doc : docs) {
				if (doc.getDoc() == null) t = doc;
			}
		}
		return t;
	}

	/**
	 * Save.
	 *
	 * @param doc
	 *            the doc
	 * @param contents
	 *            the contents
	 * @param fileName
	 *            the file name
	 * @param user
	 *            the user
	 * @param docs
	 *            the docs
	 * @throws Exception
	 *             the exception
	 */
	public void save(Doc doc, byte[] contents, String fileName, Users user, List<Doc> docs) throws Exception {
		if (docs == null || docs.size() == 0) {
			save(doc, contents, fileName, user);
		} else {
			List<IDataEntity> createList = new ArrayList<>();
			doc.setData(contents);
			doc.setOriginalFname(fileName);
			doc.setExtension(FilenameUtils.getExtension(fileName));
			doc.setUsers(user);
			Doc orig = docs.get(0);
			doc.setDoc(getRootDoc(orig.getDocVerions(), docs.get(0)));
			doc.setVersionNo(orig.getVersionNo() + 1);
			createList.add(doc);
			createList.add(new DocByte(doc.getData(), doc));
			if (createList.size() != 0) {
				dao.createBatch(createList);
			}
//			dao.create(doc);
//			dao.create(new DocByte(doc.getData(), doc));
			DocumentTrackerService.create(user, doc.getDoc(), DocumentTrackerEnum.UploadVersion);
		}

	}

	/**
	 * Save.
	 *
	 * @param doc
	 *            the doc
	 * @param user
	 *            the user
	 * @param docs
	 *            the docs
	 * @throws Exception
	 *             the exception
	 */
	public void save(Doc doc, Users user, List<Doc> docs) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		doc.setUsers(user);
		Doc orig = docs.get(0);
		doc.setDoc(getRootDoc(orig.getDocVerions(), docs.get(0)));
		doc.setVersionNo(orig.getVersionNo() + 1);
		createList.add(doc);
		createList.add(new DocByte(doc.getData(), doc));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
//		dao.create(doc);
//		dao.create(new DocByte(doc.getData(), doc));
		DocumentTrackerService.create(user, doc.getDoc(), DocumentTrackerEnum.UploadVersion);
	}

	/**
	 * Save and Create task.
	 *
	 * @param doc
	 *            the doc
	 * @param user
	 *            the user
	 * @param docs
	 *            the docs
	 * @throws Exception
	 *             the exception
	 */
	public void saveAndCreateTask(Doc doc, Users user, List<Doc> docs) throws Exception {
		Doc orig = null;
		if(docs != null){
			orig = docs.get(0);
			doc.setDoc(getRootDoc(orig.getDocVerions(), orig));
		}else{
			orig = doc;
		}
		List<IDataEntity> createList = new ArrayList<>();
		doc.setUsers(user);
		if(orig.getVersionNo() == null) orig.setVersionNo(0);
		doc.setVersionNo(orig.getVersionNo() + 1);
//		dao.create(doc);
//		dao.create(new DocByte(doc.getData(), doc));
		//createList.add(doc);
		long docId = dao.createAndReturnLastInsertedId(doc);
		doc.setId(docId);
		createList.add(new DocByte(doc.getData(), doc));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
		DocumentTrackerService.create(user, doc.getDoc() != null ? doc.getDoc() : doc, DocumentTrackerEnum.UploadVersion);
		// Creating Change reason
		ChangeReasonService.instance().createChangeReason(doc.getId(), doc.getClass().getName(), CompanyInfoUI.changeReason);
		// Creating Task
		String desc = "Company document has been updated, please approve";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, doc.getId(), doc.getClass().getName(), true, ConfigDocProcessEnum.DOC_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

	}

	/**
	 * Search.
	 *
	 * @param doc
	 *            the doc
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> search(Doc doc) throws Exception {
		if (doc == null || doc.getId() == null) return null;
		if (doc.getDoc() != null) {
			return resolveVersions(dao.search(doc.getDoc().getId()), false);
		} else {
			return resolveVersions(dao.search(doc.getId()), false);
		}
	}

	/**
	 * Resolve versions.
	 *
	 * @param search
	 *            the search
	 * @param clearcontents
	 *            the clearcontents
	 * @return the list
	 */
	public List<Doc> resolveVersions(List<Doc> search, boolean clearcontents) {
		List<Doc> result = new ArrayList<Doc>();
		try {
			for (Doc doc : search) {
				if (clearcontents) doc.setData(null);
				resolveVersionCommon(result, doc, clearcontents);
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		return result;
	}

	/**
	 * Resolve version common.
	 *
	 * @param result
	 *            the result
	 * @param doc
	 *            the doc
	 * @param clearcontents
	 *            the clearcontents
	 * @throws Exception
	 *             the exception
	 */
	private void resolveVersionCommon(List<Doc> result, Doc doc, boolean clearcontents) throws Exception {
		List<Doc> t = getVersions(doc);
		if (t != null && t.size() > 0) {
			Doc x = t.get(0);
			List<Doc> versions = new ArrayList<Doc>();
			for (Doc doc2 : t) {
				if (clearcontents) doc2.setData(null);
				if (doc2.getId() != x.getId()) versions.add(doc2);
			}
			versions.add(doc);
			x.setDocVerions(versions);
			result.add(x);
		} else {
			result.add(doc);
		}
	}

	/**
	 * Search by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> searchByCompany(Company company) throws Exception {
		return resolveVersions(dao.searchByCompany(company.getId()), false);
	}

	public List<Doc> searchByCangeReason(ChangeReason changeReason) throws Exception {
		return resolveVersions(dao.searchByCangeReason(changeReason.getId()), false);
	}

	public List<Doc> searchByBankingDetails(BankingDetails bankingDetails) throws Exception {
		return resolveVersions(dao.searchByBankingDetails(bankingDetails.getId()), false);
	}

	public List<Doc> searchByExtensionRequest(ExtensionRequest extensionRequest) throws Exception {
		return resolveVersions(dao.searchByExtensionRequest(extensionRequest.getId()), false);
	}

	public List<Doc> searchByDgVerification(DgVerification dgVerification) throws Exception {
		return resolveVersions(dao.searchByDgVerification(dgVerification.getId()), false);
	}

	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id) throws Exception {
		return resolveVersions(dao.searchByTargetKeyAndClass(targetClass, id), false);
	}
	
	public List<Doc> findDocByClassKeyLearnerDocRequirements(ConfigDocProcessEnum configDocProcess, LearnerDocRequirements learnerDocRequirements, String targetClass, Long targetKey) throws Exception {
		return resolveVersions(dao.findDocByClassKeyLearnerDocRequirements(configDocProcess,learnerDocRequirements, targetClass, targetKey), false);
	}
	
	public List<Doc> searchByTargetKeyAndClass(String targetClass1, Long id1,String targetClass2, Long id2) throws Exception {
		return resolveVersions(dao.searchByTargetKeyAndClass(targetClass1, id1,targetClass2, id2), false);
	}

	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return resolveVersions(dao.searchByTargetKeyAndClass(targetClass, id, configDocProcessEnum), false);
	}
	
	public List<Doc> searchCompanyDocByTargetKeyAndClass(String targetClass, Long id, ConfigDocProcessEnum configDocProcessEnum,Long compID) throws Exception {
		return resolveVersions(dao.searchCompanyDocByTargetKeyAndClass(targetClass, id, configDocProcessEnum,compID), false);
	}
	
	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id, ProcessRoles processRoles) throws Exception {
		return resolveVersions(dao.searchByTargetKeyAndClass(targetClass, id, processRoles), false);		
	}
	
	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id, ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserTypeEnum) throws Exception {
		return resolveVersions(dao.searchByTargetKeyAndClass(targetClass, id, configDocProcessEnum, companyUserTypeEnum), false);
		
	}
	
	public List<Doc> searchByTargetKeyAndClassNoConfigDoc(String targetClass, Long id) throws Exception {
		return resolveVersions(dao.searchByTargetKeyAndClassNoConfigDoc(targetClass, id), false);
		
	}

	/**
	 * Search by WSP.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> searchByWSP(Wsp wsp) throws Exception {
		return resolveVersions(dao.searchByWSP(wsp.getId()), false);
	}

	public List<Doc> searchBySarsLeviesPaid(SarsLeviesPaid sarsLeviesPaid) throws Exception {
		return resolveVersions(dao.searchBySarsLeviesPaid(sarsLeviesPaid.getId()), false);
	}

	/**
	 * Search by user.
	 *
	 * @param user
	 *            the user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> searchByUser(Users user) throws Exception {
		return resolveVersions(dao.searchByUser(user.getId()), false);
	}
	
	public List<Doc> searchByUserConfigtargetClassAndKey(Users forUser, ConfigDocProcessEnum configDocProcessEnum, String targetClass, Long targetKey) throws Exception {
		return resolveVersions(dao.searchByUserConfigtargetClassAndKey(forUser.getId(), configDocProcessEnum, targetClass, targetKey), false);
	}
	
	
	
	public List<Doc> searchByClassAndKey(Long targetKey, String targetClass) throws Exception {
		return resolveVersions(dao.searchByClassAndKey(targetClass, targetKey), false);
	}
	
	public List<Doc> searchByClassAndKeyConfigDocNotNull(Long targetKey, String targetClass) throws Exception {
		return resolveVersions(dao.searchByClassAndKeyConfigDocNotNull(targetClass, targetKey), false);
	}
	
	/**
	 * Locates documents by hosting company template ID.
	 *
	 * @param hostingCompanyTemplate
	 *            the hosting company template
	 * @return List<Doc>
	 * @throws Exception
	 *             the exception
	 */
	public List<Doc> searchByHostingCompanyTemplate(HostingCompanyTemplates hostingCompanyTemplate) throws Exception {
		return resolveVersions(dao.searchByHostingCompanyTemplate(hostingCompanyTemplate.getId()), false);
	}

	/**
	 * Upload new version.
	 *
	 * @param doc
	 *            the doc
	 * @param sessionUser
	 *            the session user
	 * @throws Exception
	 *             the exception
	 */
	public void uploadNewVersion(Doc doc, Users sessionUser) throws Exception {
		List<Doc> docs = null;
		if (doc.getDocVerions() == null || doc.getDocVerions().isEmpty()) {
			docs = new ArrayList<>();
			docs.add(DocService.instance().findByKey(doc.getId()));
			doc.setId(null);
		} else {
			docs = doc.getDocVerions();
			Collections.reverse(docs);
			docs.add(0, doc);
		}
		if (doc.getData() != null) {
			save(doc, sessionUser, docs);
		}
	}

	/**
	 * Upload new version and Create Task.
	 *
	 * @param doc
	 *            the doc
	 * @param sessionUser
	 *            the session user
	 * @throws Exception
	 *             the exception
	 */
	public void uploadNewVersionAndCreateTask(Doc doc, Users sessionUser) throws Exception {
		List<Doc> docs = null;
		if (doc.getDocVerions() == null || doc.getDocVerions().isEmpty()) {
			if(doc.getId() != null){
				docs = new ArrayList<>();
				docs.add(DocService.instance().findByKey(doc.getId()));
				doc.setId(null);
			}
		} else {
			docs = doc.getDocVerions();
			Collections.reverse(docs);
			docs.add(0, doc);
		}
		if (doc.getData() != null) {
			saveAndCreateTask(doc, sessionUser, docs);
		}
	}
	
	public void uploadNewVersionAndCreateSDPDocUpdateTask(Doc docChange, Users sessionUser, ChangeReason changeReason) throws Exception {
		List<Doc> docs = null;
		if (docChange.getDocVerions() == null || docChange.getDocVerions().isEmpty()) {
			docs = new ArrayList<>();
			docs.add(DocService.instance().findByKey(docChange.getId()));
			docChange.setId(null);
			docChange.setTargetClass(null);
		} else {
			docs = docChange.getDocVerions();
			Collections.reverse(docs);
			docs.add(0, docChange);
		}
		
		if (docChange.getData() != null) {
			saveAndCreateSDPDocUpdateTask(docChange, sessionUser, docs,changeReason);
		}
	}
	
	public void saveAndCreateSDPDocUpdateTask(Doc doc, Users user, List<Doc> docs, ChangeReason changeReason) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		doc.setUsers(user);
		Doc orig = docs.get(0);
		doc.setDoc(getRootDoc(orig.getDocVerions(), docs.get(0)));
		doc.setVersionNo(orig.getVersionNo() + 1);
//		dao.create(doc);
//		dao.create(new DocByte(doc.getData(), doc));
		createList.add(doc);
		createList.add(new DocByte(doc.getData(), doc));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
		DocumentTrackerService.create(user, doc.getDoc(), DocumentTrackerEnum.UploadVersion);
		
		// Creating Task
		String desc = "";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, doc.getId(), doc.getClass().getName(), true, ConfigDocProcessEnum.SDP_DOC_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		// Creating Change reason
		ChangeReasonService.instance().createChangeReason(doc.getId(), doc.getClass().getName(), changeReason);
	}

	/**
	 * Document upload for company or users.
	 *
	 * @param entity
	 *            the entity
	 * @param forUser
	 *            the for user
	 * @param sessionUser
	 *            the session user
	 * @throws Exception
	 *             the exception
	 */
	public void documentUploadForCompanyOrUsers(IDataEntity entity, Users forUser, Users sessionUser) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (entity instanceof Company) {
			Company company = (Company) entity;
			if (company.getDocs() != null) {
				for (Doc doc : company.getDocs()) {
					if (doc.getId() == null && doc.getData() != null) {
						doc.setCompany(company);
						doc.setVersionNo(1);
						doc.setUsers(sessionUser);
						dataEntities.add(doc);
						dataEntities.add(new DocByte(doc.getData(), doc));
						dataEntities.add(new DocumentTracker(doc, sessionUser, new java.util.Date(), DocumentTrackerEnum.Upload));
					}
				}
			}
		} else if (entity instanceof Users) {
			Users user = (Users) entity;
			if (user.getDocs() != null) {
				for (Doc doc : user.getDocs()) {
					if (doc.getId() == null && doc.getData() != null) {
						doc.setForUsers(forUser);
						doc.setVersionNo(1);
						doc.setUsers(sessionUser);
						dataEntities.add(doc);
						dataEntities.add(new DocByte(doc.getData(), doc));
						dataEntities.add(new DocumentTracker(doc, sessionUser, new java.util.Date(), DocumentTrackerEnum.Upload));
					}
				}
			}
		}
		this.dao.createBatch(dataEntities);
	}

	public void checkDocData(Doc doc) {
		System.out.println("in checkDocData");
		System.out.println("checkDocData---doc.getId()---"+doc.getId());
		System.out.println("checkDocData--doc.getData()---"+doc.getData());
		try {
			if (doc.getId() != null && doc.getData() == null) {
				Doc tmp = findByKey(doc.getId());
				System.out.println("tmp.getData()---"+tmp.getData());
				doc.setData(tmp.getData());
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	public List<Doc> versions(Doc doc) throws Exception {
		if (doc.getDoc() != null) {
			return dao.versions(doc.getDoc().getId());
		} else {
			return null;
		}
	}

	public DocByte findDocByteByKey(Long id) throws Exception {
		return dao.findDocByteByKey(id);
	}

	public DocByte findDocByteByDocID(Long id) throws Exception {
		return dao.findDocByteByDocID(id);
	}

	public List<Doc> searchByCompany(Company company, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.searchByCompany(company.getId(), configDocProcessEnum);
	}

	public List<Doc> searchByModule(Modules modules) throws Exception {
		return resolveVersions(dao.searchByModule(modules.getId()), false);
	}

	/**
	 * Approve document changes
	 *
	 * @param Doc
	 *            the doc
	 * @param Tasks
	 *            the task
	 * @throws Exception
	 *             the exception
	 */
	public void approveDocument(Doc doc, Tasks task) throws Exception {
		doc.setApprovalStatus(ApprovalEnum.Approved);
		DocService.instance().update(doc);
		TasksService.instance().completeTask(task);
		// Sending email to user who created the SDF
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Company Document Approval", " Your documents changes for company:  " + doc.getCompany().getCompanyName() + " has been approved on the merSETA NSDMS system.", null);
	}
	
	public void approveSDPDocument(Doc doc, Tasks task,TrainingProviderApplication tp) throws Exception {
		TrainingProviderApplicationService tpService=new TrainingProviderApplicationService();
		doc.setApprovalStatus(ApprovalEnum.Approved);
		tp.setApprovalStatus(ApprovalEnum.Approved);
		tpService.update(tp);
		DocService.instance().update(doc);
		TasksService.instance().completeTask(task);
		approveTPDocumentChangeEmail(tp.getUsers(), tp);
	}

	/**
	 * Approve document changes
	 *
	 * @param Doc
	 *            the doc
	 * @param Tasks
	 *            the task
	 * @throws Exception
	 *             the exception
	 */
	public void rejectDocument(Doc doc, Tasks task) throws Exception {

		// Getting the doc bytes and delete
		DocByteService docByteService = new DocByteService();
		DocByte docBytes = findDocByteByDocID(doc.getId());
		docByteService.delete(docBytes);
		// Deleting Document Tracker Info
		DocumentTrackerService documentTrackerService = new DocumentTrackerService();
		documentTrackerService.deleteByDoc(doc);
		// Deleting the document
		DocService.instance().delete(doc);
		// Completing the task
		TasksService.instance().completeTask(task);
		// Sending email to user who created the SDF
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Company Document Rejection", " Your documents changes for company:  " + doc.getCompany().getCompanyName() + " has been rejected on the merSETA NSDMS system.", null);
	}
	
	public void rejectSDPDocumentChange(Doc doc, Tasks task,ArrayList<RejectReasons> selectedRejectReason,TrainingProviderApplication tp,Users sessionUser) throws Exception {
		// Getting the doc bytes and delete
		DocByteService docByteService = new DocByteService();
		DocByte docBytes = findDocByteByDocID(doc.getId());
		docByteService.delete(docBytes);
		// Deleting Document Tracker Info
		DocumentTrackerService documentTrackerService = new DocumentTrackerService();
		documentTrackerService.deleteByDoc(doc);
		// Deleting the document
		DocService.instance().delete(doc);
		// Completing the task
		TasksService.instance().completeTask(task);
		
		TrainingProviderApplicationService tpService=new TrainingProviderApplicationService();
		tp.setApprovalStatus(ApprovalEnum.Approved);
		tpService.update(tp);
		
		rejectTPDocumentChangeEmail(tp.getUsers(), selectedRejectReason, tp);
	}
	
	
	public void  approveTPDocumentChangeEmail(Users tpUser,TrainingProviderApplication tp) throws Exception {
		String subject = "Skills Development Provider Document Change Outcome".toUpperCase();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ " Your documents changes for "+ tp.getCompany().getCompanyName()+ " ("+tp.getAccreditationNumber()+") has been approved on the merSETA NSDMS system."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	
	public void  rejectTPDocumentChangeEmail(Users tpUser,ArrayList<RejectReasons> selectedRejectReason,TrainingProviderApplication tp) throws Exception {
		String subject = "Skills Development Provider Document Change Outcome".toUpperCase();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your documents changes for "+tp.getCompany().getCompanyName()+ " ("+tp.getAccreditationNumber()+") has been rejected on "
				+ "the merSETA NSDMS system for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul  style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public Doc findDocByClassKey(String targetClass, Long targetKey) throws Exception {
		return  dao.findDocByClassKey( targetClass,  targetKey);
	}
	
	/**
	 * sets document file contents data and creates a zip file of the documents
	 * 
	 * @param selectedDocs
	 * @return byte[] - a ByteArrayOutputStream array
	 * @throws Exception
	 */
	public byte[] downloadZippedFiles(List<Doc> selectedDocs) throws Exception {
		for (Doc doc : selectedDocs) {
			if (findDocByteByKey(doc.getId()) != null) {
				if(findDocByteByKey(doc.getId()).getData() !=null) {
					doc.setFileContent(findDocByteByKey(doc.getId()).getData());
				}				
			} else {
				throw new ValidationException("Could Not Locate Document Contents For: " + doc.getOriginalFname());
			}
		}
		return GenericUtility.zipBytes(selectedDocs);
	}
	
	/**
	 * By doc.
	 *
	 * @param doc the doc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Doc> byOriginalDoc(long id) throws Exception  { 
		// method added for jira 114
		return dao.byOriginalDoc(id);
	}
	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a Doc object
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	public Doc findDocByKey(long id) throws Exception {
		// method added for jira 114
		return dao.findDocByKey(id);
	}

}
