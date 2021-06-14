package ceprobi.scdd.dto.general;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ceprobi.scdd.model.ScddCatOperadores;
import ceprobi.scdd.model.ScddCatVehiculos;
import ceprobi.scdd.model.ScddSoliTran;
import lombok.Data;
import lombok.experimental.PackagePrivate;

@Data
@PackagePrivate
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTOSolicitud extends ResponseGral {

	//List<ScddSoliTran> solicitud;
	
	List<ScddCatOperadores> operadores;
	
	//List<ScddCatVehiculos> vehiculos; 
}
