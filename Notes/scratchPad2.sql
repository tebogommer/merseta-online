insert into company (company_registration_number,levy_number,company_name,trading_name,sic_code_id)
select 
	company_registration_number ,
	ref_no as levy_number,
	substring(registered_name_of_entity,1,40) as company_name,
	substring(trading_name,1,40) as trading_name,
	sic_code.id as sic_code_id
from sars_employer_detail 
left join sic_code
on sic_code_2 = code
where trading_status = 'A' 
and sars_filel_id = 4




UPDATE company set company_guid = (SELECT UUID());

UPDATE company set company_status = 0;
UPDATE company set company_type = 1;


insert into company_learners (company_id,learner_id)


select CONCAT('update learner set hs_company_id =',b.id, ' where Primarykey=',   a.Primarykey,';') 
from learner a
inner join company b
on a.EmployerSDL = b.levy_number


select CONCAT('update learner set hs_learner_id =',c.id, ' where Primarykey=',   a.Primarykey,';') 
from learner a
inner join learners c
on c.rsa_id_number = a.LearnerID




select hs_company_id,hs_learner_id, LearnershipCode as , QualificationCode ,DGTag , ETQAID, CurrentStatus, Funding,CertificateDate
from learner
where hs_company_id is not null 
and hs_learner_id is not null

CREATE TABLE `company_learners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `learner_id` bigint(20) DEFAULT NULL,
  `certificate_date` datetime DEFAULT NULL,
  `current_status` varchar(100) DEFAULT NULL,
  `dgtag` varchar(50) DEFAULT NULL,
  `etqid` varchar(10) DEFAULT NULL,
  `funding` varchar(30) DEFAULT NULL,
  `learnership_code` varchar(50) DEFAULT NULL,
  `qualification_code` varchar(20) DEFAULT NULL,
  `scheme_year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn1l82rfvmvbkhba2l57nqm2ip` (`company_id`),
  KEY `FKg0lo2gwue7lhvrxyku2rpqn80` (`learner_id`),
  CONSTRAINT `FKg0lo2gwue7lhvrxyku2rpqn80` FOREIGN KEY (`learner_id`) REFERENCES `learners` (`id`),
  CONSTRAINT `FKn1l82rfvmvbkhba2l57nqm2ip` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1



select distinct AppType
from learner
where hs_company_id is not null 
and hs_learner_id is not null
and QualificationCode is not null





insert into learners(rsa_id_number,first_name,last_name,cell_number,email,date_of_birth)
select  distinct b.PersonId as rsa_id_number,
				 b.Firstname as first_name,
				 b.Surname as last_name,
				 substring(b.CellNumber,1,20) as cell_number,
				 b.Email as email,
				 b.DateOfBirth as date_of_birth
from learner a , person b
where a.PersonNo = b.`PersonNo`
and b.PersonId REGEXP '^[0-9]+$'
and length(b.PersonId) = 13



show create table company;

CREATE TABLE `company_learners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `learner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn1l82rfvmvbkhba2l57nqm2ip` (`company_id`),
  KEY `FKg0lo2gwue7lhvrxyku2rpqn80` (`learner_id`),
  CONSTRAINT `FKg0lo2gwue7lhvrxyku2rpqn80` FOREIGN KEY (`learner_id`) REFERENCES `learners` (`id`),
  CONSTRAINT `FKn1l82rfvmvbkhba2l57nqm2ip` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1


DROP TABLE `company`;
CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_guid` varchar(100) DEFAULT NULL,
  `company_name` varchar(40) DEFAULT NULL,
  `company_registration_number` varchar(40) DEFAULT NULL,
  `company_status` int(11) DEFAULT NULL,
  `company_type` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `fax_number` varchar(20) DEFAULT NULL,
  `levy_number` varchar(40) DEFAULT NULL,
  `levy_number_additional` varchar(40) DEFAULT NULL,
  `number_of_employees` int(11) DEFAULT NULL,
  `paye_sdl_number` varchar(40) DEFAULT NULL,
  `tel_number` varchar(20) DEFAULT NULL,
  `trading_name` varchar(40) DEFAULT NULL,
  `banking_details_id` bigint(20) DEFAULT NULL,
  `chamber_id` bigint(20) DEFAULT NULL,
  `form_user_id` bigint(20) DEFAULT NULL,
  `institution_type_id` bigint(20) DEFAULT NULL,
  `postal_address_id` bigint(20) DEFAULT NULL,
  `registered_address_id` bigint(20) DEFAULT NULL,
  `residential_address_id` bigint(20) DEFAULT NULL,
  `sic_code_id` bigint(20) DEFAULT NULL,
  `approval_enum` int(11) DEFAULT NULL,
  `reject_reason` longtext,
  `company_id` bigint(20) DEFAULT NULL,
  `majority_union_id` bigint(20) DEFAULT NULL,
  `recognition_agreement_id` bigint(20) DEFAULT NULL,
  `categorization` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `levy_number` (`levy_number`)
) ENGINE=InnoDB AUTO_INCREMENT=27214 DEFAULT CHARSET=latin1;


drop table learners;
CREATE TABLE `learners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cell_number` varchar(20) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `fax_number` varchar(20) DEFAULT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `passport_number` varchar(30) DEFAULT NULL,
  `rsa_id_number` varchar(13) DEFAULT NULL,
  `tel_number` varchar(20) DEFAULT NULL,
  `work_contact_number` varchar(20) DEFAULT NULL,
  `disabilityStatus` bigint(20) DEFAULT NULL,
  `disabled_id` bigint(20) DEFAULT NULL,
  `equity_id` bigint(20) DEFAULT NULL,
  `gender_id` bigint(20) DEFAULT NULL,
  `municipality_id` int(11) DEFAULT NULL,
  `nationality_id` bigint(20) DEFAULT NULL,
  `postal_address_id` bigint(20) DEFAULT NULL,
  `residential_address_id` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1