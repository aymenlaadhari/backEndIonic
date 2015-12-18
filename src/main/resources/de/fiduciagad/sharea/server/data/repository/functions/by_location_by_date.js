function (doc) {
  var karlsruhe = doc.placeFrom.match(/Karlsruhe/);
  var muenster = doc.placeFrom.match(/Münster/);
  var frankfurt = doc.placeFrom.match(/Frankfurt/);
  var berlin = doc.placeFrom.match(/Berlin/);
  var muenchen = doc.placeFrom.match(/München/);
  
  if(muenster){
    emit([muenster[0], doc.timeFrom], doc);
  }
  else if(karlsruhe){
   emit([karlsruhe[0], doc.timeFrom], doc);
  }
  else if(frankfrut){
    emit([frankfurt[0], doc.timeFrom], doc);
  }
  else if(berlin){
    emit([berlin[0], doc.timeFrom], doc);
  }
  else if(muenchen){
    emit([muenchen[0], doc.timeFrom], doc);
  }
}