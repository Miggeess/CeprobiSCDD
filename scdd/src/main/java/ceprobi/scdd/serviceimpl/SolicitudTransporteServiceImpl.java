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
import ceprobi.scdd.dto.general.ResponseDTOSolicitud;
import ceprobi.scdd.dto.general.ResponseFoliosAndLugares;
import ceprobi.scdd.dto.general.ResponseGral;
import ceprobi.scdd.dto.general.ResponsePlacaVehiculo;
import ceprobi.scdd.dto.soltransporte.RequestSolTransporte;
import ceprobi.scdd.model.ScddActividad;
import ceprobi.scdd.model.ScddCatOperadores;
import ceprobi.scdd.model.ScddCatOrigenesDestinos;
import ceprobi.scdd.model.ScddCatVehiculos;
import ceprobi.scdd.model.ScddControlVechicular;
import ceprobi.scdd.model.ScddEstatus;
import ceprobi.scdd.model.ScddFolios;
import ceprobi.scdd.model.ScddSoliTran;
import ceprobi.scdd.model.ScddUsuario;
import ceprobi.scdd.repository.ActividadRepository;
import ceprobi.scdd.repository.ControlVehicularRepository;
import ceprobi.scdd.repository.EstatusRepositoryNewName;
import ceprobi.scdd.repository.FoliosTransporteRepository;
import ceprobi.scdd.repository.OperadoresRepository;
import ceprobi.scdd.repository.OrigenesDestinosRepository;
import ceprobi.scdd.repository.SolicitudTransporteRepository;
import ceprobi.scdd.repository.UserRepository;
import ceprobi.scdd.repository.VehiculosRepository;
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
	private static final int ID_FOLIO_LIBRE = 11;
	
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
	@Autowired
	OrigenesDestinosRepository origenesDestinosRepository;
	@Autowired
	OperadoresRepository operadoresRepository;
	@Autowired
	VehiculosRepository vehiculosRepository;
	
	@Override
	public ResponseGral guardarTransporte(RequestSolTransporte request) {
		ResponseGral response = new ResponseGral();
		ScddSoliTran solTransporte = new ScddSoliTran();
		LOGGER.info("Entra a VALIDAR y GUARDAR solicitud de transporte");
		ScddActividad actividad = actividadRepository.obtieneActividadId(Integer.parseInt(request.getTxtActividad()));
		ScddEstatus estatus = estatusRepositoryNew.obtieneEstatuscodigo(SOLICITUD_CREADA);  
		ScddUsuario usuario = userRepository.buscaUsuarioNoEmpleado(Integer.parseInt(request.getTxtUsuarioNoEmpleado()));
		ScddCatOrigenesDestinos odInicio =  origenesDestinosRepository.obtieneDetinoOrigen(Integer.parseInt(request.getTxtOrigen()));
		ScddCatOrigenesDestinos odDestino =  origenesDestinosRepository.obtieneDetinoOrigen(Integer.parseInt(request.getTxtDestino()));
		ScddCatOrigenesDestinos odRegresoInicio =  origenesDestinosRepository.obtieneDetinoOrigen(Integer.parseInt(request.getTxtOrigenRegreso()));
		ScddCatOrigenesDestinos odRegresoDestino =  origenesDestinosRepository.obtieneDetinoOrigen(Integer.parseInt(request.getTxtDestinoRegreso()));
		
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
		solTransporte.setIdaOrigen(odInicio);
		solTransporte.setIdaDestino(odDestino);
		solTransporte.setNumIdaNumPasajeros(Integer.parseInt(request.getTxtNPasajeros()));
		solTransporte.setTxtIdaHoraViaje(request.getCheckHoraIda());
		solTransporte.setTxtIdaObservaciones(request.getTextAreaObservaciones());
		
		
		solTransporte.setRegresoOrigen(odRegresoInicio);
		solTransporte.setRegresoDestino(odRegresoDestino);
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
					
					solTransporte.getIdaOrigen().getIdOrigenesDestinos(), solTransporte.getIdaDestino().getIdOrigenesDestinos(), solTransporte.getFechaIdaFecha(), 
					solTransporte.getTxtIdaHoraViaje(), solTransporte.getNumIdaNumPasajeros(), solTransporte.getTxtIdaObservaciones(), 
					
					solTransporte.getRegresoOrigen().getIdOrigenesDestinos(), solTransporte.getRegresoDestino().getIdOrigenesDestinos(), solTransporte.getFechaRegresoFecha(), 
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
			ScddCatOperadores operador = operadoresRepository.obtieneOperador(request.getNomOperador());
			controlVehicular.setOperador(operador);
			ScddCatVehiculos vehiculo = vehiculosRepository.obtieneVehiculo(request.getVehiculoAsignado());
			controlVehicular.setVehiculo(vehiculo);
			
			
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
				solicitudLlenar.setDateSolicitud(new SimpleDateFormat("dd/MM/yyyy").format(
						responsesolTransporte.get(i).getFechaSolicitud()));
				
				solicitudLlenar.setTxtActividad(responsesolTransporte.get(i).getActividad().getTxtDescripcionActividad());
				
				solicitudLlenar.setTxtOrigen(responsesolTransporte.get(i).getIdaOrigen().getTxtLugar());
				solicitudLlenar.setTxtDestino(responsesolTransporte.get(i).getIdaDestino().getTxtLugar());
				solicitudLlenar.setTxtNPasajeros(""+responsesolTransporte.get(i).getNumIdaNumPasajeros());
				solicitudLlenar.setDateSalida(new SimpleDateFormat("yyyy-MM-dd").format(
						responsesolTransporte.get(i).getFechaIdaFecha()));
				solicitudLlenar.setCheckHoraIda(responsesolTransporte.get(i).getTxtIdaHoraViaje());
				solicitudLlenar.setTextAreaObservaciones(responsesolTransporte.get(i).getTxtIdaObservaciones());
				
				solicitudLlenar.setTxtOrigenRegreso(responsesolTransporte.get(i).getRegresoOrigen().getTxtLugar());
				solicitudLlenar.setTxtDestinoRegreso(responsesolTransporte.get(i).getRegresoDestino().getTxtLugar());
				solicitudLlenar.setTxtNPasajerosRegreso(""+responsesolTransporte.get(i).getNumRegresoNumPasajeros());
				solicitudLlenar.setDateSalidaRegreso(new SimpleDateFormat("yyyy-MM-dd").format(
						responsesolTransporte.get(i).getFechaRegresoFecha()));
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
				
				solicitudLlenar.setTxtOrigen(responsesolTransporte.get(i).getIdaOrigen().getTxtLugar());
				solicitudLlenar.setTxtDestino(responsesolTransporte.get(i).getIdaDestino().getTxtLugar());
				solicitudLlenar.setTxtNPasajeros(""+responsesolTransporte.get(i).getNumIdaNumPasajeros());
				solicitudLlenar.setDateSalida(responsesolTransporte.get(i).getFechaIdaFecha().toString());
				solicitudLlenar.setCheckHoraIda(responsesolTransporte.get(i).getTxtIdaHoraViaje());
				solicitudLlenar.setTextAreaObservaciones(responsesolTransporte.get(i).getTxtIdaObservaciones());
				
				solicitudLlenar.setTxtOrigenRegreso(responsesolTransporte.get(i).getRegresoOrigen().getTxtLugar());
				solicitudLlenar.setTxtDestinoRegreso(responsesolTransporte.get(i).getRegresoDestino().getTxtLugar());
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
	public ResponsePlacaVehiculo buscaPlaca(RequestSolTransporte request) {
		ResponsePlacaVehiculo response = new ResponsePlacaVehiculo();
		ScddCatVehiculos resVehiculos = vehiculosRepository.obtieneVehiculo(request.getPlacaVehiculo());
		response.setMensaje("Exito");
		response.setStatus("0");
		response.setPlacaVehiculo(resVehiculos.getTxtPlacas());
		return response;
	}
	
	@Override
	public ResponseBuscaSolicitud buscarSolicitud(RequestSolTransporte request) {
		ResponseBuscaSolicitud response = new ResponseBuscaSolicitud();
		List<ScddSoliTran> resBusqueda = solicitudTransporteRepository.buscaSolicitud(Integer.parseInt(request.getIdSolicitud()));
		List<ScddCatOperadores> resOperadores = operadoresRepository.buscaTodosOperadores();

		response.setOperadores(resOperadores);
		ArrayList<RequestSolTransporte> listaSol = new ArrayList<>();
		if(!resBusqueda.isEmpty()) {
			for(int i = 0; i < resBusqueda.size(); i++) {
				
				List<ScddCatVehiculos> resVehiculos = new ArrayList<>();
				List<ScddCatVehiculos> resVehiculos2 = vehiculosRepository.buscaTodosVehiculos();
				for(ScddCatVehiculos obj :  resVehiculos2) {
					LOGGER.info(obj.getTxtNombre() + " " + obj.getTxtPlazas());
					if(Integer.parseInt(obj.getTxtPlazas()) >= resBusqueda.get(i).getNumIdaNumPasajeros()) {
						resVehiculos.add(obj);
					}
				}
				response.setVehiculos(resVehiculos);
				
				RequestSolTransporte dtoSolicitud = new RequestSolTransporte();
				
				dtoSolicitud.setIdSolicitud(String.valueOf(resBusqueda.get(i).getIdSolicitudTransporte()));
				dtoSolicitud.setTxtNombreSolicitante(resBusqueda.get(i).getTxtNomSolicitante().trim());
				dtoSolicitud.setTxtFolioSolicitante(resBusqueda.get(i).getTxtFolio());
				dtoSolicitud.setTxtAreaAdscripcion(resBusqueda.get(i).getTxtDeptoAreaAdscripcion());
				dtoSolicitud.setDateSolicitud(new SimpleDateFormat("dd/MM/yyyy").format(
						resBusqueda.get(i).getFechaSolicitud()));
				
				dtoSolicitud.setTxtActividad(String.valueOf(resBusqueda.get(i).getActividad().getIdActividad()));
				
				dtoSolicitud.setTxtOrigen(resBusqueda.get(i).getIdaOrigen().getTxtLugar());
				dtoSolicitud.setTxtDestino(resBusqueda.get(i).getIdaDestino().getTxtLugar());
				dtoSolicitud.setTxtNPasajeros(""+resBusqueda.get(i).getNumIdaNumPasajeros());
				dtoSolicitud.setDateSalida(new SimpleDateFormat("yyyy-MM-dd").format(
						resBusqueda.get(i).getFechaIdaFecha()));
				dtoSolicitud.setCheckHoraIda(resBusqueda.get(i).getTxtIdaHoraViaje());
				dtoSolicitud.setTextAreaObservaciones(resBusqueda.get(i).getTxtIdaObservaciones());
				
				dtoSolicitud.setTxtOrigenRegreso(resBusqueda.get(i).getRegresoOrigen().getTxtLugar());
				dtoSolicitud.setTxtDestinoRegreso(resBusqueda.get(i).getRegresoDestino().getTxtLugar());
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
									resBusqueda.get(i).getControlVehicular().getVehiculo().getTxtNombre() : "");
					dtoSolicitud.setPlacas(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getVehiculo().getTxtPlacas() : "");
					dtoSolicitud.setNomOperador(resBusqueda.get(i).getControlVehicular() != null ? 
							resBusqueda.get(i).getControlVehicular().getOperador().getTxtNombre() : "");
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
	public ResponseFoliosAndLugares obtenerFolioLibre(String noEmpleado) {
		LOGGER.info("Entra a BUSCAR folio de transporte para el numero de empleado : " + noEmpleado);
		ResponseFoliosAndLugares response = new ResponseFoliosAndLugares();
		List<ScddFolios> foliosLibres = foliosTransporteRepository.obtenerFolioLibre(ID_FOLIO_LIBRE);
		String folio = foliosLibres.isEmpty() ? "1" : foliosLibres.get(0).getTxtFolio();
		
		List<ScddCatOrigenesDestinos> origenes = origenesDestinosRepository.buscaTodosOrigenesDestinos();
		
		if(!folio.equals("1")) {
			ScddEstatus estatusOcupado = estatusRepositoryNew.obtieneEstatuscodigo(ESTATUS_FOLIO_ESPERA);
			foliosLibres.get(0).setEstatus(estatusOcupado);
			ScddFolios folioOcupado = (ScddFolios) foliosLibres.get(0);
			foliosTransporteRepository.save(folioOcupado);
			response.setStatus("0");
			response.setMensaje(folio);
			response.setOrigenesDestinos(origenes);
		} else {
			response.setStatus("1");
			response.setMensaje("");
			response.setOrigenesDestinos(null);
		}
		return response;
	}
	
	@Override
	public ResponseGral consultaSolicitudesPorDia(String noEmpleado) {
		ResponseGral response = new ResponseGral();
		LOGGER.info("Entra a  consultaSolicitudesPorDia del empleado " + noEmpleado);
		List<ScddSoliTran> solicitudesTransporte = solicitudTransporteRepository.buscaTodasSolicitudes();
		int contador = 0;
		for(ScddSoliTran solicitud : solicitudesTransporte) {
			if(solicitud.getUsuario().getIntNoEmpleado()  == Integer.parseInt(noEmpleado)){
				if(new Date().equals(solicitud.getFechaSolicitud())) {
					contador++;
				}
			}
		}
		response = (contador > 5 ? 
				new ResponseGral("1","Numero de solicitudes extendido") : 
				new ResponseGral("0","Exito"));
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
