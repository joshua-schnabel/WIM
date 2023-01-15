package de.joshuaschnabel.weinfein.infrastructur.presentation.rest.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.joshuaschnabel.weinfein.infrastructur.presentation.rest.model.GuestDTO;

@RestController
@RequestMapping(path = "api/guests")
public class GuestController {

	private static Map<Integer, GuestDTO> G;

	public static void reset() {

		G = new TreeMap<>();

		G.put(0, GuestDTO.builder().firstname("Max").lastmame("Mustermann").build());
		G.put(0, GuestDTO.builder().firstname("Max").lastmame("Mustermann").build());
	}

	@GetMapping("/")
	public CollectionModel<EntityModel<GuestDTO>> all() {

		var controller = methodOn(GuestController.class);

		var selfLink = linkTo(controller.all()).withSelfRel();

		return IntStream.range(0, G.size()) //
				.mapToObj(this::findOne) //
				.collect(Collectors.collectingAndThen(Collectors.toList(), it -> CollectionModel.of(it, selfLink)));
	}

	@GetMapping("/employees/{id}")
	public EntityModel<GuestDTO> findOne(@PathVariable Integer id) {
		var controller = methodOn(GuestController.class);
		var findOneLink = linkTo(controller.findOne(id)).withSelfRel();
		var employeesLink = linkTo(controller.all()).withRel("employees");

		return EntityModel.of(G.get(id), findOneLink, employeesLink);
	}

}
