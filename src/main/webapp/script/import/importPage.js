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
            layer.msg('请选择渠道号！',{icon: 0});
            return;
        }else{
            var cvType = $("input[name='cvType']:checked").attr('title');
            if(cvType === undefined){
                layer.msg('请选择导入简历类型！',{icon: 0});
                return ;
            }else {
                if ($("#file-5").val() == "") {
                    layer.msg('文件不能为空！', {icon: 0});
                    return;
                } else {
                    var file = $("#file-5").val();
                    var fileName = getFileName(file);
                    var selectText = $("#selectID").find("option:selected").text();
                    if(fileName.indexOf(cvType) === -1){
                        layer.msg('请检查所选简历类型与文件是否匹配！', {icon: 0});
                        return ;
                    }else {
                        //***************导入文件名的处理********************
                        if (fileName.indexOf("51job") > -1) {
                            fileName = fileName.replace("51job", "前程无忧");
                        }
                        if (fileName.indexOf("赶集") > -1) {
                            fileName = fileName.replace("赶集", "赶集网");
                        }
                        if (fileName.indexOf("58") > -1) {
                            fileName = fileName.replace("58", "58同城");
                        }
                        //**************************************************

                        if (fileName.indexOf(selectText) > -1) {
                            //禁用导入按钮
                            $('#import').prop('disabled',true);
                            $('#import').css('background-color','lightgrey');
                            var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
                            var formData = new FormData($("#importForm")[0]);
                            $.ajax({
                                url: '/import',
                                type: 'POST',
                                data: formData,
                                async: false,
                                cache: false,
                                contentType: false,
                                processData: false,
                                success: function (resp) {
                                    if (resp.code == 300) {
                                        layer.msg('导入失败，请稍后再试！', {icon: 2});
                                        layer.close(index2); //关闭当前弹层
                                        $('#import').css('background-color','#1BA193');
                                        $('#import').prop('disabled',false);
                                    } else if (resp.code == 200) {
                                        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
                                        layer.msg(resp.msg, {icon: 1});
                                        layer.close(index2); //关闭当前弹层
                                        setTimeout(function () {
                                            parent.layer.close(index);
                                        }, 1500);
                                    }

                                },
                                error: function (resp) {
                                    layer.close(index2); //关闭当前弹层
                                    $('#import').prop('disabled',false);
                                    $('#import').css('background-color','#1BA193');
                                    layer.msg('对不起，您没有权限导入数据!', {icon: 2});

                                }
                            });
                        } else {
                            //alert("不包含");
                            layer.msg('请检查上传文件与渠道是否匹配!', {icon: 2});
                        }
                    }
                }
            }
        }
    });

});