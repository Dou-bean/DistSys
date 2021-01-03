package edu.illinois.web.distgrep;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/** 
* Utility Tester. 
* 
* @author  Yichen
* @since <pre>Jan 3, 2021</pre> 
* @version 1.0 
*/
public class UtilityTest {

    @Before
    public void before() throws Exception {}

    @After
    public void after() throws Exception {}


    /**
     *
     * Method: JsonReader(String fileName)
     *
     */
    @Test
    public void testJsonReader() throws Exception {
        String path = Utility.class.getClassLoader().getResource("configurationTest.json").getPath();
        path= URLDecoder.decode(path,"utf-8");

        String expected = "{  \"clusters\": [    {      \"url\": \"fa20-cs427-230.cs.illinois.edu\",      " +
                "\"port\": \"2333\",      \"path\": \"/home/yy20/distributed-log-querier/logs/test1.log\"    }  ]}";
        String actual = null;
        try {
            Method method = Utility.class.getDeclaredMethod("JsonReader", String.class);
            method.setAccessible(true);
            actual = (String) method.invoke(null, path);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
            System.err.println("[Error] Method is not obtained!");
        } catch(IllegalAccessException e) {
            e.printStackTrace();
            System.err.println("[Error] This private method cannot be set accessible!");
        } catch(InvocationTargetException e) {
            e.printStackTrace();
            System.err.println("[Error] Method invocation failure!");
        }

        assertEquals(expected, actual);
    }

    /**
     *
     * Method: getJsonObject()
     *
     */
    @Test
    public void testGetServers() throws Exception {
        ConfigurationBean expectedServer = new ConfigurationBean(
                "fa20-cs427-230.cs.illinois.edu",
                "2333",
                "/home/yy20/distributed-log-querier/logs/test1.log"
        );
        List<ConfigurationBean> expectedServers = new ArrayList<>();
        expectedServers.add(expectedServer);

        List<ConfigurationBean> actualServers = Utility.getServers();

        int n1 = expectedServers.size();
        int n2= actualServers.size();

        assertEquals(n1, n2);

        for (int i = 0; i < n1; i++) {
            ConfigurationBean expected = expectedServers.get(i);
            ConfigurationBean actual = actualServers.get(i);
            assertEquals(expected, actual);
        }

    }

}
