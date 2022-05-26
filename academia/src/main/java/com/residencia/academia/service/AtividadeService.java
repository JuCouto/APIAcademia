package com.residencia.academia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.academia.dto.AtividadeDTO;
import com.residencia.academia.dto.TurmaDTO;
import com.residencia.academia.entity.Atividade;
import com.residencia.academia.entity.Turma;
import com.residencia.academia.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	AtividadeRepository atividadeRepository;
	
	public List<Atividade> findAllAtividade(){
		return atividadeRepository.findAll();
	}
	
	public Atividade findAtividadeById(Integer id) {
		return atividadeRepository.findById(id).isPresent()?
		atividadeRepository.findById(id).get(): null;
	}
	
	public AtividadeDTO findAtividadeDTOById(Integer id) {
		Atividade atividade = atividadeRepository.findById(id).isPresent() ? atividadeRepository.findById(id).get()
				: null;
		
		AtividadeDTO atividadeDTO = new AtividadeDTO();
		if (null != atividade) {
			atividadeDTO = converterEntidadeParaDTO(atividade);
		}
		return atividadeDTO;
	}
	
	public Atividade saveAtividade(Atividade atividade) {
		return atividadeRepository.save(atividade);
	}
	
	public AtividadeDTO saveAtividadeDTO(AtividadeDTO atividadeDTO) {
		Atividade atividade = new Atividade();
		
		atividade = converteDTOParaEntidade(atividadeDTO);
		Atividade novaAtividade = atividadeRepository.save(atividade);
		AtividadeDTO novaAtividadeDTO = converterEntidadeParaDTO(novaAtividade);
		
		return novaAtividadeDTO;
		
	}
	
	public Atividade updateAtividade(Atividade atividade) {
		return atividadeRepository.save(atividade);
	}
	
	public void deleteAtividade(Integer id) {
		atividadeRepository.deleteById(id);
	}
	
	public AtividadeDTO converterEntidadeParaDTO(Atividade atividade) {
		AtividadeDTO atividadeDTO = new AtividadeDTO();
		
		atividadeDTO.setNomeAtividade(atividade.getNomeAtividade());
		atividadeDTO.setIdAtividade(atividade.getIdAtividade());
		
		List<TurmaDTO> listTurmaDTO = new ArrayList<>();
		if (atividade.getTurmaList() != null) {
			for (Turma turma : atividade.getTurmaList()) {
				TurmaDTO turmaDTO = new TurmaDTO();
				turmaDTO.setDataFim(turma.getDataFim());
				turmaDTO.setDataInicio(turma.getDataInicio());
				turmaDTO.setDuracaoTurma(turma.getDuracaoTurma());
				turmaDTO.setHorarioTurma(turma.getHorarioTurma());
				turmaDTO.setIdTurma(turma.getIdTurma());

				listTurmaDTO.add(turmaDTO);
			}
		}
		atividadeDTO.setTurmaDTOList(listTurmaDTO);
		return atividadeDTO;
	}
	
	private Atividade converteDTOParaEntidade(AtividadeDTO atividadeDTO) {
		Atividade atividade = new Atividade();
		
		atividade.setNomeAtividade(atividadeDTO.getNomeAtividade());
		atividade.setIdAtividade(atividadeDTO.getIdAtividade());
		
		List<Turma> listaTurma = new ArrayList<>();
		List<TurmaDTO> turmaDTOList = atividadeDTO.getTurmaDTOList();
		if (turmaDTOList != null) {
			for (TurmaDTO turmaDTO : turmaDTOList) {
				Turma turma = new Turma();
				turma.setDataFim(turmaDTO.getDataFim());
				turma.setDataInicio(turmaDTO.getDataInicio());
				turma.setDuracaoTurma(turmaDTO.getDuracaoTurma());
				turma.setHorarioTurma(turmaDTO.getHorarioTurma());
				turma.setIdTurma(turmaDTO.getIdTurma());

				listaTurma.add(turma);
			}
		}
		atividade.setTurmaList(listaTurma);
		return atividade;
	}
}
