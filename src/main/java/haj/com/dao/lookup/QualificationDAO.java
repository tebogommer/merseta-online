package haj.com.dao.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationDAO.
 */
public class QualificationDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Qualification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> allQualification() throws Exception {
		return (List<Qualification>) super.getList("select o from Qualification o");
	}

	/**
	 * Find qualification autocomplete.
	 *
	 * @param description
	 *            the description
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocomplete(String description) throws Exception {
		String hql = "select o from Qualification o where o.description LIKE :description or o.codeString like :code";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocomplete(String description, Date qualregistrationendDate)throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and date(o.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findLearningProgrammeByQual(String description,Qualification qual) throws Exception {
	

		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.etqaid =:etqaid and o.islearningprogramme = :islearningprogramme and o.learningprogrammequal =:learningprogrammequal ";
		
		if(description==null || description.isEmpty()==true || description.equals(""))
		{
			hql = "select o from Qualification o where o.etqaid =:etqaid and o.islearningprogramme = :islearningprogramme and o.learningprogrammequal =:learningprogrammequal ";
			
		}
		if(description !=null && description.isEmpty()==false && !description.equals(""))
		{
            parameters.put("description", description + "%");
			parameters.put("code", description + "%");
		}
		parameters.put("etqaid", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("islearningprogramme", "Yes");
		if(qual.getLearningprogrammequal() !=null){
			parameters.put("learningprogrammequal", qual.getLearningprogrammequal());
		}
		else {
			throw new Exception("No learning programme avaliable for the qualification selected");
		}
	
		return (List<Qualification>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocompleteDesignation(String description) throws Exception {
		String hql = "select o from Qualification o where o.designatedTrade is not null and (o.description LIKE :description or o.codeString like :code)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	/**
	 * All QCTO/Review qualification.
	 * 
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocompleteReview(String description) throws Exception {
		String qualificationtypedesc = "Occupational Certificate";
		String hql = "select o from Qualification o where  (o.description LIKE :description or o.codeString like :code) and o.qualificationtypedesc LIKE :qualificationtypedesc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		parameters.put("qualificationtypedesc", qualificationtypedesc + "%");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	// String hql = "select o from SaqaQualification o where o.saqadecisionnumber is
	// null and o.qualificationtitle LIKE :qualificationtitle";

	/**
	 * All SAQA/ReAlignment qualification.
	 * 
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocompleteReAlignment(String description) throws Exception {
		String qualificationtypedesc = "Occupational Certificate";
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.qualificationtypedesc Not LIKE :qualificationtypedesc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		parameters.put("qualificationtypedesc", qualificationtypedesc + "%");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	
	@SuppressWarnings("unchecked")
	public List<Qualification> findMersetaQualificationAutocomplete(String description) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.etqaid = :etqa and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) or "
				+ "(o.description LIKE :description or o.codeString like :code) and o.etqaid = :etqa and o.islearningprogramme = 'Yes' "
				+ "and (o.lastDateForEnrolment is null or date(o.lastDateForEnrolment) > date(:lastDateForEnrolment))";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("lastDateForEnrolment", new Date());
		List<Qualification> list=(List<Qualification>) super.getList(hql, parameters, 10);
		List<Qualification> returnList=new ArrayList<Qualification>();
		for(Qualification qual:list){
			if(qual.getLearningprogrammequal() !=null){
				Qualification theQual=findByCodeString(qual.getLearningprogrammequal());
				if(theQual !=null && theQual.getId() !=null){
					if(theQual.getLastDateForEnrolment() !=null){
						if(theQual.getLastDateForEnrolment().after(new Date())){
							returnList.add(qual);
						}
					}
				}
			}
			else {
				returnList.add(qual);
			}
		}
		
		return returnList;
	}
	
	public Qualification findByCodeString(String codeString) throws Exception {
		String hql = "select o from Qualification o where o.codeString = :codeString ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("codeString", codeString);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}
	
	public Qualification findByCodeStringNew(String description) throws Exception {
		String hql = "select o from Qualification o where o.codeString like :code";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		List<Qualification> quals = (List<Qualification>) super.getList(hql, parameters, 1);
		if (quals.isEmpty()) {
			return null;
		}
		return quals.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findNonMersetaQualificationAutocomplete(String description) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.etqaid != :etqa and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("lastDateForEnrolment", new Date());
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findNonMersetaQualificationAutocompleteWithTrades(String description) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and ( ( (o.etqaid is null or o.etqaid != :etqa) and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment)) or o.qualificationtypeid = :trade ) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("trade", HAJConstants.TRADE_QUALIFICATION_CODE);		
		parameters.put("lastDateForEnrolment", new Date());
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findNonMersetaQualificationAutocompleteWithNoTrades(String description) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and ( ( (o.etqaid is null or o.etqaid != :etqa) and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment)) and ( o.qualificationtypeid is null or o.qualificationtypeid <> :trade ) ) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("trade", HAJConstants.TRADE_QUALIFICATION_CODE);		
		parameters.put("lastDateForEnrolment", new Date());
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocompleteExcludingTrades(String description) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and ((date(o.lastDateForEnrolment) > date(:lastDateForEnrolment)) and ( o.qualificationtypeid is null or o.qualificationtypeid <> :trade )) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("trade", HAJConstants.TRADE_QUALIFICATION_CODE);		
		parameters.put("lastDateForEnrolment", new Date());
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findMersetaQualificationAutocompleteBeforeRegistrationDate(String description, Date qualregistrationendDate)  throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.etqaid = :etqa and date(o.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQCTOAutocompleteBeforeRegistrationDate(String description, Date qualregistrationendDate)  throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.qualificationtypedesc = :qualificationtypedesc and date(o.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("qualificationtypedesc", HAJConstants.QCTO_QUALIFICATION_TYPE_DESC);//qualificationtypeid=721
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQCTOTradeTestCentreQualBeforeRegDate(String description, Date qualregistrationendDate)  throws Exception {
		String hql = "select o from Qualification o where o.etqaid = :tradeCode or (o.description LIKE :description or o.codeString like :code) and o.qualificationtypedesc = :qualificationtypedesc and date(o.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", description + "%");
		parameters.put("qualificationtypedesc", HAJConstants.QCTO_QUALIFICATION_TYPE_DESC);//qualificationtypeid=721
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		parameters.put("tradeCode", HAJConstants.TRADE_QUALIFICATION_CODE);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	

	@SuppressWarnings("unchecked")
	public List<Qualification> findMersetaWPAQualificationAutocomplete(String description) throws Exception {
		Boolean wparequired = true;
		String hql = "select o from Qualification o where o.description LIKE :description and o.etqaid = :etqa and o.workplaceApprovalRequired = :wparequired";
		System.out.println("description:"+description);
		System.out.println("wparequired:"+wparequired);
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("wparequired", wparequired);
		// parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	@SuppressWarnings("unchecked")
	public List<Qualification> findMersetaWPAQualificationAutocompleteVersionTwo(String description) throws Exception {
		Boolean wparequired = true;
		String hql = "select o from Qualification o where (o.description LIKE :description or o.code LIKE :description ) and o.etqaid = :etqa and o.workplaceApprovalRequired = :wparequired";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("wparequired", wparequired);
		// parameters.put("code", description + "%");
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocompleteWPARequired(String description) throws Exception {
		Boolean wparequired = true;
		String hql = "select o from Qualification o where (o.description LIKE :description or o.code LIKE :description ) and o.workplaceApprovalRequired = :wparequired";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("wparequired", wparequired);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	/*
	 * @SuppressWarnings("unchecked") public List<Qualification>
	 * findMersetaQualificationAutocomplete(String description) throws Exception {
	 * String qualificationtypedesc = "Occupational Certificate"; String hql =
	 * "select o from Qualification o where  (o.description LIKE :description or o.codeString like :code) and o.qualificationtypedesc LIKE :qualificationtypedesc"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("description", "%" + description + "%");
	 * parameters.put("code", "%" + description + "%");
	 * parameters.put("qualificationtypedesc", qualificationtypedesc + "%"); return
	 * (List<Qualification>) super.getList(hql, parameters); }
	 */

	/**
	 * All qualification.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> allQualification(Long from, int noRows) throws Exception {
		String hql = "select o from Qualification o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Qualification>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return Qualification
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	public Qualification findByKey(Long id) throws Exception {
		String hql = "select o from Qualification o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by code.
	 *
	 * @param code
	 *            the code
	 * @return the qualification
	 * @throws Exception
	 *             the exception
	 */
	public Qualification findByCode(Integer code) throws Exception {
		String hql = "select o from Qualification o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by code.
	 *
	 * @param code
	 *            the code
	 * @return the qualification
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findByCompanyQualifications(Company company, boolean accept) throws Exception {
		String hql = "select o from Qualification o left join CompanyQualifications p on p.qualification.id = o.id left join company c on c.id = p.company.id where c.id = :companyId and p.accept = :accept ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		parameters.put("accept", accept);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	public Qualification findByCompanyQualifications(Company company, Qualification qualification, boolean accept) throws Exception {
		String hql = "select o from Qualification o " + "left join CompanyandQualifications p on p.qualification.id = o.id" + "left join merseta.company c on c.id = p.company.id " + "where c.id = :companyId " + "and p.accept = :accept" + "and o.id = :qualificationId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		parameters.put("qualificationId", qualification.getId());
		parameters.put("accept", accept);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Qualification by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * @throws Exception
	 *             the exception
	 * @see Qualification
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findByName(String description) throws Exception {
		String hql = "select o from Qualification o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	/**
	 * Find qualification autocomplete by description or code on decription passed
	 * and lastDateForEnrolment before lastDateForEnrolment on Qualification.
	 *
	 * @param description
	 *            Description enetered
	 * @param lastDateForEnrolment
	 *            The date passed
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationAutocompleteBeforeLastEnrolmentDate(String description, Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualBeforeLastEnrolmentDateAndTrade(String description, Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and (date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) or o.qualificationtypeid = :tradeCode)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", description + "%");
		parameters.put("code", description + "%");
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		parameters.put("tradeCode", HAJConstants.TRADE_QUALIFICATION_CODE);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	@SuppressWarnings("unchecked")
	public List<Qualification> completeNonTradeQualBeforeLastEnrolmentDate(String description, Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) and (o.registrationstatuscode <> :registrationstatuscode or o.registrationstatuscode = null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", "%" + description + "%");
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		parameters.put("registrationstatuscode", "T");
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	/**
	 * Finds Qualification where lastDateForEnrolment before lastDateForEnrolment on
	 * Qualification
	 * 
	 * @param lastDateForEnrolment
	 *            The date passed
	 * 
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * 
	 * @throws Exception
	 *             global exception
	 * @see Qualification
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationBeforeLastEnrolmentDate(Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}

	/**
	 * Finds Qualification where lastDateForEnrolment before lastDateForEnrolment on
	 * Qualification is a trade
	 * 
	 * @param lastDateForEnrolment
	 *            The date passed
	 * 
	 * @return a list of {@link haj.com.entity.lookup.Qualification}
	 * 
	 * @throws Exception
	 *             global exception
	 * @see Qualification
	 */
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationBeforeLastEnrolmentDateAndTrade(Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) and o.qualificationtypeid = :tradeCode order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		parameters.put("tradeCode", HAJConstants.TRADE_QUALIFICATION_CODE);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> completeQualificationEnrolmentDateAndIsTrade(String description, Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and  o.qualificationtypeid = :tradeCode ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", "%" + description + "%");
		parameters.put("tradeCode", HAJConstants.TRADE_QUALIFICATION_CODE);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> completeQualificationTrade(String description) throws Exception {
		String hql = "select o from Qualification o where o.qualificationtypeid = :qualificationtypeid and (o.description LIKE :description or o.codeString like :code) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", "%" + description + "%");
		parameters.put("qualificationtypeid", HAJConstants.TRADE_QUALIFICATION_CODE);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> completeQualificationTradeWPARequired(String description) throws Exception {
		boolean wparequired = true;
		String hql = "select o from Qualification o where o.qualificationtypeid = :qualificationtypeid and o.workplaceApprovalRequired = :wparequired and (o.description LIKE :description or o.codeString like :code) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wparequired", wparequired);
		parameters.put("description", "%" + description + "%");
		parameters.put("code", "%" + description + "%");
		parameters.put("qualificationtypeid", HAJConstants.TRADE_QUALIFICATION_CODE);
		return (List<Qualification>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> completeQualificationLearningProgrammes(String description) throws Exception {
		Map<String, Object> parameters = new Hashtable<String, Object>();
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.etqaid =:etqaid and o.islearningprogramme = :islearningprogramme ";
		
		if(description==null || description.isEmpty()==true || description.equals(""))
		{
			hql = "select o from Qualification o where o.etqaid =:etqaid and o.islearningprogramme = :islearningprogramme ";
			
		}
		if(description !=null && description.isEmpty()==false && !description.equals(""))
		{
            parameters.put("description", description + "%");
			parameters.put("code", description + "%");
		}
		parameters.put("etqaid", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("islearningprogramme", "Yes");
		return (List<Qualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> completeQualificationEnrolmentDateAndIsOccupationalCertificate(String description, Date lastDateForEnrolment) throws Exception {
		String hql = "select o from Qualification o where (o.description LIKE :description or o.codeString like :code) and o.qualificationtypeid = :occCode and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		parameters.put("code", "%" + description + "%");
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		parameters.put("occCode", HAJConstants.OCCUPATIONAL_CERTIFICATE_CODE);
		return (List<Qualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> allLearnerinProgrammes() throws Exception {
		String hql = "select o from Qualification o where UPPER(o.islearningprogramme) = :islearningprogramme and o.learningprogrammequal <> null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("islearningprogramme", "YES");
		return (List<Qualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> allSubField() throws Exception {
		String hql = "select o from Qualification o where o.subfield is null and o.subfielddescription is not null and o.subfielddescription <> ''";
		return (List<Qualification>) super.getList(hql);
	}
}
