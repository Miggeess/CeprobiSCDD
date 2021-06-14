var context;
$(document).ready(function () {
 	/********************   CONTEXTO   ***************/
    context = window.location.pathname.split("/");
    context = context[1];
    context = window.location.protocol + "//" + window.location.host + '/' + context;
    console.log("Contexto : " + context);
});

function convertirMayus(e){
	e.value = e.value.toUpperCase();
}

function soloNumeros(e){
	var key = window.Event ? e.which : e.keyCode
	return (key >= 48 && key <= 57)
}

function formatoNumerico(dato){
	if(!dato.includes(',')){
		dato.value = new Intl.NumberFormat().format(dato.value);
	}
}

