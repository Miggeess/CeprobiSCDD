package ceprobi.scdd.dto.general;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ceprobi.scdd.dto.soltransporte.RequestSolTransporte;
import lombok.Data;
import lombok.experimental.PackagePrivate;

@Data
@PackagePrivate
@JsonIgnoreProperties
public class ResponseBuscaSolicitud extends ResponseGral {
	
	List<RequestSolTransporte> solicitud;

}
