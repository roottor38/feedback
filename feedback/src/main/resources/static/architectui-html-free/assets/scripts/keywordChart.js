var dayMinus1 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-9), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());
var dayMinus2 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-2), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());

(function drawKeywordChart() {
	//let body = '{"start": ' +'"'+ dayMinus1 +'"'+ ', "end" : ' +'"'+ dayMinus2 +'"'+ '}'
	//let body = { start : dayMinus1, end : dayMinus2};
	let body = JSON.stringify({ start : dayMinus1, end : dayMinus2});
	console.log(body)
		axios.post("http://localhost:8000/keyword", JSON.parse(body))
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
					subcaption: "최근 일주일 합산 상위 10개 데이터",
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