package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.service.lookup.DateChangeReasonsService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "datechangereasonsUI")
@ViewScoped
public class DateChangeReasonsUI extends AbstractUI {

	private DateChangeReasonsService service = new DateChangeReasonsService();
	private List<DateChangeReasons> datechangereasonsList = null;
	private List<DateChangeReasons> datechangereasonsfilteredList = null;
	private DateChangeReasons datechangereasons = null;
	private LazyDataModel<DateChangeReasons> dataModel;

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
	 * Initialize method to get all DateChangeReasons and prepare a for a create
	 * of a new DateChangeReasons
	 * 
	 * @author TechFinium
	 * @see DateChangeReasons
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		datechangereasonsInfo();
	}

	/**
	 * Get all DateChangeReasons for data table
	 * 
	 * @author TechFinium
	 * @see DateChangeReasons
	 */
	public void datechangereasonsInfo() {

		dataModel = new LazyDataModel<DateChangeReasons>() {

			private static final long serialVersionUID = 1L;
			private List<DateChangeReasons> retorno = new ArrayList<DateChangeReasons>();

			@Override
			public List<DateChangeReasons> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allDateChangeReasons(DateChangeReasons.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(DateChangeReasons.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DateChangeReasons obj) {
				return obj.getId();
			}

			@Override
			public DateChangeReasons getRowData(String rowKey) {
				for (DateChangeReasons obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DateChangeReasons into database
	 * 
	 * @author TechFinium
	 * @see DateChangeReasons
	 */
	public void datechangereasonsInsert() {
		try {
			service.create(this.datechangereasons);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			datechangereasonsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DateChangeReasons in database
	 * 
	 * @author TechFinium
	 * @see DateChangeReasons
	 */
	public void datechangereasonsUpdate() {
		try {
			service.update(this.datechangereasons);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			datechangereasonsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DateChangeReasons from database
	 * 
	 * @author TechFinium
	 * @see DateChangeReasons
	 */
	public void datechangereasonsDelete() {
		try {
			service.delete(this.datechangereasons);
			prepareNew();
			datechangereasonsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DateChangeReasons
	 * 
	 * @author TechFinium
	 * @see DateChangeReasons
	 */
	public void prepareNew() {
		datechangereasons = new DateChangeReasons();
	}

	/*
	 * public List<SelectItem> getDateChangeReasonsDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * datechangereasonsInfo(); for (DateChangeReasons ug :
	 * datechangereasonsList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DateChangeReasons> complete(String desc) {
		List<DateChangeReasons> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DateChangeReasons> getDateChangeReasonsList() {
		return datechangereasonsList;
	}

	public void setDateChangeReasonsList(List<DateChangeReasons> datechangereasonsList) {
		this.datechangereasonsList = datechangereasonsList;
	}

	public DateChangeReasons getDatechangereasons() {
		return datechangereasons;
	}

	public void setDatechangereasons(DateChangeReasons datechangereasons) {
		this.datechangereasons = datechangereasons;
	}

	public List<DateChangeReasons> getDateChangeReasonsfilteredList() {
		return datechangereasonsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param datechangereasonsfilteredList
	 *            the new datechangereasonsfilteredList list
	 * @see DateChangeReasons
	 */
	public void setDateChangeReasonsfilteredList(List<DateChangeReasons> datechangereasonsfilteredList) {
		this.datechangereasonsfilteredList = datechangereasonsfilteredList;
	}

	public LazyDataModel<DateChangeReasons> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DateChangeReasons> dataModel) {
		this.dataModel = dataModel;
	}

}
