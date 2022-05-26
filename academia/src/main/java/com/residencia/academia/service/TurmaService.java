package com.residencia.academia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.academia.dto.AtividadeDTO;
import com.residencia.academia.dto.InstrutorDTO;
import com.residencia.academia.dto.TurmaDTO;
import com.residencia.academia.entity.Atividade;
import com.residencia.academia.entity.Instrutor;
import com.residencia.academia.entity.Turma;
import com.residencia.academia.repository.TurmaRepository;

@Service
public class TurmaService {
	@Autowired
	TurmaRepository turmaRepository;
	
	@Autowired
	InstrutorService instrutorService;
	
	@Autowired
	AtividadeService atividadeService;

	public List<Turma> findAllTurma() {
		return turmaRepository.findAll();
	}

	public Turma findTurmaById(Integer id) {
		return turmaRepository.findById(id).isPresent() ? turmaRepository.findById(id).get() : null;
	}

	public TurmaDTO findTurmaDTOById(Integer id) {
		Turma turma = turmaRepository.findById(id).isPresent() ? turmaRepository.findById(id).get()
				: null;
		
		TurmaDTO turmaDTO = new TurmaDTO();
		if (null != turma) {
			turmaDTO = converterEntidadeParaDTO(turma);
			return turmaDTO;
		}
		return null;
	}
	
	public Turma saveTurma(Turma turma) {
		return turmaRepository.save(turma);
	}
	
	public TurmaDTO saveTurmaDTO(TurmaDTO turmaDTO) {
		Turma turma= new Turma();
		turma = converteDTOParaEntidade(turmaDTO);
		Turma novaTurma = turmaRepository.save(turma);
		TurmaDTO novaTurmaDTO = converterEntidadeParaDTO(novaTurma);
		
		return novaTurmaDTO;
	}

	public Turma updateTurma(Turma turma) {
		return turmaRepository.save(turma);
	}

	public void deleteTurma(Integer id) {
		turmaRepository.deleteById(id);
	}
	
	private TurmaDTO converterEntidadeParaDTO(Turma turma) {
		TurmaDTO turmaDTO = new TurmaDTO();
		turmaDTO.setDataFim(turma.getDataFim());
		turmaDTO.setDataInicio(turma.getDataInicio());
		turmaDTO.setDuracaoTurma(turma.getDuracaoTurma());
		turmaDTO.setHorarioTurma(turma.getHorarioTurma());
		turmaDTO.setIdTurma(turma.getIdTurma());
		InstrutorDTO instrutorDTO = instrutorService.findInstrutorDTOById(turma.getInstrutor().getIdInstrutor());
        turmaDTO.setInstrutor(instrutorDTO);
        AtividadeDTO atividadeDTO = new AtividadeDTO();
        if(turma.getAtividade() != null) {
        	atividadeDTO = atividadeService.findAtividadeDTOById(turma.getAtividade().getIdAtividade());
        }
        turmaDTO.setAtividade(atividadeDTO);
		return turmaDTO;
	}
	
	private Turma converteDTOParaEntidade(TurmaDTO turmaDTO) {
		Turma turma = new Turma();
		
		turma.setDataFim(turmaDTO.getDataFim());
		turma.setDataInicio(turmaDTO.getDataInicio());
		turma.setDuracaoTurma(turmaDTO.getDuracaoTurma());
		turma.setHorarioTurma(turmaDTO.getHorarioTurma());
		turma.setIdTurma(turmaDTO.getIdTurma());
		
		Instrutor instrutor = instrutorService.findInstrutorById(turmaDTO.getInstrutor().getIdInstrutor());
		turma.setInstrutor(instrutor);
		
		Atividade atividade = atividadeService.findAtividadeById(turmaDTO.getAtividade().getIdAtividade());
		turma.setAtividade(atividade);
		
		//Turma novaTurma = turmaRepository.save(turma);
		return turma;
		
	}
	/*
	public Boolean deleteTurmaComConferencia(Integer id) {
		if(turmaRepository.findById(id).isPresent()) {
			turmaRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}Essa é alteração que acompanha o controller
	*/
}
