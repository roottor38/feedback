function scraping() {
    alert("스크레이핑 진행함돠")
    axios.get("http://127.0.0.1:8000/scraping")
    .then(resData => {
        alert(resData.data)
    })
.catch(error => {
    console.log("비정상 응답", error);
});
}
function wordcount() {
    alert("워드카운트 진행함돠")
    axios.get("http://127.0.0.1:8000/wordcount")
    .then(resData => {
        alert(resData.data)
    })
.catch(error => {
    console.log("비정상 응답", error);
});
}