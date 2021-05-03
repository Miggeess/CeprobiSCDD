package ceprobi.scdd.service;

import ceprobi.scdd.dto.general.ResponseBuscaSolicitud;
import ceprobi.scdd.dto.general.ResponseGral;
import ceprobi.scdd.dto.soltransporte.RequestSolTransporte;

public interface SolicitudTransporteService {

	ResponseGral guardarTransporte(RequestSolTransporte request);
	
	ResponseGral guardarTransporteAdmin(RequestSolTransporte request);
	
	ResponseBuscaSolicitud buscaSolicitudesCreador(RequestSolTransporte request);
	
	ResponseBuscaSolicitud buscaSolicitudesTransporteAdmin(RequestSolTransporte request);
	
	ResponseGral eliminaTransporte(RequestSolTransporte request);
	
	ResponseBuscaSolicitud buscarSolicitud(RequestSolTransporte request);
	
	ResponseGral aprovarTransporte(RequestSolTransporte request);
	
	ResponseGral rechazarTransporte(RequestSolTransporte request);
	
	ResponseGral obtenerFolioLibre(String noEmpleado);
	
	ResponseGral cancelarFolios(String folio);
}
