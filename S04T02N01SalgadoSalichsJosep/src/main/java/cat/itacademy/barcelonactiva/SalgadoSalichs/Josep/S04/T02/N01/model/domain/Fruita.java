package cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.domain;

import jakarta.persistence.*;

@Entity
@Table(name="Fruites")
public class Fruita {
    //region ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="nom")
    private String nom;
    @Column(name = "quantitatQuilos")
    private int quantitatQuilos;

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    public Fruita(){}

    public Fruita(int id, String nom, int quantitatQuilos) {
        this.id = id;
        this.nom = nom;
        this.quantitatQuilos = quantitatQuilos;
    }

    public Fruita(Fruita fruitaIn){
        this.id = fruitaIn.id;
        this.nom = fruitaIn.nom;
        this.quantitatQuilos = fruitaIn.quantitatQuilos;
    }

    //endregion CONSTRUCTOR


    //region GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantitatQuilos() {
        return quantitatQuilos;
    }

    public void setQuantitatQuilos(int quantitatQuilos) {
        this.quantitatQuilos = quantitatQuilos;
    }

    //endregion GETTERS & SETTERS


    //region METHODS: OVERRIDE
    @Override
    public String toString(){
        return String.format("Fruita [id= '%s'; nom= '%s'; Quantitat='%s']", this.id, this.nom, this.quantitatQuilos);
    }

    //endregion METHODS: OVERRIDE


}
