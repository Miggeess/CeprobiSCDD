package ceprobi.scdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ceprobi.scdd.model.ScddEstatus;

@Repository
public interface EstatusRepositoryNewName extends CrudRepository<ScddEstatus, Integer> {

	@Query(value = "SELECT e FROM ScddEstatus e ")
	public List<ScddEstatus> obtenerEstatus();
	
	@Query(value = "SELECT s FROM ScddEstatus s WHERE s.txtCodigo = :txtCodigo")
	public ScddEstatus obtieneEstatuscodigo(@Param("txtCodigo") String txtCodigo);
	
}
