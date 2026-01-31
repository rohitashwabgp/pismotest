package com.pismo.test.cards.controller.v2;

import com.pismo.test.cards.dto.request.TransactionRequest;
import com.pismo.test.cards.dto.response.Transaction;
import com.pismo.test.cards.enums.OperationType;
import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.exception.ErrorResponse;
import com.pismo.test.cards.service.v2.TransactionFactoryV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.pismo.test.cards.enums.OperationType.fromId;

@RestController
@RequestMapping("/cards/v2/transactions")
@Tag(name = "Transactions - sync", description = "Synchronous APIs to save transactional event")
public class TransactionControllerV2 {
    private final TransactionFactoryV2 factory;

    public TransactionControllerV2(TransactionFactoryV2 factory) {
        this.factory = factory;
    }

    /**
     *
     * @param request Transaction - Synchronous API
     * @return Transactions - Synchronous API return transaction created
     * @throws AppBusinessException Business exceptions
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Saves transactional events synchronously", description = "Saves transactional events")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "415", description = "Unsupported Media Type",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server ",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Transaction> process(@RequestBody TransactionRequest request) throws AppBusinessException {
        //TODO:  validation
        Optional<OperationType> operationType = fromId(request.getOperationTypeId());
        if (operationType.isEmpty()) {
            throw new AppBusinessException("Invalid operation type", String.valueOf(request.getAccountId()), 404);
        }
        Transaction transaction = factory.get(operationType.get()).process(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

}
