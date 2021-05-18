package ceprobi.scdd.serviceimpl;
 
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ceprobi.scdd.dto.general.ResponseBuscaSolicitud;
import ceprobi.scdd.dto.general.ResponseGral;
import ceprobi.scdd.dto.soltransporte.RequestSolTransporte;
import ceprobi.scdd.model.ScddActividad;
import ceprobi.scdd.model.ScddControlVechicular;
import ceprobi.scdd.model.ScddEstatus;
import ceprobi.scdd.model.ScddFolios;
import ceprobi.scdd.model.ScddSoliTran;
import ceprobi.scdd.model.ScddUsuario;
import ceprobi.scdd.repository.ActividadRepository;
import ceprobi.scdd.repository.ControlVehicularRepository;
import ceprobi.scdd.repository.EstatusRepositoryNewName;
import ceprobi.scdd.repository.FoliosTransporteRepository;
import ceprobi.scdd.repository.SolicitudTransporteRepository;
import ceprobi.scdd.repository.UserRepository;
import ceprobi.scdd.service.SolicitudTransporteService;

@Service
public class SolicitudTransporteServiceImpl implements SolicitudTransporteService{

	private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudTransporteServiceImpl.class);
	private static final String SOLICITUD_CREADA = "C";
	private static final String SOLICITUD_GUARDADA = "G";
	private static final String SOLICITUD_APROVADA = "A";
	private static final String SOLICITUD_DENEGADA = "D";
	private static final String SOLICITUD_Aceptada = "AA";
	private static final String SOLICITUD_Declinada = "DL";
	private static final String ESTATUS_FOLIO_ESPERA = "FP";
	private static final String ESTATUS_FOLIO_LIBRE = "FL";
	private static final String ESTATUS_FOLIO_ASIGNADO = "FA";
	private static final String SOLICITUD_CONCLUIDA = "CL";
	private static final String SOLICITUD_FINALIZADA = "F";
	private static final String SOLICITUD_ELIMINADA = "E";
	
	@Autowired
	EstatusRepositoryNewName estatusRepositoryNew;
	@Autowired
	ActividadRepository actividadRepository;
	@Autowired
	SolicitudTransporteRepository solicitudTransporteRepository;
	@Autowired
	ControlVehicularRepository controlVehicularRepository;
	@Autowired
	FoliosTransporteRepository foliosTransporteRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseGral guardarTransporte(RequestSolTransporte request) {
		ResponseGral response = new ResponseGral();
		ScddSoliTran solTransporte = new ScddSoliTran();
		LOGGER.info("Entra a VALIDAR y GUARDAR solicitud de transporte");
		ScddActividad actividad = actividadRepository.obtieneActividadId(Integer.parseInt(request.getTxtActividad()));
		ScddEstatus estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_CREADA);  
		ScddUsuario usuario = userRepository.buscaUsuarioNoEmpleado(Integer.parseInt(request.getTxtUsuarioNoEmpleado()));
		
		solTransporte.setTxtNomSolicitante(request.getTxtNombreSolicitante());
		solTransporte.setTxtFolio(request.getTxtFolioSolicitante());
		solTransporte.setTxtDeptoAreaAdscripcion(request.getTxtAreaAdscripcion());
		
		try {
			Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDateSolicitud());
			Timestamp currentDate = new Timestamp(date1.getTime());
			solTransporte.setFechaSolicitud(currentDate);
			
			Date dateFechaSalida = new SimpleDateFormat("yyyy/MM/dd").parse(request.getDateSalida().replaceAll("-", "/"));
			Timestamp currentDate2 = new Timestamp(dateFechaSalida.getTime());
			solTransporte.setFechaIdaFecha(currentDate2);
			
			Date dateFechaRegreso = new SimpleDateFormat("yyyy/MM/dd").parse(request.getDateSalidaRegreso().replaceAll("-", "/"));
			solTransporte.setFechaRegresoFecha(new Timestamp(dateFechaRegreso.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		solTransporte.setActividad(actividad);
		
		
		
		solTransporte.setTxtIdaOrigen(request.getTxtOrigen());
		solTransporte.setTxtIdaDestino(request.getTxtDestino());
		solTransporte.setNumIdaNumPasajeros(Integer.parseInt(request.getTxtNPasajeros()));
		solTransporte.setTxtIdaHoraViaje(request.getCheckHoraIda());
		solTransporte.setTxtIdaObservaciones(request.getTextAreaObservaciones());
		
		
		solTransporte.setTxtRegresoOrigen(request.getTxtOrigenRegreso());
		solTransporte.setTxtRegresoDestino(request.getTxtDestinoRegreso());
		solTransporte.setNumRegresoNumPasajeros(Integer.parseInt(request.getTxtNPasajerosRegreso()));
		solTransporte.setTxtRegresoHoraViaje(request.getCheckHoraRegreso());
		solTransporte.setTxtRegresoObservaciones(request.getTextAreaObservacionesRegreso());
		
		solTransporte.setTxtDescripcionViaje(request.getTextDescripcionViaje());
		
		solTransporte.setEstatus(estatus);
		solTransporte.setUsuario(usuario);
		
		if(request.getIdSolicitud() != null && !request.getIdSolicitud().equals("")){
			Integer respo = solicitudTransporteRepository.actualizaSolicitud(
					solTransporte.getTxtNomSolicitante(), solTransporte.getTxtFolio(), 
					solTransporte.getTxtDeptoAreaAdscripcion(), solTransporte.getFechaSolicitud(),
					
					solTransporte.getTxtIdaOrigen(), solTransporte.getTxtIdaDestino(), solTransporte.getFechaIdaFecha(), 
					solTransporte.getTxtIdaHoraViaje(), solTransporte.getNumIdaNumPasajeros(), solTransporte.getTxtIdaObservaciones(), 
					
					solTransporte.getTxtRegresoOrigen(), solTransporte.getTxtRegresoDestino(), solTransporte.getFechaRegresoFecha(), 
					solTransporte.getTxtRegresoHoraViaje(), solTransporte.getNumRegresoNumPasajeros(), solTransporte.getTxtRegresoObservaciones(),
					
					solTransporte.getTxtDescripcionViaje(), Integer.parseInt(request.getTxtActividad()),
					
					Integer.parseInt(request.getIdSolicitud().trim()));
		} else {
			ScddSoliTran responsesolTransporte = solicitudTransporteRepository.save(solTransporte);
		}
		
		ScddFolios folioAsignar = foliosTransporteRepository.obtenerFolioOcupado(request.getTxtFolioSolicitante());
		ScddEstatus estatusAsignado= estatusRepositoryNew.obtieneEstatuscodigo(ESTATUS_FOLIO_ASIGNADO);
		folioAsignar.setEstatus(estatusAsignado);
		foliosTransporteRepository.save(folioAsignar);
		
		response.setMensaje("Exito");
		response.setStatus("0");
		return response;
	}

	@Override
	public ResponseGral aprovarTransporte(RequestSolTransporte request) {
		LOGGER.info("Entra a APROVAR solicitud de transporte");
		ResponseGral response = new ResponseGral();
		ScddEstatus estatus = new ScddEstatus();
		if (request.getRolUsr() != null && request.getRolUsr().equals("USER_GRAL")) {
			estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_Aceptada);
		} else {
			estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_APROVADA);
		}
		
		Integer respo = solicitudTransporteRepository.actualizaEstatusTransporte(estatus.getIdEstatus(), 
				Integer.parseInt(request.getIdSolicitud()));
		if(respo == null) {
			response.setMensaje("Error al aprovar la solicitud");
			response.setStatus("1");
		} else {
			response.setMensaje("Exito");
			response.setStatus("0");
		}
		return response;
	}
	
	@Override
	public ResponseGral rechazarTransporte(RequestSolTransporte request) {
		LOGGER.info("Entra a DENEGAR solicitud de transporte");
		ResponseGral response = new ResponseGral();
		ScddEstatus estatus = new ScddEstatus();
		if (request.getRolUsr() != null && request.getRolUsr().equals("USER_GRAL")) {
			estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_Declinada);
		} else {
			estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_DENEGADA);
		} 
		Integer respo = solicitudTransporteRepository.actualizaEstatusTransporte(estatus.getIdEstatus(), 
				Integer.parseInt(request.getIdSolicitud()));
		if(respo == null) {
			response.setMensaje("Error al denegar la solicitud");
			response.setStatus("1");
		} else {
			response.setMensaje("Exito");
			response.setStatus("0");
		}
		return response;
	}
	
	@Override
	public ResponseGral guardarTransporteAdmin(RequestSolTransporte request) {
		ResponseGral response = new ResponseGral();
		ScddControlVechicular controlVehicular = new ScddControlVechicular();
		
		if(request.getIdControlVehicular() != null) {
			ScddControlVechicular objControlVehicular = controlVehicularRepository.buscaSolControlVehicular(Integer.parseInt(request.getIdControlVehicular()));
			objControlVehicular.setTxtKmEntrada(request.getKilometrosEntrada());
			objControlVehicular.setTxtCombustibleEntrada(request.getCombustibleEntrada());
			objControlVehicular.setTmHoraEntrada(request.getHoraEntrada());
			objControlVehicular.setTxtObservacionEntrada(request.getObservacionesEntrada());
			
			ScddControlVechicular saveEntity=controlVehicularRepository.save(objControlVehicular);
			LOGGER.info("se guardo la solicitud admin : " + saveEntity.getIdControlVehicular());
			
			ScddEstatus estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_CONCLUIDA);
			
			Integer respo = solicitudTransporteRepository.actualizaIdControlVechicular(
					saveEntity.getIdControlVehicular(),
					estatus.getIdEstatus(),
					Integer.parseInt(request.getIdSolicitud().trim()));
			
		} else {
			controlVehicular.setTxtNomVehiculo(request.getVehiculoAsignado());
			controlVehicular.setTxtPlacas(request.getPlacas());
			controlVehicular.setTxtNomOperador(request.getNomOperador());
			
			controlVehicular.setTxtKMSalida(request.getKilometrosSalida());
			controlVehicular.setTxtCombustibleSalida(request.getCombustibleSalida());
			controlVehicular.setTmHoraSalida(request.getHoraSalida());
			controlVehicular.setTxtObservacionSalida(request.getObservacionSalida());
			
			ScddControlVechicular saveEntity=controlVehicularRepository.save(controlVehicular);
			LOGGER.info("se guardo la solicitud admin : " + saveEntity.getIdControlVehicular());
			
			ScddEstatus estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_GUARDADA); 
			
			Integer respo = solicitudTransporteRepository.actualizaIdControlVechicular(
					saveEntity.getIdControlVehicular(),
					estatus.getIdEstatus(),
					Integer.parseInt(request.getIdSolicitud().trim()));
		}
		
		response.setMensaje("Exito");
		response.setStatus("0");
		return response;
	}
	
	@Override
	public ResponseBuscaSolicitud buscaSolicitudesTransporteAdmin(RequestSolTransporte request) {
		LOGGER.info("Entra a BUSCAR solicitud de transporte para el usuario : " + request.getTipoUsuario());
		
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		ResponseBuscaSolicitud response = new ResponseBuscaSolicitud();
		
		List<ScddSoliTran> responsesolTransporte = new ArrayList<>();
		
		if (request.getRolUsr().equals("USER_GRAL")) {
			responsesolTransporte = solicitudTransporteRepository.buscaSolicitudesGerente(SOLICITUD_APROVADA, SOLICITUD_Aceptada,SOLICITUD_Declinada, SOLICITUD_CONCLUIDA, SOLICITUD_FINALIZADA);
		} else if(request.getRolUsr().equals("USER_ADMIN")){
			responsesolTransporte = solicitudTransporteRepository.buscaTodasSolicitudes();
		}
		ArrayList<RequestSolTransporte> listaSolicitudLlenar = new ArrayList<>();
		
		if(!responsesolTransporte.isEmpty()) {
			for(int i = 0; i < responsesolTransporte.size(); i++) {
				
				RequestSolTransporte solicitudLlenar = new RequestSolTransporte();
				
				solicitudLlenar.setIdSolicitud(String.valueOf(responsesolTransporte.get(i).getIdSolicitudTransporte()));
				
				solicitudLlenar.setTxtNombreSolicitante(responsesolTransporte.get(i).getTxtNomSolicitante().trim());
				solicitudLlenar.setTxtFolioSolicitante(responsesolTransporte.get(i).getTxtFolio());
				solicitudLlenar.setTxtAreaAdscripcion(responsesolTransporte.get(i).getTxtDeptoAreaAdscripcion());
				solicitudLlenar.setDateSolicitud(responsesolTransporte.get(i).getFechaSolicitud().toString());
				
				solicitudLlenar.setTxtActividad(responsesolTransporte.get(i).getActividad().getTxtDescripcionActividad());
				
				solicitudLlenar.setTxtOrigen(responsesolTransporte.get(i).getTxtIdaOrigen());
				solicitudLlenar.setTxtDestino(responsesolTransporte.get(i).getTxtIdaDestino());
				solicitudLlenar.setTxtNPasajeros(""+responsesolTransporte.get(i).getNumIdaNumPasajeros());
				solicitudLlenar.setDateSalida(responsesolTransporte.get(i).getFechaIdaFecha().toString());
				solicitudLlenar.setCheckHoraIda(responsesolTransporte.get(i).getTxtIdaHoraViaje());
				solicitudLlenar.setTextAreaObservaciones(responsesolTransporte.get(i).getTxtIdaObservaciones());
				
				solicitudLlenar.setTxtOrigenRegreso(responsesolTransporte.get(i).getTxtRegresoOrigen());
				solicitudLlenar.setTxtDestinoRegreso(responsesolTransporte.get(i).getTxtRegresoDestino());
				solicitudLlenar.setTxtNPasajerosRegreso(""+responsesolTransporte.get(i).getNumRegresoNumPasajeros());
				solicitudLlenar.setDateSalidaRegreso(responsesolTransporte.get(i).getFechaRegresoFecha().toString());
				solicitudLlenar.setCheckHoraRegreso(responsesolTransporte.get(i).getTxtRegresoHoraViaje());
				solicitudLlenar.setTextAreaObservacionesRegreso(responsesolTransporte.get(i).getTxtRegresoObservaciones());
				
				solicitudLlenar.setTextDescripcionViaje(responsesolTransporte.get(i).getTxtDescripcionViaje());
				
				solicitudLlenar.setEstatus(responsesolTransporte.get(i).getEstatus().getTxtDescripcion());
				
				listaSolicitudLlenar.add(solicitudLlenar);
			}
		} else {
			response.setMensaje("Error : sin solicitudes");
			response.setStatus("1");
			return response;
		}
		
		response.setMensaje("Exito");
		response.setStatus("0");
		response.setSolicitud(listaSolicitudLlenar);
		
		return response;
	}
	
	@Override
	public ResponseBuscaSolicitud buscaSolicitudesCreador(RequestSolTransporte request) {
		LOGGER.info("Entra a BUSCAR solicitud de transporte");
		ResponseBuscaSolicitud response = new ResponseBuscaSolicitud();
		List<ScddSoliTran> responsesolTransporte = solicitudTransporteRepository.buscaSolicitudesCreador(SOLICITUD_CREADA, SOLICITUD_DENEGADA, SOLICITUD_APROVADA, SOLICITUD_ELIMINADA, Integer.parseInt(request.getTxtUsuarioNoEmpleado().trim()));
		ArrayList<RequestSolTransporte> listaSolicitudLlenar = new ArrayList<>();		
		if(!responsesolTransporte.isEmpty()) {
			for(int i = 0; i < responsesolTransporte.size(); i++) {
				
				RequestSolTransporte solicitudLlenar = new RequestSolTransporte();
				
				solicitudLlenar.setIdSolicitud(String.valueOf(responsesolTransporte.get(i).getIdSolicitudTransporte()));
				
				solicitudLlenar.setTxtNombreSolicitante(responsesolTransporte.get(i).getTxtNomSolicitante().trim());
				solicitudLlenar.setTxtFolioSolicitante(responsesolTransporte.get(i).getTxtFolio());
				solicitudLlenar.setTxtAreaAdscripcion(responsesolTransporte.get(i).getTxtDeptoAreaAdscripcion());
				solicitudLlenar.setDateSolicitud(new SimpleDateFormat("dd/MM/yyyy").format(
						responsesolTransporte.get(i).getFechaSolicitud()));
				
				solicitudLlenar.setTxtActividad(responsesolTransporte.get(i).getActividad().getTxtDescripcionActividad());
				
				solicitudLlenar.setTxtOrigen(responsesolTransporte.get(i).getTxtIdaOrigen());
				solicitudLlenar.setTxtDestino(responsesolTransporte.get(i).getTxtIdaDestino());
				solicitudLlenar.setTxtNPasajeros(""+responsesolTransporte.get(i).getNumIdaNumPasajeros());
				solicitudLlenar.setDateSalida(responsesolTransporte.get(i).getFechaIdaFecha().toString());
				solicitudLlenar.setCheckHoraIda(responsesolTransporte.get(i).getTxtIdaHoraViaje());
				solicitudLlenar.setTextAreaObservaciones(responsesolTransporte.get(i).getTxtIdaObservaciones());
				
				solicitudLlenar.setTxtOrigenRegreso(responsesolTransporte.get(i).getTxtRegresoOrigen());
				solicitudLlenar.setTxtDestinoRegreso(responsesolTransporte.get(i).getTxtRegresoDestino());
				solicitudLlenar.setTxtNPasajerosRegreso(""+responsesolTransporte.get(i).getNumRegresoNumPasajeros());
				solicitudLlenar.setDateSalidaRegreso(responsesolTransporte.get(i).getFechaRegresoFecha().toString());
				solicitudLlenar.setCheckHoraRegreso(responsesolTransporte.get(i).getTxtRegresoHoraViaje());
				solicitudLlenar.setTextAreaObservacionesRegreso(responsesolTransporte.get(i).getTxtRegresoObservaciones());
				
				solicitudLlenar.setTextDescripcionViaje(responsesolTransporte.get(i).getTxtDescripcionViaje());
				
				solicitudLlenar.setEstatus(responsesolTransporte.get(i).getEstatus().getTxtDescripcion());
				
				listaSolicitudLlenar.add(solicitudLlenar);
			}
		} else {
			response.setMensaje("Error : sin solicitudes");
			response.setStatus("1");
			return response;
		}
		
		response.setMensaje("Exito");
		response.setStatus("0");
		response.setSolicitud(listaSolicitudLlenar);
		
		return response;
	}

	@Override
	public ResponseGral eliminaTransporte(RequestSolTransporte request) {
		LOGGER.info("Entra a ELIMINAR solicitud de transporte");
		ResponseGral response = new ResponseGral();
		ScddEstatus estatus = new ScddEstatus();
		
		List<ScddSoliTran> resBusqueda = solicitudTransporteRepository.buscaSolicitud(Integer.parseInt(request.getIdSolicitud()));
		
		/**ScddFolios folioOcupado = foliosTransporteRepository.obtenerFolioOcupado(resBusqueda.get(0).getTxtFolio());
		ScddEstatus estatusLibre= estatusRepositoryNew.obtieneEstatuscodigo(ESTATUS_FOLIO_LIBRE);
		folioOcupado.setEstatus(estatusLibre);
		foliosTransporteRepository.save(folioOcupado);
		
		estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_ELIMINADA);
		Integer respo = solicitudTransporteRepository.actualizaEstatusTransporte(estatus.getIdEstatus(), 
				Integer.parseInt(request.getIdSolicitud()));
		*/
		//Se modifica la eliminacion de la solicitud
		Integer respo = solicitudTransporteRepository.eliminaSolicitudTransporte(Integer.parseInt(request.getIdSolicitud()));
		if(respo == null) {
			response.setMensaje("Error al eliminar la solicitud");
			response.setStatus("1");
		} else {
			response.setMensaje("Exito");
			response.setStatus("0");
		}
		return response;
	}
	
	@Override
	public ResponseBuscaSolicitud buscarSolicitud(RequestSolTransporte request) {
		ResponseBuscaSolicitud response = new ResponseBuscaSolicitud();
		List<ScddSoliTran> resBusqueda = solicitudTransporteRepository.buscaSolicitud(Integer.parseInt(request.getIdSolicitud()));
		ArrayList<RequestSolTransporte> listaSol = new ArrayList<>();
		if(!resBusqueda.isEmpty()) {
			for(int i = 0; i < resBusqueda.size(); i++) {
				RequestSolTransporte dtoSolicitud = new RequestSolTransporte();
				
				dtoSolicitud.setIdSolicitud(String.valueOf(resBusqueda.get(i).getIdSolicitudTransporte()));
				
				dtoSolicitud.setTxtNombreSolicitante(resBusqueda.get(i).getTxtNomSolicitante().trim());
				dtoSolicitud.setTxtFolioSolicitante(resBusqueda.get(i).getTxtFolio());
				dtoSolicitud.setTxtAreaAdscripcion(resBusqueda.get(i).getTxtDeptoAreaAdscripcion());
				dtoSolicitud.setDateSolicitud(new SimpleDateFormat("dd/MM/yyyy").format(
						resBusqueda.get(i).getFechaSolicitud()));
				
				dtoSolicitud.setTxtActividad(String.valueOf(resBusqueda.get(i).getActividad().getIdActividad()));
				
				dtoSolicitud.setTxtOrigen(resBusqueda.get(i).getTxtIdaOrigen());
				dtoSolicitud.setTxtDestino(resBusqueda.get(i).getTxtIdaDestino());
				dtoSolicitud.setTxtNPasajeros(""+resBusqueda.get(i).getNumIdaNumPasajeros());
				dtoSolicitud.setDateSalida(new SimpleDateFormat("yyyy-MM-dd").format(
						resBusqueda.get(i).getFechaIdaFecha()));
				dtoSolicitud.setCheckHoraIda(resBusqueda.get(i).getTxtIdaHoraViaje());
				dtoSolicitud.setTextAreaObservaciones(resBusqueda.get(i).getTxtIdaObservaciones());
				
				dtoSolicitud.setTxtOrigenRegreso(resBusqueda.get(i).getTxtRegresoOrigen());
				dtoSolicitud.setTxtDestinoRegreso(resBusqueda.get(i).getTxtRegresoDestino());
				dtoSolicitud.setTxtNPasajerosRegreso(""+resBusqueda.get(i).getNumRegresoNumPasajeros());
				dtoSolicitud.setDateSalidaRegreso(new SimpleDateFormat("yyyy-MM-dd").format(
						resBusqueda.get(i).getFechaRegresoFecha()));
				dtoSolicitud.setCheckHoraRegreso(resBusqueda.get(i).getTxtRegresoHoraViaje());
				dtoSolicitud.setTextAreaObservacionesRegreso(resBusqueda.get(i).getTxtRegresoObservaciones());
				
				dtoSolicitud.setTextDescripcionViaje(resBusqueda.get(i).getTxtDescripcionViaje());
				
				dtoSolicitud.setEstatus(resBusqueda.get(i).getEstatus().getTxtDescripcion());
				
				if(request.getTipoUsuario() != null && !request.getTipoUsuario().equals("")) {
					
					dtoSolicitud.setIdControlVehicular(String.valueOf(resBusqueda.get(i).getControlVehicular() != null ? 
									resBusqueda.get(i).getControlVehicular().getIdControlVehicular() : ""));
					
					dtoSolicitud.setVehiculoAsignado(resBusqueda.get(i).getControlVehicular() != null ? 
									resBusqueda.get(i).getControlVehicular().getTxtNomVehiculo() : "");
					dtoSolicitud.setPlacas(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getTxtPlacas() : "");
					dtoSolicitud.setNomOperador(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getTxtNomOperador() : "");
					dtoSolicitud.setKilometrosSalida(String.valueOf(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getTxtKMSalida() : ""));
					dtoSolicitud.setCombustibleSalida(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getTxtCombustibleSalida() : "");
					dtoSolicitud.setHoraSalida(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getTmHoraSalida() : "");
					dtoSolicitud.setObservacionSalida(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getTxtObservacionSalida() : "");
				
					/** Rellenar datos control vehicular regreso-entrada */
					if(resBusqueda.get(i).getControlVehicular() != null) {
						dtoSolicitud.setKilometrosEntrada(resBusqueda.get(i).getControlVehicular().getTxtKmEntrada());
						dtoSolicitud.setCombustibleEntrada(String.valueOf(resBusqueda.get(i).getControlVehicular().getTxtCombustibleEntrada() != null ? 
								resBusqueda.get(i).getControlVehicular().getTxtCombustibleEntrada() : ""));
						dtoSolicitud.setHoraEntrada(String.valueOf(resBusqueda.get(i).getControlVehicular().getTmHoraEntrada()  != null ? 
								resBusqueda.get(i).getControlVehicular().getTmHoraEntrada() : ""));
						dtoSolicitud.setObservacionesEntrada(String.valueOf(resBusqueda.get(i).getControlVehicular().getTxtObservacionEntrada() != null ? 
								resBusqueda.get(i).getControlVehicular().getTxtObservacionEntrada() : ""));
					}
				}
				
				listaSol.add(dtoSolicitud);
				
			}
			response.setMensaje("Exito");
			response.setStatus("0");
			response.setSolicitud(listaSol);
		} else {
			response.setMensaje("Error : no se encontro la solicitud");
			response.setStatus("1");
		}
		return response;
	}

	@Override
	public ResponseGral obtenerFolioLibre(String noEmpleado) {
		LOGGER.info("Entra a BUSCAR folio de transporte para el numero de empleado : " + noEmpleado);
		ResponseGral response = new ResponseGral();
		List<ScddFolios> foliosLibres = foliosTransporteRepository.obtenerFolioLibre(11);
		String folio = foliosLibres.isEmpty() ? "0" : foliosLibres.get(0).getTxtFolio();
		response.setMensaje(folio);
		response.setStatus("0");
		if(!folio.equals("0")) {
			ScddEstatus estatusOcupado = estatusRepositoryNew.obtieneEstatuscodigo(ESTATUS_FOLIO_ESPERA);
			foliosLibres.get(0).setEstatus(estatusOcupado);
			ScddFolios folioOcupado = (ScddFolios) foliosLibres.get(0);
			foliosTransporteRepository.save(folioOcupado);
		}
		return response;
	}
	
	@Override
	public ResponseGral cancelarFolios(String folio) {
		LOGGER.info("Entra a DESOCUPAR folio de transporte");
		ResponseGral response = new ResponseGral();
		ScddFolios folioOcupado = foliosTransporteRepository.obtenerFolioOcupado(folio);
		
		ScddEstatus estatusLibre= estatusRepositoryNew.obtieneEstatuscodigo(ESTATUS_FOLIO_LIBRE);
		folioOcupado.setEstatus(estatusLibre);
		foliosTransporteRepository.save(folioOcupado);
		
		response.setMensaje("Exito");
		response.setStatus("0");
		
		return response;
	}

}
