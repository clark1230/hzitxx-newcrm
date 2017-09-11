/**
 * Created by xianyaoji on 2017/2/25.
 */
$(function(){
    var index = parent.layer.getFrameIndex(window.name);
    $("#cancel").click(function(){
       parent.layer.close(index);
    });

   layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form()
            , layer = layui.layer;
        //自定义验证规则
        form.verify({
            name: function (value) {
                if (value.length == 0) {
                    return '请输入姓名!';
                }
            },
            wechatNo:function(value){
                if(value==''|| value<=0 && value>=120){
                    return '请输入微信号!';
                }
            },
            sex: function (value) {
                var i = 0;
                i++;
                if (i == 2) {
                    return '请选择性别!';
                }
            },
            tel: function (value) {
                var isMob = /1[2345678]\d{10}$/;
                if (value == '') {
                    return '请输入电话号码!'
                } else if (isMob.test(value)) {
                    return '电话号码格式不正确!'
                }
            },

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
    /**
     * 修改密码!!
     */
   $("#changePwd").click(function(){
       layer.open({
           type: 2,
           title: '修改密码',
           shadeClose: true,
           shade: 0,
           maxmin: true,
           area: ['400px', '350px'],
           content: '/employeeInfo/changePwd?userId='+$('[name="userId"]').val()//iframe的url
       });
   });
    /**
     * 服务器随机生成密码!!
     */
   $("#changePwdByEmail").click(function(){

          //用户编号,邮箱
       var userId = $('[name="userId"]').val();
       var email =  $('[name="email"]').val();
       if(email ==''){
           layer.alert('<span style="color:red;">请完善邮箱信息,才能把随机密码发送到您的邮箱中!</span>');
           return false;
       }
       var index2 = layer.load(1, {time:20*1000},{shade: [0.1, '#fff'] });
       $.get("/employeeInfo/resetPwd?userId="+userId+"&email="+email,function(result){
           if(result.code ==200){
               layer.close(index2); //关闭当前弹层
               layer.msg(result.msg);
          } else {
               layer.close(index2); //关闭当前弹层
               layer.msg(result.msg);
           }
       });
   });

}) ;

