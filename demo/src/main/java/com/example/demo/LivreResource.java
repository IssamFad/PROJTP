package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("livres")
public class LivreResource {
    @Autowired
    private LivreRepository livreRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Livre> listerLivres() {
        List<Livre> livres = new ArrayList<>();
        livreRepository.findAll().forEach(livres::add);
        return livres;
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Livre creeerLivre(Livre l) {
        return livreRepository.save(l);
    }
    
    

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLivre(@PathParam("id") Long id) {
        if (livreRepository.findById(id).isPresent()) {
            try {
                livreRepository.deleteById(id);
            } catch (Exception e) {
                return Response.serverError().build();
            }
        }
        return Response.noContent().build();
    }
}
