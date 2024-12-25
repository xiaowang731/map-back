package com.example.fs.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MybatisConfig extends DefaultSqlInjector {
    @Bean
    public MetaObjectHandler AutoPopulateHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.fillStrategy(metaObject, "createdTime", LocalDateTime.now());
                this.fillStrategy(metaObject, "updatedTime", LocalDateTime.now());
                this.fillStrategy(metaObject, "removed", false);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.fillStrategy(metaObject, "updatedTime", LocalDateTime.now());
            }
        };
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));// 如果配置多个插件,切记分页最后添加
        return interceptor;
    }

}
