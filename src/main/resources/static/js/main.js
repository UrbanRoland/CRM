
(function ($) {
    "use strict";

    
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');
    var input2 = $('.validate-secondMessage .wrap-input100 .input100');
    $('.validate-form').on('submit',function(){
        var check = true;
       
        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email' || $(input).attr('name') == 'username') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

   
    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    $('.validate-form').on('submit',function(){
    	  //  $("button").click(function() {
    	       var check = true;

    	       $('.validate-secondMessage .wrap-input100').attr("data-validate","Jelszó kitöltése kötelező!");
    	        if($('#reg1').val()!=$('#reg2').val()){
    	        	 
    	     
    	        		$('.validate-secondMessage .wrap-input100').attr("data-validate","A két jelszó nem egyezzik meg!");
    	 	
    	        	 for(var i=0; i<input2.length; i++) {
    	                 
    	                     showValidate(input2[i]);
    	                     check=false;
    	             }
    	        	
    	        	return check;
    	       
    	        }

    	        
    	    });

})(jQuery);