dessin-ws
app/
topic/
=================
model.
     DessinMessage(type,line) avec comme type possible: NEWLINE, CLEAR , REFRESH
     Line (x1,y1,y2,y2,color) sous objet éventuellement null de DessinMessage
     ConnectedMessage(type,sender,content) avec comme type possible: JOIN, LEAVE

controller.
     WebSocketController
     comportant private List<Line> allLines = new ArrayList<>();
               et .refreshAllLines(DessinMessage)
                  .notifyConnexion(ConnectedMessage)

NB:  notifyConnexion ecoutera les "ConnectedMessage" sur /connexion et les retransmettra sur /topic/connexion 
     refreshAllLines ecoutera les "DessinMessage" sur /dessin 
                     mettra à jour this.allLines selon le type du message entrant (NEWLINE avec line non null ou bien CLEAR pour tout effacer
                                                                                   ou bien REFRESH pour ne rien changer et simplement obtenir un "REFRESH" à jour)
                     retournera this.allLines sur /topic/allLines

=============
Ceci n'est qu'un embryon de dessin collaboratif via spring/WS/STOMP .
on peut faire beaucoup mieux avec plus de temps (dessiner "circle" , "rectangle" , …) et autres fonctionnalités ("undo" , …)

      