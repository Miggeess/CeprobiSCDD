package ceprobi.scdd.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ceprobi.scdd.model.ScddControlVechicular;

@Repository
public interface ControlVehicularRepository extends CrudRepository<ScddControlVechicular, Integer>{

	@Transactional
	@Modifying
	@Query(value = "UPDATE ScddControlVechicular c SET c.intKmEntrada = :kmEntrada, c.txtCombustibleEntrada = :combustibleEntrada, c.tmHoraEntrada = :horaEntrada, c.txtObservacionEntrada = :observacionesEntrada "+
					"WHERE c.idControlVehicular = :idControlVehicular")
	public Integer actualizaControlVehicular(@Param("kmEntrada") Integer kmEntrada, @Param("combustibleEntrada") String combustibleEntrada, @Param("horaEntrada") String horaEntrada, @Param("observacionesEntrada") String observacionesEntrada, @Param("idControlVehicular") Integer idControlVehicular );
	
	
	@Query(value = "SELECT v FROM ScddControlVechicular v WHERE v.idControlVehicular = :idControlVehicular")
	public ScddControlVechicular buscaSolControlVehicular(@Param("idControlVehicular") Integer idControlVehicular);
	
}
