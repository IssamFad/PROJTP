package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Personne{
	
	@Id
	   @GeneratedValue(strategy= GenerationType.AUTO)
	   private Long id;
	   private String prenom;
	   private String nom;
	   private int age;
	   private String date_emprunt;
	   private String ville;
	   
	   @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	   private List<Livre> livres;
	   @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	   private List<Bibliotheque> biblios;
	  
	   
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDate_emprunt() {
		return date_emprunt;
	}
	public void setDate_emprunt(String date_emprunt) {
		this.date_emprunt = date_emprunt;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public List<Livre> getLivres() {
		return livres;
	}
	public void setLivres(List<Livre> livres) {
		this.livres = livres;
	}
	public List<Bibliotheque> getBiblios() {
		return biblios;
	}
	public void setBiblios(List<Bibliotheque> biblios) {
		this.biblios = biblios;
	}
	
	public Personne(Long id, String prenom, String nom, int age, String date_emprunt, String ville, List<Livre> livres,
			List<Bibliotheque> biblios) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.age = age;
		this.date_emprunt = date_emprunt;
		this.ville = ville;
		this.livres = livres;
		this.biblios = biblios;
	}
	
	
	public Personne(String prenom, String nom, int age, String date_emprunt, String ville) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.age = age;
		this.date_emprunt = date_emprunt;
		this.ville = ville;
		this.biblios=new ArrayList<>();
		this.livres=new ArrayList<>();
		
		}
	
	public Personne() {
		super();
		// TODO Auto-generated constructor stub
	}	
}

