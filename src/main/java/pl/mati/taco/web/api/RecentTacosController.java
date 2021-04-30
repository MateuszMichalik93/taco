package pl.mati.taco.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mati.taco.Taco;
import pl.mati.taco.data.TacoRepository;

import java.util.List;

@RepositoryRestController
public class RecentTacosController {
    private TacoRepository tacoRepository;

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }


    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<Resources<TacoResource>> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createAt").descending());

        List<Taco> tacos = tacoRepository.findAll(pageRequest);
        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);

        Resources<TacoResource> recentResources = new Resources<TacoResource>(tacoResources);

        recentResources.add(ControllerLinkBuilder.linkTo(RecentTacosController.class)
                .slash("recent")
                .withRel("recents"));
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }
}
