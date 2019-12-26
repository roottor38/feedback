var dayMinus1 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-1), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());
(function drawPosNegChart() {
	axios.get("http://localhost:8000/risk")
		.then(resData => {
			let getres = resData.data;
			let category = getres[0];
			let pos = getres[1];
			let neg = getres[2];
			posNegDraw(category, pos, neg);
		})
	.catch(error => {
		console.log("비정상 응답", error);
	})
}());
	
	posNegDraw = (category, pos, neg) => {
	var myChart = new FusionCharts({
		type: "stackedcolumn2dline",
		renderAt: "chart-container",
		width: "100%",
		height: "100%",
		dataFormat: "json",
		dataSource: {
			chart: {
				showvalues: "0",
				caption: dayMinus1,
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
			dataset : [
				{
					seriesname: "Positive",
					data: pos
				},
				{
					seriesname: "Neutral",
				},
				{
					seriesname: "Negative",
					data: neg
				}
			]
		}
	}).render();
};