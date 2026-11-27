-- When vehicle exits, update exit time and fee
UPDATE parking_records
SET exit_time = NOW(), fee = 50, status = 'EXITED'
WHERE vehicle_number = 'KA01AB1234';