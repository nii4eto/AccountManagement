function searchFilter() {
	
	var input = $("#searchField").val().toUpperCase();
	var tableRows = $("tbody tr");

	for (var i = 0; i < tableRows.length; i++) {
		var match = false;
		var cellValues = $($(tableRows)[i]).find("td");

		for (var j = 0; j < cellValues.length; j++) {
			if (cellValues[j]) {
				if ($(cellValues[j]).text().trim().toUpperCase().indexOf(input) > -1) {
					match = true;
				}
			}
		}
		
		if(!match) {
			$($(tableRows)[i]).css( "display", "none");
		} else {
			$($(tableRows)[i]).css( "display", "");
		}
	}
}