package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.bean.AttachmentBean;
import haj.com.bean.MgTransactionsBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.GpGrantBatchListDAO;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Tasks;
import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.GpDocumentType;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SICCode;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.SICCodeService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.batch.grant.gp.DiskBackedBatchMap;
import za.co.merseta.nsdms.batch.grant.gp.payments.GrantPaymentsBatchMessageProducer;
import za.co.merseta.nsdms.batch.grant.gp.validation.GrantBatchValidationMessageProducer;
import za.co.merseta.nsdms.framework.exception.TechnicalException;

public class GpGrantBatchListService extends AbstractService {

	/** The dao. */
	private GpGrantBatchListDAO dao = new GpGrantBatchListDAO();

	/** The Service Levels */
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private GpGrantBatchEntryService gpGrantBatchEntryService = new GpGrantBatchEntryService();
	private ConfigDocService configDocService = new ConfigDocService();
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	private ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
	private InterventionTypeService interventionTypeService = new InterventionTypeService();
	private TransactionsCompanyValidiationService transactionsCompanyValidiationService = new TransactionsCompanyValidiationService();
	private UsersService usersService = new UsersService();
	private SICCodeService sicCodeService = new SICCodeService();
	private ChamberService chamberService = new ChamberService();
	private SarsLevyDetailsService sarsLevyDetailsService;

	/**
	 * Get all GpGrantBatchList
	 * 
	 * @author TechFinium
	 * @see GpGrantBatchList
	 * @return a list of {@link haj.com.entity.GpGrantBatchList}
	 * @throws Exception
	 *                   the exception
	 */
	public List<GpGrantBatchList> allGpGrantBatchList() throws Exception {
		return dao.allGpGrantBatchList();
	}

	/**
	 * Create or update GpGrantBatchList.
	 * 
	 * @author TechFinium
	 * @param entity
	 *               the entity
	 * @throws Exception
	 *                   the exception
	 * @see GpGrantBatchList
	 */
	public void create(GpGrantBatchList entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update GpGrantBatchList.
	 * 
	 * @author TechFinium
	 * @param entity
	 *               the entity
	 * @throws Exception
	 *                   the exception
	 * @see GpGrantBatchList
	 */
	public void update(GpGrantBatchList entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete GpGrantBatchList.
	 * 
	 * @author TechFinium
	 * @param entity
	 *               the entity
	 * @throws Exception
	 *                   the exception
	 * @see GpGrantBatchList
	 */
	public void delete(GpGrantBatchList entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * 
	 * @author TechFinium
	 * @param id
	 *           the id
	 * @return a {@link haj.com.entity.GpGrantBatchList}
	 * @throws Exception
	 *                   the exception
	 * @see GpGrantBatchList
	 */
	public GpGrantBatchList findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find GpGrantBatchList by description.
	 * 
	 * @author TechFinium
	 * @param desc
	 *             the desc
	 * @return a list of {@link haj.com.entity.GpGrantBatchList}
	 * @throws Exception
	 *                   the exception
	 * @see GpGrantBatchList
	 */
	public List<GpGrantBatchList> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load GpGrantBatchList
	 * 
	 * @param first
	 *                 from key
	 * @param pageSize
	 *                 no of rows to fetch
	 * @return a list of {@link haj.com.entity.GpGrantBatchList}
	 * @throws Exception
	 *                   the exception
	 */
	public List<GpGrantBatchList> allGpGrantBatchList(int first, int pageSize) throws Exception {
		return dao.allGpGrantBatchList(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of GpGrantBatchList for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the GpGrantBatchList
	 * @throws Exception
	 *                   the exception
	 */
	public Long count() throws Exception {
		return dao.count(GpGrantBatchList.class);
	}

	/**
	 * Lazy load GpGrantBatchList with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *                  GpGrantBatchList class
	 * @param first
	 *                  the first
	 * @param pageSize
	 *                  the page size
	 * @param sortField
	 *                  the sort field
	 * @param sortOrder
	 *                  the sort order
	 * @param filters
	 *                  the filters
	 * @return a list of {@link haj.com.entity.GpGrantBatchList} containing data
	 * @throws Exception
	 *                   the exception
	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchList> allGpGrantBatchList(Class<GpGrantBatchList> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList(
				(List<GpGrantBatchList>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	/**
	 * Get count of GpGrantBatchList for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *                GpGrantBatchList class
	 * @param filters
	 *                the filters
	 * @return Number of rows in the GpGrantBatchList entity
	 * @throws Exception
	 *                   the exception
	 */
	public int count(Class<GpGrantBatchList> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<GpGrantBatchList> allGpGrantBatchListByWspType(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, WspTypeEnum wspType) throws Exception {
		String hql = "select o from GpGrantBatchList o where o.wspType = :wspType";
		filters.put("wspType", wspType);
		return populateAdditionalInformationList(
				(List<GpGrantBatchList>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllGpGrantBatchListByWspType(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from GpGrantBatchList o where o.wspType = :wspType";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<GpGrantBatchList> allGpGrantBatchListByWspTypeNoResolveData(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, WspTypeEnum wspType) throws Exception {
		String hql = "select o from GpGrantBatchList o where o.wspType = :wspType";
		filters.put("wspType", wspType);
		return (List<GpGrantBatchList>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllGpGrantBatchListByWspTypeNoResolveData(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from GpGrantBatchList o where o.wspType = :wspType";
		return dao.countWhere(filters, hql);
	}

	public void generateNewBatchListDg(List<SarsLevyDetails> results, Users sessionUser, Date fromDate, Date toDate,
			WspTypeEnum wspTypeEnum) throws Exception {
		List<IDataEntity> creationBatch = new ArrayList<>();
		GpGrantBatchList newBatch = prepNewBatchEntry(sessionUser, fromDate, toDate, wspTypeEnum);
		creationBatch.add(newBatch);
	}

	public void generateNewBatchListDG(List<ActiveContractDetail> activeContractDetailList, Users sessionUser,
			Date fromDate, Date toDate, WspTypeEnum wspTypeEnum) throws Exception {
		List<IDataEntity> creationBatch = new ArrayList<>();
		GpGrantBatchList newBatch = prepNewBatchEntry(sessionUser, fromDate, toDate, wspTypeEnum);
		creationBatch.add(newBatch);

		for (ActiveContractDetail activeContractDetail : activeContractDetailList) {
			GpGrantBatchEntry gpGrantBatchEntry = new GpGrantBatchEntry();
			gpGrantBatchEntry.setGpGrantBatchList(newBatch);
			gpGrantBatchEntry.setActiveContractDetail(activeContractDetail);
			gpGrantBatchEntry.setDescription(setEntryDescription(null, null, newBatch.getBatchNumber(), wspTypeEnum,
					activeContractDetail, null));
			gpGrantBatchEntry.setDocumentNumber(
					setEntryDocumentNumber(null, null, newBatch.getBatchNumber(), wspTypeEnum, activeContractDetail));
			gpGrantBatchEntry.setLoadedToGp(Boolean.FALSE);
			gpGrantBatchEntry.setToBeRemoved(Boolean.FALSE);
			gpGrantBatchEntry.setErrorEntry(Boolean.FALSE);

			if (activeContractDetail.getPayments() < 0) {
				if (activeContractDetail.getPayments() == 0) {
					gpGrantBatchEntry.setDiscretionaryLevy(activeContractDetail.getPayments());
					gpGrantBatchEntry.setDiscretionaryLevyRounded(
							GenericUtility.roundToPrecision(activeContractDetail.getPayments(), 2));
				} else {
					gpGrantBatchEntry.setDiscretionaryLevy(activeContractDetail.getPayments() * -1);
					gpGrantBatchEntry.setDiscretionaryLevyRounded(
							GenericUtility.roundToPrecision(activeContractDetail.getPayments() * -1, 2));
				}
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Return);
			} else {
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Invoice);
				gpGrantBatchEntry.setDiscretionaryLevy(activeContractDetail.getPayments());
				gpGrantBatchEntry.setDiscretionaryLevyRounded(
						GenericUtility.roundToPrecision(activeContractDetail.getPayments(), 2));
			}
			gpGrantBatchEntry.setDocDate(activeContractDetail.getPaymentMonth());
			// Set to approved date.
			gpGrantBatchEntry.setArrivalDateFromSars(
					GenericUtility.deductYearsfromDate(activeContractDetail.getPaymentMonth(), 1));
			if (activeContractDetail.getActiveContracts().getCompany() != null) {
				gpGrantBatchEntry.setLevyNumber(activeContractDetail.getActiveContracts().getCompany().getLevyNumber());
			} else {
				gpGrantBatchEntry.setLevyNumber(activeContractDetail.getActiveContracts().getDgAllocationParent()
						.getWsp().getCompany().getLevyNumber());
			}
			creationBatch.add(gpGrantBatchEntry);
		}

		if (creationBatch.size() != 0) {
			dao.createBatch(creationBatch);
		}
		populateInfoAfterBatchGeneration(newBatch);
		// creates task
		TasksService.instance().findFirstInProcessAndCreateTask(sessionUser, newBatch.getId(),
				newBatch.getClass().getName(), ConfigDocProcessEnum.DG_TRANSACTIONS, false);
	}

	public void generateNewBatchListMandatory(List<SarsLevyDetails> results, Users sessionUser, Date fromDate,
			Date toDate, WspTypeEnum wspTypeEnum, Integer schemeYearSelected) throws Exception {
		List<IDataEntity> creationBatch = new ArrayList<>();
		GpGrantBatchList newBatch = prepNewBatchEntry(sessionUser, fromDate, toDate, wspTypeEnum);
		if (schemeYearSelected != null) {
			newBatch.setFilteredSchemeYear(schemeYearSelected);
		}
		newBatch.setCompletedGpTransation(false);
		creationBatch.add(newBatch);
		for (SarsLevyDetails sarsDetail : results) {
			GpGrantBatchEntry gpGrantBatchEntry = new GpGrantBatchEntry();
			if (sarsDetail.getId() != null) {
				gpGrantBatchEntry.setSarsLevyDetailsId(sarsDetail);
			}
			// set information
			gpGrantBatchEntry.setGpGrantBatchList(newBatch);
			gpGrantBatchEntry.setLevyNumber(sarsDetail.getRefNo());

			if (sarsDetail.getSarsFiles() != null) {
				SarsEmployerDetail sed = sarsEmployerDetailService.findByRefNo(sarsDetail.getRefNo(),
						sarsDetail.getSarsFiles());
				gpGrantBatchEntry.setEmployerName(sed.getRegisteredNameOfEntity());
				sed = null;
			}
			gpGrantBatchEntry.setDescription(setEntryDescription(sarsDetail.getRefNo(), sarsDetail.getSchemeYear(),
					newBatch.getBatchNumber(), wspTypeEnum, null, sarsDetail.getArrivalDate1()));
			gpGrantBatchEntry.setDocumentNumber(setEntryDocumentNumber(sarsDetail.getRefNo(),
					sarsDetail.getSchemeYear(), newBatch.getBatchNumber(), wspTypeEnum, null));
			gpGrantBatchEntry.setLoadedToGp(Boolean.FALSE);
			gpGrantBatchEntry.setToBeRemoved(Boolean.FALSE);

			// multiplies by -1 to swap signs
			Double mandatoryLevyAmount;
			if (sarsDetail.getMandatoryLevy() != null) {
				mandatoryLevyAmount = sarsDetail.getMandatoryLevy().doubleValue() * -1;
				// identify the document type after conversion
				if (mandatoryLevyAmount < 0) {
					// negative number return
					gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Return);
					mandatoryLevyAmount = mandatoryLevyAmount * -1;
				} else {
					// positive number invoice
					gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
				}
			} else {
				mandatoryLevyAmount = (double) 0;
				gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
			}
			Double discretionaryLevyAmount;
			if (sarsDetail.getDiscretionaryLevy() != null) {
				discretionaryLevyAmount = sarsDetail.getDiscretionaryLevy().doubleValue() * -1;

			} else {
				discretionaryLevyAmount = (double) 0;
			}
			gpGrantBatchEntry.setMandatoryLevy(mandatoryLevyAmount);
			gpGrantBatchEntry.setMandatoryLevy(GenericUtility.roundToPrecision(mandatoryLevyAmount, 2));
			gpGrantBatchEntry.setDiscretionaryLevy(discretionaryLevyAmount);
			gpGrantBatchEntry.setDiscretionaryLevyRounded(GenericUtility.roundToPrecision(discretionaryLevyAmount, 2));
			gpGrantBatchEntry.setSchemeYear(sarsDetail.getSchemeYear());

			// sets info from sars
			gpGrantBatchEntry.setArrivalDateFromSars(sarsDetail.getArrivalDate1());
			gpGrantBatchEntry.setMandatoryLevyFromSars(sarsDetail.getMandatoryLevy());
			gpGrantBatchEntry.setDiscretionaryLevyFromSars(sarsDetail.getDiscretionaryLevy());
			gpGrantBatchEntry.setTotal(sarsDetail.getTotal().doubleValue());
			gpGrantBatchEntry.setTotalFromSars(sarsDetail.getTotal());

			// calculates Percentage from sars data
			Double fullAmount = sarsDetail.getTotal().doubleValue() / (double) 0.8;
			Double percentage = sarsDetail.getMandatoryLevy().doubleValue() / fullAmount;
			percentage = (double) Math.round(percentage * 100);

			gpGrantBatchEntry.setFullLevyAmount(fullAmount);
			gpGrantBatchEntry.setFullPercentageCalculation(percentage);

			// identify the document type after conversion
			// if (gpGrantBatchEntry.getMandatoryLevy() < 0) {
			// // negative number return
			// // mandatoryLevyAmount =
			// sarsDetail.getMandatoryLevy().doubleValue() * -1;
			// gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Return);
			// } else {
			// // positive number invoice
			// gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
			// }

			if (gpGrantBatchEntry.getDiscretionaryLevy() < 0) {
				// negative number return
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Return);
			} else {
				// positive number invoice
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Invoice);
			}

			creationBatch.add(gpGrantBatchEntry);
		}

		if (creationBatch.size() != 0) {
			dao.createBatch(creationBatch);
		}

		populateInfoAfterBatchGeneration(newBatch);

		// creates task
		TasksService.instance().findFirstInProcessAndCreateTask(sessionUser, newBatch.getId(),
				newBatch.getClass().getName(), ConfigDocProcessEnum.MG_DG_TRANSACTIONS, false);
	}

	public void generateMgTransactionsBatch(List<MgTransactionsBean> resultsList, Users sessionUser,
			WspTypeEnum wspTypeEnum, Integer schemeYearSelected) throws Exception {
		List<IDataEntity> creationBatch = new ArrayList<>();
		GpGrantBatchList newBatch = prepNewBatchEntry(sessionUser, null, null, wspTypeEnum);
		if (schemeYearSelected != null) {
			newBatch.setFilteredSchemeYear(schemeYearSelected);
		}
		newBatch.setCompletedGpTransation(false);
		creationBatch.add(newBatch);
		if (sarsLevyDetailsService == null) {
			sarsLevyDetailsService = new SarsLevyDetailsService();
		}
		int resultProcessingCount = 1;
		for (MgTransactionsBean result : resultsList) {
			System.out.println(
					"Inside GpGrantBatchListService:: generateMgTransactionsBatch():: inside for loop:: resultProcessingCount:: "
							+ resultProcessingCount);
			SarsLevyDetails sarsDetail = sarsLevyDetailsService.findByKey(result.getSarsLevyFileId().longValue());
			GpGrantBatchEntry gpGrantBatchEntry = new GpGrantBatchEntry();
			if (sarsDetail.getId() != null) {
				gpGrantBatchEntry.setSarsLevyDetailsId(sarsDetail);
			}
			// set informationnnnnnn
			gpGrantBatchEntry.setGpGrantBatchList(newBatch);
			System.out.println(
					"Inside GpGrantBatchListService:: generateMgTransactionsBatch():: inside for loop:: sarsDetail.getRefNo():: "
							+ sarsDetail.getRefNo());
			System.out.println(
					"Inside GpGrantBatchListService:: generateMgTransactionsBatch():: inside for loop:: sarsDetail.getSarsFiles():: "
							+ sarsDetail.getSarsFiles().getId());
			gpGrantBatchEntry.setLevyNumber(sarsDetail.getRefNo());
			if (sarsDetail.getSarsFiles() != null) {
				SarsEmployerDetail sed = sarsEmployerDetailService.findByRefNo(sarsDetail.getRefNo(),
						sarsDetail.getSarsFiles());
				gpGrantBatchEntry.setEmployerName(sed.getRegisteredNameOfEntity());
				gpGrantBatchEntry.setSarsEmployerDetailLinked(sed);
				Chamber chamber = null;
				try {
					System.out.println(
							"Inside GpGrantBatchListService:: generateMgTransactionsBatch():: inside for loop:: inside try():: sicCodeService.findByCode:: "
									+ sed.getSicCode2());
					SICCode code = sicCodeService.findByCode(sed.getSicCode2());
					if (code != null && code.getId() != null && code.getChamber() != null
							&& code.getChamber().getId() != null) {
						System.out.println(
								"Inside GpGrantBatchListService:: generateMgTransactionsBatch():: inside for loop:: inside try():: inside if():: findByKey(code.getChamber().getId()):: "
										+ code.getChamber().getId());
						chamber = chamberService.findByKey(code.getChamber().getId());
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				if (chamber != null && chamber.getId() != null) {
					gpGrantBatchEntry.setChamberLinked(chamber);
				} else {
					gpGrantBatchEntry.setChamberLinked(chamberService.findByKey(HAJConstants.CHAMBER_UNKNOWN_ID));
				}
				chamber = null;
				sed = null;
			}
			gpGrantBatchEntry.setDescription(setEntryDescription(sarsDetail.getRefNo(), sarsDetail.getSchemeYear(),
					newBatch.getBatchNumber(), wspTypeEnum, null, sarsDetail.getArrivalDate1()));
			gpGrantBatchEntry.setDocumentNumber(setEntryDocumentNumber(sarsDetail.getRefNo(),
					sarsDetail.getSchemeYear(), newBatch.getBatchNumber(), wspTypeEnum, null));
			gpGrantBatchEntry.setLoadedToGp(Boolean.FALSE);
			gpGrantBatchEntry.setToBeRemoved(Boolean.FALSE);

			// multiplies by -1 to swap signs
			Double mandatoryLevyAmount;
			if (sarsDetail.getMandatoryLevy() != null) {
				mandatoryLevyAmount = sarsDetail.getMandatoryLevy().doubleValue() * -1;
				// identify the document type after conversion
				if (mandatoryLevyAmount < 0) {
					// negative number return
					gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Return);
					mandatoryLevyAmount = mandatoryLevyAmount * -1;
				} else {
					// positive number invoice
					gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
				}
			} else {
				mandatoryLevyAmount = (double) 0;
				gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
			}
			Double discretionaryLevyAmount;
			if (sarsDetail.getDiscretionaryLevy() != null) {
				discretionaryLevyAmount = sarsDetail.getDiscretionaryLevy().doubleValue() * -1;

			} else {
				discretionaryLevyAmount = (double) 0;
			}
			gpGrantBatchEntry.setMandatoryLevy(mandatoryLevyAmount);
			gpGrantBatchEntry.setMandatoryLevy(GenericUtility.roundToPrecision(mandatoryLevyAmount, 2));
			gpGrantBatchEntry.setDiscretionaryLevy(discretionaryLevyAmount);
			gpGrantBatchEntry.setDiscretionaryLevyRounded(GenericUtility.roundToPrecision(discretionaryLevyAmount, 2));
			gpGrantBatchEntry.setSchemeYear(sarsDetail.getSchemeYear());

			// sets info from sars
			gpGrantBatchEntry.setArrivalDateFromSars(sarsDetail.getArrivalDate1());
			gpGrantBatchEntry.setMandatoryLevyFromSars(sarsDetail.getMandatoryLevy());
			gpGrantBatchEntry.setDiscretionaryLevyFromSars(sarsDetail.getDiscretionaryLevy());
			gpGrantBatchEntry.setTotal(sarsDetail.getTotal().doubleValue());
			gpGrantBatchEntry.setTotalFromSars(sarsDetail.getTotal());
			// calculates Percentage from sars data
			Double fullAmount = sarsDetail.getTotal().doubleValue() / (double) 0.8;
			Double percentage = sarsDetail.getMandatoryLevy().doubleValue() / fullAmount;
			percentage = (double) Math.round(percentage * 100);
			gpGrantBatchEntry.setFullLevyAmount(fullAmount);
			gpGrantBatchEntry.setFullPercentageCalculation(percentage);
			// identify the document type after conversion
			// if (gpGrantBatchEntry.getMandatoryLevy() < 0) {
			// // negative number return
			// // mandatoryLevyAmount =
			// sarsDetail.getMandatoryLevy().doubleValue() * -1;
			// gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Return);
			// } else {
			// // positive number invoice
			// gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
			// }
			if (gpGrantBatchEntry.getDiscretionaryLevy() < 0) {
				// negative number return
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Return);
			} else {
				// positive number invoice
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Invoice);
			}
			creationBatch.add(gpGrantBatchEntry);
			System.out.println(
					"Inside GpGrantBatchListService:: generateMgTransactionsBatch():: for loop ends for resultProcessingCount:: "
							+ resultProcessingCount);
			resultProcessingCount++;
		}
		if (creationBatch.size() != 0) {
			dao.createBatch(creationBatch, 2500);
		}
		populateInfoAfterBatchGeneration(newBatch);

		// creates task
		TasksService.instance().findFirstInProcessAndCreateTask(sessionUser, newBatch.getId(),
				newBatch.getClass().getName(), ConfigDocProcessEnum.MG_DG_TRANSACTIONS, false);
	}

	private void populateInfoAfterBatchGeneration(GpGrantBatchList newBatch) throws Exception {
		if (newBatch.getWspType() == WspTypeEnum.Mandatory) {
			newBatch.setOriginalAmount(
					gpGrantBatchEntryService.sumMandatoryLevyAllEntriesByBatchList(newBatch.getId()));
		} else {
			newBatch.setOriginalAmount(
					gpGrantBatchEntryService.sumDiscretionaryLevyAllEntriesByBatchList(newBatch.getId()));
		}
		update(newBatch);
	}

	private GpGrantBatchList prepNewBatchEntry(Users sessionUser, Date fromDate, Date toDate, WspTypeEnum wspTypeEnum)
			throws Exception {

		GpGrantBatchList gpGrantBatchList = new GpGrantBatchList();
		gpGrantBatchList.setCreateUsers(sessionUser);
		gpGrantBatchList.setWspType(wspTypeEnum);
		gpGrantBatchList.setApprovalEnum(ApprovalEnum.PendingApproval);
		gpGrantBatchList.setValidiationRun(false);
		gpGrantBatchList.setValidiationUnderway(false);

		// what the date range was
		if (fromDate != null) {
			gpGrantBatchList.setFromDatePeriod(fromDate);
			gpGrantBatchList.setSarsFromDatePeriod(fromDate);
		}

		if (toDate != null) {
			gpGrantBatchList.setToDatePeriod(toDate);
			gpGrantBatchList.setSarsToDatePeriod(toDate);
		}

		int totalCount = dao.countAllGpGrantBatchList() + 1;
		gpGrantBatchList.setBatchNumber(totalCount);

		if (wspTypeEnum == WspTypeEnum.Discretionary) {
			gpGrantBatchList.setBatchDescription("Discretionary Grants for Project Reference " + totalCount);
		} else if (wspTypeEnum == WspTypeEnum.Mandatory) {
			gpGrantBatchList.setBatchDescription("Mandatory Grants from List " + totalCount);
		}

		return gpGrantBatchList;
	}

	private String setEntryDescription(String levyNumber, Integer schemeYear, Integer batchNumber,
			WspTypeEnum wspTypeEnum, ActiveContractDetail activeContractDetail, Date arrivalDate) throws Exception {
		String description = "";
		if (wspTypeEnum == WspTypeEnum.Mandatory) {
			Integer setaSchemeYear = schemeYear + 1;
			description = "MAND GRANT " + setaSchemeYear;
			// old version
			// if (arrivalDate != null) {
			// description = "MAND GRANT " + HAJConstants.sdfMMM.format(arrivalDate) + " " +
			// schemeYear;
			// } else {
			// description = "MAND GRANT " + schemeYear;
			// }

		} else {
			description = "DISC GRANT " + activeContractDetail.getActiveContracts().getMoaType().getFriendlyName();
		}
		return description;
	}

	private String setEntryDocumentNumber(String levyNumber, Integer schemeYear, Integer batchNumber,
			WspTypeEnum wspTypeEnum, ActiveContractDetail activeContractDetail) throws Exception {
		// levyNumber
		String documentNumber = "";
		if (wspTypeEnum == WspTypeEnum.Mandatory) {
			/*
			 * levyNumber[Scheme year][Fund A/B/C/D][List number] e.g. 2009A2291
			 */
			Integer setaSchemeYear = schemeYear + 1;
			documentNumber += setaSchemeYear.toString();
			if (setaSchemeYear >= 2006) {
				documentNumber += "A";
			} else if (setaSchemeYear == 2001) {
				documentNumber += "ABCD";
			} else if (setaSchemeYear == 2002) {
				documentNumber += "ABCD";
			} else if (setaSchemeYear >= 2003 && setaSchemeYear <= 2005) {
				documentNumber += "ABC";
			}
			documentNumber += batchNumber.toString();
		} else {
			if (activeContractDetail.getActiveContracts().getDgAllocationParent() != null) {
				int yearLastTwoValues = GenericUtility.returnPlacementInInteger(
						activeContractDetail.getActiveContracts().getDgAllocationParent().getWsp().getFinYearNonNull(),
						2, 4);
				String startingText = "DGYR";
				String tranchValue = "";
				if (activeContractDetail.getTrancheEnum() != null) {
					switch (activeContractDetail.getTrancheEnum()) {
						case TRANCHE_ONE:
							tranchValue = "T1";
							break;
						case TRANCHE_TWO:
							tranchValue = "T2";
							break;
						case TRANCHE_THREE:
							tranchValue = "T3";
							break;
						case TRANCHE_FOUR:
							tranchValue = "T4";
							break;
						case RECOVERABLE_AMOUNT:
							tranchValue = "RA";
							break;
						default:
							tranchValue = "SP";
							break;
					}
				} else {
					tranchValue = "SP";
				}
				// pip
				String interventionTypeShortDesc = "";
				ProjectImplementationPlan pip = null;
				if (activeContractDetail.getProjectImplementationPlan() != null
						&& activeContractDetail.getProjectImplementationPlan().getId() != null) {
					pip = projectImplementationPlanService
							.findByKey(activeContractDetail.getProjectImplementationPlan().getId());
					if (pip != null) {
						InterventionType interventionType = null;
						if (pip.getInterventionTypeDescription() != null
								&& !pip.getInterventionTypeDescription().isEmpty()) {
							List<InterventionType> interventionTypeList = interventionTypeService
									.findByDescription(pip.getInterventionTypeDescription().trim());
							if (interventionTypeList.size() != 0) {
								interventionType = interventionTypeList.get(0);
							}
							if (interventionType != null && interventionType.getId() != null
									&& (interventionType.getShortDescription() != null
											&& !interventionType.getShortDescription().isEmpty())) {
								interventionTypeShortDesc = interventionType.getShortDescription();
							}
						}
					}
				}
				if (interventionTypeShortDesc != null && !interventionTypeShortDesc.trim().isEmpty()) {
					// DGYR20 T1 APP R
					documentNumber = startingText + yearLastTwoValues + " " + tranchValue + " "
							+ interventionTypeShortDesc.toUpperCase() + " " + "R" + activeContractDetail.getId();
				} else {
					// DGYR20 T1 R
					documentNumber = startingText + yearLastTwoValues + " " + tranchValue + " " + "R"
							+ activeContractDetail.getId();
				}
			} else {
				Random rnd = new Random();
				int firstRandom = rnd.nextInt(9999);	
				int secondRandom = rnd.nextInt(9999);	
				documentNumber = String.format("%sA%d-%04d-%04d",new GregorianCalendar().get(Calendar.YEAR),batchNumber,firstRandom,secondRandom);
			}

		}
		return documentNumber;
	}

	public List<GpGrantBatchList> populateAdditionalInformationList(List<GpGrantBatchList> gpGrantBatchList)
			throws Exception {
		for (GpGrantBatchList entry : gpGrantBatchList) {
			populateAdditionalInformation(entry);
		}
		return gpGrantBatchList;
	}

	public GpGrantBatchList populateAdditionalInformation(GpGrantBatchList gpGrantBatchList) throws Exception {
		if (gpGrantBatchList.getId() != null) {
			gpGrantBatchList.setTotalEntiresAssigned(
					gpGrantBatchEntryService.countAllEntriesByBatchList(gpGrantBatchList.getId()));
			gpGrantBatchList.setTotalActive(
					gpGrantBatchEntryService.countAllEntriesByBatchListToBeRemoved(gpGrantBatchList.getId(), false));
			gpGrantBatchList.setDocs(findDocsAssigned(gpGrantBatchList));
		}
		return gpGrantBatchList;
	}

	public List<Doc> findDocsAssigned(GpGrantBatchList gpGrantBatchList) throws Exception {
		return DocService.instance().searchByTargetKeyAndClass(gpGrantBatchList.getClass().getName(),
				gpGrantBatchList.getId());
	}

	public void prepareNewDocsGpGrantBatchList(GpGrantBatchList gpGrantBatchList,
			ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserTypeEnum) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(gpGrantBatchList.getClass().getName(),
				gpGrantBatchList.getId(), configDocProcessEnum, companyUserTypeEnum);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				gpGrantBatchList.getDocs()
						.add(new Doc(a, gpGrantBatchList.getId(), gpGrantBatchList.getClass().getName()));
			});
		}
	}

	public void completeTask(GpGrantBatchList gpGrantBatchList, Users user, Tasks task) throws Exception {
		if (task.getFirstInProcess() == null) {
			TasksService.instance().completeTask(task);
			TasksService.instance().findFirstInProcessAndCreateTask(user, gpGrantBatchList.getId(),
					gpGrantBatchList.getClass().getName(), task.getWorkflowProcess(), false);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		}
		RejectReasonMultipleSelectionService.instance().clearEntries(gpGrantBatchList.getId(),
				gpGrantBatchList.getClass().getName(), task.getWorkflowProcess());
		gpGrantBatchList.setAdditionalRejectionReason(null);
		update(gpGrantBatchList);
	}

	public void rejectTask(GpGrantBatchList gpGrantBatchList, Users user, Tasks task,
			List<RejectReasons> selectedRejectionReasons, String additionalRejectionReason) throws Exception {
		gpGrantBatchList.setValidiationRun(false);
		gpGrantBatchList.setValidiationUnderway(false);
		if (gpGrantBatchList.getApprovalEnum() == ApprovalEnum.PendingFinalApproval) {
			gpGrantBatchList.setApprovalEnum(ApprovalEnum.PendingApproval);
			if (additionalRejectionReason != null && !additionalRejectionReason.isEmpty()) {
				gpGrantBatchList.setAdditionalRejectionReason(additionalRejectionReason);
			}
			update(gpGrantBatchList);
		} else {
			if (additionalRejectionReason != null && !additionalRejectionReason.isEmpty()) {
				gpGrantBatchList.setAdditionalRejectionReason(additionalRejectionReason);
				update(gpGrantBatchList);
			}
		}
		if (task.getFirstInProcess() == true) {

		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
			List<IDataEntity> createList = new ArrayList<>();
			createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(
					gpGrantBatchList.getId(), gpGrantBatchList.getClass().getName(), selectedRejectionReasons, user,
					task.getWorkflowProcess()));
			if (createList.size() != 0) {
				dao.createBatch(createList);
			}
		}

	}

	public void approveTask(GpGrantBatchList gpGrantBatchList, Users user, Tasks task) throws Exception {
		gpGrantBatchList.setApprovalEnum(ApprovalEnum.PendingFinalApproval);
		gpGrantBatchList.setAdditionalRejectionReason(null);
		update(gpGrantBatchList);
		TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(gpGrantBatchList.getId(),
				gpGrantBatchList.getClass().getName(), task.getWorkflowProcess());
	}

	public void finalRejectTask(GpGrantBatchList gpGrantBatchList, Users user, Tasks task,
			List<RejectReasons> selectedRejectionReasons, String additionalRejectionReason) throws Exception {
		gpGrantBatchList.setApprovalEnum(ApprovalEnum.Rejected);
		gpGrantBatchList.setApproveUser(user);
		gpGrantBatchList.setDateFinalApproved(getSynchronizedDate());
		if (additionalRejectionReason != null && !additionalRejectionReason.isEmpty()) {
			gpGrantBatchList.setAdditionalRejectionReason(additionalRejectionReason);
		}
		update(gpGrantBatchList);
		TasksService.instance().completeTask(task);
		List<IDataEntity> createList = new ArrayList<>();
		createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(gpGrantBatchList.getId(),
				gpGrantBatchList.getClass().getName(), selectedRejectionReasons, user, task.getWorkflowProcess()));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
	}

	public void finalApproveTask(GpGrantBatchList gpGrantBatchList, Users user, Tasks task) throws Exception {
		gpGrantBatchList.setApprovalEnum(ApprovalEnum.Approved);
		gpGrantBatchList.setApproveUser(user);
		gpGrantBatchList.setDateFinalApproved(getSynchronizedDate());
		gpGrantBatchList.setAdditionalRejectionReason(null);
		update(gpGrantBatchList);
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().clearEntries(gpGrantBatchList.getId(),
				gpGrantBatchList.getClass().getName(), task.getWorkflowProcess());
		finalApprovalOfBatchListActiosn(gpGrantBatchList);
	}

	private void finalApprovalOfBatchListActiosn(GpGrantBatchList gpGrantBatchList) throws Exception {

		// remove entries identified be removed
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(gpGrantBatchEntryService.allEntriesToBeDeleted(gpGrantBatchList.getId(), true));
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}

		// populate entries based on batch type
		if (gpGrantBatchList.getWspType() == WspTypeEnum.Mandatory) {
			notifyDajoTeam("GP BATCH PROCESS START",
					"Be advised: posting to GP has started. Please refain from deployments.", false, null);
			sendMandatoryInformationToGP(gpGrantBatchList);
		} else if (gpGrantBatchList.getWspType() == WspTypeEnum.Discretionary) {
			notifyDajoTeam("GP BATCH PROCESS START",
					"Be advised: posting to GP has started. Please refain from deployments.", false, null);
			sendDiscretionaryInformationToGP(gpGrantBatchList);
		}
	}

	private void sendMandatoryInformationToGP(GpGrantBatchList gpGrantBatchList) throws Exception {
		// List<GpGrantBatchEntry> resultsToBeProcessed =
		// gpGrantBatchEntryService.allEntriesToBeProcessesedGrouping(gpGrantBatchList.getId(),
		// false);
		// for (GpGrantBatchEntry gpGrantBatchEntry : resultsToBeProcessed) {
		// processesGroupingEntry(gpGrantBatchEntry, gpGrantBatchList);
		// }
		List<GpGrantBatchEntry> resultsToBeProcessed = gpGrantBatchEntryService
				.allEntriesToBeProcessesed(gpGrantBatchList.getId(), false);
		if (resultsToBeProcessed.size() != 0) {
			enqueuePaymentsMessages(gpGrantBatchList, resultsToBeProcessed);
		}
	}

	private void sendDiscretionaryInformationToGP(GpGrantBatchList gpGrantBatchList) throws Exception {
		List<GpGrantBatchEntry> resultsToBeProcessed = gpGrantBatchEntryService
				.allEntriesByBatchListToBeRemoved(gpGrantBatchList.getId(), false);
		enqueuePaymentsMessages(gpGrantBatchList, resultsToBeProcessed);
	}

	private void enqueuePaymentsMessages(GpGrantBatchList batchList, List<GpGrantBatchEntry> entries) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (GpGrantBatchEntry entry : entries) {
					try {
						GrantPaymentsBatchMessageProducer.enqueuePaymentsProcessingEntry(batchList.getId(),
								batchList.getBatchNumber(), batchList.getWspType().toString(), entry);
					} catch (Exception e) {
						logger.error("Exception occured while enqueing GP batch payments entry", e);
						GenericUtility.mailError(
								String.format("Failed to queue batch payments message for levyNumber=%s",
										entry.getLevyNumber()),
								String.format(
										"An Exception Has Accured On haj.com.service.GpGrantBatchListService.enqueuePaymentsMessages [%s:%s]",
										e.getClass(), e.getMessage()));
					}
				}
			}
		}).start();
	}

	/* No Longer Required */
	private void processesGroupingEntry(GpGrantBatchEntry gpGrantBatchEntry, GpGrantBatchList gpGrantBatchList)
			throws Exception {
		gpGrantBatchEntry.setDescription(setEntryDescription(gpGrantBatchEntry.getLevyNumber(),
				gpGrantBatchEntry.getSchemeYear(), gpGrantBatchList.getBatchNumber(), gpGrantBatchList.getWspType(),
				gpGrantBatchEntry.getActiveContractDetail(), null));
		gpGrantBatchEntry.setDocumentNumber(setEntryDocumentNumber(gpGrantBatchEntry.getLevyNumber(),
				gpGrantBatchEntry.getSchemeYear(), gpGrantBatchList.getBatchNumber(), gpGrantBatchList.getWspType(),
				gpGrantBatchEntry.getActiveContractDetail()));
		if (gpGrantBatchList.getWspType() == WspTypeEnum.Discretionary) {
			if (gpGrantBatchEntry.getDiscretionaryLevy() < 0) {
				// negative number return
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Return);
			} else {
				// positive number invoice
				gpGrantBatchEntry.setDocumentTypeDiscretionary(GpDocumentType.Invoice);
			}
		} else if (gpGrantBatchList.getWspType() == WspTypeEnum.Mandatory) {
			// identify the document type after conversion
			if (gpGrantBatchEntry.getMandatoryLevy() < 0) {
				// negative number return
				gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Return);
			} else {
				// positive number invoice
				gpGrantBatchEntry.setDocumentTypeMandatory(GpDocumentType.Invoice);
			}
		}
	}

	public void updateGroupingEntry(GpGrantBatchEntry entryToBeAdded, Long batchId) throws Exception {
		List<GpGrantBatchEntry> updateList = gpGrantBatchEntryService
				.allEntriesByBatchCompoundKey(entryToBeAdded.getLevyNumber(), entryToBeAdded.getSchemeYear(), batchId);
		List<IDataEntity> updatebatchList = new ArrayList<>();
		for (GpGrantBatchEntry result : updateList) {
			result.setLoadedToGp(true);
			result.setDateLoadedToGp(getSynchronizedDate());
			updatebatchList.add(result);
		}
		if (updatebatchList.size() != 0) {
			dao.updateBatch(updatebatchList);
		}
	}

	public void updateGroupingEntryError(GpGrantBatchEntry entryToBeAdded, Long batchId, String errorMessage)
			throws Exception {
		List<GpGrantBatchEntry> updateList = gpGrantBatchEntryService
				.allEntriesByBatchCompoundKey(entryToBeAdded.getLevyNumber(), entryToBeAdded.getSchemeYear(), batchId);
		List<IDataEntity> updatebatchList = new ArrayList<>();
		for (GpGrantBatchEntry result : updateList) {
			result.setErrorEntry(true);
			result.setErrorMessage(errorMessage);
			updatebatchList.add(result);
		}
		if (updatebatchList.size() != 0) {
			dao.updateBatch(updatebatchList);
		}
	}

	public void updateEntry(GpGrantBatchEntry entryToBeAdded) throws Exception {
		List<IDataEntity> updatebatchList = new ArrayList<>();
		entryToBeAdded.setLoadedToGp(true);
		entryToBeAdded.setDateLoadedToGp(getSynchronizedDate());
		updatebatchList.add(entryToBeAdded);
		if (!updatebatchList.isEmpty()) {
			dao.updateBatch(updatebatchList);
		}
	}

	public void updateEntry(IDataEntity updateEntry) throws Exception {
		dao.update(updateEntry);
	}
	
	public void testGPBatch(GpGrantBatchList batchList) throws Exception {
		sendMandatoryInformationToGP(batchList);
	}

	public void testDgBatch(GpGrantBatchList batchList) throws Exception {
		sendDiscretionaryInformationToGP(batchList);
	}

	public Boolean vendorOnGP(String levyNumber) throws Exception {
		if (levyNumber != null && !levyNumber.isEmpty()) {
			Vendor vendor = null;
			vendor = bankingDetailsService.getGPDetailsForLNumberAndCreateIfApp(levyNumber, "");
			if (vendor == null) {
				vendor = bankingDetailsService.getGPDetailsForLNumber(levyNumber, "");
			}
			if (vendor != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void runValidiation(Users activeUser, GpGrantBatchList gpgrantbatchlist) throws Exception {
		gpgrantbatchlist.setValidiationUnderway(true);
		update(gpgrantbatchlist);
		String subject = "BATCH COMPANY VALIDATION STARTED";
		String msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Be advised: Levy Number Validation for Batch: #BATCH_NUMBER# has been initiated by #USER_FIRST_NAME# #LAST_FIRST_NAME#.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The batch process can continue after validation is complete</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">You will be notified upon completion</p>";
		// Add tags info
		msg = msg.replace("#BATCH_NUMBER#", gpgrantbatchlist.getBatchDescription().trim());
		msg = msg.replace("#USER_FIRST_NAME#", activeUser.getFirstName().trim());
		msg = msg.replace("#LAST_FIRST_NAME#", activeUser.getLastName().trim());
		GenericUtility.sendMail(activeUser.getEmail(), subject,
				msg.replace("#FULL_NAME#", activeUser.getFirstName().trim() + " " + activeUser.getLastName().trim()),
				null);
		notifyDajoTeam(subject, msg, false, null);

		// creates thread
		runLevyNumberValidiations(activeUser, gpgrantbatchlist);
	}

	public void runValidiationMandatoryGrantTransaction(Users activeUser, GpGrantBatchList gpgrantbatchlist)
			throws Exception {
		gpgrantbatchlist.setValidiationUnderway(true);
		update(gpgrantbatchlist);
		String subject = "BATCH COMPANY VALIDATION STARTED";
		String msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Be advised: Levy Number Validation for Batch: #BATCH_NUMBER# has been initiated by #USER_FIRST_NAME# #LAST_FIRST_NAME#.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The batch process can continue after validation is complete</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">You will be notified upon completion</p>";
		msg = msg.replace("#BATCH_NUMBER#", gpgrantbatchlist.getBatchDescription().trim());
		msg = msg.replace("#USER_FIRST_NAME#", activeUser.getFirstName().trim());
		msg = msg.replace("#LAST_FIRST_NAME#", activeUser.getLastName().trim());
		GenericUtility.sendMail(activeUser.getEmail(), subject,
				msg.replace("#FULL_NAME#", activeUser.getFirstName().trim() + " " + activeUser.getLastName().trim()),
				null);
		notifyDajoTeam(subject, msg, false, null);

		// creates thread
		runLevyNumberValidiations(activeUser, gpgrantbatchlist);
	}

	public void reopenValidiationRunMandatoryGrantTransaction(Users activeUser, GpGrantBatchList gpgrantbatchlist)
			throws Exception {
		gpgrantbatchlist.setValidiationRun(false);
		update(gpgrantbatchlist);
	}

	/**
	 * @param activeUser
	 * @param gpgrantbatchlist
	 */
	private void runLevyNumberValidiations(Users activeUser, GpGrantBatchList gpgrantbatchlist) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Users initiatorUser = usersService.findByKey(activeUser.getId());
					GpGrantBatchList batch = findByKey(gpgrantbatchlist.getId());
					transactionsCompanyValidiationService
							.deleteAllEntriesByTargetClassAndKey(batch.getClass().getName(), batch.getId());
					List<String> levyNumberList = gpGrantBatchEntryService
							.allDistinctLevyNumbersByBatchIdAndToBeRemoved(batch.getId(), false);

					for (String levyNumber : levyNumberList) {
						try {
							GrantBatchValidationMessageProducer.enqueueValidationEntry(levyNumber, batch.getId(),
									initiatorUser.getId(), batch.getWspType().toString());
						} catch (Exception e) {
							logger.error(String.format(
									"Exception occured while enqueuing batch entry for validation [levyNumber=%s]",
									levyNumber), e);
							GenericUtility.mailError(
									String.format("Failed to queue validation message for levyNumber=%s", levyNumber),
									String.format(
											"An Exception Has Accured On haj.com.service.GpGrantBatchListService.runLevyNumberValidiations(Users, GpGrantBatchList). %s:%s ",
											e.getClass(), e.getMessage()));
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
					GenericUtility.mailError("EXCEPTION ON GP runLevyNumberValidiations",
							"An Exception Has Accured On haj.com.service.GpGrantBatchListService.runLevyNumberValidiations(Users, GpGrantBatchList). Error Message: "
									+ e.getMessage());
				}
			}
		}).start();
	}

	public void notifyDajoTeam(String subject, String msg, boolean addAttatchment,
			List<AttachmentBean> attachmentBeans) {
		if (Boolean.TRUE.equals(addAttatchment)) {
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMailWithAttachementTempWithLog(emailNotificiations,
						subject + " " + HAJConstants.PL_LINK, msg.replace("#FULL_NAME#", "SUPPORT USER"),
						attachmentBeans, null);
			}
		} else {
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, subject + " " + HAJConstants.PL_LINK,
						msg.replace("#FULL_NAME#", "SUPPORT USER"), null);
			}
		}
	}

	public List<Users> populateEmailRecivers(Users initiatorUser) throws Exception {
		List<Users> emailReceivers = new ArrayList<>();

		// add user who initiated the process
		emailReceivers.add(initiatorUser);

		// locate: Senior Manager: Financial Management & Reporting
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService.findByAllActiveUsersByJobTitleId(
				HAJConstants.JOB_TITLE_SENIOR_MANAGER_FINANCIAL_MANAGEMENT_REPORTING_ID));

		// locate: Manager: Levy & Grants
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_MANAGER_LEVY_GRANTS_ID));

		// locate: Coordinator: Levy & Grants
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_COORDINATOR_LEVY_GRANTS_ID));

		// locate: Chief Executive Officer
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_CHIEF_EXECUTIVE_OFFICER_ID));

		// locate: Chief Financial Officer
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_CHIEF_FINANCIAL_OFFICER_ID));

		// locate: Senior Manager: Programmes Implementation
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_SENIOR_MANAGER_PROGRAMMES_IMPLEMENTATION_ID));

		// locate: Chief Information Officer
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_CHIEF_INFORMATION_OFFICER_ID));

		// locate: Manager: Programmes Implementation
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_MANAGER_PROGRAMMES_IMPLEMENTATION_ID));

		// locate: Senior Manager: Client Services
		addUsersToNotificationList(emailReceivers, hostingCompanyEmployeesService
				.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_SENIOR_MANAGER_CLIENT_SERVICE_ID));

		return emailReceivers;
	}

	private void addUsersToNotificationList(List<Users> emailReceivers, List<Users> usersToAdd) throws Exception {
		if (!usersToAdd.isEmpty()) {
			for (Users newUser : usersToAdd) {
				boolean addToList = true;
				for (Users usersInList : emailReceivers) {
					if (usersInList.getId().equals(newUser.getId())) {
						addToList = false;
					}
				}
				if (Boolean.TRUE.equals(addToList)) {
					emailReceivers.add(newUser);
				}
			}
		}
	}

	public byte[] populateResults(GpGrantBatchList gpgrantbatchlist) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1",
				new Object[] { "Date Validation Ran", "Creditor ID / Levy Number", "Error Entry", "Error Message" });

		Integer counter = 2;
		List<TransactionsCompanyValidiation> errorList = transactionsCompanyValidiationService
				.findByTargetClassKeyAndErrorEntry(gpgrantbatchlist.getClass().getName(), gpgrantbatchlist.getId(),
						true);
		for (TransactionsCompanyValidiation transactionsCompanyValidiation : errorList) {

			String dateRun = "";
			if (transactionsCompanyValidiation.getCreateDate() != null) {
				dateRun = sdf.format(transactionsCompanyValidiation.getCreateDate());
			}

			String levyNumber = "";
			if (transactionsCompanyValidiation.getLevyNumber() != null
					&& !transactionsCompanyValidiation.getLevyNumber().trim().isEmpty()) {
				levyNumber = transactionsCompanyValidiation.getLevyNumber().trim();
			}

			String errorEntry = "";
			if (transactionsCompanyValidiation.getErrorEntry() != null) {
				errorEntry = ((Boolean.TRUE.equals(transactionsCompanyValidiation.getErrorEntry()) ? "YES" : "NO"));
			}

			String errorMessage = "";
			if (transactionsCompanyValidiation.getErrorMessage() != null
					&& !transactionsCompanyValidiation.getErrorMessage().trim().isEmpty()) {
				errorMessage = transactionsCompanyValidiation.getErrorMessage();
			}

			data.put(counter.toString(), new Object[] { dateRun, levyNumber, errorEntry, errorMessage });
			counter++;
		}

		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			return bos.toByteArray();
		} catch (Exception e) {
			logger.fatal(e);
			GenericUtility.mailError("EXCEPTION ON GP runLevyNumberValidiations: populateResults",
					"An Exception Has Accured On haj.com.service.GpGrantBatchListService.runLevyNumberValidiations(Users, GpGrantBatchList) in haj.com.service.GpGrantBatchListService.populateResults(GpGrantBatchList). Error Message: "
							+ e.getMessage());
			return null;
		}
	}

}