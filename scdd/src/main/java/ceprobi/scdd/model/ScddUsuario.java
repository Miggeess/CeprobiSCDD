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
@Table(name = "scdd_cep_usr_usuario")
public class ScddUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "TXT_NICKNAME")
	private String txtNickName;
	
	@Column(name = "TXT_PASSWORD")
	private String txtPwd;
	
	@Column(name = "B_ACTIVO")
	private int intActivo;
	
	@Column(name = "TXT_ROLE_USR")
	private String txtRolUsr;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INT_NUM_EMPLEADO", nullable = false, unique = true)
	private int intNoEmpleado;
	
	@Column(name = "TXT_NOMBRE")
	private String txtNombreUsr;
	
	@Column(name = "TXT_AREA_ADSCRIPCION")
	private String txtAreaAdscripcion;
	
	@Column(name = "TXT_APELLIDO")
	private String txtApellido;
	
}
