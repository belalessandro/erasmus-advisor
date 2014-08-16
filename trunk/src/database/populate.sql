-- Author: Mauro Piazza
-- Last Modified: 2014.08.06 12.04
--
-- Description: SQL file containing all statements to populate the database

-- luca: dirottato in file per popolare il databse con entry generiche
-- carico anche se non funziona, bisogna trovare un modo per recuperare gli ID inseriti 
-- con i SERIAL

-- Inserting Cities
INSERT INTO citta VALUES('Paris', 'France');
INSERT INTO citta VALUES('Copenhagen', 'Denmark');
INSERT INTO citta VALUES('London', 'United Kingdom');
INSERT INTO citta VALUES('Cambridge', 'United Kingdom');
INSERT INTO citta VALUES('Berlin', 'Germany');
INSERT INTO citta VALUES('Padua', 'Italy');

INSERT INTO linguacitta VALUES('fra','Paris','France');
INSERT INTO linguacitta VALUES('ita','Padua','Italy');
INSERT INTO linguacitta VALUES('deu','Berlin','Germany');
INSERT INTO linguacitta VALUES('eng','London','United Kingdom');
INSERT INTO linguacitta VALUES('eng','Cambridge','United Kingdom');
INSERT INTO linguacitta VALUES('dan','Copenhagen','Denmark');

-- Inserting Universities
INSERT INTO Universita VALUES 
	('Università degli Studi di Padova','www.unipd.it', 200, FALSE, 'Padua', 'Italy'),
	('Ecole normale supérieure','www.ens.fr', 28, TRUE, 'Paris', 'France'),
	('Imperial College London','www.imperial.ac.uk', 4, FALSE, 'London', 'United Kingdom'),
	('University College London','www.ucl.ac.uk', 4, FALSE, 'London', 'United Kingdom'),
	('University of Copenhagen','www.ku.dk', 45, TRUE, 'Copenhagen', 'Denmark'),
	('University of Cambridge','www.cam.ac.uk', 1, TRUE, 'Cambridge', 'United Kingdom');
	
-- Inserting degree courses
INSERT INTO CorsoDiLaurea (Id, Nome, NomeUniversita, Livello) VALUES
	(DEFAULT, 'Ingegneria Informatica', 'Università degli Studi di Padova', 'UNDERGRADUATE'),
	(DEFAULT, 'Ingegneria Informatica', 'Università degli Studi di Padova', 'GRADUATE'),
	(DEFAULT, 'Ingegneria delle Telecomunicazioni', 'Università degli Studi di Padova', 'GRADUATE'),
	(DEFAULT, 'Giurisprudenza', 'Università degli Studi di Padova', 'UNIQUE'),
	(DEFAULT, 'Medicina Generale', 'Università degli Studi di Padova', 'UNIQUE');

INSERT INTO Specializzazione (NomeArea, IdCorso) VALUES
	('Software and applications development and analysis', 1),
	('Software and applications development and analysis', 2),
	('Database and network design and administration', 2),
	('Database and network design and administration', 3),
	('Law', 4),
	('Medicine', 5);
	
-- Inserting FLows
INSERT INTO ResponsabileFlusso VALUES 
	( 'georgua.en',
    'Georguà', 
    'Enformage', 
    'georgua.enformage@ens.fr', 
    CURRENT_DATE, 
    'asldbkwrw98374', 
    'slakj2393fh', 
    NULL, 
    TRUE, 
    'Ecole normale supérieure'),
    ( 'pilu',
    'Andrea', 
    'Culano', 
    'culano@dei.unipd..dk', 
    CURRENT_DATE, 
    'dskjwi833', 
    'wskjscnn301qqq', 
    NULL, 
    TRUE, 
    'Università degli Studi di Padova'),
    ( 'erick.burn',
    'Erick', 
    'Burngood', 
    'erick.burn@cam.ac.uk', 
    CURRENT_DATE, 
    'lr3tjorjow', 
    'slakj2akjwcbb', 
    NULL, 
    TRUE, 
    'University of Cambridge');

INSERT INTO Flusso (Id, Destinazione, RespFlusso, PostiDisponibili, Attivo, DataUltimaModifica, Durata, Dettagli) VALUES 
	('FRA001', 'Ecole normale supérieure', 'pilu', 10, TRUE, CURRENT_DATE, 5, NULL),
	('ENG57', 'Imperial College London', 'pilu', 1, TRUE, CURRENT_DATE, 9, 'Highly Exclusive'),
	('COP001', 'University of Copenhagen', 'pilu', 3, TRUE, CURRENT_DATE, 6, NULL);

INSERT INTO Origine (IdFlusso, IdCorso) VALUES
	('FRA001', '2'),
	('FRA001', '3'),
	('ENG57', '4'),
	('COP001', '1');
	
-- manca riconoscimento
	
-- Inserting Courses
INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoDiCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Analisi 1', 12, 'Università degli Studi di Padova', 1, 'NOT VERIFIED' , 1, 'Mathematics', 'ita'),
	(DEFAULT, 'Calcolus', 9, 'Imperial College London', 1, 'NOT VERIFIED' , 1, 'Mathematics', 'eng');
	
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Luca', 'Pizzul'),
	(DEFAULT, 'Hanges', 'Stranges'),
	(DEFAULT, 'Paula', 'Spark'),
	(DEFAULT, 'Ahmed', 'Kamikaze'),
	(DEFAULT, 'Robb', 'Stark'),
	(DEFAULT, 'Jeoffrey', 'Baratheon'),
	(DEFAULT, 'Alice', 'Cooper');
	
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(1,1),
	(2,5),
	(2,6),
	(2,7);
	
-- Inserting Theses

INSERT INTO ArgomentoTesi(Id, Nome, NomeUniversita, Triennale, Magistrale, Stato) VALUES
	(DEFAULT, 'Counting up to ten', 'Imperial College London', TRUE, TRUE, 'NOT VERIFIED'),
	(DEFAULT, 'Counting up to a hundred', 'Imperial College London', FALSE, TRUE, 'VERIFIED'),
	(DEFAULT, 'Laws under Haakon Bluebeard', 'University of Copenhagen', FALSE, TRUE, 'VERIFIED');

INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(1,5),
	(1,6),
	(2,6),
	(3,2),
	(3,3);
	
INSERT INTO Estensione (IdArgomentoTesi, Area) VALUES
	(1,'Mathematics'),
	(1,'Statistics'),
	(2,'Mathematics'),
	(3,'History and archaeology'),
	(3,'Law');
	
INSERT INTO LinguaTesi(IdArgomentoTesi, SiglaLingua) VALUES
	(1, 'eng'),
	(2, 'eng'),
	(3, 'eng'),
	(3, 'dan');
	
-- inserting students

INSERT INTO Studente (NomeUtente, Email, DataRegistrazione, Password, Salt, UltimoAccesso, Attivo) VALUES
	('prezzemolino', 'hange@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	('mario.rossi', 'rossima@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	(';DROP TABLE Users CASCADE;', 'hacker@mail.com', CURRENT_DATE, 'password', 'void', NULL, FALSE),
	('JuventinoDOC', 'perdente@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	('VIVA BEPPE GRILLO', 'grillino@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE);
	
INSERT INTO Iscrizione (IdCorso, NomeUtenteStudente, AnnoInizio, AnnoFine) VALUES
	(1,'prezzemolino', '2012-09-01', '2015-06-30'),
	(1,'mario.rossi', '2012-09-01', '2015-06-30'),
	(2,';DROP TABLE Users CASCADE;', '2014-09-01', '2016-06-30'),
	(3,'JuventinoDOC', '2014-09-01', '2016-06-30'),
	(3,'VIVA BEPPE GRILLO', '2014-09-01', '2016-06-30');




