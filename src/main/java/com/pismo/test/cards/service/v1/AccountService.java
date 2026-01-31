package com.pismo.test.cards.service.v1;

import com.pismo.test.cards.dto.request.AccountDto;
import com.pismo.test.cards.dto.response.AccountDetails;
import com.pismo.test.cards.exception.AppBusinessException;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface AccountService {
    AccountDetails createAccount(AccountDto account, MultipartFile document) throws AppBusinessException;

    AccountDetails getAccountDetails(long accountId) throws AppBusinessException;
}
