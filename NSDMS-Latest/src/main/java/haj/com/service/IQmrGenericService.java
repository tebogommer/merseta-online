package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.bean.QmrScriptOneBean;
import haj.com.entity.QmrFinYears;
import haj.com.entity.QmrLearnershipData;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.QmrEnteredCompletedEnum;
import haj.com.entity.enums.QmrTypeSelectionEnum;

/**
 * The Class QmrGenericService. Actions used across all QMR related Services
 */
public interface IQmrGenericService {

	public List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList = new ArrayList<>();
	public List<EmploymentStatusEnum> employmentStatusEnum     = new ArrayList<>();
	public List<LearnerStatusEnum>    learnerStatusEnumList    = new ArrayList<>();
	public QmrEnteredCompletedEnum    qmrEnteredCompletedEnum  = null;
	
	public List<QmrLearnershipData> extractReport(QmrFinYears qmrFinYears) throws Exception;

	/**
	 * Preps LearnerStatusEnum based on indicator provided 1 - Entered Status 2 - Completed Status
	 * @param indicator
	 * @return List<LearnerStatusEnum>
	 * @throws Exception
	 */
	public default List<LearnerStatusEnum> prepLearnerStatusEnumBasedOnIndicator(int indicator) throws Exception {
		List<LearnerStatusEnum> resultsList = new ArrayList<>();
		switch (indicator) {
			case 1:
				resultsList.add(LearnerStatusEnum.Registered);
				break;
			case 2:
				resultsList.add(LearnerStatusEnum.Completed);
				resultsList.add(LearnerStatusEnum.QualificationObtained);
				break;
			default:
				break;
		}
		return resultsList;
	}
	
	public default void determineEmploymentStatusEnum(int indicator) {
		switch (indicator) {
			case 1:
				employmentStatusEnum.add(EmploymentStatusEnum.UnEmployed);		
				break;

			default:
				break;
		}
		
//		employmentStatusEnum.addAll(addEmploymentStatusEnumToArrayList(EmploymentStatusEnum.UnEmployed));
	};

	public default List<EmploymentStatusEnum> addEmploymentStatusEnumToArrayList(EmploymentStatusEnum entry) throws Exception {
		List<EmploymentStatusEnum> results = new ArrayList<>();
		if (entry != null) {
			results.add(entry);
		}
		return results;
	}

	public default List<QmrTypeSelectionEnum> addQmrTypeSelectionEnumToArrayList(QmrTypeSelectionEnum entry) throws Exception {
		List<QmrTypeSelectionEnum> results = new ArrayList<>();
		if (entry != null) {
			results.add(entry);
		}
		return results;
	}

	public default void printImplementation() {
		System.out.println("Default Implementation");
	}
}