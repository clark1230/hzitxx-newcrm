/**
 * Created by 吕游 on 2017/9/11.
 */
$(function(){

    //获取上传文件名称
    function getFileName(o){
        var pos=o.lastIndexOf("\\");
        return o.substring(pos+1);
    }

    $("#import").click(function(){
        if($("#selectID").val() == ""){
                $("#selectError").html("请选择渠道号！");
        }else{
            var file = $("#file-5").val();
            var fileName = getFileName(file);
            //alert(fileName);
            var selectText = $("#selectID").find("option:selected").text();

            if(fileName.indexOf(selectText) > -1){
                //alert("包含");
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

            }else{
                //alert("不包含");
                layer.msg('请检查上传文件是否有误');
            }
        }
    });
});