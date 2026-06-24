CREATE DATABASE cs415;

-- create API User
create user cs415apiuser with password 'cs415ApiUserPass' SUPERUSER;
grant all privileges on database cs415 to cs415apiuser;