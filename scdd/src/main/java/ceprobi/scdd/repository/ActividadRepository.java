package ceprobi.scdd.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ceprobi.scdd.model.ScddActividad;

@Repository
public interface ActividadRepository extends CrudRepository<ScddActividad, Integer>{

	@Query(value = "SELECT a FROM ScddActividad a WHERE a.idActividad = :idActividad")
	ScddActividad obtieneActividadId(@Param("idActividad") Integer idActividad);
	
}
