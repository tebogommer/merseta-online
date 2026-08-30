package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.formconfig.FormType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.FormTypeService;

@ManagedBean(name = "formtypeUI")
@ViewScoped
public class FormTypeUI extends AbstractUI {

	private FormTypeService service = new FormTypeService();
	private List<FormType> formtypeList = null;
	private List<FormType> formtypefilteredList = null;
	private FormType formtype = null;
	private LazyDataModel<FormType> dataModel;

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
	 * Initialize method to get all FormType and prepare a for a create of a new
	 * FormType
	 * 
	 * @author TechFinium
	 * @see FormType
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		formtypeInfo();
	}

	/**
	 * Get all FormType for data table
	 * 
	 * @author TechFinium
	 * @see FormType
	 */
	public void formtypeInfo() {
		dataModel = new LazyDataModel<FormType>() {

			private static final long serialVersionUID = 1L;
			private List<FormType> retorno = new ArrayList<FormType>();

			@Override
			public List<FormType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allFormType(FormType.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(FormType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(FormType obj) {
				return obj.getId();
			}

			@Override
			public FormType getRowData(String rowKey) {
				for (FormType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert FormType into database
	 * 
	 * @author TechFinium
	 * @see FormType
	 */
	public void formtypeInsert() {
		try {
			service.create(this.formtype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			formtypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update FormType in database
	 * 
	 * @author TechFinium
	 * @see FormType
	 */
	public void formtypeUpdate() {
		try {
			service.update(this.formtype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			formtypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete FormType from database
	 * 
	 * @author TechFinium
	 * @see FormType
	 */
	public void formtypeDelete() {
		try {
			service.delete(this.formtype);
			prepareNew();
			formtypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of FormType
	 * 
	 * @author TechFinium
	 * @see FormType
	 */
	public void prepareNew() {
		formtype = new FormType();
	}

	public void gotoConfig() {
		super.setParameter("formType", formtype, true);
		super.ajaxRedirect("/pages/formconfig/formtypesections.jsf");
	}

	/*
	 * public List<SelectItem> getFormTypeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * formtypeInfo(); for (FormType ug : formtypeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<FormType> complete(String desc) {
		List<FormType> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FormType> getFormTypeList() {
		return formtypeList;
	}

	public void setFormTypeList(List<FormType> formtypeList) {
		this.formtypeList = formtypeList;
	}

	public FormType getFormtype() {
		return formtype;
	}

	public void setFormtype(FormType formtype) {
		this.formtype = formtype;
	}

	public List<FormType> getFormTypefilteredList() {
		return formtypefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param formtypefilteredList
	 *            the new formtypefilteredList list
	 * @see FormType
	 */
	public void setFormTypefilteredList(List<FormType> formtypefilteredList) {
		this.formtypefilteredList = formtypefilteredList;
	}

	public LazyDataModel<FormType> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FormType> dataModel) {
		this.dataModel = dataModel;
	}

}
