CREATE DATABASE product_management_db;
CREATE USER product_manager_user WITH PASSWORD '123456';
GRANT CONNECT, CREATE ON DATABASE product_management_db TO product_manager_user;
