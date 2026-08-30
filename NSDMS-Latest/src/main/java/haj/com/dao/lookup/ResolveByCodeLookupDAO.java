package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import haj.com.entity.Company;
import haj.com.entity.Municipality;
import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyOrganisationNonLevyPaying;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Nationality;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BankDAO.
 */
public class ResolveByCodeLookupDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	public OfoCodes findOfoCode(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from OfoCodes o where o.ofoCode = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	public OfoCodes findOfoCodeBySpecialisation(String ofoCode) throws Exception {
		String hql = "select o from OfoCodes o where o.specialisationCode = :ofoCode ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCode", ofoCode);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}
	
	public OfoCodes findOfoCodeAndSpecialisation(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from OfoCodes o where o.ofoCode = :code or o.specialisationCode = :code";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}


	/**
	 * Locates the Ofo code by ofo code and where not active/old code
	 * 
	 * @param code
	 * @return OfoCodes
	 * @throws Exception
	 */
	public OfoCodes findOfoCodeAtr(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from OfoCodes o where o.ofoCode = :code and o.active = :filterValue";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		parameters.put("filterValue", false);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}
	
	public OfoCodes findOfoCodeByCodeAndYear(String code, Integer year) throws Exception {
		if (code == null) return null;
		String hql = "select o from OfoCodes o where o.ofoCode = :code and o.year = :year";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		parameters.put("year", year);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Locates the Ofo code by ofo code and where active/ not old code
	 * 
	 * @param code
	 * @return OfoCodes
	 * @throws Exception
	 */
	public OfoCodes findOfoCodeWsp(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from OfoCodes o where o.ofoCode = :code and o.active = :filterValue";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		parameters.put("filterValue", true);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Locates the Ofo code by Specialisation code and where not active/old code
	 * 
	 * @param ofoCode
	 * @return OfoCodes
	 * @throws Exception
	 */
	public OfoCodes findOfoCodeBySpecialisationAtr(String ofoCode) throws Exception {
		String hql = "select o from OfoCodes o where o.specialisationCode = :ofoCode and o.active = :filterValue";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCode", ofoCode);
		parameters.put("filterValue", false);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}
	
	public OfoCodes findOfoCodeBySpecialisationYear(String ofoCode, Integer year) throws Exception {
		String hql = "select o from OfoCodes o where o.specialisationCode = :ofoCode and o.year = :year";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCode", ofoCode);
		parameters.put("year", year);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Locates the Ofo code by Specialisation code and where active/ not old code
	 * 
	 * @param ofoCode
	 * @return OfoCodes
	 * @throws Exception
	 */
	public OfoCodes findOfoCodeBySpecialisationWsp(String ofoCode) throws Exception {
		String hql = "select o from OfoCodes o where o.specialisationCode = :ofoCode and o.active = :filterValue";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCode", ofoCode);
		parameters.put("filterValue", true);
		return (OfoCodes) super.getUniqueResult(hql, parameters);
	}

	public Funding findFunding(String code) throws Exception {
		if (code == null) return null;

		String hql = "select o from Funding o where o.code = :code and o.appearOnWsp = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		return (Funding) super.getUniqueResult(hql, parameters);
	}

	public Funding findFundingById(Long id) throws Exception {
		String hql = "select o from Funding o where o.id = :id and o.appearOnWsp = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Funding) super.getUniqueResult(hql, parameters);
	}

	public InterventionType findInterventionType(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from InterventionType o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (InterventionType) super.getUniqueResult(hql, parameters);
	}

	public Qualification findQualification(Integer code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Qualification o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}

	public Qualification findQualification(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Qualification o where o.codeString = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}

	public Company findCompany(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Company o where o.levyNumber = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Company) super.getUniqueResult(hql, parameters);
	}

	public boolean findLegacyOrganisationSites(String code) throws Exception {
		if (code == null) return false;
		String hql = "select o from LegacyOrganisationSites o where o.sdlNumber = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		List<LegacyOrganisationSites> legacyOrganisationSites = (List<LegacyOrganisationSites>) super.getList(hql, parameters);
		return !legacyOrganisationSites.isEmpty();
	}

	public boolean findLegacyOrganisationNonLevyPaying(String code) throws Exception {
		if (code == null) return false;
		String hql = "select o from LegacyOrganisationNonLevyPaying o where o.linkedSdl = :code or o.mainSDL = :code";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		List<LegacyOrganisationNonLevyPaying> legacyOrganisationSites = (List<LegacyOrganisationNonLevyPaying>) super.getList(hql, parameters);
		return !legacyOrganisationSites.isEmpty();
	}

	public boolean checkCountByRefNumber(String refNumber) throws Exception {
		String hql = "select count(o) from SarsEmployerDetail o where o.refNo =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}

	public boolean checkLegacyDTTCApproval(String refNumber) throws Exception {
		String hql = "select count(o) from LegacyDTTCApproval o where o.sdlNo =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}

	public boolean checkLegacyProviderAccreditation(String refNumber) throws Exception {
		String hql = "select count(o) from LegacyProviderAccreditation o where o.accreditationNo =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}

	public boolean checkLegacyProviderSDL(String refNumber) throws Exception {
		String hql = "select count(o) from LegacyProviderAccreditation o where o.sdlNumber =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}


	public boolean checkLegacyEmployerWA2Workplace(String refNumber) throws Exception {
		String hql = "select count(o) from LegacyEmployerWA2Workplace o where o.sdlNo =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}

	public boolean checkLegacyModeratorAccreditation(String refNumber) throws Exception {
		String hql = "select count(o) from LegacyModeratorAccreditation o where o.idNo =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}

	public boolean checkLegacyAssessorAccreditation(String refNumber) throws Exception {
		String hql = "select count(o) from LegacyAssessorAccreditation o where o.idNo =  :refNumber ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("refNumber", refNumber.trim());
		int count = ((Long) super.getUniqueResult(hql, parameters)).intValue();
		return count > 0;
	}


	public UnitStandards findUnitStandards(Integer code) throws Exception {
		if (code == null) return null;
		String hql = "select o from UnitStandards o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (UnitStandards) super.getUniqueResult(hql, parameters);
	}

	public UnitStandards findUnitStandards(String code) throws Exception {
		if (code == null) return null;
		if (!StringUtils.isNumeric(code)) return null;
		String hql = "select o from UnitStandards o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", Integer.parseInt(code));
		return (UnitStandards) super.getUniqueResult(hql, parameters);
	}

	public SkillsSet findSkillsSet(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from SkillsSet o where o.programmeID = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (SkillsSet) super.getUniqueResult(hql, parameters);
	}

	public SkillsProgram findSkillsProgram(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from SkillsProgram o where o.programmeID = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (SkillsProgram) super.getUniqueResult(hql, parameters);
	}

	public Learnership findLearnership(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Learnership o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		List<Learnership> learnerships = (List<Learnership>) super.getList(hql, parameters);
		if (learnerships.isEmpty()) return null;
		if (learnerships.size() > 1) return null;
		return learnerships.get(0);
	}

	public ProviderType findProviderType(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from ProviderType o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (ProviderType) super.getUniqueResult(hql, parameters);
	}

	public TrainingDeliveryMethod findTrainingDeliveryMethod(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from TrainingDeliveryMethod o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (TrainingDeliveryMethod) super.getUniqueResult(hql, parameters);
	}

	public DisabilityStatus findDisability(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from DisabilityStatus o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (DisabilityStatus) super.getUniqueResult(hql, parameters);
	}

	public Gender findGender(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Gender o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Gender) super.getUniqueResult(hql, parameters);
	}

	public Equity findEquity(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Equity o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Equity) super.getUniqueResult(hql, parameters);
	}

	public Nationality findNationality(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Nationality o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Nationality) super.getUniqueResult(hql, parameters);
	}

	public EnrolmentStatus findEnrolmentStatus(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from EnrolmentStatus o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (EnrolmentStatus) super.getUniqueResult(hql, parameters);
	}
	
	public Municipality findMunicipality(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from Municipality o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim().toUpperCase());
		return (Municipality) super.getUniqueResult(hql, parameters);
	}
	
	public NonCreditBearingIntervationTitle findNonCreditBearingIntervationTitle(String code) throws Exception {
		if (code == null) return null;
		String hql = "select o from NonCreditBearingIntervationTitle o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (NonCreditBearingIntervationTitle) super.getUniqueResult(hql, parameters);
	}
	
}
