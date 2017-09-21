
$(function(){

    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form()
            , layer = layui.layer
            , layedit = layui.layedit
        var form = layui.form();

        //监听提交
        form.on('submit(edit)', function (data) {
            var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //提交表单
            var data = $('#editImportInfoForm').serialize();

            $.ajax({
                type: 'post',
                url: '/importInfo/editMemo',
                data: data,
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(index2); //关闭当前弹层
                        layer.msg('保存成功!');
                        setTimeout(function () {
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

