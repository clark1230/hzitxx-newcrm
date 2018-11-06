$(function(){
    $.getJSON('/mobile/getSign',function(resp){
        ding(resp);
    });
});
function ding(data){
    dd.config({
        agentId: data.agentId, // 必填，微应用ID
        corpId: data.corpId,//必填，企业ID
        timeStamp:data.timeStamp , // 必填，生成签名的时间戳
        nonceStr: data.nonceStr, // 必填，生成签名的随机串
        signature: data.signature, // 必填，签名
        type:0,   //选填，0表示微应用的jsapi，1表示服务窗的jsapi，不填默认为0。该参数从dingtalk.js的0.8.3版本开始支持
        jsApiList : [ 'runtime.info', 'biz.contact.choose','runtime.permission.requestAuthCode',
            'runtime.permission.requestOperateAuthCode',
            'device.notification.confirm', 'device.notification.alert',
            'device.notification.prompt', 'biz.ding.post',
            'biz.util.openLink','biz.contact.choose','biz.chat',
            'biz.user.get','device.geolocation.get','biz.chat.pickConversation','biz.chat.openSingleChat' ] // 必填，需要使用的jsapi列表，注意：不要带dd。
    });
    dd.ready(function(){
        //获取用户基本信息
        dd.biz.user.get({
            corpId:'ding8fcc4db885b89ac635c2f4657eb6378f', // 可选参数，如果不传则使用用户当前企业的corpId。
            onSuccess: function (info) {
                $.post('/account/login',
                    "corpId="+info.corpId+"&emplId="+info.emplId+"&username="+info.username,
                    function(resp){
                    if(resp.status ==1){
                        dd.biz.navigation.close({
                            onSuccess : function(result) {

                            },
                            onFail : function(err) {}
                        })
                    }else if(resp.status ==0){
                    }
                });
            },
            onFail: function (err) {
                alert('获取用户信息失败!');
            }
        });
    });

    dd.error(function(err) {
        alert('dd error: ' + JSON.stringify(err));
    });
}