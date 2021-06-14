package ceprobi.scdd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SCDD_CAT_VEHICULOS")
public class ScddCatVehiculos implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_VEHICULO")
	private int idVehiculo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TXT_CODIGO_VEHICULO", nullable = false, unique = true)
	private String codigoVehiculo;
	
	@Column(name = "TXT_MARCA")
	private String txtMarca;
	
	@Column(name = "TXT_NOMBRE")
	private String txtNombre;
	
	@Column(name = "TXT_PLACAS")
	private String txtPlacas;
	
	//@ManyToOne( fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_ESTATUS", nullable = false)
	@Column(name = "ID_ESTATUS")
	private String idEstatus;
	
}
