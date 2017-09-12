/**
 * Created by 吕游 on 2017/9/11.
 */
$(function(){
    $("#import").click(function(){
        var formData = new FormData($( "#importForm" )[0]);
        $.ajax({
            url: '/import' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (resp) {
                console.log(resp);
            },
            error: function (resp) {
                console.log(resp);
            }
        });

        //$.post("/import",$("#importFrom").serialize(),function(resp){
        //    console.log(resp);
        //},"JSON");
    });

});