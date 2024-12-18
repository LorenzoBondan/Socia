package br.com.projects.webapi.api.v1.rest.follow;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.publico.follow.DFollow;
import br.com.projects.domain.business.publico.follow.api.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Follow")
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService service;

    public FollowController(FollowService service) {
        this.service = service;
    }

    /**
     * @param columns represent the columns of the table to be searched
     * @param operations represent whether the comparisons will be equality, greater than, less than, or not equal to
     * @param values represent the values to be used as search parameters
     * @param page pagination attribute, represents the page number of the search
     * @param pageSize pagination attribute, represents the total number of elements per page
     * @param sort pagination attribute, represents the sorting order
     */
    @Operation(summary = "Search all Trophies by columns, operations, and values", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(
                    example = """
                    {
                        "numberOfElements": 150,
                        "page": 0,
                        "totalPages": 15,
                        "size": 10,
                        "first": true,
                        "last": false,
                        "sortedBy": "id;d",
                         "content": []
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(value = "columns") List<String> columns,
            @RequestParam(value = "operations", required = false, defaultValue = "=") List<String> operations,
            @RequestParam(value = "values", required = false) List<String> values,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort") List<String> sort) {

        if (values == null || values.isEmpty()) {
            values = List.of("");
        }

        return ResponseEntity.ok(
                service.find(PageableRequest.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .sort(sort.toArray(String[]::new))
                        .colunas(columns)
                        .operacoes(operations)
                        .valores(values)
                        .columnMap(Map.of(
                                "id", "id",
                                "date", "date"
                                ))
                        .build())
        );
    }

    /**
     * @param id represents the ID of the Follow to be searched
     */
    @Operation(summary = "Search a Follow by id", method = "GET", description = "Search an object by id, regardless of its status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> searchById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.find(id));
    }

    /**
     * @param dto represents the Follow object to be created
     */
    @Operation(summary = "Insert a new Follow", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody
            @Schema(
                    description = "Follow object for creation",
                    requiredProperties = "userFollower.id, userFollowing.id, date",
                    example = """
                    {
                        "userFollower": {
                            "id": 1
                        },
                        "userFollowing": {
                            "id": 1
                        },
                        "date": "2020-01-01"
                    }
                    """
            )
            DFollow dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(dto));
    }

    /**
     * @param codes list of Follow codes to be deleted
     */
    @Operation(summary = "Delete a list of Follows", method = "DELETE", description = "The status of the objects is changed to 'Trash'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "206", description = "Partial content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestParam("id") List<Integer> codes){
        List<Integer> successfullyDeleted = new ArrayList<>(), failures = new ArrayList<>();

        codes.forEach(code -> {
            try {
                service.delete(code);
                successfullyDeleted.add(code);
            } catch (Exception e) {
                failures.add(code);
            }
        });

        Map<String, List<Integer>> response = Map.of(
                "successfullyDeleted", successfullyDeleted,
                "failures", failures
        );

        return failures.isEmpty() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
    }
}
