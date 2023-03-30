package xyz.icefery.demo.constant;

public class AppConstant {
    public static final long CACHE__CONFIG__TTL = 60 * 10L;
    public static final String CACHE__USER__ID = "user:id";
    public static final String CACHE__USER__USERNAME = "user:username";
    public static final String CACHE__USER__EMAIL = "user:email";

    public static final double BLOOM_FILTER__CONFIG__FALSE_PROBABILITY = 0.01D;
    public static final String BLOOM_FILTER__USER__ID_LIST = "user:id_list";

    public static final String LOCK__BLOOM_FILTER__REBUILD = "bloom-filter:rebuild";
}
