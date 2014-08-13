// funzioni per generare dei baloon con dentro informazioni
//
// esempio di utilizzo
// <a href="#" class="linkExam">Analisi matematica 1 (ing.informazione)</a>
//
// erano usate nelle pagine di ricerca (search_X)
// per ora sono state tolte, in linea di massima converrebbe includere il codice in ogni pagina
// visto che non sembra essere troppo lungo e sarebbe dipendente dalla pagina
//
// per usare queste funzioni bisogna includere anche 
// "../js/jquery.balloon.min.js" e "../js/jquery.min.js"


$(function() { 
	$('.linkUni').balloon({ contents: 'Clicca per vedere i dettagli' }); 
});

$(function() { 
	$('.linkExam').balloon({ contents: 'Clicca per vedere i dettagli' }); 
});

$(function() { 
	$('#alloggi').balloon({ contents: 'Presenza di alloggi' }); 
});

// questa non funziona
$(function() { 
	$('.rowUni').balloon({ position: "right", 
		contents: '<img src="../img/balloon-sample-loading.gif" alt="loading..." width="25" height="25" />', 
		url: '/index.html h1' 
	}); 
});