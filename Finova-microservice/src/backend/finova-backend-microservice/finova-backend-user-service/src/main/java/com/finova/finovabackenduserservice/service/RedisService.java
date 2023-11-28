package com.finova.finovabackenduserservice.service;

public interface RedisService {

    /**
     * 存储数据
     * @param key 键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 获取数据
     * @param key 键
     * @return 对应值
     */
    String get(String key);

    /**
     * 设置超期时间
     * @param key 键
     * @param expire 超期时间
     * @return 设置是否成功
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     * @param key 键
     */
    void remove(String key);

    /**
     * 自增操作
     * @param key 键
     * @param delta 自增步长
     * @return ？
     */
    Long increment(String key, long delta);
}
