(function ($) {
    "use strict";



$('.dropdown-el').click(function(e) {
	
	e.preventDefault();
  e.stopPropagation();
  $(this).toggleClass('expanded');
  $('#'+$(e.target).attr('for')).prop('checked',true);


});
$(document).click(function() {
  $('.dropdown-el').removeClass('expanded');

});



$("button").click(function() {

	if ($('#vezeto').is(':checked')) {
		$("#szerepkor").val('Vezető');
	} else if ($('#ugyfelszolgalat').is(':checked')) {

		$("#szerepkor").val('Ügyfélszolgálat');
	} else if ($('#fejleszto').is(':checked')) {

		$("#szerepkor").val('Fejlesztő');
	} else if ($('#tesztelo').is(':checked')) {

		$("#szerepkor").val('Tesztelő');
	} else if ($('#hardverszerelo').is(':checked')) {

		$("#szerepkor").val('Hardverszerelő');
	} else {

		$('#myModal').modal('toggle');
	}

});


})(jQuery);