package org.iesvdm.controlador;

import java.util.List;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String listar(Model model) {
        List<Cliente> listaClientes = clienteService.listAll();
        model.addAttribute("listaClientes", listaClientes);
        return "clientes";
    }

    @GetMapping("/clientes/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("formAction", "/clientes/crear");
        return "formularioCliente";
    }

    @PostMapping("/clientes/crear")
    public String crear(@ModelAttribute Cliente cliente) {
        clienteService.newCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/editor/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.findCliente(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        model.addAttribute("cliente", cliente);
        model.addAttribute("formAction", "/clientes/editor/" + id);
        return "formularioCliente";
    }

    @PostMapping("/clientes/editor/{id}")
    public String editar(@PathVariable int id, @ModelAttribute Cliente cliente) {
        cliente.setId(id);
        clienteService.replaceCliente(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/borrar/{id}")
    public String borrar(@PathVariable int id) {
        clienteService.deleteCliente(id);
        return "redirect:/clientes";
    }
}