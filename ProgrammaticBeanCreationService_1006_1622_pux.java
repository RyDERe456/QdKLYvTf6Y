// 代码生成时间: 2025-10-06 16:22:41
package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class ProgrammaticBeanCreationService implements ApplicationContextAware {

    // 注入ApplicationContext
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // 使用PostConstruct注解确保在依赖注入完成后执行
    @PostConstruct
    public void init() {
        // 创建bean实例
        MyBean bean = new MyBean();
        try {
            // 将bean注册到Spring容器中
            applicationContext.getAutowireCapableBeanFactory()
                .autowireBean(bean);

            // 定义bean name（确保唯一性）
            String beanName = "myBean" + System.currentTimeMillis();
            // 注册bean
            applicationContext.getAutowireCapableBeanFactory()
                .registerSingleton(beanName, bean);

            // 打印bean信息（可选）
            System.out.println("Bean registered with name: " + beanName);
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error registering bean: " + e.getMessage());
        }
    }

    // 定义一个简单的bean类
    @Component
    public static class MyBean {
        public void print() {
            System.out.println("Hello from MyBean");
        }
    }
}
