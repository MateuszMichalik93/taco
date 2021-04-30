package sia5;

import lombok.Data;
import org.junit.Test;
import org.springframework.security.access.method.P;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FluxTransformingTests {

    @Test
    public void skipAFew() {
        Flux<String> skipFlux = Flux.just("jeden", "dwa", "pomiń kilka", "dziewiędziesiąt dziewięć", "sto")
                .skip(3);

        StepVerifier.create(skipFlux)
                .expectNext("dziewiędziesiąt dziewięć", "sto")
                .verifyComplete();
    }

    @Test
    public void skipAFewSeconds() {
        Flux<String> skipFlux = Flux.just("jeden", "dwa", "pomiń kilka", "dziewiędziesiąt dziewięć", "sto")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));

        StepVerifier.create(skipFlux)
                .expectNext("dziewiędziesiąt dziewięć", "sto")
                .verifyComplete();
    }

    @Test
    public void take() {
        Flux<String> nationalParkFlux = Flux.just("Park Narodowy Yellowstone", "Park Narodowy Yosemite"
                , "Wielki Kanion Kolorado", "Park Narodowy Zion", "Park Narodowy Grand Teton").take(3);

        StepVerifier.create(nationalParkFlux)
                .expectNext("Park Narodowy Yellowstone", "Park Narodowy Yosemite"
                        , "Wielki Kanion Kolorado");
    }

    @Test
            public void takee() {
        Flux<String> nationalParkFlux = Flux.just("Park Narodowy Yellowstone", "Park Narodowy Yosemite"
                , "Wielki Kanion Kolorado", "Park Narodowy Zion", "Park Narodowy Grand Teton")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Park Narodowy Yellowstone", "Park Narodowy Yosemite"
                        , "Wielki Kanion Kolorado");
    }

    @Test
    public void filter() {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite"
                , "Wielki Kanion Kolorado", "Zion", "Park Narodowy Grand Teton")
                .filter(np -> !np.contains(" "));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Zion")
                .verifyComplete();
    }

    @Test
    public void distinct() {
        Flux<String> animalFlux = Flux.just("pies", "kot", "ptak", "ptak", "mrowkojad")
                .distinct();

        StepVerifier.create(animalFlux)
                .expectNext("pies", "kot", "ptak", "mrowkojad")
                .verifyComplete();
    }

    @Data
    private static class Player {
        private final String firstName;
        private final String lastName;
    }

    @Test
    public void map() {
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(n -> {
                    String[] split = n.split("\\s");
                    return new Player(split[0], split[1]);
                });

        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player ("Stevie", "Kerr"))
                .verifyComplete();
    }

    @Test
    public void flatMap() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(Mono::just)
                .map(p-> {
                    String[] split = p.split("\\s");
                    return new Player(split[0], split[1]);
                })
                .subscribeOn(Schedulers.parallel());

        List<Player> playerList = Arrays.asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player ("Stevie", "Kerr"));

        StepVerifier.create(playerFlux)
                .expectNextMatches(p-> playerList.contains(p))
                .expectNextMatches(p-> playerList.contains(p))
                .expectNextMatches(p-> playerList.contains(p))
                .verifyComplete();
    }
}
