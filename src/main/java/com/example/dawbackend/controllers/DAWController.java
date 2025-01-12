package com.example.dawbackend.controllers;

import com.example.dawbackend.repository.ProductRepository;
import com.example.dawbackend.security.UserService;
import com.example.dawbackend.service.ProductService;
import com.example.dawbackend.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api")
@SuppressWarnings({"java:S5738", "java:S4684"})
class DAWController {
    private ProductRepository repository;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.debug("A request has arrived to login");
//        if ("user".equals(request.getUsername()) && "password".equals(request.getPassword())) {
//            return ResponseEntity.ok(new LoginResponse("Bearer dummy-token"));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }

        try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        // Validar la contraseña
            if (userService.validatePassword(request.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(userDetails.getUsername())));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
            }
        }catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }
    }


    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductService>> getAllProducts() {
        log.debug("A request has arrived to get all Products");
        return ResponseEntity.ok(repository.findAll());

    }

    @PostMapping("/product")
    public ProductService createProduct(@RequestBody ProductService product) {
        return repository.save(product);
    }

    @PutMapping("/product")
    public ProductService updateProduct(@RequestBody ProductService person) {
        return repository.save(person);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductService> getProductById(@PathVariable Long id) {
        log.debug("A request has arrived to get product by id: {} ", id);
        Optional<ProductService> productOptional = repository.findById(id);

        // Verificar si el producto está presente
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get()); // Devolver el producto encontrado con estado 200 OK
        } else {
            log.error("Product with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Devolver 404 si no se encuentra el producto
        }
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long id) {
        log.info("A request has been received to delete the product with id {}", id);
        repository.deleteById(id);
    }
}

