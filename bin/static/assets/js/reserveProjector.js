/**
 * 
 */
$(function() {

    $("input,select").jqBootstrapValidation({
        preventSubmit: true,
        submitError: function($form, event, errors) {
            // additional error messages or events
        },
        submitSuccess: function($form, event) {
            event.preventDefault(); // prevent default submit behaviour
            // get values from FORM
            //alert(window.location.pathname);
            var url = window.location.pathname.split('/');
            var lastParam = url.length;
            //alert(url[lastParam-1]);
            var from = $("input#fromDate").val();
            var to= $("input#to").val();
            var team = $("select#team").val();
            //alert(from + to + team);
            //var firstName = name; // For Success/Failure Message
            // Check for white space in name for Success/Fail message\
            var reqData = "from="+ from +"&to=" + to + "&teamId=" + team;
            
            $.ajax({
                url: contextPath +"/make-reservation",
                type: "POST",
                data: reqData,
                cache: false,
                success: function(status) {
                	//alert(status);
                	
                	
                	var statusArray = status.split('@');
                	if(statusArray.length==1){
                		$('#success').html("<div class='alert alert-success'>");
                        $('#success > .alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                            .append("</button>");
                        $('#success > .alert-success')
                            .append("<strong>" + status + "</strong>");
                        $('#success > .alert-success')
                            .append('</div>');
                	}
                	else{
                		//alert(statusArray[1]);
                		var n = Number(statusArray[1]);
                		var date = new Date(n);
                		$('#success').html("<div class='alert alert-success'>");
                        $('#success > .alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                            .append("</button>");
                        $('#success > .alert-success')
                            .append("<strong>" + statusArray[0]+ " "+ date + "</strong>");
                        $('#success > .alert-success')
                            .append('</div>');
                	}

                    //clear all fields
                    $('#projectRequestForm').trigger("reset");
                    angular.element($("#pmController")).scope().getProjectorReservations();
                    angular.element($("#pmController")).scope().$apply();
                },
                error: function(data) {
                    
                	// Fail message
                    $('#success').html("<div class='alert alert-danger'>");
                    $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                        .append("</button>");
                    $('#success > .alert-danger').append("<strong>" + data.responseText + "</strong>");
                    $('#success > .alert-danger').append('</div>');
                    //clear all fields
                    $('#contactForm').trigger("reset");
                },
            })
        },
        filter: function() {
            return $(this).is(":visible");
        },
    });

    $("a[data-toggle=\"tab\"]").click(function(e) {
        e.preventDefault();
        $(this).tab("show");
    });
});


/*When clicking on Full hide fail/success boxes */
$('#name').focus(function() {
    $('#success').html('');
});
