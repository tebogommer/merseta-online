package haj.com.service;

import java.util.List;

import haj.com.dao.QdfCompanyUsersDAO;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.ProcessRoles;
import haj.com.entity.QdfCompany;
import haj.com.entity.QdfCompanyUsers;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.QCDTemplateTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.QualificationService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class QdfCompanyUsersService extends AbstractService {
	/** The dao. */
	private QdfCompanyUsersDAO dao = new QdfCompanyUsersDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private QdfCompanyService qdfCompanyService = new QdfCompanyService();

	/**
	 * Get all QdfCompanyUsers
 	 * @author TechFinium 
 	 * @see   QdfCompanyUsers
 	 * @return a list of {@link haj.com.entity.QdfCompanyUsers}
	 * @throws Exception the exception
 	 */
	public List<QdfCompanyUsers> allQdfCompanyUsers() throws Exception {
	  	return dao.allQdfCompanyUsers();
	}


	/**
	 * Create or update QdfCompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QdfCompanyUsers
	 */
	public void create(QdfCompanyUsers entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QdfCompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QdfCompanyUsers
	 */
	public void update(QdfCompanyUsers entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QdfCompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QdfCompanyUsers
	 */
	public void delete(QdfCompanyUsers entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QdfCompanyUsers}
	 * @throws Exception the exception
	 * @see    QdfCompanyUsers
	 */
	public QdfCompanyUsers findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QdfCompanyUsers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QdfCompanyUsers}
	 * @throws Exception the exception
	 * @see    QdfCompanyUsers
	 */
	public List<QdfCompanyUsers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QdfCompanyUsers
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QdfCompanyUsers}
	 * @throws Exception the exception
	 */
	public List<QdfCompanyUsers> allQdfCompanyUsers(int first, int pageSize) throws Exception {
		return dao.allQdfCompanyUsers(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QdfCompanyUsers for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QdfCompanyUsers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QdfCompanyUsers.class);
	}
	
    /**
     * Lazy load QdfCompanyUsers with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QdfCompanyUsers class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QdfCompanyUsers} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QdfCompanyUsers> allQdfCompanyUsers(Class<QdfCompanyUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveInfor(( List<QdfCompanyUsers>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));	
	}
	
    private List<QdfCompanyUsers> resolveInfor(List<QdfCompanyUsers> list) throws NumberFormatException, Exception {
		for(QdfCompanyUsers qdfCompanyUsers:list) {
			populate(qdfCompanyUsers);
		}
		return list;
	}


	private void populate(QdfCompanyUsers qdfCompanyUsers) throws NumberFormatException, Exception {
		QualificationService ser = new QualificationService();
		if(qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.Review) {			
			Qualification qual = ser.findByKey(Long.parseLong(qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment().getReviewQualificationIdList()));
			qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment().setQualification(qual);
		}else if(qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {	
			Qualification qual = ser.findByKey(Long.parseLong(qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment().getReAlignmentQualificationIdList()));
			qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment().setQualification(qual);
		}
		populateAdditionalInformation(qdfCompanyUsers.getQdfCompany().getQualificationsCurriculumDevelopment());
		resolveDocs(qdfCompanyUsers.getQdfCompany());
	}
	
	private QualificationsCurriculumDevelopment populateAdditionalInformation(QualificationsCurriculumDevelopment entity) throws Exception {
		// preps docs against LearnershipDevelopmentRegistration
		prepareNewDocs(entity, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, CompanyUserTypeEnum.Company);
		
		return entity;
	}
	
	public QualificationsCurriculumDevelopment prepareNewDocs(QualificationsCurriculumDevelopment entity, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
		return entity;
	}
	
	private void resolveDocs(QdfCompany qdfCompany) throws Exception {		
		prepareQualificationsCurriculumDevelopmentDocs(ConfigDocProcessEnum.QDF_Registration,qdfCompany,qdfCompany, null);		
	}
	
	public void prepareQualificationsCurriculumDevelopmentDocs(ConfigDocProcessEnum configDocProcess, QdfCompany entityDoc, QdfCompany entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {	
			if (processRoles == null) {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			}
			else {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			}
		}
	}


	/**
     * Get count of QdfCompanyUsers for lazy load with filters
     * @author TechFinium 
     * @param entity QdfCompanyUsers class
     * @param filters the filters
     * @return Number of rows in the QdfCompanyUsers entity
     * @throws Exception the exception     
     */	
	public int count(Class<QdfCompanyUsers> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	
	public QdfCompanyUsers findByUserAndQdfCompany(QdfCompany selectedQdfCompany, Users formUser) {
		return dao.findByUserAndQdfCompany(selectedQdfCompany.getId(), formUser.getId());
	}
	
	public List<QdfCompanyUsers>  findByQdfCompany(QdfCompany qdfCompany) {
		return dao.findByQdfCompany(qdfCompany.getId());
	}
	
	public Integer countUserByQdfCompany(QdfCompany qdfCompany, Users formUser) throws Exception {
		return dao.countUserByQdfCompany(qdfCompany.getId(), formUser.getId());
	}
	
	public void checkUserAssignedToQdfCompany(QdfCompany qdfCompany, Users formUser) throws Exception{
		int amount = countUserByQdfCompany(qdfCompany, formUser);
		if (amount != 0) {
			throw new Exception("User Already Assigned to Qualification Development Team");
		}
	}
}
