package haj.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.WspCalData;
import haj.com.constants.HAJConstants;
import haj.com.dao.WspChecklistDAO;
import haj.com.entity.BankingDetails;
import haj.com.entity.Doc;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.entity.WspSignoff;
import haj.com.entity.YesNoLookup;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspChecklistService.
 */
public class WspChecklistService extends AbstractService {
	/** The dao. */
	private WspChecklistDAO dao = new WspChecklistDAO();

	/**
	 * Get all WspChecklist.
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WspChecklist}
	 * @throws Exception
	 * the exception
	 * @see WspChecklist
	 */
	public List<WspChecklist> allWspChecklist() throws Exception {
		return dao.allWspChecklist();
	}

	/**
	 * Create or update WspChecklist.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see WspChecklist
	 */
	public void create(WspChecklist entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update WspChecklist.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see WspChecklist
	 */
	public void update(WspChecklist entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WspChecklist.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see WspChecklist
	 */
	public void delete(WspChecklist entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.WspChecklist}
	 * @throws Exception
	 * the exception
	 * @see WspChecklist
	 */
	public WspChecklist findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WspChecklist by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.WspChecklist}
	 * @throws Exception
	 * the exception
	 * @see WspChecklist
	 */
	public List<WspChecklist> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WspChecklist.
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspChecklist}
	 * @throws Exception
	 * the exception
	 */
	public List<WspChecklist> allWspChecklist(int first, int pageSize) throws Exception {
		return dao.allWspChecklist(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WspChecklist for lazy load.
	 * @author TechFinium
	 * @return Number of rows in the WspChecklist
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(WspChecklist.class);
	}

	/**
	 * Lazy load WspChecklist with pagination, filter, sorting.
	 * @author TechFinium
	 * @param class1
	 * WspChecklist class
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
	 * @return a list of {@link haj.com.entity.WspChecklist} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspChecklist> allWspChecklist(Class<WspChecklist> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WspChecklist>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WspChecklist for lazy load with filters.
	 * @author TechFinium
	 * @param entity
	 * WspChecklist class
	 * @param filters
	 * the filters
	 * @return Number of rows in the WspChecklist entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<WspChecklist> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by WSP.
	 * @param wsp
	 * the wsp
	 * @return the wsp checklist
	 * @throws Exception
	 * the exception
	 */
	public WspChecklist findByWSP(Wsp wsp) throws Exception {
		return dao.findByWSP(wsp);
	}

	public void populateChecklist(WspChecklist wspChecklist, Wsp wsp, BankingDetails bankingDetails) throws Exception {
		// #{initiatewspUI.wsp.company.majorityUnion}
		YesNoLookup yes = YesNoLookupService.instance().findByKey(HAJConstants.YES_ID);
		YesNoLookup no  = YesNoLookupService.instance().findByKey(HAJConstants.NO_ID);
		// Checking if extension is granted
		ArrayList<ExtensionRequest> extensionRequests = (ArrayList<ExtensionRequest>) ExtensionRequestService.instance().extensionRequestGranyedCheck(wsp.getId());
		if (extensionRequests.size() > 0) {
			ExtensionRequest latestRequest = extensionRequests.get(0);
			if (latestRequest.getStatus() == ApprovalEnum.Approved) {
				wspChecklist.setExtensionGranted(yes);
			} else {
				wspChecklist.setExtensionGranted(no);
			}
		} else {
			// NA
			wspChecklist.setExtensionGranted(null);
		}

		// Checking if ATR report is submitted
		MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
		if (mandatoryGrantDetailService.atrSubmitedCheck(wsp, WspReportEnum.ATR).size() > 0) {
			wspChecklist.setAtrSubmitted(yes);
		} else {
			// display as N/A
			// wspChecklist.setAtrSubmitted(null);
			wspChecklist.setAtrSubmitted(no);
		}

		// check if entries provided for workplace skills plan
		long count = mandatoryGrantDetailService.findByWSPAndReportCount(wsp, WspReportEnum.WSP);
		if (count != (long) 0) {
			wspChecklist.setWorkplaceSkillsPlanSubmitted(yes);
		} else {
			wspChecklist.setWorkplaceSkillsPlanSubmitted(no);
		}

		calculateDeviation(wspChecklist, wsp);

		// Checking if PIVOTAL Plan is submitted
		if (mandatoryGrantDetailService.wspPivotalPlanCheck(wsp, WspReportEnum.WSP, HAJConstants.YES_ID).size() > 0) {
			wspChecklist.setPivitolPlanSubmitted(yes);
		} else {
			// display as N/A
			wspChecklist.setPivitolPlanSubmitted(null);
			// wspChecklist.setPivitolPlanSubmitted(no);
		}

		// Check if Sign off is completed
		wspChecklist.setOtherSDFSigned(yes);
		for (WspSignoff signoff : wsp.getWspSignoffs()) {
			if (!signoff.getAccept()) {
				wspChecklist.setOtherSDFSigned(no);
			}
		}
		// Company has a signed recognition agreement with Labour/Union Auto fill
		wspChecklist.setSignedRecognitionAgreement(wsp.getCompany().getRecognitionAgreement());

		// company majoirty union
		if (wsp.getCompany().getMajorityUnion() != null) {
			wspChecklist.setMajorityUnion(wsp.getCompany().getMajorityUnion());
		}

		// Check if Banking details approved by merSETA
		if (bankingDetails != null) {
			if (bankingDetails.getStatus() != null && bankingDetails.getStatus() == ApprovalEnum.Approved) {
				wspChecklist.setBankingDetailsChecked(yes);
			} else {
				wspChecklist.setBankingDetailsChecked(no);
			}
		} else {
			wspChecklist.setBankingDetailsChecked(no);
		}

		// Check if Previous year WSP submitted
		WspService wspService = new WspService();
		// int previourYear = (Calendar.getInstance().get(Calendar.YEAR)) - 1;
		int previourYear = wsp.getFinYear() - 1;
		if (wspService.findByCompanyAndFinancialYear(wsp.getCompany().getId(), previourYear).size() > 0) {
			wspChecklist.setWspy18Submitted(yes);
		} else {
			if (previourYear == 2019) {
				// check if wsp_calculation_data provided
				if (wspChecklist.getWspy18Submitted() == null || wspChecklist.getWspy18Submitted().getId() == null) {
					if (WspCalculationDataService.instance().findByCompanyCount(wsp.getCompany()) == 0) {
						// displays as no data provided
						wspChecklist.setWspy18Submitted(null);
					} else {
						// sets as yes
						wspChecklist.setWspy18Submitted(yes);
					}
				}
			} else {
				wspChecklist.setWspy18Submitted(no);
			}
		}

		// Check if % of Annual Training Report implemented
		if (wspService.artPercentageCheck(wsp.getId()) != null) {
			wspChecklist.setAtrImplementedPercent(yes);
		} else {
			wspChecklist.setAtrImplementedPercent(no);
		}

		// Checking If checklist docs are uploaded
		wspChecklist.setMinutesUplaoded(no);
		boolean trainingDeviationChecked = false;
		boolean nonNqf                   = false;
		boolean minutesCheck1            = false;
		boolean minutesCheck2            = false;
		for (Doc doc : wsp.getDocs()) {
			// Deviation Motivation Uploaded
			if (!trainingDeviationChecked) {
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_DEVIATION_MOTIVATION_ID) {
					if (doc.getId() != null) {
						wspChecklist.setDeviationMotivation(yes);
						trainingDeviationChecked = true;
					} else {
						wspChecklist.setDeviationMotivation(no);
					}
				}
			}

			// Non-NQF intervention motivation uploaded
			if (!nonNqf) {
				if (doc.getConfigDoc().getId() == HAJConstants.NON_NQF_EXPLANATION_ID) {
					if (doc.getId() != null) {
						wspChecklist.setNonNqfMotivation(yes);
						nonNqf = true;
					} else {
						wspChecklist.setNonNqfMotivation(no);
					}
				}
			}

			// Minutes Uploaded
			if (!minutesCheck1 || !minutesCheck2) {
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES1_ID || doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES2_ID) {
					if (doc.getId() != null) {
						wspChecklist.setMinutesUplaoded(yes);
						if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES1_ID) {
							minutesCheck1 = true;
						}
						if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES2_ID) {
							minutesCheck2 = true;
						}
					}
				}
			}

			if (minutesCheck1 && minutesCheck2) {
				wspChecklist.setMinutesUplaoded(yes);
			} else {
				wspChecklist.setMinutesUplaoded(no);
			}
		}
	}

	/**
	 * Calculates Training Deviation against submitted WSP
	 * @param wspChecklist
	 * @param wsp
	 * @throws Exception
	 */
	public void calculateDeviation(WspChecklist wspChecklist, Wsp wsp) throws Exception {
		BigDecimal minReq = (BigDecimal.valueOf(60));
		if (wspChecklist.getPercentageCalculated() == null) {
			List<WspCalData> calcDataReportBean = new ArrayList<>();
			if (wsp.getFinYear() != null) {

				if (wsp.getFinYear() == 2019) {
					calcDataReportBean = WspCalculationDataService.instance().calPercentageByCompany(wsp.getCompany().getId());
				} else {
					Integer previousYear = wsp.getFinYear() - 1;
					calcDataReportBean = WspCalculationDataService.instance().calcDeviationAmountByCompanyAndFinYear(wsp.getCompany().getId(), wsp.getFinYear(), previousYear);
				}

				for (WspCalData data : calcDataReportBean) {
					wspChecklist.setPercentageCalculated(data.getPercentage());
					if (data.getPercentage() != null) {
						if (data.getPercentage().doubleValue() < minReq.doubleValue()) {
							wspChecklist.setGrantDeviated(true);
						} else {
							wspChecklist.setGrantDeviated(false);
						}
					}
					break;
				}
			}

		} else {
			if (wspChecklist.getPercentageCalculated().doubleValue() < minReq.doubleValue()) {
				wspChecklist.setGrantDeviated(true);
			} else {
				wspChecklist.setGrantDeviated(false);
			}
		}
	}
}
