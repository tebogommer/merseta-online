package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.lookup.PostCodeLink;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UnitStandardsService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.QualificationUnitStandardsService;
import haj.com.utils.CSVUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationUI.
 */
@ManagedBean(name = "qualificationUI")
@ViewScoped
public class QualificationUI extends AbstractUI {

	/** The service. */
	private QualificationService service = new QualificationService();
	
	private QualificationUnitStandardsService qualificationUnitStandardsService = new QualificationUnitStandardsService();

	/** The qualification list. */
	private List<Qualification> qualificationList = null;

	/** The qualificationfiltered list. */
	private List<Qualification> qualificationfilteredList = null;

	/** The qualification. */
	private Qualification qualification = null;

	/** The data model. */
	private LazyDataModel<Qualification> dataModel;
	
	private UnitStandardTypeEnum unitStandardTypeEnum= null;
	
	private UnitStandards selectedUnitStandard = null;
	
	private List<QualificationUnitStandards> qualificationUnitStandardsList = new ArrayList<>();

	private  QualificationUnitStandards qualificationUnitStandards;
	
	/* Util */
	private CSVUtil csvUtil = new CSVUtil();
	private String csvTypeSelection;
	private List<String> csvTypeSelectionList = new ArrayList<>();
	
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
	 * Initialize method to get all Qualification and prepare a for a create of
	 * a new Qualification.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	private void runInit() throws Exception {
		prepareNew();
		qualificationInfo();
		prepTypeSelection();
	}

	/**
	 * Get all Qualification for data table.
	 *
	 * @author TechFinium
	 * @see Qualification
	 */
	public void qualificationInfo() {

		dataModel = new LazyDataModel<Qualification>() {

			private static final long serialVersionUID = 1L;
			private List<Qualification> retorno = new ArrayList<Qualification>();

			@Override
			public List<Qualification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allQualification(Qualification.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(Qualification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Qualification obj) {
				return obj.getId();
			}

			@Override
			public Qualification getRowData(String rowKey) {
				for (Qualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}
	
	public void prepUnitStandardAdd(){
		try {			
			qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(qualification);
			qualificationUnitStandards = new  QualificationUnitStandards();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert Qualification into database.
	 *
	 * @author TechFinium
	 * @see Qualification
	 */
	public void qualificationInsert() {
		try {
			service.create(this.qualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			qualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addUnitStandard(){
		try {
			
			// make sure unit standard is selected
			if (qualificationUnitStandards.getUnitStandards() == null || qualificationUnitStandards.getUnitStandards().getId() == null) {
				throw new Exception("Select A Unit Standard before Proceeding");
			}
			
			// check if already assigned
			if (qualificationUnitStandardsService.countByUnitStandardAndQualification(qualificationUnitStandards.getUnitStandards(), qualification) > 0) {
				//selectedUnitStandard = null;
				//throw new Exception("Unit Standrd Already Assigned To Learnership");
			}
			
			// create new link
			qualificationUnitStandardsService.createLink(qualificationUnitStandards, qualification);
			//qualificationUnitStandardsService.createLink(selectedUnitStandard, qualification, unitStandardTypeEnum);
			
			// call information
			qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(qualification);
			selectedUnitStandard = null;
			qualificationUnitStandards = new  QualificationUnitStandards() ;
			//qualificationUnitStandardsList = null;
			qualificationInfo();
			addInfoMessage("Unit Standard Added To Learnership");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Qualification from database.
	 *
	 * @author TechFinium
	 * @see Qualification
	 */
	public void qualificationDelete() {
		try {
			service.delete(this.qualification);
			prepareNew();
			qualificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<UnitStandards> completeUnitStandardsLinkedToQualification(String desc) {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitleLinkedToQualification(desc, this.qualification.getCode());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the qualification list.
	 *
	 * @return the qualification list
	 */
	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	/**
	 * Sets the qualification list.
	 *
	 * @param qualificationList
	 *            the new qualification list
	 */
	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification
	 *            the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Create new instance of Qualification .
	 *
	 * @author TechFinium
	 * @see Qualification
	 */
	public void prepareNew() {
		qualification = new Qualification();
		qualificationUnitStandards = new  QualificationUnitStandards();
	}

	/**
	 * Update Qualification in database.
	 *
	 * @author TechFinium
	 * @see Qualification
	 */
	public void qualificationUpdate() {
		try {
			service.update(this.qualification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			qualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	
	public void deleteLinkPopUp(){
		try {
			if (qualificationUnitStandards == null || qualificationUnitStandards.getId() == null) {
				throw new Exception("Select Unitstandrd beofre deleting entry");
			}
			qualificationUnitStandardsService.delete(qualificationUnitStandards);
			qualificationUnitStandardsList = qualificationUnitStandardsService.findByQualificationKey(qualification);
			qualificationUnitStandards = null;
			qualificationInfo();
			addWarningMessage("Delete Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	/**
	 * Prepare select one menu data.
	 *
	 * @author TechFinium
	 * @return the qualification DD
	 * @see Qualification
	 */
	public List<SelectItem> getQualificationDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		qualificationInfo();
		for (Qualification ug : qualificationList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}
	
	public void prepTypeSelection() {
		try {
			csvTypeSelectionList = new ArrayList<>();
			csvTypeSelectionList.add(",");
			csvTypeSelectionList.add(";");
			csvTypeSelectionList.add("|");
			csvTypeSelectionList.add("-");
			csvTypeSelection = ",";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (csvTypeSelection == null || csvTypeSelection.isEmpty()) {
				csvTypeSelection = ",";
			}
			List<QualificationUnitStandards> csvDataList = csvUtil.getObjects(QualificationUnitStandards.class, event.getFile().getInputstream(), csvTypeSelection);
			qualificationUnitStandardsService.processCsvData(csvDataList);
			addInfoMessage("import Complete");
			runClientSideExecute("PF('csvUploadDlg').hide()");
			prepareNew();
			qualificationInfo();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ConstraintViolationException");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}
	}

	/**
	 * Gets the qualificationfiltered list.
	 *
	 * @return the qualificationfiltered list
	 */
	public List<Qualification> getQualificationfilteredList() {
		return qualificationfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param qualificationfilteredList
	 *            the new qualificationfiltered list
	 * @see Qualification
	 */
	public void setQualificationfilteredList(List<Qualification> qualificationfilteredList) {
		this.qualificationfilteredList = qualificationfilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> complete(String desc) {
		List<Qualification> l = null;
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
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Qualification> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Qualification> dataModel) {
		this.dataModel = dataModel;
	}

	public UnitStandardTypeEnum getUnitStandardTypeEnum() {
		return unitStandardTypeEnum;
	}

	public void setUnitStandardTypeEnum(UnitStandardTypeEnum unitStandardTypeEnum) {
		this.unitStandardTypeEnum = unitStandardTypeEnum;
	}

	public UnitStandards getSelectedUnitStandard() {
		return selectedUnitStandard;
	}

	public void setSelectedUnitStandard(UnitStandards selectedUnitStandard) {
		this.selectedUnitStandard = selectedUnitStandard;
	}

	public List<QualificationUnitStandards> getQualificationUnitStandardsList() {
		return qualificationUnitStandardsList;
	}

	public void setQualificationUnitStandardsList(List<QualificationUnitStandards> qualificationUnitStandardsList) {
		this.qualificationUnitStandardsList = qualificationUnitStandardsList;
	}

	public QualificationUnitStandards getQualificationUnitStandards() {
		return qualificationUnitStandards;
	}

	public void setQualificationUnitStandards(QualificationUnitStandards qualificationUnitStandards) {
		this.qualificationUnitStandards = qualificationUnitStandards;
	}

	public String getCsvTypeSelection() {
		return csvTypeSelection;
	}

	public void setCsvTypeSelection(String csvTypeSelection) {
		this.csvTypeSelection = csvTypeSelection;
	}

	public List<String> getCsvTypeSelectionList() {
		return csvTypeSelectionList;
	}

	public void setCsvTypeSelectionList(List<String> csvTypeSelectionList) {
		this.csvTypeSelectionList = csvTypeSelectionList;
	}
}
