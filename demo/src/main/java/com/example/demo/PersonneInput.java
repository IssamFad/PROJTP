package com.example.demo;

public class PersonneInput {
	
	String prenom;
	String nom;
	int age;
	Long idLivre;
	String ville;
	Long  idBiblio;
	String date;
	
	
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
	public Long getIdLivre() {
		return idLivre;
	}
	public void setIdLivre(Long idLivre) {
		this.idLivre = idLivre;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public Long getIdBiblio() {
		return idBiblio;
	}
	public void setIdBiblio(Long idBiblio) {
		this.idBiblio = idBiblio;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public PersonneInput() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonneInput(String prenom, String nom, int age, Long idLivre, String ville, Long idBiblio, String date) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.age = age;
		this.idLivre = idLivre;
		this.ville = ville;
		this.idBiblio = idBiblio;
		this.date = date;
	}
	

}
