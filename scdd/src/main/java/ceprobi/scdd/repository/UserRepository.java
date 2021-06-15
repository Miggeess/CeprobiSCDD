package ceprobi.scdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ceprobi.scdd.model.ScddUsuario;

@Repository
public interface UserRepository extends CrudRepository<ScddUsuario, Integer>{

	@Query(value = "SELECT u FROM ScddUsuario u WHERE u.txtNickName = :txtNickName")
	public ScddUsuario obtieneDatosPorNikName(@Param("txtNickName") String txtNickName);
	
	@Query(value = "SELECT v FROM ScddUsuario v WHERE v.txtPwd = :passVerifica")
	public List<ScddUsuario> verificaContraValida(@Param("passVerifica") String passVerifica);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ScddUsuario u SET u.intActivo = :intActivo WHERE u.txtNickName = :txtNickName AND u.intNoEmpleado = :intNoEmpleado")
	public Integer actualizaUsuarioActivo(@Param("intActivo") int intActivo, @Param("txtNickName") String txtNickName, @Param("intNoEmpleado") Integer intNoEmpleado);
	
	@Query(value = "SELECT e FROM ScddUsuario e WHERE e.intNoEmpleado = :intNoEmpleado")
	public ScddUsuario buscaUsuarioNoEmpleado(@Param("intNoEmpleado") int intNoEmpleado);
}
