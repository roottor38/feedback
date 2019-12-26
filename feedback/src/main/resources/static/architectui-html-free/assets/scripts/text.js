(function invenText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : "2019-12-01", "end" : "2019-12-07"});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("invenText").innerHTML = invenText[0].body;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());

(function DCText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : "2019-12-01", "end" : "2019-12-07"});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("DCText").innerHTML = invenText[0].body;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());

(function homeText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : "2019-12-01", "end" : "2019-12-07"});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("home").innerHTML = invenText[0].body;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());

(function PSText() {
	let body = JSON.stringify({"community": "리니지M 인벤", "start" : "2019-12-01", "end" : "2019-12-07"});
	axios.post("http://localhost:8000/text", JSON.parse(body))
		.then(resData => {
			let invenText = resData.data;
			document.getElementById("ps").innerHTML = invenText[0].body;
			
		})
	.catch(error => {
		console.log("비정상 응답", error);
	});
}());