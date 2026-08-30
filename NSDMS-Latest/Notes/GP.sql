update company set on_gp = true where levy_number in
(select b.vendor_id from gp_creditors b)