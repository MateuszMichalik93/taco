package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class FluxMergingTests {

    @Test
    public void mergeFluxes() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(250));
        Flux<String> foodFlux = Flux
                .just("lasagne", "lizaki", "jabłka")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("Garfield")
                .expectNext("lasagne")
                .expectNext("Kojak")
                .expectNext("lizaki")
                .expectNext("Barbossa")
                .expectNext("jabłka")
                .verifyComplete();
    }

    @Test
    public void zipFluxes() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");

        Flux<String> foodFlux = Flux.just("lasagne", "lizaki", "jabłka");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
                .expectNextMatches(p ->
                        p.getT1().equals("Garfield") &&
                        p.getT2().equals("lasagne"))
                .expectNextMatches(p ->
                        p.getT1().equals("Kojak") &&
                                p.getT2().equals("lizaki"))
                .expectNextMatches(p ->
                        p.getT1().equals("Barbossa") &&
                                p.getT2().equals("jabłka"))
                .verifyComplete();

    }

    @Test
    public void zipFluxesToObject() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");

        Flux<String> foodFlux = Flux.just("lasagne", "lizaki", "jabłka");

        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " lubi " + f);

        StepVerifier.create(zippedFlux)
                .expectNext("Garfield lubi lasagne")
                .expectNext("Kojak lubi Lizaki")
                .expectNext("Barbossa lubi jabłka")
                .verifyComplete();
    }

    @Test
    public void firstFlux() {
        Flux<String> slowFlux = Flux.just("żółw lądowy", "ślimak", "leniwiec")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("zając", "puma", "wiewiórka");

        Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);

        StepVerifier.create(firstFlux)
                .expectNext("zając")
                .expectNext("puma")
                .expectNext("wiewiórka")
                .verifyComplete();
    }

}
