-- Sample claims data for development/testing
INSERT INTO claims (claim_id, patient_name, medication, amount, status, flagged_for_review, flag_reason, created_at)
VALUES
  ('RX-001', 'Maria Garcia',    'Lisinopril',   45.00,  'APPROVED', false, null, CURRENT_TIMESTAMP),
  ('RX-002', 'James Wilson',    'Metformin',    120.00, 'APPROVED', false, null, CURRENT_TIMESTAMP),
  ('RX-003', 'Sarah Johnson',   'Atorvastatin', 650.00, 'FLAGGED',  true,  'High-value claim exceeds $500 threshold', CURRENT_TIMESTAMP),
  ('RX-004', 'Maria Garcia',    'Omeprazole',   89.00,  'PENDING',  false, null, CURRENT_TIMESTAMP),
  ('RX-005', 'Maria Garcia',    'Amlodipine',   75.00,  'FLAGGED',  true,  'Duplicate claim detected: patient has 2 existing claims', CURRENT_TIMESTAMP),
  ('RX-006', 'Robert Chen',     'Metoprolol',   210.00, 'APPROVED', false, null, CURRENT_TIMESTAMP),
  ('RX-007', 'Linda Martinez',  'Levothyroxine',55.00,  'PENDING',  false, null, CURRENT_TIMESTAMP);
