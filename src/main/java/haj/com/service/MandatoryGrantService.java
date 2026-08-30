package haj.com.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.MandatoryGrantDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.entity.MgVerification;
import haj.com.entity.Signoff;
import haj.com.entity.Wsp;
import haj.com.entity.WspSignoff;
import haj.com.entity.YesNoLookup;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.NextRefreshYear;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.NextRefreshYearService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.SizeOfCompanyService;
import haj.com.utils.GenericUtility;

public class MandatoryGrantService extends AbstractService {
	/** The dao. */
	private MandatoryGrantDAO              dao                            = new MandatoryGrantDAO();
	private NextRefreshYearService         nextRefreshYearService         = new NextRefreshYearService();
	private Long                           finYear                        = Long.parseLong(GenericUtility.sdfYear.format(getSynchronizedDate()));
	private MgVerificationService          mgVerificationService          = new MgVerificationService();
	private WspSignoffService              wspSignoffService              = new WspSignoffService();
	private EtqaService                    etqaService                    = new EtqaService();
	private YesNoLookupService             yesNoService                   = new YesNoLookupService();
	private ResolveByCodeLookupDAO         lookupDAO                      = new ResolveByCodeLookupDAO();
	private SarsFilesService               sarsFilesService               = new SarsFilesService();
	private MandatoryGrantWorkplaceService mandatoryGrantWorkplaceService = new MandatoryGrantWorkplaceService();

	/**
	 * Get all MandatoryGrant
	 * @author TechFinium
	 * @see MandatoryGrant
	 * @return a list of {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 * the exception
	 */
	public List<MandatoryGrant> allMandatoryGrant() throws Exception {
		return dao.allMandatoryGrant();
	}

	/**
	 * Create or update MandatoryGrant.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrant
	 */
	public void create(MandatoryGrant entity) throws Exception {
		validateCSVDataOnUpdate(entity);
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update MandatoryGrant.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrant
	 */
	public void update(MandatoryGrant entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MandatoryGrant.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrant
	 */
	public void delete(MandatoryGrant entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrant
	 */
	public MandatoryGrant findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public MandatoryGrant findByKeyExcludeImport(long id) throws Exception {
		return dao.findByKeyExcludeImport(id);
	}

	/**
	 * Find MandatoryGrant by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 * the exception
	 * @see MandatoryGrant
	 */
	public List<MandatoryGrant> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MandatoryGrant
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 * the exception
	 */
	public List<MandatoryGrant> allMandatoryGrant(int first, int pageSize) throws Exception {
		return dao.allMandatoryGrant(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MandatoryGrant for lazy load
	 * @author TechFinium
	 * @return Number of rows in the MandatoryGrant
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(MandatoryGrant.class);
	}

	/**
	 * Lazy load MandatoryGrant with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * MandatoryGrant class
	 * @param first
	 * the first
	 * @param pageSize
	 * the page size
	 * @param sortField
	 * the sort field
	 * @param sortOrder
	 * the sort order
	 * @param filters
	 * the filters
	 * @return a list of {@link haj.com.entity.MandatoryGrant} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(Class<MandatoryGrant> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<MandatoryGrant>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(Class<MandatoryGrant> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from MandatoryGrant o left join fetch o.etqa e where o.wsp.company.id = :companyID";
		filters.put("companyID", company.getId());
		return (List<MandatoryGrant>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Class<MandatoryGrant> entity, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wsp.company.id = :companyID";
		filters.put("companyID", company.getId());
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrantByFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.finYear = :finYear";
		filters.put("finYear", finYear);
		return (List<MandatoryGrant>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllMandatoryGrantByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wsp.finYear = :finYear";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Lazy load MandatoryGrant with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * MandatoryGrant class
	 * @param first
	 * the first
	 * @param pageSize
	 * the page size
	 * @param sortField
	 * the sort field
	 * @param sortOrder
	 * the sort order
	 * @param filters
	 * the filters
	 * @return a list of {@link haj.com.entity.MandatoryGrant} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(Class<MandatoryGrant> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WspReportEnum wspReport, Company company, long fundingID) throws Exception {
		// String hql = "select o from MandatoryGrant o where o.wspReport =:wspReport
		// and o.etqa.code = :etqaCode and o.wsp.company.id = :companyID and o.etqa is
		// not null";
		String hql = "select o from MandatoryGrant o left join fetch o.etqa e where o.wsp.company.id = :companyID and o.wspReport =:wspReport and o.funding.id = :fundingID";
		filters.put("wspReport", wspReport);
		filters.put("companyID", company.getId());
		return (List<MandatoryGrant>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(Class<MandatoryGrant> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp) throws Exception {
		// String hql = "select o from MandatoryGrant o where o.wspReport =:wspReport
		// and o.etqa.code = :etqaCode and o.wsp.company.id = :companyID and o.etqa is
		// not null";
		String hql = "select o from MandatoryGrant o left join fetch o.etqa e where o.wsp.id = :wspId";
		filters.put("wspId", wsp.getId());
		return (List<MandatoryGrant>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Class<MandatoryGrant> entity, Map<String, Object> filters, WspReportEnum wspReport, Company company, long fundingID) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wsp.company.id = :companyID and o.wspReport =:wspReport and o.funding.id = :fundingID";
		filters.put("wspReport", wspReport);
		filters.put("companyID", company.getId());
		filters.put("fundingID", fundingID);
		return dao.countWhere(filters, hql);
	}

	public int count(Class<MandatoryGrant> entity, Map<String, Object> filters, Wsp company) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wsp.id = :wspId";
		filters.put("wspId", company.getId());
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allMandatoryGrantCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select distinct(o.wsp.company) from MandatoryGrant o where o.wspReport =:wspReport and o.etqa.code = :etqaCode and o.etqa is not null";
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countCompany(Map<String, Object> filters, WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select count(distinct o.wsp.company) from MandatoryGrant o where o.wspReport =:wspReport and o.etqa.code = :etqaCode and o.etqa is not null";
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		return dao.countWhere(filters, hql);
	}

	public void createMGVerificationData() throws Exception {
		if (getSynchronizedDate().before(GenericUtility.getEndOfApril(getSynchronizedDate()))) {
			Calendar now = Calendar.getInstance();
			this.finYear = Long.valueOf(now.get(Calendar.YEAR) + 1);
		}
		if (dao.countForYear(finYear) == 0) {
			NextRefreshYear       nextRefreshYear = null;
			List<IDataEntity>     dataEntities    = new ArrayList<>();
			List<NextRefreshYear> refreshYear     = nextRefreshYearService.findLast();
			if (!refreshYear.isEmpty()) {
				nextRefreshYear = refreshYear.get(0);
			}
			if (nextRefreshYear == null || nextRefreshYear.getNextRefresh().compareTo(GenericUtility.getEndOfApril(getSynchronizedDate())) == 0) {
				nextRefreshYear = new NextRefreshYear();
				nextRefreshYear.setNextRefresh(GenericUtility.getEndOfApril(GenericUtility.addYearsToDate(getSynchronizedDate(), 4)));
				dataEntities.add(nextRefreshYear);
			}
			if (nextRefreshYear.getNextRefresh().after(getSynchronizedDate())) {
				Long count = countDistinctWSP(finYear.intValue());
				if (count > 4) {
					genCompletedVerification(dataEntities, nextRefreshYear, finYear, count / 4);
				} else {
					genCompletedVerification(dataEntities, nextRefreshYear, finYear, 1l);
				}
			}
			dao.createBatch(dataEntities);
		}

	}

	public Double deviationAnalysis(Company c) throws Exception {
		Long   plannedTraining   = findMGForDeviation(WspReportEnum.WSP, finYear.intValue() - 1, c);
		Long   completedTraining = findMGForDeviation(WspReportEnum.ATR, finYear.intValue(), c);
		Double deviationAmount   = 0.0;
		if (completedTraining != null && plannedTraining != null && completedTraining < plannedTraining) {
			deviationAmount = (plannedTraining - (double) completedTraining) / ((double) plannedTraining) * 100;
		}
		if (deviationAmount > 40.0) {
			System.out.println("Deviation greater than 40%: " + deviationAmount + "%");
		}
		return deviationAmount;
	}

	public Double deviationAnalysis(Wsp c) throws Exception {
		Long   plannedTraining   = findMGForDeviation(WspReportEnum.WSP, c.getFinYear() - 1, c.getCompany());
		Long   completedTraining = findMGForDeviation(WspReportEnum.ATR, c.getFinYear(), c.getCompany());
		Double deviationAmount   = 0.0;
		if (completedTraining != null && plannedTraining != null && completedTraining < plannedTraining) {
			deviationAmount = (plannedTraining - (double) completedTraining) / ((double) plannedTraining) * 100;
		}
		if (deviationAmount > 40.0) {
			System.out.println("Deviation greater than 40%: " + deviationAmount + "%");
		}
		return deviationAmount;
	}

	private void genCompletedVerification(List<IDataEntity> dataEntities, NextRefreshYear nextRefreshYear, Long finYear, Long count) throws Exception {
		List<Wsp> c = findRandomCompanies(finYear.intValue(), count.intValue());
		c.forEach(wsp -> {
			resolveWspSignOff(wsp);
		});

		YesNoLookup yes = YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId());
		YesNoLookup no  = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);

		for (Wsp company : c) {
			MgVerification mgVerification = new MgVerification();
			mgVerification.setFinalFin(Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh())));
			mgVerification.setResetDate(nextRefreshYear.getNextRefresh());
			mgVerification.setWsp(company);
			doChecks(yes, no, company, mgVerification);
			for (WspSignoff wspSignOff : company.getWspSignoffs()) {
				dataEntities.add(new Signoff(mgVerification, wspSignOff.getUser(), wspSignOff.getSdfType().getDescription()));
			}
			if (company.getCompany().getResidentialAddress() != null) {
				RegionTown rt = RegionTownService.instance().findByTown(company.getCompany().getResidentialAddress().getTown());
				dataEntities.add(new Signoff(mgVerification, rt.getClo().getUsers(), "merSETA Region CLO"));
				dataEntities.add(new Signoff(mgVerification, rt.getCrm().getUsers(), "merSETA Region CRM"));
			}
			dataEntities.add(mgVerification);
		}
	}

	public void refreshData() {
		try {
			List<IDataEntity> dataEntities = new ArrayList<>();
			YesNoLookup       yes          = YesNoLookupService.instance().findByKey(HAJConstants.YES_ID);
			YesNoLookup       no           = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);
			mgVerificationService.allMgVerification().forEach(mg -> {
				try {
					doChecks(yes, no, resolveWspSignOff(mg.getWsp()), mg);
					dataEntities.add(mg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			dao.updateBatch(dataEntities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doChecks(YesNoLookup yes, YesNoLookup no, Wsp company, MgVerification mgVerification) throws Exception {
		mgVerification.setWspSubmitted((this.findByWSPCount(company, WspReportEnum.WSP) > 0) ? yes : no);
		mgVerification.setAtrSubmitted((this.findByWSPCount(company, WspReportEnum.ATR) > 0) ? yes : no);
		mgVerification.setOrganisedLabourUnion(company.getCompany().getRecognitionAgreement());
		company.getWspSignoffs().forEach(s -> {
			mgVerification.setMgSignedOff((s.getAccept() != null && s.getAccept() && (mgVerification.getMgSignedOff() == null || !mgVerification.getMgSignedOff().equals(no))) ? yes : no);
		});
		List<MandatoryGrant> mg = findByWSP(company, WspReportEnum.WSP);
		mg.forEach(mandatoryGrant -> {
			mgVerification.setCompleteInYear((mandatoryGrant.getInterventionType().getDuration() <= 12 && (mgVerification.getCompleteInYear() == null || !mgVerification.getCompleteInYear().equals(no))) ? yes : no);
		});
		// Long plannedTraining = findMGForDeviation(WspReportEnum.WSP,
		// finYear.intValue() - 1, company.getCompany());
		// long completedTraining = findMGForDeviation(WspReportEnum.ATR,
		// finYear.intValue(), company.getCompany());
		// if (plannedTraining != null)
		// mgVerification.setTrainingCompletedAsPrevious((plannedTraining ==
		// completedTraining) ? yes : no);
		// else
		// mgVerification.setTrainingCompletedAsPrevious(no);
		// mgVerification.setTrainingComitteeMinutes(yes);

	}

	public void applySaqaData(MandatoryGrant mandatoryGrant) throws Exception {
		if (mandatoryGrant.getQualification() != null) {
			mandatoryGrant.setNqfLevels(mandatoryGrant.getQualification().getNqflevel());

			if (mandatoryGrant.getQualification().getNqflevel() != null) {
				mandatoryGrant.setInterventionLevel(mandatoryGrant.getNqfLevels().getInterventionLevel());
			}

			mandatoryGrant.setEtqa(etqaService.findByCode(mandatoryGrant.getQualification().getEtqaid()));
		}
	}

	public void applySkillsSet(MandatoryGrant mandatoryGrant) throws Exception {
		mandatoryGrant.setEtqa(mandatoryGrant.getSkillsSet().getEtqa());
	}

	public void applySkillsProgram(MandatoryGrant mandatoryGrant) throws Exception {
		mandatoryGrant.setEtqa(mandatoryGrant.getSkillsProgram().getEtqa());
	}

	public void applyInterventionData(MandatoryGrant mandatoryGrant) throws Exception {
		mandatoryGrant.setPivotNonPivot(mandatoryGrant.getInterventionType().getPivotNonPivot());
		if (mandatoryGrant.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
			mandatoryGrant.setNqfAligned(yesNoService.findByKey(YesNoEnum.Yes.getYesNoLookupId()));
		} else {
			mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
		}

	}

	public void populateAdditionalInformationForGeneration(MandatoryGrant mandatoryGrant) throws Exception {
		if (etqaService == null) {
			etqaService = new EtqaService();
		}
		// populates ETQA Information
		if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getEtqaid() != null && !mandatoryGrant.getQualification().getEtqaid().isEmpty()) {
			Etqa etqa = etqaService.findByCode(mandatoryGrant.getQualification().getEtqaid());
			if (etqa != null) {
				mandatoryGrant.setEtqa(etqa);
			}
		}
	}

	public Wsp resolveWspSignOff(Wsp wsp) {
		try {
			wsp.setWspSignoffs(wspSignoffService.findByWsp(wsp));
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
		return wsp;
	}

	public long countDistinctWSP(Integer finYear) {
		return dao.countDistinctWSP(finYear);
	}

	public List<Wsp> findRandomCompanies(Integer finYear, int limit) {
		return dao.findRandomCompanies(finYear, limit);
	}

	public List<Company> findRandomCompaniesSDF(Integer finYear, int limit) {
		return dao.findRandomCompaniesSDF(finYear, limit);
	}

	/**
	 * Get count of MandatoryGrant for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * MandatoryGrant class
	 * @param filters
	 * the filters
	 * @return Number of rows in the MandatoryGrant entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<MandatoryGrant> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<MandatoryGrant> findByWSP(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSP(wsp, wspReport);
	}

	public List<MandatoryGrant> resolveWorkplace(List<MandatoryGrant> mandatoryGrants) throws Exception {
		for (MandatoryGrant mandatoryGrant : mandatoryGrants) {
			mandatoryGrant.setWorkplaces(mandatoryGrantWorkplaceService.findByMandatoryGrant(mandatoryGrant));
		}
		return mandatoryGrants;
	}

	public List<MandatoryGrant> findByWSPCsv(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSPCsv(wsp, wspReport);
	}

	public long findByWSPCount(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSPCount(wsp, wspReport);
	}

	public List<MandatoryGrant> findByWSPPivotalPlan(Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) throws Exception {
		return dao.findByWSPPivotalPlan(wsp, wspReport, nqfAlignedID);
	}

	public List<MandatoryGrant> findByWSPNotHosting(Wsp wsp, WspReportEnum wspReport, String etqaCode) throws Exception {
		return dao.findByWSPNotHosting(wsp, wspReport, etqaCode);
	}

	public List<MandatoryGrant> findByWSPHosting(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.findByWSPHosting(wsp, wspReport, fundingID);
	}

	public List<Wsp> findCompaniesForVerification(Integer finYear) {
		return dao.findCompaniesForVerification(finYear);
	}

	public Long findMGForDeviation(WspReportEnum wspReport, Integer finYear, Company company) {
		return dao.findMGForDeviation(wspReport, finYear, company.getId());
	}

	public List<MandatoryGrant> findByWSPHosting(WspReportEnum wspReport, String etqaCode) throws Exception {
		return dao.findByWSPHosting(wspReport, etqaCode);
	}

	public void save(List<MandatoryGrant> mandatoryGrantCSVImports, Wsp wsp, WspReportEnum wspReport) throws Exception {
		for (MandatoryGrant a : mandatoryGrantCSVImports) {
			a.setWsp(wsp);
			a.setWspReport(wspReport);

			validateCSVData(a);

			if (a.getId() == null) {
				dao.create(a);
			} else {
				dao.update(a);
			}
		}
		// List<IDataEntity> entityList = mandatoryGrantCSVImports.stream().map(x ->
		// (IDataEntity)x).collect(Collectors.toList());
		// dao.createBatch(entityList);
	}

	private void validateCSVData(MandatoryGrant a) {
		a.setImportError(false);
		try {
			a.setOfoCodes(lookupDAO.findOfoCode(a.getOfoCode()));
			if (a.getOfoCodes() == null) throw new Exception("Invalid OFO Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			a.setFunding(lookupDAO.findFunding(a.getFundingCode()));
			if (a.getFunding() == null) throw new Exception("Invalid Funding Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			a.setInterventionType(lookupDAO.findInterventionType(a.getInterventionTypeCode()));
			if (a.getInterventionType() == null) throw new Exception("Invalid Intervention Type Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			a.setQualification(lookupDAO.findQualification(a.getQualificationCode()));
			if (a.getQualification() == null) throw new Exception("Invalid Qualification Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			a.setProviderType(lookupDAO.findProviderType(a.getProviderTypeCode()));
			if (a.getProviderType() == null) throw new Exception("Invalid Provider Type Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			a.setTrainingDeliveryMethod(lookupDAO.findTrainingDeliveryMethod(a.getTrainingDeliveryMethodCode()));
			if (a.getTrainingDeliveryMethod() == null) throw new Exception("Invalid Training Delivery Method Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			a.setEmploymentStatus(EmploymentStatusEnum.getEmploymentStatusEnumByValue(a.getEmploymentStatusCode()));
			if (a.getEmploymentStatus() == null) throw new Exception("Invalid Employment Status Code");
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}

		try {
			a.setAmount(a.getNumberAgesTotal());
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}

		calcEmployeesBreakDown(a, true);

		try {
			applySaqaData(a);
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
		try {
			applyInterventionData(a);
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}

		if (a.isImportError()) {
			a.setImported(false);
		} else {
			a.setImported(true);
			a.setImportErrors(null);
		}
	}

	private void calcEmployeesBreakDown(MandatoryGrant a, boolean zeroTotalsOnError) {
		// Check male female breakdown
		try {
			if (a.getNumberGender().longValue() != 0.0) {
				if (zeroTotalsOnError) a.zeroGendeMaleAndFemale();
				;
				throw new Exception("The number of male/female breakdown is incorrect. Values restored to zero.");
			}
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}

		try {
			if (a.getNumberDisabled().longValue() != 0.0) {
				if (zeroTotalsOnError) a.zeroDisabled();
				throw new Exception("The number of disabled breakdown is incorrect. Values restored to zero.");
			}
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}
	}

	private void validateCSVDataOnUpdate(MandatoryGrant a) {
		a.setImportError(false);
		if (a.getWsp().getWspType() != WspTypeEnum.AdditionalFunding) {

			try {
				a.setAmount(a.getNumberAgesTotal());
			} catch (Exception e) {
				logger.fatal(e);
				storeError(a, e);
			}
			calcEmployeesBreakDown(a, false);
			try {
				applyInterventionData(a);
			} catch (Exception e) {
				logger.fatal(e);
				storeError(a, e);
			}

			try {
				if (a.getPivotNonPivot() == PivotNonPivotEnum.Pivotal && a.getSkillsProgram() != null && a.getSkillsSet() != null) {
					applySaqaData(a);
				}
			} catch (Exception e) {
				logger.fatal(e);
				storeError(a, e);
			}

		}
		if (a.isImportError()) {
			a.setImported(false);
		} else {
			a.setImported(true);
			a.setImportErrors(null);
		}
	}

	private void storeError(MandatoryGrant a, Exception e) {
		a.setImportError(true);
		a.setImportErrors(a.getImportErrors() + "<li>" + e.getMessage() + "</li>");
	}

	public List<MandatoryGrant> findByCompanyFinYear(Company company, WspReportEnum wspReport, long fundingID, long finYear) throws Exception {
		return dao.findByCompanyFinYear(company, wspReport, fundingID, finYear);
	}

	public Double findByCompanyFinYearTotal(Company company, WspReportEnum wspReport, long fundingID, int finYear) throws Exception {
		return dao.findByCompanyFinYearTotal(company, wspReport, fundingID, finYear);
	}

	public void calcCosts(Wsp wsp) throws Exception {
		Double estimatedCostWSP = findByCompanyFinYearTotal(wsp.getCompany(), WspReportEnum.WSP, HAJConstants.MAN_FUNDING_ID, wsp.getFinYear() - 1);
		Double estimatedCostATR = findByCompanyFinYearTotal(wsp.getCompany(), WspReportEnum.ATR, HAJConstants.MAN_FUNDING_ID, wsp.getFinYear());
		wsp.setTotalEstimatedCostTraining(estimatedCostWSP);
		wsp.setTotalTrainingCosts(estimatedCostATR);
		if (estimatedCostATR != null && estimatedCostWSP != null && estimatedCostWSP > 0.0) {
			wsp.setPercentageTrainingCostSpentTraining((estimatedCostATR / estimatedCostWSP) * 100);
		}
		if (wsp.getTotalPayroll() != null && wsp.getTotalPayroll() != null && wsp.getTotalPayroll() > 0.0) {
			wsp.setPercentagePayrollSpent((estimatedCostATR / wsp.getTotalPayroll()) * 100);
		}
	}

	public List<MandatoryGrant> allMandatoryGrant(int finYear) {
		return dao.allMandatoryGrant(finYear);
	}

	public List<MandatoryGrant> allMandatoryGrant(Wsp wsp) {
		return dao.allMandatoryGrant(wsp);
	}

	public List<MandatoryGrant> findByWSPDgVerification(Wsp wsp) throws Exception {
		return dao.findByWSPDgVerification(wsp);
	}

	public List<MandatoryGrant> findByWSPForDgVerificationIgnoreImport(Wsp wsp) throws Exception {
		return dao.findByWSPForDgVerificationIgnoreImport(wsp);
	}

	/**
	 * Reporting Start
	 */
	/**
	 * Locates all MandatoryGrant by DG verification Status (Approved)
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return List<MandatoryGrant>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrantWhereDgApproved(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MandatoryGrant o left join fetch DgVerification dg on dg.wsp.id = o.wsp.id " + "where dg.status = :dgStatus";
		filters.put("dgStatus", ApprovalEnum.Approved);
		return (List<MandatoryGrant>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllMandatoryGrantWhereDgApproved(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MandatoryGrant o left join fetch DgVerification dg on dg.wsp.id = o.wsp.id " + "where dg.status = :dgStatus";
		return dao.countWhere(filters, hql);
	}

	public Integer countMandatoryGrantByWspId(Long wspId) throws Exception {
		return dao.countMandatoryGrantByWspId(wspId);
	}

	/**
	 * Populates additional Information for reporting purposes
	 * @param mandatoryGrant
	 * @return MandatoryGrant
	 * @throws Exception
	 */
	public MandatoryGrant populateAdditionalInformationForReporting(MandatoryGrant mandatoryGrant) throws Exception {
		// testing hit
		// if
		// (mandatoryGrant.getWsp().getCompany().getLevyNumber().equals("L560717367")) {
		// System.out.println("Hit");
		// }
		// locates Dg Verification Status
		// mandatoryGrant.getWsp().setPercentageCalculatedForDeviationDoubleValue(sarsFilesService.getPercentageCalculatedForDeviationDoubleValue(mandatoryGrant.getWsp().getCompany().getLevyNumber(),
		// mandatoryGrant.getWsp().getFinYear()- 2));
		mandatoryGrant.getWsp().getCompany().setDiscretionaryLevyForReport(sarsFilesService.returnDiscretionaryLevyByRefNoAndYear(mandatoryGrant.getWsp().getCompany().getLevyNumber(), mandatoryGrant.getWsp().getFinYear() - 2));
		CategorizationEnum companyCategorization = CategorizationDataService.instance().returnCategorizationFound(mandatoryGrant.getWsp().getCompany().getLevyNumber());
		if (companyCategorization == null) {
			mandatoryGrant.getWsp().getCompany().setCategorization(CategorizationEnum.Silver);
		} else {
			mandatoryGrant.getWsp().getCompany().setCategorization(companyCategorization);
		}
		// if (mandatoryGrant.getWsp().getCompany().getCategorization() == null) {
		// mandatoryGrant.getWsp().getCompany().setCategorization(CategorizationDataService.instance().returnCategorizationFound(mandatoryGrant.getWsp().getCompany().getLevyNumber()));
		// }
		if (mandatoryGrant != null && mandatoryGrant.getWsp() != null && mandatoryGrant.getWsp().getId() != null) {
			mandatoryGrant.setDgVerificationStatus(DgVerificationService.instance().findByWspAndReturnStatus(mandatoryGrant.getWsp().getId()));
		}

		if (mandatoryGrant.getGrantRecommendations().size() != 0) {
			// populate latest recommended learner amount
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getQuantity() != null) {
				mandatoryGrant.setLastestRecommendLearnerAmount(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getQuantity());
			} else if (mandatoryGrant.getDgVerificationStatus() != null && (mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.Approved || mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.PendingFinalApproval)) {
				mandatoryGrant.setLastestRecommendLearnerAmount(mandatoryGrant.getAmount());
			}

			// Intervention Type
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getInterventionType() != null) {
				mandatoryGrant.setInterventionTypeReporting(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getInterventionType());
				// System.out.println(mandatoryGrant.getWsp().getCompany().getLevyNumber() + " -
				// " + mandatoryGrant.getOfoCodes().getDescription() + " - Intervention Type
				// Rec: " + mandatoryGrant.getInterventionTypeReporting().getDescription() + ".
				// On Mandatory Grant: " +
				// mandatoryGrant.getInterventionType().getDescription());
			} else {
				mandatoryGrant.setInterventionTypeReporting(mandatoryGrant.getInterventionType());
			}

			// Qualification
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getQualification() != null) {
				mandatoryGrant.setQualificationReporting(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getQualification());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getQualificationReporting().getNqflevelg2description());
			} else {
				if (mandatoryGrant.getQualification() != null) {
					mandatoryGrant.setQualificationReporting(mandatoryGrant.getQualification());
					mandatoryGrant.setNqfLevelQualification(null);
				}
			}

			// Unit Standards
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getUnitStandards() != null) {
				mandatoryGrant.setUnitStandardsReporting(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getUnitStandards());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getUnitStandardsReporting().getNqflevelg2description());
			}

			// Skills Set
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getSkillsSet() != null) {
				mandatoryGrant.setSkillsSetReporting(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getSkillsSet());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getSkillsSet().getQualification().getNqflevelg2description());
			} else {
				if (mandatoryGrant.getSkillsSet() != null) {
					mandatoryGrant.setSkillsSetReporting(mandatoryGrant.getSkillsSet());
					mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getSkillsSet().getQualification().getNqflevelg2description());
				}
			}

			// Skills Program
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getSkillsProgram() != null) {
				mandatoryGrant.setSkillsProgramReporting(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getSkillsProgram());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getSkillsProgram().getQualification().getNqflevelg2description());
			} else {
				if (mandatoryGrant.getSkillsProgram() != null) {
					mandatoryGrant.setSkillsProgramReporting(mandatoryGrant.getSkillsProgram());
					mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getSkillsProgram().getQualification().getNqflevelg2description());
				}
			}

			// NonCreditBearingIntervetionTitle
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getNonCreditBearingIntervationTitle() != null) {
				mandatoryGrant.setNonCreditBearingIntervationTitleReporting(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getNonCreditBearingIntervationTitle());
				mandatoryGrant.setNqfLevelQualification("N/A");
			}
		} else {
			// learner amount
			if (mandatoryGrant.getDgVerificationStatus() != null && (mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.Approved || mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.PendingFinalApproval)) {
				mandatoryGrant.setLastestRecommendLearnerAmount(mandatoryGrant.getAmount());
			}

			// Intervention Type
			mandatoryGrant.setInterventionTypeReporting(mandatoryGrant.getInterventionType());

			// Qualification
			if (mandatoryGrant.getQualification() != null) {
				mandatoryGrant.setQualificationReporting(mandatoryGrant.getQualification());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getQualification().getNqflevelg2description());
			}

			// Skills Set
			if (mandatoryGrant.getSkillsSet() != null) {
				mandatoryGrant.setSkillsSetReporting(mandatoryGrant.getSkillsSet());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getSkillsSet().getQualification().getNqflevelg2description());
			}

			// Skills Program
			if (mandatoryGrant.getSkillsProgram() != null) {
				mandatoryGrant.setSkillsProgramReporting(mandatoryGrant.getSkillsProgram());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getSkillsProgram().getQualification().getNqflevelg2description());
			}

			// unit Standard
			if (mandatoryGrant.getUnitStandard() != null) {
				mandatoryGrant.setUnitStandardsReporting(mandatoryGrant.getUnitStandard());
				mandatoryGrant.setNqfLevelQualification(mandatoryGrant.getUnitStandard().getNqflevelg2description());
			}

			// non credit bearing intervention tittle
			if (mandatoryGrant.getNonCreditBearingIntervationTitle() != null) {
				mandatoryGrant.setNonCreditBearingIntervationTitleReporting(mandatoryGrant.getNonCreditBearingIntervationTitle());
				mandatoryGrant.setNqfLevelQualification("N/A");
			}
		}

		// set size of company against company object and region against wsp
		if (mandatoryGrant.getWsp() != null && mandatoryGrant.getWsp().getCompany() != null) {
			mandatoryGrant.getWsp().getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(mandatoryGrant.getWsp().getCompany()));
			mandatoryGrant.getWsp().setRegionTown(RegionTownService.instance().findByTown(mandatoryGrant.getWsp().getCompany().getResidentialAddress().getTown()));
		}

		// calculation checks
		if (mandatoryGrant.getInterventionTypeReporting().getBasicGrantAmount() != null) {
			Double amount = mandatoryGrant.getInterventionTypeReporting().getBasicGrantAmount() * mandatoryGrant.getAmount();
			mandatoryGrant.setEstimatedCostAppliedFor(GenericUtility.roundToPrecision(amount, 2));
			amount = null;
			if (mandatoryGrant.getLastestRecommendLearnerAmount() != null) {
				Double amountClo = mandatoryGrant.getInterventionTypeReporting().getBasicGrantAmount() * mandatoryGrant.getLastestRecommendLearnerAmount();
				mandatoryGrant.setEstimatedCostRecommenedCrm(GenericUtility.roundToPrecision(amountClo, 2));
				amountClo = null;
			} else {
				mandatoryGrant.setEstimatedCostRecommenedCrm(0.00);
			}
		} else {
			mandatoryGrant.setEstimatedCostAppliedFor(0.00);
			mandatoryGrant.setEstimatedCostRecommenedCrm(0.00);
		}
		// Double percentageCalculatedForDeviationDoubleValue =
		// sarsFilesService.getPercentageCalculatedForDeviationDoubleValue(mandatoryGrant.getWsp().getCompany().getLevyNumber(),
		// mandatoryGrant.getWsp().getFinYear());

		/*
		 * Wsp myWsp = mandatoryGrant.getWsp(); myWsp.setPercentageCalculatedForDeviationDoubleValue( percentageCalculatedForDeviationDoubleValue); mandatoryGrant.setWsp(myWsp);
		 */
		// does the estimated costs
		// #{rvDgDetail.interventionType.basicGrantAmount*rvDgDetail.amount} for
		// learners applied for
		// #{rvDgDetail.interventionType.basicGrantAmount*rvDgDetail.lastestRecommendLearnerAmount}

		// System.out.println(mandatoryGrant.getWsp().getPercentageCalculatedForDeviationDoubleValue()
		// + "");
		return mandatoryGrant;
	}

	public MandatoryGrant populateAdditionalInformationForReportingRecomendation(MandatoryGrant mandatoryGrant) throws Exception {
		MandatoryGrantRecommendation mandatoryGrantRecommendation = new MandatoryGrantRecommendation();
		// locates Dg Verification Status
		if (mandatoryGrant != null && mandatoryGrant.getWsp() != null && mandatoryGrant.getWsp().getId() != null) {
			mandatoryGrant.setDgVerificationStatus(DgVerificationService.instance().findByWspAndReturnStatus(mandatoryGrant.getWsp().getId()));
		}
		if (mandatoryGrant.getGrantRecommendations().size() != 0) {
			mandatoryGrantRecommendation = mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1);
			// System.out.println(mandatoryGrantRecommendation.getId() + "");
			// populate latest recommended learner amount
			if (mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getQuantity() != null) {
				mandatoryGrant.setLastestRecommendLearnerAmount(mandatoryGrant.getGrantRecommendations().get(mandatoryGrant.getGrantRecommendations().size() - 1).getQuantity());

			} else if (mandatoryGrant.getDgVerificationStatus() != null && (mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.Approved || mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.PendingFinalApproval)) {
				mandatoryGrant.setLastestRecommendLearnerAmount(mandatoryGrant.getAmount());
			}

		} else {
			if (mandatoryGrant.getDgVerificationStatus() != null && (mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.Approved || mandatoryGrant.getDgVerificationStatus() == ApprovalEnum.PendingFinalApproval)) {
				mandatoryGrant.setLastestRecommendLearnerAmount(mandatoryGrant.getAmount());
			}

			// Intervention Type
			mandatoryGrant.setInterventionTypeReporting(mandatoryGrant.getInterventionType()); // need to test

			// Qualification
			mandatoryGrant.setQualificationReporting(mandatoryGrant.getQualification()); // need to test
			mandatoryGrant.setNqfLevelQualification(null);
		}

		// set size of company against company object and region against wsp
		if (mandatoryGrant.getWsp() != null && mandatoryGrant.getWsp().getCompany() != null) {
			mandatoryGrant.getWsp().getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(mandatoryGrant.getWsp().getCompany()));
			mandatoryGrant.getWsp().setRegionTown(RegionTownService.instance().findByTown(mandatoryGrant.getWsp().getCompany().getResidentialAddress().getTown()));
		}

		// calculation checks
		if (mandatoryGrant.getInterventionTypeReporting() != null) {
			if (mandatoryGrant.getInterventionTypeReporting().getBasicGrantAmount() != null) {
				Double amount = mandatoryGrant.getInterventionTypeReporting().getBasicGrantAmount() * mandatoryGrant.getAmount();
				mandatoryGrant.setEstimatedCostAppliedFor(GenericUtility.roundToPrecision(amount, 2));
				amount = null;
				if (mandatoryGrant.getLastestRecommendLearnerAmount() != null) {
					Double amountClo = mandatoryGrant.getInterventionTypeReporting().getBasicGrantAmount() * mandatoryGrant.getLastestRecommendLearnerAmount();
					mandatoryGrant.setEstimatedCostRecommenedCrm(GenericUtility.roundToPrecision(amountClo, 2));
					amountClo = null;
				} else {
					mandatoryGrant.setEstimatedCostRecommenedCrm(0.00);
				}
			} else {
				mandatoryGrant.setEstimatedCostAppliedFor(0.00);
				mandatoryGrant.setEstimatedCostRecommenedCrm(0.00);
			}
		}
		mandatoryGrant.getWsp().setPercentageCalculatedForDeviationDoubleValue(sarsFilesService.getPercentageCalculatedForDeviationDoubleValue(mandatoryGrant.getWsp().getCompany().getLevyNumber(), mandatoryGrant.getWsp().getFinYear() - 2));
		// wsp.getCompany().getLevyNumber(), wsp.getFinYear() - 2
		mandatoryGrant.setGrantRecommendation(mandatoryGrantRecommendation);
		return mandatoryGrant;
	}

	/**
	 * Reporting End
	 */

	public int countByWSP(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.countByWSP(wsp, wspReport);
	}
}
