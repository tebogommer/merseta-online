package haj.com.service;

import java.util.List;

import haj.com.dao.NLRDDAO;
import haj.com.dataextract.bean.NLRDFile21Bean;
import haj.com.dataextract.bean.NLRDFile21BeanProviderVersionTwo;
import haj.com.dataextract.bean.NLRDFile22Bean;
import haj.com.dataextract.bean.NLRDFile23Bean;
import haj.com.dataextract.bean.NLRDFile24Bean;
import haj.com.dataextract.bean.NLRDFile24BeanProviderAccreditationVersionTwo;
import haj.com.dataextract.bean.NLRDFile25Bean;
import haj.com.dataextract.bean.NLRDFile25BeanPersonInformationVersionTwo;
import haj.com.dataextract.bean.NLRDFile26Bean;
import haj.com.dataextract.bean.NLRDFile26BeanPersonDesignationVersionTwo;
import haj.com.dataextract.bean.NLRDFile27Bean;
import haj.com.dataextract.bean.NLRDFile27BeanNQFDesignationRegistrationVersionTwo;
import haj.com.dataextract.bean.NLRDFile28Bean;
import haj.com.dataextract.bean.NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile29Bean;
import haj.com.dataextract.bean.NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile30Bean;
import haj.com.dataextract.bean.NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo;
import haj.com.entity.enums.DhetFileNumberEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NRLDService extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();
	
	/** Service Levels */
	private DhetReportingService dhetReportingService = new DhetReportingService();

	/**
	 * NRLD FILES
	 */
	public List<NLRDFile21Bean> extractNLRDFile21Bean() throws Exception {
		return dao.extractNLRDFile21Bean();
	}
	
	public List<NLRDFile21BeanProviderVersionTwo> extractNLRDFile21BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile21BeanVersionTwo();
	}
	
	public List<NLRDFile21BeanProviderVersionTwo> extractNLRDFile21BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile21BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile21));
	}
	
	public int extractNLRDFile21Insert() throws Exception {
		return dao.extractNLRDFile21Insert();
	}
	
	public List<NLRDFile22Bean> extractNLRDFile22Bean() throws Exception {
		return dao.extractNLRDFile22Bean();
	}
	
	public List<NLRDFile23Bean> extractNLRDFile23Bean() throws Exception {
		return dao.extractNLRDFile23Bean();
	}
	
	public List<NLRDFile24Bean> extractNLRDFile24Bean() throws Exception {
		return dao.extractNLRDFile24Bean();
	}
	
	public List<NLRDFile24BeanProviderAccreditationVersionTwo> extractNLRDFile24BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile24BeanVersionTwo();
	}
	
	public List<NLRDFile24BeanProviderAccreditationVersionTwo> extractNLRDFile24BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile24BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile24));
	}
	
	public int extractNLRDFile24Insert() throws Exception {
		return dao.extractNLRDFile24Insert();
	}
	
	public List<NLRDFile25Bean> extractNLRDFile25Bean() throws Exception {
		return dao.extractNLRDFile25Bean();
	}
	
	public List<NLRDFile25BeanPersonInformationVersionTwo> extractNLRDFile25BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile25BeanVersionTwo();
	}
	
	public List<NLRDFile25BeanPersonInformationVersionTwo> extractNLRDFile25BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile25BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile25));
	}
	
	public int extractNLRDFile25Insert() throws Exception {
		return dao.extractNLRDFile25Insert();
	}
	
	public List<NLRDFile26Bean> extractNLRDFile26Bean() throws Exception {
		return dao.extractNLRDFile26Bean();
	}
	
	public List<NLRDFile26BeanPersonDesignationVersionTwo> extractNLRDFile26BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile26BeanVersionTwo();
	}
	
	public List<NLRDFile26BeanPersonDesignationVersionTwo> extractNLRDFile26BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile26BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile26)); 
	}
	
	public int extractNLRDFile26Insert() throws Exception {
		return dao.extractNLRDFile26Insert();
	}
	
	public List<NLRDFile27Bean> extractNLRDFile27Bean() throws Exception {
		return dao.extractNLRDFile27Bean();
	}
	
	public List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> extractNLRDFile27BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile27BeanVersionTwo();
	}
	
	public List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> extractNLRDFile27BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile27BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile27));  
	}
	
	public int extractNLRDFile27Validation() throws Exception {
		return dao.extractNLRDFile27Validation();
	}
	
	public List<NLRDFile28Bean> extractNLRDFile28Bean() throws Exception {
		return dao.extractNLRDFile28Bean();
	}
	
	public List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> extractNLRDFile28BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile28BeanVersionTwo();
	}
	
	public List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> extractNLRDFile28BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile28BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile28)); 
	}
	
	public int extractNLRDFile28Insert() throws Exception {
		return dao.extractNLRDFile28Insert();
	}
	
	public List<NLRDFile29Bean> extractNLRDFile29Bean() throws Exception {
		return dao.extractNLRDFile29Bean();
	}
	
	public List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> extractNLRDFile29BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile29BeanVersionTwo();
	}
	
	public List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> extractNLRDFile29BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile29BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile29));  
	}
	
	public int extractNLRDFile29Validation() throws Exception {
		return dao.extractNLRDFile29Validation();
	}
	
	public List<NLRDFile30Bean> extractNLRDFile30Bean() throws Exception {
		return dao.extractNLRDFile30Bean();
	}
	
	public List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> extractNLRDFile30BeanVersionTwo() throws Exception {
		return dao.extractNLRDFile30BeanVersionTwo();
	}
	
	public List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> extractNLRDFile30BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractNLRDFile30BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.NrldFile30));
	}
	
	public int extractNLRDFile30Insert() throws Exception {
		return dao.extractNLRDFile30Insert();
	}
}
