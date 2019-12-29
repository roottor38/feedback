var dayMinus1 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-15), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());
var dayMinus2 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-10), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());

(function drawKeywordChart() {
	let body = JSON.stringify({ start : dayMinus1, end : dayMinus2});
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