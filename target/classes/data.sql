INSERT INTO companies (id, name, description) VALUES
    (1, 'Acme Holding Group', 'Sample company with multiple delivery streams');

INSERT INTO projects (id, name, description, company_id) VALUES
    (1, 'Phoenix', 'Customer engagement revamp' , 1),
    (2, 'Atlas', 'Data warehouse modernization' , 1),
    (3, 'Horizon', 'Mobile banking rollout' , 1);

INSERT INTO employees (id, first_name, last_name, email, job_title, company_id) VALUES
    (1, 'Liam', 'Bennett', 'liam.bennett@example.com', 'Engineering Lead', 1),
    (2, 'Olivia', 'Hart', 'olivia.hart@example.com', 'Product Manager', 1),
    (3, 'Noah', 'Griffin', 'noah.griffin@example.com', 'Backend Developer', 1),
    (4, 'Emma', 'Wade', 'emma.wade@example.com', 'QA Lead', 1),
    (5, 'Oliver', 'Flynn', 'oliver.flynn@example.com', 'Data Engineer', 1),
    (6, 'Ava', 'Rhodes', 'ava.rhodes@example.com', 'UI Engineer', 1),
    (7, 'Elijah', 'Cruz', 'elijah.cruz@example.com', 'DevOps Specialist', 1),
    (8, 'Charlotte', 'Lane', 'charlotte.lane@example.com', 'Delivery Manager', 1),
    (9, 'William', 'Sharp', 'william.sharp@example.com', 'Security Analyst', 1),
    (10, 'Sophia', 'York', 'sophia.york@example.com', 'Business Analyst', 1),
    (11, 'James', 'Parks', 'james.parks@example.com', 'Full Stack Engineer', 1),
    (12, 'Amelia', 'Reed', 'amelia.reed@example.com', 'Frontend Engineer', 1),
    (13, 'Benjamin', 'Stone', 'benjamin.stone@example.com', 'Scrum Master', 1),
    (14, 'Mia', 'Dalton', 'mia.dalton@example.com', 'QA Engineer', 1),
    (15, 'Lucas', 'Abbott', 'lucas.abbott@example.com', 'Data Scientist', 1),
    (16, 'Isabella', 'Cross', 'isabella.cross@example.com', 'UX Researcher', 1),
    (17, 'Mason', 'Irwin', 'mason.irwin@example.com', 'Site Reliability Engineer', 1),
    (18, 'Evelyn', 'Steele', 'evelyn.steele@example.com', 'Solution Architect', 1),
    (19, 'Logan', 'Prince', 'logan.prince@example.com', 'Integration Engineer', 1),
    (20, 'Harper', 'Quinn', 'harper.quinn@example.com', 'Technical Writer', 1),
    (21, 'Jacob', 'Summers', 'jacob.summers@example.com', 'Business Analyst', 1),
    (22, 'Ella', 'Thorpe', 'ella.thorpe@example.com', 'Data Analyst', 1);

INSERT INTO addresses (id, street, city, state, postal_code, country, employee_id) VALUES
    (1, '101 First Ave', 'Austin', 'TX', '73301', 'USA', 1),
    (2, '202 First Ave', 'Austin', 'TX', '73301', 'USA', 2),
    (3, '303 First Ave', 'Austin', 'TX', '73301', 'USA', 3),
    (4, '404 First Ave', 'Austin', 'TX', '73301', 'USA', 4),
    (5, '505 First Ave', 'Austin', 'TX', '73301', 'USA', 5),
    (6, '606 First Ave', 'Austin', 'TX', '73301', 'USA', 6),
    (7, '707 First Ave', 'Austin', 'TX', '73301', 'USA', 7),
    (8, '808 First Ave', 'Austin', 'TX', '73301', 'USA', 8),
    (9, '909 First Ave', 'Austin', 'TX', '73301', 'USA', 9),
    (10, '100 First Ave', 'Austin', 'TX', '73301', 'USA', 10),
    (11, '110 First Ave', 'Austin', 'TX', '73301', 'USA', 11),
    (12, '120 First Ave', 'Austin', 'TX', '73301', 'USA', 12),
    (13, '130 First Ave', 'Austin', 'TX', '73301', 'USA', 13),
    (14, '140 First Ave', 'Austin', 'TX', '73301', 'USA', 14),
    (15, '150 First Ave', 'Austin', 'TX', '73301', 'USA', 15),
    (16, '160 First Ave', 'Austin', 'TX', '73301', 'USA', 16),
    (17, '170 First Ave', 'Austin', 'TX', '73301', 'USA', 17),
    (18, '180 First Ave', 'Austin', 'TX', '73301', 'USA', 18),
    (19, '190 First Ave', 'Austin', 'TX', '73301', 'USA', 19),
    (20, '200 First Ave', 'Austin', 'TX', '73301', 'USA', 20),
    (21, '210 First Ave', 'Austin', 'TX', '73301', 'USA', 21),
    (22, '220 First Ave', 'Austin', 'TX', '73301', 'USA', 22);

-- Assign employees to projects (Phoenix has 20+ employees)
INSERT INTO project_employees (project_id, employee_id) VALUES
    (1, 1),(1, 2),(1, 3),(1, 4),(1, 5),(1, 6),(1, 7),(1, 8),(1, 9),(1, 10),
    (1, 11),(1, 12),(1, 13),(1, 14),(1, 15),(1, 16),(1, 17),(1, 18),(1, 19),(1, 20),(1, 21),(1, 22),
    (2, 1),(2, 5),(2, 7),(2, 9),(2, 11),(2, 13),(2, 15),(2, 17),(2, 19),(2, 21),
    (3, 2),(3, 4),(3, 6),(3, 8),(3, 10),(3, 12),(3, 14),(3, 16),(3, 18),(3, 20);
