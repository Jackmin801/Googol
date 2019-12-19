package com.jacksteam.googol.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirerController implements ErrorController {

  @RequestMapping("/error")
  @ResponseBody
  public String handleError(HttpServletRequest request) {
      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
      //Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
      String errorPage ="<html><style>*{margin: 0;padding: 0;}body{text-align: center;background-color: #1a4531;color:white}h4{font-size: 4em;font-family: Verdana, Geneva, Tahoma, sans-serif;}html{height: 100%;}</style>";
          errorPage += "<head><title>MILO SPILL!!!</title></head>";
          //errorPage += String.format("<body><div height=100%><div height=600px><h4>Error %s </h4>",statusCode);
          errorPage += "<body><div height=100%><div height=600px><h4>Error "+statusCode+" </h4>";
          errorPage += "<img height=60% width=40% src=\"http://localhost:8080/catmilo.png\">";
          errorPage += "</div><div height=200px><h1>Oh No! We spilled the Milo.</h1><h2>Get a new cup of Milo</h2><br>";
          errorPage +="<a href=\"http://localhost:8080/jacksteam/googol/api/query?q=hello\"><img src=\"http://localhost:8080/milostix.png\" height=200></a>";       
          errorPage +="</div></div></body></html>";            
                      
        return errorPage;   
                      
    //   return String.format("<html><body><h2>%s UwU we spwilled thwe mwilo!</h2><div><a href=\"http://localhost:8080/jacksteam/googol/api/query?q=n+.++\">Get a new cup of Milo</a></div>"
    //                   + "<div>Exception Message: <b>%s</b></div><body></html>",
    //           statusCode);
  }


  @Override
  public String getErrorPath() {
      return "/error";
  }
}


