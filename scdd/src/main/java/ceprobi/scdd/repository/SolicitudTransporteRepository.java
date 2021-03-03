package ceprobi.scdd.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ceprobi.scdd.model.ScddSoliTran;

@Repository
public interface SolicitudTransporteRepository extends CrudRepository<ScddSoliTran, Integer>{

	@Query(value = "SELECT t FROM ScddSoliTran t WHERE t.estatus.txtCodigo in (:codCreada, :codRechazada, :codAprobada, :codEliminada) AND t.usuario.intNoEmpleado = :intNoEmpleado")
	List<ScddSoliTran> buscaSolicitudesCreador(
			@Param("codCreada") String creada, @Param("codRechazada") String rechazada, @Param("codAprobada") String aprovada, @Param("codEliminada") String eliminada, @Param("intNoEmpleado") Integer intNoEmpleado);
	
	@Query(value = "SELECT t FROM ScddSoliTran t ")
	List<ScddSoliTran> buscaTodasSolicitudes();
	
	@Query(value = "SELECT t FROM ScddSoliTran t WHERE t.estatus.txtCodigo  in (:codAprovada, :codAceptada, :codDenegada, :codConcluida, :codFinalizada)")
	List<ScddSoliTran> buscaSolicitudesGerente(@Param("codAprovada") String aprovada, @Param("codAceptada") String aceptada, @Param("codDenegada") String denegada, @Param("codConcluida") String codConcluida, @Param("codFinalizada") String codFinalizada);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM ScddSoliTran t WHERE t.idSolicitudTransporte = :IdSolicituTransporte")
	public Integer eliminaSolicitudTransporte(@Param("IdSolicituTransporte") Integer IdSolicituTransporte);
	
	@Query(value = "SELECT t FROM ScddSoliTran t WHERE t.idSolicitudTransporte = :idSolicitud")
	List<ScddSoliTran> buscaSolicitud(@Param("idSolicitud") Integer idSolicitud);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ScddSoliTran s set s.txtNomSolicitante = :nomSolicitante, s.txtFolio = :txtFolio,  s.txtDeptoAreaAdscripcion = :txtDeptoAreaAdscripcion, s.fechaSolicitud = :fechaSolicitud, "
			+ "s.txtIdaOrigen = :txtIdaOrigen, s.txtIdaDestino = :txtIdaDestino, s.fechaIdaFecha = :fechaIdaFecha, "
			+ "s.txtIdaHoraViaje = :txtIdaHoraViaje, s.numIdaNumPasajeros = :numIdaNumPasajeros, s.txtIdaObservaciones = :txtIdaObservaciones, "
			
			+ "s.txtRegresoOrigen = :txtRegresoOrigen, s.txtRegresoDestino = :txtRegresoDestino, s.fechaRegresoFecha = :fechaRegresoFecha, "
			+ "s.txtRegresoHoraViaje = :txtRegresoHoraViaje, s.numRegresoNumPasajeros = :numRegresoNumPasajeros, s.txtRegresoObservaciones = :txtRegresoObservaciones, "
			
			+ "s.txtDescripcionViaje = :txtDescripcionViaje, s.actividad.idActividad = :idActividad "
			
			+ "WHERE s.idSolicitudTransporte = :IdSolicituTransporte")
	public Integer actualizaSolicitud(@Param("nomSolicitante") String nomSolicitante, @Param("txtFolio") String txtFolio, @Param("txtDeptoAreaAdscripcion") String txtDeptoAreaAdscripcion, @Param("fechaSolicitud") LocalDate fechaSolicitud, 
			@Param("txtIdaOrigen") String txtIdaOrigen, @Param("txtIdaDestino") String txtIdaDestino, @Param("fechaIdaFecha") LocalDate fechaIdaFecha, 
			@Param("txtIdaHoraViaje") String txtIdaHoraViaje, @Param("numIdaNumPasajeros") Integer numIdaNumPasajeros, @Param("txtIdaObservaciones") String txtIdaObservaciones,
			
			@Param("txtRegresoOrigen") String txtRegresoOrigen, @Param("txtRegresoDestino") String txtRegresoDestino, @Param("fechaRegresoFecha") LocalDate fechaRegresoFecha, 
			@Param("txtRegresoHoraViaje") String txtRegresoHoraViaje, @Param("numRegresoNumPasajeros") Integer numRegresoNumPasajeros, @Param("txtRegresoObservaciones") String txtRegresoObservaciones,
			
			@Param("txtDescripcionViaje") String txtDescripcionViaje, @Param("idActividad") Integer idActividad,
			
			@Param("IdSolicituTransporte") Integer IdSolicituTransporte);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ScddSoliTran t SET t.controlVehicular.idControlVehicular = :IdControlVehicular, t.estatus.idEstatus = :idEstatus  WHERE t.idSolicitudTransporte = :IdSolicituTransporte")
	public Integer actualizaIdControlVechicular(@Param("IdControlVehicular") Integer IdControlVehicular, @Param("idEstatus") Integer idEstatus, @Param("IdSolicituTransporte") Integer IdSolicituTransporte);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ScddSoliTran t SET t.estatus.idEstatus = :idEstatus  WHERE t.idSolicitudTransporte = :IdSolicituTransporte")
	public Integer actualizaEstatusTransporte(@Param("idEstatus") Integer idEstatus, @Param("IdSolicituTransporte") Integer IdSolicituTransporte);
}
