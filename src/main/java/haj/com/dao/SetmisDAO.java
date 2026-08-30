package haj.com.dao;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.dataextract.bean.QCTO01Bean;
import haj.com.dataextract.bean.QCTO02Bean;
import haj.com.dataextract.bean.QCTO03Bean;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.dataextract.bean.SETMISFile100BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile200Bean;
import haj.com.dataextract.bean.SETMISFile200BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.dataextract.bean.SETMISFile400BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.dataextract.bean.SETMISFile503Bean;
import haj.com.dataextract.bean.SETMISFile503BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile505Bean;
import haj.com.dataextract.bean.SETMISFile506Bean;
import haj.com.dataextract.bean.SETMISFile506BeanSandra;
import haj.com.entity.Company;
import haj.com.entity.SetmisFile100;
import haj.com.entity.SetmisFile200;
import haj.com.entity.SetmisFile304;
import haj.com.entity.SetmisFile400;
import haj.com.entity.SetmisFile401;
import haj.com.entity.SetmisFile500;
import haj.com.entity.SetmisFile501;
import haj.com.entity.SetmisFile502;
import haj.com.entity.SetmisFile503;
import haj.com.entity.SetmisFile505;
import haj.com.entity.SetmisFile506;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankDAO.
 */
public class SetmisDAO extends AbstractDAO {
	
	private static final String whereEmployers = "select cJoinE.id from company cJoinE "
			+ " inner join sdf_company sdfJoinE on sdfJoinE.company_id = cJoinE.id "
			+ " inner join sic_code scJoinE on scJoinE.id = cJoinE.sic_code_id "
			+ " left join address aJoinE on aJoinE.id = cJoinE.postal_address_id "
			+ " left join address apJoinE on aJoinE.id = cJoinE.residential_address_id "
			+ " inner join users uJoinE on uJoinE.id = sdfJoinE.sdf_id "
			+ " left join municipality mJoinE on mJoinE.id = aJoinE.municipality_id "
			+ " left join province pJoinE on pJoinE.id = mJoinE.provinces_idprovinces "
			+ " where cJoinE.levy_number is not NULL and sdf_type_id = 1 ";
	private static final String whereProviders = "select cJoinP.id "
			+ " from training_provider_application tpaJoinP  "
			+ " inner join company cJoinP on cJoinP.id = tpaJoinP.company_id  "
			+ " left join sic_code scJoinP on scJoinP.id = cJoinP.sic_code_id  "
			+ " inner join etqa eJoinP on tpaJoinP.etqa_id = eJoinP.id  "
			+ " left join provider_type ptJoinP on ptJoinP.id = tpaJoinP.provider_type  "
			+ " inner join address aJoinP on aJoinP.id = cJoinP.residential_address_id  "
			+ " inner join address apJoinP on apJoinP.id = cJoinP.postal_address_id  "
			+ " inner join municipality mJoinP on mJoinP.id = apJoinP.municipality_id  "
			+ " inner join province pJoinP on pJoinP.id = mJoinP.provinces_idprovinces  "
			+ " left join provider_status psJoinP on psJoinP.id = tpaJoinP.provider_status_id "
			+ " left join provider_class pcJoinP on pcJoinP.id = tpaJoinP.provider_class "
			+ " left join ( select MAX(rcmgJoinP.decision_number) as etqeDecisionNumber, rcmgJoinP.review_committee_meeting_id from Review_committee_meeting_agenda rcmgJoinP group by rcmgJoinP.review_committee_meeting_id) fJoinP on fJoinP.review_committee_meeting_id = tpaJoinP.review_committee_meeting_id ";
	
	private static final String whereNQFsset = "select sset.id "
	+ " from company_learners cl  "
	+ " inner join users u on u.id = cl.user_id "
	+ " inner join skills_set sset on sset.id = cl.skills_set_id "
	+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
	+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
	+ " inner join company c on c.id = cl.company_id "
	+ " inner join training_provider_application tpa on tpa.company_id = c.id "
	+ " left join etqa on etqa.id = tpa.etqa_id  "
	+ " left join company cEmp on cEmp.id = cl.employer_id "
	+ " inner join employment_type et on et.employment_status = u.employment_status  "
	+ " left join funding f on f.id = cl.funding "
	+ " left join ofo on ofo.id = cl.ofo_codes_id  "
	+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	+ " inner join subfield sbf on sbf.id = sq.sub_field_id "
	+ " inner join etqa e on e.id = sset.netqa_id "
	+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null ";



	private static final String whereNQFspro = " select spro.id "
	+ " from company_learners cl  "
	+ " inner join users u on u.id = cl.user_id "
	+ " inner join skills_program spro on spro.id = cl.skills_set_id "
	+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
	+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
	+ " inner join company c on c.id = cl.company_id "
	+ " inner join training_provider_application tpa on tpa.company_id = c.id  "
	+ " left join etqa on etqa.id = tpa.etqa_id  "
	+ " left join company cEmp on cEmp.id = cl.employer_id "
	+ " inner join employment_type et on et.employment_status = u.employment_status  "
	+ " left join funding f on f.id = cl.funding "
	+ " left join ofo on ofo.id = cl.ofo_codes_id  "
	+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	+ " inner join subfield sbf on sbf.id = sq.sub_field_id "
	+ " inner join etqa e on e.id = spro.netqa_id "
	+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null ";

	
	private static final String whereLearners = " select c.id from company_learners cl  "
			+ " inner join users u on u.id = cl.user_id "
			+ " inner join learnership lship on lship.id = cl.learnership_id "
			+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ " inner join company c on c.id = cl.company_id "
			+ " inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ " left join etqa on etqa.id = tpa.etqa_id  "
			+ " inner join company cEmp on cEmp.id = cl.employer_id  "
			+ " inner join employment_type et on et.employment_status = u.employment_status  "
			+ " left join funding f on f.id = cl.funding "
			+ " left join ofo on ofo.id = cl.ofo_codes_id  "
			+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null "
			+ "  UNION ALL  "
			+ "  select c.id "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   UNION ALL  "
			+ "   select c.id "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "   inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "   inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "   left join etqa etqt on etqt.id = ct.etqa_id  "
			+ "   UNION ALL "
			+ "   select c.id "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join learnership lship on lship.id = cl.learnership_id "
			+ "   inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
			+ "   UNION ALL "
			+ "   select c.id  "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "   inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id  "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
			+ "   UNION ALL "
			+ "   select c.id  "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "   inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id  "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding  "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null "
			+ "   UNION ALL "
			+ "   select c.id  "
			+ "    from company_learners cl  "
			+ "    inner join users u on u.id = cl.user_id "
			+ "    inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "    inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "    inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "    inner join company c on c.id = cl.company_id "
			+ "    inner join training_provider_application tpa on tpa.company_id = c.id "
			+ "    left join etqa on etqa.id = tpa.etqa_id  "
			+ "    left join company cEmp on cEmp.id = cl.employer_id "
			+ "    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "    left join funding f on f.id = cl.funding "
			+ "    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "    inner join subfield sbf on sbf.id = sq.sub_field_id "
			+ "    inner join etqa e on e.id = sset.netqa_id "
			+ "    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
			+ "    UNION ALL "
			+ "    select c.id   "
			+ "    from company_learners cl  "
			+ "    inner join users u on u.id = cl.user_id "
			+ "    inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "    inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "    inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "    inner join company c on c.id = cl.company_id "
			+ "    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "    left join etqa on etqa.id = tpa.etqa_id  "
			+ "    left join company cEmp on cEmp.id = cl.employer_id "
			+ "    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "    left join funding f on f.id = cl.funding "
			+ "    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "    inner join subfield sbf on sbf.id = sq.sub_field_id "
			+ "    inner join etqa e on e.id = spro.netqa_id "
			+ "    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
			+ "	UNION ALL "
			+ "	select c.id "
			+ "	  from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	 inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 UNION ALL  "
			+ "	 select c.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	 inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "	 inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "	 inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "	 left join etqa etqt on etqt.id = ct.etqa_id  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id  "
			+ "	 UNION ALL  "
			+ "	 select c.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join learnership lship on lship.id = cl.learnership_id "
			+ "	 inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
			+ "	 UNION ALL  "
			+ "	 select c.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "	 inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
			+ "	 UNION ALL "
			+ "	 select c.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "	 inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null "
			+ "	 UNION ALL "
			+ "	 select c.id "
			+ "	  from company_learners cl  "
			+ "	  inner join users u on u.id = cl.user_id "
			+ "	  inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "	  inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	  inner join company c on c.id = cl.company_id "
			+ "	  inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	  left join etqa on etqa.id = tpa.etqa_id  "
			+ "	  inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	  left join funding f on f.id = cl.funding "
			+ "	  left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	  left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "	  inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "	  inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "	  left join etqa etqt on etqt.id = ct.etqa_id "
			+ "	  UNION ALL "
			+ "	  select c.id   "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    UNION ALL "
			+ "	   select c.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "	    inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "	    inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "	    left join etqa etqt on etqt.id = ct.etqa_id  "
			+ "	    UNION ALL "
			+ "	    select c.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join learnership lship on lship.id = cl.learnership_id "
			+ "	    inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
			+ "	    UNION ALL "
			+ "	    select c.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "	    inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
			+ "	    UNION ALL "
			+ "	    select c.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "	    inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null ";
	
	private static final String whereLearnersTwo = " select cl.id from company_learners cl  "
			+ " inner join users u on u.id = cl.user_id "
			+ " inner join learnership lship on lship.id = cl.learnership_id "
			+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ " inner join company c on c.id = cl.company_id "
			+ " inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ " left join etqa on etqa.id = tpa.etqa_id  "
			+ " inner join company cEmp on cEmp.id = cl.employer_id  "
			+ " inner join employment_type et on et.employment_status = u.employment_status  "
			+ " left join funding f on f.id = cl.funding "
			+ " left join ofo on ofo.id = cl.ofo_codes_id  "
			+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null "
			+ "  UNION ALL  "
			+ "  select cl.id "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   UNION ALL  "
			+ "   select cl.id "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "   inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "   inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "   left join etqa etqt on etqt.id = ct.etqa_id  "
			+ "   UNION ALL "
			+ "   select cl.id "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join learnership lship on lship.id = cl.learnership_id "
			+ "   inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
			+ "   UNION ALL "
			+ "   select cl.id  "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "   inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id  "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
			+ "   UNION ALL "
			+ "   select cl.id  "
			+ "   from company_learners cl  "
			+ "   inner join users u on u.id = cl.user_id "
			+ "   inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "   inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "   inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "   inner join company c on c.id = cl.company_id "
			+ "   inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "   left join etqa on etqa.id = tpa.etqa_id  "
			+ "   left join company cEmp on cEmp.id = cl.employer_id  "
			+ "   inner join employment_type et on et.employment_status = u.employment_status  "
			+ "   left join funding f on f.id = cl.funding  "
			+ "   left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "   where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null "
			+ "   UNION ALL "
			+ "   select cl.id  "
			+ "    from company_learners cl  "
			+ "    inner join users u on u.id = cl.user_id "
			+ "    inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "    inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "    inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "    inner join company c on c.id = cl.company_id "
			+ "    inner join training_provider_application tpa on tpa.company_id = c.id "
			+ "    left join etqa on etqa.id = tpa.etqa_id  "
			+ "    left join company cEmp on cEmp.id = cl.employer_id "
			+ "    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "    left join funding f on f.id = cl.funding "
			+ "    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "    inner join subfield sbf on sbf.id = sq.sub_field_id "
			+ "    inner join etqa e on e.id = sset.netqa_id "
			+ "    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
			+ "    UNION ALL "
			+ "    select cl.id   "
			+ "    from company_learners cl  "
			+ "    inner join users u on u.id = cl.user_id "
			+ "    inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "    inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "    inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "    inner join company c on c.id = cl.company_id "
			+ "    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "    left join etqa on etqa.id = tpa.etqa_id  "
			+ "    left join company cEmp on cEmp.id = cl.employer_id "
			+ "    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "    left join funding f on f.id = cl.funding "
			+ "    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "    inner join subfield sbf on sbf.id = sq.sub_field_id "
			+ "    inner join etqa e on e.id = spro.netqa_id "
			+ "    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
			+ "	UNION ALL "
			+ "	select cl.id "
			+ "	  from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	 inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 UNION ALL  "
			+ "	 select cl.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	 inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "	 inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "	 inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "	 left join etqa etqt on etqt.id = ct.etqa_id  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id  "
			+ "	 UNION ALL  "
			+ "	 select cl.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join learnership lship on lship.id = cl.learnership_id "
			+ "	 inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
			+ "	 UNION ALL  "
			+ "	 select cl.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "	 inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
			+ "	 UNION ALL "
			+ "	 select cl.id "
			+ "	 from company_learners cl  "
			+ "	 inner join users u on u.id = cl.user_id "
			+ "	 inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "	 inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	 inner join company c on c.id = cl.company_id "
			+ "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	 left join etqa on etqa.id = tpa.etqa_id  "
			+ "	 left join company cEmp on cEmp.id = cl.employer_id "
			+ "	 inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	 left join funding f on f.id = cl.funding "
			+ "	 left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
			+ "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
			+ "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
			+ "	 where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null "
			+ "	 UNION ALL "
			+ "	 select cl.id "
			+ "	  from company_learners cl  "
			+ "	  inner join users u on u.id = cl.user_id "
			+ "	  inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "	  inner join intervention_type it on it.id = cl.intervention_type_id  "
			+ "	  inner join company c on c.id = cl.company_id "
			+ "	  inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	  left join etqa on etqa.id = tpa.etqa_id  "
			+ "	  inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	  left join funding f on f.id = cl.funding "
			+ "	  left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	  left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	  inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "	  inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "	  inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "	  left join etqa etqt on etqt.id = ct.etqa_id "
			+ "	  UNION ALL "
			+ "	  select cl.id   "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    UNION ALL "
			+ "	   select cl.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
			+ "	    inner join company ct on ct.id = cltt.`preferred_training_center_id` "
			+ "	    inner join training_provider_application tpat on tpat.company_id = ct.id  "
			+ "	    left join etqa etqt on etqt.id = ct.etqa_id  "
			+ "	    UNION ALL "
			+ "	    select cl.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join learnership lship on lship.id = cl.learnership_id "
			+ "	    inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
			+ "	    UNION ALL "
			+ "	    select cl.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join skills_set sset on sset.id = cl.skills_set_id "
			+ "	    inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
			+ "	    UNION ALL "
			+ "	    select cl.id  "
			+ "	    from company_learners cl  "
			+ "	    inner join users u on u.id = cl.user_id "
			+ "	    inner join skills_program spro on spro.id = cl.skills_set_id "
			+ "	    inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
			+ "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
			+ "	    inner join company c on c.id = cl.company_id "
			+ "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
			+ "	    left join etqa on etqa.id = tpa.etqa_id  "
			+ "	    inner join company cEmp on cEmp.id = cl.employer_id "
			+ "	    inner join employment_type et on et.employment_status = u.employment_status  "
			+ "	    left join funding f on f.id = cl.funding "
			+ "	    left join ofo on ofo.id = cl.ofo_codes_id  "
			+ "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
			+ "	    where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null ";
	
	
	
	private static final String whereLearnersEmployers = "select cEmp.id  "
	  + " from company_learners cl  "
	  + " inner join users u on u.id = cl.user_id "
	  + " inner join learnership lship on lship.id = cl.learnership_id "
	  + " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
	  + " inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + " inner join company c on c.id = cl.company_id "
	  + " inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + " left join etqa on etqa.id = tpa.etqa_id  "
	  + " inner join company cEmp on cEmp.id = cl.employer_id  "
	  + " inner join employment_type et on et.employment_status = u.employment_status  "
	  + " left join funding f on f.id = cl.funding "
	  + " left join ofo on ofo.id = cl.ofo_codes_id  "
	  + " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null "
	  + "  UNION ALL  "
	  + "  select cEmp.id "
	  + "   from company_learners cl  "
	  + "   inner join users u on u.id = cl.user_id "
	  + "   inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
	  + "   inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "   inner join company c on c.id = cl.company_id "
	  + "   inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "   left join etqa on etqa.id = tpa.etqa_id  "
	  + "   left join company cEmp on cEmp.id = cl.employer_id "
	  + "   inner join employment_type et on et.employment_status = u.employment_status  "
	  + "   left join funding f on f.id = cl.funding "
	  + "   left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "   UNION ALL  "
	  + "   select cEmp.id "
	  + "   from company_learners cl  "
	  + "   inner join users u on u.id = cl.user_id "
	  + "   inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
	  + "   inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "   inner join company c on c.id = cl.company_id "
	  + "   inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "   left join etqa on etqa.id = tpa.etqa_id  "
	  + "   left join company cEmp on cEmp.id = cl.employer_id "
	  + "   inner join employment_type et on et.employment_status = u.employment_status  "
	  + "   left join funding f on f.id = cl.funding "
	  + "   left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "   inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
	  + "   inner join company ct on ct.id = cltt.`preferred_training_center_id` "
	  + "   inner join training_provider_application tpat on tpat.company_id = ct.id  "
	  + "   left join etqa etqt on etqt.id = ct.etqa_id  "
	  + "   UNION ALL "
	  + "   select cEmp.id "
	  + "   from company_learners cl  "
	  + "   inner join users u on u.id = cl.user_id "
	  + "   inner join learnership lship on lship.id = cl.learnership_id "
	  + "   inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
	  + "   inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "   inner join company c on c.id = cl.company_id "
	  + "   inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "   left join etqa on etqa.id = tpa.etqa_id  "
	  + "   left join company cEmp on cEmp.id = cl.employer_id "
	  + "   inner join employment_type et on et.employment_status = u.employment_status  "
	  + "   left join funding f on f.id = cl.funding "
	  + "   left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "   where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
	  + "   UNION ALL "
	  + "   select cEmp.id  "
	  + "   from company_learners cl  "
	  + "   inner join users u on u.id = cl.user_id "
	  + "   inner join skills_set sset on sset.id = cl.skills_set_id "
	  + "   inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
	  + "   inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "   inner join company c on c.id = cl.company_id "
	  + "   inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "   left join etqa on etqa.id = tpa.etqa_id  "
	  + "   left join company cEmp on cEmp.id = cl.employer_id  "
	  + "   inner join employment_type et on et.employment_status = u.employment_status  "
	  + "   left join funding f on f.id = cl.funding "
	  + "   left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "   where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
	  + "   UNION ALL "
	  + "   select cEmp.id  "
	  + "   from company_learners cl  "
	  + "   inner join users u on u.id = cl.user_id "
	  + "   inner join skills_program spro on spro.id = cl.skills_set_id "
	  + "   inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
	  + "   inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "   inner join company c on c.id = cl.company_id "
	  + "   inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "   left join etqa on etqa.id = tpa.etqa_id  "
	  + "   left join company cEmp on cEmp.id = cl.employer_id  "
	  + "   inner join employment_type et on et.employment_status = u.employment_status  "
	  + "   left join funding f on f.id = cl.funding  "
	  + "   left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "   left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "   where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null "
	  + "   UNION ALL "
	  + "   select cEmp.id  "
	  + "    from company_learners cl  "
	  + "    inner join users u on u.id = cl.user_id "
	  + "    inner join skills_set sset on sset.id = cl.skills_set_id "
	  + "    inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
	  + "    inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "    inner join company c on c.id = cl.company_id "
	  + "    inner join training_provider_application tpa on tpa.company_id = c.id "
	  + "    left join etqa on etqa.id = tpa.etqa_id  "
	  + "    left join company cEmp on cEmp.id = cl.employer_id "
	  + "    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "    left join funding f on f.id = cl.funding "
	  + "    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "    inner join subfield sbf on sbf.id = sq.sub_field_id "
	  + "    inner join etqa e on e.id = sset.netqa_id "
	  + "    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
	  + "    UNION ALL "
	  + "    select cEmp.id   "
	  + "    from company_learners cl  "
	  + "    inner join users u on u.id = cl.user_id "
	  + "    inner join skills_program spro on spro.id = cl.skills_set_id "
	  + "    inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
	  + "    inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "    inner join company c on c.id = cl.company_id "
	  + "    inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "    left join etqa on etqa.id = tpa.etqa_id  "
	  + "    left join company cEmp on cEmp.id = cl.employer_id "
	  + "    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "    left join funding f on f.id = cl.funding "
	  + "    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "    inner join subfield sbf on sbf.id = sq.sub_field_id "
	  + "    inner join etqa e on e.id = spro.netqa_id "
	  + "    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
	  + "	UNION ALL "
	  + "	select cEmp.id "
	  + "	  from company_learners cl  "
	  + "	 inner join users u on u.id = cl.user_id "
	  + "	 inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
	  + "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "	 inner join company c on c.id = cl.company_id "
	  + "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	 left join etqa on etqa.id = tpa.etqa_id  "
	  + "	 left join company cEmp on cEmp.id = cl.employer_id "
	  + "	 inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	 left join funding f on f.id = cl.funding "
	  + "	 left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	 inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
	  + "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
	  + "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
	  + "	 UNION ALL  "
	  + "	 select cEmp.id "
	  + "	 from company_learners cl  "
	  + "	 inner join users u on u.id = cl.user_id "
	  + "	 inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
	  + "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "	 inner join company c on c.id = cl.company_id "
	  + "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	 left join etqa on etqa.id = tpa.etqa_id  "
	  + "	 left join company cEmp on cEmp.id = cl.employer_id "
	  + "	 inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	 left join funding f on f.id = cl.funding "
	  + "	 left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	 inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
	  + "	 inner join company ct on ct.id = cltt.`preferred_training_center_id` "
	  + "	 inner join training_provider_application tpat on tpat.company_id = ct.id  "
	  + "	 left join etqa etqt on etqt.id = ct.etqa_id  "
	  + "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
	  + "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
	  + "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id  "
	  + "	 UNION ALL  "
	  + "	 select cEmp.id "
	  + "	 from company_learners cl  "
	  + "	 inner join users u on u.id = cl.user_id "
	  + "	 inner join learnership lship on lship.id = cl.learnership_id "
	  + "	 inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
	  + "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "	 inner join company c on c.id = cl.company_id "
	  + "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	 left join etqa on etqa.id = tpa.etqa_id  "
	  + "	 left join company cEmp on cEmp.id = cl.employer_id "
	  + "	 inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	 left join funding f on f.id = cl.funding "
	  + "	 left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
	  + "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
	  + "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
	  + "	 where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
	  + "	 UNION ALL  "
	  + "	 select cEmp.id "
	  + "	 from company_learners cl  "
	  + "	 inner join users u on u.id = cl.user_id "
	  + "	 inner join skills_set sset on sset.id = cl.skills_set_id "
	  + "	 inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
	  + "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "	 inner join company c on c.id = cl.company_id "
	  + "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	 left join etqa on etqa.id = tpa.etqa_id  "
	  + "	 left join company cEmp on cEmp.id = cl.employer_id "
	  + "	 inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	 left join funding f on f.id = cl.funding "
	  + "	 left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
	  + "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
	  + "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
	  + "	 where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
	  + "	 UNION ALL "
	  + "	 select cEmp.id "
	  + "	 from company_learners cl  "
	  + "	 inner join users u on u.id = cl.user_id "
	  + "	 inner join skills_program spro on spro.id = cl.skills_set_id "
	  + "	 inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
	  + "	 inner join intervention_type it on it.id = cl.intervention_type_id  "
	  + "	 inner join company c on c.id = cl.company_id "
	  + "	 inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	 left join etqa on etqa.id = tpa.etqa_id  "
	  + "	 left join company cEmp on cEmp.id = cl.employer_id "
	  + "	 inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	 left join funding f on f.id = cl.funding "
	  + "	 left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	 left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
	  + "	 inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
	  + "	 inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
	  + "	 where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null "
	  + "	  UNION ALL "
	  + "	  select cEmp.id   "
	  + "	    from company_learners cl  "
	  + "	    inner join users u on u.id = cl.user_id "
	  + "	    inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
	  + "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
	  + "	    inner join company c on c.id = cl.company_id "
	  + "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	    left join etqa on etqa.id = tpa.etqa_id  "
	  + "	    inner join company cEmp on cEmp.id = cl.employer_id "
	  + "	    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	    left join funding f on f.id = cl.funding "
	  + "	    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	    UNION ALL "
	  + "	   select cEmp.id  "
	  + "	    from company_learners cl  "
	  + "	    inner join users u on u.id = cl.user_id "
	  + "	    inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
	  + "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
	  + "	    inner join company c on c.id = cl.company_id "
	  + "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	    left join etqa on etqa.id = tpa.etqa_id  "
	  + "	    inner join company cEmp on cEmp.id = cl.employer_id "
	  + "	    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	    left join funding f on f.id = cl.funding "
	  + "	    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	    inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
	  + "	    inner join company ct on ct.id = cltt.`preferred_training_center_id` "
	  + "	    inner join training_provider_application tpat on tpat.company_id = ct.id  "
	  + "	    left join etqa etqt on etqt.id = ct.etqa_id  "
	  + "	    UNION ALL "
	  + "	    select cEmp.id  "
	  + "	    from company_learners cl  "
	  + "	    inner join users u on u.id = cl.user_id "
	  + "	    inner join learnership lship on lship.id = cl.learnership_id "
	  + "	    inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
	  + "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
	  + "	    inner join company c on c.id = cl.company_id "
	  + "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	    left join etqa on etqa.id = tpa.etqa_id  "
	  + "	    inner join company cEmp on cEmp.id = cl.employer_id "
	  + "	    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	    left join funding f on f.id = cl.funding "
	  + "	    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null  "
	  + "	    UNION ALL "
	  + "	    select cEmp.id  "
	  + "	    from company_learners cl  "
	  + "	    inner join users u on u.id = cl.user_id "
	  + "	    inner join skills_set sset on sset.id = cl.skills_set_id "
	  + "	    inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
	  + "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
	  + "	    inner join company c on c.id = cl.company_id "
	  + "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	    left join etqa on etqa.id = tpa.etqa_id  "
	  + "	    inner join company cEmp on cEmp.id = cl.employer_id "
	  + "	    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	    left join funding f on f.id = cl.funding "
	  + "	    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	    where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null  "
	  + "	    UNION ALL "
	  + "	    select cEmp.id  "
	  + "	    from company_learners cl  "
	  + "	    inner join users u on u.id = cl.user_id "
	  + "	    inner join skills_program spro on spro.id = cl.skills_set_id "
	  + "	    inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
	  + "	    inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4 "
	  + "	    inner join company c on c.id = cl.company_id "
	  + "	    inner join training_provider_application tpa on tpa.company_id = c.id  "
	  + "	    left join etqa on etqa.id = tpa.etqa_id  "
	  + "	    inner join company cEmp on cEmp.id = cl.employer_id "
	  + "	    inner join employment_type et on et.employment_status = u.employment_status  "
	  + "	    left join funding f on f.id = cl.funding "
	  + "	    left join ofo on ofo.id = cl.ofo_codes_id  "
	  + "	    left join urbal_rural ur on ur.id = u.urban_rural_enum  "
	  + "	    where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null ";

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
	 * SETMIS FILES All
	 */

	@SuppressWarnings("unchecked")
	public List<SetmisFile100> allSetmisFile100() throws Exception {
		return (List<SetmisFile100>) super.getList("select o from SetmisFile100 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile200> allSetmisFile200() throws Exception {
		return (List<SetmisFile200>) super.getList("select o from SetmisFile200 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile304> allSetmisFile304() throws Exception {
		return (List<SetmisFile304>) super.getList("select o from SetmisFile304 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile400> allSetmisFile400() throws Exception {
		return (List<SetmisFile400>) super.getList("select o from SetmisFile400 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile401> allSetmisFile401() throws Exception {
		return (List<SetmisFile401>) super.getList("select o from SetmisFile401 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile500> allSetmisFile500() throws Exception {
		return (List<SetmisFile500>) super.getList("select o from SetmisFile500 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile501> allSetmisFile501() throws Exception {
		return (List<SetmisFile501>) super.getList("select o from SetmisFile501 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile502> allSetmisFile502() throws Exception {
		return (List<SetmisFile502>) super.getList("select o from SetmisFile502 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile503> allSetmisFile503() throws Exception {
		return (List<SetmisFile503>) super.getList("select o from SetmisFile503 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile505> allSetmisFile505() throws Exception {
		return (List<SetmisFile505>) super.getList("select o from SetmisFile505 o");
	}

	@SuppressWarnings("unchecked")
	public List<SetmisFile506> allSetmisFile506() throws Exception {
		return (List<SetmisFile506>) super.getList("select o from SetmisFile506 o");
	}
												
												
	/**
	 * SETMIS FILES Extracts
	 */
	public List<SETMISFile100Bean> extractSETMISFile100Bean() throws Exception {
		String sql = "select CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode,  "
				+ " e.setmis_code as providerETQAId,  "
				+ " sc.code as sICCode,  "
				+ " c.company_name as providerName,  "
				+ " pt.nlrd_code as providerTypeId,  "
				+ " ap.address_line_1 as providerPostalAddress1,  "
				+ " ap.address_line_2 as providerPostalAddress2,  "
				+ " ap.address_line_3 as providerPostalAddress3,  "
				+ " ap.postcode as providerPostalAddressCode,  "
				+ " c.tel_number as providerPhoneNumber,  "
				+ " c.fax_number as providerFaxNumber,  "
				+ " c.company_registration_number as providerSarsNumber,  "
				+ " '' as providerContactName,  "
				+ " '' as providerContactEmailAddress,  "
				+ " '' as providerContactPhoneNumber,  "
				+ " '' as providerContactCellNumber,  "
				+ " tpa.`accreditation_number` as providerAccreditationNum,  "
				+ " tpa.start_date as providerStartDate,  "
				+ " IF(ps.setmis_code = '513',NULL ,tpa.expiriy_date) as providerEndDate,  "
				+ "  f.etqeDecisionNumber as etqeDecisionNumber,  "
				+ " pc.setmis_code as providerClassId,  "
				+ " ps.setmis_code as providerStatusId,  "
				+ " p.stats_prov_id	as provinceCode,  "
				+ " 'ZA' as countryCode,   "
				+ " ap.latitude_degrees	as latitudeDegree,   "
				+ " ap.latitude_minutes as latitudeMinutes,  "
				+ " ap.latitude_seconds as latitudeSeconds,   "
				+ " ap.longitude_degrees as longitudeDegree,   "
				+ " ap.longitude_minutes as longitudeMinutes,   "
				+ " ap.longitude_seconds as longitudeSeconds,  "
				+ " a.address_line_1 as providerPhysicalAddress1,   "
				+ " a.address_line_2 as providerPhysicalAddress2,   "
				+ " a.address_line_3 as providerPhysicalAddress3,  "
				+ " a.postcode as providerPhysicalAddressCode,   "
				+ " '' as providerWebAddress, "
				+ " c.levy_number as sDLNo,   "
				+ " CURDATE() as dateStamp  "
				+ " from training_provider_application tpa  "
				+ " inner join company c on c.id = tpa.company_id  "
				+ " left join sic_code sc on sc.id = c.sic_code_id  "
				+ " inner join etqa e on tpa.etqa_id = e.id  "
				+ " left join provider_type pt on pt.id = tpa.provider_type  "
				+ " inner join address a on a.id = c.residential_address_id  "
				+ " inner join address ap on ap.id = c.postal_address_id  "
				+ " inner join municipality m on m.id = ap.municipality_id  "
				+ " inner join province p on p.id = m.provinces_idprovinces  "
				+ " left join provider_status ps on ps.id = tpa.provider_status_id "
				+ " left join provider_class pc on pc.id = tpa.provider_class "
				+ " left join ( select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id "
				+ " where c.id in (" + whereLearners + ")";
		return (List<SETMISFile100Bean>) super.nativeSelectSqlList(sql, SETMISFile100Bean.class);
	}
	
	/**
	 * SETMIS file 100 Extract Version Two
	 * 20 December 2019
	 * @author Jonathan Bowett
	 * @return List<SETMISFile100BeanVersionTwo>
	 * @throws Exception
	 */
	public List<SETMISFile100BeanVersionTwo> extractSETMISFile100BeanVersionTwo() throws Exception {
		String sql = "select     " + 
				"    providerCode,       " + 
				"    providerETQAId,     " + 
				"    sICCode,       " + 
				"    providerName,       " + 
				"    providerTypeId,      " + 
				"    providerPostalAddress1,       " + 
				"    providerPostalAddress2,       " + 
				"    providerPostalAddress3,       " + 
				"    providerPostalAddressCode,       " + 
				"    providerPhoneNumber,       " + 
				"    providerFaxNumber,       " + 
				"    providerSarsNumber,       " + 
				"    providerContactName,       " + 
				"    providerContactEmailAddress,       " + 
				"    providerContactPhoneNumber,       " + 
				"    providerContactCellNumber,       " + 
				"    providerAccreditationNum,     " + 
				"    providerStartDate,     " + 
				"    providerEndDate,     " + 
				"    etqeDecisionNumber,       " + 
				"    providerClassId,       " + 
				"    providerStatusId,     " + 
				"    provinceCode,       " + 
				"    countryCode,        " + 
				"    latitudeDegree,      " + 
				"    latitudeMinutes,     " + 
				"    latitudeSeconds,     " + 
				"    longitudeDegree,     " + 
				"    longitudeMinutes,     " + 
				"    longitudeSeconds,     " + 
				"    providerPhysicalAddress1,        " + 
				"    providerPhysicalAddress2,        " + 
				"    providerPhysicalAddress3,       " + 
				"    providerPhysicalAddressCode,        " + 
				"    providerWebAddress,      " + 
				"    sDLNo,     " + 
				"    dateStamp     " + 
				"from     " + 
				"    (     " + 
				"    select     " + 
				"        tpa.id as provider_id,     " + 
				"        SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode,      " + 
				"        case     " + 
				"			when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"			when e.code is not null and e.code  <> '' then SUBSTRING(e.code , 1, 10)     " + 
				"			ELSE SUBSTRING('599', 1, 10)      " + 
				"        end as providerETQAId,     " + 
				"        SUBSTRING(sc.code, 1, 10) as sICCode,       " + 
				"        SUBSTRING(c.company_name, 1, 70) as providerName,       " + 
				"        tpa.provider_type,     " + 
				"        case     " + 
				"            when  pt.setmis_code is not null and pt.setmis_code <> '' then SUBSTRING(pt.setmis_code, 1, 10)     " + 
				"            ELSE SUBSTRING('500', 1, 10)     " + 
				"            end as providerTypeId,      " + 
				"        SUBSTRING(ap.address_line_1, 1, 50) as providerPostalAddress1,       " + 
				"        SUBSTRING(ap.address_line_2, 1, 50) as providerPostalAddress2,       " + 
				"        SUBSTRING(ap.address_line_3, 1, 50) as providerPostalAddress3,       " + 
				"        SUBSTRING(ap.postcode, 1, 4) as providerPostalAddressCode,       " + 
				"        SUBSTRING(c.tel_number, 1, 10) as providerPhoneNumber,       " + 
				"        SUBSTRING(c.fax_number, 1, 20) as providerFaxNumber,       " + 
				"        SUBSTRING(c.company_registration_number, 1, 20) as providerSarsNumber,       " + 
				"        SUBSTRING('', 1, 50) as providerContactName,       " + 
				"        SUBSTRING('', 1, 50) as providerContactEmailAddress,       " + 
				"        SUBSTRING('', 1, 20) as providerContactPhoneNumber,       " + 
				"        SUBSTRING('', 1, 20) as providerContactCellNumber,       " + 
				"        case     " + 
				"            when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)     " + 
				"            when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)     " + 
				"            else ''     " + 
				"            end as providerAccreditationNum,     " + 
				"        tpa.start_date as providerStartDate,     " + 
				"        IF(ps.setmis_code = '513', NULL , NULL) as providerEndDate,     " + 
				"        SUBSTRING(f.etqeDecisionNumber, 1, 20) as etqeDecisionNumber,       " + 
				"        SUBSTRING(pc.setmis_code, 1, 10) as providerClassId,       " + 
				"        case     " + 
				"            when tpa.approval_status = 34 then SUBSTRING('512', 1, 20)     " + 
				"            when tpa.accreditation_application_type in (0,2,3) and tpa.approval_status = 0 then SUBSTRING('510', 1, 10)     " + 
				"            when tpa.accreditation_application_type in (4,5,6) and tpa.approval_status = 0 then SUBSTRING('501', 1, 10)     " + 
				"            when tpa.accreditation_application_type in (1) and tpa.approval_status = 0 then SUBSTRING('511', 1, 10)     " + 
				"            ELSE SUBSTRING('515', 1, 10)     " + 
				"            end as providerStatusId,     " + 
				"             " + 
				"        SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode,       " + 
				"        SUBSTRING('ZA', 1, 4) as countryCode,        " + 
				"     " + 
				"        SUBSTRING(FORMAT(a.latitude_degrees, 0), 1, 3) as latitudeDegree,      " + 
				"        RIGHT(CONCAT('0', FORMAT(a.latitude_minutes,0)), 2) as latitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.latitude_seconds, 3)),6) as latitudeSeconds,     " + 
				"     " + 
				"        SUBSTRING(FORMAT(a.longitude_degrees, 0), 1, 2) as longitudeDegree,     " + 
				"        RIGHT(CONCAT('0', FORMAT(a.longitude_minutes,0)), 2) as longitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.longitude_seconds, 3)),6) as longitudeSeconds,     " + 
				"     " + 
				"        SUBSTRING(a.address_line_1, 1, 50) as providerPhysicalAddress1,        " + 
				"        SUBSTRING(a.address_line_2, 1, 50) as providerPhysicalAddress2,        " + 
				"        SUBSTRING(a.address_line_3, 1, 50) as providerPhysicalAddress3,       " + 
				"        SUBSTRING(a.postcode, 1, 4) as providerPhysicalAddressCode,        " + 
				"        SUBSTRING(c.company_website, 1, 50) as providerWebAddress,      " + 
				"        SUBSTRING(c.levy_number, 1, 10) as sDLNo,     " + 
				"        CURDATE() as dateStamp     " + 
				"    from training_provider_application tpa       " + 
				"    inner join company c on c.id = tpa.company_id       " + 
				"    left join sic_code sc on sc.id = c.sic_code_id       " + 
				"    left join etqa e on tpa.etqa_id = e.id     " + 
				"    left join etqa eCompany on c.etqa_id = eCompany.id       " + 
				"    left join provider_type pt on pt.id = tpa.provider_type       " + 
				"    inner join address a on a.id = c.residential_address_id       " + 
				"    inner join address ap on ap.id = c.postal_address_id       " + 
				"    inner join municipality m on m.id = ap.municipality_id       " + 
				"    inner join province p on p.id = m.provinces_idprovinces       " + 
				"    left join provider_status ps on ps.id = tpa.provider_status_id      " + 
				"    left join provider_class pc on pc.id = tpa.provider_class      " + 
				"    left join (select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id      " + 
				"    where      " + 
				"        tpa.approval_status in (0,34)     " + 
				"        and tpa.accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        and tpa.duplicate_application = false     " + 
				"        and tpa.company_id in (     " + 
				"             " + 
				"        	select appId from     " + 
				"        	     " + 
				"        	(     " + 
				"        	     " + 
				"        		select      " + 
				"        			cl.company_id as appId     " + 
				"        		from      " + 
				"        			company_learners cl     " + 
				"        		where     " + 
				"        			cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"					and cl.intervention_type_id is not null      " + 
				"					and cl.company_id is not null      " + 
				"        		group by cl.company_id     " + 
				"        			     " + 
				"        		union all     " + 
				"        		     " + 
				"        		select     " + 
				"        			cl.company_id as appId     " + 
				"        		from      " + 
				"        			company_learners cl     " + 
				"        		where     " + 
				"        		    cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"					and cl.intervention_type_id is not null      " + 
				"					and cl.company_id is not null     " + 
				"        			and cl.test_centre_training_provider_application_id is not null     " + 
				"        		group by cl.company_id     " + 
				"        		     " + 
				"        	     " + 
				"        	) base     " + 
				"        	group by appId     " + 
				"        )     " + 
				") base     " + 
				"    inner join (     " + 
				"        select      " + 
				"            max(tpa.id) as provider_id     " + 
				"            , tpa.company_id     " + 
				"        from      " + 
				"            training_provider_application tpa     " + 
				"        where      " + 
				"            tpa.company_id is not null     " + 
				"            and tpa.approval_status in (0,34)     " + 
				"            and tpa.accreditation_application_type in (0,1,3,4,5,6)     " + 
				"            and tpa.duplicate_application = false     " + 
				"        group by tpa.company_id     " + 
				") filt on filt.provider_id = base.provider_id     " + 
				"     " + 
				"UNION ALL     " + 
				"     " + 
				"select     " + 
				"        SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode,      " + 
				"        case     " + 
				"			when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"			ELSE SUBSTRING('599', 1, 10)      " + 
				"        end as providerETQAId,     " + 
				"        SUBSTRING(sc.code, 1, 10) as sICCode,       " + 
				"        case   " + 
				"        	when c.levy_number in ('L230711980', 'L170710323', 'L080770524', 'L780726594', 'L600718649', 'L810714826') then SUBSTRING(c.trading_name, 1, 70)  " + 
				"			Else SUBSTRING(c.company_name, 1, 70)  " + 
				"			end as providerName,  " + 
				"        SUBSTRING('4', 1, 10) as providerTypeId,      " + 
				"        SUBSTRING(ap.address_line_1, 1, 50) as providerPostalAddress1,       " + 
				"        SUBSTRING(ap.address_line_2, 1, 50) as providerPostalAddress2,       " + 
				"        SUBSTRING(ap.address_line_3, 1, 50) as providerPostalAddress3,       " + 
				"        SUBSTRING(ap.postcode, 1, 4) as providerPostalAddressCode,       " + 
				"        SUBSTRING(c.tel_number, 1, 10) as providerPhoneNumber,       " + 
				"        SUBSTRING(c.fax_number, 1, 20) as providerFaxNumber,       " + 
				"        SUBSTRING(c.company_registration_number, 1, 20) as providerSarsNumber,       " + 
				"             " + 
				"        SUBSTRING('', 1, 50) as providerContactName,       " + 
				"        SUBSTRING('', 1, 50) as providerContactEmailAddress,       " + 
				"        SUBSTRING('', 1, 20) as providerContactPhoneNumber,       " + 
				"        SUBSTRING('', 1, 20) as providerContactCellNumber,       " + 
				"             " + 
				"        SUBSTRING('', 1, 20) as providerAccreditationNum,     " + 
				"        c.approval_date as providerStartDate,     " + 
				"        NULL as providerEndDate,     " + 
				"        SUBSTRING('', 1, 20) as etqeDecisionNumber,       " + 
				"        SUBSTRING('5', 1, 10) as providerClassId,       " + 
				"        SUBSTRING('1', 1, 10) as providerStatusId,     " + 
				"             " + 
				"        SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode,       " + 
				"        SUBSTRING('ZA', 1, 4) as countryCode,        " + 
				"     " + 
				"        SUBSTRING(FORMAT(a.latitude_degrees, 0), 1, 3) as latitudeDegree,      " + 
				"        RIGHT(CONCAT('0', FORMAT(a.latitude_minutes,0)), 2) as latitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.latitude_seconds, 3)),6) as latitudeSeconds,     " + 
				"     " + 
				"        SUBSTRING(FORMAT(a.longitude_degrees, 0), 1, 2) as longitudeDegree,     " + 
				"        RIGHT(CONCAT('0', FORMAT(a.longitude_minutes,0)), 2) as longitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.longitude_seconds, 3)),6) as longitudeSeconds,     " + 
				"     " + 
				"        SUBSTRING(a.address_line_1, 1, 50) as providerPhysicalAddress1,        " + 
				"        SUBSTRING(a.address_line_2, 1, 50) as providerPhysicalAddress2,        " + 
				"        SUBSTRING(a.address_line_3, 1, 50) as providerPhysicalAddress3,       " + 
				"        SUBSTRING(a.postcode, 1, 4) as providerPhysicalAddressCode,        " + 
				"        SUBSTRING(c.company_website, 1, 50) as providerWebAddress,      " + 
				"        SUBSTRING(c.levy_number, 1, 10) as sDLNo,     " + 
				"        CURDATE() as dateStamp     " + 
				"from      " + 
				"	company c          " + 
				"left join sic_code sc on sc.id = c.sic_code_id       " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id        " + 
				"inner join address a on a.id = c.residential_address_id       " + 
				"inner join address ap on ap.id = c.postal_address_id       " + 
				"inner join municipality m on m.id = ap.municipality_id       " + 
				"inner join province p on p.id = m.provinces_idprovinces       " + 
				"     " + 
				"where c.id in (     " + 
				"select      " + 
				"	company_id      " + 
				"from company_learners      " + 
				"where company_id = employer_id     " + 
				"and learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"and intervention_type_id is not null      " + 
				"and company_id is not null     " + 
				"and company_id not in (     " + 
				"	select      " + 
				"		company_id      " + 
				"	from      " + 
				"		training_provider_application      " + 
				"	where         " + 
				"		company_id is not null     " + 
				"        and approval_status in ( 0, 34 )     " + 
				"        and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        and duplicate_application = false     " + 
				"    group by company_id     " + 
				"	)	     " + 
				"group by company_id     " + 
				")  " + 
				"";
		return (List<SETMISFile100BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile100BeanVersionTwo.class);
	}
	
	public List<SETMISFile100BeanVersionTwo> extractSETMISFile100BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile100BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile100BeanVersionTwo.class);
	}
	

	public List<SETMISFile200Bean> extractSETMISFile200Bean() throws Exception {
			String sql = "select c.levy_number as sDLNo "
					+ " , c.company_site_number as siteNo "
					+ " , c.seta_id as sETAId "
					+ " , sc.code as sICCode "
					+ " , c.company_registration_number as employerRegistrationNumber "
					+ " , c.company_name as employerCompanyName "
					+ " , c.trading_name as employerTradingName "
					+ " , a.address_line_1 as providerPostalAddress1 "
					+ " , a.address_line_2 as providerPostalAddress2 "
					+ " , a.address_line_3 as providerPostalAddress3 "
					+ " , a.postcode as providerPostalAddressCode "
					+ " , ap.address_line_1 as providerPhysicalAddress1 "
					+ " , ap.address_line_2 as providerPhysicalAddress2 "
					+ " , ap.address_line_3 as providerPhysicalAddress3 "
					+ " , ap.postcode as providerPhysicalAddressCode "
					+ " , c.tel_number as providerPhoneNumber "
					+ " , c.fax_number as providerFaxNumber "
					+ " , u.first_name as providerContactName "
					+ " , u.email as providerContactEmailAddress "
					+ " , u.tel_number as providerContactPhoneNumber "
					+ " , u.cell_number as providerContactCellNumber "
					+ " , IF( c.approval_enum = 0, 2, 1) as employerApprovalStatusId "
					+ " , c.approval_date as  employerApprovalStatusStartDate "
					+ " , '' as employerApprovalStatusEndDate "
					+ " , CONCAT(IF( c.approval_enum = 0, 2, 1) , LPAD(c.id, 8, '0')) as employerApprovalStatusNum "
					+ " , p.stats_prov_id as provinceCode "
					+ " , 'ZA' as countryCode "
					+ " , ap.latitude_degrees as latitudeDegree "
					+ " , ap.latitude_minutes as latitudeMinutes "
					+ " , ap.latitude_seconds as latitudeSeconds "
					+ " , ap.longitude_degrees as longitudeDegree "
					+ " , ap.longitude_minutes as longitudeMinutes "
					+ " , ap.longitude_seconds as longitudeSeconds "
					+ " ,  c.levy_number as mainSDLNo "
					+ " , '' as filler01 "
					+ " , '' as filler02 "
					+ " , CURDATE() as dateStamp "
					+ " from company c "
					+ " inner join sdf_company sdf on sdf.company_id = c.id "
					+ " inner join sic_code sc on sc.id = c.sic_code_id "
					+ " left join address a on a.id = c.postal_address_id "
					+ " left join address ap on a.id = c.residential_address_id "
					+ " inner join users u on u.id = sdf.sdf_id "
					+ " left join municipality m on m.id = a.municipality_id "
					+ " left join province p on p.id = m.provinces_idprovinces "
					+ " where c.levy_number is not NULL and sdf_type_id = 1 and c.id in (" + whereLearnersEmployers + ")";
			return (List<SETMISFile200Bean>) super.nativeSelectSqlList(sql, SETMISFile200Bean.class);
		}
	
	public List<SETMISFile200BeanVersionTwo> extractSETMISFile200BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"	SUBSTRING(c.levy_number, 1, 10) as sDLNo  " + 
				"	, SUBSTRING(c.company_site_number, 1, 10) as siteNo  " + 
				"	, case  " + 
				"		when e.setmis_code is not null and e.setmis_code <> '' then SUBSTRING(e.setmis_code, 1, 10)  " + 
				"		ELSE SUBSTRING('17', 1, 3)  " + 
				"		end as sETAId  " + 
				"	, SUBSTRING(sc.code, 1, 10) as sICCode  " + 
				"	, SUBSTRING(c.company_registration_number, 1, 20) as employerRegistrationNumber  " + 
				"	, SUBSTRING(c.company_name, 1, 70) as employerCompanyName  " + 
				"	, SUBSTRING(c.trading_name, 1, 70) as employerTradingName  " + 
				"	, SUBSTRING(a.address_line_1, 1, 50) as providerPostalAddress1  " + 
				"	, SUBSTRING(a.address_line_2, 1, 50) as providerPostalAddress2  " + 
				"	, SUBSTRING(a.address_line_3, 1, 50) as providerPostalAddress3  " + 
				"	, SUBSTRING(a.postcode, 1, 4) as providerPostalAddressCode  " + 
				"	, SUBSTRING(ap.address_line_1, 1, 50) as providerPhysicalAddress1  " + 
				"	, SUBSTRING(ap.address_line_2, 1, 50) as providerPhysicalAddress2  " + 
				"	, SUBSTRING(ap.address_line_3, 1, 50) as providerPhysicalAddress3  " + 
				"	, SUBSTRING(ap.postcode, 1, 4) as providerPhysicalAddressCode  " + 
				"	, SUBSTRING(c.tel_number, 1, 20) as providerPhoneNumber  " + 
				"	, SUBSTRING(c.fax_number, 1, 20) as providerFaxNumber  " + 
				"	  " + 
				"	, case  " + 
				"		when u.id is not null then SUBSTRING(u.first_name, 1, 50)  " + 
				"		when uContact.id is not null then SUBSTRING(uContact.first_name, 1, 50)  " + 
				"	  	end as providerContactName  " + 
				"	, case  " + 
				"		when u.id is not null then SUBSTRING(u.email, 1, 50)  " + 
				"		when uContact.id is not null then SUBSTRING(uContact.email, 1, 50)  " + 
				"	  	end as providerContactEmailAddress  " + 
				"	, case  " + 
				"		when u.id is not null then SUBSTRING(u.tel_number, 1, 20)  " + 
				"		when uContact.id is not null then SUBSTRING(uContact.tel_number, 1, 20)  " + 
				"	 	end as providerContactPhoneNumber  " + 
				"	, case  " + 
				"		when u.id is not null then  SUBSTRING(u.cell_number, 1, 20)  " + 
				"		when uContact.id is not null then  SUBSTRING(uContact.cell_number, 1, 20)  " + 
				"	 	end as providerContactCellNumber  " + 
				"	 	  " + 
				"	, case  " + 
				"		when c.company_status in (1,4,5,6,8) then SUBSTRING('2', 1, 3)  " + 
				"		when c.company_status in (0,2,3,7) and c.approval_date is not null then SUBSTRING('3', 1, 3)  " + 
				"		ELSE SUBSTRING('1', 1, 3)  " + 
				"		end as employerApprovalStatusId  " + 
				"		  " + 
				"	, c.approval_date as employerApprovalStatusStartDate  " + 
				"	  " + 
				"	, case  " + 
				"		when c.company_status in (1,4,5,6,8) then (SELECT STR_TO_DATE('01,6,2022','%d,%m,%Y'))  " + 
				"		Else null  " + 
				"		end as employerApprovalStatusEndDate  " + 
				"		  " + 
				"	, CONCAT(IF( c.approval_enum = 0, 2, 1), LPAD(c.id, 8, '0')) as employerApprovalStatusNum  " + 
				"	, SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode  " + 
				"	, SUBSTRING('ZA', 1, 4) as countryCode  " + 
				"	  " + 
				"	, SUBSTRING(FORMAT(ap.latitude_degrees, 0), 1, 3) as latitudeDegree  " + 
				"	, RIGHT(CONCAT('0', FORMAT(ap.latitude_minutes,0)), 2) as latitudeMinutes  " + 
				"	, RIGHT(CONCAT('00', FORMAT(ap.latitude_seconds, 3)),6) as latitudeSeconds  " + 
				"	  " + 
				"	, SUBSTRING(FORMAT(ap.longitude_degrees, 0), 1, 2) as longitudeDegree  " + 
				"	, RIGHT(CONCAT('0', FORMAT(ap.longitude_minutes,0)), 2) as longitudeMinutes  " + 
				"	, RIGHT(CONCAT('00', FORMAT(ap.longitude_seconds, 3)),6) as longitudeSeconds  " + 
				"	  " + 
				"	, SUBSTRING(c.levy_number, 1, 10) as mainSDLNo  " + 
				"	, SUBSTRING('', 1, 20) as filler01  " + 
				"	, SUBSTRING('', 1, 4) as filler02  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company c  " + 
				"inner join (  " + 
				"	select ch.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_hist ch   " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	group by ch.id  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"inner join (  " + 
				"	select   " + 
				"		cu.company_id as id  " + 
				"		, MAX(user_id) as contact_user_id  " + 
				"	from company_users cu   " + 
				"	group by cu.company_id  " + 
				"	) lastestContactPerson on lastestContactPerson.id = c.id   " + 
				"left join etqa e on c.etqa_id = e.id  " + 
				"left join sdf_company sdf on sdf.company_id = c.id and sdf.sdf_type_id = 1  " + 
				"left join sic_code sc on sc.id = c.sic_code_id  " + 
				"left join address a on a.id = c.postal_address_id  " + 
				"left join address ap on ap.id = c.residential_address_id  " + 
				"left join users u on u.id = sdf.sdf_id  " + 
				"left join users uContact on uContact.id = lastestContactPerson.contact_user_id  " + 
				"left join municipality m on m.id = a.municipality_id  " + 
				"left join province p on p.id = m.provinces_idprovinces  " + 
				"where   " + 
				"	c.levy_number is not NULL   " + 
				"	and c.id in (  " + 
				"		select   " + 
				"	employer_id   " + 
				"from (  " + 
				"	select   " + 
				"		employer_id_500 as employer_id  " + 
				"	from (  " + 
				"		select  " + 
				"			cl.employer_id as employer_id_500  " + 
				"		from   " + 
				"    		company_learners cl  " + 
				"    	left join learnership lship on lship.id = cl.learnership_id  " + 
				"		where      " + 
				"    		cl.learnership_id is not null     " + 
				"    		and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"			and cl.intervention_type_id is not null      " + 
				"			and cl.company_id is not null  " + 
				"			and lship.setmis_code <> '8'  " + 
				"	) as FiveZeroZeroFile  " + 
				"  " + 
				"	UNION ALL  " + 
				"	select   " + 
				"		employer_id_501 as employer_id  " + 
				"	from (  " + 
				"		select  " + 
				"			cl.employer_id as employer_id_501  " + 
				"		from   " + 
				"    		company_learners cl  " + 
				"    	left join learnership l on l.id = cl.learnership_id and l.setmis_code <> '8'  " + 
				"		where  " + 
				"			cl.skills_set_id is null  " + 
				"			and cl.skills_program_id is null  " + 
				"			and (cl.qualification_id is not null or (cl.learnership_id is not null and l.setmis_code <> '8'))  " + 
				"    		and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"			and cl.intervention_type_id is not null   " + 
				"			and cl.company_id is not null  " + 
				"	) as FiveZeroOneFile  " + 
				"	  " + 
				"  " + 
				"	UNION ALL  " + 
				"	  " + 
				"	select   " + 
				"		employer_id_502 as employer_id  " + 
				"	from (  " + 
				"		select   " + 
				"			cl.employer_id as employer_id_502  " + 
				"		from   " + 
				"			company_learners cl  " + 
				"		left join skills_set sset on sset.id = cl.skills_set_id  " + 
				"		where   " + 
				"			cl.skills_set_id is not null   " + 
				"			and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"			and cl.intervention_type_id is not null   " + 
				"			and cl.company_id is not null  " + 
				"			and sset.qualification_id is not null  " + 
				"		UNION ALL  " + 
				"		select   " + 
				"			cl.employer_id as employer_id_502  " + 
				"		from   " + 
				"			company_learners cl  " + 
				"		left join skills_program spro on spro.id = cl.skills_program_id  " + 
				"		where   " + 
				"			cl.skills_program_id is not null   " + 
				"			and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"			and cl.intervention_type_id is not null   " + 
				"			and cl.company_id is not null  " + 
				"			and spro.qualification_id is not null  " + 
				"		) as FiveZeroTwoFile  " + 
				"					  " + 
				"		UNION ALL  " + 
				"	  " + 
				"		select   " + 
				"			employer_id_503 as employer_id  " + 
				"		from (  " + 
				"		  " + 
				"			select   " + 
				"				cl.employer_id as employer_id_503  " + 
				"			from   " + 
				"				company_learners cl   " + 
				"			left join skills_program spro on spro.id = cl.skills_program_id  " + 
				"			where   " + 
				"				cl.unit_standard_id is not null  " + 
				"    			and cl.qualification_id is null  " + 
				"    			and cl.learnership_id is null  " + 
				"				and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"				and cl.intervention_type_id is not null   " + 
				"				and cl.company_id is not null  " + 
				"			  " + 
				"		) as FiveZeroThreeFile	  " + 
				"  " + 
				"  " + 
				"		) allEmployerId  " + 
				"	group by employer_id  " + 
				"	);  " + 
				"";
		return (List<SETMISFile200BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile200BeanVersionTwo.class);
	}
	
	public List<SETMISFile200BeanVersionTwo> extractSETMISFile200BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile200BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile200BeanVersionTwo.class);
	}
		
		
		public List<SETMISFile304Bean> extractSETMISFile304Bean() throws Exception {
				String sql = " select spro.programme_id as nonNQFIntervCode "
						+ " , spro.description as nonNQFIntervName "
						+ " , '' as filler01 "
						+ " , sbf.setmis_code as subfieldId "
						+ " , '' as filler02 "
						+ " , sq.qualregistrationstartdate as nonNQFIntervRegStartDate "
						+ " , sq.qualregistrationenddate as nonNQFIntervRegEndDate "
						+ " ,'' as filler03 "
						+ " , e.setmis_code as nonNQFIntervETQEId "
						+ " , '1' as nonNQFIntervStatusId "
						+ " , spro.credits as nonNQFIntervCredit "
						+ " , '1' as learningProgrammeTypeId "
						+ " ,CURDATE() as dateStamp "
						+ " from skills_program spro "
						+ " inner join saqa_qualification sq on sq.id = spro.qualification_id "
						+ " left join subfield sbf on sbf.id = sq.sub_field_id "
						+ " inner join etqa e on e.id = spro.netqa_id "
						+ " where spro.id in (" + whereNQFspro + ")"
						+ " UNION ALL "
						+ " select sset.programme_id as NonNQFIntervCode "
						+ " , sset.description as NonNQFIntervName "
						+ " , '' as Filler01 "
						+ " , sbf.setmis_code as SubfieldId "
						+ " , '' as Filler02 "
						+ " , sq.qualregistrationstartdate as NonNQFIntervRegStartDate "
						+ " , sq.qualregistrationenddate as NonNQFIntervRegEndDate "
						+ " ,'' as Filler03 "
						+ " , e.setmis_code as NonNQFIntervETQEId "
						+ " , '1' as NonNQFIntervStatusId "
						+ " , sset.credits as NonNQFIntervCredit "
						+ " , '1' as LearningProgrammeTypeId "
						+ " ,CURDATE() as DateStamp "
						+ " from skills_set sset "
						+ " inner join saqa_qualification sq on sq.id = sset.qualification_id "
						+ " left join subfield sbf on sbf.id = sq.sub_field_id "
						+ " inner join etqa e on e.id = sset.netqa_id "
						+ " where sset.id in (" + whereNQFsset + ")";
				return (List<SETMISFile304Bean>) super.nativeSelectSqlList(sql, SETMISFile304Bean.class);
			}
		
		public List<SETMISFile304Bean> extractSETMISFile304BeanVersionTwo() throws Exception {
			String sql = "select   " + 
					"	SUBSTRING(spro.programme_id , 1, 20) as nonNQFIntervCode   " + 
					"	, SUBSTRING(spro.description, 1, 200) as nonNQFIntervName   " + 
					"	, SUBSTRING('', 1, 8) as filler01   " + 
					"	  " + 
					"	, SUBSTRING(sbf.setmis_code, 1, 8) as subfieldId   " + 
					"	, SUBSTRING('', 1, 8) as filler02   " + 
					"	  " + 
					"	, DATE(sq.qualregistrationstartdate) as nonNQFIntervRegStartDate   " + 
					"	, IF(DATE(sq.qualregistrationenddate) > CURDATE() , null , sq.qualregistrationenddate) as nonNQFIntervRegEndDate   " + 
					"	, SUBSTRING('', 1, 20) as filler03   " + 
					"	  " + 
					"	, SUBSTRING(e.code, 1, 10) as nonNQFIntervETQEId   " + 
					"	, IF(DATE(sq.qualregistrationenddate) > CURDATE() , SUBSTRING('1', 1, 10) , SUBSTRING('2', 1, 10)) as nonNQFIntervStatusId   " + 
					"	  " + 
					"	, SUBSTRING(spro.credits, 1, 10) as nonNQFIntervCredit   " + 
					"	, SUBSTRING('8', 1, 10) as learningProgrammeTypeId   " + 
					"	  " + 
					"	, spro.create_date as dateStamp   " + 
					"from   " + 
					"	skills_program spro   " + 
					"left join saqa_qualification sq on sq.id = spro.qualification_id   " + 
					"left join subfield sbf on sbf.id = sq.sub_field_id   " + 
					"left join etqa e on e.id = spro.netqa_id  " + 
					"where   " + 
					"	spro.id in (  " + 
					"		select   " + 
					"			cl.skills_program_id  " + 
					"		from   " + 
					"			company_learners cl  " + 
					"		where  " + 
					"			cl.learner_status in (1,2,4,5,7,8,10,11,12,13)   " + 
					"			and cl.intervention_type_id is not null   " + 
					"			and cl.company_id is not null  " + 
					"	)  " + 
					"	and spro.qualification_id is not null  " + 
					"UNION ALL  " + 
					"select   " + 
					"	SUBSTRING(sset.programme_id , 1, 20) as nonNQFIntervCode   " + 
					"	, SUBSTRING(sset.description, 1, 200) as nonNQFIntervName   " + 
					"	, SUBSTRING('', 1, 8) as filler01   " + 
					"	, SUBSTRING(sbf.setmis_code, 1, 8) as subfieldId   " + 
					"	, SUBSTRING('', 1, 8) as filler02   " + 
					"	, DATE(sq.qualregistrationstartdate) as nonNQFIntervRegStartDate   " + 
					"	, IF(DATE(sq.qualregistrationenddate) > CURDATE() , null , sq.qualregistrationenddate) as nonNQFIntervRegEndDate   " + 
					"	, SUBSTRING('', 1, 20) as filler03   " + 
					"	, SUBSTRING(e.code, 1, 10) as nonNQFIntervETQEId   " + 
					"	, IF(DATE(sq.qualregistrationenddate) > CURDATE() , SUBSTRING('1', 1, 10) , SUBSTRING('2', 1, 10)) as nonNQFIntervStatusId   " + 
					"	, SUBSTRING(sset.credits, 1, 10) as nonNQFIntervCredit   " + 
					"	, SUBSTRING('8', 1, 10) as learningProgrammeTypeId   " + 
					"	, sset.create_date as dateStamp  " + 
					"from   " + 
					"	skills_set sset   " + 
					"left join saqa_qualification sq on sq.id = sset.qualification_id   " + 
					"left join subfield sbf on sbf.id = sq.sub_field_id   " + 
					"left join etqa e on e.id = sset.netqa_id  " + 
					"where   " + 
					"	sset.id in (  " + 
					"		select   " + 
					"			cl.skills_set_id  " + 
					"		from   " + 
					"			company_learners cl  " + 
					"		where  " + 
					"			cl.learner_status in (1,2,4,5,7,8,10,11,12,13)   " + 
					"			and cl.intervention_type_id is not null   " + 
					"			and cl.company_id is not null  " + 
					"	)  " + 
					"	and sset.qualification_id is not null";
			return (List<SETMISFile304Bean>) super.nativeSelectSqlList(sql, SETMISFile304Bean.class);
		}
		
	
		public List<SETMISFile304Bean> extractSETMISFile304BeanVersionTwoSqlPassed(String sql) throws Exception {
			return (List<SETMISFile304Bean>) super.nativeSelectSqlList(sql, SETMISFile304Bean.class);
		}
			
			
			public List<SETMISFile400Bean> extractSETMISFile400Bean() throws Exception {
					String sql = " select u.rsa_id_number as nationalId "
							+ " , u.passport_number as personAlternateId "
							+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
							+ " , e.`setmis_code` as equityCode "
							+ " , n.`setmis_code` as nationalityCode "
							+ " , l.`setmis_code` as homeLanguageCode "
							+ " , g.`setmis_code` as genderCode "
							+ " , IF(n.nlrd_code = 'SA','SA','U') as citizenResidentStatusCode "
							+ " , '' as filler01 "
							+ " , '' as filler02 "
							+ " , u.last_name as personLastName "
							+ " , u.first_name as personFirstName "
							+ " , u.middle_name as personMiddleName "
							+ " , t.description as personTitle "
							+ " , u.date_of_birth as personBirthDate "
							+ " , ap.address_line_1 as personHomeAddress1 "
							+ " , ap.address_line_2 as personHomeAddress2 "
							+ " , ap.address_line_3 as personHomeAddress3 "
							+ " , a.address_line_1 as personPostalAddress1 "
							+ " , a.address_line_2 as personPostalAddress2 "
							+ " , a.address_line_3 as personPostalAddress3 "
							+ " , ap.postcode as personHomeAddrPostalCode "
							+ " , a.postcode as personPostalAddrPostalCode "
							+ " , u.tel_number as personPhoneNumber "
							+ " , u.cell_number as personCellPhoneNumber "
							+ " , u.fax_number as personFaxNumber "
							+ " , u.email as personEmailAddress "
							+ " , p.stats_prov_id as provinceCode "
							+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
							+ " , etqa.setmis_code as providerETQEId "
							+ " , '' as personPreviousLastname "
							+ " , '' as personPreviousAlternateId "
							+ " , '' as personPreviousAlternativeIdType "
							+ " , '' as personPreviousProviderCode "
							+ " , '' as personPreviousProviderETQEId "
							+ " , '6' as seeingRatingId "
							+ " , '6' as hearingRatingId "
							+ " , '6' as walkingRatingId "
							+ " , '6' as rememberingRatingId "
							+ " , '6' as communicationRatingId "
							+ " , '6' as selfcareRatingId "
							+ " ,  psemis.`new_natemis` as lastSchoolEMISNo "
							+ " , f.lastSchoolYear as lastSchoolYear"
							+ " , ssaac.`setmis_code` as sTATSSAAreaCode "
							+ " , '1' as pOPIActStatusID "
							+ " , u.`create_date` as pOPIActStatusDate "
							+ " , CURDATE() as dateStamp "
							+ " from company_learners cl "
							+ " inner join ( "
							+ " select MAX(cl.`last_school_year`) as lastSchoolYear, cl.user_id from company_learners cl group by cl.user_id "
							+ " ) f on f.user_id = cl.user_id and f.lastSchoolYear = cl.`last_school_year` "
							+ " left join company_qualifications cq on cq.company_id = cl.company_id "
							+ " left join company_unit_standard cus on cus.company_id = cl.company_id "
							+ " left join statssa_area_code ssaac on ssaac.id = cl.`stats_saarea_code_id` "
							+ " left join previous_schools psemis on cl.`previous_schools` = psemis.id "
							+ " inner join users u on u.id = cl.user_id "
							+ " left join equity e on e.id = u.equity_id "
							+ " left join nationality n on u.nationality_id = n.id "
							+ " left join language l on u.home_language = l.id "
							+ " left join gender g on u.gender_id = g.id "
							+ " left join address a on a.id = u.postal_address_id "
							+ " left join address ap on ap.id = u.residential_address_id "
							+ " left join disability_status ds on ds.id = u.disabilityStatus "
							+ " left join title t on u.title_id = t.id "
							+ " left join municipality m on m.id = a.municipality_id "
							+ " left join province p on p.id = m.provinces_idprovinces "
							+ " left join training_provider_application tpa on  tpa.users_id = u.id "
				            + " left join company c on c.id = tpa.company_id "
							+ " left join etqa on etqa.id = tpa.etqa_id "
							+ " group by "
							+ "   nationalId "
							+ " , personAlternateId "
							+ " , alternativeIdType "
							+ " , equityCode "
							+ " , nationalityCode "
							+ " , homeLanguageCode "
							+ " , genderCode "
							+ " , citizenResidentStatusCode "
							+ " , personLastName "
							+ " , personFirstName "
							+ " , personMiddleName "
							+ " , personTitle "
							+ " , personBirthDate "
							+ " , personHomeAddress1 "
							+ " , personHomeAddress2 "
							+ " , personHomeAddress3 "
							+ " , personPostalAddress1 "
							+ " , personPostalAddress2 "
							+ " , personPostalAddress3 "
							+ " , personHomeAddrPostalCode "
							+ " , personPostalAddrPostalCode "
							+ " , personPhoneNumber "
							+ " , personCellPhoneNumber "
							+ " , personFaxNumber "
							+ " , personEmailAddress "
							+ " , provinceCode "
							+ " , providerCode "
							+ " , providerETQEId "
							+ " , personPreviousLastname "
							+ " , personPreviousAlternateId "
							+ " , personPreviousAlternativeIdType "
							+ " , personPreviousProviderCode "
							+ " , personPreviousProviderETQEId "
							+ " , seeingRatingId "
							+ " , hearingRatingId "
							+ " , walkingRatingId "
							+ " , rememberingRatingId "
							+ " , communicationRatingId "
							+ " , selfcareRatingId "
							+ " , lastSchoolEMISNo "
							+ " , lastSchoolYear "
							+ " , sTATSSAAreaCode "
							+ " , pOPIActStatusID "
							+ " , pOPIActStatusDate "
							+ " where cl.id in (" + whereLearnersTwo  +")";
					return (List<SETMISFile400Bean>) super.nativeSelectSqlList(sql, SETMISFile400Bean.class) ;
				}
			
			public List<SETMISFile400BeanVersionTwo> extractSETMISFile400BeanVersionTwo() throws Exception {
				String sql = "SELECT  " + 
						"    nationalId  " + 
						"    , personAlternateId  " + 
						"    , alternativeIdType  " + 
						"    , equityCode  " + 
						"    , nationalityCode  " + 
						"    , homeLanguageCode  " + 
						"    , genderCode  " + 
						"    , citizenResidentStatusCode  " + 
						"    , filler01  " + 
						"    , filler02  " + 
						"    , personLastName  " + 
						"    , personFirstName  " + 
						"    , personMiddleName  " + 
						"    , personTitle  " + 
						"    , personBirthDate  " + 
						"    , personHomeAddress1  " + 
						"    , personHomeAddress2  " + 
						"    , personHomeAddress3  " + 
						"    , personPostalAddress1  " + 
						"    , personPostalAddress2  " + 
						"    , personPostalAddress3  " + 
						"    , personHomeAddrPostalCode  " + 
						"    , personPostalAddrPostalCode  " + 
						"    , personPhoneNumber  " + 
						"    , personCellPhoneNumber  " + 
						"    , personFaxNumber  " + 
						"    , personEmailAddress  " + 
						"    , provinceCode  " + 
						"    , providerCode  " + 
						"    , providerETQEId  " + 
						"    , personPreviousLastname  " + 
						"    , personPreviousAlternateId  " + 
						"    , personPreviousAlternativeIdType  " + 
						"    , personPreviousProviderCode  " + 
						"    , personPreviousProviderETQEId  " + 
						"    , seeingRatingId  " + 
						"    , hearingRatingId  " + 
						"    , walkingRatingId  " + 
						"    , rememberingRatingId  " + 
						"    , communicationRatingId  " + 
						"    , selfcareRatingId  " + 
						"    , lastSchoolEMISNo  " + 
						"    , lastSchoolYear  " + 
						"    , sTATSSAAreaCode  " + 
						"    , pOPIActStatusID  " + 
						"    , pOPIActStatusDate  " + 
						"    , dateStamp  " + 
						"FROM (  " + 
						"    select  " + 
						"        cl.id as company_learner_id  " + 
						"        , case   " + 
						"            when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
						"            ELSE ''  " + 
						"        end as nationalId  " + 
						"  " + 
						"        , case   " + 
						"            when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
						"            ELSE ''  " + 
						"        end as personAlternateId  " + 
						"  " + 
						"        , case   " + 
						"            when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
						"            when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
						"            ELSE ''  " + 
						"        end as alternativeIdType  " + 
						"  " + 
						"        , SUBSTRING(e.setmis_code, 1, 10) as equityCode  " + 
						"        , SUBSTRING(n.setmis_code, 1, 3) as nationalityCode  " + 
						"        , case  " + 
						"            when homeLang.setmis_code is not null and homeLang.setmis_code <> '' then SUBSTRING(homeLang.setmis_code, 1, 10)  " + 
						"            Else SUBSTRING('Eng', 1, 10)  " + 
						"            end as homeLanguageCode  " + 
						"        , SUBSTRING(g.setmis_code,1,1) as genderCode  " + 
						"        , IF(n.setmis_code = 'SA', SUBSTRING('SA', 1, 10),SUBSTRING('U', 1, 10)) as citizenResidentStatusCode  " + 
						"          " + 
						"        , SUBSTRING('', 1, 2) as filler01  " + 
						"        , SUBSTRING('', 1, 10) as filler02  " + 
						"          " + 
						"        , SUBSTRING(u.last_name, 1, 45) as personLastName  " + 
						"        , SUBSTRING(u.first_name, 1, 26) as personFirstName  " + 
						"        , SUBSTRING(u.middle_name, 1, 50) as personMiddleName  " + 
						"        , SUBSTRING(t.description, 1, 10) as personTitle  " + 
						"        , u.date_of_birth as personBirthDate  " + 
						"          " + 
						"        , SUBSTRING(ap.address_line_1, 1, 50) as personHomeAddress1  " + 
						"        , SUBSTRING(ap.address_line_2, 1, 50) as personHomeAddress2  " + 
						"        , SUBSTRING(ap.address_line_3, 1, 50) as personHomeAddress3  " + 
						"        , SUBSTRING(a.address_line_1, 1, 50) as personPostalAddress1  " + 
						"        , SUBSTRING(a.address_line_2, 1, 50) as personPostalAddress2  " + 
						"        , SUBSTRING(a.address_line_3, 1, 50) as personPostalAddress3  " + 
						"          " + 
						"        , SUBSTRING(ap.postcode, 1, 4) as personHomeAddrPostalCode  " + 
						"        , SUBSTRING(a.postcode, 1, 4) as personPostalAddrPostalCode  " + 
						"          " + 
						"        , SUBSTRING(u.tel_number, 1, 20) as personPhoneNumber  " + 
						"        , SUBSTRING(u.cell_number, 1, 20) as personCellPhoneNumber  " + 
						"        , SUBSTRING(u.fax_number, 1, 20) as personFaxNumber  " + 
						"        , SUBSTRING(u.email, 1, 50) as personEmailAddress  " + 
						"          " + 
						"        , SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode  " + 
						"        , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode    " + 
						"          " + 
						"        , case  " + 
						"			when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)  " + 
						"			when et.code is not null and et.code  <> '' then SUBSTRING(et.code, 1, 10)  " + 
						"			ELSE SUBSTRING('599', 1, 10)  " + 
						"			end as providerETQEId  " + 
						"			  " + 
						"        , SUBSTRING('', 1, 45) as personPreviousLastname  " + 
						"        , SUBSTRING('', 1, 20) as personPreviousAlternateId  " + 
						"        , SUBSTRING('', 1, 3) as personPreviousAlternativeIdType  " + 
						"        , SUBSTRING('', 1, 20) as personPreviousProviderCode  " + 
						"        , SUBSTRING('', 1, 10) as personPreviousProviderETQEId  " + 
						"  " + 
						"        , SUBSTRING('', 1, 2) as seeingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as hearingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as walkingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as rememberingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as communicationRatingId  " + 
						"        , SUBSTRING('', 1, 2) as selfcareRatingId  " + 
						"        , case   " + 
						"        	when psemis.new_natemis is not null and psemis.new_natemis <> ''and psemis.new_natemis = '600105386' then SUBSTRING('000000002', 1, 20)  " + 
						"        	when (psemis.new_natemis is null or psemis.new_natemis <> '' ) and psemis.nat_emis is not null and psemis.nat_emis <> '' and psemis.nat_emis = '600105386' then SUBSTRING('000000002', 1, 20)  " + 
						"            when psemis.new_natemis is not null and psemis.new_natemis <> '' and psemis.new_natemis <> '99' then SUBSTRING(psemis.new_natemis, 1, 20)  " + 
						"            when psemis.new_natemis is not null and psemis.new_natemis <> '' and psemis.new_natemis = '99' and psemis.nat_emis is not null and psemis.nat_emis <> '' then SUBSTRING(psemis.nat_emis, 1, 20)  " + 
						"            when (psemis.new_natemis is null or psemis.new_natemis <> '' ) and psemis.nat_emis is not null and psemis.nat_emis <> '' then SUBSTRING(psemis.nat_emis, 1, 20)  " + 
						"            Else '000000001'  " + 
						"            end as lastSchoolEMISNo  " + 
						"        , cl.last_school_year as lastSchoolYear  " + 
						"          " + 
						"        , SUBSTRING(ssaac.setmis_code, 1, 20) as sTATSSAAreaCode  " + 
						"        , SUBSTRING('1', 1, 2) as pOPIActStatusID  " + 
						"          " + 
						"        , u.create_date as pOPIActStatusDate  " + 
						"        , CURDATE() as dateStamp  " + 
						"    from company_learners cl  " + 
						"    left join previous_schools psemis on cl.previous_schools = psemis.id  " + 
						"    left join users u on u.id = cl.user_id  " + 
						"    left join equity e on e.id = u.equity_id  " + 
						"    left join nationality n on u.nationality_id = n.id  " + 
						"    left join language l on u.home_language = l.id  " + 
						"    left join gender g on u.gender_id = g.id  " + 
						"    left join address a on a.id = u.postal_address_id  " + 
						"    left join address ap on ap.id = u.residential_address_id  " + 
						"    left join statssa_area_code ssaac on ssaac.id = ap.stats_saarea_code_id  " + 
						"    left join disability_status ds on ds.id = u.disabilityStatus  " + 
						"    left join title t on u.title_id = t.id  " + 
						"    left join municipality m on m.id = a.municipality_id  " + 
						"    left join province p on p.id = m.provinces_idprovinces  " + 
						"    left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
						"    left join etqa et on tpa.etqa_id = et.id   " + 
						"    left join company c on c.id = cl.company_id  " + 
						"    left join etqa eCompany on c.etqa_id = eCompany.id  " + 
						"    left join etqa on etqa.id = tpa.etqa_id  " + 
						"    left join users_language ul on ul.id = (select MIN(id) from users_language where user_id = u.id and home_language = true)  " + 
						"    left join language homeLang on homeLang.id = ul.language_id  " + 
						"    where   " + 
						"		 cl.id in (  " + 
						"	select   " + 
						"		company_learner_id   " + 
						"	from (  " + 
						"		select   " + 
						"			company_learner_id_500 as company_learner_id  " + 
						"		from (  " + 
						"			select  " + 
						"				cl.id as company_learner_id_500  " + 
						"			from   " + 
						"    			company_learners cl  " + 
						"    		left join learnership lship on lship.id = cl.learnership_id  " + 
						"			where      " + 
						"    			cl.learnership_id is not null     " + 
						"    			and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
						"				and cl.intervention_type_id is not null      " + 
						"				and cl.company_id is not null  " + 
						"				and lship.setmis_code <> '8'  " + 
						"		) as FiveZeroZeroFile  " + 
						"		UNION ALL  " + 
						"		select   " + 
						"			company_learner_id_501 as company_learner_id  " + 
						"		from (  " + 
						"			select  " + 
						"				cl.id as company_learner_id_501  " + 
						"			from   " + 
						"    			company_learners cl  " + 
						"    		left join learnership l on l.id = cl.learnership_id and l.setmis_code <> '8'  " + 
						"			where  " + 
						"				cl.skills_set_id is null  " + 
						"				and cl.skills_program_id is null  " + 
						"				and (cl.qualification_id is not null or (cl.learnership_id is not null and l.setmis_code <> '8'))  " + 
						"    			and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
						"				and cl.intervention_type_id is not null   " + 
						"				and cl.company_id is not null  " + 
						"		) as FiveZeroOneFile  " + 
						"		UNION ALL  " + 
						"		select   " + 
						"			company_learner_id_502 as company_learner_id  " + 
						"			from (  " + 
						"					select   " + 
						"						cl.id as company_learner_id_502  " + 
						"					from   " + 
						"						company_learners cl  " + 
						"					left join skills_set sset on sset.id = cl.skills_set_id  " + 
						"					where   " + 
						"						cl.skills_set_id is not null   " + 
						"						and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
						"						and cl.intervention_type_id is not null   " + 
						"						and cl.company_id is not null  " + 
						"						and sset.qualification_id is not null  " + 
						"					UNION ALL  " + 
						"					select   " + 
						"						cl.id as company_learner_id_502  " + 
						"					from   " + 
						"						company_learners cl   " + 
						"					left join skills_program spro on spro.id = cl.skills_program_id  " + 
						"					where   " + 
						"						cl.skills_program_id is not null   " + 
						"						and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
						"						and cl.intervention_type_id is not null   " + 
						"						and cl.company_id is not null  " + 
						"						and spro.qualification_id is not null  " + 
						"					) as FiveZeroTwoFile  " + 
						"					  " + 
						"					UNION ALL   " + 
						"					select   " + 
						"						company_learner_id_503 as company_learner_id  " + 
						"					from (  " + 
						"						select   " + 
						"							cl.id as company_learner_id_503  " + 
						"						from   " + 
						"							company_learners cl   " + 
						"						where   " + 
						"							cl.unit_standard_id is not null  " + 
						"    						and cl.qualification_id is null  " + 
						"    						and cl.learnership_id is null  " + 
						"							and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
						"							and cl.intervention_type_id is not null   " + 
						"							and cl.company_id is not null  " + 
						"					) as FiveZeroThreeFile	  " + 
						"					  " + 
						"  " + 
						"				) allCompanyLearnersId  " + 
						"			group by company_learner_id  " + 
						"		)  " + 
						"        and u.rsa_id_number <> '9003055928084'  " + 
						"	) base  " + 
						"inner join (  " + 
						"    select   " + 
						"    	max(cl.id) as company_learner_id  " + 
						"    	, cl.user_id   " + 
						"    from   " + 
						"    	company_learners cl  " + 
						"    where   " + 
						"    	cl.user_id is not null  " + 
						"    	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)   " + 
						"        and cl.intervention_type_id is not null   " + 
						"        and cl.company_id is not null  " + 
						"    group by   " + 
						"    	cl.user_id  " + 
						") filt on filt.company_learner_id = base.company_learner_id  " + 
						"  " + 
						"union all  " + 
						"  " + 
						"select   " + 
						"		case   " + 
						"            when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
						"            ELSE ''  " + 
						"        end as nationalId  " + 
						"        , case   " + 
						"            when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
						"            ELSE ''  " + 
						"        end as personAlternateId  " + 
						"        , case   " + 
						"            when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
						"            when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
						"            ELSE ''  " + 
						"        end as alternativeIdType  " + 
						"        , SUBSTRING(e.setmis_code, 1, 10) as equityCode  " + 
						"        , SUBSTRING(n.setmis_code, 1, 3) as nationalityCode  " + 
						"        , case  " + 
						"            when homeLang.setmis_code is not null and homeLang.setmis_code <> '' then SUBSTRING(homeLang.setmis_code, 1, 10)  " + 
						"            Else SUBSTRING('Eng', 1, 10)  " + 
						"            end as homeLanguageCode  " + 
						"        , SUBSTRING(g.setmis_code,1,1) as genderCode  " + 
						"        , IF(n.setmis_code = 'SA', SUBSTRING('SA', 1, 10),SUBSTRING('U', 1, 10)) as citizenResidentStatusCode  " + 
						"        , SUBSTRING('', 1, 2) as filler01  " + 
						"        , SUBSTRING('', 1, 10) as filler02  " + 
						"        , SUBSTRING(u.last_name, 1, 45) as personLastName  " + 
						"        , SUBSTRING(u.first_name, 1, 26) as personFirstName  " + 
						"        , SUBSTRING(u.middle_name, 1, 50) as personMiddleName  " + 
						"        , SUBSTRING(t.description, 1, 10) as personTitle  " + 
						"        , u.date_of_birth as personBirthDate  " + 
						"        , SUBSTRING(ap.address_line_1, 1, 50) as personHomeAddress1  " + 
						"        , SUBSTRING(ap.address_line_2, 1, 50) as personHomeAddress2  " + 
						"        , SUBSTRING(ap.address_line_3, 1, 50) as personHomeAddress3  " + 
						"        , SUBSTRING(a.address_line_1, 1, 50) as personPostalAddress1  " + 
						"        , SUBSTRING(a.address_line_2, 1, 50) as personPostalAddress2  " + 
						"        , SUBSTRING(a.address_line_3, 1, 50) as personPostalAddress3  " + 
						"        , SUBSTRING(ap.postcode, 1, 4) as personHomeAddrPostalCode  " + 
						"        , SUBSTRING(a.postcode, 1, 4) as personPostalAddrPostalCode  " + 
						"        , SUBSTRING(u.tel_number, 1, 20) as personPhoneNumber  " + 
						"        , SUBSTRING(u.cell_number, 1, 20) as personCellPhoneNumber  " + 
						"        , SUBSTRING(u.fax_number, 1, 20) as personFaxNumber  " + 
						"        , SUBSTRING(u.email, 1, 50) as personEmailAddress  " + 
						"        , SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode  " + 
						"          " + 
						"          " + 
						"        , SUBSTRING('', 1, 20) as providerCode    " + 
						"        , SUBSTRING('', 1, 10) as providerETQEId  " + 
						"          " + 
						"        , SUBSTRING('', 1, 45) as personPreviousLastname  " + 
						"        , SUBSTRING('', 1, 20) as personPreviousAlternateId  " + 
						"        , SUBSTRING('', 1, 3) as personPreviousAlternativeIdType  " + 
						"        , SUBSTRING('', 1, 20) as personPreviousProviderCode  " + 
						"        , SUBSTRING('', 1, 10) as personPreviousProviderETQEId  " + 
						"  " + 
						"        , SUBSTRING('', 1, 2) as seeingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as hearingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as walkingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as rememberingRatingId  " + 
						"        , SUBSTRING('', 1, 2) as communicationRatingId  " + 
						"        , SUBSTRING('', 1, 2) as selfcareRatingId  " + 
						"          " + 
						"        , '000000001' as lastSchoolEMISNo  " + 
						"        , STR_TO_DATE('01,01,2007','%d,%m,%Y') as lastSchoolYear  " + 
						"          " + 
						"        , SUBSTRING('2011-599052001', 1, 20) as sTATSSAAreaCode  " + 
						"        , SUBSTRING('1', 1, 2) as pOPIActStatusID  " + 
						"          " + 
						"        , u.create_date as pOPIActStatusDate  " + 
						"        , CURDATE() as dateStamp  " + 
						"          " + 
						"from   " + 
						"	users u  " + 
						"left join equity e on e.id = u.equity_id  " + 
						"left join nationality n on u.nationality_id = n.id  " + 
						"left join language l on u.home_language = l.id  " + 
						"left join gender g on u.gender_id = g.id  " + 
						"left join address a on a.id = u.postal_address_id  " + 
						"left join address ap on ap.id = u.residential_address_id  " + 
						"left join statssa_area_code ssaac on ssaac.id = ap.stats_saarea_code_id  " + 
						"left join disability_status ds on ds.id = u.disabilityStatus  " + 
						"left join title t on u.title_id = t.id  " + 
						"left join municipality m on m.id = a.municipality_id  " + 
						"left join province p on p.id = m.provinces_idprovinces  " + 
						"left join users_language ul on ul.id = (select MIN(id) from users_language where user_id = u.id and home_language = true)  " + 
						"left join language homeLang on homeLang.id = ul.language_id  " + 
						"where   " + 
						"   u.rsa_id_number = '9003055928084'";
				return (List<SETMISFile400BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile400BeanVersionTwo.class) ;
			}
			
			public List<SETMISFile400BeanVersionTwo> extractSETMISFile400BeanVersionTwoSqlPassed(String sql) throws Exception {
				
				return (List<SETMISFile400BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile400BeanVersionTwo.class) ;
			}
				
				
			public List<SETMISFile401Bean> extractSETMISFile401Bean() throws Exception {
					String sql = " select u.rsa_id_number as nationalId,  "
							+ " u.passport_number as personAlternateId,  "
							+ " IF(u.passport_number is not NULL,'533','527') as alternativeIdType, "
							+ " '1' as designationId,  "
							+ " amp.certificate_number as designationRegistrationNumber,  "
							+ " eqt.setmis_code as designationETQEId, "
							+ " amp.start_date as designationStartDate, "
							+ " amp.end_date  as designationEndDate, "
							+ " IF(amp.status = 0, '510' , (IF(amp.status = 1, '515' , (IF(amp.status = 2, '506' , (IF(amp.status = 3, '506' ,(IF(amp.status = 4, '506' , (IF(amp.status = 5, '501' , '501' )) )) )))) )) )  as designationStructureStatusId, "
							+ " rcmg.decision_number  as eTQEDecisionNumber,  "
							+ " CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode, "
							+ " eqt.setmis_code as providerETQEId,  "
							+ " '' as filler01,  "
							+ " '' as filler02, "
							+ " '' as filler03,  "
							+ " CURDATE() as dateStamp "
							+ " from assessor_moderator_application amp "
							+ " inner join users u on amp.users_id = u.id "
							+ " left join company_users cu on cu.user_id = u.id and assessor_mod_type is not NULL "
							+ " left join company c on cu.company_id = c.id "
							+ " left join training_provider_application tpa on c.id = tpa.company_id "
							+ " left join etqa eqt on c.`etqa_id` = eqt.id "
							+ " left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id "
							+ " left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id "
							+ " left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id "
							+ " group by nationalId, personAlternateId, alternativeIdType, designationId, designationRegistrationNumber, designationETQEId, designationStartDate, designationEndDate, designationStructureStatusId, eTQEDecisionNumber, providerCode, providerETQEId "
							+ " where amp.certificate_number in (1)"; 
					return (List<SETMISFile401Bean>) super.nativeSelectSqlList(sql, SETMISFile401Bean.class);
				}
			
			
	public List<SETMISFile401Bean> extractSETMISFile401BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"	case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE ' '  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE ' '  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE ' '  " + 
				"        end as alternativeIdType  " + 
				"    , case   " + 
				"    	when amp.application_type is not null and amp.application_type in (0,4) then SUBSTRING('1', 1, 5)  " + 
				"    	when amp.application_type is not null and amp.application_type in (1,6) then SUBSTRING('0', 1, 5)  " + 
				"    	ELSE ' '  " + 
				"    	end as designationId  " + 
				"	, SUBSTRING(amp.certificate_number, 1, 20) as designationRegistrationNumber  " + 
				"	, case  " + 
				"    	when eqtApp.code is not null and eqtApp.code  <> '' then SUBSTRING(eqtApp.code , 1, 10)  " + 
				"     	ELSE '599'  " + 
				"    	end as designationETQEId  " + 
				"	, amp.start_date as designationStartDate  " + 
				"	, case  " + 
				"		when amp.status in (34) then amp.end_date  " + 
				"		when amp.application_type is not null and amp.application_type in (0,1) and amp.status in (0) then NULL  " + 
				"		when amp.application_type is not null and amp.application_type in (4,6) and amp.status in (0) then NULL		  " + 
				"		Else amp.end_date  " + 
				"		end as designationEndDate  " + 
				"	, case   " + 
				"		when amp.status in (34) then SUBSTRING('503', 1, 10)  " + 
				"		when amp.application_type is not null and amp.application_type in (0,1) and amp.status in (0) then SUBSTRING('501', 1, 10)  " + 
				"		when amp.application_type is not null and amp.application_type in (4,6) and amp.status in (0) then SUBSTRING('505', 1, 10)		  " + 
				"		Else SUBSTRING('506', 1, 10)  " + 
				"		end as designationStructureStatusId  " + 
				"	, Case  " + 
				"		when rcmg.decision_number is null or rcmg.decision_number = '' then SUBSTRING(' ', 1, 3)  " + 
				"		Else SUBSTRING(CONCAT(REPLACE(REPLACE(rcmg.decision_number, 'ETQA', '') , '/', ''), amp.id), 1, 20)  " + 
				"	 	end as eTQEDecisionNumber  " + 
				"	, SUBSTRING(' ', 1, 20) as providerCode    " + 
				"    , SUBSTRING(' ' , 1, 10) as providerETQEId  " + 
				"	, SUBSTRING(' ', 1, 10) as filler01  " + 
				"	, SUBSTRING(' ', 1, 10) as filler02  " + 
				"	, SUBSTRING(' ', 1, 10) as filler03  " + 
				"	, CURDATE() as dateStamp  " + 
				"from   " + 
				"	assessor_moderator_application amp  " + 
				"inner join users u on amp.users_id = u.id  " + 
				"left join company_users cu on cu.user_id = u.id and assessor_mod_type is not NULL  " + 
				"left join company c on cu.company_id = c.id  " + 
				"left join training_provider_application tpa on c.id = tpa.company_id  " + 
				"left join etqa eqt on c.etqa_id = eqt.id  " + 
				"left join etqa eqtApp on amp.etqa_id = eqtApp.id  " + 
				"left join Review_committee_meeting_agenda rcmg on rcmg.id  = amp.review_committee_meeting_agenda_id  " + 
				"left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id  " + 
				"left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id  " + 
				"where   " + 
				"	amp.certificate_number is not null  " + 
				"	and amp.application_type in (0,4,1,6)  " + 
				"	and amp.status in (0,34)  " + 
				"	and u.rsa_id_number = '9003055928084'  " + 
				"group by   " + 
				"	nationalId  " + 
				"	, personAlternateId  " + 
				"	, alternativeIdType  " + 
				"	, designationId  " + 
				"	, designationRegistrationNumber  " + 
				"	, designationETQEId  " + 
				"	, designationStartDate  " + 
				"	, designationEndDate  " + 
				"	, designationStructureStatusId  " + 
				"	, eTQEDecisionNumber  " + 
				"	, providerCode  " + 
				"	, providerETQEId"; 
		return (List<SETMISFile401Bean>) super.nativeSelectSqlList(sql, SETMISFile401Bean.class);
	}
	
	public List<SETMISFile401Bean> extractSETMISFile401BeanVersionTwoSqlPassed(String sql) throws Exception { 
		return (List<SETMISFile401Bean>) super.nativeSelectSqlList(sql, SETMISFile401Bean.class);
	}
					
					
			public List<SETMISFile500Bean> extractSETMISFile500Bean() throws Exception {
					String sql =  "select u.rsa_id_number as nationalId "
							 + " , u.passport_number as personAlternateId "
							 + " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
							 + " , lship.code as learnershipId "
							 + " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
							 + " , '' as assessorRegistrationNumber "
							 + " , IF(cl.certificate_date is NULL, cl.registered_date, cl.certificate_date) as enrolmentStatusDate"
							 + " , cl.approval_date as enrolmentDate "
							 + " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
							 + " , etqa.setmis_code as providerETQEId"
							 + " , '' as assessorETQEId "
							 + " , '' as enrolmentStatusReasonId "
							 + " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
							 + " , '' as certificateNumber "
							 + " , et.setmis_code as economicStatusId"
							 + " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId"
							 + " , '' as cumulativeSpend"
							 + " , ofo.setmis_code as oFOCode "
							 + " , cEmp.levy_number as sDLNo"
							 + " , cEmp.company_site_number as siteNo "
							 + " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
							 + " , CURDATE() as dateStamp "
							 + " from company_learners cl "
							 + " inner join users u on u.id = cl.user_id"
							 + " inner join learnership lship on lship.id = cl.learnership_id"
							 + " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554"
							 + " inner join intervention_type it on it.id = cl.intervention_type_id "
							 + " inner join company c on c.id = cl.company_id"
							 + " inner join training_provider_application tpa on tpa.company_id = c.id "
							 + " left join etqa on etqa.id = tpa.etqa_id "
							 + " inner join company cEmp on cEmp.id = cl.employer_id "
							 + " inner join employment_type et on et.employment_status = u.employment_status "
							 + " left join funding f on f.id = cl.funding"
							 + " left join ofo on ofo.id = cl.ofo_codes_id "
							 + " left join urbal_rural ur on ur.id = u.urban_rural_enum "
							 + " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null and c.id in (" + whereProviders +")"
							 + " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learnershipId ";
					return (List<SETMISFile500Bean>) super.nativeSelectSqlList(sql, SETMISFile500Bean.class);
				}
			
				
	public List<SETMISFile500Bean> extractSETMISFile500BeanVersionTwo() throws Exception {
		String sql =  "select      " + 
				"	case      " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)     " + 
				"       ELSE SUBSTRING('', 1, 15)     " + 
				"       end as nationalId     " + 
				"	, case      " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)     " + 
				"       ELSE SUBSTRING('', 1, 20)     " + 
				"       end as personAlternateId     " + 
				"	, case      " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)     " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)     " + 
				"        ELSE SUBSTRING('', 1, 3)     " + 
				"        end as alternativeIdType     " + 
				"    , SUBSTRING(lship.setmis_code, 1, 10) as learnershipId      " + 
				"    , case      " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)     " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)     " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)     " + 
				"    	Else SUBSTRING('', 1, 3)     " + 
				"    	end as enrolmentStatusId	     " + 
				"    , case      " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)     " + 
				"    	Else ''     " + 
				"    	end as assessorRegistrationNumber     " + 
				"    , case      " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained     " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date     " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date     " + 
				"    	Else null     " + 
				"    	end as enrolmentStatusDate     " + 
				"    , case      " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date     " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date     " + 
				"		Else null     " + 
				"		end as enrolmentDate     " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode     " + 
				"    , case     " + 
				"    	when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)     " + 
				"     	ELSE SUBSTRING('599', 1, 10)     " + 
				"    	end as providerETQEId     " + 
				"    , case      " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)     " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)     " + 
				"    	Else SUBSTRING('', 1, 10)     " + 
				"    	end as assessorETQEId     " + 
				"    , SUBSTRING('', 1, 10) as enrolmentStatusReasonId      " + 
				"    , case      " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained     " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date     " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date     " + 
				"    	Else null     " + 
				"    	end as mostRecentRegistrationDate     " + 
				"    , case     " + 
				"    	when cl.learner_status in (10) then SUBSTRING(cl.certificate_number, 1, 30)     " + 
				"    	Else SUBSTRING('', 1, 30)     " + 
				"    	end as certificateNumber     " + 
				"    , case     " + 
				"        when u.employment_status = '0' then SUBSTRING('1', 1, 10)     " + 
				"        when u.employment_status = '1' then SUBSTRING('2', 1, 10)     " + 
				"        when u.employment_status = '2' then SUBSTRING('2', 1, 10)     " + 
				"        Else '4'     " + 
				"        end as economicStatusId     " + 
				"    , IF(f.setmis_code is NULL, SUBSTRING('4', 1, 10) , SUBSTRING(f.setmis_code, 1, 10)) as fundingId     " + 
				"    , case     " + 
				"    	when f.setmis_code is not null and f.setmis_code = '1' then SUBSTRING(FLOOR(it.basic_grant_amount), 1, 10)     " + 
				"    	Else SUBSTRING('', 1, 10)     " + 
				"    	End as cumulativeSpend     " + 
				"    , case      " + 
				"		when ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)     " + 
				"		when ofo.id is not null then SUBSTRING(ofo.ofo_code, 6, 21)     " + 
				"		when ofoParentU.id is not null then SUBSTRING(ofoParentU.ofo_code, 6, 21)     " + 
				"		when ofoU.id is not null then SUBSTRING(ofoU.ofo_code, 6, 21)     " + 
				"		end as oFOCode     " + 
				"    , SUBSTRING(cEmp.levy_number, 1, 10) as sDLNo     " + 
				"    , SUBSTRING(cEmp.company_site_number, 1, 10) as siteNo      " + 
				"    , IF(ur.setmis_code is NULL, SUBSTRING('1', 1, 10) , SUBSTRING(ur.setmis_code, 1, 10)) as urbanRuralId      " + 
				"    , lastUpdateEntry.linux_timestamp as dateStamp     " + 
				"from      " + 
				"    company_learners cl      " + 
				"inner join (     " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp     " + 
				"	from company_learners_hist clh      " + 
				"	inner join REVINFO rv on rv.REV = clh.REV     " + 
				"	group by clh.id     " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id     " + 
				"left join users u on u.id = cl.user_id     " + 
				"left join learnership lship on lship.id = cl.learnership_id     " + 
				"left join saqa_qualification sq on sq.id = lship.qualification_id      " + 
				"left join intervention_type it on it.id = cl.intervention_type_id      " + 
				"left join company c on c.id = cl.company_id   " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id    " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id     " + 
				"left join etqa on etqa.id = tpa.etqa_id      " + 
				"left join company cEmp on cEmp.id = cl.employer_id      " + 
				"left join funding f on f.id = cl.funding     " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id      " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id      " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id      " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id     " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum      " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id     " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id     " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id     " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id     " + 
				"where      " + 
				"    cl.learnership_id is not null     " + 
				"    and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"	and cl.intervention_type_id is not null      " + 
				"	and cl.company_id is not null  " + 
				"	and lship.setmis_code <> '8'";
		return (List<SETMISFile500Bean>) super.nativeSelectSqlList(sql, SETMISFile500Bean.class);
	}
	
	public List<SETMISFile500Bean> extractSETMISFile500BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile500Bean>) super.nativeSelectSqlList(sql, SETMISFile500Bean.class);
	}
						
						
	public List<SETMISFile501Bean> extractSETMISFile501Bean() throws Exception {
			String sql = "select u.rsa_id_number as nationalId "
					+ " , u.passport_number as personAlternateId "
					+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
					+ " , sq.qualificationid as qualificationId "
					+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
					+ " , '' as assessorRegistrationNumber "
					+ " , '7' as enrolmentTypeId "
					+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
					+ " , cl.approval_date as enrolmentDate "
					+ " , '' as filler01 "
					+ " , '2' as partOfId "
					+ " , '' as learnershipId "
					+ " , CONCAT('MERSETA-' , tpa.accreditation_number) as providerCode "
					+ " , etqa.setmis_code as providerETQEId "
					+ " , '' as assessorETQEId "
					+ " , '' as filler02 "
					+ " , '' as enrolmentStatusReasonId "
					+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
					+ " , '' as certificateNumber "
					+ " , '' as filler03 "
					+ " , '' as filler04 "
					+ " , '' as filler05 "
					+ " , '' as filler06 "
					+ " , et.setmis_code as economicStatusId "
					+ " , '' as filler07 "
					+ " , cEmp.levy_number as sDLNo "
					+ " , '' as filler08 "
					+ " , '' as filler09 "
					+ " , '' as filler10 "
					+ " , '' as filler11 "
					+ " , cEmp.company_site_number as siteNo "
					+ " , '' as practicalProviderCode "
					+ " , '' as practicalProviderETQEId "
					+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
					+ " , '' as cumulativeSpending "
					+ " , ofo.setmis_code as oFOCode "
					+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
					+ " , it.setmis_code as learningProgrammeTypeId "
					+ " , CURDATE() as dateStamp "
					+ " from company_learners cl "
					+ " inner join users u on u.id = cl.user_id"
					+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554"
					+ " inner join intervention_type it on it.id = cl.intervention_type_id "
					+ " inner join company c on c.id = cl.company_id"
					+ " inner join training_provider_application tpa on tpa.company_id = c.id "
					+ " left join etqa on etqa.id = tpa.etqa_id "
					+ " left join company cEmp on cEmp.id = cl.employer_id"
					+ " inner join employment_type et on et.employment_status = u.employment_status "
					+ " left join funding f on f.id = cl.funding"
					+ " left join ofo on ofo.id = cl.ofo_codes_id "
					+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
					+ " where c.id in (" + whereProviders +")"
					+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learningProgrammeTypeId "
					+ " UNION ALL "
					+ " select u.rsa_id_number as nationalId "
					+ " , u.passport_number as personAlternateId "
					+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
					+ " , sq.qualificationid as qualificationId "
					+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
					+ " , '' as assessorRegistrationNumber "
					+ " , '7' as enrolmentTypeId "
					+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
					+ " , cl.approval_date as enrolmentDate "
					+ " , '' as filler01 "
					+ " , '2' as partOfId "
					+ " , '' as learnershipId "
					+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
					+ " , etqa.setmis_code as providerETQEId "
					+ " , '' as assessorETQEId "
					+ " , '' as filler02 "
					+ " , '' as enrolmentStatusReasonId "
					+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
					+ " , '' as certificateNumber "
					+ " , '' as filler03 "
					+ " , '' as filler04 "
					+ " , '' as filler05 "
					+ " , '' as filler06 "
					+ " , et.setmis_code as economicStatusId "
					+ " , '' as filler07 "
					+ " , cEmp.levy_number as sDLNo "
					+ " , '' as filler08 "
					+ " , '' as filler09 "
					+ " , '' as filler10 "
					+ " , '' as filler11 "
					+ " , cEmp.company_site_number as siteNo "
					+ " , '' as practicalProviderCode "
					+ " , '' practicalProviderETQEId "
					+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId"
					+ " , '' as cumulativeSpending "
					+ " , ofo.setmis_code as oFOCode "
					+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
					+ " , it.setmis_code as learningProgrammeTypeId "
					+ " , CURDATE() as dateStamp "
					+ " from company_learners cl "
					+ " inner join users u on u.id = cl.user_id"
					+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554"
					+ " inner join intervention_type it on it.id = cl.intervention_type_id "
					+ " inner join company c on c.id = cl.company_id"
					+ " inner join training_provider_application tpa on tpa.company_id = c.id "
					+ " left join etqa on etqa.id = tpa.etqa_id "
					+ " left join company cEmp on cEmp.id = cl.employer_id"
					+ " inner join employment_type et on et.employment_status = u.employment_status "
					+ " left join funding f on f.id = cl.funding"
					+ " left join ofo on ofo.id = cl.ofo_codes_id "
					+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
					+ " inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id"
					+ " inner join company ct on ct.id = cltt.`preferred_training_center_id`"
					+ " inner join training_provider_application tpat on tpat.company_id = ct.id "
					+ " left join etqa etqt on etqt.id = ct.etqa_id "
					+ " where c.id in (" + whereProviders +")" 
					+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learningProgrammeTypeId "
					+ " UNION ALL"
					+ " select u.rsa_id_number as nationalId "
					+ " , u.passport_number as personAlternateId "
					+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
					+ " , sq.qualificationid as qualificationId "
					+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
					+ " , '' as assessorRegistrationNumber "
					+ " , '7' as enrolmentTypeId "
					+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
					+ " , cl.approval_date as enrolmentDate "
					+ " , '' as filler01 "
					+ " , '3' as partOfId "
					+ " , lship.code as learnershipId "
					+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
					+ " , etqa.setmis_code as providerETQEId "
					+ " , '' as assessorETQEId "
					+ " , '' as filler02 "
					+ " , '' as enrolmentStatusReasonId "
					+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
					+ " , '' as certificateNumber "
					+ " , '' as filler03 "
					+ " , '' as filler04 "
					+ " , '' as filler05 "
					+ " , '' as filler06 "
					+ " , et.setmis_code as economicStatusId "
					+ " , '' as filler07 "
					+ " , cEmp.levy_number as sDLNo "
					+ " , '' as filler08 "
					+ " , '' as filler09 "
					+ " , '' as filler10 "
					+ " , '' as filler11 "
					+ " , cEmp.company_site_number as siteNo "
					+ " , '' as practicalProviderCode "
					+ " , '' as practicalProviderETQEId "
					+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
					+ " , '' as cumulativeSpending "
					+ " , ofo.setmis_code as oFOCode "
					+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
					+ " , it.setmis_code as learningProgrammeTypeId "
					+ " , CURDATE() as dateStamp "
					+ " from company_learners cl "
					+ " inner join users u on u.id = cl.user_id"
					+ " inner join learnership lship on lship.id = cl.learnership_id"
					+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554"
					+ " inner join intervention_type it on it.id = cl.intervention_type_id "
					+ " inner join company c on c.id = cl.company_id"
					+ " inner join training_provider_application tpa on tpa.company_id = c.id "
					+ " left join etqa on etqa.id = tpa.etqa_id "
					+ " left join company cEmp on cEmp.id = cl.employer_id"
					+ " inner join employment_type et on et.employment_status = u.employment_status "
					+ " left join funding f on f.id = cl.funding"
					+ " left join ofo on ofo.id = cl.ofo_codes_id "
					+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
					+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null and c.id in (" + whereProviders +")" 
					+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learningProgrammeTypeId, learnershipId "
					+ " UNION ALL"
					+ " select u.rsa_id_number as nationalId "
					+ " , u.passport_number as personAlternateId "
					+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
					+ " , sq.qualificationid as qualificationId "
					+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
					+ " , '' as assessorRegistrationNumber "
					+ " , '7' as enrolmentTypeId "
					+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
					+ " , cl.approval_date as enrolmentDate "
					+ " , '' as filler01 "
					+ " , '5' as partOfId "
					+ " , '' as learnershipId "
					+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
					+ " , etqa.setmis_code as providerETQEId "
					+ " , '' as assessorETQEId "
					+ " , '' as filler02 "
					+ " , '' as enrolmentStatusReasonId "
					+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
					+ " , '' as certificateNumber "
					+ " , '' as filler03 "
					+ " , '' as filler04 "
					+ " , '' as filler05 "
					+ " , '' as filler06 "
					+ " , et.setmis_code as economicStatusId "
					+ " , '' as filler07 "
					+ " , cEmp.levy_number as sDLNo "
					+ " , '' as filler08 "
					+ " , '' as filler09 "
					+ " , '' as filler10 "
					+ " , '' as filler11 "
					+ " , cEmp.company_site_number as siteNo "
					+ " , '' as practicalProviderCode "
					+ " , '' as practicalProviderETQEId "
					+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
					+ " , '' as cumulativeSpending "
					+ " , ofo.setmis_code as oFOCode "
					+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
					+ " , it.setmis_code as learningProgrammeTypeId "
					+ " , CURDATE() as dateStamp "
					+ " from company_learners cl "
					+ " inner join users u on u.id = cl.user_id"
					+ " inner join skills_set sset on sset.id = cl.skills_set_id"
					+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554"
					+ " inner join intervention_type it on it.id = cl.intervention_type_id "
					+ " inner join company c on c.id = cl.company_id"
					+ " inner join training_provider_application tpa on tpa.company_id = c.id "
					+ " left join etqa on etqa.id = tpa.etqa_id "
					+ " left join company cEmp on cEmp.id = cl.employer_id "
					+ " inner join employment_type et on et.employment_status = u.employment_status "
					+ " left join funding f on f.id = cl.funding"
					+ " left join ofo on ofo.id = cl.ofo_codes_id "
					+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
					+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null and c.id in (" + whereProviders +")"
					+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learningProgrammeTypeId "
					+ " UNION ALL"
					+ " select u.rsa_id_number as nationalId "
					+ " , u.passport_number as personAlternateId "
					+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
					+ " , sq.qualificationid as qualificationId "
					+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
					+ " , '' as assessorRegistrationNumber "
					+ " , '7' as enrolmentTypeId "
					+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
					+ " , cl.approval_date as enrolmentDate "
					+ " , '' as filler01 "
					+ " , '5' as partOfId "
					+ " , '' as learnershipId "
					+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
					+ " , etqa.setmis_code as providerETQEId "
					+ " , '' as assessorETQEId "
					+ " , '' as filler02 "
					+ " , '' as enrolmentStatusReasonId "
					+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
					+ " , '' as certificateNumber "
					+ " , '' as filler03 "
					+ " , '' as filler04 "
					+ " , '' as filler05 "
					+ " , '' as filler06 "
					+ " , et.setmis_code as economicStatusId "
					+ " , '' as filler07 "
					+ " , cEmp.levy_number as sDLNo "
					+ " , '' as filler08 "
					+ " , '' as filler09 "
					+ " , '' as filler10 "
					+ " , '' as filler11 "
					+ " , cEmp.company_site_number as siteNo "
					+ " , '' as practicalProviderCode "
					+ " , '' as practicalProviderETQEId "
					+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
					+ " , '' as cumulativeSpending "
					+ " , ofo.setmis_code as oFOCode "
					+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
					+ " , it.setmis_code as learningProgrammeTypeId "
					+ " , CURDATE() as dateStamp "
					+ " from company_learners cl "
					+ " inner join users u on u.id = cl.user_id"
					+ " inner join skills_program spro on spro.id = cl.skills_set_id"
					+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554"
					+ " inner join intervention_type it on it.id = cl.intervention_type_id "
					+ " inner join company c on c.id = cl.company_id"
					+ " inner join training_provider_application tpa on tpa.company_id = c.id "
					+ " left join etqa on etqa.id = tpa.etqa_id "
					+ " left join company cEmp on cEmp.id = cl.employer_id "
					+ " inner join employment_type et on et.employment_status = u.employment_status "
					+ " left join funding f on f.id = cl.funding "
					+ " left join ofo on ofo.id = cl.ofo_codes_id "
					+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
					+ " where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null and c.id in (" + whereProviders +")"
					+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learningProgrammeTypeId ";
			return (List<SETMISFile501Bean>) super.nativeSelectSqlList(sql, SETMISFile501Bean.class);
		}
	
	public List<SETMISFile501Bean> extractSETMISFile501BeanVersionTwo() throws Exception {
		String sql = "select	  " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"    , case  " + 
				"    	when l.id is not null then SUBSTRING(lsq.qualificationid_string, 1, 10)  " + 
				"    	Else SUBSTRING(sq.qualificationid_string, 1, 10)  " + 
				"    	End as qualificationId    " + 
				"	, case   " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)  " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)  " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)  " + 
				"    	Else SUBSTRING('', 1, 3)  " + 
				"    	end as enrolmentStatusId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"	, '7' as enrolmentTypeId   " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as enrolmentStatusDate  " + 
				"	, case   " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date  " + 
				"		Else null  " + 
				"		end as enrolmentDate  " + 
				"	, SUBSTRING('', 1, 3) as filler01  " + 
				"	, case   " + 
				"		when it.part_of_id_string = '2' then SUBSTRING('1', 1, 2)  " + 
				"		Else SUBSTRING(it.part_of_id_string, 1, 2)  " + 
				"	 	end as partOfId   " + 
				"	, case  " + 
				"    	when l.id is not null then SUBSTRING(l.setmis_code, 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as learnershipId  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerETQEId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorETQEId  " + 
				"	, SUBSTRING('', 1, 20) as filler02   " + 
				"	, SUBSTRING('', 1, 10) as enrolmentStatusReasonId   " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as mostRecentRegistrationDate  " + 
				"    , case  " + 
				"    	when cl.learner_status in (10) then SUBSTRING(cl.certificate_number, 1, 30)  " + 
				"    	Else SUBSTRING('', 1, 30)  " + 
				"    	end as certificateNumber  " + 
				"	, SUBSTRING('', 1, 4) as filler03   " + 
				"	, SUBSTRING('', 1, 10) as filler04   " + 
				"	, SUBSTRING('', 1, 10) as filler05   " + 
				"	, SUBSTRING('', 1, 10) as filler06   " + 
				"	, case  " + 
				"        when cl.employment_status = '0' then SUBSTRING('1', 1, 10)  " + 
				"        when cl.employment_status = '1' then SUBSTRING('2', 1, 10)  " + 
				"        when cl.employment_status = '2' then SUBSTRING('2', 1, 10)  " + 
				"        Else SUBSTRING('4', 1, 10)  " + 
				"        end as economicStatusId  " + 
				"	, SUBSTRING('', 1, 10) as filler07   " + 
				"	, SUBSTRING(cEmp.levy_number, 1, 10) as sDLNo   " + 
				"	, SUBSTRING('', 1, 10) as filler08   " + 
				"	, SUBSTRING('', 1, 1) as filler09   " + 
				"	, SUBSTRING('', 1, 10) as filler10   " + 
				"	, SUBSTRING('', 1, 10) as filler11   " + 
				"	, SUBSTRING(cEmp.company_site_number, 1, 10) as siteNo   " + 
				"	, case  " + 
				"		when cl.qualification_id is not null and sq.id is not null and sq.qualificationtypeid <> '554' then SUBSTRING('', 1, 20)  " + 
				"		when cl.learnership_id is not null and lsq.id is not null and lsq.qualificationtypeid <> '554' then SUBSTRING('', 1, 20)  " + 
				"		when sq.qualificationtypeid ='554' then SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20)   " + 
				"		end as practicalProviderCode  " + 
				"    , case  " + 
				"    	when cl.qualification_id is not null and sq.id is not null and sq.qualificationtypeid <> '554' then SUBSTRING('', 1, 10)  " + 
				"    	when cl.learnership_id is not null and lsq.id is not null and lsq.qualificationtypeid <> '554' then SUBSTRING('', 1, 10)  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as practicalProviderETQEId  " + 
				"	, case  " + 
				"		when l.id is not null then SUBSTRING('5', 1, 10)  " + 
				"		when f.id is not null and f.setmis_code is not null and f.setmis_code <> '' then SUBSTRING(f.setmis_code, 1, 10)   " + 
				"		Else SUBSTRING('4', 1, 10)  " + 
				"		end as fundingId  " + 
				"    , case  " + 
				"    	when f.setmis_code is not null and f.setmis_code = '1' then SUBSTRING(FLOOR(it.basic_grant_amount), 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as cumulativeSpending  " + 
				"    , case   " + 
				"		when l.id is null and ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)  " + 
				"		when l.id is null and ofo.id is not null then SUBSTRING(ofo.ofo_code, 6, 21)  " + 
				"		when l.id is null and ofoParentU.id is not null then SUBSTRING(ofoParentU.ofo_code, 6, 21)  " + 
				"		when l.id is null and ofoU.id is not null then SUBSTRING(ofoU.ofo_code, 6, 21)  " + 
				"		Else SUBSTRING('', 1, 2)  " + 
				"		end as oFOCode  " + 
				"	, IF(ur.setmis_code is NULL, SUBSTRING('1', 1, 15) , SUBSTRING(ur.setmis_code, 1, 15)) as urbanRuralId   " + 
				"	, SUBSTRING(it.setmis_code, 1, 10) as learningProgrammeTypeId   " + 
				"	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"    company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id   " + 
				"left join learnership l on l.id = cl.learnership_id and l.setmis_code <> '8'  " + 
				"left join saqa_qualification lsq on lsq.id = l.qualification_id  " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id   " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"	cl.skills_set_id is null  " + 
				"	and cl.skills_program_id is null  " + 
				"	and (cl.qualification_id is not null or (cl.learnership_id is not null and l.setmis_code <> '8'))  " + 
				"    and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null";
		return (List<SETMISFile501Bean>) super.nativeSelectSqlList(sql, SETMISFile501Bean.class);
	}
	
	public List<SETMISFile501Bean> extractSETMISFile501BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile501Bean>) super.nativeSelectSqlList(sql, SETMISFile501Bean.class);
	}
							
							
	public List<SETMISFile502Bean> extractSETMISFile502Bean() throws Exception {
		String sql = "select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , sset.programme_id as NonNQFIntervCode"
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId"
				+ " , '' as assessorRegistrationNumber"
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate"
				+ " , '5' as partOfId "
				+ " , sq.qualificationid as qualificationId "
				+ " , '' as learnershipId"
				+ " , CONCAT('MERSETA-' , tpa.accreditation_number) as providerCode"
				+ " , etqa.setmis_code as providerETQEId"
				+ " , '' as assessorETQEId "
				+ " , '' as enrolmentStatusReasonId"
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , et.setmis_code as economicStatusId "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , '' as cumulativeSpending"
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , e.setmis_code as nonNQFIntervETQEId"
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp "
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join skills_set sset on sset.id = cl.skills_set_id"
				+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id"
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join subfield sbf on sbf.id = sq.sub_field_id"
				+ " inner join etqa e on e.id = sset.netqa_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null and c.id in (" + whereProviders +")"
				+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learnershipId, sset.programme_id, e.setmis_code "
				+ " UNION ALL"
				+ " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , spro.programme_id as NonNQFIntervCode"
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId"
				+ " , '' as assessorRegistrationNumber"
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate"
				+ " , '5' as partOfId "
				+ " , sq.qualificationid as qualificationId "
				+ " , '' as learnershipId"
				+ " , CONCAT('MERSETA-' , tpa.accreditation_number) as providerCode"
				+ " , etqa.setmis_code as providerETQEId"
				+ " , '' as assessorETQEId "
				+ " , '' as enrolmentStatusReasonId"
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , et.setmis_code as economicStatusId "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , '' as cumulativeSpending"
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , e.setmis_code as nonNQFIntervETQEId"
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp "
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join skills_program spro on spro.id = cl.skills_set_id"
				+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join subfield sbf on sbf.id = sq.sub_field_id"
				+ " inner join etqa e on e.id = spro.netqa_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null and c.id in (" + whereProviders + ")"
				+ " group by nationalId, personAlternateId, alternativeIdType, qualificationId, enrolmentStatusId, enrolmentStatusDate, enrolmentDate, tpa.accreditation_number, providerETQEId, mostRecentRegistrationDate, economicStatusId, sDLNo, siteNo, fundingId, oFOCode, urbanRuralId, learnershipId, e.setmis_code, spro.programme_id "; 
		return (List<SETMISFile502Bean>) super.nativeSelectSqlList(sql, SETMISFile502Bean.class);
	}

	public List<SETMISFile502Bean> extractSETMISFile502BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"    , SUBSTRING(sset.programme_id , 1, 20) as NonNQFIntervCode  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)  " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)  " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)  " + 
				"    	Else SUBSTRING('', 1, 3)  " + 
				"    	end as enrolmentStatusId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"	, '7' as enrolmentTypeId   " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as enrolmentStatusDate  " + 
				"    , case   " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date  " + 
				"		Else null  " + 
				"		end as enrolmentDate  " + 
				"	, SUBSTRING('2', 1, 2) as partOfId   " + 
				"	, SUBSTRING(sq.qualificationid, 1, 10) as qualificationId   " + 
				"	, SUBSTRING('', 1, 10) as learnershipId  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerETQEId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorETQEId  " + 
				"	, SUBSTRING('', 1, 10) as enrolmentStatusReasonId  " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as mostRecentRegistrationDate  " + 
				"    , case  " + 
				"        when u.employment_status = '0' then SUBSTRING('1', 1, 10)  " + 
				"        when u.employment_status = '1' then SUBSTRING('2', 1, 10)  " + 
				"        when u.employment_status = '2' then SUBSTRING('2', 1, 10)  " + 
				"        Else '4'  " + 
				"        end as economicStatusId  " + 
				"    , IF(f.setmis_code is NULL, SUBSTRING('4', 1, 10) , SUBSTRING(f.setmis_code, 1, 10)) as fundingId  " + 
				"    , case  " + 
				"    	when f.setmis_code is not null and f.setmis_code = '1' then SUBSTRING(FLOOR(it.basic_grant_amount), 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as cumulativeSpending  " + 
				"    , case   " + 
				"		when ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)  " + 
				"		when ofo.id is not null then SUBSTRING(ofo.ofo_code, 6, 21)  " + 
				"		when ofoParentU.id is not null then SUBSTRING(ofoParentU.ofo_code, 6, 21)  " + 
				"		when ofoU.id is not null then SUBSTRING(ofoU.ofo_code, 6, 21)  " + 
				"		end as oFOCode  " + 
				"	, SUBSTRING(cEmp.levy_number, 1, 10) as sDLNo  " + 
				"	, SUBSTRING(cEmp.company_site_number, 1, 10) as siteNo  " + 
				"	, SUBSTRING(e.code, 1, 10) as nonNQFIntervETQEId  " + 
				"	, IF(ur.setmis_code is NULL, SUBSTRING('1', 1, 15) , SUBSTRING(ur.setmis_code, 1, 15)) as urbanRuralId   " + 
				"	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"	company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join skills_set sset on sset.id = cl.skills_set_id  " + 
				"left join saqa_qualification sq on sq.id = sset.qualification_id  " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id   " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join subfield sbf on sbf.id = sq.sub_field_id  " + 
				"left join etqa e on e.id = sset.netqa_id  " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"    cl.skills_set_id is not null   " + 
				"	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null  " + 
				"	and sset.qualification_id is not null  " + 
				"UNION ALL  " + 
				"select   " + 
				"	case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"	, SUBSTRING(spro.programme_id , 1, 20) as NonNQFIntervCode  " + 
				"	, case   " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)  " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)  " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)  " + 
				"    	Else SUBSTRING('', 1, 3)  " + 
				"    	end as enrolmentStatusId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"	, '7' as enrolmentTypeId   " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as enrolmentStatusDate  " + 
				"    , case   " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date  " + 
				"		Else null  " + 
				"		end as enrolmentDate  " + 
				"	, SUBSTRING('2', 1, 2) as partOfId   " + 
				"	, SUBSTRING(sq.qualificationid, 1, 10) as qualificationId   " + 
				"	, SUBSTRING('', 1, 10) as learnershipId  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerETQEId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorETQEId  " + 
				"	, SUBSTRING('', 1, 10) as enrolmentStatusReasonId  " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as mostRecentRegistrationDate  " + 
				"    , case  " + 
				"        when u.employment_status = '0' then SUBSTRING('1', 1, 10)  " + 
				"        when u.employment_status = '1' then SUBSTRING('2', 1, 10)  " + 
				"        when u.employment_status = '2' then SUBSTRING('2', 1, 10)  " + 
				"        Else '4'  " + 
				"        end as economicStatusId  " + 
				"    , IF(f.setmis_code is NULL, SUBSTRING('4', 1, 10) , SUBSTRING(f.setmis_code, 1, 10)) as fundingId  " + 
				"    , case  " + 
				"    	when f.setmis_code is not null and f.setmis_code = '1' then SUBSTRING(FLOOR(it.basic_grant_amount), 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as cumulativeSpending  " + 
				"    , case   " + 
				"		when ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)  " + 
				"		when ofo.id is not null then SUBSTRING(ofo.ofo_code, 6, 21)  " + 
				"		when ofoParentU.id is not null then SUBSTRING(ofoParentU.ofo_code, 6, 21)  " + 
				"		when ofoU.id is not null then SUBSTRING(ofoU.ofo_code, 6, 21)  " + 
				"		end as oFOCode  " + 
				"	, SUBSTRING(cEmp.levy_number, 1, 10) as sDLNo  " + 
				"	, SUBSTRING(cEmp.company_site_number, 1, 10) as siteNo  " + 
				"	, SUBSTRING(e.code, 1, 10) as nonNQFIntervETQEId  " + 
				"	, IF(ur.setmis_code is NULL, SUBSTRING('1', 1, 15) , SUBSTRING(ur.setmis_code, 1, 15)) as urbanRuralId   " + 
				"	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"	company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join skills_program spro on spro.id = cl.skills_program_id  " + 
				"left join saqa_qualification sq on sq.id = spro.qualification_id   " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id   " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join subfield sbf on sbf.id = sq.sub_field_id  " + 
				"left join etqa e on e.id = spro.netqa_id  " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"	cl.skills_program_id is not null   " + 
				"	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null  " + 
				"	and spro.qualification_id is not null"; 
		return (List<SETMISFile502Bean>) super.nativeSelectSqlList(sql, SETMISFile502Bean.class);
	}
	
	public List<SETMISFile502Bean> extractSETMISFile502BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile502Bean>) super.nativeSelectSqlList(sql, SETMISFile502Bean.class);
	}
								
	public List<SETMISFile503Bean> extractSETMISFile503Bean() throws Exception {
		String sql = "select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , su.unitstandardid as unitStandardId "
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
				+ " , '' as assessorRegistrationNumber "
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate "
				+ " , '' as filler01 "
				+ " , '2' as partOf "
				+ " , sq.qualificationid as qualificationId "
				+ " , '' as learnershipId "
				+ " , CONCAT('MERSETA-' , tpa.accreditation_number) as providerCode "
				+ " , etqa.setmis_code as providerETQEId "
				+ " , '' as assessorETQEId "
				+ " , '' as filler02 "
				+ " , '' as filler03 "
				+ " , '' as enrolmentStatusReasonId "
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , '' as filler04 "
				+ " , '' as filler05 "
				+ " , '' as filler06 "
				+ " , et.setmis_code as economicStatusId "
				+ " , '' as filler07 "
				+ " , '' as filler08 "
				+ " , '' as filler09 "
				+ " , '' as cumulativeSpend"
				+ " , '' as certificateNumber "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , '' as nonNQFIntervCode  "
				+ " , '' as nonNQFIntervETQEId  "
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp"
				+ "  from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " left join summative_assessment_report sar on sar.company_learners_id = cl.id"
				+ " left join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id"
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id"
				+ " where c.id in (" + whereProviders +") "
				+ " UNION ALL "
				+ " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , su.unitstandardid as unitStandardId "
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
				+ " , '' as assessorRegistrationNumber "
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate "
				+ " , '' as filler01  "
				+ " , '2' as partOf "
				+ " , sq.qualificationid as qualificationId "
				+ " , '' as learnershipId "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId "
				+ " , '' as assessorETQEId "
				+ " , '' as filler02 "
				+ " , '' as filler03 "
				+ " , '' as enrolmentStatusReasonId "
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , '' as filler04 "
				+ " , '' as filler05 "
				+ " , '' as filler06 "
				+ " , et.setmis_code as economicStatusId "
				+ " , '' as filler07 "
				+ " , '' as filler08 "
				+ " , '' as filler09 "
				+ " , '' as cumulativeSpend"
				+ " , '' as certificateNumber "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , '' as nonNQFIntervCode  "
				+ " , '' as nonNQFIntervETQEId  "
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp"
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id"
				+ " inner join company ct on ct.id = cltt.`preferred_training_center_id`"
				+ " inner join training_provider_application tpat on tpat.company_id = ct.id "
				+ " left join etqa etqt on etqt.id = ct.etqa_id "
				+ " left join summative_assessment_report sar on sar.company_learners_id = cl.id"
				+ " left join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id"
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
				+ " where c.id in (" + whereProviders +")"
				+ " UNION ALL "
				+ " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , su.unitstandardid as unitStandardId "
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
				+ " , '' as assessorRegistrationNumber "
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate "
				+ " , '' as filler01  "
				+ " , '3' as partOf "
				+ " , sq.qualificationid as qualificationId "
				+ " , lship.code as learnershipId "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId "
				+ " , '' as assessorETQEId "
				+ " , '' as filler02 "
				+ " , '' as filler03 "
				+ " , '' as enrolmentStatusReasonId "
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , '' as filler04 "
				+ " , '' as filler05 "
				+ " , '' as filler06 "
				+ " , et.setmis_code as economicStatusId "
				+ " , '' as filler07 "
				+ " , '' as filler08 "
				+ " , '' as filler09 "
				+ " , '' as cumulativeSpend"
				+ " , '' as certificateNumber "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , '' as nonNQFIntervCode  "
				+ " , '' as nonNQFIntervETQEId  "
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp"
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join learnership lship on lship.id = cl.learnership_id"
				+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " left join summative_assessment_report sar on sar.company_learners_id = cl.id"
				+ " left join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id"
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null and c.id in (" + whereProviders +")"
				+ " UNION ALL "
				+ " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , su.unitstandardid as unitStandardId "
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
				+ " , '' as assessorRegistrationNumber "
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate "
				+ " , '' as filler01 "
				+ " , '5' as partOf "
				+ " , sq.qualificationid as qualificationId "
				+ " , '' as learnershipId "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId "
				+ " , '' as assessorETQEId "
				+ " , '' as filler02 "
				+ " , '' as filler03 "
				+ " , '' as enrolmentStatusReasonId "
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , '' as filler04 "
				+ " , '' as filler05 "
				+ " , '' as filler06 "
				+ " , et.setmis_code as economicStatusId "
				+ " , '' as filler07 "
				+ " , '' as filler08 "
				+ " , '' as filler09 "
				+ " , '' as cumulativeSpend"
				+ " , '' as certificateNumber "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , '' as nonNQFIntervCode "
				+ " , '' as nonNQFIntervETQEId "
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp"
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join skills_set sset on sset.id = cl.skills_set_id"
				+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " left join summative_assessment_report sar on sar.company_learners_id = cl.id"
				+ " left join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id"
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null and c.id in (" + whereProviders +")"
				+ " UNION ALL"
				+ " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , su.unitstandardid as unitStandardId "
				+ " , IF(cl.certificate_date is NULL, '3', '2' ) as enrolmentStatusId "
				+ " , '' as assessorRegistrationNumber "
				+ " , '7' as enrolmentTypeId "
				+ " , IF(cl.certificate_date is NULL, cl.approval_date, cl.certificate_date) as enrolmentStatusDate "
				+ " , cl.approval_date as enrolmentDate "
				+ " , '' as filler01 "
				+ " , '5' as partOf "
				+ " , sq.qualificationid as qualificationId "
				+ " , '' as learnershipId "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId "
				+ " , '' as assessorETQEId "
				+ " , '' as filler02 "
				+ " , '' as filler03 "
				+ " , '' as enrolmentStatusReasonId "
				+ " , IF(date(cl.certificate_date) is NULL, date(cl.approval_date), date(cl.certificate_date)) as mostRecentRegistrationDate"
				+ " , '' as filler04 "
				+ " , '' as filler05 "
				+ " , '' as filler06 "
				+ " , et.setmis_code as economicStatusId "
				+ " , '' as filler07 "
				+ " , '' as filler08 "
				+ " , '' as filler09 "
				+ " , '' as cumulativeSpend"
				+ " , '' as certificateNumber "
				+ " , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ " , ofo.setmis_code as oFOCode "
				+ " , cEmp.levy_number as sDLNo"
				+ " , cEmp.company_site_number as siteNo"
				+ " , '' as nonNQFIntervCode  "
				+ " , '' as nonNQFIntervETQEId  "
				+ " , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ " , CURDATE() as dateStamp"
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join skills_program spro on spro.id = cl.skills_set_id"
				+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " left join company cEmp on cEmp.id = cl.employer_id"
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  inner join summative_assessment_report sar on sar.company_learners_id = cl.id"
				+ " inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id"
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null and c.id in (" + whereProviders +")";  
		return (List<SETMISFile503Bean>) super.nativeSelectSqlList(sql, SETMISFile503Bean.class);
	}
	
	public List<SETMISFile503BeanVersionTwo> extractSETMISFile503BeanVersionTwo() throws Exception {
		String sql = "select  " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"    , SUBSTRING(su.unitstandardid, 1, 10) as unitStandardId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)  " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)  " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)  " + 
				"    	Else SUBSTRING('', 1, 3)  " + 
				"    	end as enrolmentStatusId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"    , SUBSTRING('7', 1, 3) as enrolmentTypeId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as enrolmentStatusDate  " + 
				"	, case   " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date  " + 
				"		Else null  " + 
				"		end as enrolmentDate  " + 
				"    , SUBSTRING('', 1, 3) as filler01   " + 
				"    , '1' as partOf   " + 
				"    , SUBSTRING('', 1, 10) as qualificationId   " + 
				"    , SUBSTRING('', 1, 10) as learnershipId   " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerETQEId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorETQEId  " + 
				"    , SUBSTRING('', 1, 20) as filler02   " + 
				"    , SUBSTRING('', 1, 20) as filler03   " + 
				"    , SUBSTRING('', 1, 10) as enrolmentStatusReasonId  " + 
				"   	, case   " + 
				"    	when cl.learner_status in (6,10) then cl.date_qualification_obtained  " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then cl.commencement_date  " + 
				"    	Else null  " + 
				"    	end as mostRecentRegistrationDate   " + 
				"    , SUBSTRING('', 1, 10) as filler04   " + 
				"    , SUBSTRING('', 1, 10) as filler05   " + 
				"    , SUBSTRING('', 1, 10) as filler06   " + 
				"    , case  " + 
				"        when cl.employment_status = '0' then SUBSTRING('1', 1, 10)  " + 
				"        when cl.employment_status = '1' then SUBSTRING('2', 1, 10)  " + 
				"        when cl.employment_status = '2' then SUBSTRING('2', 1, 10)  " + 
				"        Else SUBSTRING('4', 1, 10)  " + 
				"        end as economicStatusId  " + 
				"    , SUBSTRING('', 1, 10) as filler07   " + 
				"    , SUBSTRING('', 1, 1) as filler08   " + 
				"    , SUBSTRING('', 1, 10) as filler09   " + 
				"    , case  " + 
				"    	when f.setmis_code is not null and f.setmis_code = '1' then SUBSTRING(FLOOR(it.basic_grant_amount), 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as cumulativeSpend  " + 
				"    , case  " + 
				"    	when cl.learner_status in (10) then SUBSTRING(cl.certificate_number, 1, 30)  " + 
				"    	Else SUBSTRING('', 1, 30)  " + 
				"    	end as certificateNumber   " + 
				"    , case  " + 
				"		when f.id is not null and f.setmis_code is not null and f.setmis_code <> '' then SUBSTRING(f.setmis_code, 1, 10)   " + 
				"		Else SUBSTRING('4', 1, 10)  " + 
				"		end as fundingId  " + 
				"    , case   " + 
				"		when ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)  " + 
				"		when ofo.id is not null then SUBSTRING(ofo.ofo_code, 6, 21)  " + 
				"		when ofoParentU.id is not null then SUBSTRING(ofoParentU.ofo_code, 6, 21)  " + 
				"		when ofoU.id is not null then SUBSTRING(ofoU.ofo_code, 6, 21)  " + 
				"		end as oFOCode  " + 
				"    , SUBSTRING(cEmp.levy_number, 1, 10) as sDLNo   " + 
				"    , SUBSTRING(cEmp.company_site_number, 1, 10) as siteNo   " + 
				"    , SUBSTRING('', 1, 20) as nonNQFIntervCode    " + 
				"    , SUBSTRING('', 1, 10) as nonNQFIntervETQEId    " + 
				"    , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId   " + 
				"   	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"    company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id and sq.qualificationtypeid <> '554'  " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id   " + 
				"left join training_provider_application tpa on tpa.company_id = c.id   " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id   " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join saqa_unitstandard su on su.id = cl.unit_standard_id  " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"    cl.unit_standard_id is not null  " + 
				"    and cl.qualification_id is null  " + 
				"    and cl.learnership_id is null  " + 
				"	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null";  
		return (List<SETMISFile503BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile503BeanVersionTwo.class);
	}
	
	public List<SETMISFile503BeanVersionTwo> extractSETMISFile503BeanVersionTwoSqlPassed(String sql) throws Exception { 
		return (List<SETMISFile503BeanVersionTwo>) super.nativeSelectSqlList(sql, SETMISFile503BeanVersionTwo.class);
	}
									
									
									
	public List<SETMISFile505Bean> extractSETMISFile505Bean() throws Exception {
		String sql = " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,533,527)  as alternativeIdType "
				+ " , sq.qualificationid as qualificationId "
				+ " , cltt.id as tradeTestNumber "
				+ " , IF(cltt.competence_enum is null, 2, IF(cltt.competence_enum = 0, 1, 2)) as tradeTestResultId"
				+ " , CONCAT('MERSETA-' , tpat.accreditation_number) as tradeTestProviderCode"
				+ " , '' as assessorRegistrationNumber"
				+ " , '' as moderatorRegistrationNumber"
				+ " , cltt.date_of_test as tradeTestDate"
				+ " , '' as tradeTestResultReasonId"
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ " , etqa.setmis_code as providerETQEId "
				+ " , etqt.setmis_code as tradeTestProviderETQEId "
				+ " , '' as assessorETQEId "
				+ " , '' as moderatorETQEId "
				+ " , CURDATE() as dateStamp "
				+ " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding"
				+ " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id"
				+ " inner join company ct on ct.id = cltt.`preferred_training_center_id`"
				+ " inner join training_provider_application tpat on tpat.company_id = ct.id "
				+ " left join etqa etqt on etqt.id = ct.etqa_id where c.id in (" + whereProviders +")";
		return (List<SETMISFile505Bean>) super.nativeSelectSqlList(sql, SETMISFile505Bean.class);
	}
	
	public List<SETMISFile505Bean> extractSETMISFile505BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternativeId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as aternativeIdTypeId  " + 
				"    , SUBSTRING(sq.qualificationid, 1, 10) as qualificationId   " + 
				"	, SUBSTRING(cltt.id, 1, 2) as tradeTestNumber  " + 
				"	, IF(cltt.competence_enum is null, SUBSTRING('2', 1, 2), IF(cltt.competence_enum = 0, SUBSTRING('1', 1, 2), SUBSTRING('2', 1, 2))) as tradeTestResultId  " + 
				"	, CONCAT('MERSETA-' , tpat.accreditation_number) as tradeTestProviderCode  " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampMod.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as moderatorRegistrationNumber  " + 
				"	, cltt.date_of_test as tradeTestDate  " + 
				"   	, SUBSTRING('', 1, 10) as tradeTestResultReasonId  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerETQEId  " + 
				"    , case  " + 
				"    	when etqt.code is not null and etqt.code  <> '' then SUBSTRING(etqt.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as tradeTestProviderETQEId  " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorETQEId  " + 
				"	, case   " + 
				"    	when cl.learner_status in (6,10) and eqtaMod.code is not null and eqtaMod.code  <> '' then SUBSTRING(eqtaMod.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaMod.code is null or eqtaMod.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as moderatorETQEId    	  " + 
				"	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"	company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id  " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join training_provider_application tpa on tpa.company_id = c.id   " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join employment_type et on et.employment_status = u.employment_status   " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join company_learners_trade_test cltt on cltt.company_learners_id = cl.id  " + 
				"left join company ct on ct.id = cltt.preferred_training_center_id  " + 
				"left join training_provider_application tpat on tpat.company_id = ct.id   " + 
				"left join etqa etqt on etqt.id = ct.etqa_id   " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"	cl.learner_status in (10)  " + 
				"	and cl.qualification_id is not null  " + 
				"	and cl.skills_program_id is null  " + 
				"	and cl.skills_set_id is null   " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null  " + 
				"    and sq.qualificationtypeid = '554'";
		return (List<SETMISFile505Bean>) super.nativeSelectSqlList(sql, SETMISFile505Bean.class);
	}
	
	public List<SETMISFile505Bean> extractSETMISFile505BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile505Bean>) super.nativeSelectSqlList(sql, SETMISFile505Bean.class);
	}
	
	
										
										
										
	public List<SETMISFile506Bean> extractSETMISFile506Bean() throws Exception {
		String sql = "select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid_string as qualificationId "
				+ "  , cl.certificate_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId "
				+ "  , cl.commencement_date as startDate"
				+ "  , cl.certificate_date as endDate"
				+ "  , cEmp.levy_number as sDLNo "
				+ "  , cEmp.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId"
				+ "  , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ "  , '' as  cumulativeSpend"
				+ "  , ofo.setmis_code as oFOCode"
				+ "  , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ "  , CURDATE() as dateStamp  "
				+ "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id "
				+ "  left join etqa on etqa.id = tpa.etqa_id "
				+ "  inner join company cEmp on cEmp.id = cl.employer_id"
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding"
				+ "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " where c.id in (" + whereProviders +")"
				+ "  UNION ALL"
				+ " select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid_string as qualificationId "
				+ "  , cl.certificate_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId "
				+ "  , cl.commencement_date as startDate"
				+ "  , cl.certificate_date as endDate"
				+ "  , cEmp.levy_number as sDLNo "
				+ "  , cEmp.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId"
				+ "  , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ "  , '' as  cumulativeSpend"
				+ "  , ofo.setmis_code as oFOCode"
				+ "  , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ "  , CURDATE() as dateStamp"
				+ "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id "
				+ "  left join etqa on etqa.id = tpa.etqa_id "
				+ "  inner join company cEmp on cEmp.id = cl.employer_id"
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding"
				+ "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id"
				+ "  inner join company ct on ct.id = cltt.`preferred_training_center_id`"
				+ "  inner join training_provider_application tpat on tpat.company_id = ct.id "
				+ "  left join etqa etqt on etqt.id = ct.etqa_id "
				+ " where c.id in (" + whereProviders +")"
				+ "  UNION ALL"
				+ "  select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid_string as qualificationId "
				+ "  , cl.certificate_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId "
				+ "  , cl.commencement_date as startDate"
				+ "  , cl.certificate_date as endDate"
				+ "  , cEmp.levy_number as sDLNo "
				+ "  , cEmp.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId"
				+ "  , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ "  , '' as  cumulativeSpend"
				+ "  , ofo.setmis_code as oFOCode"
				+ "  , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ "  , CURDATE() as dateStamp"
				+ "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join learnership lship on lship.id = cl.learnership_id"
				+ "  inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id "
				+ "  left join etqa on etqa.id = tpa.etqa_id "
				+ "  inner join company cEmp on cEmp.id = cl.employer_id"
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding"
				+ "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null and c.id in (" + whereProviders +")"
				+ "  UNION ALL"
				+ "  select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid_string as qualificationId "
				+ "  , cl.certificate_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId "
				+ "  , cl.commencement_date as startDate"
				+ "  , cl.certificate_date as endDate"
				+ "  , cEmp.levy_number as sDLNo "
				+ "  , cEmp.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId"
				+ "  , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ "  , '' as  cumulativeSpend"
				+ "  , ofo.setmis_code as oFOCode"
				+ "  , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ "  , CURDATE() as dateStamp"
				+ "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join skills_set sset on sset.id = cl.skills_set_id"
				+ "  inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id "
				+ "  left join etqa on etqa.id = tpa.etqa_id "
				+ "  inner join company cEmp on cEmp.id = cl.employer_id"
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding"
				+ "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null and c.id in (" + whereProviders +")"
				+ "  UNION ALL"
				+ "  select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid_string as qualificationId "
				+ "  , cl.certificate_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId "
				+ "  , cl.commencement_date as startDate"
				+ "  , cl.certificate_date as endDate"
				+ "  , cEmp.levy_number as sDLNo "
				+ "  , cEmp.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId"
				+ "  , IF(f.setmis_code is NULL, 4 , f.setmis_code) as fundingId "
				+ "  , '' as  cumulativeSpend"
				+ "  , ofo.setmis_code as oFOCode"
				+ "  , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId "
				+ "  , CURDATE() as dateStamp "
				+ "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join skills_program spro on spro.id = cl.skills_set_id"
				+ "  inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id "
				+ "  left join etqa on etqa.id = tpa.etqa_id "
				+ "  inner join company cEmp on cEmp.id = cl.employer_id"
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding"
				+ "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null and c.id in (" + whereProviders +")";
		return (List<SETMISFile506Bean>) super.nativeSelectSqlList(sql, SETMISFile506Bean.class);
	}

	public List<SETMISFile506Bean> extractSETMISFile506BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"    , SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId   " + 
				"    , cl.certificate_date as qualificationAchievementDate   " + 
				"    , IF(cl.status = 21,'2','1') as internshipStatusId   " + 
				"    , cl.commencement_date as startDate  " + 
				"    , cl.completion_date as endDate  " + 
				"    , SUBSTRING(cEmp.levy_number, 1, 10) as sDLNo  " + 
				"	, SUBSTRING(cEmp.company_site_number, 1, 10) as siteNo  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerETQEId  " + 
				"    , IF(f.setmis_code is NULL, SUBSTRING('4', 1, 10) , SUBSTRING(f.setmis_code, 1, 10)) as fundingId  " + 
				"    , case  " + 
				"    	when f.setmis_code is not null and f.setmis_code = '1' then SUBSTRING(FLOOR(it.basic_grant_amount), 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as cumulativeSpend  " + 
				"     , case   " + 
				"		when ofoParent.id is null then SUBSTRING(ofo.ofo_code, 6, 21)  " + 
				"		when ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)  " + 
				"		end as oFOCode  " + 
				"    , IF(ur.setmis_code is NULL, '1' , ur.setmis_code) as urbanRuralId   " + 
				"	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"	company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id   " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join training_provider_application tpa on tpa.company_id = c.id   " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum  " + 
				"where  " + 
				"    cl.learner_status in (6,10)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.qualification_id  is not null   " + 
				"	and cl.company_id is not null  " + 
				"    and sq.qualificationtypeid <> '554'  " + 
				"    and (it.setmis_code = '4' or it.setmis_code = '3')";
		return (List<SETMISFile506Bean>) super.nativeSelectSqlList(sql, SETMISFile506Bean.class);
	}
	
	public List<SETMISFile506Bean> extractSETMISFile506BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<SETMISFile506Bean>) super.nativeSelectSqlList(sql, SETMISFile506Bean.class);
	}
	
	public Integer allActiveContractDetailSetmisFile401(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from SetmisFile401 o "
				+ " where o.alternativeIdType = :alternateIdTypeId and "
				+ "  o.personAlternateId = :personAlternateId and "
				+ "  o.nationalId = :nationalId and "
				+ "  o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("alternateIdTypeId", alternateIdTypeId);
		parameters.put("personAlternateId", personAlternateId);
		parameters.put("nationalId", nationalId);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveContractDetailSetmisFile500(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from SetmisFile500 o "
				+ " where o.alternativeIdType = :alternateIdTypeId and "
				+ "  o.personAlternateId = :personAlternateId and "
				+ "  o.nationalId = :nationalId and "
				+ "  o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("alternateIdTypeId", alternateIdTypeId);
		parameters.put("personAlternateId", personAlternateId);
		parameters.put("nationalId", nationalId);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveContractDetailSetmisFile501(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from SetmisFile501 o "
				+ " where o.alternativeIdType = :alternateIdTypeId and "
				+ "  o.personAlternateId = :personAlternateId and "
				+ "  o.nationalId = :nationalId and "
				+ "  o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("alternateIdTypeId", alternateIdTypeId);
		parameters.put("personAlternateId", personAlternateId);
		parameters.put("nationalId", nationalId);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveContractDetailSetmisFile503(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from SetmisFile503 o "
				+ " where o.alternativeIdType = :alternateIdTypeId and "
				+ "  o.personAlternateId = :personAlternateId and "
				+ "  o.nationalId = :nationalId and "
				+ "  o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("alternateIdTypeId", alternateIdTypeId);
		parameters.put("personAlternateId", personAlternateId);
		parameters.put("nationalId", nationalId);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveContractDetailSetmisFile505(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from SetmisFile505 o "
				+ " where o.alternativeIdType = :alternateIdTypeId and "
				+ "  o.personAlternateId = :personAlternateId and "
				+ "  o.nationalId = :nationalId and "
				+ "  o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("alternateIdTypeId", alternateIdTypeId);
		parameters.put("personAlternateId", personAlternateId);
		parameters.put("nationalId", nationalId);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveRegNumberFile401(String assessorRegistrationNumber, String providerETQEId) throws Exception {
		String hql = "select count(o) from SetmisFile401 o "
				+ " where o.designationRegistrationNumber = :assessorRegistrationNumber and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("assessorRegistrationNumber", assessorRegistrationNumber);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveProviderCode(String entityName,String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from "+entityName+" o "
				+ " where o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId ";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("entityName", entityName);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allActiveSdlNumbersFile200(String sDLNo, BigInteger siteNo) throws Exception {
		String hql = "select count(o) from SetmisFile200 o "
				+ " where o.sDLNo = :sDLNo and "
				+ "  o.siteNo = :siteNo ";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("sDLNo", sDLNo);
		parameters.put("siteNo", siteNo);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer allAltIdType(String entityName,String alternativeIdType, String personAlternateId, String nationalId, String providerCode, String providerETQEId) throws Exception {
		String hql = "select count(o) from "+entityName+" o "
				+ " where o.alternateIdTypeId = :alternateIdTypeId and "
				+ "  o.personAlternateId = :personAlternateId and "
				+ "  o.nationalId = :nationalId and "
				+ "  o.providerCode = :providerCode and "
				+ "  o.providerETQEId = :providerETQEId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("entityName", entityName);
		parameters.put("alternativeIdType", alternativeIdType);
		parameters.put("personAlternateId", personAlternateId);
		parameters.put("nationalId", nationalId);
		parameters.put("providerCode", providerCode);
		parameters.put("providerETQEId", providerETQEId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	
	/**
	 * QCTO FILES
	 */

	public List<QCTO01Bean> extractQCTO01Bean(Company company) throws Exception {
		String sql = " SELECT CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as sdpCode " + " , u.rsa_id_number as nationalId "
				+ "	 , u.passport_number as learnerAlternateId                           "
				+ "	 , IF(u.passport_number is not NULL,533,527) as alternativeIdType    "
				+ "	 , e.qcto_code as equityCode                                         "
				+ "	 , n.qcto_code as nationalityCode                                    "
				+ "   , l.qcto_code as homeLanguageCode                                  "
				+ "    , g.qcto_code as genderCode                                       "
				+ "    , IF(n.qcto_code = 'SA','SA','U') as citizenResidentStatusCode    "
				+ "    , et.qcto_code as socioeconomicStatusCode                         "
				+ "    , ds.qcto_code as disabilityStatusCode                            "
				+ "    , IF(ds.qcto_code > 0,01,02) as disabilityRating                  "
				+ "    , IF(n.qcto_code = 'SA','03','01') as immigrantStatus             "
				+ "    , u.last_name as learnerLastName                                  "
				+ "    , u.first_name as learnerFirstName                                "
				+ "    , u.middle_name as middleName                                     "
				+ "    , t.description as title                                          "
				+ "    , u.date_of_birth as birthDate                                    "
				+ "    , a.address_line_1 as learnerHomeAddress1                         "
				+ "    , a.address_line_2 as learnerHomeAddress2                         "
				+ "    , a.address_line_3 as learnerHomeAddress3                         "
				+ "    , ap.address_line_1 as learnerPostalAddress1                      "
				+ "    , ap.address_line_2 as learnerPostalAddress2                      "
				+ "    , ap.address_line_3 as learnerPostalAddress3                      "
				+ "    , ap.postcode as learnerPostalAddressPostCode                     "
				+ "    , a.postcode as learnerHomeAddressPostCode                        "
				+ "    , u.tel_number as learnerPhoneNumber                              "
				+ "    , u.cell_number as learnerCellPhoneNumber                         "
				+ "    , u.fax_number as learnerFaxNumber                                "
				+ "    , u.email as learnerEmailAddress                                  "
				+ "    , p.stats_prov_id provinceCode                                    "
				+ "	 , cl.stats_saarea_code_id as statssaAreaCode                        "
				+ "	 , u.accept_popi as popiActAgree                                     "
				+ "    , u.create_date as popiActDate                                    "
				+ "    , CURDATE() as dateStamp                                          "
				+ " FROM MerSETA.company_learners cl                                                 "
				+ " inner join company c on c.id = cl.company_id                                     "
				+ " inner join users u on  u.id = cl.user_id                                         "
				+ " inner join gender g on g.id = u.gender_id                                        "
				+ " inner join title t on t.id = u.title_id                                          "
				+ " inner join nationality n on n.id = u.nationality_id                               "
				+ " inner join language l on l.id = u.home_language                                  "
				+ " inner join disability_status ds on ds.id = u.disabilityStatus                    "
				+ " inner join equity e on e.id = u.equity_id                                        "
				+ " inner join address a on a.id = u.residential_address_id                          "
				+ " inner join address ap on ap.id = u.postal_address_id                             "
				+ " inner join municipality m on m.id = a.municipality_id                            "
				+ " inner join province p on p.id = m.provinces_idprovinces                          "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " where c.id = :company";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("company", company.getId());
		return (List<QCTO01Bean>) super.nativeSelectSqlList(sql, QCTO01Bean.class, parameters);
	}

	public List<QCTO02Bean> extractQCTO02Bean(Company company) throws Exception {
		String sql = " select 	  clqa.assessment_partner_code as assessmentPartnerCode "
		+ " 		, clqa.enrolled_sdp_code as enrolledSDPCode                                           "
		+ "         , clqa.assessment_centre_code as assessmentCentreCode                                 "
		+ "         , clqa.national_id as nationalId                                                      "
		+ "         , clqa.learner_alternate_id as learnerAlternateID                                     "
		+ "         , clqa.qualification_id as qualificationId                                            "
		+ "         , clqa.learner_readiness_for_eisa_type_id as learnerReadinessForEISATypeId            "
		+ "         , clqa.module_code as moduleCode                                                      "
		+ "         , clqa.module_achievement_status as moduleAchievementStatus                           "
		+ "         , clqa.employment_status as employmentStatus                                          "
		+ "         , clqa.learner_modular_achievement_type_id as learnerModularAchievementTypeID         "
		+ "         , clqa.learner_modular_achievement_date as learnerModularAchievementDate              "
		+ "         , clqa.learner_enrolled_date as learnerEnrolledDate                                   "
		+ "         , clqa.expected_training_certificate_date as expectedTrainingCompletionDate            "
		+ "         , clqa.linked_to_workplace as linkedToWorkplacePointOfEntryQualification              "
		+ "         , clqa.qualification_entry_requirement as qualificationEntryRequirementStatus         "
		+ "         , clqa.flc as FLC                                                                     "
		+ "         , clqa.flc_statement_result_number as fLCSORNumber                                    "
		+ "         , clqa.statement_results_status as sorSTATUS                                          "
		+ "         , clqa.statement_results_issue_date as sorIssueDate                                   "
		+ "         , CURDATE() as dateStamp                                                              "
		+ " FROM MerSETA.company_learners cl                                                              "
		+ " inner join users u on cl.user_id = u.id                                                       "
		+ " inner join qualification_type q on q.id = u.qualification_id                                  "
		+ " inner join company_learners_achievement_status_eisa clase on clase.qualification_id = q.id    "
		+ " inner join cl_qualification_achievement clqa on clqa.qualification_id = q.id                  "
		+ " where cl.company_id = :company                                                        ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("company", company.getId());
		return (List<QCTO02Bean>) super.nativeSelectSqlList(sql, QCTO02Bean.class, parameters);
	}

	public List<QCTO03Bean> extractQCTO03Bean(Company company) throws Exception {
		String sql = "SELECT c.assessment_partner_code as assessmentPartnerCode, "
				+ "  c.assessment_centre_code as assessmentCentreCode,                        "
			    + "  c.national_id as nationalId,                                             "
			    + "  c.learner_alternate_id as learnerAlternateID,                            "
			    + "  c.qualification_id as qualificationId,                                   "
			    + "  c.eisa_component_number as eISAComponentNumber,                          "
			    + "  c.eisa_component_achievement_value as eISAComponentAchievementValue,     "
			    + "  c.date_assessed as dateAssessed,                                         "
			    + "  c.eisa_percentage_obtained as eISAPercentageObtained,                    "
			    + "  CURDATE() as dateStamp                                                   "
			    + "  FROM company_learners_achievement_status_eisa c "
			    + "	left join company_learners cl on cl.qualification_id = c.qualification_id                         "
			    + "  where cl.company_id = :company                                         ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("company", company.getId());
		return (List<QCTO03Bean>) super.nativeSelectSqlList(sql, QCTO03Bean.class, parameters);
	}
	
	
	////Sandra good will
	
	
	public List<SETMISFile506BeanSandra> extractSETMISFile506BeanSandra() throws Exception {
		String sql = "select u.National_Id as nationalId, " 
				+ " '' as personAlternativeId,  "
				+ " '533' as aternativeIdTypeId, "
				+ "  u.Qualification_Id qualificationId, "
				 + " u.Enrolment_Date as qualificationAchievementDate,  "
				+ "  u.Enrolment_Status as internshipStatusId,  "
				+ "  u.Enrolment_Date as startDate, "
				+ "  '' as endDate,  "
				+ "  u.SDL_No as  sDLNo,  "
				+ "  '1' as siteNo,  "
				+ "  u.Provider  as providerCode,  "
				+ "  u.Provider_ETQE as providerETQEId,  "
				+ "  u.Funding_Id as fundingId, "
				+ "  '' as cumulativeSpend,  "
				+ "  u.OFO_Code as oFOCode,  "
				+ "  u.Urban_Rural as  urbanRuralID,  "
				+ "  u.LastUpDate as dateStamp "
				 + " from file506 u";
		return (List<SETMISFile506BeanSandra>) super.nativeSelectSqlList(sql, SETMISFile506BeanSandra.class);
	}

	/**
	 * SETMIS FILES Inserts
	 */

	public int extractSetmisFile100Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_100 (provider_code, " + " provider_etqa_id, " + " sic_code, "
				+ " provider_name, " + " provider_type_id, " + " provider_postal_address_1, "
				+ " provider_postal_address_2, " + " provider_postal_address_3, " + " provider_postal_address_code, "
				+ " provider_phone_number, " + " provider_fax_number, " + " provider_sars_number, "
				+ " provider_contact_name, " + " provider_contact_email_address, " + " provider_contact_phone_number, "
				+ " provider_contact_cell_number, " + " provider_accreditation_num, " + " provider_start_date, "
				+ " provider_end_date, " + " etqe_decision_number, " + " provider_class_id, " + " provider_status_id, "
				+ " province_code, " + " country_code,  " + " latitude_degree,  " + " latitude_minutes, "
				+ " latitude_seconds,  " + " longitude_degree,  " + " longitude_minutes,  " + " longitude_seconds, "
				+ " provider_physical_address_1,  " + " provider_physical_address_2,  "
				+ " provider_physical_address_3, " + " provider_physical_address_code,  " + " provider_web_address,"
				+ " sdl_no,  " + " date_stamp )"
				+ " select CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode,  "
				+ " e.setmis_code as providerETQAId,  " + " sc.code as sICCode,  "
				+ " c.trading_name as providerName,  " + " pt.nlrd_code as providerTypeId,  "
				+ " ap.address_line_1 as providerPostalAddress1,  " + " ap.address_line_2 as providerPostalAddress2,  "
				+ " ap.address_line_3 as providerPostalAddress3,  " + " ap.postcode as providerPostalAddressCode,  "
				+ " c.tel_number as providerPhoneNumber,  " + " c.fax_number as providerFaxNumber,  "
				+ " c.company_registration_number as providerSarsNumber,  " + " '' as providerContactName,  "
				+ " '' as providerContactEmailAddress,  " + " '' as providerContactPhoneNumber,  "
				+ " '' as providerContactCellNumber,  " + " tpa.`accreditation_number` as providerAccreditationNum,  "
				+ " tpa.start_date as providerStartDate,  " + " tpa.expiriy_date as providerEndDate,  "
				+ "  f.etqeDecisionNumber as etqeDecisionNumber,  " + " pc.setmis_code as providerClassId,  "
				+ " ps.setmis_code as providerStatusId,  " + " p.stats_prov_id	as provinceCode,  "
				+ " 'ZA' as countryCode,   " + " ap.latitude_degrees	as latitudeDegree,   "
				+ " ap.latitude_minutes as latitudeMinutes,  " + " ap.latitude_seconds as latitudeSeconds,   "
				+ " ap.longitude_degrees as longitudeDegree,   " + " ap.longitude_minutes as longitudeMinutes,   "
				+ " ap.longitude_seconds as longitudeSeconds,  " + " a.address_line_1 as providerPhysicalAddress1,   "
				+ " a.address_line_2 as providerPhysicalAddress2,   "
				+ " a.address_line_3 as providerPhysicalAddress3,  " + " a.postcode as providerPhysicalAddressCode,   "
				+ " '' as providerWebAddress, " + " c.levy_number as sDLNo,   " + " CURDATE() as dateStamp  "
				+ " from training_provider_application tpa  " + " inner join company c on c.id = tpa.company_id  "
				+ " left join sic_code sc on sc.id = c.sic_code_id  " + " inner join etqa e on tpa.etqa_id = e.id  "
				+ " left join provider_type pt on pt.id = tpa.provider_type  "
				+ " inner join address a on a.id = c.residential_address_id  "
				+ " inner join address ap on ap.id = c.postal_address_id  "
				+ " inner join municipality m on m.id = ap.municipality_id  "
				+ " inner join province p on p.id = m.provinces_idprovinces  "
				+ " left join provider_status ps on ps.id = tpa.provider_status_id "
				+ " left join provider_class pc on pc.id = tpa.provider_class "
				+ " inner join ( select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id ";

		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile200Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_200 (" + "	sdl_no,                                 "
				+ "	site_no,                                " + "	seta_id,                                "
				+ "	sic_code,                               " + "	employer_registration_number,           "
				+ "	employer_company_name,                  " + "	employer_trading_name,                  "
				+ "	employer_postal_address_1,              " + "	employer_postal_address_2,              "
				+ "	employer_postal_address_3,              " + "	employer_postal_address_code,           "
				+ "	employer_physical_address_1,            " + "	employer_physical_address_2,            "
				+ "	employer_physical_address_3,            " + "	employer_physical_address_code,         "
				+ "	employer_phone_number,                  " + "	employer_fax_number,                    "
				+ "	employer_contact_name,                  " + "	employer_contact_email_address,         "
				+ "	employer_contact_phone_number,          " + "	employer_contact_cell_number,           "
				+ "	employer_approval_status_id,            " + "	employer_approval_status_start_date,    "
				+ "	employer_approval_status_end_date,      " + "	employer_approval_status_num,           "
				+ "	province_code,                          " + "	country_code,                           "
				+ "	latitude_degree,                        " + "	latitude_minutes,                       "
				+ "	latitude_seconds,                       " + "	longitude_degree,                       "
				+ "	longitude_minutes,                      " + "	longitude_seconds,                      "
				+ "	main_sdl_no,                            " + "	filler_01,                              "
				+ "	filler_02,                              " + "	date_stamp       )                       "
				+ "select c.levy_number as sDLNo " + " , 1 as siteNo " + " , c.seta_id as sETAId "
				+ " , sc.code as sICCode " + " , c.company_registration_number as employerRegistrationNumber "
				+ " , c.company_name as employerCompanyName " + " , c.trading_name as employerTradingName "
				+ " , a.address_line_1 as providerPostalAddress1 " + " , a.address_line_2 as providerPostalAddress2 "
				+ " , a.address_line_3 as providerPostalAddress3 " + " , a.postcode as providerPostalAddressCode "
				+ " , ap.address_line_1 as providerPhysicalAddress1 "
				+ " , ap.address_line_2 as providerPhysicalAddress2 "
				+ " , ap.address_line_3 as providerPhysicalAddress3 " + " , ap.postcode as providerPhysicalAddressCode "
				+ " , c.tel_number as providerPhoneNumber " + " , c.fax_number as providerFaxNumber "
				+ " , u.first_name as providerContactName " + " , u.email as providerContactEmailAddress "
				+ " , u.tel_number as providerContactPhoneNumber " + " , u.cell_number as providerContactCellNumber "
				+ " , IF( c.approval_enum = 0, 2, 1) as employerApprovalStatusId "
				+ " , c.approval_date as  employerApprovalStatusStartDate " + " , '' as employerApprovalStatusEndDate "
				+ " , CONCAT(IF( c.approval_enum = 0, 2, 1) , LPAD(c.id, 8, '0')) as employerApprovalStatusNum "
				+ " , p.stats_prov_id as provinceCode " + " , 'ZA' as countryCode "
				+ " , ap.latitude_degrees as latitudeDegree " + " , ap.latitude_minutes as latitudeMinutes "
				+ " , ap.latitude_seconds as latitudeSeconds " + " , ap.longitude_degrees as longitudeDegree "
				+ " , ap.longitude_minutes as longitudeMinutes " + " , ap.longitude_seconds as longitudeSeconds "
				+ " ,  c.levy_number as mainSDLNo " + " , '' as filler01 " + " , '' as filler02 "
				+ " , CURDATE() as dateStamp " + " from company c "
				+ " inner join sdf_company sdf on sdf.company_id = c.id "
				+ " inner join sic_code sc on sc.id = c.sic_code_id "
				+ " left join address a on a.id = c.postal_address_id "
				+ " left join address ap on a.id = c.residential_address_id "
				+ " inner join users u on u.id = sdf.sdf_id " + " left join municipality m on m.id = a.municipality_id "
				+ " left join province p on p.id = m.provinces_idprovinces "
				+ " where c.levy_number is not NULL and sdf_type_id = 1 ";

		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile304Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_304 ( non_nqf_interv_code, " + " non_nqf_interv_name, " + " filler_01, "
				+ " sub_field_id, " + " filler_02, " + " non_nqf_interv_reg_start_date, "
				+ " non_nqf_interv_reg_end_date, " + " filler_03, " + " non_nqf_interv_etqe_id, "
				+ " non_nqf_interv_status_id, " + " non_nqf_interv_credit, " + " learning_programme_type_id, "
				+ " date_stamp ) " + " select spro.programme_id as nonNQFIntervCode  "
				+ " , spro.description as nonNQFIntervName  " + " , '' as filler01  "
				+ " , sbf.setmis_code as subfieldId  " + " , '' as filler02  "
				+ " , sq.qualregistrationstartdate as nonNQFIntervRegStartDate  "
				+ " , sq.qualregistrationenddate as nonNQFIntervRegEndDate  " + " ,'' as filler03  "
				+ " , e.setmis_code as nonNQFIntervETQEId  " + " , '1' as nonNQFIntervStatusId  "
				+ " , spro.credits as nonNQFIntervCredit  " + " , '1' as learningProgrammeTypeId  "
				+ " ,CURDATE() as dateStamp  " + " from skills_program spro  "
				+ " inner join saqa_qualification sq on sq.id = spro.qualification_id  "
				+ " left join subfield sbf on sbf.id = sq.sub_field_id  "
				+ " inner join etqa e on e.id = spro.netqa_id  " + " UNION ALL  "
				+ " select sset.programme_id as NonNQFIntervCode  " + " , sset.description as NonNQFIntervName  "
				+ " , '' as Filler01  " + " , sbf.setmis_code as SubfieldId  " + " , '' as Filler02  "
				+ " , sq.qualregistrationstartdate as NonNQFIntervRegStartDate  "
				+ " , sq.qualregistrationenddate as NonNQFIntervRegEndDate  " + " ,'' as Filler03  "
				+ " , e.setmis_code as NonNQFIntervETQEId  " + " , '1' as NonNQFIntervStatusId  "
				+ " , sset.credits as NonNQFIntervCredit  " + " , '1' as LearningProgrammeTypeId  "
				+ " ,CURDATE() as DateStamp  " + " from skills_set sset  "
				+ " inner join saqa_qualification sq on sq.id = sset.qualification_id  "
				+ " left join subfield sbf on sbf.id = sq.sub_field_id  "
				+ " inner join etqa e on e.id = sset.netqa_id ";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile400Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_400 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " equity_code, " + " nationality_code, " + " home_language_code, "
				+ " gender_code, " + " citizen_resident_status_code, " + " filler_01, " + " filler_02, "
				+ " person_last_name, " + " person_first_name, " + " person_middle_name, " + " person_title, "
				+ " person_birth_date, " + " person_home_address_1, " + " person_home_address_2, "
				+ " person_home_address_3, " + " person_postal_address_1, " + " person_postal_address_2, "
				+ " person_postal_address_3, " + " person_home_addr_postal_code, " + " person_postal_addr_postal_code, "
				+ " person_phone_number, " + " person_cell_phone_number, " + " person_fax_number, "
				+ " person_email_address, " + " province_code, " + " provider_code, " + " provider_etqe_id, "
				+ " person_previous_last_name, " + " person_previous_alternate_id, "
				+ " person_previous_alternate_id_type, " + " person_previous_provider_code, "
				+ " person_previous_provider_etqe_id, " + " seeing_rating_id, " + " hearing_rating_id, "
				+ " walking_rating_id, " + " remembering_rating_id, " + " communicating_rating_id, "
				+ " selfcare_rating_id, " + " last_school_emis_no, " + " last_school_year, " + " statssa_area_code, "
				+ " popi_act_status_id, " + " popi_act_status_date, " + " date_stamp ) "
				+ " select u.rsa_id_number as nationalId " + " , u.passport_number as personAlternateId "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ " , e.`setmis_code` as equityCode " + " , n.`setmis_code` as nationalityCode "
				+ " , l.`setmis_code` as homeLanguageCode " + " , g.`setmis_code` as genderCode "
				+ " , IF(n.nlrd_code = 'SA','SA','U') as citizenResidentStatusCode " + " , '' as filler01 "
				+ " , '' as filler02 " + " , u.last_name as personLastName " + " , u.first_name as personFirstName "
				+ " , u.middle_name as personMiddleName " + " , t.description as personTitle "
				+ " , u.date_of_birth as personBirthDate " + " , ap.address_line_1 as personHomeAddress1 "
				+ " , ap.address_line_2 as personHomeAddress2 " + " , ap.address_line_3 as personHomeAddress3 "
				+ " , a.address_line_1 as personPostalAddress1 " + " , a.address_line_2 as personPostalAddress2 "
				+ " , a.address_line_3 as personPostalAddress3 " + " , ap.postcode as personHomeAddrPostalCode "
				+ " , a.postcode as personPostalAddrPostalCode " + " , u.tel_number as personPhoneNumber "
				+ " , u.cell_number as personCellPhoneNumber " + " , u.fax_number as personFaxNumber "
				+ " , u.email as personEmailAddress " + " , p.stats_prov_id as provinceCode "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId " + " , '' as personPreviousLastname "
				+ " , '' as personPreviousAlternateId " + " , '' as personPreviousAlternativeIdType "
				+ " , '' as personPreviousProviderCode " + " , '' as personPreviousProviderETQEId "
				+ " , '6' as seeingRatingId " + " , '6' as hearingRatingId " + " , '6' as walkingRatingId "
				+ " , '6' as rememberingRatingId " + " , '6' as communicationRatingId " + " , '6' as selfcareRatingId "
				+ " ,  psemis.`new_natemis` as lastSchoolEMISNo " + " , f.lastSchoolYear as lastSchoolYear"
				+ " , ssaac.`setmis_code` as sTATSSAAreaCode " + " , '1' as pOPIActStatusID "
				+ " , u.`create_date` as pOPIActStatusDate " + " , CURDATE() as dateStamp "
				+ " from company_learners cl " + " inner join ( "
				+ " select MAX(cl.`last_school_year`) as lastSchoolYear, cl.user_id from company_learners cl group by cl.user_id "
				+ " ) f on f.user_id = cl.user_id and f.lastSchoolYear = cl.`last_school_year` "
				+ " left join company_qualifications cq on cq.company_id = cl.company_id "
				+ " left join company_unit_standard cus on cus.company_id = cl.company_id "
				+ " left join statssa_area_code ssaac on ssaac.id = cl.`stats_saarea_code_id` "
				+ " left join previous_schools psemis on cl.`previous_schools` = psemis.id "
				+ " inner join users u on u.id = cl.user_id " + " left join equity e on e.id = u.equity_id "
				+ " left join nationality n on u.nationality_id = n.id "
				+ " left join language l on u.home_language = l.id " + " left join gender g on u.gender_id = g.id "
				+ " left join address a on a.id = u.postal_address_id "
				+ " left join address ap on ap.id = u.residential_address_id "
				+ " left join disability_status ds on ds.id = u.disabilityStatus "
				+ " left join title t on u.title_id = t.id " + " left join municipality m on m.id = a.municipality_id "
				+ " left join province p on p.id = m.provinces_idprovinces "
				+ " left join training_provider_application tpa on  tpa.users_id = u.id "
				+ " left join company c on c.id = tpa.company_id " + " left join etqa on etqa.id = tpa.etqa_id "
				+ " group by " + "   nationalId " + " , personAlternateId " + " , alternativeIdType " + " , equityCode "
				+ " , nationalityCode " + " , homeLanguageCode " + " , genderCode " + " , citizenResidentStatusCode "
				+ " , personLastName " + " , personFirstName " + " , personMiddleName " + " , personTitle "
				+ " , personBirthDate " + " , personHomeAddress1 " + " , personHomeAddress2 " + " , personHomeAddress3 "
				+ " , personPostalAddress1 " + " , personPostalAddress2 " + " , personPostalAddress3 "
				+ " , personHomeAddrPostalCode " + " , personPostalAddrPostalCode " + " , personPhoneNumber "
				+ " , personCellPhoneNumber " + " , personFaxNumber " + " , personEmailAddress " + " , provinceCode "
				+ " , providerCode " + " , providerETQEId " + " , personPreviousLastname "
				+ " , personPreviousAlternateId " + " , personPreviousAlternativeIdType "
				+ " , personPreviousProviderCode " + " , personPreviousProviderETQEId " + " , seeingRatingId "
				+ " , hearingRatingId " + " , walkingRatingId " + " , rememberingRatingId "
				+ " , communicationRatingId " + " , selfcareRatingId " + " , lastSchoolEMISNo " + " , lastSchoolYear "
				+ " , sTATSSAAreaCode " + " , pOPIActStatusID " + " , pOPIActStatusDate ";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile401Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_401 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " designation_id, " + " designation_registration_number, "
				+ " designation_etqe_id, " + " designation_start_date, " + " designation_end_date, "
				+ " designation_structure_status_id, " + " etqe_decision_number, " + " provider_code, "
				+ " provider_etqe_id, " + " filler_01, " + " filler_02, " + " filler_03, " + " date_stamp ) "
				+ " select u.rsa_id_number as nationalId,  " + " u.passport_number as personAlternateId,  "
				+ " IF(u.passport_number is not NULL,'533','527') as alternativeIdType, " + " '1' as designationId,  "
				+ " amp.certificate_number as designationRegistrationNumber,  "
				+ " eqt.setmis_code as designationETQEId, " + " amp.start_date as designationStartDate, "
				+ " amp.end_date  as designationEndDate, "
				+ " IF(amp.status = 0, '510' , (IF(amp.status = 1, '515' , (IF(amp.status = 2, '506' , (IF(amp.status = 3, '506' ,(IF(amp.status = 4, '506' , (IF(amp.status = 5, '501' , '501' )) )) )))) )) )  as designationStructureStatusId, "
				+ " rcmg.decision_number  as eTQEDecisionNumber,  "
				+ " CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode, "
				+ " eqt.setmis_code as providerETQEId,  " + " '' as filler01,  " + " '' as filler02, "
				+ " '' as filler03,  " + " CURDATE() as dateStamp " + " from assessor_moderator_application amp "
				+ " inner join users u on amp.users_id = u.id "
				+ " left join company_users cu on cu.user_id = u.id and assessor_mod_type is not NULL "
				+ " left join company c on cu.company_id = c.id "
				+ " left join training_provider_application tpa on c.id = tpa.company_id "
				+ " left join etqa eqt on c.`etqa_id` = eqt.id "
				+ " left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id "
				+ " left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id "
				+ " left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id "
				+ " group by nationalId, personAlternateId, alternativeIdType, designationId, designationRegistrationNumber, designationETQEId, designationStartDate, designationEndDate, designationStructureStatusId, eTQEDecisionNumber, providerCode, providerETQEId ";

		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile500Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_500 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " learnership_id, " + " enrolment_status_id, "
				+ " assessor_registration_number, " + " enrolment_status_date, " + " enrolment_date, "
				+ " provider_code, " + " provider_etqe_id, " + " assessor_etqe_id, " + " enrolment_status_reason_id, "
				+ " most_recent_registration_date, " + " certificate_number, " + " economic_status_id, "
				+ " funding_id, " + " cumulative_spend, " + " ofo_code, " + " sdl_no, " + " site_no, "
				+ " urban_rural_id, " + " date_stamp ) " + " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ " , lship.code as learnershipId "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId "
				+ " , '' as assessorRegistrationNumber "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate"
				+ " , cl.registered_date as enrolmentDate "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId" + " , '' as assessorETQEId "
				+ " , '' as enrolmentStatusReasonId "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as mostRecentRegistrationDate"
				+ " , '' as certificateNumber " + " , et.setmis_code as economicStatusId"
				+ " , f.setmis_code as fundingId" + " , '' as cumulativeSpend" + " , ofo.setmis_code as oFOCode "
				+ " , c.levy_number as sDLNo" + " , c.company_site_number as siteNo "
				+ " , ur.setmis_code as urbanRuralId " + " , CURDATE() as dateStamp " + " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join learnership lship on lship.id = cl.learnership_id"
				+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ " left join etqa on etqa.id = c.etqa_id "
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding" + " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile501Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_501 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " qualification_id, " + " enrolment_status_id, "
				+ " assessor_registration_number, " + " enrolment_type_id, " + " enrolment_status_date, "
				+ " enrolment_date, " + " filler_01, " + " part_of_id, " + " learnership_id, " + " provider_code, "
				+ " provider_etqe_id, " + " assessor_etqe_id, " + " filler_02, " + " enrolment_status_reason_id, "
				+ " most_recent_registration_date, " + " certificate_number, " + " filler_03, " + " filler_04, "
				+ " filler_05, " + " filler_06, " + " economic_status_id, " + " filler_07, " + " sdl_no, "
				+ " filler_08, " + " filler_09, " + " filler_10, " + " filler_11, " + " site_no, "
				+ " practical_provider_code, " + " practical_provider_etqe_id, " + " funding_id, "
				+ " cumulative_spending, " + " ofo_code, " + " urban_rural_id, " + " learning_programme_type_id, "
				+ " date_stamp ) " + " select u.rsa_id_number as nationalId  "
				+ " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , sq.qualificationid as qualificationId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '2' as partOfId  "
				+ " , '' as learnershipId  " + " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as certificateNumber  " + " , '' as filler03  " + " , '' as filler04  " + " , '' as filler05  "
				+ " , '' as filler06  " + " , et.setmis_code as economicStatusId  " + " , '' as filler07  "
				+ " , c.levy_number as sDLNo  " + " , '' as filler08  " + " , '' as filler09  " + " , '' as filler10  "
				+ " , '' as filler11  " + " , c.company_site_number as siteNo  " + " , '' as practicalProviderCode  "
				+ " , '' as practicalProviderETQEId  " + " , f.setmis_code as fundingId  "
				+ " , '' as cumulativeSpending  " + " , ofo.setmis_code as oFOCode  "
				+ " , ur.setmis_code as urbanRuralId  " + " , it.setmis_code as learningProgrammeTypeId  "
				+ " , CURDATE() as dateStamp  " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  " + " UNION ALL "
				+ " select u.rsa_id_number as nationalId  " + " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , sq.qualificationid as qualificationId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '2' as partOfId  "
				+ " , '' as learnershipId  " + " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as certificateNumber  " + " , '' as filler03  " + " , '' as filler04  " + " , '' as filler05  "
				+ " , '' as filler06  " + " , et.setmis_code as economicStatusId  " + " , '' as filler07  "
				+ " , c.levy_number as sDLNo  " + " , '' as filler08  " + " , '' as filler09  " + " , '' as filler10  "
				+ " , '' as filler11  " + " , '' as cumulativeSpending  " + " , c.company_site_number as siteNo  "
				+ " , CONCAT('MERSETA-' , tpat.`accreditation_number`) as practicalProviderCode  "
				+ " , etqt.setmis_code as practicalProviderETQEId  " + " , f.setmis_code as fundingId  "
				+ " , ofo.setmis_code as oFOCode  " + " , ur.setmis_code as urbanRuralId  "
				+ " , it.setmis_code as learningProgrammeTypeId  " + " , CURDATE() as dateStamp  "
				+ " from company_learners cl  " + " inner join users u on u.id = cl.user_id "
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
				+ " inner join company ct on ct.id = cltt.`preferred_training_center_id` "
				+ " inner join training_provider_application tpat on tpat.company_id = ct.id and tpat.`accreditation_number` is not null "
				+ " left join etqa etqt on etqt.id = ct.etqa_id  " + " UNION ALL "
				+ " select u.rsa_id_number as nationalId  " + " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , sq.qualificationid as qualificationId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '3' as partOfId  "
				+ " , lship.code as learnershipId  "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as certificateNumber  " + " , '' as filler03  " + " , '' as filler04  " + " , '' as filler05  "
				+ " , '' as filler06  " + " , et.setmis_code as economicStatusId  " + " , '' as filler07  "
				+ " , c.levy_number as sDLNo  " + " , '' as filler08  " + " , '' as filler09  " + " , '' as filler10  "
				+ " , '' as filler11  " + " , '' as cumulativeSpending  " + " , c.company_site_number as siteNo  "
				+ " , '' as practicalProviderCode  " + " , '' as practicalProviderETQEId  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  "
				+ " , ur.setmis_code as urbanRuralId  " + " , it.setmis_code as learningProgrammeTypeId  "
				+ " , CURDATE() as dateStamp  " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join learnership lship on lship.id = cl.learnership_id "
				+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null "
				+ " UNION ALL " + " select u.rsa_id_number as nationalId  "
				+ " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , sq.qualificationid as qualificationId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '5' as partOfId  "
				+ " , '' as learnershipId  " + " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as certificateNumber  " + " , '' as filler03  " + " , '' as filler04  " + " , '' as filler05  "
				+ " , '' as filler06  " + " , et.setmis_code as economicStatusId  " + " , '' as filler07  "
				+ " , c.levy_number as sDLNo  " + " , '' as filler08  " + " , '' as filler09  " + " , '' as filler10  "
				+ " , '' as filler11  " + " , '' as cumulativeSpending  " + " , c.company_site_number as siteNo  "
				+ " , '' as practicalProviderCode  " + " , '' as practicalProviderETQEId  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  "
				+ " , ur.setmis_code as urbanRuralId  " + " , it.setmis_code as learningProgrammeTypeId  "
				+ " , CURDATE() as dateStamp  " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join skills_set sset on sset.id = cl.skills_set_id "
				+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
				+ " UNION ALL " + " select u.rsa_id_number as nationalId  "
				+ " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , sq.qualificationid as qualificationId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '5' as partOfId  "
				+ " , '' as learnershipId  " + " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as certificateNumber  " + " , '' as filler03  " + " , '' as filler04  " + " , '' as filler05  "
				+ " , '' as filler06  " + " , et.setmis_code as economicStatusId  " + " , '' as filler07  "
				+ " , c.levy_number as sDLNo  " + " , '' as filler08  " + " , '' as filler09  " + " , '' as filler10  "
				+ " , '' as filler11  " + " , '' as cumulativeSpending  " + " , c.company_site_number as siteNo  "
				+ " , '' as practicalProviderCode  " + " , '' as practicalProviderETQEId  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  "
				+ " , ur.setmis_code as urbanRuralId  " + " , it.setmis_code as learningProgrammeTypeId  "
				+ " , CURDATE() as dateStamp  " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join skills_program spro on spro.id = cl.skills_set_id "
				+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null ";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile502Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_502 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " non_nqf_intervention_code, " + " enrolment_status_id, "
				+ " assessor_registration_number, " + " enrolment_type_id, " + " enrolment_status_date, "
				+ " enrolment_date, " + " part_of_id, " + " qualification_id, " + " learnership_id, "
				+ " provider_code, " + " provider_etqe_id, " + " assessor_etqe_id, " + " enrolment_status_reason_id, "
				+ " most_recent_registration_date, " + " economic_status_id, " + " funding_id, "
				+ " cumulative_spending, " + " ofo_code, " + " sdl_no, " + " site_no, " + " non_nqf_interv_etqe_id, "
				+ " urban_rural_id, " + " date_stamp ) " + " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ " , sset.programme_id as NonNQFIntervCode"
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId"
				+ " , '' as assessorRegistrationNumber" + " , '7' as enrolmentTypeId "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate "
				+ " , cl.registered_date as enrolmentDate" + " , '5' as partOfId "
				+ " , sq.qualificationid as qualificationId " + " , '' as learnershipId"
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ " , etqa.setmis_code as providerETQEId" + " , '' as assessorETQEId "
				+ " , '' as enrolmentStatusReasonId"
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate"
				+ " , et.setmis_code as economicStatusId " + " , f.setmis_code as fundingId "
				+ " , '' as cumulativeSpending" + " , ofo.setmis_code as oFOCode " + " , c.levy_number as sDLNo"
				+ " , c.company_site_number as siteNo" + " , e.setmis_code as nonNQFIntervETQEId"
				+ " , ur.setmis_code as urbanRuralId " + " , CURDATE() as dateStamp " + " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join skills_set sset on sset.id = cl.skills_set_id"
				+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ " left join etqa on etqa.id = c.etqa_id "
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding" + " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join subfield sbf on sbf.id = sq.sub_field_id" + " inner join etqa e on e.id = sset.netqa_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null"
				+ " UNION ALL" + " select u.rsa_id_number as nationalId " + " , u.passport_number as personAlternateId "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ " , spro.programme_id as NonNQFIntervCode"
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId"
				+ " , '' as assessorRegistrationNumber" + " , '7' as enrolmentTypeId "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate "
				+ " , cl.registered_date as enrolmentDate" + " , '5' as partOfId "
				+ " , sq.qualificationid as qualificationId " + " , '' as learnershipId"
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ " , etqa.setmis_code as providerETQEId" + " , '' as assessorETQEId "
				+ " , '' as enrolmentStatusReasonId"
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate"
				+ " , et.setmis_code as economicStatusId " + " , f.setmis_code as fundingId "
				+ " , '' as cumulativeSpending" + " , ofo.setmis_code as oFOCode " + " , c.levy_number as sDLNo"
				+ " , c.company_site_number as siteNo" + " , e.setmis_code as nonNQFIntervETQEId"
				+ " , ur.setmis_code as urbanRuralId " + " , CURDATE() as dateStamp " + " from company_learners cl "
				+ " inner join users u on u.id = cl.user_id"
				+ " inner join skills_program spro on spro.id = cl.skills_set_id"
				+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ " left join etqa on etqa.id = c.etqa_id "
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding" + " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join subfield sbf on sbf.id = sq.sub_field_id" + " inner join etqa e on e.id = spro.netqa_id"
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null ";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile503Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_503 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " unit_standard_id, " + " enrolment_status_id, "
				+ " assessor_registration_number, " + " enrolment_type_id, " + " enrolment_status_date, "
				+ " enrolment_date, " + " filler_01, " + " part_of_id, " + " qualification_id, " + " learnership_id, "
				+ " provider_code, " + " provider_etqe_id, " + " assessor_etqe_id, " + " filler_02, " + " filler_03, "
				+ " enrolment_status_reason_id, " + " most_recent_registration_date, " + " filler_04, " + " filler_05, "
				+ " filler_06, " + " economic_status_id, " + " filler_07, " + " filler_08, " + " filler_09, "
				+ " cumulative_spend, " + " certificate_number, " + " funding_id, " + " ofo_code, " + " sdl_no, "
				+ " site_no, " + " non_nqf_interv_code, " + " non_nqf_intervetqe_id, " + " urban_rural_id, "
				+ " date_stamp ) " + " select u.rsa_id_number as nationalId  "
				+ " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , su.unitstandardid as unitStandardId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '2' as partOf  "
				+ " , sq.qualificationid as qualificationId  " + " , '' as learnershipId  "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as filler03  " + " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as filler04  " + " , '' as filler05  " + " , '' as filler06  "
				+ " , et.setmis_code as economicStatusId  " + " , '' as filler07  " + " , '' as filler08  "
				+ " , '' as filler09  " + " , '' as cumulativeSpend " + " , '' as certificateNumber  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  " + " , c.levy_number as sDLNo "
				+ " , c.company_site_number as siteNo " + " , '' as nonNQFIntervCode   "
				+ " , '' as nonNQFIntervETQEId   " + " , ur.setmis_code as urbanRuralId  "
				+ " , CURDATE() as dateStamp " + "  from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
				+ " inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id " + " UNION ALL  "
				+ " select u.rsa_id_number as nationalId  " + " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , su.unitstandardid as unitStandardId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01   " + " , '2' as partOf  "
				+ " , sq.qualificationid as qualificationId  " + " , '' as learnershipId  "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as filler03  " + " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as filler04  " + " , '' as filler05  " + " , '' as filler06  "
				+ " , et.setmis_code as economicStatusId  " + " , '' as filler07  " + " , '' as filler08  "
				+ " , '' as filler09  " + " , '' as cumulativeSpend " + " , '' as certificateNumber  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  " + " , c.levy_number as sDLNo "
				+ " , c.company_site_number as siteNo " + " , '' as nonNQFIntervCode   "
				+ " , '' as nonNQFIntervETQEId   " + " , ur.setmis_code as urbanRuralId  "
				+ " , CURDATE() as dateStamp " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id "
				+ " inner join company ct on ct.id = cltt.`preferred_training_center_id` "
				+ " inner join training_provider_application tpat on tpat.company_id = ct.id and tpat.`accreditation_number` is not null "
				+ " left join etqa etqt on etqt.id = ct.etqa_id  "
				+ "  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
				+ " inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id " + " UNION ALL  "
				+ " select u.rsa_id_number as nationalId  " + " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , su.unitstandardid as unitStandardId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01   " + " , '3' as partOf  "
				+ " , sq.qualificationid as qualificationId  " + " , lship.code as learnershipId  "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as filler03  " + " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as filler04  " + " , '' as filler05  " + " , '' as filler06  "
				+ " , et.setmis_code as economicStatusId  " + " , '' as filler07  " + " , '' as filler08  "
				+ " , '' as filler09  " + " , '' as cumulativeSpend " + " , '' as certificateNumber  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  " + " , c.levy_number as sDLNo "
				+ " , c.company_site_number as siteNo " + " , '' as nonNQFIntervCode   "
				+ " , '' as nonNQFIntervETQEId   " + " , ur.setmis_code as urbanRuralId  "
				+ " , CURDATE() as dateStamp " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join learnership lship on lship.id = cl.learnership_id "
				+ " inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ "  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
				+ " inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null "
				+ " UNION ALL  " + " select u.rsa_id_number as nationalId  "
				+ " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , su.unitstandardid as unitStandardId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '5' as partOf  "
				+ " , sq.qualificationid as qualificationId  " + " , '' as learnershipId  "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as filler03  " + " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as filler04  " + " , '' as filler05  " + " , '' as filler06  "
				+ " , et.setmis_code as economicStatusId  " + " , '' as filler07  " + " , '' as filler08  "
				+ " , '' as filler09  " + " , '' as cumulativeSpend " + " , '' as certificateNumber  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  " + " , c.levy_number as sDLNo "
				+ " , c.company_site_number as siteNo " + " , '' as nonNQFIntervCode  "
				+ " , '' as nonNQFIntervETQEId  " + " , ur.setmis_code as urbanRuralId  " + " , CURDATE() as dateStamp "
				+ " from company_learners cl  " + " inner join users u on u.id = cl.user_id "
				+ " inner join skills_set sset on sset.id = cl.skills_set_id "
				+ " inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ "  inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
				+ " inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
				+ " where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null "
				+ " UNION ALL " + " select u.rsa_id_number as nationalId  "
				+ " , u.passport_number as personAlternateId  "
				+ " , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType  "
				+ " , su.unitstandardid as unitStandardId  "
				+ " , IF(cl.completion_date is NULL, '3', '2' ) as enrolmentStatusId  "
				+ " , '' as assessorRegistrationNumber  " + " , '7' as enrolmentTypeId  "
				+ " , IF(cl.completion_date is NULL, cl.registered_date, cl.completion_date) as enrolmentStatusDate  "
				+ " , cl.registered_date as enrolmentDate  " + " , '' as filler01  " + " , '5' as partOf  "
				+ " , sq.qualificationid as qualificationId  " + " , '' as learnershipId  "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode  "
				+ " , etqa.setmis_code as providerETQEId  " + " , '' as assessorETQEId  " + " , '' as filler02  "
				+ " , '' as filler03  " + " , '' as enrolmentStatusReasonId  "
				+ " , IF(date(cl.completion_date) is NULL, date(cl.registered_date), date(cl.completion_date)) as mostRecentRegistrationDate "
				+ " , '' as filler04  " + " , '' as filler05  " + " , '' as filler06  "
				+ " , et.setmis_code as economicStatusId  " + " , '' as filler07  " + " , '' as filler08  "
				+ " , '' as filler09  " + " , '' as cumulativeSpend " + " , '' as certificateNumber  "
				+ " , f.setmis_code as fundingId  " + " , ofo.setmis_code as oFOCode  " + " , c.levy_number as sDLNo "
				+ " , c.company_site_number as siteNo " + " , '' as nonNQFIntervCode   "
				+ " , '' as nonNQFIntervETQEId   " + " , ur.setmis_code as urbanRuralId  "
				+ " , CURDATE() as dateStamp " + " from company_learners cl  "
				+ " inner join users u on u.id = cl.user_id "
				+ " inner join skills_program spro on spro.id = cl.skills_set_id "
				+ " inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554 "
				+ " inner join intervention_type it on it.id = cl.intervention_type_id  "
				+ " inner join company c on c.id = cl.company_id "
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null "
				+ " left join etqa on etqa.id = c.etqa_id  "
				+ " inner join employment_type et on et.employment_status = u.employment_status  "
				+ " left join funding f on f.id = cl.funding " + " left join ofo on ofo.id = cl.ofo_codes_id  "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum  "
				+ " inner join summative_assessment_report sar on sar.company_learners_id = cl.id "
				+ " inner join summative_assessment_report_unit_standards sarus on sarus.summative_assessment_report_id = sar.id "
				+ " inner join saqa_unitstandard su on su.id = sarus.unit_standards_id "
				+ " where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null ";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile505Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_505 ( national_id, " + " person_alternate_id, "
				+ " alternative_id_type, " + " qualification_id, " + " trade_test_number, " + " trade_test_result_id, "
				+ " trade_test_provider_code, " + " assessor_registration_number, " + " moderator_registration_number, "
				+ " trade_test_date, " + " trade_test_result_reason_id, " + " provider_code, " + " provider_etqe_id, "
				+ " trade_test_provider_etqe_id, " + " assessor_etqe_id, " + " moderator_etqe_id, " + " date_stamp ) "
				+ " select u.rsa_id_number as nationalId " + " , u.passport_number as personAlternateId "
				+ " , IF(u.passport_number is not NULL,533,527)  as alternativeIdType "
				+ " , sq.qualificationid as qualificationId " + " , cltt.id as tradeTestNumber "
				+ " , IF(cltt.competence_enum is null, 2, IF(cltt.competence_enum = 0, 1, 2)) as tradeTestResultId"
				+ " , CONCAT('MERSETA-' , tpat.accreditation_number) as tradeTestProviderCode"
				+ " , '' as assessorRegistrationNumber" + " , '' as moderatorRegistrationNumber"
				+ " , cltt.date_of_test as tradeTestDate" + " , '' as tradeTestResultReasonId"
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ " , etqa.setmis_code as providerETQEId " + " , etqt.setmis_code as tradeTestProviderETQEId "
				+ " , '' as assessorETQEId " + " , '' as moderatorETQEId " + " , CURDATE() as dateStamp "
				+ " from company_learners cl " + " inner join users u on u.id = cl.user_id"
				+ " inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554"
				+ " inner join intervention_type it on it.id = cl.intervention_type_id "
				+ " inner join company c on c.id = cl.company_id"
				+ " inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ " left join etqa on etqa.id = c.etqa_id "
				+ " inner join employment_type et on et.employment_status = u.employment_status "
				+ " left join funding f on f.id = cl.funding" + " left join ofo on ofo.id = cl.ofo_codes_id "
				+ " left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ " inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id"
				+ " inner join company ct on ct.id = cltt.`preferred_training_center_id`"
				+ " inner join training_provider_application tpat on tpat.company_id = ct.id and tpat.accreditation_number is not null"
				+ " left join etqa etqt on etqt.id = ct.etqa_id  ";
		return (int) super.nativeSQLInsert(sql);
	}

	public int extractSetmisFile506Insert() throws Exception {
		String sql = " INSERT INTO setmis_file_506 ( national_id, " + " person_alternative_id, "
				+ " aternative_id_type_id, " + " qualification_id, " + " qualification_achievement_date, "
				+ " internship_status_id, " + " start_date, " + " end_date, " + " sdl_no, " + " site_no, "
				+ " provider_code, " + " provider_etqe_id, " + " funding_id, " + " cumulative_spend, " + " ofo_code, "
				+ " urban_rural_id, " + " date_stamp ) " + " select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid as qualificationId "
				+ "  , cl.completion_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId " + "  , cl.commencement_date as startDate"
				+ "  , cl.completion_date as endDate" + "  , c.levy_number as sDLNo "
				+ "  , c.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId" + "  , f.setmis_code as fundingId"
				+ "  , '' as  cumulativeSpend" + "  , ofo.setmis_code as oFOCode"
				+ "  , ur.setmis_code as urbanRuralId " + "  , CURDATE() as dateStamp  " + "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ "  left join etqa on etqa.id = c.etqa_id "
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding" + "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum " + "  UNION ALL"
				+ " select u.rsa_id_number as nationalId " + "  , u.passport_number as personAlternateId "
				+ "  , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid as qualificationId "
				+ "  , cl.completion_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId " + "  , cl.commencement_date as startDate"
				+ "  , cl.completion_date as endDate" + "  , c.levy_number as sDLNo "
				+ "  , c.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId" + "  , f.setmis_code as fundingId"
				+ "  , '' as  cumulativeSpend" + "  , ofo.setmis_code as oFOCode"
				+ "  , ur.setmis_code as urbanRuralId " + "  , CURDATE() as dateStamp" + "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join saqa_qualification sq on sq.id = cl.qualification_id and qualificationtypeid = 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ "  left join etqa on etqa.id = c.etqa_id "
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding" + "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  inner join company_learners_trade_test cltt on cltt.company_learners_id = cl.id"
				+ "  inner join company ct on ct.id = cltt.`preferred_training_center_id`"
				+ "  inner join training_provider_application tpat on tpat.company_id = ct.id and tpat.`accreditation_number` is not null"
				+ "  left join etqa etqt on etqt.id = ct.etqa_id " + "  UNION ALL"
				+ "  select u.rsa_id_number as nationalId " + "  , u.passport_number as personAlternateId "
				+ "  , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid as qualificationId "
				+ "  , cl.completion_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId " + "  , cl.commencement_date as startDate"
				+ "  , cl.completion_date as endDate" + "  , c.levy_number as sDLNo "
				+ "  , c.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId" + "  , f.setmis_code as fundingId"
				+ "  , '' as  cumulativeSpend" + "  , ofo.setmis_code as oFOCode"
				+ "  , ur.setmis_code as urbanRuralId " + "  , CURDATE() as dateStamp" + "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join learnership lship on lship.id = cl.learnership_id"
				+ "  inner join saqa_qualification sq on sq.id = lship.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ "  left join etqa on etqa.id = c.etqa_id "
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding" + "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is null and cl.learnership_id is not null"
				+ "  UNION ALL" + "  select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid as qualificationId "
				+ "  , cl.completion_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId " + "  , cl.commencement_date as startDate"
				+ "  , cl.completion_date as endDate" + "  , c.levy_number as sDLNo "
				+ "  , c.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId" + "  , f.setmis_code as fundingId"
				+ "  , '' as  cumulativeSpend" + "  , ofo.setmis_code as oFOCode"
				+ "  , ur.setmis_code as urbanRuralId " + "  , CURDATE() as dateStamp" + "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join skills_set sset on sset.id = cl.skills_set_id"
				+ "  inner join saqa_qualification sq on sq.id = sset.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ "  left join etqa on etqa.id = c.etqa_id "
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding" + "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  where cl.qualification_id is null and cl.skills_program_id is null and cl.skills_set_id is not null and cl.learnership_id is null"
				+ "  UNION ALL" + "  select u.rsa_id_number as nationalId "
				+ "  , u.passport_number as personAlternateId "
				+ "  , IF(u.passport_number is not NULL,'533','527')  as alternativeIdType "
				+ "  , sq.qualificationid as qualificationId "
				+ "  , cl.completion_date as qualificationAchievementDate "
				+ "  , IF(cl.status = 21,'2','1')   as  internshipStatusId " + "  , cl.commencement_date as startDate"
				+ "  , cl.completion_date as endDate" + "  , c.levy_number as sDLNo "
				+ "  , c.company_site_number as siteNo"
				+ "  , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode"
				+ "  , etqa.setmis_code as providerETQEId" + "  , f.setmis_code as fundingId"
				+ "  , '' as  cumulativeSpend" + "  , ofo.setmis_code as oFOCode"
				+ "  , ur.setmis_code as urbanRuralId " + "  , CURDATE() as dateStamp " + "  from company_learners cl "
				+ "  inner join users u on u.id = cl.user_id"
				+ "  inner join skills_program spro on spro.id = cl.skills_set_id"
				+ "  inner join saqa_qualification sq on sq.id = spro.qualification_id and qualificationtypeid <> 554"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id and it.setmis_code = 4"
				+ "  inner join company c on c.id = cl.company_id"
				+ "  inner join training_provider_application tpa on tpa.company_id = c.id and tpa.`accreditation_number` is not null"
				+ "  left join etqa on etqa.id = c.etqa_id "
				+ "  inner join employment_type et on et.employment_status = u.employment_status "
				+ "  left join funding f on f.id = cl.funding" + "  left join ofo on ofo.id = cl.ofo_codes_id "
				+ "  left join urbal_rural ur on ur.id = u.urban_rural_enum "
				+ "  where cl.qualification_id is null and cl.skills_program_id is not null and cl.skills_set_id is null and cl.learnership_id is null ";
		return (int) super.nativeSQLInsert(sql);
	}
	
	/**
	 * SETMIS FILES Validation
	 */
	 
	 
	 public int extractSetmisFile200Validation() throws Exception {
	        String sql = " INSERT INTO setmis_file_200 ( sdl_no, "
	                + " site_no, "
	                + " seta_id, "
	                + " sic_code, "
	                + " employer_registration_number, "
	                + " employer_company_name, "
	                + " employer_trading_name, "
	                + " employer_postal_address_1, "
	                + " employer_postal_address_2, "
	                + " employer_postal_address_3, "
	                + " employer_postal_address_code, "
	                + " employer_physical_address_1, "
	                + " employer_physical_address_2, "
	                + " employer_physical_address_3, "
	                + " employer_physical_address_code, "
	                + " employer_phone_number, "
	                + " employer_fax_number, "
	                + " employer_contact_name, "
	                + " employer_contact_email_address, "
	                + " employer_contact_phone_number, "
	                + " employer_contact_cell_number, "
	                + " employer_approval_status_id, "
	                + " employer_approval_status_start_date, "
	                + " employer_approval_status_end_date, "
	                + " employer_approval_status_num, "
	                + " province_code, "
	                + " country_code, "
	                + " latitude_degree, "
	                + " latitude_minutes, "
	                + " latitude_seconds, "
	                + " longitude_degree, "
	                + " longitude_minutes, "
	                + " longitude_seconds, "
	                + " main_sdl_no, "
	                + " filler_01, "
	                + " filler_02, "
	                + " date_stamp ) "
	                + "select c.levy_number as sDLNo "
					+ " , 1 as siteNo "
					+ " , c.seta_id as sETAId "
					+ " , sc.code as sICCode "
					+ " , c.company_registration_number as employerRegistrationNumber "
					+ " , c.company_name as employerCompanyName "
					+ " , c.trading_name as employerTradingName "
					+ " , a.address_line_1 as providerPostalAddress1 "
					+ " , a.address_line_2 as providerPostalAddress2 "
					+ " , a.address_line_3 as providerPostalAddress3 "
					+ " , a.postcode as providerPostalAddressCode "
					+ " , ap.address_line_1 as providerPhysicalAddress1 "
					+ " , ap.address_line_2 as providerPhysicalAddress2 "
					+ " , ap.address_line_3 as providerPhysicalAddress3 "
					+ " , ap.postcode as providerPhysicalAddressCode "
					+ " , c.tel_number as providerPhoneNumber "
					+ " , c.fax_number as providerFaxNumber "
					+ " , u.first_name as providerContactName "
					+ " , u.email as providerContactEmailAddress "
					+ " , u.tel_number as providerContactPhoneNumber "
					+ " , u.cell_number as providerContactCellNumber "
					+ " , IF( c.approval_enum = 0, 2, 1) as employerApprovalStatusId "
					+ " , c.approval_date as  employerApprovalStatusStartDate "
					+ " , '' as employerApprovalStatusEndDate "
					+ " , CONCAT(IF( c.approval_enum = 0, 2, 1) , LPAD(c.id, 8, '0')) as employerApprovalStatusNum "
					+ " , p.stats_prov_id as provinceCode "
					+ " , 'ZA' as countryCode "
					+ " , ap.latitude_degrees as latitudeDegree "
					+ " , ap.latitude_minutes as latitudeMinutes "
					+ " , ap.latitude_seconds as latitudeSeconds "
					+ " , ap.longitude_degrees as longitudeDegree "
					+ " , ap.longitude_minutes as longitudeMinutes "
					+ " , ap.longitude_seconds as longitudeSeconds "
					+ " ,  c.levy_number as mainSDLNo "
					+ " , '' as filler01 "
					+ " , '' as filler02 "
					+ " , CURDATE() as dateStamp "
					+ " from company c "
					+ " inner join sdf_company sdf on sdf.company_id = c.id "
					+ " inner join sic_code sc on sc.id = c.sic_code_id "
					+ " left join address a on a.id = c.postal_address_id "
					+ " left join address ap on a.id = c.residential_address_id "
					+ " inner join users u on u.id = sdf.sdf_id "
					+ " left join municipality m on m.id = a.municipality_id "
					+ " left join province p on p.id = m.provinces_idprovinces "
					+ " where c.levy_number is not NULL and sdf_type_id = 1 ";
	        return (int) super.nativeSQLInsert(sql);
	    }
		
		
		

	
}
