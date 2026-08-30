package haj.com.dao;

import java.util.List;

import haj.com.bean.MISReportBean;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportDataDAO.
 */
public class MISReportDataDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Find by employer per province.
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerProv() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean (o.residentialAddress.municipality.province.provinceDesc , count(o) ) " +
				"from Company o where coalesce(levyNumber,'X') like 'L%' " +
				"group by o.residentialAddress.municipality.province.provinceDesc order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}


	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerProvNonLevyPaying() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean (o.residentialAddress.municipality.province.provinceDesc , count(o) ) " +
				"from Company o where coalesce(levyNumber,'X') like 'N%' or  coalesce(levyNumber,'X') like 'X%' " +
				"group by o.residentialAddress.municipality.province.provinceDesc order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerProvCode() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean (o.residentialAddress.municipality.province.code , count(o) ) " +
				"from Company o  where coalesce(levyNumber,'X') like 'L%'  " +
				"group by o.residentialAddress.municipality.province.code order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerProvCodeNonLevyPaying() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean (o.residentialAddress.municipality.province.code , count(o) ) " +
				"from Company o  where coalesce(levyNumber,'X') like 'N%' or  coalesce(levyNumber,'X') like 'X%' " +
				"group by o.residentialAddress.municipality.province.code order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerProvCodeApproved() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean (o.residentialAddress.municipality.province.provinceDesc , count(o) ) " +
				"from Company o where o.approvalDate is not null and coalesce(levyNumber,'X') like 'L%' " +
				"group by o.residentialAddress.municipality.province.provinceDesc order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerProvCodeApprovedNonLevyPaying() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean (o.residentialAddress.municipality.province.provinceDesc , count(o) ) " +
				"from Company o where o.approvalDate is not null and (coalesce(levyNumber,'X') like 'N%' or  coalesce(levyNumber,'X') like 'X%') " +
				"group by o.residentialAddress.municipality.province.provinceDesc order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}
	/**
	 * Find by employer per city.
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> findEmpPerCity() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.residentialAddress.town.description , count(o)) " +
				"from Company o " +
				"group by o.residentialAddress.town";
		return (List<MISReportBean>) super.getList(hql);
	}


	/**
	 * Find by SDF per Employer.
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> findSDFPerEmploy() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.company.companyName , count(o)) " +
				"from CompanyUsers o " +
				//"inner join Users b on o.sdf.id = b.id " +
				"where o.user.active = true and o.user.admin = false and o.user.approved = true " +
				"group by o.company.companyName";
		return (List<MISReportBean>) super.getList(hql);
	}

	/**
	 * Find by SDF per town.
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> findSDFPerArea() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.company.residentialAddress.town.description, count(distinct o.sdf.id)) " +
					"from SDFCompany o " +
					//"inner join Users b on o.sdf.id = b.id " +
					//"where o.user.active = true and o.user.admin = false and o.user.approved = true " +
					"group by o.company.residentialAddress.town";
		return (List<MISReportBean>) super.getList(hql);
	}

	/**
	 * Find by SDF per Province.
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> findSDFPerProvince() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.company.residentialAddress.municipality.province.provinceDesc, count(distinct o.user.id)) " +
					"from CompanyUsers o " +
					"where o.company.id in (select s.company.id from SDFCompany s) " +
					"group by o.company.residentialAddress.municipality.province.provinceDesc order by count(distinct o.user.id) desc ";
		return (List<MISReportBean>) super.getList(hql);
	}

	/**
	 * Find by SDF Profile.
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> findSDFPerProfileX() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.sdfType.description, count(o)) " +
					"from SDFCompany o " +
					"where o.sdf.id in (select b.id from  Users b)  " +
					"group by o.sdfType.description order by count(o) desc";
		return (List<MISReportBean>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<MISReportBean> findSDFPerRelationship() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.relationshipToCompany.description, count(distinct o.sdf.id)) " +
					"from SDFCompany o " +
					"where o.sdf.id in (select b.id from  Users b)  " +
					//"where b.id = null " +
					//"where o.sdf.active = true and o.sdf.admin = false and o.sdf.approved = true " +
					"group by o.relationshipToCompany.description order by count(distinct o.sdf.id) desc";
		return (List<MISReportBean>) super.getList(hql);
	}



	@SuppressWarnings("unchecked")
	public List<MISReportBean> usersByType() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.sdfType.description, coalesce(o.sdf.active,false), count(distinct o.sdf.id)) " +
				"from SDFCompany o " +
				"group by o.sdfType.description, coalesce(o.sdf.active,false) " +
				"order by  o.sdfType.description , coalesce(o.sdf.active,false) desc";
		return (List<MISReportBean>) super.getList(hql);
	}

	/*
select o.sdfType.description, coalesce(o.sdf.active,false), count(*)
from SDFCompany o
group by o.sdfType.description, coalesce(o.sdf.active,false)
order by coalesce(o.sdf.active,false) desc, o.sdfType.description

	 */




	//public List<Employer> allEmployer() throws Exception {
	//	return (List<Employer>)super.getList("select o from Employer o");


	@SuppressWarnings("unchecked")
	public List<MISReportBean> sdfBiographical () throws Exception {
		String hql = "select o.sdf.gender.code , o.sdf.disabilityStatus.description,   o.sdf.equity.description , o.sdf.nationality.description " +
					"from SDFCompany o " +
					"inner join Users b on o.sdf.id = b.id";
		return (List<MISReportBean>) super.getList(hql);
	}


	@SuppressWarnings("unchecked")
	public List<MISReportBean> countByBusinessProcess() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.workflowProcess, count(o)) " +
				"from Tasks o where o.taskStatus <> " + TaskStatusEnum.ERROR.ordinal() +
				" group by o.workflowProcess";
		return (List<MISReportBean>) super.getList(hql);
	}

	public Long countContacts() throws Exception {
		String hql = "select  count(distinct o.user.id) " +
			"from CompanyUsers o " +
			"where o.user.id not in (select x.sdf.id from SDFCompany x)";
		return (Long)super.getUniqueResult(hql);
	}

	/*  EXECUTIVE & SENIOR MANAGER DASHBOARD/ INDICATORS/REPORTS  */
	//

/**
 * 1.     Total number of companies registered on NSDMS in relation to total levy-paying companies by Chamber
 * @return
 * @throws Exception
 */
	@SuppressWarnings("unchecked")
	public List<MISReportBean> totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamberSARS() throws Exception {
			String hql = "select  new haj.com.bean.MISReportBean(s.chamber.description, count(b)) " +
					"from SarsEmployerDetail b, SICCode s , SarsLevyDetails lp " +
					"where b.sicCode2 = s.code " +
					"and b.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  ) " +
					"and b.tradingStatus = 'A' " +
					"and lp.sarsFiles.id = b.sarsFiles.id " +
					"and b.refNo = lp.refNo "+
					"group by s.chamber.description";
			return (List<MISReportBean>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<MISReportBean> totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamber() throws Exception {
		String hql = "select new haj.com.bean.MISReportBean(o.sicCode.chamber.description, count(o)) " +
				"from Company o " +
				"where o.id in (select s.company.id from SDFCompany s ) " +
				"and o.levyNumber is not null " +
				"and o.linkedCompany is null " +
				"group by  o.sicCode.chamber.description";
		return (List<MISReportBean>) super.getList(hql);
}

/**
 * 3.   Total Levy contribution of the companies that have been registered on the NSDMS by size of company and Chamber
 * @return
 * @throws Exception
 */
		@SuppressWarnings("unchecked")
		public List<MISReportBean> totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompanyAndChamber() throws Exception {
			String hql = "select new haj.com.bean.MISReportBean(o.sicCode.chamber.description, sz.id , coalesce(sz.description,'Unknown'), sum(ld.total)) " +
					"from Company o    " +
					"inner join SarsLevyDetails ld   " +
					" on ld.refNo = o.levyNumber    " +
					" and ld.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )   " +
					"left join SizeOfCompany sz    " +
					" on coalesce(o.numberOfEmployees,-1) between sz.minSize and coalesce(sz.mazSize, 9999999)    " +
					"where  o.levyNumber is not null    " +
					"and o.linkedCompany is null   " +
					"and 	o.id in (select s.company.id		from SDFCompany s)     " +
					"group by  o.sicCode.chamber.description,sz.id,sz.description    " +
					"order by o.sicCode.chamber.description , sz.id";
			return (List<MISReportBean>) super.getList(hql);
		}


		@SuppressWarnings("unchecked")
		public List<MISReportBean> totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsByChamber() throws Exception {
			String hql = "select new haj.com.bean.MISReportBean(o.sicCode.chamber.description,  sum(ld.total)) " +
					"from Company o,  SarsLevyDetails ld  " +
					"where o.id in (select s.company.id from SDFCompany s) " +
					"and o.levyNumber is not null  " +
					"and ld.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )  " +
					"and ld.refNo = o.levyNumber  " +
					"and o.linkedCompany is null  " +
					"group by  o.sicCode.chamber.description  " +
					"order by o.sicCode.chamber.description ";
			return (List<MISReportBean>) super.getList(hql);
		}

		@SuppressWarnings("unchecked")
		public List<MISReportBean> totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompany() throws Exception {
			String hql = "select new haj.com.bean.MISReportBean(sz.id , coalesce(sz.description,'Unknown'), sum(ld.total)) " +
					"from Company o  " +
					"inner join SarsLevyDetails ld " +
					" on ld.refNo = o.levyNumber  " +
					" and ld.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )  " +
					"left join SizeOfCompany sz  " +
					" on coalesce(o.numberOfEmployees,-1) between sz.minSize and coalesce(sz.mazSize, 9999999)  " +
					"where  o.levyNumber is not null  " +
					"and o.id in (select s.company.id from SDFCompany s)  " +
					"and o.linkedCompany is null " +
					"group by sz.id,sz.description  " +
					"order by  sz.id";
			return (List<MISReportBean>) super.getList(hql);
		}

		@SuppressWarnings("unchecked")
		public List<MISReportBean> findSDFPerProfile() throws Exception {
			String hql = "select  new haj.com.bean.MISReportBean(o.sdfType.description, count(distinct o.sdf.id)) from SDFCompany o group by o.sdfType.description";
			return (List<MISReportBean>) super.getList(hql);
		}

		@SuppressWarnings("unchecked")
		public List<MISReportBean> countContactsByAvtiveInactive() throws Exception {
			String hql = "select new haj.com.bean.MISReportBean( " +
					"	case when o.user.lastLogin is null then false " +
					"		 else true " +
					"	end	 " +
					" , count(distinct o.user.id)) " +
					"from CompanyUsers o  " +
					"where o.user.id not in (select x.sdf.id from SDFCompany x) " +
					"group by case  " +
					"		when o.user.lastLogin is null then false " +
					"		else true " +
					"	end	";
			return (List<MISReportBean>) super.getList(hql);
		}

	/*


select new haj.com.bean.MISReportBean(case
		when o.user.lastLogin is null then false
		else true
	end
 , count(distinct o.user.id))
from CompanyUsers o
where o.user.id not in (select x.sdf.id from SDFCompany x)
group by case
		when o.user.lastLogin is null then false
		else true
	end


1.         EXTERNAL PARTY INPUT


1.     Number of new tasks logged on NSDMS by external parties per hour, day, week, month, year

	Grand TOTAL
	 select count(o) from Tasks o
				where o.createUser.id not in (select x.users.id from HostingCompanyEmployees x)

	Total per month day
	 select month(o.createDate), MONTHNAME(o.createDate), day(o.createDate), count(o)
	 from Tasks o
     where o.createUser.id not in (select x.users.id from HostingCompanyEmployees x)
     group by  month(o.createDate), MONTHNAME(o.createDate), day(o.createDate)
     order by month(o.createDate), day(o.createDate)

 	Total per hour for day
   	 select  hour(o.createDate), count(o)
	 from Tasks o
     where o.createUser.id not in (select x.users.id from HostingCompanyEmployees x)
     and date(o.createDate) = '2018-02-21'
     group by  hour(o.createDate)
     order by hour(o.createDate)


2.     Number of new tasks completed within work day (work day 8am – 4.30 Mon – Thurs and then 8am – 3.30pm Friday) by merSETA

3.     Average time it takes for external party to complete process e.g. SDF registration, Banking details, Grants application etc
	   select o.workflowProcess, avg(datediff(o.actionDate, o.createDate)) from Tasks o
				where o.taskStatus = 2
				and datediff(o.actionDate, o.createDate) is not null
				and o.actionUser.id not in (select x.users.id from HostingCompanyEmployees x)
		group by o.workflowProcess

4.     Age analysis of tasks by category for external party: Not yet started and Underway (link this to the SLA so age analysis could use: 1 day; 2 days; 3 days; 4 days; 5 days; & >5days)

5.     Number of System Issues by Users daily, weekly, monthly & annual



2.         EXECUTIVE & SENIOR MANAGER DASHBOARD/ INDICATORS/REPORTS



1.     Total number of companies registered on NSDMS in relation to total levy-paying companies by Chamber and Company size  --- DONE

select o.sicCode.chamber.description, count(*) from Company o, SDFCompany s
where o.id = s.company.id
and o.levyNumber is not null
and o.linkedCompany is null
group by  o.sicCode.chamber.description

select o.sicCode.chamber.description, sz.description, count(*) from Company o, SDFCompany s, SizeOfCompany sz
where o.id = s.company.id
and o.numberOfEmployees between sz.minSize and coalesce(sz.mazSize, 9999999)
and o.levyNumber is not null
group by  o.sicCode.chamber.description, sz.description


select s.chamber.description, count(b) from SarsEmployerDetail b, SICCode s
where b.sicCode2 = s.code
and b.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )
and b.tradingStatus = 'A'
group by s.chamber.description

//no data on db yet
select s.chamber.description, count(b) from SarsEmployerDetail b, SICCode s, SizeOfCompany sz
where b.sicCode2 = s.code
and b.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )
and b.tradingStatus = 'A'
and  b.noEmployesAccordingToSARS  between sz.minSize and coalesce(sz.mazSize, 9999999)
group by s.chamber.description


2.      Total number of companies registered on NSDMS in relation to company status (Active vs Stopped Trading) by Chamber and Company size

3.     Total Levy contribution of the companies that have been registered on the NSDMS by size of company and Chamber   --- DONE


select o.sicCode.chamber.description,sz.id, sz.description, sum(ld.total) from Company o, SDFCompany s, SarsLevyDetails ld, SizeOfCompany sz
where o.id = s.company.id
and o.levyNumber is not null
and ld.sarsFiles.id = (select  x.id from SarsFiles x where  x.forMonth = (select max(y.forMonth) from SarsFiles y)  )
and ld.refNo = o.levyNumber
and o.numberOfEmployees between sz.minSize and coalesce(sz.mazSize, 9999999)
group by  o.sicCode.chamber.description,sz.id,sz.description
order by o.sicCode.chamber.description , sz.id



4.     Size of Company: Total number of companies by company size category registered on NSDMS and Chamber

5.     Total number of employees (by different type of employee) by company size category registered on NSDMS

6.     Total number of companies registered on NSDMS by company size with recognition agreements, Training Committees, with multiple sites and linked companies

7.     Chamber Profile: Total number (and by %) of registered companies on NSDMS by Chamber (including non-merSETA Chamber)

8.     Company Grant applications trend analysis (we would use the MG approval history that we also use for the SARS stuff)

a.     Total number of first time submissions (maybe we need to ask companies if this is their first time submitting when they are initiating Grants?)

b.     Total number of companies submitting MG applications in past five years (this year and last 4 years)

c.     Number of companies that have submitted each time for past 5 years, have submitted 4 times for past 5 years, have submitted 3 times in last 5 years, have submitted 2 times in past 5 years, have submitted 1 time in past 5 years)

9.     Operational Targets vs Actual (I will need to get this information and then we will work with these as well)



3.         STAFF PERFORMANCE REPORTS



a.         Team Productivity Dashboard Indicators



1.     Total Tasks processed by team per day, week, month, year

2.     Average time taken to process total tasks by employee per day, week, month, year

3.     Tasks completed by Team: Total Tasks completed breakdown by all employees

4.     Employees-per-Task Ratio: Number of employees allocated to a task per day/the total number of tasks completed per day (would also want a weekly, monthly and annual average)

5.     Team productivity: Calculation – Number of total tasks completed* per day/number of users allocated to process task

6.     Age analysis of tasks by category merSETA: Not yet started and Underway (must link this to the SLA so age analysis could use: 1 day; 2 days; 3 days; 4 days; 5 days; & >5days

7.     NSDMS Activity: NSDMS login activity of each employee daily, weekly, monthly & annual (trying to show which employees are logging in)

a.     NSDMS Login to Task Completion: % that a system login translated to an employee completing available task – so want to see if people are just logging in but they actually don’t complete the work…)



b.         Individual Dashboards



1.     Total Tasks completed per employee per day, week, month, year

2.     Average time taken to complete total tasks by employee per day, week, month, year

3.     Employee Productivity Contribution: Total number of tasks completed by employee/Total number of tasks completed (can be presented as a table or bar chart) (analysis to be per day, week, month, year)




select tmp.description, count(tmp.description) from
(select
  a.sdf_id,
  b.description ,
  count(a.id)
 from
  sdf_company a
 inner join
  sdf_type b
 where
  a.sdf_type_id=b.id
 group by
  a.sdf_id ,
  b.description
  order by b.description) as tmp
group by   tmp.description


	 */


}
