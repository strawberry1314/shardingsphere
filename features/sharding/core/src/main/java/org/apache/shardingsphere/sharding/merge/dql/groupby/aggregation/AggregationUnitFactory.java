/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sharding.merge.dql.groupby.aggregation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.infra.exception.generic.UnsupportedSQLOperationException;
import org.apache.shardingsphere.sql.parser.statement.core.enums.AggregationType;

/**
 * Aggregation unit factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AggregationUnitFactory {
    
    /**
     * Create aggregation unit instance.
     *
     * @param type aggregation function type
     * @param isDistinct is distinct
     * @param separator is separator for group_concat
     * @return aggregation unit instance
     * @throws UnsupportedSQLOperationException unsupported SQL operation exception
     */
    public static AggregationUnit create(final AggregationType type, final boolean isDistinct, final String separator) {
        switch (type) {
            case MAX:
                return new ComparableAggregationUnit(false);
            case MIN:
                return new ComparableAggregationUnit(true);
            case SUM:
                return isDistinct ? new DistinctSumAggregationUnit() : new AccumulationAggregationUnit();
            case COUNT:
                return isDistinct ? new DistinctCountAggregationUnit() : new AccumulationAggregationUnit();
            case AVG:
                return isDistinct ? new DistinctAverageAggregationUnit() : new AverageAggregationUnit();
            case BIT_XOR:
                return new BitXorAggregationUnit();
            case GROUP_CONCAT:
                return isDistinct ? new DistinctGroupConcatAggregationUnit(separator) : new GroupConcatAggregationUnit(separator);
            default:
                throw new UnsupportedSQLOperationException(type.name());
        }
    }
}
