package ceprobi.scdd.daosession;

public class UsuarioSessionDAO {

	private String txtNickNameUser;
	private boolean booleanActiveUser;
	private String idSession;
	private String txtRolUsr;
	private int nEmpleado;
	private String txtAreAdscripcion;

	public UsuarioSessionDAO(String txtNickNameUser, boolean booleanActiveUser, String idSession, String txtRolUsr, int nEmpleado, String txtAreAdscripcion) {
		this.txtNickNameUser = txtNickNameUser;
		this.booleanActiveUser = booleanActiveUser;
		this.idSession = idSession;
		this.txtRolUsr = txtRolUsr;
		this.nEmpleado = nEmpleado;
		this.txtAreAdscripcion = txtAreAdscripcion;
	}
	
	public String getTxtNickNameUser() {
		return txtNickNameUser;
	}
	public void setTxtNickNameUser(String txtNickNameUser) {
		this.txtNickNameUser = txtNickNameUser;
	}
	public boolean getBooleanActiveUser() {
		return booleanActiveUser;
	}
	public void setBooleanActiveUser(boolean booleanActiveUser) {
		this.booleanActiveUser = booleanActiveUser;
	}
	public String getIdSession() {
		return idSession;
	}
	public void setIdSession(String string) {
		this.idSession = string;
	}
	public String getTxtRolUsr() {
		return txtRolUsr;
	}
	public void setTxtRolUsr(String txtRolUsr) {
		this.txtRolUsr = txtRolUsr;
	}
	public int getnEmpleado() {
		return nEmpleado;
	}
	public void setnEmpleado(int nEmpleado) {
		this.nEmpleado = nEmpleado;
	}

	public String getTxtAreAdscripcion() {
		return txtAreAdscripcion;
	}

	public void setTxtAreAdscripcion(String txtAreAdscripcion) {
		this.txtAreAdscripcion = txtAreAdscripcion;
	}
	
}
