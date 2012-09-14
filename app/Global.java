import play.*;

import org.springframework.context.*;
import org.springframework.context.support.*;

public class Global extends GlobalSettings {

	private ApplicationContext ctx;

	@Override
	public void onStart(Application app) {
		ctx = new ClassPathXmlApplicationContext("components.xml");
	}

	@Override
	public <A> A getControllerInstance(Class<A> clazz) {
		return ctx.getBean(clazz);
	}

}