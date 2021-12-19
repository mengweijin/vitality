package com.github.mengweijin.quickboot.jpa;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 自动填充 @CreatedBy， @LastModifiedBy
 * @author Meng Wei Jin
 * @date Create in 2019-10-28 22:10
 **/
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("SYSTEM");
    }
}
