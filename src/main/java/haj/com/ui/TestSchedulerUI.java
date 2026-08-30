package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import haj.com.constants.HAJConstants;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;

@ManagedBean(name = "testSchedulerUI")
@ViewScoped
public class TestSchedulerUI extends AbstractUI {
	private CompanyLearnersService companyLearnersService=new CompanyLearnersService();
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
		
		
	}
	
	/**
	 * System to provide a list of agreements/contracts that have expired 
	 * System to send notifications to Learner, CLO and the Provider/ Workplace 
	 * to notify on expiration/ termination status of the agreement 
	 * */
	public void checkExpiredContactsAndSendNotification()
	{
		try {
			
			if ("T".equals(HAJConstants.DEV_TEST_PROD))
			{
				companyLearnersService.checkExpiredContactsAndSendNotification();
				addInfoMessage("Notification sent...!!");
			}
			else
			{
				addWarningMessage("This option is available on the test site");
			}
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	
	

	
	
}
