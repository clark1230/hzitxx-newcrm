/**
 * Created by xianyaoji on 2017/2/26.
 */
/*
 添加用户信息
 */
var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
$(function(){
    $("#cancel").click(function(){
        parent.layer.close(index);
    });
    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form(), layer = layui.layer, layedit = layui.layedit ;
        /*form.on('select(companyId)', function (data) {
         if(data.value!=''){
         //到服务器中获取数据
         //根据pid获取部门信息
         $.get('/employeeInfo/getDept?id='+data.value,function(result){
         //拼接!!
         //清空 部门select标签中的内容
         var $deptId = $("#deptId");
         $deptId.children().remove();
         var option = null;
         $.each(result,function(item){
         option = $('<option value="'+result[item].id+'">'+result[item].name+'</option>');
         $deptId.append(option);
         });
         form.render();
         });

         }
         });*/

        /*form.on('select(deptId)', function (data) {
            var value = $("#companyId").children("option:selected").val();
            if(value == ''){
                layer.msg('请选择公司!');
                $("#companyId").css('border-color','red');
                return false;
            }
        });*/

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则
        form.verify({
            name: function (value) {
                if (value.length == 0) {
                    return '请输入学员名称!';
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
            customerLevel: function (value) {
                if (value == '') {
                    return '请选择学员级别!';
                }
            },
            companyId: function (value) {
                if (value == '') {
                    return '请选择公司!';
                }
            },
            deptId:function(value){
                if(value==''){
                    return '请选择部门!';
                }
            }

        });
        //监听提交
        form.on('submit(edit)', function (data) {
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //校验表单
            //提交表单
            $.ajax({
                type: 'post',
                url: '/employeeInfo/edit',
                data: $('#editCustomerInfoForm').serialize(),
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
