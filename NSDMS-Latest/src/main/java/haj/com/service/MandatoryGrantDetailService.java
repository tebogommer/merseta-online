package haj.com.service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.ATRReportSummayBean;
import haj.com.bean.DGApplicationSummaryBean;
import haj.com.bean.EmpEmploymentStatusBean;
import haj.com.bean.EmployeeEquityProfileBean;
import haj.com.bean.EmployeeProfileBean;
import haj.com.bean.GrantSspReportBean;
import haj.com.bean.PivotalTrainingReportBean;
import haj.com.bean.WSPReportSummayBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.MandatoryGrantDetailDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.entity.DgAllocation;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.MgVerification;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Signoff;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.YesNoLookup;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.GrantOfoSelection;
import haj.com.entity.lookup.NextRefreshYear;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.SaqaUnitstandard;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.EmploymentTypeService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.NextRefreshYearService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.SaqaUnitstandardService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;
import static haj.com.utils.GenericUtility.getYear;

public class MandatoryGrantDetailService extends AbstractService {
	/** The dao. */
	private MandatoryGrantDetailDAO dao = new MandatoryGrantDetailDAO();
	private NextRefreshYearService nextRefreshYearService = new NextRefreshYearService();
	private Long finYear = Long.parseLong(GenericUtility.sdfYear.format(getSynchronizedDate()));
	private MgVerificationService mgVerificationService = new MgVerificationService();
	private WspSignoffService wspSignoffService = new WspSignoffService();
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	private ResolveByCodeLookupDAO lookupDAO = new ResolveByCodeLookupDAO();
	private EmploymentTypeService employmentTypeService = new EmploymentTypeService();
	private NqfLevelsService nqfLevelsService = new NqfLevelsService();
	
	/**
	 * Get all MandatoryGrantDetail
	 * 
	 * @author TechFinium
	 * @see MandatoryGrantDetail
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             the exception
	 */
	public List<MandatoryGrantDetail> allMandatoryGrantDetail() throws Exception {
		return dao.allMandatoryGrantDetail();
	}

	/**
	 * Create or update MandatoryGrantDetail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetail
	 */
	public void create(MandatoryGrantDetail entity) throws Exception {
		validateCSVDataOnUpdate(entity);
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	@SuppressWarnings("unchecked")
	public void deleteAll(Wsp wsp, WspReportEnum wspReport) throws Exception {
		List<MandatoryGrantDetail> mgd = findByWSP(wsp, wspReport);
		this.dao.deleteBatch((List<IDataEntity>) (List<?>) mgd, 3000);
	}

	@SuppressWarnings("unchecked")
	public void deleteAllCSV(Wsp wsp, WspReportEnum wspReport) throws Exception {
		List<MandatoryGrantDetail> mgd = findByWSPCsv(wsp, wspReport);
		this.dao.deleteBatch((List<IDataEntity>) (List<?>) mgd, 3000);
	}

	/**
	 * Update MandatoryGrantDetail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetail
	 */
	public void update(MandatoryGrantDetail entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MandatoryGrantDetail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetail
	 */
	public void delete(MandatoryGrantDetail entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetail
	 */
	public MandatoryGrantDetail findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MandatoryGrantDetail by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantDetail
	 */
	public List<MandatoryGrantDetail> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MandatoryGrantDetail
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             the exception
	 */
	public List<MandatoryGrantDetail> allMandatoryGrantDetail(int first, int pageSize) throws Exception {
		return dao.allMandatoryGrantDetail(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MandatoryGrantDetail for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the MandatoryGrantDetail
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(MandatoryGrantDetail.class);
	}

	/**
	 * Lazy load MandatoryGrantDetail with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MandatoryGrantDetail class
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
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetail(Class<MandatoryGrantDetail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<MandatoryGrantDetail>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Lazy load MandatoryGrantDetail with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MandatoryGrantDetail class
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
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetail(Class<MandatoryGrantDetail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WspReportEnum wspReport, Company company, long fundingID) throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.etqa e where o.wsp.company.id = :companyID and o.wspReport =:wspReport and o.funding.id = :fundingID";
		filters.put("wspReport", wspReport);
		filters.put("companyID", company.getId());
		return (List<MandatoryGrantDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetail(Class<MandatoryGrantDetail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WspReportEnum wspReport, Wsp company, long fundingID) throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.etqa e where o.wsp.id = :wspId and o.wspReport =:wspReport and o.funding.id = :fundingID and o.imported = true";
		filters.put("wspReport", wspReport);
		filters.put("wspId", company.getId());
		filters.put("fundingID", fundingID);
		return (List<MandatoryGrantDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public List<MandatoryGrant> findSummarizedData(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp, WspReportEnum wspReport, long fundingID) {
		/*
		 * select count(id), ofo_code_id, intervention_type_id, qualification_id,
		 * skills_set_id, skills_program_id from mandatory_grant_detail where wsp_id = 1
		 * and wsp_report = 1 and funding_id = 7 and imported = true group by
		 * ofo_code_id, intervention_type_id, qualification_id, skills_set_id,
		 * skills_program_id
		 */
		return dao.findSummarizedData(entity, startingAt, pageSize, sortField, sortOrder, filters, wsp, wspReport, fundingID);

	}

	public int findSummarizedDataCount(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		return ((Long) dao.findSummarizedDataCount(wsp, wspReport, fundingID)).intValue();
	}

	public List<MandatoryGrant> findSummarizedData(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		return dao.findSummarizedData(wsp, wspReport, fundingID);
	}
	
	public List<MandatoryGrant> findSummarizedDataVersionTwo(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		return dao.findSummarizedDataVersionTwo(wsp, wspReport, fundingID);
	}
	
	public List<MandatoryGrant> findSummarizedDataVersionThree(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		return dao.findSummarizedDataVersionThree(wsp, wspReport, fundingID);
	}
	
	public List<MandatoryGrant> findSummarizedDataVersionFour(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		return dao.findSummarizedDataVersionFour(wsp, wspReport, fundingID);
	}
	
	public List<MandatoryGrant> findSummarizedDataVersionFive(Wsp wsp, WspReportEnum wspReport, long fundingID, long enrolmentStatusId) {
		return dao.findSummarizedDataVersionFive(wsp, wspReport, fundingID, enrolmentStatusId);
	}
	
	public List<MandatoryGrant> findSummarizedDataVersionSix(Wsp wsp, WspReportEnum wspReport, long fundingID, long enrolmentStatusId) {
		return dao.findSummarizedDataVersionSix(wsp, wspReport, fundingID, enrolmentStatusId);
	}

	public long findCountCreatedMandatoryGrant(Wsp wsp) throws Exception {
		return dao.findCountCreatedMandatoryGrant(wsp);
	}

	public int count(Class<MandatoryGrantDetail> entity, Map<String, Object> filters, WspReportEnum wspReport, Company company, long fundingID) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.company.id = :companyID and o.wspReport =:wspReport and o.funding.id = :fundingID";
		filters.put("wspReport", wspReport);
		filters.put("companyID", company.getId());
		filters.put("fundingID", fundingID);
		return dao.countWhere(filters, hql);
	}

	public int count(Class<MandatoryGrantDetail> entity, Map<String, Object> filters, WspReportEnum wspReport, Wsp company, long fundingID) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspId and o.wspReport =:wspReport and o.funding.id = :fundingID";
		filters.put("wspReport", wspReport);
		filters.put("wspId", company.getId());
		filters.put("fundingID", fundingID);
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allMandatoryGrantDetailCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select distinct(o.wsp.company) from MandatoryGrantDetail o where o.wspReport =:wspReport and o.etqa.code = :etqaCode and o.etqa is not null";
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countCompany(Map<String, Object> filters, WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select count(distinct o.wsp.company) from MandatoryGrantDetail o where o.wspReport =:wspReport and o.etqa.code = :etqaCode and o.etqa is not null";
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		return dao.countWhere(filters, hql);
	}

	public void createMGVerificationInformation() throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();

		List<Wsp> wppList = findRandomCompaniesForMgVerification(2019, 12000);

		NextRefreshYear nextRefreshYear = null;
		List<NextRefreshYear> refreshYear = nextRefreshYearService.findLast();
		if (!refreshYear.isEmpty()) {
			nextRefreshYear = refreshYear.get(0);
		}

		if (nextRefreshYear == null || nextRefreshYear.getNextRefresh().compareTo(GenericUtility.getEndOfApril(getSynchronizedDate())) == 0) {
			nextRefreshYear = new NextRefreshYear();
			nextRefreshYear.setNextRefresh(GenericUtility.getEndOfApril(GenericUtility.addYearsToDate(getSynchronizedDate(), 4)));
			dataEntities.add(nextRefreshYear);
		}

		for (Wsp wsp : wppList) {
			List<MandatoryGrantDetail> mandatoryGrantDetailList = findByWSPForMgverification(wsp, 2019);
			// long noPlannedLearners = findByMandatoryGrantDetailCountOFO(wsp, 2019);
			long noPlannedLearners = findByWSPCount(wsp);
			for (MandatoryGrantDetail mg : mandatoryGrantDetailList) {
				dataEntities.add(new MgVerificationDetails(mg, finYear, noPlannedLearners, false));
			}

			MgVerification mgVerification = new MgVerification();
			mgVerification.setFinalFin(Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh())));
			mgVerification.setResetDate(nextRefreshYear.getNextRefresh());
			mgVerification.setWsp(wsp);
			mgVerification.setStatus(ApprovalEnum.PendingApproval);

			dataEntities.add(mgVerification);
		}
		dao.createBatch(dataEntities);
	}

	public void generateDetail(MgVerification mgVerification) throws Exception {
//		Long detailCount = allMgVerificationDetailsCount(mgVerification.getWsp());
		Long detailCount = allMgVerificationDetailsByWspIdCount(mgVerification.getWsp());
		if (detailCount == 0) {
			List<IDataEntity> dataEntities = new ArrayList<>();
			/* Version One Generation */
////			List<MandatoryGrantDetail> mandatoryGrantDetails = findByWSP(mgVerification.getWsp());
//			List<MandatoryGrantDetail> mandatoryGrantDetails = findByWSPByFundingAndReportType(mgVerification.getWsp());
//			for (MandatoryGrantDetail mandatoryGrantDetail : mandatoryGrantDetails) {
////				long noPlannedLearners = findByWSPCount(mandatoryGrantDetail.getWsp());
//				long noPlannedLearners = findByWSPByFuningAndReportTypeCount(mandatoryGrantDetail.getWsp());
//				dataEntities.add(new MgVerificationDetails(mandatoryGrantDetail, finYear, noPlannedLearners, false));
//			}
			/* Version Two Generation */
			List<MgVerificationDetails> mgVerificationDetailsGeneratedList = findSummarizedDataMgVerificationVersionOne(mgVerification.getWsp(), WspReportEnum.WSP, HAJConstants.MAN_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW, HAJConstants.WSP_ENROLEMNT_IN_PROG);
			for (MgVerificationDetails mandatoryGrantDetail : mgVerificationDetailsGeneratedList) {
				mandatoryGrantDetail.setFinalFin(finYear);
				mandatoryGrantDetail.setEvidanceRequired(false);
				mandatoryGrantDetail.setNoPlannedLearners(mandatoryGrantDetail.getAmount().longValue());
				dataEntities.add(mandatoryGrantDetail);
			}
			dao.createBatch(dataEntities);
		}
	}

	public List<MgVerificationDetails> allMgVerificationDetails(Wsp wsp) throws Exception {
		return dao.allMgVerificationDetails(wsp.getId());
	}

	public Long allMgVerificationDetailsCount(Wsp wsp) throws Exception {
		return dao.allMgVerificationDetailsCount(wsp.getId());
	}
	
	public Long allMgVerificationDetailsByWspIdCount(Wsp wsp) throws Exception {
		return dao.allMgVerificationDetailsByWspIdCount(wsp.getId());
	}

	public void createMGVerificationData() throws Exception {
		if (dao.countForYear(finYear) == 0) {
			NextRefreshYear nextRefreshYear = null;
			List<IDataEntity> dataEntities = new ArrayList<>();
			List<NextRefreshYear> refreshYear = nextRefreshYearService.findLast();
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
					// completeMGVerification(dataEntities, nextRefreshYear, finYear, count / 4);
				} else {
					genCompletedVerification(dataEntities, nextRefreshYear, finYear, 1l);
					// completeMGVerification(dataEntities, nextRefreshYear, finYear, 1l);
				}
			}
			dao.createBatch(dataEntities, 500);
		}
	}
	
	public void createMGVerificationDataVersionTwo() throws Exception{
//		if (dao.countForYear(finYear) == 0) {
		if (dao.countForWspYear(finYear) == 0) {
			NextRefreshYear nextRefreshYear = null;
			List<IDataEntity> dataEntities = new ArrayList<>();
			List<NextRefreshYear> refreshYear = nextRefreshYearService.findLast();
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
					generateverificationByClo(dataEntities, nextRefreshYear, finYear, 4);
				} else {
					generateverificationByClo(dataEntities, nextRefreshYear, finYear, 1);
				}
			}
			if (dataEntities.size() != 0) {
				dao.createBatch(dataEntities, 500);
			}
		}
	}
	
	public void createMGVerificationDataVersionTwoByFinYear(Long finYearPassed) throws Exception{
//		if (dao.countForYear(finYearPassed) == 0) {
		if (dao.countForWspYear(finYearPassed) == 0) {
			NextRefreshYear nextRefreshYear = null;
			List<IDataEntity> dataEntities = new ArrayList<>();
			List<NextRefreshYear> refreshYear = nextRefreshYearService.findLast();
			if (!refreshYear.isEmpty()) {
				nextRefreshYear = refreshYear.get(0);
			}
			if (nextRefreshYear == null || nextRefreshYear.getNextRefresh().compareTo(GenericUtility.getEndOfApril(getSynchronizedDate())) == 0) {
				nextRefreshYear = new NextRefreshYear();
				nextRefreshYear.setNextRefresh(GenericUtility.getEndOfJuly(GenericUtility.addYearsToDate(getSynchronizedDate(), 4)));
				dataEntities.add(nextRefreshYear);
			}
			if (nextRefreshYear.getNextRefresh().after(getSynchronizedDate())) {
//				Long count = countDistinctWSP(finYearPassed.intValue());
				Long count = countDistinctWSPPassedDraft(finYearPassed.intValue());
				if (count > 4) {
					generateverificationByClo(dataEntities, nextRefreshYear, finYearPassed, 4);
				} else {
					generateverificationByClo(dataEntities, nextRefreshYear, finYearPassed, 1);
				}
			}
			if (dataEntities.size() != 0) {
				dao.createBatch(dataEntities, 500);
			}
		}
	}

	public Double deviationAnalysis(Company c) throws Exception {
		Long plannedTraining = findMGForDeviation(WspReportEnum.WSP, finYear.intValue() - 1, c);
		Long completedTraining = findMGForDeviation(WspReportEnum.ATR, finYear.intValue(), c);
		Double deviationAmount = 0.0;
		if (completedTraining != null && plannedTraining != null && completedTraining < plannedTraining) {
			deviationAmount = (plannedTraining - (double) completedTraining) / ((double) plannedTraining) * 100;
		}
		if (deviationAmount > 40.0) {
			System.out.println("Deviation greater than 40%: " + deviationAmount + "%");
		}
		return deviationAmount;
	}

	public Double deviationAnalysis(Wsp c) throws Exception {
		Long plannedTraining = findMGForDeviation(WspReportEnum.WSP, c.getFinYear() - 1, c.getCompany());
		Long completedTraining = findMGForDeviation(WspReportEnum.ATR, c.getFinYear(), c.getCompany());
		Double deviationAmount = 0.0;
		if (completedTraining != null && plannedTraining != null && completedTraining < plannedTraining) {
			deviationAmount = (plannedTraining - (double) completedTraining) / ((double) plannedTraining) * 100;
		}
		if (deviationAmount > 40.0) {
			System.out.println("Deviation greater than 40%: " + deviationAmount + "%");
		}
		return deviationAmount;
	}

	private void completeMGVerification(List<IDataEntity> dataEntities, NextRefreshYear nextRefreshYear, Long finYear, Long count) throws Exception {
		List<MandatoryGrantDetail> c = findRandomMandatoryGrantDetailCompanies(finYear.intValue(), count.intValue());

		YesNoLookup yes = YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId());
		YesNoLookup no = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);
		for (MandatoryGrantDetail mandatoryGrantDetail : c) {
			MgVerification mgVerification = new MgVerification();
			mgVerification.setFinalFin(Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh())));
			mgVerification.setResetDate(nextRefreshYear.getNextRefresh());
			mgVerification.setWsp(mandatoryGrantDetail.getWsp());
			long noPlannedLearners = findByWSPCount(mandatoryGrantDetail.getWsp());
			dataEntities.add(new MgVerificationDetails(mandatoryGrantDetail, finYear, noPlannedLearners, false));
			c.forEach(wsp -> {
				resolveWspSignOff(mandatoryGrantDetail.getWsp());
			});
			doChecks(yes, no, mandatoryGrantDetail.getWsp(), mgVerification);
			if (mandatoryGrantDetail.getWsp() != null && mandatoryGrantDetail.getWsp().getWspSignoffs() != null) {
				mandatoryGrantDetail.getWsp().getWspSignoffs().forEach(wspSignOff -> {
					dataEntities.add(new Signoff(mgVerification, wspSignOff.getUser(), wspSignOff.getSdfType().getDescription()));
				});
			}
			RegionTown rt = RegionTownService.instance().findByTown(mandatoryGrantDetail.getWsp().getCompany().getResidentialAddress().getTown());
			dataEntities.add(new Signoff(mgVerification, rt.getClo().getUsers(), "merSETA Region CLO"));
			dataEntities.add(new Signoff(mgVerification, rt.getCrm().getUsers(), "merSETA Region CRM"));
			dataEntities.add(mgVerification);
		}
	}

	private void genCompletedVerification(List<IDataEntity> dataEntities, NextRefreshYear nextRefreshYear, Long finYear, Long count) throws Exception {
		List<Wsp> c = findRandomCompanies(finYear.intValue(), count.intValue());
		c.forEach(wsp -> {
			resolveWspSignOff(wsp);
		});
		YesNoLookup yes = YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId());		
		YesNoLookup no = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);
		for (Wsp company : c) {
			
			MgVerification mgVerification = new MgVerification();
			mgVerification.setFinalFin(Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh())));
			mgVerification.setResetDate(nextRefreshYear.getNextRefresh());
			mgVerification.setWsp(company);
			doChecks(yes, no, company, mgVerification);

			dataEntities.add(mgVerification);
			company.getWspSignoffs().forEach(wspSignOff -> {
				dataEntities.add(new Signoff(mgVerification, wspSignOff.getUser(), wspSignOff.getSdfType().getDescription()));
			});
			
			RegionTown rt = RegionTownService.instance().findByTown(company.getCompany().getResidentialAddress().getTown());
			dataEntities.add(new Signoff(mgVerification, rt.getClo().getUsers(), "merSETA Region CLO"));
			dataEntities.add(new Signoff(mgVerification, rt.getCrm().getUsers(), "merSETA Region CRM"));
			
			// Generate Entries
			List<MandatoryGrantDetail> mandatoryGrantDetails = findByWSP(mgVerification.getWsp());
			for (MandatoryGrantDetail mandatoryGrantDetail : mandatoryGrantDetails) {
				long noPlannedLearners = findByWSPCount(mandatoryGrantDetail.getWsp());
				dataEntities.add(new MgVerificationDetails(mandatoryGrantDetail, finYear, noPlannedLearners, false));
			}
		}
	}
	
	private void generateverificationByClo(List<IDataEntity> dataEntities, NextRefreshYear nextRefreshYear, Long finYearPassed, Integer devideAmount) throws Exception {
		// locate distinct CLO Users
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		YesNoLookup yes = YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId());		
		YesNoLookup no = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);
		List<Users> allActiveClos = hceService.locateAllActiveCurrentCloUsers();
		Long nextRefershYearLong = Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh()));
		
		long batchNumber = 0l;
		// works out batch number
		Long amountYearSubtraction = nextRefershYearLong - finYearPassed;
		if (amountYearSubtraction.equals(3l)) {
//			System.out.println("1");
			batchNumber = 1l;
		}else if (amountYearSubtraction.equals(2l)) {
//			System.out.println("2");
			batchNumber = 2l;
		}else if (amountYearSubtraction.equals(1l)) {
//			System.out.println("3");
			batchNumber = 3l;
		} else {
//			System.out.println("4");
			batchNumber = 4l;
		}
		
		for (Users clo : allActiveClos) {
			// count grant applications against CLO
//			int distinctWspByClo = countDistinctWSPByClo(finYear.intValue(), clo);
			int distinctWspByClo = countDistinctWSPByCloAndFinalYear(finYearPassed.intValue(), clo, nextRefershYearLong);
			if (distinctWspByClo != 0) {
				int amount = distinctWspByClo / devideAmount;
				if (amount != 0) {
					// locate wsp information
//					List<Wsp> c = findRandomCompaniesByClo(finYear.intValue(), amount, clo);
					List<Wsp> c = findRandomCompaniesByCloAndFinalYear(finYearPassed.intValue(), amount, clo, nextRefershYearLong);
					c.forEach(wsp -> {
						resolveWspSignOff(wsp);
					});	
					for (Wsp company : c) {
						// Generate MG verification
						MgVerification mgVerification = new MgVerification();
						mgVerification.setCloGeneratedFor(clo);
						mgVerification.setFinalFin(Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh())));
						mgVerification.setResetDate(nextRefreshYear.getNextRefresh());
						mgVerification.setWsp(company);
						mgVerification.setBatchNumber(batchNumber);
						doChecks(yes, no, company, mgVerification);
						dataEntities.add(mgVerification);
//						company.getWspSignoffs().forEach(wspSignOff -> {
//							dataEntities.add(new Signoff(mgVerification, wspSignOff.getUser(), wspSignOff.getSdfType().getDescription()));
//						});
					
						RegionTown rt = RegionTownService.instance().findByTown(company.getCompany().getResidentialAddress().getTown());
						
						// Company REP Sign OFF
						Signoff signoffSdp = new Signoff(null, mgVerification, "COMPANY REPRESENTATIVE", null, null);
						signoffSdp.setExternalUserSignoff(true);
						dataEntities.add(signoffSdp);
						signoffSdp = null;
						 
						// CLO Sign OFF
						Signoff signoffClo = new Signoff(rt.getClo().getUsers(), mgVerification, "MERSETA: REGION CLO", null, null);
						signoffClo.setExternalUserSignoff(false);
						dataEntities.add(signoffClo);
						signoffClo = null;
						
						// CRM Sign OFF
						Signoff signoffCrm = new Signoff(rt.getCrm().getUsers(), mgVerification, "MERSETA: REGION CRM", null, null);
						signoffCrm.setExternalUserSignoff(false);
						dataEntities.add(signoffCrm);
						signoffCrm = null;
						
						// Version One Of Sign Offs
//						dataEntities.add(new Signoff(mgVerification, rt.getClo().getUsers(), "merSETA Region CLO"));
//						dataEntities.add(new Signoff(mgVerification, rt.getCrm().getUsers(), "merSETA Region CRM"));
						
						// Generate Entries
						/* Version one generation */
//						List<MandatoryGrantDetail> mandatoryGrantDetails = findByWSP(mgVerification.getWsp());
//						List<MandatoryGrantDetail> mandatoryGrantDetails = findByWSPByFundingAndReportType(mgVerification.getWsp());
//						for (MandatoryGrantDetail mandatoryGrantDetail : mandatoryGrantDetails) {
//							long noPlannedLearners = findByWSPCount(mandatoryGrantDetail.getWsp());
////						long noPlannedLearners = findByWSPByFuningAndReportTypeCount(mandatoryGrantDetail.getWsp());
//							dataEntities.add(new MgVerificationDetails(mandatoryGrantDetail, finYear, noPlannedLearners, false));
//						}
//						mandatoryGrantDetails = null;
						/* Version two generation */
						List<MgVerificationDetails> mgVerificationDetailsGeneratedList = findSummarizedDataMgVerificationVersionOne(mgVerification.getWsp(), WspReportEnum.WSP, HAJConstants.MAN_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW, HAJConstants.WSP_ENROLEMNT_IN_PROG);
						for (MgVerificationDetails mandatoryGrantDetail : mgVerificationDetailsGeneratedList) {
							mandatoryGrantDetail.setFinalFin(Long.parseLong(GenericUtility.sdfYear.format(nextRefreshYear.getNextRefresh())));
//							mandatoryGrantDetail.setFinalFin(finYear);
							mandatoryGrantDetail.setEvidanceRequired(false);
							mandatoryGrantDetail.setNoPlannedLearners(mandatoryGrantDetail.getAmount().longValue());
							dataEntities.add(mandatoryGrantDetail);
						}
						
					}
					// could send notification here
					c = null;
				}
			}
		}
		
		hceService = null;
		yes = null;
		no = null;
		allActiveClos = null;
	}

	public void refreshData() {
		try {
			List<IDataEntity> dataEntities = new ArrayList<>();
			YesNoLookup yes = YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId());
			YesNoLookup no = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);
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
		if (company != null && company.getCompany() != null && company.getCompany().getRecognitionAgreement() != null) {
			mgVerification.setOrganisedLabourUnion(company.getCompany().getRecognitionAgreement());
		}else {
			mgVerification.setOrganisedLabourUnion(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
		}
		
		/*
		 * company.getWspSignoffs().forEach(s -> {
		 * mgVerification.setMgSignedOff((s.getAccept() != null && s.getAccept() &&
		 * (mgVerification.getMgSignedOff() == null ||
		 * !mgVerification.getMgSignedOff().equals(no))) ? yes : no); });
		 */
		List<MandatoryGrantDetail> mg = findByWSP(company, WspReportEnum.WSP);
		mg.forEach(MandatoryGrantDetail -> {
			mgVerification.setCompleteInYear((MandatoryGrantDetail.getInterventionType().getDuration() <= 12 && (mgVerification.getCompleteInYear() == null || !mgVerification.getCompleteInYear().equals(no))) ? yes : no);
		});
		Long plannedTraining = findMGForDeviation(WspReportEnum.WSP, finYear.intValue() - 1, company.getCompany());
		long completedTraining = findMGForDeviation(WspReportEnum.ATR, finYear.intValue(), company.getCompany());
		if (plannedTraining != null) mgVerification.setTrainingCompletedAsPrevious((plannedTraining == completedTraining) ? yes : no);
		else mgVerification.setTrainingCompletedAsPrevious(no);
		// mgVerification.setTrainingComitteeMinutes(yes);

	}

	public void applySaqaData(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		if (mandatoryGrantDetail.getQualification() != null && mandatoryGrantDetail.getQualification().getNqflevel() != null) {
			mandatoryGrantDetail.setNqfLevels(mandatoryGrantDetail.getQualification().getNqflevel());
			mandatoryGrantDetail.setInterventionLevel(mandatoryGrantDetail.getNqfLevels().getInterventionLevel());
			mandatoryGrantDetail.setEtqa(etqaService.findByCode(mandatoryGrantDetail.getQualification().getEtqaid()));
		}
	}

	public void applyNQFData(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		mandatoryGrantDetail.setInterventionLevel(mandatoryGrantDetail.getNqfLevels().getInterventionLevel());
	}

	public void applySkillsSet(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		if (mandatoryGrantDetail.getSkillsSet() != null) {
			if (mandatoryGrantDetail.getSkillsSet().getQualification() != null) {
				mandatoryGrantDetail.setNqfLevels(mandatoryGrantDetail.getSkillsSet().getQualification().getNqflevel());
				mandatoryGrantDetail.setInterventionLevel(mandatoryGrantDetail.getNqfLevels().getInterventionLevel());
				mandatoryGrantDetail.setEtqa(etqaService.findByCode(mandatoryGrantDetail.getSkillsSet().getQualification().getEtqaid()));

			} else {
				mandatoryGrantDetail.setEtqa(mandatoryGrantDetail.getSkillsSet().getEtqa());
			}
		}
	}

	public void applySkillsProgram(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		if (mandatoryGrantDetail.getSkillsProgram() != null) {
			if (mandatoryGrantDetail.getSkillsProgram().getQualification() != null) {
				mandatoryGrantDetail.setNqfLevels(mandatoryGrantDetail.getSkillsProgram().getQualification().getNqflevel());
				mandatoryGrantDetail.setInterventionLevel(mandatoryGrantDetail.getNqfLevels().getInterventionLevel());
				mandatoryGrantDetail.setEtqa(etqaService.findByCode(mandatoryGrantDetail.getSkillsProgram().getQualification().getEtqaid()));

			} else {
				mandatoryGrantDetail.setEtqa(mandatoryGrantDetail.getSkillsProgram().getEtqa());
			}
		}
	}
	
	public void applyUnitStandardData(MandatoryGrantDetail mandatoryGrantDetail) throws Exception{
		if (mandatoryGrantDetail.getUnitStandard() != null) {
			if (mandatoryGrantDetail.getUnitStandard().getNqflevelg2description() != null && !mandatoryGrantDetail.getUnitStandard().getNqflevelg2description().trim().isEmpty()) {
				mandatoryGrantDetail.setNqfLevels(nqfLevelsService.findByPre2009Description(mandatoryGrantDetail.getUnitStandard().getNqflevelg2description().trim()));
			}
		}
	}

	public void applyInterventionData(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		if (mandatoryGrantDetail.getInterventionType() == null) {
			mandatoryGrantDetail.setPivotNonPivot(null);
		} else {
			mandatoryGrantDetail.setPivotNonPivot(mandatoryGrantDetail.getInterventionType().getPivotNonPivot());
			if (mandatoryGrantDetail.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				mandatoryGrantDetail.setNqfAligned(yesNoService.findByKey(YesNoEnum.Yes.getYesNoLookupId()));
			} else {
				mandatoryGrantDetail.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
			}
		}
	}

	public Wsp resolveWspSignOff(Wsp wsp) {
		try {
			wsp.setWspSignoffs(wspSignoffService.findByWsp(wsp));
		} catch (Exception e) {
			logger.fatal(e);
			logger.fatal(e.getMessage(), e);
		}
		return wsp;
	}

	public long countDistinctWSP(Integer finYear) throws Exception{
		return dao.countDistinctWSP(finYear);
	}
	
	public long countDistinctWSPPassedDraft(Integer finYear) {
		return dao.countDistinctWSPPassedDraft(finYear);
	}
	
	public int countDistinctWSPByClo(Integer finYear, Users clo) throws Exception {
		return dao.countDistinctWSPByClo(finYear, clo);
	}
	
	public int countDistinctWSPByCloAndFinalYear(Integer finYear, Users clo, Long finalYear) throws Exception {
		return dao.countDistinctWSPByCloAndFinalYear(finYear, clo, finalYear);
	}

	public List<Wsp> findRandomCompanies(Integer finYear, int limit) throws Exception{
		return dao.findRandomCompanies(finYear, limit);
	}
	
	public List<Wsp> findRandomCompaniesByClo(Integer finYear, int limit, Users clo) throws Exception{
		return dao.findRandomCompaniesByClo(finYear, limit, clo);
	}
	
	public List<Wsp> findRandomCompaniesByCloAndFinalYear(Integer finYear, int limit, Users clo, Long finalYear) throws Exception {
		return dao.findRandomCompaniesByCloAndFinalYear(finYear, limit, clo, finalYear);
	}

	public List<Wsp> findRandomCompaniesForMgVerification(Integer finYear, int limit) {
		return dao.findRandomCompaniesForMgVerification(finYear, limit);
	}

	public List<MandatoryGrantDetail> findRandomMandatoryGrantDetailCompanies(Integer finYear, int limit) {
		return dao.findRandomMandatoryGrantDetailCompanies(finYear, limit);
	}

	/**
	 * Get count of MandatoryGrantDetail for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            MandatoryGrantDetail class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the MandatoryGrantDetail entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<MandatoryGrantDetail> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<MandatoryGrantDetail> findByWSP(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSP(wsp, wspReport);
	}

	public List<MandatoryGrantDetail> findByWSPCsv(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSPCsv(wsp, wspReport);
	}

	public List<MandatoryGrantDetail> findByWSPCsvAll(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSPCsvAll(wsp, wspReport);
	}

	public long findByWSPCount(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSPCount(wsp, wspReport);
	}

	public List<MandatoryGrantDetail> findByWSPPivotalPlan(Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) throws Exception {
		return dao.findByWSPPivotalPlan(wsp, wspReport, nqfAlignedID);
	}

	public List<MandatoryGrantDetail> findByWSPPivotalPlan(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) {
		return dao.findByWSPPivotalPlan(entity, startingAt, pageSize, sortField, sortOrder, filters, wsp, wspReport, nqfAlignedID);
	}

	public long allMandatoryGrantDetailCountWSPPivotalPlan(Map<String, Object> filters, Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) throws Exception {
		return dao.allMandatoryGrantDetailCountWSPPivotalPlan(filters, wsp, wspReport, nqfAlignedID);
	}

	public List<MandatoryGrantDetail> findByWSPNotHosting(Wsp wsp, WspReportEnum wspReport, String etqaCode) throws Exception {
		return dao.findByWSPNotHosting(wsp, wspReport, etqaCode);
	}

	public List<MandatoryGrantDetail> findByWSPNotHosting(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid, WspReportEnum wspReport, String etqaCode) {
		return dao.findByWSPNotHosting(entity, startingAt, pageSize, sortField, sortOrder, filters, wspid, wspReport, etqaCode);
	}

	public long findByWSPNotHostingCount(Long wspid, WspReportEnum wspReport, String etqaCode) throws Exception {
		return dao.findByWSPNotHostingCount(wspid, wspReport, etqaCode);
	}

	public List<MandatoryGrantDetail> findByWSPHosting(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.findByWSPHosting(wsp, wspReport, fundingID);
	}

	public List<MandatoryGrantDetail> findByWSPHosting(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.findByWSPHosting(entity, startingAt, pageSize, sortField, sortOrder, filters, wsp, wspReport, fundingID);
	}

	public long findByWSPHostingCount(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.findByWSPHostingCount(wsp, wspReport, fundingID);
	}

	public List<Wsp> findCompaniesForVerification(Integer finYear) {
		return dao.findCompaniesForVerification(finYear);
	}

	public Long findMGForDeviation(WspReportEnum wspReport, Integer finYear, Company company) {
		return dao.findMGForDeviation(wspReport, finYear, company.getId());
	}

	public List<MandatoryGrantDetail> findByWSPHosting(WspReportEnum wspReport, String etqaCode) throws Exception {
		return dao.findByWSPHosting(wspReport, etqaCode);
	}

	public List<MandatoryGrantDetail> atrSubmitedCheck(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.atrSubmitedCheck(wsp, wspReport);
	}

	public List<MandatoryGrantDetail> wspPivotalPlanCheck(Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) {
		return dao.wspPivotalPlanCheck(wsp, wspReport, nqfAlignedID);
	}

	@SuppressWarnings("unchecked")
	public void save(List<MandatoryGrantDetail> MandatoryGrantDetailCSVImports, Wsp wsp, WspReportEnum wspReport) throws Exception {
		for (MandatoryGrantDetail a : MandatoryGrantDetailCSVImports) {
			a.setWsp(wsp);
			a.setWspReport(wspReport);
			validateCSVData(a);
			if (a.getIdNumber() != null && a.getIdNumber().length() > 13) {
				System.out.println(a.getIdNumber());
			}
		}

		try {
//			this.dao.createBatch((List<IDataEntity>) (List<?>) MandatoryGrantDetailCSVImports);
			this.dao.createBatch((List<IDataEntity>) (List<?>) MandatoryGrantDetailCSVImports, 2000);
		} catch (org.hibernate.exception.DataException de) {
			throw new Exception("There is a problem loading the CSV file. Please ensure all data is correct before trying again.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void saveVersionTwo(List<MandatoryGrantDetail> MandatoryGrantDetailCSVImports, Wsp wsp, WspReportEnum wspReport, GrantOfoSelection grantOfoSelection) throws Exception {
		for (MandatoryGrantDetail a : MandatoryGrantDetailCSVImports) {
			a.setWsp(wsp);
			a.setWspReport(wspReport);
			validateCSVDataVersionTwo(a, grantOfoSelection);
		}

		try {
//			this.dao.createBatch((List<IDataEntity>) (List<?>) MandatoryGrantDetailCSVImports);
			this.dao.createBatch((List<IDataEntity>) (List<?>) MandatoryGrantDetailCSVImports, 2000);
		} catch (org.hibernate.exception.DataException de) {
			throw new Exception("There is a problem loading the CSV file. Please ensure all data is correct before trying again.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateCSVData(MandatoryGrantDetail a) {
		a.setImportError(false);

		try {
			if (a.getWspReport() == WspReportEnum.ATR) {
				if (a.getIdTypeCode() == null) throw new Exception("ID type code is required");
				a.setIdType(IdPassportEnum.getIdPassportEnumByValue(a.getIdTypeCode()));
				if (a.getIdType() == null) throw new Exception("Invalid ID type code");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getOfoCode() == null && a.getSpecialisationCode() == null) throw new Exception("An OFO or Specialisation code is required");
			if (a.getSpecialisationCode() != null) {
				if (a.getWspReport() == WspReportEnum.ATR) {
					a.setOfoCodes(lookupDAO.findOfoCodeBySpecialisationAtr(a.getSpecialisationCode()));
				} else {
					a.setOfoCodes(lookupDAO.findOfoCodeBySpecialisationWsp(a.getSpecialisationCode()));
				}
				if (a.getOfoCodes() == null) throw new Exception("Invalid OFO Specialisation Code");
			} else {
				if (a.getWspReport() == WspReportEnum.ATR) {
					a.setOfoCodes(lookupDAO.findOfoCodeAtr(a.getOfoCode()));
				} else {
					a.setOfoCodes(lookupDAO.findOfoCodeWsp(a.getOfoCode()));
				}
				if (a.getOfoCodes() == null) throw new Exception("Invalid OFO Code");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getIdNumber() != null && !a.getIdNumber().isEmpty()) {
				if (a.getIdType() != null && a.getIdType() == IdPassportEnum.RsaId) {
					GenericUtility.calcIDData(a);
				}
			} else if (a.getWspReport() == WspReportEnum.ATR) {
				throw new Exception("RSA ID Number/Passport Number is required");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getWspReport() == WspReportEnum.ATR) {
				if (a.getFirstName() == null || a.getFirstName().isEmpty()) throw new Exception("First Name is required");
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getWspReport() == WspReportEnum.ATR) {
				if (a.getLastName() == null || a.getLastName().isEmpty()) throw new Exception("Last Name is required");
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getGender() == null) {
				if (a.getGenderCode() == null) throw new Exception("Gender code is required");
				a.setGender(lookupDAO.findGender(a.getGenderCode()));
			}
			if (a.getGender() == null) throw new Exception("Invalid Gender Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getEquityCode() == null) throw new Exception("Equity code is required");
			a.setEquity(lookupDAO.findEquity(a.getEquityCode()));
			if (a.getEquity() == null) throw new Exception("Invalid Equity Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getNationality() == null) {
				if (a.getNationalityCode() == null) throw new Exception("Nationality code is required");
				a.setNationality(lookupDAO.findNationality(a.getNationalityCode()));
			}
			if (a.getNationality() == null) throw new Exception("Invalid Nationality Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEnrolmentStatusCode() == null) throw new Exception("Enrolment Status code is required");
			a.setEnrolmentStatus(lookupDAO.findEnrolmentStatus(a.getEnrolmentStatusCode()));
			if (a.getEnrolmentStatus() == null) throw new Exception("Invalid Enrolment Status Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getFundingCode() == null) throw new Exception("Funding code is required");
			a.setFunding(lookupDAO.findFunding(a.getFundingCode()));
			if (a.getFunding() == null) throw new Exception("Invalid Funding Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getInterventionTypeCode() == null) throw new Exception("Intervention Type code is required");
			a.setInterventionType(lookupDAO.findInterventionType(a.getInterventionTypeCode()));
			if (a.getInterventionType() == null) throw new Exception("Invalid Intervention Type Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getInterventionType() != null && a.getInterventionType().getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				if (SKILLS_SET_LIST.contains(a.getInterventionType().getId())) {

					if (a.getSkillsSetCode() == null) throw new Exception("Skills Set Code is required");
					a.setSkillsSet(lookupDAO.findSkillsSet(a.getSkillsSetCode()));
					if (a.getSkillsSet() == null) throw new Exception("Invalid Skills Set Code");

				} else if (SKILLS_PROGRAM_LIST.contains(a.getInterventionType().getId())) {

					if (a.getSkillsProgramCode() == null) throw new Exception("Skills Program Code is required");
					a.setSkillsProgram(lookupDAO.findSkillsProgram(a.getSkillsProgramCode()));
					if (a.getSkillsProgram() == null) throw new Exception("Invalid Skills Program Code");

				} else if (a.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {

					if (a.getQualificationCode() == null) throw new Exception("Unit Standard Code is required");
					a.setUnitStandard(lookupDAO.findUnitStandards(a.getQualificationCode()));
					if (a.getUnitStandard() == null) throw new Exception("Invalid Unit Standard Code");

				} else {

					if (a.getQualificationCode() == null) throw new Exception("Qualification Code is required");
					a.setQualification(lookupDAO.findQualification(a.getQualificationCode()));
					if (a.getQualification() == null) throw new Exception("Invalid Qualification Code");

				}
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getDisabilityCode() != null) {
				a.setDisability(lookupDAO.findDisability(a.getDisabilityCode()));
				if (a.getDisability() == null) throw new Exception("Invalid Disability Code");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getProviderTypeCode() == null) throw new Exception("Provider Type code is required");
			a.setProviderType(lookupDAO.findProviderType(a.getProviderTypeCode()));
			if (a.getProviderType() == null) throw new Exception("Invalid Provider Type Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getTrainingDeliveryMethodCode() == null) throw new Exception("Training Delivery Method code is required");
			a.setTrainingDeliveryMethod(lookupDAO.findTrainingDeliveryMethod(a.getTrainingDeliveryMethodCode()));
			if (a.getTrainingDeliveryMethod() == null) throw new Exception("Invalid Training Delivery Method Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getEmploymentTypeCode() == null) throw new Exception("Employment Type code is required");
			a.setEmploymentType(employmentTypeService.findByCode(a.getEmploymentTypeCode()));
			if (a.getEmploymentType() == null) throw new Exception("Invalid Employment Type Code");
			a.setEmploymentStatus(a.getEmploymentType().getEmploymentStatus());
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			applyInterventionData(a);
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getStartDate() == null) throw new Exception("Start date is required");
			else if (a.getStartDate().before(GenericUtility.deductYearsfromDate(GenericUtility.getEndOfApril(getSynchronizedDate()), 6))) throw new Exception("Start date is invalid");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEndDate() == null) throw new Exception("End date is required");
			else if (a.getStartDate().before(GenericUtility.deductYearsfromDate(GenericUtility.getEndOfApril(getSynchronizedDate()), 6))) throw new Exception("End date is invalid");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEstimatedCost() == null) throw new Exception("Estimated Cost of training is required");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getPivotNonPivot() != null && a.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				if (a.getInterventionType() != null && SKILLS_PROGRAM_LIST.contains(a.getInterventionType().getId())) {
					applySkillsProgram(a);
				} else if (a.getInterventionType() != null && SKILLS_SET_LIST.contains(a.getInterventionType().getId())) {
					applySkillsSet(a);
				} else {
					applySaqaData(a);
				}
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		if (a.isImportError()) {
			a.setImported(false);
		} else {
			a.setImported(true);
			a.setImportErrors(null);
		}
	}
	
	private void validateCSVDataVersionTwo(MandatoryGrantDetail a, GrantOfoSelection grantOfoSelection) {
		a.setImportError(false);
		try {
			if (a.getWspReport() == WspReportEnum.ATR) {
				if (a.getIdTypeCode() == null) throw new Exception("ID type code is required");
				a.setIdType(IdPassportEnum.getIdPassportEnumByValue(a.getIdTypeCode()));
				if (a.getIdType() == null) throw new Exception("Invalid ID type code");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			Integer wspOfoYear = 2019;
			Integer atrOfoYear = 2017;
			if (grantOfoSelection != null) {
				if (grantOfoSelection.getOfoAtrSelectionYear() != null) {
					atrOfoYear = grantOfoSelection.getOfoAtrSelectionYear();
				}
				if (grantOfoSelection.getOfoWspSelectionYear() != null) {
					wspOfoYear = grantOfoSelection.getOfoWspSelectionYear();
				}
			}
			
			if (a.getOfoCode() == null && a.getSpecialisationCode() == null) throw new Exception("An OFO or Specialisation code is required");
			if (a.getSpecialisationCode() != null) {
				if (a.getWspReport() == WspReportEnum.ATR) {
					a.setOfoCodes(lookupDAO.findOfoCodeBySpecialisationYear(a.getSpecialisationCode(), atrOfoYear));
				} else {
					a.setOfoCodes(lookupDAO.findOfoCodeBySpecialisationYear(a.getSpecialisationCode(), wspOfoYear));
				}
				if (a.getOfoCodes() == null) throw new Exception("Invalid OFO Specialisation Code");
			} else {
				if (a.getWspReport() == WspReportEnum.ATR) {
					a.setOfoCodes(lookupDAO.findOfoCodeByCodeAndYear(a.getOfoCode(), atrOfoYear));
				} else {
					a.setOfoCodes(lookupDAO.findOfoCodeByCodeAndYear(a.getOfoCode(), wspOfoYear));
				}
				if (a.getOfoCodes() == null) throw new Exception("Invalid OFO Code");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getIdNumber() != null && !a.getIdNumber().isEmpty()) {
				if (a.getIdType() != null && a.getIdType() == IdPassportEnum.RsaId) {
					GenericUtility.calcIDData(a);
				}
			} else if (a.getWspReport() == WspReportEnum.ATR) {
				throw new Exception("RSA ID Number/Passport Number is required");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getWspReport() == WspReportEnum.ATR) {
				if (a.getFirstName() == null || a.getFirstName().isEmpty()) throw new Exception("First Name is required");
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getWspReport() == WspReportEnum.ATR) {
				if (a.getLastName() == null || a.getLastName().isEmpty()) throw new Exception("Last Name is required");
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getGender() == null) {
				if (a.getGenderCode() == null) throw new Exception("Gender code is required");
				a.setGender(lookupDAO.findGender(a.getGenderCode()));
			}
			if (a.getGender() == null) throw new Exception("Invalid Gender Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getEquityCode() == null) throw new Exception("Equity code is required");
			a.setEquity(lookupDAO.findEquity(a.getEquityCode()));
			if (a.getEquity() == null) throw new Exception("Invalid Equity Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getNationality() == null) {
				if (a.getNationalityCode() == null) throw new Exception("Nationality code is required");
				a.setNationality(lookupDAO.findNationality(a.getNationalityCode()));
			}
			if (a.getNationality() == null) throw new Exception("Invalid Nationality Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEnrolmentStatusCode() == null) throw new Exception("Enrolment Status code is required");
			a.setEnrolmentStatus(lookupDAO.findEnrolmentStatus(a.getEnrolmentStatusCode()));
			if (a.getEnrolmentStatus() == null) throw new Exception("Invalid Enrolment Status Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if(a.getWspReport() == WspReportEnum.ATR) {
				if (a.getFundingCode() == null) throw new Exception("Funding code is required");
				a.setFunding(lookupDAO.findFunding(a.getFundingCode()));
				if (a.getFunding() == null) throw new Exception("Invalid Funding Code");
			}
			else if(a.getWspReport() == WspReportEnum.WSP) {
				a.setFundingCode("SETAMG_Fun");
				a.setFunding(new ResolveByCodeLookupDAO().findFunding("SETAMG_Fun"));
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getInterventionTypeCode() == null) throw new Exception("Intervention Type code is required");
			a.setInterventionType(lookupDAO.findInterventionType(a.getInterventionTypeCode()));
			if (a.getInterventionType() == null) throw new Exception("Invalid Intervention Type Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getInterventionType() != null && a.getInterventionType().getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				if (SKILLS_SET_LIST.contains(a.getInterventionType().getId())) {

					if (a.getSkillsSetCode() == null) throw new Exception("Skills Set Code is required");
					a.setSkillsSet(lookupDAO.findSkillsSet(a.getSkillsSetCode()));
					if (a.getSkillsSet() == null) throw new Exception("Invalid Skills Set Code");

				} else if (SKILLS_PROGRAM_LIST.contains(a.getInterventionType().getId())) {

					if (a.getSkillsProgramCode() == null) throw new Exception("Skills Program Code is required");
					a.setSkillsProgram(lookupDAO.findSkillsProgram(a.getSkillsProgramCode()));
					if (a.getSkillsProgram() == null) throw new Exception("Invalid Skills Program Code");

				} else if (a.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {

					if (a.getQualificationCode() == null) throw new Exception("Unit Standard Code is required");
					a.setUnitStandard(lookupDAO.findUnitStandards(a.getQualificationCode()));
					if (a.getUnitStandard() == null) throw new Exception("Invalid Unit Standard Code");

				} else if (a.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
					
					if (a.getInterventionTitle() == null) throw new Exception("Intervention Title Code is required");
					a.setNonCreditBearingIntervationTitle(lookupDAO.findNonCreditBearingIntervationTitle(a.getInterventionTitle()));
					if (a.getNonCreditBearingIntervationTitle() == null) throw new Exception("Invalid Intervention Title Code");
					
				} else {
					
					if (a.getQualificationCode() == null) throw new Exception("Qualification Code is required");
					a.setQualification(lookupDAO.findQualification(a.getQualificationCode()));
					if (a.getQualification() == null) throw new Exception("Invalid Qualification Code");
					
				}
			} else if (a.getInterventionType() != null && a.getInterventionType().getPivotNonPivot() == PivotNonPivotEnum.NonPivotal) {
				if (a.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
					if (a.getInterventionTitle() == null) throw new Exception("Intervention Title Code is required");
					a.setNonCreditBearingIntervationTitle(lookupDAO.findNonCreditBearingIntervationTitle(a.getInterventionTitle()));
					if (a.getNonCreditBearingIntervationTitle() == null) throw new Exception("Invalid Intervention Title Code");
				}
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		
		try {
			if (a.getMunicipalityCode() == null || a.getMunicipalityCode().trim().isEmpty())  throw new Exception("Municipality Code is required");
			a.setMunicipality(lookupDAO.findMunicipality(a.getMunicipalityCode()));
			if (a.getMunicipality() == null) throw new Exception("Invalid Municipality Code");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getDisabilityCode() != null) {
				a.setDisability(lookupDAO.findDisability(a.getDisabilityCode()));
				if (a.getDisability() == null) throw new Exception("Invalid Disability Code");
			}
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getProviderTypeCode() == null) throw new Exception("Provider Type code is required");
			a.setProviderType(lookupDAO.findProviderType(a.getProviderTypeCode()));
			if (a.getProviderType() == null) throw new Exception("Invalid Provider Type Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getTrainingDeliveryMethodCode() == null) throw new Exception("Training Delivery Method code is required");
			a.setTrainingDeliveryMethod(lookupDAO.findTrainingDeliveryMethod(a.getTrainingDeliveryMethodCode()));
			if (a.getTrainingDeliveryMethod() == null) throw new Exception("Invalid Training Delivery Method Code");
		} catch (Exception e) {
			storeError(a, e);
		}
		try {
			if (a.getEmploymentTypeCode() == null) throw new Exception("Employment Type code is required");
			a.setEmploymentType(employmentTypeService.findByCode(a.getEmploymentTypeCode()));
			if (a.getEmploymentType() == null) throw new Exception("Invalid Employment Type Code");
			a.setEmploymentStatus(a.getEmploymentType().getEmploymentStatus());
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			applyInterventionData(a);
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getStartDate() == null) throw new Exception("Start date is required");
			else if (a.getWspReport() == WspReportEnum.ATR && Year.of(getYear(a.getStartDate())).compareTo(Year.of(getYear(new Date()) - 1)) != 0)
				throw new Exception("Start date year should be between "+Year.of(getYear(new Date()) - 1));
			else if (a.getWspReport() == WspReportEnum.WSP && Year.of(getYear(a.getStartDate())).compareTo(Year.of(getYear(new Date()))) != 0)
				throw new Exception("Start date year should be between "+Year.of(getYear(new Date())));
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEndDate() == null) throw new Exception("End date is required");
			else if (a.getWspReport() == WspReportEnum.ATR && Year.of(getYear(a.getEndDate())).compareTo(Year.of(getYear(new Date()) - 1)) != 0)
				throw new Exception("End date year should be between "+Year.of(getYear(new Date()) - 1));
			else if (a.getWspReport() == WspReportEnum.WSP && Year.of(getYear(a.getEndDate())).compareTo(Year.of(getYear(new Date()))) != 0)
				throw new Exception("End date year should be between "+Year.of(getYear(new Date())));
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEstimatedCost() == null) throw new Exception("Estimated Cost of training is required");
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getPivotNonPivot() != null && a.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				if (a.getInterventionType() != null && SKILLS_PROGRAM_LIST.contains(a.getInterventionType().getId())) {
					applySkillsProgram(a);
				} else if (a.getInterventionType() != null && SKILLS_SET_LIST.contains(a.getInterventionType().getId())) {
					applySkillsSet(a);
				} else {
					applySaqaData(a);
				}
			}
		} catch (Exception e) {
			storeError(a, e);
		}

		if (a.isImportError()) {
			a.setImported(false);
		} else {
			a.setImported(true);
			a.setImportErrors(null);
		}
	}

	private void validateCSVDataOnUpdate(MandatoryGrantDetail a) {
		a.setImportError(false);
		a.setImportErrors(null);
		try {
			applyInterventionData(a);
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}

		try {
			if (a.getPivotNonPivot() != null && a.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				if (a.getInterventionType() != null && SKILLS_PROGRAM_LIST.contains(a.getInterventionType().getId())) {
					applySkillsProgram(a);
				} else if (a.getInterventionType() != null && SKILLS_SET_LIST.contains(a.getInterventionType().getId())) {
					applySkillsSet(a);
				} else {
					applySaqaData(a);
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
			storeError(a, e);
		}

		try {
			if (a.getStartDate() == null) throw new Exception("Start date is required");
			else if (a.getWspReport() == WspReportEnum.ATR && Year.of(getYear(a.getStartDate())).compareTo(Year.of(getYear(new Date()) - 1)) != 0)
				throw new Exception("Start date year should be between "+Year.of(getYear(new Date()) - 1));
			else if (a.getWspReport() == WspReportEnum.WSP && Year.of(getYear(a.getStartDate())).compareTo(Year.of(getYear(new Date()))) != 0)
				throw new Exception("Start date year should be between "+Year.of(getYear(new Date())));
		} catch (Exception e) {
			storeError(a, e);
		}

		try {
			if (a.getEndDate() == null) throw new Exception("End date is required");
			else if (a.getWspReport() == WspReportEnum.ATR && Year.of(getYear(a.getEndDate())).compareTo(Year.of(getYear(new Date()) - 1)) != 0)
				throw new Exception("End date year should be between "+Year.of(getYear(new Date()) - 1));
			else if (a.getWspReport() == WspReportEnum.WSP && Year.of(getYear(a.getEndDate())).compareTo(Year.of(getYear(new Date()))) != 0)
				throw new Exception("End date year should be between "+Year.of(getYear(new Date())));
		} catch (Exception e) {
			storeError(a, e);
		}

		if (a.isImportError()) {
			a.setImported(false);
		} else {
			a.setImported(true);
			a.setImportErrors(null);
		}
	}

	private void storeError(MandatoryGrantDetail a, Exception e) {
		a.setImportError(true);
		a.setImportErrors(a.getImportErrors() + "<li>" + e.getMessage() + "</li>");
	}

	public List<MandatoryGrantDetail> findByCompanyFinYear(Company company, WspReportEnum wspReport, long fundingID, long finYear) throws Exception {
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
		if (estimatedCostATR != null && estimatedCostWSP != null && estimatedCostWSP > 0.0 ) {
			wsp.setPercentageTrainingCostSpentTraining((estimatedCostATR / estimatedCostWSP) * 100);
		} else {
			wsp.setPercentageTrainingCostSpentTraining(0.0);
		}
		
		if (estimatedCostATR != null && wsp.getTotalPayroll() != null && wsp.getTotalPayroll() > 0.0) {
			wsp.setPercentagePayrollSpent((estimatedCostATR / wsp.getTotalPayroll()) * 100);
		} else {
			wsp.setPercentagePayrollSpent(0.0);
		}
	}

	/**
	 * Lazy load MandatoryGrantDetail with pagination, filter, sorting.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	public List<MandatoryGrantDetail> findByWsp(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid, WspReportEnum wspReport) {
		return dao.findByWsp(entity, startingAt, pageSize, sortField, sortOrder, filters, wspid, wspReport);
	}

	/**
	 * All MandatoryGrantDetail from WSP count.
	 *
	 * @param filters
	 *            the filters
	 * @param wspid
	 *            wspid
	 * @param wspReport
	 *            wspReport
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allMandatoryGrantDetailCount(Map<String, Object> filters, Long wspid, WspReportEnum wspReport) throws Exception {
		return dao.allMandatoryGrantDetailCount(filters, wspid, wspReport);
	}

	/**
	 * Lazy load MandatoryGrantDetail CSV with pagination, filter, sorting.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	public List<MandatoryGrantDetail> findByWspCsv(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid, WspReportEnum wspReport) {
		return dao.findByWspCsv(entity, startingAt, pageSize, sortField, sortOrder, filters, wspid, wspReport);
	}

	/**
	 * All MandatoryGrantDetail CSV from WSP count.
	 *
	 * @param filters
	 *            the filters
	 * @param wspid
	 *            wspid
	 * @param wspReport
	 *            wspReport
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allMandatoryGrantDetailCountCsv(Map<String, Object> filters, Long wspid, WspReportEnum wspReport) throws Exception {
		return dao.allMandatoryGrantDetailCountCsv(filters, wspid, wspReport);
	}

	public List<MandatoryGrantDetail> findByWSP(Wsp wsp) throws Exception {
		return dao.findByWSP(wsp);
	}
	
	public List<MandatoryGrantDetail> findByWSPByFundingAndReportType(Wsp wsp) throws Exception {
		return dao.findByWSPByFundingAndReportType(wsp);
	}

	public List<MandatoryGrantDetail> findByWSPForMgverification(Wsp wsp, long finYear) throws Exception {
		return dao.findByWSPForMgverification(wsp, finYear);
	}

	public long findByWSPAndReportCount(Wsp wsp, WspReportEnum wspReport) throws Exception {
		return dao.findByWSPAndReportCount(wsp, wspReport);
	}

	public long findByMandatoryGrantDetailCountOFO(Wsp wsp, long finYear) throws Exception {
		return dao.findByMandatoryGrantDetailCountOFO(wsp, finYear);
	}

	public long findByWSPCount(Wsp wsp) throws Exception {
		return dao.findByWSPCount(wsp);
	}
	
	public long findByWSPByFuningAndReportTypeCount(Wsp wsp) throws Exception {
		return dao.findByWSPByFuningAndReportTypeCount(wsp);
	}

	public List<EmployeeProfileBean> allEmployeeProfileBean(Company wsp) throws Exception {
		return dao.allEmployeeProfileBean(wsp);
	}

	public List<EmployeeEquityProfileBean> allEmployeeEquityProfileBean(Company wsp) throws Exception {
		return dao.allEmployeeEquityProfileBean(wsp);
	}

	public List<EmpEmploymentStatusBean> allEmpEmploymentStatusBean(Company wsp) throws Exception {
		return dao.allEmpEmploymentStatusBean(wsp);
	}

	public List<ATRReportSummayBean> allATRReportSummayBean(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.allATRReportSummayBean(wsp, wspReport, fundingID);
	}

	public List<WSPReportSummayBean> allWSPReportSummayBean(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.allWSPReportSummay(wsp, wspReport, fundingID);
	}

	public List<PivotalTrainingReportBean> allPivotalTrainingReport(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.allPivotalTrainingReport(wsp, wspReport, fundingID);
	}

	public List<PivotalTrainingReportBean> allPivotalTrainingReportWSP(Wsp wsp, WspReportEnum pivotalReport, Long nqfAlignedID) throws Exception {
		return dao.allPivotalTrainingReportWSP(wsp, pivotalReport, nqfAlignedID);
	}

	public List<DGApplicationSummaryBean> allDGApplicationSummaryBean(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		return dao.allDGApplicationSummaryBean(wsp, wspReport, fundingID);
	}

	public List<ProjectImplementationPlan> findProjectImplementationPlanData(Wsp wsp) throws Exception {
		return dao.findProjectImplementationPlanData(wsp);
	}

	public List<ProjectImplementationPlan> findProjectImplementationPlanDataUngroup(Wsp wsp) throws Exception {
		return dao.findProjectImplementationPlanDataUngroup(wsp);
	}

	public List<ProjectImplementationPlan> findPIMReportData(Wsp wsp) throws Exception {
		return dao.findPIMReportData(wsp);
	}

	public List<DgAllocation> findDgAllocationPlanData(Long wspID) throws Exception {
		return dao.findDgAllocationPlanData(wspID);
	}
	
	public List<MandatoryGrantDetail> allMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(Date fromDate, Date toDate, WspStatusEnum wspStatus, int numberOfEntries) throws Exception {
		return dao.allMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(fromDate, toDate, wspStatus, numberOfEntries);
	}
	
	public Integer countMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(Date fromDate, Date toDate, WspStatusEnum wspStatus) throws Exception {
		return dao.countMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(fromDate, toDate, wspStatus);
	}
	
	public List<MandatoryGrantDetail> allMandatoryGrantDetailFailedImportedByWspId(Long wspId, int numberOfEntries) throws Exception {
		return dao.allMandatoryGrantDetailFailedImportedByWspId(wspId, numberOfEntries);
	}
	
	public Integer countMandatoryGrantDetailFailedImportedByWspId(Long wspId) throws Exception {
		return dao.countMandatoryGrantDetailFailedImportedByWspId(wspId);
	}
	
	public void deleteMandatoryGrantDetailFailedImportedByWspId(Long wspId, int bacthSize, Long sessionUserId) throws Exception{
		List<IDataEntity> entriesToDelete = new ArrayList<IDataEntity>();
		entriesToDelete.addAll(allMandatoryGrantDetailFailedImportedByWspId(wspId, bacthSize));
		try {
			UploadTrackerAtrWspService.instance().createEntry("GRANT DATA", entriesToDelete.size(), "SYSTEM DELETION START", wspId, sessionUserId);
		} catch (Exception e) {
			logger.fatal(e);
		}
		if (entriesToDelete.size() != 0) {
			dao.deleteBatch(entriesToDelete);
		}
		try {
			UploadTrackerAtrWspService.instance().createEntry("GRANT DATA", entriesToDelete.size(), "SYSTEM DELETION END", wspId, sessionUserId);
		} catch (Exception e) {
			logger.fatal(e);
		}
		entriesToDelete = null;
	}
	
	public List<MgVerificationDetails> findSummarizedDataMgVerificationVersionOne(Wsp wsp, WspReportEnum wspReport, long fundingID, long enrolmentStatusIdOne, long enrolmentStatusIdTwo) {
		return dao.findSummarizedDataMgVerificationVersionOne(wsp, wspReport, fundingID, enrolmentStatusIdOne, enrolmentStatusIdTwo);
	}

	public List<GrantSspReportBean> populateSspReportingByGrantYearAndStatusLists(Integer grantYear, List<WspStatusEnum> wspStatusList, List<WspReportEnum> wspReporteList) throws Exception {
		return dao.populateSspReportingByGrantYearAndStatusLists(grantYear, wspStatusList, wspReporteList);
	}
	
}