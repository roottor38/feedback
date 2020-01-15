(function drawKeywordChart() {
	let body = JSON.stringify({ start : "2019-12-01", end : "2019-12-07"});
		axios.post("http://localhost:8000/keyword", JSON.parse(body))
			.then(resData => {
				data = resData.data;
				keywordDraw(data);
			})
		.catch(error => {
			console.log("비정상 응답", error);
		});
	}());
	
	keywordDraw = data => {
		let myChart = new FusionCharts({
			type: "doughnut2d",
			renderAt: "keyword",
			width: "100%",
			height: "100%",
			dataFormat: "json",
			dataSource: {
				chart: {
					caption: "Top5 Weekly Keyword ",
					subcaption: "Top 5",
					showpercentvalues: "1",
					defaultcenterlabel: "Keyword",
					aligncaptionwithcanvas: "0",
					captionpadding: "0",
					decimals: "1",
					theme: "fusion"
				},
				data
			}
		}).render();
	};