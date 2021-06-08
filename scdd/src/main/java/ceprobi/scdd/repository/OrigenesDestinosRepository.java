package ceprobi.scdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ceprobi.scdd.model.ScddCatOrigenesDestinos;

@Repository
public interface OrigenesDestinosRepository extends CrudRepository<ScddCatOrigenesDestinos, Integer>{
	
	@Query(value = "SELECT o FROM ScddCatOrigenesDestinos o ")
	List<ScddCatOrigenesDestinos> buscaTodosOrigenesDestinos();
	
	@Query(value = "SELECT od FROM ScddCatOrigenesDestinos od WHERE od.idOrigenesDestinos = :txtCodigo")
	ScddCatOrigenesDestinos obtieneDetinoOrigen(@Param("txtCodigo") Integer txtCodigo);
	
}
