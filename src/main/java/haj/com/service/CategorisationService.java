package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.TrainingComittee;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.exceptions.CategorizationException;
import haj.com.framework.AbstractService;
import haj.com.sars.SarsFiles;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * <h3>Categorisation rules.</h3>
 * <ol>
 * <li>All non-levy paying companies (denoted with an N number) will
 * automatically obtain a Silver level categorisation and can never upgrade to
 * Gold or Platinum level</li>
 * <li>Levy paying companies can only have one status at any given time i.e.
 * either Silver, Gold or Platinum</li>
 * </ol>
 * 
 */
public class CategorisationService extends AbstractService {

	private CompanyService companyService = new CompanyService();
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private SarsLevyDetailsService sarsLevyDetailsService = new SarsLevyDetailsService();
	public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();
	/**
	 * Start of the SARS financial year
	 */
	public final static String SARS_FIN_YEAR_START = "-03-01";

	/**
	 * Start the process of categorization of a company
	 * 
	 * @param company
	 * @throws Exception
	 */
	public void categorizeCompany(Company company) throws Exception {
		// All non-levy paying companies (denoted with an N number) will automatically obtain a Silver level categorization
		if (StringUtils.isBlank(company.getLevyNumber())) {
			company.setCategorization(CategorizationEnum.Silver);
			companyService.updateIgnoreAddressUpdate(company);
		} else {
			startWithPlatiniumChecks(company);
		}

		companyService.updateIgnoreAddressUpdate(company);
	}

	/**
	 * Initiate the platinium status checks
	 * 
	 * @param company
	 */
	private void startWithPlatiniumChecks(Company company) throws Exception {
		try {
			company.setCategorization(null);
			doPlatiniumChecks(company);
		} catch (CategorizationException pe) {
			try {
				doGoldChecks(company);
			} catch (CategorizationException ge) {
				try {
					doSilverChecks(company);
				} catch (CategorizationException se) {
					company.setCategorization(CategorizationEnum.Silver);
				}
			}
		}
	}

	/**
	 * Detailed platinium status checks
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void doPlatiniumChecks(Company company) throws CategorizationException, Exception {
		// Submitted Mandatory Grant Application and has been approved for at least 2  out of a 3 year period or has been approved in all 3 years
		sumittedMandatoryGrantAppApprvovedMin2Times(company);

		// Check if levy paid for the previous financial year
		paidLast12MonthsLevy(company.getLevyNumber());

		// Check if recognition agreement in place if company has 50 or more employees
		if (company.getNumberOfEmployees() >= 50) {
			recognitionAgreementInPlace(company);
		}
		trainingCommitteeChecks(company);

		// Check if percentage of learners that have the 'completed' status any intervention since 2011 is equal to or greater than 80%
		learnersCompletedAnyInterventionSince2011(company, 80, 100);

		// Check if at least 90% of learners have a 'registered' status since 2011 for all MoA (contracts)
		learnersRegsiteredSince2011ForAllMoA(company, 90);

		// Check if percentage of completion of all funded MoA (contracts) is at least 75% since 2011
		percentageOfCompletionOfAllFundedMoA(company, 75);

		// Check if of learner progress is equal to or greater than 60%
		learnerProgressComparedToPercentage(company, 60);

		// This a a Platininum customer
		company.setCategorization(CategorizationEnum.Platinum);
	}
	
	

	/**
	 * Detailed gold status checks
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void doGoldChecks(Company company) throws CategorizationException, Exception {
		// Submitted Mandatory Grant Application and has been approved for at least 2 out of a 3 year period or has been approved in all 3 years
		sumittedMandatoryGrantAppApprvovedMin2Times(company);

		// Check if levy paid for the previous financial year
		paidLast12MonthsLevy(company.getLevyNumber());

		// Check if recognition agreement in place if company has 50 or more employees
		if (company.getNumberOfEmployees() >= 50) {
			recognitionAgreementInPlace(company);
		}

		trainingCommitteeChecks(company);

		// Check if percentage of learners that have the 'completed' status any intervention since 2011 is equal to or greater than 60% and less than 79%
		learnersCompletedAnyInterventionSince2011(company, 60, 79);

		// Check if at least 80% of learners have a 'registered' status since 2011 for all MoA (contracts)
		learnersRegsiteredSince2011ForAllMoA(company, 80);

		// Check if percentage of completion of all funded MoA (contracts) is at least 65% since 2011
		percentageOfCompletionOfAllFundedMoA(company, 65);

		// Check if of learner progress is equal to or greater than 60%
		learnerProgressComparedToPercentage(company, 60);

		// This a a Gold customer
		company.setCategorization(CategorizationEnum.Gold);
	}
	
	


	/**
	 * Detailed silver checks
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void doSilverChecks(Company company) throws CategorizationException, Exception {
		// Check if company has submitted a mandatory grant application
		sumittedMandatoryGrantApplication(company);
		// Check if levy paid for the previous financial year
		paidLast12MonthsLevy(company.getLevyNumber());
		// This a a Silver customer
		company.setCategorization(CategorizationEnum.Silver);
	}

	/**
	 * Did company submitted a Mandatory Grant Application and has been approved for
	 * at least 2 out of a 3 year period or has been approved in all 3 years
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void sumittedMandatoryGrantAppApprvovedMin2Times(Company company) throws CategorizationException, Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Company has submitted a mandatory grant application
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void sumittedMandatoryGrantApplication(Company company) throws CategorizationException, Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Check if employer has paid levies for the last 12 months
	 * 
	 * @param sdlNummber
	 * @return
	 * @throws Exception
	 */
	public void paidLast12MonthsLevy(String sdlNummber) throws CategorizationException, Exception {
		List<SarsFiles> l = sarsFilesService.last12MonthsSarsFiles();
		if (l.size() < 12) throw new CategorizationException("Not enough levy data loaded to do calculation");
		

		Date fromDate = GenericUtility.getFirstDayOfMonth(l.get(11).getForMonth());
		Date toDate = GenericUtility.getFirstDayOfMonth(l.get(0).getForMonth());
		Long noLevies = sarsLevyDetailsService.numberOfLevies(sdlNummber, fromDate, toDate, GenericUtility.initNumericSqlInClause(resolveFinYears(toDate).toString()));
		if (noLevies < 12) throw new CategorizationException("Company did not pay the last 12 months levies");
	}

	/**
	 * Resolve fin years.
	 *
	 * @param date
	 *            the date
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Integer> resolveFinYears(Date date) throws Exception {
		/*
		 * SARS scheme year 2017 : 2017-03-01 => 2018-02-28 SARS scheme year 2016 :
		 * 2016-03-01 => 2017-02-28 SARS scheme year 2015 : 2015-03-01 => 2016-02-28
		 * SARS scheme year 2014 : 2014-03-01 => 2015-02-28
		 */
		List<Integer> finYear = new ArrayList<Integer>();
		Date startFinYear = sdf.parse(sdfYYYY.format(date) + SARS_FIN_YEAR_START);
		if (date.before(startFinYear)) {
			startFinYear = sdf.parse((Integer.valueOf(sdfYYYY.format(date)) - 1) + SARS_FIN_YEAR_START);
		}
		int months = GenericUtility.getMonthsBetweenDates(startFinYear, date);
		int yy = Integer.valueOf(sdfYYYY.format(startFinYear));
		finYear.add(yy);
		if (months < 11) {
			finYear.add((yy - 1));
		}
		return finYear;
	}

	/**
	 * Is recognition agreement in place
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void recognitionAgreementInPlace(Company company) throws CategorizationException, Exception {
		if (company.getRecognitionAgreement() != null && company.getRecognitionAgreement().getId() == HAJConstants.NO_ID) throw new CategorizationException("No Recognition Agreement in Place");
	}

	/**
	 * Check if Training Committee in place and if recognised trade union member is
	 * part of Training Committee
	 * <ul>
	 * <li>Check if two sets of Training Committee minutes have been uploaded</li>
	 * <li>Check if trade union member has attended meetings</li>
	 * <li>Check if company participates in Training Committee i.e. there is an
	 * Employer Representative</li>
	 * <li>System to generate Training Committee Status Report which is summary to
	 * show that a Training Committee is in place, recognised trade union member is
	 * part of training committee, 2 sets of Training Committee Minutes have been
	 * submitted, trade union member has attended meetings and Employer has a
	 * representative attending the meetings.
	 * <ul>
	 * <li>Once populated, this report is to be sent to the Region Client
	 * Relationship Manager to review and to approve.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void trainingCommitteeChecks(Company company) throws CategorizationException, Exception {
		List<TrainingComittee> tc = trainingComitteeService.findByCompany(company);
		if (tc.size() == 0) throw new CategorizationException("No Training Comittee in Place");
	}

	/**
	 * Check if percentage of learners that have the 'completed' status any
	 * intervention since 2011 (validated against a percentage)
	 * <ol>
	 * <li>These are learners who have been funded my merSETA</li>
	 * <li>Percentage calculated as: [Number of learners with a completed/ (Total
	 * number of learners since 2011 - number learners with a 'registered' status
	 * since 2011)] * 100</li>
	 * </ol>
	 * 
	 * @param company
	 * @param fromPercentage
	 * @param toPercentage
	 * @param i
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void learnersCompletedAnyInterventionSince2011(Company company, int fromPercentage, int toPercentage) throws CategorizationException, Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * Check if at least a certain percentage of learners have a 'registered' status
	 * since 2011 for all MoA (contracts)
	 * <ol>
	 * <li>Percentage calculated as follows: (Total number learners with a
	 * 'registered' status learners since 2011 / Total number learners since 2011) *
	 * 100</li>
	 * </ol>
	 * 
	 * @param company
	 * @param percentage
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void learnersRegsiteredSince2011ForAllMoA(Company company, int percentage) throws CategorizationException, Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * Check if percentage of completion of all funded MoA (contracts) is at least
	 * n% since 2011
	 * <ol>
	 * <li>Calculated as follows: (Total Number of MoAs with completed/closed status
	 * since 2011/Total Number MoAs awarded since 2011) * 100</li>
	 * </ol>
	 * 
	 * @param company
	 * @param percentage
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private void percentageOfCompletionOfAllFundedMoA(Company company, int percentage) throws CategorizationException, Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * Check if of learner progress is equal to or greater than n%
	 * <ol>
	 * <li>Calculated as follows: (Total Tranche Payments/Total Contract Value) *
	 * 100</li>
	 * </ol>
	 * 
	 * @param company
	 * @param percentage
	 */
	private void learnerProgressComparedToPercentage(Company company, int percentage) throws CategorizationException, Exception {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Runs Categorization without saving to the DB
	 */
	public void categorizeCompanyWithoutUpdate(Company company) throws Exception {
		CategorizationEnum result = null; 
		try {
			company.setCategorization(null);
			result = doPlatiniumChecksReturnResult(company);
		} catch (CategorizationException pe) {
			try {
				result = doGoldChecksReturnResult(company);
			} catch (CategorizationException ge) {
				try {
					result = doSilverChecksReturnResult(company);
				} catch (CategorizationException se) {
					result = CategorizationEnum.Silver;
				}
			}
		}
		if (result == null) {
			company.setCategorization(CategorizationEnum.Silver);
		} else {
			company.setCategorization(result);
		}
	}
	
	/**
	 * Test of Detailed platinium status checks
	 * 
	 * @param company
	 * @return CategorizationEnum
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private CategorizationEnum doPlatiniumChecksReturnResult(Company company) throws CategorizationException, Exception {
		// Submitted Mandatory Grant Application and has been approved for at least 2  out of a 3 year period or has been approved in all 3 years
		sumittedMandatoryGrantAppApprvovedMin2Times(company);
		// Check if levy paid for the previous financial year
		paidLast12MonthsLevy(company.getLevyNumber());
		// Check if recognition agreement in place if company has 50 or more employees
		if (company.getNumberOfEmployees() >= 50) {
			recognitionAgreementInPlace(company);
		}
		trainingCommitteeChecks(company);
		// Check if percentage of learners that have the 'completed' status any intervention since 2011 is equal to or greater than 80%
		learnersCompletedAnyInterventionSince2011(company, 80, 100);
		// Check if at least 90% of learners have a 'registered' status since 2011 for all MoA (contracts)
		learnersRegsiteredSince2011ForAllMoA(company, 90);
		// Check if percentage of completion of all funded MoA (contracts) is at least 75% since 2011
		percentageOfCompletionOfAllFundedMoA(company, 75);
		// Check if of learner progress is equal to or greater than 60%
		learnerProgressComparedToPercentage(company, 60);
		// This a a Platininum customer
//		company.setCategorization(CategorizationEnum.Platinum);
		return CategorizationEnum.Platinum;
	}
	
	/**
	 * Test of Detailed gold status checks
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private CategorizationEnum doGoldChecksReturnResult(Company company) throws CategorizationException, Exception {
		// Submitted Mandatory Grant Application and has been approved for at least 2 out of a 3 year period or has been approved in all 3 years
		sumittedMandatoryGrantAppApprvovedMin2Times(company);
		// Check if levy paid for the previous financial year
		paidLast12MonthsLevy(company.getLevyNumber());
		// Check if recognition agreement in place if company has 50 or more employees
		if (company.getNumberOfEmployees() >= 50) {
			recognitionAgreementInPlace(company);
		}
		trainingCommitteeChecks(company);
		// Check if percentage of learners that have the 'completed' status any intervention since 2011 is equal to or greater than 60% and less than 79%
		learnersCompletedAnyInterventionSince2011(company, 60, 79);
		// Check if at least 80% of learners have a 'registered' status since 2011 for all MoA (contracts)
		learnersRegsiteredSince2011ForAllMoA(company, 80);
		// Check if percentage of completion of all funded MoA (contracts) is at least 65% since 2011
		percentageOfCompletionOfAllFundedMoA(company, 65);
		// Check if of learner progress is equal to or greater than 60%
		learnerProgressComparedToPercentage(company, 60);
		// This a a Gold customer
		return CategorizationEnum.Gold;
//		company.setCategorization(CategorizationEnum.Gold);
	}
	
	/**
	 * Test of Detailed silver checks
	 * 
	 * @param company
	 * @throws CategorizationException
	 * @throws Exception
	 */
	private CategorizationEnum doSilverChecksReturnResult(Company company) throws CategorizationException, Exception {
		// Check if company has submitted a mandatory grant application
		sumittedMandatoryGrantApplication(company);
		// Check if levy paid for the previous financial year
		paidLast12MonthsLevy(company.getLevyNumber());
		// This a a Silver customer
//		company.setCategorization(CategorizationEnum.Silver);
		return CategorizationEnum.Silver;
	}
}
