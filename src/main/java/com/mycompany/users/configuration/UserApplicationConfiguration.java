package com.mycompany.users.configuration;

import com.mycompany.users.adapter.ouput.database.UserDatabaseAdapter;
import com.mycompany.users.adapter.ouput.database.mapper.UserDbMapper;
import com.mycompany.users.adapter.ouput.database.repository.UserRepository;
import com.mycompany.users.domain.interactor.CreateUserInteractor;
import com.mycompany.users.domain.port.input.CreateUserUseCase;
import com.mycompany.users.domain.port.output.UserPort;
import com.mycompany.users.domain.util.ExceptionUtils;
import com.mycompany.users.domain.util.UserApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserApplicationConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "application.users")
  public UserApplicationProperties properties() {
    return new UserApplicationProperties();
  }

  @Bean
  public ExceptionUtils exceptionUtils(UserApplicationProperties properties) {
    return new ExceptionUtils(properties.getErrors());
  }

  @Bean
  public UserPort userDbAdapter(UserDbMapper userDbMapper, UserRepository userRepository) {
    return new UserDatabaseAdapter(userDbMapper, userRepository);
  }

  @Bean
  public CreateUserUseCase createUserInteractor(ExceptionUtils exceptionUtils, UserPort userPort) {
    return new CreateUserInteractor(exceptionUtils, userPort);
  }
}
