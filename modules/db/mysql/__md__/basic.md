## 数据库范式

|                       |                                                                        |                                                                           |
| --------------------- | ---------------------------------------------------------------------- | ------------------------------------------------------------------------- |
| 第一范式（1NF）       | 所有属性都不可再分（1NF 是所有关系型数据库的最基本要求）               |                                                                           |
| 第二范式（2NF）       | 非主属性完全依赖于候选码（消除非主属性对候选码的部分函数依赖）         | ![](https://gitee.com/yu120/lemon-guide/raw/main/images/Database/2NF.png) |
| 第三范式（3NF）       | 非主属性直接依赖于后候选码（消除非主属性对候选码的传递函数依赖）       | ![](https://gitee.com/yu120/lemon-guide/raw/main/images/Database/3NF.png) |
| 巴斯-科德范式（BCNF） | 主属性之间不存在依赖（消除主属性对候选码的部分函数依赖或传递函数依赖） |                                                                           |

## 数据库设计

#### 数据库设计通常步骤

1. **需求分析**：分析用户的需求，包括数据、功能、性能需求
2. **概念结构设计**：主要采用 E-R 模型进行设计，包括画 E-R 图
3. **逻辑结构设计**：通过将 E-R 图转换成表，实现从 E-R 模型到关系模型的转换
4. **物理结构设计**：主要是为所设计的数据库选择合适的存储结构和存取路径
5. **数据库实施**：包括编程、测试和试运行
6. **数据库运行和维护**：系统的运行和数据库的日常维护