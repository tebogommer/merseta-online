package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.entity.formconfig.FormType;
import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.entity.formconfig.FormTypeSections;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.FormSectionQuestionsService;
import haj.com.service.FormTypeAnswersService;
import haj.com.service.FormTypeSectionsService;
import haj.com.service.FormTypeService;

@ManagedBean(name = "formtypesectionsUI")
@ViewScoped
public class FormTypeSectionsUI extends AbstractUI {

	private FormTypeSectionsService service = new FormTypeSectionsService();
	private List<FormTypeSections> formtypesectionsList = null;
	private List<FormTypeSections> formtypesectionsfilteredList = null;
	private FormTypeSections formtypesections = null;
	private LazyDataModel<FormTypeSections> dataModel;
	private FormSectionQuestionsService formSectionQuestionsService = new FormSectionQuestionsService();
	private FormType formtype = null;
	private FormTypeService formTypeService = new FormTypeService();
	private FormSectionQuestions formSectionQuestions;
	private FormTypeAnswers formTypeAnswers;
	private FormTypeAnswersService formTypeAnswersService = new FormTypeAnswersService();
	private int numOfQuestios;
	private Date qpDate;
	private Date qpFromTime;
	private Date qpToTime;

	private FormTypeSections formTypeSections = new FormTypeSections();
	private boolean editMode = true;

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

	private void runInit() throws Exception {
		formtype = (FormType) super.getParameter("formType", true);
		if (formtype != null) {
			formtypesectionsInfo();
		}
		prepareNewQuestion();
		prepareNew();
		prepareNewAnswer();
	}

	public void formtypesectionsInfo() {
		try {
			formtypesectionsList = service.findByFormType(formtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveFormType() {
		try {
			formTypeService.create(formtype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveQuestion(FormSectionQuestions entity) {
		try {
			formSectionQuestionsService.create(entity);
			prepareNewQuestion();
			addInfoMessage(super.getEntryLanguage("update.successful"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveNewQuestion(FormTypeSections formtypesections) {
		try {
			formSectionQuestions.setFormTypeSections(formtypesections);

			if (formtypesections.getFormSectionQuestions().size() > 0) formSectionQuestions.setOrderPos(formtypesections.getFormSectionQuestions().get(formtypesections.getFormSectionQuestions().size() - 1).getOrderPos() + 1);
			else formSectionQuestions.setOrderPos(0);

			formSectionQuestionsService.create(formSectionQuestions);
			service.findQuestions(formtypesections);
			prepareNewQuestion();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteQuestion() {
		try {

			formSectionQuestionsService.delete(formSectionQuestions);
			formtypesectionsInfo();
			prepareNewQuestion();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteSection() {
		try {
			formSectionQuestionsService.delete(formSectionQuestions);
			formtypesectionsInfo();
			prepareNewQuestion();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void prepareNewQuestion() {
		formSectionQuestions = new FormSectionQuestions();
	}

	public void toggleEdit() {
		this.editMode = !editMode;
	}

	public void saveSection(FormTypeSections formtypesections) {
		try {
			service.create(formtypesections);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveNewSection() {
		try {
			formtypesections.setParentTemplate(formtype);
			if (formtypesectionsList.size() > 0) formtypesections.setOrderPos(formtypesectionsList.get(formtypesectionsList.size() - 1).getOrderPos() + 1);
			else formtypesections.setOrderPos(0);
			formtypesectionsInsert();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveAnswer(FormTypeAnswers entity) {
		try {
			formTypeAnswersService.create(entity);
			prepareNewAnswer();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveNewAnswer(FormSectionQuestions sectionQuestions) {
		try {
			formTypeAnswers.setFormSectionQuestions(sectionQuestions);
			formTypeAnswersService.create(formTypeAnswers);
			prepareNewAnswer();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void prepareNewAnswer() {
		formTypeAnswers = new FormTypeAnswers();
	}

	/**
	 * Insert FormTypeSections into database
	 * 
	 * @author TechFinium
	 * @see FormTypeSections
	 */
	public void formtypesectionsInsert() {
		try {
			service.create(this.formtypesections);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			formtypesectionsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update FormTypeSections in database
	 * 
	 * @author TechFinium
	 * @see FormTypeSections
	 */
	public void formtypesectionsUpdate() {
		try {
			service.update(this.formtypesections);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			formtypesectionsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete FormTypeSections from database
	 * 
	 * @author TechFinium
	 * @see FormTypeSections
	 */
	public void formtypesectionsDelete() {
		try {
			service.delete(this.formtypesections);
			prepareNew();
			formtypesectionsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of FormTypeSections
	 * 
	 * @author TechFinium
	 * @see FormTypeSections
	 */
	public void prepareNew() {
		formtypesections = new FormTypeSections();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<FormTypeSections> complete(String desc) {
		List<FormTypeSections> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FormTypeSections> getFormTypeSectionsList() {
		return formtypesectionsList;
	}

	public void setFormTypeSectionsList(List<FormTypeSections> formtypesectionsList) {
		this.formtypesectionsList = formtypesectionsList;
	}

	public FormTypeSections getFormtypesections() {
		return formtypesections;
	}

	public void setFormtypesections(FormTypeSections formtypesections) {
		this.formtypesections = formtypesections;
	}

	public List<FormTypeSections> getFormTypeSectionsfilteredList() {
		return formtypesectionsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param formtypesectionsfilteredList
	 *            the new formtypesectionsfilteredList list
	 * @see FormTypeSections
	 */
	public void setFormTypeSectionsfilteredList(List<FormTypeSections> formtypesectionsfilteredList) {
		this.formtypesectionsfilteredList = formtypesectionsfilteredList;
	}

	public LazyDataModel<FormTypeSections> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FormTypeSections> dataModel) {
		this.dataModel = dataModel;
	}

	public List<FormTypeSections> getFormtypesectionsList() {
		return formtypesectionsList;
	}

	public void setFormtypesectionsList(List<FormTypeSections> formtypesectionsList) {
		this.formtypesectionsList = formtypesectionsList;
	}

	public FormType getFormtype() {
		return formtype;
	}

	public void setFormtype(FormType formtype) {
		this.formtype = formtype;
	}

	public FormSectionQuestions getFormSectionQuestions() {
		return formSectionQuestions;
	}

	public void setFormSectionQuestions(FormSectionQuestions formSectionQuestions) {
		this.formSectionQuestions = formSectionQuestions;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public FormTypeAnswers getFormTypeAnswers() {
		return formTypeAnswers;
	}

	public void setFormTypeAnswers(FormTypeAnswers formTypeAnswers) {
		this.formTypeAnswers = formTypeAnswers;
	}

	public void downlaodQuestionPaper() {

		try {

			if (numOfQuestios < 1) {
				addErrorMessage("Please enter number of questions");
			} else if (numOfQuestios > formTypeSections.getFormSectionQuestions().size()) {
				addErrorMessage("Please enter a maximum of " + formTypeSections.getFormSectionQuestions().size() + " question(s)");
			} else {
				service.downloadQuestionPaper(formTypeSections, numOfQuestios, formtype, qpDate, qpFromTime, qpToTime);
				formtypesectionsInfo();
				super.runClientSideExecute("PF('dlgDownloadQP').hide()");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
		}
	}

	public FormTypeSections getFormTypeSections() {
		return formTypeSections;
	}

	public void setFormTypeSections(FormTypeSections formTypeSections) {
		this.formTypeSections = formTypeSections;
	}

	public int getNumOfQuestios() {
		return numOfQuestios;
	}

	public void setNumOfQuestios(int numOfQuestios) {
		this.numOfQuestios = numOfQuestios;
	}

	public Date getQpDate() {
		return qpDate;
	}

	public void setQpDate(Date qpDate) {
		this.qpDate = qpDate;
	}

	public Date getQpToTime() {
		return qpToTime;
	}

	public void setQpToTime(Date qpToTime) {
		this.qpToTime = qpToTime;
	}

	public Date getQpFromTime() {
		return qpFromTime;
	}

	public void setQpFromTime(Date qpFromTime) {
		this.qpFromTime = qpFromTime;
	}

}
