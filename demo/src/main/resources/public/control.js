/**
 * 
 */

$(document).ready(function() {
	let $listeLivres = $("#listLivres");         /*UL des Livres contenus en BD*/
	let $listePersonnes = $("#listPersonnes");   /*UL des Personnes contenues en BD*/
	let $selectLivres = $("#selectLivres");		 /*UL des Livres à sélectionner dans l'ajout de personne*/
	let $listeBiblios = $("#listBiblios");		 /*UL des Bibliohèques contenues BD */
	let $InputLivre = $("#filt-input");			 /*input de recherche de livre*/
	let $RechbtnLiv =$("#RechbtnLivre");		 /*bouton recherche livre*/
	let $selectBiblio =$("#selectBib");			 /*UL des Bibliothèques à sélectionner dans l'ajout de personne*/
	let listLiv = [];                  			 /*Liste intermédiaire pour le filtrage des livres*/
	let $RechBib= $("#bib-input1");				 /* input de recherche Bibliothèque*/
	let $Rechbtn= $("#RechbtnBiblio1"); 		 /* bouton recherche Bibliothèque*/
	let listBib = [];							 /*Liste intermédiaire pour le filtrage des Bibliothèques*/
	let listPers =[];							 /*Liste intermédiaire pour le filtrage des Personnes*/
	let $RechbtnPers =$("#RechbtnPers1");		 /* bouton recherche personne*/
	let $InputPers = $("#pers-input1");			 /*input de recherche de personne*/
	
	
	/*Ajout des Bibliothèques de la base de données à la liste des bibliothèques et celle des bibliothèques à sélectionner dans les pages HTML*/
	$.get("http://localhost:8080/api/biblio",function(resp){
		listBib = resp;
		resp.forEach( b => {
			appendToListBiblio(b)
			appendToSelectsBib(b);
			
		});
	});
	
	
	/*Ajout des personnes de la base de données à la liste des personne dans la page HTML*/
    $.get("http://localhost:8080/api/personnes",function(resp){
		console.log("CONSOLE GET PERSONNES",resp);
		listPers = resp;
		resp.forEach( p => appendToListPersonne(p));
    });


	/*Ajout des Livres de la base de données à la liste des livres et celle des livres à sélectionner dans les pages HTML*/
	$.get("http://localhost:8080/api/livres",function(resp){
		listLiv=resp;
		resp.forEach( l => {
			appendToListLivre(l);
			appendToSelects(l);
			
		});
	});
	
	
	/*Au clique sur le bouton de recherche d'une bibliothèque, on remplace l'affichage de la liste des bibliothèques par la liste des bibliothèques contenant le input de recherche dans leurs noms */
	$Rechbtn.click(function() {
  		let searchFilter = $RechBib.val();
		$listeBiblios.empty();
		filtresBib(searchFilter).forEach( b => appendToListBiblio(b));
  		
	});
	
	
	/*Au clique sur le bouton de recherche d'un Livre, on remplace l'affichage de la liste des livres par la liste des livres contenant le input de recherche dans leurs titres, noms ou prénoms d'auteurs*/
	$RechbtnLiv.click(function() {
  		let searchFilter = $InputLivre.val();
		$listeLivres.empty();
		filtresLiv(searchFilter).forEach( l => appendToListLivre(l));
		
  		
	});
	
	
	/*Au clique sur le bouton de recherche d'une Personne, on remplace l'affichage de la liste des personnes par la liste des personnes contenant le input de recherche dans leurs noms, prénoms, bibliothèque */
	$RechbtnPers.click(function() {
  		let searchFilter = $InputPers.val();
		$listePersonnes.empty();
		filtresPers(searchFilter).forEach( p => appendToListPersonne(p));
  		
	});
	
	
	/*Fonction qui filtre la liste des livres*/
	function filtresLiv(searchFilter) {
		return listLiv.filter(l => l.titre.includes(searchFilter)||l.auteur.nom.includes(searchFilter)||l.auteur.prenom.includes(searchFilter));
	}
	
	
	/*Fonction qui filtre la liste des bibliothèques*/
	function filtresBib(searchFilter) {
		return listBib.filter(b => b.nom.includes(searchFilter)||b.adresse.includes(searchFilter));
	}
	
	/*Fonction qui filtre la liste des personnes*/
	function filtresPers(searchFilter) {
		return listPers.filter(p => p.nom.includes(searchFilter)||p.prenom.includes(searchFilter)||p.nomBiblio.includes(searchFilter)||p.titre.includes(searchFilter));
	}
	

	

	/*Au clique sur la croix devant l'affichage d'une personne, on supprime celle-ci de la base de données */
	$listePersonnes.on("click", "li button", function() {
    	let elemid = $(this).parent().attr('id');
    	
    	$.ajax({/* envoie de la requete delete avec l'id de la personne à supprimer au serveur */
		    type: "DELETE",
		    url: "http://localhost:8080/api/personnes/" + elemid.replace('personne-',''),
		    dataType: "json",
		    success: function(data){
		    	$("#"+elemid).remove(); /* suppression */
		    }
		});	
    });


	/*Au clique sur la croix devant l'affichage d'un livre, on supprime celui-ci de la base de données */
    $listeLivres.on("click", "li button", function() {
        	let elemid = $(this).parent().attr('id');
            let idLivre = elemid.replace('livre-','');

        	$.ajax({/* envoie de la requete delete avec l'id du livre à supprimer au serveur */
    		    type: "DELETE",
    		    url: "http://localhost:8080/api/livres/" + idLivre,
    		    dataType: "json",
    		    success: function(data){
    		    	$("#"+elemid).remove();/* suppression */
    		    	$(`#selectLivres option[value="${idLivre}"]`).remove();
    		    },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(xhr);
                    alert("Impossible de supprimer le livre, il est peut-être encore utilisé");/*Exception levée, lorsqu'on veut supprimer un livre qui est déja utilisé par une personne*/
                }
    		});
        });


	/*Au clique sur la croix devant l'affichage d'une bibliothèque, on supprime celle-ci de la base de données */
	$listeBiblios.on("click", "li button", function() {
        	let elemid = $(this).parent().attr('id');
            let idBiblio = elemid.replace('biblio-','');

        	$.ajax({/* envoie de la requete delete avec l'id de la bibliothèque à supprimer au serveur */
    		    type: "DELETE",
    		    url: "http://localhost:8080/api/biblio/" + idBiblio,
    		    dataType: "json",
    		    success: function(data){
    		    	$("#"+elemid).remove();;/* suppression */
    		    	$(`#selectbiblios option[value="${idBiblio}"]`).remove();
    		    },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(xhr);
                    alert("Impossible de supprimer la bibliothèque, elle est peut-être encore utilisée");
                }
    		});
        });
    
	/*Au clique sur le bouton d'ajout d'une personne, ajax envoie les données en input au serveur avec une requete POST pour les enregistrer en base de données*/
    $('#addbtnPersonne').click(function(){
		let prenom = $('#prenom-input').val();
		let nom = $('#nom-input').val();
		let age = $('#age-input').val();
		let ville = $('#depart-input').val();
		let date = $('#date-input').val();
		let idLivre = $selectLivres.val();
		let idBiblio = $selectBiblio.val();
		
		
		$.ajax({/* envoie de la requete post et les données en JSON */
		    type: "POST",
		    url: "http://localhost:8080/api/personnes",
		    data: JSON.stringify({ "prenom": prenom,"nom": nom, "age" : age,"idLivre" : idLivre,"ville": ville,"idBiblio" : idBiblio, "date": date }),
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		        console.log(data);
				appendToListPersonne(data);
				//addLivreToPerson(data.id, idLivre);
				//addBiblioToPerson(data.id, idBiblio)
				
		    }
		});
		
		//gestionEvenementsLiv(idLivre);
		
		$('#prenom-input').val('');
		$('#nom-input').val();
		$('#age-input').val('');
		$('#depart-input').val();
		$('#selectBib').val();
		$('#date-input').val();
		return false;
	});
	

	/*Au clique sur le bouton d'ajout d'un livre, ajax envoie les données en input au serveur avec une requete POST pour les enregistrer en base de données*/
	$('#addbtnLivre').click(function(){
		let livre = {
			titre: $('#title-input').val(),
			nbPages: $('#nbpage-input').val(),
			auteur: {
				nom: $('#nom-auteur-input').val(),
				prenom: $('#prenom-auteur-input').val()
			}
		};
		

		$.ajax({/* envoie de la requete post et les données en JSON */
			type: "POST",
			url: "http://localhost:8080/api/livres",
			data: JSON.stringify(livre),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data){
				console.log(data);
				appendToListLivre(data); /*Ajout livre à la liste des livres HTML */
				appendToSelects(data); /*Ajout livre à la liste des livres à sélectionner lors de l'ajout d'une personne*/
			}
		});

		$('#title-input').val('');
		$('#nbpage-input').val('');
		$('#nom-auteur-input').val('');
		$('#prenom-auteur-input').val('');
		return false;
	})

	
	/*Au clique sur le bouton d'ajout d'une bibliothèque, ajax envoie les données en input au serveur avec une requete POST pour les enregistrer en base de données*/		
	$('#addbtnBiblio').click(function(){
		let valeur = $('#nombib-input').val()
		let valeur2 = $('#adr-input').val()
		
		let bib={
			nomBiblio : valeur,
			adresseBiblio : valeur2
		}
		
		$.ajax({
			type: "POST", /* envoie de la requete post et les données en JSON */
			url: "http://localhost:8080/api/biblio",
			data: JSON.stringify(bib),	
				
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(data){
				console.log(data);
				appendToListBiblio(data) /*Ajout bibliothèque à la liste des bibliotheques HTML */
				appendToSelectsBib(data); /*Ajout bibliothèque à la liste des bibliotheques à sélectionner lors de l'ajout d'une personne*/
			}
		});

		$('#nombib-input').val('');
		return false;
	})

	
	/* Ajoute un élément li dans la liste de livres*/
	function appendToListLivre(livre) {
		
	    $listeLivres.append(`<li id="livre-${livre.id}" class="list-group-item">${livre.titre} - ${livre.auteur.nom} ${livre.auteur.prenom}<button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >X</button> </li>`);
		console.log($listeLivres)
	}
	
	
	/* Ajoute un élément li dans la liste des bibliothèques*/
	function appendToListBiblio(biblio) {
		$listeBiblios.append(`<li id="biblio-${biblio.id}" class="list-group-item">${biblio.nom}-${biblio.adresse}<button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >X</button> </li>`)
		console.log($listeBiblios);
	}
	
	/* Ajoute un élément li dans la liste des bibliothèques à sélectionner*/
	function appendToSelectsBib(biblio) {
		$selectBiblio.append(`<option value="${biblio.id}">${biblio.nom}</option>`)
	}

	/* Ajoute un élément li dans le select des livres*/
	function appendToSelects(livre) {
		$selectLivres.append(`<option value="${livre.id}">${livre.titre}</option>`);
		console.log(livre)
		
	}
	
	/* Ajoute un élément li dans la liste de personne*/
	function appendToListPersonne(personne) {
	    let liToAppend = `<li id="personne-${personne.id}" class="list-group-item">${personne.prenom} ${personne.nom} ${personne.age} - ${personne.ville} - ${personne.date_emprunt}`;
	    personne.livres.forEach( livre => liToAppend+= ` - ${livre.titre} `);
	    personne.biblios.forEach( b => liToAppend+= ` - ${b.nom} `);
		liToAppend+= ` <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >X</button></li>`;
		$listePersonnes.append(liToAppend);
	}

	/* Ajout un livre à une personne existante*/
	/*function addLivreToPerson(idPersonne, idLivre) {
	    $.ajax({
    			type: "POST",
    			url: "http://localhost:8080/api/personnes/"+idPersonne+"/livres",
    			data: JSON.stringify({"idLivre" : idLivre}),
    			contentType: "application/json; charset=utf-8",
    			dataType: "json",
    			success: function(data){
					console.log(data)
    				appendToListPersonne(data)
    			}
    	});
	}
	
	
	function addBiblioToPerson(idPersonne, idBiblio){
		$.ajax({
    			type: "POST",
    			url: "http://localhost:8080/api/personnes/"+idPersonne+"/biblio",
    			data: JSON.stringify({"idBiblio" : idBiblio}),
    			contentType: "application/json; charset=utf-8",
    			dataType: "json",
    			success: function(data){
    				appendToListPersonne(data);
    			}
    	});
	}*/
});