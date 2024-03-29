<!DOCTYPE html>
<html>
   <head>
      <title>Spring Boot WebSocket</title>
     <link rel="stylesheet" href="./css/main.css" />
      
      <!-- https://cdnjs.com/libraries/sockjs-client -->
      <!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script> --> 
      <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script> -->
       <script src="js/sockjs.min.js"></script>
      <!-- https://cdnjs.com/libraries/stomp.js/ -->
      <!--  <script  src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script> --> 
      <script  src="js/stomp.min.js"></script> 
     
      
   </head>
   <body>
      <div id="chat-container">
         <div class="chat-header">
            <div class="user-container">
               <span id="username">${username}</span>
               <a href="./logout">Logout</a>
            </div>
            <h3>Spring WebSocket Chat Demo</h3>
         </div>
         
         <hr/>
         
         <div id="connecting">Connecting...</div>
         <ul id="messageArea">
         </ul>
         <form id="messageForm" name="messageForm">
            <div class="input-message">
               <input type="text" id="message" autocomplete="off"
                  placeholder="Type a message..."/>
               <button type="submit">Send</button>
            </div>
         </form>
      </div>
      
      <script src="./js/main.js"></script>
      
   </body>
</html>