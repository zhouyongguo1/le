package le.oa.core.event;

import com.google.common.eventbus.EventBus;
import org.junit.Test;


public class EventBusTest {

    @Test
    public void test_post() {
        EventBus eventBus=new EventBus();
        eventBus.register(new EventBusChangeRecorder());
        System.out.println("--------------------");
        eventBus.post("ddddd");
        System.out.println("--------------------");
    }
}
