package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.gptransations.ArrayOfInfo;
import haj.com.gptransations.ArrayOfRecentPayments;

@ManagedBean(name = "gpTransactionsTestUI")
@ViewScoped
public class GpTransactionsTestUI extends AbstractUI {
	
	/* Objects */
	private ArrayOfInfo arrayOfInfo = null;
	private ArrayOfRecentPayments arrayOfPayments = null;
//	private ArrayOfRecentAdjustments arrayOfAdjustments = null;
//	private ArrayOfPaymentReturns arrayOfPaymentReturns = null;
	
	public String levyNumber;

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		levyNumber = "";
	}
	
	private void validiateLNumber() throws Exception{
		if (levyNumber == null || levyNumber.trim().isEmpty()) {
			throw new Exception("Provide Entity ID Before Proceeding");
		}
	}
	
	public void clearAllEntries(){
		try {
			arrayOfInfo = null;
			arrayOfPayments = null;
//			arrayOfAdjustments = null;
			addWarningMessage("Data Cleared");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runGetInfo() {
		try {
//			validiateLNumber();
//			arrayOfInfo = GPTransactionsService.instance().getInfo(levyNumber);
//			if (arrayOfInfo == null|| arrayOfInfo.getInfo().isEmpty()) {
//				addWarningMessage("Action Complete, No Results");
//			} else {
//				addInfoMessage("Action Complete");
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage() , e);
		}
	}

	public void runGetPayments() {
		try {
//			validiateLNumber();
//			arrayOfPayments = GPTransactionsService.instance().getPayments(levyNumber);
//			if (arrayOfPayments == null || arrayOfPayments.getRecentPayments().isEmpty()) {
//				addWarningMessage("Action Complete, No Results");
//			} else {
//				addInfoMessage("Action Complete");
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage() , e);
		}
	}
	
	public void runGetAjustment() {
		try {
//			validiateLNumber();
//			arrayOfAdjustments = GPTransactionsService.instance().getAjustments(levyNumber);
//			if (arrayOfAdjustments == null || arrayOfAdjustments.getRecentAdjustments().isEmpty()) {
//				addWarningMessage("Action Complete, No Results");
//			} else {
//				addInfoMessage("Action Complete");
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage() , e);
		}
	}
	
	public void runGetPaymentReturns() {
		try {
//			validiateLNumber();
//			arrayOfPaymentReturns = GPTransactionsService.instance().getPaymentReturns(levyNumber);
//			if (arrayOfPaymentReturns == null || arrayOfPaymentReturns.getPaymentReturns().isEmpty()) {
//				addWarningMessage("Action Complete, No Results");
//			} else {
//				addInfoMessage("Action Complete");
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage() , e);
		}
	}
	
	public void runAllEntries(){
		try {
//			validiateLNumber();
//			arrayOfInfo = GPTransactionsService.instance().getInfo(levyNumber);
//			arrayOfPayments = GPTransactionsService.instance().getPayments(levyNumber);
//			arrayOfAdjustments = GPTransactionsService.instance().getAjustments(levyNumber);
//			arrayOfPaymentReturns = GPTransactionsService.instance().getPaymentReturns(levyNumber);
//			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public ArrayOfInfo getArrayOfInfo() {
		return arrayOfInfo;
	}

	public void setArrayOfInfo(ArrayOfInfo arrayOfInfo) {
		this.arrayOfInfo = arrayOfInfo;
	}

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public ArrayOfRecentPayments getArrayOfPayments() {
		return arrayOfPayments;
	}

	public void setArrayOfPayments(ArrayOfRecentPayments arrayOfPayments) {
		this.arrayOfPayments = arrayOfPayments;
	}

//	public ArrayOfRecentAdjustments getArrayOfAdjustments() {
//		return arrayOfAdjustments;
//	}
//
//	public void setArrayOfAdjustments(ArrayOfRecentAdjustments arrayOfAdjustments) {
//		this.arrayOfAdjustments = arrayOfAdjustments;
//	}
//
//	public ArrayOfPaymentReturns getArrayOfPaymentReturns() {
//		return arrayOfPaymentReturns;
//	}
//
//	public void setArrayOfPaymentReturns(ArrayOfPaymentReturns arrayOfPaymentReturns) {
//		this.arrayOfPaymentReturns = arrayOfPaymentReturns;
//	}

	
}
