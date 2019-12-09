google.charts.load("current", {packages:["calendar"]});

function drawActiveChart() {
		console.log("drawChart")
		axios.get("http://localhost:8000/getActive")
			.then(resData => {
				data = resData.data;
				activeDraw(data)
			})
		.catch(error => {
			console.log("비정상 응답", error);
		});
	}

	activeDraw = data => {
		console.log("ActiveDraw")
	    const dataTable = new google.visualization.DataTable();
	    dataTable.addColumn({ type: 'date', id: 'Date' });
	    dataTable.addColumn({ type: 'number', id: 'posts' });
	    console.log(data)
	    dataTable.addRows(data.map(x => [new Date(x[0]), x[1]]))
	     const options = {
	       title: "커뮤니티 활동량",
	       height: 350,
	     };
	    const chart = new google.visualization.Calendar(document.getElementById('chart-container'));
	     chart.draw(dataTable, options);
 }