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
@Table(name = "SCDD_CAT_OPERADORES")
public class ScddCatOperadores implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_OPERADOR")
	private int idOperador;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TXT_CODIGO_OPERADOR", nullable = false, unique = true)
	private String codigoOperador;
	
	@Column(name = "TXT_NOMBRE")
	private String txtNombre;
	
	@Column(name = "TXT_APELLIDOS")
	private String txtApellidos;
	
	//@ManyToOne( fetch = FetchType.LAZY)
	//@JoinColumn(name = "ID_ESTATUS", nullable = false)
	//private ScddEstatus idEstatus;
	@Column(name = "ID_ESTATUS")
	private String idEstatus;
	
	@Column(name = "TXT_NOEMPLEADO")
	private String noEmpleado;
}
