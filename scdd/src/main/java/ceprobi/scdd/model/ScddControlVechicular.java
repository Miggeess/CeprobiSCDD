package ceprobi.scdd.model;

import java.io.Serializable;
import java.time.LocalTime;

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
@Table(name = "SCDD_SOL_TRANS_CONTROL_VEHICULAR")
public class ScddControlVechicular implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOL_TRANS_CONTROL_VEHICULAR", nullable = false, unique = true)
	private int idControlVehicular;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VEHICULO")
	private ScddCatVehiculos vehiculo;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_OPERADOR")
	private ScddCatOperadores operador;
	
	@Column(name = "TXT_SALIDA_KM")
	private String txtKMSalida;
	
	@Column(name = "TXT_SALIDA_COMBUSTIBLE")
	private String txtCombustibleSalida;
	
	@Column(name = "TM_SALIDA_HORA")
	private String tmHoraSalida;
	
	@Column(name = "TXT_SALIDA_OBSERVACIONES")
	private String txtObservacionSalida;
    
    @Column(name = "TXT_ENTRADA_KM")
	private String txtKmEntrada;
	
	@Column(name = "TXT_ENTRADA_COMBUSTIBLE")
	private String txtCombustibleEntrada;
	
	@Column(name = "TM_ENTRADA_HORA")
	private String tmHoraEntrada;
	
	@Column(name = "TXT_ENTRADA_OBSERVACIONES")
	private String txtObservacionEntrada;
	
}
