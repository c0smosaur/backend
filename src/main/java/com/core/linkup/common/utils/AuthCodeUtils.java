package com.core.linkup.common.utils;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.membership.company.entity.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
public class AuthCodeUtils {

    public String createEmailAuthCode() {
        int len = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder authCode = new StringBuilder();
            for (int i = 0; i < len; i++) {
                authCode.append(random.nextInt(10));
            }
            return authCode.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("AuthCodeUtils.createAuthCode() met an exception");
            throw new BaseException(BaseResponseStatus.AUTHCODE_ISSUE_ERROR);
        }
    }

    public String createCompanyAuthCode(Company company){
        int len = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder authCode = new StringBuilder();
            for (int i = 0; i < len; i++) {
                char alphabet = (char) ('A'+random.nextInt(26));
                authCode.append(alphabet);
            }
            return authCode.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("While creating company authcode, we met an exception");
            throw new BaseException(BaseResponseStatus.AUTHCODE_ISSUE_ERROR);
        }
    }
}
