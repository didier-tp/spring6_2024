package tp.appliSpring.explicit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tp.appliSpring.explicit.beans.Prefixeur;
import tp.appliSpring.explicit.conf.ExempleConfigExplicite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;


@ExtendWith(SpringExtension.class) //si junit5/jupiter
@ContextConfiguration(classes= {ExempleConfigExplicite.class})
@ActiveProfiles({"maj"})
public class TestPrefixeur {

    @Autowired
    private Prefixeur prefixeur; //à tester

    @Test
    public void testPrefixer(){
        String res = prefixeur.prefixer("spring");
        //
        assertEquals(">>>SPRING",res);
        //Assertions.assertEquals(">>>spring",res);
    }
}
