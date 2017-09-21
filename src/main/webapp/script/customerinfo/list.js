/**
 * Created by xianyaoji on 2017/2/12.
 */

$(function () {
    //页面加载时根据用户编号获取用户所能访问的按钮,移除隐藏样式
    $.get('/showButton/show?userId='+$("input[name='userId']").val(),function(result){
        var $span ="";
        $.each(result,function(item){
            var buttonResource =   result[item];
            if(buttonResource.indexOf("customerInfo")!=-1){
                //显示隐藏的按钮
                //截取字符串
                var button  = buttonResource.substr(buttonResource.indexOf(":")+1,buttonResource.length);
                $("#span-"+button).removeClass('span-hidden');
            }
        });
    });
    var url = "";
    var $table;
    var searchParams;
    var url = "";
    var userId= "";
    var isConsultant = $("input[name='isConsultant']").val();
    var roleName = $("input[name='roleName']").val();
    userId = $("input[name='userId']").val();
    var companyId = $("input[name='companyId']").val();
    if(isConsultant =='0'){//不是咨询师
        if(roleName.indexOf('创量') !=-1){   //说明是创量人员或市场经理
            url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
        }else if(roleName=='市场经理'){
            url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
        } else if(roleName=='管理员'|| roleName=='总经理'){
            url = '/customerInfo/listData?isDelete=0';
            $('option[value="company_id"]').css('display','inline');
        }else{
            url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
        }
    }else if(isConsultant =='1'){   //是咨询师
        if(roleName.indexOf('主管')!=-1){
            url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
        }else{
            url = '/customerInfo/listData?isDelete=0&userId='+userId;
        }
    }

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
        exportDataType: "basic",              //basic', 'all', 'selected'.
        showRefresh: true,
        showColumns: true,
        detailView: true,
        detailFormatter: function (index, row) {
            var lastTime = new Date(parseInt(row.lastTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
            var createTime = new Date(parseInt(row.lastTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
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
                '<li class="customerinfo-li">目标技能:' + (row.targetSkillMsg==null ?'----':row.targetSkillMsg) + '</li>' +
                '<li class="customerinfo-li">关单人:' + (row.guandaMsg==null?'----':row.guandaMsg) + '</li>' +
                '<li class="customerinfo-li">邀约人:' + (row.introducerMsg ==null?'----':row.introducerMsg) + '</li>' +
                '<li class="customerinfo-li">录入时间:' + (createTime==null ? '----':createTime) + '</li>' +
                '<li class="customerinfo-li">最后跟进时间:' + (lastTime==null ?'----':lastTime) + '</li>' +
                '<li class="customerinfo-li">所属公司:' + (row.companyIdMsg==null ?'----':row.companyIdMsg) + '</li>' +
                '<li class="customerinfo-li">备注:' + (row.memo==null?'----':row.memo) + '</li>' +
                '</ul>';
            /* value = '<table>' +
             '' +
             '</table>';*/
            return value;
        },
        //showFooter:true,
        silent: true, // 刷新事件必须设置
        onDblClickRow: function (row, $element) {//双击事件
            //$('#hiddenCustomerId').val(row.customerId);
            //创量人员只能修改一次
            //获取邀约人信息和角色信息
            var roleName = $('[name="roleName"]').val();
            var introducer = row.introducer;
            console.log("角色名称："+roleName);
            console.log("打印："+introducer);
            var createTime = row.createTime;
            var currentTime = Date.parse(new Date())/1000;
            if(roleName=='创量人员'){ //说明是创量人员
                //判断有没有邀约人信息
               if(typeof(introducer)=='string'){
                   if(introducer.length>0){//只能修改一次
                       layer.alert('对不起，您只能修改一次，如需再次修改则联系部门主管修改!');
                       return false;
                   }
               }
                /*if(typeof(introducer)!= 'object'){
                    layer.alert('对不起，您只能修改一次，如需再次修改则联系部门主管修改!');
                    return false;
                }*//*else {

                }*/
            }else if(roleName.indexOf('管理员')!=-1){
                companyId = 0;
            }else if(roleName.indexOf('总经理')!=-1){
                companyId = 0;
            }else if(roleName.indexOf('会销')!=-1){    //数据超过7天不能修改了
               createTime = createTime /1000;
               console.log(createTime+"----------"+currentTime);
               var checkTime = currentTime - createTime;
               console.log(checkTime);
               if(checkTime >=7*24*60*60){
                   layer.alert('该数据已经过期7天，不能再次修改了!');
                   return false;
               }
            }
            layer.open({
                type: 2,
                title: '编辑学员信息',
                shadeClose: true,
                shade:0,
                maxmin: true,
                area: ['100%', '97%'],
                content: '/customerInfo/edit?customerId='+row.customerId+"&companyId="+companyId,//iframe的url
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
                    ,title:'备注(弹窗2秒后自动关闭)'
                    ,content: '<div style="padding: 20px 20px;color:orange;">'+value+'</div>'
                    ,btn: '关闭'
                    ,time:2000
                    ,btnAlign: 'c' //按钮居中
                    ,shade: 0 //不显示遮罩
                    ,yes: function(){
                        layer.closeAll();
                    }
                });
                //layer.alert(value,5,1);
                /*var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
                 setInterval(function(){
                 parent.layer.close(index);
                 },2000);*/
            }
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
            }
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
        }],queryParams: function getParams(params){

            var  tmp = {
                offset:(this.pageNumber)*this.pageSize,
                limit:this.pageSize ,
                condition:$('#searchParam option:selected').val(),
                value:$("#searchValue").val()//,//,
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
     //***********************************搜索开始*****************************
    var queryParam;
    $('#searchValue').blur(function(){

        if($(this).val()===''){
            layer.msg('请输入搜索值!');
            $(this).addClass('search-border-color');
        }else{
            $(this).removeClass('search-border-color');
        }
    });

    $('#searchParam').change(function(){
        var value = $(this).children('option:selected').val();
        if(value=='-1'){
            $(this).addClass('search-border-color');
            layer.msg('请选择搜索条件!');
        }else{
            $(this).removeClass('search-border-color');
        }
        //弹出界面让用户选择
        if(value=='customerState'){

        }else if(value=='customerLevel'){

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

   function getUrl(){
       var isConsultant = $("input[name='isConsultant']").val();
       userId = $("input[name='userId']").val();
       if(isConsultant =='0'){//不是咨询师
           url = '/customerInfo/listData?isDelete=0';
       }else if(isConsultant =='1'){   //是咨询师
           url = '/customerInfo/listData?isDelete=0&userId='+userId;
       }
   }
    //搜索
    $('#searchCustomerInfo').click(function () {
        //getUrl();
        //搜索条件和搜索值验证
        //验证搜索值
        var $searchValue =$("#searchValue");
        if($searchValue.val()==''){
            if($searchValue.val()===''){
                layer.msg('请输入搜索值!');
                $searchValue.addClass('search-border-color');
                return false;
            }else{
                $searchValue.removeClass('search-border-color');
            }
        }

        //验证搜索条件
        var $searchParam = $("#searchParam");
        var $searchParamValue = $("#searchParam").children('option:selected').val();
        if($searchParamValue=='-1'){
            $searchParam.addClass('search-border-color');
            layer.msg('请选择搜索条件!');
            return false;
        }else{
            $searchParam.removeClass('search-border-color');
        }
        console.log(url);
        //searchParams.offset
        //searchParams.limit
        var value =url+'&offset=0'+'&limit='+searchParams.limit+
            '&condition='+$('#searchParam option:selected').val()+ '&value='+$("#searchValue").val();
        $.get(value,function(result){
            $table.bootstrapTable('load', result);
            var tmp = {
                offset:searchParams.offset,
                limit:searchParams.limit,
                condition:$('#searchParam option:selected').val(),
                value:$("#searchValue").val() //,
                /* sort:"",
                order:""*/
            };
            searchParams = tmp;
        });
    });
    function checkSeachParam(){
        var $searchParam = $('#searchParam option:selected').val();
        if($searchParam == -1){
            layer.msg('请选择搜索条件!');
            return false;
        }
        var $searchValue = $('#searchValue');
        if($searchValue.val() == ''){
            layer.msg('请输入搜索值!');
            return false;
        }
        if($searchParam == 'age'){
            if(isNaN($searchValue.val())){
                layer.msg('年龄不是数字!');
                $searchValue.val('');
                return false;
            }
        }
        return true;
    }
    //**************************************搜索结束***************************
    /*----------------面试学员---------------------------*/
    $("#interview").click(function () {
        var $selectData = $table.bootstrapTable('getAllSelections');
        var customerIdArr = [];
        if ($selectData.length < 1) {
            layer.msg('请选择要面试的学员!');
        } else {
            for (var item in $selectData) {
                if ($selectData[item].customerState != '28') {
                    layer.msg('已经面试过学员了!');
                    return false;
                } else {
                    customerIdArr.push($selectData[item].customerId);
                }
            }
            var data = "customerIdArr=" + customerIdArr;
            //加载层
            //loading层
            var index2 = layer.load(1,{time:15*1000}, {shade: [0.1, '#fff']});
            $.ajax({
                type: 'post',
                url: '/customerInfo/interview',
                data: data,
                success: function (result) {
                    layer.close(index2); //关闭当前弹层
                    if(result.code==200){
                        layer.msg(result.msg);
                        window.setTimeout(function(){
                            $("#table").bootstrapTable("refresh"); //刷新
                        },2000);
                    }  else{
                        layer.msg(result.msg);
                    }
                   
                }
            });
        }
    });


    /*--------------------添加学员---------------------------*/

    $('#add').click(function () {
        var roleName = $('[name="roleName"]').val();
        var companyId = $("input[name='companyId']").val();
        if(roleName.indexOf('管理员')!=-1){
            companyId=0;
        } else if(roleName.indexOf('总经理')!=-1){
            companyId =0;
        }
        layer.open({
            type: 2,
            title: '添加',
            shadeClose: true,
            shade: 0,
            maxmin: true,
            area: ['100%', '95%'],
            content: ['/customerInfo/add?companyId='+companyId, 'on'], //iframe的url
            end: function (layer, index) {
                $("#table").bootstrapTable("refresh"); //刷新
            }
        });
    });
    /*---------------------编辑学员-----------------------------*/
    $("#edit").click(function () {
        var $selectData = $table.bootstrapTable('getAllSelections');
        if ($selectData.length == 1) {
            //获取学员编号
            var customerId = $selectData[0].customerId;
            //创量人员只能修改一次
            //获取邀约人信息和角色信息
            var roleName = $('[name="roleName"]').val();
            var introducer = $selectData[0].introducer;
            console.log("角色名称："+roleName);
            console.log("打印："+introducer);
            var createTime = $selectData[0].createTime;
            var currentTime = Date.parse(new Date())/1000;
            if(roleName=='创量人员'){ //说明是创量人员
                //判断有没有邀约人信息
                //判断有没有邀约人信息
                /*if(typeof(introducer)!= 'object'){
                    layer.alert('对不起，您只能修改一次，如需再次修改则联系部门主管修改!');
                    $("#table").bootstrapTable("refresh"); //刷新
                    return false;
                }*//*else {
                 if(introducer.length>0){//只能修改一次
                 layer.alert('对不起，您只能修改一次，如需再次修改则联系部门主管修改!');
                 return false;
                 }
                 }*/
                if(typeof(introducer) =='string'){
                    if(introducer.length >=1){//只能修改一次
                        layer.alert('对不起，您只能修改一次，如需再次修改则联系部门主管修改!');
                        $("#table").bootstrapTable("refresh"); //刷新
                        return false;
                    }
                }
            }else if(roleName.indexOf('管理员')!=-1){
                companyId=0;
            } else if(roleName.indexOf('总经理')!=-1){
                companyId =0;
            } else if(roleName.indexOf('会销')!=-1){    //数据超过7天不能修改了
                createTime = createTime /1000;
                console.log(createTime+"----------"+currentTime);
                var checkTime = currentTime - createTime;
                console.log(checkTime);
                if(checkTime >=7*24*60*60){
                    layer.alert('该数据已经过期7天，不能再次修改了!');
                    return false;
                }
            }
            console.log(companyId+"--------------");
            layer.open({
                type: 2,
                title: '修改,<span style="color:red;">注意:应聘渠道和邀约人应由创量人员来修改!</span>',
                shadeClose: true,
                shade: 0,
                maxmin: true,
                area: ['100%', '95%'],
                content: ['/customerInfo/edit?customerId=' + customerId+"&companyId="+companyId], //iframe的url
                end: function (layer, index) {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });
        } else if ($selectData.length > 1) {
            layer.msg('所选数据大于1条的数据!');
            $("#table").bootstrapTable("refresh"); //刷新
        } else {
            layer.msg('请选择要修改的数据!');
        }

    });


    /*------------------回收站--------------------------------*/
    $("#remove").click(function () {
        var $selectData = $table.bootstrapTable('getAllSelections');
        var customerIdArr = [];
        if ($selectData.length < 1) {
            layer.msg('请选择要丢进回收站的学员!');
        } else {
            for (var item in $selectData) {
                customerIdArr.push($selectData[item].customerId);
            }
            var data = "customerIdArr=" + customerIdArr;
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
                    url: '/customerInfo/remove',
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
    /*----------------------------学员跟进--------------------*/
    $('#follow').click(function () {
        var $selectData = $table.bootstrapTable('getAllSelections');
        var customerIdArr = [];
        if ($selectData.length < 1) {
            layer.msg('请选择要跟进的学员!');
        } else if ($selectData.length > 1) {
            layer.msg('所选数据大于1条!');
        } else { //跳转到跟进页面
            layer.open({
                type: 2,
                title: '学员跟进',
                shadeClose: true,
                shade: 0,
                maxmin: false,
                minmin:true,
                area: ['100%', '95%'],
                content: ['/customerInfo/follow?customerId=' + $selectData[0].customerId, 'on'], //iframe的url
                end: function (layer, index) {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });

        }
    });
    /**
     * 高级搜索
     */
    $("#multiSearch").click(function(){
        $("iframe").attr("id","我的id");
        companyId =$("input[name='companyId']").val();
        if(roleName.indexOf('管理员')!=-1||roleName.indexOf('总经理')!=-1){
            companyId =0;
        }
        //输入框禁止使用
        $("#searchValue").addClass('search-value-disabled');
            layer.open({
                type: 2,
                maxmin: true,
                content: '/customerInfo/searchPage?companyId='+companyId,
                area: ['700px', '500px'],
                shade:0,
                anim:2,
                end:function(layero, index){
                    //恢复
                    $("#searchParam").addClass("search-param-enabled");
                    $("#searchValue").addClass("search-value-enabled");
                    var conditionArr = [[ '-1','请选择搜索条件'],['real_name',"学员姓名"],['company_id','所属校区'],['age','年龄'],
                        ['tel','电话号码'],['native_place','籍贯'],['native_place','籍贯'],['wechat_no','微信号'],
                        ['qq','qq号'],['graduate_time','毕业时间'],['graduate_from','毕业学校'],['major_in','专业']];
                    
                    var $searchParam = $("#searchParam");
                    //清空搜索下拉列表框
                    $searchParam.html('');
                    //遍历
                    var $option = null;
                    $.each(conditionArr,function(item){
                       $option = $("<option value='"+conditionArr[item][0]+"'>"+conditionArr[item][1]+"</option>");
                        $searchParam.append($option);
                    });
                    var url = "";
                    var isConsultant = $("input[name='isConsultant']").val();
                    var companyId = $("input[name='companyId']").val();
                    var roleName = $("input[name='roleName']").val();
                    userId = $("input[name='userId']").val();
                    if(isConsultant =='0'){//不是咨询师
                        if(roleName.indexOf("创量")!=-1){ //创量
                            url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
                        }else if(roleName.indexOf('市场经理')!=-1){
                            url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
                        } else {
                            url = '/customerInfo/listData?isDelete=0';
                        }
                    }else if(isConsultant =='1'){   //是咨询师
                            url = '/customerInfo/listData?isDelete=0&userId='+userId;

                    }
                    $.get(url,function(result){
                        if(typeof(result)=='object'){
                            //清空查询条件
                            $('#searchValue').val('');
                            $('#searchValue').css('display','inline');
                            //$('#searchParam').children('option[value="-1"]').attr("selected","selected");
                            $table.bootstrapTable('load', result);
                            layer.msg('已重新获取数据!');
                        }
                    });
                }
            });
    });
    /**
     * 报表导出
     */
    $("#export").click(function(){
        layer.open({
            type:2,
            title:'导出数据',
            shadeClose:true,
            shade:0,
            maxmin:true,
            area:["1000px","400px"],
            content:['/customerInfo/export?companyId=0','on'],
            end:function(layer,index){
                $("#table").bootstrapTable("refresh"); //刷新
            }

        })
    });

    /**
     * 学员进班
     */
    $("#enterClass").click(function(){
        var $selectData = $table.bootstrapTable('getAllSelections');
        if ($selectData.length == 1) {
            //获取学员编号
            var customerId = $selectData[0].customerId;
            var stuentName = $selectData[0].realName;
            $("input[name='customerId']").val(customerId);
            $("input[name='studentName']").val(stuentName);
            layer.open({
                type: 2,
                title: '学员进班',
                shadeClose: true,
                shade: 0,
                maxmin: false,
                area: ["500px", "500px"],
                content: ['/customerInfo/enterClass', 'on'],
                end: function (layer, index) {
                    $("#table").bootstrapTable("refresh"); //刷新
                }

            })
        }else if($selectData.length==0){
            layer.msg('请选择要进班的学员!');
        }else if($selectData.length>1){
            layer.msg('所选数据大于1!');
        }
    });

    /**
     * 咨询部门主管获取本公司所有的咨询数据
     */
/*
     $("#span-company").click(function(){
         url = '/customerInfo/listData?isDelete=0&companyId='+companyId;
         $.get(url,function(result){
             if(typeof(result)=='object'){
                 //清空查询条件
                 $('#searchValue').val('');
                 $('#searchValue').css('display','inline');
                 $table.bootstrapTable('load', result);
                 layer.msg('已重新获取数据!');
             }
         });
     });*/

});