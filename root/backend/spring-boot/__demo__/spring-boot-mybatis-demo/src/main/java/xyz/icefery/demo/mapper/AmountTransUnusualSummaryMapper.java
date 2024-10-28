package xyz.icefery.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import xyz.icefery.demo.entity.AmountTransUnusualSummaryDTO;

@Mapper
public interface AmountTransUnusualSummaryMapper {
    List<AmountTransUnusualSummaryDTO> selectList(AmountTransUnusualSummaryDTO dto);
}
