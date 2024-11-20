package org.ably.farm_management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Sale;
import org.ably.farm_management.dto.SaleDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.SaleMapper;
import org.ably.farm_management.repository.SaleRepository;
import org.ably.farm_management.service.SaleService;
import org.ably.farm_management.vm.SaleVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleDTO save(SaleVM saleVM) {
        Sale sale = saleMapper.vmToEntity(saleVM);
        Sale savedSale = saleRepository.save(sale);

        log.info("Sale created: {}", savedSale.getId());
        return saleMapper.entityToDTO(savedSale);
    }

    @Override
    @Transactional
    public SaleDTO update(Long id, SaleVM saleVM) {
        if (!saleRepository.existsById(id)) {
            throw new BusinessException("Sale not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        Sale updatedSale = saleRepository.save(saleMapper.vmToEntity(saleVM));

        log.info("Sale updated: {}", updatedSale.getId());
        return saleMapper.entityToDTO(updatedSale);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new BusinessException("Sale not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        saleRepository.deleteById(id);
        log.info("Sale deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleDTO findById(Long id) {
        return saleRepository.findById(id)
                .map(saleMapper::entityToDTO)
                .orElseThrow(() -> new BusinessException("Sale not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleDTO> findAll() {
        List<Sale> sales = saleRepository.findAll();

        if (sales.isEmpty()) {
            log.warn("No sales found in the database");
        }

        return saleMapper.toDTOList(sales);
    }
}