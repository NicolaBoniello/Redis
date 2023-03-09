package co.develhope.redisCache.services;

import co.develhope.redisCache.entities.UserSuper;
import co.develhope.redisCache.entities.jpa.UserJpa;
import co.develhope.redisCache.entities.redis.UserRedis;
import co.develhope.redisCache.repositories.jpa.UserJpaRepository;
import co.develhope.redisCache.repositories.redis.UserRedisRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserJpaRepository userRepositoryJPA;

    @Autowired
    private UserRedisRepository userRepositoryRedis;

    public UserRedis convertData(UserJpa user){
        UserRedis userRedis = new UserRedis();
        BeanUtils.copyProperties(user, userRedis);
        return userRedis;
    }

    public UserJpa convertData(UserRedis user){
        UserJpa userRedis = new UserJpa();
        BeanUtils.copyProperties(user, userRedis);
        return userRedis;
    }

    public UserJpa create(UserJpa user) {
        if(user == null) return null;
        user.setId(null);
        return userRepositoryJPA.save(user);
    }

    public List<UserJpa> readAll() {
        return userRepositoryJPA.findAll();
    }

    public UserSuper readOne(Long id) {
        Optional<UserRedis> userRedis = userRepositoryRedis.findById(id);
        if (userRedis.isPresent()){
            return userRedis.get();
        }else {
            UserJpa userFromDb = userRepositoryJPA.getById(id);
            userRepositoryRedis.save(convertData(userFromDb));
            return userFromDb;
        }
    }

    public UserJpa update(Long id, UserJpa user) {
        if(user == null) return null;
        user.setId(id);
        UserJpa newUser = userRepositoryJPA.save(user);

        Optional<UserRedis> userRedis = userRepositoryRedis.findById(id);
        if(userRedis.isPresent()) {
            //userRepositoryRedis.deleteById(id); // Update veloce Lettura lenta
            userRepositoryRedis.save(convertData(newUser)); // Update lento  Lettura veloce
        }

        return user;
    }

    public void delete(Long id) {
        userRepositoryJPA.deleteById(id);
        userRepositoryRedis.deleteById(id);
    }

    public void readOneFast(Long id) {
    }
}
