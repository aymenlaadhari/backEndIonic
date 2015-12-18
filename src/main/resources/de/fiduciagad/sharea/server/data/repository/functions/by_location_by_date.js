function (doc) {
	if (doc.docType === 'Share') {
		var karlsruhe = doc.startLocation.match(/Karlsruhe/);
		var muenster = doc.startLocation.match(/Münster/);
		var frankfurt = doc.startLocation.match(/Frankfurt/);
		var berlin = doc.startLocation.match(/Berlin/);
		var muenchen = doc.startLocation.match(/München/);
		
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
}