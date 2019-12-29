var dayMinus1 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-18), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());
var dayMinus2 = (function formatDate() { var d = new Date(), month = '' + (d.getMonth() + 1), day = '' + (d.getDate()-11), year = d.getFullYear(); if (month.length < 2) month = '0' + month; if (day.length < 2) day = '0' + day; return [year, month, day].join('-'); }());


(function invenText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : dayMinus1, "end" : dayMinus2});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("invenText").innerHTML = invenText[0].title;
			var invenURL = invenText[0].url;
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());

function invenPostOpen(invenURL) {
    var win = window.open('http://www.inven.co.kr/board/lineagem/5019/200283', '_blank');
    win.focus();
}

(function homeText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : dayMinus1, "end" : dayMinus2});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("home").innerHTML = invenText[0].title;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());

(function DCText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : dayMinus1, "end" : dayMinus2});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("DCText").innerHTML = invenText[0].title;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());

(function PSText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : dayMinus1, "end" : dayMinus2});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("ps").innerHTML = invenText[0].title;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());