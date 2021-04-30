package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxBufferingTests {

    @Test
    public void buffer() {
        Flux<String> fruitFlux = Flux.just(
                "Jabłko", "pomarańcza", "banan", "kiwi", "truskawki"
        );

        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier
                .create(bufferedFlux)
                .expectNext(Arrays.asList("Jabłko", "pomarańcza", "banan"))
                .expectNext(Arrays.asList("kiwi", "truskawki"))
                .verifyComplete();
    }
}
