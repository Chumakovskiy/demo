package com.zapeka.demo;

import com.zapeka.demo.repository.CountryRepositoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({CountryRepositoryTest.class})
@SpringBootTest
public class DemoApplicationTests {
}