CREATE TABLE parking_records (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  vehicle_number VARCHAR(20) NOT NULL,
  vehicle_type VARCHAR(10),
  slot_number INT,
  entry_time DATETIME,
  exit_time DATETIME,
  fee DOUBLE,
  status VARCHAR(20)
);