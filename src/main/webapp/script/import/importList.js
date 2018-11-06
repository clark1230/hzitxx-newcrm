/**
 * Created by xianyaoji on 2017/2/12.
 */

$(function () {
    $("#introducer").hide();
    $("#educationBg").hide();
    $("#cvType").hide();
    //页面加载时根据用户编号获取用户所能访问的按钮,移除隐藏样式
    $.get('/showButton/show?userId='+$("input[name='userId']").val(),function(result){
        var $span ="";
        $.each(result,function(item){
            var buttonResource =   result[item];
            if(buttonResource.indexOf("importInfo")!=-1){
                //显示隐藏的按钮
                //截取字符串
                var button  = buttonResource.substr(buttonResource.indexOf(":")+1,buttonResource.length);
                //layer.msg(button);
                $("#span-"+button).removeClass('span-hidden');
            }
        });
    });

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
        paginationShowPageGo:true,
        pageSize: 15,                       //每页的记录行数（*）
        pageList: [5,10,15, 20, 30, 50],        //可供选择的每页的行数（*）
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
            }
            var sendTime = "---";
            if(row.sendTime === null || row.sendTime === ""){
                sendTime = "---";
            }else{
                var mydate = new Date(row.sendTime);
                var year = mydate.getFullYear()+"";
                var month = mydate.getMonth()+1;
                if(month <= 9){
                    month = 0+""+month;
                }
                var day = mydate.getDate();
                if(day <= 9){
                    day = 0+""+day;
                }
                sendTime =  year+"-"+month+"-"+day;
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
                '<li class="customerinfo-li">学历:' + (row.educationBg) + '</li>' +
                '<li class="customerinfo-li">毕业时间:' + (row.graduateTime==null?'----':row.graduateTime) + '</li>' +
                '<li class="customerinfo-li">毕业学校:' + (row.graduateFrom==null ?'----':row.graduateFrom) + '</li>' +
                '<li class="customerinfo-li">专业:' + (row.majorIn ==null ?'----':row.majorIn )+ '</li>' +
                '<li class="customerinfo-li">工作年限:' + (row.workAge==null ?'----':row.workAge) + '</li>' +
                '<li class="customerinfo-li">工作经历:' + (row.workExperience==null ? '----':row.workExperience) + '</li>' +
                '<li class="customerinfo-li">应聘渠道:' + (row.recruitChannel) + '</li>' +
                '<li class="customerinfo-li">职位:' + (row.job== null?'----':row.job) + '</li>' +
                '<li class="customerinfo-li">教育培训经历:' + (row.educateExperience==null ?'----':row.educateExperience) + '</li>' +
                '<li class="customerinfo-li">学员状态:' + (row.customerStateMsg==null?'----':row.customerStateMsg) + '</li>' +
                '<li class="customerinfo-li">学员级别:' + (row.customerLevelMsg==null ?'----':row.customerLevelMsg) + '</li>' +
                '<li class="customerinfo-li">咨询师:' + (row.userIdMsg==null?'----':row.userIdMsg) + '</li>' +
                '<li class="customerinfo-li">应聘职位:' + (row.applyJob==null ?'----':row.applyJob) + '</li>' +
                '<li class="customerinfo-li">关单人:' + (row.guandaMsg==null?'----':row.guandaMsg) + '</li>' +
                '<li class="customerinfo-li">邀约人:' + (row.introducerMsg ==null?'----':row.introducerMsg) + '</li>' +
                '<li class="customerinfo-li">录入人:' + (row.createBy ==null?'----':row.createBy) + '</li>' +
                '<li class="customerinfo-li">录入时间:' + (createTime==null ? '----':createTime) + '</li>' +
                '<li class="customerinfo-li">最后跟进时间:' + (lastTime==null ?'----':lastTime) + '</li>' +
                '<li class="customerinfo-li">所属公司:' + (row.companyIdMsg==null ?'----':row.companyIdMsg) + '</li>' +
                '<li class="customerinfo-li">简历类型:' + (row.cvTypeMsg==null?'----':row.cvTypeMsg) + '</li>' +
                '<li class="customerinfo-li">目前居住地:' + (row.liveAddress==null?'----':row.liveAddress) + '</li>' +
                '<li class="customerinfo-li">期望薪资:' + (row.expectSalary==null?'----':row.expectSalary) + '</li>' +
                '<li class="customerinfo-li">应聘公司:' + (row.license==null?'----':row.license) + '</li>' +
                '<li class="customerinfo-li">目前年收入:' + (row.curIncome==null?'----':row.curIncome) + '</li>' +
                '<li class="customerinfo-li">求职状态:' + (row.jobStatus==null?'----':row.jobStatus) + '</li>' +
                '<li class="customerinfo-li">投递时间:' + (row.sendTime==null?'----':sendTime) + '</li>' +
                '<li class="customerinfo-li">备注:' + (row.memo==null?'----':row.memo) + '</li>' +
                '<li class="customerinfo-li">状态:' + (row.status==0?'未上门':row.status) + '</li>' +
                '</ul>';
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
            field: 'createTime',
            title: '录入时间',
            width: 100,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return '<span style="font-weight: bold">'+row.createTime+'</span>';
                }else{
                    return '<span style="color:#'+color+';font-weight: bold;">'+row.createTime+'</span>';
                }
            }
        }, {
            field: 'realName',
            title: '姓名',
            width: 60,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.realName;
                }else{
                    return '<span style="color:#'+color+';">'+row.realName+'</span>';
                }
            }
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
                $(row).parent().css('background-color','red');
                return sex;
            }
        }, {
            field: 'tel',
            title: '电话号码',
            align:'center',
            width: 60,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.tel;
                }else{
                    return '<span style="color:#'+color+';">'+row.tel+'</span>';
                }
            }
        }, {
            field: 'age',
            title: '年龄',
            align: 'center',
            width: 10,
            formatter:function(value,row,index){
                var color = row.color;
                var age= row.age;
                if(age == null){
                    age ='---';
                }
                if(color== null){
                    return age;
                }else{
                    return '<span style="color:#'+color+';">'+age+'</span>';
                }
            }
        }, {
            field: 'nativePlace',
            title: '籍贯',
            width: 120,
            visible: false,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.nativePlace;
                }else{
                    return '<span style="color:#'+color+';">'+row.nativePlace+'</span>';
                }
            }
        }, {
            field: 'wechatNo',
            title: '微信号',
            width:60,
            visible: false,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.wechatNo;
                }else{
                    return '<span style="color:#'+color+';">'+row.wechatNo+'</span>';
                }
            }
        }, {
            field: 'qq',
            title: 'QQ',
            width:60,
            visible: false,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.qq;
                }else{
                    return '<span style="color:#'+color+';">'+row.qq+'</span>';
                }
            }
        }, {
            field: 'educationBg',
            title: '学历',
            align:'center',
            width: 40,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.educationBg;
                }else{
                    return '<span style="color:#'+color+';">'+row.educationBg+'</span>';
                }
            }
        }, {
            field: 'graduateTime',
            title: '毕业时间',
            visible:false,
            width: 80,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.graduateTime;
                }else{
                    return '<span style="color:#'+color+';">'+row.graduateTime+'</span>';
                }
            }
        }, {
            field:'expectSalary',
            title:'期望薪资',
            align:'center',
            width:80,
            formatter:function(value,row,index){
                var expectSalary = row.expectSalary;
                if(expectSalary == null || expectSalary == ''){
                    expectSalary = "---";
                }
                var color = row.color;
                if(color== null){
                    return expectSalary;
                }else{
                    return '<span style="color:#'+color+';">'+expectSalary+'</span>';
                }
            }
        }, {
            field: 'graduateFrom',
            title: '毕业学校',
            width: 110,
            visible: false,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.graduateFrom;
                }else{
                    return '<span style="color:#'+color+';">'+row.graduateFrom+'</span>';
                }
            }
        }, {
            field: 'majorIn',
            title: '专业',
            width: 80,
            visible: false,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.majorIn;
                }else{
                    return '<span style="color:#'+color+';">'+row.majorIn+'</span>';
                }
            }
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
            field: 'educateExperience',
            title: '教育培训经历',
            visible: false,
            width: 70,
            width: 60
        }, {
            field: 'applyJob',
            title: '求职岗位',
            align:'center',
            width: 110,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.applyJob;
                }else{
                    return '<span style="color:#'+color+';">'+row.applyJob+'</span>';
                }
            }
        }, {
            field: 'introducerMsg',
            title: '邀约人',
            visible: true,
            align:'center',
            width: 60,
            formatter:function(value,row,index){
                var name = row.introducerMsg;
                if(name ==null || name ==''){
                    name ='---';
                }
                var color = row.color;
                if(color== null){
                    return name;
                }else{
                    return '<span style="color:#'+color+';">'+name+'</span>';
                }
            }
        },{
            field:"createBy",
            title:'录入人',
            visible:true,
            width:60,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.createBy;
                }else{
                    return '<span style="color:#'+color+';">'+row.createBy+'</span>';
                }
            }
        },{
            field:'liveAddress',
            title:'目前居住地',
            align:'center',
            width:50,
            visible: true,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.liveAddress;
                }else{
                    return '<span style="color:#'+color+';">'+row.liveAddress+'</span>';
                }
            }
        }, {
            field: 'recruitChannel',
            title: '应聘渠道',
            visible: true,
            width: 60,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.recruitChannel;
                }else{
                    return '<span style="color:#'+color+';">'+row.recruitChannel+'</span>';
                }
            }
        },{
            field: 'source',
            title: '后台名称',
            visible: true,
            width: 60,
            formatter:function(value,row,index){
                var source = row.source;
                if(source ==null){
                    source ='---';
                }
                var color = row.color;
                if(color== null){
                    return source;
                }else{
                    return '<span style="color:#'+color+';">'+source+'</span>';
                }
            }
        }, {
            field:'sendTime',
            title:'投递时间',
            align:'center',
            width:100,
            formatter:function(value,row,index){
                var color = row.color;
                var sendTime = row.sendTime;
                if(sendTime == null){
                    sendTime = '---';
                }
                if(color== null){
                    return sendTime;
                }else{
                    return '<span style="color:#'+color+';">'+sendTime+'</span>';
                }
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
                if(row.memo == null){
                    return "---";
                }
                var color = row.color;
                if (memo.length > 10) {
                    memo = row.memo.substr(0, 10) + "....";
                }else if(memo == null || memo =='null' || memo ==''){
                    memo = '---'
                } else {
                    memo = row.memo;
                }
                if(color== null){
                    return memo;
                }else{
                    return '<span style="color:#'+color+';">'+memo+'</span>';
                }
                //return '<span class="memo">' + memo + '</span>';
            }
        },{
            field:'cvTypeMsg',
            title:'简历类型',
            align:'center',
            visible: true,
            width:10,
            formatter:function(value,row,index){
                var color = row.color;
                if(color== null){
                    return row.cvTypeMsg;
                }else{
                    return '<span style="color:#'+color+';">'+row.cvTypeMsg+'</span>';
                }
            }
        },{
            field:'license',
            title:'应聘公司',
            align:'center',
            visible: false
        },{
            field:'curIncome',
            title:'目前年收入',
            align:'center',
            visible: false
        },{
            field:'jobStatus',
            title:'求职状态',
            align:'center',
            visible: false
        },{
            field:'status',
            title:'状态',
            align:'center',
            visible: true,
            width:100,
            formatter:function(value,row,index){
                var status = row.status;
                if(status== 0){
                    return '未上门';
                }else if(status == '已上门'){
                    return  '<span style="color:orange;font-weight: bold;">'+row.status+'</span>';
                }else if(status =='已报名'){
                    return  '<span style="color:red;font-weight: bold;">'+row.status+'</span>';
                }

            }
        }],queryParams: function getParams(params){

            var searchParam =$('#searchParam option:selected').val();
            var  value = $("#searchValue").val();
            if(searchParam == 'introducer'){
                value = $('#introducer option:selected').val();
            }
            if(searchParam =='education_bg'){
                value=  $('#educationBg option:selected').val();
            }
            if(searchParam =='cvType'){
                value=  $('#cvType option:selected').val();
            }
            if(searchParam == 'apply_job'){
                value= $('#apply_job option:selected').val();
            }
            if(searchParam == 'sex'){
                value =$('#sex option:selected').val();
            }
            var  tmp = {
                offset:(this.pageNumber)*this.pageSize,
                limit:this.pageSize,
                condition:searchParam,
                value:value
                /*sort:this.sortName,
                 order:this.sortOrder*/
            };
            console.log(searchParams);
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
    $("#searchParam").change(function(){
        var _this = $(this);
        if(_this.val() === "introducer"){
            $("#introducer").show();
            $("#educationBg").hide();
            $("#status").hide();
            $("#cvType").hide();
            $("#searchValue").hide();
            $("#apply_job").hide();
            $("#sex").hide();
        }else if(_this.val() === "education_bg"){
            $("#educationBg").show();
            $("#introducer").hide();
            $("#status").hide();
            $("#cvType").hide();
            $("#searchValue").hide();
            $("#apply_job").hide();
            $("#sex").hide();
        }else if(_this.val() === "cvType"){
            $("#cvType").show();
            $("#introducer").hide();
            $("#status").hide();
            $("#educationBg").hide();
            $("#searchValue").hide();
            $("#apply_job").hide();
            $("#sex").hide();
        }else if(_this.val() === 'apply_job'){
            $("#cvType").hide();
            $("#introducer").hide();
            $("#status").hide();
            $("#educationBg").hide();
            $("#searchValue").hide();
            $("#sex").hide();
            $("#apply_job").show();
        }else if(_this.val() =='sex'){
            $("#cvType").hide();
            $("#introducer").hide();
            $("#status").hide();
            $("#educationBg").hide();
            $("#searchValue").hide();
            $("#sex").show();
            $("#apply_job").hide();
        } else if(_this.val() == 'create_time'){
            $("#cvType").hide();
            $("#searchValue").show();
            $("#introducer").show();
            $("#educationBg").hide();
            $("#status").hide();
            $("#sex").hide();
            $("#apply_job").hide();
        } else if(_this.val() == 'status'){
            $("#cvType").hide();
            $("#searchValue").hide();
            $("#introducer").hide();
            $("#educationBg").hide();
            $("#status").show();
            $("#sex").hide();
            $("#apply_job").hide();
        }else{
            $("#searchValue").show();
            $("#apply_job").hide();
            $("#introducer").hide();
            $("#status").hide();
            $("#educationBg").hide();
            $("#sex").hide();
            $("#cvType").hide();
        }
    });
    /**
     * 搜索
     */
    $("#searchImport").click(function(){
        var searchParam = $('#searchParam option:selected').val();
        var searchValue = "";

        if(searchParam=='-1'){
            layer.msg('请选择搜索条件!');
            $('#searchParam').css('border','1px solid red');
            return false;
        } else if(searchParam === "introducer"){
            searchValue = $("#introducer").val();
        }else if(searchParam === "education_bg"){
            searchValue = $("#educationBg").val();
        }else if(searchParam === "cvType"){
            searchValue = $("#cvType").val();
        }else if(searchParam === "apply_job"){
            searchValue = $("#apply_job").val();
        }else if(searchParam =='sex'){
            searchValue = $("#sex").val();
        } else if(searchParam =='status'){
            searchValue = $("#status").val();
        }else{
            searchValue = $("#searchValue").val();
        }
        if(searchValue==''){
            layer.msg('请输入搜索值!');
            $('#border').css('border','1px solid red');
            return false;
        }else{
            $('#border').css('border','1px solid lightgrey');
        }
        var searchIntroducer = ($("#introducer").val() !== '')?'&introducer='+$("#introducer").val():'';
        var value= url+"?offset="+searchParams.offset+"&limit="+searchParams.limit+
            "&condition="+searchParam+"&value="+searchValue+searchIntroducer;

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


    /**
     * 修改
     */
   $("#edit").click(function(){
       var $selectData = $table.bootstrapTable('getAllSelections');
       if($selectData.length == 1){
           var customerId = $selectData[0].customerId;
           layer.open({
               type: 2,
               title: '修改数据',
               shadeClose: true,
               shade: 0,
               maxmin: true,
               area: ['100%', '95%'],
               content: ['/import/edit?customerId=' + customerId], //iframe的url
               end: function (layer, index) {
                   $("#table").bootstrapTable("refresh"); //刷新
               }
           });
       }else if($selectData.length > 1){
           layer.msg('所选数据大于1条的数据!');
           $("#table").bootstrapTable("refresh"); //刷新
       }else{
           layer.msg('请选择需要修改的数据!');
       }
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
        });
    });

    /**
     * 分配创量人员
     */
    $("#allot").click(function(){
        var $selectData = $table.bootstrapTable('getAllSelections');
        //console.log($selectData);
        if($selectData.length <= 0 ){
            layer.msg("请选择要分配的数据!",{icon:3,time:1500});
            return false;
        }
        var ids = new Array();
        for(var i = 0; i < $selectData.length; i++){
            ids[i] = $selectData[i].customerId;
        }
        layer.open({
            type:2,
            title:'分配创量人员',
            shadeClose:true,
            shade:0,
            maxmin:true,
            area:["500px","450px"],
            content:['/import/allotIntroducer?customerIds='+ids,'on'],
            end:function(layer,index){
                $("#table").bootstrapTable("refresh"); //刷新
            }
        })
    });

    /**
     * 数据去重
     */
    $('#filter').click(function(){
        //当数据库查询当天的数据

        //列出重复的数据(姓名) 和不重复的结果
        window.location.href= '/import/filterData';
        //
        // layer.open({
        //     type:2,
        //     title:'数据去重',
        //     shadeClose:true,
        //     shade:0,
        //     maxmin:true,
        //     area:["100%","90%"],
        //     content:['/import/filterData','on']
        //});
    });


    //获取手机号码
    $('#getTel').click(function(){
        showData("手机号码");
    });

    $('#getEmail').click(function(){
        showData("邮箱号");
    });

    function showData(id,msg){
        var $selectData = $table.bootstrapTable('getAllSelections');
        if($selectData.length >0){
            var $showTel = $("#showData");
            for(var i =0;i<$selectData.length;i++){
                var tel = $selectData[i].tel;
                console.log(tel);
                $showTel.html($showTel.html()+"<br/>"+tel);
            }
            //页面层-自定义
            layer.open({
                type: 1,
                title: msg,
                //closeBtn:true,
                area: ['300px', '500px'],
                shade:0,
                content: $("#showData"),
                end: function (layer, index) {
                    $("#showData").css('display','none');
                }
            });

        }else{
            layer.msg('请选择数据?',{icon:3});
        }
    }
    //重点标识
    $('#color').click(function(){
        var $selectData = $table.bootstrapTable('getAllSelections');
        if($selectData.length >0) {
            var customerIdArr = [];
            for (var i = 0; i < $selectData.length; i++) {
                var customerId = $selectData[i].customerId;
                customerIdArr.push(customerId);
            }
            console.log(customerIdArr);
            layer.open({
                title: '选择颜色!(<span style="color:red;">红色:很重要数据</span>,<span style="color:#CC9900;">深黄色:次重要数据!</span>)',
                type: 1,
                shade: 0,
                content: $('#colorDiv'),
                area: ['400px', '200px'],
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    //获取颜色
                    var color = $('input[type="radio"]:checked').val();
                    //异步发送数据
                    $.ajax({
                        type:'GET',
                        url:'setColor?customerId='+customerIdArr+'&color='+color,
                        success:function(resp){
                            if(resp.code != 0){
                                layer.msg('操作成功!',{icon:1});
                                setTimeout(function(){
                                    //关闭层
                                     layer.close(index);

                                },1000);
                            }else{
                                layer.msg('操作失败!',{icon:2});
                            }
                        },error:function(resp){

                        }

                    })

                },
                cancel: function (index, layero) {

                },end:function(){
                    //隐藏弹框内容
                    $('#colorDiv').css("display","none");
                    //表格重载
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            })
        }else{
            layer.msg('请选择数据?',{icon:3});
        }
    });

    //数据导出
    $('#exportExcel').click(function(){
        //展示弹框
        //发送请求
        layer.open({
            title: '导出数据',
            type: 1,
            shade: 0,
            content: $('#exportExcelDiv'),
            area: ['250px', '350px'],
            btn: ['确定', '关闭'],
            yes: function (index, layero) {
                //获取日期
                var date = $('#exportExcelDate').val();
                window.location.href='/import/exportExcel?date='+date;
            },
            cancel: function (index, layero) {

            },end:function(){
                //隐藏弹框内容
                $('#exportExcelDiv').css("display","none");
                //表格重载
                //$("#table").bootstrapTable("refresh"); //刷新
                console.log('隐藏!!');
            }
        })
    });
    /**
     * 创量数据转运营
     */
    $('#yunying').click(function () {
        var $selectData = $table.bootstrapTable('getAllSelections');
        if($selectData.length >0) {
            var customerIdArr = [];
            for (var i = 0; i < $selectData.length; i++) {
                var customerId = $selectData[i].customerId;
                customerIdArr.push(customerId);
            }
            console.log(customerIdArr);
            //询问框

            layer.confirm('<p style="color:red;font-weight: bold;">是否把选择的数据转到运营中?</p>', {
                btn: ['确定','取消'] //按钮
            }, function(){
                //异步发送数据
                $.ajax({
                    type:'GET',
                    url:'/import/turnToYunYing?customerIdArr='+customerIdArr,
                    success:function(resp){
                        if(resp.status === 0){
                            layer.msg(resp.msg,{icon:1});
                            $("#table").bootstrapTable("refresh"); //刷新
                        }else{
                            layer.msg(resp.msg,{icon:2});
                        }
                    },error:function(resp){
                        if(resp.status=== 500){
                            layer.msg('服务器内部错误!',{icon:2});
                        }else if(resp.status === 401){
                            layer.msg('对不起，您没有权限进行该操作!',{icon:2});
                        }else if(resp.status === 404){
                            layer.msg('请求出错!',{icon:2});
                        }
                    }

                })
            }, function(){

            });
        }else{
            layer.msg('请选择转到运营的数据?',{icon:3});
        }
    })
});