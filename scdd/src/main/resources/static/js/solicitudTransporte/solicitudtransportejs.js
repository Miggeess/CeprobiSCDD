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

function llenarDatatable(datos) {
	 $('#tablaSolicitudTransporte').DataTable(
        {
        	"language": {
      			"emptyTable": "No data available in table"
      		},
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

	camposVacios = $("#txtOrigen").val().length <= 0 ? true : false;
	camposVacios = $("#txtDestino").val().length <= 0 ? true : false;
	camposVacios = $("#txtNPasajeros").val().length <= 0 ? true : false;
	camposVacios = $("#dateSalida").val().length <= 0 ? true : false;

	camposVacios = $("#txtOrigenRegreso").val().length <= 0 ? true : false;
	camposVacios = $("#txtDestinoRegreso").val().length <= 0 ? true : false;
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

		"txtOrigen" : $("#txtOrigen").val(),
		"txtDestino" : $("#txtDestino").val(),
		"txtNPasajeros" : $("#txtNPasajeros").val(),
		"dateSalida" : $("#dateSalida").val(),
		"checkHoraIda" : checkViajeTiempoIda,
		"textAreaObservaciones" : $("#textAreaObservaciones").val(),

		"txtOrigenRegreso" : $("#txtOrigenRegreso").val(),
		"txtDestinoRegreso" : $("#txtDestinoRegreso").val(),
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

function llenarSolicitud(data){
	console.log("Id solicitud a ver" + data[0].idSolicitudTransporte);
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
        //context+/solicitudTransporte
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
			if (txtFolioLibre == "0") {
				bootbox.alert("No hay folios disponibles, consulte al administrador");
			} else {
			console.log("Folio libre = " + txtFolioLibre)
			$("#txtNombre").val(datosSession.session.txtNickNameUser);
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

	        $("#txtOrigen").val('');
	        $("#txtDestino").val('');
	        $("#txtNPasajeros").val('');
	        $("#dateSalida").val('');
	        $("#textAreaObservaciones").val('');

	        $("#txtOrigenRegreso").val('');
	        $("#txtDestinoRegreso").val('');
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
}

function desHabilitarCamposSolicitud(){
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

	$("#txtOrigen").prop("disabled", false );
	$("#txtDestino").prop("disabled", false );
	$("#txtNPasajeros").prop("disabled", false );
	$("#dateSalida").prop("disabled", false );
	$("#textAreaObservaciones").prop("disabled", false );

	$("#txtOrigenRegreso").prop("disabled", false );
	$("#txtDestinoRegreso").prop("disabled", false );
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
