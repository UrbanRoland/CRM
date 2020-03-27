
   
 $( function () {
	 $(" button").on ("click", function () {
		   var pass1 = document.getElementById('reg1').value;
		   var pass2 =  document.getElementById('reg2').value;
		 if(pass1==pass2){
			 
		 }else{
			alert('A jelszavak nem egyeznek meg!');
		 }
		 
	 	}) ;
	 }) ;
