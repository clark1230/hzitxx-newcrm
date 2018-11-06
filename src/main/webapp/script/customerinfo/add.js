/**
 * Created by xianyaoji on 2017/2/12.
 */

$(function(){
    var zixunLayVerify =['realName','sex','age','nativePlace','tel',
        'educationBg','customerState','customerLevel', 'targetSkill','userId','guandan','isMarket'];
    var yunYingVerify = ['realName','tel','recruitChannel','introducer'];
    var roleName = $('[name="roleName"]').val();//获取角色名称
    console.log(roleName);
    if(roleName.indexOf('运营')!=-1){
        $('[name="isYunYing"]').val(1);
        $('#yunYing').removeAttr('style');
         for(var i =0;i<yunYingVerify.length ;i++){
             showChectMsg(yunYingVerify[i]);
         }
    } else if(roleName.indexOf('管理员') !=-1 || roleName.indexOf('总经理')!=-1){
        for(var i=0;i<zixunLayVerify.length;i++){
               showChectMsg(zixunLayVerify[i]);
        }
        $('#yunYing').removeAttr('style');
    }  else{
        for(var i=0;i<zixunLayVerify.length;i++){
            showChectMsg(zixunLayVerify[i]);
        }
    }

    function showChectMsg(msg){
        $('[name='+msg+']').attr('lay-verify',msg);
        var html = $('[for='+msg+']').html();
        $('[for='+msg+']').html(html+'<span style="color:red;font-size:18px;">*</span>');
    }
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    if(index <200){
        $("#cancel").css('display','inline');
    }
    $('#cancel').click(function(){
        parent.layer.close(index);//关闭层

    });
    //校验
    var $tel =$("input[name='tel']");
    var $realName = $("input[name='realName']");
    $tel.blur(function(){
          checkExists();
    });
    $realName.blur(function(){
       if($tel.val()!=''){
           checkExists();
       }
    });
    function checkExists(){
        var telValue = $tel.val();
        var isAdd = true;
        if(telValue!='' && $realName.val()!=''){
            //到服务器中检测该用户是否存在!!
            $.get('/customerInfo/checkCustomerInfo?realName='+$realName.val()+"&tel="+$tel.val(),function(result){
                if(result.code==200){
                    layer.msg(result.msg);
                    isAdd = true;
                    $('#realName').css('border-color','lightgrey');
                    $('#add').css('pointer-events','visible').css('background-color','#0099FF');
                }else{
                    isAdd=false;
                    $('#realName').css('border-color','red');
                    $('#add').css('pointer-events','none').css('background-color','lightgrey');
                    layer.msg(result.msg);
                }
            });
        }
        return isAdd;
    }
    
    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form()
            , layer = layui.layer
            , layedit = layui.layedit
        form.on('select(customerState)', function(data) {
            if(data.value==34){
                layer.alert('注意，选择为<span style="color:red;">已流失</span>，会导致该数据从您的列表中消失，请慎重操作!');
            }
        });
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则
        form.verify({
            realName: function (value) {
                if (value.length == 0) {
                    return '请输入学员名称!';
                }
            },

            sex: function (value) {
                var i = 0;
                i++;
                if (i == 2) {
                    return '请选择性别!';
                }
            },
            nativePlace: function (value) {
                if (value == '') {
                    return '请输入籍贯!';
                }
            },
            tel: function (value) {
                var isMob = /^(11|12|13|14|15|16|17|18|19)[0-9]{9}$/;
                if (value == '') {
                    return '请输入电话号码!'
                } else if (!isMob.test(value)) {
                    return '电话号码格式不正确!'
                }
            },
            educationBg: function (value) {
                if (value == '') {
                    return '请选择学历!';
                }
            },
            customerState: function (value) {
                if (value == '') {
                    return '请选择学员状态!';
                }
            },
            customerLevel: function (value) {
                if (value == '') {
                    return '请选择学员级别!';
                }
            },
            targetSkill: function (value) {
                if (value == '') {
                    return '请选择目标技能!';
                }
            },
            userId: function (value) {
                if (value == '') {
                    return '请选择咨询师!';
                }
            },
            guandan: function (value) {
                if (value == '') {
                    return '请选择关单人!';
                }
            },
            recruitChannel: function (value) {
                if (value == '') {
                    return '请选择应聘渠道!';
                }
            },
            introducer: function (value) {
                if (value == '') {
                    return '请选择邀约人!';
                }
            }
            , content: function (value) {
                layedit.sync(editIndex);
            }
        });
        //监听提交
        form.on('submit(add)', function (data) {
            var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //校验表单
            //提交表单
            $.ajax({
                type: 'post',
                url: '',
                data: $('#addCustomerInfoForm').serialize(),
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(index2); //关闭当前弹层
                        layer.msg('保存成功!');
                        window.setTimeout(function () {
                           parent.layer.close(index);//关闭层
                        }, 1000);
                    } else {
                        layer.msg('保存失败!');
                        layer.close(index2); //关闭当前弹层
                    }
                }
            });
        });
    });
});
