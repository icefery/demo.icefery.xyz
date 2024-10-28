package xyz.icefery.demo.service;

import java.util.List;
import xyz.icefery.demo.entity.AmountTransUnusualSummaryDTO;

public interface AmountTransUnusualSummaryService {
    List<AmountTransUnusualSummaryDTO> list(AmountTransUnusualSummaryDTO dto);
}
