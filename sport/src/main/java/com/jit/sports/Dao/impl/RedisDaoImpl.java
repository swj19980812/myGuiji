package com.jit.sports.Dao.impl;

import com.jit.sports.Dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import org.springframework.stereotype.Repository;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by xushangyu on 2019/8/9.
 */

@Repository
public class RedisDaoImpl implements RedisDao {
        @Autowired
        private StringRedisTemplate template;

        @Override
        public void putHashTable(String tname, Map<String, String> htable){
                HashOperations<String, String ,String> ops = template.opsForHash();
                ops.putAll(tname, htable);

        }

        @Override
        public List<String> getHashTable(String tname, List<String> hkeylist){
                HashOperations<String, String ,String> ops = template.opsForHash();
                return ops.multiGet(tname, hkeylist);
        }

        @Override
        public boolean existTable(String tname, String heky){
                HashOperations<String, String ,String> ops = template.opsForHash();
                return ops.hasKey(tname, heky);
        }

}
