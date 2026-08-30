package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.QualificationDAO;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Subfield;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationService.
 */
public class QualificationService extends AbstractService {

	/** The dao. */
	private QualificationDAO dao = new QualificationDAO();
	
	private static QualificationService qualificationService;
	public static synchronized QualificationService instance() {
		if (qualificationService == null) {
			qualificationService = new QualificationService();
		}
		return qualificationService;
	}

	/**
	 * Get all Qualification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public List<Qualification> allQualification() throws Exception {
		return dao.allQualification();
	}

	/**
	 * Find qualification autocomplete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Qualification> findQualificationAutocomplete(String desc) throws Exception {
		return dao.findQualificationAutocomplete(desc);
	}
	
	public List<Qualification> findQualificationAutocomplete(String desc, Date qualregistrationendDate) throws Exception {
		return dao.findQualificationAutocomplete(desc, qualregistrationendDate);
	}
	
	public List<Qualification> findLearningProgrammeByQual(String desc,Qualification qual) throws Exception {
		return dao.findLearningProgrammeByQual(desc,qual);
	}
	
	public List<Qualification> findQualificationAutocompleteDesignation(String description) throws Exception {
		return dao.findQualificationAutocompleteDesignation(description);
	}
	public List<Qualification> findQualificationAutocompleteReview(String desc) throws Exception {
		return dao.findQualificationAutocompleteReview(desc);
	}
	public List<Qualification> findQualificationAutocompleteReAlignment(String desc) throws Exception {
		return dao.findQualificationAutocompleteReAlignment(desc);
	}
	
	public List<Qualification> findMersetaQualificationAutocomplete(String description) throws Exception {
		return dao.findMersetaQualificationAutocomplete(description);
	}
	
	public List<Qualification> findNonMersetaQualificationAutocomplete(String description) throws Exception {
		return dao.findNonMersetaQualificationAutocomplete(description);
	}
	
	public List<Qualification> findNonMersetaQualificationAutocompleteWithTrades(String description) throws Exception {
		return dao.findNonMersetaQualificationAutocompleteWithTrades(description);
	}
	
	public List<Qualification> findNonMersetaQualificationAutocompleteWithNoTrades(String description) throws Exception {
		return dao.findNonMersetaQualificationAutocompleteWithNoTrades(description);
	}
	
	public List<Qualification> findQualificationAutocompleteExcludingTrades(String description) throws Exception {
		return dao.findQualificationAutocompleteExcludingTrades(description);
	}
	
	public List<Qualification> findMersetaQualificationAutocompleteBeforeRegistrationDate(String description, Date qualregistrationendDate) throws Exception {
		return dao.findMersetaQualificationAutocompleteBeforeRegistrationDate(description, qualregistrationendDate);
	}
	
	public List<Qualification> findQCTOQualificationAutocompleteBeforeRegistrationDate(String description, Date qualregistrationendDate) throws Exception {
		return dao.findQCTOAutocompleteBeforeRegistrationDate(description, qualregistrationendDate);
	}
	
	
	
	public List<Qualification> findQCTOTradeTestCentreQualBeforeRegDate(String description, Date qualregistrationendDate) throws Exception {
		return dao.findQCTOTradeTestCentreQualBeforeRegDate(description, qualregistrationendDate);
	}
	
	public List<Qualification> findMersetaWPAQualificationAutocomplete(String description) throws Exception {
		return dao.findMersetaWPAQualificationAutocomplete(description);
	}
	
	public List<Qualification> findMersetaWPAQualificationAutocompleteVersionTwo(String description) throws Exception {
		return dao.findMersetaWPAQualificationAutocompleteVersionTwo(description);
	}
	
	
	public List<Qualification> findQualificationAutocompleteWPARequired(String description) throws Exception {
		return dao.findQualificationAutocompleteWPARequired(description);
	}
	

	/**
	 * Create or update Qualification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public void create(Qualification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Creates the return.
	 *
	 * @param entity
	 *            the entity
	 * @return the qualification
	 * @throws Exception
	 *             the exception
	 */
	public Qualification createReturn(Qualification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
		return entity;
	}

	/**
	 * Update Qualification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public void update(Qualification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Qualification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public void delete(Qualification entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a Qualification object
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public Qualification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Qualification by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public List<Qualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Qualification.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * @throws Exception
	 *             the exception
	 */
	public List<Qualification> allQualification(int first, int pageSize) throws Exception {
		return dao.allQualification(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Qualification
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Qualification.class);
	}

	/**
	 * All qualification.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Qualification class
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
	 * @return a list of {@link haj.com.entity.lookup.Qualification} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> allQualification(Class<Qualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Qualification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            Qualification class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Qualification entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Qualification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by code.
	 *
	 * @param code
	 *            the code
	 * @return the qualification
	 * @throws Exception
	 *             the exception
	 */
	public Qualification findByCode(String code) throws Exception {
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(Integer.valueOf(code.trim()));
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> allQualificationByTrainingProviderLink(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Qualification o where o.id in (select x.qualification.id from CompanyQualifications x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.qualification <> null)";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<Qualification>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllQualificationByTrainingProviderLink(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Qualification o where o.id in (select x.qualification.id from CompanyQualifications x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.qualification <> null)";
		return dao.countWhere(filters, hql);
	}
	
	public List<Qualification> findQualificationAutocompleteBeforeLastEnrolmentDate(String description, Date lastDateForEnrolment) throws Exception {
		return dao.findQualificationAutocompleteBeforeLastEnrolmentDate(description, lastDateForEnrolment);
	}
	
	public List<Qualification> findQualBeforeLastEnrolmentDateAndTrade(String description, Date lastDateForEnrolment) throws Exception {
		return dao.findQualBeforeLastEnrolmentDateAndTrade(description, lastDateForEnrolment);
	}
	public List<Qualification> completeNonTradeQualBeforeLastEnrolmentDate(String description, Date lastDateForEnrolment) throws Exception {
		return dao.completeNonTradeQualBeforeLastEnrolmentDate(description, lastDateForEnrolment);
	}
	
	public List<Qualification> findQualificationBeforeLastEnrolmentDate(Date lastDateForEnrolment) throws Exception {
		return dao.findQualificationBeforeLastEnrolmentDate(lastDateForEnrolment);
	}
	
	public List<Qualification> findQualificationBeforeLastEnrolmentDateAndTrade(String description, Date lastDateForEnrolment) throws Exception {
		return dao.findQualificationBeforeLastEnrolmentDateAndTrade(lastDateForEnrolment);
	}
	
	public List<Qualification> completeQualificationEnrolmentDateAndIsTrade(String description, Date lastDateForEnrolment) throws Exception {
		return dao.completeQualificationEnrolmentDateAndIsTrade(description, lastDateForEnrolment);
	}
	
	public List<Qualification> completeQualificationTrade(String description) throws Exception {
		return dao.completeQualificationTrade(description);
	}
	
	public List<Qualification> completeQualificationTradeWPARequired(String description) throws Exception {
		return dao.completeQualificationTradeWPARequired(description);
	}	
	
	public List<Qualification> completeQualificationLearningProgrammes(String description) throws Exception {
		return dao.completeQualificationLearningProgrammes(description);
	}
	
	public List<Qualification> completeQualificationEnrolmentDateAndIsOccupationalCertificate(String description, Date lastDateForEnrolment) throws Exception {
		return dao.completeQualificationEnrolmentDateAndIsOccupationalCertificate(description, lastDateForEnrolment);
	}
	
	public Qualification findByCodeString(String code) throws Exception {
		return dao.findByCodeString(code);
	}
	
	public Qualification findByCodeStringNew(String description) throws Exception {
		return dao.findByCodeStringNew(description);
	}
	
	public List<Qualification> allLearnerinProgrammes() throws Exception {
		return dao.allLearnerinProgrammes();
	}
	
	public List<Qualification> allSubField() throws Exception {
		return dao.allSubField();
	}

	public void updateLearneringProgrammesWithInformation() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<IDataEntity> updateList = new ArrayList<>();
					List<Qualification> allLearningProgrammes = allLearnerinProgrammes();
					for (Qualification qualification : allLearningProgrammes) {
						if (qualification.getLearningprogrammequal() != null && !qualification.getLearningprogrammequal().isEmpty()) {
							
							Qualification parnet = null;
							try {
								parnet = findByCode(qualification.getLearningprogrammequal());
							} catch (Exception e) {
							}
							
							if (parnet != null) {	
								if (parnet.getQualregistrationstartDate() != null) {
									qualification.setQualregistrationstartDate(parnet.getQualregistrationstartDate());
								}
								if (parnet.getQualregistrationendDate() != null) {
									qualification.setQualregistrationendDate(parnet.getQualregistrationendDate());
								}					
								if (parnet.getLastDateForEnrolment() != null) {
									qualification.setLastDateForEnrolment(parnet.getLastDateForEnrolment());
								}
								
								if (parnet.getLastdateforachievement() != null) {
									qualification.setLastdateforachievement(parnet.getLastdateforachievement());
								}
								updateList.add(qualification);
							}	
						}
					}
					
					allLearningProgrammes = null;
					
					if (updateList.size() != 0) {
						dao.updateBatch(updateList);
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Qualification Learnering Programmes", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Qualification Learnering Programmes", "Processing Complete With Errors on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
					}
				}
			}
		}).start();
	}
	
	public void updateQualificationSubSet() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SubfieldService subfieldService = new SubfieldService();
					List<IDataEntity> updateList = new ArrayList<>();
					List<Qualification> allSubField = allSubField();
					for (Qualification qualification : allSubField) {
						if (qualification.getSubfielddescription() != null && !qualification.getSubfielddescription().isEmpty()) {
							Subfield subfield = null;
							try {
								subfield = subfieldService.findByDescription(qualification.getSubfielddescription().trim());
							} catch (Exception e) {
								logger.fatal(e);
							}
							if (subfield != null && subfield.getId() != null) {	
								qualification.setSubfield(subfield);
								updateList.add(qualification);
							}		
						}
					}
					allSubField = null;
					if (updateList.size() != 0) {
						dao.updateBatch(updateList);
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Qualification SubField", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Qualification SubField", "Processing Complete With Errors on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
					}
				}
			}
		}).start();
	}
	
}