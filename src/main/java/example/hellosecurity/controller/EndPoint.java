package example.hellosecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/endpoint")
public class EndPoint {

    @GetMapping("/notsecure")
    public ResponseEntity<String> getMessage1() {
        return ResponseEntity.ok("endpoint sin seguridad");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> getMessage2() {
        return ResponseEntity.ok("si esta autenticado");
    }
}
