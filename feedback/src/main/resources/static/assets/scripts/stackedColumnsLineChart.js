
(function drawPosNegChart() {
	let body = JSON.stringify({ start : "2019-12-01", end : "2019-12-07" });
	axios.post("http://localhost:8000/risk", JSON.parse(body))
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
		renderAt: "PosNeg",
		width: "100%",
		height: "100%",
		dataFormat: "json",
		dataSource: {
			chart: {
				showvalues: "0",
				caption: "",
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