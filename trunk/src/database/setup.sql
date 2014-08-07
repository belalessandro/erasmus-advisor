-- Author: Mauro Piazza
-- Description: script psql that creates the database, 
--              tables, functions and triggers.


\c postgres
drop database "erasmusadvisor";
create database "erasmusadvisor";
\c erasmusadvisor;
\ir 'erasmusAdvisor-sql.sql';   
\ir 'triggers.sql';           
\ir 'fill.sql';               

