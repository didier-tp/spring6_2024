
var stompClient = null;
var x1=0,y1=0,x2=0,y2=0;

function setConnected(connected) {
	document.getElementById('connect').disabled = connected;
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('dessinDiv').style.visibility
		= connected ? 'visible' : 'hidden';
	document.getElementById('lastSentMessage').innerHTML = '';
	document.getElementById('lastReceivedMessage').innerHTML = '';
}

function connect() {
	let from = document.getElementById('from').value;
	if(from=="") return;
	initMyEventListeners();
	let socket = new SockJS('/springWSDessin/dessin-ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		
		stompClient.subscribe('/topic/connexion', function(message) {
			showConnexionMessage(JSON.parse(message.body));
		});

		stompClient.subscribe('/topic/allLines', function(message) {
        			updateCanvas(JSON.parse(message.body));
        		});
		
		stompClient.send("/app/connexion", {},
		                  JSON.stringify({ type: "JOIN" , sender: from, content: "is connected" }));
	});
}



function initMyEventListeners(){
    var eltCanvas = document.getElementById("myCanvas");
    eltCanvas.addEventListener("mousedown", (evt)=>{
        x1 = evt.pageX  - eltCanvas.offsetLeft;
        y1 = evt.pageY - eltCanvas.offsetTop;
        console.log(`mousedown : x1=${x1} y1=${y1}`);
        });
    eltCanvas.addEventListener("mouseup", (evt)=>{
            x2 = evt.pageX  - eltCanvas.offsetLeft;
            y2 = evt.pageY - eltCanvas.offsetTop;
            console.log(`mouseup :  x2=${x2} y2=${y2}`)
            let color = document.getElementById('selColor').value;
            let ctx = eltCanvas.getContext("2d");
            drawLineWithColor(ctx,x1,y1,x2,y2,color);
            });
}

function disconnect() {
	let from = document.getElementById('from').value;
	if (stompClient != null) {
		stompClient.send("/app/connexion", {},
		                  JSON.stringify({ type: "LEAVE" , sender: from, content: "is disconnected" }));
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function refreshDessin(){
let dessinMessage = {  type: "REFRESH" };
stompClient.send("/app/dessin", {},JSON.stringify(dessinMessage));
}

function newLine() {
	let color = document.getElementById('selColor').value;
	let dessinMessage = {
	  type: "NEWLINE" ,
	  line: { x1: x1 , y1 : y1 ,
	         x2: x2 , y2 : y2,
	         color : color}
	  };
	showDessinMessage(dessinMessage);
	stompClient.send("/app/dessin", {},JSON.stringify(dessinMessage));
}

function clearDessin() {
    let dessinMessage = { type: "CLEAR" };
    showDessinMessage(dessinMessage);
	stompClient.send("/app/dessin", {},JSON.stringify(dessinMessage));
}

function showConnexionMessage(message) {
	let lastMessageSpan = document.getElementById('lastReceivedMessage');
	lastMessageSpan.innerHTML="<b>"+message.sender
	  + "</b> :<i> "+ message.content + "</i>";
}

function drawLineWithColor(ctx,_x1,_y1,_x2,_y2,_color) {
    ctx.beginPath();
    ctx.strokeStyle = _color;
    ctx.moveTo(_x1,_y1);
    ctx.lineTo(_x2,_y2);
    ctx.stroke();
}

function updateCanvas(allLinesMessage) {
	let lastMessageSpan = document.getElementById('lastReceivedMessage');
	lastMessageSpan.innerHTML="<i>"+JSON.stringify(allLinesMessage) + "</i> ";
	var eltCanvas = document.getElementById("myCanvas");
	var ctx = eltCanvas.getContext("2d");
    ctx.clearRect (0,0,eltCanvas.width,eltCanvas.height);//tout effacer
    //puis tout redessiner:
    for(line of allLinesMessage){
       drawLineWithColor(ctx,line.x1,line.y1,line.x2,line.y2,line.color)
    }
}

function showDessinMessage(message) {
	let lastMessageSpan = document.getElementById('lastSentMessage');
	lastMessageSpan.innerHTML="<b>"+JSON.stringify(message) + "</b>";
}
