package com.pismo.test.cards.controller.v1;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.enums.OperationType;
import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.exception.ErrorResponse;
import com.pismo.test.cards.service.v1.transaction.TransactionFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.pismo.test.cards.enums.OperationType.fromId;

@RestController
@RequestMapping("/cards/v1/transactions")
@Tag(name = "Transactions Async", description = "Asynchronous APIs to save transactional event")
public class TransactionController {
    private final TransactionFactory factory;

    public TransactionController(TransactionFactory factory) {
        this.factory = factory;
    }

    /**
     *
     * @param transaction Transaction
     * @return - fire & forget api : message drops to queue & an immediate accepted response is sent
     * @throws AppBusinessException business exceptions
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Saves transactional events asynchronously", description = "Saves transactional events")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "415", description = "Unsupported Media Type",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server ",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> process(@RequestBody TransactionRequest transaction) throws AppBusinessException {
        Optional<OperationType> operationType = fromId(transaction.getOperationTypeId());
        if (operationType.isEmpty()) {
            throw new AppBusinessException("Invalid operation type", String.valueOf(transaction.getAccountId()), 404);
        }
        this.factory.get(operationType.get()).process(transaction);
        String message = "Transaction accepted";
        return ResponseEntity.accepted().body(message);
    }
}
