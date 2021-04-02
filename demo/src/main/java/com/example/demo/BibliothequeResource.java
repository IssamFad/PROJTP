package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("biblio")
public class BibliothequeResource {
    @Autowired
    private BibliothequeRepository BibliothequeRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bibliotheque> listerBibliotheques() {
        List<Bibliotheque> biblios = new ArrayList<>();
        BibliothequeRepository.findAll().forEach(biblios::add);
        return biblios;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bibliotheque creeerBibliotheque(BibliothequeInput bi) {
    	Bibliotheque b= new Bibliotheque(bi.getNomBiblio(),bi.getAdresseBiblio());
        return BibliothequeRepository.save(b);
    }
    
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBibliotheque(@PathParam("id") Long id) {
        if (BibliothequeRepository.findById(id).isPresent()) {
            try {
            	BibliothequeRepository.deleteById(id);
            } catch (Exception e) {
                return Response.serverError().build();
            }
        }
        return Response.noContent().build();
    }



}
