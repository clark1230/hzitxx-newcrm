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
            layer.msg('请选择渠道号！',{icon: 2});
            return;
        }else{
            if($("#file-5").val() == ""){
                layer.msg('文件不能为空！',{icon: 2});
                return;
            }else{
                var file = $("#file-5").val();
                var fileName = getFileName(file);
                //alert(fileName);
                var selectText = $("#selectID").find("option:selected").text();

                //***************导入文件名的处理********************
                if(fileName.indexOf("51job") > -1){
                    fileName = fileName.replace("51job", "前程无忧");
                }
                if(fileName.indexOf("赶集") > -1){
                    fileName =  fileName.replace("赶集", "赶集网");
                }
                if(fileName.indexOf("58") > -1){
                    fileName =  fileName.replace("58", "58同城");
                }
                //**************************************************

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
                            if(resp.code == 300){
                                layer.msg('导入失败，请稍后再试！',{icon: 5});
                            }else if(resp.code == 200){
                                var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引

                                layer.msg('导入成功！',{icon: 6});

                                setTimeout(function(){
                                    parent.layer.close(index);
                                },1000);
                            }

                        },
                        error: function (resp) {
                            console.log(resp);
                            layer.msg('导入失败，请稍后再试！',{icon: 5});
                        }
                    });
                }else{
                    //alert("不包含");
                    layer.msg('请检查上传文件与渠道是否匹配!',{icon: 2});
                }
            }

        }
    });

});