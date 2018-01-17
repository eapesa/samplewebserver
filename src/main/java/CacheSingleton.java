import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CacheSingleton {
    private static JedisPool pool;
    private static CacheSingleton instance = null;

    private CacheSingleton() { }

    public static CacheSingleton getInstance() {
        if (instance == null) {
            instance = new CacheSingleton();
        }
        return instance;
    }

    public void initialize() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        pool = new JedisPool(poolConfig, "localhost");
    }

    public Jedis getConn() {
        try {
            return pool.getResource();
        } catch(Exception exception) {
            System.out.println(exception.toString());
            initialize();
            return pool.getResource();
        }
    }


}
