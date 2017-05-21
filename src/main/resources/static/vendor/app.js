$("#addToCart").click(function(event) {

			var data = {}
			data["productId"] = $("#productId").val();
	       data["quantity"] = $("#quantity").val();



			$.ajax({
		             type: "POST",
		             contentType: "application/json",
		             url: "http://localhost:8080/addToCart",
		             data: JSON.stringify(data),
		             dataType: 'text',
		             timeout: 600000,
		             success: function (data) {
		             if(data === "SUCCESS"){
		                swal({
                          title: "Added to cart!",
                          text: "Are you sure you dont want to give us more money?",

                          showCancelButton: true,
                          confirmButtonColor: "#DD6B55",
                          confirmButtonText: "Go to cart!",
                          cancelButtonText: "No I wanna buy more stuff!",
                          closeOnCancel: true
                        },
                        function(isConfirm){
                          if (isConfirm) {
                            window.location.href = '/cart';
                          }
                        });
		             }else{
		             swal({
                       title: "Sorry!",
                       text: "Sorry we dont have that many in stock!",
                     });
		             }
		             },
		             error: function (e) {
		              swal({
                      title: "Whoops!",
                     text: "An error occured,please try again!",
                                         });
		                 //...
		             }
			});
event.preventDefault();

		});



	$("#addComment").click(function(event) {

    			var data = {}
    			data["productId"] = $("#productId").val();
    	       data["text"] = $("#text").val();



    			$.ajax({
    		             type: "POST",
    		             contentType: "application/json",
    		             url: "http://localhost:8080/addComment",
    		             data: JSON.stringify(data),
    		             dataType: 'json',
    		             timeout: 600000,
    		             success: function (data) {
    		             if(data["success"] == "tooshort"){
    		             swal({
                          title: "Whoops!",
                          text: "The comment has to have atleast 10 characters!",
                                                                     });
    		             }else if(data["success"] == "success"){
                            let HTML = '<div id ="newComment"class="col s12 l4">';
                            HTML += '<blockquote>';
                            HTML += '<p>';
                            HTML += data["text"];
                            HTML += '</p>';
                            HTML += '<small>';
                            HTML += data["userName"];
                            HTML += '</small>';
                            HTML += '</blockquote>';
                            HTML += '</div>';
                             $('#comments').prepend(HTML);
                             $('#text').val("");
                             document.getElementById("newComment").scrollIntoView()
                             $('#newComment').effect("highlight", {}, 5000);
    		             }else{
    		             swal({
                         title: "Whoops!",
                         text: "an error occured,please try again!",
                                                                                              });
    		             }


    		             },
    		             error: function (e) {
    		             console.log(e);
    		              swal({
                          title: "Whoops!",
                         text: "An error occured,please try again!",
                                             });
    		                 //...
    		             }
    			});
    event.preventDefault();

    		});