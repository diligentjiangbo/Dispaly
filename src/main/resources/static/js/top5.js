$(document).ready(function(){

    var url = '115.159.198.113';

    //初始化echarts
    var myEcharts = echarts.init(document.getElementById("two"));

    var option = {
        title: {
            text: 'top5'
        },
        legend: {
            data: ['inTps']
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: ['one', 'two', 'three', 'four', 'five']
        },
        series: [
            {
                name: 'inTps',
                type: 'bar',
                data: [100, 200, 300, 400, 500]
            }
        ]
    };

    myEcharts.setOption(option);

    setInterval(function(){
        $.ajax({
            type: 'get',
            url: 'http://' + url + ':8080/getTop5',
            dataType: 'json',
            success: function(data) {
                if (!data) return;
                console.log(data);
                var name = [];
                var option_data = [];
                $.each(data, function(index, item){
                    console.log(item);
                    name.push(item.name);
                    option_data.push(item.tps);

                });

                option.yAxis.data = name;

                myEcharts.setOption(option);

                myEcharts.setOption({
//                yAxis: {
//                    data: name
//                },
                    series: [{
                        data: option_data.reverse()
                    }]

                });

            }
        });
    }, 30 * 1000);
});