package haj.com.service;

import java.util.Date;

import haj.com.constants.HAJConstants;
import haj.com.entity.TrainingProviderApplication;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

public class ScheduleService extends AbstractService {

	/** Service level */
	private DgVerificationService dgVerificationService = new DgVerificationService();
	private WspService wspService = new WspService();
	private MandatoryGrantDetailService mandatoryGrantDetailService = null;
	private MandatoryGrantDetailArchiveService mandatoryGrantDetailArchiveService = null;
	private ReportGenerationService reportGenerationService = null;
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = null;
	private CompanyLearnersLostTimeService companyLearnersLostTimeService = null;
	private ActiveContractsService activeContractsService = null;
	private QmrFinYearsService qmrFinYearsService = null;
	private TrainingProviderApplicationService trainingProviderApplicationService = null;
	private AssessorModeratorApplicationService assessorModeratorApplicationService = null;

	/**
	 * Run ALL the Schedules
	 */
	public void runSchedule() {
		try {

			// date populated for processes to run
			Date today = new Date();
			
			/**
			 * Schedule Check for DG verification
			 */
			try {
				dgVerificationService.dgVerificationScheduleService();
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("DG verification check completed with errors (dgVerificationService.dgVerificationScheduleService)", e.getMessage());
			}
			
			/**
			 * Schedule Check for WSP / Grants
			 */
			try {
				wspService.scheduleServiceWsp();
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("WSP / Grants check completed with errors (wspService.scheudleServiceWsp())", e.getMessage());
			}
			
			/**
			 * Generation of MG Verification 
			 * Set to generate  of current year
			 */
			try {
				Date date =  GenericUtility.getStartOfDay(today);
				Date compare = GenericUtility.getStartOfDay(GenericUtility.sdf5.parse("16 June " + GenericUtility.getYear(date)));
				if (GenericUtility.getYear(date) == 2020) {
					compare = GenericUtility.getStartOfDay(GenericUtility.sdf5.parse("16 August " + GenericUtility.getYear(date)));
				}
				if (date.compareTo(compare) == 0) {
					if (mandatoryGrantDetailService == null) {
						mandatoryGrantDetailService = new MandatoryGrantDetailService();
					}
					mandatoryGrantDetailService.createMGVerificationDataVersionTwo();
					mandatoryGrantDetailService = null;
				}
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("Generation of Mandatory Grant Verifications failed", e.getMessage());
			}
			
			/**
			 * Learner on last level extension
			 */
			try {
				
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("Learner on last level extension completed with errors (tasksService)", e.getMessage());
			}
			
			/**
			 * contract period end
			 */
			try {
				
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("contract period end completed with errors (tasksService)", e.getMessage());
			}
			
			/**
			 * Nightly Archive Of Mandatory Grant Detail Data That Failed Import For Approved Wsps Between 2018 and 2019 
			 */
			try {
				if (mandatoryGrantDetailArchiveService == null) {
					mandatoryGrantDetailArchiveService = new MandatoryGrantDetailArchiveService();
				}
				mandatoryGrantDetailArchiveService.schedularArchiveEntriesNotImportedByYear();
				mandatoryGrantDetailArchiveService = null;
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("mandatory Grant Data Archive Completed With Errors (mandatoryGrantDetailArchiveService.schedularArchiveEntriesNotImportedByYear())", e.getMessage());
			}
			
			/**
			 * ARPL Email Reminder Service
			 * 1. 5 and current date Day reminder for document submission
			 */
			try {
				if (companyLearnersTradeTestService == null) {
					companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
				}
				companyLearnersTradeTestService.arplEmailReminderServiceDoucmnetSubmission(today);
				companyLearnersTradeTestService = null;
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("ARPL Email Reminder Service Completed With Errors (companyLearnersTradeTestService.arplEmailReminderServiceDoucmnetSubmission())", e.getMessage());
			}
			
			/**
			 * ARPL Email Reminder Service
			 * Trade test date reminders
			 * 
			 * - need to investigate
			 */
			try {
				if (companyLearnersTradeTestService == null) {
					companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
				}
				companyLearnersTradeTestService.arplEmailReminderService(today);
				companyLearnersTradeTestService = null;
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("ARPL Email Reminder Service Completed With Errors (companyLearnersTradeTestService.arplEmailReminderService(today))", e.getMessage());
			}
			
			/**
			 * Report Generation on scheduler service
			 */
			try {
				if (reportGenerationService == null) {
					reportGenerationService = new ReportGenerationService();
				}
				reportGenerationService.runReportgeneration();
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("Report Generation Schedule Completed With Errors (reportGenerationService.runReportgeneration())", e.getMessage());
			}
			
			/**
			 * Expiry of agreement/contract for Learner: on scheduler service
			 * 
			 */
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {	
				try {
					if (companyLearnersLostTimeService == null) {
						companyLearnersLostTimeService = new CompanyLearnersLostTimeService();
					}
					companyLearnersLostTimeService.runLearneAggrementCheck();
				} catch (Exception e) {
					logger.fatal(e);
					GenericUtility.mailError("Report Generation Schedule Completed With Errors (reportGenerationService.runReportgeneration())", e.getMessage());
				}
			}
			
			/**
			 * Active Contract Reminder Service
			 * 
			 */
			try {
				if (!HAJConstants.DEV_TEST_PROD.equals("P")) {	
					if (activeContractsService == null) {
						activeContractsService = new ActiveContractsService();
					}
					activeContractsService.activeContractReminderSchedual(today);
				}
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("Active Contract Reminder Service Completed With Errors (activeContractsService.activeContractReminderSchedual())", e.getMessage());
			}
			
			/**
			 * QMR Reporting: New Financial Year Generation
			 */
			try {	
				if (qmrFinYearsService == null) {
					qmrFinYearsService = new QmrFinYearsService();
				}
				qmrFinYearsService.generateNewFinYear(today);
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("QMR Reporting Service Completed With Errors (qmrFinYearsService.generateNewFinYear(today))", e.getMessage());
			}
			
			/**
			 * QMR Reporting: Generating data for quarters
			 */
			try {	
				if (qmrFinYearsService == null) {
					qmrFinYearsService = new QmrFinYearsService();
				}
				qmrFinYearsService.generateDataForQuarter(today);
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("QMR Reporting Service Completed With Errors (qmrFinYearsService.generateDataForQuarter(today))", e.getMessage());
			}
			
			/**
			 * Assessors / Moderator Application De-Accreditation Schedule
			 */
			try {
				if (assessorModeratorApplicationService == null) {
					assessorModeratorApplicationService = new AssessorModeratorApplicationService();
				}
				assessorModeratorApplicationService.applicationDeAccreditationSchedule(today);
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("AssessorModeratorApplicationService Completed With Errors (assessorModeratorApplicationService.applicationDeAccreditationSchedule(today))", e.getMessage());
			}
			
			/**
			 * Training Provider Application De-Accreditation Schedule
			 */
			try {
				if (trainingProviderApplicationService == null) {
					trainingProviderApplicationService = new TrainingProviderApplicationService();
				}
				trainingProviderApplicationService.applicationDeAccreditationSchedule(today);
			} catch (Exception e) {
				logger.fatal(e);
				GenericUtility.mailError("TrainingProviderApplicationService Completed With Errors (trainingProviderApplicationService.applicationDeAccreditationSchedule(today))", e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			GenericUtility.mailError("Schedule completed with errors", e.getMessage());
		}
	}
	
}
