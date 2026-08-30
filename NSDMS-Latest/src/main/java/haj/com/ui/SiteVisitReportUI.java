package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.Signoff;
import haj.com.entity.SiteVisit;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.SiteVisitReportDispute;
import haj.com.entity.SiteVisitReportSME;
import haj.com.entity.Sites;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.YesNoLookup;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.SiteVisitReportStatusEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SignoffService;
import haj.com.service.SiteVisitReportDisputeService;
import haj.com.service.SiteVisitReportSMEService;
import haj.com.service.SiteVisitReportService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.validators.CheckID;

@ManagedBean(name = "sitevisitreportUI")
@ViewScoped
public class SiteVisitReportUI extends AbstractUI {

	private SiteVisitReportService service = new SiteVisitReportService();
	private CompanyService companyService = new CompanyService();
	private List<SiteVisitReport> sitevisitreportfilteredList = null;
	private SiteVisitReport sitevisitreport = new SiteVisitReport();
	private Signoff signoffSelected;
	private LazyDataModel<Company> dataModel;
	private List<WorkPlaceApproval> workPlaceApprovalList = new ArrayList<>();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	private List<Sites> sitesList;
	private SitesService sitesService = new SitesService();
	private Sites site = new Sites();
	private Doc doc;
	private List<RejectReasons> rejectReasons = new ArrayList<>();
	private List<RejectReasons> selectedRejectReason;
	private List<RejectReasonsChild> rejectReasonsChild;
	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();
	private RegionTown rt;
	private Date maxDate = new Date();
	private SiteVisitReportSMEService siteVisitReportSMEService = new SiteVisitReportSMEService();
	private SiteVisitReportSME sme;
	private YesNoLookupService yesNoLookupService = new YesNoLookupService();
	private List<YesNoLookup> yesNoList = new ArrayList<>();
	private boolean displayCalendarEquipmentOne;
	private boolean displayCalendarEquipmentTwo;
	private boolean displayCalendarEquipmentThree;
	private boolean displayCalendarEquipmentFour;
	private boolean displayCalendarEquipmentFive;
	private boolean displayCalendarRecordOne;
	private boolean displayCalendarRecordTwo;
	private boolean displayCalendarSafetyOne;
	private boolean displayCalendarSafetyTwo;
	private boolean displayCalendarSafetyThree;
	private Boolean crmCloUser;
	private List<SiteVisitReport> siteVisitReportList = new ArrayList<>();
	private Company company;
	private Boolean sitevisitreportSelected;
	private Boolean siteVisitReportCreateClicked;
	private Boolean companyHasSites;
	private List<SiteVisitReportDispute> disputes = new ArrayList<>();
	private SiteVisitReportDispute dispute;
	private SiteVisitReportDisputeService siteVisitReportDisputeService = new SiteVisitReportDisputeService();
	private boolean disableAll = false;
	private Signoff signoff;
	private SignoffService signoffService = new SignoffService();
	// private SiteVisitReportDisputeService disputeService =
	// SiteVisitReportDisputeService();

	/** The search user passport or id UI. */
	/** The sme user. */
	private Users smeUser;
	private UsersService usersService = null;
	private IdPassportEnum idpassport;
	@CheckID(message = "Not a valid RSA ID number")
	private String idnumber;
	public Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;
	public Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;
	public String passportNumberFormat = HAJConstants.passportNumberFormat;
	private String passportNumber;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all SiteVisitReport and prepare a for a create
	 * of a new SiteVisitReport
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		signoff = new Signoff();
		if (getSessionUI().getTask() != null
				&& getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SITE_VISIT_REPORT) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			sitevisitreport = service.findByKey(getSessionUI().getTask().getTargetKey());
			rejectReasonsChild = rejectReasonsService.findByTargetClassAndKey(SiteVisitReport.class.getName(),
					sitevisitreport.getId());
			rt = RegionTownService.instance()
					.findByTown(sitevisitreport.getCompany().getResidentialAddress().getTown());
			yesNoSelected();
			Company theResolvedCompany = companyService.findByKey(sitevisitreport.getCompany().getId());
			this.setCompany(theResolvedCompany);
			sitevisitreport.setCompany(theResolvedCompany);
			companySelected();
			siteVisitReportSelected();
			if (rt.getClo().getUsers().getId() == getSessionUI().getActiveUser().getId()
					&& SiteVisitReportStatusEnum.Rejected == sitevisitreport.getSiteVisitReportStatusEnum()) {
				disableAll = false;
			} else {
				disableAll = true;
			}
			prepareNewDispute();
			companyHasSites = false;
			rt = RegionTownService.instance().findByTown(getCompany().getResidentialAddress().getTown());
			getSme().setSiteName(rt.getTown().getDescription());
			prepareNewSME();
		} else {
			prepareNewSmeEntry();
			sitevisitreportInfo();
			identifyUserLevel();
			disableAll = false;
			if (getCompany() != null) {
				rt = RegionTownService.instance().findByTown(getCompany().getResidentialAddress().getTown());
			}
		}
	}

	/**
	 * Identifys if the user is either: false - general user true - Clo or CRM
	 * 
	 * @throws Exception
	 */
	private void identifyUserLevel() throws Exception {
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
		// no client users should be able to view DG Verifications
		if (hce == null) {
			super.redirectToDashboard();
		} else {
			crmCloUser = RegionTownService.instance().checkIfCrmCloUser(hce);
		}
	}

	/**
	 * 
	 */
	private void prepareNewGPS() {

		String gpsDetails = "";

		try {
			gpsDetails = getSessionUI().getLatitude() != 0 && getSessionUI().getLongitude() != 0
					? getSessionUI().getLatitude() + " , " + getSessionUI().getLongitude() : "No Location Details";

			// logger.info("gpsCoOrdinates : " + gpsDetails);

			sitevisitreport.setGpsDetails(gpsDetails);

			// try {
			// Address address =
			// GeoCoderUtil.getLocationInfoAndSaveForConsumer(getSessionUI().getLatitude(),
			// getSessionUI().getLongitude(), getSessionUI().getActiveUser());
			// addressService.create(address);
			// survey.setAddress(address);
			// if (!gpsCoOrdinates.equals("No Location Details")) {
			// this.gpsAddress = address.getFommattedAddress() + " (" +
			// gpsCoOrdinates +
			// ")";
			// } else {
			// this.gpsAddress = gpsCoOrdinates;
			// }
			// } catch (Exception exception) {
			// this.gpsAddress = gpsCoOrdinates;
			// }

		} catch (Exception exception) {

			gpsDetails = "No Location Details";
		}

	}

	/**
	 * 
	 */
	private void populateMaxDateSelection() {
		maxDate = new Date();
	}

	/**
	 * 
	 */
	private void populatesDropDowns() {

		try {

			if (this.getCompany() != null) {
				try {
					siteVisitReportList = service.allSiteVisitReportByCompany(this.getCompany());
					workPlaceApprovalList = workPlaceApprovalService.findByCompany(this.getCompany());
					service.populateSignOffs(sitevisitreport);
				} catch (Exception e) {
					this.addErrorMessage(e.getMessage(), e);
				}
			}

			yesNoList = yesNoLookupService.allYesNoLookup();

			populateMaxDateSelection();

		} catch (Exception e) {
			this.addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	public void companySelected() {

		siteVisitReportCreateClicked = false;
		sitevisitreportSelected = false;
		try {
			if (this.getCompany() != null) {
				siteVisitReportList = service.allSiteVisitReportByCompany(this.getCompany());
				sitesList = sitesService.findByCompany(getCompany());
				if (sitesList.size() > 0) {
					companyHasSites = true;
				} else {
					companyHasSites = false;
					rt = RegionTownService.instance().findByTown(getCompany().getResidentialAddress().getTown());
					// System.out.println(rt.getTown().getDescription());
					if (sme != null) {
						getSme().setSiteName(rt.getTown().getDescription());
					}
				}
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage(), e);

		}
	}

	public void siteVisitReportSelected() {

		sitevisitreportSelected = true;
		siteVisitReportCreateClicked = false;
		populatesDropDowns();
		try {
			sitevisitreport = service.populateInformation(sitevisitreport);
			sitevisitreport.setSiteVisitReportSMEs(siteVisitReportSMEService.findBySiteVisitReport(sitevisitreport));
			// service.prepareNewDocs(sitevisitreport,
			// ConfigDocProcessEnum.SITE_VISIT_REPORT,
			// CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage(), e);
		}
		smeInsert();
		yesNoSelected();

	}

	/**
	 * This is the view for when a user wants to view the report All must be
	 * disabled
	 */
	public void viewSiteVisitReport() {
		try {
			sitevisitreportSelected = true;
			siteVisitReportCreateClicked = false;
			populatesDropDowns();
			sitevisitreport = service.populateInformation(sitevisitreport);
			sitevisitreport.setSiteVisitReportSMEs(siteVisitReportSMEService.findBySiteVisitReport(sitevisitreport));
			yesNoSelected();
			disableAll = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * @throws Exception
	 */
	public void createSiteVisitReport() throws Exception {
		// System.out.println("SITE VISIT REPORT CREATE CALLED !");
		sitevisitreport = new SiteVisitReport();
		sitevisitreportSelected = false;
		disableAll = false;
		siteVisitReportCreateClicked = true;
		smeInsertOnCreate();
		sitevisitreport.setCompany(this.getCompany());
		prepareNewSmeEntry();
		populatesDropDowns();
		// prepareNewSME();
	}

	/**
	 * Preps new instance of SME user for selection
	 * 
	 * @throws Exception
	 */
	public void prepNewSmeUser() throws Exception {
		// getSearchUserPassportOrIdUI().setObject(this);
		this.idpassport = IdPassportEnum.RsaId;
		smeUser = new Users();
		smeUser.setDoneSearch(false);
		idnumber = "";
		passportNumber = "";
	}

	public void searchForUser() {
		try {
			if (usersService == null) {
				usersService = new UsersService();
			}
			smeUser = new Users();
			switch (idpassport) {
			case RsaId:
				smeUser = usersService.findByRsaIdJoinAddress(this.idnumber);
				break;
			case Passport:
				smeUser = usersService.findByPassportNumberJoinAddress(this.passportNumber);
				break;
			default:
				smeUser = new Users();
				break;
			}
			usersService = null;
			if (smeUser != null && smeUser.getId() != null) {
				sme.setIdentityNumber(smeUser.getRsaIDNumber());
				sme.setPassport(smeUser.getPassportNumber());
				sme.setFirstName(smeUser.getFirstName());
				sme.setLastName(smeUser.getLastName());
				sme.setFullNames(smeUser.getFirstName() + " " + smeUser.getLastName());
			} else {
				smeUser = new Users();
				sme.setIdentityNumber(this.idnumber);
				sme.setPassport(this.passportNumber);
			}
			smeUser.setDoneSearch(true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Done user bit.
	 */
	public void doneUserBit() {
		this.smeUser.setRegFieldsDone(true);
	}

	/**
	 * @param desc
	 * @return List<Company>
	 */
	public List<Company> completeCompany(String desc) {
		CompanyService equityService = new CompanyService();
		List<Company> l = null;
		try {
			l = equityService.findByNameOrLevyNum(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * 
	 */
	public void prepareNewSME() {
		try {
			sme = new SiteVisitReportSME();
			if (companyHasSites != null && companyHasSites == false) {
				rt = RegionTownService.instance().findByTown(getCompany().getResidentialAddress().getTown());
				sme.setSiteName(rt.getTown().getDescription());
				if (sitevisitreport != null && sitevisitreport.getId() != null) {
					sme.setSiteVisitReport(sitevisitreport);
				}

				sme.setUser(getSessionUI().getActiveUser());
				if (sitevisitreport.getSite() != null) {
					sme.setSite(sitevisitreport.getSite());
				}

			}
			prepNewSmeUser();
			// service.prepareNewDocs(sitevisitreport);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	public void smeInsert() {

		boolean error = false;
		if (sitevisitreport != null) {
			if (sitevisitreport.getSiteVisitReportSMEs() == null) {
				sitevisitreport.setSiteVisitReportSMEs(new ArrayList<>());
			}
			if (sme == null) {
				sme = new SiteVisitReportSME();
			} else {
				if (sme.getSiteName() == null || sme.getSiteName().equals("")) {
					this.addErrorMessage("Provide: Site Name Before Proceeding");
					error = true;
				}
				// if(sme.getFullNames() == null ||
				// sme.getFullNames().equals("")){
				// this.addErrorMessage("Please supply the full names");
				// error = true;
				// }
				if (sme.getFirstName() == null || sme.getFirstName().equals("")) {
					this.addErrorMessage("Provide: First Name Before Proceeding");
					error = true;
				}
				if (sme.getLastName() == null || sme.getLastName().equals("")) {
					this.addErrorMessage("Provide: Last Name Before Proceeding");
					error = true;
				}
				// if(sme.getIdentityNumber() == null && sme.getPassport() ==
				// null){
				// this.addErrorMessage("Please supply either a valid SA ID
				// number or a valid passport number");
				// error = true;
				// }
				// else if(sme.getIdentityNumber() != null && sme.getPassport()
				// == null){
				// if(sme.getIdentityNumber().length() > 15){
				// this.addErrorMessage("Invalid RSA ID number");
				// error = true;
				// }
				// }
				// else if(sme.getIdentityNumber() == null && sme.getPassport()
				// != null){
				// if(sme.getPassport().length() > 20){
				// this.addErrorMessage("A valid passport number cant exceed 20
				// characters");
				// error = true;
				// }
				// }
				if (sme.getQualification() == null && sme.getTrade() == null) {
					this.addErrorMessage("Provide Either: Qualification And / Or Trade Before Proceeding");
					error = true;
				}
				// if no errors have accrued do final validation check
				if (!error) {
					boolean qualificationProvided = false;
					boolean tradeProvided = false;
					boolean usedRsaIdNumber = false;
					if (sme.getQualification() != null) {
						qualificationProvided = true;
					}
					if (sme.getTrade() != null) {
						tradeProvided = true;
					}
					if (sme.getIdentityNumber() != null && !sme.getIdentityNumber().equals("")) {
						usedRsaIdNumber = true;
					} else {
						usedRsaIdNumber = false;
					}
					// check to ensure no duplicate data has been added
					for (SiteVisitReportSME smePreviouslyAdded : sitevisitreport.getSiteVisitReportSMEs()) {
						if (usedRsaIdNumber) {
							if ((smePreviouslyAdded.getIdentityNumber() != null
									&& smePreviouslyAdded.getIdentityNumber() != "")
									&& smePreviouslyAdded.getIdentityNumber().trim()
											.equals(sme.getIdentityNumber().trim())) {
								// check if qualification
								if (qualificationProvided) {
									if (smePreviouslyAdded.getQualification() != null && smePreviouslyAdded
											.getQualification().getId().equals(sme.getQualification().getId())) {
										this.addErrorMessage("Duplicate Qualification Added For RSA ID Number: "
												+ sme.getIdentityNumber()
												+ ". Provide A Different Qualification Before Proceeding.");
										error = true;
									}
								}
								// check if trade provided
								if (tradeProvided) {
									if (smePreviouslyAdded.getTrade() != null
											&& smePreviouslyAdded.getTrade().getId().equals(sme.getTrade().getId())) {
										this.addErrorMessage(
												"Duplicate Trade Provided For RSA ID Number: " + sme.getIdentityNumber()
														+ ". Provide A Different Trade Before Proceeding.");
										error = true;
									}
								}
							}
						} else {
							if ((smePreviouslyAdded.getPassport() != null && smePreviouslyAdded.getPassport() != "")
									&& smePreviouslyAdded.getPassport().trim().toLowerCase()
											.equals(sme.getPassport().trim().toLowerCase())) {
								// check if qualification
								if (qualificationProvided) {
									if (smePreviouslyAdded.getQualification() != null && smePreviouslyAdded
											.getQualification().getId().equals(sme.getQualification().getId())) {
										this.addErrorMessage("Duplicate Qualification Added For Passport Number: "
												+ sme.getPassport()
												+ ". Provide A Different Qualification Before Proceeding.");
										error = true;
									}
								}
								// check if trade provided
								if (tradeProvided) {
									if (smePreviouslyAdded.getTrade() != null
											&& smePreviouslyAdded.getTrade().getId().equals(sme.getTrade().getId())) {
										this.addErrorMessage("Duplicate Trade Provided For Passport Number: "
												+ sme.getPassport() + ". Provide A Different Trade Before Proceeding.");
										error = true;
									}
								}
							}
						}
						if (error) {
							break;
						}
					}
				}
				if (error == false) {
					sme.setFullNames(sme.getFirstName() + " " + sme.getLastName());
					sitevisitreport.getSiteVisitReportSMEs().add(sme);
					addInfoMessage("User Added");
					prepareNewSME();
				}
			}
		}
	}

	public void createSmeEntry() {
		boolean error = false;
		if (sitevisitreport != null) {
			if (sitevisitreport.getSiteVisitReportSMEs() == null) {
				sitevisitreport.setSiteVisitReportSMEs(new ArrayList<>());
			}
			if (sme == null) {
				sme = new SiteVisitReportSME();
			} else {
				if (sme.getSiteName() == null || sme.getSiteName().equals("")) {
					this.addErrorMessage("Provide: Site Name Before Proceeding");
					error = true;
				}
				// if(sme.getFullNames() == null ||
				// sme.getFullNames().equals("")){
				// this.addErrorMessage("Please supply the full names");
				// error = true;
				// }
				if (sme.getFirstName() == null || sme.getFirstName().equals("")) {
					this.addErrorMessage("Provide: First Name Before Proceeding");
					error = true;
				}
				if (sme.getLastName() == null || sme.getLastName().equals("")) {
					this.addErrorMessage("Provide: Last Name Before Proceeding");
					error = true;
				}
				// if(sme.getIdentityNumber() == null && sme.getPassport() ==
				// null){
				// this.addErrorMessage("Please supply either a valid SA ID
				// number or a valid passport number");
				// error = true;
				// }
				// else if(sme.getIdentityNumber() != null && sme.getPassport()
				// == null){
				// if(sme.getIdentityNumber().length() > 15){
				// this.addErrorMessage("Invalid RSA ID number");
				// error = true;
				// }
				// }
				// else if(sme.getIdentityNumber() == null && sme.getPassport()
				// != null){
				// if(sme.getPassport().length() > 20){
				// this.addErrorMessage("A valid passport number cant exceed 20
				// characters");
				// error = true;
				// }
				// }
				if (sme.getQualification() == null && sme.getTrade() == null) {
					this.addErrorMessage("Provide Either: Qualification And / Or Trade Before Proceeding");
					error = true;
				}
				// if no errors have accrued do final validation check
				if (!error) {
					boolean qualificationProvided = false;
					boolean tradeProvided = false;
					boolean usedRsaIdNumber = false;
					if (sme.getQualification() != null) {
						qualificationProvided = true;
					}
					if (sme.getTrade() != null) {
						tradeProvided = true;
					}
					if (sme.getIdentityNumber() != null && !sme.getIdentityNumber().equals("")) {
						usedRsaIdNumber = true;
					} else {
						usedRsaIdNumber = false;
					}
					// check to ensure no duplicate data has been added
					for (SiteVisitReportSME smePreviouslyAdded : sitevisitreport.getSiteVisitReportSMEs()) {
						if (usedRsaIdNumber) {
							if ((smePreviouslyAdded.getIdentityNumber() != null
									&& smePreviouslyAdded.getIdentityNumber() != "")
									&& smePreviouslyAdded.getIdentityNumber().trim()
											.equals(sme.getIdentityNumber().trim())) {
								// check if qualification
								if (qualificationProvided) {
									if (smePreviouslyAdded.getQualification() != null && smePreviouslyAdded
											.getQualification().getId().equals(sme.getQualification().getId())) {
										this.addErrorMessage("Duplicate Qualification Added For RSA ID Number: "
												+ sme.getIdentityNumber()
												+ ". Provide A Different Qualification Before Proceeding.");
										error = true;
									}
								}
								// check if trade provided
								if (tradeProvided) {
									if (smePreviouslyAdded.getTrade() != null
											&& smePreviouslyAdded.getTrade().getId().equals(sme.getTrade().getId())) {
										this.addErrorMessage(
												"Duplicate Trade Provided For RSA ID Number: " + sme.getIdentityNumber()
														+ ". Provide A Different Trade Before Proceeding.");
										error = true;
									}
								}
							}
						} else {
							if ((smePreviouslyAdded.getPassport() != null && smePreviouslyAdded.getPassport() != "")
									&& smePreviouslyAdded.getPassport().trim().toLowerCase()
											.equals(sme.getPassport().trim().toLowerCase())) {
								// check if qualification
								if (qualificationProvided) {
									if (smePreviouslyAdded.getQualification() != null && smePreviouslyAdded
											.getQualification().getId().equals(sme.getQualification().getId())) {
										this.addErrorMessage("Duplicate Qualification Added For Passport Number: "
												+ sme.getPassport()
												+ ". Provide A Different Qualification Before Proceeding.");
										error = true;
									}
								}
								// check if trade provided
								if (tradeProvided) {
									if (smePreviouslyAdded.getTrade() != null
											&& smePreviouslyAdded.getTrade().getId().equals(sme.getTrade().getId())) {
										this.addErrorMessage("Duplicate Trade Provided For Passport Number: "
												+ sme.getPassport() + ". Provide A Different Trade Before Proceeding.");
										error = true;
									}
								}
							}
						}
						if (error) {
							break;
						}
					}
				}
				if (error == false) {
					try {
						siteVisitReportSMEService.create(sme);
						sitevisitreport.setSiteVisitReportSMEs(
								siteVisitReportSMEService.findBySiteVisitReport(sitevisitreport));
						addInfoMessage("User Succusfully Added");
						prepareNewSME();
					} catch (Exception e) {
						addErrorMessage(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	public void disputeInsert() {
		try {
			if (dispute.getResonForDispute() == null || dispute.getResonForDispute().isEmpty()
					|| dispute.getResonForDispute().equals(" ")) {
				throw new Exception("Provide Dispute Reason Before Trying To Add A Dispute.");
			}
			dispute.setCreateUser(getSessionUI().getActiveUser());
			siteVisitReportDisputeService.create(dispute);
			prepareNewDispute();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	public void prepareNewDispute() {
		try {
			dispute = new SiteVisitReportDispute();
			dispute.setSiteVisitReport(sitevisitreport);
			sitevisitreport
					.setSiteVisitReportDisputes(siteVisitReportDisputeService.findBySiteVisitReport(sitevisitreport));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void disputeDelete() {
		try {
			SiteVisitReportDisputeService disputeService = new SiteVisitReportDisputeService();
			disputeService.delete(dispute);
			prepareNewDispute();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	public void smeInsertOnCreate() {

		if (sitevisitreport.getSiteVisitReportSMEs() == null) {
			sitevisitreport.setSiteVisitReportSMEs(new ArrayList<>());
		}
		if (sme == null) {
			sme = new SiteVisitReportSME();
		}
	}

	/**
	 * 
	 */
	public void smeDelete() {
		if (sitevisitreport.getSiteVisitReportSMEs().contains(sme)) {
			sitevisitreport.getSiteVisitReportSMEs().remove(sme);
		}
		prepareNewSME();
	}

	/**
	 * Deletes a created SME
	 */
	public void deleteSme() {
		try {
			siteVisitReportSMEService.delete(sme);
			sitevisitreport.setSiteVisitReportSMEs(siteVisitReportSMEService.findBySiteVisitReport(sitevisitreport));
			addInfoMessage("User Succusfully Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Get all SiteVisitReport for data table
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 */
	public void sitevisitreportInfo() {

		dataModel = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					if (crmCloUser) {
						retorno = companyService.allCompanyByCloCrm(first, pageSize, sortField, sortOrder, filters,
								getSessionUI().getActiveUser());
						dataModel.setRowCount(companyService.countRegion(Company.class, filters));
					} else {
						retorno = companyService.allCompany(Company.class, first, pageSize, sortField, sortOrder,
								filters);
						dataModel.setRowCount(companyService.count(Company.class, filters));
					}
				} catch (Exception e) {

					logger.fatal(e);
					e.printStackTrace();
				}

				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};
	}

	/**
	 * 
	 */
	public void checkAddressOnSite() {

		sitevisitreport.setSite(this.getSite());

		if (sitevisitreport.getSite() == null) {
			sitevisitreport.setSite(new Sites());
		}
		if (sitevisitreport.getSite().getRegisteredAddress() == null) {
			sitevisitreport.getSite().setRegisteredAddress(new Address());
		}
	}

	/**
	 * Create new instance of SiteVisitReport
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 */
	public void prepareNewSmeEntry() {
		sme = new SiteVisitReportSME();
		try {
			if (companyHasSites != null && companyHasSites == false) {
				rt = RegionTownService.instance().findByTown(getCompany().getResidentialAddress().getTown());
				sme.setSiteName(rt.getTown().getDescription());
			}
			prepNewSmeUser();
			// service.prepareNewDocs(sitevisitreport);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		prepareNewGPS();
		prepareNewCalendarDropdowns();
	}

	/**
	 * @return boolean
	 */
	public boolean checkTradeOrQualificationSelected() {

		if (sme.getTrade() == null && sme.getQualification() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return boolean
	 */
	public boolean checkIdNumberOrPassportSelected() {

		if (sme.getIdentityNumber() == null && sme.getPassport() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	private void prepareNewCalendarDropdowns() {

		displayCalendarEquipmentOne = false;
		displayCalendarEquipmentTwo = false;
		displayCalendarEquipmentThree = false;
		displayCalendarEquipmentFour = false;
		displayCalendarEquipmentFive = false;
		displayCalendarRecordOne = false;
		displayCalendarRecordTwo = false;
		displayCalendarSafetyOne = false;
		displayCalendarSafetyTwo = false;
		displayCalendarSafetyThree = false;

		sitevisitreport.setYesNoEquipmentOne(new YesNoLookup());
		sitevisitreport.setYesNoEquipmentTwo(new YesNoLookup());
		sitevisitreport.setYesNoEquipmentThree(new YesNoLookup());
		sitevisitreport.setYesNoEquipmentFour(new YesNoLookup());
		sitevisitreport.setYesNoEquipmentFive(new YesNoLookup());
		sitevisitreport.setYesNoRecordOne(new YesNoLookup());
		sitevisitreport.setYesNoRecordTwo(new YesNoLookup());
		sitevisitreport.setYesNoSafetyOne(new YesNoLookup());
		sitevisitreport.setYesNoSafetyTwo(new YesNoLookup());
		sitevisitreport.setYesNoSafetyThree(new YesNoLookup());
	}

	/**
	 * @param yesNo
	 */
	public void yesNoSelected() {

		if (sitevisitreport.getYesNoEquipmentOne().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentOne().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarEquipmentOne = true;
		}
		if (sitevisitreport.getYesNoEquipmentOne().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentOne().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarEquipmentOne = false;
		}
		if (sitevisitreport.getYesNoEquipmentTwo().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentTwo().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarEquipmentTwo = true;
		}
		if (sitevisitreport.getYesNoEquipmentTwo().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentTwo().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarEquipmentTwo = false;
		}
		if (sitevisitreport.getYesNoEquipmentThree().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentThree().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarEquipmentThree = true;
		}
		if (sitevisitreport.getYesNoEquipmentThree().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentThree().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarEquipmentThree = false;
		}
		if (sitevisitreport.getYesNoEquipmentFour().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentFour().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarEquipmentFour = true;
		}
		if (sitevisitreport.getYesNoEquipmentFour().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentFour().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarEquipmentFour = false;
		}
		if (sitevisitreport.getYesNoEquipmentFive().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentFive().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarEquipmentFive = true;
		}
		if (sitevisitreport.getYesNoEquipmentFive().getYesNoName() != null
				&& sitevisitreport.getYesNoEquipmentFive().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarEquipmentFive = false;
		}
		if (sitevisitreport.getYesNoRecordOne().getYesNoName() != null
				&& sitevisitreport.getYesNoRecordOne().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarRecordOne = true;
		}
		if (sitevisitreport.getYesNoRecordOne().getYesNoName() != null
				&& sitevisitreport.getYesNoRecordOne().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarRecordOne = false;
		}
		if (sitevisitreport.getYesNoRecordTwo().getYesNoName() != null
				&& sitevisitreport.getYesNoRecordTwo().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarRecordTwo = true;
		}
		if (sitevisitreport.getYesNoRecordTwo().getYesNoName() != null
				&& sitevisitreport.getYesNoRecordTwo().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarRecordTwo = false;
		}
		if (sitevisitreport.getYesNoSafetyOne().getYesNoName() != null
				&& sitevisitreport.getYesNoSafetyOne().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarSafetyOne = true;
		}
		if (sitevisitreport.getYesNoSafetyOne().getYesNoName() != null
				&& sitevisitreport.getYesNoSafetyOne().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarSafetyOne = false;
		}
		if (sitevisitreport.getYesNoSafetyTwo().getYesNoName() != null
				&& sitevisitreport.getYesNoSafetyTwo().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarSafetyTwo = true;
		}
		if (sitevisitreport.getYesNoSafetyTwo().getYesNoName() != null
				&& sitevisitreport.getYesNoSafetyTwo().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarSafetyTwo = false;
		}
		if (sitevisitreport.getYesNoSafetyThree().getYesNoName() != null
				&& sitevisitreport.getYesNoSafetyThree().getYesNoName().equalsIgnoreCase("No")) {
			displayCalendarSafetyThree = true;
		}
		if (sitevisitreport.getYesNoSafetyThree().getYesNoName() != null
				&& sitevisitreport.getYesNoSafetyThree().getYesNoName().equalsIgnoreCase("Yes")) {
			displayCalendarSafetyThree = false;
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SiteVisitReport> complete(String desc) {
		List<SiteVisitReport> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * @param event
	 */
	public void storeNewFile(FileUploadEvent event) {

		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setTargetClass(sitevisitreport.getClass().getName());
			doc.setTargetKey(sitevisitreport.getId());
			if (doc.getId() == null) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			} else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SiteVisit in database
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitreportUpdate() {

		try {
			service.update(this.sitevisitreport);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitevisitreportInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete sitevisitreport from database
	 * 
	 * @author TechFinium
	 * @see sitevisitreport
	 */
	public void sitevisitreportDelete() {
		try {
			service.delete(this.sitevisitreport);
			prepareNewSmeEntry();
			sitevisitreportInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	public void completeTask() {

		try {
			if (getSessionUI().getTask().getProcessRole() == null) {
				service.completeToFirst(sitevisitreport, getSessionUI().getActiveUser(), getSessionUI().getTask());
			} else {
				service.completeTask(sitevisitreport, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}

			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * CLO - CREATE TASK AND FORWARD TO SDF AND LABOUR SDF (INITIAL SUBMIT)
	 * 
	 * Insert SiteVisitReport into database
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 */
	public void sitevisitreportInsert() {

		try {
			// validation checks
			// if (sitevisitreport.getSiteVisitReportSMEs().size() == 0) {
			// throw new Exception("Provide Atleast One Entry For: List Of
			// Qualified Artisans / Trainers /Subject Matter Experts (SME) Per
			// Site Before Proceeding");
			// }
			if (sitevisitreport.getSignOffs().size() == 0) {
				throw new Exception("Provide Atleast One Sign Off User Before Proceeding");
			}

			if (sitevisitreport.getSite() != null && sitevisitreport.getSite().getId() != null) {
				service.insertSiteAddress(sitevisitreport.getSite().getRegisteredAddress(),
						getSessionUI().getActiveUser());
				service.updateSiteWithSiteAddresss(sitevisitreport.getSite(),
						sitevisitreport.getSite().getRegisteredAddress(), getSessionUI().getUser());
			}
			service.insertSiteVisitReport(sitevisitreport, getSessionUI().getActiveUser());
			service.setSiteVisitReportIntoAllLinkedUplaodedDocs(sitevisitreport);
			service.sendToSDF(sitevisitreport, getSessionUI().getActiveUser(), sitevisitreport.getSiteVisitReportSMEs(),
					getSessionUI().getTask());
			// prepareNew();
			addInfoMessage(super.getEntryLanguage("site.visit.report.insert.success"));
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			super.addCallBackParm("validationFailed", true);
		}
	}

	/**
	 * CLO - CREATE TASK AND FORWARD TO SDF AND LABOUR SDF AFTER REJECTION
	 * RECEIVED (NOT INITIAL TASK)
	 * 
	 * Insert SiteVisit into database once it was rejected prior
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitreportInsertOnceRejected() {

		try {
			if (sitevisitreport.getSite() != null && sitevisitreport.getSite().getId() != null) {
				service.insertSiteAddress(sitevisitreport.getSite().getRegisteredAddress(),
						getSessionUI().getActiveUser());
				service.updateSiteWithSiteAddresss(sitevisitreport.getSite(),
						sitevisitreport.getSite().getRegisteredAddress(), getSessionUI().getUser());
			}
			service.insertSiteVisitReport(sitevisitreport, getSessionUI().getActiveUser());
			service.setSiteVisitReportIntoAllLinkedUplaodedDocs(sitevisitreport);
			service.sendToSDF(sitevisitreport, getSessionUI().getActiveUser(), sitevisitreport.getSiteVisitReportSMEs(),
					getSessionUI().getTask());
			// prepareNew();
			addInfoMessage(super.getEntryLanguage("site.visit.report.update.success"));

			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * SDF AND LABOUR SDF - APPROVE TASK AND FORWARD TO CRM
	 */
	public void approveTask() {
		try {
			for (Signoff signoff : sitevisitreport.getSignOffs()) {
				if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId())
						&& signoff.getAccept() != true) {
					throw new Exception("Please Sign Off Before Proceeding");
				}
			}
			service.approveTaskSDF(sitevisitreport, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * SDF AND LABOUR SDF - LODGE DISPUTES ABD APPROVE TASK AND FORWARD TO CRM
	 */
	public void disputeAndApproveTask() {

		try {
			service.disputeAndApproveTask(sitevisitreport, getSessionUI().getActiveUser(), getSessionUI().getTask());

			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * CRM - ACKNOWLEDGE DISPUTES AND FINAL APPROVE TASK
	 */
	public void acknowledgeDisputeAndApproveTask() {

		try {
			service.acknowledgeDisputeAndFinalApproveTask(sitevisitreport, getSessionUI().getActiveUser(),
					getSessionUI().getTask());

			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * CRM - ACKNOWLEDGE DISPUTES AND FINAL REJECT TASK BACK TO CLO
	 */
	public void acknowledgeDisputeAndRejectTask() {

		try {

			service.acknowledgeDisputeAndFinalRejectTask(sitevisitreport, getSessionUI().getActiveUser(),
					getSessionUI().getTask());

			ajaxRedirectToDashboard();

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * CRM - FINAL APPROVE TASK AND COMPLETES TASK
	 */
	public void finalApproveTask() {
		try {
			service.finalApproveTask(sitevisitreport, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * CRM - FINAL REJECT TASK BACK TO CLO
	 */
	private List<RejectReasons> selectedRejectReasons = null;

	public void prepCrmRejection() {
		try {
			selectedRejectReasons = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<RejectReasons> getRejectReasonsSelection() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SITE_VISIT_REPORT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getRejectReasonsByCrm() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(sitevisitreport.getId(),
					sitevisitreport.getClass().getName(), ConfigDocProcessEnum.SITE_VISIT_REPORT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void finalRejectTask() {
		try {
			if (selectedRejectReasons.size() == 0) {
				throw new Exception("Provide Atleast 1 Reason Before Proceeding");
			}
			service.finalRejectTask(sitevisitreport, getSessionUI().getActiveUser(), getSessionUI().getTask());
			service.setRejectedReasons(sitevisitreport.getClass().getName(), sitevisitreport.getId(),
					getSessionUI().getActiveUser(), selectedRejectReasons, ConfigDocProcessEnum.SITE_VISIT_REPORT);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNewSignOff() {
		try {
			signoff = new Signoff();
			// service.populateSignOffs(sitevisitreport);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addToList() {
		try {
			if (signoff.getUser() == null || signoff.getUser().getId() == null) {
				this.addErrorMessage("Please select a sign off user");
			} else {
				for (Signoff signoffloop : sitevisitreport.getSignOffs()) {
					// System.out.println(signoffloop.getUser().getId() + "
					// Already Added user");
					// System.out.println(signoff.getUser().getId() + " To Be
					// Added user");
					if (signoffloop.getUser().getId().equals(signoff.getUser().getId())) {
						prepareNewSignOff();
						throw new Exception("User is already allocated for sign off");
					}
				}
				signoff.setTargetClass(SiteVisitReport.class.getName());
				signoff.setTargetKey(sitevisitreport.getId());
				sitevisitreport.getSignOffs().add(signoff);
				prepareNewSignOff();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void removeFromSignOffList() {
		if (signoffSelected != null) {
			sitevisitreport.setSignOffs(sitevisitreport.getSignOffs().stream()
					.filter(sf -> !validateWspSignOffRemoval(sf)).collect(Collectors.toList()));
			prepareNewSignOff();
			addInfoMessage("Sign Off Removed");
		} else {
			addErrorMessage("Unable to locate selected sign off, contact support!");
		}
		// companyList = companyList.stream().filter(cmp ->
		// !validateCompaniesNoID(cmp)).collect(Collectors.toList());
		// if (sitevisitreport.getId() == null) {
		// sitevisitreport.getSignOffs().remove(indexLocation);
		// if (sitevisitreport.getSignOffs().size() == 0) {
		// sitevisitreport.setSignOffs(new ArrayList<>());
		// }
		// } else {
		//
		// }

	}

	/**
	 * Validates entry to be removed
	 * 
	 * @param signoff
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateWspSignOffRemoval(Signoff signoff) {
		if (signoff.getUser().getId().equals(signoffSelected.getUser().getId())) {
			return true;
		} else {
			return false;
		}
	}

	public void removeFromList() {
		try {
			signoffService.delete(signoff);
			prepareNewSignOff();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param sitevisitreportfilteredList
	 *            the new sitevisitreportfilteredList list
	 * @see SiteVisitReport
	 */
	public void setSiteVisitReportfilteredList(List<SiteVisitReport> sitevisitreportfilteredList) {
		this.sitevisitreportfilteredList = sitevisitreportfilteredList;
	}

	public SiteVisitReportService getService() {
		return service;
	}

	public void setService(SiteVisitReportService service) {
		this.service = service;
	}

	public List<SiteVisitReport> getSitevisitreportfilteredList() {
		return sitevisitreportfilteredList;
	}

	public void setSitevisitreportfilteredList(List<SiteVisitReport> sitevisitreportfilteredList) {
		this.sitevisitreportfilteredList = sitevisitreportfilteredList;
	}

	public List<WorkPlaceApproval> getWorkPlaceApprovalList() {
		return workPlaceApprovalList;
	}

	public void setWorkPlaceApprovalList(List<WorkPlaceApproval> workPlaceApprovalList) {
		this.workPlaceApprovalList = workPlaceApprovalList;
	}

	public List<Sites> getSitesList() {
		return sitesList;
	}

	public void setSitesList(List<Sites> sitesList) {
		this.sitesList = sitesList;
	}

	public SitesService getSitesService() {
		return sitesService;
	}

	public void setSitesService(SitesService sitesService) {
		this.sitesService = sitesService;
	}

	public WorkPlaceApprovalService getWorkPlaceApprovalService() {
		return workPlaceApprovalService;
	}

	public void setWorkPlaceApprovalService(WorkPlaceApprovalService workPlaceApprovalService) {
		this.workPlaceApprovalService = workPlaceApprovalService;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<RejectReasons> getRejectReasons() {
		return rejectReasons;
	}

	public void setRejectReasons(List<RejectReasons> rejectReasons) {
		this.rejectReasons = rejectReasons;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public RegionTown getRt() {
		return rt;
	}

	public void setRt(RegionTown rt) {
		this.rt = rt;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	public SiteVisitReportSMEService getSiteVisitReportSMEService() {
		return siteVisitReportSMEService;
	}

	public void setSiteVisitReportSMEService(SiteVisitReportSMEService siteVisitReportSMEService) {
		this.siteVisitReportSMEService = siteVisitReportSMEService;
	}

	public SiteVisitReportSME getSme() {
		return sme;
	}

	public void setSme(SiteVisitReportSME sme) {
		this.sme = sme;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public RejectReasonsChildService getRejectReasonsService() {
		return rejectReasonsService;
	}

	public void setRejectReasonsService(RejectReasonsChildService rejectReasonsService) {
		this.rejectReasonsService = rejectReasonsService;
	}

	public YesNoLookupService getYesNoLookupService() {
		return yesNoLookupService;
	}

	public void setYesNoLookupService(YesNoLookupService yesNoLookupService) {
		this.yesNoLookupService = yesNoLookupService;
	}

	public List<YesNoLookup> getYesNoList() {
		return yesNoList;
	}

	public void setYesNoList(List<YesNoLookup> yesNoList) {
		this.yesNoList = yesNoList;
	}

	public boolean isDisplayCalendarEquipmentOne() {
		return displayCalendarEquipmentOne;
	}

	public void setDisplayCalendarEquipmentOne(boolean displayCalendarEquipmentOne) {
		this.displayCalendarEquipmentOne = displayCalendarEquipmentOne;
	}

	public boolean isDisplayCalendarEquipmentTwo() {
		return displayCalendarEquipmentTwo;
	}

	public void setDisplayCalendarEquipmentTwo(boolean displayCalendarEquipmentTwo) {
		this.displayCalendarEquipmentTwo = displayCalendarEquipmentTwo;
	}

	public boolean isDisplayCalendarEquipmentThree() {
		return displayCalendarEquipmentThree;
	}

	public void setDisplayCalendarEquipmentThree(boolean displayCalendarEquipmentThree) {
		this.displayCalendarEquipmentThree = displayCalendarEquipmentThree;
	}

	public boolean isDisplayCalendarEquipmentFour() {
		return displayCalendarEquipmentFour;
	}

	public void setDisplayCalendarEquipmentFour(boolean displayCalendarEquipmentFour) {
		this.displayCalendarEquipmentFour = displayCalendarEquipmentFour;
	}

	public boolean isDisplayCalendarEquipmentFive() {
		return displayCalendarEquipmentFive;
	}

	public void setDisplayCalendarEquipmentFive(boolean displayCalendarEquipmentFive) {
		this.displayCalendarEquipmentFive = displayCalendarEquipmentFive;
	}

	public boolean isDisplayCalendarRecordOne() {
		return displayCalendarRecordOne;
	}

	public void setDisplayCalendarRecordOne(boolean displayCalendarRecordOne) {
		this.displayCalendarRecordOne = displayCalendarRecordOne;
	}

	public boolean isDisplayCalendarRecordTwo() {
		return displayCalendarRecordTwo;
	}

	public void setDisplayCalendarRecordTwo(boolean displayCalendarRecordTwo) {
		this.displayCalendarRecordTwo = displayCalendarRecordTwo;
	}

	public boolean isDisplayCalendarSafetyOne() {
		return displayCalendarSafetyOne;
	}

	public void setDisplayCalendarSafetyOne(boolean displayCalendarSafetyOne) {
		this.displayCalendarSafetyOne = displayCalendarSafetyOne;
	}

	public boolean isDisplayCalendarSafetyTwo() {
		return displayCalendarSafetyTwo;
	}

	public void setDisplayCalendarSafetyTwo(boolean displayCalendarSafetyTwo) {
		this.displayCalendarSafetyTwo = displayCalendarSafetyTwo;
	}

	public SiteVisitReport getSitevisitreport() {
		return sitevisitreport;
	}

	public void setSitevisitreport(SiteVisitReport sitevisitreport) {
		this.sitevisitreport = sitevisitreport;
	}

	public List<SiteVisitReport> getSiteVisitReportfilteredList() {
		return sitevisitreportfilteredList;
	}

	public boolean isDisplayCalendarSafetyThree() {
		return displayCalendarSafetyThree;
	}

	public void setDisplayCalendarSafetyThree(boolean displayCalendarSafetyThree) {
		this.displayCalendarSafetyThree = displayCalendarSafetyThree;
	}

	public Boolean getCrmCloUser() {
		return crmCloUser;
	}

	public void setCrmCloUser(Boolean crmCloUser) {
		this.crmCloUser = crmCloUser;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getSiteVisitReportCreateClicked() {
		return siteVisitReportCreateClicked;
	}

	public void setSiteVisitReportCreateClicked(Boolean siteVisitReportCreateClicked) {
		this.siteVisitReportCreateClicked = siteVisitReportCreateClicked;
	}

	public Boolean getSitevisitreportSelected() {
		return sitevisitreportSelected;
	}

	public void setSitevisitreportSelected(Boolean sitevisitreportSelected) {
		this.sitevisitreportSelected = sitevisitreportSelected;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public Boolean getCompanyHasSites() {
		return companyHasSites;
	}

	public void setCompanyHasSites(Boolean companyHasSites) {
		this.companyHasSites = companyHasSites;
	}

	public List<SiteVisitReportDispute> getDisputes() {
		return disputes;
	}

	public void setDisputes(List<SiteVisitReportDispute> disputes) {
		this.disputes = disputes;
	}

	public SiteVisitReportDispute getDispute() {
		return dispute;
	}

	public void setDispute(SiteVisitReportDispute dispute) {
		this.dispute = dispute;
	}

	public List<SiteVisitReport> getSiteVisitReportList() {
		return siteVisitReportList;
	}

	public void setSiteVisitReportList(List<SiteVisitReport> siteVisitReportList) {
		this.siteVisitReportList = siteVisitReportList;
	}

	public boolean isDisableAll() {
		return disableAll;
	}

	public void setDisableAll(boolean disableAll) {
		this.disableAll = disableAll;
	}

	public Signoff getSignoff() {
		return signoff;
	}

	public void setSignoff(Signoff signoff) {
		this.signoff = signoff;
	}

	public Signoff getSignoffSelected() {
		return signoffSelected;
	}

	public void setSignoffSelected(Signoff signoffSelected) {
		this.signoffSelected = signoffSelected;
	}

	public Users getSmeUser() {
		return smeUser;
	}

	public void setSmeUser(Users smeUser) {
		this.smeUser = smeUser;
	}

	public IdPassportEnum getIdpassport() {
		return idpassport;
	}

	public void setIdpassport(IdPassportEnum idpassport) {
		this.idpassport = idpassport;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public List<RejectReasons> getSelectedRejectReasons() {
		return selectedRejectReasons;
	}

	public void setSelectedRejectReasons(List<RejectReasons> selectedRejectReasons) {
		this.selectedRejectReasons = selectedRejectReasons;
	}
}
