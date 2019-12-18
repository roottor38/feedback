var category = [
	{
		label: "2019-12-09"
	},
	{
		label: "2019-12-10"
	},
	{
		label: "2019-12-11"
	}
];

var dataset = [
	{
		seriesname: "Positive",
		data: [
			{
				value: "21"
			},
			{
				value: "24"
			},
			{
				value: "27"
			},
			{
				value: "30"
			}
		]
	},
	{
		seriesname: "Neutral",
		data: [
			{
				value: "0"
			},
			{
				value: "0"
			},
			{
				value: "0"
			},
			{
				value: "0"
			}
		]
	},
	{
		seriesname: "Negative",
		data: [
			{
				value: "2"
			},
			{
				value: "4"
			},
			{
				value: "5"
			},
			{
				value: "5.5"
			}
		]
	}
];

(function drawPosNegChart() {
	var myChart = new FusionCharts({
		type: "stackedcolumn2dline",
		renderAt: "chart-container",
		width: "100%",
		height: "100%",
		dataFormat: "json",
		dataSource: {
			chart: {
				showvalues: "0",
				caption: "2019-12-09 ~ 2019-12-16",
				subcaption: "",
				numberprefix: "",
				numbersuffix: "",
				plottooltext: "Pos/Neg of $seriesName in $label was <b>$dataValue</b>",
				showhovereffect: "1",
				yaxisname: " ",
				showsum: "1",
				theme: "fusion"
			},
			categories: [
				{
					category
				}
			],
			dataset
		}
	}).render();
}());