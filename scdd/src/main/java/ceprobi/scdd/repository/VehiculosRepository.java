package ceprobi.scdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ceprobi.scdd.model.ScddCatVehiculos;


@Repository
public interface VehiculosRepository extends CrudRepository<ScddCatVehiculos, String>{

	@Query(value = "SELECT v FROM ScddCatVehiculos v ")
	List<ScddCatVehiculos> buscaTodosVehiculos();
	
	@Query(value = "SELECT vs FROM ScddCatVehiculos vs WHERE vs.txtPlazas >= :plazas")
	List<ScddCatVehiculos> buscaVehiculosPorPlazas(@Param("plazas") String plazas);
	
	@Query(value = "SELECT ve FROM ScddCatVehiculos ve WHERE ve.codigoVehiculo = :txtCodigoVehiculo")
	ScddCatVehiculos obtieneVehiculo(@Param("txtCodigoVehiculo") String txtCodigoVehiculo);
	
}
