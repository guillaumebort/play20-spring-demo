Using Spring from within a Play 2.0 application
===============================================

This is a simple application demonstrating how to integrate a Play 2.0 application components with <a href="http://www.springsource.org/">Spring framework</a>.

> Note that the same technique can be applied to any other dependency injection framework.

## How does it work?

There is a few places where the Spring _binding_ is done.

### First, add the spring dependency

In the `project/Build.scala` file, add a dependency to `spring-context`:

```scala
import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "mama"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.springframework" % "spring-context" % "3.0.7.RELEASE"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
```

### Using controllers instances

We wiil use _dynamic_ controller dispatching instead of the _statically compiled_ dispatching used by default in Play framework. To do that, just prefix your controller class name with the `@` symbol in the `routes` file:

```
GET    /       @controllers.Application.index()
```

### Managing controllers instances

The controllers instances management will be delegated to the `Global` object of your application. Here is an implementation of the `Global` using Spring framework:

```java
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
```

And here is the associated `conf/components.xml` file we are using to configure Spring:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:context="http://www.springframework.org/schema/context"
     xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context-3.0.xsd">

   <context:component-scan base-package="controllers,services,dao"/>

</beans>
```

### Creating Spring components

In this example we are using the annotation driven binding for Spring (so we use the annotations provided in `org.springframework.stereotype` to mark our components as _Spring managed_).

First a simple _"Spring style"_ service:

```java
package services;

@org.springframework.stereotype.Service
public class HelloService {

	public String hello() {
		return "Hello world!";
	}

}
```

And now the controller with the service _autowired_:

```java
package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import services.*;

import org.springframework.beans.factory.annotation.*;

@org.springframework.stereotype.Controller
public class Application extends Controller {

	@Autowired
	private HelloService helloService;
  
  	public Result index() {
    	return ok(index.render(helloService.hello()));
  	}
  
}
```

## To go further

You can then integrate any Spring componenents to your application. The auto-reload feature will just work magically. If you plan to use a database or an ORM, you could want to manage it directly via Spring instead of using the default Play plugins.