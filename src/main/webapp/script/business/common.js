/**
 * Created by xianyaoji on 2017/3/29.
 */
var $table;
$(function () {
    var url = "/business/getAjaxData?common=1";
    var searchParams = null;
    var json = {
        url: url,
        toolbar: '#toolbar',                //工具按钮用哪个容器
        pagination: true,                   //是否显示分页（*）
        striped: true,                      //是否显示行间隔色
        sortable: true,                     //是否启用排序
        sortOrder: "desc",                   //排序方式
        sidePagination: "server",           //分页方式：client学员端分页，server服务端分页（*）
        idField: 'customerId',
        paginationShowPageGo: true,
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
            align: 'center',
            width: 20,
            formatter: function (value, row, index) {
                var sex;
                if (row.sex == 1) {
                    sex = '<img style="width:20px;height:16px;" alt="男" src="/assets/images/男.png"/>';
                } else if (row.sex == 2) {
                    sex = '<img style="width:20px;height:16px;" alt="女" src="/assets/images/女.png"/>';
                } else {
                    sex = '<img style="width:20px;height:16px;" alt="女" src="/assets/images/未知.png"/>';
                }
                return sex;
            }
        }, {
            field: 'tel',
            title: '电话号码',
            align: 'center',
            width: 60
        }, {
            field: 'age',
            title: '年龄',
            align: 'center',
            width: 10,
            visible: false

        }, {
            field: 'nativePlace',
            title: '籍贯',
            width: 120,
            visible: false
        }, {
            field: 'wechatNo',
            title: '微信号',
            width: 60,
            visible: false
        }, {
            field: 'qq',
            title: 'QQ',
            width: 60,
            visible: false
        }, {
            field: 'educationBg',
            title: '学历',
            width: 50,
            visible: false

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
            visible: false
        }, {
            field: 'educateExperience',
            title: '教育培训经历',
            visible: false,
            width: 70
        }, {
            field: 'guandaMsg',
            title: '关单人',
            visible: false,
            width: 80
        }, {
            field: 'customerStateMsg',
            title: '学员状态',
            width: 40,
            formatter: function (value, row, index) {
                var customerStateMsg = row.customerStateMsg;
                if (row.customerStateMsg == '数据字典') {
                    customerStateMsg = '--';
                }
                return customerStateMsg;
            }
        }, {
            field: 'customerLevelMsg',
            title: '学员级别',
            align: 'center',
            width: 20,
            formatter: function (value, row, index) {
                var customerLevelMsg = row.customerLevelMsg;
                if (customerLevelMsg == '数据字典' || customerLevelMsg == 'null') {
                    customerLevelMsg = '---';
                } else if (customerLevelMsg == 'A') {
                    customerLevelMsg = '<span style="margin:0 auto;width:30px;height:16px;background-color:red;display: block">' + row.customerLevelMsg + '</span>';
                } else if (customerLevelMsg == 'B') {
                    customerLevelMsg = '<span style="margin:0 auto;width:30px;height:16px;background-color: #CC9900;display: block">' + row.customerLevelMsg + '</span>';
                } else {
                    customerLevelMsg = row.customerLevelMsg;
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
            visible: false,
            align: 'center',
            width: 80
        }, {
            field: 'recruitChannel',
            title: '应聘渠道',
            visible: true,
            width: 60
        }, {
            field: 'createTime',
            title: '录入时间',
            width: 150,
            formatter: function (value, row, index) {
                return row.createTime;
            },
            visible: false
        }, {
            field: 'lastTime',
            title: '最后跟进时间',
            width: 150,
            formatter: function (value, row, index) {
                return row.lastTime;
            }
        }, {
            field: 'companyIdMsg',
            title: '所属公司',
            visible: true,
            width: 100
        }, {
            field: 'memo',
            title: '备注',
            width: 80,
            formatter: function (value, row, index) {
                var memo = (typeof(row.memo) == 'null') ? "---" : row.memo + "";
                if (row.memo == null) {
                    return "---";
                }
                if (memo.length > 5) {
                    memo = row.memo.substr(0, 5) + "....";
                } else if (memo == null || memo == 'null' || memo == '') {
                    memo = '---'
                } else {
                    memo = row.memo;
                }
                return '<span class="memo">' + memo + '</span>';
            },
        }, {
            field: 'isMarket',
            title: '会销',
            align: 'center',
            width: 20,
            formatter: function (value, row, index) {
                var isMarket = row.isMarket;
                if (isMarket != '0') {
                    isMarket = '<i style="font-size: 16px;color:green;" class="layui-icon"></i>' + "--" + isMarket;
                } else if (isMarket == '0') {
                    isMarket = '<i style="font-size: 16px;color:red;" class="layui-icon">ဇ</i>';
                }
                return isMarket;
            }
        }, {
            field: 'isLearn',
            title: '试听',
            align: 'center',
            width: 20,
            formatter: function (value, row, index) {
                var isLearn = row.isLearn;
                if (isLearn != '0') {
                    isLearn = '<i style="font-size: 16px;color:green;" class="layui-icon"></i>' + "--" + isLearn;
                } else if (isLearn == '0') {
                    isLearn = '<i style="font-size: 16px;color:red;" class="layui-icon">ဇ</i>';
                }
                return isLearn;
            }
        }],queryParams: function getParams(params){
            var  tmp = {
                offset:(this.pageNumber)*this.pageSize,
                limit:this.pageSize ,
                condition: 'real_name',
                value:$("#searchValue").val()//,//,
                /*sort:this.sortName,
                 order:this.sortOrder*/
            };
            searchParams =tmp;
            return tmp;
        }
    }
    $table = $('#table').bootstrapTable(json);

    /**
     * 公共函数
     * @param title
     * @param content
     * @param area
     */
    function common(title, content, area) {
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

    /**
     * 还原
     */
    $('#recover').click(function(){
        var $selectData = $table.bootstrapTable('getAllSelections');
        var customerIdArr = [];
        if ($selectData.length < 1) {
            layer.msg('请选择要还原的学员!');
        } else{
            $.each($selectData,function(index,value){
                customerIdArr.push(value.customerId);
            });

            layer.confirm('是否要把所选数据进行还原了?', {
                btn: ['确定','取消'] //按钮
            }, function(){
                var index2 = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    type:'post',
                    url:'/business/recover',
                    data: 'customerIdArr='+customerIdArr,
                    success:function(resp){
                        if(resp.status === 0){
                            layer.close(index2); //关闭当前弹层
                            layer.msg('还原成功!',{icon:1});
                            reload();
                        }else{
                            layer.close(index2); //关闭当前弹层
                            layer.msg('还原失败!',{icon:2});
                        }
                    },error: function (error) {
                        if(error.status === 404){
                            layer.msg('请求路径不正确!');
                        }else if(error.status === 401){
                            layer.msg('对不起，您没有权限进行该操作!');
                        }else if(error.status === 500){
                            layer.msg('服务器内部错误!');
                        }
                    }
                });
            }, function(){


            });
            console.log(customerIdArr);
        }
    });

    /**
     * 搜索信息
     */
    $('#searchCustomerInfo').click(function(){
        var searchValue = $('#searchValue').val();
        if(searchValue === ''){
            layer.msg('请输入搜索值!',{icon:2});
            return false;
        }

        var value =url+'&offset=0'+'&limit='+searchParams.limit+
            '&condition=real_name&value='+$("#searchValue").val();
        $.get(value,function(result){
            $table.bootstrapTable('load', result);
            var tmp = {
                offset:searchParams.offset,
                limit:searchParams.limit,
                condition:'real_name',
                value:$("#searchValue").val() //,
                /* sort:"",
                order:""*/
            };
            searchParams = tmp;
        });

    });
    /**
     * 刷新数据
     */
    $('#searchRefresh').click(function(){
        reload();
    });
    
    function  reload() {
        $.get(url,function(result){
            if(typeof(result)=='object'){
                //清空查询条件
                $('#searchValue').val('');
                $table.bootstrapTable('load', result);
                layer.msg('已重新获取数据!');
            }
        });
    }

});
