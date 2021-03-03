 var registroSeleccionado = "";
 $(document).ready(function () {
 	console.log(datosSession);
 	document.getElementById("txtNombreUsuario").innerHTML = datosSession.session.txtNickNameUser;
 	consultaListaSolicitudes();
	seleccionaFila();
});

function consultaListaSolicitudes() {
	console.log("llenar tabla solicitud Admin");
	var request = {
		"rolUsr" : datosSession.session.txtRolUsr
	}
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(request),
		url : context + "/buscaSolTransporteAdmin",
		type : "post",
		beforeSend : function() {
			console.log("Antes de mandar los datos");
		},
		success : function (data) {
			console.log("succes peticion");
			llenarTblGerente(data.solicitud);
		},
		error : function (data) {
			$("#exampleModal").modal("hide"); 
			console.log("Error al invocar el servicio");
			alert("Error");
		}
	});
}

function seleccionaFila(){
	$('#tblSolicitudTransporteGerente').on('click', 'tr', function () {
		var rowDataSeleccionado = $('#tblSolicitudTransporteGerente').DataTable().row(this).data();
		if($('#tblSolicitudTransporteGerente').DataTable().row(this).data() != null){
		if(registroSeleccionado != ""){
			if(registroSeleccionado.idSolicitudTransporte == rowDataSeleccionado.idSolicitudTransporte){
				registroSeleccionado = "";
			} else {
				registroSeleccionado = rowDataSeleccionado;
			}
		} else {
			registroSeleccionado = rowDataSeleccionado;
		}
		} else {
			registroSeleccionado = "";
		}
 	});
}

function llenarTblGerente(datos){
	$('#tblSolicitudTransporteGerente').DataTable({
        searching: false,
        data: datos,
        select: true,
        "columns": [
            {"data": "nomSolicitante"},
            {"data": "folioSolicitud"},
            {"data": "areaAdscripcion"},
            {"data": "dateSolicitud"},
            {"data": "estatus"}
        ]
    });
}

function cerrarSolicitud(){
	$('#exampleModal').modal('hide');
}

function verSolicitudGerente() {
	if (registroSeleccionado!= "") {
		var idSolicitud = registroSeleccionado.idSolicitudTransporte;
		var tipoUsuarioRequest = registroSeleccionado.idSolicitudTransporte;
		var datos = {
			idSolicitudTransporte : idSolicitud,
			tipoUsuario : datosSession.session.txtNickNameUser
		};
			$.ajax({
			contentType: 'application/json; charset=UTF-8',
        	data: JSON.stringify(datos),
			url : context + "/buscarSolicitud",
			type : "post",
			beforeSend : function() {
					console.log("Antes de VER solicitud GERENTE");
				},
			success : function (data) {
				console.log("Exito VER solicitud GERENTE");
				if(data.status == 0){
					llenarSolicitudGerente(data.solicitud);
					deshabilitarCamposGerente();
					$("#botonEditar").hide();
					$("#botonEnviar").hide();
					$('#exampleModal').modal('show');
				} else {
					bootbox.alert("Error al consultar la solicitud");
				}
			},
			error : function (data) {
				bootbox.alert("Error al invocar el servicio");
			}
		});
	} else {
		bootbox.alert("Seleccione un registro!");
	}
}

function llenarSolicitudGerente(data){
	$("#txtNombre").val(data[0].nomSolicitante);
	$("#txtFolio").val(data[0].folioSolicitud);
	$("#txtAreaAdscripcion").val(data[0].areaAdscripcion);
	$("#dateSolicitud").val(data[0].dateSolicitud);

	$("#txtOrigen").val(data[0].txtOrigen);
	$("#txtDestino").val(data[0].txtDestino);
	$("#txtNPasajeros").val(data[0].txtNPasajeros);
	$("#dateSalida").val(data[0].dateSalida);
	$("#textAreaObservaciones").val(data[0].textAreaObservaciones);

	$("#txtOrigenRegreso").val(data[0].txtOrigenRegreso);
	$("#txtDestinoRegreso").val(data[0].txtDestinoRegreso);
	$("#txtNPasajerosRegreso").val(data[0].txtNPasajerosRegreso);
	$("#dateSalidaRegreso").val(data[0].dateSalidaRegreso);
	$("#textAreaObservacionesRegreso").val(data[0].textAreaObservacionesRegreso);

	$("#textDescripcionViaje").val(data[0].textDescripcionViaje);

	if(data[0].txtActividad == 1){
		$("#tipoActividad1").prop( "checked", true);
	} else if (data[0].txtActividad == 2){
		$("#tipoActividad2").prop( "checked", true);
	}
	if ( data[0].checkHoraIda == 1 ) {
		$("#horaSalida1").prop( "checked", true);
	} else if ( data[0].checkHoraIda == 2 ) {
		$("#horaSalida2").prop( "checked", true);
	}
	if ( data[0].checkHoraRegreso == 1 ) {
		$("#horaSalidaRegreso1").prop( "checked", true);
	} else if ( data[0].checkHoraRegreso == 2 ) {
		$("#horaSalidaRegreso2").prop( "checked", true);
	}

	$("#txtVehiculoAsignado").val(data[0].vehiculoAsignado);
	$("#txtPlacas").val(data[0].placas);
	$("#txtNomOperador").val(data[0].nomOperador);

	$("#txtKilometros").val(data[0].kilometrosSalida);
	$("#txtCombustible").val(data[0].combustibleSalida);
	$("#txtHoraSalida").val(data[0].horaSalida);
	$("#textAreaObservacionesVehiculo").val(data[0].observacionSalida);
	
	$("#txtKilometrosEntrada").val(data[0].kilometrosEntrada);
	$("#txtCombustibleEntrada").val(data[0].combustibleEntrada);
	$("#txtHoraSalidaEntrada").val(data[0].horaEntrada);
	$("#textAreaObservacionesVehiculoEntrada").val(data[0].observacionesEntrada);
	
}

function deshabilitarCamposGerente(){
	$("#txtNombre").prop("disabled", true );
	$("#txtFolio").prop("disabled", true );
	$("#txtAreaAdscripcion").prop("disabled", true );
	$("#dateSolicitud").prop("disabled", true );

	$("#txtOrigen").prop("disabled", true );
	$("#txtDestino").prop("disabled", true );
	$("#txtNPasajeros").prop("disabled", true );
	$("#dateSalida").prop("disabled", true );
	$("#textAreaObservaciones").prop("disabled", true );

	$("#txtOrigenRegreso").prop("disabled", true );
	$("#txtDestinoRegreso").prop("disabled", true );
	$("#txtNPasajerosRegreso").prop("disabled", true );
	$("#dateSalidaRegreso").prop("disabled", true );
	$("#textAreaObservacionesRegreso").prop("disabled", true );

	$("#textDescripcionViaje").prop("disabled", true );

	$("#tipoActividad1").prop("disabled", true );
	$("#tipoActividad2").prop("disabled", true );
	$("#horaSalida1").prop("disabled", true );
	$("#horaSalida2").prop("disabled", true );
	$("#horaSalidaRegreso1").prop("disabled", true );
	$("#horaSalidaRegreso2").prop("disabled", true );

	/*************		datos solicitud transporte admin ************/
	$("#txtVehiculoAsignado").prop("disabled", true );
	$("#txtPlacas").prop("disabled", true );
	$("#txtNomOperador").prop("disabled", true );

	$("#txtKilometros").prop("disabled", true );
	$("#txtCombustible").prop("disabled", true );
	$("#txtHoraSalida").prop("disabled", true );
	$("#textAreaObservacionesVehiculo").prop("disabled", true );

	$("#txtKilometrosEntrada").prop("disabled", true );
	$("#txtCombustibleEntrada").prop("disabled", true );
	$("#txtHoraSalidaEntrada").prop("disabled", true );
	$("#textAreaObservacionesVehiculoEntrada").prop("disabled", true );
}

/**********************************************************************************************************/
							/**ACEPTAR solicituid de transporte gerente*/
/**********************************************************************************************************/

function aceptarSolicitudGerente(){
	if (registroSeleccionado != "") {
		if (registroSeleccionado.estatus == 'Aprobada') {
			bootbox.confirm({
			title: 'Aceptar solicitud',
			message: '<p>¿Estas seguro de aceptar la solicitud?</p>',
			buttons: {
				cancel: {
 					label: 'No',
            		className: 'btn-danger'
				},
				confirm: {
					label: 'Si',
            		className: 'btn-success'
				}
			},
			callback: function(result){
				console.log("Aceptar solicitud : " + result);
				if(result){
					console.log("True: " + result);
					aproveSoliGerente();
				}
			}
			});
		} else {
			bootbox.alert("Estatus de la solicitud no permitido!");	
		}
	} else {
		bootbox.alert("Seleccione un registro!");
	}
}

function aproveSoliGerente(){
	var request = {
		idSolicitudTransporte : registroSeleccionado.idSolicitudTransporte,
		"rolUsr" : datosSession.session.txtRolUsr
	}
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(request),
		url : context + "/aprovarSolTransporteAdmin",
		type : "post",
		beforeSend : function() {
			console.log("Antes de mandar los datos");
		},
		success : function (data) {
			console.log("Exito al aprovar solicitud");
		bootbox.alert("Exito al guadar la solicitud!", function(){ 
    		console.log('function despues de darle click!');
    		location.reload();
		});
		},
	error : function (data) { 
		console.log("Error al aprovar solicitud");
		alert("Error");
	}
	});
}

/**********************************************************************************************************/
							/**DENEGAR solicituid de transporte gerente*/
/**********************************************************************************************************/

function denegarSolicitudTransporte(){
	if (registroSeleccionado != "") {
		if (registroSeleccionado.estatus == 'Aprobada') {
			bootbox.confirm({
			title: 'Aceptar solicitud',
			message: '<p>¿Estas seguro de denegar la solicitud?</p>',
			buttons: {
				cancel: {
 					label: 'No',
            		className: 'btn-danger'
				},
				confirm: {
					label: 'Si',
            		className: 'btn-success'
				}
			},
			callback: function(result){
				console.log("Denegar solicitud : " + result);
				if(result){
					console.log("True: " + result);
					cancelSoliGerente();
				}
			}
			});
		} else {
			bootbox.alert("Estatus de la solicitud no permitido!");	
		}
	} else {
		bootbox.alert("Seleccione un registro!");
	}
}

function cancelSoliGerente(){
	var request = {
		idSolicitudTransporte : registroSeleccionado.idSolicitudTransporte,
		"rolUsr" : datosSession.session.txtRolUsr
	}
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(request),
		url : context + "/rechazarSolTransporteAdmin",
		type : "post",
		beforeSend : function() {
			console.log("Antes de mandar los datos");
		},
		success : function (data) {
			console.log("Exito al aprovar solicitud");
		bootbox.alert("Exito al guadar la solicitud!", function(){ 
    		console.log('function despues de darle click!');
    		location.reload();
		});
		},
	error : function (data) { 
		console.log("Error al aprovar solicitud");
		alert("Error");
	}
	});	
}