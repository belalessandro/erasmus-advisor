$(function() {
		
		$( "#accordion" ).accordion();
			
		var availableTags = [
"Università degli studi di Padova","Aberdeen University",
"Barcellona"
		];
		$( "#autocomplete" ).autocomplete({
			source: availableTags
		});
		
		var availableTags = [
"Ingegneria Informatica", "Ingegneria delle Telecomunicazioni","Farmacia","Psicologia del lavoro","Medicina","Giurisprudenza"
		];
		$( "#autocomplete2" ).autocomplete({
			source: availableTags
		});
		

		
		$( "#button" ).button();
		$( "#radioset" ).buttonset();
		

		
		$( "#tabs" ).tabs();
		

		
		$( "#dialog" ).dialog({
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "Ok",
					click: function() {
						$( this ).dialog( "close" );
					}
				},
				{
					text: "Cancel",
					click: function() {
						$( this ).dialog( "close" );
					}
				}
			]
		});

		// Link to open the dialog
		$( "#dialog-link" ).click(function( event ) {
			$( "#dialog" ).dialog( "open" );
			event.preventDefault();
		});
		

		
		$( "#datepicker" ).datepicker({
			inline: true
		});
		$( "#datepicker2" ).datepicker({
			inline: true
		});

		
		$( "#slider" ).slider({
			range: true,
			values: [ 17, 67 ]
		});
		

		
		$( "#progressbar" ).progressbar({
			value: 20
		});
		

		// Hover states on the static widgets
		$( "#dialog-link, #icons li" ).hover(
			function() {
				$( this ).addClass( "ui-state-hover" );
			},
			function() {
				$( this ).removeClass( "ui-state-hover" );
			}
		);
		
	});
