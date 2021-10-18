package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Override
    public Boolean checkTokenAuthorizationHeader(String header) {
        if(StringUtils.isBlank(header)){
            log.error("token为空");
            return false;
        }

        return true;
    }
}
