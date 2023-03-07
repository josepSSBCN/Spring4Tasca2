package cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.service;

import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.domain.Fruita;
import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.repository.FruitaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
            // Initial checks
            if (!checkString(fruitaIn.getNom())) {
                fruitaCreated = new Fruita("-2", "-2", 0);
            } else {

                // Create it
                fruitaIn.setId(null);
                fruitaCreated = fruitaRepoInter.save(fruitaIn);
            }

        } catch (org.springframework.dao.DuplicateKeyException ex) {
            // This excepction appears when the Fruit name already exist.
            fruitaCreated = new Fruita("-1", "-1", -1);
        } catch (Exception ex) {
            fruitaCreated = null;
        }

        //endregion ACTIONS


        // OUT
        return fruitaCreated;

    }

    @Override
    public int deleteFruita(String idIn) {
        //region VARIABLES
        int resul;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initial checks
            if (checkString(idIn)) {
                // Delete Fruita
                fruitaRepoInter.deleteById(idIn);
                resul = 1;
            } else {
                // idIn format data isn't correct.
                resul = -2;
            }
        } catch (Exception ex) {
            resul = -1;
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

            // Loking for all Fruita
            fruites = new ArrayList<>(fruitaRepoInter.findAll());

        } catch (Exception ex) {
            fruites = null;
        }

        //endregion ACTIONS


        // OUT
        return fruites;

    }

    @Override
    public Fruita getOne(String idIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialchecks
            if (checkString(idIn)) {
                // Get Fruita
                fruita = fruitaRepoInter.findById(idIn).get();

            }else{
                fruita = new Fruita("-2", "-2", -2);
            }

        } catch (NoSuchElementException ex) {
            // This exception appears when the idIn isn't on DDBB.
            fruita = new Fruita("-1", "-1", -1);
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
            // Initial checks
            if (checkString(fruitaIn.getId()) && checkString(fruitaIn.getNom())) {
                // Check if the ID exist on the table
                if (fruitaRepoInter.existsById(fruitaIn.getId())) {
                    // Save the Fruita on the ddbb
                    fruita = fruitaRepoInter.save(fruitaIn);

                } else {
                    // Fruita don't exist on table
                    fruita = new Fruita("-1", "-1", -1);
                }
            }else{
                fruita = new Fruita("-2", "-2", -2);
            }

        } catch (Exception ex) {
            fruita = null;
        }

        //endregion ACTIONS


        // OUT
        return fruita;

    }

    //endregion METHODS: OVERRIDE


    //region METHODS: PRIVATES

    /**
     * Method to check if Fruita's data, id and nom is correct, not is null not blank or empty.
     * @param stringIn String what will be checked.
     * @return true = all data is correct; false = some data not is correct.
     */
    public boolean checkString(String stringIn) {
        return (stringIn != null) && !stringIn.trim().isEmpty();
    }

    //endregion METHODS: PRIVATES

}
