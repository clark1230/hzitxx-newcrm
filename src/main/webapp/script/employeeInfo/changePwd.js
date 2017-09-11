/**
 * Created by xianyaoji on 2017/3/21.
 */


$(function(){
    var index = parent.layer.getFrameIndex(window.name);
    $("#cancel").click(function(){
        parent.layer.close(index);
    });
    var count =0;
    //到服务器中校验原密码是否正确
    $("[name='password0']").blur(function(){
        if($(this).val()==''){
            layer.msg('请输入原密码!');
            $(this).css("border","1px solid red");
            return false;
        }
        $.get("/employeeInfo/checkPwd?userId="+$("[name='userId']").val()+"&password="+$(this).val(),function(result){
              if(result.code==200){
                  layer.msg(result.msg);
                  count++;
              } else{
                  layer.msg(result.msg);
                  $("[name='password0']").css("border","1px solid red");
              }
              if(count==1){
                  $("#edit").removeClass("layui-btn layui-btn-small edit-disable").addClass("layui-btn layui-btn-small");
              }
        });
    });
    //校验新密码
    $("[name='password2']").change(function(){
        if($(this).val()==''){
            layer.msg('请确认信密码!');
            $("[name='password2']").css('border','1px solid red');
            return false;
        }
        if($(this).val() == $('[name="password"]').val()){
            if(count==1){
                $("#edit").removeClass("layui-btn layui-btn-small edit-disable").addClass("layui-btn layui-btn-small");
            }
        }else{
            layer.msg('两次密码不一致!');
            $("[name='password']").css('border','1px solid red');
            $("[name='password2']").css('border','1px solid red');
            return false;
        }
    });
    
    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form()
            , layer = layui.layer;
        //自定义验证规则
        form.verify({
            password: function (value) {
                if (value.length == 0) {
                    return '请输入原密码!';
                }
            },
            password1:function(value){
                if(value==''|| value<=0 && value>=120){
                    return '请输入新密码!';
                }
            },
            password2: function (value) {
                if (value.length == 0) {
                    return '请确认新密码';
                }
            }
        });
        //监听提交
        form.on('submit(edit)', function (data) {
            //loading层
            var index2 = layer.load(1, {time:15*1000},{shade: [0.1, '#fff'] });
            //校验表单
            //提交表单
            $.post('/employeeInfo/edit',$('#personInfoForm').serialize(),function(result){
                if (result.code == 200) {
                    layer.close(index2); //关闭当前弹层
                    layer.msg(result.msg);
                    window.setTimeout(function () {
                        parent.layer.close(index);//关闭层
                    }, 1000);
                } else {
                    layer.msg(result.msg);
                    layer.close(index2); //关闭当前弹层
                }
            });
        });
    });
});