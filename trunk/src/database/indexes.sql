-- Author: Alessandro Beltramin
--
-- Description: File for additional DB's indexes
--   PostgreSQL will use the index in queries when it thinks doing so would 
--   be more efficient than a sequential table scan. After an index 
--   is created, the system has to keep it synchronized with the table. 
--   This adds overhead to data manipulation operations. Therefore 
--   indexes that are seldom or never used in queries should be removed.

-- Indice for Operation 3: Ricerca di un Insegnamento
CREATE INDEX Insegnamento_NomeUniversita_index ON Insegnamento (NomeUniversita);
CREATE INDEX Insegnamento_NomeArea_index ON Insegnamento (NomeArea);

-- Index for Operation 4: Ricerca di un Argomento di Tesi
CREATE INDEX ArgomentoTesi_NomeUniversita_index ON ArgomentoTesi (NomeUniversita);

-- Indexes for other queries: .......Arriving soon......
