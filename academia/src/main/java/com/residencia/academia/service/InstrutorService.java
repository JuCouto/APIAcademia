package com.residencia.academia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.academia.dto.InstrutorDTO;
import com.residencia.academia.dto.TurmaDTO;
import com.residencia.academia.entity.Instrutor;
import com.residencia.academia.entity.Turma;
import com.residencia.academia.repository.InstrutorRepository;

@Service
public class InstrutorService {
	@Autowired
	InstrutorRepository instrutorRepository;

	public List<Instrutor> findAllInstrutor() {
		return instrutorRepository.findAll();
	}

	public Instrutor findInstrutorById(Integer id) {
		return instrutorRepository.findById(id).orElse(null);
		
	}

	public InstrutorDTO findInstrutorDTOById(Integer id) {
		Instrutor instrutor = instrutorRepository.findById(id).isPresent() ? instrutorRepository.findById(id).get()
				: null;

		InstrutorDTO instrutorDTO = new InstrutorDTO();
		if (null != instrutor) {
			instrutorDTO = converterEntidadeParaDTO(instrutor);
			return instrutorDTO;
		}
		return null;
	}

	public Instrutor saveInstrutor(Instrutor instrutor) {
		return instrutorRepository.save(instrutor);
	}

	public InstrutorDTO saveInstrutorDTO(InstrutorDTO instrutorDTO) {
		Instrutor instrutorSalvar = new Instrutor();
		// BeanUtils.copyProperties(instrutorDTO, instrutorSalvar);
		instrutorSalvar = converteDTOParaEntidade(instrutorDTO);
		Instrutor novoInstrutor = instrutorRepository.save(instrutorSalvar);
		InstrutorDTO novoInstrutorDTO = converterEntidadeParaDTO(novoInstrutor);

		return novoInstrutorDTO;
	}

	public Instrutor updateInstrutor(Instrutor instrutor) {
		return instrutorRepository.save(instrutor);
	}

	public void deleteInstrutor(Integer id) {
		Instrutor inst = instrutorRepository.findById(id).get();
		instrutorRepository.delete(inst);
	}

	public void deleteInstrutor(Instrutor instrutor) {
		instrutorRepository.delete(instrutor);
	}

	private InstrutorDTO converterEntidadeParaDTO(Instrutor instrutor) {
		InstrutorDTO instrutorDTO = new InstrutorDTO();
		instrutorDTO.setDataNascimento(instrutor.getDataNascimento());
		instrutorDTO.setIdInstrutor(instrutor.getIdInstrutor());
		instrutorDTO.setNomeInstrutor(instrutor.getNomeInstrutor());
		instrutorDTO.setRgInstrutor(instrutor.getRgInstrutor());
		instrutorDTO.setTitulacaoInstrutor(instrutor.getTitulacaoInstrutor());

		List<TurmaDTO> listTurmaDTO = new ArrayList<>();
		if (instrutor.getTurmaList() != null) {
			for (Turma turma : instrutor.getTurmaList()) {
				TurmaDTO turmaDTO = new TurmaDTO();
				turmaDTO.setDataFim(turma.getDataFim());
				turmaDTO.setDataInicio(turma.getDataInicio());
				turmaDTO.setDuracaoTurma(turma.getDuracaoTurma());
				turmaDTO.setHorarioTurma(turma.getHorarioTurma());
				turmaDTO.setIdTurma(turma.getIdTurma());

				listTurmaDTO.add(turmaDTO);
			}
		}
		instrutorDTO.setTurmaDTOList(listTurmaDTO);
		return instrutorDTO;
	}
	
	private Instrutor converteDTOParaEntidade(InstrutorDTO instrutorDTO) {
		Instrutor instrutorSalvar = new Instrutor();
		
		instrutorSalvar.setDataNascimento(instrutorDTO.getDataNascimento());
		instrutorSalvar.setNomeInstrutor(instrutorDTO.getNomeInstrutor());
		instrutorSalvar.setRgInstrutor(instrutorDTO.getRgInstrutor());
		instrutorSalvar.setTitulacaoInstrutor(instrutorDTO.getTitulacaoInstrutor());
		
		List<Turma> listaTurma = new ArrayList<>();
		List<TurmaDTO> turmaDTOList = instrutorDTO.getTurmaDTOList();
		// vou percorrer cada item dentro da turmaDTOList e salvar ele na listaTurma
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
		instrutorSalvar.setTurmaList(listaTurma);
		return instrutorSalvar;
	}
	
}
