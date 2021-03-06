package mushop.tests.stream;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oracle.bmc.streaming.StreamClient;

import mushop.stream.AppStartupRunner;
import mushop.stream.StreamApplication;
import mushop.stream.configuration.OciStreamsConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"job.autorun.enabled=false"}, classes=StreamApplication.class)
public class TestConsumer {

	@Autowired
	private AppStartupRunner appStartRunner;
   
	@Autowired
	private OciStreamsConfiguration streamConfig; 
	
	private StreamClient streamClient;
	private String streamId; 

	@Before
	public void setup() {
		
		streamClient = streamConfig.getStreamClient();
		streamId = streamConfig.getStreamId();
	}
	
	@Test
	public void testGetCursorByPartition() {
		
		
		String partition = "0";
		Method method;
		
		assertNotNull(streamConfig);
		assertNotNull(streamClient);
		assertNotNull(streamId);
		
		try {
			method = AppStartupRunner.class.getDeclaredMethod("getCursorByPartition", StreamClient.class, String.class, String.class);
			method.setAccessible(true);
			String cursor = (String) method.invoke(appStartRunner, streamClient, streamId, partition);
			
			assertNotNull(cursor);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
