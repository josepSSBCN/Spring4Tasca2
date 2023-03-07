package cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.service;

import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.domain.Fruita;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface of Service for Fruita, define all methods it will need.
 */
@Service
public interface FruitaService {
    //region METHODS

    /**
     * Method to add a Fruita on the table.
     *
     * @param fruitaIn Fruita class to add. Data control is not done.
     * @return if return...
     * null => some error occurred
     * ID and nom ="-1" => Fruita already exist;
     * ID!="" => Added correctly.
     */
    Fruita addFruita(Fruita fruitaIn);

    /**
     * Method to delete a Fruit from the table.
     *
     * @param idIn id of Fruit to delete. Data control is not done.
     * @return if return...
     * 1 => all good;
     * -1 => some error occurred;
     * -2 => id data format isn't correct.
     */
    int deleteFruita(String idIn);

    /**
     * Method to get all Fruita of table.
     *
     * @return List of Fruita. If it's empty, some problem occurred.
     */
    List<Fruita> getAll();

    /**
     * Method to get a Fruit with ID as param sended.
     *
     * @param idIn id of Fruit to get. Data control is not done.
     * @return if return...
     * null => some error occurred;
     * id and name = -1 => Fruita id dosen't exist;
     * id and name = -2 => id data format isn't correct.
     */
    Fruita getOne(String idIn);

    /**
     * Method to update a Fruita on the table.
     *
     * @param fruitaIn Fruita class to update. Data control is not done.
     * @return if return...
     * null => some error occurred;
     * id and name = -1 => Fruita id dosen't exist;
     * id and name = -2 => fruitaIn data format isn't correct.
     */
    Fruita updateFruita(Fruita fruitaIn);

    //endregion METHODS

}
