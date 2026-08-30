package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.FormTypeAnswersService;

@ManagedBean(name = "sectionquestionanswersUI")
@ViewScoped
public class SectionQuestionAnswersUI extends AbstractUI {

	private FormTypeAnswersService service = new FormTypeAnswersService();
	private List<FormTypeAnswers> sectionquestionanswersList = null;
	private List<FormTypeAnswers> sectionquestionanswersfilteredList = null;
	private FormTypeAnswers sectionquestionanswers = null;
	private LazyDataModel<FormTypeAnswers> dataModel;

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
	 * Initialize method to get all SectionQuestionAnswers and prepare a for a
	 * create of a new SectionQuestionAnswers
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		sectionquestionanswersInfo();
	}

	/**
	 * Get all SectionQuestionAnswers for data table
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 */
	public void sectionquestionanswersInfo() {

		dataModel = new LazyDataModel<FormTypeAnswers>() {

			private static final long serialVersionUID = 1L;
			private List<FormTypeAnswers> retorno = new ArrayList<FormTypeAnswers>();

			@Override
			public List<FormTypeAnswers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allFormTypeAnswers(FormTypeAnswers.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(FormTypeAnswers.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(FormTypeAnswers obj) {
				return obj.getId();
			}

			@Override
			public FormTypeAnswers getRowData(String rowKey) {
				for (FormTypeAnswers obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SectionQuestionAnswers into database
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 */
	public void sectionquestionanswersInsert() {
		try {
			service.create(this.sectionquestionanswers);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sectionquestionanswersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SectionQuestionAnswers in database
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 */
	public void sectionquestionanswersUpdate() {
		try {
			service.update(this.sectionquestionanswers);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sectionquestionanswersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SectionQuestionAnswers from database
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 */
	public void sectionquestionanswersDelete() {
		try {
			service.delete(this.sectionquestionanswers);
			prepareNew();
			sectionquestionanswersInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SectionQuestionAnswers
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 */
	public void prepareNew() {
		sectionquestionanswers = new FormTypeAnswers();
	}

	/*
	 * public List<SelectItem> getSectionQuestionAnswersDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * sectionquestionanswersInfo(); for (SectionQuestionAnswers ug :
	 * sectionquestionanswersList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<FormTypeAnswers> complete(String desc) {
		List<FormTypeAnswers> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FormTypeAnswers> getSectionQuestionAnswersList() {
		return sectionquestionanswersList;
	}

	public void setSectionQuestionAnswersList(List<FormTypeAnswers> sectionquestionanswersList) {
		this.sectionquestionanswersList = sectionquestionanswersList;
	}

	public FormTypeAnswers getSectionquestionanswers() {
		return sectionquestionanswers;
	}

	public void setSectionquestionanswers(FormTypeAnswers sectionquestionanswers) {
		this.sectionquestionanswers = sectionquestionanswers;
	}

	public List<FormTypeAnswers> getSectionQuestionAnswersfilteredList() {
		return sectionquestionanswersfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param sectionquestionanswersfilteredList
	 *            the new sectionquestionanswersfilteredList list
	 * @see FormTypeAnswers
	 */
	public void setSectionQuestionAnswersfilteredList(List<FormTypeAnswers> sectionquestionanswersfilteredList) {
		this.sectionquestionanswersfilteredList = sectionquestionanswersfilteredList;
	}

	public LazyDataModel<FormTypeAnswers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FormTypeAnswers> dataModel) {
		this.dataModel = dataModel;
	}

}
