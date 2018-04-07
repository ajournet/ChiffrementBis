<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Outil de chiffrement (Stéganographie)</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            a {
                text-decoration: none;
            }
        </style>
        <script> 
            $(function(){
              $("#footer").load("footer.html"); 
            });
        </script> 
    </head>
    <body style="background-color:#bfbfbf;">
  	<input type="hidden" id="URLAudio" name="URLAudio">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand" href="index.html">Outil de chiffrement (Stéganographie)</a>
              </div>
              <ul class="nav navbar-nav">
                <li><a href="index.html">Home</a></li>
                <li class="active"><a href="chiffrement.jsp">Chiffrer</a></li>
                <li><a href="dechiffrement.jsp">Dechiffrer</a></li>
              </ul>
            </div>
          </nav>
        <label for="dlFile">Voici votre fichier avec votre message :</label>
        <button id="dlFile" type="button" class="btn btn-danger btn-lg active" style="text-align: center;">Télécharger</button>
        
        <div id="footer"></div>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="js/vendor/jquery.ui.widget.js"></script>
    </body>
</html>