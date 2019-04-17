$(function(){
	exam_statistics_scoreTab_onFormconditionChg();
	$("#exam-statistics-score-link").on('shown.bs.tab', exam_statistics_onScoreTabShow);
	$("#exam-statistics-region-link").on('shown.bs.tab', exam_statistics_onRegionTabShow);
	
	$("#exam-statistics-score-tab-examId").change(exam_statistics_scoreTab_onFormconditionChg);
	$("#exam-statistics-region-tab-examId").change(exam_statistics_RegionTab_onFormconditionChg);
});

function exam_statistics_onRegionTabShow(){
	exam_statistics_RegionTab_onFormconditionChg();
}

function exam_statistics_RegionTab_onFormconditionChg(){
	var param = {};
	param.callback = function(isSuccess, data){
		if(data == null){
			return;
		}
		
		
		var total = 0;
		var percent = [];
		for(var i = 1; i < 7; ++i){
			var count = data['region' + i];
			$("#exam-statistics-region" + i).text(count);
			total += count;
		}
		
		var option = {
		    title : {
		        text: ''
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        type: 'scroll',
		        orient: 'vertical',
		        right: 10,
		        top: 20,
		        bottom: 20,
		        data: ['北京', '广东', '四川', '湖南', '上海', '辽宁'],
		        selected: {'北京':true, '广东':true, '四川':true, '湖南':true, '上海':true, '辽宁':true}
		    },
		    series : [
		        {
		            name: '姓名',
		            type: 'pie',
		            radius : '55%',
		            center: ['40%', '50%'],
		            data: [{name: '北京',value: data['region1']},{name: '广东',value: data['region2']},{name: '四川',value: data['region3']},{name: '湖南',value: data['region4']},{name: '上海',value: data['region5']},{name: '辽宁',value: data['region6']}],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
		
		
		
		exam_statistics_setCharInfo(option, "exam-statistics-region-tab-char");
	};
	
	comm_Ajax_submitForm("exam-statistics-region-tab-form", param);
}




function exam_statistics_onScoreTabShow(e){
	exam_statistics_scoreTab_onFormconditionChg();
}

function exam_statistics_scoreTab_onFormconditionChg(){
	var param = {};
	param.callback = function(isSuccess, data){
		if(data == null){
			return;
		}
		
		for(var i = 1; i < 9; ++i){
			$("#exam-statistics-score-range" + i).text(data['scoreRange' + i]);
		}
		 var option = {
			     title: {
			         text: ''
			     },
			     tooltip: {},
			     legend: {
			         data:['人数']
			     },
			     xAxis: {
			         data: ["0-100","100-200","200-300","300-400","400-500","500-600","600-700", "700以上"]
			     },
			     yAxis: {},
			     series: [{
			         name: '人数',
			         type: 'bar',
			         data: [data['scoreRange1'], data['scoreRange2'], data['scoreRange3'], data['scoreRange4'], data['scoreRange5'], data['scoreRange6'], data['scoreRange7'], data['scoreRange8']]
			     }]
			 };
		 exam_statistics_setCharInfo(option, "exam-statistics-score-tab-char");
	};
	
	comm_Ajax_submitForm("exam-statistics-score-tab-form", param);
}

function exam_statistics_setCharInfo(option, divId){
	 // 基于准备好的dom，初始化echarts实例
	 var myChart = echarts.init(document.getElementById(divId));
	 // 指定图表的配置项和数据
	 // 使用刚指定的配置项和数据显示图表。
	 myChart.setOption(option);
}



