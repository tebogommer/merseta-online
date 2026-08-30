package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.ProcessRoles;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerDocRequirements;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DocDAO.
 */
public class DocDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
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
	@SuppressWarnings("unchecked")
	public List<Doc> allDoc() throws Exception {
		return (List<Doc>) super.getList("select o from Doc o");
	}

	/**
	 * All doc.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> allDoc(Long from, int noRows) throws Exception {
		String hql = "select o from Doc o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Doc>) super.getList(hql, parameters, from.intValue(), noRows);
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
	public Doc findByKey(Long id) throws Exception {
		String hql = "select o from Doc o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Doc) super.getUniqueResult(hql, parameters);
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
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.doc dc left join fetch o.configDoc cd where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Doc) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Doc by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return List<Doc>
	 * @throws Exception
	 *             the exception
	 * @see Doc
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> findByName(String description) throws Exception {
		String hql = "select o from Doc o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Doc>) super.getList(hql, parameters);
	}

	/**
	 * Find by users.
	 *
	 * @param userId
	 *            the user id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> findByUsers(Long userId) throws Exception {
		String hql = "select o from Doc o left join fetch o.users u where o.users.id = :userId order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<Doc>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<Doc> findByUsersAndCompanyNull(Long userId) throws Exception {
		String hql = "select o from Doc o left join fetch o.users left join fetch o.configDoc u where o.users.id = :userId and o.company is null order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<Doc>) super.getList(hql, parameters);
	}

	/**
	 * Search parent.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> searchParent(Long id) throws Exception {
		String hql = "select o from Doc o  where o.doc.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<Doc> getVersions(Doc d) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.doc dc left join fetch o.configDoc cd where o.doc.id = :id order by o.versionNo desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", d.getId());
		return (List<Doc>) super.getList(hql, parameters);
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
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.doc dc left join fetch o.configDoc cd where o.doc.id = :id order by o.versionNo desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", d.getDoc().getId());
		return (List<Doc>) super.getList(hql, parameters);
	}

	/**
	 * Search.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> search(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByCangeReason(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.changeReason.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	/**
	 * Search by company.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> searchByCompany(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.company.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByModule(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.modules comp where o.modules.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByCompany(Long id, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.company.id = :id and o.doc is null and o.configDoc.configDocProcess = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByBankingDetails(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.bankingDetails.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByExtensionRequest(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.extensionRequest.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByDgVerification(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.dgVerification.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.targetClass = :targetClass and o.targetKey = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("targetClass", targetClass);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> searchByTargetKeyAndClass(String targetClass1, Long id1,String targetClass2, Long id2) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where (o.targetClass = :targetClass1 and o.targetKey = :id1) or (o.targetClass = :targetClass2 and o.targetKey = :id2) and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id1", id1);
		parameters.put("targetClass1", targetClass1);
		parameters.put("id2", id1);
		parameters.put("targetClass1", targetClass2);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.configDoc.parent.configDocProcess = :configDocProcessEnum and o.targetClass = :targetClass and o.targetKey = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("targetClass", targetClass);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> searchByTargetKeyAndClassNoConfigDoc(String targetClass, Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp where o.configDoc is null and o.targetClass = :targetClass and o.targetKey = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("targetClass", targetClass);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> searchCompanyDocByTargetKeyAndClass(String targetClass, Long id, ConfigDocProcessEnum configDocProcessEnum,Long compID) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.configDoc.parent.configDocProcess = :configDocProcessEnum and o.targetClass = :targetClass and o.targetKey = :id and  o.company.id = :compID and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("compID", compID);
		parameters.put("targetClass", targetClass);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id, ProcessRoles processRoles) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.configDoc.processRoles.id = :processRolesID and o.targetClass = :targetClass and o.targetKey = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("targetClass", targetClass);
		parameters.put("processRolesID", processRoles.getId());
		return (List<Doc>) super.getList(hql, parameters);
	}

	
	@SuppressWarnings("unchecked")
	public List<Doc> searchByTargetKeyAndClass(String targetClass, Long id, ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserTypeEnum) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.configDoc.companyOrUserDocument = :companyUserTypeEnum and o.configDoc.parent.configDocProcess = :configDocProcessEnum and o.targetClass = :targetClass and o.targetKey = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		parameters.put("targetClass", targetClass);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		parameters.put("companyUserTypeEnum", companyUserTypeEnum);
		return (List<Doc>) super.getList(hql, parameters);
	}


	/**
	 * Search by WSP.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> searchByWSP(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.company comp left join fetch o.configDoc cd where o.wsp.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> searchBySarsLeviesPaid(Long id) throws Exception {
		String hql = "select new haj.com.entity.Doc(o.id, o.createDate, o.users, o.extension, o.originalFname, o.newFname,o.versionNo,o.note) from Doc o left join o.users usr where o.bankStatement.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Doc> versions(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr where o.doc.id = :id or (o.id = :id) order by coalesce(o.versionNo,0) desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	/*
	 * select o.id from Doc o where o.doc.id = 96 or (o.id = 96) order by
	 * coalesce(o.versionNo,0) desc
	 */

	/**
	 * Search by user.
	 *
	 * @param id
	 *            the id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> searchByUser(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.configDoc cd where o.forUsers.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> searchByUserConfigtargetClassAndKey(Long forUserId, ConfigDocProcessEnum configDocProcessEnum, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.configDoc cd where o.forUsers.id = :forUserId and o.doc is null and o.configDoc.parent.configDocProcess = :configDocProcessEnum and o.targetClass = :targetClass and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forUserId", forUserId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> searchByClassAndKey(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Doc o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.configDoc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> searchByClassAndKeyConfigDocNotNull(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.configDoc cd where o.targetClass = :targetClass and o.targetKey = :targetKey and o.configDoc is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	/**
	 * Locates documents by hosting company template ID.
	 *
	 * @param id
	 *            the id
	 * @return List<Doc>
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> searchByHostingCompanyTemplate(Long id) throws Exception {
		String hql = "select o from Doc o left join fetch o.users usr left join fetch o.hostingCompanyTemplates hcT where o.hostingCompanyTemplates.id = :id and o.doc is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Doc>) super.getList(hql, parameters);
	}

	public DocByte findDocByteByKey(Long id) throws Exception {
		String hql = "select o from DocByte o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DocByte) super.getUniqueResult(hql, parameters);
	}

	public DocByte findDocByteByDocID(Long id) throws Exception {
		String hql = "select o from DocByte o where o.doc.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DocByte) super.getUniqueResult(hql, parameters);
	}

	public Doc findDocByClassKey(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Doc o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (Doc) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> findDocByClassKeyLearnerDocRequirements(ConfigDocProcessEnum configDocProcessEnum, LearnerDocRequirements learnerDocRequirements, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Doc o left join fetch o.configDoc cd where o.configDoc.parent.configDocProcess = :configDocProcessEnum and o.configDoc.learnerDocRequirements = :learnerDocRequirements and o.targetClass = :targetClass and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		parameters.put("learnerDocRequirements", learnerDocRequirements);
		return (List<Doc>) super.getList(hql, parameters);
	}
	
	/**
	 * By doc.
	 *
	 * @param docId the doc id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doc> byOriginalDoc(Long docId) throws Exception  {
		// method added for jira 114
		String hql = "select o from Doc o join fetch o.users where o.doc.id = :docId order by o.createDate desc";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("docId", docId);
	    return (List<Doc>)super.getList(hql, parameters);
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
	public Doc findDocByKey(Long id) throws Exception {
		// method added for jira 114
		String hql = "select o from Doc o join fetch o.users where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Doc) super.getUniqueResult(hql, parameters);
	}
}
