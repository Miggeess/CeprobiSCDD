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
@Table(name = "SCDD_CAT_TRANS_ACTIVIDAD")
public class ScddActividad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CAT_TRANS_ACTIVIDAD", nullable = false, unique = true)
	private int idActividad;
	
	@Column(name = "TXT_COD_ACTIVIDAD")
	private String txtCodActividad;
	
	@Column(name = "TXT_DESCRIPCION_ACTIVIDAD")
	private String txtDescripcionActividad;
	
	
}
