package net.cpollet.pocs.filters;

import com.google.common.cache.CacheLoader;
import net.cpollet.pocs.filters.services.FriendsAndFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cpollet on 10.02.17.
 */
@Component
public class FriendAndFamilyCacheLoader extends CacheLoader<String, Boolean> {
    @Autowired
    private FriendsAndFamilyService friendsAndFamilyService;

    @Override
    public Boolean load(String key) throws Exception {
        return friendsAndFamilyService.isFriendAndFamily(key);
    }
}
