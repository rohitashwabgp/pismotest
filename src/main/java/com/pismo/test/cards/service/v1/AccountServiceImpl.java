package com.pismo.test.cards.service.v1;

import com.pismo.test.cards.config.PropertySource;
import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dto.request.AccountDto;
import com.pismo.test.cards.domn.Account;
import com.pismo.test.cards.dto.response.AccountDetails;
import com.pismo.test.cards.exception.AppBusinessException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final PropertySource props;

    public AccountServiceImpl(AccountDao accountDao, PropertySource props) {
        this.accountDao = accountDao;
        this.props = props;
    }

    /**
     * Improvements: improvement on flux - make  transaction reactive by using proper libraries ||
     * chunk based blocking processing || S3
     *
     * @param accountDto AccountDto
     * @param document   FilePart
     * @return Mono<Account>
     * @author Rohitashwa Kumar
     */
    @Transactional
    @Override
    public Mono<AccountDetails> createAccount(AccountDto accountDto, FilePart document) throws AppBusinessException {

        try {
            Account account = accountDao.save(new Account(accountDto.getDocumentId()));

            String filePath = String.format(props.getAccount().getUpload().getPath(), account.getAccountId());

            Files.createDirectories(Paths.get(filePath));

            return document.transferTo(Paths.get(filePath.concat(document.filename()))).then(Mono.fromCallable(() -> buildResponse(account)))
                    // Roll back compensating
                    .onErrorResume(e -> Mono.fromRunnable(() -> accountDao.delete(account))
                            .then(Mono.error(new RuntimeException("File upload failed, DB rolled back", e))));

        } catch (Exception e) {
            throw new AppBusinessException(ExceptionUtils.getRootCauseMessage(e), accountDto.getDocumentId(), 500);
        }
    }


    @Override
    public AccountDetails getAccountDetails(long accountId) throws AppBusinessException {
        Optional<Account> account = accountDao.findById(accountId);
        if (account.isEmpty()) {
            throw new AppBusinessException("Account not found", String.valueOf(accountId), 404);
        }
        return new AccountDetails.Builder().accountId(account.get().getAccountId()).documentId(account.get().getDocumentNumber()).build();
    }

    private AccountDetails buildResponse(Account account) {
        return new AccountDetails.Builder().accountId(account.getAccountId()).documentId(account.getDocumentNumber()).build();
    }
}
