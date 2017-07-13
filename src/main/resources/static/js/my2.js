$(document).ready(function(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('one'));

    // 常量
    var ONE_HOUR = 60;
    var MAX = 2000;

    // 初始数据
    var data = [];

    var startTps = 0;

    (function () {
        var startDate = new Date(new Date().getTime() - ONE_HOUR * 1000);
        for(var i = 0 ;i < ONE_HOUR ; i++) {
            var date = new Date(startDate.getTime() + (i * 1000 ) );
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
            text: '实时tps'
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

    // setInterval(function () {
    //     data.shift();
    //     var date = new Date();
    //     console.log("time",date);
    //     data.push({
    //         name:date.toString(),
    //         value:[date,Math.random()*MAX+.5|0]
    //     });
    //     myChart.setOption({
    //         series: [{
    //             data: data
    //         }]
    //     });
    // }, 60000);

    setInterval(function() {
        $.ajax({
            type:'get',
            //data:{},
            url:'http://115.159.198.113:8080/getTpsAndNum',
            dataType:'json',
            success:function(json_data) {
                //alert(data.tps)
                //alert(data.num)
                data.shift();
                var date = new Date();
                data.push({
                    name:date.toString(),
                    value:[date, json_data.tps]
                });

            }
        });
        myChart.setOption({
            series:[{
                data: data
            }]
        });

    }, 1000);


});


