package haj.com.ui;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.ManyToOne;
import javax.validation.ConstraintViolationException;

import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfVendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddressKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;

import haj.com.bean.HeaderBean;
import haj.com.bean.MgVerificationReportBean;
import haj.com.bean.QmrScriptOneBean;
import haj.com.bean.TechfiniumTreeNode;
import haj.com.constants.HAJConstants;
import haj.com.dao.QmrReportingDAO;
import haj.com.dao.TS2DAO;
import haj.com.dataextract.bean.NLRDFile21Bean;
import haj.com.dataextract.bean.NLRDFile21BeanProviderVersionTwo;
import haj.com.dataextract.bean.NLRDFile22Bean;
import haj.com.dataextract.bean.NLRDFile22BeanQualificationDegreeLegacyVersionTwo;
import haj.com.dataextract.bean.NLRDFile23Bean;
import haj.com.dataextract.bean.NLRDFile23BeanCourseLegacyVersionTwo;
import haj.com.dataextract.bean.NLRDFile24Bean;
import haj.com.dataextract.bean.NLRDFile24BeanProviderAccreditationVersionTwo;
import haj.com.dataextract.bean.NLRDFile25Bean;
import haj.com.dataextract.bean.NLRDFile25BeanPersonInformationVersionTwo;
import haj.com.dataextract.bean.NLRDFile26Bean;
import haj.com.dataextract.bean.NLRDFile26BeanPersonDesignationVersionTwo;
import haj.com.dataextract.bean.NLRDFile27Bean;
import haj.com.dataextract.bean.NLRDFile27BeanNQFDesignationRegistrationVersionTwo;
import haj.com.dataextract.bean.NLRDFile28Bean;
import haj.com.dataextract.bean.NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile29Bean;
import haj.com.dataextract.bean.NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile30Bean;
import haj.com.dataextract.bean.NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.QCTO01Bean;
import haj.com.dataextract.bean.QCTO02Bean;
import haj.com.dataextract.bean.QCTO03Bean;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.dataextract.bean.SETMISFile100BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile200Bean;
import haj.com.dataextract.bean.SETMISFile200BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.dataextract.bean.SETMISFile400BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.dataextract.bean.SETMISFile503Bean;
import haj.com.dataextract.bean.SETMISFile503BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile505Bean;
import haj.com.dataextract.bean.SETMISFile506Bean;
import haj.com.dataextract.bean.SETMISFile506BeanSandra;
import haj.com.datatakeon.MandatoryGrantsRecon;
import haj.com.entity.ActiveContracts;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.NLRDFile21;
import haj.com.entity.NLRDFile22;
import haj.com.entity.NLRDFile23;
import haj.com.entity.NLRDFile24;
import haj.com.entity.NLRDFile25;
import haj.com.entity.NLRDFile26;
import haj.com.entity.NLRDFile27;
import haj.com.entity.NLRDFile28;
import haj.com.entity.NLRDFile29;
import haj.com.entity.NLRDFile30;
import haj.com.entity.QmrFinYears;
import haj.com.entity.SetmisFile100;
import haj.com.entity.SetmisFile200;
import haj.com.entity.SetmisFile304;
import haj.com.entity.SetmisFile400;
import haj.com.entity.SetmisFile401;
import haj.com.entity.SetmisFile500;
import haj.com.entity.SetmisFile501;
import haj.com.entity.SetmisFile502;
import haj.com.entity.SetmisFile503;
import haj.com.entity.SetmisFile505;
import haj.com.entity.SetmisFile506;
import haj.com.entity.Tempx;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.QmrEnteredCompletedEnum;
import haj.com.entity.enums.QmrTypeSelectionEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.lookup.Region;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.gpservices.GPAddressTypeEnum;
import haj.com.gpservices.GPPrepTransactionsService;
import haj.com.gpservices.GPVendorClassEnum;
import haj.com.saqa.qualifications.LoadQualification;
import haj.com.sars.SarsLoadLevies;
import haj.com.service.ActiveContractDetailService;
import haj.com.service.ActiveContractExtensionRequestService;
import haj.com.service.ActiveContractsService;
import haj.com.service.ArchiveDocumentsService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.BankingDetailsService;
import haj.com.service.CategorisationService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyService;
import haj.com.service.DataExtractService;
import haj.com.service.DgAllocationService;
import haj.com.service.DgVerificationService;
import haj.com.service.ICalendarService;
import haj.com.service.JasperService;
import haj.com.service.LegacyDataService;
import haj.com.service.MandatoryGrantDetailArchiveService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MgVerificationService;
import haj.com.service.NRLDService;
import haj.com.service.OfoCodesService;
import haj.com.service.OnScreenHelpService;
import haj.com.service.QCTOService;
import haj.com.service.QmrFinYearsService;
import haj.com.service.QmrLearnershipDataService;
import haj.com.service.ReportGenerationService;
import haj.com.service.SETMISService;
import haj.com.service.SarsEmployerDetailService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyReconService;
import haj.com.service.ScheduleService;
import haj.com.service.SitesService;
import haj.com.service.TestService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UsersService;
import haj.com.service.WspCompanyHistoryCreateService;
import haj.com.service.WspService;
import haj.com.service.lookup.RegionService;
import haj.com.service.nlrd.NLRDFile21Service;
import haj.com.service.nlrd.NLRDFile24Service;
import haj.com.service.nlrd.NLRDFile25Service;
import haj.com.service.nlrd.NLRDFile26Service;
import haj.com.service.nlrd.NLRDFile27Service;
import haj.com.service.nlrd.NLRDFile28Service;
import haj.com.service.nlrd.NLRDFile29Service;
import haj.com.service.nlrd.NLRDFile30Service;
import haj.com.service.setmis.SETMISFile100Service;
import haj.com.service.setmis.SETMISFile200Service;
import haj.com.service.setmis.SETMISFile304Service;
import haj.com.service.setmis.SETMISFile400Service;
import haj.com.service.setmis.SETMISFile401Service;
import haj.com.service.setmis.SETMISFile500Service;
import haj.com.service.setmis.SETMISFile501Service;
import haj.com.service.setmis.SETMISFile502Service;
import haj.com.service.setmis.SETMISFile503Service;
import haj.com.service.setmis.SETMISFile505Service;
import haj.com.service.setmis.SETMISFile506Service;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;
import haj.com.utils.PropertyDuplicateCheck;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.CreateVendorAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class TestUI.
 */
@ManagedBean(name = "testUI")
@ViewScoped
public class TestUI extends AbstractUI {

	private List<Company>                      allTrainingProvidersList;
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private ReportGenerationService            reportGenerationService            = new ReportGenerationService();
	private ScheduleService                    scheduleService                    = new ScheduleService();
	private SitesService                       sitesService                       = new SitesService();
	private TestService                        testService                        = new TestService();

	/** The dummy. */
	private String dummy;

	/** The root. */
	private TreeNode root;

	/** The company. */
	private Company company = new Company();

	/** The upload doc UI. */
	@ManagedProperty(value = "#{uploadDocUI}")
	private UploadDocUI                 uploadDocUI;
	private MandatoryGrantDetailService companyService = new MandatoryGrantDetailService();

	private CategorisationService categorisationService = new CategorisationService();
	private DgVerificationService dgVerificationService = new DgVerificationService();
	private OfoCodesService       ofoCodesService       = null;
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();

	private static final Gson gson = buildGson();

	private CompanyService companyServicea = new CompanyService();
	private WspService     wspService      = new WspService();

	private String               generalMessage;
	private ConfigDocProcessEnum workflow;

	/** Bean Services. */
	private NRLDService          nrldService          = new NRLDService();
	private QCTOService          qctoService          = new QCTOService();
	private SETMISService        setmisService        = new SETMISService();
	private SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
	private SETMISFile200Service setmisFile200Service = new SETMISFile200Service();
	private SETMISFile304Service setmisFile304Service = new SETMISFile304Service();
	private SETMISFile400Service setmisFile400Service = new SETMISFile400Service();
	private SETMISFile401Service setmisFile401Service = new SETMISFile401Service();
	private SETMISFile500Service setmisFile500Service = new SETMISFile500Service();
	private SETMISFile501Service setmisFile501Service = new SETMISFile501Service();
	private SETMISFile502Service setmisFile502Service = new SETMISFile502Service();
	private SETMISFile503Service setmisFile503Service = new SETMISFile503Service();
	private SETMISFile505Service setmisFile505Service = new SETMISFile505Service();
	private SETMISFile506Service setmisFile506Service = new SETMISFile506Service();

	private NLRDFile21Service nlrdFile21Service = new NLRDFile21Service();
	private NLRDFile24Service nlrdFile24Service = new NLRDFile24Service();
	private NLRDFile25Service nlrdFile25Service = new NLRDFile25Service();
	private NLRDFile26Service nlrdFile26Service = new NLRDFile26Service();
	private NLRDFile27Service nlrdFile27Service = new NLRDFile27Service();
	private NLRDFile28Service nlrdFile28Service = new NLRDFile28Service();
	private NLRDFile29Service nlrdFile29Service = new NLRDFile29Service();
	private NLRDFile30Service nlrdFile30Service = new NLRDFile30Service();

	/** Bean */
	private List<NLRDFile21Bean>          			nlrdFile21Bean;
	private List<NLRDFile22Bean>          			nlrdFile22Bean;
	private List<NLRDFile23Bean>          			nlrdFile23Bean;
	private List<NLRDFile24Bean>          			nlrdFile24Bean;
	private List<NLRDFile25Bean>          			nlrdFile25Bean;
	private List<NLRDFile26Bean>         			nlrdFile26Bean;
	private List<NLRDFile27Bean>          			nlrdFile27Bean;
	private List<NLRDFile28Bean>          			nlrdFile28Bean;
	private List<NLRDFile29Bean>          			nlrdFile29Bean;
	private List<NLRDFile30Bean>          			nlrdFile30Bean;
	
	private List<NLRDFile21BeanProviderVersionTwo>  						nlrdFile21BeanProviderVersionTwo;
	private List<NLRDFile22BeanQualificationDegreeLegacyVersionTwo>			nlrdFile22BeanQualificationDegreeLegacyVersionTwo;
	private List<NLRDFile23BeanCourseLegacyVersionTwo>						nlrdFile23BeanCourseLegacyVersionTwo;
	private List<NLRDFile24BeanProviderAccreditationVersionTwo>				nlrdFile24BeanProviderAccreditationVersionTwo;
	private List<NLRDFile25BeanPersonInformationVersionTwo>					nlrdFile25BeanPersonInformationVersionTwo;
	private List<NLRDFile26BeanPersonDesignationVersionTwo>					nlrdFile26BeanPersonDesignationVersionTwo;
	private List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo>		nlrdFile27BeanNQFDesignationRegistrationVersionTwo;
	private List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo>	nlrdFile28BeanLearnershipEnrolmentAchievementVersionTwo;
	private List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo>	nlrdFile29BeanQualificationEnrolmentAchievementVersionTwo;
	private List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo>	nlrdFile30BeanUnitStandardEnrolmentAchievementVersionTwo;
	
	private List<QCTO01Bean>              			qcto01Bean;
	private List<QCTO02Bean>              			qcto02Bean;
	private List<QCTO03Bean>             			qcto03Bean;
	private List<SETMISFile100Bean>       			setmisFile100Bean;
	private List<SETMISFile100BeanVersionTwo>       setmisFile100BeanVersionTwo;
	private List<SETMISFile200Bean>       			setmisFile200Bean;
	private List<SETMISFile200BeanVersionTwo>       setmisFile200BeanVersionTwo;
	private List<SETMISFile400Bean>       			setmisFile400Bean;
	private List<SETMISFile400BeanVersionTwo>       setmisFile400BeanVersionTwo;
	private List<SETMISFile503Bean>       			setmisFile503Bean;
	private List<SETMISFile503BeanVersionTwo>       setmisFile503BeanVersionTwo;
	
	private List<SETMISFile304Bean>       setmisFile304Bean;
	private List<SETMISFile401Bean>       setmisFile401Bean;
	private List<SETMISFile500Bean>       setmisFile500Bean;
	private List<SETMISFile501Bean>       setmisFile501Bean;
	private List<SETMISFile502Bean>       setmisFile502Bean;
	
	private List<SETMISFile505Bean>       setmisFile505Bean;
	private List<SETMISFile506Bean>       setmisFile506Bean;
	private List<SETMISFile506BeanSandra> setmisFile506BeanSandra;

	private List<SetmisFile100> setmisFile100;
	private List<SetmisFile200> setmisFile200;
	private List<SetmisFile304> setmisFile304;
	private List<SetmisFile400> setmisFile400;
	private List<SetmisFile401> setmisFile401;
	private List<SetmisFile500> setmisFile500;
	private List<SetmisFile501> setmisFile501;
	private List<SetmisFile502> setmisFile502;
	private List<SetmisFile503> setmisFile503;
	private List<SetmisFile505> setmisFile505;
	private List<SetmisFile506> setmisFile506;

	private List<NLRDFile21> nlrdFile21;
	private List<NLRDFile22> nlrdFile22;
	private List<NLRDFile23> nlrdFile23;
	private List<NLRDFile24> nlrdFile24;
	private List<NLRDFile25> nlrdFile25;
	private List<NLRDFile26> nlrdFile26;
	private List<NLRDFile27> nlrdFile27;
	private List<NLRDFile28> nlrdFile28;
	private List<NLRDFile29> nlrdFile29;
	private List<NLRDFile30> nlrdFile30;

	private Date             date = new Date();
	private String           currentDate;
	private String           currentDateNrld;
	private HeaderBean       headerBean;
	private List<HeaderBean> headerBeanList;
	
	private String testingString;

	private static Gson buildGson() {
		return new GsonBuilder().addSerializationExclusionStrategy(getExclusionStrategy()).create();
	}

	private static ExclusionStrategy getExclusionStrategy() {
		ExclusionStrategy exlStrategy = new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fas) {
				return (null != fas.getAnnotation(ManyToOne.class));
			}

			@Override
			public boolean shouldSkipClass(Class<?> classO) {
				return (null != classO.getAnnotation(ManyToOne.class));
			}

		};
		return exlStrategy;
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			currentDate = sdf.format(date);
			SimpleDateFormat sdfNrld = new SimpleDateFormat("yyMMdd");
			currentDateNrld = sdfNrld.format(date);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void testErrors() {
		try {
			AssessorModeratorApplication assessorModeratorApplication = new AssessorModeratorApplication();
			assessorModeratorApplication.setCertificateNumber("ONTBREEK");
			assessorModeratorApplication.setInitialValidation(true);
			assessorModeratorApplication.setUser(new Users());
			assessorModeratorApplication.getUser().setFirstName("{}{}{}[][][}{}{");
			assessorModeratorApplication.getUser().setLastName("NONE");
			assessorModeratorApplication.getUser().setMiddleName("{}{}{}[][][}{}{");
			new AssessorModeratorApplicationService().create(assessorModeratorApplication);
		} catch (ConstraintViolationException e) {
			extractValidationErrors(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run init.
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		// dgVerificationService.generateForWSP(new WspService().findByKey(221L));
		// dgVerificationService.generateForWSP(new WspService().findByKey(2167L));
		// new DgAllocationService().doAllocation(new WspService().findByKey(221L));
		// new DgAllocationService().doAllocation(new WspService().findByKey(2167L));
		// new DgAllocationService().doAllocation(new WspService().findByKey(3l));
		// new DgAllocationService().doAllocation(new WspService().findByKey(4l));
		// new DgAllocationService().doAllocation(new WspService().findByKey(5l));
		/*
		 * Doc doc = new Doc(); doc.setUsers(getSessionUI().getUser()); doc.setId(1l); getUploadDocUI().setDoc(doc); getUploadDocUI().findDoc(doc); doDiagram();
		 */
		// System.out.println(companyServicea.findLastNNumber());
		// this.company = companyServicea.findByKey(7l);

		// Users u = new Users();
		// u.setRsaIDNumber("0008275168089");
		// GenericUtility.calcIDData(u);
		// System.out.println();
		// createPIP();
		// trainingProvidersList();
	}

	public void trainingProvidersList() {
		try {
			allTrainingProvidersList = trainingProviderApplicationService.findAllTrainingProviders();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void testGPWithLNumber() {
		bankingDetailsService.getGPDetailsForLNumber("L111111111", "1111122222");
	}

	public void checkDuplicates() {
		PropertyDuplicateCheck.checkProps();
	}

	public void generateVerification() {
		try {
			// companyService.createMGVerificationInformation();

			// dgVerificationService.generateForWSP();
			// wspService.checkIfCorrectSignOff();

			// orginal Version
			// companyService.createMGVerificationData();
			// verison two
			companyService.createMGVerificationDataVersionTwo();
		} catch (Exception e) {
			logger.fatal(e);
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void checkWspData() {
		try {
			// companyService.createMGVerificationData();
			dgVerificationService.generateForWSP(new WspService().findByKey(6049l));
			// wspService.checkIfCorrectSignOff();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	// ETQ-TP-027-AssessorCertificateLetter.jrxml
	// ETQ-TP-028-ModeratorCertificateLetter.jrxml
	public void downloadCertificates() {
		try {
			Map<String, Map<String, Object>> zipParams = new HashMap<String, Map<String, Object>>();
			String                           fileName  = "Cert.zip";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("certificate_number", String.valueOf(UUID.randomUUID()));
			params.put("date_of_expiry", GenericUtility.sdf5.format(GenericUtility.addYearsToDate(getNow(), 2)));
			params.put("date_of_registration", GenericUtility.sdf5.format(getNow()));
			params.put("barcode", UUID.randomUUID());
			params.put("user_id", 24l);
			JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
			JasperService.addImage(params, "left_right_boder.png", "left_right_border");
			JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
			JasperService.addImage(params, "corner_image.png", "corner_image");
			JasperService.addImage(params, "logo2.png", "logo");

			zipParams.put("AssessorCertificate.jasper", params);

			JasperService.instance().multipleJasperToZip(zipParams, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshMG() {
		companyService.refreshData();
	}

	public void sendBulk() {
		try {
			GenericUtility.sendMailAllUsers("NSDMS Notification", generalMessage, null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sendBulkPrimarySdfUsers() {
		try {
			GenericUtility.sendMailAllPrimarySdfUsers("NSDMS Notification", generalMessage, null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	public void sendBulkWorkflow() {
		try {
			GenericUtility.sendMailAllTaskUsers("Outstanding NSDMS task", generalMessage, null, workflow);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void doAllocation() {
		try {
			// new DgAllocationService().doAllocation(new WspService().findByKey(363l));
			// new DgAllocationService().doAllocation(new WspService().findByKey(2l));
			// new DgAllocationService().doAllocation(new WspService().findByKey(3l));
			// new DgAllocationService().doAllocation(new WspService().findByKey(4l));
			// new DgAllocationService().doAllocation(new WspService().findByKey(5l));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateWorkplaceApproval() {
		try {
			DgVerificationService.instance().generateWorkplaceApproval();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Do diagram.
	 */
	private void doDiagram() {
		root = new TechfiniumTreeNode("Root", null);
		root.setType("redClass");
		root.setExpanded(true);
		TreeNode node0 = new DefaultTreeNode("Node 0", root);
		TreeNode node1 = new DefaultTreeNode("Node 1", root);
		node1.setType("redClass");

		TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
		node00.setType("greenClass");
		TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);

		TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);

		node1.getChildren().add(new DefaultTreeNode("Node 1.1"));
		node00.getChildren().add(new DefaultTreeNode("Node 0.0.0"));
		node00.getChildren().add(new DefaultTreeNode("Node 0.0.1"));
		node01.getChildren().add(new DefaultTreeNode("Node 0.1.0"));
		node10.getChildren().add(new DefaultTreeNode("Node 1.0.0"));
		root.getChildren().add(new DefaultTreeNode("Node 2"));
	}

	/**
	 * Test.
	 */
	public void test() {
		try {
			// GenericUtility.sendMail("Peter.Moloi@67as.com", "subject", "msg", null);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void test2() {
		try {
			TS2DAO dao   = new TS2DAO();
			Date   start = new Date();
			logger.info("Start upate all the data");
			List<Tempx> l = dao.allTempx();
			logger.info("Read all the data");
			List<IDataEntity> entityList = new ArrayList<IDataEntity>();
			for (Tempx tempx : l) {
				entityList.add(tempx);
			}
			dao.updateBatch(entityList, 20);
			Date end = new Date();
			long ms  = end.getTime() - start.getTime();
			logger.info("End update all the data took: " + ms + "ms" + "  " + (ms / 1000) + "seconds");
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void test3() {
		try {
			TS2DAO dao   = new TS2DAO();
			Date   start = new Date();
			logger.info("Start NQ upate all the data");
			dao.updateBatch("NQ_allTempX", 20);
			Date end = new Date();
			long ms  = end.getTime() - start.getTime();
			logger.info("End NQ update all the data took: " + ms + "ms" + "  " + (ms / 1000) + "seconds");

		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void test4() {
		try {
			TS2DAO dao   = new TS2DAO();
			Date   start = new Date();
			logger.info("Start NQ select all the data");
			List<Tempx> l = dao.findByfindBySDLnumber("L000725046");
			for (Tempx tempx : l) {
				System.out.println(tempx.getRefVoucherNumber4());
			}
			Date end = new Date();
			long ms  = end.getTime() - start.getTime();
			logger.info("End NQ select took: " + ms + "ms" + "  " + (ms / 1000) + "seconds");

		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void test5() {
		try {
			WspService wspService = new WspService();
			Wsp        wsp        = wspService.findByGuid("ea70f1e2-9fbf-497a-86bf-4e56852a3a24", new UsersService().findByKey(44l));

			// EntityCloner<Wsp> cloner = new EntityCloner<Wsp>(wsp);
			// Wsp clone = cloner.generateClone();
			// System.out.println("clone"+clone.toString());

			// Wsp object2= (Wsp)org.apache.commons.lang.SerializationUtils.clone(wsp);
			// System.out.println("object2"+object2.toString());

		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void test6() {
		try {
			SarsFilesService s = new SarsFilesService();
			s.summaryBySchemeYear(2016);

		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test7() {
		try {
			SarsLevyReconService s = new SarsLevyReconService();
			s.levyReconPerSchemeYearPerSDL(2014);

		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test8() {
		try {

			// createVendor();
			// GPService.instance().createPayablesInvoice(2, "L580783233", BigDecimal.valueOf(100.19));

		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test9() {
		try {
			SarsEmployerDetailService service = new SarsEmployerDetailService();
			service.populateNewFromSARS(183l);
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test10() {
		try {
			GPPrepTransactionsService s = new GPPrepTransactionsService();

			s.createCreditorOnGp(this.company);
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test11() {
		try {
			System.out.println(HAJConstants.APP_PATH);
			List<String> l = GenericUtility.allFilesRecursivelyWithExtension(HAJConstants.APP_PATH, "xhtml");
			l.forEach(System.out::println);
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test12() {
		try {

			MandatoryGrantsRecon recon = new MandatoryGrantsRecon();
			// recon.convertDatesAndAmounts();
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test13() {
		try {

			ICalendarService sm = new ICalendarService();
			sm.sendMeetingBooking("summary", "description", "location", GenericUtility.addMiniutesToDate(new Date(), 20), 1, 15, "test@a.com", "subject", "text");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();

	public void test14() {
		try {
			CompanyLearnersService companyLearnersService = new CompanyLearnersService();
			activeContractDetailService.addTranchePaymentDetail(companyLearnersService.findByKey(16L), getSessionUI().getActiveUser(), 0.25, TrancheEnum.TRANCHE_TWO, true);
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void test15() {
		try {

			// createVendor();
			// GPService.instance().vendorByKeyForCompany(2, "L580783233");

		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void loadPre2009Qualifications() {
		try {

			LoadQualification.loadPre2009();
			addInfoMessage("Load complete");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void loadSARSCompanies() {
		try {
			SarsEmployerDetailService s = new SarsEmployerDetailService();

			s.populateNewFromSARS(182l);
			addInfoMessage("Done");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	/**
	 * Gets the upload doc UI.
	 * @return the upload doc UI
	 */
	public UploadDocUI getUploadDocUI() {
		return uploadDocUI;
	}

	/**
	 * Sets the upload doc UI.
	 * @param uploadDocUI
	 * the new upload doc UI
	 */
	public void setUploadDocUI(UploadDocUI uploadDocUI) {
		this.uploadDocUI = uploadDocUI;
	}

	/**
	 * Gets the dummy.
	 * @return the dummy
	 */
	public String getDummy() {
		return dummy;
	}

	/**
	 * Sets the dummy.
	 * @param dummy
	 * the new dummy
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see haj.com.framework.AbstractUIInterface#callBackMethod(java.lang.Object)
	 */
	@Override
	public void callBackMethod(Object obj) {

		logger.info("in TestUI in callBackMethod() " + obj.getClass().getName());
		logger.info("{}",((Users) obj).getId());
	}

	/**
	 * Gets the root.
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Sets the root.
	 * @param root
	 * the new root
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * Gets the company.
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 * @param company
	 * the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	private int companyId = 2;

	public VendorAddress crtAddress(String vendorId) throws Exception {

		VendorAddress address = new VendorAddress();

		VendorKey vendorKey = new VendorKey();
		vendorKey.setId(vendorId);
		CompanyKey companyKey = new CompanyKey();
		companyKey.setId(companyId);
		vendorKey.setCompanyKey(companyKey);
		VendorAddressKey key = new VendorAddressKey();
		key.setCompanyKey(vendorKey.getCompanyKey());
		key.setVendorKey(vendorKey);
		key.setId(GPAddressTypeEnum.POSTAL.getGPName());

		address.setKey(key);
		address.setDeleteOnUpdate(false);
		address.setCity("Benoni");
		address.setLine1("6 Malherbe Street");
		address.setLine2("Rynfield");
		address.setPostalCode("1501");
		address.setName("POSTAL");
		address.setCountryRegion("ZA");
		address.setUPSZone("");
		return address;
	}

	private void createVendor() throws Exception {
		VendorKey vendorKey = new VendorKey();
		vendorKey.setId("L000000003");
		Vendor vendor = new Vendor();
		vendor.setKey(vendorKey);
		vendor.setName("Hendrik Test Vendor 2");

		vendor.setIsActive(Boolean.TRUE);
		vendor.setIsOneTime(Boolean.FALSE);
		vendor.setIsOnHold(Boolean.FALSE);

		ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
		addresses.getVendorAddress().add(crtAddress("L000000003"));

		vendor.setAddresses(addresses);

		VendorClassKey vendorClassKey = new VendorClassKey();
		vendorClassKey.setId(GPVendorClassEnum.PLASTICS.getGPName());

		CompanyKey companyKey = new CompanyKey();
		companyKey.setId(companyId);
		vendorClassKey.setCompanyKey(companyKey);
		vendor.setClassKey(vendorClassKey);
		CreateVendorAdapter adapter= new CreateVendorAdapter();
		adapter.createVendor(vendor);
	}

	public String getGeneralMessage() {
		return generalMessage;
	}

	public void setGeneralMessage(String generalMessage) {
		this.generalMessage = generalMessage;
	}

	public ConfigDocProcessEnum getWorkflow() {
		return workflow;
	}

	public void setWorkflow(ConfigDocProcessEnum workflow) {
		this.workflow = workflow;
	}

	public void downloadDocStore(String fileName, String downloadName) {
		try {
			Thread.sleep(3000);
			Path   path = Paths.get(HAJConstants.DOC_PATH + fileName);
			byte[] data = Files.readAllBytes(path);
			Faces.sendFile(data, downloadName, true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public StreamedContent downloadDocStore2(String fileName, String downloadName) {
		StreamedContent file = null;
		try {
			Thread.sleep(3000);
			String      path   = Paths.get(HAJConstants.DOC_PATH + fileName).toString();
			InputStream stream = new BufferedInputStream(new FileInputStream(path));
			file = new DefaultStreamedContent(stream, "data/file", downloadName);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return file;
	}

	public void sleepFor(int sleepDuration) {
		// super.runClientSideExecute("uploadStart()");
		try {
			Thread.sleep(sleepDuration);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		// super.runClientSideExecute("uploadEnd()");
	}

	public void runExtract() {
		try {
			DataExtractService s = new DataExtractService();
			s.extractQCTO("17", "test@a.com", new Date(), new Date());
			addInfoMessage("Done");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void clearTemps() {
		try {
			DataExtractService s = new DataExtractService();
			s.extractQCTO("17", "test@a.com", new Date(), new Date());
			addInfoMessage("Done");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public void testEmailFuntionality() {
		try {
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "TEST CONNECTION FROM PORTAL", "Test email to view if connections correct", null);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runOfoCodeMain() {
		try {
			if (ofoCodesService == null) {
				ofoCodesService = new OfoCodesService();
			}
			ofoCodesService.populateOfoCodeWithGroups();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runOfoCodeSpecial() {
		try {
			if (ofoCodesService == null) {
				ofoCodesService = new OfoCodesService();
			}
			ofoCodesService.populateOfoCodeSpecials();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * NRLD FILES
	 */
	public void extractNLRDFile21Bean() {
		try {
//			nlrdFile21Bean = nrldService.extractNLRDFile21Bean();
//			List<NLRDFile21BeanProviderVersionTwo> bean = nrldService.extractNLRDFile21BeanVersionTwo();
			List<NLRDFile21BeanProviderVersionTwo> bean = nrldService.extractNLRDFile21BeanVersionTwoSqlPassed();
			if (!bean.isEmpty()) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Provider File (21)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS21" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Provider File (21)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading;
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS21" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
//	nlrdFile21BeanProviderVersionTwo

	public void extractNLRDFile21Insert() {
		try {
			int count = nlrdFile21Service.extractNLRDFile21Insert();
			extractNLRDFile21Validation(nlrdFile21Service.allNLRDFile21());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile21Validation(List<NLRDFile21> nlrdFile21) {
		try {
			nlrdFile21Service.extractNLRDFile21Validation(nlrdFile21);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile22Bean() {
		try {
			nlrdFile22Bean = nrldService.extractNLRDFile22Bean();
			if (nlrdFile22Bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(nlrdFile22Bean.size());
				headerBean.setFileDescription("Qualification/Degree (Legacy) (File 22)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "\n" + CSVUtil.writeFixedLength(nlrdFile22Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS22" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile23Bean() {
		try {
			nlrdFile23Bean = nrldService.extractNLRDFile23Bean();
			if (nlrdFile23Bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(nlrdFile23Bean.size());
				headerBean.setFileDescription("Course (Legacy) (File 23)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "\n" + CSVUtil.writeFixedLength(nlrdFile23Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS23" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile24Bean() {
		try {
//			nlrdFile24Bean = nrldService.extractNLRDFile24Bean();
//			List<NLRDFile24BeanProviderAccreditationVersionTwo> bean = nrldService.extractNLRDFile24BeanVersionTwo();
			List<NLRDFile24BeanProviderAccreditationVersionTwo> bean = nrldService.extractNLRDFile24BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Provider Accreditation (File 24)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS24" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Provider Accreditation (File 24)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading;
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS24" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile24Insert() {
		try {
			int count = nlrdFile24Service.extractNLRDFile24Insert();
			extractNLRDFile24Validation(nlrdFile24Service.allNLRDFile24());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile24Validation(List<NLRDFile24> nlrdFile24) {
		try {
			nlrdFile24Service.extractNLRDFile24Validation(nlrdFile24);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile25Bean() {
		try {
//			nlrdFile25Bean = nrldService.extractNLRDFile25Bean();
//			List<NLRDFile25BeanPersonInformationVersionTwo> bean = nrldService.extractNLRDFile25BeanVersionTwo();
			List<NLRDFile25BeanPersonInformationVersionTwo> bean = nrldService.extractNLRDFile25BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Person Information (File 25)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS25" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Person Information (File 25)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS25" + currentDateNrld + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile25Insert() {
		try {
			int count = nlrdFile25Service.extractNLRDFile25Insert();
			extractNLRDFile25Validation(nlrdFile25Service.allNLRDFile25());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile25Validation(List<NLRDFile25> nlrdFile25) {
		try {
			nlrdFile25Service.extractNLRDFile25Validation(nlrdFile25);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile26Bean() {
		try {
//			nlrdFile26Bean = nrldService.extractNLRDFile26Bean();
//			List<NLRDFile26BeanPersonDesignationVersionTwo> bean = nrldService.extractNLRDFile26BeanVersionTwo();
			List<NLRDFile26BeanPersonDesignationVersionTwo> bean = nrldService.extractNLRDFile26BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("File Name (File 26)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS26" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("File Name (File 26)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS26" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile26Insert() {
		try {
			int count = nlrdFile26Service.extractNLRDFile26Insert();
			extractNLRDFile26Validation(nlrdFile26Service.allNLRDFile26());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile26Validation(List<NLRDFile26> nlrdFile26) {
		try {
			nlrdFile26Service.extractNLRDFile26Validation(nlrdFile26);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile27Bean() {
		try {
//			nlrdFile27Bean = nrldService.extractNLRDFile27Bean();
//			List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> bean = nrldService.extractNLRDFile27BeanVersionTwo();
			List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> bean = nrldService.extractNLRDFile27BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("NQF Designation registration (File 27)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS27" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("NQF Designation registration (File 27)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS27" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile27Insert() {
		try {
			int count = nlrdFile27Service.extractNLRDFile27Insert();
			extractNLRDFile27Validation(nlrdFile27Service.allNLRDFile27());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile27Validation(List<NLRDFile27> nlrdFile27) {
		try {
			int count = nrldService.extractNLRDFile27Validation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile28Bean() {
		try {
//			nlrdFile28Bean = nrldService.extractNLRDFile28Bean();
//			List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile28BeanVersionTwo();
			List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile28BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Learnership Enrolment/Achievement (File 28)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS28" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Learnership Enrolment/Achievement (File 28)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + " ";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS28" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile28Insert() {
		try {
			int count = nlrdFile28Service.extractNLRDFile28Insert();
			extractNLRDFile28Validation(nlrdFile28Service.allNLRDFile28());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile28Validation(List<NLRDFile28> nlrdFile28) {
		try {
			nlrdFile28Service.extractNLRDFile28Validation(nlrdFile28);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile29Bean() {
		try {
//			nlrdFile29Bean = nrldService.extractNLRDFile29Bean();
//			 List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile29BeanVersionTwo();
			List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile29BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Qualification Enrolment/Achievement (File 29)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS29" + currentDateNrld + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Qualification Enrolment/Achievement (File 29)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading ;
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS29" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile29Insert() {
		try {
			int count = nlrdFile29Service.extractNLRDFile29Insert();
			extractNLRDFile29Validation(nlrdFile29Service.allNLRDFile29());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile29Validation(List<NLRDFile29> nlrdFile29) {
		try {
			int count = nrldService.extractNLRDFile29Validation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile30Bean() {
		try {
//			nlrdFile30Bean = nrldService.extractNLRDFile30Bean();
//			List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile30BeanVersionTwo();
			List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> bean = nrldService.extractNLRDFile30BeanVersionTwoSqlPassed();
			if (bean.size() > 0) {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(bean.size());
				headerBean.setFileDescription("Unit Standard Enrolment/Achievement (File 30)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "" + CSVUtil.writeFixedLength(bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS30" + currentDateNrld + ".dat", "text/plain");
			} else {
				headerBeanList = new ArrayList<>();
				headerBean = new HeaderBean();
				headerBean.setNumberOfRecords(0);
				headerBean.setFileDescription("Unit Standard Enrolment/Achievement (File 30)");
				headerBean.setFiller("");
				headerBeanList.add(headerBean);
				String heading = CSVUtil.writeFixedLength(headerBeanList);
				String csv     = heading + "";
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS30" + currentDateNrld + ".dat", "text/plain");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile30Insert() {
		try {
			int count = nlrdFile30Service.extractNLRDFile30Insert();
			extractNLRDFile30Validation(nlrdFile30Service.allNLRDFile30());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractNLRDFile30Validation(List<NLRDFile30> nlrdFile30) {
		try {
			nlrdFile30Service.extractNLRDFile30Validation(nlrdFile30);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * QCTO FILES
	 */
	public void extractQCTO01Bean() {
		try {
			qcto01Bean = qctoService.extractQCTO01Bean(company);
			if (qcto01Bean.size() > 0) {
				String csv = CSVUtil.writeFixedLength(qcto01Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "QCTO01-" + company.getAccreditationNumber() + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractQCTO02Bean() {
		try {
			qcto02Bean = qctoService.extractQCTO02Bean(company);
			if (qcto02Bean.size() > 0) {
				String csv = CSVUtil.writeFixedLength(qcto02Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "QCTO02-" + company.getAccreditationNumber() + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractQCTO03Bean() {
		try {
			qcto03Bean = qctoService.extractQCTO03Bean(company);
			if (qcto03Bean.size() > 0) {
				String csv = CSVUtil.writeFixedLength(qcto03Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "QCTO03-" + company.getAccreditationNumber() + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * SETMIS FILES
	 */
	public void extractSETMISFile100Bean() {
		try {
			setmisFile100Bean = setmisFile100Service.extractSETMISFile100Bean();
			String csv = "";
			if (!setmisFile100Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile100Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export but file donwloaded");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile100BeanVersionTwo() {
		try {
//			setmisFile100BeanVersionTwo = setmisFile100Service.extractSETMISFile100BeanVersionTwo();
			setmisFile100BeanVersionTwo = setmisFile100Service.extractSETMISFile100BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile100BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile100BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_100_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export but file donwloaded");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile100Insert() {
		try {
			int count = setmisFile100Service.extractSetmisFile100Insert();
			extractSetmisFile100Validation(setmisFile100Service.allSetmisFile100());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile100Validation(List<SetmisFile100> setmisFile100) {
		try {
			setmisFile100Service.extractSetmisFile100Validation(setmisFile100);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runExport() {
		try {
			setmisFile100Service.runExport();
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile200Bean() {
		try {
			setmisFile200Bean = setmisService.extractSETMISFile200Bean();
			String csv = "";
			if (!setmisFile200Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile200Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile200BeanVersionTwo() {
		try {
//			setmisFile200BeanVersionTwo = setmisService.extractSETMISFile200BeanVersionTwo();
			setmisFile200BeanVersionTwo = setmisService.extractSETMISFile200BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile200BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile200BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_200_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile200Insert() {
		try {
			int count = setmisFile200Service.extractSetmisFile200Insert();
			extractSetmisFile200Validation(setmisFile200Service.allSetmisFile200());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile200Validation(List<SetmisFile200> setmisFile200) {
		try {
			setmisFile200Service.extractSetmisFile200Validation(setmisFile200);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile304Bean() {
		try {
//			setmisFile304Bean = setmisService.extractSETMISFile304Bean();
//			setmisFile304Bean = setmisService.extractSETMISFile304BeanVersionTwo();
			setmisFile304Bean = setmisService.extractSETMISFile304BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile304Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile304Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_304_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_304_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile304Insert() {
		try {
			int count = setmisFile304Service.extractSetmisFile304Insert();
			extractSetmisFile304Validation(setmisFile304Service.allSetmisFile304());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile304Validation(List<SetmisFile304> setmisFile304) {
		try {
			setmisFile304Service.extractSetmisFile304Validation(setmisFile304);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile400Bean() {
		try {
			setmisFile400Bean = setmisService.extractSETMISFile400Bean();
			String csv = "";
			if (!setmisFile400Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile400Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile400BeanVersionTwo() {
		try {
//			setmisFile400BeanVersionTwo = setmisService.extractSETMISFile400BeanVersionTwo();
			setmisFile400BeanVersionTwo = setmisService.extractSETMISFile400BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile400BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile400BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_400_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	

	public void extractSetmisFile400Insert() {
		try {
			int count = setmisFile400Service.extractSetmisFile400Insert();
			extractSetmisFile400Validation(setmisFile400Service.allSetmisFile400());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile400Validation(List<SetmisFile400> setmisFile400) {
		try {
			setmisFile400Service.extractSetmisFile400Validation(setmisFile400);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile401Bean() {
		try {
			setmisFile401Bean = setmisService.extractSETMISFile401Bean();
			String csv = "" ;
			if (!setmisFile401Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile401Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile401BeanVersionTwo() {
		try {
//			setmisFile401Bean = setmisService.extractSETMISFile401BeanVersionTwo();
			setmisFile401Bean = setmisService.extractSETMISFile401BeanVersionTwoSqlPassed();
			String csv = "" ;
			if (!setmisFile401Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile401Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_401_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile401Insert() {
		try {
			int count = setmisFile401Service.extractSetmisFile401Insert();
			extractSetmisFile401Validation(setmisFile401Service.allSetmisFile401());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile401Validation(List<SetmisFile401> setmisFile401) {
		try {
			setmisFile401Service.extractSetmisFile401Validation(setmisFile401);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile500Bean() {
		try {
			setmisFile500Bean = setmisService.extractSETMISFile500Bean();
			String csv = "";
			if (!setmisFile500Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile500Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile500BeanVersionTwo() {
		try {
//			setmisFile500Bean = setmisService.extractSETMISFile500BeanVersionTwo();
			setmisFile500Bean = setmisService.extractSETMISFile500BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile500Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile500Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_500_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile500Insert() {
		try {
			int count = setmisFile500Service.extractSetmisFile500Insert();
			extractSetmisFile500Validation(setmisFile500Service.allSetmisFile500());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile500Validation(List<SetmisFile500> setmisFile500) {
		try {
			setmisFile500Service.extractSetmisFile500Validation(setmisFile500);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile501Bean() {
		try {
			setmisFile501Bean = setmisService.extractSETMISFile501Bean();
			String csv = "";
			if (!setmisFile501Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile501Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile501BeanVersionTwo() {
		try {
//			setmisFile501Bean = setmisService.extractSETMISFile501BeanVersionTwo();
			setmisFile501Bean = setmisService.extractSETMISFile501BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile501Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile501Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_501_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile501Insert() {
		try {
			int count = setmisFile501Service.extractSetmisFile501Insert();
			extractSetmisFile501Validation(setmisFile501Service.allSetmisFile501());
		} catch (Exception e) {
			logger.fatal(e);
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile501Validation(List<SetmisFile501> setmisFile501) {
		try {
			setmisFile501Service.extractSetmisFile501Validation(setmisFile501);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile502Bean() {
		try {
			setmisFile502Bean = setmisService.extractSETMISFile502Bean();
			String csv = "";
			if (!setmisFile502Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile502Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile502BeanVersionTwo() {
		try {
//			setmisFile502Bean = setmisService.extractSETMISFile502BeanVersionTwo();
			setmisFile502Bean = setmisService.extractSETMISFile502BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile502Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile502Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_502_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile502Insert() {
		try {
			int count = setmisFile502Service.extractSetmisFile502Insert();
			extractSetmisFile502Validation(setmisFile502Service.allSetmisFile502());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile502Validation(List<SetmisFile502> setmisFile502) {
		try {
			setmisFile502Service.extractSetmisFile502Validation(setmisFile502);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile503Bean() {
		try {
			setmisFile503Bean = setmisService.extractSETMISFile503Bean();
			String csv = "";
			if (!setmisFile503Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile503Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile503BeanVersionTwo() {
		try {
//			setmisFile503BeanVersionTwo = setmisService.extractSETMISFile503BeanVersionTwo();
			setmisFile503BeanVersionTwo = setmisService.extractSETMISFile503BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile503BeanVersionTwo.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile503BeanVersionTwo);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_503_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile503Insert() {
		try {
			int count = setmisFile503Service.extractSetmisFile503Insert();
			extractSetmisFile503Validation(setmisFile503Service.allSetmisFile503());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile503Validation(List<SetmisFile503> setmisFile503) {
		try {
			setmisFile503Service.extractSetmisFile503Validation(setmisFile503);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile505Bean() {
		try {
			setmisFile505Bean = setmisService.extractSETMISFile505Bean();
			String csv = "";
			if (!setmisFile505Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile505Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile505BeanVersionTwo() {
		try {
//			setmisFile505Bean = setmisService.extractSETMISFile505BeanVersionTwo();
//			setmisFile505Bean = setmisService.extractSETMISFile505BeanVersionTwoSqlPassed();
			setmisFile505Bean = new ArrayList<>();
			String csv = "";
			if (!setmisFile505Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile505Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_505_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile505Insert() {
		try {
			int count = setmisFile505Service.extractSetmisFile505Insert();
			extractSetmisFile505Validation(setmisFile505Service.allSetmisFile505());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile505Validation(List<SetmisFile505> setmisFile505) {
		try {
			setmisFile505Service.extractSetmisFile505Validation(setmisFile505);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile506Bean() {
		try {
			setmisFile506Bean = setmisService.extractSETMISFile506Bean();
			String csv = "";
			if (!setmisFile506Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile506Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extractSETMISFile506BeanVersionTwo() {
		try {
//			setmisFile506Bean = setmisService.extractSETMISFile506BeanVersionTwo();
			setmisFile506Bean = setmisService.extractSETMISFile506BeanVersionTwoSqlPassed();
			String csv = "";
			if (!setmisFile506Bean.isEmpty()) {
				csv = CSVUtil.writeFixedLength(setmisFile506Bean);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSetmisFile506Insert() {
		try {
			int count = setmisFile506Service.extractSetmisFile506Insert();
			extractSetmisFile506Validation(setmisFile506Service.allSetmisFile506());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void extractSetmisFile506Validation(List<SetmisFile506> setmisFile506) {
		try {
			setmisFile506Service.extractSetmisFile506Validation(setmisFile506);
			addInfoMessage("Validation Complete!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extractSETMISFile506BeanSandra() {
		try {
			setSetmisFile506BeanSandra(setmisService.extractSETMISFile506BeanSandra());
			if (setmisFile506BeanSandra.size() > 0) {
				String csv = CSVUtil.writeFixedLength(setmisFile506BeanSandra);
				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "MERS_0006_506_v001_" + currentDate + ".dat", "text/plain");
				addInfoMessage("Extract Complete!");
			} else {
				addWarningMessage("No data to export");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void testArchive() {
		try {
			MandatoryGrantDetailArchiveService mandatoryGrantDetailArchiveService = new MandatoryGrantDetailArchiveService();
			mandatoryGrantDetailArchiveService.test(2l);
			System.out.println("completed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runScheduler() {
		try {
			ScheduleService scheduleService = new ScheduleService();
			scheduleService.runSchedule();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void testCopy() {
		try {
			// Company company = companyServicea.findByKey(46632l);
			// WspCompanyHistory wspCompanyHistory = new WspCompanyHistory();
			// org.apache.commons.beanutils.BeanUtils.copyProperties(wspCompanyHistory,
			// company);
			// WspCompanyHistoryService wspCompanyHistoryService = new
			// WspCompanyHistoryService();
			Company company = companyServicea.findByKey(46632l);
			// WspCompanyHistoryCreateService.instance().populateWspCompanyHistory(company,
			// wsp);
			WspCompanyHistoryCreateService.instance().populateWspCompanyHistory(company, Wsp.class.getName(), 118l);
			addInfoMessage("Test Underway");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void testDelete() {
		try {
			WspCompanyHistoryCreateService.instance().clearExistingEntriesByTargetClassAndKey(Wsp.class.getName(), 118l);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<Company> getAllTrainingProvidersList() {
		return allTrainingProvidersList;
	}

	public void setAllTrainingProvidersList(List<Company> allTrainingProvidersList) {
		this.allTrainingProvidersList = allTrainingProvidersList;
	}

	public List<NLRDFile21Bean> getNlrdFile21Bean() {
		return nlrdFile21Bean;
	}

	public void setNlrdFile21Bean(List<NLRDFile21Bean> nlrdFile21Bean) {
		this.nlrdFile21Bean = nlrdFile21Bean;
	}

	public List<NLRDFile22Bean> getNlrdFile22Bean() {
		return nlrdFile22Bean;
	}

	public void setNlrdFile22Bean(List<NLRDFile22Bean> nlrdFile22Bean) {
		this.nlrdFile22Bean = nlrdFile22Bean;
	}

	public List<NLRDFile23Bean> getNlrdFile23Bean() {
		return nlrdFile23Bean;
	}

	public void setNlrdFile23Bean(List<NLRDFile23Bean> nlrdFile23Bean) {
		this.nlrdFile23Bean = nlrdFile23Bean;
	}

	public List<NLRDFile24Bean> getNlrdFile24Bean() {
		return nlrdFile24Bean;
	}

	public void setNlrdFile24Bean(List<NLRDFile24Bean> nlrdFile24Bean) {
		this.nlrdFile24Bean = nlrdFile24Bean;
	}

	public List<NLRDFile25Bean> getNlrdFile25Bean() {
		return nlrdFile25Bean;
	}

	public void setNlrdFile25Bean(List<NLRDFile25Bean> nlrdFile25Bean) {
		this.nlrdFile25Bean = nlrdFile25Bean;
	}

	public List<NLRDFile26Bean> getNlrdFile26Bean() {
		return nlrdFile26Bean;
	}

	public void setNlrdFile26Bean(List<NLRDFile26Bean> nlrdFile26Bean) {
		this.nlrdFile26Bean = nlrdFile26Bean;
	}

	public List<NLRDFile27Bean> getNlrdFile27Bean() {
		return nlrdFile27Bean;
	}

	public void setNlrdFile27Bean(List<NLRDFile27Bean> nlrdFile27Bean) {
		this.nlrdFile27Bean = nlrdFile27Bean;
	}

	public List<NLRDFile28Bean> getNlrdFile28Bean() {
		return nlrdFile28Bean;
	}

	public void setNlrdFile28Bean(List<NLRDFile28Bean> nlrdFile28Bean) {
		this.nlrdFile28Bean = nlrdFile28Bean;
	}

	public List<NLRDFile29Bean> getNlrdFile29Bean() {
		return nlrdFile29Bean;
	}

	public void setNlrdFile29Bean(List<NLRDFile29Bean> nlrdFile29Bean) {
		this.nlrdFile29Bean = nlrdFile29Bean;
	}

	public List<NLRDFile30Bean> getNlrdFile30Bean() {
		return nlrdFile30Bean;
	}

	public void setNlrdFile30Bean(List<NLRDFile30Bean> nlrdFile30Bean) {
		this.nlrdFile30Bean = nlrdFile30Bean;
	}

	public List<QCTO01Bean> getQcto01Bean() {
		return qcto01Bean;
	}

	public void setQcto01Bean(List<QCTO01Bean> qcto01Bean) {
		this.qcto01Bean = qcto01Bean;
	}

	public List<QCTO02Bean> getQcto02Bean() {
		return qcto02Bean;
	}

	public void setQcto02Bean(List<QCTO02Bean> qcto02Bean) {
		this.qcto02Bean = qcto02Bean;
	}

	public List<QCTO03Bean> getQcto03Bean() {
		return qcto03Bean;
	}

	public void setQcto03Bean(List<QCTO03Bean> qcto03Bean) {
		this.qcto03Bean = qcto03Bean;
	}

	public List<SETMISFile100Bean> getSetmisFile100Bean() {
		return setmisFile100Bean;
	}

	public void setSetmisFile100Bean(List<SETMISFile100Bean> setmisFile100Bean) {
		this.setmisFile100Bean = setmisFile100Bean;
	}

	public List<SETMISFile200Bean> getSetmisFile200Bean() {
		return setmisFile200Bean;
	}

	public void setSetmisFile200Bean(List<SETMISFile200Bean> setmisFile200Bean) {
		this.setmisFile200Bean = setmisFile200Bean;
	}

	public List<SETMISFile304Bean> getSetmisFile304Bean() {
		return setmisFile304Bean;
	}

	public void setSetmisFile304Bean(List<SETMISFile304Bean> setmisFile304Bean) {
		this.setmisFile304Bean = setmisFile304Bean;
	}

	public List<SETMISFile400Bean> getSetmisFile400Bean() {
		return setmisFile400Bean;
	}

	public void setSetmisFile400Bean(List<SETMISFile400Bean> setmisFile400Bean) {
		this.setmisFile400Bean = setmisFile400Bean;
	}

	public List<SETMISFile401Bean> getSetmisFile401Bean() {
		return setmisFile401Bean;
	}

	public void setSetmisFile401Bean(List<SETMISFile401Bean> setmisFile401Bean) {
		this.setmisFile401Bean = setmisFile401Bean;
	}

	public List<SETMISFile500Bean> getSetmisFile500Bean() {
		return setmisFile500Bean;
	}

	public void setSetmisFile500Bean(List<SETMISFile500Bean> setmisFile500Bean) {
		this.setmisFile500Bean = setmisFile500Bean;
	}

	public List<SETMISFile501Bean> getSetmisFile501Bean() {
		return setmisFile501Bean;
	}

	public void setSetmisFile501Bean(List<SETMISFile501Bean> setmisFile501Bean) {
		this.setmisFile501Bean = setmisFile501Bean;
	}

	public List<SETMISFile502Bean> getSetmisFile502Bean() {
		return setmisFile502Bean;
	}

	public void setSetmisFile502Bean(List<SETMISFile502Bean> setmisFile502Bean) {
		this.setmisFile502Bean = setmisFile502Bean;
	}

	public List<SETMISFile503Bean> getSetmisFile503Bean() {
		return setmisFile503Bean;
	}

	public void setSetmisFile503Bean(List<SETMISFile503Bean> setmisFile503Bean) {
		this.setmisFile503Bean = setmisFile503Bean;
	}

	public List<SETMISFile505Bean> getSetmisFile505Bean() {
		return setmisFile505Bean;
	}

	public void setSetmisFile505Bean(List<SETMISFile505Bean> setmisFile505Bean) {
		this.setmisFile505Bean = setmisFile505Bean;
	}

	public List<SETMISFile506Bean> getSetmisFile506Bean() {
		return setmisFile506Bean;
	}

	public void setSetmisFile506Bean(List<SETMISFile506Bean> setmisFile506Bean) {
		this.setmisFile506Bean = setmisFile506Bean;
	}

	public List<HeaderBean> getHeaderBeanList() {
		return headerBeanList;
	}

	public void setHeaderBeanList(List<HeaderBean> headerBeanList) {
		this.headerBeanList = headerBeanList;
	}

	public void sarsNotification() {
		try {
			SarsLoadLevies   s     = new SarsLoadLevies();
			SimpleDateFormat sdf   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date             adate = sdf.parse("2018-12-01 00:00:00");
			s.notifyParties(adate);
			//
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void testExcelGeneration() {
		try {
			reportGenerationService.generateForeCastForAllWspByFinYear(2019);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void allcoationGenerationFix() {
		try {
			DgAllocationService allocationService = new DgAllocationService();
			allocationService.onceOffFix();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void testTpByRegion() {
		try {
			RegionService regionService = new RegionService();
			List<Region>  allRegions    = regionService.allRegion();
			for (Region region : allRegions) {
				List<Company> companiesFound = trainingProviderApplicationService.testTpApplicationByRegionId(region.getId());
				System.out.println(region.getDescription() + " - Count: " + companiesFound.size());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<SETMISFile506BeanSandra> getSetmisFile506BeanSandra() {
		return setmisFile506BeanSandra;
	}

	public void setSetmisFile506BeanSandra(List<SETMISFile506BeanSandra> setmisFile506BeanSandra) {
		this.setmisFile506BeanSandra = setmisFile506BeanSandra;
	}

	private LegacyDataService legacyDataService = new LegacyDataService();

	public void downloadAllLegacyData() {
		new Thread(() -> {
			legacyDataService.downlaodAllData();
		}).start();
	}

	public void testArplEmailReminders() {
		try {
			CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
//			companyLearnersTradeTestService.arplEmailReminderService();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runSiteNumberGeneration() {
		try {
			companyServicea.updateNonLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runSiteNumberLGeneration() {
		try {
			companyServicea.updateLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runSiteNumberGenerationSites() {
		try {
			sitesService.updateSiteNumbersWhereNoSiteNumberAssigned();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runTestServiceUserValidiation(){
		try {
			testService.checkUserValidation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runTestServiceCompanyValidiation(){
		try {
			testService.checkCompanyValidiation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runTestServiceAddressValidiation(){
		try {
			testService.checkAddressValidiation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runAllExecptions(){
		new Thread(() -> {
			testService.runAll();
		}).start();
		addInfoMessage("Action Complete");
	}
	
	public void runTestActiveContract(){
		try {
			ActiveContractsService activeContractsService = new ActiveContractsService(); 
			activeContractsService.testDateAmount();
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
	
	public void runTestActiveContractService(){
		try {
			ActiveContractsService activeContractsService = new ActiveContractsService(); 
			activeContractsService.activeContractReminderSchedual(HAJConstants.sdfDDMMMMYYYY.parse("25 September 2019"));
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
	
	public void generateWorkflowContractExtensionRequest(){
		try {
			ActiveContractsService acs = new ActiveContractsService();
			ActiveContractExtensionRequestService acers = new ActiveContractExtensionRequestService();
			UsersService usersService = new UsersService();
			
			ActiveContracts ac = acs.findByKey(2788l);
			Users user = usersService.findByKey(1l);
			acers.requestExtensionRequestWorkflowByActiveContractTesting(ac, user);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testArplDaysBetweenDatesCheck(){
		try {
			CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
//			companyLearnersTradeTestService.testDateDifference();
			System.out.println(companyLearnersTradeTestService.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true,GenericUtility.getStartOfDay(new Date())));
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public void testGpTransactionsWS(){
		try {
			// String levyNumber = "L470713134";	
			// ArrayOfInfo info = GPTransactionsService.instance().getOutStandingTransactions(levyNumber);
			// for (Info infoEntry: info.getInfo()) {
			// 	System.out.println(infoEntry.getDocDescription());
			// }
			// ArrayOfRecentPayments payments = GPTransactionsService.instance().getRecentTransactions(levyNumber);
			// for (RecentPayments rp : payments.getRecentPayments()) {
			// 	System.out.println(rp);
			// }
//			ArrayOfInfo arrayOfInfo = GPTransactionsService.instance().getInfo(levyNumber);
//			ArrayOfRecentPayments arrayOfPayments = GPTransactionsService.instance().getPayments(levyNumber);
//			ArrayOfRecentAdjustments arrayOfAdjustments = GPTransactionsService.instance().getAjustments(levyNumber);
//			ArrayOfPaymentReturns arrayOfPaymentReturns = GPTransactionsService.instance().getPaymentReturns(levyNumber);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testReportGeneration(){
		try {
			MgVerificationService mgService = new MgVerificationService();
			List<MgVerificationReportBean> list = mgService.allMandatoryVerificationsByFinYearReport(2019);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			logger.fatal(e);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testReadInFiles(){
		try {
			ArchiveDocumentsService serviceTest = new ArchiveDocumentsService();
			List<String> paths = serviceTest.viewFilesInDirectory();
			for (String file : paths) {
				System.out.println(file);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void downloadFirstDocInArchive(){
		try {
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadLastDocInArchive(){
		try {
			ArchiveDocumentsService serviceTest = new ArchiveDocumentsService();
			List<String> paths = serviceTest.viewFilesInDirectory();
			for (String path : paths) {
				byte[] bytesArray = serviceTest.readBytesFromFile(path);
				Faces.sendFile(bytesArray, "protected.pdf", true);
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void testQMRInfo() {
		try {
			QmrReportingDAO qmrReportingDAO = new QmrReportingDAO();
			// test script one
			qmrReportingDAO.qmrScriptOneBeanExtractTestMethod();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void testQMRWithParamaters() {
		try {
			QmrReportingDAO qmrReportingDAO = new QmrReportingDAO();
			
			Date fromDate = GenericUtility.getStartOfDay(HAJConstants.sdf.parse("2019-11-25"));
			Date toDate = GenericUtility.getEndOfDay(HAJConstants.sdf.parse("2020-01-25"));
			
			QmrEnteredCompletedEnum entry = QmrEnteredCompletedEnum.Entered;
			
			List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = new ArrayList<>();
			qmrTypeSelectionEnumList.add(QmrTypeSelectionEnum.LEARNERSHIP);
			
			List<EmploymentStatusEnum> employmentStatusEnum = new ArrayList<>();
			employmentStatusEnum.add(EmploymentStatusEnum.Employed);
			
			List<LearnerStatusEnum> learnerStatusEnumList = new ArrayList<>();
			learnerStatusEnumList.add(LearnerStatusEnum.Registered);
			
			// test script one
			List<QmrScriptOneBean> resultSet =  qmrReportingDAO.qmrScriptOneGeneration("Y21Q1", entry, fromDate, toDate, qmrTypeSelectionEnumList, employmentStatusEnum, learnerStatusEnumList);
			addInfoMessage("Test Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testQMRGeneration() {
		try {
			QmrFinYearsService qmrFinYearsService = new QmrFinYearsService();
			QmrFinYears finYear = qmrFinYearsService.findByKey(9l);
			QmrLearnershipDataService qmrLearnershipDataService = new QmrLearnershipDataService();
			qmrLearnershipDataService.generateQmrLearnershipDataForQuater(finYear);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testGenQuarter(){
		try {
			QmrFinYearsService qmrFinYearsService = new QmrFinYearsService();
			QmrFinYears finYear = qmrFinYearsService.findByKey(9l);
			qmrFinYearsService.generateDataForQmrFinYear(finYear);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateOnScreenHelp(){
		try {
			OnScreenHelpService.instance().populateHelpTextTable();
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public List<SetmisFile100> getSetmisFile100() {
		return setmisFile100;
	}

	public void setSetmisFile100(List<SetmisFile100> setmisFile100) {
		this.setmisFile100 = setmisFile100;
	}

	public List<SetmisFile200> getSetmisFile200() {
		return setmisFile200;
	}

	public void setSetmisFile200(List<SetmisFile200> setmisFile200) {
		this.setmisFile200 = setmisFile200;
	}

	public List<SetmisFile304> getSetmisFile304() {
		return setmisFile304;
	}

	public void setSetmisFile304(List<SetmisFile304> setmisFile304) {
		this.setmisFile304 = setmisFile304;
	}

	public List<SetmisFile400> getSetmisFile400() {
		return setmisFile400;
	}

	public void setSetmisFile400(List<SetmisFile400> setmisFile400) {
		this.setmisFile400 = setmisFile400;
	}

	public List<SetmisFile401> getSetmisFile401() {
		return setmisFile401;
	}

	public void setSetmisFile401(List<SetmisFile401> setmisFile401) {
		this.setmisFile401 = setmisFile401;
	}

	public List<SetmisFile500> getSetmisFile500() {
		return setmisFile500;
	}

	public void setSetmisFile500(List<SetmisFile500> setmisFile500) {
		this.setmisFile500 = setmisFile500;
	}

	public List<SetmisFile501> getSetmisFile501() {
		return setmisFile501;
	}

	public void setSetmisFile501(List<SetmisFile501> setmisFile501) {
		this.setmisFile501 = setmisFile501;
	}

	public List<SetmisFile502> getSetmisFile502() {
		return setmisFile502;
	}

	public void setSetmisFile502(List<SetmisFile502> setmisFile502) {
		this.setmisFile502 = setmisFile502;
	}

	public List<SetmisFile503> getSetmisFile503() {
		return setmisFile503;
	}

	public void setSetmisFile503(List<SetmisFile503> setmisFile503) {
		this.setmisFile503 = setmisFile503;
	}

	public List<SetmisFile505> getSetmisFile505() {
		return setmisFile505;
	}

	public void setSetmisFile505(List<SetmisFile505> setmisFile505) {
		this.setmisFile505 = setmisFile505;
	}

	public List<SetmisFile506> getSetmisFile506() {
		return setmisFile506;
	}

	public void setSetmisFile506(List<SetmisFile506> setmisFile506) {
		this.setmisFile506 = setmisFile506;
	}

	public List<NLRDFile21> getNlrdFile21() {
		return nlrdFile21;
	}

	public void setNlrdFile21(List<NLRDFile21> nlrdFile21) {
		this.nlrdFile21 = nlrdFile21;
	}

	public List<NLRDFile22> getNlrdFile22() {
		return nlrdFile22;
	}

	public void setNlrdFile22(List<NLRDFile22> nlrdFile22) {
		this.nlrdFile22 = nlrdFile22;
	}

	public List<NLRDFile23> getNlrdFile23() {
		return nlrdFile23;
	}

	public void setNlrdFile23(List<NLRDFile23> nlrdFile23) {
		this.nlrdFile23 = nlrdFile23;
	}

	public List<NLRDFile24> getNlrdFile24() {
		return nlrdFile24;
	}

	public void setNlrdFile24(List<NLRDFile24> nlrdFile24) {
		this.nlrdFile24 = nlrdFile24;
	}

	public List<NLRDFile25> getNlrdFile25() {
		return nlrdFile25;
	}

	public void setNlrdFile25(List<NLRDFile25> nlrdFile25) {
		this.nlrdFile25 = nlrdFile25;
	}

	public List<NLRDFile26> getNlrdFile26() {
		return nlrdFile26;
	}

	public void setNlrdFile26(List<NLRDFile26> nlrdFile26) {
		this.nlrdFile26 = nlrdFile26;
	}

	public List<NLRDFile27> getNlrdFile27() {
		return nlrdFile27;
	}

	public void setNlrdFile27(List<NLRDFile27> nlrdFile27) {
		this.nlrdFile27 = nlrdFile27;
	}

	public List<NLRDFile28> getNlrdFile28() {
		return nlrdFile28;
	}

	public void setNlrdFile28(List<NLRDFile28> nlrdFile28) {
		this.nlrdFile28 = nlrdFile28;
	}

	public List<NLRDFile30> getNlrdFile30() {
		return nlrdFile30;
	}

	public void setNlrdFile30(List<NLRDFile30> nlrdFile30) {
		this.nlrdFile30 = nlrdFile30;
	}

	public List<NLRDFile29> getNlrdFile29() {
		return nlrdFile29;
	}

	public void setNlrdFile29(List<NLRDFile29> nlrdFile29) {
		this.nlrdFile29 = nlrdFile29;
	}

	public String getCurrentDateNrld() {
		return currentDateNrld;
	}

	public void setCurrentDateNrld(String currentDateNrld) {
		this.currentDateNrld = currentDateNrld;
	}

}