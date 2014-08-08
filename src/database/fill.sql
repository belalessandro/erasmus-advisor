-- Author: Mauro Piazza
-- Last Modified: 2014.08.06 12.04
--
-- Description: SQL file containing all statements to populate the database

--INSERT INTO Studente VALUES('mauro.piazza', 'mr.charlie90@gmail.com','2013-04-01', 'aldskj2o38428dqjkhdkqj1832','alskdjlkj23948209wdh', NULL, TRUE);
--INSERT INTO Studente VALUES('mauro.piazza1', 'mr.charle90gmail.com','2013-04-01', 'aldskj2o38428dqjkhdkqj1832','alskdjlkj23948209wdh', NULL, TRUE);


-- Inserting Cities
INSERT INTO citta VALUES('Paris', 'France');
INSERT INTO citta VALUES('Copenhagen', 'Denmark');
INSERT INTO citta VALUES('Cambridge', 'United Kingdom');

--Inserting Languages
INSERT INTO lingua VALUES('fra', 'French');
INSERT INTO lingua VALUES('eng', 'English');

INSERT INTO linguacitta VALUES('fra','Paris','France');

--delete from citta where Nome='Paris';

-- Inserting Universities
INSERT INTO Universita VALUES ('Ecole normale supérieure','www.ens.fr', 28, TRUE, 'Paris', 'France');
INSERT INTO Universita VALUES ('University of Copenhagen','www.ku.dk', 45, TRUE, 'Copenhagen', 'Denmark');
INSERT INTO Universita VALUES ('University of Cambridge','www.cam.ac.uk', 32, TRUE, 'Cambridge', 'United Kingdom');


-- Inserting FlowManagers
INSERT INTO ResponsabileFlusso VALUES (
    'georgua.en',
    'Georguà', 
    'Enformage', 
    'georgua.enformage@ens.fr', 
    CURRENT_DATE, 
    'asldubvdkwjbkwrw98374', 
    'slakj238940293fh', 
    NULL, 
    TRUE, 
    'Ecole normale supérieure');

INSERT INTO ResponsabileFlusso VALUES (
    'haning.bar',
    'Hansen', 
    'Inger Barnechow', 
    'haning.bar@ku.dk', 
    CURRENT_DATE, 
    'dskjrewoifowi833', 
    'wskjscnn301qqq', 
    NULL, 
    TRUE, 
    'University of Copenhagen');
    
    
INSERT INTO ResponsabileFlusso VALUES (
    'erick.burn',
    'Erick', 
    'Burngood', 
    'erick.burn@cam.ac.uk', 
    CURRENT_DATE, 
    'lr3tjktj09weiorjow', 
    'slakj2awòefhkjwcbb', 
    NULL, 
    TRUE, 
    'University of Cambridge');
    
-- Inserting Courses
INSERT INTO CorsoDiLaurea VALUES (1, 'Économie', 'triennale', 'Ecole normale supérieure');
INSERT INTO CorsoDiLaurea VALUES (2, 'Département de chimie', 'triennale', 'Ecole normale supérieure');
INSERT INTO CorsoDiLaurea VALUES (3, 'Department of Mathematical Sciences', 'triennale', 'University of Copenhagen');
INSERT INTO CorsoDiLaurea VALUES (4, 'Computer Science', 'triennale', 'University of Cambridge');
INSERT INTO CorsoDiLaurea VALUES (5, 'Département de physique', 'triennale', 'Ecole normale supérieure');

-- Inserting Flows
INSERT INTO Flusso VALUES ('FRA001', 'Ecole normale supérieure', 'georgua.en', 10, TRUE, CURRENT_DATE, 5);
INSERT INTO Flusso VALUES ('COP001', 'University of Copenhagen', 'haning.bar', 3, TRUE, CURRENT_DATE, 6);



-- TEST Trigger 1
--INSERT INTO Origine VALUES ('FRA001', 1)

-- Inserting Flow's origins
INSERT INTO Origine VALUES ('COP001', 1);
INSERT INTO Origine VALUES ('COP001', 2);

-- TEST Trigger 2
--INSERT INTO Origine VALUES ('COP001', 4);

INSERT INTO Origine VALUES ('COP001', 5);


