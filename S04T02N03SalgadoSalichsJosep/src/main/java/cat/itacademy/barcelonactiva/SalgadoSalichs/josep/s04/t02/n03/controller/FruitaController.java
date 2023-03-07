package cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.controller;

import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.domain.Fruita;
import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.service.FruitaService;
import cat.itacademy.barcelonactiva.SalgadoSalichs.josep.s04.t02.n03.model.service.FruitaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the management of Fruita endpoints.
 */
@RestController
@RequestMapping("/fruita")
public class FruitaController {
    //region ATTRIBUTES
    @Autowired
    private FruitaService fruitaService = new FruitaServiceImpl();
    private HttpHeaders httpHeaders = new HttpHeaders();
    private ResponseEntity responseEntity;

    //region MessagesTexts
    private final String UnexpectedError = "Some unexpected error occurred!\nPlease try again later.";
    private final String FruitaNoInfo = "The Fruita received, don't has all necessary information.";
    private final String FruitaNoExist = "The Fruita with ID '%s', dosen't exist on the ddbb.";

    //endregion MessagesTexts

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    {
        httpHeaders.set("API version", "v3.0");
    }

    //endregion CONSTRUCTOR


    //region METHODS

    /**
     * End point to add a new Fruita on the DDBB.
     *
     * @param fruitaIn json with Fruita structure.
     * @return ResponseEntity, with Fruita created in JSON format.
     */
    @Operation(summary = "Add Fruit", description = "Add a new Fruit to DDBB", tags = "ADD")
    @PostMapping(value = "/add")
    public ResponseEntity<Fruita> add(@RequestBody Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruitaCreated;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint version", "v3.1");

            // Save the Fruita
            fruitaCreated = fruitaService.addFruita(fruitaIn);

            // Check the results
            if (fruitaCreated == null) {
                responseEntity = new ResponseEntity<>(String.format("Some error occurred when to save '%s' on the DDBB.",
                        fruitaIn), httpHeaders, HttpStatus.CONFLICT);
            } else if (fruitaCreated.getId().equals("-1") & fruitaCreated.getNom().equals("-1")) {
                responseEntity = new ResponseEntity<>("This Fruita already exist on the table.", httpHeaders,
                        HttpStatus.CONFLICT);
            } else if (fruitaCreated.getId().equals("-2") & fruitaCreated.getNom().equals("-2")) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, HttpStatus.NOT_ACCEPTABLE);
            } else {
                responseEntity = new ResponseEntity<>(fruitaCreated, httpHeaders, HttpStatus.CREATED);
            }

        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    /**
     * End point to delete the Fruita with ID like param in.
     *
     * @param idIn String with Fruita ID.
     * @return ResponseEntity, with Fruita deleted in JSON format.
     */
    @Operation(summary = "Delete Fruit", description = "Delete a Fruit with id like id param", tags = "DELETE")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") String idIn) {
        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v3.2");

            // Call service method to delete fruit & check result.
            switch (fruitaService.deleteFruita(idIn)) {
                case 1 ->
                        responseEntity = new ResponseEntity<>(String.format("Fruita with ID '%s', was deleted correctly!",
                                idIn), httpHeaders, HttpStatus.OK);
                case -1 -> responseEntity = new ResponseEntity<>(String.format("Fruita with ID '%s', wasn't deleted!",
                        idIn), httpHeaders, HttpStatus.CONFLICT);
                case -2 -> responseEntity = new ResponseEntity<>(FruitaNoInfo, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
                default ->
                        responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    /**
     * End point to get all Fruita, without filters.
     *
     * @return ResponseEntity, with Fruita List on JSON format.
     */
    @Operation(summary = "Get all Fruita", description = "Get all Fruit without params.", tags = "GET")
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Fruita>> getAll() {
        //region VARIABLES
        List<Fruita> fruites;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v3.3");

            // Get all Fruites from the table
            fruites = fruitaService.getAll();

            // Check result of search.
            if (fruites == null) {
                responseEntity = new ResponseEntity<>("Some error occurred when to get all Fruita from the DDBB.",
                        httpHeaders, HttpStatus.CONFLICT);
            } else if (fruites.isEmpty()) {
                responseEntity = new ResponseEntity<>("The table is empty.", httpHeaders, HttpStatus.NO_CONTENT);
            } else {
                responseEntity = new ResponseEntity<>(fruites, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    /**
     * End point to get Fruit with id like param.
     *
     * @param idIn String with Fruita ID.
     * @return ResponseEntity, with Fruita in JSON format.
     */
    @Operation(summary = "Get a Fruita", description = "Get a Fruit with ID like the param.", tags = "GET")
    @GetMapping(value = "/getOne/{id}")
    public ResponseEntity<Fruita> getOne(@PathVariable("id") String idIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v3.4");

            // Looking for the Fruita
            fruita = fruitaService.getOne(idIn);

            // Check results of search
            if (fruita == null) {
                // Some error occurred
                responseEntity = new ResponseEntity<>(String.format("Some error occurred when to get Fruita with ID's " +
                        "'%s' from DDBB.", idIn), httpHeaders, HttpStatus.CONFLICT);
            } else if (fruita.getId().equals("-1") & fruita.getNom().equals("-1")) {
                // This id dosen't exist on DDBB
                responseEntity = new ResponseEntity<>(String.format(FruitaNoExist,
                        idIn), httpHeaders, HttpStatus.NO_CONTENT);
            } else if (fruita.getId().equals("-2") & fruita.getNom().equals("-2")) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
            } else {
                responseEntity = new ResponseEntity<>(fruita, httpHeaders, HttpStatus.OK);
            }

        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    /**
     * End point to update Fruita with id like Fruita id of param.
     *
     * @param fruitaIn Fruita class with new Fruita's values.
     * @return ResponseEntity, with Fruita updated in JSON format.
     */
    @Operation(summary = "Update Fruit", description = "Update a Fruit", tags = "PUT")
    @PutMapping(value = "/update")
    public ResponseEntity<Fruita> update(@RequestBody Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v3.5");

            // Modified the Fruita
            fruita = fruitaService.updateFruita(fruitaIn);

            // Check result of modified
            if (fruita == null) {
                // Some error occurred
                responseEntity = new ResponseEntity<>(String.format("Some error occurred when to update '%s' on the ddbb.",
                        fruitaIn), httpHeaders, HttpStatus.CONFLICT);
            } else if (fruita.getId().equals("-1") & fruita.getNom().equals("-1")) {
                // This id dosen't exist on DDBB
                responseEntity = new ResponseEntity<>(String.format(FruitaNoExist,
                        fruitaIn.getId()), httpHeaders, HttpStatus.CONFLICT);
            } else if (fruita.getId().equals("-2") & fruita.getNom().equals("-2")) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
            } else {
                responseEntity = new ResponseEntity<>(fruita, httpHeaders, HttpStatus.OK);
            }

        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    //endregion ENDPOINTS

}



