package com.mycompany.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.Model.Ticket;
import com.mycompany.Model.User;
import com.mycompany.Service.TicketService;
import com.mycompany.Service.UserNotFoundException;
import com.mycompany.Service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService service;
    private final TicketService ticketService;

    @GetMapping("/home")
    public String showHomePage2() {
        return "home";
    }
    
    @GetMapping("/admin")
    public String showUserList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/{id}")
    public String showUserDetail(@PathVariable Long id, Model model){
            User user = service.getUserById(id);
            List<Ticket> listTicket = user.getTickets();
            model.addAttribute("Tickets", listTicket);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "User Details (ID: " + id + ")");

            return "userdetail";
    }

    @GetMapping("/users/new")
    public String handleNewForm(
        @RequestParam String seats,
        @RequestParam String types,
        @RequestParam String price,
        Model model) {
        User user = new User();
        //user.setPrice(Double.parseDouble(price));
        model.addAttribute("seats", seats);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Add New User");
        return "user_form"; // trang Thymeleaf sẽ được hiển thị cùng dữ liệu
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User newUser, @RequestParam String seats, RedirectAttributes ra) {
        User user = service.getByEmail(newUser.getEmail())
                        .orElseGet(() -> {
                            User u = new User();
                            u.setEmail(newUser.getEmail());
                            u.setFirstName(newUser.getFirstName());
                            u.setLastName(newUser.getLastName());
                            return u;
                        });
        User savedUser = service.save(user);
        List<String> listseat = List.of(seats.split(", "));
        listseat.forEach(System.out::println);
        List<Ticket> tickets = listseat.stream().map(seat -> {
            Ticket ticket = ticketService.findByTicketName(seat);
            ticket.setUser(savedUser);
            return ticketService.save(ticket);
        })
        .toList();
        savedUser.setTickets(tickets);

        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/home";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");

            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            user.getTickets().forEach(ticket -> {
                ticket.setUser(null);
                ticketService.save(ticket);
            });
            service.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin";
    }

}
