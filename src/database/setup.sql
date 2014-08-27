-- Author: Mauro Piazza
-- Description: script psql that creates the database, 
--              tables, functions and triggers.


\c postgres
DROP database "erasmusadvisor";
CREATE  database "erasmusadvisor" WITH ENCODING = 'UTF8';
\c erasmusadvisor;
\encoding UTF-8;
\ir 'create.sql';   
\ir 'triggers.sql'; 
\ir 'indexes.sql';   
\ir 'populateFixedDomain.sql'; 
\ir 'populate.sql';                         
