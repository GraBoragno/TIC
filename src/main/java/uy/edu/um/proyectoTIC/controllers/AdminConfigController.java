package uy.edu.um.proyectoTIC.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.proyectoTIC.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//@RequestMapping("/admin/config")
//public class AdminConfigController {
//
//    private final AdminService adminService;
//
//    public AdminConfigController(AdminService adminService) {
//        this.adminService = adminService;
//    }
//
//    // Mostrar la página de configuración
//    @GetMapping
//    public String showAdminConfigPage(Model model) {
//        // Obtener todas las configuraciones desde el servicio
//        model.addAttribute("configs", adminService.getAllConfigs());
//        return "admin/config-page"; // Ruta de la vista
//    }
//
//    // Guardar los cambios de configuración
//    @PostMapping("/update")
//    public String updateAdminConfig(@RequestParam("configId") Long configId,
//                                    @RequestParam("value") String value,
//                                    RedirectAttributes redirectAttributes) {
//        try {
//            adminService.updateConfig(configId, value); // Actualizar en el servicio
//            redirectAttributes.addFlashAttribute("success", "Configuración actualizada correctamente.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error al actualizar la configuración: " + e.getMessage());
//        }
//        return "redirect:/admin/config";
//    }
//
//    // Eliminar una configuración
//    @PostMapping("/delete")
//    public String deleteAdminConfig(@RequestParam("configId") Long configId, RedirectAttributes redirectAttributes) {
//        try {
//            adminService.deleteConfig(configId); // Eliminar en el servicio
//            redirectAttributes.addFlashAttribute("success", "Configuración eliminada correctamente.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error al eliminar la configuración: " + e.getMessage());
//        }
//        return "redirect:/admin/config";
//    }
//
//    // Agregar una nueva configuración
//    @PostMapping("/add")
//    public String addAdminConfig(@RequestParam("name") String name,
//                                 @RequestParam("value") String value,
//                                 RedirectAttributes redirectAttributes) {
//        try {
//            adminService.addConfig(name, value); // Agregar nueva configuración
//            redirectAttributes.addFlashAttribute("success", "Nueva configuración añadida correctamente.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error al añadir la configuración: " + e.getMessage());
//        }
//        return "redirect:/admin/config";
//    }
//}
