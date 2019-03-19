var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
function reqListener () {
    console.log(this.responseText);
  }
  
  var oReq = new XMLHttpRequest();
  oReq.addEventListener("load", reqListener);
  oReq.open("PUT", "http://localhost:8080/addSkill?skill=PHP");
  oReq.send();