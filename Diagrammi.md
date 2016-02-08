# Introduzione #

Qui vengono presentati dei diagrammi di esempio che illustrano l’architettura adottata nelle servlet.

# Esempio di GET #

In questo esempio, si suppone che un browser client voglia visualizzare i dettagli completi di una specifica università: esso non deve far altro che inviare una richiesta HTTP GET all’indirizzo “/university”, specificandone il nome attraverso il parametro “name”. Sarà compito della Servlet gestire la richiesta e visualizzare quanto richiesto.

![http://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgR0VUIC91bml2ZXJzaXR5P25hbWU9VW5pcGQKCm5vdGUgbGVmdCBvZiBVABsJU2VydmxldDogZG9HZXQoLi4pCgAMES0-ACcJYURhdGFiYXNlOiBzZWFyY2gAQApNb2RlbEJ5TmFtZSguLiwgbmFtZQBCCwAxCS0tPisAJw86IG5ldwCBCgsARgUoKQCBLgZyaWdoAIEnDgB6C1NFTEVDVCAqIEZST00AFwsgV0hFUkUgbm9tZT0_AG8WAIFKCkJlYW46IHJlc3VsdFNldACBKQtCZWFuAB8MAIEnCHNldACBBAtCZWFuAIJaBwCBAitWYWx1dGF6aW9uaQCBIxUAgmoKAIEqGAAtFQCBPRAADBkAgUAYTGlzdDwAQhk-AIQUCwCDbQUtLT4tAIQLFACDZhQAg20MAIRwCXJldHVybiAAhS0KAIREBQCDZBgAhScKaWYgKHN1Y2Nlc3MpXG4gc2V0QXR0cmlidXRlcwCBEhMrc2hvd18AhhMKLmpzcDogcmVxLgAtDQCFahgtACoVZm9yd2FyZCB0byBKU1AgcGFnZQoAVRMAhFsTPGpzcDpnZXRQcm9wZXJ0eSAuLi4uIC8AglQLAIR1CACBIxUAURUAFRY8Yzpmb3JFYWNoPi4uPC8ABAoAgRMXAIQ8GgCBHBggAIRIHQCCZBQAh0sPAIMGFVNIT1cgVEhFIFBBR0U&s=default&nonsense=something_that_ends_with.png](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgR0VUIC91bml2ZXJzaXR5P25hbWU9VW5pcGQKCm5vdGUgbGVmdCBvZiBVABsJU2VydmxldDogZG9HZXQoLi4pCgAMES0-ACcJYURhdGFiYXNlOiBzZWFyY2gAQApNb2RlbEJ5TmFtZSguLiwgbmFtZQBCCwAxCS0tPisAJw86IG5ldwCBCgsARgUoKQCBLgZyaWdoAIEnDgB6C1NFTEVDVCAqIEZST00AFwsgV0hFUkUgbm9tZT0_AG8WAIFKCkJlYW46IHJlc3VsdFNldACBKQtCZWFuAB8MAIEnCHNldACBBAtCZWFuAIJaBwCBAitWYWx1dGF6aW9uaQCBIxUAgmoKAIEqGAAtFQCBPRAADBkAgUAYTGlzdDwAQhk-AIQUCwCDbQUtLT4tAIQLFACDZhQAg20MAIRwCXJldHVybiAAhS0KAIREBQCDZBgAhScKaWYgKHN1Y2Nlc3MpXG4gc2V0QXR0cmlidXRlcwCBEhMrc2hvd18AhhMKLmpzcDogcmVxLgAtDQCFahgtACoVZm9yd2FyZCB0byBKU1AgcGFnZQoAVRMAhFsTPGpzcDpnZXRQcm9wZXJ0eSAuLi4uIC8AglQLAIR1CACBIxUAURUAFRY8Yzpmb3JFYWNoPi4uPC8ABAoAgRMXAIQ8GgCBHBggAIRIHQCCZBQAh0sPAIMGFVNIT1cgVEhFIFBBR0U&s=default&nonsense=something_that_ends_with.png)

|Nome|Descrizione|
|:---|:----------|
| UniversityServlet| è la servlet mappata in "/university" e contenuta nel package **servlets**; si occupa di aprire una **sola** connessione al database e richiuderla correttamente |
| UniversitaDatabase| è la classe per le operazioni sul database che riguardano Universita; contiene metodi statici con le varie istruzioni SQL, che vengono eseguite sulla Connection passata come parametro |
| UniversityModel| è il data model contenuto nel package **resources**|
| UniversitaBean| è l'entity bean contenuto nel package **beans**: esso mappa lo schema della relazione Universita nelle rispettive variabili Java: ci **deve** essere corrispondenza biunivoca con i nomi nel database |
| ValutazioneUniversitaBean| è l'entity bean che mappa lo schema della relazione ValutazioneUniversita nelle rispettive variabili Java: ci **deve** essere corrispondenza biunivoca con i nomi nel database |
| show\_university.jsp| è la pagina della **webapp** contenuta in _/jsp_ che si occupa di elencare i dettagli dell'universita' e le valutazioni passate come argomenti |

<a href='Hidden comment: 
https://www.websequencediagrams.com/#

title GET /university?name=Unipd

note left of UniversityServlet: doGet(..)
UniversityServlet->UniversitaDatabase: searchUniversityModelByName(.., name)
UniversitaDatabase-->+UniversityModel: new UniversityModel()
note right of UniversitaDatabase: SELECT * FROM Universita WHERE nome=?
UniversitaDatabase-->UniversitaBean: resultSet
UniversitaBean-->UniversityModel: set UniversitaBean

note right of UniversitaDatabase: SELECT * FROM ValutazioniUniversita WHERE nomeUniversita=?
UniversitaDatabase-->ValutazioniUniversitaBean: resultSet
ValutazioniUniversitaBean-->UniversityModel: set List<ValutazioniUniversitaBean>
UniversityModel-->-UniversitaDatabase:
UniversitaDatabase->+UniversityServlet: return universityModel
note right of UniversityServlet: if (success)\n setAttributes
UniversityModel-->+show_university.jsp: req.setAttributes(..)
UniversityServlet->-show_university.jsp: forward to JSP page
show_university.jsp-->UniversitaBean: <jsp:getProperty .... />
UniversitaBean-->show_university.jsp:
show_university.jsp->show_university.jsp: <c:forEach>..

Unknown end tag for &lt;/forEach&gt;


show_university.jsp-->ValutazioniUniversitaBean:<jsp:getProperty .... />
ValutazioniUniversitaBean-->show_university.jsp:
note right of show_university.jsp: SHOW THE PAGE
'></a>


# Esempio di POST #

In questo esempio, si suppone che l’utente abbia compilato i campi di un FORM (ad es. di una pagina di inserimento precedentemente richiesta) e lo abbia sottomesso attraverso una richiesta HTTP POST all’indirizzo “/university”, specificando un’operazione di “insert”. La servlet gestisce l’operazione, visualizzando al termine la pagina con i dettagli dell’università appena inserita, oppure un messaggio di errore.

![http://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgUE9TVCAvdW5pdmVyc2l0eQoKaW5zZXJ0XwAJCi5qc3AtPlUAGglTZXJ2bGV0OiBTdWJtaXRzAD4GRk9STSAKbm90ZSBsZWZ0IG9mIAAgE2RvUG9zdCguLikAURcAVBVvcGVyYXRpb24gPSAiAIEaBiIAFCxwYXJhbWV0ZXJzIGZpbGxlZCBieSB1c2VyCgCBRREtPisAgWEJYUJlYW46IHVuaSA9IG5ldwCBRwoAFQUoKTsALBQALBMuc2V0TmFtZQCBcwUAECMuLi4ALytMaW5rAIJNBQCCeAVyaWdoAIJmGEJFR0lOIFRSQU5TQUNUSU9OAIFhH0RhdGFiYXNlOiBjcmVhdGUAghAKKGNvbm4sIHVuaQCBRgsAgisFAIM2DAA2C3VuaS5nZXROb21lKAAMJwCBeQ0AVAgtAEIbAIIEBQCBbhkAgUMLSU5TRVJUIElOVE8Ag0kLAIJyBWFsdCBTdWNjZXNzCiAgIAAsEwCFdhVyZXR1cm4AgmAiQ09NTUlUAIRjFFZJRVc6IHNlbmRzIHJlZGlyZWN0IHRvAIcQDD9uYW1lPXh4eHh4eACDXg8AOAZTSE9XUyBUSEVcbk5FVyBVTklWRVJTSVRZCmVsc2UgRXJyb3IAgT0ZAIJQCgCHWAp0aHJvd3MgU1FMRXhjZXB0aW8AgUEjUk9MTEJBQ0sAgUwaZm9yd2FyZHMgdG8gdGhlIEVSUk9SIHBhZ2UAgTcgACQGTUVTU0FHRQplbmQ&s=default&s=default&somethingnosense=.png](http://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgUE9TVCAvdW5pdmVyc2l0eQoKaW5zZXJ0XwAJCi5qc3AtPlUAGglTZXJ2bGV0OiBTdWJtaXRzAD4GRk9STSAKbm90ZSBsZWZ0IG9mIAAgE2RvUG9zdCguLikAURcAVBVvcGVyYXRpb24gPSAiAIEaBiIAFCxwYXJhbWV0ZXJzIGZpbGxlZCBieSB1c2VyCgCBRREtPisAgWEJYUJlYW46IHVuaSA9IG5ldwCBRwoAFQUoKTsALBQALBMuc2V0TmFtZQCBcwUAECMuLi4ALytMaW5rAIJNBQCCeAVyaWdoAIJmGEJFR0lOIFRSQU5TQUNUSU9OAIFhH0RhdGFiYXNlOiBjcmVhdGUAghAKKGNvbm4sIHVuaQCBRgsAgisFAIM2DAA2C3VuaS5nZXROb21lKAAMJwCBeQ0AVAgtAEIbAIIEBQCBbhkAgUMLSU5TRVJUIElOVE8Ag0kLAIJyBWFsdCBTdWNjZXNzCiAgIAAsEwCFdhVyZXR1cm4AgmAiQ09NTUlUAIRjFFZJRVc6IHNlbmRzIHJlZGlyZWN0IHRvAIcQDD9uYW1lPXh4eHh4eACDXg8AOAZTSE9XUyBUSEVcbk5FVyBVTklWRVJTSVRZCmVsc2UgRXJyb3IAgT0ZAIJQCgCHWAp0aHJvd3MgU1FMRXhjZXB0aW8AgUEjUk9MTEJBQ0sAgUwaZm9yd2FyZHMgdG8gdGhlIEVSUk9SIHBhZ2UAgTcgACQGTUVTU0FHRQplbmQ&s=default&s=default&somethingnosense=.png)

|Nome|Descrizione|
|:---|:----------|
| insert\_university.jsp | è la pagina della **webapp** contenuta in _/jsp_ che contiene il FORM di inserimento. L'utente vi accede da /university/insert e, dopo aver compilato i campi necessari, esegue il POST "submit". |
| UniversityServlet| è la servlet mappata in "/university" e contenuta nel package **servlets**; si occupa di aprire una **sola** connessione al database e richiuderla correttamente |
| UniversitaDatabase| è la classe per le operazioni sul database che riguardano Universita; contiene metodi statici con le varie istruzioni SQL, che vengono eseguite sulla Connection passata come parametro |
| UniversitaBean| è l'entity bean contenuto nel package **beans**: esso mappa lo schema della relazione Universita nelle rispettive variabili Java: ci **deve** essere corrispondenza biunivoca con i nomi nel database |
| VIEW | è l'output che viene visualizzato all'utente |


<a href='Hidden comment: 
https://www.websequencediagrams.com/#

title POST /university

insert_university.jsp->UniversityServlet: Submits POST FORM
note left of UniversityServlet: doPost(..)
insert_university.jsp-->UniversityServlet: operation = "insert"
insert_university.jsp-->UniversityServlet: parameters filled by user
UniversityServlet->+UniversitaBean: uni = new UniversitaBean();
UniversityServlet->UniversitaBean: uni.setName(..)
UniversityServlet->UniversitaBean: ...
UniversityServlet->UniversitaBean: uni.setLink(..)
note right of UniversityServlet: BEGIN TRANSACTION
UniversityServlet->+UniversitaDatabase: createUniversita(conn, uni)
UniversitaBean-->UniversitaDatabase: uni.getNome()
UniversitaBean-->UniversitaDatabase: ...
UniversitaBean-->-UniversitaDatabase: uni.getLink()
note right of UniversitaDatabase: INSERT INTO Universita ...
alt Success
UniversitaDatabase->UniversityServlet: return
note right of UniversityServlet: COMMIT
UniversityServlet->VIEW: sends redirect to /university?name=xxxxxx
note right of VIEW: SHOWS THE\nNEW UNIVERSITY
else Error
UniversitaDatabase->-UniversityServlet: throws SQLException
note right of UniversityServlet: ROLLBACK
UniversityServlet->VIEW: forwards to the ERROR page
note right of VIEW: SHOWS THE\nERROR MESSAGE
end

'></a>