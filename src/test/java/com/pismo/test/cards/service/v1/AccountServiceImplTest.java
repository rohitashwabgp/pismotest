package com.pismo.test.cards.service.v1;

import com.pismo.test.cards.config.PropertySource;
import com.pismo.test.cards.dao.AccountDao;
import com.pismo.test.cards.domn.Account;
import com.pismo.test.cards.dto.request.AccountDto;
import com.pismo.test.cards.dto.response.AccountDetails;
import com.pismo.test.cards.exception.AppBusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountDao repository;
    @Mock
    private PropertySource.Account accountProperties;
    @Mock
    private PropertySource.Account.Upload accountUploadProperties;
    @Mock
    private PropertySource propertySource;

    @Captor
    private ArgumentCaptor<Long> accountIdCaptor;

    @Test
    void verify_repository_called_with_document_id_when_successful_createAccount() throws AppBusinessException, IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        AccountDto accountDto = new AccountDto();
        accountDto.setDocumentId("DOC123");
        Account account = new Account("DOC123");
        account.setAccountId(10L);
        when(repository.save(any(Account.class))).thenReturn(account);
        when(propertySource.getAccount()).thenReturn(accountProperties);
        when(accountProperties.getUpload()).thenReturn(accountUploadProperties);
        when(accountUploadProperties.getPath()).thenReturn("/mock_path/%s");
        when(file.getName()).thenReturn("TEST_DATA");
        AccountDetails accountDetails = accountService.createAccount(accountDto, file);
        verify(repository, times(1)).save(any());
        assertNotNull(accountDetails);
        verify(file).transferTo(any(Path.class));
        assertEquals(10L, accountDetails.getAccountId());
        assertEquals(accountDto.getDocumentId(), accountDetails.getDocumentId());
    }

    @Test
    void verify_business_exception_when_path_not_set_createAccount() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        AccountDto accountDto = new AccountDto();
        accountDto.setDocumentId("MOCK");
        when(propertySource.getAccount()).thenReturn(accountProperties);
        when(accountProperties.getUpload()).thenReturn(accountUploadProperties);
        assertThrows(AppBusinessException.class, () -> accountService.createAccount(accountDto, file));
    }

    @Test
    void throws_business_exception_when_account_does_not_exists_getAccountDetails() throws AppBusinessException {
        long accountId = 1L;
        Exception ex = assertThrows(AppBusinessException.class, () -> accountService.getAccountDetails(accountId));
        verify(repository, times(1)).findById(accountId);
        assertEquals("Account not found",ex.getMessage());
    }

    @Test
    void returns_account_details_when_account_exists_getAccountDetails() throws AppBusinessException {
        long accountId = 1L;
        Account account = new Account();
        account.setAccountId(accountId);
        when(repository.findById(accountId)).thenReturn(Optional.of(account));
        accountService.getAccountDetails(accountId);
        verify(repository, times(1)).findById(accountIdCaptor.capture());
        assertEquals(accountId, accountIdCaptor.getValue());
    }
}