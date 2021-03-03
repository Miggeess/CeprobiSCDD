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
@Table(name = "scdd_cat_folios_soltransporte")
public class ScddFolios implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_folio", nullable = false, unique = true)
	private int idFolio;
	
	@Column(name = "txt_folio")
	private String txtFolio;
	
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus", nullable = false)
	private ScddEstatus estatus;
}
