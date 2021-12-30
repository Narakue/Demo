package com.example.projectdemo.pojo;

/**
 * @author Zero
 */
public enum CacheKey {
    /**
     * redis缓存
     */
    HASH_KEY("subject_hash"),
    LIMIT_KEY("subject_limit");

    private final String key;

    CacheKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

