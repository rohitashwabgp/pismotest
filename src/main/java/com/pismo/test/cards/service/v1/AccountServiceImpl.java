package com.pismo.test.cards.service.v1;

import com.pismo.test.cards.config.PropertySource;
import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.dto.request.AccountDto;
import com.pismo.test.cards.domn.Account;
import com.pismo.test.cards.dto.response.AccountDetails;
import com.pismo.test.cards.exception.AppBusinessException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
     * Improvements: Flux can be used
     *
     * @param accountDto AccountDto
     * @param document   MultipartFile
     * @return Mono<Account>
     * @author Rohitashwa Kumar
     */
    @Transactional(rollbackFor = AppBusinessException.class)
    @Override
    public AccountDetails createAccount(AccountDto accountDto, MultipartFile document) throws AppBusinessException {

        try {
            Account account = accountDao.save(new Account(accountDto.getDocumentId()));

            String filePath = String.format(props.getAccount().getUpload().getPath(), account.getAccountId());

            Files.createDirectories(Paths.get(filePath));
            document.transferTo(Paths.get(filePath.concat(document.getName())));
            return buildResponse(account);

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
