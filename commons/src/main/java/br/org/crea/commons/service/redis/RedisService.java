package br.org.crea.commons.service.redis;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RedisService {
	
	@Inject Properties properties;
	
	@Inject HttpClientGoApi httpGoApi;
	
	private String hostRedis;
	private String portRedis;
	
	@PostConstruct
	public void before () {
		this.hostRedis = properties.getProperty("redis.remote.host");
		this.portRedis = properties.getProperty("redis.remote.port");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}

	public void setValue(String key, String value) {
		Jedis jedis = new Jedis(hostRedis, new Integer(portRedis));
		jedis.connect();
		jedis.set(key, value);
		jedis.disconnect();
		jedis.close();
	}

	public String getValue(String key) {
		String value = null;
		Jedis jedis = new Jedis(hostRedis, new Integer(portRedis));
		jedis.connect();
		value = jedis.get(key);
		jedis.disconnect();
		jedis.close();
		return value;
	}

	public void delValue(String key) {
		Jedis jedis = new Jedis(hostRedis, new Integer(portRedis));
		jedis.connect();
		jedis.del(key);
		jedis.disconnect();
		jedis.close();
	}
	
	/**
	 * Verifica a disponibilidade do Redis. Com o host e porta informados.
	 * O método getResource lança uma JedisConnectionException caso o serviço não esteja rodando.
	 * @return true / false
	 * */
	@SuppressWarnings("resource")
	public boolean servicoRedisEstaRodando() {
		
		try {
			
			JedisPool pool = new JedisPool(hostRedis, new Integer(portRedis));
		    pool.getResource();
		    return true;
			
		} catch (JedisConnectionException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RedisService || servicoRedisEstaRodando", StringUtil.convertObjectToJson(hostRedis + ":" + portRedis), e);
		}
		return false;
	}

}
