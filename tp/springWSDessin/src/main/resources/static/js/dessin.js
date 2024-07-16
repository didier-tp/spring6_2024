
var stompClient = null;
var numPoint = 0;
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
    initClickListener(); numPoint=0;
	let from = document.getElementById('from').value;
	if(from=="") return;
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



function initClickListener(){
    var eltCanvas = document.getElementById("myCanvas");
    eltCanvas.addEventListener("click", (evt)=>{
        console.log("evt.type="+evt.type);
        console.log("evt.target.id="+evt.target.id);
        console.log("evt.pageX="+evt.pageX);
        console.log("par rapport à la page, evt.pageY="+evt.pageY);
        console.log("par rapport à myCanvas, y="+(evt.pageY - eltCanvas.offsetTop));
        let x = evt.pageX  - eltCanvas.offsetLeft;
        let y = evt.pageY - eltCanvas.offsetTop;
        numPoint++;
        if(numPoint==1){ x1=x;y1=y; }
        else if(numPoint>=2){
             x2=x;y2=y; //for current line
             newLine();
             x1=x2;y1=y2; //for next line
          }
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
	numPoint=0;
}

function showConnexionMessage(message) {
	let lastMessageSpan = document.getElementById('lastReceivedMessage');
	lastMessageSpan.innerHTML="<b>"+message.sender
	  + "</b> :<i> "+ message.content + "</i>";
}

function updateCanvas(allLinesMessage) {
	let lastMessageSpan = document.getElementById('lastReceivedMessage');
	lastMessageSpan.innerHTML="<i>"+JSON.stringify(allLinesMessage) + "</i> ";
	var eltCanvas = document.getElementById("myCanvas");
	var ctx = eltCanvas.getContext("2d");
    ctx.clearRect (0,0,eltCanvas.width,eltCanvas.height);//tout effacer
    //puis tout redessiner:
    for(line of allLinesMessage){
        ctx.beginPath();
        ctx.strokeStyle = line.color;
        ctx.moveTo(line.x1,line.y1);
        ctx.lineTo(line.x2,line.y2);
        ctx.stroke();
    }
}

function showDessinMessage(message) {
	let lastMessageSpan = document.getElementById('lastSentMessage');
	lastMessageSpan.innerHTML="<b>"+JSON.stringify(message) + "</b>";
}
