 var json = [
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    }, 
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    }, 
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    },
    {
        idSolicitudTransporte: "1",
        nomSolicitante: "solero",
        folioSolicitud: "santa rosalia",
        areaAdscripcion: "5564565454",
        dateSolicitud: "chavito del ofirtz"
    }
];
var dataSeleccionado = "";
//window.onload = function() {
$(document).ready(function () {
	console.log(datosSession);
 	document.getElementById("txtNombreUsuario").innerHTML = datosSession.session.txtNickNameUser;
 	servicioSolicitudTransporte();//servicio consulta solicitudes
	seleccionFila(); 
});	
// }

function seleccionFila(){

	$('#tblSolicitudTransporte').on('click', 'tr', function () {
	var rowDataSeleccionado = $('#tblSolicitudTransporte').DataTable().row(this).data();
	if($('#tblSolicitudTransporte').DataTable().row(this).data() != null){
		if(dataSeleccionado != ""){
			if(dataSeleccionado.idSolicitudTransporte == rowDataSeleccionado.idSolicitudTransporte){
				dataSeleccionado = "";
			} else {
				dataSeleccionado = rowDataSeleccionado;
			}
		} else {
			dataSeleccionado = rowDataSeleccionado;
		}
	} else {
		dataSeleccionado = "";
	}
 	});
}

function servicioSolicitudTransporte() {
	console.log("llenar tabla solicitud Admin");
	var request = {
		"rolUsr" : datosSession.session.txtRolUsr
	}
	console.log("request : " + request);
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
			cargarTablaAdmin(data.solicitud);
		},
		error : function (data) {
			$("#exampleModal").modal("hide"); 
			console.log("Error al invocar el servicio");
			alert("Error");
		}
	});
}

function cargarTablaAdmin(datos) {
	$('#tblSolicitudTransporte').DataTable(
        {
            searching: false,
            data: datos,
            select: true,
            "columns": [
                //{"data": "idSolicitudTransporte"},
                {"data": "nomSolicitante"},
                {"data": "folioSolicitud"},
                {"data": "areaAdscripcion"},
                {"data": "dateSolicitud"},
                {"data": "estatus"}
            ]
        });
}

function ocultarDivsDeSolicitud () {
	$("#datosSolicitante").removeClass("show");
	$("#datosServTransporte").removeClass("show");
	$("#controlFlotaVehicular").removeClass("show");
}

/*********************************************************************************************************************
***********************                               ****************************************************************
***********************		VER solicitude transporte ****************************************************************
***********************                               ****************************************************************
**********************************************************************************************************************
*/
function verSolicitud() {
	if (dataSeleccionado!= "") {
		ocultarDivsDeSolicitud ();
		var idSolicitud = dataSeleccionado.idSolicitudTransporte;
		var datos = {
			idSolicitudTransporte : idSolicitud,
			tipoUsuario : datosSession.session.txtNickNameUser
		}
		$.ajax({
			contentType: 'application/json; charset=UTF-8',
        	data: JSON.stringify(datos),
			url : context + "/buscarSolicitud",
			type : "post",
			beforeSend : function() {
					console.log("Antes de VER solicitud ADMIN");
				},
			success : function (data) {
				console.log("Exito VER solicitud ADMIN");
				if(data.status == 0){
					llenarSolicitudAdmin(data);
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

function seleccionarPlaca(value){
	var datos = {placaVehiculo : value}
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(datos),
		url : context + "/buscaPlaca",
		type : "post",
		success : function (data) {
			$("#txtPlacas").val(data.placaVehiculo);
			$("#txtPlacas").prop("disabled", true );
		},
		error : function (data) {
			$("#exampleModal").modal("hide");
			alert("Error al invocar el servicio");
		}
	});
}

function llenaVehiculos(data, listaHtml){
	if(listaHtml.length == 1){
	//if(listaHtml == null){
		for(var od in data){
			var option = document.createElement("option");
			option.value = data[od].codigoVehiculo;
    		option.text = data[od].txtMarca + " | " + data[od].txtNombre;
    		listaHtml.appendChild(option);
		}
	} else {
		listaHtml.selectedIndex = "0";
	}
}

function llenaOperadores(data, listaHtml){
	if(listaHtml.length == 1){
		for(var od in data){
			var option = document.createElement("option");
			option.value = data[od].codigoOperador;
    		option.text = data[od].txtNombre + " | " + data[od].txtApellidos;
    		listaHtml.appendChild(option);
		}
	} else {
		listaHtml.selectedIndex = "0";
	}
}

function llenarSolicitudAdmin(data) {
	llenaVehiculos(data.vehiculos, document.getElementById("listaVehiculos"));
	llenaOperadores(data.operadores, document.getElementById("listaOperadores"));

	$("#txtNombre").val(data.solicitud[0].nomSolicitante);
	$("#txtFolio").val(data.solicitud[0].folioSolicitud);
	$("#txtAreaAdscripcion").val(data.solicitud[0].areaAdscripcion);
	$("#dateSolicitud").val(data.solicitud[0].dateSolicitud);

	$("#txtOrigen").val(data.solicitud[0].txtOrigen);
	$("#txtDestino").val(data.solicitud[0].txtDestino);
	$("#txtNPasajeros").val(data.solicitud[0].txtNPasajeros);
	$("#dateSalida").val(data.solicitud[0].dateSalida);
	$("#textAreaObservaciones").val(data.solicitud[0].textAreaObservaciones);


	$("#txtOrigenRegreso").val(data.solicitud[0].txtOrigenRegreso);
	$("#txtDestinoRegreso").val(data.solicitud[0].txtDestinoRegreso);
	$("#txtNPasajerosRegreso").val(data.solicitud[0].txtNPasajerosRegreso);
	$("#dateSalidaRegreso").val(data.solicitud[0].dateSalidaRegreso);
	$("#textAreaObservacionesRegreso").val(data.solicitud[0].textAreaObservacionesRegreso);

	$("#textDescripcionViaje").val(data.solicitud[0].textDescripcionViaje);

	if(data.solicitud[0].txtActividad == 1){
		$("#tipoActividad1").prop( "checked", true);
	} else if (data.solicitud[0].txtActividad == 2){
		$("#tipoActividad2").prop( "checked", true);
	}
	if ( data.solicitud[0].checkHoraIda == 1 ) {
		$("#horaSalida1").prop( "checked", true);
	} else if ( data.solicitud[0].checkHoraIda == 2 ) {
		$("#horaSalida2").prop( "checked", true);
	}
	if ( data.solicitud[0].checkHoraRegreso == 1 ) {
		$("#horaSalidaRegreso1").prop( "checked", true);
	} else if ( data.solicitud[0].checkHoraRegreso == 2 ) {
		$("#horaSalidaRegreso2").prop( "checked", true);
	}

	/************* Deshabilitar campos gral ************/

		$("#txtKilometros").prop("disabled", true );
		$("#txtCombustible").prop("disabled", true );
		$("#txtHoraSalida").prop("disabled", true );
		$("#textAreaObservacionesVehiculo").prop("disabled", true );


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

	if(data.solicitud[0].estatus != "Creada"){
		console.log("solicitud aceptada, denegada, guardada");

		document.getElementById("listaVehiculos").style.display="none";
		document.getElementById("listaOperadores").style.display="none";
		document.getElementById("txtVehiculoAsignado").value = data.solicitud[0].vehiculoAsignado;
		document.getElementById("txtVehiculoAsignado").style.display="block";
		$("#txtVehiculoAsignado").prop("disabled", true );
		document.getElementById("txtOperadorAsignado").value = data.solicitud[0].nomOperador;
		document.getElementById("txtOperadorAsignado").style.display="block";
		$("#txtOperadorAsignado").prop("disabled", true );

		$("#idConstrolVehicular").val(data.solicitud[0].idControlVehicular);
		$("#txtPlacas").prop("disabled", true );
		$("#txtPlacas").val(data.solicitud[0].placas);

		$("#txtKilometros").val(data.solicitud[0].kilometrosSalida);
		$("#txtCombustible").val(data.solicitud[0].combustibleSalida);
		$("#txtHoraSalida").val(data.solicitud[0].horaSalida);
		$("#textAreaObservacionesVehiculo").val(data.solicitud[0].observacionSalida);

		if(data.solicitud[0].estatus == "Aceptada"){
			$("#txtKilometrosEntrada").prop("disabled", false );
			$("#txtCombustibleEntrada").prop("disabled", false );
			$("#txtHoraSalidaEntrada").prop("disabled", false );
			$("#textAreaObservacionesVehiculoEntrada").prop("disabled", false );
			$("#botonEditar").hide();
			$("#botonEnviar").show();
		} else if (data.solicitud[0].estatus == "Concluida") {

			$("#txtKilometros").prop("disabled", true );
			$("#txtCombustible").prop("disabled", true );
			$("#txtHoraSalida").prop("disabled", true );
			$("#textAreaObservacionesVehiculo").prop("disabled", true );

			$("#txtKilometrosEntrada").val(data.solicitud[0].kilometrosEntrada);
			$("#txtCombustibleEntrada").val(data.solicitud[0].combustibleEntrada);
			$("#txtHoraSalidaEntrada").val(data.solicitud[0].horaEntrada);
			$("#textAreaObservacionesVehiculoEntrada").val(data.solicitud[0].observacionesEntrada);
			$("#txtKilometrosEntrada").prop("disabled", true );
			$("#txtCombustibleEntrada").prop("disabled", true );
			$("#txtHoraSalidaEntrada").prop("disabled", true );
			$("#textAreaObservacionesVehiculoEntrada").prop("disabled", true );

			$("#botonEditar").hide();
			$("#botonEnviar").hide();
		}else {
			$("#txtKilometrosEntrada").prop("disabled", true );
			$("#txtCombustibleEntrada").prop("disabled", true );
			$("#txtHoraSalidaEntrada").prop("disabled", true );
			$("#textAreaObservacionesVehiculoEntrada").prop("disabled", true );
			$("#botonEditar").hide();
			$("#botonEnviar").hide();
		}
		$('#exampleModal').modal('show');
	} else {
		habilitarDeshabilitarCamposSolAdmin();
		$("#botonEditar").hide();
		$("#botonEnviar").show();
		$('#exampleModal').modal('show');
	}
}

function habilitarDeshabilitarCamposSolAdmin(){
	/************* solicitud creada ************/
	$("#txtVehiculoAsignado").prop("disabled", false );
	$("#txtPlacas").prop("disabled", false );
	$("#txtNomOperador").prop("disabled", false );
	/************* Habilita Salida ************/
	$("#txtKilometros").prop("disabled", false );
	$("#txtCombustible").prop("disabled", false );
	$("#txtHoraSalida").prop("disabled", false );
	$("#textAreaObservacionesVehiculo").prop("disabled", false );
	/************* Deshabilita Entrada ************/
	$("#txtKilometrosEntrada").prop("disabled", true );
	$("#txtCombustibleEntrada").prop("disabled", true );
	$("#txtHoraSalidaEntrada").prop("disabled", true );
	$("#textAreaObservacionesVehiculoEntrada").prop("disabled", true );
}

function cerrarSolicitud(){

	$("#txtVehiculoAsignado").val('');
	$("#txtPlacas").val('');
	$("#txtNomOperador").val('');

	$("#txtKilometros").val('');
	$("#txtCombustible").val('');
	$("#txtHoraSalida").val('');
	$("#textAreaObservacionesVehiculo").val('');

	$('#exampleModal').modal('hide');
}

/*********************************************************************************************************************
***********************                               ****************************************************************
***********************		GUARDAR solicitud transporte ****************************************************************
***********************                               ****************************************************************
**********************************************************************************************************************/

function guardarSolicitudTransporte(){
	console.log("init guardar solicitud admin");
	if (!validaCampos()) {
		var request = crearRequest();
		$.ajax({
			contentType: 'application/json; charset=UTF-8',
        	data: JSON.stringify(request),
        	//context+/solicitudTransporte
			url : context + "/guardaSolicitudAdmin",
			type : "post",
			beforeSend : function() {
				console.log("Antes de mandar los datos");
			},
			success : function (data) {
			console.log("Exito al guardar solicitud");
			$("#exampleModal").modal("hide"); 
			bootbox.alert("Exito al guadar la solicitud!", function(){ 
    			console.log('function despues de darle click!');
    			location.reload();
			});
		},
		error : function (data) {
			$("#exampleModal").modal("hide"); 
			console.log("Error al guardar solicitud");
			alert("Error");
		}
		});
	} else {
		bootbox.alert("Verifique sus datos!");
	}
}


function validaCampos(){
	var camposVacios;
	camposVacios = $("#listaVehiculos").val().length <= 0 ? true : false;
	camposVacios = $("#txtPlacas").val().length <= 0 ? true : false;
	camposVacios = $("#listaOperadores").val().length <= 0 ? true : false;
	if(dataSeleccionado.estatus == "Creada") {
		camposVacios = $("#txtKilometros").val().length <= 0 ? true : false;
		camposVacios = $("#txtCombustible").val().length <= 0 ? true : false;
		camposVacios = $("#txtHoraSalida").val().length <= 0 ? true : false;
	} else if (dataSeleccionado.estatus == "Aceptada") {
		camposVacios = $("#txtKilometrosEntrada").val().length <= 0 ? true : false;
		camposVacios = $("#txtCombustibleEntrada").val().length <= 0 ? true : false;
		camposVacios = $("#txtHoraSalidaEntrada").val().length <= 0 ? true : false;
	}
	return camposVacios;
}

function crearRequest() {
	var requestSolicitudTransporte = "";
	var idSolicitud = (dataSeleccionado!= "" ? dataSeleccionado.idSolicitudTransporte : "");

	if(dataSeleccionado.estatus == "Creada") {
		requestSolicitudTransporte = {
			"idSolicitudTransporte" : idSolicitud,
			"vehiculoAsignado" : $("#listaVehiculos").val(),
			"placas" : $("#txtPlacas").val(),
			"nomOperador" : $("#listaOperadores").val(),
			"kilometrosSalida" : $("#txtKilometros").val(),
			"combustibleSalida" : $("#txtCombustible").val(),
			"horaSalida" : $("#txtHoraSalida").val(),
			"observacionSalida" : $("#textAreaObservacionesVehiculo").val()
		}
	} else if (dataSeleccionado.estatus == "Aceptada") {
		requestSolicitudTransporte = {
			"idSolicitudTransporte" : idSolicitud,
			"idControlVehicular" : $("#idConstrolVehicular").val(),
			"kilometrosEntrada" : $("#txtKilometrosEntrada").val(),
			"combustibleEntrada" : $("#txtCombustibleEntrada").val(),
			"horaEntrada" : $("#txtHoraSalidaEntrada").val(),
			"observacionesEntrada" : $("#textAreaObservacionesVehiculoEntrada").val()
		}
	}
	console.log("Request guardar admin : " + requestSolicitudTransporte);
	return requestSolicitudTransporte;
}

/*********************************************************************************************************************
***********************                               		**********************************************************
***********************		RECHAZAR solicitud transporte 	**********************************************************
***********************                               		**********************************************************
**********************************************************************************************************************/

function rechazarSolicitud() {
	console.log("Rechazar solicitud ");
	if(dataSeleccionado != ""){
		if (dataSeleccionado.estatus == 'Creada' || dataSeleccionado.estatus == 'Guardada') {
		bootbox.confirm({
		title: 'Rechazar solicitud',
		message: '<p>¿Estas seguro de rechazar la solicitud?</p>',
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
			console.log("Eliminacion de solicitud : " + result);
			if(result){
				denegarSolicitudAdmin();
			}
		}
		});
		} else {
			bootbox.alert("El estado de la solicitud no permite rechazar el registro!");
		}
	}else{
		bootbox.alert("Seleccione un registro!");
	}
}

function denegarSolicitudAdmin(){
	var request = {
		"idSolicitudTransporte" : dataSeleccionado.idSolicitudTransporte
	}
	$.ajax({
			contentType: 'application/json; charset=UTF-8',
        	data: JSON.stringify(request),
        	//context+/solicitudTransporte
			url : context + "/rechazarSolTransporteAdmin",
			type : "post",
			beforeSend : function() {
				console.log("Antes de mandar los datos");
			},
			success : function (data) {
			console.log("Exito al aprovar solicitud");
			bootbox.alert("Exito al rechazar la solicitud!", function(){ 
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

/*********************************************************************************************************************
***********************                               		**********************************************************
***********************		APROVAR solicitud transporte 	**********************************************************
***********************                               		**********************************************************
**********************************************************************************************************************/

function aprovarSolicitud() {
	console.log("Aprovar solicitud ");
	if(dataSeleccionado != ""){
		if(dataSeleccionado.estatus == 'Guardada'){
			bootbox.confirm({
			title: 'Aprovar solicitud',
			message: '<p>¿Estas seguro de aprovar la solicitud?</p>',
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
				console.log("Aprovar solicitud : " + result);
				if(result){
					console.log("True: " + result);
					aproveSoliAdmin();
				}
			}
			});
		} else {
			bootbox.alert("Falta por guardar los datos de control vehicular !");
		}
	} else {
		bootbox.alert("Seleccione un registro!");
	}
}

function aproveSoliAdmin(){
	var request = {
		"idSolicitudTransporte" : dataSeleccionado.idSolicitudTransporte
	}
	$.ajax({
			contentType: 'application/json; charset=UTF-8',
        	data: JSON.stringify(request),
        	//context+/solicitudTransporte
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

function formatoHora(e){
	var inputLenth = $('#txtHoraSalida').val().length;
	var key = window.Event ? e.which : e.keyCode;
	if(inputLenth == 1){
		var isNumerico = (key >= 48 && key <= 57);
		if(isNumerico){
			var hora = $('#txtHoraSalida').val();
			$('#txtHoraSalida').val(hora+e.key+":");
			//Para que ya no se coloque el numero
			return false;
		} else {
			return false;
		}
	} else {
		return (key >= 48 && key <= 57);
	}
}