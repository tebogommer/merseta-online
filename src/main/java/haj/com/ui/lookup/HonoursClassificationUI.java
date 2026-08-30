package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.HonoursClassification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.HonoursClassificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class HonoursClassificationUI.
 */
@ManagedBean(name = "honoursclassificationUI")
@ViewScoped
public class HonoursClassificationUI extends AbstractUI {

	/** The service. */
	private HonoursClassificationService service = new HonoursClassificationService();
	
	/** The honoursclassification list. */
	private List<HonoursClassification> honoursclassificationList = null;
	
	/** The honoursclassificationfiltered list. */
	private List<HonoursClassification> honoursclassificationfilteredList = null;
	
	/** The honoursclassification. */
	private HonoursClassification honoursclassification = null;
	
	/** The data model. */
	private LazyDataModel<HonoursClassification> dataModel;

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
	 * Initialize method to get all HonoursClassification and prepare a for a
	 * create of a new HonoursClassification.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see HonoursClassification
	 */
	private void runInit() throws Exception {
		prepareNew();
		honoursclassificationInfo();
	}

	/**
	 * Get all HonoursClassification for data table.
	 *
	 * @author TechFinium
	 * @see HonoursClassification
	 */
	public void honoursclassificationInfo() {

		dataModel = new LazyDataModel<HonoursClassification>() {

			private static final long serialVersionUID = 1L;
			private List<HonoursClassification> retorno = new ArrayList<HonoursClassification>();

			@Override
			public List<HonoursClassification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allHonoursClassification(HonoursClassification.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(HonoursClassification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HonoursClassification obj) {
				return obj.getId();
			}

			@Override
			public HonoursClassification getRowData(String rowKey) {
				for (HonoursClassification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert HonoursClassification into database.
	 *
	 * @author TechFinium
	 * @see HonoursClassification
	 */
	public void honoursclassificationInsert() {
		try {
			service.create(this.honoursclassification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			honoursclassificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update HonoursClassification in database.
	 *
	 * @author TechFinium
	 * @see HonoursClassification
	 */
	public void honoursclassificationUpdate() {
		try {
			service.update(this.honoursclassification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			honoursclassificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete HonoursClassification from database.
	 *
	 * @author TechFinium
	 * @see HonoursClassification
	 */
	public void honoursclassificationDelete() {
		try {
			service.delete(this.honoursclassification);
			prepareNew();
			honoursclassificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of HonoursClassification.
	 *
	 * @author TechFinium
	 * @see HonoursClassification
	 */
	public void prepareNew() {
		honoursclassification = new HonoursClassification();
	}

	/*
	 * public List<SelectItem> getHonoursClassificationDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * honoursclassificationInfo(); for (HonoursClassification ug :
	 * honoursclassificationList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<HonoursClassification> complete(String desc) {
		List<HonoursClassification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the honours classification list.
	 *
	 * @return the honours classification list
	 */
	public List<HonoursClassification> getHonoursClassificationList() {
		return honoursclassificationList;
	}

	/**
	 * Sets the honours classification list.
	 *
	 * @param honoursclassificationList the new honours classification list
	 */
	public void setHonoursClassificationList(List<HonoursClassification> honoursclassificationList) {
		this.honoursclassificationList = honoursclassificationList;
	}

	/**
	 * Gets the honoursclassification.
	 *
	 * @return the honoursclassification
	 */
	public HonoursClassification getHonoursclassification() {
		return honoursclassification;
	}

	/**
	 * Sets the honoursclassification.
	 *
	 * @param honoursclassification the new honoursclassification
	 */
	public void setHonoursclassification(HonoursClassification honoursclassification) {
		this.honoursclassification = honoursclassification;
	}

	/**
	 * Gets the honours classificationfiltered list.
	 *
	 * @return the honours classificationfiltered list
	 */
	public List<HonoursClassification> getHonoursClassificationfilteredList() {
		return honoursclassificationfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param honoursclassificationfilteredList            the new honoursclassificationfilteredList list
	 * @see HonoursClassification
	 */
	public void setHonoursClassificationfilteredList(List<HonoursClassification> honoursclassificationfilteredList) {
		this.honoursclassificationfilteredList = honoursclassificationfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HonoursClassification> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HonoursClassification> dataModel) {
		this.dataModel = dataModel;
	}

}
