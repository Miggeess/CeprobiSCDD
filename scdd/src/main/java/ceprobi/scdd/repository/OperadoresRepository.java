package ceprobi.scdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ceprobi.scdd.model.ScddCatOperadores;

@Repository
public interface OperadoresRepository extends CrudRepository<ScddCatOperadores, String> {

	@Query(value = "SELECT o FROM ScddCatOperadores o ")
	List<ScddCatOperadores> buscaTodosOperadores();

	@Query(value = "SELECT op FROM ScddCatOperadores op WHERE op.codigoOperador = :txtCodigoOperador")
	ScddCatOperadores obtieneOperador(@Param("txtCodigoOperador") String txtCodigoOperador);
	
}
