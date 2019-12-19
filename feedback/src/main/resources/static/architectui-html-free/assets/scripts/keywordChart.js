(function drawKeywordChart() {
		axios.get("http://localhost:8000/getKeyword")
			.then(resData => {
				data = resData.data;
				keywordDraw(data)
			})
		.catch(error => {
			console.log("비정상 응답", error);
		});
	}());
	
  
	keywordDraw = data => {
		const myChart = new FusionCharts({
			type: "doughnut2d",
			renderAt: "keyword",
			width: "100%",
			height: "100%",
			dataFormat: "json",
			dataSource: {
				chart: {
					caption: "리니지M 키워드",
					subcaption: "상위 10개 데이터",
					showpercentvalues: "1",
					defaultcenterlabel: "인벤",
					aligncaptionwithcanvas: "0",
					captionpadding: "0",
					decimals: "1",
					theme: "fusion"
				},
				data
			}
		}).render();
	};