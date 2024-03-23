package xyz.icefery.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.entity.AmountTransUnusualSummaryDTO;
import java.util.List;

@Mapper
public interface AmountTransUnusualSummaryMapper {
    List<AmountTransUnusualSummaryDTO> selectList(AmountTransUnusualSummaryDTO dto);
}