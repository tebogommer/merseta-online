package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.TrainingComittee;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TrainingComitteeHistoryService;
import haj.com.service.TrainingComitteeService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingComitteeUI.
 */
@ManagedBean(name = "trainingcomitteeUI")
@ViewScoped
public class TrainingComitteeUI extends AbstractUI {

	/** The service. */
	private TrainingComitteeService service = new TrainingComitteeService();

	/** The trainingcomittee list. */
	private List<TrainingComittee> trainingcomitteeList = null;

	/** The trainingcomitteefiltered list. */
	private List<TrainingComittee> trainingcomitteefilteredList = null;

	/** The trainingcomittee. */
	private TrainingComittee trainingcomittee = null;

	/** The company. */
	private Company company = null;

	/** The data model. */
	private LazyDataModel<TrainingComittee> dataModel;

	/** the haj constants */

	/** the maximum input length for email address */

	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** the maximum input length for rsa id number */

	private Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;

	/** the maximum input length for fax number */

	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** the maximum input length for passport number */

	private Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;

	/** the maximum input length for first name */

	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** the maximum input length for last name */

	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** the maximum input length address line 1-4 */

	private Long MAX_ADDRESS_LINE_SIZE = HAJConstants.MAX_ADDRESS_LINE_SIZE;

	/** the maximum input length address post code */

	private Long MAX_ADDRESS_CODE_SIZE = HAJConstants.MAX_ADDRESS_CODE_SIZE;

	/** the maximum input length company name */

	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** the maximum input length number of employees */

	private Long MAX_NUMBER_OF_EMPLOYEES_SIZE = HAJConstants.MAX_NUMBER_OF_EMPLOYEES_SIZE;

	/** the passport number format */

	private final String passportNumberFormat = HAJConstants.passportNumberFormat;

	/** the company levy number format */

	private String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;

	/** the telephone number format */

	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** the cellphone number format */

	private String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	/**
	 * Inits the.
	 */
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
	 * Initialize method to get all TrainingComittee and prepare a for a create
	 * of a new TrainingComittee.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingcomitteeInfo();
	}

	/**
	 * Get all TrainingComittee for data table.
	 *
	 * @author TechFinium
	 * @see TrainingComittee
	 */
	public void trainingcomitteeInfo() {

		dataModel = new LazyDataModel<TrainingComittee>() {

			private static final long serialVersionUID = 1L;
			private List<TrainingComittee> retorno = new ArrayList<TrainingComittee>();

			@Override
			public List<TrainingComittee> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {

					if (company != null) {
						filters.put("companyID", company.getId());
						retorno = service.allTrainingComittee(first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(TrainingComittee.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingComittee obj) {
				return obj.getId();
			}

			@Override
			public TrainingComittee getRowData(String rowKey) {
				for (TrainingComittee obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert TrainingComittee into database.
	 *
	 * @author TechFinium
	 * @see TrainingComittee
	 */
	public void trainingcomitteeInsert() {
		try {
			trainingcomittee.setCompany(company);
			service.create(this.trainingcomittee);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingcomitteeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createTrainingComAndCreateTask() {
		try {
			if (trainingcomittee.getCompany() == null) {
				trainingcomittee.setCompany(company);
			}
			service.checkByCriteria(this.trainingcomittee,this.trainingcomittee.getCompany());
			if (trainingcomittee.getId() == null) {
				trainingcomittee.setApprovalStatus(ApprovalEnum.PendingApproval);
				
				service.createTrainingComAndTask(this.trainingcomittee, getSessionUI().getActiveUser());
			} else {
				// Creating Training Committee History
				TrainingComitteeHistoryService.instance().createHistory(trainingcomittee.getId());
				trainingcomittee.setApprovalStatus(ApprovalEnum.PendingApproval);
				service.updateTrainingComAndTask(this.trainingcomittee, getSessionUI().getActiveUser());
			}
			// Clearing Change reason
			CompanyInfoUI.changeReason = new ChangeReason();
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingcomitteeInfo();
			super.runClientSideExecute("uploadDone()");
			runClientSideExecute("PF('dlgtrainingComittee').hide(), updateInfo()");
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update TrainingComittee in database.
	 *
	 * @author TechFinium
	 * @see TrainingComittee
	 */
	public void trainingcomitteeUpdate() {
		try {
			service.update(this.trainingcomittee);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingcomitteeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete TrainingComittee from database.
	 *
	 * @author TechFinium
	 * @see TrainingComittee
	 */
	public void trainingcomitteeDelete() {
		try {
			service.delete(this.trainingcomittee);
			prepareNew();
			trainingcomitteeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void applyIDData() {
		try {
			GenericUtility.calcIDData(this.trainingcomittee);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Create new instance of TrainingComittee.
	 *
	 * @author TechFinium
	 * @see TrainingComittee
	 */
	public void prepareNew() {
		trainingcomittee = new TrainingComittee();
	}

	/*
	 * public List<SelectItem> getTrainingComitteeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * trainingcomitteeInfo(); for (TrainingComittee ug : trainingcomitteeList)
	 * { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<TrainingComittee> complete(String desc) {
		List<TrainingComittee> l = null;
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
	 * Gets the training comittee list.
	 *
	 * @return the training comittee list
	 */
	public List<TrainingComittee> getTrainingComitteeList() {
		return trainingcomitteeList;
	}

	/**
	 * Sets the training comittee list.
	 *
	 * @param trainingcomitteeList
	 *            the new training comittee list
	 */
	public void setTrainingComitteeList(List<TrainingComittee> trainingcomitteeList) {
		this.trainingcomitteeList = trainingcomitteeList;
	}

	/**
	 * Gets the trainingcomittee.
	 *
	 * @return the trainingcomittee
	 */
	public TrainingComittee getTrainingcomittee() {
		return trainingcomittee;
	}

	/**
	 * Sets the trainingcomittee.
	 *
	 * @param trainingcomittee
	 *            the new trainingcomittee
	 */
	public void setTrainingcomittee(TrainingComittee trainingcomittee) {
		this.trainingcomittee = trainingcomittee;
	}

	/**
	 * Gets the training comitteefiltered list.
	 *
	 * @return the training comitteefiltered list
	 */
	public List<TrainingComittee> getTrainingComitteefilteredList() {
		return trainingcomitteefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param trainingcomitteefilteredList
	 *            the new trainingcomitteefilteredList list
	 * @see TrainingComittee
	 */
	public void setTrainingComitteefilteredList(List<TrainingComittee> trainingcomitteefilteredList) {
		this.trainingcomitteefilteredList = trainingcomitteefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TrainingComittee> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<TrainingComittee> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public Long getMAX_FAX_NUMBER() {
		return MAX_FAX_NUMBER;
	}

	public void setMAX_FAX_NUMBER(Long mAX_FAX_NUMBER) {
		MAX_FAX_NUMBER = mAX_FAX_NUMBER;
	}

	public Long getMAX_NUMBER_OF_EMPLOYEES_SIZE() {
		return MAX_NUMBER_OF_EMPLOYEES_SIZE;
	}

	public void setMAX_NUMBER_OF_EMPLOYEES_SIZE(Long mAX_NUMBER_OF_EMPLOYEES_SIZE) {
		MAX_NUMBER_OF_EMPLOYEES_SIZE = mAX_NUMBER_OF_EMPLOYEES_SIZE;
	}

	public Long getMAX_ADDRESS_LINE_SIZE() {
		return MAX_ADDRESS_LINE_SIZE;
	}

	public void setMAX_ADDRESS_LINE_SIZE(Long mAX_ADDRESS_LINE_SIZE) {
		MAX_ADDRESS_LINE_SIZE = mAX_ADDRESS_LINE_SIZE;
	}

	public Long getMAX_ADDRESS_CODE_SIZE() {
		return MAX_ADDRESS_CODE_SIZE;
	}

	public void setMAX_ADDRESS_CODE_SIZE(Long mAX_ADDRESS_CODE_SIZE) {
		MAX_ADDRESS_CODE_SIZE = mAX_ADDRESS_CODE_SIZE;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public Long getMAX_COMPANY_NAME_SIZE() {
		return MAX_COMPANY_NAME_SIZE;
	}

	public void setMAX_COMPANY_NAME_SIZE(Long mAX_COMPANY_NAME_SIZE) {
		MAX_COMPANY_NAME_SIZE = mAX_COMPANY_NAME_SIZE;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
		CELLPHONE_FORMAT = cELLPHONE_FORMAT;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCompanyLevyNumberFormat() {
		return companyLevyNumberFormat;
	}

	public void setCompanyLevyNumberFormat(String companyLevyNumberFormat) {
		this.companyLevyNumberFormat = companyLevyNumberFormat;
	}

	public Long getMAX_LAST_NAME_SIZE() {
		return MAX_LAST_NAME_SIZE;
	}

	public void setMAX_LAST_NAME_SIZE(Long mAX_LAST_NAME_SIZE) {
		MAX_LAST_NAME_SIZE = mAX_LAST_NAME_SIZE;
	}

	public Long getMAX_FIRST_NAME_SIZE() {
		return MAX_FIRST_NAME_SIZE;
	}

	public void setMAX_FIRST_NAME_SIZE(Long mAX_FIRST_NAME_SIZE) {
		MAX_FIRST_NAME_SIZE = mAX_FIRST_NAME_SIZE;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

}
