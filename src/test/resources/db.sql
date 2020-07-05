DROP TABLE IF EXISTS test_user;
DROP TABLE IF EXISTS test_role;
DROP TABLE IF EXISTS test_user_role;

CREATE TABLE test_user (
    id   VARCHAR(250) NOT NULL PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    test_role_id varchar(20) default '' null
);

CREATE TABLE test_role (
    id   VARCHAR(250) NOT NULL PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);
CREATE TABLE test_user_role (
    id   VARCHAR(250) NOT NULL PRIMARY KEY,
    test_user_id varchar(20) default '' null,
    test_role_id varchar(20) default '' null
);

 INSERT INTO test_user (id, username,test_role_id) VALUES
('10000', 'mike','20000'),
('10001', 'jake','20000');

INSERT INTO test_role (id, name) VALUES
('20000', 'role-1'),
('20001', 'role-2');


 INSERT INTO test_user_role (id, test_user_id,test_role_id) VALUES
('30000', '10000','20000'),
('30001', '10000','20001');

