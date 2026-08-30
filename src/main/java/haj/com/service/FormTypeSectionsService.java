package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.primefaces.model.SortOrder;


import haj.com.bean.FormSectionQuestionsBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.FormTypeSectionsDAO;
import haj.com.entity.enums.AnswerTypeEnum;
import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.entity.formconfig.FormType;
import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.entity.formconfig.FormTypeSections;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FormTypeSectionsService extends AbstractService {
	/** The dao. */
	private FormTypeSectionsDAO dao = new FormTypeSectionsDAO();

	/**
	 * Get all FormTypeSections
	 * 
	 * @author TechFinium
	 * @see FormTypeSections
	 * @return a list of {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             the exception
	 */
	public List<FormTypeSections> allFormTypeSections() throws Exception {
		return dao.allFormTypeSections();
	}

	/**
	 * Create or update FormTypeSections.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see FormTypeSections
	 */
	public void create(FormTypeSections entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update FormTypeSections.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see FormTypeSections
	 */
	public void update(FormTypeSections entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete FormTypeSections.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see FormTypeSections
	 */
	public void delete(FormTypeSections entity) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (FormSectionQuestions iterable_element : entity.getFormSectionQuestions()) {
			dataEntities.add(iterable_element);
		}
		dataEntities.add(entity);
		this.dao.deleteBatch(dataEntities);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             the exception
	 * @see FormTypeSections
	 */
	public FormTypeSections findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find FormTypeSections by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             the exception
	 * @see FormTypeSections
	 */
	public List<FormTypeSections> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load FormTypeSections
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             the exception
	 */
	public List<FormTypeSections> allFormTypeSections(int first, int pageSize) throws Exception {
		return dao.allFormTypeSections(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of FormTypeSections for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the FormTypeSections
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(FormTypeSections.class);
	}

	/**
	 * Lazy load FormTypeSections with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            FormTypeSections class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.FormTypeSections} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeSections> allFormTypeSections(Class<FormTypeSections> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<FormTypeSections>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of FormTypeSections for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            FormTypeSections class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the FormTypeSections entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<FormTypeSections> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<FormTypeSections> findByFormType(FormType formType) throws Exception {
		return resolveQuestions(dao.findByFormType(formType));
	}

	private List<FormTypeSections> resolveQuestions(List<FormTypeSections> formTypeSections) throws Exception {
		for (FormTypeSections formTypeSection : formTypeSections) {
			findQuestions(formTypeSection);
		}
		return formTypeSections;
	}

	public void findQuestions(FormTypeSections formTypeSection) throws Exception {
		formTypeSection.setFormSectionQuestions(findFormSectionQuestions(formTypeSection));
	}

	public List<FormSectionQuestions> findFormSectionQuestions(FormTypeSections formType) throws Exception {
		return dao.findFormSectionQuestions(formType);
	}

	public void downloadQuestionPaper(FormTypeSections formTypeSections, int numOfQuestions, FormType formtype, Date qpDate, Date qpFromTime, Date qpToTime) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		ArrayList<FormSectionQuestionsBean> questionList = createFormSectionQuestionsBeanList((ArrayList<FormSectionQuestions>) formTypeSections.getFormSectionQuestions(), numOfQuestions);
		int totalMarks = 0;
		for (FormSectionQuestionsBean question : questionList) {
			totalMarks += question.getMarks();
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm a");

		long diffMin = qpToTime.getTime() - qpFromTime.getTime();
		long diffSec = diffMin / 1000;
		long min = diffSec / 60;
		long sec = diffSec % 60;

		JasperService.addLogo(params);
		params.put("formSectionQuestionsBeanDataSource", new JRBeanCollectionDataSource(questionList));
		params.put("formType", formtype);
		params.put("totalMarks", totalMarks);
		params.put("qpDate", sdfDate.format(qpDate));
		params.put("qpFromTime", sdfTime.format(qpFromTime));
		params.put("qpToTime", sdfTime.format(qpToTime));
		params.put("duration", min + " min and " + sec + " sec");

		JasperService.instance().createReportFromDBtoServletOutputStream("HWSETAQuestionPaper.jasper", params, "QuestionPaper.pdf");

	}

	public ArrayList<FormSectionQuestionsBean> createFormSectionQuestionsBeanList(ArrayList<FormSectionQuestions> formSectionQuestions, int numOfQuestions) throws Exception {
		ArrayList<FormSectionQuestionsBean> list = new ArrayList<>();
		FormSectionQuestionsBean bean = null;
		ArrayList<FormSectionQuestions> newList = formSectionQuestions;
		for (FormSectionQuestions question : randomQuestions(newList, numOfQuestions)) {
			String marks = "(" + question.getMarks() + ")";
			if (question.getAnswerType() == AnswerTypeEnum.Text) {
				String data = question.getQuestion() + "<br/><br/>";
				data += "" + addSpace(marks) + "<br/>";
				for (int x = 0; x <= question.getMarks(); x++) {
					String answerSplace = "________________________________________________________________________________<br/><br/>";
					data += answerSplace;

				}
				bean = new FormSectionQuestionsBean(data, question.getMarks(), question.getAnswerType());
				list.add(bean);
			} else if (question.getAnswerType() == AnswerTypeEnum.TextArea) {
				String data = question.getQuestion() + "<br/><br/>";
				data += "" + addSpace(marks) + "<br/>";
				for (int x = 0; x <= question.getMarks(); x++) {
					String answerSplace = "________________________________________________________________________________<br/><br/>";
					data += answerSplace;
				}
				bean = new FormSectionQuestionsBean(data, question.getMarks(), question.getAnswerType());
				list.add(bean);
			} else if (question.getAnswerType() == AnswerTypeEnum.DropDown) {
				FormTypeAnswersService formTypeAnswersService = new FormTypeAnswersService();
				List<FormTypeAnswers> formTypeAnswers = formTypeAnswersService.findByQuestion(question);
				String data = question.getQuestion() + "<br/><br/>";
				data += "" + addSpace(marks) + "<br/>";
				for (FormTypeAnswers answer : formTypeAnswers) {
					data += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>[&nbsp;&nbsp;]&nbsp;&nbsp;&nbsp;</b>" + answer.getAnswerDesc() + "<br/>";
				}

				bean = new FormSectionQuestionsBean(data, question.getMarks(), question.getAnswerType());
				list.add(bean);
			} else if (question.getAnswerType() == AnswerTypeEnum.RadioButtons) {
				FormTypeAnswersService formTypeAnswersService = new FormTypeAnswersService();
				List<FormTypeAnswers> formTypeAnswers = formTypeAnswersService.findByQuestion(question);
				String data = question.getQuestion() + "<br/><br/>";
				data += "" + addSpace(marks) + "<br/>";
				for (FormTypeAnswers answer : formTypeAnswers) {
					data += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>(&nbsp;&nbsp;)&nbsp;&nbsp;&nbsp;</b>" + answer.getAnswerDesc() + "<br/>";
				}
				bean = new FormSectionQuestionsBean(data, question.getMarks(), question.getAnswerType());
				list.add(bean);

			} else if (question.getAnswerType() == AnswerTypeEnum.Date) {

			}
		}

		return list;

	}

	public String addSpace(String txt) {
		String space = "";
		for (int x = 0; x <= (155 - txt.length()); x++) {
			space += "&nbsp;";
		}
		space += txt;
		return space;
	}

	public ArrayList<FormSectionQuestions> randomQuestions(ArrayList<FormSectionQuestions> formSectionQuestions, int numQuestion) {
		ArrayList<FormSectionQuestions> randomQuestionList = new ArrayList<>();
		Random randomGenerator = new Random();
		do {
			int index = randomGenerator.nextInt(formSectionQuestions.size());
			randomQuestionList.add(formSectionQuestions.get(index));
			formSectionQuestions.remove(index);
		} while (randomQuestionList.size() < numQuestion && formSectionQuestions.size() > 0);

		return randomQuestionList;

	}

}
