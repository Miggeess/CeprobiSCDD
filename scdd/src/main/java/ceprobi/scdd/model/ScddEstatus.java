package ceprobi.scdd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "scdd_estatus")
public class ScddEstatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESTATUS", nullable = false, unique = true)
	private int idEstatus;
	
	@Column(name = "TXT_CODIGO")
	private String txtCodigo;
	
	@Column(name = "TXT_DESCRIPCION")
	private String txtDescripcion;
}
