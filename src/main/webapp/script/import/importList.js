/**
 * Created by xianyaoji on 2017/2/12.
 */

$(function () {

    var $table;
    var searchParams;
    var url = "/importInfo/listData";


    var json ={
        url: url,
        toolbar: '#toolbar',                //工具按钮用哪个容器
        pagination: true,                   //是否显示分页（*）
        striped: true,                      //是否显示行间隔色
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client学员端分页，server服务端分页（*）
        idField: 'customerId',
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: [10, 20, 30, 50, 100],        //可供选择的每页的行数（*）
        clickToSelect: true,                //是否启用点击选中行
        smartDisplay: false, // 智能显示 pagination 和 cardview 等
        exportDataType: "basic",              //basic', 'all', 'selected'.
        showRefresh: true,
        showColumns: true,
        detailView: true,
        paginationLoop:false,
        detailFormatter: function (index, row) {
            var lastTime = "---";
            if(row.lastTime === null || row.lastTime === ""){
                lastTime = "---";
            }else{
                lastTime = new Date(parseInt(row.lastTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
            }
            var createTime = new Date(parseInt(row.createTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
            var sex = '';
            if(row.sex == '1'){
                sex= '男';
            } else if(row.sex=='2'){
                sex='女';
            }else{
                sex='未知';
            }
            var value = '<ul style="margin: 0;padding:0;">' +
                '<li class="customerinfo-li">学员名称:' + row.realName + '</li>' +
                '<li class="customerinfo-li">性别:' + sex + '</li>' +
                '<li class="customerinfo-li">年龄:' + (row.age==null?'----':row.age) + '</li>' +
                '<li class="customerinfo-li">籍贯:' + (row.nativePlace==null?'----':row.nativePlace) + '</li>' +
                '<li class="customerinfo-li">电话号码:' + row.tel + '</li>' +
                '<li class="customerinfo-li">微信号:' + (row.wechatNo==null ?"----":row.wechatNo) + '</li>' +
                '<li class="customerinfo-li">qq号:' + (row.qq==null?'----':row.qq) + '</li>' +
                '<li class="customerinfo-li">学历:' + (row.educationBgMsg==null ?'----':row.educationBgMsg) + '</li>' +
                '<li class="customerinfo-li">毕业时间:' + (row.graduateTime==null?'----':row.graduateTime) + '</li>' +
                '<li class="customerinfo-li">毕业学校:' + (row.graduateFrom==null ?'----':row.graduateFrom) + '</li>' +
                '<li class="customerinfo-li">专业:' + (row.majorIn ==null ?'----':row.majorIn )+ '</li>' +
                '<li class="customerinfo-li">工作年限:' + (row.workAge==null ?'----':row.workAge) + '</li>' +
                '<li class="customerinfo-li">工作经历:' + (row.workExperience==null ? '----':row.workExperience) + '</li>' +
                '<li class="customerinfo-li">应聘渠道:' + (row.recruitChannelMsg==null ?'----':row.recruitChannelMsg) + '</li>' +
                '<li class="customerinfo-li">职位:' + (row.job== null?'----':row.job) + '</li>' +
                '<li class="customerinfo-li">教育培训经历:' + (row.educateExperience==null ?'----':row.educateExperience) + '</li>' +
                '<li class="customerinfo-li">学员状态:' + (row.customerStateMsg==null?'----':row.customerStateMsg) + '</li>' +
                '<li class="customerinfo-li">学员级别:' + (row.customerLevelMsg==null ?'----':row.customerLevelMsg) + '</li>' +
                '<li class="customerinfo-li">咨询师:' + (row.userIdMsg==null?'----':row.userIdMsg) + '</li>' +
                '<li class="customerinfo-li">应聘职位:' + (row.applyJob==null ?'----':row.applyJob) + '</li>' +
                '<li class="customerinfo-li">关单人:' + (row.guandaMsg==null?'----':row.guandaMsg) + '</li>' +
                '<li class="customerinfo-li">邀约人:' + (row.introducerMsg ==null?'----':row.introducerMsg) + '</li>' +
                '<li class="customerinfo-li">录入时间:' + (createTime==null ? '----':createTime) + '</li>' +
                '<li class="customerinfo-li">最后跟进时间:' + (lastTime==null ?'----':lastTime) + '</li>' +
                '<li class="customerinfo-li">所属公司:' + (row.companyIdMsg==null ?'----':row.companyIdMsg) + '</li>' +
                '<li class="customerinfo-li">备注:' + (row.memo==null?'----':row.memo) + '</li>' +
                '<li class="customerinfo-li">简历类型:' + (row.cvTypeMsg==null?'----':row.cvTypeMsg) + '</li>' +
                '</ul>';
            /* value = '<table>' +
             '' +
             '</table>';*/
            return value;
        },

        silent: true, // 刷新事件必须设置

        onDblClickRow: function (row, $element) {//双击事件

            layer.open({
                type: 2,
                title: '添加备注',
                shadeClose: true,
                shade:0,
                maxmin: true,
                area:["550px","408px"],
                content:['/import/editMemo?customerId='+row.customerId,'on'],
                end: function () {
                    $("#table").bootstrapTable("refresh"); //刷新

                }
            });

        },

        onClickCell:function(field, value, row, $element){
            //小tips
            //边缘弹出
            if(field =='memo'){
                if(value=='null '|| value==null || value==''){
                    value ="暂无备注!";
                };
                layer.open({
                    type: 1
                    ,offset: 'rb' //具体配置参考：offset参数项   rb:右下
                    ,title:'备注(弹窗3秒后自动关闭)'
                    ,content: '<div style="padding: 20px 20px;color:orange;">'+value+'</div>'
                    ,btn: '关闭'
                    ,time:3000
                    ,btnAlign: 'c' //按钮居中
                    ,shade: 0 //不显示遮罩
                    ,yes: function(){
                        layer.closeAll();
                    }
                });
            }
        },

        columns: [{
            filed: 'customerId',
            title: '编号',
            checkbox: true
        }, {
            field: 'realName',
            title: '姓名',
            width: 60
        }, {
            field: 'sex',
            title: '性别',
            align:'center',
            width: 20,
            formatter: function (value, row, index) {
                var sex;
                if (row.sex == 1) {
                    sex = '<img style="width:20px;height:16px;" alt="男" src="/assets/images/男.png"/>';
                } else if (row.sex == 2) {
                    sex = '<img style="width:20px;height:16px;" alt="女" src="/assets/images/女.png"/>';
                }else{
                    sex = '<img style="width:20px;height:16px;" alt="未知" src="/assets/images/未知.png"/>';
                }
                return sex;
            }
        }, {
            field: 'tel',
            title: '电话号码',
            align:'center',
            width: 60
        }, {
            field: 'age',
            title: '年龄',
            align: 'center',
            width: 10
        }, {
            field: 'nativePlace',
            title: '籍贯',
            width: 120,
            visible: false
        }, {
            field: 'wechatNo',
            title: '微信号',
            width:60,
            visible: false
        }, {
            field: 'qq',
            title: 'QQ',
            width:60,
            visible: false
        }, {
            field: 'educationBgMsg',
            title: '学历',
            width: 40,
            formatter: function (value, row, index) {
                var educationBgMsg = row.educationBgMsg;
                /*if(row.educationBgMsg=='数据字典'){
                 educationBgMsg = '--';
                 }*/
                return educationBgMsg;
            }
        }, {
            field: 'graduateTime',
            title: '毕业时间',
            visible:false,
            width: 80
        }, {
            field: 'graduateFrom',
            title: '毕业学校',
            width: 110
        }, {
            field: 'majorIn',
            title: '专业',
            width: 80,
            visible: false
        }, {
            field: 'workAge',
            title: '工作年限',
            width: 40,
            visible: false
        }, {
            field: 'workExperience',
            title: '工作经历',
            width: 60,
            visible: false
        }, {
            field: 'job',
            title: '从事职业',
            width: 70,
            visible: false
        }, {
            field: 'userIdMsg',
            title: '咨询师',
            width: 80,
            visible: false
        }, {
            field: 'educateExperience',
            title: '教育培训经历',
            visible: false,
            width: 70,
            width: 60
        }, {
            field: 'guandaMsg',
            title: '关单人',
            visible: false,
            width: 80
        }, {
            field: 'customerStateMsg',
            title: '学员状态',
            width: 40,
            visible: false
        }, {
            field: 'customerLevelMsg',
            title: '学员级别',
            align: 'center',
            width: 20,
            visible: false,
            formatter:function(value,row,index){
                var customerLevelMsg =row.customerLevelMsg;
                if(customerLevelMsg=='数据字典' || customerLevelMsg =='null'){
                    customerLevelMsg = '--';
                }else if(customerLevelMsg =='A'){
                    customerLevelMsg = '<span style="margin:0 auto;width:30px;height:16px;background-color: #FF0066;display: block">'+row.customerLevelMsg+'</span>';
                }else if(customerLevelMsg =='B'){
                    customerLevelMsg = '<span style="margin:0 auto;width:30px;height:16px;background-color: #FF6666;display: block">'+row.customerLevelMsg+'</span>';
                }else if(customerLevelMsg == 'C'){
                    customerLevelMsg = '<span style="margin:0 auto;width:30px;height:16px;background-color: #FF9966;display: block">'+row.customerLevelMsg+'</span>';
                }else if(customerLevelMsg == 'D'){
                    customerLevelMsg = '<span style="margin:0 auto;width:30px;height:16px;background-color: #FFCC66;display: block">'+row.customerLevelMsg+'</span>';
                }
                return customerLevelMsg;
            }
        }, {
            field: 'applyJob',
            title: '求职岗位',
            width: 70
        }, {
            field: 'introducerMsg',
            title: '邀约人',
            visible: true,
            align:'center',
            width: 80,
            formatter:function(value,row,index){
                var name = row.introducerMsg;
                if(name == $('[name="name"]').val()){
                    name ='<span style="background-color:orange;color:white;display: block;">'+name+'</span>';
                }else if(name =='null' || name ==''){
                    name ='-';
                }
                return name;
            }
        }, {
            field: 'recruitChannelMsg',
            title: '应聘渠道',
            visible: true,
            width: 60
        }, {
            field: 'createTime',
            title: '录入时间',
            width: 150,
            formatter: function (value, row, index) {
                return new Date(parseInt(row.createTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ")
            },
            visible: false
        }, {
            field: 'lastTime',
            title: '最后跟进时间',
            width: 100,
            formatter: function (value, row, index) {
                if(row.lastTime === null || row.lastTime === ""){
                    return "---";
                }
                return new Date(parseInt(row.lastTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
            }
        }, {
            field: 'companyIdMsg',
            title: '所属公司',
            visible: false,
            width: 100
        }, {
            field: 'memo',
            title: '备注',
            width: 100,
            formatter: function (value, row, index) {
                var memo = (typeof(row.memo) == 'null') ? "---" : row.memo + "";
                if (memo.length > 10) {
                    memo = row.memo.substr(0, 10) + "....";
                }else if(memo == null || memo =='null' || memo ==''){
                    memo = '---'
                } else {
                    memo = row.memo;
                }
                return '<span class="memo">' + memo + '</span>';
            }
        },{
            field:'isMarket',
            title:'会销',
            align:'center',
            width:20,
            visible:false,
            formatter:function(value,row,index){
                var isMarket = row.isMarket;
                if(isMarket  == '0'){
                    isMarket = '<i style="font-size: 16px;color:green;" class="layui-icon"></i>';
                } else if(isMarket =='1'){
                    isMarket ='<i style="font-size: 16px;color:red;" class="layui-icon">ဇ</i>';
                } else{
                    isMarket = '---';
                }
                return isMarket;
            }
        },{
            field:'cvTypeMsg',
            title:'简历类型',
            align:'center',
            visible: true,
            width:20
        }],queryParams: function getParams(params){

            var  tmp = {
                offset:(this.pageNumber)*this.pageSize,
                limit:this.pageSize,
                condition:$('#searchParam option:selected').val(),
                value:$("#searchValue").val()
                /*sort:this.sortName,
                 order:this.sortOrder*/
            };
            searchParams =tmp;
            return tmp;
        }

    }

    showCustomerInfo();
    function showCustomerInfo() {
        //getUrl();
        $("#table").bootstrapTable('destroy');//先要将table销毁，否则会保留上次加载的内容
        $table = $('#table').bootstrapTable(json);
    }

    window.setInterval(function(){
        layer.msg('开始从服务器获取最新的数据!');
        showCustomerInfo;
    },10*60*1000);


    //****************************搜索*****************************************
    $("#searchImport").click(function(){
        var searchParam = $('#searchParam option:selected').val();
        var searchValue = $("#searchValue").val();

        if(searchParam=='-1'){
            layer.msg('请选择搜索条件!');
            $('#searchParam').css('border','1px solid red');
            return false;
        }else{
            $('#searchParam').css('border','1px solid lightgrey');
        }
        if(searchValue==''){
            layer.msg('请输入搜索值!');
            $('#searchValue').css('border','1px solid red');
            return false;
        }else{
            $('#searchValue').css('border','1px solid lightgrey');
        }
        var value= url+"?offset="+searchParams.offset+"&limit="+searchParams.limit+"&condition="+searchParam+"&value="+searchValue;

        $.get(value,function(result){
            $("#table").bootstrapTable("load",result);
            var tmp = {
                offset:searchParams.offset,
                limit:searchParams.limit,
                condition:searchParam,
                value:searchValue //,

            };
            searchParams = tmp;
        });

    });


    /*------------------回收站--------------------------------*/
    $("#remove").click(function () {
        var $selectData = $table.bootstrapTable('getAllSelections');
        var importIdArr = [];
        if ($selectData.length < 1) {
            layer.msg('请选择要丢进回收站的学员!');
        } else {
            for (var item in $selectData) {
                importIdArr.push($selectData[item].customerId);
            }
            var data = "importIdArr=" + importIdArr;
            layer.msg(data);
            //加载层
            //var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            layer.confirm('是否要把所选数据丢进回收站!', {
                btn: ['确定','取消'] //按钮
            }, function(){
                removeData();
            }, function(){
                layer.close(index2); //关闭当前弹层
                layer.msg('已经取消了!');

            });
            function  removeData() {
                $.ajax({
                    type: 'post',
                    url: '/importInfo/remove',
                    data: data,
                    success: function (result) {
                        layer.close(index2); //关闭当前弹层
                        if (result.code == 200) {
                            layer.msg("操作成功!");
                            $("#table").bootstrapTable("refresh"); //刷新
                        } else {
                            layer.msg('操作失败!');
                        }

                    }
                });
            }
        }
    });


    //****************************刷新*****************************************
    $("#searchRefresh").click(function(){
        $.get(url,function(result){
            if(typeof(result)=='object'){
                //清空查询条件
                $('#searchValue').val('');
                $('#searchParam').children('option[value="-1"]').attr("selected","selected");
                $table.bootstrapTable('load', result);
                layer.msg('已重新获取数据!');
            }
        });
    }) ;

    /**
     * 报表导入
     */
    $("#import").click(function(){
        layer.open({
            type:2,
            title:'导出数据',
            shadeClose:true,
            shade:0,
            maxmin:true,
            area:["750px","558px"],
            content:['/import/importPage','on'],
            end:function(layer,index){
                $("#table").bootstrapTable("refresh"); //刷新
            }

        })
    });


});