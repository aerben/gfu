package io.erben.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan("io.erben.springboot")
public class UserServiceTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        UserEntity newUser = new UserEntity();
        newUser.setName("John Doe");
        newUser.setEmail("john.doe@example.com");

        testEntityManager.persist(newUser);
        testEntityManager.flush();

        List<UserEntity> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getName()).isEqualTo(newUser.getName());
        assertThat(users.get(0).getEmail()).isEqualTo(newUser.getEmail());
    }
}
