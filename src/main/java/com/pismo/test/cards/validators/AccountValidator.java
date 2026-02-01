package com.pismo.test.cards.validators;

import com.pismo.test.cards.config.PropertySource;
import com.pismo.test.cards.dto.request.AccountDto;
import com.pismo.test.cards.exception.AppBusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AccountValidator {
    private final PropertySource props;

    public AccountValidator(PropertySource props) {
        this.props = props;
    }

    public void validate(MultipartFile document, AccountDto accountDto) throws AppBusinessException {
        long size = document.getSize();
        if (size > props.getAccount().getUpload().getMaxSize() && size != -1) {
            throw new AppBusinessException("File too large", accountDto.getDocumentId(), 500);
        }
    }
}
