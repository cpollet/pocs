package net.cpollet.pocs.filters.services;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

/**
 * Created by cpollet on 10.02.17.
 */
@Service
@Log4j
public class DefaultFriendsAndFamilyService implements FriendsAndFamilyService {
    @Override
    public boolean isFriendAndFamily(String username) {
        log.info(username);
        return true;
    }
}
