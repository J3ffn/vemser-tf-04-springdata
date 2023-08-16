package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.NegocioException;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoOutputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalAtendimentoDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalInputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalOutputDTO;
import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import br.com.dbc.wbhealth.model.entity.HospitalEntity;
import br.com.dbc.wbhealth.repository.HospitalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ObjectMapper objectMapper;

    public List<HospitalOutputDTO> findAll() {
        List<HospitalEntity> hospitais = hospitalRepository.findAll();
        return convertListToDTO(hospitais);
    }

    public HospitalOutputDTO findById(Integer idHospital) {
        validateExist(idHospital);
        return convertToDTO(hospitalRepository.getById(idHospital));
    }

    public HospitalOutputDTO save(HospitalInputDTO hospitalInputDTO) {
        HospitalEntity hospital = convertToEntity(hospitalInputDTO);
        HospitalEntity hospitalCadastrado = hospitalRepository.save(hospital);
        return convertToDTO(hospitalCadastrado);
    }

    public HospitalOutputDTO update(Integer idHospital, HospitalInputDTO hospitalInputDTO) {
        HospitalEntity hospital = validateExist(idHospital);
        hospital.setNome(hospitalInputDTO.getNome());

        hospitalRepository.save(hospital);
        return convertToDTO(hospital);
    }

    public void deleteById(Integer idHospital) {
        validateExist(idHospital);
        hospitalRepository.deleteById(idHospital);
    }

    private HospitalEntity validateExist(Integer idHospital) {
        Optional<HospitalEntity> hospitalOptional = hospitalRepository.findById(idHospital);
        validateExist(hospitalOptional);
        return hospitalOptional.get();
    }

    private void validateExist(Optional<HospitalEntity> hospitalOptional) {
        if (hospitalOptional.isEmpty()) {
            throw new NegocioException("Hospital não existe");
        }
    }

    private HospitalEntity convertToEntity(HospitalInputDTO hospitalInputDTO) {
        return objectMapper.convertValue(hospitalInputDTO, HospitalEntity.class);
    }

    private HospitalOutputDTO convertToDTO(HospitalEntity hospital) {
        return objectMapper.convertValue(hospital, HospitalOutputDTO.class);
    }

    private List<HospitalOutputDTO> convertListToDTO(List<HospitalEntity> hospitais) {
        return objectMapper.convertValue(hospitais, List.class);
    }

    public List<HospitalAtendimentoDTO> findHospitaisWithAllAtendimentos(Integer pagina, Integer quantidadeRegistros) {
        Sort ordenacao = Sort.by("idHospital");
        Pageable pageable = PageRequest.of(pagina, quantidadeRegistros, ordenacao);
        Page<HospitalEntity> pacientesPaginados = hospitalRepository.findAll(pageable);

        return pacientesPaginados.getContent().stream().map(this::convertToHospitalAtendimentosDTO).toList();
    }

    private HospitalAtendimentoDTO convertToHospitalAtendimentosDTO(HospitalEntity hospital){
        HospitalAtendimentoDTO pacienteAtendimentosOutput = new HospitalAtendimentoDTO();

        pacienteAtendimentosOutput.setIdHospital(hospital.getIdHospital());
        pacienteAtendimentosOutput.setNome(hospital.getNome());

        List<AtendimentoOutputDTO> atendimentosOutput = hospital.getAtendimentos().stream()
                .map(this::convertAtendimentoToOutput).toList();
        pacienteAtendimentosOutput.setAtendimentos(atendimentosOutput);

        return pacienteAtendimentosOutput;
    }

    private AtendimentoOutputDTO convertAtendimentoToOutput(AtendimentoEntity atendimento) {
        AtendimentoOutputDTO atendimentoOutputDTO = new AtendimentoOutputDTO();
        atendimentoOutputDTO.setIdAtendimento(atendimento.getIdAtendimento());
        atendimentoOutputDTO.setIdHospital(atendimento.getHospitalEntity().getIdHospital());
        atendimentoOutputDTO.setIdPaciente(atendimento.getPacienteEntity().getIdPaciente());
        atendimentoOutputDTO.setIdMedico(atendimento.getMedicoEntity().getIdMedico());
        atendimentoOutputDTO.setLaudo(atendimento.getLaudo());
        atendimentoOutputDTO.setValorDoAtendimento(atendimento.getValorDoAtendimento());
        atendimentoOutputDTO.setTipoDeAtendimento(atendimento.getTipoDeAtendimento().name());
        atendimentoOutputDTO.setDataAtendimento(atendimento.getDataAtendimento());

        return atendimentoOutputDTO;
    }
}
