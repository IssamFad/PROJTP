package com.example.demo;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("personnes")
public class PersonneResource {
	@Autowired //Instantiation du Crud
	private PersonneRepository personneRepository;
	@Autowired
	private LivreRepository livreRepository;
	@Autowired
	private BibliothequeRepository biblioRepository;
	
	Personne personne=new Personne();

	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Personne createPersonne(Personne p) {
		return personneRepository.save(p);
	}*/

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Personne> getAllPersonne() {
		List<Personne> personnes = new ArrayList<>();
		personneRepository.findAll().forEach(personnes::add);
		return personnes;
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Personne updateTotalyPersonne(@PathParam("id") Long id, Personne p) {
		p.setId(id);
		return personneRepository.save(p);
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePersonne(@PathParam("id") Long id) {
		if (personneRepository.findById(id).isPresent()) {
			personneRepository.deleteById(id);
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getpersonneById(@PathParam("id") Long id) {
		Optional<Personne> p = personneRepository.findById(id);
		if (p.isPresent()) {
			return Response.ok(p.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	// PATCH /personnes/{id}
	public Response updateAge(@PathParam("id") Long id, Personne p) {
		int age = p.getAge();
		Optional<Personne> optional = personneRepository.findById(id);

		if (optional.isPresent()) {
			Personne pBDD = optional.get();
			pBDD.setAge(age);
			personneRepository.save(pBDD);
			return Response.ok(pBDD).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("{id}/livres")
	@Produces(MediaType.APPLICATION_JSON)
	// GET /personnes/{id}/livres
	public List<Livre> listerLivres(@PathParam("id") Long id) {
		return personneRepository.findById(id).get().getLivres();
	}
	
	
	@GET
	@Path("{id}/biblio")
	@Produces(MediaType.APPLICATION_JSON)
	// GET /personnes/{id}/livres
	public List<Bibliotheque> listerBiblios(@PathParam("id") Long id) {
		List<Bibliotheque> S= personneRepository.findById(id).get().getBiblios();
		int k= S.size();
		return S;
	}
	

	@POST
	@Path("{idPersonne}/livres")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLivreDejaExistant(@PathParam("idPersonne") Long idPersonne, LivreInput livres) {
		Optional<Personne> pOpt = personneRepository.findById(idPersonne);
		Optional<Livre> lOpt = livreRepository.findById(livres.getIdLivre());

		if (!pOpt.isPresent() || !lOpt.isPresent()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		Personne p = pOpt.get();
		Livre l = lOpt.get();
		p.getLivres().add(l);
		personneRepository.save(p);
		return Response.ok(p).build();
	}
	
	
	@POST
	@Path("{idPersonne}/biblio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBiblioDejaExistant(@PathParam("idPersonne") Long idPersonne, BibliothequeInput biblios) {
		Optional<Personne> pOpt = personneRepository.findById(idPersonne);
		Bibliotheque b= new Bibliotheque(biblios.getNomBiblio());
		Optional<Bibliotheque> lOpt = biblioRepository.findById(b.getId());

		if (!pOpt.isPresent() || !lOpt.isPresent()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		Personne p = pOpt.get();
		Bibliotheque bi = lOpt.get();
		p.getBiblios().add(bi);
		personneRepository.save(p);
		return Response.ok(p).build();
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPersonne(PersonneInput L) {
        Optional<Livre> optL = livreRepository.findById(L.getIdLivre());
        Optional<Bibliotheque> optB = biblioRepository.findById(L.getIdBiblio());
        if((!optL.isPresent()) || (!optB.isPresent()) )
            return Response.status(Response.Status.NOT_FOUND).build();
        
        Personne p = new Personne(L.getPrenom(), L.getNom(), L.getAge(), L.getDate(), L.getVille());
        
        p.getLivres().add(optL.get());
        p.getBiblios().add(optB.get());
        
        personneRepository.save(p);
        return Response.ok(p).build();
    }
	
}

	
	
	
	
	
