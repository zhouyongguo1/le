package le.oa.core.event;

import com.google.common.eventbus.Subscribe;

import javax.swing.event.ChangeEvent;

public class EventBusChangeRecorder {
    @Subscribe
    public void recordCustomerChange(String s) {
        System.out.println(s);
    }
}

