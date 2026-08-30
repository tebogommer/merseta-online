package haj.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.SortOrder;

import haj.com.bean.AnnexureABean;
import haj.com.bean.AnnexureCBean;
import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.DgAllocationDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Address;
import haj.com.entity.AllocationChangesReasons;
import haj.com.entity.CategorizationData;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.entity.ProcessRoles;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.AllocationStatusEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AllocationChange;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Office;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Title;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.OfficeService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.TitleService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DgAllocationService extends AbstractService {

	/** The dao. */
	private WspService                          wspService                 = new WspService();
	private DgAllocationDAO                     dao                        = new DgAllocationDAO();
	private MandatoryGrantService               mandatoryGrantService      = new MandatoryGrantService();
	private RejectReasonsChildService           rejectReasonsService       = new RejectReasonsChildService();
	private SarsFilesService                    sarsFilesService           = new SarsFilesService();
	private MandatoryGrantRecommendationService grantRecommendationService = new MandatoryGrantRecommendationService();
	private InterventionTypeService             interventionTypeService    = new InterventionTypeService();
	private CategorisationService               categorisationService      = new CategorisationService();
	private DgAllocationParentService           dgAllocationParentService  = new DgAllocationParentService();
	private InterventionType                    artisan;
	private SDFCompanyService                   sdfCompanyService          = new SDFCompanyService();
	private boolean                             canDoCofunding             = true;
	private BigDecimal                          allCompaniesAwardAmount;
	private BigDecimal                          maxAllocationAmount;
	private AddressService                      addressService             = null;

	private BigDecimal availableAmount        = null;
	private BigDecimal previousTotal          = null;
	private BigDecimal previousCoFundingTotal = null;
	private BigDecimal availableCoFunding     = null;
	private BigDecimal coFundingAmount        = BigDecimal.ZERO;

	private RegionService regionService;

	private CompanyService companyService = new CompanyService();

	private CategorizationDataService categorizationDataService = new CategorizationDataService();

	private SignoffService signoffService;

	private ActiveContractsService activeContractsService;

	/**
	 * Get all DgAllocation
	 * @author TechFinium
	 * @see DgAllocation
	 * @return a list of {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 * the exception
	 */
	public List<DgAllocation> allDgAllocation() throws Exception {
		return dao.allDgAllocation();
	}

	/**
	 * Create or update DgAllocation.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DgAllocation
	 */
	public void create(DgAllocation entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update DgAllocation.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DgAllocation
	 */
	public void update(DgAllocation entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DgAllocation.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DgAllocation
	 */
	public void delete(DgAllocation entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 * the exception
	 * @see DgAllocation
	 */
	public DgAllocation findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find DgAllocation by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 * the exception
	 * @see DgAllocation
	 */
	public List<DgAllocation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load DgAllocation
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgAllocation}
	 * @throws Exception
	 * the exception
	 */
	public List<DgAllocation> allDgAllocation(int first, int pageSize) throws Exception {
		return dao.allDgAllocation(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DgAllocation for lazy load
	 * @author TechFinium
	 * @return Number of rows in the DgAllocation
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(DgAllocation.class);
	}

	/**
	 * Lazy load DgAllocation with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * DgAllocation class
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
	 * @return a list of {@link haj.com.entity.DgAllocation} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocation(Class<DgAllocation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DgAllocation>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of DgAllocation for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * DgAllocation class
	 * @param filters
	 * the filters
	 * @return Number of rows in the DgAllocation entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<DgAllocation> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Check to see if allocation can commence
	 * @param wsp
	 * @throws Exception
	 */
	public void checkForAllocation(Wsp wsp) throws Exception {
		DgVerification dg = DgVerificationService.instance().findByWspId(wsp);
		// WSP check
		boolean wspApproved = wsp != null && wsp.getWspStatusEnum() != null && wsp.getWspStatusEnum() == WspStatusEnum.Approved;
		// verification check
		boolean dgVerificationApproved = dg != null && dg.getStatus() != null && dg.getStatus() == ApprovalEnum.Approved;
		// check if both WSP and verification both approved before allocation generation
		if (wsp.getFinYearNonNull() == null) doAllocation(wsp);
	}

	public void checkForAllocationWithException(Wsp wsp) throws Exception {
		DgVerification dg = DgVerificationService.instance().findByWspId(wsp);
		// WSP check
		boolean wspApproved = wsp != null && wsp.getWspStatusEnum() != null && wsp.getWspStatusEnum() == WspStatusEnum.Approved;
		// verification check
		boolean dgVerificationApproved = dg != null && dg.getStatus() != null && dg.getStatus() == ApprovalEnum.Approved;
		// check if both WSP and verification both approved before allocation generation
		if (dgVerificationApproved) {
			doAllocation(wsp);
		}
		// if (wspApproved && dgVerificationApproved) doAllocation(wsp);
		// else throw new Exception("Can't allocate, please ensure verification and
		// grant application are both approved.");
	}

	public void doAllocation(Wsp wsp) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		wsp = wspService.findByKey(wsp.getId());
		Boolean acceptCoFunding = wsp.getCoFundingPartnership() != null && wsp.getCoFundingPartnership().getId() == YesNoEnum.Yes.getYesNoLookupId();
		categorisationService.categorizeCompany(wsp.getCompany());
		CategorizationData cd = categorizationDataService.findByCompany(wsp.getCompany().getId());
		if (cd != null) {
			wsp.getCompany().setCategorization(cd.getCategorization());
		}
		SarsLevyDetails sld = null;
		if (wsp.getFinYearNonNull() != null) {
			sld = sarsFilesService.sdlSummaryByRefAndSchemeYear(wsp.getCompany().getLevyNumber(), wsp.getFinYearNonNull() - 2);
		}

		if (artisan == null) artisan = interventionTypeService.findByKey(HAJConstants.APPRENTICESHIP_ID);
		if (acceptCoFunding) {
			coFundingAmount = calcCofunding(sld, wsp.getCompany(), artisan.getBasicGrantAmount());
		}
		List<MandatoryGrant> mgd    = checkRequiredOrder(mandatoryGrantService.allMandatoryGrant(wsp));
		BigDecimal           amount = BigDecimal.ZERO;
		if (wsp.getFinYearNonNull() != null) {
			amount = sld.getDiscretionaryLevy() == null ? BigDecimal.ZERO : (wsp.getCompany().getCategorization() == CategorizationEnum.Platinum ? sld.getDiscretionaryLevy().multiply(new BigDecimal(2)) : sld.getDiscretionaryLevy());
		} else {
			amount = wsp.getDgYear().getAllocationAmount();
		}
		DgAllocationParent dgAllocationParent = new DgAllocationParent(wsp, amount);
		// System.out.println(dgAllocationParent.getDgLevyAmount());
		if (wsp.getFinYearNonNull() != null) {
			dgAllocationParent.setDgLevyOriginalAmount(sld.getDiscretionaryLevy() == null ? BigDecimal.ZERO : sld.getDiscretionaryLevy());
		}
		dgAllocationParent.setAvailableCoFundingAmount(coFundingAmount);
		dataEntities.add(dgAllocationParent);
		if (wsp.getFinYearNonNull() != null) {
			availableCoFunding = calcCofunding(sld, wsp.getCompany(), artisan.getBasicGrantAmount());
		} else {
			availableCoFunding = BigDecimal.ZERO;
		}
		for (MandatoryGrant mandatoryGrant : mgd) {
			DgAllocation da = new DgAllocation(dgAllocationParent);
			allocateMoney(wsp, sld, mandatoryGrant, da, false);
			dataEntities.add(da);
		}
		dao.createBatch(dataEntities);
	}

	public void redoAllocation(DgAllocationParent dgAllocationParent, boolean useCompanyRecommendation) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		SarsLevyDetails   sld          = sarsFilesService.sdlSummaryByRefAndSchemeYear(dgAllocationParent.getWsp().getCompany().getLevyNumber(), dgAllocationParent.getWsp().getFinYearNonNull() - 2);

		// relocate company categorization data
		CategorizationData cd = categorizationDataService.findByCompany(dgAllocationParent.getWsp().getCompany().getId());
		if (cd != null) {
			dgAllocationParent.getWsp().getCompany().setCategorization(cd.getCategorization());
		}

		List<DgAllocationParent> daps = allDgAllocationParentNotStatus(dgAllocationParent, AllocationStatusEnum.Rejected);

		if (dgAllocationParent.getAllocationStatusEnum() == null || dgAllocationParent.getAllocationStatusEnum() == AllocationStatusEnum.Approved) {
			daps.add(0, dgAllocationParent);
		}

		if (artisan == null) artisan = interventionTypeService.findByKey(HAJConstants.APPRENTICESHIP_ID);

		for (DgAllocationParent dap : daps) {
			List<DgAllocation> dgAllocations = findByParentNotStatus(dap, AllocationStatusEnum.Rejected);
			availableAmount         = null;
			previousTotal           = null;
			previousCoFundingTotal  = null;
			coFundingAmount         = BigDecimal.ZERO;
			availableCoFunding      = calcCofunding(sld, dap.getWsp().getCompany(), artisan.getBasicGrantAmount());
			allCompaniesAwardAmount = null;
			maxAllocationAmount     = null;
			for (DgAllocation da : dgAllocations) {
				if (!useCompanyRecommendation) {
					zeroEverything(da);
				}
				allocateMoney(dgAllocationParent.getWsp(), sld, da.getMandatoryGrant(), da, useCompanyRecommendation);
				dataEntities.add(da);
			}
			dao.updateBatch(dataEntities);
		}
	}

	private void allocateMoney(Wsp wsp, SarsLevyDetails sld, MandatoryGrant mandatoryGrant, DgAllocation da, boolean useCompanyRecommendation) throws Exception {
		setBasicInfo(availableAmount, availableCoFunding, coFundingAmount, sld, mandatoryGrant, da, useCompanyRecommendation);
		BigDecimal maxLevel = getRemainingAllocationAmount(wsp, da.getDgAllocationParent());

		if ((useCompanyRecommendation && da.getChangeAllocationLearners() != null) || (!useCompanyRecommendation)) {
			if ((da.getGrantAmount() != null && da.getGrantAmount().doubleValue() == 0) || (da.getNumberOfLearners() == null || da.getNumberOfLearners() == 0)) zeroEverything(da);
			else doCalculations(maxLevel, previousTotal, previousCoFundingTotal, mandatoryGrant, da);
		}
		da.setTotalAwardAmount(da.getAwardAmount().add(da.getCoFundingAwardAmount()));
		if (da.getTotalAwardAmount() != null) allCompaniesAwardAmount = allCompaniesAwardAmount.add(da.getTotalAwardAmount());
		if (da.getNumberDisabled() != null && da.getNumberDisabled() > 0) {
			int totalLearners = da.getMaxPossibleLearners() + da.getCoFundingLearnersAwarded();
			if (da.getNumberDisabled() >= totalLearners) {
				da.setDisabledGrantAmount(BigDecimal.valueOf(da.getDisabledGrantAmount().doubleValue() * totalLearners));
				da.setTotalAwardAmount(da.getTotalAwardAmount().add(da.getDisabledGrantAmount()));
				da.setDisabledTotalLearners(totalLearners);
			} else {
				da.setDisabledGrantAmount(BigDecimal.valueOf(da.getDisabledGrantAmount().doubleValue() * da.getNumberDisabled()));
				da.setTotalAwardAmount(da.getTotalAwardAmount().add(da.getDisabledGrantAmount()));
				da.setDisabledTotalLearners(da.getNumberDisabled().intValue());
			}
		} else {
			da.setDisabledGrantAmount(BigDecimal.ZERO);
			da.setDisabledTotalLearners(0);
		}
		availableAmount        = da.getBalanceRemaining();
		previousTotal          = da.getRunningTotal();
		previousCoFundingTotal = da.getCoFundingRunningTotal();
		availableCoFunding     = da.getRemainingCoFundingGrantAmount();
	}

	private void doCalculations(BigDecimal maxLeft, BigDecimal previousTotal, BigDecimal cofundingPreviousTotal, MandatoryGrant mandatoryGrant, DgAllocation da) {
		da.setRecommendedAmount(BigDecimal.valueOf(da.getGrantAmount().doubleValue() * da.getNumberOfLearners()));
		da.setMaxPossibleLearners(BigDecimal.valueOf(da.getAvailableAmount().doubleValue() / da.getGrantAmount().doubleValue()).intValue());
		da.setMaxPossibleAmount(BigDecimal.valueOf(da.getMaxPossibleLearners() * da.getGrantAmount().doubleValue()));
		da.setMaxLearners(da.getNumberOfLearners());
		if (da.getMaxPossibleLearners() > da.getMaxLearners()) da.setMaxPossibleLearners(da.getMaxLearners());
		if (previousTotal == null) da.setRunningTotal(BigDecimal.ZERO);
		else da.setRunningTotal(previousTotal);
		if (cofundingPreviousTotal == null) da.setCoFundingRunningTotal(BigDecimal.ZERO);
		else da.setCoFundingRunningTotal(cofundingPreviousTotal);
		if (da.getMaxPossibleLearners().intValue() > 0) {
			da.setAwardAmount(BigDecimal.valueOf(da.getGrantAmount().doubleValue() * da.getMaxPossibleLearners()));
			da.setRunningTotal(da.getRunningTotal().add(da.getAwardAmount()));
			da.setBalanceRemaining(da.getAvailableAmount().subtract(da.getAwardAmount()));
			da.setMaxPossibleAmountRemaining(da.getMaxPossibleAmount().subtract(da.getAwardAmount()));
			da.setCoFundingAwardAmount(BigDecimal.ZERO);
		} else {
			da.setAwardAmount(BigDecimal.ZERO);
			da.setBalanceRemaining(da.getAvailableAmount());
			if (da.getRemainingCoFundingGrantAmount() == null) da.setRemainingCoFundingGrantAmount(da.getCoFundingGrantAmount());
			da.setMaxPossibleAmountRemaining(da.getMaxPossibleAmount().subtract(da.getAwardAmount()));
			da.setCoFundingAwardAmount(BigDecimal.ZERO);
		}
		da.setMaxPossibleLearnersRemaining(da.getNumberOfLearners() - da.getMaxPossibleLearners());
		if (canDoCofunding && da.getMaxPossibleLearnersRemaining() > 0) {
			if (da.getDgAllocationParent().getWsp().getCoFundingPartnership() != null && da.getDgAllocationParent().getWsp().getCoFundingPartnership().getId() == YesNoEnum.Yes.getYesNoLookupId()) {
				// maxLeft = maxLeft.subtract(da.getAwardAmount());
				if (da.getRemainingCoFundingGrantAmount().doubleValue() > (da.getMaxPossibleLearnersRemaining() * (da.getGrantAmount().doubleValue() / 2))) {
					doCoFunding(mandatoryGrant, da, maxLeft);
				} else {
					da.setMaxPossibleLearnersRemaining(BigDecimal.valueOf(da.getRemainingCoFundingGrantAmount().doubleValue() / (da.getGrantAmount().doubleValue() / 2)).intValue());
					if (da.getMaxPossibleLearnersRemaining() > 0) {
						doCoFunding(mandatoryGrant, da, maxLeft);
					}
				}
			}
		}
	}

	public void doCoFunding(MandatoryGrant mandatoryGrant, DgAllocation da, BigDecimal maxLeft) {
		da.setCoFundingAwardAmount(BigDecimal.valueOf((da.getGrantAmount().doubleValue() / 2) * da.getMaxPossibleLearnersRemaining()));
		da.setCoFundingRunningTotal(da.getCoFundingRunningTotal().add(da.getCoFundingAwardAmount()));
		da.setRemainingCoFundingGrantAmount(da.getRemainingCoFundingGrantAmount().subtract(da.getCoFundingAwardAmount()));
		da.setCoFundingLearnersAwarded(da.getMaxPossibleLearnersRemaining());
	}

	private void zeroEverything(DgAllocation da) {
		da.setRecommendedAmount(BigDecimal.ZERO);
		da.setMaxLearners(0);
		da.setAwardAmount(BigDecimal.ZERO);
		if (previousTotal == null) da.setRunningTotal(BigDecimal.ZERO);
		else da.setRunningTotal(previousTotal);
		if (previousCoFundingTotal == null) da.setCoFundingRunningTotal(BigDecimal.ZERO);
		else da.setCoFundingRunningTotal(previousCoFundingTotal);
		da.setCrmLearners(0);
		da.setMaxPossibleLearners(0);
		da.setBalanceRemaining(da.getAvailableAmount().subtract(da.getAwardAmount()));
		da.setCoFundingAwardAmount(BigDecimal.ZERO);
		da.setTotalAwardAmount(BigDecimal.ZERO);
		da.setCoFundingLearnersAwarded(0);
	}

	private void setBasicInfo(BigDecimal availableAmount, BigDecimal availableCoFund, BigDecimal coFundingAmount, SarsLevyDetails sld, MandatoryGrant mandatoryGrant, DgAllocation da, boolean useCompanyRecommendation) throws Exception {
		if (sld != null) {
			da.setTotalLevyAmount(sld.getTotal());
		}

		da.setMandatoryGrant(mandatoryGrant);
		da.setCoFundingGrantAmount(coFundingAmount);
		da.setNumberDisabled(mandatoryGrant.getNumberDisabled());
		List<MandatoryGrantRecommendation> rec = grantRecommendationService.findByMG(mandatoryGrant);
		da.setRequestedAmount(BigDecimal.valueOf(mandatoryGrant.getInterventionType().getBasicGrantAmount().doubleValue() * mandatoryGrant.getAmount()));

		if (!useCompanyRecommendation) {
			da.setNumberOfLearners(null);
		}

		for (MandatoryGrantRecommendation mandatoryGrantRecommendation : rec) {
			da.setNumberOfLearners(mandatoryGrantRecommendation.getQuantity());
			da.setInterventionType(mandatoryGrantRecommendation.getInterventionType());
		}

		if (da.getInterventionType() == null) da.setInterventionType(da.getMandatoryGrant().getInterventionType());

		if (useCompanyRecommendation) {
			if (da.getChangeAllocationLearners() != null) {
				da.setSystemChangeAllocationLearners(false);
				if (da.getInitialLearners() == null) da.setInitialLearners(da.getNumberOfLearners());
				da.setNumberOfLearners(da.getChangeAllocationLearners());
			} else {
				da.setChangeAllocationLearners(0);
				da.setSystemChangeAllocationLearners(true);
				if (da.getInitialLearners() == null) da.setInitialLearners(da.getNumberOfLearners());
				da.setNumberOfLearners(da.getChangeAllocationLearners());
			}
		} else {
			if (da.getChangeAllocationLearners() != null) {
				if (da.getInitialLearners() == null) da.setInitialLearners(da.getNumberOfLearners());
				da.setNumberOfLearners(da.getChangeAllocationLearners());
			}
		}

		if (da.getNumberOfLearners() == null) da.setNumberOfLearners(da.getMandatoryGrant().getAmount());

		da.setGrantAmount(BigDecimal.valueOf(da.getInterventionType().getBasicGrantAmount()));

		if (da.getInterventionType() != null && da.getInterventionType().getDisabilityGrantAmount() != null) da.setDisabledGrantAmount(BigDecimal.valueOf(da.getInterventionType().getDisabilityGrantAmount()));
		else da.setDisabledGrantAmount(BigDecimal.ZERO);

		da.setTotalLearners(mandatoryGrant.getAmount());

		if (artisan == null) artisan = interventionTypeService.findByKey(HAJConstants.APPRENTICESHIP_ID);
		BigDecimal levyamount = da.getDgAllocationParent().getDgLevyAmount();

		if (levyamount == BigDecimal.ZERO && mandatoryGrant.getWsp().getFinYearNonNull() != null) {
			levyamount = BigDecimal.valueOf(this.artisan.getBasicGrantAmount());
		} else if (mandatoryGrant.getWsp().getFinYearNonNull() == null) {
			levyamount = mandatoryGrant.getWsp().getDgYear().getAllocationAmount();
		}

		// version two
		if (mandatoryGrant.getWsp().getFinYear() == null) {
			da.setAvailableAmount(mandatoryGrant.getWsp().getDgYear().getAllocationAmount());
		} else if (availableAmount != null) {
			da.setAvailableAmount(availableAmount);
		} else {
			if (da.getDgAllocationParent().getDgLevyOriginalAmount() != null) {
				BigDecimal levyamountOrginal = da.getDgAllocationParent().getDgLevyOriginalAmount();
				if ((artisan.getBasicGrantAmount() > levyamountOrginal.doubleValue() && levyamountOrginal.doubleValue() > 0) || (levyamountOrginal.doubleValue() == 0)) {
					if (da.getDgAllocationParent().getCompanyCategorization() != null && da.getDgAllocationParent().getCompanyCategorization() == CategorizationEnum.Platinum) {
						da.setAvailableAmount(BigDecimal.valueOf(artisan.getBasicGrantAmount()).multiply(new BigDecimal(2)));
					} else {
						da.setAvailableAmount(BigDecimal.valueOf(artisan.getBasicGrantAmount()));
					}
				} else {
					if ((artisan.getBasicGrantAmount() > levyamount.doubleValue() && levyamount.doubleValue() > 0) || (levyamount.doubleValue() == 0)) {
						if (da.getDgAllocationParent().getCompanyCategorization() != null && da.getDgAllocationParent().getCompanyCategorization() == CategorizationEnum.Platinum) {
							da.setAvailableAmount(BigDecimal.valueOf(artisan.getBasicGrantAmount()).multiply(new BigDecimal(2)));
						} else {
							da.setAvailableAmount(BigDecimal.valueOf(artisan.getBasicGrantAmount()));
						}
					} else {
						da.setAvailableAmount(levyamount);
					}
				}
			} else {
				if ((artisan.getBasicGrantAmount() > levyamount.doubleValue() && levyamount.doubleValue() > 0) || (levyamount.doubleValue() == 0)) {
					if (da.getDgAllocationParent().getCompanyCategorization() != null && da.getDgAllocationParent().getCompanyCategorization() == CategorizationEnum.Platinum) {
						da.setAvailableAmount(BigDecimal.valueOf(artisan.getBasicGrantAmount()).multiply(new BigDecimal(2)));
					} else {
						da.setAvailableAmount(BigDecimal.valueOf(artisan.getBasicGrantAmount()));
					}
				} else {
					da.setAvailableAmount(levyamount);
				}
			}
		}

		// Version One
		// if (availableAmount != null) da.setAvailableAmount(availableAmount);
		// else da.setAvailableAmount(((artisan.getBasicGrantAmount() > levyamount.doubleValue() && levyamount.doubleValue() > 0) ? BigDecimal.valueOf(artisan.getBasicGrantAmount()) : levyamount));

		if (availableCoFund != null) da.setRemainingCoFundingGrantAmount(availableCoFund);
		else da.setRemainingCoFundingGrantAmount(((artisan.getBasicGrantAmount() > da.getDgAllocationParent().getDgLevyAmount().doubleValue()) ? BigDecimal.valueOf(artisan.getBasicGrantAmount()) : da.getDgAllocationParent().getDgLevyAmount()));

		da.setCoFundingLearnersAwarded(0);
	}

	private BigDecimal calcCofunding(SarsLevyDetails da, Company company, Double artisanAmount) throws Exception {

		double multiplier = 0.0;
		switch (company.getCategorization()) {
			case Silver:
				multiplier = 0.5;
				break;
			case Gold:
				multiplier = 1.0;
				break;
			case Platinum:
				multiplier = 1.0;
				break;
			default:
				break;
		}
		if (da.getDiscretionaryLevy() == null) {
			da.setDiscretionaryLevy(BigDecimal.ZERO);
		}
		// return da.getDiscretionaryLevy();
		return BigDecimal.valueOf(multiplier * (da.getDiscretionaryLevy().doubleValue() > artisanAmount ? da.getDiscretionaryLevy().doubleValue() : artisanAmount));
	}

	public BigDecimal findMaxAllocationAmount(int year) throws Exception {
		DGYear dgyear = findAllocationAmount(year);
		return dgyear.getAllocationAmount();
	}

	public BigDecimal findMaxAllocationAmount(DGYear dgyear) throws Exception {
		return dgyear.getAllocationAmount();
	}

	public BigDecimal findTotalAllocated(int year, DgAllocationParent da) throws Exception {
		BigDecimal bd = dao.findTotalAllocated(year, da);
		return (bd == null ? BigDecimal.ZERO : bd);
	}

	public BigDecimal findTotalRequested(int year) throws Exception {
		BigDecimal bd = dao.findTotalRequested(year);
		return (bd == null ? BigDecimal.ZERO : bd);
	}

	public BigDecimal findTotalAllocated(DGYear year, DgAllocationParent da) throws Exception {
		if (year == null) return BigDecimal.ZERO;
		BigDecimal bd = dao.findTotalAllocated(year, da);
		return (bd == null ? BigDecimal.ZERO : bd);
	}

	public BigDecimal findTotalAllocatedWhereAmountAwarded(DgAllocationParent da) throws Exception {
		BigDecimal bd = dao.findTotalAllocatedWhereAmountAwarded(da);
		return (bd == null ? BigDecimal.ZERO : bd);
	}

	public BigDecimal findTotalRequested(DGYear year) throws Exception {
		if (year == null) return BigDecimal.ZERO;
		BigDecimal bd = dao.findTotalRequested(year);
		return (bd == null ? BigDecimal.ZERO : bd);
	}

	private BigDecimal getRemainingAllocationAmount(Wsp wsp, DgAllocationParent da) {
		BigDecimal remainingAllocationAmount;
		try {

			if (allCompaniesAwardAmount == null) {
				if (wsp.getFinYearNonNull() != null) allCompaniesAwardAmount = findTotalAllocated(wsp.getFinYearNonNull(), da);
				else allCompaniesAwardAmount = findTotalAllocated(wsp.getDgYear(), da);
			}

			if (maxAllocationAmount == null) {
				if (wsp.getDgYear() == null) {
					maxAllocationAmount = findMaxAllocationAmount(wsp.getFinYearNonNull());
				} else {
					maxAllocationAmount = wsp.getDgYear().getAllocationAmount();
				}
			}

			remainingAllocationAmount = maxAllocationAmount.subtract(allCompaniesAwardAmount);
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			remainingAllocationAmount = BigDecimal.ZERO;
		}
		return remainingAllocationAmount;
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, DgAllocationParent entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void approveAllocation(DgAllocationParent entity, Users u) throws Exception {
		entity.setAllocationStatusEnum(AllocationStatusEnum.Approved);
		dao.update(entity);
		redoAllocation(entity, false);

	}

	public void rejectAllocation(DgAllocationParent entity, Users u) throws Exception {
		entity.setAllocationStatusEnum(AllocationStatusEnum.Rejected);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);

		List<DgAllocation> das = findByParent(entity);
		for (DgAllocation da : das) {
			zeroEverything(da);
			dataEntities.add(da);
		}
		dao.updateBatch(dataEntities);

		redoAllocation(entity, false);
	}

	public void approveAllocationLineItem(DgAllocation entity, Users u) throws Exception {
		entity.setAllocationStatusEnum(AllocationStatusEnum.Approved);
		dao.update(entity);
		redoAllocation(entity.getDgAllocationParent(), false);
	}

	public void rejectAllocationLineItem(DgAllocation entity, Users u) throws Exception {
		entity.setAllocationStatusEnum(AllocationStatusEnum.Rejected);
		zeroEverything(entity);
		dao.update(entity);
		redoAllocation(entity.getDgAllocationParent(), false);
	}

	public void requesteWorkflow(List<DgAllocation> entities, Users u) throws Exception {
		if (entities != null && entities.size() > 0) {
			List<IDataEntity>  dataEntities   = new ArrayList<>();
			DgAllocationParent mainSubmission = new DgAllocationParent();

			for (DgAllocation entity : entities) {
				entity.getDgAllocationParent().setStatus(ApprovalEnum.PendingApproval);
				entity.getDgAllocationParent().setAllocationParent(mainSubmission);
				entity.getDgAllocationParent().setSubmissionDate(getSynchronizedDate());
				entity.getDgAllocationParent().setSubmissionUser(u);
				dataEntities.add(entity.getDgAllocationParent());
			}
			dao.create(mainSubmission);
			dao.updateBatch(dataEntities);
			TasksService.instance().findFirstInProcessAndCreateTask(u, mainSubmission.getId(), mainSubmission.getClass().getName(), ConfigDocProcessEnum.DG_ALLOCATION, false);
		} else {
			throw new Exception("No DG allocation prepared, please enter levy number and do allocation");
		}
	}

	public void completeWorkflow(DgAllocationParent entity, Users user, Tasks tasks, boolean versionTwoSuccessfulEmail) throws Exception {
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}

		if (error.length() > 0) throw new ValidationException(error);

		if (tasks.getProcessRole() != null) TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		else {
			entity.setAcceptanceDate(getSynchronizedDate());
			entity.setStatus(ApprovalEnum.AcceptedMAO);
			dao.update(entity);
			TasksService.instance().completeTask(tasks);
		}

		// no longer required
		// // Sending Email Notification
		// Region r = new Region();
		// if (entity.getWsp() != null) {
		// r = getRegion(entity.getWsp());
		// }
		// String region = "";
		// if (r != null && r.getId() != null) {
		// region = r.getDescription();
		// }
		//
		// if (entity.getWsp() != null) {
		// if (entity.getWsp().getCompany() != null) {
		// SDFCompany sDFCompany =
		// sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
		// if (sDFCompany != null) {
		// if (versionTwoSuccessfulEmail) {
		// sendDGApplicationSuccessfulEmailVersionTwo(entity, sDFCompany.getSdf(),
		// entity.getWsp().getCompany(), getCLO(entity.getWsp()), region,
		// String.valueOf(entity.getWsp().getFinYearNonNull()), "", "");
		// } else {
		// sendDGApplicationSuccessfulEmail(entity, sDFCompany.getSdf(),
		// entity.getWsp().getCompany(), getCLO(entity.getWsp()), region,
		// String.valueOf(entity.getWsp().getFinYearNonNull()), "", "");
		// }
		//
		// } else {
		// throw new Exception("No Sdf assigned to a company");
		// }
		//
		// } else {
		// throw new Exception("No company assigned to a wsp");
		// }
		// } else {
		// throw new Exception("No wsp assigned to a DG Allocation");
		// }
	}

	public void mailValidiations(DgAllocationParent entity) throws Exception {
		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany == null) {
					throw new Exception("No Sdf assigned to a company");
				}
			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void sendNotificationMOA(DgAllocationParent entity, Users user, boolean versionTwoSuccessfulEmail) throws Exception {
		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					if (versionTwoSuccessfulEmail) {
						sendDGApplicationSuccessfulEmailVersionTwo(entity, sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), "", "");
					} else {
						sendDGApplicationSuccessfulEmail(entity, sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), "", "");
					}

				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void requestHigherallocation(DgAllocationParent entity, Users user, Tasks tasks, AllocationChange allocationChange) throws Exception {
		String error = "";

		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}

		if (error.length() > 0) throw new ValidationException(error);

		if (tasks.getProcessRole() != null) TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		else {
			entity.setAcceptanceDate(getSynchronizedDate());
			entity.setStatus(ApprovalEnum.HigherAllocationRequest);
			dao.update(entity);
			if (allocationChange != null) {
				AllocationChangesReasons allocationChangesReasons = new AllocationChangesReasons(allocationChange, entity);
				dao.create(allocationChangesReasons);
			}
			TasksService.instance().completeTask(tasks);
		}

		// Sending Email Notification
		String reasons = "";
		if (allocationChange != null) {
			reasons = allocationChange.getDescription();
		}

		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					sendDGApplicationRequestHigherAllocationEmail(entity, sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), reasons, null);
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void requestHigherallocationVersionTwo(DgAllocationParent entity, Users user, Tasks tasks, AllocationChange allocationChange) throws Exception {
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}

		if (error.length() > 0) throw new ValidationException(error);

		if (tasks.getProcessRole() != null) TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		else {
			entity.setAcceptanceDate(getSynchronizedDate());
			entity.setStatus(ApprovalEnum.HigherAllocationRequest);
			dao.update(entity);
			if (allocationChange != null) {
				AllocationChangesReasons allocationChangesReasons = new AllocationChangesReasons(allocationChange, entity);
				dao.create(allocationChangesReasons);
			}
			TasksService.instance().completeTask(tasks);
		}
	}

	public void requestHigherallocationVersionTwoNotification(DgAllocationParent entity, Users user, AllocationChange allocationChange, ActiveContracts activeContracts) throws Exception {
		// Sending Email Notification
		String reasons = "";
		if (allocationChange != null) {
			reasons = allocationChange.getDescription();
		}

		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					sendDGApplicationRequestHigherAllocationEmail(entity, sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), reasons, activeContracts);
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void sendEmailNotConfirmNotification(DgAllocationParent entity, Users reciverUser) throws Exception {
		// Sending Email Notification
		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}
		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					Company company    = entity.getWsp().getCompany();
					Users   primarySDF = sDFCompany.getSdf();
					Users   cloUser    = getCLO(entity.getWsp());
					String  subject    = "DISCRETIONARY GRANT APPLICATION OUTCOME FOR #COMPANY# (#ENTITYID#): APPROVED";
					String  message    = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> DISCRETIONARY GRANT APPLICATION OUTCOME FOR #COMPANY# (#ENTITYID#): APPROVED </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Thank you for submitting a discretionary grant application #FINYEAR#. We are pleased to inform you that an award has been made in accordance with the merSETA Grant Policy.  Please may you complete the project implementation plan (PIP) on the NSDMS.</p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that you have been selected by #PrimarySdfFirstName# #PrimarySdfSurname# as the duly authorised signatory for the abovenamed entity. However, we note that you have not yet confirmed your email; a requirement in order for you to receive communication and details regarding the electronic Memorandum of Agreement (MOA) sign off. </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly note that the MOA must be signed off electronically by the duly authorised signatory within 30 business days from the date of acceptance.  The electronic MOA sign off process is detailed in the email that will be sent to the authorised signatory. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> <b>To confirm your email, kindly do the following:</b> </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> " + "1. <b>Go</b> to " + HAJConstants.PL_LINK + "<br/> " + "2. Click on <b>Re-send Confirmation Email</b>" + "<br/> " + "3. Enter your <b>RSA ID or Passport Number</b>" + "<br/> " + "4. Enter the <b>email address</b> linked to your profile (as this is the email address linked to your profile, use this email)" + "<br/> " + "5. Click on the button <b>Request re-send of confirmation email</b>" + "<br/> "
							+ "6. Go to your email and look for an email that will have the details required to confirm the email" + "<br/> " + "7. To confirm the email, click on the <b>confirm</b> link and you will be taken to a new screen" + "<br/> " + "8. Click on <b>login</b> and the system will re-direct you to a page where you are required to enter your email address and <b>system generated password</b> from your email confirmation notification" + "<br/> "
							+ "9. A Change Password screen will pop up and you must enter your new password." + "<br/> " + "10. The password must be at least 8 characters long and must contain at least one caps (i.e. A etc) letter, one numerical character (i.e. 1, 2, 3,4 etc) and one special character (e.g. !@#$%^*& etc)" + "<br/> " + "11. Please use your email and new password" + "<br/> " + "</p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the #Region# Office.</p> " + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";
					String  title      = "";
					if (reciverUser.getTitle() != null && reciverUser.getTitle().getId() != null) {
						title = reciverUser.getTitle().getDescription();
					}
					message = message.replaceAll("#Title#", title);
					message = message.replaceAll("#FirstName#", reciverUser.getFirstName());
					message = message.replaceAll("#Surname#", reciverUser.getLastName());
					subject = subject.replaceAll("#COMPANY#", company.getCompanyName());
					subject = subject.replaceAll("#ENTITYID#", company.getLevyNumber());
					message = message.replaceAll("#COMPANY#", company.getCompanyName());
					message = message.replaceAll("#ENTITYID#", company.getLevyNumber());
					message = message.replaceAll("#FINYEAR#", entity.getWsp().getFinYearNonNull().toString());
					message = message.replaceAll("#PrimarySdfFirstName#", primarySDF.getFirstName());
					message = message.replaceAll("#PrimarySdfSurname#", primarySDF.getLastName());
					message = message.replaceAll("#ClientLiaisonOfficerNameandSurname#", cloUser.getFirstName() + " " + cloUser.getLastName());
					message = message.replaceAll("#ClientLiaisonOfficerEmail#", cloUser.getFirstName() + " " + cloUser.getEmail());
					message = message.replaceAll("#Region#", region);
					GenericUtility.sendMail(reciverUser.getEmail(), subject, message, null);

				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void resetAllocation(DgAllocationParent entity, AllocationChange allocationChange, Users user, Tasks tasks) throws Exception {
		entity.setStatus(null);
		entity.setAllocationParent(null);
		redoAllocation(entity, true);
		dao.update(entity);
		if (allocationChange != null) {
			AllocationChangesReasons allocationChangesReasons = new AllocationChangesReasons(allocationChange, entity);
			dao.create(allocationChangesReasons);
		}
		TasksService.instance().completeTask(tasks);

		/// Sending Email notification
		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					sendDGApplicationAllocationChangeEmail(sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), entity.getAllocationStatusEnum().getFriendlyName());
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void sendBackToSDFToChangeAlteredLearners(DgAllocationParent entity, List<DgAllocation> dgAllocationList, Users user, Tasks tasks) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		entity.setStatus(ApprovalEnum.Approved);
		updateList.add(entity);
		for (DgAllocation allocation : dgAllocationList) {
			allocation.setChangeAllocationLearners(null);
			updateList.add(allocation);
		}
		if (updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
		List<Users> users              = new ArrayList<>();
		String      companyNameDisplay = "";
		SDFCompany  sdf                = sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany());
		if (sdf != null) {
			users.add(sdf.getSdf());
			companyNameDisplay = sdf.getCompany().getCompanyNameDisplay();
		} else {
			companyNameDisplay = entity.getWsp().getCompany().getCompanyNameDisplay();
		}
		String desc = "A Discretionary Grant allocation for company " + companyNameDisplay + " has been processed. Please review.";
		if (users.size() != 0) {
			TasksService.instance().createTaskUser(users, DgAllocationParent.class.getName(), entity.getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.DG_ALLOCATION, true);
		} else {
			TasksService.instance().createTaskUserDgAllocationFallBack(users, DgAllocationParent.class.getName(), entity.getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.DG_ALLOCATION, true);
			GenericUtility.mailError("DG Allocation, Primary SDF not found Final Approval", "<p>Primary SDF not found for company: " + companyNameDisplay + ".</p> However task created.<p> Description for task: " + desc + "</p><p> Refer to: haj.com.service.DgAllocationService.sendBackToSDFToChangeAlteredLearners(DgAllocationParent entity, Users user, Tasks tasks) </p>");
		}
	}

	public void changeAllocation(DgAllocationParent entity, AllocationChange allocationChange, Users user, Tasks tasks) throws Exception {

		entity.setStatus(ApprovalEnum.RequestedChange);
		dao.update(entity);

		if (allocationChange != null) {
			AllocationChangesReasons allocationChangesReasons = new AllocationChangesReasons(allocationChange, entity);
			dao.create(allocationChangesReasons);
		}

		TasksService.instance().completeTask(tasks);
		List<Users> users = new ArrayList<>();
		users.add(entity.getSubmissionUser());
		String desc = "A Discretionary Grant allocation for company " + entity.getWsp().getCompany().getCompanyNameDisplay() + " has requested a change";
		TasksService.instance().createTaskUser(users, DgAllocationParent.class.getName(), entity.getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.DG_ALLOCATION, false);

		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		String reasons = "";
		if (allocationChange != null) {
			reasons += allocationChange.getDescription();
		}

		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					sendDGApplicationAllocationChangeEmail(sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), reasons);
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void requestHigherAllocation(DgAllocationParent entity, AllocationChange allocationChange, Users user, Tasks tasks) throws Exception {
		AllocationChangesReasons allocationChangesReasons = new AllocationChangesReasons(allocationChange, entity);
		dao.create(allocationChangesReasons);
		TasksService.instance().completeTask(tasks);
	}

	public void approveWorkflow(DgAllocationParent entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectWorkflow(DgAllocationParent entity, Users user, Tasks tasks, List<RejectReasons> selectedRejectReason) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		dao.update(entity);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);

		Region r      = getRegion(entity.getWsp());
		String region = "";
		Users  toUser = entity.getWsp().getCreateUsers();
		if (r != null) {
			region = r.getDescription();
		}

		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					String reasons = "";
					if (selectedRejectReason.size() > 0) {
						reasons = convertRejectReasonsToHTML(selectedRejectReason);
					}
					sendDGApplicationUnsuccessfulEmail(entity, toUser, entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), "", reasons, user);
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void finalApproveWorkflow(DgAllocationParent entity, List<DgAllocation> children, Users user, Tasks tasks) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dataEntities.add(entity);

		for (DgAllocation da : children) {
			if (!dataEntities.contains(da.getDgAllocationParent())) {
				if (da.getDgAllocationParent().getAllocationStatusEnum() == null || da.getDgAllocationParent().getAllocationStatusEnum() == AllocationStatusEnum.Approved) {
					da.getDgAllocationParent().setStatus(ApprovalEnum.Approved);
					da.getDgAllocationParent().setApprovalDate(getSynchronizedDate());
					dataEntities.add(da.getDgAllocationParent());
					List<Users> users              = new ArrayList<>();
					String      companyNameDisplay = "";
					SDFCompany  sdf                = sdfCompanyService.findPrimarySDF(da.getDgAllocationParent().getWsp().getCompany());
					if (sdf != null) {
						users.add(sdf.getSdf());
						companyNameDisplay = sdf.getCompany().getCompanyNameDisplay();
					} else {
						companyNameDisplay = da.getDgAllocationParent().getWsp().getCompany().getCompanyNameDisplay();
					}
					String desc = "A Discretionary Grant allocation for company " + companyNameDisplay + " has been processed. Please review.";
					if (users.size() != 0) {
						TasksService.instance().createTaskUser(users, DgAllocationParent.class.getName(), da.getDgAllocationParent().getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.DG_ALLOCATION, true);
					} else {
						TasksService.instance().createTaskUserDgAllocationFallBack(users, DgAllocationParent.class.getName(), da.getDgAllocationParent().getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.DG_ALLOCATION, true);
						GenericUtility.mailError("DG Allocation, Primary SDF not found Final Approval", "<p>Primary SDF not found for company: " + companyNameDisplay + ".</p> However task created.<p> Description for task: " + desc + "</p><p> Refer to: haj.com.service.DgAllocationService.finalApproveWorkflow(DgAllocationParent, List<DgAllocation>, Users, Tasks) </p>");
					}

				} else {

					Region r = new Region();
					if (da.getDgAllocationParent().getWsp() != null) {
						r = getRegion(da.getDgAllocationParent().getWsp());
					}
					String region = "";
					if (r != null && r.getId() != null) {
						region = r.getDescription();
					}
					da.getDgAllocationParent().setStatus(ApprovalEnum.Rejected);
					da.getDgAllocationParent().setApprovalDate(getSynchronizedDate());
					dataEntities.add(da.getDgAllocationParent());

					if (da.getDgAllocationParent().getWsp() != null) {
						if (da.getDgAllocationParent().getWsp().getCompany() != null) {
							SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(da.getDgAllocationParent().getWsp().getCompany());
							if (sDFCompany != null) {
								String                   rejectReason = "";
								List<RejectReasonsChild> reasonsList  = rejectReasonsService.findByTargetClassAndKey(DgAllocationParent.class.getName(), da.getDgAllocationParent().getId());
								if (reasonsList.size() > 0) {
									rejectReason = convertToHTML(reasonsList);
								}
								sendDGApplicationUnsuccessfulEmail(da.getDgAllocationParent(), sDFCompany.getSdf(), da.getDgAllocationParent().getWsp().getCompany(), getCLO(da.getDgAllocationParent().getWsp()), region, String.valueOf(da.getDgAllocationParent().getWsp().getFinYearNonNull()), "", rejectReason, user);

							} else {
								throw new Exception("No Sdf assigned to a company");
							}

						} else {
							throw new Exception("No company assigned to a wsp");
						}
					} else {
						throw new Exception("No wsp assigned to a DG Allocation");
					}
				}

			}
		}
		dao.updateBatch(dataEntities);
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		// sendApproveOrRejectEmails(entity, children, user, tasks, dataEntities);
	}

	public void rejectChange(DgAllocationParent entity, List<DgAllocation> children, Users user, Tasks tasks) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dataEntities.add(entity);

		for (DgAllocation da : children) {
			da.setChangeAllocationLearners(null);
			dataEntities.add(da);
		}
		List<Users> users = new ArrayList<>();
		SDFCompany  sdf   = sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany());
		users.add(sdf.getSdf());
		String desc = "A Discretionary Grant allocation for company " + sdf.getCompany().getCompanyNameDisplay() + " has been processed. Please review.";
		TasksService.instance().createTaskUser(users, DgAllocationParent.class.getName(), entity.getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.DG_ALLOCATION, true);
		dao.updateBatch(dataEntities);

		TasksService.instance().completeTask(tasks);

	}

	public void resetAllocations(DgAllocationParent entity, List<DgAllocation> children, Users user, Tasks tasks) throws Exception {

		List<IDataEntity> updateEntityList = new ArrayList<>();
		entity.setStatus(null);
		entity.setAllocationParent(null);
		updateEntityList.add(entity);

		for (DgAllocation da : children) {
			if (!updateEntityList.contains(da.getDgAllocationParent())) {
				da.getDgAllocationParent().setStatus(null);
				da.getDgAllocationParent().setApprovalDate(null);
				da.getDgAllocationParent().setAllocationParent(null);
				updateEntityList.add(da.getDgAllocationParent());
			}
		}

		dao.updateBatch(updateEntityList);
		TasksService.instance().completeTask(tasks);
	}

	public void finalRejectWorkflow(DgAllocationParent entity, List<DgAllocation> children, Users user, Tasks tasks) throws Exception {

		List<IDataEntity> updateEntityList = new ArrayList<>();
		entity.setStatus(ApprovalEnum.Withdrawn);
		entity.setApprovalDate(getSynchronizedDate());
		updateEntityList.add(entity);

		for (DgAllocation da : children) {
			if (!updateEntityList.contains(da.getDgAllocationParent())) {
				da.getDgAllocationParent().setStatus(ApprovalEnum.Withdrawn);
				da.getDgAllocationParent().setApprovalDate(getSynchronizedDate());
				updateEntityList.add(da.getDgAllocationParent());
			}
		}
		dao.updateBatch(updateEntityList);
		TasksService.instance().completeTask(tasks);

		Region r = new Region();
		if (entity.getWsp() != null) {
			r = getRegion(entity.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}
		if (entity.getWsp() != null) {
			if (entity.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService.locateFirstPrimarySDF(entity.getWsp().getCompany());
				if (sDFCompany != null) {
					sendDGApplicationWithdrawalEmail(sDFCompany.getSdf(), entity.getWsp().getCompany(), getCLO(entity.getWsp()), region, String.valueOf(entity.getWsp().getFinYearNonNull()), "", entity.getDiscretionalWithdrawalAppealEnum().getFriendlyName());
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public List<DgAllocation> findByParent(DgAllocationParent wsp) throws Exception {
		return dao.findByParent(wsp);
	}

	public List<DgAllocation> findByParentWhereAmountAwarded(DgAllocationParent wsp) throws Exception {
		return dao.findByParentWhereAmountAwarded(wsp);
	}

	public List<DgAllocation> findBySuperParent(DgAllocationParent wsp) throws Exception {
		return dao.findBySuperParent(wsp);
	}

	public List<DgAllocation> allDgAllocationNotStatus() throws Exception {
		return dao.allDgAllocationNotStatus();
	}

	public List<DgAllocation> allDgAllocationNotStatusAdditional(DGYear dgYear) throws Exception {
		return dao.allDgAllocationNotStatusAdditional(dgYear);
	}

	public List<DgAllocation> allDgAllocationNotStatusAdditional() throws Exception {
		return dao.allDgAllocationNotStatusAdditional();
	}

	public DGYear findAllocationAmount(int year) throws Exception {
		return dao.findAllocationAmount(year);
	}

	public List<DgAllocationParent> allDgAllocationParentNotStatus(DgAllocationParent allocationParent, AllocationStatusEnum allocationStatusEnum) throws Exception {
		return dao.allDgAllocationParentNotStatus(allocationParent, allocationStatusEnum);
	}

	public List<DgAllocationParent> allDgAllocationParentStatus(AllocationStatusEnum allocationStatusEnum) throws Exception {
		return dao.allDgAllocationParentStatus(allocationStatusEnum);
	}

	public List<DgAllocation> findByParentNotStatus(DgAllocationParent wsp, AllocationStatusEnum allocationStatusEnum) throws Exception {
		return dao.findByParentNotStatus(wsp, allocationStatusEnum);
	}

	public List<DgAllocation> findByParentWsp(Wsp wsp) throws Exception {
		return dao.findByParentWsp(wsp.getId());
	}

	public int countByParentWsp(Wsp wsp) throws Exception {
		return dao.countByParentWsp(wsp.getId());
	}

	public List<DgAllocation> findByParentWspWithStatus(Wsp wsp) throws Exception {
		return dao.findByParentWspWithStatus(wsp.getId());
	}

	public int countByParentWspWithStatus(Wsp wsp) throws Exception {
		return dao.countByParentWspWithStatus(wsp.getId());
	}

	public void sendDGApplicationRequestHigherAllocationEmail(DgAllocationParent entity, Users user, Company company, Users clo, String cloRegion, String grantyear, String reasons, ActiveContracts activeContracts) throws Exception {
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail    = clo.getEmail();

		Users  ceo         = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("cloFullName", cloFullName);
		convertStringToHTML(reasons);

		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("DGD-TP-007-Notification_Discretionary_Grants_Application_Outcome_Successful.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("DISCRETIONARY_GRANT_APPLICATION.pdf");
		attachmentBeans.add(beanAttachment);
		byte[] bites1 = null;
		beanAttachment = new AttachmentBean();
		if (activeContracts != null) {
			bites1 = moaVersionTwoReturnByte(activeContracts, true);
		} else {
			bites1 = getContractBites(entity, true);
		}
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites1);
		beanAttachment.setFilename("MOA_Contract.pdf");
		attachmentBeans.add(beanAttachment);
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> DISCRETIONARY GRANT APPLICATION REQUEST FOR HIGHER ALLOCATION: #COMPANY# (#ENTITYID#) </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> The merSETA acknowledges receipt of your request for a higher allocation for the following reason:</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + reasons
				+ "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We wish to inform you that a Memorandum of Agreement (MOA) for the current allocation is enclosed and must be processed within 30 working days from date of issue while the merSETA considers your request.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that you are required to submit a motivation/proposal to the merSETA via email: dgmoa@merseta.org.za with respect to the request for a higher allocation. If your request is successful, another MOA will be issued. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly note that the Memorandum of Agreement (MOA) must be signed off electronically by the duly authorised signatory within 30 business days from the date of acceptance. The electronic MOA sign off process is detailed in the email that will be sent to the authorised signatory.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please note that should the PIP and MOA not be signed electronically on the NSDMS within 30 business days from the date of acceptance, the merSETA may withdraw the MOA. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For further assistance/support, kindly contact the designated Client Liaison Officer: " + "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the #Region# Office. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";

		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);

		// GenericUtility.sendMail(user.getEmail(), "DISCRETIONARY GRANT APPLICATION
		// REQUEST FOR HIGHER ALLOCATION", welcome, null);
		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), "DISCRETIONARY GRANT APPLICATION REQUEST FOR HIGHER ALLOCATION", welcome, attachmentBeans, null);
	}

	public void sendDGApplicationAllocationChangeEmail(Users user, Company company, Users clo, String cloRegion, String grantyear, String reasons) throws Exception {
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "<p>ALLOCATION CHANGE IN DISCRETIONARY GRANT APPLICATION FOR: #COMPANY# (#ENTITYID#) </p>" + "<p>Thank you for submitting a discretionary grant application for #grantyear#.</p>" + "<p>We acknowledge the request to change the allocation in your application for the following reason(s):</p>" + reasons + "<p>Kindly be advised that the request will be considered by the merSETA within 14 days "
				+ "from receipt of the request and after consultation with your organisation. " + "Kindly be advised that only one change request will be considered.</p>"

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: " + "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the  #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);

		GenericUtility.sendMail(user.getEmail(), "ALLOCATION CHANGE IN DISCRETIONARY GRANT APPLICATION", welcome, null);
	}

	public void sendDGApplicationSuccessfulEmailVersionTwo(DgAllocationParent entity, Users user, Company company, Users clo, String cloRegion, String grantyear, String Appealsubmissiondate, String reason) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail    = clo.getEmail();

		Users  ceo         = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("cloFullName", cloFullName);

		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("DGD-TP-007-Notification_Discretionary_Grants_Application_Outcome_Successful.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("DISCRETIONARY_GRANT_APPLICATION.pdf");
		attachmentBeans.add(beanAttachment);

		beanAttachment = new AttachmentBean();
		byte[] bites1 = null;
		if (entity.getWsp() != null) {
			bites1 = moaVerstionTwoByWspRetrunBytes(entity.getWsp());
		} else {
			bites1 = getContractBites(entity, true);
		}
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites1);
		beanAttachment.setFilename("MOA_Contract.pdf");
		attachmentBeans.add(beanAttachment);
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #title# #FirstName# #Surname#,</p>"
				// version two
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">DISCRETIONARY GRANT APPLICATION OUTCOME FOR #COMPANY# (#ENTITYID#)</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Thank you for submitting a discretionary grant application #grantyear#. We are pleased to inform you that an award has been made in accordance with the merSETA Grant Policy. Please may you complete the project implementation plan (PIP) on the NSDMS.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly note that the Memorandum of Agreement (MOA) must be signed off electronically by the duly authorised signatory within 30 business days from the date of acceptance. The electronic MOA sign off process is detailed in the email that will be sent to the authorised signatory.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that should the PIP and MOA not be signed electronically on the NSDMS within 30 business days from the date of acceptance, the merSETA may withdraw the MOA.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the #Region# Office.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Chief Executive Officer of the merSETA</p>";

		// version one
		// + "<p>DISCRETIONARY GRANT APPLICATION OUTCOME FOR #COMPANY# (#ENTITYID#):
		// APPROVED</p>"
		// + "<p>Thank you for submitting a discretionary grant application
		// #grantyear#.</p>"
		// + "<p>We are pleased to inform you that an award has been made in accordance
		// with the merSETA Grant Policy. " + "Please may you complete the project
		// implementation plan on the NSDMS and " + "submit together with the signed
		// Memorandum of Agreement (MOA) within 30 business days from the date of
		// receipt.</p>"
		// + "<p>Please note that should the enclosed MOA is not uploaded on the NSDMS
		// within 30 business days from the date of issue, the merSETA may withdraw the
		// MOA.</p>"
		// + "<p>For further assistance/support, kindly contact the designated Client
		// Liaison Officer: Client Liaison Officer " +
		// "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or
		// the #Region# Office. </p>"
		// + "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>"
		// + "<br/>"

		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);
		welcome = welcome.replaceAll("#Appealsubmissiondate#", Appealsubmissiondate);

		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), "DISCRETIONARY GRANT APPLICATION OUTCOME", welcome, attachmentBeans, null);
	}

	public void sendDGApplicationSuccessfulEmail(DgAllocationParent entity, Users user, Company company, Users clo, String cloRegion, String grantyear, String Appealsubmissiondate, String reason) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail    = clo.getEmail();

		Users  ceo         = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("cloFullName", cloFullName);

		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("DGD-TP-007-Notification_Discretionary_Grants_Application_Outcome_Successful.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("DISCRETIONARY_GRANT_APPLICATION.pdf");
		attachmentBeans.add(beanAttachment);

		beanAttachment = new AttachmentBean();
		byte[] bites1 = getContractBites(entity, true);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites1);
		beanAttachment.setFilename("MOA_Contract.pdf");
		attachmentBeans.add(beanAttachment);

		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}

		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" + "<p>DISCRETIONARY GRANT APPLICATION OUTCOME FOR #COMPANY# (#ENTITYID#): APPROVED</p>"

				+ "<p>Thank you for submitting a discretionary grant application  #grantyear#.</p>"

				+ "<p>We are pleased to inform you that an award has been made in accordance with the merSETA Grant Policy. " + "Please may you complete the project implementation plan on the NSDMS and " + "submit together with the signed Memorandum of Agreement (MOA) within 30 business days from the date of receipt.</p>"

				+ "<p>Please note that should the enclosed MOA is not uploaded on the NSDMS within 30 business days from the date of issue, the merSETA may withdraw the MOA.</p>"

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer " + "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the  #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>" + "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);
		welcome = welcome.replaceAll("#Appealsubmissiondate#", Appealsubmissiondate);

		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), "DISCRETIONARY GRANT APPLICATION OUTCOME", welcome, attachmentBeans, null);
	}

	public void sendDGApplicationUnsuccessfulEmail(DgAllocationParent entity, Users user, Company company, Users clo, String cloRegion, String grantyear, String Appealsubmissiondate, String reason, Users ceo_user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail    = clo.getEmail();

		Users  ceo         = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("reason", reason);
		params.put("cloFullName", cloFullName);

		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("DGD-TP-006-Notification_Discretionary_Grants_Application_Unsuccessful.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("DISCRETIONARY_GRANT_APPLICATION.pdf");
		attachmentBeans.add(beanAttachment);

		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" + "<p>DISCRETIONARY GRANT APPLICATION FOR: #COMPANY# (#ENTITYID#)</p>"

				+ "<p>Thank you for submitting a discretionary grant application for  #grantyear#.</p>"

				+ "<p>Please be advised that the application was unsuccessful for reason:</p>"

				+ reason

				+ "<p>Should you wish to appeal the discretionary grant application outcome, " + "please submit an appeal on the NSDMS within 14 days (10 business days) of receipt of this notification. " + "Kindly note that should you not appeal within the allocated time, your application status will be finalised and have a status of rejected.</p>"

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: " + "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the  #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p><b>Chief Executive Officer of the merSETA</b></p>" + "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);
		welcome = welcome.replaceAll("#Appealsubmissiondate#", Appealsubmissiondate);

		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), "DISCRETIONARY GRANT APPLICATION", welcome, attachmentBeans, null);
	}

	public void sendDGApplicationWithdrawalEmail(Users user, Company company, Users clo, String cloRegion, String grantyear, String Appealsubmissiondate, String reason) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail    = clo.getEmail();
		Users  ceo         = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}
		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("reason", convertStringToHTML(reason));
		params.put("cloFullName", cloFullName);

		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		byte[]                    bites           = JasperService.instance().convertJasperReportToByte("DGD-TP-005-Notification_Discretionary_Grants_Application_Withdrawal.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("WITHDRAWAL_OF_DISCRETIONARY_GRANT.pdf");
		attachmentBeans.add(beanAttachment);

		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" + "<p>WITHDRAWAL OF DISCRETIONARY GRANT APPLICATION FOR: #COMPANY# (#ENTITYID#)</p>"

				+ "<p>Thank you for submitting a discretionary grant application for  #grantyear#.</p>"

				+ "<p>We acknowledge the notification to withdraw your application for the following reason: </p>"

				+ reason

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: " + "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the  #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>" + "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);
		welcome = welcome.replaceAll("#Appealsubmissiondate#", Appealsubmissiondate);
		// GenericUtility.sendMailWithAttachement(user.getEmail(), "WITHDRAWAL OF
		// DISCRETIONARY GRANT APPLICATION", welcome, bites,
		// "WITHDRAWAL_OF_DISCRETIONARY_GRANT_APPLICATION.pdf", "pdf", null);
		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), "WITHDRAWAL OF DISCRETIONARY GRANT APPLICATION", welcome, attachmentBeans, null);
	}

	public byte[] getContractBitesTest(DgAllocationParent dgAllocationParent, boolean isElectricSignoff) throws Exception {
		Wsp                 wsp    = dgAllocationParent.getWsp();
		Map<String, Object> params = new HashMap<String, Object>();
		String              path   = HAJConstants.APP_PATH;
		// Creating Discretionary grant year to be displayed on the cover page
		String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYearNonNull()).substring(Math.max(String.valueOf(wsp.getFinYearNonNull()).length() - 2, 0));
		String year                    = wsp.getFinYearNonNull() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
		// Adding Discretionary_Grant_Agreeement_details params
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());

		// ***************************Dg Allocation Data*****************************
		ArrayList<DgAllocation> dgList              = new ArrayList<>();
		DgAllocationService     dgAllocationService = new DgAllocationService();
		if (wsp.getId() != null) {
			if (dgAllocationParent != null) {
				dgList = (ArrayList<DgAllocation>) dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
			}

		}
		params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
		params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));

		/*
		 * double totalAwardedAmnt=0.00; for(DgAllocation dga:dgList) { if(dga.getTotalAwardAmount() !=null) { totalAwardedAmnt +=dga.getTotalAwardAmount().longValueExact(); } }
		 * 
		 * params.put("total_awarded_amnt",totalAwardedAmnt);
		 */

		params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());

		// params.put("total_awarded_amnt",Math.round(dgAllocationService.findTotalAllocated(wsp.getFinYearNonNull(),
		// dgAllocationParent).longValue()));
		params.put("wsp", wsp);
		params.put("company", companyService.findByKey(wsp.getCompany().getId()));
		params.put("wsp_id", wsp.getId());
		params.put("wsp_fin_year", wsp.getFinYearNonNull());
		params.put("wsp_report", WspReportEnum.WSP.ordinal());
		params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
		params.put("region", r.getDescription());
		params.put("year", year);
		params.put("merSETA_CEO", "");// Not in User
		params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
		params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
		params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
		JasperService.addLogo(params);
		JasperService.addDiscretionaryGrantSubReports(params);
		// Adding books
		// params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
		// params.put("table_of_content", path +
		// "reports/DG_Agreement_Book_toc.jasper");
		// params.put("DGA_details", path +
		// "reports/Discretionary_Grant_Agreeement_details.jasper");

		if (isElectricSignoff) {
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			Integer prjectedStartDateInt = wsp.getFinYearNonNull() - 1;
			params.put("projectedStartDate", "1 April " + prjectedStartDateInt);
			params.put("projectedEndDate", "31 March " + wsp.getFinYearNonNull());
			if (wsp.getFinYearNonNull() > 2021){
				params.put("revisionDate", "01 October 2022");
				params.put("nextRevisionDate", "01 April 2025");
				params.put("DGA_details", path + "reports/newmoaver12/DGDTP001MemorandumOfAgreement.jasper");
			}
			else {
				params.put("revisionDate", "01 April 2019");
				params.put("nextRevisionDate", "01 April 2021");
				params.put("DGA_details", path + "reports/DGDTP001MemorandumOfAgreement.jasper");
			}
			// params.put("revisionDate", "01 April " + (wsp.getFinYearNonNull()));
			// params.put("nextRevisionDate", "01 April " + (wsp.getFinYearNonNull() + 4));
			// params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			// params.put("projectedStartDate", "1 Apr "+wsp.getFinYearNonNull());
			// params.put("projectedEndDate", "31 Mar "+(wsp.getFinYearNonNull()+1));
			// params.put("DGA_details", path +
			// "reports/DGDTP001MemorandumOfAgreement.jasper");
			params.put("pip_subreport", path + "reports/DGDTP001ProjectImplementationPlan.jasper");
		} else {
			params.put("revisionDate", "01 October 2018");
			params.put("nextRevisionDate", "01 October 2020");
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
		}

		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Agreement_Book.jasper", params);

		return bites;

	}

	public void sendDGApplicationRequestHigherAllocationEmail(DgAllocationParent entity, Users user, Company company, Users clo, String cloRegion, String grantyear, String reasons, boolean isElectricSignoff) throws Exception {
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail    = clo.getEmail();

		Users  ceo         = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("cloFullName", cloFullName);
		convertStringToHTML(reasons);

		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("DGD-TP-007-Notification_Discretionary_Grants_Application_Outcome_Successful.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("DISCRETIONARY_GRANT_APPLICATION.pdf");
		attachmentBeans.add(beanAttachment);

		beanAttachment = new AttachmentBean();
		byte[] bites1 = getContractBites(entity, isElectricSignoff);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites1);
		beanAttachment.setFilename("MOA_Contract.pdf");
		attachmentBeans.add(beanAttachment);

		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "<p>DISCRETIONARY GRANT APPLICATION REQUEST FOR HIGHER ALLOCATION: #COMPANY# (#ENTITYID#) </p>" + "<p>The merSETA acknowledges receipt of your request for a higher allocation for the following reason:</p>"

				+ "<p>" + reasons + "</p>"

				+ "<p>We wish to inform you that a Memorandum of Agreement (MOA) for the current allocation is enclosed and " + "must be processed within 30 working days from date of issue while the merSETA considers your request. </p>"

				+ "<p>Please be advised that you are required to submit a motivation/proposal to the merSETA via email: dgmoa@merseta.org.za " + "with respect to the request for a higher allocation. If your request is successful, another MOA will be issued.  </p>"

				+ "<p>Please be advised that should the enclosed MOA is not uploaded on the NSDMS within 30 working days from the date of issue, " + "the merSETA may withdraw the MOA. Furthermore, please note that you are also " + "required to complete the Project Implementation Plan online within the same period.</p>"

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: " + "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);

		// GenericUtility.sendMail(user.getEmail(), "DISCRETIONARY GRANT APPLICATION
		// REQUEST FOR HIGHER ALLOCATION", welcome, null);
		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), "DISCRETIONARY GRANT APPLICATION REQUEST FOR HIGHER ALLOCATION", welcome, attachmentBeans, null);
	}

	public byte[] getContractBites(DgAllocationParent dgAllocationParent, boolean isElectricSignoff) throws Exception {
		Wsp                 wsp    = dgAllocationParent.getWsp();
		Map<String, Object> params = new HashMap<String, Object>();
		String              path   = HAJConstants.APP_PATH;

		String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYearNonNull()).substring(Math.max(String.valueOf(wsp.getFinYearNonNull()).length() - 2, 0));
		String year                    = wsp.getFinYearNonNull() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";

		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());

		// ***************************Dg Allocation Data*****************************
		ArrayList<DgAllocation> dgList              = new ArrayList<>();
		DgAllocationService     dgAllocationService = new DgAllocationService();
		if (wsp.getId() != null) {
			if (dgAllocationParent != null) {
				dgList = (ArrayList<DgAllocation>) dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
			}

		}
		params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
		params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));

		params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());

		params.put("wsp", wsp);
		params.put("company", companyService.findByKey(wsp.getCompany().getId()));
		params.put("wsp_id", wsp.getId());
		params.put("wsp_fin_year", wsp.getFinYearNonNull());
		params.put("wsp_report", WspReportEnum.WSP.ordinal());
		params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
		params.put("region", r.getDescription());
		params.put("year", year);
		params.put("merSETA_CEO", "");// Not in User

		JasperService.addLogo(params);
		JasperService.addDiscretionaryGrantSubReports(params);

		// Adding books
		if (wsp.getFinYearNonNull() > 2021){
			params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
		}
		else {
			params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
		}
		params.put("initials", "JB");
		params.put("initials", "PM");
		if (isElectricSignoff) {
			params.put("revisionDate", "01 October 2022"); // 1 April 2019
			params.put("nextRevisionDate", "01 April 2025"); // 01 April 2021
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			Integer prjectedStartDateInt = wsp.getFinYearNonNull() - 1;
			params.put("projectedStartDate", "1 April " + prjectedStartDateInt);
			params.put("projectedEndDate", "31 March " + wsp.getFinYearNonNull());
			if (wsp.getFinYearNonNull() > 2021){
				params.put("revisionDate", "01 October 2022"); // 1 April 2019
				params.put("nextRevisionDate", "01 April 2025"); // 01 April 2021
				params.put("DGA_details", path + "reports/newmoaver12/DGDTP001MemorandumOfAgreement.jasper");
			}
			else {
				params.put("revisionDate", "01 April 2019"); 
				params.put("nextRevisionDate", "01 April 2021");
				params.put("DGA_details", path + "reports/DGDTP001MemorandumOfAgreement.jasper");
			}
		} else {
			params.put("revisionDate", "01 October 2018");
			params.put("nextRevisionDate", "01 October 2020");
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
		}
		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Agreement_Book.jasper", params);
		return bites;
	}

	/**
	 * Download application form.
	 */
	public void downloadMOA(DgAllocationParent dgAllocationParent, boolean isElectricSignoff) throws Exception {

		Wsp wsp = dgAllocationParent.getWsp();
		wsp = wspService.findByKey(wsp.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		String              path   = HAJConstants.APP_PATH;
		// Creating Discretionary grant year to be displayed on the cover page
		String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYearNonNull()).substring(Math.max(String.valueOf(wsp.getFinYearNonNull()).length() - 2, 0));
		String year                    = wsp.getFinYearNonNull() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
		// Adding Discretionary_Grant_Agreeement_details params
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		// ***************************Dg Allocation Data*****************************
		ArrayList<DgAllocation>   dgList                    = new ArrayList<>();
		DgAllocationService       dgAllocationService       = new DgAllocationService();
		DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();

		if (wsp.getId() != null) {
			dgAllocationParent = dgAllocationParentService.findByWSP(wsp.getId());
			if (dgAllocationParent != null) {
				dgList = (ArrayList<DgAllocation>) dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
			}

		}
		params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
		params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));

		List<AnnexureABean> annexureABeanList = new ArrayList<>();
		List<AnnexureCBean> annexureCBeanList = new ArrayList<>();
		annexureABeanList.add(new AnnexureABean("Patrick", 4, 5, 6, BigDecimal.valueOf(100.19), BigDecimal.valueOf(100.19)));
		annexureABeanList.add(new AnnexureABean("PPPPPP", 4, 5, 6, BigDecimal.valueOf(100.19), BigDecimal.valueOf(100.19)));

// Commented 2 lines for Jira #271
//		annexureCBeanList.add(new AnnexureCBean("Mthombeni", "Bushman", "1234", 6, 7));
//		annexureCBeanList.add(new AnnexureCBean("dddd", "Bushsssman", "1234", 6, 7));

// Calling new constructors of AnnexureCBean
		annexureCBeanList.add(new AnnexureCBean("OFOCode1", "Mthombeni", "Bushman", "1234", 6, 7));
		annexureCBeanList.add(new AnnexureCBean("OFOCode2", "dddd", "Bushsssman", "1234", 6, 7));

		params.put("isElectricSignoff", isElectricSignoff);

		params.put("annexureABeanList", new JRBeanCollectionDataSource(annexureABeanList));
		params.put("annexureCBeanList", new JRBeanCollectionDataSource(annexureCBeanList));

		params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users>                    ceoList                        = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
		Users                          ceo                            = null;
		if (ceoList.size() > 0) {
			ceo = ceoList.get(0);
		}

		String merSETA_CEO = "";
		if (ceo != null) {
			if (ceo.getTitle() != null) {
				TitleService titleService = new TitleService();
				Title        tile         = titleService.findByKey(ceo.getTitle().getId());
				merSETA_CEO = tile.getDescription() + " " + ceo.getLastName() + " " + ceo.getFirstName();
			} else {
				merSETA_CEO = ceo.getLastName() + " " + ceo.getFirstName();
			}

		}
		// **************************************************************************

		params.put("wsp", wsp);
		params.put("company", wsp.getCompany());
		params.put("wsp_id", wsp.getId());
		params.put("wsp_fin_year", wsp.getFinYearNonNull());
		params.put("wsp_report", WspReportEnum.WSP.ordinal());
		params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
		params.put("region", r.getDescription());
		params.put("year", year);
		params.put("merSETA_CEO", merSETA_CEO);
		params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
		params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
		params.put("projectedEndDate", "31 Mar " + wsp.getFinYearNonNull() + 1);
		JasperService.addLogo(params);
		JasperService.addDiscretionaryGrantSubReports(params);
		String fileName = wsp.getCompany().getLevyNumber() + "-(Yr" + lastTwoDigitsOfNextYear + ")-Discretionary_Grant_Agreement.pdf";

		if (isElectricSignoff) {

			// SignOff
			// Adding books
			params.put("initials", "JB");
			params.put("initialsTwo", "PM");
			String merSetaSignedAt = "Mpumalanga Siyabuswa Mall";
			params.put("merSetaSignedAt", merSetaSignedAt);
			String merSetaThis = "20";
			params.put("merSetaThis", merSetaThis);
			String merSetaDayOf = "July";
			params.put("merSetaDayOf", merSetaDayOf);
			String merSetaYear = "19";
			params.put("merSetaYear", merSetaYear);
			String merSetaName = "Patrick Solomons";
			params.put("merSetaName", merSetaName);

			String organisationSignedAt = "Johaneesburg Midrand Mall";
			params.put("organisationSignedAt", organisationSignedAt);
			String organisationThis = "30";
			params.put("organisationThis", organisationThis);
			String organisationDayOf = "June";
			params.put("organisationDayOf", organisationDayOf);
			String organisationYear = "20";
			params.put("organisationYear", organisationYear);
			String organisationName = "Bushman Themba";
			params.put("organisationName", organisationName);

			// PIP
			String primary_sdf_fullname     = "";
			String clo_fullname             = "";
			String primary_sdf_subtion_date = "";
			String crm_fullname             = "";
			String clo_aaproved_date        = "";
			String crm_approved_date        = "";
			String guid                     = wsp.getCompany().getCompanyGuid();

			params.put("primary_sdf_fullname", primary_sdf_fullname);
			params.put("primary_sdf_subtion_date", primary_sdf_subtion_date);
			params.put("clo_fullname", clo_fullname);
			params.put("clo_approved_date", clo_aaproved_date);
			params.put("crm_fullname", crm_fullname);
			params.put("crm_approved_date", crm_approved_date);

			params.put("guid", guid);
			// ********************************************

			ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
			List<ProjectImplementationPlan>  list                             = new ArrayList<>();
			list = projectImplementationPlanService.findByWsp(wsp);
			if (list == null || list.size() <= 0) {
				list = new ArrayList<>();
			}
			params.put("PIMDataSource1", new JRBeanCollectionDataSource(list));

			List<ProjectImplementationPlan> dummyList = new ArrayList<>();
			for (int x = 0; x < 10; x++) {
				ProjectImplementationPlan imp = new ProjectImplementationPlan();
				dummyList.add(imp);
			}
			params.put("PIMDataSource2", new JRBeanCollectionDataSource(dummyList));

			// END
			params.put("revisionDate", "01 October " + (wsp.getFinYearNonNull()));
			params.put("nextRevisionDate", "01 April " + (wsp.getFinYearNonNull() + 4));
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			params.put("projectedStartDate", "1 Apr " + wsp.getFinYearNonNull());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
			if (wsp.getFinYearNonNull() > 2021){
				params.put("revisionDate", "01 October " + (wsp.getFinYearNonNull()));
				params.put("nextRevisionDate", "01 April " + (wsp.getFinYearNonNull() + 4));
				params.put("DGA_details", path + "reports/newmoaver12/DGDTP001MemorandumOfAgreement.jasper");
			}
			else {
				params.put("revisionDate", "01 October " + (wsp.getFinYearNonNull()));
				params.put("nextRevisionDate", "01 April " + (wsp.getFinYearNonNull() + 4));
				params.put("DGA_details", path + "reports/DGDTP001MemorandumOfAgreement.jasper");
			}
			params.put("pip_subreport", path + "reports/DGDTP001ProjectImplementationPlan.jasper");
		} else {
			params.put("revisionDate", "01 October 2018");
			params.put("nextRevisionDate", "01 October 2020");
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
		}
		if (wsp.getFinYearNonNull() > 2021){
			params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
		}
		else {
			params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
		}
		JasperService.instance().createReportFromDBtoServletOutputStream("DG_Agreement_Book.jasper", params, fileName);
	}

	/*
	 * Downloads the new and old version of MOA by WSP
	 */
	public void donloadMoaVerstionTwoByWsp(Wsp wsp) throws Exception {
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		ActiveContracts activecontract = activeContractsService.findByWSP(wsp.getId());
		if (activecontract != null) {
			if (activecontract.getEletronicSignoff() != null && activecontract.getEletronicSignoff()) {
				downloadMoaVersionTwo(activecontract, true);
			} else {
				downloadMoaVersionTwo(activecontract, false);
			}
		} else {
			throw new Exception("Unable to locate Contract, contract support!");
		}
	}

	public ActiveContracts findActiveContractsByWsp(Wsp wsp) throws Exception {
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		return activeContractsService.findByWSP(wsp.getId());
	}

	public void downloadMoaVersionTwo(ActiveContracts activeContracts, boolean isElectricSignoff) throws Exception {
		if (regionService == null) {
			regionService = new RegionService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (dgAllocationParentService == null) {
			dgAllocationParentService = new DgAllocationParentService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}

		ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
		HostingCompanyEmployeesService   hostingCompanyEmployeesService   = new HostingCompanyEmployeesService();
		OfficeService                    officeService                    = new OfficeService();

		Company    company           = null;
		Address    address           = null;
		Office     office            = null;
		RegionTown companyRegionTown = null;

		DgAllocationParent dgAllocationParent = dgAllocationParentService.findByKey(activeContracts.getDgAllocationParent().getId());
		Wsp                wsp                = dgAllocationParent.getWsp();
		if (wsp.getCompany() != null) {
			company = companyService.findByKey(wsp.getCompany().getId());
			if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
				if (address.getTown() != null && address.getTown().getId() != null) {
					companyRegionTown = RegionTownService.instance().findByTown(address.getTown());
				}
			}
		}

		if (companyRegionTown != null && companyRegionTown.getRegion() != null && companyRegionTown.getRegion().getId() != null) {
			List<Office> officeList = officeService.allOfficesByRegionId(companyRegionTown.getRegion().getId());
			if (officeList != null && officeList.size() != 0) {
				office = officeService.findByKey(officeList.get(0).getId());
			}
		}

		Map<String, Object> params                  = new HashMap<String, Object>();
		String              path                    = HAJConstants.APP_PATH;
		String              lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYearNonNull()).substring(Math.max(String.valueOf(wsp.getFinYearNonNull()).length() - 2, 0));
		String              year                    = wsp.getFinYearNonNull() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
		String              fileName                = wsp.getCompany().getLevyNumber() + "-(Yr" + lastTwoDigitsOfNextYear + ")-Discretionary_Grant_Agreement.pdf";

		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Region     r  = regionService.findByKey(rt.getRegion().getId());

		List<DgAllocation>  dgList              = new ArrayList<>();
		DgAllocationService dgAllocationService = new DgAllocationService();
		if (wsp.getId() != null) {
			if (dgAllocationParent != null) {
				dgList = dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
			}
		}

		// description, maxPossibleLearners, coFundingLearnersAwarded,
		// disabledTotalLearners, disabledGrantAmount, totalAwardAmount
		List<AnnexureABean> annexureABeanList = new ArrayList<>();
		// interventionType, interventionTitle, qualificationCode, maxPossibleLearners,
		// coFundingLearnersAwarded
		List<AnnexureCBean> annexureCBeanList = new ArrayList<>();

		if (dgList != null && dgList.size() != 0) {
			for (DgAllocation allocation : dgList) {

				String resultADescription           = "";
				String interventionTitleDescription = "";
				String qualificationCodeDescription = "";
				// Added for JIRA #2201
				String qualificationTitleDescription = "";

				// locate Mandatory grant and mandatory grant recommendation if found
				MandatoryGrantRecommendation mgr = null;
				MandatoryGrant               mg  = null;
				if (allocation.getMandatoryGrant() != null && allocation.getMandatoryGrant().getId() != null) {
					mg = allocation.getMandatoryGrant();
					List<MandatoryGrantRecommendation> rec = grantRecommendationService.findByMG(allocation.getMandatoryGrant());
					for (MandatoryGrantRecommendation mandatoryGrantRecommendation : rec) {
						mgr = mandatoryGrantRecommendation;
						break;
					}
				}

				// populate descriptions used for report beans
				if (mg != null) {
					if (mgr != null) {
						if (mgr.getQualification() != null && mgr.getQualification().getId() != null) {
							resultADescription           = mgr.getQualification().getDescription();
							interventionTitleDescription = mgr.getQualification().getDescription();
							qualificationCodeDescription = mgr.getQualification().getCode().toString();
							qualificationTitleDescription = mgr.getQualification().getDescription().toString();
						} else if (mgr.getUnitStandards() != null && mgr.getUnitStandards().getId() != null) {
							resultADescription           = mgr.getUnitStandards().getTitle();
							interventionTitleDescription = mgr.getUnitStandards().getTitle();
							qualificationCodeDescription = mgr.getUnitStandards().getCode().toString();
							qualificationTitleDescription = mgr.getUnitStandards().getTitle().toString();
						} else if (mgr.getSkillsProgram() != null && mgr.getSkillsProgram().getId() != null) {
							resultADescription           = mgr.getSkillsProgram().getDescription();
							interventionTitleDescription = mgr.getSkillsProgram().getDescription();
							qualificationCodeDescription = mgr.getSkillsProgram().getProgrammeID();
							qualificationTitleDescription = mgr.getSkillsProgram().getDescription().toString();
						} else if (mgr.getSkillsSet() != null && mgr.getSkillsSet().getId() != null) {
							resultADescription           = mgr.getSkillsSet().getDescription();
							interventionTitleDescription = mgr.getSkillsSet().getDescription();
							qualificationCodeDescription = mgr.getSkillsSet().getCode();
							qualificationTitleDescription = mgr.getSkillsSet().getDescription().toString();
						} else if (mgr.getNonCreditBearingIntervationTitle() != null && mgr.getNonCreditBearingIntervationTitle().getId() != null) {
							resultADescription           = mgr.getNonCreditBearingIntervationTitle().getDescription();
							interventionTitleDescription = mgr.getNonCreditBearingIntervationTitle().getDescription();
							qualificationCodeDescription = mgr.getNonCreditBearingIntervationTitle().getCode();
							qualificationTitleDescription = mgr.getNonCreditBearingIntervationTitle().getDescription().toString();
						}

						// fail safe if values null
						boolean resultADescriptionNotPopulated           = (resultADescription.isEmpty() || resultADescription.equals(""));
						boolean interventionTitleDescriptionNotPopulated = (resultADescription.isEmpty() || resultADescription.equals(""));
						boolean qualificationCodeDescriptionNotPopulated = (resultADescription.isEmpty() || resultADescription.equals(""));

						if (resultADescriptionNotPopulated || interventionTitleDescriptionNotPopulated || qualificationCodeDescriptionNotPopulated) {
							if (mg.getQualification() != null && mg.getQualification().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getQualification().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getQualification().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getQualification().getCode().toString();
									qualificationTitleDescription = mg.getQualification().getDescription().toString();
								}
							} else if (mg.getUnitStandard() != null && mg.getUnitStandard().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getUnitStandard().getTitle();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getUnitStandard().getTitle();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getUnitStandard().getCode().toString();
									qualificationTitleDescription = mg.getUnitStandard().getTitle().toString();
								}
							} else if (mg.getSkillsProgram() != null && mg.getSkillsProgram().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getSkillsProgram().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getSkillsProgram().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getSkillsProgram().getProgrammeID();
									qualificationTitleDescription = mg.getSkillsProgram().getDescription().toString();
								}
							} else if (mg.getSkillsSet() != null && mg.getSkillsSet().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getSkillsSet().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getSkillsSet().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getSkillsSet().getCode();
									qualificationTitleDescription = mg.getSkillsSet().getDescription().toString();
								}
							} else if (mg.getNonCreditBearingIntervationTitle() != null && mg.getNonCreditBearingIntervationTitle().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getNonCreditBearingIntervationTitle().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getNonCreditBearingIntervationTitle().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getNonCreditBearingIntervationTitle().getCode();
									qualificationTitleDescription = mg.getNonCreditBearingIntervationTitle().getDescription().toString();
								}
							}
						}

					} else {
						if (mg.getQualification() != null && mg.getQualification().getId() != null) {
							resultADescription           = mg.getQualification().getDescription();
							interventionTitleDescription = mg.getQualification().getDescription();
							qualificationCodeDescription = mg.getQualification().getCode().toString();
							qualificationTitleDescription = mg.getQualification().getDescription().toString();
						} else if (mg.getUnitStandard() != null && mg.getUnitStandard().getId() != null) {
							resultADescription           = mg.getUnitStandard().getTitle();
							interventionTitleDescription = mg.getUnitStandard().getTitle();
							qualificationCodeDescription = mg.getUnitStandard().getCode().toString();
							qualificationTitleDescription = mg.getUnitStandard().getTitle().toString();
						} else if (mg.getSkillsProgram() != null && mg.getSkillsProgram().getId() != null) {
							resultADescription           = mg.getSkillsProgram().getDescription();
							interventionTitleDescription = mg.getSkillsProgram().getDescription();
							qualificationCodeDescription = mg.getSkillsProgram().getProgrammeID();
							qualificationTitleDescription = mg.getSkillsProgram().getDescription().toString();
						} else if (mg.getSkillsSet() != null && mg.getSkillsSet().getId() != null) {
							resultADescription           = mg.getSkillsSet().getDescription();
							interventionTitleDescription = mg.getSkillsSet().getDescription();
							qualificationCodeDescription = mg.getSkillsSet().getCode();
							qualificationTitleDescription = mg.getSkillsSet().getDescription().toString();
						} else if (mg.getNonCreditBearingIntervationTitle() != null && mg.getNonCreditBearingIntervationTitle().getId() != null) {
							resultADescription           = mg.getNonCreditBearingIntervationTitle().getDescription();
							interventionTitleDescription = mg.getNonCreditBearingIntervationTitle().getDescription();
							qualificationCodeDescription = mg.getNonCreditBearingIntervationTitle().getCode();
							qualificationTitleDescription = mg.getNonCreditBearingIntervationTitle().getDescription().toString();
						}
					}
				}

				// AnnexureABean
				AnnexureABean resultA = new AnnexureABean();

				resultA.setDescription(allocation.getInterventionType().getDescription());
				// DG Allocation Level
				resultA.setMaxPossibleLearners(((allocation.getMaxPossibleLearners() == null) ? 0 : allocation.getMaxPossibleLearners()));
				// DG Allocation Level
				resultA.setCoFundingLearnersAwarded(((allocation.getCoFundingLearnersAwarded() == null) ? 0 : allocation.getCoFundingLearnersAwarded()));
				// DG Allocation Level
				resultA.setDisabledTotalLearners(((allocation.getDisabledTotalLearners() == null) ? 0 : allocation.getDisabledTotalLearners()));
				// DG Allocation Level
				resultA.setDisabledGrantAmount(((allocation.getDisabledGrantAmount() == null) ? BigDecimal.valueOf(0) : allocation.getDisabledGrantAmount()));
				// DG Allocation Level
				resultA.setTotalAwardAmount(((allocation.getTotalAwardAmount() == null) ? BigDecimal.valueOf(0) : allocation.getTotalAwardAmount()));
				annexureABeanList.add(resultA);

				// AnnexureCBean
				AnnexureCBean resultC = new AnnexureCBean();
				// DG Allocation Level
				// Added for Jira #271
				resultC.setOfoCodes(((allocation.getMandatoryGrant().getOfoCodes().getOfoDescription() == null) ? "" : allocation.getMandatoryGrant().getOfoCodes().getOfoDescription()));
				
				resultC.setInterventionType(((allocation.getInterventionType() == null) ? "" : allocation.getInterventionType().getDescription()));
				resultC.setInterventionTitle(interventionTitleDescription);
				resultC.setQualificationCode(qualificationCodeDescription);
				//Added for JIRA #2201
				resultC.setQualificationTitle(qualificationTitleDescription);
				// DG Allocation Level
				resultC.setMaxPossibleLearners(((allocation.getMaxPossibleLearners() == null) ? 0 : allocation.getMaxPossibleLearners()));
				// DG Allocation Level
				resultC.setCoFundingLearnersAwarded(((allocation.getCoFundingLearnersAwarded() == null) ? 0 : allocation.getCoFundingLearnersAwarded()));
				annexureCBeanList.add(resultC);
			}
		}

		params.put("isElectricSignoff", isElectricSignoff);

		params.put("annexureABeanList", new JRBeanCollectionDataSource(annexureABeanList));
		params.put("annexureCBeanList", new JRBeanCollectionDataSource(annexureCBeanList));

		/* Sign off Information SDF/REP */
		// region
		String organisationSignedAt = "";
		// day of sign off
		String organisationThis = "";
		// month of sign off
		String organisationDayOf = "";
		// year of sign off
		String organisationYear = "";
		// User who signed off
		String organisationName = "";

		/* Sign off Information MerSETA */
		// region
		String merSetaSignedAt = "";
		// day of sign off
		String merSetaThis = "";
		// month of sign off
		String merSetaDayOf = "";
		// year of sign off
		String merSetaYear = "";
		// User who signed off
		String merSetaName = "";
		// Added for JIRA #2201
		String organisationPosition = "";
		String organisationWitness1Name = "";
		String organisationWitness1Id = "";
		String organisationWitness2Name = "";
		String organisationWitness2Id = "";
		String merSetaPosition = "";
		String merSetaWitness1Name = "";
		String merSetaWitness1Id = "";
		String merSetaWitness2Name = "";
		String merSetaWitness2Id = "";
		String merSetaCEO = "";
		String merSetaCOO = "";

		List<Signoff> signoffList = signoffService.findByTargetKeyAndClass(activeContracts.getId(), activeContracts.getClass().getName());
		String        intitalsOne = "";
		String        intitalsTwo = "";
		int           count       = 1;
		for (Signoff signoff : signoffList) {
			if (signoff.getUser() != null) {
				if (count == 1) {
					// SDF / Rep Sign Off
					if (signoff.getAccept() != null && signoff.getAccept() && signoff.getSignOffDate() != null) {
						intitalsOne       += signoff.getUser().getFirstName().trim().substring(0, 1).toUpperCase();
						intitalsOne       += signoff.getUser().getLastName().trim().substring(0, 1).toUpperCase();
						organisationName   = signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim();
						organisationThis   = HAJConstants.sdfDD.format(signoff.getSignOffDate());
						organisationDayOf  = HAJConstants.sdfMMMM.format(signoff.getSignOffDate());
						organisationYear   = HAJConstants.sdfYY.format(signoff.getSignOffDate());
						// Added for JIRA #2201
						//organisationPosition = sdfCompanyService.findByCompanyAndUser(wsp.getCompany(), signoff.getUser()).getSdfType().getDescription();
					}
					if (signoff.getAccept() != null && signoff.getAccept() && companyRegionTown != null) {
						organisationSignedAt = companyRegionTown.getTown().getDescription().trim();
					}
				} else if (count == 2) {
					// MerSETA Sign off
					if (signoff.getAccept() != null && signoff.getAccept() && signoff.getSignOffDate() != null) {
						intitalsTwo  += signoff.getUser().getFirstName().substring(0, 1).toUpperCase();
						intitalsTwo  += signoff.getUser().getLastName().substring(0, 1).toUpperCase();
						merSetaName   = signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim();
						merSetaThis   = HAJConstants.sdfDD.format(signoff.getSignOffDate());
						merSetaDayOf  = HAJConstants.sdfMMMM.format(signoff.getSignOffDate());
						merSetaYear   = HAJConstants.sdfYY.format(signoff.getSignOffDate());
						// Added for JIRA #2201
						merSetaPosition = hostingCompanyEmployeesService.findByUserReturnHostCompany(signoff.getUser().getId()).getJobTitle().getDescription();
					}
					if (signoff.getAccept() != null && signoff.getAccept() && office != null) {
						merSetaSignedAt = "Johannesburg";
					}
				} else {
					break;
				}
			}
			count++;
		}

		params.put("organisationSignedAt", organisationSignedAt);
		params.put("organisationThis", organisationThis);
		params.put("organisationDayOf", organisationDayOf);
		params.put("organisationYear", organisationYear);
		params.put("organisationName", organisationName);
		params.put("merSetaSignedAt", merSetaSignedAt);
		params.put("merSetaThis", merSetaThis);
		params.put("merSetaDayOf", merSetaDayOf);
		params.put("merSetaYear", merSetaYear);
		params.put("merSetaName", merSetaName);

		// Added for JIRA #2201
		params.put("organisationPosition", organisationPosition);
		params.put("organisationWitness1Name", organisationWitness1Name);
		params.put("organisationWitness1Id", organisationWitness1Id);
		params.put("organisationWitness2Name", organisationWitness2Name);
		params.put("organisationWitness2Id", organisationWitness2Id);
		
		params.put("merSetaPosition", merSetaPosition);
		params.put("merSetaWitness1Name", merSetaWitness1Name);
		params.put("merSetaWitness1Id", merSetaWitness1Id);
		params.put("merSetaWitness2Name", merSetaWitness2Name);
		params.put("merSetaWitness2Id", merSetaWitness2Id);
		
		List<Users> mersetaCEOList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
		merSetaCEO = mersetaCEOList.get(0).getFirstName() + " " + mersetaCEOList.get(0).getLastName();
		params.put("merSetaCEO", merSetaCEO);
		
		List<Users> mersetaCOOList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.COO_JOB_TITLE_ID);;
		merSetaCOO = mersetaCOOList.get(0).getFirstName() + " " + mersetaCOOList.get(0).getLastName();
		params.put("merSetaCOO", merSetaCOO);

		// PIP
		String primary_sdf_fullname     = "";
		String clo_fullname             = "";
		String primary_sdf_subtion_date = "";
		String crm_fullname             = "";
		String clo_aaproved_date        = "";
		String crm_approved_date        = "";
		String guid                     = wsp.getCompany().getCompanyGuid();

		if (activeContracts != null) {
			if (activeContracts.getClo() != null) {
				clo_fullname = activeContracts.getClo().getFirstName() + " " + activeContracts.getClo().getLastName();
				if (activeContracts.getCloSignDate() != null) {
					clo_aaproved_date = HAJConstants.sdf3.format(activeContracts.getCloSignDate());
				}
			}
			if (activeContracts.getCrm() != null) {
				crm_fullname = activeContracts.getCrm().getFirstName() + " " + activeContracts.getCrm().getLastName();
				if (activeContracts.getCrmSignDate() != null) {
					crm_approved_date = HAJConstants.sdf3.format(activeContracts.getCrmSignDate());
				}
			}
			if (activeContracts.getSdf() != null) {
				primary_sdf_fullname = activeContracts.getSdf().getFirstName() + " " + activeContracts.getSdf().getLastName();
				if (activeContracts.getSdfSignDate() != null) {
					primary_sdf_subtion_date = HAJConstants.sdf3.format(activeContracts.getSdfSignDate());
				}
			}
		}

		params.put("primary_sdf_fullname", primary_sdf_fullname);
		params.put("primary_sdf_subtion_date", primary_sdf_subtion_date);
		params.put("clo_fullname", clo_fullname);
		params.put("clo_approved_date", clo_aaproved_date);
		params.put("crm_fullname", crm_fullname);
		params.put("crm_approved_date", crm_approved_date);
		params.put("guid", guid);

		List<ProjectImplementationPlan> list = new ArrayList<>();
		// list = projectImplementationPlanService.findByWsp(wsp);
		list = projectImplementationPlanService.findByWspWhereTotalaountIsGreaterThanZero(wsp);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<>();
		}
		params.put("PIMDataSource1", new JRBeanCollectionDataSource(list));

		List<ProjectImplementationPlan> dummyList = new ArrayList<>();
		for (int x = 0; x < 10; x++) {
			ProjectImplementationPlan imp = new ProjectImplementationPlan();
			dummyList.add(imp);
		}
		params.put("PIMDataSource2", new JRBeanCollectionDataSource(dummyList));

		List<Users> ceoList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
		Users       ceo     = null;
		if (ceoList.size() > 0) {
			ceo = ceoList.get(0);
		}
		String merSETA_CEO = "";
		if (ceo != null) {
			if (ceo.getTitle() != null) {
				TitleService titleService = new TitleService();
				Title        tile         = titleService.findByKey(ceo.getTitle().getId());
				merSETA_CEO = tile.getDescription() + " " + ceo.getLastName() + " " + ceo.getFirstName();
			} else {
				merSETA_CEO = ceo.getLastName() + " " + ceo.getFirstName();
			}
		}
		params.put("merSETA_CEO", merSETA_CEO);

		// params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
		// params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));

		params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());

		params.put("wsp", wsp);
		params.put("company", companyService.findByKey(wsp.getCompany().getId()));
		params.put("wsp_id", wsp.getId());
		params.put("wsp_fin_year", wsp.getFinYearNonNull());
		params.put("wsp_report", WspReportEnum.WSP.ordinal());
		params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
		params.put("region", r.getDescription());
		params.put("year", year);

		JasperService.addLogo(params);
		JasperService.addDiscretionaryGrantSubReports(params);

		// Adding books
		if (wsp.getFinYearNonNull() > 2021){
			params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
		}
		else {
			params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
		}
		params.put("initials", intitalsOne);
		params.put("initialsTwo", intitalsTwo);
		if (isElectricSignoff) {
			params.put("revisionDate", "01 October 2022");
			params.put("nextRevisionDate", "01 April 2025");
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			Integer prjectedStartDateInt = wsp.getFinYearNonNull() - 1;
			params.put("projectedStartDate", "1 April " + prjectedStartDateInt);
			params.put("projectedEndDate", "31 March " + wsp.getFinYearNonNull());
			if (wsp.getFinYearNonNull() > 2021){
				params.put("revisionDate", "01 October 2022");
				params.put("nextRevisionDate", "01 April 2025");
				params.put("DGA_details", path + "reports/newmoaver12/DGDTP001MemorandumOfAgreement.jasper");
			}
			else{
				params.put("revisionDate", "01 April 2019");
				params.put("nextRevisionDate", "01 April 2021");
				params.put("DGA_details", path + "reports/DGDTP001MemorandumOfAgreement.jasper");
			}
			// params.put("revisionDate", "01 April " + (wsp.getFinYearNonNull()));
			// params.put("nextRevisionDate", "01 April " + (wsp.getFinYearNonNull() + 4));
			// params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			// params.put("projectedStartDate", "1 Apr "+wsp.getFinYearNonNull());
			// params.put("projectedEndDate", "31 Mar "+(wsp.getFinYearNonNull()+1));
			// params.put("DGA_details", path +
			// "reports/DGDTP001MemorandumOfAgreement.jasper");
			params.put("pip_subreport", path + "reports/DGDTP001ProjectImplementationPlan.jasper");
		} else {
			params.put("revisionDate", "01 October 2018");
			params.put("nextRevisionDate", "01 October 2020");
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
		}
		JasperService.instance().createReportFromDBtoServletOutputStream("DG_Agreement_Book.jasper", params, fileName);
		// byte[] bites =
		// JasperService.instance().convertJasperReportToByte("DG_Agreement_Book.jasper",
		// params);
		// return bites;
	}

	/*
	 * Returns Byte Array for MOA for the new and old version of MOA by WSP
	 */
	public byte[] moaVerstionTwoByWspRetrunBytes(Wsp wsp) throws Exception {
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		ActiveContracts activecontract = activeContractsService.findByWSP(wsp.getId());
		if (activecontract != null) {
			if (activecontract.getEletronicSignoff() != null && activecontract.getEletronicSignoff()) {
				return moaVersionTwoReturnByte(activecontract, true);
			} else {
				return moaVersionTwoReturnByte(activecontract, false);
			}
		} else {
			throw new Exception("Unable to locate Contract, contract support!");
		}
	}

	/**
	 * Returns Byte Array for MOA for the new and old version of MOA by Active Contract Id
	 * @param activecontractId
	 * @return
	 * @throws Exception
	 */
	public byte[] moaVerstionTwoByWspRetrunBytesByActiveContract(Long activecontractId) throws Exception {
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		ActiveContracts activecontract = activeContractsService.findByKey(activecontractId);
		if (activecontract != null) {
			if (activecontract.getEletronicSignoff() != null && activecontract.getEletronicSignoff()) {
				return moaVersionTwoReturnByte(activecontract, true);
			} else {
				return moaVersionTwoReturnByte(activecontract, false);
			}
		} else {
			throw new Exception("Unable to locate Contract, contract support!");
		}
	}

	public byte[] moaVersionTwoReturnByte(ActiveContracts activeContracts, boolean isElectricSignoff) throws Exception {
		if (regionService == null) {
			regionService = new RegionService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (dgAllocationParentService == null) {
			dgAllocationParentService = new DgAllocationParentService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}

		ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
		HostingCompanyEmployeesService   hostingCompanyEmployeesService   = new HostingCompanyEmployeesService();
		OfficeService                    officeService                    = new OfficeService();

		Company    company           = null;
		Address    address           = null;
		Office     office            = null;
		RegionTown companyRegionTown = null;

		DgAllocationParent dgAllocationParent = dgAllocationParentService.findByKey(activeContracts.getDgAllocationParent().getId());
		Wsp                wsp                = dgAllocationParent.getWsp();
		if (wsp.getCompany() != null) {
			company = companyService.findByKey(wsp.getCompany().getId());
			if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
				if (address.getTown() != null && address.getTown().getId() != null) {
					companyRegionTown = RegionTownService.instance().findByTown(address.getTown());
				}
			}
		}

		if (companyRegionTown != null && companyRegionTown.getRegion() != null && companyRegionTown.getRegion().getId() != null) {
			List<Office> officeList = officeService.allOfficesByRegionId(companyRegionTown.getRegion().getId());
			if (officeList != null && officeList.size() != 0) {
				office = officeService.findByKey(officeList.get(0).getId());
			}
		}

		Map<String, Object> params                  = new HashMap<String, Object>();
		String              path                    = HAJConstants.APP_PATH;
		String              lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYearNonNull()).substring(Math.max(String.valueOf(wsp.getFinYearNonNull()).length() - 2, 0));
		String              year                    = wsp.getFinYearNonNull() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
		String              fileName                = wsp.getCompany().getLevyNumber() + "-(Yr" + lastTwoDigitsOfNextYear + ")-Discretionary_Grant_Agreement.pdf";

		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Region     r  = regionService.findByKey(rt.getRegion().getId());

		List<DgAllocation>  dgList              = new ArrayList<>();
		DgAllocationService dgAllocationService = new DgAllocationService();
		if (wsp.getId() != null) {
			if (dgAllocationParent != null) {
				dgList = dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
			}
		}

		// description, maxPossibleLearners, coFundingLearnersAwarded,
		// disabledTotalLearners, disabledGrantAmount, totalAwardAmount
		List<AnnexureABean> annexureABeanList = new ArrayList<>();
		// interventionType, interventionTitle, qualificationCode, maxPossibleLearners,
		// coFundingLearnersAwarded
		List<AnnexureCBean> annexureCBeanList = new ArrayList<>();

		if (dgList != null && dgList.size() != 0) {
			for (DgAllocation allocation : dgList) {

				String resultADescription           = "";
				String interventionTitleDescription = "";
				String qualificationCodeDescription = "";

				// locate Mandatory grant and mandatory grant recommendation if found
				MandatoryGrantRecommendation mgr = null;
				MandatoryGrant               mg  = null;
				if (allocation.getMandatoryGrant() != null && allocation.getMandatoryGrant().getId() != null) {
					mg = allocation.getMandatoryGrant();
					List<MandatoryGrantRecommendation> rec = grantRecommendationService.findByMG(allocation.getMandatoryGrant());
					for (MandatoryGrantRecommendation mandatoryGrantRecommendation : rec) {
						mgr = mandatoryGrantRecommendation;
						break;
					}
				}

				// populate descriptions used for report beans
				if (mg != null) {
					if (mgr != null) {
						if (mgr.getQualification() != null && mgr.getQualification().getId() != null) {
							resultADescription           = mgr.getQualification().getDescription();
							interventionTitleDescription = mgr.getQualification().getDescription();
							qualificationCodeDescription = mgr.getQualification().getCode().toString();
						} else if (mgr.getUnitStandards() != null && mgr.getUnitStandards().getId() != null) {
							resultADescription           = mgr.getUnitStandards().getTitle();
							interventionTitleDescription = mgr.getUnitStandards().getTitle();
							qualificationCodeDescription = mgr.getUnitStandards().getCode().toString();
						} else if (mgr.getSkillsProgram() != null && mgr.getSkillsProgram().getId() != null) {
							resultADescription           = mgr.getSkillsProgram().getDescription();
							interventionTitleDescription = mgr.getSkillsProgram().getDescription();
							qualificationCodeDescription = mgr.getSkillsProgram().getProgrammeID();
						} else if (mgr.getSkillsSet() != null && mgr.getSkillsSet().getId() != null) {
							resultADescription           = mgr.getSkillsSet().getDescription();
							interventionTitleDescription = mgr.getSkillsSet().getDescription();
							qualificationCodeDescription = mgr.getSkillsSet().getCode();
						} else if (mgr.getNonCreditBearingIntervationTitle() != null && mgr.getNonCreditBearingIntervationTitle().getId() != null) {
							resultADescription           = mgr.getNonCreditBearingIntervationTitle().getDescription();
							interventionTitleDescription = mgr.getNonCreditBearingIntervationTitle().getDescription();
							qualificationCodeDescription = mgr.getNonCreditBearingIntervationTitle().getCode();
						}

						// fail safe if values null
						boolean resultADescriptionNotPopulated           = (resultADescription.isEmpty() || resultADescription.equals(""));
						boolean interventionTitleDescriptionNotPopulated = (resultADescription.isEmpty() || resultADescription.equals(""));
						boolean qualificationCodeDescriptionNotPopulated = (resultADescription.isEmpty() || resultADescription.equals(""));

						if (resultADescriptionNotPopulated || interventionTitleDescriptionNotPopulated || qualificationCodeDescriptionNotPopulated) {
							if (mg.getQualification() != null && mg.getQualification().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getQualification().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getQualification().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getQualification().getCode().toString();
								}
							} else if (mg.getUnitStandard() != null && mg.getUnitStandard().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getUnitStandard().getTitle();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getUnitStandard().getTitle();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getUnitStandard().getCode().toString();
								}
							} else if (mg.getSkillsProgram() != null && mg.getSkillsProgram().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getSkillsProgram().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getSkillsProgram().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getSkillsProgram().getProgrammeID();
								}
							} else if (mg.getSkillsSet() != null && mg.getSkillsSet().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getSkillsSet().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getSkillsSet().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getSkillsSet().getCode();
								}
							} else if (mg.getNonCreditBearingIntervationTitle() != null && mg.getNonCreditBearingIntervationTitle().getId() != null) {
								if (resultADescriptionNotPopulated) {
									resultADescription = mg.getNonCreditBearingIntervationTitle().getDescription();
								}
								if (interventionTitleDescriptionNotPopulated) {
									interventionTitleDescription = mg.getNonCreditBearingIntervationTitle().getDescription();
								}
								if (qualificationCodeDescriptionNotPopulated) {
									qualificationCodeDescription = mg.getNonCreditBearingIntervationTitle().getCode();
								}
							}
						}

					} else {
						if (mg.getQualification() != null && mg.getQualification().getId() != null) {
							resultADescription           = mg.getQualification().getDescription();
							interventionTitleDescription = mg.getQualification().getDescription();
							qualificationCodeDescription = mg.getQualification().getCode().toString();
						} else if (mg.getUnitStandard() != null && mg.getUnitStandard().getId() != null) {
							resultADescription           = mg.getUnitStandard().getTitle();
							interventionTitleDescription = mg.getUnitStandard().getTitle();
							qualificationCodeDescription = mg.getUnitStandard().getCode().toString();
						} else if (mg.getSkillsProgram() != null && mg.getSkillsProgram().getId() != null) {
							resultADescription           = mg.getSkillsProgram().getDescription();
							interventionTitleDescription = mg.getSkillsProgram().getDescription();
							qualificationCodeDescription = mg.getSkillsProgram().getProgrammeID();
						} else if (mg.getSkillsSet() != null && mg.getSkillsSet().getId() != null) {
							resultADescription           = mg.getSkillsSet().getDescription();
							interventionTitleDescription = mg.getSkillsSet().getDescription();
							qualificationCodeDescription = mg.getSkillsSet().getCode();
						} else if (mg.getNonCreditBearingIntervationTitle() != null && mg.getNonCreditBearingIntervationTitle().getId() != null) {
							resultADescription           = mg.getNonCreditBearingIntervationTitle().getDescription();
							interventionTitleDescription = mg.getNonCreditBearingIntervationTitle().getDescription();
							qualificationCodeDescription = mg.getNonCreditBearingIntervationTitle().getCode();
						}
					}
				}

				// AnnexureABean
				AnnexureABean resultA = new AnnexureABean();

				resultA.setDescription(allocation.getInterventionType().getDescription());
				// DG Allocation Level
				resultA.setMaxPossibleLearners(((allocation.getMaxPossibleLearners() == null) ? 0 : allocation.getMaxPossibleLearners()));
				// DG Allocation Level
				resultA.setCoFundingLearnersAwarded(((allocation.getCoFundingLearnersAwarded() == null) ? 0 : allocation.getCoFundingLearnersAwarded()));
				// DG Allocation Level
				resultA.setDisabledTotalLearners(((allocation.getDisabledTotalLearners() == null) ? 0 : allocation.getDisabledTotalLearners()));
				// DG Allocation Level
				resultA.setDisabledGrantAmount(((allocation.getDisabledGrantAmount() == null) ? BigDecimal.valueOf(0) : allocation.getDisabledGrantAmount()));
				// DG Allocation Level
				resultA.setTotalAwardAmount(((allocation.getTotalAwardAmount() == null) ? BigDecimal.valueOf(0) : allocation.getTotalAwardAmount()));
				annexureABeanList.add(resultA);

				// AnnexureCBean
				AnnexureCBean resultC = new AnnexureCBean();
				// DG Allocation Level
				// Added for Jira #271
				resultC.setOfoCodes(((allocation.getMandatoryGrant().getOfoCodes().getOfoDescription() == null) ? "" : allocation.getMandatoryGrant().getOfoCodes().getOfoDescription()));
				resultC.setInterventionType(((allocation.getInterventionType() == null) ? "" : allocation.getInterventionType().getDescription()));
				resultC.setInterventionTitle(interventionTitleDescription);
				resultC.setQualificationCode(qualificationCodeDescription);
				// DG Allocation Level
				resultC.setMaxPossibleLearners(((allocation.getMaxPossibleLearners() == null) ? 0 : allocation.getMaxPossibleLearners()));
				// DG Allocation Level
				resultC.setCoFundingLearnersAwarded(((allocation.getCoFundingLearnersAwarded() == null) ? 0 : allocation.getCoFundingLearnersAwarded()));
				annexureCBeanList.add(resultC);
			}
		}

		params.put("isElectricSignoff", isElectricSignoff);

		params.put("annexureABeanList", new JRBeanCollectionDataSource(annexureABeanList));
		params.put("annexureCBeanList", new JRBeanCollectionDataSource(annexureCBeanList));

		/* Sign off Information SDF/REP */
		// region
		String organisationSignedAt = "";
		// day of sign off
		String organisationThis = "";
		// month of sign off
		String organisationDayOf = "";
		// year of sign off
		String organisationYear = "";
		// User who signed off
		String organisationName = "";

		/* Sign off Information MerSETA */
		// region
		String merSetaSignedAt = "";
		// day of sign off
		String merSetaThis = "";
		// month of sign off
		String merSetaDayOf = "";
		// year of sign off
		String merSetaYear = "";
		// User who signed off
		String merSetaName = "";

		List<Signoff> signoffList = signoffService.findByTargetKeyAndClass(activeContracts.getId(), activeContracts.getClass().getName());
		String        intitalsOne = "";
		String        intitalsTwo = "";
		int           count       = 1;
		for (Signoff signoff : signoffList) {
			if (signoff.getUser() != null) {
				if (count == 1) {
					// SDF / Rep Sign Off
					if (signoff.getAccept() != null && signoff.getAccept() && signoff.getSignOffDate() != null) {
						intitalsOne       += signoff.getUser().getFirstName().trim().substring(0, 1).toUpperCase();
						intitalsOne       += signoff.getUser().getLastName().trim().substring(0, 1).toUpperCase();
						organisationName   = signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim();
						organisationThis   = HAJConstants.sdfDD.format(signoff.getSignOffDate());
						organisationDayOf  = HAJConstants.sdfMMMM.format(signoff.getSignOffDate());
						organisationYear   = HAJConstants.sdfYY.format(signoff.getSignOffDate());
					}
					if (signoff.getAccept() != null && signoff.getAccept() && companyRegionTown != null) {
						organisationSignedAt = companyRegionTown.getTown().getDescription().trim();
					}
				} else if (count == 2) {
					// MerSETA Sign off
					if (signoff.getAccept() != null && signoff.getAccept() && signoff.getSignOffDate() != null) {
						intitalsTwo  += signoff.getUser().getFirstName().substring(0, 1).toUpperCase();
						intitalsTwo  += signoff.getUser().getLastName().substring(0, 1).toUpperCase();
						merSetaName   = signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim();
						merSetaThis   = HAJConstants.sdfDD.format(signoff.getSignOffDate());
						merSetaDayOf  = HAJConstants.sdfMMMM.format(signoff.getSignOffDate());
						merSetaYear   = HAJConstants.sdfYY.format(signoff.getSignOffDate());
					}
					if (signoff.getAccept() != null && signoff.getAccept() && office != null) {
						merSetaSignedAt = "Johannesburg";
					}
				} else {
					break;
				}
			}
			count++;
		}

		params.put("organisationSignedAt", organisationSignedAt);
		params.put("organisationThis", organisationThis);
		params.put("organisationDayOf", organisationDayOf);
		params.put("organisationYear", organisationYear);
		params.put("organisationName", organisationName);
		params.put("merSetaSignedAt", merSetaSignedAt);
		params.put("merSetaThis", merSetaThis);
		params.put("merSetaDayOf", merSetaDayOf);
		params.put("merSetaYear", merSetaYear);
		params.put("merSetaName", merSetaName);

		// PIP
		String primary_sdf_fullname     = "";
		String clo_fullname             = "";
		String primary_sdf_subtion_date = "";
		String crm_fullname             = "";
		String clo_aaproved_date        = "";
		String crm_approved_date        = "";
		String guid                     = wsp.getCompany().getCompanyGuid();

		if (activeContracts != null) {
			if (activeContracts.getClo() != null) {
				clo_fullname = activeContracts.getClo().getFirstName() + " " + activeContracts.getClo().getLastName();
				if (activeContracts.getCloSignDate() != null) {
					clo_aaproved_date = HAJConstants.sdf3.format(activeContracts.getCloSignDate());
				}
			}
			if (activeContracts.getCrm() != null) {
				crm_fullname = activeContracts.getCrm().getFirstName() + " " + activeContracts.getCrm().getLastName();
				if (activeContracts.getCrmSignDate() != null) {
					crm_approved_date = HAJConstants.sdf3.format(activeContracts.getCrmSignDate());
				}
			}
			if (activeContracts.getSdf() != null) {
				primary_sdf_fullname = activeContracts.getSdf().getFirstName() + " " + activeContracts.getSdf().getLastName();
				if (activeContracts.getSdfSignDate() != null) {
					primary_sdf_subtion_date = HAJConstants.sdf3.format(activeContracts.getSdfSignDate());
				}
			}
		}

		params.put("primary_sdf_fullname", primary_sdf_fullname);
		params.put("primary_sdf_subtion_date", primary_sdf_subtion_date);
		params.put("clo_fullname", clo_fullname);
		params.put("clo_approved_date", clo_aaproved_date);
		params.put("crm_fullname", crm_fullname);
		params.put("crm_approved_date", crm_approved_date);
		params.put("guid", guid);

		List<ProjectImplementationPlan> list = new ArrayList<>();
		list = projectImplementationPlanService.findByWsp(wsp);
		if (list == null || list.size() <= 0) {
			list = new ArrayList<>();
		}
		params.put("PIMDataSource1", new JRBeanCollectionDataSource(list));

		List<ProjectImplementationPlan> dummyList = new ArrayList<>();
		for (int x = 0; x < 10; x++) {
			ProjectImplementationPlan imp = new ProjectImplementationPlan();
			dummyList.add(imp);
		}
		params.put("PIMDataSource2", new JRBeanCollectionDataSource(dummyList));

		List<Users> ceoList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
		Users       ceo     = null;
		if (ceoList.size() > 0) {
			ceo = ceoList.get(0);
		}
		String merSETA_CEO = "";
		if (ceo != null) {
			if (ceo.getTitle() != null) {
				TitleService titleService = new TitleService();
				Title        tile         = titleService.findByKey(ceo.getTitle().getId());
				merSETA_CEO = tile.getDescription() + " " + ceo.getLastName() + " " + ceo.getFirstName();
			} else {
				merSETA_CEO = ceo.getLastName() + " " + ceo.getFirstName();
			}
		}
		params.put("merSETA_CEO", merSETA_CEO);

		params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());

		params.put("wsp", wsp);
		params.put("company", companyService.findByKey(wsp.getCompany().getId()));
		params.put("wsp_id", wsp.getId());
		params.put("wsp_fin_year", wsp.getFinYearNonNull());
		params.put("wsp_report", WspReportEnum.WSP.ordinal());
		params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
		params.put("region", r.getDescription());
		params.put("year", year);

		JasperService.addLogo(params);
		JasperService.addDiscretionaryGrantSubReports(params);

		// Adding books
		if (wsp.getFinYearNonNull() > 2021){
			params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
		}
		else {
			params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
			params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
		}
		params.put("initials", intitalsOne);
		params.put("initialsTwo", intitalsTwo);
		if (isElectricSignoff) {
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			Integer prjectedStartDateInt = wsp.getFinYearNonNull() - 1;
			params.put("projectedStartDate", "1 April " + prjectedStartDateInt);
			params.put("projectedEndDate", "31 March " + wsp.getFinYearNonNull());
			if (wsp.getFinYearNonNull() > 2021){
				params.put("revisionDate", "12 April 2021");
				params.put("nextRevisionDate", "01 April 2025");
				params.put("DGA_details", path + "reports/newmoaver12/DGDTP001MemorandumOfAgreement.jasper");
			}
			else {
				params.put("revisionDate", "01 April 2019");
				params.put("nextRevisionDate", "01 April 2021");
				params.put("DGA_details", path + "reports/DGDTP001MemorandumOfAgreement.jasper");
			}
			params.put("pip_subreport", path + "reports/DGDTP001ProjectImplementationPlan.jasper");
		} else {
			params.put("revisionDate", "01 October 2018");
			params.put("nextRevisionDate", "01 October 2020");
			params.put("terminationDate", "30 September " + (wsp.getFinYearNonNull() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYearNonNull());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYearNonNull() + 1));
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
		}
		byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Agreement_Book.jasper", params);
		return bites;
	}

	public Users getCLO(Wsp wsp) throws Exception {
		RegionTown rt      = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users      cloUser = rt.getClo().getUsers();

		return cloUser;
	}

	public Region getRegion(Wsp wsp) throws Exception {
		RegionService regionService = new RegionService();
		RegionTown    rt            = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Region        r             = regionService.findByKey(rt.getRegion().getId());
		return r;
	}

	public void redoAllocation(List<DgAllocation> dgAllocations, DgAllocationParent dgAllocationParent, boolean useCompanyRecommendation) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		SarsLevyDetails   sld          = sarsFilesService.sdlSummaryByRefAndSchemeYear(dgAllocationParent.getWsp().getCompany().getLevyNumber(), dgAllocationParent.getWsp().getFinYearNonNull() - 2);
		if (artisan == null) artisan = interventionTypeService.findByKey(HAJConstants.APPRENTICESHIP_ID);
		availableAmount        = null;
		previousTotal          = null;
		previousCoFundingTotal = null;
		coFundingAmount        = BigDecimal.ZERO;
		/* Added Fix for locating correct allocation */
		CategorizationData cd = categorizationDataService.findByCompany(dgAllocationParent.getWsp().getCompany().getId());
		if (cd != null) {
			dgAllocationParent.getWsp().getCompany().setCategorization(cd.getCategorization());
		}
		availableCoFunding = calcCofunding(sld, dgAllocationParent.getWsp().getCompany(), artisan.getBasicGrantAmount());
		// dap.setAvailableCoFundingAmount(calcCofunding(sld, dap.getWsp().getCompany(),
		// artisan.getBasicGrantAmount()));
		// dataEntities.add(dap);
		allCompaniesAwardAmount = null;
		maxAllocationAmount     = null;

		for (DgAllocation da : dgAllocations) {
			if (!useCompanyRecommendation) {
				zeroEverything(da);
			}
			// zeroEverything(da);
			allocateMoney(dgAllocationParent.getWsp(), sld, da.getMandatoryGrant(), da, useCompanyRecommendation);
			dataEntities.add(da);
		}
		dao.updateBatch(dataEntities);

	}

	public void approveChangeAllocation(List<DgAllocation> entities, DgAllocationParent entity, AllocationChange allocationChange, Users user, Tasks tasks) throws Exception {
		entity.setStatus(null);
		entity.setAllocationParent(null);
		// entities.stream().filter(a -> a.getChangeAllocationLearners() ==
		// null).forEach(b -> {
		// b.setChangeAllocationLearners(b.getMaxPossibleLearners());
		// });
		// List<DgAllocation> allocs = entities.stream().filter(a ->
		// a.getChangeAllocationLearners() != null).collect(Collectors.toList());
		redoAllocation(entities, entity, true);
		dao.update(entity);
		if (allocationChange != null) {
			AllocationChangesReasons allocationChangesReasons = new AllocationChangesReasons(allocationChange, entity);
			dao.create(allocationChangesReasons);
		}
		TasksService.instance().completeTask(tasks);

		/// Sending Email notification

		/*
		 * Region r = getRegion(entity.getWsp()); String region = ""; Users toUser = entity.getWsp().getCreateUsers(); if (r != null) { region = r.getDescription(); }
		 */
		// dgApplicationAllocationChange(toUser, entity.getWsp().getCompany(),
		// allocationChange.getDescription(), getCLO(entity.getWsp()), region,
		// String.valueOf(entity.getWsp().getFinYearNonNull()));

		// sendDGApplicationAllocationChangeEmail(toUser, entity.getWsp().getCompany(),
		// getCLO(entity.getWsp()), region,
		// String.valueOf(entity.getWsp().getFinYearNonNull()),
		// entity.getAllocationStatusEnum().getFriendlyName());
	}

	public String convertToHTML(List<RejectReasonsChild> rejectReasons) {
		String sb = "<ul>";
		for (RejectReasonsChild r : rejectReasons) {
			sb += "<li>" + r.getRejectReasons().getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}

	public String convertRejectReasonsToHTML(List<RejectReasons> selectedRejectReason) {
		String sb = "<ul>";
		for (RejectReasons r : selectedRejectReason) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}

	private List<MandatoryGrant> checkRequiredOrder(List<MandatoryGrant> mg) throws Exception {
		for (MandatoryGrant mandatoryGrant : mg) {
			List<MandatoryGrantRecommendation> rec = grantRecommendationService.findByMG(mandatoryGrant);
			for (MandatoryGrantRecommendation mandatoryGrantRecommendation : rec) {
				mandatoryGrant.setRecinterventionType(mandatoryGrantRecommendation.getInterventionType());
			}
		}
		// (o1, o2)->o1.getItem().getValue().
		// compareTo(o2.getItem().getValue())).
		// collect(Collectors.toList()
		return mg.stream().sorted((o1, o2) -> (o1.getRecinterventionType() != null ? o1.getRecinterventionType() : o1.getInterventionType()).getPrioritisationScale().compareTo((o2.getRecinterventionType() != null ? o2.getRecinterventionType() : o2.getInterventionType()).getPrioritisationScale())).collect(Collectors.toList());
	}

	public String convertStringToHTML(String r) {
		String sb = "<ul>" + r + "</ul>";
		return sb;
	}

	public Users findCEO() {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users>                    ceoList                        = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
		Users                          ceo                            = null;
		if (ceoList.size() > 0) {
			ceo = ceoList.get(0);
		}
		return ceo;
	}

	/* Reporting Start */
	@SuppressWarnings("unchecked")
	public List<DgAllocation> allDgAllocationListByFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from DgAllocation o where o.dgAllocationParent.wsp.finYear = :finYear";
		filters.put("finYear", finYear);
		return populateInformationReportList((List<DgAllocation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllDgAllocationListByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgAllocation o where o.dgAllocationParent.wsp.finYear = :finYear";
		return dao.countWhere(filters, hql);
	}

	public List<DgAllocation> populateInformationReportList(List<DgAllocation> dgAllocationList) throws Exception {
		for (DgAllocation dgAllocation : dgAllocationList) {
			populateInformationReport(dgAllocation);
		}
		return dgAllocationList;
	}

	public DgAllocation populateInformationReport(DgAllocation dgAllocation) throws Exception {
		// sets company region town
		if (addressService == null) {
			addressService = new AddressService();
		}
		if (grantRecommendationService == null) {
			grantRecommendationService = new MandatoryGrantRecommendationService();
		}

		// locate comapny information
		if (dgAllocation.getMandatoryGrant().getWsp().getCompany().getResidentialAddress() != null && dgAllocation.getMandatoryGrant().getWsp().getCompany().getResidentialAddress().getId() != null) {
			dgAllocation.getMandatoryGrant().getWsp().getCompany().setResidentialAddress(addressService.findByKey(dgAllocation.getMandatoryGrant().getWsp().getCompany().getResidentialAddress().getId()));
			RegionTown rt = RegionTownService.instance().findByTown(dgAllocation.getMandatoryGrant().getWsp().getCompany().getResidentialAddress().getTown());
			dgAllocation.getMandatoryGrant().getWsp().getCompany().setRegionTown(rt);
		}

		// set verification status
		dgAllocation.getMandatoryGrant().getWsp().setDgVerificationStatus(DgVerificationService.instance().findByWspAndReturnStatus(dgAllocation.getMandatoryGrant().getWsp().getId()));

		// locate lastest recommendation against mandatory grant for allocation
		List<MandatoryGrantRecommendation> rec = grantRecommendationService.findByMG(dgAllocation.getMandatoryGrant());
		if (rec != null && rec.size() != 0) {
			dgAllocation.setLastestMandatoryGrantRecommendation(rec.get(0));
		}

		return dgAllocation;
	}

	/*
	 * Runs allocation by WSP without saving to the DB. Used as a forecast of what allocation is. Recommenced only one WSP at a time for report.
	 */
	public List<DgAllocation> doAllocationForecastReportingByWsp(Wsp wsp) throws Exception {
		List<DgAllocation> allocationForcastList = new ArrayList<>();
		wsp = wspService.findByKey(wsp.getId());
		Boolean acceptCoFunding = wsp.getCoFundingPartnership() != null && wsp.getCoFundingPartnership().getId() == YesNoEnum.Yes.getYesNoLookupId();
		categorisationService.categorizeCompanyWithoutUpdate(wsp.getCompany());
		CategorizationData cd = categorizationDataService.findByCompany(wsp.getCompany().getId());
		if (cd != null) {
			wsp.getCompany().setCategorization(cd.getCategorization());
		}
		SarsLevyDetails sld = null;
		if (wsp.getFinYearNonNull() != null) {
			sld = sarsFilesService.sdlSummaryByRefAndSchemeYear(wsp.getCompany().getLevyNumber(), wsp.getFinYearNonNull() - 2);
			if (artisan == null) artisan = interventionTypeService.findByKey(HAJConstants.APPRENTICESHIP_ID);
			if (acceptCoFunding) {
				coFundingAmount = calcCofunding(sld, wsp.getCompany(), artisan.getBasicGrantAmount());
			}
		}
		List<MandatoryGrant> mgd    = checkRequiredOrder(mandatoryGrantService.allMandatoryGrant(wsp));
		BigDecimal           amount = BigDecimal.ZERO;
		if (wsp.getFinYearNonNull() != null) {
			amount = sld.getDiscretionaryLevy() == null ? BigDecimal.ZERO : (wsp.getCompany().getCategorization() == CategorizationEnum.Platinum ? sld.getDiscretionaryLevy().multiply(new BigDecimal(2)) : sld.getDiscretionaryLevy());
		} else {
			amount = wsp.getDgYear().getAllocationAmount();
		}
		DgAllocationParent dgAllocationParent = new DgAllocationParent(wsp, (amount));
		dgAllocationParent.setAvailableCoFundingAmount(coFundingAmount);
		if (wsp.getFinYearNonNull() != null) {
			availableCoFunding = calcCofunding(sld, wsp.getCompany(), artisan.getBasicGrantAmount());
		}
		for (MandatoryGrant mandatoryGrant : mgd) {
			DgAllocation da = new DgAllocation(dgAllocationParent);
			allocateMoney(wsp, sld, mandatoryGrant, da, false);
			allocationForcastList.add(da);
		}
		return populateInformationReportList(allocationForcastList);
	}

	public void onceOffFix() throws Exception {
		ReportService             reportService = new ReportService();
		WspService                wspService    = new WspService();
		List<ByChamberReportBean> wspIdList     = reportService.getWspIdsFromNativeSQL();
		for (ByChamberReportBean byChamberReportBean : wspIdList) {
			Wsp wsp = wspService.findByKey(byChamberReportBean.getWspID().longValue());
			if (wsp != null) {
				checkForAllocationWithException(wsp);
			}
		}
	}

	/*
	 * public void checkForAllocationWithException(Wsp wsp) throws Exception { DgVerification dg = DgVerificationService.instance().findByWspId(wsp); // WSP check boolean wspApproved = wsp != null && wsp.getWspStatusEnum() != null && wsp.getWspStatusEnum() == WspStatusEnum.Approved; // verification check boolean dgVerificationApproved = dg != null && dg.getStatus() != null && dg.getStatus() == ApprovalEnum.Approved; // check if both WSP and verification both approved before allocation generation
	 * if (dgVerificationApproved) { doAllocation(wsp); } // if (wspApproved && dgVerificationApproved) doAllocation(wsp); // else throw new Exception("Can't allocate, please ensure verification and grant application are both approved." ); }
	 */
	/* Reporting End */

}