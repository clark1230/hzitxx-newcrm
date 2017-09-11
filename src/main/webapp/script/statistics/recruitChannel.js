
/**
 * Created by xianyaoji on 2017/3/10.
 */
$(function(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('showCompanyCount'));
    var myChart2 = echarts.init(document.getElementById('showChuangLiangMonthData'));
    var roleName = $('input[name="roleName"]').val();
   if(roleName.indexOf('管理员')!=-1|| roleName.indexOf('总经理')!=-1){
       $('[name="company"]').css('display','inline');
       $('#companySpan').css('display','inline');
   }
    var companyId = $("input[name='companyId']").val();
    var date = new Date();
    var month = date.getMonth()+1;
    if(month <10){
        month = "0"+month;
    }
    var currentMonth = date.getFullYear()+"-"+month;
    $("#month").val(currentMonth);
    getData(companyId,currentMonth);     //页面加载时获取数据
     function getData(companyValue,monthValue) {
         var data = {};
         $.post('/statistics/recruitChannel?companyId='+companyValue+"&month="+monthValue,data,function(result){
             var recruitChannel = [];
             if(result .length >0){
                 layer.msg('获取新统计数据了!');
                 for(var i  =0;i<result.length;i++){
                     recruitChannel.push(result[i].name);
                 }
                 showCompanyCount(result,recruitChannel);
             }else if(result.length==0){   //说明没有数据
                 layer.alert('对不起,暂无数据!');
                // myChart.setOption(option);
                 showCompanyCount(0,0);
                 return false;
             }
         });
         $.get('/statistics/chuangliangMonthData?companyId='+companyValue+"&month="+monthValue,function(result){
             var monthData =[];
             var total =0;
             if(result.length >0){
                 for (var i=0;i<result.length;i++){
                    monthData.push(result[i].name);
                    total = total+result[i].value;
                 }
                 showChuangLiangMonthData(result,monthData,total);
             } else{
                  showChuangLiangMonthData(0,0,0);
                 return false;
             }
         });
     }

    $("select[name='company']").change(function(){
        var value = $(this).children("option:selected").val();
        if(value== "-1"){
            layer.msg('请选择校区!');
            return false;
        }
        getData(value,currentMonth);
    });

    /**
     * 中间操作(点击日期时，其实就是调用该按钮的点击事件)
     */
    $("#search").click(function(){
        //获取分公司数据
        var companyValue= $("select[name='company']").children("option:selected").val();
        if(companyValue =='-1'){
            companyValue = companyId;
        }
        $("select[name='company']").children('option[value="'+companyId+'"]').attr("selected","selected");

        //获取month
        var monthValue = $("#month").val();
        console.log(companyValue,monthValue);
        //调用函数
        getData(companyValue,monthValue);
    });

    /**
     * 日期选择
     */
    function datePick(){
        //获取分公司数据
        var companyValue= $("select[name='company']").children("option:selected").val();
        if(companyValue =='-1'){
            companyValue = companyId;
        }
        $("select[name='company']").children('option[value^="宝安"]').attr("selected","selected");
        //获取month
        var monthValue = $("#month").val();
        //调用函数
        getData(companyValue,monthValue);
    }


    function showCompanyCount(result,recruitChannel){
        var  option = {
            backgroundColor: '#F9F8F8',
            title: {
                text: '应聘来源',
                textStyle:
                    {fontSize:22},
                subtext: '单位：人',
                x:'20%',
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: true},
                    // restore: {},
                    saveAsImage: {}
                }
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            color:['#8fc31f','#f35833','#00ccff','#ffcc00','#045FB4','#FF8000'],
            legend: {
                orient: 'vertical',
                x: 'right',
                y:'10%',
                data: recruitChannel,
                formatter:function(name){
                    var oa = option.series[0].data;
                    //var num = oa[0].value + oa[1].value + oa[2].value + oa[3].value;
                    for(var i = 0; i < option.series[0].data.length; i++){
                        if(name==oa[i].name){
                            return name  ;
                        }
                    }
                }
            },
            series : [
                {
                    name: '应聘渠道',
                    type: 'pie',
                    radius : '55%',
                    center: ['48%', '60%'],
                    data:result,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    itemStyle: {
                        normal: {
                            label:{
                                textStyle:{fontSize:16},
                                show: true,
                                //  position:'inside',
                                formatter: '{b} : {c} ({d}%)'
                            }
                        },
                        labelLine :{show:true}
                    }
                }
            ]
        };
        myChart.setOption(option);
    }


    function showChuangLiangMonthData(result,montData,total){
        var  option = {
            backgroundColor: '#F9F8F8',
            title: {
                text: '邀约人数(总数:'+total+")",
                textStyle:
                    {fontSize:22},
                subtext: '单位：人',
                x:'20%',
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: true},
                   // lineChart: _iconLineChart,
                    // restore: {},

                    saveAsImage: {}
                }
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            color:['#8fc31f','#f35833','#00ccff','#ffcc00','#045FB4','#FF8000'],
            legend: {
                orient: 'vertical',
                x: 'right',
                y:'10%',
                data: montData,
                formatter:function(name){
                    var oa = option.series[0].data;
                    //var num = oa[0].value + oa[1].value + oa[2].value + oa[3].value;
                    for(var i = 0; i < option.series[0].data.length; i++){
                        if(name==oa[i].name){
                            return name  ;
                        }
                    }
                }
            },
            series : [
                {
                    name: '邀约人数',
                    type: 'pie',
                    radius : '55%',
                    center: ['48%', '60%'],
                    data:result,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    itemStyle: {
                        normal: {
                            label:{
                                textStyle:{fontSize:16},
                                show: true,
                                //  position:'inside',
                                formatter: '{b} : {c} ({d}%)'
                            }
                        },
                        labelLine :{show:true}
                    }
                }
            ]
        };
        myChart2.setOption(option);
    }
});