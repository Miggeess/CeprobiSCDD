var context;
$(document).ready(function () {
 	/********************   CONTEXTO   ***************/
    context = window.location.pathname.split("/");
    context = context[1];
    context = window.location.protocol + "//" + window.location.host + '/' + context;
    console.log("Contexto : " + context);
});