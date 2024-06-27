package tp.appliSpringBoot.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import tp.appliSpringBoot.dto.GeoApiGouvFr;
import tp.appliSpringBoot.dto.Zippopotam;

@SpringBootTest() //just for logging config
public class TestMyHttp2Util {
	
	private static Logger logger = LoggerFactory.getLogger(TestMyHttp2Util.class);
	
	private MyHttp2Util myHttp2Util = MyHttp2Util.INSTANCE;
	

	
	@Test
	public void testFetchZippopotam() {
		Zippopotam.Response zippopotamResponse = myHttp2Util.fetch("http://api.zippopotam.us/fr/75001", Zippopotam.Response.class);
		assertNotNull(zippopotamResponse);
		logger.debug("zippopotamResponse="+zippopotamResponse);
	}
	
	@Test
	public void testFetchGeoApiGouvFrCommune() {
		List<GeoApiGouvFr.Commune> communes = myHttp2Util.fetchList("https://geo.api.gouv.fr/communes?codePostal=78000", GeoApiGouvFr.Commune.class);
		assertNotNull(communes);
		logger.debug("communes="+communes);
	}
	
	

}
