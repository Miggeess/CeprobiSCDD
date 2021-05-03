package ceprobi.scdd.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "SCDD_SOL_TRANS_DATOS_GRAL")
public class ScddSoliTran implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOL_TRANS_DATOS_GRAL", nullable = false, unique = true)
	private int idSolicitudTransporte;
	
    @Column(name = "TXT_NOMBRE")
    private String txtNomSolicitante;
	
	@Column(name = "TXT_FOLIO")
	private String txtFolio;
	
	@Column(name = "TXT_DEPTO_AREA_ADSCRIPCION")
	private String txtDeptoAreaAdscripcion;
	
	@Column(name = "DT_FECHA_SOLICITUD")
	private Timestamp fechaSolicitud;
    
    @Column(name = " TXT_IDA_ORIGEN")
	private String txtIdaOrigen;
	
	@Column(name = "TXT_IDA_DESTINO")
	private String txtIdaDestino;
	
	@Column(name = "TXT_IDA_FECHA")
	private Timestamp fechaIdaFecha;
	
	@Column(name = "TXT_IDA_HORA")
	private String txtIdaHoraViaje;
	
	@Column(name = "NUM_IDA_PASAJEROS")
	private int numIdaNumPasajeros;
	
	@Column(name = "TXT_IDA_OBSERVACIONES")
	private String txtIdaObservaciones;
	
	
    @Column(name = " TXT_REGRESO_ORIGEN")
	private String txtRegresoOrigen;
	
	@Column(name = "TXT_REGRESO_DESTINO")
	private String txtRegresoDestino;
	
	@Column(name = "TXT_REGRESO_FECHA")
	private Timestamp fechaRegresoFecha;
	
	@Column(name = "TXT_REGRESO_HORA")
	private String txtRegresoHoraViaje;
	
	@Column(name = "NUM_REGRESO_PASAJEROS")
	private int numRegresoNumPasajeros;
	
	@Column(name = "TXT_REGRESO_OBSERVACIONES")
	private String txtRegresoObservaciones;
	
	
	@Column(name = "TXT_DESCRIPCION_VIAJE")
	private String txtDescripcionViaje;
	
	@Column(name = "TXT_OBSERVACION_GRAL")
	private String txtObservacionGral;
	
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "INT_NUM_EMPLEADO", nullable = false)
	private ScddUsuario usuario;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CAT_TRANS_ACTIVIDAD", nullable = false)
	private ScddActividad actividad;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTATUS", nullable = false)
	private ScddEstatus estatus;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SOL_TRANS_CONTROL_VEHICULAR")
	private ScddControlVechicular controlVehicular;
	
}
