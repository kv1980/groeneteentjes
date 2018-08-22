document.getElementById('zoekForm').onsubmit = zoekFiliaal;

function zoekFiliaal() {
	const request = new XMLHttpRequest();
	request.open("GET",document.getElementById('filiaalId').value,true);
	request.setRequestHeader('accept','application/json');
	request.onload = responsIsBinnenGekomen;
	request.send();
	return false;
}

function responsIsBinnenGekomen() {
	switch (this.status) {
	case 200:
		const filiaalResource = JSON.parse(this.responseText);
		const filiaal = filiaalResource.filiaal;
		document.getElementById('naam').innerHTML = filiaal.naam;
		const adres = filiaal.adres;
		document.getElementById('adres').innerHTML = adres.straat+' '+adres.huisNr+' '+adres.postcode+' '+adres.gemeente;
		break;
	case 404:
		alert('Filiaal bestaat niet');
		break;
	default:
		alert('Technisch probleem')
	}
}

function verwijderBericht() {
	const request = new XMLHttpRequest();
	request.open("DELETE",document.getElementById('berichtId').value,true);
	request.setRequestHeader('accept','application/json');
	request.send();
	document.getElementById('berichtId').delete();
	return false;
}