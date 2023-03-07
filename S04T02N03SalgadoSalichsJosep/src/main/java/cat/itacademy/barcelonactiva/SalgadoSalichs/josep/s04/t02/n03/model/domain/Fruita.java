package cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Structure class, to define Fruita.
 */
@Document(collection = "Fruita")
public class Fruita {
    //region ATTRIBUTES
    @Id
    private String id;
    @Indexed(unique = true)
    private String nom;
    private int quantitatQuilos;

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    public Fruita() {
    }

    public Fruita(String id, String nom, int quantitatQuilos) {
        this.id = id;
        this.nom = nom;
        this.quantitatQuilos = quantitatQuilos;
    }

    public Fruita(Fruita fruitaIn) {
        this.id = fruitaIn.id;
        this.nom = fruitaIn.nom;
        this.quantitatQuilos = fruitaIn.quantitatQuilos;
    }

    //endregion CONSTRUCTOR


    //region GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public String toString() {
        return String.format("Fruita [id= '%s'; nom= '%s'; Quantitat='%s']", this.id, this.nom, this.quantitatQuilos);
    }

    //endregion METHODS: OVERRIDE

}
