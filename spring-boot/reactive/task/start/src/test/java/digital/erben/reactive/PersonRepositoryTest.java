package digital.erben.reactive;

import static digital.erben.reactive.PersonGenerator.randomPerson;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

@DataR2dbcTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private List<Person> testPeople;

    @BeforeEach
    void setUp() {
        this.testPeople =
            personRepository
                .deleteAll()
                .thenMany(
                    personRepository.saveAll(
                        List.of(randomPerson(), randomPerson(), randomPerson())
                    )
                )
                .toStream()
                .collect(Collectors.toList());
    }

    @Test
    void testFindAll() {
        StepVerifier
            .create(personRepository.findAll())
            .expectNextMatches(person -> person.equals(this.testPeople.getFirst()))
            .expectNextMatches(person -> person.equals(this.testPeople.get(1)))
            .expectNextMatches(person -> person.equals(this.testPeople.get(2)))
            .verifyComplete();
    }

    @Test
    void testFindOne() {
        StepVerifier
            .create(personRepository.findById(this.testPeople.getFirst().getId()))
            .expectNextMatches(person -> person.equals(this.testPeople.getFirst()))
            .verifyComplete();
    }
}
