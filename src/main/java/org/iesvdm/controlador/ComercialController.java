package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComercialController {

    private final ComercialService comercialService;

    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales")
    public String listar(Model model) {
        List<Comercial> listaComerciales = comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);
        return "comerciales";
    }

    @GetMapping("/comerciales/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("comercial", new Comercial());
        model.addAttribute("formAction", "/comerciales/crear");
        return "formularioComercial";
    }

    @PostMapping("/comerciales/crear")
    public String crear(@ModelAttribute Comercial comercial) {
        comercialService.newComercial(comercial);
        return "redirect:/comerciales";
    }

    @GetMapping("/comerciales/editor/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Comercial comercial = comercialService.findComercial(id)
                .orElseThrow(() -> new IllegalArgumentException("Comercial no encontrado: " + id));
        model.addAttribute("comercial", comercial);
        model.addAttribute("formAction", "/comerciales/editor/" + id);
        return "formularioComercial";
    }

    @PostMapping("/comerciales/editor/{id}")
    public String editar(@PathVariable int id, @ModelAttribute Comercial comercial) {
        comercial.setId(id);
        comercialService.replaceComercial(comercial);
        return "redirect:/comerciales";
    }

    @PostMapping("/comerciales/borrar/{id}")
    public String borrar(@PathVariable int id) {
        comercialService.deleteComercial(id);
        return "redirect:/comerciales";
    }
}