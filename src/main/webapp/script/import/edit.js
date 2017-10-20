/**
 * Created by xianyaoji on 2017/2/12.
 */

$(function(){
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    if(index <200){
        $("#cancel").css('display','inline');
    }
    $('#cancel').click(function(){
        parent.layer.close(index);//关闭层

    });
    //校验
    var $tel =$("input[name='tel']");
    $tel.blur(function(){
        checkExists();
    });
    function checkExists(){
        var telValue = $tel.val();
        var oldtelValue = $("#oldtel").val();
        var companyIdValue = $("#companyId").val();
        var isAdd = true;
        if(telValue!='' && telValue != oldtelValue){
            //到服务器中检测该手机号是否存在!!
            $.get('/import/checkImportInfo?tel='+telValue+"&companyId="+companyIdValue,function(result){
                if(result.code==300){
                    layer.msg("该手机号可以录入！");
                    isAdd = true;
                    $('#tel').css('border-color','lightgrey');
                    $('#edit').css('pointer-events','visible').css('background-color','#0099FF');
                }else{
                    isAdd=false;
                    $('#tel').css('border-color','red');
                    $('#edit').css('pointer-events','none').css('background-color','lightgrey');
                    layer.msg("该手机号已存在，请核对！");
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
                    return '请输入姓名!';
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
            memo: function (value) {
                if(value.length > 100){
                    return '备注长度超出范围，请适当删减!'
                }
            }
        });
        //监听提交
        form.on('submit(edit)', function (data) {
            var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //alert($('#editImportInfoForm').serialize());
            //校验表单
            //提交表单
            $.ajax({
                type: 'post',
                url: '/import/editImportInfo',
                data: $('#editImportInfoForm').serialize(),
                success: function (result) {
                    if (result.code == 200) {
                        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
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
