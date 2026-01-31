package com.pismo.test.cards.controller.v1;

import com.pismo.test.cards.config.PropertySource;
import com.pismo.test.cards.dto.request.AccountDto;
import com.pismo.test.cards.dto.response.AccountDetails;
import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.service.v1.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Accounts", description = "APIs to get or create Account")
@RestController
@RequestMapping("/cards/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final PropertySource props;

    public AccountController(AccountService accountService, PropertySource props) {
        this.accountService = accountService;
        this.props = props;
    }

    /**
     *
     * @param accountDto AccountDto
     * @param document   FilePart - non-blocking & non-resumable . For resumable chuck based processing required
     * @return created Mono of Account for non-blocking operations
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account", description = "Creates account with verified document upload")
    public Mono<AccountDetails> create(@RequestPart("account") AccountDto accountDto, @RequestPart("document") FilePart document) throws AppBusinessException {
        validate(document, accountDto);
        return accountService.createAccount(accountDto, document);
    }

    /**
     *
     * @param accountId account number
     * @return AccountDetails
     * @throws AppBusinessException validations & business Exceptions
     */
    @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get account by accountId")
    public ResponseEntity<AccountDetails> getAccount(@PathVariable long accountId) throws AppBusinessException {
        AccountDetails accountDetails = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(accountDetails);
    }

    private void validate(FilePart document, AccountDto accountDto) throws AppBusinessException {
        long size = document.headers().getContentLength();
        if (size > props.getAccount().getUpload().getMaxSize() && size != -1) {
            throw new AppBusinessException("File too large", accountDto.getDocumentId(), 500);
        }
    }

}
