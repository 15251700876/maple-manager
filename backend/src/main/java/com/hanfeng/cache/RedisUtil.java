package com.hanfeng.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * author HanFeng
 */
@Component
public class RedisUtil {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 是否存在key
     *
     * @param key key
     * @return 1
     */
    public boolean hasKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey == null ? false : hasKey;
    }

    /**
     * 普通缓存放入
     * <p>
     * param key 键
     * return true成功 false失败
     */
    public String getString(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey != null && hasKey) {
            logger.info("Redis中查询");
            return (String) redisTemplate.opsForValue().get(key);
        } else {
            String val = "lcoil";
            redisTemplate.opsForValue().set(key, val);
            logger.info("数据库中查询的");
            return val;
        }
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, String value) {
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(key, value);
        return isAbsent == null ? false : isAbsent;
    }

    /**
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return isAbsent == null ? false : isAbsent;
    }

    /**
     * 普通缓存放入
     * <p>
     * param value      值
     * param expireTime 超时时间(秒)
     * return true成功 false失败
     */
    public Boolean set(String key, Object value, int expireTime) {

        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    *获取MAP中的某个值
     *
       param key  键
       param item 项
       return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * <
     * param key 键
     * return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 以map集合的形式添加键值对
     * <p>
     * param key 键
     * param map 对应多个键值
     * return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * <p>
     * param key  键
     * param map  对应多个键值
     * param time 时间(秒)
     * return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * <p>
     * param key   键
     * param item  项
     * param value 值
     * return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * <p>
     * param key   键
     * param item  项
     * param value 值
     * param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * <p>
     * param key  键 不能为null
     * param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {

        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     * <p>
     * param key  键 不能为null
     * param item 项 不能为null
     * return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * <p>
     * param key  键
     * param item 项
     * param by   要增加几(大于0)
     * return
     */
    public long hincr(String key, String item, long by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * <p>
     * param key  键
     * param item 项
     * param by   要减少记(小于0)
     * return
     */
    public long hdecr(String key, String item, long by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 获取指定变量中的hashMap值。
     * <p>
     * param key
     * return 返回LIST对象
     */

    public List<Object> values(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取变量中的键。
     * <p>
     * param key
     * return 返回SET集合
     */

    public Set<Object> keys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取变量的长度。
     * <p>
     * param key 键
     * return 返回长度
     */

    public long size(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 以集合的方式获取变量中的值。
     * <p>
     * param key
     * param list
     * return 返回LIST集合值
     */

    public List multiGet(String key, List list) {
        return redisTemplate.opsForHash().multiGet(key, list);
    }

    /**
     * 如果变量值存在，在变量中可以添加不存在的的键值对
     * 如果变量不存在，则新增一个变量，同时将键值对添加到该变量。
     * <p>
     * param key
     * param hashKey
     * param value
     */

    public void putIfAbsent(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 匹配获取键值对，ScanOptions.NONE为获取全部键对， ScanOptions.scanOptions().match("map1").build()
     * 匹配获取键位map1的键值对,不能模糊匹配。
     * <p>
     * param key
     * param options
     * return
     */

    public Cursor<Map.Entry<Object, Object>> scan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /**
     * 删除变量中的键值对，可以传入多个参数，删除多个键值对。
     * <p>
     * param key      键
     * param hashKeys MAP中的KEY
     */

    public void delete(String key, String... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * param key
     * param seconds
     * return
     */
    public boolean expire(String key, long seconds) {
        Boolean expire = redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return expire == null ? false : expire;

    }

    /**
     * 删除
     * <p>
     * param keys
     */

    public void del(String... keys) {


        if (keys != null && keys.length > 0) {

            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                List<String> list = new ArrayList<>(Arrays.asList(keys));
                redisTemplate.delete((list));
            }
        }
    }


    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 将list放入缓存
     * <p>
     * param key     键
     * param value 值
     * return true 成功 false 失败
     */
    public boolean lpushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * <p>
     * param key     键
     * param value 值
     * param time  时间(秒)
     * return true 成功 false 失败
     */
    public boolean lpushAll(String key, List<Object> value, long time) {
        Boolean flag = false;
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0) {
                expire(key, time);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 将list放入缓存
     * <p>
     * param key     键
     * param value 值
     * return true 成功 false 失败
     */
    public boolean rpushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * <p>
     * param key     键
     * param value 值
     * param time  时间(秒)
     * return true 成功 false 失败
     */
    public boolean rpushAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 在变量左边添加元素值。
     * <p>
     * param key    键
     * param object 值
     * return true 成功 false 失败
     */

    public Boolean lpush(String key, Object object) {
        try {
            redisTemplate.opsForList().leftPush(key, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，如果中间参数值存在的话。
     * <p>
     * param key    键
     * param pivot  中间参数
     * param object 要放的值
     * return 成功 true 失败 false
     */

    public Boolean lpush(String key, Object pivot, Object object) {
        try {
            redisTemplate.opsForList().leftPush(key, pivot, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 集合中第一次出现第二个参数变量元素的右边添加第三个参数变量的元素值。
     * <p>
     * param key    键
     * param pivot  中间参数
     * param object 要放的值
     * return 成功 true 失败 false
     */

    public Boolean rpush(String key, Object pivot, Object object) {
        try {
            redisTemplate.opsForList().rightPush(key, pivot, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向集合最右边添加元素。
     * <p>
     * param key    键
     * param object 值
     * return 成功 true 失败 false
     */


    public Boolean rpush(String key, Object object) {
        try {
            redisTemplate.opsForList().rightPush(key, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在变量左边添加元素值。
     * <p>
     * param key        键
     * param expireTime 超时时间
     * param objects    值
     * return 成功 true 失败 false
     */

    public Boolean lpush(String key, int expireTime, Object... objects) {
        boolean flag = false;
        try {
            redisTemplate.opsForList().leftPush(key, objects);
            if (expireTime > 0) {
                expire(key, expireTime);
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

    /**
     * 在变量右边添加元素值。
     * <p>
     * param key        键
     * param expireTime 超时时间
     * param objects    值
     * return 成功 true 失败 false
     */

    public Boolean rpush(String key, int expireTime, Object... objects) {
        boolean flag = false;
        try {
            redisTemplate.opsForList().rightPush(key, objects);
            if (expireTime > 0) {
                expire(key, expireTime);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

    /**
     * 如果存在集合则向左边添加元素，不存在不加
     * <p>
     * param key    键
     * param object 值
     * return 成功 true 失败 false
     */

    public boolean lPushIfPresent(String key, Object object) {
        try {
            redisTemplate.opsForList().leftPushIfPresent(key, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 如果存在集合则向右边添加元素，不存在不加
     * <p>
     * param key    键
     * param object 返回
     * return 成功 true 失败 false
     */

    public boolean rPushIfPresent(String key, Object object) {
        try {
            redisTemplate.opsForList().rightPushIfPresent(key, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除集合中的左边第一个元素
     * <p>
     * param key 键
     * return 返回右边的第一个元素
     */

    public Object lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中右边的元素。一般用在队列取值
     * <p>
     * param key 键
     * return 返回右边的元素
     */

    public Object rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。一般用在队列取值
     * <p>
     * param key  键
     * param time 时间
     * return 左边的元素
     */

    public Object lpop(String key, long time) {
        return redisTemplate.opsForList().leftPop(key, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。一般用在队列取值
     * <p>
     * param key  键
     * param time 时间
     * return 返回右边元素
     */

    public Object rpop(String key, long time) {
        return redisTemplate.opsForList().rightPop(key, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取指定区间的值。
     * <p>
     * param key   键
     * param start 开始位置
     * param end     结束位置，为-1指结尾的位置， start 0，end -1取所有
     * return
     */

    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取集合长度
     * <p>
     * param key 键
     * return 返回长度
     */

    public Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+n则会报错。
     * <p>
     * param key   键
     * param index 位置
     * param value 值
     */

    public void set(String key, Long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 获取集合指定位置的值
     * <p>
     * param key   键
     * param index 位置
     * return 返回值
     */

    public Object lindex(String key, Long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。count> 0：
     * 删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素。
     * <p>
     * param key    键
     * param count
     * param object
     * return
     */

    public long remove(String key, long count, Object object) {
        return redisTemplate.opsForList().remove(key, count, object);
    }

    /**
     * 截取集合元素长度，保留长度内的数据。
     * <p>
     * param key   键
     * param start 开始位置
     * param end     结束位置
     */

    public void trim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 除集合中右边的元素，同时在左边加入一个元素。
     * <p>
     * param key 键
     * param str 加入的元素
     * return 返回右边的元素
     */

    public Object rightPopAndLeftPush(String key, String str) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key, str);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，同时在左边添加元素，如果超过等待的时间仍没有元素则退出。
     * <p>
     * param key     键
     * param str     左边增中的值
     * param timeout 超时时间
     * return 返回移除右边的元素
     */

    public Object rightPopAndLeftPush(String key, String str, long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key, str, timeout, TimeUnit.MILLISECONDS);
    }


}
