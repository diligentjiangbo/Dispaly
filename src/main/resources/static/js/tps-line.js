$(document).ready(function(){

    var url = '115.159.198.113';

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('one'));

    // 常量
    var ONE_HOUR = 60;
    //var MAX = 2000;
    var PERIOD = 60;

    //一秒钟变化一次太难看
    //var CACHE_VALUE = 0;
    //var AVG_VALUE = 0;

    // 初始数据
    var data = [];

    var startTps = 0;

    (function () {
        var startDate = new Date(new Date().getTime() - ONE_HOUR * 60 * 1000);
        for(var i = 0 ;i < ONE_HOUR ; i++) {
            var date = new Date(startDate.getTime() + (i * 60 * 1000 ) );
            data.push({
                name:date.toString(),
                value:[date,0]
            });
        }
    })();

    // 指定图表的配置项和数据
    var option = {
        // 标题
        title: {
            text: 'inTps'
        },
        // x轴
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        // y轴
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        // 图例
        series: [{
            name: '模拟数据',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: data
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    //首先拿一次数据缓存
    // $.ajax({
    //     type: 'get',
    //     url: 'http://' + url + ':8080/getTpsAndNum',
    //     dataType: 'json',
    //     success: function(data) {
    //         if (data) {
    //             console.log('hello' + data.tps);
    //             CACHE_VALUE = data.tps;
    //         }
    //     }
    // });

    //一分钟拿一次数据
    setInterval(function(){
        $.ajax({
            type: 'get',
            url: 'http://' + url + ':8080/getTpsAndNum',
            dataType: 'json',
            success: function(json_data) {
                // var newCache = data.tps;
                // console.log("newCache:" + data.tps);
                // var temp = newCache - CACHE_VALUE;
                // AVG_VALUE = temp / PERIOD;
                if (json_data) {
                    data.shift();
                    var date = new Date();
                    console.log("now:" + date + "tps:" + json_data.tps);
                    data.push({
                        name: date.toString(),
                        value: [date, json_data.tps]
                    });
                    myChart.setOption({
                        series: [{
                            data: data
                        }]
                    });
                }
            }
        });
    }, PERIOD * 1000);

    //每秒钟变化一下
    // setInterval(function() {
    //     data.shift();
    //     var date = new Date();
    //     CACHE_VALUE = parseFloat(CACHE_VALUE) + parseFloat(AVG_VALUE);
    //     console.log("cache_value:" + CACHE_VALUE);
    //     data.push({
    //         name:date.toString(),
    //         value:[date, CACHE_VALUE]
    //     });
    //     myChart.setOption({
    //         series:[{
    //             data: data
    //         }]
    //     });
    //
    // }, 1000);


});


