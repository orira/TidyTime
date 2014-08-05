package com.baws.tidytime.module;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.baws.tidytime.adapter.AssignedChoreAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 5/08/14.
 */

@Module(
    injects = AssignedChoreAdapter.class,
    library = true
)
public class CacheModule {
    private LruCache<String, Bitmap> mMemoryCache;

    public CacheModule() {
        initialiseCache();
    }

    private void initialiseCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()) / 1024;
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Provides @Singleton
    public LruCache<String, Bitmap> provideLruCache() {
        return mMemoryCache;
    }
}
