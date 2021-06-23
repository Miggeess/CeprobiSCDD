var numeroFilasSeleccionadas = 0;
var row = "";
var datosSeleccionados = "";
var txtFolioLibre = "";
$(document).ready(function () {
	console.log(datosSession);
 	document.getElementById("txtNombreUsuario").innerHTML = datosSession.session.txtNickNameUser;
 	llenarTablaSolicitud();
 	seleccionarRegistro();
});

function seleccionarRegistro () {
	$('#tablaSolicitudTransporte').on('click', 'tr', function () {
		var nuevosDatos = $('#tablaSolicitudTransporte').DataTable().row(this).data();
		if($('#tablaSolicitudTransporte').DataTable().row(this).data() != null){
		if(datosSeleccionados != ""){
			if(datosSeleccionados.idSolicitudTransporte == nuevosDatos.idSolicitudTransporte){
				datosSeleccionados = "";
			} else {
				datosSeleccionados = nuevosDatos;
			}
		} else {
			datosSeleccionados = nuevosDatos;
		}
		} else {
			datosSeleccionados = "";
		}
		console.log("info : " + datosSeleccionados);
	});
}

function cambiarRegresoDestino(){
	var indexOrigenDestino2 = document.getElementById("listaOrigenesDestinos2").selectedIndex;
	document.getElementById("lodRegresoIda").selectedIndex = indexOrigenDestino2;
}
function cambiarRegresoNPasajeros(){
	$("#txtNPasajerosRegreso").val($("#txtNPasajeros").val());	
}
function cambiarRegresoFecha(){
	document.getElementById("dateSalidaRegreso").value = document.getElementById("dateSalida").value;
}

function llenarDatatable(datos) {
	 $('#tablaSolicitudTransporte').DataTable(
        {
        	"language": {
      			"emptyTable": "Sin informacion disponible en la tabla",
      			"paginate": {
      				"first":      "Primero",
      				"last":       "Último",
      				"next":       "Siguiente",
      				"previous":   "Anterior"
      			},
      			"infoEmpty":      "Muestra 0 a 0 de 0 entradas",
      			select: {
      				rows: "%d rows selected"
      			}
      		},
            searching: false,
            data: datos,
            select: true,
        	scrollCollapse: true,
            "columns": [
                {"data": "nomSolicitante"},
                {"data": "folioSolicitud"},
                {"data": "areaAdscripcion"},
                {"data": "dateSolicitud"},
                {"data": "estatus"}
            ]
        });

}

function llenarTablaSolicitud(){
	console.log("llenar tabla solicitud");
	var request = {txtUsuarioNoEmpleado : datosSession.session.nEmpleado};
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
		url : context + "/buscaSolicitudesCreador",
		data: JSON.stringify(request),
		type : "post",
		beforeSend : function() {
			console.log("Antes de mandar los datos");
		},
		success : function (data) {
			console.log("Exito al consulta informacion");
			llenarDatatable(data.solicitud);
		},
		error : function (data) {
			$("#exampleModal").modal("hide"); 
			console.log("Error al invocar el servicio");
			alert("Error");
		}
	});
}

function guardarSolicitudTransporte() {
	var registroNuevo;
	if(!validaCampos()){
		if(datosSeleccionados != "") {
			console.log("Es una edicion");
			registroNuevo = 2;
		} else {
			console.log("Es un nuevo registro");
			registroNuevo = 1;
		}
		var request = creaRequestSaveSolicitud(registroNuevo);
		$.ajax({
		contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(request),
		url : context + "/guardaSolicitud",
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
			alert("Error al invocar el servicio al guardar la solicitud");
		}
	});
	} else {
		bootbox.alert("Verifique sus datos!");
	}
}

function validaCampos(){
	var camposVacios;
	camposVacios = $("#txtNombre").val().length <= 0 ? true : false;
	camposVacios = $("#txtFolio").val().length <= 0 ? true : false;
	camposVacios = $("#txtAreaAdscripcion").val().length <= 0 ? true : false;
	camposVacios = $("#dateSolicitud").val().length <= 0 ? true : false;

	if( $("#tipoActividad1")[0].checked == false && $("#tipoActividad2")[0].checked == false ){
		camposVacios = true;
	} else if ( $("#horaSalida1")[0].checked == false && $("#horaSalida2")[0].checked == false ) {
		camposVacios = true;
	} else if ( $("#horaSalidaRegreso1")[0].checked == false && $("#horaSalidaRegreso1")[0].checked == false ) {
		camposVacios = true;
	}

	camposVacios = $("#listaOrigenesDestinos").val().length <= 0 ? true : false;
	camposVacios = $("#listaOrigenesDestinos2").val().length <= 0 ? true : false;
	camposVacios = $("#txtNPasajeros").val().length <= 0 ? true : false;
	camposVacios = $("#dateSalida").val().length <= 0 ? true : false;

	camposVacios = $("#lodRegresoIda").val().length <= 0 ? true : false;
	camposVacios = $("#lodRegresoDestino").val().length <= 0 ? true : false;
	camposVacios = $("#txtNPasajerosRegreso").val().length <= 0 ? true : false;
	camposVacios = $("#dateSalidaRegreso").val().length <= 0 ? true : false;

	return camposVacios;
}

function creaRequestSaveSolicitud(tipoRequest){
	
	var idSolicitudTransporte = (tipoRequest == 2 ? datosSeleccionados.idSolicitudTransporte : "");

	var checkActividad =  $('input[name=tipoViaje]:checked').val();
	var checkViajeTiempoIda = $('input[name=horaSalida]:checked').val();
	var checkViajeTiempoRegreso = $('input[name=horaSalidaRegreso]:checked').val();
	var noEmpleado = datosSession.session.nEmpleado;

	var requestSolicitudTransporte = {

		"idSolicitudTransporte" : idSolicitudTransporte,

    	"nomSolicitante" : $("#txtNombre").val(),
		"folioSolicitud" : $("#txtFolio").val(),
		"areaAdscripcion" : $("#txtAreaAdscripcion").val(),
		"dateSolicitud" : $("#dateSolicitud").val(),

		"txtActividad" : checkActividad,

		"txtOrigen" : $( "#listaOrigenesDestinos" ).val(),
		"txtDestino" : $( "#listaOrigenesDestinos2" ).val(),
		"txtNPasajeros" : $("#txtNPasajeros").val(),
		"dateSalida" : $("#dateSalida").val(),
		"checkHoraIda" : checkViajeTiempoIda,
		"textAreaObservaciones" : $("#textAreaObservaciones").val(),

		"txtOrigenRegreso" : $("#lodRegresoIda").val(),
		"txtDestinoRegreso" : $("#lodRegresoDestino").val(),
		"txtNPasajerosRegreso" : $("#txtNPasajerosRegreso").val(),
		"dateSalidaRegreso" : $("#dateSalidaRegreso").val(),
		"checkHoraRegreso" : checkViajeTiempoRegreso,
		"textAreaObservacionesRegreso" : $("#textAreaObservacionesRegreso").val(),

		"textDescripcionViaje" : $("#textDescripcionViaje").val(),
		txtUsuarioNoEmpleado : noEmpleado
	};
	return requestSolicitudTransporte;
}

function editarSoliTransporte()	{
	console.log("Entra a EDITAR solicitud");
	ocultarDivsDeSolicitud();
	if(datosSeleccionados != "") {
		var idSolicitud = datosSeleccionados.idSolicitudTransporte;

		var datos = {idSolicitudTransporte : idSolicitud,tipoUsuario : datosSession.session.txtNickNameUser};

			console.log("request : " + JSON.stringify(datos));
			$.ajax({
			contentType: 'application/json; charset=UTF-8',
        	data: JSON.stringify(datos),
        	//context+/solicitudTransporte
			url : context + "/buscarSolicitud",
			type : "post",
			beforeSend : function() {
				console.log("Antes de EDITAR la solicitud");
				},
			success : function (data) {
				console.log("Exito EDITAR solicitud");
				if(data.status == 0){
					llenarSolicitud(data.solicitud);
				} else {
					bootbox.alert("Error al EDITAR solicitud");
				}
			},
			error : function (data) {
				console.log("Error al invocar el servicio");
			}
		});
	} else {
		bootbox.alert("Seleccione un registro!");
	}
}

function crearElemento(data, idElement){
	var option = document.createElement("option");
	option.text = data;
	idElement.appendChild(option);
}

function llenarSolicitud(data){
	console.log("Id solicitud a ver" + data[0].idSolicitudTransporte);
	$("#txtNombre").val(data[0].nomSolicitante);
	$("#txtFolio").val(data[0].folioSolicitud);
	$("#txtAreaAdscripcion").val(data[0].areaAdscripcion);
	$("#dateSolicitud").val(data[0].dateSolicitud);

	document.getElementById("listaOrigenesDestinos").style.display="none";
	document.getElementById("listaOrigenesDestinos2").style.display="none";
	document.getElementById("txtOrigenOrigen").value = data[0].txtOrigen;
	document.getElementById("txtOrigenDestino").value = data[0].txtDestino;
	document.getElementById("txtOrigenOrigen").style.display="block";
	document.getElementById("txtOrigenDestino").style.display="block";

	$("#txtNPasajeros").val(data[0].txtNPasajeros);
	$("#dateSalida").val(data[0].dateSalida);
	$("#textAreaObservaciones").val(data[0].textAreaObservaciones);

	//crearElemento(data[0].txtOrigenRegreso, document.getElementById("lodRegresoIda"));
	//crearElemento(data[0].txtDestinoRegreso, document.getElementById("lodRegresoDestino"));
	document.getElementById("lodRegresoIda").style.display="none";
	document.getElementById("lodRegresoDestino").style.display="none";
	document.getElementById("txtDestinoOrigen").value = data[0].txtOrigenRegreso;
	document.getElementById("txtDestinoDestino").value = data[0].txtDestinoRegreso;
	document.getElementById("txtDestinoOrigen").style.display="block";
	document.getElementById("txtDestinoDestino").style.display="block";
	
	$("#txtNPasajerosRegreso").val(data[0].txtNPasajerosRegreso);
	$("#dateSalidaRegreso").val(data[0].dateSalidaRegreso);
	$("#textAreaObservacionesRegreso").val(data[0].textAreaObservacionesRegreso);

	$("#textDescripcionViaje").val(data[0].textDescripcionViaje);

	$("#tipoActividad1").prop( "checked", data[0].txtActividad == 1 ? true : false);
	$("#tipoActividad2").prop( "checked", data[0].txtActividad == 2 ? true : false);
	$("#horaSalida1").prop( "checked", data[0].checkHoraIda == 1 ? true : false );
	$("#horaSalida2").prop( "checked", data[0].checkHoraIda == 2 ? true : false );
	$("#horaSalidaRegreso1").prop( "checked", data[0].checkHoraRegreso == 1 ? true : false );
	$("#horaSalidaRegreso2").prop( "checked", data[0].checkHoraRegreso == 2 ? true : false );

	
	/**SI SE GUARDARON LOS DATOS Y DESPUES SE CANCELO*/
	$("#txtVehiculoAsignado").val(data[0].vehiculoAsignado);
	$("#txtPlacas").val(data[0].placas);
	$("#txtNomOperador").val(data[0].nomOperador);

	$("#txtKilometros").val(data[0].kilometrosSalida);
	$("#txtCombustible").val(data[0].combustibleSalida);
	$("#txtHoraSalida").val(data[0].horaSalida);
	$("#textAreaObservacionesVehiculo").val(data[0].observacionSalida);

	desHabilitarCamposSolicitud();

	$('#exampleModal').modal('show');
}

function eliminarSolicitudIni() {
	console.log("Eliminacion de la solicitud : init");
	if(datosSeleccionados != ""){
		if(datosSeleccionados.estatus != "Eliminada"){
		if(datosSeleccionados.estatus == "Creada"){	
			bootbox.confirm({
				title: 'Eliminacion de solicitud',
				message: '<p>¿Estas seguro de eliminar la solicitud?</p>',
				buttons: {
					cancel: {
 						label: 'No',
            			className: 'btn-danger'
					},
					confirm: {
						label: 'Yes',
            			className: 'btn-success'
					}
				},
			callback: function(result){
				console.log("Selecciona : " + result);
				if(result){
					console.log("True: " + result);
					eliminaSolicitud(datosSeleccionados.idSolicitudTransporte);
				} else {
					console.log("False: " + result);
				}
			}
	});
	} else {
		bootbox.alert("La solicitud ya se proceso");
	}
	} else {
		bootbox.alert("El registro ya se eliminó");
	}
	} else {
		bootbox.alert("Seleccione un registro!");
	}
}

function eliminaSolicitud(idSolicitud){
	console.log("solicitud a eliminar : " + idSolicitud);
	var datos = {
		idSolicitudTransporte : idSolicitud
		};
	console.log("request : " + JSON.stringify(datos));
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(datos),
		url : context + "/eliminarSolicitud",
		type : "post",
		beforeSend : function() {
			console.log("Antes de eliminar la solicitud");
		},
		success : function (data) {
			console.log("Exito eliminar solicitud");
			if(data.status == 0){
				location.reload();
			} else {
				bootbox.alert("Error al eliminar solicitud");
			}
		},
		error : function (data) {
			console.log("Error al invocar el servicio");
		}
	});
}

function llenaDestinosLugares(data, listaHtml){
	if(listaHtml.length == 0){
		for(var od in data){
			var option = document.createElement("option");
			option.value = data[od].idOrigenesDestinos;
    		option.text = data[od].txtLugar;
    		listaHtml.appendChild(option);
		}
	} else {
		listaHtml.selectedIndex = "0";
	}
	/**var listaDespegable = document.getElementById("listaOrigenesDestinos");
	var listaDespegable2 = document.getElementById("listaOrigenesDestinos2");
	var lodRegresoIda = document.getElementById("lodRegresoIda");
	var lodRegresoDestino = document.getElementById("lodRegresoDestino");
	if(listaDespegable.length == 0){
	for(var od in data){
		var option = document.createElement("option");
		option.value = data[od].idOrigenesDestinos;
    	option.text = data[od].txtLugar;
    	listaDespegable.appendChild(option);
	}
	for(var od in data){
		var option2 = document.createElement("option");
		option2.value = data[od].idOrigenesDestinos;
    	option2.text = data[od].txtLugar;
    	listaDespegable2.appendChild(option2);
	}
	for(var od in data){
		var opcion = document.createElement("option");
		opcion.value = data[od].idOrigenesDestinos;
    	opcion.text = data[od].txtLugar;
    	lodRegresoIda.appendChild(opcion);
    	lodRegresoDestino.appendChild(opcion);
	}
	listaDespegable.selectedIndex = "0";
	listaDespegable2.selectedIndex = "0";
	lodRegresoIda.selectedIndex = "0";
	lodRegresoDestino.selectedIndex = "0";
	} else {
		listaDespegable.selectedIndex = "0";
		listaDespegable2.selectedIndex = "0";
	}**/
}

function consultaSolicitudesPorDia(request){
		var numeroMaximoSolicitudes;
		$.ajax({
		contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(request),
		url : context + "/consultaSolicitudesPorDia",
		type : "post",
		beforeSend : function() {
		},
		success : function (data) {
			if(data.status == 1){
				numeroMaximoSolicitudes = true;
			} else {
				numeroMaximoSolicitudes = false;
			}
	    },
		error : function (data) {
			numeroMaximoSolicitudes = true;
		}
	});
	return numeroMaximoSolicitudes;
}


function ocultarDivsDeSolicitud () {
	$("#datosSolicitante").removeClass("show");
	$("#datosServTransporte").removeClass("show");
	$("#controlFlotaVehicular").removeClass("show");
}

function consultaFolioLibre() {

	datosSeleccionados = "";
	$("#tablaSolicitudTransporte tr").removeClass("selected");
	ocultarDivsDeSolicitud ();

	var f = new Date();
	console.log("Dia : " + f.getDate());
	console.log("Mes : " + (f.getMonth() +1));
	console.log("Año : " + f.getFullYear());
	var now = new Date();
	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	//var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
	var today = day + "/" + month + "/" + now.getFullYear();

	var request = {txtUsuarioNoEmpleado : datosSession.session.nEmpleado};

	var consultaMaximoSolicitudes = consultaSolicitudesPorDia(request);

	if (!consultaMaximoSolicitudes) {
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(request),
		url : context + "/buscaFolios",
		type : "post",
		beforeSend : function() {
			console.log("Antes de buscar folios la solicitud");
		},
		success : function (data) {
			console.log("Exito buscar folios solicitud");
			txtFolioLibre = data.mensaje;
			if (data.status == "1") {
				bootbox.alert("No hay folios disponibles, consulte al administrador");
			} else {
			llenaDestinosLugares(data.origenesDestinos, document.getElementById("listaOrigenesDestinos"));
			llenaDestinosLugares(data.origenesDestinos, document.getElementById("listaOrigenesDestinos2"));
			llenaDestinosLugares(data.origenesDestinos, document.getElementById("lodRegresoIda"));
			llenaDestinosLugares(data.origenesDestinos, document.getElementById("lodRegresoDestino"));

			document.getElementById("txtOrigenOrigen").style.display="none";
			document.getElementById("txtOrigenDestino").style.display="none";
			document.getElementById("txtDestinoOrigen").style.display="none";
			document.getElementById("txtDestinoDestino").style.display="none";

			document.getElementById("listaOrigenesDestinos").style.display="block";
			document.getElementById("listaOrigenesDestinos2").style.display="block";
			document.getElementById("lodRegresoIda").style.display="block";
			document.getElementById("lodRegresoDestino").style.display="block";


			$("#txtNombre").val(datosSession.session.txtNombres + " " + datosSession.session.txtApellidos);
			$("#txtNombre").prop("disabled", true );
			$("#txtAreaAdscripcion").val(datosSession.session.txtAreAdscripcion);
			$("#txtAreaAdscripcion").prop("disabled", true );

	        $("#txtFolio").val(txtFolioLibre);
	        $("#txtFolio").prop("disabled", true );
	        $("#dateSolicitud").val(today);
	        $("#dateSolicitud").prop("disabled", true );

	        $("#tipoActividad1").prop( "checked", false );
	        $("#tipoActividad1").prop("disabled", false );
	        $("#tipoActividad2").prop( "checked", false );
	        $("#tipoActividad2").prop("disabled", false );
	        $("#horaSalida1").prop( "checked", false );
	        $("#horaSalida1").prop("disabled", false );
	        $("#horaSalida2").prop( "checked", false );
	        $("#horaSalida2").prop("disabled", false );
	        $("#horaSalidaRegreso1").prop( "checked", false );
	        $("#horaSalidaRegreso1").prop("disabled", false );
	        $("#horaSalidaRegreso2").prop( "checked", false );
	        $("#horaSalidaRegreso2").prop("disabled", false );

	        $("#txtNPasajeros").val('');
	        $("#dateSalida").val('');
	        $("#textAreaObservaciones").val('');

	        $("#txtNPasajerosRegreso").val('');
	        $("#dateSalidaRegreso").val('');
	        $("#textAreaObservacionesRegreso").val('');

	        $("#textDescripcionViaje").val('');
			//OCULTAR BOTON EDITAR AL CREAR UNA NUEVA SOLICITUD
	        $("#botonEditar").hide();
	        $("#botonEnviar").show();

	        $("#cancelarSolicitud").show();
	        $("#cancelarSolicitudVer").hide();

	        $("#exampleModal").modal({backdrop: 'static', keyboard: false});
	        //<button data-target="#myModal" data-toggle="modal" data-backdrop="static" data-keyboard="false">
			}
	        },
		error : function (data) {
			console.log("Error al invocar el servicio");
			bootbox.alert("Error al invocar el servicio para consultar los folios, consulte al administrador");
		}
	});
	} else{
		bootbox.alert("Numero maximo de solicitudes creadas por dia, consulte al administrador");
	}
}

function desHabilitarCamposSolicitud(){
	$("#txtNombre").prop("disabled", true );
	$("#txtFolio").prop("disabled", true );
	$("#txtAreaAdscripcion").prop("disabled", true );
	$("#dateSolicitud").prop("disabled", true );

	//$("#listaOrigenesDestinos").prop("disabled", true );
	//$("#listaOrigenesDestinos2").prop("disabled", true );
	$("#txtOrigenOrigen").prop("disabled", true );
	$("#txtOrigenDestino").prop("disabled", true );
	$("#txtNPasajeros").prop("disabled", true );
	$("#dateSalida").prop("disabled", true );
	$("#textAreaObservaciones").prop("disabled", true );

	//$("#lodRegresoIda").prop("disabled", true );
	//$("#lodRegresoDestino").prop("disabled", true );
	$("#txtDestinoOrigen").prop("disabled", true );
	$("#txtDestinoDestino").prop("disabled", true );
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

	//OCULTA BOTON ENVIAR
	//$("#botonEnviar").hide();
	//$("#botonEditar").show();
	$("#cancelarSolicitudVerX").hide();
	$("#cancelarSolicitud").hide();
	$("#cancelarSolicitudVer").show();
	$("#botonEnviar").hide();
	$("#botonEditar").hide();
}

function habilitarCampos(){
	$("#txtNombre").prop("disabled", false );
	$("#txtFolio").prop("disabled", false );
	$("#txtAreaAdscripcion").prop("disabled", false );
	$("#dateSolicitud").prop("disabled", false );

	$("#listaOrigenesDestinos").prop("disabled", false );
	$("#listaOrigenesDestinos2").prop("disabled", false );
	$("#txtNPasajeros").prop("disabled", false );
	$("#dateSalida").prop("disabled", false );
	$("#textAreaObservaciones").prop("disabled", false );

	$("#lodRegresoIda").prop("disabled", false );
	$("#lodRegresoDestino").prop("disabled", false );
	$("#txtNPasajerosRegreso").prop("disabled", false );
	$("#dateSalidaRegreso").prop("disabled", false );
	$("#textAreaObservacionesRegreso").prop("disabled", false );

	$("#textDescripcionViaje").prop("disabled", false );

	
	//Modificar checkbox
	$("#checkAcadeAdmin").prop("disabled", false );
	$("#checkInveCampo").prop("disabled", false );
	//Modificar checkbox
	$("#checkHoraSalidaMañana").prop("disabled", false );
	$("#checkHoraSalidaTarde").prop("disabled", false );
	//Modificar checkbox
	$("#checkHoraSalidaMañanaRegreso").prop("disabled", false );
	$("#checkHoraSalidaTardeRegreso").prop("disabled", false );

	$("#botonEditar").hide();
	$("#botonEnviar").prop("disabled", false );
	$("#botonEnviar").show();
}

function cancelarSolicitud(){
	var datos = {
		txtFolioLibre : txtFolioLibre
		};
	$.ajax({
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(datos),
		url : context + "/cancelarFolios",
		type : "post",
		beforeSend : function() {
			console.log("Antes de cancelar folios la solicitud");
		},
		success : function (data) {
			console.log("Exito cancelar folios solicitud"+txtFolioLibre);
			txtFolioLibre = ""
			habilitarCampos();
			$('#exampleModal').modal('hide');
	    },
		error : function (data) {
			console.log("Error al invocar el servicio");
		}
	});
}

function cancelarSolicitudVer(){
	habilitarCampos();
	$('#exampleModal').modal('hide');
}
