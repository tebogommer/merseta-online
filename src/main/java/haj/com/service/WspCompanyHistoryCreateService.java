package haj.com.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import haj.com.dao.BlankDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Employees;
import haj.com.entity.SDFCompany;
import haj.com.entity.TrainingComittee;
import haj.com.entity.Wsp;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.entity.WspCompanyHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.entity.WspSdfCompanyHistory;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;


public class WspCompanyHistoryCreateService extends AbstractService {
	
	private static WspCompanyHistoryCreateService wspCompanyHistoryCreateService;
	public static synchronized WspCompanyHistoryCreateService instance() {
		if (wspCompanyHistoryCreateService == null) {
			wspCompanyHistoryCreateService = new WspCompanyHistoryCreateService();
		}
		return wspCompanyHistoryCreateService;
	}
	
	/** The dao. */
	private BlankDAO dao = new BlankDAO();
	
	/** The Service Levels */
	private CompanyService companyService;
	private EmployeesService employeesService;
	private WspService wspService;
	private TrainingComitteeService trainingComitteeService;
	private SDFCompanyService sdfCompanyService;
	
	public void clearAndPopulateWspCompanyHistory(Company company, String targetClass, Long targetKey){
//		clearExistingEntriesByTargetClassAndKey(targetClass, targetKey);
		populateWspCompanyHistory(company, targetClass, targetKey);
	}
	
	/**
	 * Creates history links to the WSP
	 * Company, target class and target key require
	 * 
	 * @param company
	 * @param targetClass the targetClass
	 * @param targetKey the id of the targetClass
	 */
	public void populateWspCompanyHistory(Company company, String targetClass, Long targetKey) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				Company threadCompany = company;
				String threadTargetClass = targetClass;
				Long threadTargetKey = targetKey;
				
				try {
					if (threadCompany == null || threadCompany.getId() == null ) {
						throw new Exception("company passed can not be null");
					}
					if (threadTargetKey == null) {
						throw new Exception("target key can not be null");
					}
					if (threadTargetClass == null || threadTargetClass.isEmpty()) {
						throw new Exception("target class can not be null or empty");
					}
					
					// populate service levels
					if (companyService == null) {
						companyService = new CompanyService();
					}
					if (wspService == null) {
						wspService = new WspService();
					}
					if (employeesService == null) {
						employeesService = new EmployeesService();
					}
					if (trainingComitteeService == null) {
						trainingComitteeService = new TrainingComitteeService();
					}
					if (sdfCompanyService == null) {
						sdfCompanyService = new SDFCompanyService();
					}
					
					// populate objects and lists
					threadCompany = companyService.findByKey(threadCompany.getId());
					
					// prep create list
					List<IDataEntity> createList = new ArrayList<IDataEntity>();
					
					/* Main Company History Link */
					WspCompanyMainHistory wspCompanyMainHistory = new WspCompanyMainHistory();
					wspCompanyMainHistory.setCompany(threadCompany);
					wspCompanyMainHistory.setTargetClass(threadTargetClass);
					wspCompanyMainHistory.setTargetKey(threadTargetKey);
					createList.add(wspCompanyMainHistory);
					
					/* Address History */
					Address address = null;
					
					// Residential Address
					WspCompanyAddressHistory wspCompanyAddressHistoryResidential = null;
					if (threadCompany.getResidentialAddress() != null && threadCompany.getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(threadCompany.getResidentialAddress().getId());
						wspCompanyAddressHistoryResidential = new WspCompanyAddressHistory();
						org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyAddressHistoryResidential, address);
						wspCompanyAddressHistoryResidential.setId(null);
						wspCompanyAddressHistoryResidential.setOrginalAddressId(address.getId());
						wspCompanyAddressHistoryResidential.setCompany(null);
						wspCompanyAddressHistoryResidential.setTargetClass(threadTargetClass);
						wspCompanyAddressHistoryResidential.setTargetKey(threadTargetKey);
						wspCompanyAddressHistoryResidential.setWspCompanyMainHistory(wspCompanyMainHistory);
						if (address.getCreateDate() != null) {
							wspCompanyAddressHistoryResidential.setOrginalCreateDate(address.getCreateDate());
						}
						createList.add(wspCompanyAddressHistoryResidential);
						address = null;
					}
					
					// Postal Address
					WspCompanyAddressHistory wspCompanyAddressHistoryPostal = null;
					if (threadCompany.getPostalAddress() != null && threadCompany.getPostalAddress().getId() != null) {
						address = AddressService.instance().findByKey(threadCompany.getPostalAddress().getId());
						wspCompanyAddressHistoryPostal = new WspCompanyAddressHistory();
						org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyAddressHistoryPostal, address);
						wspCompanyAddressHistoryPostal.setId(null);
						wspCompanyAddressHistoryPostal.setOrginalAddressId(address.getId());
						wspCompanyAddressHistoryPostal.setCompany(null);
						wspCompanyAddressHistoryPostal.setTargetClass(threadTargetClass);
						wspCompanyAddressHistoryPostal.setTargetKey(threadTargetKey);
						wspCompanyAddressHistoryPostal.setWspCompanyMainHistory(wspCompanyMainHistory);
						if (address.getCreateDate() != null) {
							wspCompanyAddressHistoryPostal.setOrginalCreateDate(address.getCreateDate());
						}
						createList.add(wspCompanyAddressHistoryPostal);
						address = null;
					}
					
					// Registered Address
					WspCompanyAddressHistory wspCompanyAddressHistoryRegistered = null;
					if (threadCompany.getRegisteredAddress() != null && threadCompany.getRegisteredAddress().getId() != null) {
						address = AddressService.instance().findByKey(threadCompany.getRegisteredAddress().getId());
						wspCompanyAddressHistoryRegistered = new WspCompanyAddressHistory();
						org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyAddressHistoryRegistered, address);
						wspCompanyAddressHistoryRegistered.setId(null);
						wspCompanyAddressHistoryRegistered.setOrginalAddressId(address.getId());
						wspCompanyAddressHistoryRegistered.setCompany(null);
						wspCompanyAddressHistoryRegistered.setTargetClass(threadTargetClass);
						wspCompanyAddressHistoryRegistered.setTargetKey(threadTargetKey);
						wspCompanyAddressHistoryRegistered.setWspCompanyMainHistory(wspCompanyMainHistory);
						if (address.getCreateDate() != null) {
							wspCompanyAddressHistoryRegistered.setOrginalCreateDate(address.getCreateDate());
						}
						createList.add(wspCompanyAddressHistoryRegistered);
						address = null;
					}

					/* WSP Company History */
					WspCompanyHistory wspCompanyHistory = new WspCompanyHistory();
					org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyHistory, threadCompany);
					wspCompanyHistory.setId(null);
					wspCompanyHistory.setOrginalCompanyId(threadCompany.getId());
					wspCompanyHistory.setTargetClass(threadTargetClass);
					wspCompanyHistory.setTargetKey(threadTargetKey);
					wspCompanyHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
					
					if (threadCompany.getCreateDate() != null) {
						wspCompanyHistory.setOrginalCreateDate(threadCompany.getCreateDate());
					}
					
					// linking address information
					if (wspCompanyAddressHistoryResidential != null) {
						wspCompanyHistory.setResidentialAddressOrginalAddress(wspCompanyAddressHistoryResidential.getOrginalAddressId());
						wspCompanyHistory.setWspCompanyAddressHistoryResidential(wspCompanyAddressHistoryResidential);
					}
					
					if (wspCompanyAddressHistoryPostal != null) {
						wspCompanyHistory.setPostalAddressOrginalAddress(wspCompanyAddressHistoryPostal.getOrginalAddressId());
						wspCompanyHistory.setWspCompanyAddressHistoryPostal(wspCompanyAddressHistoryPostal);
					}
					
					if (wspCompanyAddressHistoryRegistered != null) {
						wspCompanyHistory.setRegisteredAddressOrginalAddress(wspCompanyAddressHistoryRegistered.getOrginalAddressId());
						wspCompanyHistory.setWspCompanyAddressHistoryRegistered(wspCompanyAddressHistoryRegistered);
					}
					
					createList.add(wspCompanyHistory);
					
					/* Employee History Data */
					List<Employees> employeeList = new ArrayList<>();
					employeeList = employeesService.findEmployeesByCompanyId(threadCompany.getId());
					for (Employees employee : employeeList) {
						
						WspCompanyEmployeesHistory wspCompanyEmployeesHistory = new WspCompanyEmployeesHistory();
						org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyEmployeesHistory, employee);

						wspCompanyEmployeesHistory.setId(null);
						wspCompanyEmployeesHistory.setOrginalEmployeesId(employee.getId());
						wspCompanyEmployeesHistory.setCompanyOriginalId(threadCompany.getId());
						wspCompanyEmployeesHistory.setTargetClass(threadTargetClass);
						wspCompanyEmployeesHistory.setTargetKey(threadTargetKey);
						wspCompanyEmployeesHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
						if (employee.getWsp() != null && employee.getWsp().getId() != null) {
							wspCompanyEmployeesHistory.setWspOriginalId(employee.getWsp().getId());
						}
						if (employee.getCreateDate() != null) {
							wspCompanyEmployeesHistory.setOrginalCreateDate(employee.getCreateDate());
						}
						if (employee.getSite() != null && employee.getSite().getId() != null) {
							wspCompanyEmployeesHistory.setSiteOriginalId(employee.getSite().getId());
						}
						
						createList.add(wspCompanyEmployeesHistory);
						wspCompanyEmployeesHistory = null;
					}
					employeeList = null;
					
					/* Training Committee History */
					List<TrainingComittee> trainingComitteeList = new ArrayList<TrainingComittee>();
					trainingComitteeList = trainingComitteeService.findByCompany(threadCompany);
					for (TrainingComittee trainingComittee : trainingComitteeList) {
						
						WspCompanyTrainingComitteeHistory wspCompanyTrainingComitteeHistory = new WspCompanyTrainingComitteeHistory();
						org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyTrainingComitteeHistory, trainingComittee);
						
						wspCompanyTrainingComitteeHistory.setId(null);
						wspCompanyTrainingComitteeHistory.setOrginalTrainingComitteeId(trainingComittee.getId());
						wspCompanyTrainingComitteeHistory.setTargetClass(threadTargetClass);
						wspCompanyTrainingComitteeHistory.setTargetKey(threadTargetKey);
						wspCompanyTrainingComitteeHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
						
						if (trainingComittee.getCreateDate() != null) {
							wspCompanyTrainingComitteeHistory.setOrginalCreateDate(trainingComittee.getCreateDate());
						}
						if (trainingComittee.getCompany() != null && trainingComittee.getCompany().getId() != null) {
							wspCompanyTrainingComitteeHistory.setCompanyOriginal(trainingComittee.getCompany().getId());
						}
						
						createList.add(wspCompanyTrainingComitteeHistory);
						wspCompanyTrainingComitteeHistory = null;
					}
					trainingComitteeList = null;
					
					/* SDF Company History */
					List<SDFCompany> sdfCompanyList = new ArrayList<SDFCompany>();
					sdfCompanyList = sdfCompanyService.findByCompany(threadCompany);
					for (SDFCompany sdfCompany : sdfCompanyList) {
						
						WspSdfCompanyHistory wspSdfCompanyHistory = new WspSdfCompanyHistory();
						org.apache.commons.beanutils.BeanUtils.copyProperties(wspSdfCompanyHistory, sdfCompany);
						
						wspSdfCompanyHistory.setId(null);
						wspSdfCompanyHistory.setOrginalSDFCompanyId(sdfCompany.getId());
						wspSdfCompanyHistory.setTargetClass(threadTargetClass);
						wspSdfCompanyHistory.setTargetKey(threadTargetKey);
						wspSdfCompanyHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
						
						if (sdfCompany.getCreateDate() != null) {
							wspSdfCompanyHistory.setOrginalCreateDate(sdfCompany.getCreateDate());
						}
						createList.add(wspSdfCompanyHistory);
						wspSdfCompanyHistory = null;
					}
					sdfCompanyList = null;
					
					// create new entries
					if (createList.size() != 0) {
						dao.createBatch(createList, 500);
					}
					
					// clear objects and lists
					threadCompany = null;
					threadTargetClass = null;
					threadTargetKey = null;
					address = null;
					
					wspCompanyMainHistory = null;
					wspCompanyHistory = null;
					wspCompanyAddressHistoryResidential = null;
					wspCompanyAddressHistoryPostal = null;
					wspCompanyAddressHistoryRegistered = null;
					createList = null;

					// for testing
//					GenericUtility.sendMail("email@a.com", "WSP History Complete", "Action complete", null);
				} catch (Exception e) {
					
					logger.fatal(e);
					
					String companyLevyNumber = "Unable To Locate";
					if (threadCompany != null && threadCompany.getLevyNumber() != null) {
						companyLevyNumber = threadCompany.getLevyNumber();
					}
					String targetClass = "Unable To Locate";
					if (threadTargetClass != null && !threadTargetClass.isEmpty()) {
						targetClass = threadTargetClass;
					}
					String targetKey = "Unable To Locate";
					if (threadTargetKey != null) {
						targetKey = threadTargetKey.toString();
					}
					String subject = "ERROR: haj.com.service.WspCompanyHistoryCreateService.populateWspCompanyHistory(Company, String, Long) ";
					String msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> An Expection Has Accured For Company Information History Copy. Levy Number: #LEVY_NUMBER# , Target Class: #TARGET_CLASS# , Target Key: #TARGET_KEY#. </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Error Stacktrace: <br/> #ERROR# </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> NO NOT REPLAY TO AUTOMATED NOTIFICATION </p>";
					String errorMessage = "";
					try {
						errorMessage = e.getMessage();
//						StringWriter sw = new StringWriter();
//						e.printStackTrace(new PrintWriter(sw));
//						errorMessage = sw.toString();
					} catch (Exception e2) {
						errorMessage = e.getMessage();
					}
					msg = msg.replaceAll("#LEVY_NUMBER#", companyLevyNumber);
					msg = msg.replaceAll("#TARGET_CLASS#", targetClass);
					msg = msg.replaceAll("#TARGET_KEY#", targetKey);
					msg = msg.replaceAll("#ERROR#", errorMessage);
					
					GenericUtility.mailError(subject, msg);
					
					// for testing 
//					GenericUtility.sendMail("test.email@a.com", subject, msg, null);
				}
			}
		}).start();
	}
	
	/* SHOULD NOT BE USED */
	public void clearExistingEntriesByTargetClassAndKey(String targetClass, Long targetKey) {
		try {
			// WSP Company History
			WspCompanyHistoryService.instance().deleteEntriesByTargetKeyAndClass(targetClass, targetKey);
			// WSP Company Address History
			WspCompanyAddressHistoryService.instance().deleteEntriesByTargetKeyAndClass(targetClass, targetKey);
			// WSP Company Employees History
			WspCompanyEmployeesHistoryService.instance().deleteEntriesByTargetKeyAndClass(targetClass, targetKey);
			// WSP Company Training Committee History
			WspCompanyTrainingComitteeHistoryService.instance().deleteEntriesByTargetKeyAndClass(targetClass, targetKey);
			// WSP SDF Company History
			WspSdfCompanyHistoryService.instance().deleteEntriesByTargetKeyAndClass(targetClass, targetKey);
		} catch (Exception e) {
			logger.fatal(e);
			String targetClassMsg = "Unable To Locate";
			if (targetClass != null && !targetClass.isEmpty()) {
				targetClassMsg = targetClass;
			}
			String targetKeyMsg = "Unable To Locate";
			if (targetKey != null) {
				targetKeyMsg = targetKey.toString();
			}
			String subject = "ERROR: haj.com.service.WspCompanyHistoryCreateService.clearExistingEntriesByTargetClassAndKey(String, Long) ";
			String msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> An Expection Has Accured For Clearning Company Histroy By Target Class and Targey Key. Target Class: #TARGET_CLASS#, Target Key: #TARGET_KEY#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Error Stacktrace: <br/> #ERROR# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> NO NOT REPLAY TO AUTOMATED NOTIFICATION </p>";
			msg = msg.replaceAll("#TARGET_CLASS#", targetClassMsg);
			msg = msg.replaceAll("#TARGET_KEY#", targetKeyMsg);
			msg = msg.replaceAll("#ERROR#", e.getMessage());
			GenericUtility.mailError(subject, msg);
		}
	}
	
	// this is a one time fix, not entented to be run again
	public void populateWspCompanyHistoryForAllWsp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (wspService == null) {
						wspService = new WspService();
					}
					int finYear = 2020;
					List<Wsp> allThisYearWsp = wspService.findByFinancialYear(finYear);
					System.out.println(" Total WSP: "+allThisYearWsp.size());
					String errors = "";
					List<IDataEntity> createList = new ArrayList<IDataEntity>();
					Integer wspHistoryCreateCount = 0;
					for (Wsp wsp : allThisYearWsp) {
						if (wsp.getCompany() != null && wsp.getCompany().getId() != null) {
							List<IDataEntity> tempCreateList = new ArrayList<IDataEntity>();
							Company threadCompany = wsp.getCompany();
							String threadTargetClass = wsp.getClass().getName();
							Long threadTargetKey = wsp.getId();
							try {
								// populate service levels
								if (companyService == null) {
									companyService = new CompanyService();
								}
								if (wspService == null) {
									wspService = new WspService();
								}
								if (employeesService == null) {
									employeesService = new EmployeesService();
								}
								if (trainingComitteeService == null) {
									trainingComitteeService = new TrainingComitteeService();
								}
								if (sdfCompanyService == null) {
									sdfCompanyService = new SDFCompanyService();
								}
								
								// Validations
								if (threadCompany == null || threadCompany.getId() == null ) {
									throw new Exception("company passed can not be null");
								}
								if (threadTargetKey == null) {
									throw new Exception("target key can not be null");
								}
								if (threadTargetClass == null || threadTargetClass.isEmpty()) {
									throw new Exception("target class can not be null or empty");
								}
								// populate objects and lists
								threadCompany = companyService.findByKey(threadCompany.getId());
								
								WspCompanyMainHistory wspCompanyMainHistory = new WspCompanyMainHistory();
								wspCompanyMainHistory.setCompany(threadCompany);
								wspCompanyMainHistory.setTargetClass(threadTargetClass);
								wspCompanyMainHistory.setTargetKey(threadTargetKey);
								tempCreateList.add(wspCompanyMainHistory);
								
								/* Address History */
								Address address = null;
								
								// Residential Address
								WspCompanyAddressHistory wspCompanyAddressHistoryResidential = null;
								if (threadCompany.getResidentialAddress() != null && threadCompany.getResidentialAddress().getId() != null) {
									address = AddressService.instance().findByKey(threadCompany.getResidentialAddress().getId());
									wspCompanyAddressHistoryResidential = new WspCompanyAddressHistory();
									org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyAddressHistoryResidential, address);
									wspCompanyAddressHistoryResidential.setId(null);
									wspCompanyAddressHistoryResidential.setOrginalAddressId(address.getId());
									wspCompanyAddressHistoryResidential.setCompany(null);
									wspCompanyAddressHistoryResidential.setTargetClass(threadTargetClass);
									wspCompanyAddressHistoryResidential.setTargetKey(threadTargetKey);
									wspCompanyAddressHistoryResidential.setWspCompanyMainHistory(wspCompanyMainHistory);
									if (address.getCreateDate() != null) {
										wspCompanyAddressHistoryResidential.setOrginalCreateDate(address.getCreateDate());
									}
									tempCreateList.add(wspCompanyAddressHistoryResidential);
									address = null;
								}
								
								// Postal Address
								WspCompanyAddressHistory wspCompanyAddressHistoryPostal = null;
								if (threadCompany.getPostalAddress() != null && threadCompany.getPostalAddress().getId() != null) {
									address = AddressService.instance().findByKey(threadCompany.getPostalAddress().getId());
									wspCompanyAddressHistoryPostal = new WspCompanyAddressHistory();
									org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyAddressHistoryPostal, address);
									wspCompanyAddressHistoryPostal.setId(null);
									wspCompanyAddressHistoryPostal.setOrginalAddressId(address.getId());
									wspCompanyAddressHistoryPostal.setCompany(null);
									wspCompanyAddressHistoryPostal.setTargetClass(threadTargetClass);
									wspCompanyAddressHistoryPostal.setTargetKey(threadTargetKey);
									wspCompanyAddressHistoryPostal.setWspCompanyMainHistory(wspCompanyMainHistory);
									if (address.getCreateDate() != null) {
										wspCompanyAddressHistoryPostal.setOrginalCreateDate(address.getCreateDate());
									}
									tempCreateList.add(wspCompanyAddressHistoryPostal);
									address = null;
								}
								
								// Registered Address
								WspCompanyAddressHistory wspCompanyAddressHistoryRegistered = null;
								if (threadCompany.getRegisteredAddress() != null && threadCompany.getRegisteredAddress().getId() != null) {
									address = AddressService.instance().findByKey(threadCompany.getRegisteredAddress().getId());
									wspCompanyAddressHistoryRegistered = new WspCompanyAddressHistory();
									org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyAddressHistoryRegistered, address);
									wspCompanyAddressHistoryRegistered.setId(null);
									wspCompanyAddressHistoryRegistered.setOrginalAddressId(address.getId());
									wspCompanyAddressHistoryRegistered.setCompany(null);
									wspCompanyAddressHistoryRegistered.setTargetClass(threadTargetClass);
									wspCompanyAddressHistoryRegistered.setTargetKey(threadTargetKey);
									wspCompanyAddressHistoryRegistered.setWspCompanyMainHistory(wspCompanyMainHistory);
									if (address.getCreateDate() != null) {
										wspCompanyAddressHistoryRegistered.setOrginalCreateDate(address.getCreateDate());
									}
									tempCreateList.add(wspCompanyAddressHistoryRegistered);
									address = null;
								}

								/* WSP Company History */
								WspCompanyHistory wspCompanyHistory = new WspCompanyHistory();
								org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyHistory, threadCompany);
								wspCompanyHistory.setId(null);
								wspCompanyHistory.setOrginalCompanyId(threadCompany.getId());
								wspCompanyHistory.setTargetClass(threadTargetClass);
								wspCompanyHistory.setTargetKey(threadTargetKey);
								wspCompanyHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
								
								if (threadCompany.getCreateDate() != null) {
									wspCompanyHistory.setOrginalCreateDate(threadCompany.getCreateDate());
								}
								
								// linking address information
								if (wspCompanyAddressHistoryResidential != null) {
									wspCompanyHistory.setResidentialAddressOrginalAddress(wspCompanyAddressHistoryResidential.getOrginalAddressId());
									wspCompanyHistory.setWspCompanyAddressHistoryResidential(wspCompanyAddressHistoryResidential);
								}
								
								if (wspCompanyAddressHistoryPostal != null) {
									wspCompanyHistory.setPostalAddressOrginalAddress(wspCompanyAddressHistoryPostal.getOrginalAddressId());
									wspCompanyHistory.setWspCompanyAddressHistoryPostal(wspCompanyAddressHistoryPostal);
								}
								
								if (wspCompanyAddressHistoryRegistered != null) {
									wspCompanyHistory.setRegisteredAddressOrginalAddress(wspCompanyAddressHistoryRegistered.getOrginalAddressId());
									wspCompanyHistory.setWspCompanyAddressHistoryRegistered(wspCompanyAddressHistoryRegistered);
								}
								
								tempCreateList.add(wspCompanyHistory);
								
								/* Employee History Data */
								List<Employees> employeeList = new ArrayList<>();
								employeeList = employeesService.findEmployeesByCompanyId(threadCompany.getId());
								for (Employees employee : employeeList) {
									
									WspCompanyEmployeesHistory wspCompanyEmployeesHistory = new WspCompanyEmployeesHistory();
									org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyEmployeesHistory, employee);

									wspCompanyEmployeesHistory.setId(null);
									wspCompanyEmployeesHistory.setOrginalEmployeesId(employee.getId());
									wspCompanyEmployeesHistory.setCompanyOriginalId(threadCompany.getId());
									wspCompanyEmployeesHistory.setTargetClass(threadTargetClass);
									wspCompanyEmployeesHistory.setTargetKey(threadTargetKey);
									wspCompanyEmployeesHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
									if (employee.getWsp() != null && employee.getWsp().getId() != null) {
										wspCompanyEmployeesHistory.setWspOriginalId(employee.getWsp().getId());
									}
									if (employee.getCreateDate() != null) {
										wspCompanyEmployeesHistory.setOrginalCreateDate(employee.getCreateDate());
									}
									if (employee.getSite() != null && employee.getSite().getId() != null) {
										wspCompanyEmployeesHistory.setSiteOriginalId(employee.getSite().getId());
									}
									
									tempCreateList.add(wspCompanyEmployeesHistory);
									wspCompanyEmployeesHistory = null;
								}
								employeeList = null;
								
								/* Training Committee History */
								List<TrainingComittee> trainingComitteeList = new ArrayList<TrainingComittee>();
								trainingComitteeList = trainingComitteeService.findByCompany(threadCompany);
								for (TrainingComittee trainingComittee : trainingComitteeList) {
									
									WspCompanyTrainingComitteeHistory wspCompanyTrainingComitteeHistory = new WspCompanyTrainingComitteeHistory();
									org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyTrainingComitteeHistory, trainingComittee);
									
									wspCompanyTrainingComitteeHistory.setId(null);
									wspCompanyTrainingComitteeHistory.setOrginalTrainingComitteeId(trainingComittee.getId());
									wspCompanyTrainingComitteeHistory.setTargetClass(threadTargetClass);
									wspCompanyTrainingComitteeHistory.setTargetKey(threadTargetKey);
									wspCompanyTrainingComitteeHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
									
									if (trainingComittee.getCreateDate() != null) {
										wspCompanyTrainingComitteeHistory.setOrginalCreateDate(trainingComittee.getCreateDate());
									}
									if (trainingComittee.getCompany() != null && trainingComittee.getCompany().getId() != null) {
										wspCompanyTrainingComitteeHistory.setCompanyOriginal(trainingComittee.getCompany().getId());
									}
									
									tempCreateList.add(wspCompanyTrainingComitteeHistory);
									wspCompanyTrainingComitteeHistory = null;
								}
								trainingComitteeList = null;
								
								/* SDF Company History */
								List<SDFCompany> sdfCompanyList = new ArrayList<SDFCompany>();
								sdfCompanyList = sdfCompanyService.findByCompany(threadCompany);
								for (SDFCompany sdfCompany : sdfCompanyList) {
									
									WspSdfCompanyHistory wspSdfCompanyHistory = new WspSdfCompanyHistory();
									org.apache.commons.beanutils.BeanUtils.copyProperties(wspSdfCompanyHistory, sdfCompany);
									
									wspSdfCompanyHistory.setId(null);
									wspSdfCompanyHistory.setOrginalSDFCompanyId(sdfCompany.getId());
									wspSdfCompanyHistory.setTargetClass(threadTargetClass);
									wspSdfCompanyHistory.setTargetKey(threadTargetKey);
									wspSdfCompanyHistory.setWspCompanyMainHistory(wspCompanyMainHistory);
									
									if (sdfCompany.getCreateDate() != null) {
										wspSdfCompanyHistory.setOrginalCreateDate(sdfCompany.getCreateDate());
									}
									tempCreateList.add(wspSdfCompanyHistory);
									wspSdfCompanyHistory = null;
								}
								sdfCompanyList = null;
								
								
								if (tempCreateList.size() != 0) {
									wspHistoryCreateCount++;
									System.out.println(" wspHistoryCreateCount: "+wspHistoryCreateCount.toString());
									createList.addAll(tempCreateList);
								}
							} catch (Exception e) {
								errors += "Target Key: "+ threadTargetKey.toString() + ". Error: " + e.getMessage() + "<br/>";
							}
						}
					}
					
					if (createList.size() != 0) {
						dao.createBatch(createList, 1000);
					}
					
					String subject = "Wsp Company History Creation Completed";
					String message = "";
					if (errors.equals("")) {
						// no errors
						subject += " With No Errors";
						message += "Process complete with no errors. <br/> Main company entries created: #ENTRIES_CREATED#.";
					} else {
						// errors
						subject += " With Errors";
						message += "Process complete with errors. <br/> Main company entries created: #ENTRIES_CREATED#. <br/> Errors in generation process: <br/> #ERRORS#";
					}
					// wspHistoryCreateCount
					// errors
					message = message.replaceAll("#ENTRIES_CREATED#", wspHistoryCreateCount.toString());
					message = message.replaceAll("#ERRORS#", errors);
					GenericUtility.mailError(subject, message);
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On Company HistoryPopulation By Wsp", "<p>Error On Company HistoryPopulation By Wsp.</p><p> Stack Trace:<p/><p> "  +exceptionAsString+ "</p>");
				}
			}
		}).start();
	}
	
}