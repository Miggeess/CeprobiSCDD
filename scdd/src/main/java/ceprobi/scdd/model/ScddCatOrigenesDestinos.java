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
@Table(name = "SCDD_CAT_ORIGENES_DESTINOS")
public class ScddCatOrigenesDestinos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ORIGENES_DESTINOS", nullable = false, unique = true)
	private int idOrigenesDestinos;
	
	@Column(name = "TXT_CODIGO_OD")
	private String txtCodigo;
	
	@Column(name = "TXT_LUGAR")
	private String txtLugar;
	
	@Column(name = "TXT_DETALLE")
	private String txtDetalle;
	
}
