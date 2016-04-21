/**
 * 
 */
$('input,textarea').bind('cut copy paste', function(e) {
	e.preventDefault(); // disable cut,copy,paste
});