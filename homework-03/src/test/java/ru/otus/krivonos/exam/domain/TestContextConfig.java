package ru.otus.krivonos.exam.domain;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan({"ru.otus.krivonos.exam.domain", "ru.otus.krivonos.exam.infrastructure", "ru.otus.krivonos.exam.config"})
public class TestContextConfig {

}
