package xyz.icefery.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.entity.AmountTransUnusualSummaryDTO;
import xyz.icefery.demo.mapper.AmountTransUnusualSummaryMapper;
import xyz.icefery.demo.service.AmountTransUnusualSummaryService;

@Service
public class AmountTransUnusualSummaryServiceImpl implements AmountTransUnusualSummaryService {

    @Autowired
    private AmountTransUnusualSummaryMapper mapper;

    @Override
    public List<AmountTransUnusualSummaryDTO> list(AmountTransUnusualSummaryDTO dto) {
        List<AmountTransUnusualSummaryDTO> list = mapper.selectList(dto);
        System.out.println(list);
        return list;
    }
}
