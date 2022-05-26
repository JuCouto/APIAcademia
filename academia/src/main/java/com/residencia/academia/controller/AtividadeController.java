package com.residencia.academia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.academia.dto.AtividadeDTO;
import com.residencia.academia.entity.Atividade;
import com.residencia.academia.exception.NoSuchElementFoundException;
import com.residencia.academia.service.AtividadeService;

@RestController
@RequestMapping("/atividade")
public class AtividadeController {

	@Autowired
	AtividadeService atividadeService;
	
	@GetMapping
	public ResponseEntity<List<Atividade>>findAllAtividade(){
		List<Atividade> atividadeList = atividadeService.findAllAtividade();
		  if (atividadeList.isEmpty()) {
	            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	        } else
		return new ResponseEntity<>(atividadeList,HttpStatus.OK);
	}
	
	@GetMapping("/dto/{id}")
	public ResponseEntity<AtividadeDTO> findAtividadeDTOById(@PathVariable Integer id) {
		AtividadeDTO atividadeDTO = atividadeService.findAtividadeDTOById(id);
		if (null == atividadeDTO)
			throw new NoSuchElementFoundException("Não foi encontrado Instrutor com o id: " + id);
		else
			return new ResponseEntity<>(atividadeDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Atividade> findAtividadeById(@PathVariable Integer id){
	Atividade atividade = atividadeService.findAtividadeById(id);
	if (null == atividade)
		throw new NoSuchElementFoundException("Não foi encontrada Atividade com o id: " + id);
	else
		return new ResponseEntity<>(atividadeService.findAtividadeById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Atividade> saveAtividade(@RequestBody Atividade atividade) {
		return new ResponseEntity<>(atividadeService.saveAtividade(atividade), HttpStatus.CREATED);
	}
	
	@PostMapping("/saveDto")
	public ResponseEntity<AtividadeDTO> saveAtividadeDTO(@RequestBody AtividadeDTO atividadeDTO) {
		AtividadeDTO novaAtividadeDTO = atividadeService.saveAtividadeDTO(atividadeDTO);
		return new ResponseEntity<>(novaAtividadeDTO, HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<Atividade> updateAtividade(@RequestBody Atividade atividade) {
		return new ResponseEntity<>(atividadeService.updateAtividade(atividade), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAtividade(@PathVariable Integer id) {
		Atividade atividade = atividadeService.findAtividadeById(id);
		if (null == atividade)
			throw new NoSuchElementFoundException("Não foi possível excluir, não foi encontrada Atividade com o id: " + id);
		else {
			atividadeService.deleteAtividade(id);
			return new ResponseEntity<>( HttpStatus.OK);
		}
	}
}
