package ceprobi.scdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ceprobi.scdd.model.ScddFolios;

@Repository
public interface FoliosTransporteRepository extends CrudRepository<ScddFolios, Integer> {

	
	@Query("SELECT f FROM ScddFolios f WHERE f.estatus.idEstatus = :idEstatus")
	List<ScddFolios> obtenerFolioLibre(@Param("idEstatus") Integer idEstatus);
	
	@Query("SELECT f FROM ScddFolios f WHERE f.txtFolio = :txtFolio")
	ScddFolios obtenerFolioOcupado(@Param("txtFolio") String txtFolio);
}
