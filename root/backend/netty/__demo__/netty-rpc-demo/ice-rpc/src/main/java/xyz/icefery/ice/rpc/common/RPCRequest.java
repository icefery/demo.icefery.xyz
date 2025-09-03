package xyz.icefery.ice.rpc.common;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RPCRequest {

    private String requestId = UUID.randomUUID().toString();
    private String serviceName;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;
}
