package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.lookup.AbetBand;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AbetBandService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbetBandUI.
 */
@ManagedBean(name = "abetbandUI")
@ViewScoped
public class AbetBandUI extends AbstractUI {

	/** The service. */
	private AbetBandService service = new AbetBandService();

	/** The abetband list. */
	private List<AbetBand> abetbandList = null;

	/** The abetbandfiltered list. */
	private List<AbetBand> abetbandfilteredList = null;

	/** The abetband. */
	private AbetBand abetband = null;

	/** The data model. */
	private LazyDataModel<AbetBand> dataModel;

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
	 * Initialize method to get all AbetBand and prepare a for a create of a new
	 * AbetBand.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see AbetBand
	 */
	private void runInit() throws Exception {
		prepareNew();
		abetbandInfo();
	}

	/**
	 * Get all AbetBand for data table.
	 *
	 * @author TechFinium
	 * @see AbetBand
	 */
	public void abetbandInfo() {

		dataModel = new LazyDataModel<AbetBand>() {

			private static final long serialVersionUID = 1L;
			private List<AbetBand> retorno = new ArrayList<AbetBand>();

			@Override
			public List<AbetBand> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allAbetBand(AbetBand.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(AbetBand.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AbetBand obj) {
				return obj.getId();
			}

			@Override
			public AbetBand getRowData(String rowKey) {
				for (AbetBand obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert AbetBand into database.
	 *
	 * @author TechFinium
	 * @see AbetBand
	 */
	public void abetbandInsert() {
		try {
			//abetband.setCompany(new Company());
			//abetband.getCompany().setResidentialAddress(new Address());
			service.create(this.abetband);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			abetbandInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (javax.validation.ConstraintViolationException e) {
			e.getConstraintViolations().forEach(elt -> addErrorMessage(elt.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update AbetBand in database.
	 *
	 * @author TechFinium
	 * @see AbetBand
	 */
	public void abetbandUpdate() {
		try {
			service.update(this.abetband);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			abetbandInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete AbetBand from database.
	 *
	 * @author TechFinium
	 * @see AbetBand
	 */
	public void abetbandDelete() {
		try {
			service.delete(this.abetband);
			prepareNew();
			abetbandInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of AbetBand.
	 *
	 * @author TechFinium
	 * @see AbetBand
	 */
	public void prepareNew() {
		abetband = new AbetBand();
	}

	/*
	 * public List<SelectItem> getAbetBandDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * abetbandInfo(); for (AbetBand ug : abetbandList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<AbetBand> complete(String desc) {
		List<AbetBand> l = null;
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
	 * Gets the abet band list.
	 *
	 * @return the abet band list
	 */
	public List<AbetBand> getAbetBandList() {
		return abetbandList;
	}

	/**
	 * Sets the abet band list.
	 *
	 * @param abetbandList
	 *            the new abet band list
	 */
	public void setAbetBandList(List<AbetBand> abetbandList) {
		this.abetbandList = abetbandList;
	}

	/**
	 * Gets the abetband.
	 *
	 * @return the abetband
	 */
	public AbetBand getAbetband() {
		return abetband;
	}

	/**
	 * Sets the abetband.
	 *
	 * @param abetband
	 *            the new abetband
	 */
	public void setAbetband(AbetBand abetband) {
		this.abetband = abetband;
	}

	/**
	 * Gets the abet bandfiltered list.
	 *
	 * @return the abet bandfiltered list
	 */
	public List<AbetBand> getAbetBandfilteredList() {
		return abetbandfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param abetbandfilteredList
	 *            the new abetbandfilteredList list
	 * @see AbetBand
	 */
	public void setAbetBandfilteredList(List<AbetBand> abetbandfilteredList) {
		this.abetbandfilteredList = abetbandfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<AbetBand> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<AbetBand> dataModel) {
		this.dataModel = dataModel;
	}

}
