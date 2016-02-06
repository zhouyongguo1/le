package le.oa.core.test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apache.xpath.SourceTree;
import org.junit.Assert;
import org.junit.Test;

public class HelleWorldTest {
    @Test
    public void testSayHello() {
        Injector inj = Guice.createInjector(new Module() {

            @Override
            public void configure(Binder binder) {
                binder.bind(HelloWorld.class).to(HelloWorldImpl.class);
            }
        });
        HelloWorld hw = inj.getInstance(HelloWorld.class);
        System.out.println(hw.sayHello());
    }
}
