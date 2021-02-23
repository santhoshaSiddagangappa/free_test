package com.freenow.repository;

import com.freenow.FreeNowServerApplicantTestApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = AbstractRepositoryTest.IntegrationTest.class)
public class AbstractRepositoryTest
{

    @Configuration
    @EntityScan( basePackages = {"com.freenow.domainobject"} )
    @EnableAutoConfiguration(exclude = {
            WebMvcAutoConfiguration.class
    })
    protected static class IntegrationTest {
        public static void main(String[] args) {
            SpringApplication.run(FreeNowServerApplicantTestApplication.class, args);
        }
    }

}
