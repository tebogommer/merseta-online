package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.SummativeAssessmentReportDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyLearnershipAssessment;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationUnitStandards;
import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.LegacyLearnershipAssessmentService;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.LegacyModeratorAccreditationService;
import haj.com.service.lookup.LegacySkillsProgrammeAssessmentsService;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.LegacyTvetService;
import haj.com.service.lookup.LegacyUnitStandardService;
import haj.com.service.lookup.QualificationUnitStandardsService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsProgramUnitStandardsService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.service.lookup.SkillsSetUnitStandardsService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class SummativeAssessmentReportService extends AbstractService {
	/** The dao. */
	private SummativeAssessmentReportDAO dao = new SummativeAssessmentReportDAO();
	private SkillsSetService skillsSetService = new SkillsSetService();
	private SkillsProgramService skillsProgramService = new SkillsProgramService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	/**
	 * Get all SummativeAssessmentReport
	 * 
	 * @author TechFinium
	 * @see SummativeAssessmentReport
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             the exception
	 */
	public List<SummativeAssessmentReport> allSummativeAssessmentReport() throws Exception {
		return dao.allSummativeAssessmentReport();
	}

	/**
	 * Create or update SummativeAssessmentReport.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SummativeAssessmentReport
	 */
	public void create(SummativeAssessmentReport entity, List<UnitStandards> unitStandards) throws Exception {
		if (unitStandards != null) {
			if (entity.getSkillsSet() != null && entity.getSkillsSet().getId() == null) {
				skillsSetService.create(entity.getSkillsSet(), unitStandards);
			}
			else if (entity.getSkillsProgram() != null && entity.getSkillsProgram().getId() == null) {
				skillsProgramService.create(entity.getSkillsProgram(), unitStandards);
			}
		}

		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);

		if (SKILLS_PROGRAM_LIST.contains(entity.getInterventionTypeId())) {
			unitStandards = unitStandardsService.findBySkillsProgram(entity.getSkillID());
		}
		else if (SKILLS_SET_LIST.contains(entity.getInterventionTypeId())) {
			unitStandards = unitStandardsService.findBySkillsSet(entity.getSkillID());
		}else if(entity.getCompanyLearners()!=null && entity.getCompanyLearners().getQualification()!=null){
			unitStandards = unitStandardsService.findByQualification(entity.getQualificationID());
		}

		for (UnitStandards unitStandard : unitStandards) {
			this.dao.create(new SummativeAssessmentReportUnitStandards(entity, unitStandard));
		}
	}
	
	public void createSummativeAssessmentReportUnitStandards(SummativeAssessmentReport entity, List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards) throws Exception {
		for (SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandard : summativeAssessmentReportUnitStandards) {
			summativeAssessmentReportUnitStandard.setSummativeAssessmentReport(entity);
			this.dao.create(summativeAssessmentReportUnitStandard);
		}
	}

	public void updateUnitStandards(SummativeAssessmentReport entity) throws Exception {
		for (SummativeAssessmentReportUnitStandards iterable_element : entity.getUnitStandards()) {
			this.dao.update(iterable_element);
		}

	}
	
	public void downloadReport(SummativeAssessmentReport entity, Users user) throws Exception {
		List<SummativeAssessmentReportUnitStandards> list = entity.getUnitStandards();
		Users learner = entity.getCompanyLearners().getUser();
		Qualification qualification = new Qualification();
		Company provider = entity.getCompanyLearners().getCompany();
		InterventionType interventionType = entity.getCompanyLearners().getInterventionType();
		
		if(entity.getCompanyLearners().getQualification() != null && entity.getCompanyLearners().getQualification().getId() != null) {
			qualification = entity.getCompanyLearners().getQualification();
		}else if(entity.getCompanyLearners().getSkillsSet() != null && entity.getCompanyLearners().getSkillsSet() .getId() != null) {
			qualification = entity.getCompanyLearners().getSkillsSet().getQualification();
		}else if(entity.getCompanyLearners().getSkillsProgram() != null && entity.getCompanyLearners().getSkillsProgram() .getId() != null) {
			qualification = entity.getCompanyLearners().getSkillsProgram().getQualification();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		Users users = new Users();
		List<Users> listUsers=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if(listUsers!=null && listUsers.size()>0) {
			users = listUsers.get(0) ;
		}
		JasperService.addFormTemplateParams(params);			
		params.put("users", users);
		params.put("learner", learner);
		params.put("qualification", qualification);
		params.put("trainingproviderverfication", entity);
		params.put("companylearner", entity.getCompanyLearners());
		params.put("representative", user);
		params.put("provider", provider);	
		params.put("company", provider);
		params.put("intervention", interventionType);
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(list));
		js.createReportFromDBtoServletOutputStream("ETQ-TP-011-StatementOfQualificationsandorUnitStandards.jasper", params,"SummativeAssessmentReport.pdf");			
			
	}
	
	public void downloadReport(TrainingProviderVerfication entity, Users user) throws Exception {
		SummativeAssessmentReportUnitStandardsService sruts = new SummativeAssessmentReportUnitStandardsService();
		SummativeAssessmentReport summativeAssessmentReport = findByTrainingProviderVerficationKey(entity.getId());
		List<SummativeAssessmentReportUnitStandards> list = sruts.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());
		Users learner = cl.getUser();
		Qualification qualification = new Qualification();
		Company provider = cl.getCompany();
		InterventionType interventionType = cl.getInterventionType();
		
		if(cl.getQualification() != null && cl.getQualification().getId() != null) {
			qualification = cl.getQualification();
		}else if(cl.getSkillsSet() != null && cl.getSkillsSet() .getId() != null) {
			qualification = cl.getSkillsSet().getQualification();
		}else if(cl.getSkillsProgram() != null && cl.getSkillsProgram() .getId() != null) {
			qualification = cl.getSkillsProgram().getQualification();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			JasperService.addFormTemplateParams(params);			
			params.put("learner", learner);
			params.put("qualification", qualification);
			params.put("representative", user);
			params.put("provider", provider);	
			params.put("intervention", interventionType);
			params.put("datasource", new JRBeanCollectionDataSource(list));
			js.createReportFromDBtoServletOutputStream("ETQ-FM-005-SummativeAssessmentReport.jasper", params,"SummativeAssessmentReport.pdf");			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void download007Report(TrainingProviderVerfication entity, Users user) throws Exception {
		SummativeAssessmentReportUnitStandardsService sruts = new SummativeAssessmentReportUnitStandardsService();
		SummativeAssessmentReport summativeAssessmentReport = findByTrainingProviderVerficationKey(entity.getId());
		List<SummativeAssessmentReportUnitStandards> list = sruts.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());
		Users learner = cl.getUser();
		Qualification qualification = new Qualification();
		Company provider = cl.getCompany();
		InterventionType interventionType = cl.getInterventionType();
		
		/*if(cl.getTrainingProviderApplication() != null) {
			TrainingProviderApplication tp = TrainingProviderApplicationService.instance().findByKey(cl.getTrainingProviderApplication().getId());
			accreditation_number = tp.getAccreditationNumber();
		}*/
		String description = "";
		String qual_code = "";
		String qual_description = "";
		if(cl.getSkillsProgram() != null && cl.getSkillsProgram() .getId() != null) {
			qualification = cl.getSkillsProgram().getQualification();
			qual_description = cl.getSkillsProgram().getDescription();
			description = "unit standard-based";
			qual_code = cl.getSkillsProgram().getProgrammeID();
		}else if(cl.getSkillsSet() != null && cl.getSkillsSet() .getId() != null) {
			qualification = cl.getSkillsSet().getQualification();
			qual_description = cl.getSkillsSet().getDescription();
			qual_code = cl.getSkillsSet().getProgrammeID();
			description = "unit standard-based";
		}else  if(cl.getUnitStandard() != null && cl.getUnitStandard().getId() != null) {
			qualification = cl.getUnitStandard().getQualification();
			qual_description = cl.getUnitStandard().getTitle();
			qual_code = cl.getUnitStandard().getCode() +" ";
			description = "unit standard-based";
		}else if(cl.getQualification() != null && cl.getQualification().getId() != null) {
			qualification = cl.getQualification();
			qual_description = cl.getQualification().getDescription();
			qual_code = cl.getQualification().getCodeString();
			description = "unit standard-based";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		
		Users senior_manager = null;
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users> ul = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if(ul!=null && ul.size()>0) {
			senior_manager = ul.get(0);
		}
		JasperService.addFormTemplateParams(params);	
		
		params.put("senior_manager", senior_manager);
		//params.put("users", users);
		params.put("learner", learner);
		params.put("qualification", qualification);
		params.put("trainingproviderverfication", entity);
		params.put("companylearner", cl);
		params.put("representative", user);
		params.put("provider", provider);	
		params.put("company", provider);
		params.put("intervention", interventionType);		
		params.put("qual_code", qual_code);
		params.put("qual_description", qual_description);		
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(list));
		js.createReportFromDBtoServletOutputStream("ETQ-TP-011-StatementOfQualificationsandorUnitStandards.jasper", params,"LEARNER_STATEMENT_OF_RESULTS.pdf");	
	}
	
	public void downloadETQ_TP_011_Report(TrainingProviderVerfication entity, Users user) throws Exception {
		SummativeAssessmentReportUnitStandardsService sruts = new SummativeAssessmentReportUnitStandardsService();
		SummativeAssessmentReport summativeAssessmentReport = findByTrainingProviderVerficationKey(entity.getId());
		List<SummativeAssessmentReportUnitStandards> list = sruts.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());
		Users learner = cl.getUser();
		Qualification qualification = new Qualification();
		Company provider = cl.getCompany();
		InterventionType interventionType = cl.getInterventionType();
		
		if(cl.getQualification() != null && cl.getQualification().getId() != null) {
			qualification = cl.getQualification();
		}else if(cl.getSkillsSet() != null && cl.getSkillsSet() .getId() != null) {
			qualification = cl.getSkillsSet().getQualification();
		}else if(cl.getSkillsProgram() != null && cl.getSkillsProgram() .getId() != null) {
			qualification = cl.getSkillsProgram().getQualification();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		try {
			JasperService.addFormTemplateParams(params);			
			params.put("learner", learner);
			params.put("qualification", qualification);
			params.put("companylearner", cl);
			params.put("companylearner", cl);
			params.put("representative", user);
			params.put("provider", provider);	
			params.put("company", provider);
			params.put("intervention", interventionType);
			params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(list));
			js.createReportFromDBtoServletOutputStream("ETQ-FM-005-SummativeAssessmentReport.jasper", params,"LEARNER_STATEMENT_OF_RESULTS.pdf");			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Update SummativeAssessmentReport.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SummativeAssessmentReport
	 */
	public void update(SummativeAssessmentReport entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SummativeAssessmentReport.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SummativeAssessmentReport
	 */
	public void delete(SummativeAssessmentReport entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             the exception
	 * @see SummativeAssessmentReport
	 */
	public SummativeAssessmentReport findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SummativeAssessmentReport by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             the exception
	 * @see SummativeAssessmentReport
	 */
	public List<SummativeAssessmentReport> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SummativeAssessmentReport
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport}
	 * @throws Exception
	 *             the exception
	 */
	public List<SummativeAssessmentReport> allSummativeAssessmentReport(int first, int pageSize) throws Exception {
		return dao.allSummativeAssessmentReport(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SummativeAssessmentReport for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SummativeAssessmentReport
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SummativeAssessmentReport.class);
	}

	/**
	 * Lazy load SummativeAssessmentReport with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SummativeAssessmentReport class
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
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReport} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> allSummativeAssessmentReport(Class<SummativeAssessmentReport> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SummativeAssessmentReport>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SummativeAssessmentReport for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SummativeAssessmentReport class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SummativeAssessmentReport entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SummativeAssessmentReport> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> allSummativeAssessmentReportForNonSetaQualificationsCompletion(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SummativeAssessmentReport o left join fetch o.users left join fetch o.qualification where o.nonSetaQualificationsCompletion.id =:nonSetaQualificationsCompletionID";
		return resolveQualUS((List<SummativeAssessmentReport>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));

	}

	public int countForNonSetaQualificationsCompletion(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SummativeAssessmentReport o where o.nonSetaQualificationsCompletion.id =:nonSetaQualificationsCompletionID";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> allSummativeAssessmentReportForVerification(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SummativeAssessmentReport o left join fetch o.companyLearners left join fetch o.trainingProviderVerfication where o.trainingProviderVerfication.id =:trainingProviderVerficationID";
		return resolveQualUS((List<SummativeAssessmentReport>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReport> allSummativeAssessmentReportForVerificationNoUS(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SummativeAssessmentReport o left join fetch o.companyLearners left join fetch o.trainingProviderVerfication where o.trainingProviderVerfication.id =:trainingProviderVerficationID";
		return (List<SummativeAssessmentReport>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public List<SummativeAssessmentReport> resolveQualUS(List<SummativeAssessmentReport> sar) throws Exception {
		for (SummativeAssessmentReport summativeAssessmentReport : sar)
			summativeAssessmentReport.setUnitStandards(findUnitStandards(summativeAssessmentReport.getId()));
		return sar;
	}
	
	public SummativeAssessmentReport resolveQualUS(SummativeAssessmentReport summativeAssessmentReport) throws Exception {
		summativeAssessmentReport.setUnitStandards(findUnitStandards(summativeAssessmentReport.getId()));
		return summativeAssessmentReport;
	}

	public List<SummativeAssessmentReportUnitStandards> findUnitStandards(Long summativeAssessmentReportID) throws Exception {
		List<SummativeAssessmentReportUnitStandards> list = dao.findUnitStandards(summativeAssessmentReportID);
		for(SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards:list) {
			List<Doc>docs = DocService.instance().searchByClassAndKey(summativeAssessmentReportUnitStandards.getId(), SummativeAssessmentReportUnitStandards.class.getName());
			if(docs != null && docs.size() >0) {
				summativeAssessmentReportUnitStandards.setDocuments(docs.get(docs.size()-1));
			}
		}
		return list;
	}

	public int countForVerification(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SummativeAssessmentReport o where o.trainingProviderVerfication.id =:trainingProviderVerficationID";
		return dao.countWhere(filters, hql);
	}

	public void applyInterventionData(SummativeAssessmentReport companyLearners) throws Exception {
		if (companyLearners.getInterventionType() == null) {
			companyLearners.setPivotNonPivot(null);
		} else {
			companyLearners.setPivotNonPivot(companyLearners.getInterventionType().getPivotNonPivot());
			if (companyLearners.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				companyLearners.setNqfAligned(YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId()));
			} else {
				companyLearners.setNqfAligned(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
			}
		}
	}

	public SummativeAssessmentReport findByTrainingProviderVerficationKey(long id) throws Exception {
		return dao.findByTrainingProviderVerficationKey(id);
	}

	public List<SummativeAssessmentReportUnitStandards> findByCompanylearners(CompanyLearners companylearners) throws Exception {
		List<SummativeAssessmentReportUnitStandards> list= new ArrayList<>();	
		if(companylearners.getUnitStandard() != null) {
			
			UnitStandards unitStandards = companylearners.getUnitStandard();
			SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
			summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));			
			summativeAssessmentReportUnitStandards.setUnitStandards(unitStandards);
			list.add(summativeAssessmentReportUnitStandards);
			
			
			/*QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			if(unitStandards.getQualification() == null) {
				throw new Exception("Qualification not linked to unit standard");
			}
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(unitStandards.getQualification());
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this qualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}*/
		}else if(SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgramUnitStandardsService skillsProgramUnitStandardsService = new SkillsProgramUnitStandardsService();
			List<SkillsProgramUnitStandards>skillsProgramUnitStandardsList = skillsProgramUnitStandardsService.findBySkillsProgramKey(companylearners.getSkillsProgram());
			if(skillsProgramUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(SkillsProgramUnitStandards skillsProgramUnitStandards: skillsProgramUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(skillsProgramUnitStandards.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(skillsProgramUnitStandards.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(skillsProgramUnitStandards.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsSetUnitStandardsService skillsSetUnitStandardsService = new SkillsSetUnitStandardsService();
			List<SkillsSetUnitStandards>SkillsSetUnitStandardsList = skillsSetUnitStandardsService.findBySkillsSetKey(companylearners.getSkillsSet());
	
			if(SkillsSetUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(SkillsSetUnitStandards skillsProgramUnitStandards: SkillsSetUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(skillsProgramUnitStandards.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(skillsProgramUnitStandards.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(skillsProgramUnitStandards.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(companylearners.getQualification());
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			/*LearnershipUnitStandardsService learnershipUnitStandardsService = new LearnershipUnitStandardsService();
			List<LearnershipUnitStandards>learnershipUnitStandardsList = learnershipUnitStandardsService.findByLearnership(companylearners.getLearnership());
			if(learnershipUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(LearnershipUnitStandards learnershipUnitStandards: learnershipUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(learnershipUnitStandards.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(learnershipUnitStandards.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(learnershipUnitStandards.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}*/
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(companylearners.getLearnership().getQualification());
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(companylearners.getQualification());
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				list.add(summativeAssessmentReportUnitStandards);
			}
		}
		
		return list;
	}
	
	public Integer checkCompanyLearnersCreditProgress(CompanyLearners companyLearners) throws Exception {
		Integer credits = 0;
		TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();
		TrainingProviderVerfication trainingproviderverfication = trainingProviderVerficationService.findTrainingProviderVerficationByCompanyLearner(companyLearners);
		if(trainingproviderverfication != null) {
			SummativeAssessmentReport summativeAssessmentReport = findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			if(summativeAssessmentReport != null) {
				List<SummativeAssessmentReportUnitStandards>list = findUnitStandards(summativeAssessmentReport.getId());
				for(SummativeAssessmentReportUnitStandards s:list) {
					if(s.getCompetenceEnum() != null && s.getCompetenceEnum() == CompetenceEnum.Competent && s.getUnitStandards() != null && s.getUnitStandards().getUnitstdnumberofcredits() != null) {
						credits += Integer.parseInt(s.getUnitStandards().getUnitstdnumberofcredits());
					}
				}
			}
		}
		return credits;
	}
	
	public List<SummativeAssessmentReportUnitStandards> findByCompanylearnersLegacy(CompanyLearners cl) throws Exception{
		List<SummativeAssessmentReportUnitStandards> list= new ArrayList<>();	
		LegacyAssessorAccreditationService legacyAssessorAccreditationService = new LegacyAssessorAccreditationService();
		LegacyModeratorAccreditationService legacyModeratorAccreditationService = new LegacyModeratorAccreditationService();
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
		
		SimpleDateFormat sdf = HAJConstants.sdfDDMMYYYY2;
		
		cl = CompanyLearnersService.instance().findByKey(cl.getId());
		
		if(cl.getLegacyTargetClass().equals(LegacyUnitStandard.class.getName())){
			LegacyUnitStandard legacyunitstandard = LegacyUnitStandardService.instance().findByKey(cl.getLegacyId());	
			SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();	

			if(legacyunitstandard.getUnitStandard() != null) {
				summativeAssessmentReportUnitStandards.setUnitStandards(legacyunitstandard.getUnitStandard());	
				list.add(summativeAssessmentReportUnitStandards);
			}else {
				UnitStandards standards = unitStandardsService.findByUnitStandartId(Integer.parseInt(legacyunitstandard.getUnitStdCode())) ;
				if(standards != null) {
					summativeAssessmentReportUnitStandards.setUnitStandards(standards);			
					list.add(summativeAssessmentReportUnitStandards);
				}
			}
		}else if(cl.getLegacyTargetClass().equals(LegacySkillsProgramme.class.getName())){
			LegacySkillsProgramme legacyskillsprogramme = LegacySkillsProgrammeService.instance().findByKey(cl.getLegacyId());							
			LegacySkillsProgrammeAssessmentsService LegacySkillsProgrammeAssessmentService = new LegacySkillsProgrammeAssessmentsService();
			List<LegacySkillsProgrammeAssessments>legacySkillsProgrammeAssessmentsList = LegacySkillsProgrammeAssessmentService.findByLegacySkillsProgramme(legacyskillsprogramme);
			
			for(LegacySkillsProgrammeAssessments legacySkillsProgrammeAssessments:legacySkillsProgrammeAssessmentsList ) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				if(legacySkillsProgrammeAssessments.getDtAssessment()!= null) {
					summativeAssessmentReportUnitStandards.setAssesmentDate(sdf.parse(legacySkillsProgrammeAssessments.getDtAssessment()));
				}
				if(legacySkillsProgrammeAssessments.getAssessorRegNo() != null) {
					AssessorModeratorApplication assessorModeratorApplication = assessorModeratorApplicationService.findByCerticateNumberAssessorModeratorTypeAndApplicationType(legacySkillsProgrammeAssessments.getAssessorRegNo(), AssessorModeratorTypeEnum.MerSETA_AM, AssessorModeratorAppTypeEnum.getAssessorValuesTTC());
					if(assessorModeratorApplication != null) {
						summativeAssessmentReportUnitStandards.setAssessorApplication(assessorModeratorApplication);
					}else {
						summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(legacyAssessorAccreditationService.findByIdRegistrationNumber(legacySkillsProgrammeAssessments.getAssessorID()));
					}
				}
				if(legacySkillsProgrammeAssessments.getUnitStdCode() != null) {
					UnitStandards standards = unitStandardsService.findByUnitStandartId(Integer.parseInt(legacySkillsProgrammeAssessments.getUnitStdCode())) ;
					summativeAssessmentReportUnitStandards.setUnitStandards(standards);
					list.add(summativeAssessmentReportUnitStandards);
				}
			}
		}else if(cl.getLegacyTargetClass().equals(LegacyLearnership.class.getName())){
			LegacyLearnership legacylearnership = LegacyLearnershipService.instance().findByKey(cl.getLegacyId());	
						
			LegacyLearnershipAssessmentService legacyLearnershipAssessmentService = new LegacyLearnershipAssessmentService();
			List<LegacyLearnershipAssessment>legacyLearnershipAssessmentsList = legacyLearnershipAssessmentService.findByLegacyLegacyLearnership(legacylearnership);
			
			for(LegacyLearnershipAssessment legacyLearnershipAssessment:legacyLearnershipAssessmentsList ) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				if(legacyLearnershipAssessment.getDtAssessment()!= null) {
					summativeAssessmentReportUnitStandards.setAssesmentDate(sdf.parse(legacyLearnershipAssessment.getDtAssessment()));
				}
				if(legacyLearnershipAssessment.getAssessorRegNo() != null) {
					AssessorModeratorApplication assessorModeratorApplication = assessorModeratorApplicationService.findByCerticateNumberAssessorModeratorTypeAndApplicationType(legacyLearnershipAssessment.getAssessorRegNo(), AssessorModeratorTypeEnum.MerSETA_AM, AssessorModeratorAppTypeEnum.getAssessorValuesTTC());
					if(assessorModeratorApplication != null) {
						summativeAssessmentReportUnitStandards.setAssessorApplication(assessorModeratorApplication);
					}else {
						summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(legacyAssessorAccreditationService.findByIdRegistrationNumber(legacyLearnershipAssessment.getAssessorId()));
					}
				}
				if(legacyLearnershipAssessment.getUnitStdCode() != null) {
					UnitStandards standards = unitStandardsService.findByUnitStandartId(Integer.parseInt(legacyLearnershipAssessment.getUnitStdCode())) ;
					summativeAssessmentReportUnitStandards.setUnitStandards(standards);
					list.add(summativeAssessmentReportUnitStandards);
				}
				
			}
		}else if (cl.getLegacyTargetClass().equals(LegacyApprenticeship.class.getName())) {	
			//LegacyApprenticeship legacyapprenticeship = LegacyApprenticeshipService.instance().findByKey(cl.getLegacyId());	
			
		}else if(cl.getLegacyTargetClass().equals(LegacyBursary.class.getName())) {
			//LegacyBursary legacybursary = LegacyBursaryService.instance().findByKey(cl.getLegacyId());	
			
		}else if(cl.getLegacyTargetClass().equals(LegacyInternship.class.getName())){
			//LegacyInternship legacyinternship = LegacyInternshipService.instance().findByKey(cl.getLegacyId());		
			
		}else if(cl.getLegacyTargetClass().equals(LegacyTvet.class.getName())){
			//LegacyTvet legacytvet = LegacyTvetService.instance().findByKey(cl.getLegacyId());	
						
		}
		return list;
	}
	
	public List<UnitStandards> findUnitStandardsByCompanylearners(CompanyLearners companylearners) throws Exception {
		List<UnitStandards> list= new ArrayList<>();	
		if(companylearners.getUnitStandard() != null) {			
			UnitStandards unitStandards = companylearners.getUnitStandard();
			list.add(unitStandards);
		}else if(SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgramUnitStandardsService skillsProgramUnitStandardsService = new SkillsProgramUnitStandardsService();
			list = skillsProgramUnitStandardsService.findUnitStandardsBySkillsProgrammeId(companylearners.getSkillsProgram().getId());
			
		}else if(SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsSetUnitStandardsService skillsSetUnitStandardsService = new SkillsSetUnitStandardsService();
			list = skillsSetUnitStandardsService.findUnitStandardsBySkillsSetId(companylearners.getSkillsSet().getId());

		}else if(companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			list = qualificationUnitStandardsService.findUnitStandardByQualificationKey(companylearners.getQualification().getId());		
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {	
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			list = qualificationUnitStandardsService.findUnitStandardByQualificationKey(companylearners.getLearnership().getQualification().getId());		
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			list = qualificationUnitStandardsService.findUnitStandardByQualificationKey(companylearners.getQualification().getId());
		}		
		return list;
	}
	
	public List<SummativeAssessmentReportUnitStandards> findByCompanylearners(CompanyLearners companylearners, SummativeAssessmentReport summativeAssessmentReport) throws Exception {
		List<SummativeAssessmentReportUnitStandards> list= new ArrayList<>();
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
		if(companylearners.getUnitStandard() != null) {
			if(summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId()).size() == 0) {
				UnitStandards unitStandards = companylearners.getUnitStandard();
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));			
				summativeAssessmentReportUnitStandards.setUnitStandards(unitStandards);
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(summativeAssessmentReport);
				list.add(summativeAssessmentReportUnitStandards);
			}			
			
		}else if(SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgramUnitStandardsService skillsProgramUnitStandardsService = new SkillsProgramUnitStandardsService();
			List<SkillsProgramUnitStandards>skillsProgramUnitStandardsList = skillsProgramUnitStandardsService.findBySkillsProgramKey(companylearners.getSkillsProgram(), summativeAssessmentReport);
			if(skillsProgramUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(SkillsProgramUnitStandards skillsProgramUnitStandards: skillsProgramUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(skillsProgramUnitStandards.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(skillsProgramUnitStandards.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(skillsProgramUnitStandards.getUnitStandardTypeEnum());
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(summativeAssessmentReport);
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsSetUnitStandardsService skillsSetUnitStandardsService = new SkillsSetUnitStandardsService();
			List<SkillsSetUnitStandards>SkillsSetUnitStandardsList = skillsSetUnitStandardsService.findBySkillsSetKey(companylearners.getSkillsSet(), summativeAssessmentReport);
	
			if(SkillsSetUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(SkillsSetUnitStandards skillsProgramUnitStandards: SkillsSetUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(skillsProgramUnitStandards.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(skillsProgramUnitStandards.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(skillsProgramUnitStandards.getUnitStandardTypeEnum());
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(summativeAssessmentReport);
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(companylearners.getQualification(), summativeAssessmentReport);
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(summativeAssessmentReport);
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(companylearners.getLearnership().getQualification(), summativeAssessmentReport);
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(summativeAssessmentReport);
				list.add(summativeAssessmentReportUnitStandards);
			}
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();
			List<QualificationUnitStandards>qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(companylearners.getQualification(), summativeAssessmentReport);
			if(qualificationUnitStandardsList == null) {
				throw new Exception("No Linked unit standard for this wualification");
			}
			for(QualificationUnitStandards qualificationUnitStandard: qualificationUnitStandardsList) {
				SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setCompanyLearners(CompanyLearnersService.instance().findByKey(companylearners.getId()));
				summativeAssessmentReportUnitStandards.setQualification(qualificationUnitStandard.getQualification());
				summativeAssessmentReportUnitStandards.setUnitStandards(qualificationUnitStandard.getUnitStandards());
				summativeAssessmentReportUnitStandards.setUnitStandardTypeEnum(qualificationUnitStandard.getUnitStandardTypeEnum());
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(summativeAssessmentReport);
				list.add(summativeAssessmentReportUnitStandards);
			}
		}
		
		return list;
	}
}
