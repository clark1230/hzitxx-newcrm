/**
 * Created by xianyaoji on 2017/3/29.
 */

$(function(){
    var url = "/business/getAjaxData";
    var json ={
        url: url,
        toolbar: '#toolbar',                //工具按钮用哪个容器
        pagination: true,                   //是否显示分页（*）
        striped: true,                      //是否显示行间隔色
        sortable: true,                     //是否启用排序
        sortOrder: "desc",                   //排序方式
        sidePagination: "server",           //分页方式：client学员端分页，server服务端分页（*）
        idField: 'customerId',
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: [10, 20, 30, 50, 100],        //可供选择的每页的行数（*）
        clickToSelect: true,                //是否启用点击选中行
        smartDisplay: false, // 智能显示 pagination 和 cardview 等
        //exportDataType: "basic",              //basic', 'all', 'selected'.
        showRefresh: true,
        showColumns: true,
       // detailView: true,
        silent: true, // 刷新事件必须设置
        onDblClickRow: function (row, $element) {//双击事件
            //$('#hiddenCustomerId').val(row.customerId);
            //创量人员只能修改一次
            //获取邀约人信息和角色信息
           // var companyId  =
            
            layer.open({
                type: 2,
                title: '编辑学员信息',
                shadeClose: true,
                shade:0,
                maxmin: true,
                area: ['100%', '97%'],
                content: '/customerInfo/edit?customerId='+row.customerId+"&isYunYing=1",//iframe的url
                end: function () {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });

        },
        columns: [{
            filed: 'customerId',
            title: '编号',
            checkbox: true
        }, {
            field: 'realName',
            title: '学员姓名',
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
                    sex = '<img style="width:20px;height:16px;" alt="女" src="/assets/images/未知.png"/>';
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
            width: 10,
            visible:false

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
            width: 50,
            visible:false,
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
            width: 80,
            visible: false
        }, {
            field: 'graduateFrom',
            title: '毕业学校',
            width: 100,
            visible: false
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
            visible:true
        }, {
            field: 'educateExperience',
            title: '教育培训经历',
            visible: false,
            width: 70,
            width: 60
        }, {
            field: 'guandaMsg',
            title: '关单人',
            visible: true,
            width: 80
        }, {
            field: 'customerStateMsg',
            title: '学员状态',
            width: 40
        }, {
            field: 'customerLevelMsg',
            title: '学员级别',
            align: 'center',
            width: 20,
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
            field: 'targetSkillMsg',
            title: '目标技能',
            width: 70
        }, {
            field: 'introducerMsg',
            title: '邀约人',
            visible: true,
            align:'center',
            width: 80
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
            width: 150,
            formatter: function (value, row, index) {
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
            width: 80,
            formatter: function (value, row, index) {
                var memo = (typeof(row.memo) == 'null') ? "---" : row.memo + "";
                if (memo.length > 5) {
                    memo = row.memo.substr(0, 5) + "....";
                }else if(memo == null || memo =='null' || memo ==''){
                    memo = '---'
                } else {
                    memo = row.memo;
                }
                return '<span class="memo">' + memo + '</span>';
            },
        },{
            field:'isMarket',
            title:'会销',
            align:'center',
            width:20,
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
        }]
    }
   var  $table = $('#table').bootstrapTable(json);

    $("#span-add").click(function(){
          common("添加","/customerInfo/add?isYunYing=1",['100%', '95%'])
    });
    $("#span-edit").click(function(){
        var $selectData = $table.bootstrapTable('getAllSelections');
        if($selectData.length <1){
            layer.msg('请选择要修改的数据!');
            return false;
        } else if($selectData.length >1){
            layer.msg('所选数据大于1条!');
            return false;
        }else{
            common("编辑",'/customerInfo/edit?customerId='+10+'&isYunYing=1',['100%','95%']);
        }
    });
     function common(title,content,area){
         layer.open({
             type: 2,
             title: title,
             shadeClose: true,
             shade: 0,
             maxmin: true,
             area: area,
             content: [content, 'on'], //iframe的url
             end: function (layer, index) {
                 $("#table").bootstrapTable("refresh"); //刷新
             }
         });
     }
    $("#span-grant").click(function(){
        var $selectData = $table.bootstrapTable('getAllSelections');
        if($selectData.length <1){
            layer.msg('请选择要分配的数据!');
            return false;
        }
        common("分配咨询师","/business/grant?companyId="+$('[name="companyId"]').val()+"&customerId="+$selectData[0].customerId,['500px', '400px']);
    });                                                                                                   
});
