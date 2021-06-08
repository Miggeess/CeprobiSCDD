package ceprobi.scdd.dto.general;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ceprobi.scdd.model.ScddCatOrigenesDestinos;
import lombok.Data;
import lombok.experimental.PackagePrivate;

@Data
@PackagePrivate
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseFoliosAndLugares extends ResponseGral {

	List<ScddCatOrigenesDestinos> origenesDestinos;
	
}
