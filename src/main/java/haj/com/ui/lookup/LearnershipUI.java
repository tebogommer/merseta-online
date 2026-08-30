package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LearnershipUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UnitStandardsService;
import haj.com.service.lookup.LearnershipService;
import haj.com.service.lookup.LearnershipUnitStandardsService;

/**
 * The Class LearnershipUI.
 */
@ManagedBean(name = "learnershipUI")
@ViewScoped
public class LearnershipUI extends AbstractUI {

	/** The service. */
	private LearnershipService service = new LearnershipService();
	private LearnershipUnitStandardsService learnershipUnitStandardsService = new LearnershipUnitStandardsService();
	private LearnershipUnitStandards selectedLearnershipUnitStandards = null; 
	
	/** The learnership list. */
	private List<Learnership> learnershipList = null;
	
	/** The link to qualification */
	private List<LearnershipUnitStandards> learnershipUnitStandardsAssignedList = new ArrayList<>();
	
	/** The learnershipfiltered list. */
	private List<Learnership> learnershipfilteredList = null;
	
	/** The learnership. */
	private Learnership learnership = null;
	private Learnership learnershipLink = null;
	private UnitStandardTypeEnum unitStandardTypeEnum= null;
	
	/** Unit Standard */
	private UnitStandards selectedUnitStandard = null;
	
	/** The data model. */
	private LazyDataModel<Learnership> dataModel;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all Learnership and prepare a for a create of a
	 * new Learnership.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Learnership
	 */
	private void runInit() throws Exception {
		prepareNew();
		learnershipInfo();
	}

	/**
	 * Get all Learnership for data table.
	 *
	 * @author TechFinium
	 * @see Learnership
	 */
	public void learnershipInfo() {

		dataModel = new LazyDataModel<Learnership>() {

			private static final long serialVersionUID = 1L;
			private List<Learnership> retorno = new ArrayList<Learnership>();

			@Override
			public List<Learnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLearnership(Learnership.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Learnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Learnership obj) {
				return obj.getId();
			}

			@Override
			public Learnership getRowData(String rowKey) {
				for (Learnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Learnership into database.
	 *
	 * @author TechFinium
	 * @see Learnership
	 */
	public void learnershipInsert() {
		try {
			service.create(this.learnership);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Learnership in database.
	 *
	 * @author TechFinium
	 * @see Learnership
	 */
	public void learnershipUpdate() {
		try {
			service.update(this.learnership);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Learnership from database.
	 *
	 * @author TechFinium
	 * @see Learnership
	 */
	public void learnershipDelete() {
		try {
			service.delete(this.learnership);
			prepareNew();
			learnershipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Learnership.
	 *
	 * @author TechFinium
	 * @see Learnership
	 */
	public void prepareNew() {
		selectedLearnershipUnitStandards = new LearnershipUnitStandards();
		learnership = new Learnership();
	}

	/*
	 * public List<SelectItem> getLearnershipDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * learnershipInfo(); for (Learnership ug : learnershipList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Learnership> complete(String desc) {
		List<Learnership> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<UnitStandards> completeUnitStandardsLinkedToQualification(String desc) {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitleLinkedToQualification(desc, this.learnershipLink.getQualification().getCode());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void prepUnitStandardAdd(){
		try {			
			prepareNew();
			if(learnershipLink == null) {
				throw new Exception("Select A Unit learnership before Proceeding");
			}
			learnershipUnitStandardsAssignedList = learnershipUnitStandardsService.findByLearnership(learnershipLink);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addUnitStandard(){
		try {
			
			// make sure unit standard is selected
			/*if (selectedUnitStandard == null || selectedUnitStandard.getId() == null) {
				throw new Exception("Select A Unit Standard before Proceeding");
			}*/
			
			// check if already assigned
			if (learnershipUnitStandardsService.countByUnitStandardAndLearnership(selectedLearnershipUnitStandards.getUnitStandards(), learnershipLink) > 0) {
				learnershipUnitStandardsService.updateLink(selectedLearnershipUnitStandards);
				// call information
				learnershipUnitStandardsAssignedList = learnershipUnitStandardsService.findByLearnership(learnershipLink);
				selectedUnitStandard = null;
				selectedLearnershipUnitStandards = new LearnershipUnitStandards();
				learnershipInfo();
				addInfoMessage("Unit Standard Updated");
			}else {
				// create new link
				learnershipUnitStandardsService.createLink(selectedLearnershipUnitStandards, learnershipLink);
				//learnershipUnitStandardsService.createLink(selectedUnitStandard, learnershipLink, unitStandardTypeEnum);
				// call information
				learnershipUnitStandardsAssignedList = learnershipUnitStandardsService.findByLearnership(learnershipLink);
				selectedUnitStandard = null;
				selectedLearnershipUnitStandards = new LearnershipUnitStandards();
				learnershipInfo();
				addInfoMessage("Unit Standard Added To Learnership");
			}
			
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteLink(){
		try {
			if (selectedLearnershipUnitStandards == null || selectedLearnershipUnitStandards.getId() == null) {
				throw new Exception("Select Unitstandrd beofre deleting entry");
			}
			learnershipUnitStandardsService.delete(selectedLearnershipUnitStandards);
			learnershipInfo();
			selectedUnitStandard = null;
			selectedLearnershipUnitStandards = null;
			learnershipInfo();
			addWarningMessage("Delete Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteLinkPopUp(){
		try {
			if (selectedLearnershipUnitStandards == null || selectedLearnershipUnitStandards.getId() == null) {
				throw new Exception("Select Unitstandrd beofre deleting entry");
			}
			learnershipUnitStandardsService.delete(selectedLearnershipUnitStandards);
			learnershipUnitStandardsAssignedList = learnershipUnitStandardsService.findByLearnership(learnershipLink);
			selectedUnitStandard = null;
			selectedLearnershipUnitStandards = null;
			learnershipInfo();
			addWarningMessage("Delete Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeView(){
		try {
			learnershipLink = null;
			learnershipUnitStandardsAssignedList = null;
			selectedUnitStandard = null;
			learnershipInfo();
			prepareNew();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Gets the learnership list.
	 *
	 * @return the learnership list
	 */
	public List<Learnership> getLearnershipList() {
		return learnershipList;
	}

	/**
	 * Sets the learnership list.
	 *
	 * @param learnershipList the new learnership list
	 */
	public void setLearnershipList(List<Learnership> learnershipList) {
		this.learnershipList = learnershipList;
	}

	/**
	 * Gets the learnership.
	 *
	 * @return the learnership
	 */
	public Learnership getLearnership() {
		return learnership;
	}

	/**
	 * Sets the learnership.
	 *
	 * @param learnership the new learnership
	 */
	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	/**
	 * Gets the learnershipfiltered list.
	 *
	 * @return the learnershipfiltered list
	 */
	public List<Learnership> getLearnershipfilteredList() {
		return learnershipfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param learnershipfilteredList            the new learnershipfilteredList list
	 * @see Learnership
	 */
	public void setLearnershipfilteredList(List<Learnership> learnershipfilteredList) {
		this.learnershipfilteredList = learnershipfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Learnership> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Learnership> dataModel) {
		this.dataModel = dataModel;
	}

	public List<LearnershipUnitStandards> getLearnershipUnitStandardsAssignedList() {
		return learnershipUnitStandardsAssignedList;
	}

	public void setLearnershipUnitStandardsAssignedList( List<LearnershipUnitStandards> learnershipUnitStandardsAssignedList) {
		this.learnershipUnitStandardsAssignedList = learnershipUnitStandardsAssignedList;
	}

	public UnitStandards getSelectedUnitStandard() {
		return selectedUnitStandard;
	}

	public void setSelectedUnitStandard(UnitStandards selectedUnitStandard) {
		this.selectedUnitStandard = selectedUnitStandard;
	}

	public LearnershipUnitStandards getSelectedLearnershipUnitStandards() {
		return selectedLearnershipUnitStandards;
	}

	public void setSelectedLearnershipUnitStandards(LearnershipUnitStandards selectedLearnershipUnitStandards) {
		this.selectedLearnershipUnitStandards = selectedLearnershipUnitStandards;
	}

	public Learnership getLearnershipLink() {
		return learnershipLink;
	}

	public void setLearnershipLink(Learnership learnershipLink) {
		this.learnershipLink = learnershipLink;
	}

	public UnitStandardTypeEnum getUnitStandardTypeEnum() {
		return unitStandardTypeEnum;
	}

	public void setUnitStandardTypeEnum(UnitStandardTypeEnum unitStandardTypeEnum) {
		this.unitStandardTypeEnum = unitStandardTypeEnum;
	}
}
