package haj.com.ui.lookup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.DGYearLearningPrograms;
import haj.com.entity.lookup.InterventionType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DGYearLearningProgramsService;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

@ManagedBean(name = "dgyearlearningprogramsUI")
@ViewScoped
public class DGYearLearningProgramsUI extends AbstractUI {

	private static final NSDMSLogger logger = NSDMSLogger.getLogger(DGYearLearningProgramsUI.class);
	private DGYearLearningProgramsService         service                            = new DGYearLearningProgramsService();
	private List<DGYearLearningPrograms>          dgyearlearningprogramsList         = null;
	private List<DGYearLearningPrograms>          dgyearlearningprogramsfilteredList = null;
	private DGYearLearningPrograms                dgyearlearningprograms             = null;
	private LazyDataModel<DGYearLearningPrograms> dataModel;
	private DGYear                                dgYear;

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
	 * Initialize method to get all DGYearLearningPrograms and prepare a for a create of a new DGYearLearningPrograms
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		dgyearlearningprogramsInfo();
	}

	/**
	 * Get all DGYearLearningPrograms for data table
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 */
	public void dgyearlearningprogramsInfo() {

		dataModel = new LazyDataModel<DGYearLearningPrograms>() {

			private static final long            serialVersionUID = 1L;
			private List<DGYearLearningPrograms> retorno          = new ArrayList<DGYearLearningPrograms>();

			@Override
			public List<DGYearLearningPrograms> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if (filters == null) filters = new HashMap<String, Object>();
					filters.put("dgYearID", dgYear.getId());
					retorno = service.allDGYearLearningPrograms(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DGYearLearningPrograms obj) {
				return obj.getId();
			}

			@Override
			public DGYearLearningPrograms getRowData(String rowKey) {
				for (DGYearLearningPrograms obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DGYearLearningPrograms into database
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 */
	public void dgyearlearningprogramsInsert() {
		try {
			service.create(this.dgyearlearningprograms);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgyearlearningprogramsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DGYearLearningPrograms in database
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 */
	public void dgyearlearningprogramsUpdate() {
		try {
			service.update(this.dgyearlearningprograms);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			dgyearlearningprogramsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DGYearLearningPrograms from database
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 */
	public void dgyearlearningprogramsDelete() {
		try {
			service.delete(this.dgyearlearningprograms);
			prepareNew();
			dgyearlearningprogramsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DGYearLearningPrograms
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 */
	public void prepareNew() {
		dgyearlearningprograms = new DGYearLearningPrograms();
		dgyearlearningprograms.setDgYear(dgYear);
		dgyearlearningprograms.setInterventionType(new InterventionType());
		dgyearlearningprograms.setNumberOfEmployedLearners(null);
		dgyearlearningprograms.setNumberOfUnemployedLearners(null);
		dgyearlearningprogramsList=null;
	}

	public void calcAllocationAmount() {
		Integer employedLeaners   = 0;
		Integer unEmployedLeaners = 0;
		if (dgyearlearningprograms.getInterventionType() != null) {
			try {
				employedLeaners = dgyearlearningprograms.getNumberOfEmployedLearners() == null ? 0 : dgyearlearningprograms.getNumberOfEmployedLearners();
				unEmployedLeaners = dgyearlearningprograms.getNumberOfUnemployedLearners() == null ? 0 : dgyearlearningprograms.getNumberOfUnemployedLearners();
				if (employedLeaners > 0 || unEmployedLeaners > 0) {
					dgyearlearningprograms.setAllocationAmount(new BigDecimal(dgyearlearningprograms.getInterventionType().getBasicGrantAmount() * (employedLeaners + unEmployedLeaners)));
				}
			}catch(Exception e){
				logger.error(String.format("Failed to calculate allocation amount (interventiontype.basicgrantamount is not set) - [%s:%s]", e.getClass(), e.getMessage()), e);
				dgyearlearningprograms.setAllocationAmount(BigDecimal.ZERO);
			}
		}
	}

	/**
	 * Complete.
	 * @param desc
	 * the desc
	 * @return the list
	 */
	public List<DGYearLearningPrograms> complete(String desc) {
		List<DGYearLearningPrograms> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DGYearLearningPrograms> getDGYearLearningProgramsList() {
		return dgyearlearningprogramsList;
	}

	public void setDGYearLearningProgramsList(List<DGYearLearningPrograms> dgyearlearningprogramsList) {
		this.dgyearlearningprogramsList = dgyearlearningprogramsList;
	}

	public DGYearLearningPrograms getDgyearlearningprograms() {
		return dgyearlearningprograms;
	}

	public void setDgyearlearningprograms(DGYearLearningPrograms dgyearlearningprograms) {
		this.dgyearlearningprograms = dgyearlearningprograms;
	}

	public List<DGYearLearningPrograms> getDGYearLearningProgramsfilteredList() {
		return dgyearlearningprogramsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * @author TechFinium
	 * @param dgyearlearningprogramsfilteredList
	 * the new dgyearlearningprogramsfilteredList list
	 * @see DGYearLearningPrograms
	 */
	public void setDGYearLearningProgramsfilteredList(List<DGYearLearningPrograms> dgyearlearningprogramsfilteredList) {
		this.dgyearlearningprogramsfilteredList = dgyearlearningprogramsfilteredList;
	}

	public LazyDataModel<DGYearLearningPrograms> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DGYearLearningPrograms> dataModel) {
		this.dataModel = dataModel;
	}

	public DGYear getDgYear() {
		return dgYear;
	}

	public void setDgYear(DGYear dgYear) {
		this.dgYear = dgYear;
	}

	public void addLearningProgram(){
		if(dgyearlearningprogramsList == null){
			dgyearlearningprogramsList = new ArrayList<>();
		}

		dgyearlearningprograms = new DGYearLearningPrograms();
		dgyearlearningprograms.setDgYear(dgYear);
		dgyearlearningprograms.setInterventionType(new InterventionType());
		dgyearlearningprograms.setNumberOfEmployedLearners(null);
		dgyearlearningprograms.setNumberOfUnemployedLearners(null);

		dgyearlearningprogramsList.add(0,dgyearlearningprograms);

		try {
			DataTable dataTable = ((DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("dgYearInsForm:learningProgramsDataTable"));
			dataTable.setFirst(0);
		}catch(Exception e){
			logger.error("Failed to set learning programs table to the first page", e);
		}
	}

	public void saveLearningPrograms(){
		if(dgyearlearningprogramsList == null){
			return;
		}

		for(DGYearLearningPrograms learningProgram: dgyearlearningprogramsList){
			try {
				service.create(learningProgram);
			}catch(Exception e){
				logger.error(String.format("Failed to add learning program : %s",learningProgram.getInterventionType().getDescription()), e);
				addErrorMessage(getEntryLanguage(e.getMessage()));
			}
		}
		dgyearlearningprogramsInfo();
		this.dgyearlearningprogramsList=null;
		addLearningProgram();
	}

	public void loadListFromDataModel(){
		if(dgYear == null){
			return;
		}
		try {
			dgyearlearningprogramsList = service.findByDgYear(dgYear);
		}catch(Exception e){
			String message = String.format("Failed to retrieve the learning programs using DGYear[%s]", dgYear.getId());
			logger.error(message);
			addErrorMessage(getEntryLanguage(message));
		}

	}

}
