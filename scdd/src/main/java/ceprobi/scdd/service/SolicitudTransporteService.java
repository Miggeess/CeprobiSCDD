package ceprobi.scdd.service;

import ceprobi.scdd.dto.general.ResponseBuscaSolicitud;
import ceprobi.scdd.dto.general.ResponseDTOSolicitud;
import ceprobi.scdd.dto.general.ResponseFoliosAndLugares;
import ceprobi.scdd.dto.general.ResponseGral;
import ceprobi.scdd.dto.general.ResponsePlacaVehiculo;
import ceprobi.scdd.dto.soltransporte.RequestSolTransporte;

public interface SolicitudTransporteService {

	ResponseGral guardarTransporte(RequestSolTransporte request);
	
	ResponseGral guardarTransporteAdmin(RequestSolTransporte request);
	
	ResponseBuscaSolicitud buscaSolicitudesCreador(RequestSolTransporte request);
	
	ResponseBuscaSolicitud buscaSolicitudesTransporteAdmin(RequestSolTransporte request);
	
	ResponseGral eliminaTransporte(RequestSolTransporte request);
	
	ResponseBuscaSolicitud buscarSolicitud(RequestSolTransporte request);
	
	ResponsePlacaVehiculo buscaPlaca(RequestSolTransporte request);
	
	ResponseGral aprovarTransporte(RequestSolTransporte request);
	
	ResponseGral rechazarTransporte(RequestSolTransporte request);
	
	ResponseFoliosAndLugares obtenerFolioLibre(String noEmpleado);
	
	ResponseGral consultaSolicitudesPorDia(String noEmpleado);
	
	ResponseGral cancelarFolios(String folio);
}
