-- Show all parked vehicles
SELECT * FROM parking_records WHERE status = 'PARKED';

-- Search by vehicle number
SELECT * FROM parking_records WHERE vehicle_number = 'KA01AB1234';

-- Total collection
SELECT SUM(fee) AS total_collection FROM parking_records WHERE status = 'EXITED';

-- Count cars vs bikes
SELECT vehicle_type, COUNT(*) AS total FROM parking_records GROUP BY vehicle_type;