package pl.mati.taco.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import pl.mati.taco.Order;
import pl.mati.taco.User;
import pl.mati.taco.data.OrderRepository;


import javax.validation.Valid;



@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {


    private OrderProps orderProps;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderProps orderProps, OrderRepository orderRepository) {
        this.orderProps = orderProps;
        this.orderRepository = orderRepository;
    }




    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String proccesOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()){
            return "orderForm";
        }
        order.setUser(user);

        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }


}
