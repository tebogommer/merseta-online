select c.levy_number, c.company_name , c.company_registration_number,c.company_status,
( select b.bank_acc_number from banking_details b  where b.company_id =  c.id order by b.id desc limit 1) as bank_acc_number,
( select bank_holder from banking_details b  where b.company_id =  c.id order by b.id desc limit 1) as bank_holder,
( select branch_code from banking_details b  where b.company_id =  c.id order by b.id desc limit 1) as branch_code,
( select k.description from banking_details b , bank k where b.company_id =  c.id  and b.bank_id = k.id order by b.id desc limit 1) as bank,
a.scheme_year, a.mandatory_levy, a.discretionary_levy, a.admin_levy, a.interest, a.penalty , a.total
from sars_levy_detail a , company c
where a.sars_filel_id = 218
and  c.levy_number = a.ref_no





delete from sars_levy_detail where    sars_filel_id = 216;
delete from sars_employer_detail where    sars_filel_id = 216;
delete from sars_files where id = 216;