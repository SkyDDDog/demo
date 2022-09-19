package com.lyd.demo.config;

import com.lczyfz.edp.springboot.core.entity.User;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maple on 2019-07-26.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManagerByEhcache() {
//        CachingProvider cachingProvider = Caching.getCachingProvider();
//        CacheManager cacheManager = null;
//        try {
//            cacheManager = (CacheManager) cachingProvider.getCacheManager(
//                    getClass().getResource("/cache/ehcache-jsr107.xml").toURI(),
//                    getClass().getClassLoader());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        cacheManager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(getClass().getResource("/cache/ehcache-jsr107.xml")));

        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File("../temp/demo", "ehcache")))
                .withCache("sysCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, HashMap.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(2000, EntryUnit.ENTRIES)
                                        .offheap(10, MemoryUnit.MB)
                                        .disk(100, MemoryUnit.MB, true)
                        ).withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(60)))
                )
                .withCache("userCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, User.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(2000, EntryUnit.ENTRIES)
                                        .offheap(10, MemoryUnit.MB)
                                        .disk(100, MemoryUnit.MB, true)
                        ).withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(60)))
                )
                .withCache("roleCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(2000, EntryUnit.ENTRIES)
                                        .offheap(10, MemoryUnit.MB)
                                        .disk(100, MemoryUnit.MB, true)
                        ).withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(60)))
                )
                .withCache("menuCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(2000, EntryUnit.ENTRIES)
                                        .offheap(10, MemoryUnit.MB)
                                        .disk(100, MemoryUnit.MB, true)
                        ).withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(60)))
                )
                .build(true);


//        cacheManager.init();
        return persistentCacheManager;
    }
}
