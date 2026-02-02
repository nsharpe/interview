CREATE DATABASE testdb;


CREATE ROLE kafka_connect_user WITH REPLICATION LOGIN PASSWORD 'kc_password';

-- Allowing the schema to be modified by kafka connect user is not a thing I would allow in a production environment
-- This is done to simplify development as this is not going to be production code
GRANT CONNECT ON DATABASE testdb TO kafka_connect_user;
GRANT CONNECT ON DATABASE media TO kafka_connect_user;
GRANT USAGE ON SCHEMA public TO kafka_connect_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO kafka_connect_user;


GRANT CONNECT ON DATABASE testdb TO `user`;
