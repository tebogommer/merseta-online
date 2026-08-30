package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.TasksService;

/**
 * The Class LearnerTranfserUI
 */
@ManagedBean(name = "learnerTranfserUI")
@ViewScoped
public class LearnerTranfserUI extends AbstractUI {
	
	/* Entity Level */
	private CompanyLearnersTransfer companyLearnersTransfer;
	
	/* Service Level */
	private CompanyLearnersService companyLearnersService;
	
	/* Booleans */
	private boolean inworkflow = true;

    /**
     * Inits the.
     */
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
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CompanyLearnersTransfer
	 */
	private void runInit() throws Exception {
		populateServiceLevels();
		if (super.getParameter("id", false) != null) {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LearnerTransfer) {
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				this.companyLearnersTransfer = companyLearnersService.findTransferByKey(getSessionUI().getTask().getTargetKey(), ConfigDocProcessEnum.LearnerTransfer);
				companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.LearnerTransfer, companyLearnersTransfer.getCompanyLearners());
				// populateExistingLearner();
			} else {
				super.ajaxRedirectToDashboard();
			}
		}else {
			super.ajaxRedirectToDashboard();
		}
	}

	private void populateServiceLevels() throws Exception{
		if (companyLearnersService == null) {
			companyLearnersService = new CompanyLearnersService();
		}
	}

	
	/* Getters and setters */
	public boolean isInworkflow() {
		return inworkflow;
	}

	public void setInworkflow(boolean inworkflow) {
		this.inworkflow = inworkflow;
	}

	public CompanyLearnersTransfer getCompanyLearnersTransfer() {
		return companyLearnersTransfer;
	}

	public void setCompanyLearnersTransfer(CompanyLearnersTransfer companyLearnersTransfer) {
		this.companyLearnersTransfer = companyLearnersTransfer;
	}
}
