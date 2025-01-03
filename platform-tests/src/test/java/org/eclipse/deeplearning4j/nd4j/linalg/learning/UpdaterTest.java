/*
 *  ******************************************************************************
 *  *
 *  *
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Apache License, Version 2.0 which is available at
 *  * https://www.apache.org/licenses/LICENSE-2.0.
 *  *
 *  *  See the NOTICE file distributed with this work for additional
 *  *  information regarding copyright ownership.
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *  *
 *  * SPDX-License-Identifier: Apache-2.0
 *  *****************************************************************************
 */

package org.eclipse.deeplearning4j.nd4j.linalg.learning;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.nd4j.common.tests.tags.NativeTag;
import org.nd4j.common.tests.tags.TagNames;
import org.nd4j.linalg.BaseNd4jTestWithBackends;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.rng.distribution.Distribution;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.factory.Nd4jBackend;
import org.nd4j.linalg.learning.*;
import org.nd4j.linalg.learning.config.AdaDelta;
import org.nd4j.linalg.learning.config.AdaGrad;
import org.nd4j.linalg.learning.config.AdaMax;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.learning.config.Nesterovs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nd4j.linalg.api.buffer.DataType.FLOAT16;

@Tag(TagNames.TRAINING)
@NativeTag
@Tag(TagNames.DL4J_OLD_API)
public class UpdaterTest extends BaseNd4jTestWithBackends {



    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testNesterovs(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;

        NesterovsUpdater grad = new NesterovsUpdater(new Nesterovs(0.5, 0.9));
        grad.setStateViewArray(Nd4j.zeros(1, rows * cols), new long[]{rows, cols}, 'c', true);
        INDArray W = Nd4j.zeros(rows, cols);
        Distribution dist = Nd4j.getDistributions().createNormal(1, 1);
        for (int i = 0; i < W.rows(); i++)
            W.putRow(i, Nd4j.create(dist.sample(W.columns())));

        for (int i = 0; i < 5; i++) {
            W.addi(Nd4j.randn(rows, cols));
        }
    }

    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testAdaGrad(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;

        AdaGradUpdater grad = new AdaGradUpdater(new AdaGrad(0.1, AdaGrad.DEFAULT_ADAGRAD_EPSILON));
        grad.setStateViewArray(Nd4j.zeros(1, rows * cols), new long[]{rows, cols}, 'c', true);
        INDArray W = Nd4j.zeros(rows, cols);
        Distribution dist = Nd4j.getDistributions().createNormal(1, 1);
        for (int i = 0; i < W.rows(); i++)
            W.putRow(i, Nd4j.create(dist.sample(W.columns())));

        for (int i = 0; i < 5; i++) {
            W.addi(Nd4j.randn(rows, cols));
        }

    }

    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testAdaDelta(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;


        AdaDeltaUpdater grad = new AdaDeltaUpdater(new AdaDelta());
        grad.setStateViewArray(Nd4j.zeros(1, 2 * rows * cols), new long[]{rows, cols}, 'c', true);
        INDArray W = Nd4j.zeros(rows, cols);
        Distribution dist = Nd4j.getDistributions().createNormal(1e-3, 1e-3);
        for (int i = 0; i < W.rows(); i++)
            W.putRow(i, Nd4j.create(dist.sample(W.columns())));

        for (int i = 0; i < 5; i++) {
            W.addi(Nd4j.randn(rows, cols));
        }
    }

    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testAdam(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;


        AdamUpdater grad = new AdamUpdater(new Adam());
        grad.setStateViewArray(Nd4j.zeros(1, 2 * rows * cols), new long[]{rows, cols}, 'c', true);
        INDArray W = Nd4j.zeros(rows, cols);
        Distribution dist = Nd4j.getDistributions().createNormal(1e-3, 1e-3);
        for (int i = 0; i < W.rows(); i++)
            W.putRow(i, Nd4j.create(dist.sample(W.columns())));

        for (int i = 0; i < 5; i++) {
            W.addi(Nd4j.randn(rows, cols));
        }
    }

    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testNadam(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;

        NadamUpdater grad = new NadamUpdater(new Nadam());
        grad.setStateViewArray(Nd4j.zeros(1, 2 * rows * cols), new long[]{rows, cols}, 'c', true);
        INDArray W = Nd4j.zeros(rows, cols);
        Distribution dist = Nd4j.getDistributions().createNormal(1e-3, 1e-3);
        for (int i = 0; i < W.rows(); i++)
            W.putRow(i, Nd4j.create(dist.sample(W.columns())));

        for (int i = 0; i < 5; i++) {
            W.addi(Nd4j.randn(rows, cols));
        }
    }

    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testAdaMax(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;


        AdaMaxUpdater grad = new AdaMaxUpdater(new AdaMax());
        grad.setStateViewArray(Nd4j.zeros(1, 2 * rows * cols), new long[]{rows, cols}, 'c', true);
        INDArray W = Nd4j.zeros(rows, cols);
        Distribution dist = Nd4j.getDistributions().createNormal(1e-3, 1e-3);
        for (int i = 0; i < W.rows(); i++)
            W.putRow(i, Nd4j.create(dist.sample(W.columns())));

        for (int i = 0; i < 5; i++) {
            W.addi(Nd4j.randn(rows, cols));
        }
    }


    @ParameterizedTest
    @MethodSource("org.nd4j.linalg.BaseNd4jTestWithBackends#configs")
    public void testAdamFp16(Nd4jBackend backend) {
        int rows = 10;
        int cols = 2;
        Adam adam = new Adam();
        adam.setEpsilon(1e-6);
        AdamUpdater grad = new AdamUpdater(adam);

        INDArray originalGradients = Nd4j.zeros(rows, cols).castTo(FLOAT16);
        Distribution dist = Nd4j.getDistributions().createNormal(1e-3, 1e-3);
        for (int i = 0; i < originalGradients.rows(); i++) {
            originalGradients.putRow(i, Nd4j.create(dist.sample(originalGradients.columns())).castTo(FLOAT16));
        }
        INDArray gradientsCloned = originalGradients.dup();
        INDArray updates = Nd4j.randn(rows, cols);

        grad.setStateViewArray(Nd4j.zeros(1, 2 * rows * cols).castTo(FLOAT16), new long[]{rows, cols}, 'c', true);
        for (int i = 0; i < 5; i++) {
            grad.applyUpdater(originalGradients, i, 0);
            originalGradients.addi(updates);
        }

        grad.setStateViewArray(Nd4j.zeros(1, 2 * rows * cols).castTo(FLOAT16), new long[]{rows, cols}, 'c', true);
        for (int i = 0; i < 5; i++) {
            grad.applyUpdater(gradientsCloned, i, 0);
            gradientsCloned.addi(updates);
        }

        assertEquals(originalGradients, gradientsCloned);

    }


    @Override
    public char ordering() {
        return 'f';
    }
}
