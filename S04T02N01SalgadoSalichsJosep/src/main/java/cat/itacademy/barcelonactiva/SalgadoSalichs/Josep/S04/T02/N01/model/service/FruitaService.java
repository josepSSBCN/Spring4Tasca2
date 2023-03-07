package cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.service;

import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.domain.Fruita;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FruitaService {
    //region METHODS

    /**
     * Method to add a Fruita on the table.
     *
     * @param fruitaIn Fruita class to add. Data control is not done.
     * @return if return is null, some error occurred; if return ID=0, Fruita already exist;
     */
    Fruita addFruita(Fruita fruitaIn);

    /**
     * Method to update a Fruita on the table.
     *
     * @param fruitaIn Fruita class to update. Data control is not done.
     * @return if return is null => some error occurred;
     * if return ID=0, Fruita name already exist;
     * if return ID=-1, Fruita id don't exist;
     */
    Fruita updateFruita(Fruita fruitaIn);

    /**
     * Method to delete a Fruit from the table.
     *
     * @param idIn id to Fruit to delete. Data control is not done.
     * @return false = some error occurred; true = all good.
     */
    boolean deleteFruita(int idIn);

    /**
     * Method to get a Fruit with ID as param sended.
     *
     * @param id id to Fruit to get. Data control is not done.
     * @return if return is null => some error occurred;
     * if return ID=0, Fruita id don't exist;
     */
    Fruita getOne(int id);

    /**
     * Method to get all Fruita of table.
     * @return List of Fruita.
     */
    List<Fruita> getAll();


    //endregion METHODS

}
