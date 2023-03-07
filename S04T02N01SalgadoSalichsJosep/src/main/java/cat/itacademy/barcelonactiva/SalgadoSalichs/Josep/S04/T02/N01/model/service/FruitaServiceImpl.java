package cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.service;

import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.domain.Fruita;
import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.repository.FruitaRepoInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FruitaServiceImpl implements FruitaService {
    //region ATTRIBUTES
    @Autowired
    private FruitaRepoInter fruitaRepoInter;

    //endregion ATTRIBUTES


    //region METHODS: OVERRIDE
    @Override
    public Fruita addFruita(Fruita fruitaIn) {
        //region VARIABLES
        List<Fruita> fruites = new ArrayList<>();
        Fruita fruitaCreated = new Fruita();

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check if exist
            fruites.addAll(fruitaRepoInter.findbyNom(fruitaIn.getNom()));

            // Check results exist on the table
            if (fruites.isEmpty()) {
                // Don't exist, can create it
                fruitaCreated = fruitaRepoInter.save(fruitaIn);

                // Check results of create
                if (fruitaCreated == null) {
                    fruitaCreated = fruitaRepoInter.save(fruitaIn);
                }

            } else {
                fruitaCreated = new Fruita(0, null, 0);
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
        Fruita fruita = new Fruita();
        List<Fruita> fruitaList = new ArrayList<>();

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Check if Fruita name exist on the table.
            fruitaList.addAll(fruitaRepoInter.findbyNom(fruitaIn.getNom()));

            // Check results exist on the table with other ID.
            if (fruitaList.isEmpty() || (fruitaList.get(0).getId() == fruitaIn.getId() && fruitaList.get(0).getNom().equals(fruitaIn.getNom()))) {
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

    //endregion METHODS: OVERRIDE

}
