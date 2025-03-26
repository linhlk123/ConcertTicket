package com.mycompany.Service;

import org.springframework.stereotype.Service;

import com.mycompany.Model.Ticket;
import com.mycompany.Model.User;
import com.mycompany.dao.TicketDAO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketDAO ticketDAO;
    public void taoTicket() {
        for(int i = 0; i < 3; i++){
            String rowTempt;
            if(i == 0){
                rowTempt = "A";
            }else if(i == 1){
                rowTempt = "B";
            }else{ 
                rowTempt = "C";
            }
            for (int j = 1; j < 5; j++) {
                Ticket ticket = new Ticket();
                ticket.setTicketName (rowTempt + j);
                ticket.setSeatRow (rowTempt);
                ticket.setPrice(60);
                ticket.setStatus("VIP");
                ticketDAO.save(ticket);
            }
        }

        for(int i = 0; i < 6; i++){
            String rowTempt;
            if(i == 0){
                rowTempt = "D";
            }else if(i == 1){
                rowTempt = "E";
            }else if(i == 2){
                rowTempt = "F";
            }else if(i == 3){
                rowTempt = "G";
            }else if(i == 4){
                rowTempt = "H";
            }else{ 
                rowTempt = "I";
            }
            for (int j = 1; j < 19; j++) {
                if((j>5 && j<10)){
                    continue;
                }
                Ticket ticket = new Ticket();
                if(j < 10){
                    ticket.setTicketName (rowTempt + "0" + j);
                }
                else{
                    ticket.setTicketName (rowTempt + j);
                }
                ticket.setSeatRow (rowTempt);
                ticket.setPrice((rowTempt.equals("D")||rowTempt.equals("E"))? 50:40);
                ticket.setStatus((rowTempt.equals("D")||rowTempt.equals("E"))? "Premium":"Regular");
                ticketDAO.save(ticket);
            }
        }
    }

    public List<Ticket> listAll() {
        return ticketDAO.findAll().stream().sorted((t1, t2) -> {
            return t1.getTicketName().compareTo(t2.getTicketName());
        }).toList();
    }

    public Ticket findByTicketName(String ticketName) {
        return ticketDAO.findByTicketName(ticketName).orElseThrow(() -> new RuntimeException("Ticket not found: "));
    }

    public Ticket save(Ticket ticket) {
        return ticketDAO.save(ticket);
    }

}
