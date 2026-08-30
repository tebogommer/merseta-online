package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.FormSectionQuestionsService;

@ManagedBean(name = "formsectionquestionsUI")
@ViewScoped
public class FormSectionQuestionsUI extends AbstractUI {

	private FormSectionQuestionsService service = new FormSectionQuestionsService();
	private List<FormSectionQuestions> formsectionquestionsList = null;
	private List<FormSectionQuestions> formsectionquestionsfilteredList = null;
	private FormSectionQuestions formsectionquestions = null;
	private LazyDataModel<FormSectionQuestions> dataModel;

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
	 * Initialize method to get all FormSectionQuestions and prepare a for a create
	 * of a new FormSectionQuestions
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		formsectionquestionsInfo();
	}

	/**
	 * Get all FormSectionQuestions for data table
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 */
	public void formsectionquestionsInfo() {

		dataModel = new LazyDataModel<FormSectionQuestions>() {

			private static final long serialVersionUID = 1L;
			private List<FormSectionQuestions> retorno = new ArrayList<FormSectionQuestions>();

			@Override
			public List<FormSectionQuestions> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allFormSectionQuestions(FormSectionQuestions.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(FormSectionQuestions.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(FormSectionQuestions obj) {
				return obj.getId();
			}

			@Override
			public FormSectionQuestions getRowData(String rowKey) {
				for (FormSectionQuestions obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert FormSectionQuestions into database
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 */
	public void formsectionquestionsInsert() {
		try {
			service.create(this.formsectionquestions);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			formsectionquestionsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update FormSectionQuestions in database
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 */
	public void formsectionquestionsUpdate() {
		try {
			service.update(this.formsectionquestions);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			formsectionquestionsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete FormSectionQuestions from database
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 */
	public void formsectionquestionsDelete() {
		try {
			service.delete(this.formsectionquestions);
			prepareNew();
			formsectionquestionsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of FormSectionQuestions
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 */
	public void prepareNew() {
		formsectionquestions = new FormSectionQuestions();
	}

	/*
	 * public List<SelectItem> getFormSectionQuestionsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * formsectionquestionsInfo(); for (FormSectionQuestions ug :
	 * formsectionquestionsList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<FormSectionQuestions> complete(String desc) {
		List<FormSectionQuestions> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FormSectionQuestions> getFormSectionQuestionsList() {
		return formsectionquestionsList;
	}

	public void setFormSectionQuestionsList(List<FormSectionQuestions> formsectionquestionsList) {
		this.formsectionquestionsList = formsectionquestionsList;
	}

	public FormSectionQuestions getFormsectionquestions() {
		return formsectionquestions;
	}

	public void setFormsectionquestions(FormSectionQuestions formsectionquestions) {
		this.formsectionquestions = formsectionquestions;
	}

	public List<FormSectionQuestions> getFormSectionQuestionsfilteredList() {
		return formsectionquestionsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param formsectionquestionsfilteredList
	 *            the new formsectionquestionsfilteredList list
	 * @see FormSectionQuestions
	 */
	public void setFormSectionQuestionsfilteredList(List<FormSectionQuestions> formsectionquestionsfilteredList) {
		this.formsectionquestionsfilteredList = formsectionquestionsfilteredList;
	}

	public LazyDataModel<FormSectionQuestions> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FormSectionQuestions> dataModel) {
		this.dataModel = dataModel;
	}

}
