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
import haj.com.entity.SarsLevyDetailCalculation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SarsLevyDetailCalculationService;

@ManagedBean(name = "sarslevydetailcalculationUI")
@ViewScoped
public class SarsLevyDetailCalculationUI extends AbstractUI {

	private SarsLevyDetailCalculationService service = new SarsLevyDetailCalculationService();
	private List<SarsLevyDetailCalculation> sarslevydetailcalculationList = null;
	private List<SarsLevyDetailCalculation> sarslevydetailcalculationfilteredList = null;
	private SarsLevyDetailCalculation sarslevydetailcalculation = null;
	private LazyDataModel<SarsLevyDetailCalculation> dataModel;
	private final String YEAR_FORMAT = HAJConstants.YEAR_FORMAT;

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
	 * Initialize method to get all SarsLevyDetailCalculation and prepare a for
	 * a create of a new SarsLevyDetailCalculation
	 * 
	 * @author TechFinium
	 * @see SarsLevyDetailCalculation
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		sarslevydetailcalculationInfo();
	}

	/**
	 * Get all SarsLevyDetailCalculation for data table
	 * 
	 * @author TechFinium
	 * @see SarsLevyDetailCalculation
	 */
	public void sarslevydetailcalculationInfo() {
		dataModel = new LazyDataModel<SarsLevyDetailCalculation>() {
			private static final long serialVersionUID = 1L;
			private List<SarsLevyDetailCalculation> retorno = new ArrayList<SarsLevyDetailCalculation>();

			@Override
			public List<SarsLevyDetailCalculation> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSarsLevyDetailCalculation(SarsLevyDetailCalculation.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SarsLevyDetailCalculation.class, filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SarsLevyDetailCalculation obj) {
				return obj.getId();
			}

			@Override
			public SarsLevyDetailCalculation getRowData(String rowKey) {
				for (SarsLevyDetailCalculation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SarsLevyDetailCalculation into database
	 * 
	 * @author TechFinium
	 * @see SarsLevyDetailCalculation
	 */
	public void sarslevydetailcalculationInsert() {
		try {
			sarslevydetailcalculation.setLastActionDate(getNow());
			sarslevydetailcalculation.setLastActionUser(getSessionUI().getActiveUser());
			service.validiateInformation(this.sarslevydetailcalculation);
			service.create(this.sarslevydetailcalculation);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sarslevydetailcalculationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SarsLevyDetailCalculation in database
	 * 
	 * @author TechFinium
	 * @see SarsLevyDetailCalculation
	 */
	public void sarslevydetailcalculationUpdate() {
		try {
			service.update(this.sarslevydetailcalculation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sarslevydetailcalculationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SarsLevyDetailCalculation from database
	 * 
	 * @author TechFinium
	 * @see SarsLevyDetailCalculation
	 */
	public void sarslevydetailcalculationDelete() {
		try {
			service.delete(this.sarslevydetailcalculation);
			prepareNew();
			sarslevydetailcalculationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SarsLevyDetailCalculation
	 * 
	 * @author TechFinium
	 * @see SarsLevyDetailCalculation
	 */
	public void prepareNew() {
		sarslevydetailcalculation = new SarsLevyDetailCalculation();
	}

	/*
	 * public List<SelectItem> getSarsLevyDetailCalculationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * sarslevydetailcalculationInfo(); for (SarsLevyDetailCalculation ug :
	 * sarslevydetailcalculationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SarsLevyDetailCalculation> complete(String desc) {
		List<SarsLevyDetailCalculation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SarsLevyDetailCalculation> getSarsLevyDetailCalculationList() {
		return sarslevydetailcalculationList;
	}

	public void setSarsLevyDetailCalculationList(List<SarsLevyDetailCalculation> sarslevydetailcalculationList) {
		this.sarslevydetailcalculationList = sarslevydetailcalculationList;
	}

	public SarsLevyDetailCalculation getSarslevydetailcalculation() {
		return sarslevydetailcalculation;
	}

	public void setSarslevydetailcalculation(SarsLevyDetailCalculation sarslevydetailcalculation) {
		this.sarslevydetailcalculation = sarslevydetailcalculation;
	}

	public List<SarsLevyDetailCalculation> getSarsLevyDetailCalculationfilteredList() {
		return sarslevydetailcalculationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param sarslevydetailcalculationfilteredList
	 *            the new sarslevydetailcalculationfilteredList list
	 * @see SarsLevyDetailCalculation
	 */
	public void setSarsLevyDetailCalculationfilteredList(
			List<SarsLevyDetailCalculation> sarslevydetailcalculationfilteredList) {
		this.sarslevydetailcalculationfilteredList = sarslevydetailcalculationfilteredList;
	}

	public LazyDataModel<SarsLevyDetailCalculation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SarsLevyDetailCalculation> dataModel) {
		this.dataModel = dataModel;
	}

	public String getYEAR_FORMAT() {
		return YEAR_FORMAT;
	}

}
