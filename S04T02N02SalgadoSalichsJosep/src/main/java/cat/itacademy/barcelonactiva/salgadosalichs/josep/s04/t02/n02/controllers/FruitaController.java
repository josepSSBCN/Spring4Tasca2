package cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.controllers;

import cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.domain.Fruita;
import cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.services.FruitaService;
import cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02.model.services.FruitaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private String UnexpectedError = "Some unexpected error occurred!\nPlease try again later.";
    private String FruitaNoInfo = "The Fruita received, don't has all necessary information.";

    //endregion MessagesTexts

    //endregion ATTRIBUTES


    //region CONSTRUCTOR
    {
        httpHeaders.set("API version", "v2.");
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
            httpHeaders.set("Endpoint version", "v2.1");

            // Initial checks
            if (fruitaIn.getNom() == null || fruitaIn.getQuantitatQuilos() == 0) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, HttpStatus.NOT_ACCEPTABLE);
            } else {
                // Save the Fruita
                fruitaCreated = fruitaService.addFruita(fruitaIn);

                // Check the results
                if (fruitaCreated == null) {
                    responseEntity = new ResponseEntity<>(String.format("Some error occured when to save '%s' on the DDBB.",
                            fruitaIn.toString()), httpHeaders, HttpStatus.CONFLICT);
                } else if (fruitaCreated.getId() == 0) {
                    responseEntity = new ResponseEntity<>(String.format("This Fruita already exist on the table.",
                            fruitaIn.toString()), httpHeaders, HttpStatus.CONFLICT);
                } else {
                    responseEntity = new ResponseEntity<>(fruitaCreated, httpHeaders, HttpStatus.CREATED);
                }
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
    public ResponseEntity delete(@PathVariable("id") Integer idIn) {
        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v2.2");

            // Initial checks
            if (idIn == 0) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
            } else {
                // Delete Fruita
                if (fruitaService.deleteFruita(idIn)) {
                    responseEntity = new ResponseEntity<>(String.format("Fruita with ID's '%s', was deleted correctly!",
                            idIn), httpHeaders, HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(String.format("Fruita with ID's '%s', wasn't deleted!",
                            idIn), httpHeaders, HttpStatus.CONFLICT);
                }

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
        List<Fruita> fruites = new ArrayList<>();

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v2.3");

            // Find all Fruites on table
            fruites.addAll(fruitaService.getAll());

            // Check result of find
            if (fruites.isEmpty()) {
                responseEntity = new ResponseEntity<>("The table is empty.", httpHeaders, HttpStatus.NO_CONTENT);
            } else if (fruites == null) {
                responseEntity = new ResponseEntity<>("Some error occurred when to get all Fruita from the DDBB.",
                        httpHeaders, HttpStatus.CONFLICT);
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
    public ResponseEntity getOne(@PathVariable("id") Integer idIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v2.4");

            // Initial checks
            if (idIn == 0) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
            } else {
                // Looking for the Fruita
                fruita = fruitaService.getOne(idIn);

                // Check results of search
                if (fruita == null) {
                    responseEntity = new ResponseEntity<>(String.format("Some error occurred when to get Fruita with ID's '%s' from DDBB.",
                            idIn), httpHeaders, HttpStatus.CONFLICT);
                } else if (fruita.getId() == 0) {
                    responseEntity = new ResponseEntity<>(String.format("The Fruita with id = %s, doesn't exist on the ddbb.",
                            idIn), httpHeaders, HttpStatus.NO_CONTENT);
                } else {
                    responseEntity = new ResponseEntity<>(fruita, httpHeaders, HttpStatus.OK);
                }

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
    public ResponseEntity update(@RequestBody Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v2.5");

            // Initial checks
            if ((fruitaIn.getNom() == null) || (fruitaIn.getId()) == 0 || fruitaIn.getQuantitatQuilos() == 0) {
                responseEntity = new ResponseEntity<>(FruitaNoInfo, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
            } else {
                // Fruita is modified
                fruita = fruitaService.updateFruita(fruitaIn);

                // Check result of modified
                if (fruita == null) {
                    responseEntity = new ResponseEntity<>(String.format("Some error occured when to update '%s' on the DDBB.",
                            fruitaIn.toString()), httpHeaders, HttpStatus.CONFLICT);
                } else if (fruita.getId() == 0) {
                    responseEntity = new ResponseEntity<>(String.format("The Fruita with name %s, already exist on the ddbb.",
                            fruitaIn.getNom()), httpHeaders, HttpStatus.CONFLICT);
                } else if (fruitaIn.getId() == -1) {
                    responseEntity = new ResponseEntity<>(String.format("The Fruita with id = %s, doesn't exist on the ddbb.",
                            fruitaIn.getId()), httpHeaders, HttpStatus.NO_CONTENT);
                } else {
                    responseEntity = new ResponseEntity<>(fruita, httpHeaders, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(UnexpectedError, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //endregion ACTIONS


        // OUT
        return responseEntity;

    }

    //endregion METHODS

}


