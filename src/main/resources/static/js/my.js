$(document).ready(function(){
    period = 1000;

    var date = [];

    var data = [Math.random() * 100];
    var now = new Date();

    function addData(shift) {
        var now_format = [now.getHours(), now.getMinutes(), now.getSeconds()].join(':');


        if (!shift) {
            date.push(now_format)
            data.push((Math.random() - 0.4) * 10 + data[data.length - 1]);
        }

        if (shift) {
            //date.shift();
            //data.shift();
        }

        now = new Date(now).getTime() + period;


        now = new Date(now);

    }


    addData();
    addData();
    addData();
    addData();
    addData();


    var myChartOne = echarts.init(document.getElementById('one'));
    var myChartTwo = echarts.init(document.getElementById('two'));

    var option = {
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: date
        },
        yAxis: {
            boundaryGap: [0, '50%'],
            type: 'value'
        },
        series: [{
            type: 'line',
            smooth: true,
            symbol: 'none',
            areaStyle: {
                normal: {}
            },
            data: data
        }]
    };

    myChartOne.setOption(option);
    myChartTwo.setOption(option);

    setInterval(function(){
        $.ajax({
            type:'post',
            data:{},
            url:'http://localhost:8080/js_data',
            dataType:'json',
            success:function(json_data) {
                //alert(data.tps)
                data.push(json_data.tps);
                //alert(data.num)
            }
        });
        addData(true)
        myChartOne.setOption({
            xAxis: {
                data:date
            },
            series: [{
                data:data
            }]
        });
        myChartTwo.setOption({
            xAxis: {
                data:date
            },
            series: [{
                data:data
            }]
        });
    },1000);
});


