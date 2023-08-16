package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoAtendimentoDTO;
import br.com.dbc.wbhealth.model.dto.medico.PeriodoDTO;
import br.com.dbc.wbhealth.model.entity.MedicoEntity;
import br.com.dbc.wbhealth.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoRelatorioService {

    private final AtendimentoRepository atendimentoRepository;

    private final MedicoService medicoService;

    public List<MedicoAtendimentoDTO> generateMedicoAtendimento(Integer idMedico, LocalDate dataInicio, LocalDate dataFim) throws EntityNotFound {
        MedicoEntity medico = medicoService.getMedicoById(idMedico);
        if(medico == null) {
            throw new EntityNotFound("Médico não encontrado com o ID: " + idMedico);
        }

        Long quantidadeAtendimentos = atendimentoRepository.countAtendimentosByMedicoAndDateRange(medico, dataInicio, dataFim);

        List<MedicoAtendimentoDTO> atendimento = new ArrayList<>();
        MedicoAtendimentoDTO medicoAtendimentoDTO = new MedicoAtendimentoDTO();
        medicoAtendimentoDTO.setNomeMedico(medico.getPessoa().getNome());
        medicoAtendimentoDTO.setCrm(medico.getCrm());
        medicoAtendimentoDTO.setQuantidadeAtendimentos(quantidadeAtendimentos);

        PeriodoDTO periodoDTO = new PeriodoDTO(dataInicio, dataFim);
        medicoAtendimentoDTO.setPeriodo(periodoDTO);

        atendimento.add(medicoAtendimentoDTO);

        return atendimento;
    }

}
