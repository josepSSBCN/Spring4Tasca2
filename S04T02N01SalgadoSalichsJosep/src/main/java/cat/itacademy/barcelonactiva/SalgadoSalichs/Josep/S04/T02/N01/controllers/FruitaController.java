package cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.controllers;

import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.domain.Fruita;
import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.service.FruitaService;
import cat.itacademy.barcelonactiva.SalgadoSalichs.Josep.S04.T02.N01.model.service.FruitaServiceImpl;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    //region CONSTRUCOTRS
    {
        httpHeaders.set("API version", "v1");
    }

    //endregion CONSTRUCOTRS


    //region METHODS: ENDPOINTS

    /**
     * EndPoint to add Fruita on the H2 DDBB.
     *
     * @param fruitaIn Structure class qhat recibe from FrontEnd
     */
    @Operation(
            summary = "Add Fruit", description = "Add a Fruit to H2 DDBB", tags = "ADD",
            externalDocs = @ExternalDocumentation(description = "No have external Docs"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fruita class. Important: id not necessary or put 0",
                    content = @Content(schema = @Schema(implementation = Fruita.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "406", description = "The JSON sended hasn't all or part of information",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Fruita.class),
                                    examples={
                                            @ExampleObject(
                                                    name = "Missing information: name",
                                                    value = "{\n\"id\": 0,\n\"nom\": \"\",\n\"quantitatQuilos\": 23}"
                                            ),
                                            @ExampleObject(
                                                    name = "Missing information: quantitatQuilos",
                                                    value = "{\n\"id\": 0,\n\"nom\": \"Peres\",\n\"quantitatQuilos\": 0}"
                                            )
                                    }
                            )}
                    ),
                    @ApiResponse(
                            responseCode = "409", description = "Problems with save it on ddbb process",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Fruita.class)
                            )}
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Unexpected server error.",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Fruita.class))}
                    )
            }
    )
    @PostMapping(value = "/add")
    public ResponseEntity<Fruita> add(@RequestBody Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruitaCreated;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initialization
            httpHeaders.set("Endpoint version", "v1");

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

    @Operation(summary = "Delete Fruit", description = "Delete a Fruit with id like id param", tags = "DELETE")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer idIn) {
        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v1");

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

    @Operation(summary = "Get all Fruita", description = "Get all Fruit without params.", tags = "GET")
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Fruita>> getAll() {
        //region VARIABLES
        List<Fruita> fruites = new ArrayList<>();

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v1");

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

    @Operation(summary = "Get a Fruita", description = "Get a Fruit with ID like the param.", tags = "GET")
    @GetMapping(value = "/getOne/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer idIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v1");

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

    @Operation(summary = "Update Fruit", description = "Update a Fruit", tags = "PUT")
    @PutMapping(value = "/update")
    public ResponseEntity update(@RequestBody Fruita fruitaIn) {
        //region VARIABLES
        Fruita fruita;

        //endregion VARIABLES


        //region ACTIONS
        try {
            // Initializations
            httpHeaders.set("Endpoint version", "v1");

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

    //endregion METHODS: ENDPOINTS


}
