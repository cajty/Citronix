package org.ably.farm_management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ably.farm_management.domain.entity.Harvest;
import org.ably.farm_management.domain.entity.Sale;
import org.ably.farm_management.dto.HarvestDTO;
import org.ably.farm_management.dto.SaleDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.SaleMapper;
import org.ably.farm_management.repository.SaleRepository;
import org.ably.farm_management.service.HarvestService;
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
    private final HarvestService harvestService;


    @Override
    public SaleDTO save(Sale sale) {
        log.info("Sale saved: {}", sale.getId());
        return saleMapper.entityToDTO(saleRepository.save(sale));
    }

    @Override
    @Transactional
    public SaleDTO create(SaleVM saleVM) {
        HarvestDTO harvest = harvestService.findById(saleVM.getHarvestId());
        if(harvest.getQuantityTotal()< saleVM.getQuantity()) {
            throw new BusinessException("Sale quantity exceeds harvest quantity", HttpStatus.BAD_REQUEST);
        }
        Sale sale = saleMapper.vmToEntity(saleVM);
        harvestService.updateQuantity(harvest.getId(), harvest.getQuantityTotal() - saleVM.getQuantity());
        log.info("Sale created: {}", sale.getId());
        return save(sale);
    }


    @Override
    @Transactional
    public SaleDTO update(Long id, SaleVM saleVM) {
        existsById(id); // check if sale exists
        Sale updatedSale = saleMapper.vmToEntity(saleVM);
        updatedSale.setId(id);
        log.info("Sale updated: {}", updatedSale.getId());
        return save(updatedSale);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        existsById(id);
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

    @Override
    public void existsById(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new BusinessException("Sale not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}