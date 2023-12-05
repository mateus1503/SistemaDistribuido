package com.example.demo.controller;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor // cria um construtor com todos os atributos da classe
public class ProdutoController {
    //Injeta um instância do tipo da variável
    private final ProdutoRepository repository;

    @GetMapping
    public List<Produto> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable long id){
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        repository.findAndDelete(id);
    }


    @PostMapping
    public Produto insert(@RequestBody Produto produto){
        return repository.save(produto);
    }

    @PutMapping("{id}")
    public Produto update(@PathVariable long id, @RequestBody Produto produto){
        final var msg = "O ID informado não coincide com o ID do objeto passado";
        if (id != produto.getId())
            throw new ResponseStatusException(HttpStatus.CONFLICT, msg);

        return repository.save(produto);
    }

    /**
     * https://swagger.io/
     * https://www.openapis.org/
     */
}
