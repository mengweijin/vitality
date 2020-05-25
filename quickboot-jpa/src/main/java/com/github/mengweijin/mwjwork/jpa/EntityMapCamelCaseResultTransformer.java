package com.github.mengweijin.mwjwork.jpa;

import cn.hutool.core.util.StrUtil;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.hibernate.transform.ResultTransformer;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ResultTransformer} implementation which builds a map for each "row",
 * made up  of each aliased value where the alias is the map key.
 * <p/>
 * Since this transformer is stateless, all instances would be considered equal.
 * So for optimization purposes we limit it to a single, singleton {@link #INSTANCE instance}.
 *
 * @author Meng Wei Jin
 */
public class EntityMapCamelCaseResultTransformer extends AliasedTupleSubsetResultTransformer {

    public static final EntityMapCamelCaseResultTransformer INSTANCE = new EntityMapCamelCaseResultTransformer();

    /**
     * Disallow instantiation of EntityMapCamelCaseResultTransformer.
     */
    private EntityMapCamelCaseResultTransformer() {
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map result = new HashMap(tuple.length);
        for (int i = 0; i < tuple.length; i++) {
            String alias = aliases[i];
            if (alias != null) {
                result.put(StrUtil.toCamelCase(alias.toLowerCase()), tuple[i]);
            }
        }
        return result;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
