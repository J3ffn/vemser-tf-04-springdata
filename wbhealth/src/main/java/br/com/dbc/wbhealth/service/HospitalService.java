package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.NegocioException;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalInputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalOutputDTO;
import br.com.dbc.wbhealth.model.entity.HospitalEntity;
import br.com.dbc.wbhealth.repository.HospitalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        HospitalEntity hospital = hospitalRepository.getById(idHospital);
        validateExist(hospital);
        return hospital;
    }

    private void validateExist(HospitalEntity hospital) {
        if (ObjectUtils.isEmpty(hospital)) {
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
}
