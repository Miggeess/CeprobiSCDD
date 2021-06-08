package ceprobi.scdd.viewcontroller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceprobi.scdd.dto.general.ResponseBuscaSolicitud;
import ceprobi.scdd.dto.general.ResponseFoliosAndLugares;
import ceprobi.scdd.dto.general.ResponseGral;
import ceprobi.scdd.dto.soltransporte.RequestSolTransporte;
import ceprobi.scdd.service.SolicitudTransporteService;

@RestController
//@RequestMapping("solicitudTransporte")
public class TransporteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransporteController.class);
	
	@Autowired
	SolicitudTransporteService SolicitudTransporteService;
	
	@PostMapping(value = "buscaSolicitudesCreador", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBuscaSolicitud buscaSolicitudesCreador(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.buscaSolicitudesCreador(request);
	}
	
	@PostMapping(value = "buscarSolicitud", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBuscaSolicitud buscarSolicitud(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.buscarSolicitud(request);
	}
	
	@PostMapping(value = "buscaFolios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFoliosAndLugares obtenerFolioLibre(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.obtenerFolioLibre(request.getTxtUsuarioNoEmpleado());
	}
	
	@PostMapping(value = "consultaSolicitudesPorDia", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral consultaSolicitudesPorDia(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.consultaSolicitudesPorDia(request.getTxtUsuarioNoEmpleado());
	}
	
	
	@PostMapping(value = "cancelarFolios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral cancelarFolios(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.cancelarFolios(request.getTxtFolioLibre());
	}
	
	@PostMapping(value = "guardaSolicitud", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral guardarTransporte(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.guardarTransporte(request);
	}
	
	@PostMapping(value = "guardaSolicitudAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral guardarTransporteAdmin(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.guardarTransporteAdmin(request);
	}
	
	@PostMapping(value = "buscaSolTransporteAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBuscaSolicitud buscaSolTransporteAdmin(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.buscaSolicitudesTransporteAdmin(request);
	}
	
	@PostMapping(value = "aprovarSolTransporteAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral aprovarSolicitudAdmin(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.aprovarTransporte(request);
	}
	
	@PostMapping(value = "rechazarSolTransporteAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral rechazarSolicitudAdmin(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.rechazarTransporte(request);
	}
	
	@PostMapping(value = "eliminarSolicitud", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseGral eliminaSolicitud(@RequestBody RequestSolTransporte request) {
		return SolicitudTransporteService.eliminaTransporte(request);
	}
}
