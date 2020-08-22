package com.freeziyou.newcoder.util;

/**
 * @author Dylan Guo
 * @date 8/21/2020 14:52
 * @description TODO
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";


    /**
     * 某个实体的赞 like:entity:entityType:entityId -> set(userId)
     *
     * @param entityType 实体类型
     * @param entityId   实体 Id
     * @return key
     */
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 某个用户的赞
     *
     * @param userId 用户 Id
     * @return key
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

}
