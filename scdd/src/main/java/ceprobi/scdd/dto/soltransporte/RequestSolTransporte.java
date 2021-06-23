package ceprobi.scdd.dto.soltransporte;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import ceprobi.scdd.model.ScddCatOperadores;
import ceprobi.scdd.model.ScddCatVehiculos;
import lombok.Data;
import lombok.experimental.PackagePrivate;

@Data
@PackagePrivate
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSolTransporte {
	
	@JsonProperty("nomSolicitante")
	String txtNombreSolicitante;
	@JsonProperty("folioSolicitud")
	String txtFolioSolicitante;
	@JsonProperty("areaAdscripcion")
	String txtAreaAdscripcion;
	@JsonProperty("dateSolicitud")
	String dateSolicitud;
	
	@JsonProperty("txtActividad")//1 o 0
	String txtActividad;
	
	@JsonProperty("txtOrigen")
	String txtOrigen;
	@JsonProperty("txtDestino")
	String txtDestino;
	@JsonProperty("txtNPasajeros")
	String txtNPasajeros;
	@JsonProperty("dateSalida")
	String dateSalida;
	@JsonProperty("checkHoraIda")//1 o 0
	String checkHoraIda;
	@JsonProperty("textAreaObservaciones")
	String textAreaObservaciones;

	@JsonProperty("txtOrigenRegreso")
	String txtOrigenRegreso;
	@JsonProperty("txtDestinoRegreso")
	String txtDestinoRegreso;
	@JsonProperty("txtNPasajerosRegreso")
	String txtNPasajerosRegreso;
	@JsonProperty("dateSalidaRegreso")
	String dateSalidaRegreso;
	@JsonProperty("checkHoraRegreso")//1 o 0
	String checkHoraRegreso;
	@JsonProperty("textAreaObservacionesRegreso")
	String textAreaObservacionesRegreso;
	
	@JsonProperty("textDescripcionViaje")
	String textDescripcionViaje;

	@JsonProperty("vehiculoAsignado")
	String vehiculoAsignado;
	@JsonProperty("placas")
	String placas;
	@JsonProperty("plazas")
	String plazas;
	@JsonProperty("nomOperador")
	String nomOperador;
	@JsonProperty("kilometrosSalida")
	String kilometrosSalida;
	@JsonProperty("combustibleSalida")
	String combustibleSalida;
	@JsonProperty("horaSalida")
	String horaSalida;
	@JsonProperty("observacionSalida")
	String observacionSalida;
	
	@JsonProperty("kilometrosEntrada")
	String kilometrosEntrada;
	@JsonProperty("combustibleEntrada")
	String combustibleEntrada;
	@JsonProperty("horaEntrada")
	String horaEntrada;
	@JsonProperty("observacionesEntrada")
	String observacionesEntrada;
	@JsonProperty("idControlVehicular")
	String idControlVehicular;
	
	
	@JsonProperty("idSolicitudTransporte")
	String idSolicitud;
	
	String observacionGral;
	
	//String actividad;
	
	@JsonIgnoreProperties("txtEstatus")
	String estatus;
	
	@JsonProperty("tipoUsuario")
	String tipoUsuario;
	

	@JsonProperty("txtFolioLibre")
	String txtFolioLibre;
	
	@JsonProperty("txtUsuarioNoEmpleado")
	String txtUsuarioNoEmpleado;
	
	@JsonProperty("rolUsr")
	String rolUsr;
	
	@JsonProperty("placaVehiculo")
	String placaVehiculo;
	
}
