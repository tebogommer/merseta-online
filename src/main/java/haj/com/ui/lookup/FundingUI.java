package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Funding;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.FundingService;

// TODO: Auto-generated Javadoc
/**
 * The Class FundingUI.
 */
@ManagedBean(name = "fundingUI")
@ViewScoped
public class FundingUI extends AbstractUI {

	/** The service. */
	private FundingService service = new FundingService();
	
	/** The funding list. */
	private List<Funding> fundingList = null;
	
	/** The fundingfiltered list. */
	private List<Funding> fundingfilteredList = null;
	
	/** The funding. */
	private Funding funding = null;
	
	/** The data model. */
	private LazyDataModel<Funding> dataModel;

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
	 * Initialize method to get all Funding and prepare a for a create of a new
	 * Funding.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Funding
	 */
	private void runInit() throws Exception {
		prepareNew();
		fundingInfo();
	}

	/**
	 * Get all Funding for data table.
	 *
	 * @author TechFinium
	 * @see Funding
	 */
	public void fundingInfo() {

		dataModel = new LazyDataModel<Funding>() {

			private static final long serialVersionUID = 1L;
			private List<Funding> retorno = new ArrayList<Funding>();

			@Override
			public List<Funding> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allFunding(Funding.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Funding.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Funding obj) {
				return obj.getId();
			}

			@Override
			public Funding getRowData(String rowKey) {
				for (Funding obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Funding into database.
	 *
	 * @author TechFinium
	 * @see Funding
	 */
	public void fundingInsert() {
		try {
			service.create(this.funding);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			fundingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Funding in database.
	 *
	 * @author TechFinium
	 * @see Funding
	 */
	public void fundingUpdate() {
		try {
			service.update(this.funding);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			fundingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Funding from database.
	 *
	 * @author TechFinium
	 * @see Funding
	 */
	public void fundingDelete() {
		try {
			service.delete(this.funding);
			prepareNew();
			fundingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Funding.
	 *
	 * @author TechFinium
	 * @see Funding
	 */
	public void prepareNew() {
		funding = new Funding();
	}

	/*
	 * public List<SelectItem> getFundingDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * fundingInfo(); for (Funding ug : fundingList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Funding> complete(String desc) {
		List<Funding> l = null;
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
	 * Gets the funding list.
	 *
	 * @return the funding list
	 */
	public List<Funding> getFundingList() {
		return fundingList;
	}

	/**
	 * Sets the funding list.
	 *
	 * @param fundingList the new funding list
	 */
	public void setFundingList(List<Funding> fundingList) {
		this.fundingList = fundingList;
	}

	/**
	 * Gets the funding.
	 *
	 * @return the funding
	 */
	public Funding getFunding() {
		return funding;
	}

	/**
	 * Sets the funding.
	 *
	 * @param funding the new funding
	 */
	public void setFunding(Funding funding) {
		this.funding = funding;
	}

	/**
	 * Gets the fundingfiltered list.
	 *
	 * @return the fundingfiltered list
	 */
	public List<Funding> getFundingfilteredList() {
		return fundingfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param fundingfilteredList            the new fundingfilteredList list
	 * @see Funding
	 */
	public void setFundingfilteredList(List<Funding> fundingfilteredList) {
		this.fundingfilteredList = fundingfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Funding> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Funding> dataModel) {
		this.dataModel = dataModel;
	}

}
