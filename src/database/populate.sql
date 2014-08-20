-- Author: Mauro Piazza
-- Last Modified: 2014.08.06 12.04
--
-- Description: SQL file containing all statements to populate the database

-- luca: dirottato in file per popolare il databse con entry generiche
-- carico anche se non funziona, (bisogna trovare un modo per recuperare gli ID inseriti 
-- con i SERIAL)

--Nicola: Recuperati gli ID inseriti con i SERIAL, aggiunte tutte le tabelle mancanti, 
--da ancora qualche errore sul riempimento delle tabelle di valutazione


-- Inserting Cities
INSERT INTO citta VALUES('Paris', 'France');
INSERT INTO citta VALUES('Copenhagen', 'Denmark');
INSERT INTO citta VALUES('London', 'United Kingdom');
INSERT INTO citta VALUES('Cambridge', 'United Kingdom');
INSERT INTO citta VALUES('Berlin', 'Germany');
INSERT INTO citta VALUES('Padua', 'Italy');
INSERT INTO citta VALUES('Dublin', 'Ireland');
INSERT INTO citta VALUES ('Barcelona', 'Spain');

INSERT INTO linguacitta VALUES('fra','Paris','France');
INSERT INTO linguacitta VALUES('ita','Padua','Italy');
INSERT INTO linguacitta VALUES('deu','Berlin','Germany');
INSERT INTO linguacitta VALUES('eng','London','United Kingdom');
INSERT INTO linguacitta VALUES('eng','Cambridge','United Kingdom');
INSERT INTO linguacitta VALUES('dan','Copenhagen','Denmark');

-- inserting students

INSERT INTO Studente (NomeUtente, Email, DataRegistrazione, Password, Salt, UltimoAccesso, Attivo) VALUES
	('prezzemolino', 'hange@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	('mario.rossi', 'rossima@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	('user', 'hacker@mail.com', CURRENT_DATE, 'password', 'void', NULL, FALSE),
	('JuventinoDOC', 'perdente@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	('VIVA_BEPPE_GRILLO', 'grillino@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE),
	('Pippo', 'pippo@gmail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE);
	

-- Inserting Universities
INSERT INTO Universita VALUES 
	('Universit�degli Studi di Padova','www.unipd.it', 200, FALSE, 'Padua', 'Italy'),
	('Ecole normale supérieure','www.ens.fr', 28, TRUE, 'Paris', 'France'),
	('Imperial College London','www.imperial.ac.uk', 4, FALSE, 'London', 'United Kingdom'),
	('University College London','www.ucl.ac.uk', 4, FALSE, 'London', 'United Kingdom'),
	('University of Copenhagen','www.ku.dk', 45, TRUE, 'Copenhagen', 'Denmark'),
	('Universitat de Barcelona- Main Site','http://www.ub.edu/uri/estudiantsNOUB/nie_en.htm', 4, FALSE, 'Barcelona', 'Spain'),
	('Universit� Pierre et Marie Curie, Paris 6- Main Site','http://www.upmc.fr/en/', 45, TRUE, 'Paris', 'France'),
	('Dublin Institute of Technology- Main Site','http://www.dit.ie/', 45, TRUE, 'Dublin', 'Ireland'),
	('University of Cambridge','www.cam.ac.uk', 1, TRUE, 'Cambridge', 'United Kingdom');

-- Inserting FLows Resp.
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
    'Universit�degli Studi di Padova'),
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

	
--Inserting Coordinator
INSERT INTO Coordinatore (NomeUtente, Email, DataRegistrazione, Password, Salt, UltimoAccesso, Attivo, NomeUniversita) VALUES
	('ErasmusCoordinator', 'EACoordinator@mail.com', CURRENT_DATE, 'password', 'void', NULL, TRUE, 'Universit�degli Studi di Padova');

--Inserting Flows
INSERT INTO Flusso (Id, Destinazione, RespFlusso, PostiDisponibili, Attivo, DataUltimaModifica, Durata, Dettagli) VALUES 
	('FRA001', 'Ecole normale supérieure', 'pilu', 10, TRUE, CURRENT_DATE, 5, NULL),
	('ENG57', 'Imperial College London', 'pilu', 1, TRUE, CURRENT_DATE, 9, 'Highly Exclusive'),
	('COP001', 'University of Copenhagen', 'pilu', 3, TRUE, CURRENT_DATE, 6, NULL),
	('E-BARCELO01', 'Universitat de Barcelona- Main Site','erick.burn', 3, TRUE, CURRENT_DATE, 5, NULL),
	('F-PARIS006','Universit� Pierre et Marie Curie, Paris 6- Main Site', 'georgua.en', 2, TRUE, CURRENT_DATE, 10, NULL),
	('IRL-DUBLIN27', 'Dublin Institute of Technology- Main Site', 'erick.burn', 4, TRUE, CURRENT_DATE, 12, NULL);


	
	
-- Inserting degree courses
INSERT INTO CorsoDiLaurea (Id, Nome, NomeUniversita, Livello) VALUES
	(DEFAULT, 'Ingegneria Informatica', 'Universit�degli Studi di Padova', 'UNDERGRADUATE');
INSERT INTO Specializzazione (NomeArea, IdCorso) VALUES
	('Software and applications development and analysis', CURRVAL('CorsoDiLaurea_id_seq')),
	('Database and network design and administration', CURRVAL('CorsoDiLaurea_id_seq'));
INSERT INTO Iscrizione (IdCorso, NomeUtenteStudente, AnnoInizio, AnnoFine) VALUES
	(CURRVAL('CorsoDiLaurea_id_seq'),'prezzemolino', '2012-09-01', '2015-06-30'),
	(CURRVAL('CorsoDiLaurea_id_seq'),'mario.rossi', '2012-08-01', '2015-06-12');
INSERT INTO Origine (IdFlusso, IdCorso) VALUES
	('FRA001', CURRVAL('CorsoDiLaurea_id_seq')),
	('IRL-DUBLIN27', CURRVAL('CorsoDiLaurea_id_seq')),
	('COP001', CURRVAL('CorsoDiLaurea_id_seq'));

--Inserting Partecipations	
INSERT INTO Partecipazione (NomeUtenteStudente, IdFlusso, Inizio, Fine) VALUES 
	('mario.rossi', 'E-BARCELO01', '2012-09-01', '2013-01-30'),
	('prezzemolino', 'E-BARCELO01','2011-01-07', '2011-07-10'),
	('user','IRL-DUBLIN27', '2013-02-03', '2013-09-14'),
	('JuventinoDOC','FRA001', '2013-01-15', '2013-06-22');
	

INSERT INTO CorsoDiLaurea (Id, Nome, NomeUniversita, Livello) VALUES
	(DEFAULT, 'Ingegneria delle Telecomunicazioni', 'Universit�degli Studi di Padova', 'GRADUATE');
INSERT INTO Specializzazione (NomeArea, IdCorso) VALUES
	('Software and applications development and analysis', CURRVAL('CorsoDiLaurea_id_seq')),
	('Database and network design and administration', CURRVAL('CorsoDiLaurea_id_seq'));
INSERT INTO Iscrizione (IdCorso, NomeUtenteStudente, AnnoInizio, AnnoFine) VALUES
	(CURRVAL('CorsoDiLaurea_id_seq'),'Pippo', '2014-09-01', '2016-06-30');
INSERT INTO Origine (IdFlusso, IdCorso) VALUES
	('FRA001', CURRVAL('CorsoDiLaurea_id_seq'));
	
INSERT INTO CorsoDiLaurea (Id, Nome, NomeUniversita, Livello) VALUES
	(DEFAULT, 'Giurisprudenza', 'Universit�degli Studi di Padova', 'UNIQUE');
INSERT INTO Specializzazione (NomeArea, IdCorso) VALUES
	('Law', CURRVAL('CorsoDiLaurea_id_seq'));
INSERT INTO Iscrizione (IdCorso, NomeUtenteStudente, AnnoInizio, AnnoFine) VALUES
	(CURRVAL('CorsoDiLaurea_id_seq'),'JuventinoDOC', '2014-06-01', '2016-06-30');
INSERT INTO Origine (IdFlusso, IdCorso) VALUES
	('E-BARCELO01', CURRVAL('CorsoDiLaurea_id_seq')),
	('ENG57', CURRVAL('CorsoDiLaurea_id_seq'));

	
INSERT INTO CorsoDiLaurea (Id, Nome, NomeUniversita, Livello) VALUES
	(DEFAULT, 'Medicina Generale', 'Universit�degli Studi di Padova', 'UNIQUE');
INSERT INTO Specializzazione (NomeArea, IdCorso) VALUES
	('Medicine', CURRVAL('CorsoDiLaurea_id_seq'));
INSERT INTO Iscrizione (IdCorso, NomeUtenteStudente, AnnoInizio, AnnoFine) VALUES
	(CURRVAL('CorsoDiLaurea_id_seq'),'VIVA_BEPPE_GRILLO', '2014-09-01', '2016-06-30');
INSERT INTO Origine (IdFlusso, IdCorso) VALUES
	('COP001', CURRVAL('CorsoDiLaurea_id_seq'));


-- Inserting Courses
INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Analisi 1', 12, 'Universitat de Barcelona- Main Site', 1, 'NOT VERIFIED' , 1, 'Mathematics', 'ita');
INSERT INTO Riconoscimento (IdFlusso, IdInsegnamento) VALUES
	('E-BARCELO01',CURRVAL('Insegnamento_id_seq'));
INSERT INTO ArgomentoTesi(Id, Nome, NomeUniversita, Triennale, Magistrale, Stato) VALUES
	(DEFAULT, 'Counting up to ten', 'Universitat de Barcelona- Main Site', TRUE, TRUE, 'NOT VERIFIED');
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Luca', 'Pizzul');
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(CURRVAL('Insegnamento_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Estensione (IdArgomentoTesi, Area) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'Mathematics');
INSERT INTO LinguaTesi(IdArgomentoTesi, SiglaLingua) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'eng');
INSERT INTO ValutazioneInsegnamento VALUES
('prezzemolino', CURRVAL('Insegnamento_id_seq'),3,4,3,4,CURRENT_DATE, NULL);
INSERT INTO ValutazioneTesi VALUES 
('mario.rossi', CURRVAL('ArgomentoTesi_id_seq'),4,3,2,1,CURRENT_DATE, NULL);


INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Chimica', 6, 'Universit�degli Studi di Padova', 2, 'NOT VERIFIED' , 2, 'Chemistry', 'ita');
INSERT INTO Riconoscimento (IdFlusso, IdInsegnamento) VALUES
	('COP001',CURRVAL('Insegnamento_id_seq'));
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Hanges', 'Stranges');
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(CURRVAL('Insegnamento_id_seq'),CURRVAL('Professore_id_seq'));
--Questo professore gestisce la stessa tesi del primo
INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'),CURRVAL('Professore_id_seq'));
	
INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Network Security', 6, 'Imperial College London', 2, 'NOT VERIFIED' , 2, 'Computer use', 'eng');
INSERT INTO Riconoscimento (IdFlusso, IdInsegnamento) VALUES
	('IRL-DUBLIN27',CURRVAL('Insegnamento_id_seq'));
INSERT INTO ArgomentoTesi(Id, Nome, NomeUniversita, Triennale, Magistrale, Stato) VALUES
	(DEFAULT, 'Counting up to a hundred', 'Imperial College London', FALSE, TRUE, 'VERIFIED');
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Paula', 'Spark');
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(CURRVAL('Insegnamento_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Estensione (IdArgomentoTesi, Area) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'Mathematics');
INSERT INTO LinguaTesi(IdArgomentoTesi, SiglaLingua) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'eng');
	
INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Elettronica Digitale', 9, 'University of Copenhagen', 1, 'NOT VERIFIED' , 2, 'Electronics and automation', 'ita');
INSERT INTO Riconoscimento (IdFlusso, IdInsegnamento) VALUES
	('E-BARCELO01',CURRVAL('Insegnamento_id_seq'));
INSERT INTO ArgomentoTesi(Id, Nome, NomeUniversita, Triennale, Magistrale, Stato) VALUES
	(DEFAULT, 'Microchip', 'University of Copenhagen', FALSE, TRUE, 'VERIFIED');
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Ahmed', 'Kamikaze');
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(CURRVAL('Insegnamento_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Estensione (IdArgomentoTesi, Area) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'Electronics and automation');
INSERT INTO LinguaTesi(IdArgomentoTesi, SiglaLingua) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'ita');

INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Biologia Molecolare', 6, 'University of Copenhagen', 1, 'NOT VERIFIED' , 1, 'Biology', 'ita');
INSERT INTO Riconoscimento (IdFlusso, IdInsegnamento) VALUES
	('E-BARCELO01',CURRVAL('Insegnamento_id_seq'));
INSERT INTO ArgomentoTesi(Id, Nome, NomeUniversita, Triennale, Magistrale, Stato) VALUES
	(DEFAULT, 'Laws under Haakon Bluebeard', 'University of Copenhagen', FALSE, TRUE, 'VERIFIED');
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Robb', 'Stark');
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(CURRVAL('Insegnamento_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Estensione (IdArgomentoTesi, Area) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'Law');
INSERT INTO LinguaTesi(IdArgomentoTesi, SiglaLingua) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'dan');

INSERT INTO Insegnamento(Id, Nome, Crediti, NomeUniversita, PeriodoErogazione, Stato, AnnoCorso, NomeArea, NomeLingua) VALUES
	(DEFAULT, 'Calcolus', 9, 'Imperial College London', 1, 'NOT VERIFIED' , 1, 'Mathematics', 'eng');
INSERT INTO Riconoscimento (IdFlusso, IdInsegnamento) VALUES
	('F-PARIS006',CURRVAL('Insegnamento_id_seq'));
INSERT INTO ArgomentoTesi(Id, Nome, NomeUniversita, Triennale, Magistrale, Stato) VALUES
	(DEFAULT, 'Uniformization of Riemann Surfaces', 'Imperial College London', FALSE, TRUE, 'VERIFIED');
INSERT INTO Professore(Id, Nome, Cognome) VALUES
	(DEFAULT, 'Jeoffrey', 'Baratheon');
INSERT INTO Svolgimento (IdInsegnamento, IdProfessore) VALUES
	(CURRVAL('Insegnamento_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Gestione (IdArgomentoTesi, IdProfessore) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'),CURRVAL('Professore_id_seq'));
INSERT INTO Estensione (IdArgomentoTesi, Area) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'Mathematics');
INSERT INTO LinguaTesi(IdArgomentoTesi, SiglaLingua) VALUES
	(CURRVAL('ArgomentoTesi_id_seq'), 'dan');

--Inserting Interests
INSERT INTO Interesse (NomeUtenteStudente, IdFlusso) VALUES
	('prezzemolino', 'FRA001'),
	('prezzemolino', 'COP001'),
	('JuventinoDOC', 'FRA001'),
	('JuventinoDOC', 'ENG57'),
	('user', 'FRA001'),
	('mario.rossi', 'FRA001'),
	('user', 'IRL-DUBLIN27');

--Inserting Documentations
INSERT INTO Documentazione (IdFlusso, NomeCertificato, LivelloCertificato) VALUES
	('FRA001', 'fra', 'A1'),
	('COP001', 'dan', 'B2'),
	('IRL-DUBLIN27', 'eng', 'B1'),
	('ENG57', 'eng', 'C1'),
	('E-BARCELO01', 'spa', 'B1'),
	('F-PARIS006', 'fra', 'B2');
	
--Inserting Cities Evaluations
INSERT INTO ValutazioneCitta VALUES
	('prezzemolino', 'Barcelona','Spain',4,4,3,1,CURRENT_DATE,'Ricca di monumenti'),
	('mario.rossi', 'Barcelona', 'Spain', 2,4,3,1,CURRENT_DATE, NULL),
	('user', 'Dublin', 'Ireland',4,4,4,5,CURRENT_DATE,NULL);

--Inserting Universities Evaluations
INSERT INTO ValutazioneUniversita VALUES
	('JuventinoDOC','Ecole normale supérieure',5,4,3,4,CURRENT_DATE,NULL),
	--('prezzemolino','Universitat de Barcelona- Main Site', 4,3,2,2,CURRENT_DATE,''),
	('user', 'Dublin Institute of Technology- Main Site', 3,4,3,5,CURRENT_DATE,NULL),
	('mario.rossi', 'Universitat de Barcelona- Main Site', 4,4,1,2,CURRENT_DATE, NULL);

--Inserting Flows Evaluations
INSERT INTO ValutazioneFlusso VALUES 
	('mario.rossi', 'E-BARCELO01', 4,5,2,1,CURRENT_DATE, NULL),
	--('prezzemolino', 'E-BARCELO01', 3,4,5,4,CURRENT_DATE, NULL),
	('user', 'IRL-DUBLIN27',4,4,3,2,CURRENT_DATE, NULL),
	('JuventinoDOC', 'FRA001',1,4,2,1,CURRENT_DATE, NULL);
	
	


