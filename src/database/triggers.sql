-- Author: Mauro Piazza
--
-- Description: File containing all functions for managing database's domains and triggers

-- Checking foreach flow that the departure university is not the same as the arriving one
CREATE FUNCTION CheckFlowOriginAndDestination() RETURNS TRIGGER AS $$
    BEGIN
	    PERFORM C.NomeUniversita, F.Destinazione
	            FROM CorsoDiLaurea AS C, Flusso AS F
	            WHERE NEW.idCorso = C.id AND NEW.idFlusso = F.id AND C.nomeUniversita =  F.destinazione;
	    RAISE NOTICE 'YO mother fucker!';
	    
	    IF FOUND THEN
	       RAISE EXCEPTION 'EA ERROR: Origin and destination universities are equal.' USING ERRCODE = 'EA001'; 
	    END IF;
	    
	    RETURN NEW;
    END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER CheckingFlowUniversities BEFORE UPDATE OR INSERT 
    ON origine
    FOR EACH ROW 
    EXECUTE PROCEDURE CheckFlowOriginAndDestination();
    
    
    