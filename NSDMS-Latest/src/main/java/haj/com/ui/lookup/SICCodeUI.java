package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SICCode;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SICCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SICCodeUI.
 */
@ManagedBean(name = "siccodeUI")
@ViewScoped
public class SICCodeUI extends AbstractUI {

	/** The service. */
	private SICCodeService service = new SICCodeService();
	
	/** The siccode list. */
	private List<SICCode> siccodeList = null;
	
	/** The siccodefiltered list. */
	private List<SICCode> siccodefilteredList = null;
	
	/** The siccode. */
	private SICCode siccode = null;
	
	/** The data model. */
	private LazyDataModel<SICCode> dataModel;

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
	 * Initialize method to get all SICCode and prepare a for a create of a new
	 * SICCode.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see SICCode
	 */
	private void runInit() throws Exception {
		prepareNew();
		siccodeInfo();
	}

	/**
	 * Get all SICCode for data table.
	 *
	 * @author TechFinium
	 * @see SICCode
	 */
	public void siccodeInfo() {

		dataModel = new LazyDataModel<SICCode>() {

			private static final long serialVersionUID = 1L;
			private List<SICCode> retorno = new ArrayList<SICCode>();

			@Override
			public List<SICCode> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSICCode(SICCode.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SICCode.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SICCode obj) {
				return obj.getId();
			}

			@Override
			public SICCode getRowData(String rowKey) {
				for (SICCode obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SICCode into database.
	 *
	 * @author TechFinium
	 * @see SICCode
	 */
	public void siccodeInsert() {
		try {
			service.create(this.siccode);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			siccodeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SICCode in database.
	 *
	 * @author TechFinium
	 * @see SICCode
	 */
	public void siccodeUpdate() {
		try {
			service.update(this.siccode);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			siccodeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SICCode from database.
	 *
	 * @author TechFinium
	 * @see SICCode
	 */
	public void siccodeDelete() {
		try {
			service.delete(this.siccode);
			prepareNew();
			siccodeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SICCode.
	 *
	 * @author TechFinium
	 * @see SICCode
	 */
	public void prepareNew() {
		siccode = new SICCode();
	}

	/*
	 * public List<SelectItem> getSICCodeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * siccodeInfo(); for (SICCode ug : siccodeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SICCode> complete(String desc) {
		List<SICCode> l = null;
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
	 * Gets the SIC code list.
	 *
	 * @return the SIC code list
	 */
	public List<SICCode> getSICCodeList() {
		return siccodeList;
	}

	/**
	 * Sets the SIC code list.
	 *
	 * @param siccodeList the new SIC code list
	 */
	public void setSICCodeList(List<SICCode> siccodeList) {
		this.siccodeList = siccodeList;
	}

	/**
	 * Gets the siccode.
	 *
	 * @return the siccode
	 */
	public SICCode getSiccode() {
		return siccode;
	}

	/**
	 * Sets the siccode.
	 *
	 * @param siccode the new siccode
	 */
	public void setSiccode(SICCode siccode) {
		this.siccode = siccode;
	}

	/**
	 * Gets the SIC codefiltered list.
	 *
	 * @return the SIC codefiltered list
	 */
	public List<SICCode> getSICCodefilteredList() {
		return siccodefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param siccodefilteredList            the new siccodefilteredList list
	 * @see SICCode
	 */
	public void setSICCodefilteredList(List<SICCode> siccodefilteredList) {
		this.siccodefilteredList = siccodefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SICCode> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SICCode> dataModel) {
		this.dataModel = dataModel;
	}

}
