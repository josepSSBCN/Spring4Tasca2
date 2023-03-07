package cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.services;

import cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.domain.Fruita;
import cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.repository.FruitaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of interface of Service for Fruita, implement all methods.
 */
@Service
public class FruitaServiceImpl implements FruitaService {
    //region ATTRIBUTES
    @Autowired
    private FruitaRepo fruitaRepoInter;

    //endregion ATTRIBUTES


    //region METHODS: OVERRIDE
    @Override
    public Fruita addFruita(Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruitaCreated;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check if Fruita name exist on ddbb
            if (fruitaRepoInter.existsByNom(fruitaIn.getNom())) {
                // Exist, can't
                fruitaCreated = new Fruita(0, null, 0);
            } else {
                // Don't exist, can create it
                fruitaCreated = fruitaRepoInter.save(fruitaIn);

                // Check results of create
                if (fruitaCreated == null) {
                    fruitaCreated = fruitaRepoInter.save(fruitaIn);
                }
            }
        } catch (Exception ex) {
            fruitaCreated = null;
        }

        //endregion ACTIONS


        // OUT
        return fruitaCreated;

    }

    @Override
    public boolean deleteFruita(int idIn) {
        //region VARIABLES
        boolean resul;

        //endregion VARIABLES


        //region ACTIONS
        try {
            fruitaRepoInter.deleteById(idIn);
            resul = true;
        } catch (Exception ex) {
            resul = false;
        }

        //endregion ACTIONS


        // OUT
        return resul;

    }

    @Override
    public List<Fruita> getAll() {
        //region VARIABLES
        List<Fruita> fruites;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            fruites = new ArrayList<>();

            // Loking for all Fruita
            fruites.addAll(fruitaRepoInter.findAll());

        } catch (Exception ex) {
            fruites = null;
        }

        //endregion ACTIONS


        // OUT
        return fruites;

    }

    @Override
    public Fruita getOne(int idIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check if exist
            if (fruitaRepoInter.existsById(idIn)) {
                // Get Fruita from ddbb
                fruita = fruitaRepoInter.findById(idIn).get();

            } else {
                fruita = new Fruita(0, null, 0);
            }
        } catch (Exception ex) {
            fruita = null;
        }

        //endregion ACTIONS


        // OUT
        return fruita;

    }

    @Override
    public Fruita updateFruita(Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check if Fruita name exist on the table.
            if (fruitaRepoInter.existsByNom(fruitaIn.getNom())) {
                // Don't exist, don't can be update.
                fruita = new Fruita(0, null, 0);
            } else {
                // Exist, can be update it.
                // Check if the ID exist on the table
                fruita = fruitaRepoInter.findById(fruitaIn.getId()).get();
                if (fruita != null) {
                    fruita.setNom(fruitaIn.getNom());
                    fruita.setQuantitatQuilos(fruitaIn.getQuantitatQuilos());

                    // Save the Fruita on the ddbb
                    fruita = fruitaRepoInter.save(fruita);
                } else {
                    // Fruita's ID don't exist on table
                    fruita = new Fruita(-1, null, -1);
                }
            }

        } catch (Exception ex) {
            fruita = null;
        }

        //endregion ACTIONS


        // OUT
        return fruita;

    }


    //endregion METHODS: OVERRIDE

}
