package com.example.demo.controller;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor // cria um construtor com todos os atributos da classe
public class ProdutoController {
    //Injeta um instância do tipo da variável
    private final ProdutoRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@Valid @PathVariable long id){
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                //.orElse(ResponseEntity.notFound().build());
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Produto> insert(@Valid @RequestBody Produto produto){
        final var newProduto = repository.save(produto);
        final var location = URI.create("/produto/" + newProduto.getId());
        return ResponseEntity.created(location).body(newProduto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Produto> update(@PathVariable long id, @RequestBody Produto produto){
        final var msg = "O ID informado não coincide com o ID do objeto passado";
        if (id != produto.getId())
            throw new ResponseStatusException(HttpStatus.CONFLICT, msg);

        return ResponseEntity.ok(repository.save(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@Valid @PathVariable long id){
        repository.findAndDelete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * https://swagger.io/
     * https://www.openapis.org/
     */
}
