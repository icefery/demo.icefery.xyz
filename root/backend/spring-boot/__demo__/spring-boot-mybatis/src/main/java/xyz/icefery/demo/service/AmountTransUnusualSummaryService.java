package xyz.icefery.demo.service;

import xyz.icefery.demo.entity.AmountTransUnusualSummaryDTO;
import java.util.List;

public interface AmountTransUnusualSummaryService {
    List<AmountTransUnusualSummaryDTO> list(AmountTransUnusualSummaryDTO dto);
}