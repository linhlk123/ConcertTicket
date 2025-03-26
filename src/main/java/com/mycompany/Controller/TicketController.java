package com.mycompany.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mycompany.Service.TicketService;
import com.mycompany.Model.Ticket;
import java.util.List;


import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/tao_Ticket")
    public String taoTicket() {
        ticketService.taoTicket();
        return "redirect:/ticket/all";
    }

    @GetMapping("/ticket/all")
    public String showBookingTicket(Model model) {
        List<Ticket> listTicket = ticketService.listAll();
        model.addAttribute("listTicket", listTicket);
        return "/booking/index1";
    }
}
